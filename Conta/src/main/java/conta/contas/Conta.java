package conta.contas;

import java.math.BigDecimal;
import conta.enums.TipoContaEnum;

public class Conta {
    private int id;
    private TipoContaEnum tipoConta;
    private BigDecimal saldo;

    public Conta(TipoContaEnum tipoContaEnum, double saldo) {
        this.tipoConta = tipoContaEnum;
        this.saldo = BigDecimal.valueOf(saldo);
    }


    public void adicionarSaldo(double valor) {
        this.saldo = this.saldo.add(BigDecimal.valueOf(valor));
    }

    public void retirarSaldo(double valor) {
        this.saldo = this.saldo.subtract(BigDecimal.valueOf(valor));
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TipoContaEnum getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(TipoContaEnum tipoConta) {
        this.tipoConta = tipoConta;
    }

    @Override
    public String toString() {
        return "Conta{id=" + id + ", tipoConta=" + tipoConta + ", saldo=" + saldo + "}";
    }
}
