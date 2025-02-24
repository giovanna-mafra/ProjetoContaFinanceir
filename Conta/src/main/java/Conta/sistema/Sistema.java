package Conta.sistema;
import Conta.contas.Conta;
import Conta.contas.ContaCorrente;
import Conta.contas.ContaPoupanca;
import Conta.usuarios.Usuario;
import Conta.categorias.Categoria;
import Conta.transacoes.Transacao;

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

    private void cadastrarUsuario() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Usuario usuario = new Usuario(nome, email, senha);

        System.out.println("Escolha o tipo de conta:");
        System.out.println("1. Conta Corrente");
        System.out.println("2. Conta Poupança");
        int tipoConta = scanner.nextInt();
        scanner.nextLine();

        if (tipoConta == 1) {
            System.out.print("Informe o saldo inicial da Conta Corrente: ");
            double saldoInicial = scanner.nextDouble();
            scanner.nextLine();
            ContaCorrente contaCorrente = new ContaCorrente(saldoInicial);
            usuario.setConta(contaCorrente);
        } else if (tipoConta == 2) {

            System.out.print("Informe o saldo inicial da Conta Poupança: ");
            double saldoInicial = scanner.nextDouble();
            scanner.nextLine();
            ContaPoupanca contaPoupanca = new ContaPoupanca(saldoInicial);
            usuario.setConta(contaPoupanca);
        } else {
            System.out.println("Tipo de conta inválido!");
            return;
        }

        usuarios.add(usuario);
        System.out.println("Usuário cadastrado com sucesso!");
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

    private void cadastrarTransacao() {
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
                System.out.print("Tipo da transação (Receita/Despesa): ");
                String tipo = scanner.nextLine();

                Categoria categoria = encontrarCategoriaPorId(idCategoria);
                if (categoria != null) {
                    Transacao transacao = new Transacao(idUsuario, valor, tipo, usuario, categoria);
                    transacoes.add(transacao);
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
                System.out.println(usuario);
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


    private void excluirTransacoesDoUsuario(Usuario usuario) {
        List<Transacao> transacoesToRemove = new ArrayList<>();

        for (Transacao transacao : transacoes) {
            if (transacao.getUsuario().getId() == usuario.getId()) {
                transacoesToRemove.add(transacao);
            }
        }

        for (Transacao transacao : transacoesToRemove) {
            transacoes.remove(transacao);
        }
    }

    private void excluirCategoriasDoUsuario(Usuario usuario) {
        List<Categoria> categoriasToRemove = new ArrayList<>();

        for (Categoria categoria : categorias) {
            if (categoria.getId() == usuario.getId()) {
                categoriasToRemove.add(categoria);
            }
        }

        for (Categoria categoria : categoriasToRemove) {
            categorias.remove(categoria);
        }
    }
    private void excluirCategoria() {
        System.out.print("Informe o ID da categoria a ser excluída: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Categoria com ID " + id + " excluída com sucesso!");
    }

    private void excluirTransacao() {
        System.out.print("Informe o ID da transação a ser excluída: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Transação com ID " + id + " excluída com sucesso!");
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

        System.out.println("Usuário com ID " + id + " atualizado com sucesso!");
    }

    private void atualizarCategoria() {
        System.out.print("Informe o ID da categoria a ser atualizada: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Informe o novo nome da categoria: ");
        String nome = scanner.nextLine();

        System.out.println("Categoria com ID " + id + " atualizada com sucesso!");
    }

    private void atualizarTransacao() {
        System.out.print("Informe o ID da transação a ser atualizada: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Informe o novo valor da transação: ");
        double valor = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Transação com ID " + id + " atualizada com sucesso!");
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
}