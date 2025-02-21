package Conta;

// Conta Corrente usando a estratégia específica
public class ContaCorrente extends Conta {
    public ContaCorrente(double saldoInicial) {
        super(new StrategyContaCorrente(saldoInicial));
    }
}
