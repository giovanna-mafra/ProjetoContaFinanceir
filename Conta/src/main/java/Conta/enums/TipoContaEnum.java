package Conta.enums;

import Conta.strategy.StrategyConta;
import Conta.strategy.impl.StrategyCorrente;
import Conta.strategy.impl.StrategyPoupanca;

public enum TipoContaEnum {
    CORRENTE {
        @Override
        public StrategyConta criarEstrategia() {
            return new StrategyCorrente();
        }
    },
    POUPANCA {
        @Override
        public StrategyConta criarEstrategia() {
            return new StrategyPoupanca();
        }
    };

    public abstract StrategyConta criarEstrategia();
}
