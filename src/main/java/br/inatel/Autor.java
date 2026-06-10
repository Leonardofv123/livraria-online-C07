package br.inatel;

public class Autor {
    private String nome, nacionalidade, data_nascimento, biografia;

    public Autor(String nome, String nacionalidade, String data_nascimento, String biografia) {
        this.nome = nome;
        this.nacionalidade = nacionalidade;
        this.data_nascimento = data_nascimento;
        this.biografia = biografia;
    }

    public String getNome() { return nome; }
    public String getNacionalidade() { return nacionalidade; }
    public String getData_nascimento() { return data_nascimento; }
    public String getBiografia() { return biografia; }
}
