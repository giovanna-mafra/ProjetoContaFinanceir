package Conta.strategy;

public class StrategyContaCorrente implements StrategyConta {
    private double saldo;

    public StrategyContaCorrente(double saldoInicial) {
        this.saldo = saldoInicial;
    }

    @Override
    public void adicionarSaldo(double valor) {
        saldo += valor;
    }

    @Override
    public void debitarSaldo(double valor) {
        saldo -= valor;
    }

    @Override
    public double getSaldo() {
        return saldo;
    }
}
