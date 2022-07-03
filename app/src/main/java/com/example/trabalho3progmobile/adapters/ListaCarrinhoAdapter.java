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

import com.example.trabalho3progmobile.entities.Carrinho;
import com.example.trabalho3progmobile.db.DBHelper;
import com.example.trabalho3progmobile.R;
import com.example.trabalho3progmobile.activities.CarrinhoActivity;
import com.example.trabalho3progmobile.entities.Cliente;
import com.example.trabalho3progmobile.entities.Fornecedor;

import java.util.ArrayList;

public class ListaCarrinhoAdapter extends BaseAdapter {

    Context myContext;
    int layout;
    ArrayList<Carrinho> data;
    Fornecedor fornecedor;
    Cliente cliente;

    public ListaCarrinhoAdapter(Context myContext, int layout, ArrayList<Carrinho> data, Fornecedor fornecedor, Cliente cliente) {
        this.myContext = myContext;
        this.layout = layout;
        this.data = data;
        this.fornecedor = fornecedor;
        this.cliente = cliente;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Carrinho getItem(int position) { return data.get(position); }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //View view = View.inflate(myContext, R.layout.adapter_carrinho, null);
        //return view; }
        View row = convertView;
        CarrinhoHolder holder = null;

        if(row==null) {
            LayoutInflater inflater = ((Activity) myContext).getLayoutInflater();
            row = inflater.inflate(layout, parent, false);

            holder = new CarrinhoHolder();
            //holder.descricao = (TextView) row.findViewById(R.id.descricao);
            //holder.imageView = (ImageView) row.findViewById(R.id.imagem);
            holder.nome = (TextView) row.findViewById(R.id.nome);
            holder.preco = (TextView) row.findViewById(R.id.preco);
            holder.excluir = (TextView) row.findViewById(R.id.excluir);
            holder.id = (TextView) row.findViewById(R.id.id);
            /*TextView txt = convertView.findViewById(R.id.carrinho);
            txt.setText("eitapreula");*/
            View finalRow = row;
            holder.excluir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView nome = finalRow.findViewById(R.id.nome);
                    TextView id = finalRow.findViewById(R.id.id);

                    Toast.makeText(myContext.getApplicationContext(), "Item excluído do carrinho com Sucesso!", Toast.LENGTH_SHORT).show();


                    DBHelper helper = new DBHelper(myContext.getApplicationContext());
                    //(Activity) myContext)
                    //helper.excluirDoCarrinho(String.valueOf(finalHolder.nome.getText()), String.valueOf(finalHolder.preco.getText()));
                    helper.excluirDoCarrinho(String.valueOf(id.getText()));
                    helper.close();
                    ((Activity) myContext).finish();
                    Intent it = new Intent(myContext, CarrinhoActivity.class);
                    it.putExtra("fornecedor", fornecedor);
                    it.putExtra("clienteLogado", cliente);
                    myContext.startActivity(it);
                }
            });
            row.setTag(holder);
        } else {
            holder = (CarrinhoHolder)row.getTag();
        }

        Carrinho carrinho = new Carrinho();
        carrinho = (Carrinho) this.getItem(position);
        holder.nome.setText(carrinho.getNome());
        String novoPreco = "+ R$ "+String.valueOf(carrinho.getPreco())+"0";
        holder.preco.setText(novoPreco);
        holder.id.setText(String.valueOf(carrinho.getId()));

        return row;
    }

    static class CarrinhoHolder{
        ImageView imageView;
        TextView descricao;
        TextView nome;
        TextView preco;
        TextView excluir;
        TextView id;
        TextView aumentar;
    }
}
