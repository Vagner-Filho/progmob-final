package com.example.trabalho3progmobile.entities;

import java.io.Serializable;

public class Produto implements Serializable {

    private int id;
    private String foto;
    private String nome;
    private String descricao;
    private double preco;
    private int quantidade;
    private int fornecedorCpf;

    public Produto(int id, String foto, String nome, String descricao, double preco, int quantidade, int fornecedorCpf) {
        this.id = id;
        this.foto = foto;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidade = quantidade;
        this.fornecedorCpf = fornecedorCpf;
    }

    public Produto() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getFornecedorCpf() {
        return fornecedorCpf;
    }

    public void setFornecedorCpf(int fornecedorCpf) {
        this.fornecedorCpf = fornecedorCpf;
    }

    @Override
    public String toString() {
        return "Produtos{" +
                "nome='" + nome + '\'' +
                ", preco=" + preco +
                '}';
    }
}
