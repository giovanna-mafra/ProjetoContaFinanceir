package Conta.contas;

import Conta.strategy.StrategyContaCorrente;

public class ContaCorrente extends Conta {
    public ContaCorrente(double saldoInicial) {
        super(new StrategyContaCorrente(saldoInicial));
    }
}
