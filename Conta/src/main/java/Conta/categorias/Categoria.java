package Conta.categorias;

import Conta.sistema.GeradorId;

public class Categoria {
    private int id;
    private String nome;

    public Categoria(String nome) {
        this.id = new GeradorId().gerarId(); // Gera um id único
        this.nome = nome;
    }

    public Categoria(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Nome: " + nome;
    }
}