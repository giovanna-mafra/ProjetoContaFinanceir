package Conta.contas;
import Conta.strategy.StrategyConta;

public class Conta {
    private StrategyConta strategyConta;

    public Conta(StrategyConta strategyConta) {
        this.strategyConta = strategyConta;

    }

    public void adicionarSaldo(double valor) {
        strategyConta.adicionarSaldo(valor);
    }

    public void debitarSaldo(double valor) {
       strategyConta.debitarSaldo(valor);
    }

    public double getSaldo() {
        return strategyConta.getSaldo();
    }

    public StrategyConta getStrategyConta() {
        return strategyConta;
    }
}
