package br.inatel.models;

public class Livro {
    private String titulo, isbn, descricao;
    private int estoque, ano_publicacao;
    private float preco;

    public Livro(String titulo, String isbn, String descricao, int estoque, int ano_publicacao, float preco) {
        this.titulo = titulo;
        this.isbn = isbn;
        this.descricao = descricao;
        this.estoque = estoque;
        this.ano_publicacao = ano_publicacao;
        this.preco = preco;
    }

    public String getTitulo() { return titulo; }
    public String getIsbn() { return isbn; }
    public String getDescricao() { return descricao; }
    public int getAno_publicacao() { return ano_publicacao; }
    public int getEstoque() { return estoque; }
    public float getPreco() { return preco; }
}
