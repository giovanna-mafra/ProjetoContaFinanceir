package conta.transacoes;

import conta.usuarios.Usuario;
import conta.categorias.Categoria;
import java.math.BigDecimal;

public class Transacao {
    private int id;
    private BigDecimal valor;
    private String tipo;
    private Usuario usuario;
    private Categoria categoria;

    public Transacao(int id, BigDecimal valor, String tipo, Usuario usuario, Categoria categoria) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Valor da transação não pode ser negativo");
        }
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
        if (valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Valor não pode ser negativo");
        }
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
