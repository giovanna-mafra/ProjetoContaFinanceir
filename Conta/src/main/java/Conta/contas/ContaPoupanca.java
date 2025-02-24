package Conta.contas;

public class ContaPoupanca extends Conta {
    private static final double RENDIMENTO = 0.02;

    public ContaPoupanca(double saldoInicial) {
        super(saldoInicial);
    }

    @Override
    public String getTipoConta() {
        return "Conta Poupança";
    }

    @Override
    public void adicionarSaldo(double valor) {
        super.adicionarSaldo(valor + (valor * RENDIMENTO));
    }

    @Override
    public void debitarSaldo(double valor) {
        if (getSaldo() >= valor) {
            super.debitarSaldo(valor);
        } else {
            System.out.println("Saldo na Conta Poupança insuficiente!");
        }
    }
}
