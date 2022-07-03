package com.example.trabalho3progmobile.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trabalho3progmobile.entities.Fornecedor;
import com.example.trabalho3progmobile.R;

import java.util.ArrayList;

public class FornecedorAdapter extends ArrayAdapter<Fornecedor> {

    Context myContext;
    int layout;
    ArrayList<Fornecedor> data;

    public FornecedorAdapter (Context context, int tipoLayout, ArrayList<Fornecedor> data) {
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
    public Fornecedor getItem(int position) { return data.get(position); }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        FornecedorHolder holder = null;

        if(row==null) {
            LayoutInflater inflater = ((Activity) myContext).getLayoutInflater();
            row = inflater.inflate(layout, parent, false);

            holder = new FornecedorHolder();
            holder.imageView = (ImageView) row.findViewById(R.id.imagem);
            holder.nome = (TextView) row.findViewById(R.id.nome);
            row.setTag(holder);
        } else {
            holder = (FornecedorHolder)row.getTag();
        }

        Fornecedor fornecedor= new Fornecedor();
        fornecedor = (Fornecedor) this.getItem(position);
        //holder.imageView.setImageResource(R.mipmap.ic_launcher_round);
        holder.nome.setText(fornecedor.getNomeLoja());

        return row;
    }

    static class FornecedorHolder{
        ImageView imageView;
        TextView nome;
    }
}
