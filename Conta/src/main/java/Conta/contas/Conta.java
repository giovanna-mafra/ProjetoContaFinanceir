package Conta.contas;

import Conta.enums.TipoContaEnum;
import Conta.strategy.StrategyConta;
import Conta.strategy.impl.StrategyCorrente;
import Conta.strategy.impl.StrategyPoupanca;

public class Conta {
    private double saldo;
    private StrategyConta estrategia;

    public Conta(double saldoInicial, TipoContaEnum tipoContaEnum) {
        this.saldo = saldoInicial;

        switch (tipoContaEnum) {
            case CORRENTE:
                this.estrategia = new StrategyCorrente();
                break;
            case POUPANCA:
                this.estrategia = new StrategyPoupanca();
                break;
            default:
                throw new IllegalArgumentException("Tipo de conta não suportado");
        }
    }

    public double getSaldo() {
        return saldo;
    }

    public void adicionarSaldo(double valor) {
        saldo += valor;
    }

}
