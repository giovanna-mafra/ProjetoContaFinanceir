package Conta;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transacao {
    private int id;
    private double valor;
    private LocalDateTime dtHora;
    private String tipo;
    private Usuario usuario;
    private Categoria categoria;

    public Transacao(int id, double valor, String tipo, Usuario usuario, Categoria categoria){
        this.id = id;
        this.valor = valor;
        this.tipo = tipo;
        this.usuario = usuario;
        this.categoria = categoria;
        this.dtHora = LocalDateTime.now();
        processarTransacao();
    }

    public void processarTransacao() {
        if(tipo.equalsIgnoreCase("Receita")) {
            usuario.getConta().adicionarSaldo(valor);
        } else if (tipo.equalsIgnoreCase("despesa")) {
            usuario.getConta().debitarSaldo(valor);
        }

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return "Transacao{" +
                "id=" + id +
                ", valor=" + valor +
                ", tipo='" + tipo + '\'' +
                ", usuario=" + usuario.getNome() +
                ", categoria=" + categoria.getTipo() +
                ", categoria=" + categoria.getTipo() +
                tipo + " de R$" + valor + " em " + dtHora.format(formatter)+ "}";
    }
}
