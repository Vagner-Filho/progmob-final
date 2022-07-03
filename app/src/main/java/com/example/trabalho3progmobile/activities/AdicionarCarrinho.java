package com.example.trabalho3progmobile.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.trabalho3progmobile.R;
import com.example.trabalho3progmobile.db.DBHelper;
import com.example.trabalho3progmobile.entities.Carrinho;
import com.example.trabalho3progmobile.entities.Produto;

import java.util.ArrayList;

public class AdicionarCarrinho extends AppCompatActivity {

    DBHelper helper;
    ImageView imagem;
    TextView nome;
    TextView descricao;
    Spinner quantidadeSpinner;
    TextView preco;
    double precoProduto;
    Button bntAdd;
    int quantidade;
    Produto produtoSelecionado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_carrinho);

        Intent it=getIntent();
        produtoSelecionado = (Produto) it.getSerializableExtra("infos");

        recuperarIds();
        configurarSpinner();


        nome.setText(produtoSelecionado.getNome());
        descricao.setText(produtoSelecionado.getDescricao());
        preco.setText("R$ "+String.valueOf(produtoSelecionado.getPreco())+"0");
        precoProduto = produtoSelecionado.getPreco();

        bntAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helper = new DBHelper(AdicionarCarrinho.this);
                helper.inserenoCarrinho(produtoSelecionado, quantidade, getApplicationContext());
                //Toast.makeText(AdicionarSacola.this, String.valueOf(quantidade), Toast.LENGTH_LONG).show();
                helper.close();
                finish();
            }
        });

    }

    private void recuperarIds() {
        imagem = findViewById(R.id.imagemProduto);
        nome = findViewById(R.id.nomeProduto);
        descricao = findViewById(R.id.descricaoProduto);
        preco = findViewById(R.id.precoProduto);
        bntAdd = findViewById(R.id.btnAdicionar);
    }

    private void configurarSpinner() {
        quantidadeSpinner = findViewById(R.id.spinnerQuantidade);

        /*Resources res = getResources();
        String[] qtd = res.getStringArray(R.array.numeros);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, qtd);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quantidadeSpinner.setAdapter(arrayAdapter);*/

        ArrayAdapter arrayAdapter;
        helper = new DBHelper(AdicionarCarrinho.this);
        ArrayList<Carrinho> c = helper.buscarCarrinho();
        boolean apenasUm = helper.apenasUmDisponivel(produtoSelecionado, c);
        helper.close();

        if (produtoSelecionado.getQuantidade() == 1 || apenasUm) {
            arrayAdapter = ArrayAdapter.createFromResource(this, R.array.numeros,
                    android.R.layout.simple_spinner_item);
        } else /*if (produtoSelecionado.getQuantidade() == 2){*/
            arrayAdapter = ArrayAdapter.createFromResource(this, R.array.numeros2,
                    android.R.layout.simple_spinner_item);
        /*} else if (produtoSelecionado.getQuantidade() == 3){
            arrayAdapter = ArrayAdapter.createFromResource(this, R.array.numeros3,
                    android.R.layout.simple_spinner_item);
        } else
            arrayAdapter = ArrayAdapter.createFromResource(this, R.array.numeros4,
                    android.R.layout.simple_spinner_item);*/


        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quantidadeSpinner.setAdapter(arrayAdapter);

        quantidadeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                preco.setText("R$ "+String.valueOf(precoProduto*(pos+1))+"0");
                quantidade = pos+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




    }

    @Override
    protected void onResume() {
        super.onResume();

    }

}