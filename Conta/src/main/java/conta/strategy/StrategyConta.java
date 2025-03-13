package conta.strategy;

import conta.contas.Conta;

public interface StrategyConta {
    void alterarSaldo(Conta conta, double valor);
}
