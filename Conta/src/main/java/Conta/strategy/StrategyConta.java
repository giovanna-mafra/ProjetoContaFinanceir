package Conta.strategy;

import Conta.contas.Conta;


public interface StrategyConta {
    void alterarSaldo(Conta conta, double valor);
}
