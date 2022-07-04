package com.example.trabalho3progmobile.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trabalho3progmobile.entities.Carrinho;
import com.example.trabalho3progmobile.entities.Cliente;
import com.example.trabalho3progmobile.db.DBHelper;
import com.example.trabalho3progmobile.entities.Fornecedor;
import com.example.trabalho3progmobile.adapters.ListaCarrinhoAdapter;
import com.example.trabalho3progmobile.R;

import java.util.ArrayList;

public class CarrinhoActivity extends AppCompatActivity {
    private Cliente clienteLogado;
    private Fornecedor fornecedor;
    DBHelper helper;
    ArrayList<Carrinho> arrayListCarrinho;
    ListView listCarrinho;
    ArrayAdapter<Cliente> arrayAdapterCarrinho;
    TextView invisivel;
    TextView total;
    Button btnContinuar;
    double soma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);


        Intent intent = getIntent();
        fornecedor = (Fornecedor) intent.getSerializableExtra("fornecedor");
        clienteLogado = (Cliente) intent.getSerializableExtra("clienteLogado");

        listCarrinho = findViewById(R.id.listCarrinho);
        btnContinuar = findViewById(R.id.continuar);

        preencheTela();

        listCarrinho.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(CarrinhoActivity.this, String.valueOf(arrayListCarrinho.get(i).getId()), Toast.LENGTH_SHORT).show();
            }
        });

        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(CarrinhoActivity.this, FinalizarPedidoActivity.class);
                it.putExtra("preco", soma);
                it.putExtra("fornecedor", fornecedor);
                it.putExtra("clienteLogado", clienteLogado);
                startActivity(it);
            }
        });

        //carrinho = findViewById(R.id.carrinho);
        //carrinho.setText(String.valueOf(arrayListCarrinho.size()));

        /*helper = new DBHelper(CarrinhoActivity.this);
        arrayListCarrinho = helper.buscarCarrinho();
        helper.close();

        //listCarrinho = findViewById(R.id.listCarrinho);

        /*if (listCarrinho != null) {
            arrayAdapterCarrinho = new ArrayAdapter<Cliente>(
                    CarrinhoActivity.this,
                    android.R.layout.simple_list_item_1,
                    arrayListCarrinho);/*
            listCarrinho.setAdapter(arrayAdapterCarrinho);
        } else {
            Toast.makeText(this, "afeeee", Toast.LENGTH_LONG);
        }*/


    }

    @Override
    protected void onResume() {
        super.onResume();
        preencheTela();
    }

    public void preencheTela() {

        soma = 0;
        total = findViewById(R.id.total);

        helper = new DBHelper(CarrinhoActivity.this);
        arrayListCarrinho = helper.buscarCarrinho();
        helper.close();

        if (!arrayListCarrinho.isEmpty()) {
            ListaCarrinhoAdapter adapter = new ListaCarrinhoAdapter(this, R.layout.adapter_carrinho, arrayListCarrinho, fornecedor, clienteLogado);
            //this.registerForContextMenu(listClientes);

            listCarrinho.setAdapter(adapter);
        } else {

            invisivel = findViewById(R.id.naoExiste);
            invisivel.getLayoutParams().height = 120;
            total.setVisibility(View.INVISIBLE);
            btnContinuar.setVisibility(View.INVISIBLE);
            finish();
        }

        for (Carrinho c: arrayListCarrinho) {
            soma += Double.valueOf(c.getPreco());
        }

        total.setText(String.valueOf("R$ "+soma+"0"));
    }
}