package Conta.sistema;

import Conta.usuarios.Usuario;

import Conta.categorias.Categoria;
import Conta.contas.Conta;
import Conta.transacoes.Transacao;
import Conta.strategy.StrategyContaCorrente;
import Conta.strategy.StrategyContaPoupanca;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Sistema {
    private List<Usuario> usuarios;
    private List<Categoria> categorias;
    private List<Transacao> transacoes;
    private Scanner scanner;

    public Sistema() {
        this.usuarios = new ArrayList<>();
        this.categorias = new ArrayList<>();
        this.transacoes = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public void menu() {
        int opcao;
        do {
            System.out.println("\n=== MENU ===");
            System.out.println("1. Cadastrar Usuário");
            System.out.println("2. Cadastrar Categoria");
            System.out.println("3. Criar Transação");
            System.out.println("4. Listar Transações");
            System.out.println("5. Listar Categorias");
            System.out.println("6. Listar Usuários");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarUsuario();
                    break;
                case 2:
                    cadastrarCategoria();
                    break;
                case 3:
                    criarTransacao();
                    break;
                case 4:
                    listarTransacoes();
                    break;
                case 5:
                    listarCategorias();
                    break;
                case 6:
                    listarUsuarios();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void cadastrarUsuario() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();


        System.out.println("Escolha o tipo de conta:");
        System.out.println("1. Conta Corrente");
        System.out.println("2. Conta Poupança");
        System.out.print("Escolha uma opção: ");
        int tipoConta = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Saldo inicial da conta: ");
        double saldoInicial = scanner.nextDouble();
        scanner.nextLine();

        Conta conta = null;
        if (tipoConta == 1) {
            conta = new Conta(new StrategyContaCorrente(saldoInicial));
        } else if (tipoConta == 2) {
            conta = new Conta(new StrategyContaPoupanca(saldoInicial));
        } else {
            System.out.println("Opção inválida! Conta Corrente será selecionada por padrão.");
            conta = new Conta(new StrategyContaCorrente(saldoInicial));
        }

        Usuario usuario = new Usuario(usuarios.size() + 1, nome, email, senha, conta);
        usuarios.add(usuario);
        System.out.println("Usuário cadastrado com sucesso!");
    }





    private void cadastrarCategoria() {
        System.out.print("Nome da categoria: ");
        String nomeCategoria = scanner.nextLine();
        Categoria categoria = new Categoria(categorias.size() + 1, nomeCategoria);
        categorias.add(categoria);
        System.out.println("Categoria cadastrada com sucesso!");
    }

    private void criarTransacao() {
        if (usuarios.isEmpty() || categorias.isEmpty()) {
            System.out.println("Cadastre pelo menos um usuário e uma categoria antes de criar uma transação.");
            return;
        }

        System.out.print("ID do usuário: ");
        int idUsuario = scanner.nextInt();
        scanner.nextLine();

        Usuario usuario = null;
        for (Usuario u : usuarios) {
            if (u.getId() == idUsuario) {
                usuario = u;
                break;
            }
        }

        if (usuario == null) {
            System.out.println("Usuário não encontrado.");
            return;
        }


        System.out.print("ID da categoria: ");
        int idCategoria = scanner.nextInt();
        scanner.nextLine();

        Categoria categoria = null;
        for (Categoria c : categorias) {
            if (c.getId() == idCategoria) {
                categoria = c;
                break;
            }
        }

        if (categoria == null) {
            System.out.println("Categoria não encontrada.");
            return;
        }

        System.out.print("Valor da transação: ");
        double valor = -1;
        while (valor < 0) {
            if (scanner.hasNextDouble()) {
                valor = scanner.nextDouble();
                if (valor <= 0) {
                    System.out.println("Por favor, insira um valor positivo.");
                }
            } else {
                System.out.println("Valor inválido. Insira um número.");
                scanner.nextLine();
            }
        }


        System.out.print("Tipo da transação (Receita/Despesa): ");
        String tipo = scanner.nextLine();

        if (!tipo.equalsIgnoreCase("Receita") && !tipo.equalsIgnoreCase("Despesa")) {
            System.out.println("Tipo de transação inválido.");
            return;
        }

        Transacao transacao = new Transacao(transacoes.size() + 1, valor, tipo, usuario, categoria);
        usuario.adicionarTransacao(transacao);
        transacoes.add(transacao);

        System.out.println("Transação criada com sucesso!");
    }

    public void listarTransacoes() {
        if (transacoes.isEmpty()) {
            System.out.println("Nenhuma transação cadastrada.");
            return;
        }

        System.out.println("\n=== LISTA DE TRANSAÇÕES ===");
        for (Transacao transacao : transacoes) {
            System.out.println(transacao);
        }
    }

    public void listarCategorias() {
        if (categorias.isEmpty()) {
            System.out.println("Nenhuma categoria cadastrada.");
            return;
        }

        System.out.println("\n=== LISTA DE CATEGORIAS ===");
        for (Categoria categoria : categorias) {
            System.out.println(categoria);
        }
    }

    public void listarUsuarios() {
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
            return;
        }

        System.out.println("\n=== LISTA DE USUÁRIOS ===");
        for (Usuario usuario : usuarios) {
            System.out.println(usuario);
        }
    }
}
