package Conta;

public class ContaCorrente extends Conta {

    public ContaCorrente(double saldoInicial) {
        super(saldoInicial);
    }

    @Override
    public void adicionarSaldo(double valor) {
        saldo += valor;
    }

    @Override
    public void debitarSaldo(double valor) {
        if(saldo == 0) {
            System.out.println("Saldo da ContaCorrente insuficiente");
        }
        else {
            saldo += valor;
        }
    }


}
