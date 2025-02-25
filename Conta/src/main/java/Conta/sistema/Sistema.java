package Conta.sistema;

import Conta.contas.Conta;
import Conta.usuarios.Usuario;
import Conta.categorias.Categoria;
import Conta.transacoes.Transacao;
import Conta.enums.TipoContaEnum;
import Conta.strategy.StrategyConta;

import java.math.BigDecimal;


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
        StrategyConta estrategia = usuario.getTipoConta().criarEstrategia();
        estrategia.alterarSaldo(usuario.getConta(), valor);
        System.out.println("Saldo após operação para " + usuario.getNome() + ": R$ " + usuario.getConta().getSaldo());
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

        Usuario usuario = new Usuario(nome, email, senha, tipoContaEnum);

        Conta conta = new Conta(saldoInicial, tipoContaEnum);
        usuario.setConta(conta);

        usuarios.add(usuario);
        System.out.println("Usuário criado: " + usuario);
    }




    private void cadastrarCategoria() {
        System.out.print("Informe seu ID de usuário: ");
        int idUsuario = scanner.nextInt();
        scanner.nextLine();

        Usuario usuario = encontrarUsuarioPorId(idUsuario);
        if (usuario != null) {
            System.out.print("Informe sua senha para confirmar o cadastro: ");
            String senha = scanner.nextLine();

            if (usuario.getSenha().equals(senha)) {
                System.out.print("Nome da categoria: ");
                String nomeCategoria = scanner.nextLine();
                int idCategoria = new GeradorId().gerarId();
                Categoria categoria = new Categoria(idCategoria, nomeCategoria);
                categorias.add(categoria);
                System.out.println("Categoria cadastrada com sucesso!");
            } else {
                System.out.println("Senha incorreta. Cadastro de categoria cancelado.");
            }
        } else {
            System.out.println("Usuário não encontrado. Cadastro de categoria cancelado.");
        }
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
                    Transacao transacao = new Transacao(idUsuario, valorTransacao, tipo, usuario, categoria);
                    transacoes.add(transacao);

                    if (tipo.equalsIgnoreCase("Receita")) {
                        realizarOperacao(usuario, valor);
                    } else if (tipo.equalsIgnoreCase("Despesa")) {
                        realizarOperacao(usuario, -valor);
                    }

                    System.out.println("Transação criada com sucesso!");
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
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
        } else {
            System.out.println("Listando usuários:");
            for (Usuario usuario : usuarios) {
                System.out.println(usuario + " - Saldo: R$ " + usuario.getConta().getSaldo());
            }
        }
    }


    private void listarCategorias() {
        if (categorias.isEmpty()) {
            System.out.println("Nenhuma categoria cadastrada.");
        } else {
            System.out.println("Listando categorias:");
            for (Categoria categoria : categorias) {
                System.out.println(categoria);
            }
        }
    }

    private void listarTransacoes() {
        if (transacoes.isEmpty()) {
            System.out.println("Nenhuma transação realizada.");
        } else {
            System.out.println("Listando transações:");
            for (Transacao transacao : transacoes) {
                System.out.println(transacao);
            }
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
        System.out.print("Informe o ID do usuário a ser excluído: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Usuario usuario = encontrarUsuarioPorId(id);

        if (usuario != null) {
            excluirTransacoesDoUsuario(usuario);
            excluirCategoriasDoUsuario(usuario);

            usuarios.remove(usuario);
            System.out.println("Usuário com ID " + id + " e suas transações e categorias excluídos com sucesso!");
        } else {
            System.out.println("Usuário não encontrado!");
        }
    }

    private void excluirCategoriasDoUsuario(Usuario usuario) {
        categorias.removeIf(categoria -> categoria.getId() == usuario.getId());
    }

    private void excluirTransacoesDoUsuario(Usuario usuario) {
        transacoes.removeIf(transacao -> transacao.getUsuario().getId() == usuario.getId());
    }

    private void excluirCategoria() {
        System.out.print("Informe o ID da categoria a ser excluída: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Categoria categoria = encontrarCategoriaPorId(id);
        if (categoria != null) {
            categorias.remove(categoria);
            System.out.println("Categoria com ID " + id + " excluída com sucesso!");
        } else {
            System.out.println("Categoria não encontrada!");
        }
    }

    private void excluirTransacao() {
        System.out.print("Informe o ID da transação a ser excluída: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Transacao transacao = encontrarTransacaoPorId(id);
        if (transacao != null) {
            transacoes.remove(transacao);
            System.out.println("Transação com ID " + id + " excluída com sucesso!");
        } else {
            System.out.println("Transação não encontrada!");
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
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Informe o novo nome do usuário: ");
        String nome = scanner.nextLine();

        Usuario usuario = encontrarUsuarioPorId(id);
        if (usuario != null) {
            usuario.setNome(nome);
            System.out.println("Usuário com ID " + id + " atualizado com sucesso!");
        } else {
            System.out.println("Usuário não encontrado!");
        }
    }

    private void atualizarCategoria() {
        System.out.print("Informe o ID da categoria a ser atualizada: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Informe o novo nome da categoria: ");
        String nome = scanner.nextLine();

        Categoria categoria = encontrarCategoriaPorId(id);
        if (categoria != null) {
            categoria.setNome(nome);
            System.out.println("Categoria com ID " + id + " atualizada com sucesso!");
        } else {
            System.out.println("Categoria não encontrada!");
        }
    }

    private void atualizarTransacao() {
        System.out.print("Informe o ID da transação a ser atualizada: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Informe o novo valor da transação: ");
        double valor = scanner.nextDouble();
        scanner.nextLine();

        BigDecimal valorBigDecimal = BigDecimal.valueOf(valor);


        System.out.println("Transação com ID " + id + " atualizada com sucesso! Novo valor: " + valorBigDecimal);
    }

    private Usuario encontrarUsuarioPorId(int id) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId() == id) {
                return usuario;
            }
        }
        return null;
    }

    private Categoria encontrarCategoriaPorId(int id) {
        for (Categoria categoria : categorias) {
            if (categoria.getId() == id) {
                return categoria;
            }
        }
        return null;
    }

    private Transacao encontrarTransacaoPorId(int id) {
        for (Transacao transacao : transacoes) {
            if (transacao.getId() == id) {
                return transacao;
            }
        }
        return null;
    }
}
