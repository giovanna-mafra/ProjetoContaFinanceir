package Conta.sistema;

public class GeradorId {
    private int ultimoId;

    public GeradorId() {
        this.ultimoId = 0;
    }

    public int gerarId() {
        ultimoId++;
        return ultimoId;
    }
}
