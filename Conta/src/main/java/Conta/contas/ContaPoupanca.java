package Conta.contas;

import Conta.strategy.StrategyContaPoupanca;

public class ContaPoupanca extends Conta {
    public ContaPoupanca(double saldoInicial) {
        super(new StrategyContaPoupanca(saldoInicial));
    }
}
