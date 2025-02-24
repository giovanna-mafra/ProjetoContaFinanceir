package Conta.transacoes;

import Conta.usuarios.Usuario;
import Conta.categorias.Categoria;
import java.math.BigDecimal;

public class Transacao {
    private int id;
    private BigDecimal valor;
    private String tipo;  // Exemplo: "Receita" ou "Despesa"
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

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Transacao{id=" + id + ", valor=" + valor + ", tipo='" + tipo + "', usuario=" + usuario + ", categoria=" + categoria + "}";
    }
}
