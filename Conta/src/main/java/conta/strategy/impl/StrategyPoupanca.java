package conta.strategy.impl;

import conta.contas.Conta;
import conta.strategy.StrategyConta;

public class StrategyPoupanca implements StrategyConta {
    private static final double RENDIMENTO = 0.02;

    @Override
    public void alterarSaldo(Conta conta, double valor) {
        conta.adicionarSaldo(valor + (valor * RENDIMENTO));  // Altera o saldo com rendimento
    }
}
