package br.inatel.models;

public class Pedido {
    private int id_cliente;
    private String status, observacao;
    private float total;

    public Pedido(int id_cliente, String status, String observacao, float total) {
        this.id_cliente = id_cliente;
        this.status = status;
        this.observacao = observacao;
        this.total = total;
    }

    public int getId_cliente() { return id_cliente; }
    public String getStatus() { return status; }
    public String getObservacao() { return observacao; }
    public float getTotal() { return total; }
}
