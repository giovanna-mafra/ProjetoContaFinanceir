package Conta;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Sistema {
    private List<Usuario> usuarios = new ArrayList<>();
    private List<Categoria> categorias = new ArrayList<>();
    private List<Transacao> transacoes = new ArrayList<>();
    private int idUsuario = 1, idCategoria = 1, idTransacao = 1;
    private Scanner scanner = new Scanner(System.in);

    public void menu() {
        while (true) {
            System.out.println("\n1. Criar Usuário\n2. Criar Categoria\n3. Criar Transação\n4. Listar Transações\n5. Sair");
            int opcao = scanner.nextInt();
            scanner.nextLine();
            switch (opcao) {
                case 1: criarUsuario(); break;
                case 2: criarCategoria(); break;
                case 3: criarTransacao(); break;
                case 4: listarTransacoes(); break;
                case 5: System.out.println("Saindo..."); return;
                default: System.out.println("Opção inválida.");
            }
        }
    }

    private void criarUsuario() {
        System.out.print("Nome do usuário: ");
        String nome = scanner.nextLine();
        System.out.print("Email do usuário: ");
        String email = scanner.nextLine();
        System.out.print("Defina uma senha: ");
        String senha = scanner.nextLine();

        System.out.println("Escolha o tipo de conta: \n 1 - Conta Corrente \n 2 - Conta Poupança ");

        int tipoConta = scanner.nextInt();

        Conta conta;

        if (tipoConta == 1) {
            conta = new ContaCorrente(0);
        } else {
            conta = new ContaPoupanca(0);
        }

        usuarios.add(new Usuario(idUsuario++, nome, email, senha));
        System.out.println("Usuário criado com sucesso!");

    }

    private void criarCategoria() {
        System.out.print("Nome da categoria: ");
        String nome = scanner.nextLine();
        categorias.add(new Categoria(idCategoria++, nome));
        System.out.println("Categoria criada com sucesso!");
    }

    private void criarTransacao() {
        if (usuarios.isEmpty() || categorias.isEmpty()) {
            System.out.println("É necessário ter pelo menos um usuário e uma categoria.");
            return;
        }

        System.out.println("Usuários disponíveis:");
        for (Usuario u : usuarios) {
            System.out.println(u);
        }
        System.out.print("ID do usuário: ");
        int idUsuario = scanner.nextInt();
        scanner.nextLine();

        Usuario usuario = usuarios.stream().filter(u -> u.getId() == idUsuario).findFirst().orElse(null);

        if (usuario == null) {
            System.out.println("Usuário não encontrado.");
            return;
        }

        System.out.print("Digite a senha do usuário: ");
        String senhaDigitada = scanner.nextLine();

        if (!usuario.verificarSenha(senhaDigitada)) {
            System.out.println("Senha incorreta! Operação cancelada.");
            return;
        }

        System.out.print("Valor da transação: ");
        double valor = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Tipo (Receita/Despesa): ");
        String tipo = scanner.nextLine();

        System.out.println("Categorias disponíveis:");
        for (Categoria c : categorias) {
            System.out.println(c);
        }
        System.out.print("ID da categoria: ");
        int idCategoria = scanner.nextInt();
        scanner.nextLine();

        Categoria categoria = categorias.stream().filter(c -> c.getId() == idCategoria).findFirst().orElse(null);

        if (categoria != null) {
            transacoes.add(new Transacao(idTransacao++, valor, tipo, usuario, categoria));
            System.out.println("Transação criada com sucesso!");
        } else {
            System.out.println("Categoria inválida.");
        }
    }

    private void listarTransacoes() {
        if (transacoes.isEmpty()) {
            System.out.println("Nenhuma transação cadastrada.");
        } else {
            transacoes.forEach(System.out::println);
        }

    }
}