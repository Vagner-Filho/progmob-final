package com.example.trabalho3progmobile.entities;

import java.io.Serializable;

public class Pedido implements Serializable {

    int id;
    int clienteCpf;
    int fornecedorCpf;
    double preco;
    String horarioRetirada;
    String dataRetirada;
    String horarioRealizado;
    int status; // -1 negado 1 aprovado 0 sem definicao

    public Pedido() {
    }

    public Pedido(int id, int clienteCpf, int fornecedorCpf, double preco, String horarioRetirada,
                  String dataRetirada, String horarioRealizado, int status) {
        this.id = id;
        this.clienteCpf = clienteCpf;
        this.fornecedorCpf = fornecedorCpf;
        this.preco = preco;
        this.horarioRetirada = horarioRetirada;
        this.dataRetirada = dataRetirada;
        this.horarioRealizado = horarioRealizado;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClienteCpf() {
        return clienteCpf;
    }

    public void setClienteCpf(int clienteCpf) {
        this.clienteCpf = clienteCpf;
    }

    public int getFornecedorCpf() {
        return fornecedorCpf;
    }

    public void setFornecedorCpf(int fornecedorCpf) {
        this.fornecedorCpf = fornecedorCpf;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getHorarioRetirada() {
        return horarioRetirada;
    }

    public void setHorarioRetirada(String horarioRetirada) {
        this.horarioRetirada = horarioRetirada;
    }

    public String getDataRetirada() {
        return dataRetirada;
    }

    public void setDataRetirada(String dataRetirada) {
        this.dataRetirada = dataRetirada;
    }

    public String getHorarioRealizado() {
        return horarioRealizado;
    }

    public void setHorarioRealizado(String horarioRealizado) {
        this.horarioRealizado = horarioRealizado;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
