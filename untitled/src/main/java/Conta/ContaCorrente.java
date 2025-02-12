package Conta;

public class ContaCorrente extends Conta {
    private double limite;
    private double saldo;''

    public ContaCorrente(int id, Usuario usuario, double limite) {
        this.id = id;
        this.usuario = usuario;
        this.limite = limite;
        this.saldo = 0;

    }
}
