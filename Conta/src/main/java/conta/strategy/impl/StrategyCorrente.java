package conta.strategy.impl;

import conta.contas.Conta;
import conta.strategy.StrategyConta;

public class StrategyCorrente implements StrategyConta {
    @Override
    public void alterarSaldo(Conta conta, double valor) {
        if (conta != null) {
            conta.adicionarSaldo(valor);
        } else {
            System.out.println("Erro: Conta não foi inicializada.");
        }
    }
}
