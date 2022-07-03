package com.example.trabalho3progmobile.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.trabalho3progmobile.R;
import com.example.trabalho3progmobile.entities.Cliente;
import com.example.trabalho3progmobile.entities.Fornecedor;

public class FornecedorLogado extends AppCompatActivity {

    LinearLayout produtos;
    LinearLayout pedidos;
    Fornecedor fornecedorLogado;
    String tipoPedidos;
    TextView titulo1;
    TextView subTitulo1;
    TextView titulo2;
    TextView subTitulo2;
    TextView teste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fornecedor_logado);

        Intent it=getIntent();
        fornecedorLogado = (Fornecedor) it.getSerializableExtra("infos");
        tipoPedidos = (String) it.getSerializableExtra("tipoPedidos");

        produtos = findViewById(R.id.gerenciarProdutos);
        pedidos = findViewById(R.id.verPedidos);
        titulo1 = findViewById(R.id.titulo1);
        titulo2 = findViewById(R.id.titulo2);
        subTitulo1 = findViewById(R.id.subtitulo1);
        subTitulo2 = findViewById(R.id.subtitulo2);
        //teste = findViewById(R.id.textView9);

        /*if(fornecedorLogado != null) {
            teste.setText("AAAAAAAAAAAAAA");
        }
        if(fornecedorLogado == null) {
            teste.setText("NBBBBBBBBBBBBB");
        }*/
        if (tipoPedidos != null) {
            titulo1.setText("Pedidos Aceitos");
            titulo2.setText("Pedidos em Espera");
            subTitulo1.setText("Pedidos que ja foram aceitos");
            subTitulo2.setText("Pedidos que aguardam confirmação");
        }

        produtos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tipoPedidos == null) {
                    Intent intent = new Intent(FornecedorLogado.this, ProdutosDisponiveisActivity.class);
                    intent.putExtra("fornecedor", fornecedorLogado);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(FornecedorLogado.this, PedidosRealizadosActivity.class);
                    intent.putExtra("fornecedor", fornecedorLogado);
                    intent.putExtra("tipoPedidos", "aceitos");
                    startActivity(intent);
                }
            }
        });

        pedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tipoPedidos == null) {
                    Intent intent = new Intent(FornecedorLogado.this, FornecedorLogado.class);
                    intent.putExtra("infos", fornecedorLogado);
                    intent.putExtra("tipoPedidos", "sim");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(FornecedorLogado.this, PedidosRealizadosActivity.class);
                    intent.putExtra("fornecedor", fornecedorLogado);
                    intent.putExtra("tipoPedidos", "emEspera");
                    startActivity(intent);
                }
            }
        });
    }

}