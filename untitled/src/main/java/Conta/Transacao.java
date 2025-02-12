package Conta;

public class Transacao {
    private int id;
    private double valor;
    private String tipo;
    private Usuario usuario;
    private Categoria categoria;

    public Transacao(int id, double valor, String tipo, Usuario usuario, Categoria categoria){
        this.id = id;
        this.valor = valor;
        this.tipo = tipo;
        this.usuario = usuario;
        this.categoria = categoria;
    }

    public Transacao() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public double getValor() {
        return valor;
    }

    public String getTipo(){
        return tipo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Categoria getCategoria(){
        return categoria;
    }


    @Override
    public String toString() {
        return "Transacao{" +
                "id=" + id +
                ", valor=" + valor +
                ", tipo='" + tipo + '\'' +
                ", usuario=" + usuario +
                ", categoria=" + categoria +
                '}';
    }
}
