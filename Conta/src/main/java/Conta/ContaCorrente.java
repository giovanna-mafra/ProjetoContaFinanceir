package Conta;

public class ContaCorrente implements Conta {
private double saldo;

public ContaCorrente(double saldoInicial) {
    this.saldo = saldoInicial;
}

    @Override
    public double getSaldo() {
        return saldo;
    }

    @Override
    public void adicionarSaldo(double valor) {
    saldo += valor;

    }

    @Override
    public void debitarSaldo(double valor) {

        if(saldo>= valor) {
            saldo -= valor;
        } else {
            System.out.println("Saldo na Conta Corrente insuficiente!");
        }
    }

  @Override
  public String toString() {
      return "ContaCorrente saldo= " + saldo;
  }

}
