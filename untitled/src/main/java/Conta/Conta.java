package Conta;

public class Conta {
    private int id;
    private Usuario usuario;
    private double saldo;

    public Conta(int id, Usuario usuario) {
        this.id = id;
        this.usuario = usuario;
        this.saldo = 0;
    }

    public int getId() {
        return id;
    }

    public  Usuario getUsuario() {
        return usuario;
    }

    public double getSaldo() {
        return saldo;
    }

    public void depositar(double valor, String categoria) {
        if (valor > 0){
            saldo += valor;

            System.out.println("Depósito realizado com sucucesso! \n No valor de R$" + valor + " na categoria " + categoria) ;
        } else{
            System.out.println("Insira um valor válido!");
        }
    }

    public void sacar (double valor) {
        if (valor > 0 && valor <= saldo){
            saldo -= valor;
            System.out.println("");
        }
    }

    @Override
    public String toString() {
        return "Conta" + "id:" + id + ", usuario=" + usuario + ", saldo=" + saldo;
    }

}
