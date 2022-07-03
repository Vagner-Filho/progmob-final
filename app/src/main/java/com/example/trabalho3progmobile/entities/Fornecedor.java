package com.example.trabalho3progmobile.entities;

import java.io.Serializable;

public class Fornecedor implements Serializable {
    private int cpf;
    private String nomeLoja;
    private String nome;
    private String sobrenome;
    private String localizacao;
    private String email;
    private String senha;

    public  Fornecedor() {}

    public Fornecedor(int cpf, String nomeLoja, String nome, String sobrenome, String localizacao, String email, String senha) {
        this.cpf = cpf;
        this.nomeLoja = nomeLoja;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.localizacao = localizacao;
        this.email = email;
        this.senha = senha;
    }

    public int getCpf() {
        return cpf;
    }

    public void setCpf(int cpf) {
        this.cpf = cpf;
    }

    public String getNomeLoja() {
        return nomeLoja;
    }

    public void setNomeLoja(String nomeLoja) {
        this.nomeLoja = nomeLoja;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
