package com.example.trabalho3progmobile.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trabalho3progmobile.entities.Cliente;
import com.example.trabalho3progmobile.db.DBHelper;
import com.example.trabalho3progmobile.entities.Fornecedor;
import com.example.trabalho3progmobile.adapters.FornecedorAdapter;
import com.example.trabalho3progmobile.R;

import java.util.ArrayList;

public class FornecedoresDisponiveisActivity extends AppCompatActivity {

    ListView listFornecedores;
    private DBHelper helper;
    private ArrayList<Fornecedor> arrayListFornecedores;
    Cliente clienteLogado;
    //TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fornecedores_disponiveis);

        Intent it=getIntent();
        clienteLogado = (Cliente) it.getSerializableExtra("clienteLogado");

        listFornecedores = findViewById(R.id.listFornecedores);
        //txt = findViewById(R.id.teste);

        listFornecedores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(FornecedoresDisponiveisActivity.this, ProdutosDisponiveisActivity.class);
                intent.putExtra("fornecedor", arrayListFornecedores.get(i));
                intent.putExtra("clienteLogado", clienteLogado);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        preencheLista();
    }

    public void preencheLista() {

        helper = new DBHelper(FornecedoresDisponiveisActivity.this);
        helper.excluirCarrinho();
        arrayListFornecedores = helper.buscarFornecedores();
        //txt.setText(String.valueOf(arrayListFornecedores.size()));
        helper.close();

        if (listFornecedores != null) {
            FornecedorAdapter adapter = new FornecedorAdapter(this, R.layout.adapter_fornecedor, arrayListFornecedores);
            //this.registerForContextMenu(listClientes);
            listFornecedores.setAdapter(adapter);
        } else {
            Toast.makeText(this, "afeeee", Toast.LENGTH_LONG);
        }
    }
}