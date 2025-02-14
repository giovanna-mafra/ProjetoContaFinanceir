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
                case 5: excluir(); break;
                case 6: System.out.println("Saindo..."); return;
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

        usuarios.add(new Usuario(idUsuario++, nome, email, senha, conta));
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
            Transacao transacao = new Transacao(idTransacao++, valor, tipo, usuario, categoria);
            transacoes.add(transacao);
            atualizarSaldoUsuario(usuario, transacao);
            System.out.println("Transação criada com sucesso!");
            } else {
            System.out.println("Categoria inválida.");
        }
    }

    private void atualizarSaldoUsuario(Usuario usuario, Transacao transacao) {
        if (transacao.getTipo().equalsIgnoreCase("Receita")) {
            usuario.getConta().adicionarSaldo(transacao.getValor());
        }
        else if (transacao.getTipo().equalsIgnoreCase("Despesa")){
             {
                usuario.getConta().debitarSaldo(transacao.getValor());
            }
        }
    }

    private void listarTransacoes() {
        double saldoAtual = 0.0;
        if (transacoes.isEmpty()) {
            System.out.println("Nenhuma transação cadastrada.");
        } else {
            for (Transacao transacao : transacoes) {
                System.out.println(transacao);
                saldoAtual = atualizarSaldoLista(saldoAtual, transacao);
                System.out.println("Saldo atual: R$ " + saldoAtual);
            }
        }

    }

    private double atualizarSaldoLista(double saldoAtual, Transacao transacao) {
        if (transacao.getTipo().equalsIgnoreCase("Receita")) {
            saldoAtual += transacao.getValor();
        }
        else if (transacao.getTipo().equalsIgnoreCase("Despesa")) {
            saldoAtual -= transacao.getValor();
        }
        return saldoAtual;
    }

    private void excluir() {
        System.out.println("\n Selecione a opção para excluir: " );
        System.out.println("1 - Excluir Usuário");
        System.out.println("2 - Excluir Categoria");
        System.out.println("3 - Excluir Transação");

        int opcaoExcluir = scanner.nextInt();
        scanner.nextLine();

        switch (opcaoExcluir) {
            case 1: excluirUsuario(); break;
            case 2: excluirCategoria(); break;
            case 3: excluirTransacao(); break;
            default:
                System.out.println("Opção inválida!");
        }
    }

    private void excluirUsuario() {
        System.out.println("Digite o ID do usuário a ser excluído: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Usuario usuario = null;

        for(Usuario u : usuarios) {
            if(u.getId() == id) {
                usuario = u;
                break;
            }
        }

        if (usuario != null) {
            System.out.println("Usuário encontrado: " + usuario);
            System.out.println("Deseja excluir esse usuário? (S/N)");
            String confirm = scanner.nextLine();

            if(confirm.equalsIgnoreCase("S")) {
                usuarios.remove(usuario);
                System.out.println("Usuário excluído com sucesso!");
            } else{
                System.out.println("Operação cancelada");
            }

        } else{
            System.out.println("Usuário não encontrado!");
        }
    }

    private void excluirCategoria() {
        System.out.println("Digite o ID da categoria a ser excluída: " );
        int id = scanner.nextInt();
        scanner.nextLine();

        Categoria categoria = null;

        for(Categoria c : categorias) {
            if(c.getId() == id) {
                categoria = c;
                break;
            }
        }

        if (categoria != null) {
            System.out.println("Categoria encontrada: " + categoria);
            System.out.println("Deseja excluir essa categoria? (S/N)");
            String confirm = scanner.nextLine();

            if(confirm.equalsIgnoreCase("S")) {
                categorias.remove(categoria);
                System.out.println("Categoria excluída com sucesso!");
            } else{
                System.out.println("Operação cancelada");
            }

        } else{
            System.out.println("Categoria não encontrada!");
        }
    }

    private void excluirTransacao() {
        System.out.println("Digite o ID da transação a ser excluída: " );
        int id = scanner.nextInt();
        scanner.nextLine();

        Transacao transacao = null;

        for(Transacao t : transacoes) {
            if(t.getId() == id) {
                transacao = t;
                break;
            }
        }

        if (transacao != null) {
            System.out.println("Transação encontrada: " + transacao);
            System.out.println("Deseja excluir essa transação? (S/N)");
            String confirm = scanner.nextLine();

            if(confirm.equalsIgnoreCase("S")) {
                transacoes.remove(transacao);
                System.out.println("Transação excluída com sucesso!");
            } else{
                System.out.println("Operação cancelada");
            }

        } else{
            System.out.println("Transação não encontrada!");
        }
    }

}