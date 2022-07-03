package com.example.trabalho3progmobile.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.trabalho3progmobile.R;
import com.example.trabalho3progmobile.entities.Cliente;

public class ClienteLogado extends AppCompatActivity {

    LinearLayout fornecedores;
    LinearLayout pedidos;
    Cliente clienteLogado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_logado);

        Intent it=getIntent();
        clienteLogado = (Cliente) it.getSerializableExtra("infos");

        fornecedores = findViewById(R.id.verFornecedores);
        pedidos = findViewById(R.id.gerenciarPedidos);

        fornecedores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClienteLogado.this, FornecedoresDisponiveisActivity.class);
                intent.putExtra("clienteLogado", clienteLogado);
                startActivity(intent);
            }
        });

        pedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClienteLogado.this, PedidosRealizadosActivity.class);
                intent.putExtra("clienteLogado", clienteLogado);
                startActivity(intent);
            }
        });
    }

}