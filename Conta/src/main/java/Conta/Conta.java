package Conta;

public abstract class Conta {
     protected  double saldo;

        public Conta(double saldoInicial) {
            this.saldo = saldoInicial;
        }

        public double getSaldo() {
            return saldo;
        }

        public abstract void adicionarSaldo (double valor);
        public abstract void debitarSaldo(double valor);

}
