package Conta.contas;

public abstract class Conta {
    private double saldo;

    public Conta(double saldoInicial) {
        this.saldo = saldoInicial;
    }


    public abstract String getTipoConta();

    public void adicionarSaldo(double valor) {
        saldo += valor;
    }

    public void debitarSaldo(double valor) {
        saldo -= valor;
    }

    public double getSaldo() {
        return saldo;
    }
}
