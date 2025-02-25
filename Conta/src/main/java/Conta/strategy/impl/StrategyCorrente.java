package Conta.strategy.impl;

import Conta.contas.Conta;
import Conta.strategy.StrategyConta;

public class StrategyCorrente implements StrategyConta {
    @Override
    public void alterarSaldo(Conta conta, double valor) {
        conta.adicionarSaldo(valor);
    }
}
