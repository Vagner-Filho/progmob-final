package com.example.trabalho3progmobile.entities;

import java.io.Serializable;

public class Carrinho implements Serializable {

    private int id;
    private int id_produto;
    private String nome;
    private double preco;

    public Carrinho() {
    }

    public Carrinho(int id, int id_produto, String nome, double preco) {
        this.id = id;
        this.id_produto = id_produto;
        this.nome = nome;
        this.preco = preco;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_produto() {
        return id_produto;
    }

    public void setId_produto(int id_produto) {
        this.id_produto = id_produto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}
