package conta.enums;

import conta.strategy.StrategyConta;
import conta.strategy.impl.StrategyCorrente;
import conta.strategy.impl.StrategyPoupanca;

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
