package br.inatel;

public class Cliente {
    private String nome, cpf, email, telefone, data_nascimento;

    public Cliente(String nome, String cpf, String email, String telefone, String data_nascimento) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.data_nascimento = data_nascimento;
    }

    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public String getEmail() { return email; }
    public String getTelefone() { return telefone; }
    public String getData_nascimento() { return data_nascimento; }
}
