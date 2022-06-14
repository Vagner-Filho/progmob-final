package com.example.trabalho3progmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ClienteLogado extends AppCompatActivity {
    private Cliente cliente;
    int cpf;
    String email;
    private ListView listClientes;
    DBHelper helper;
    ArrayList<Cliente> arrayListClientes;
    ArrayAdapter<Cliente> arrayAdapterClientes;
    private int id1,id2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_logado);
/*
        Intent intent = getIntent();
        cliente = (Cliente) intent.getSerializableExtra("infos");
*/
        Bundle args = getIntent().getExtras();
        cpf = args.getInt("cpf");
        //email = args.getString("cpf");

        listClientes=findViewById(R.id.listClientes);
        registerForContextMenu(listClientes);
    }

    public void preencheLista() {
        helper = new DBHelper(ClienteLogado.this);
        arrayListClientes = helper.buscarClientes();
        helper.close();
        if (listClientes != null) {
            arrayAdapterClientes = new ArrayAdapter<Cliente>(
                    ClienteLogado.this,
                    android.R.layout.simple_list_item_1,
                    arrayListClientes);
            listClientes.setAdapter(arrayAdapterClientes);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        preencheLista();
        cliente = helper.buscarClientePorCpf(cpf);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cliente, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.op_editar) {
            Intent intent= new Intent(this, FormCliente.class);
            intent.putExtra("infos", cliente);
            startActivity(intent);
            return true;
        }else if(id==R.id.op_sair) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}