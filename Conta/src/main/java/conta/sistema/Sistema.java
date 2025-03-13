package conta.sistema;

import conta.contas.Conta;
import conta.strategy.impl.StrategyCorrente;
import conta.usuarios.Usuario;
import conta.categorias.Categoria;
import conta.transacoes.Transacao;
import conta.enums.TipoContaEnum;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Sistema {
    private Scanner scanner;
    private List<Usuario> usuarios = new ArrayList<>();
    private List<Categoria> categorias = new ArrayList<>();
    private List<Transacao> transacoes = new ArrayList<>();

    public Sistema() {
        this.scanner = new Scanner(System.in);
    }

    public void realizarOperacao(Usuario usuario, double valor) {
        Conta conta = usuario.getConta();
        if (conta != null) {
            conta.adicionarSaldo(valor);
        } else {
            System.out.println("Erro: Usuário não possui conta associada.");
        }
    }

    public void menu() {
        int opcao;
        do {
            System.out.println("\n=== MENU ===");
            System.out.println("1. Cadastrar");
            System.out.println("2. Listar");
            System.out.println("3. Excluir");
            System.out.println("4. Atualizar");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrar();
                    break;
                case 2:
                    listar();
                    break;
                case 3:
                    excluir();
                    break;
                case 4:
                    atualizar();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void cadastrar() {
        System.out.println("\nEscolha o que deseja cadastrar:");
        System.out.println("1. Usuário");
        System.out.println("2. Categoria");
        System.out.println("3. Transação");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        switch (escolha) {
            case 1:
                cadastrarUsuario();
                break;
            case 2:
                cadastrarCategoria();
                break;
            case 3:
                cadastrarTransacao();
                break;
            default:
                System.out.println("Opção inválida!");
        }
    }

    public void cadastrarUsuario() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        System.out.println("Escolha o tipo de conta:");
        System.out.println("1. Corrente");
        System.out.println("2. Poupança");
        int tipoConta = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Informe o saldo inicial: R$ ");
        double saldoInicial = scanner.nextDouble();
        scanner.nextLine();

        TipoContaEnum tipoContaEnum = (tipoConta == 1) ? TipoContaEnum.CORRENTE : TipoContaEnum.POUPANCA;

        Conta conta = new Conta(tipoContaEnum, saldoInicial);

        String sql = "INSERT INTO usuarios (nome, email, senha, tipo_conta, saldo) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.setString(3, senha);
            stmt.setString(4, tipoContaEnum.name());
            stmt.setDouble(5, saldoInicial);

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int idUsuario = generatedKeys.getInt(1);

                    Usuario usuario = new Usuario(idUsuario, nome, email, senha, tipoContaEnum, conta);
                    System.out.println("Usuário cadastrado com sucesso! ID: " + idUsuario);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    private void cadastrarCategoria() {
        System.out.print("Informe seu nome: ");
        String nomeUsuario = scanner.nextLine();

        Usuario usuario = encontrarUsuarioPorNome(nomeUsuario);

        if (usuario != null) {
            System.out.print("Informe o nome da categoria: ");
            String nomeCategoria = scanner.nextLine();

            String sql = "INSERT INTO categorias (nome, id_usuario) VALUES (?, ?)";

            try (Connection conn = ConexaoBD.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, nomeCategoria);
                stmt.setInt(2, usuario.getId());

                int affectedRows = stmt.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("Categoria cadastrada com sucesso!");
                } else {
                    System.out.println("Erro ao cadastrar a categoria.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Usuário não encontrado. Cadastro de categoria cancelado.");
        }
    }

    private Usuario encontrarUsuarioPorNome(String nome) {
        String sql = "SELECT * FROM usuarios WHERE nome = ?";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                int id = rs.getInt("id");
                String nomeUsuario = rs.getString("nome");
                String email = rs.getString("email");
                String senha = rs.getString("senha");
                TipoContaEnum tipoContaEnum = TipoContaEnum.valueOf(rs.getString("tipo_conta"));
                double saldo = rs.getDouble("saldo");

                Conta conta = new Conta(tipoContaEnum, saldo);

                return new Usuario(id, nomeUsuario, email, senha, tipoContaEnum, conta);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public void cadastrarTransacao() {
        System.out.print("Informe seu ID de usuário: ");
        int idUsuario = scanner.nextInt();
        scanner.nextLine();

        Usuario usuario = encontrarUsuarioPorId(idUsuario);
        if (usuario != null) {
            System.out.print("Informe sua senha para confirmar a transação: ");
            String senha = scanner.nextLine();

            if (usuario.getSenha().equals(senha)) {
                System.out.print("ID da categoria: ");
                int idCategoria = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Valor: ");
                double valor = scanner.nextDouble();
                scanner.nextLine();

                BigDecimal valorTransacao = BigDecimal.valueOf(valor);

                System.out.print("Tipo da transação (Receita/Despesa): ");
                String tipo = scanner.nextLine();

                Categoria categoria = encontrarCategoriaPorId(idCategoria);
                if (categoria != null) {

                    String sql = "INSERT INTO transacoes (id_usuario, valor, tipo, id_categoria) VALUES (?, ?, ?, ?)";

                    try (Connection conn = ConexaoBD.getConnection();
                         PreparedStatement stmt = conn.prepareStatement(sql)) {

                        stmt.setInt(1, idUsuario);
                        stmt.setBigDecimal(2, valorTransacao);
                        stmt.setString(3, tipo);
                        stmt.setInt(4, idCategoria);

                        int affectedRows = stmt.executeUpdate();

                        if (affectedRows > 0) {
                            System.out.println("Transação criada com sucesso!");

                            if (tipo.equalsIgnoreCase("Receita")) {
                                realizarOperacao(usuario, valor);
                            } else if (tipo.equalsIgnoreCase("Despesa")) {
                                realizarOperacao(usuario, -valor);
                            }
                        } else {
                            System.out.println("Erro ao cadastrar a transação.");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Categoria não encontrada. Transação não realizada.");
                }
            } else {
                System.out.println("Senha incorreta. Cadastro de transação cancelado.");
            }
        } else {
            System.out.println("Usuário não encontrado. Transação não realizada.");
        }
    }

    private void listar() {
        System.out.println("\nEscolha o que deseja listar:");
        System.out.println("1. Usuários");
        System.out.println("2. Categorias");
        System.out.println("3. Transações");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        switch (escolha) {
            case 1:
                listarUsuarios();
                break;
            case 2:
                listarCategorias();
                break;
            case 3:
                listarTransacoes();
                break;
            default:
                System.out.println("Opção inválida!");
        }
    }

    private void listarUsuarios() {
        String sql = "SELECT * FROM usuarios";

        try (Connection conn = ConexaoBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (!rs.isBeforeFirst()) {
                System.out.println("Nenhum usuário cadastrado.");
                return;
            }

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String email = rs.getString("email");
                String tipoConta = rs.getString("tipo_conta");
                double saldo = rs.getDouble("saldo");

                System.out.printf("ID: %d | Nome: %s | Email: %s | Tipo: %s | Saldo: R$ %.2f%n",
                        id, nome, email, tipoConta, saldo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void listarCategorias() {
        String sql = "SELECT * FROM categorias";

        try (Connection conn = ConexaoBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (!rs.isBeforeFirst()) {
                System.out.println("Nenhuma categoria cadastrada.");
                return;
            }

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                int idUsuario = rs.getInt("id_usuario");

                System.out.printf("ID: %d | Nome: %s | ID Usuário: %d%n", id, nome, idUsuario);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void listarTransacoes() {
        String sql = "SELECT * FROM transacoes";

        try (Connection conn = ConexaoBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (!rs.isBeforeFirst()) {
                System.out.println("Nenhuma transação realizada.");
                return;
            }

            while (rs.next()) {
                int id = rs.getInt("id");
                int idUsuario = rs.getInt("id_usuario");
                BigDecimal valor = rs.getBigDecimal("valor");
                String tipo = rs.getString("tipo");
                int idCategoria = rs.getInt("id_categoria");

                System.out.printf("ID: %d | ID Usuário: %d | Valor: R$ %.2f | Tipo: %s | ID Categoria: %d%n",
                        id, idUsuario, valor, tipo, idCategoria);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void excluir() {
        System.out.println("\nEscolha o que deseja excluir:");
        System.out.println("1. Usuário");
        System.out.println("2. Categoria");
        System.out.println("3. Transação");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        switch (escolha) {
            case 1:
                excluirUsuario();
                break;
            case 2:
                excluirCategoria();
                break;
            case 3:
                excluirTransacao();
                break;
            default:
                System.out.println("Opção inválida!");
        }
    }

    private void excluirUsuario() {
        System.out.print("Informe o ID do usuário que deseja excluir: ");
        int idUsuario = scanner.nextInt();
        scanner.nextLine();

        Usuario usuario = encontrarUsuarioPorId(idUsuario);
        if (usuario != null) {

            excluirTransacoesAssociadas(idUsuario);


            excluirCategoriasAssociadas(idUsuario);

            String sql = "DELETE FROM usuarios WHERE id = ?";

            try (Connection conn = ConexaoBD.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, idUsuario);

                int affectedRows = stmt.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("Usuário excluído com sucesso!");
                } else {
                    System.out.println("Erro ao excluir o usuário.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Usuário não encontrado.");
        }
    }

    private void excluirTransacoesAssociadas(int idUsuario) {
        String sql = "DELETE FROM transacoes WHERE id_usuario = ?";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Transações associadas ao usuário excluídas com sucesso!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void excluirCategoriasAssociadas(int idUsuario) {
        String sql = "DELETE FROM categorias WHERE id_usuario = ?";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Categorias associadas ao usuário excluídas com sucesso!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    private void excluirCategoria() {
        System.out.print("Informe o ID da categoria que deseja excluir: ");
        int idCategoria = scanner.nextInt();
        scanner.nextLine();

        Categoria categoria = encontrarCategoriaPorId(idCategoria);
        if (categoria != null) {
            String sql = "DELETE FROM categorias WHERE id = ?";

            try (Connection conn = ConexaoBD.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, idCategoria);

                int affectedRows = stmt.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("Categoria excluída com sucesso!");
                } else {
                    System.out.println("Erro ao excluir a categoria.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Categoria não encontrada.");
        }
    }

    private void excluirTransacao() {
        System.out.print("Informe o ID da transação que deseja excluir: ");
        int idTransacao = scanner.nextInt();
        scanner.nextLine();

        String sql = "DELETE FROM transacoes WHERE id = ?";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idTransacao);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Transação excluída com sucesso!");
            } else {
                System.out.println("Erro ao excluir a transação.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void atualizar() {
        System.out.println("\nEscolha o que deseja atualizar:");
        System.out.println("1. Usuário");
        System.out.println("2. Categoria");
        System.out.println("3. Transação");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        switch (escolha) {
            case 1:
                atualizarUsuario();
                break;
            case 2:
                atualizarCategoria();
                break;
            case 3:
                atualizarTransacao();
                break;
            default:
                System.out.println("Opção inválida!");
        }
    }

    private void atualizarUsuario() {
        System.out.print("Informe o ID do usuário a ser atualizado: ");
        int idUsuario = scanner.nextInt();
        scanner.nextLine();

        Usuario usuario = encontrarUsuarioPorId(idUsuario);
        if (usuario != null) {
            System.out.print("Novo nome (atual: " + usuario.getNome() + "): ");
            String nome = scanner.nextLine();
            System.out.print("Novo email (atual: " + usuario.getEmail() + "): ");
            String email = scanner.nextLine();
            System.out.print("Nova senha (atual: " + usuario.getSenha() + "): ");
            String senha = scanner.nextLine();

            System.out.println("Escolha o novo tipo de conta (1. Corrente / 2. Poupança): ");
            int tipoConta = scanner.nextInt();
            scanner.nextLine();

            TipoContaEnum tipoContaEnum = (tipoConta == 1) ? TipoContaEnum.CORRENTE : TipoContaEnum.POUPANCA;

            String sql = "UPDATE usuarios SET nome = ?, email = ?, senha = ?, tipo_conta = ? WHERE id = ?";

            try (Connection conn = ConexaoBD.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, nome);
                stmt.setString(2, email);
                stmt.setString(3, senha);
                stmt.setString(4, tipoContaEnum.name());
                stmt.setInt(5, idUsuario);

                int affectedRows = stmt.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Usuário atualizado com sucesso!");
                } else {
                    System.out.println("Erro ao atualizar usuário.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Usuário não encontrado.");
        }
    }

    private void atualizarCategoria() {
        System.out.print("Informe o ID da categoria a ser atualizada: ");
        int idCategoria = scanner.nextInt();
        scanner.nextLine();

        Categoria categoria = encontrarCategoriaPorId(idCategoria);
        if (categoria != null) {
            System.out.print("Novo nome da categoria (atual: " + categoria.getNome() + "): ");
            String nome = scanner.nextLine();

            String sql = "UPDATE categorias SET nome = ? WHERE id = ?";

            try (Connection conn = ConexaoBD.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, nome);
                stmt.setInt(2, idCategoria);

                int affectedRows = stmt.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Categoria atualizada com sucesso!");
                } else {
                    System.out.println("Erro ao atualizar categoria.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Categoria não encontrada.");
        }
    }


    private void atualizarTransacao() {
        System.out.print("Informe o ID da transação a ser atualizada: ");
        int idTransacao = scanner.nextInt();
        scanner.nextLine();

        String sql = "SELECT * FROM transacoes WHERE id = ?";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idTransacao);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                BigDecimal valorAtual = rs.getBigDecimal("valor");
                String tipoAtual = rs.getString("tipo");
                int idCategoria = rs.getInt("id_categoria");

                System.out.println("Valor atual: R$ " + valorAtual);
                System.out.println("Tipo atual: " + tipoAtual);

                System.out.print("Novo valor (atual: R$ " + valorAtual + "): ");
                double novoValor = scanner.nextDouble();
                scanner.nextLine();

                System.out.print("Novo tipo de transação (Receita/Despesa) (atual: " + tipoAtual + "): ");
                String novoTipo = scanner.nextLine();

                String updateSql = "UPDATE transacoes SET valor = ?, tipo = ? WHERE id = ?";

                try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                    updateStmt.setBigDecimal(1, BigDecimal.valueOf(novoValor));
                    updateStmt.setString(2, novoTipo);
                    updateStmt.setInt(3, idTransacao);

                    int affectedRows = updateStmt.executeUpdate();
                    if (affectedRows > 0) {
                        System.out.println("Transação atualizada com sucesso!");
                    } else {
                        System.out.println("Erro ao atualizar transação.");
                    }
                }
            } else {
                System.out.println("Transação não encontrada.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





    private Usuario encontrarUsuarioPorId(int id) {
        String sql = "SELECT * FROM usuarios WHERE id = ?";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String nome = rs.getString("nome");
                String email = rs.getString("email");
                String senha = rs.getString("senha");
                TipoContaEnum tipoContaEnum = TipoContaEnum.valueOf(rs.getString("tipo_conta"));
                double saldo = rs.getDouble("saldo");

                Conta conta = new Conta(tipoContaEnum, saldo);

                return new Usuario(id, nome, email, senha, tipoContaEnum, conta);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    private Categoria encontrarCategoriaPorId(int id) {
        String sql = "SELECT * FROM categorias WHERE id = ?";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Categoria(
                        rs.getInt("id"),
                        rs.getString("nome")
                );
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
