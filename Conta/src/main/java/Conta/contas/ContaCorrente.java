package Conta.contas;

public class ContaCorrente extends Conta {

    public ContaCorrente(double saldoInicial) {
        super(saldoInicial);
    }

    @Override
    public String getTipoConta() {
        return "Conta Corrente";
    }
}
