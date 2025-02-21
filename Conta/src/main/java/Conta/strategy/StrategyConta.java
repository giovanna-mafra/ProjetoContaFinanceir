package Conta.strategy;

public interface StrategyConta {
    void adicionarSaldo(double valor);
    void debitarSaldo(double valor);
    double getSaldo();
}
