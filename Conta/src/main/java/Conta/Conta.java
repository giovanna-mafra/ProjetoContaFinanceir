package Conta;

public class Conta {
    protected StrategyConta strategyConta;

    public Conta(StrategyConta estrategiaConta) {
        if (estrategiaConta == null) {
            System.out.println("Erro: A estratégia da conta não foi definida corretamente.");
        } else {
            this.strategyConta = estrategiaConta;
        }
    }

    public void adicionarSaldo(double valor) {
        if (strategyConta != null) {
            strategyConta.adicionarSaldo(valor);
        } else {
            System.out.println("Erro: A estratégia de conta não foi inicializada corretamente.");
        }
    }

    public void debitarSaldo(double valor) {
        if (strategyConta != null) {
            strategyConta.debitarSaldo(valor);
        } else {
            System.out.println("Erro: A estratégia de conta não foi inicializada corretamente.");
        }
    }

    public double getSaldo() {
        if (strategyConta != null) {
            return strategyConta.getSaldo();
        } else {
            System.out.println("Erro: A estratégia de conta não foi inicializada corretamente.");
            return 0.0;
        }
    }
}
