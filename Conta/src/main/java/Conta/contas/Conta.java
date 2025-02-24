package Conta.contas;

public class Conta {
    private double saldo;

    public Conta(double saldoInicial) {
        this.saldo = saldoInicial;
    }

    public String getTipoConta() {
        return "Tipo de Conta Desconhecido";
    }

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
