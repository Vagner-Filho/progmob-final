package com.example.trabalho3progmobile.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trabalho3progmobile.entities.Produto;
import com.example.trabalho3progmobile.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ProdutosAdapter extends ArrayAdapter<Produto> {

    Context myContext;
    int layout;
    ArrayList<Produto> data;

    public ProdutosAdapter(Context context, int tipoLayout, ArrayList<Produto> data) {
        super(context, tipoLayout, data);
        this.myContext = context;
        this.layout = tipoLayout;
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

    /*public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ClienteHolder holder = null;

        if(row==null) {
            LayoutInflater inflater = ((Activity) myContext).getLayoutInflater();
            row = inflater.inflate(layout, parent, false);

            holder = new ClienteHolder();
            holder.textView = (TextView) row.findViewById(R.id.textView);
            holder.imageView = (ImageView) row.findViewById(R.id.imageView);
            row.setTag(holder);
        } else {
            holder = (ClienteHolder)row.getTag();
        }

        Cliente cliente = new Cliente();
        cliente = (Cliente) this.getItem(position);
        holder.imageView.setImageResource(R.mipmap.ic_launcher);
        holder.textView.setText(cliente.getNome());

        return row;
    }

    static class ClienteHolder{
        TextView textView;
        ImageView imageView;
    }*/

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ProdutoHolder holder = null;

        if(row==null) {
            LayoutInflater inflater = ((Activity) myContext).getLayoutInflater();
            row = inflater.inflate(layout, parent, false);

            holder = new ProdutoHolder();
            //holder.descricao = (TextView) row.findViewById(R.id.descricao);
            holder.imageView = (ImageView) row.findViewById(R.id.imagem);
            holder.nome = (TextView) row.findViewById(R.id.nome);
            holder.preco = (TextView) row.findViewById(R.id.preco);
            holder.descricao = (TextView) row.findViewById(R.id.descricao);
            holder.unidades = (TextView) row.findViewById(R.id.unidades);
            row.setTag(holder);
        } else {
            holder = (ProdutoHolder)row.getTag();
        }

        Produto produtos = new Produto();
        produtos = (Produto) this.getItem(position);
        //int qtd = cardapio.getUnidades();


        if (produtos.getFoto()!=null){
            byte[] img;
            img = Base64.decode(produtos.getFoto(), Base64.DEFAULT);
            Bitmap imgDecodificada = BitmapFactory.decodeByteArray(img, 0, img.length);
            holder.imageView.setImageBitmap(imgDecodificada);
        } else if (produtos.getFoto()==null){
            holder.imageView.setImageResource(R.mipmap.ic_launcher_round);
        }
        holder.descricao.setText(produtos.getDescricao());
        holder.nome.setText(produtos.getNome());
        holder.unidades.setText("Unidades disponíveis: " + String.valueOf(produtos.getQuantidade()));
        String novoPreco = "R$ " + String.valueOf(produtos.getPreco()) + "0";
        holder.preco.setText(novoPreco);
        return row;
    }

    static class ProdutoHolder{
        ImageView imageView;
        TextView descricao;
        TextView nome;
        TextView preco;
        TextView unidades;
        TextView aumentar;
    }

}
