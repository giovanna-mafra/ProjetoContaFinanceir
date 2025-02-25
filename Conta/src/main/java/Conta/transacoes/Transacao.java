package Conta.transacoes;

import Conta.usuarios.Usuario;
import Conta.categorias.Categoria;
import java.math.BigDecimal;

public class Transacao {
    private int id;
    private BigDecimal valor;
    private String tipo;
    private Usuario usuario;
    private Categoria categoria;

    public Transacao(int id, BigDecimal valor, String tipo, Usuario usuario, Categoria categoria) {
        this.id = id;
        this.valor = valor;
        this.tipo = tipo;
        this.usuario = usuario;
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }



    public Usuario getUsuario() {
        return usuario;
    }



    @Override
    public String toString() {
        return "Transacao{id=" + id + ", valor=" + valor + ", tipo='" + tipo + "', usuario=" + usuario + ", categoria=" + categoria + "}";
    }
}
