package Conta.transacoes;

import Conta.contas.ContaCorrente;
import Conta.usuarios.Usuario;
import Conta.categorias.Categoria;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transacao {
    private int id;
    private double valor;
    private String tipo;
    private Usuario usuario;
    private Categoria categoria;
    private LocalDateTime dtHora;

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
        if (tipo.equalsIgnoreCase("Receita")) {
            usuario.getConta().adicionarSaldo(valor);
        } else if (tipo.equalsIgnoreCase("Despesa")) {
            usuario.getConta().debitarSaldo(valor);
        }
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

        String tipoConta = usuario.getConta() instanceof ContaCorrente ? "Conta Corrente" : "Conta Poupança";

        return "Transacao{" +
                "id=" + id +
                ", valor=" + valor +
                ", tipo='" + tipo + '\'' +
                ", usuario=" + usuario.getNome() +
                ", categoria=" + categoria.getTipo() +
                ", tipoConta=" + tipoConta +
                ", dataHora: " + dtHora.format(formatter) +
                "}";
    }

}
