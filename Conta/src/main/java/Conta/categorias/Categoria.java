package Conta.categorias;

public class Categoria {
    private int id;
    private String tipo;

    public Categoria(int id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + id +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
