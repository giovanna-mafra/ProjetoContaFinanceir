package Conta;

import java.util.List;

public class Categoria {
    private int id;
    private String tipo;

    public Categoria(int id,String tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }


    public String getTipo(){
        return tipo;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Categoria:" + tipo;
    }

    public static void listarCategorias(List<Categoria> categorias) {
        if (categorias.isEmpty()) {
            System.out.println("Nenhuma categoria foi cadastrada ainda");
        } else{
            categorias.forEach(System.out::println);
        }
    }

}
