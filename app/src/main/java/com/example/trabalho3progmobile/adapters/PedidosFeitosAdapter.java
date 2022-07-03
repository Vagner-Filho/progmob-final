package com.example.trabalho3progmobile.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trabalho3progmobile.R;
import com.example.trabalho3progmobile.entities.Pedido;

import java.util.ArrayList;

public class PedidosFeitosAdapter extends ArrayAdapter<Pedido> {

    Context myContext;
    int layout;
    ArrayList<Pedido> data;

    public PedidosFeitosAdapter(Context context, int tipoLayout, ArrayList<Pedido> data) {
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
    public Pedido getItem(int position) { return data.get(position); }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ProdutoHolder holder = null;

        if(row==null) {
            LayoutInflater inflater = ((Activity) myContext).getLayoutInflater();
            row = inflater.inflate(layout, parent, false);

            holder = new ProdutoHolder();
            holder.horario = (TextView) row.findViewById(R.id.horarioRetirada);
            holder.imageView = (ImageView) row.findViewById(R.id.imagem);
            holder.status = (TextView) row.findViewById(R.id.status);
            holder.preco = (TextView) row.findViewById(R.id.preco);
            //holder.descricao = (TextView) row.findViewById(R.id.descricao);
            //holder.unidades = (TextView) row.findViewById(R.id.unidades);
            row.setTag(holder);
        } else {
            holder = (ProdutoHolder)row.getTag();
        }

        Pedido pedidos = new Pedido();
        pedidos = (Pedido) this.getItem(position);
        //int qtd = cardapio.getUnidades();


        //holder.imageView.setImageResource(R.mipmap.ic_launcher_round);
        holder.horario.setText("Retirada: " +pedidos.getHorarioRetirada()+"  "+pedidos.getDataRetirada());

        if(pedidos.getStatus() == 0)  {
            holder.status.setText("Status: Em espera");
        } else if (pedidos.getStatus() == 1) {
            holder.status.setText("Status: Pedido Aprovado");
        } else
            holder.status.setText("Status: Pedido Negado");

        //holder.unidades.setText("Unidades disponíveis: " + String.valueOf(pedidos.getUnidades()));
        String novoPreco = "R$ " + String.valueOf(pedidos.getPreco()) + "0";
        holder.preco.setText(novoPreco);
        return row;
    }

    static class ProdutoHolder{
        ImageView imageView;
        TextView horario;
        TextView status;
        TextView preco;
    }

}