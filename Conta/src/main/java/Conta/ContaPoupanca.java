package Conta;

public class ContaPoupanca extends Conta{
    private static final double RENDIMENTO = 0.02;

    public ContaPoupanca ( double saldoInicial) {
        super(saldoInicial);
    }

    @Override
    public void adicionarSaldo(double valor) {
        saldo += valor + (valor * RENDIMENTO);
    }

    @Override
    public void debitarSaldo(double valor) {
     if(saldo>= valor) {
         saldo -= valor;
     } else{
         System.out.println("Saldo insuficiente na conta poupança!");
     }
    }

}
