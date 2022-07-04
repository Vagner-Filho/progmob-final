package com.example.trabalho3progmobile.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trabalho3progmobile.R;
import com.example.trabalho3progmobile.activities.CarrinhoActivity;
import com.example.trabalho3progmobile.adapters.ListaCarrinhoAdapter;
import com.example.trabalho3progmobile.db.DBHelper;
import com.example.trabalho3progmobile.entities.Carrinho;
import com.example.trabalho3progmobile.entities.Produto;

import java.util.ArrayList;

public class ProdutosPedidoAdapter extends BaseAdapter {

    Context myContext;
    int layout;
    ArrayList<Produto> data;

    public ProdutosPedidoAdapter(Context myContext, int layout, ArrayList<Produto> data) {
        this.myContext = myContext;
        this.layout = layout;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Produto getItem(int position) { return data.get(position); }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //View view = View.inflate(myContext, R.layout.adapter_carrinho, null);
        //return view; }
        View row = convertView;
        ProdutoHolder holder = null;

        if(row==null) {
            LayoutInflater inflater = ((Activity) myContext).getLayoutInflater();
            row = inflater.inflate(layout, parent, false);

            holder = new ProdutoHolder();
            holder.nome = (TextView) row.findViewById(R.id.nome);
            holder.preco = (TextView) row.findViewById(R.id.preco);
            holder.excluir = (TextView) row.findViewById(R.id.excluir);
            row.setTag(holder);
        } else {
            holder = (ProdutoHolder)row.getTag();
        }

        Produto produto = new Produto();
        produto = (Produto) this.getItem(position);
        holder.nome.setText(produto.getNome());
        String novoPreco = "+ R$ "+String.valueOf(produto.getPreco())+"0";
        holder.preco.setText(novoPreco);
        holder.excluir.setVisibility(View.INVISIBLE);

        return row;
    }

    static class ProdutoHolder{
        TextView nome;
        TextView preco;
        TextView excluir;
    }
}
