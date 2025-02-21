package Conta;

public class ContaPoupanca extends Conta {
    public ContaPoupanca(double saldoInicial) {
        super(new StrategyContaPoupanca(saldoInicial));
    }
}
