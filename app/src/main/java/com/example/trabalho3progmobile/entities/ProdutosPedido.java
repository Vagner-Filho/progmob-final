package com.example.trabalho3progmobile.entities;

import java.io.Serializable;

public class ProdutosPedido implements Serializable {

    int pedidoId;
    int produtoId;

    public ProdutosPedido() {
    }

    public ProdutosPedido(int pedidoId, int produtoId) {
        this.pedidoId = pedidoId;
        this.produtoId = produtoId;
    }

    public int getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(int id) {
        this.pedidoId = id;
    }

    public int getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(int produtoId) {
        this.produtoId = produtoId;
    }
}
