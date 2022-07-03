package com.example.trabalho3progmobile;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trabalho3progmobile.adapters.PedidosFeitosAdapter;
import com.example.trabalho3progmobile.db.DBHelper;
import com.example.trabalho3progmobile.entities.Cliente;
import com.example.trabalho3progmobile.entities.Pedido;

import java.util.ArrayList;

public class PedidosRecebidosAdapter extends ArrayAdapter<Pedido> {

    Context myContext;
    int layout;
    ArrayList<Pedido> data;

    public PedidosRecebidosAdapter(Context context, int tipoLayout, ArrayList<Pedido> data) {
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
            holder.preco = (TextView) row.findViewById(R.id.status);
            holder.cpfCliente = (TextView) row.findViewById(R.id.preco);
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
        holder.cpfCliente.setText(String.valueOf(pedidos.getClienteCpf()));
        DBHelper dbHelper = new DBHelper(myContext);
        Cliente cliente = dbHelper.buscarClientePorCpf(pedidos.getClienteCpf());
        holder.cpfCliente.append(" - "+String.valueOf(cliente.getNome()));
        dbHelper.close();
        String novoPreco = "R$ " + String.valueOf(pedidos.getPreco()) + "0";
        holder.preco.setText(novoPreco);
        return row;
    }

    static class ProdutoHolder{
        ImageView imageView;
        TextView horario;
        TextView cpfCliente;
        TextView preco;
    }

}
