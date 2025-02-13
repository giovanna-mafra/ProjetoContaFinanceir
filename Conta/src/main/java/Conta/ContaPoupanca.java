package Conta;

public class ContaPoupanca implements Conta {
    private double saldo;
    private static final double RENDIMENTO = 0.02;

    public ContaPoupanca (double saldoInicial) {
        this.saldo = saldoInicial;
    }

    @Override
    public double getSaldo() {
        return saldo;
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
         System.out.println("Saldo na Conta Poupança insuficiente!");
     }
    }

    @Override
    public String toString() {
        return "ContaPoupança com saldo de R$ " + saldo ;
    }

}
