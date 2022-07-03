package com.example.trabalho3progmobile.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trabalho3progmobile.EditarProduto;
import com.example.trabalho3progmobile.entities.Produto;
import com.example.trabalho3progmobile.entities.Carrinho;
import com.example.trabalho3progmobile.entities.Cliente;
import com.example.trabalho3progmobile.db.DBHelper;
import com.example.trabalho3progmobile.entities.Fornecedor;
import com.example.trabalho3progmobile.adapters.ProdutosAdapter;
import com.example.trabalho3progmobile.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ProdutosDisponiveisActivity extends AppCompatActivity {
    private Cliente clienteLogado;
    private Fornecedor fornecedor;
    int cpf;
    String email;
    private ListView listClientes;
    DBHelper helper;
    ArrayList<Cliente> arrayListClientes;
    ArrayAdapter<Cliente> arrayAdapterClientes;
    ArrayList<Produto> arrayListProdutos;
    private int id1,id2;
    TextView diminuir;
    TextView quantidadeText;
    int quantidadeInt;
    FloatingActionButton btnAdd;
    private ArrayList<Carrinho> arrayListCarrinho;
    TextView teste;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos_disponiveis);

        Intent intent = getIntent();
        fornecedor = (Fornecedor) intent.getSerializableExtra("fornecedor");
        clienteLogado = (Cliente) intent.getSerializableExtra("clienteLogado");


        /*Bundle args = getIntent().getExtras();
        cpf = args.getInt("cpf");
        //email = args.getString("cpf");*/
        txt=findViewById(R.id.textView8);

        listClientes=findViewById(R.id.listClientes);
        //registerForContextMenu(listClientes);

        btnAdd = findViewById(R.id.btnAdd);



        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clienteLogado != null) {
                    Intent intent = new Intent(ProdutosDisponiveisActivity.this, CarrinhoActivity.class);
                    intent.putExtra("fornecedor", fornecedor);
                    intent.putExtra("clienteLogado", clienteLogado);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(ProdutosDisponiveisActivity.this, EditarProduto.class);
                    intent.putExtra("fornecedor", fornecedor);
                    startActivity(intent);
                }
            }
        });

        listClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Intent intent= new Intent(this, AdicionarSacola.class);
                boolean existe = helper.existemUnidadesSuficientesCarrinho(arrayListProdutos.get(i), arrayListCarrinho);

                if (clienteLogado != null) {

                    if (arrayListProdutos.get(i).getQuantidade() == 0) {
                        alert("Item indisponível!");
                    }
                    else if (!existe) {
                        alert("Voce já colocou todas as unidades disponíveis desse produto no carrinho");
                    }
                    else {
                        Intent intent = new Intent(ProdutosDisponiveisActivity.this, AdicionarCarrinho.class);
                        intent.putExtra("infos", arrayListProdutos.get(i));
                        startActivity(intent);
                    }
                }else {
                    Intent intent = new Intent(ProdutosDisponiveisActivity.this, EditarProduto.class);
                    intent.putExtra("infos", arrayListProdutos.get(i));
                    intent.putExtra("fornecedor", fornecedor);
                    startActivity(intent);
            }
            }});
    }

    public void preencheLista() {
        /*helper = new DBHelper(ClienteLogado.this);
        arrayListClientes = helper.buscarClientes();
        helper.close();
        if (listClientes != null) {
            /*arrayAdapterClientes = new ArrayAdapter<Cliente>(
                    ClienteLogado.this,
                    android.R.layout.simple_list_item_1,
                    arrayListClientes);
            listClientes.setAdapter(arrayAdapterClientes);*/

            /*ProdutosAdapter adapter = new ProdutosAdapter(ClienteLogado.this, arrayListClientes);
            this.registerForContextMenu(listClientes);
            listClientes.setAdapter(adapter);*/
/*
            Teste adapter = new Teste(this, R.layout.adapter_produtos_item, arrayListClientes);
            //this.registerForContextMenu(listClientes);
            listClientes.setAdapter(adapter);
        }*/


        helper = new DBHelper(ProdutosDisponiveisActivity.this);
        arrayListProdutos = helper.buscarProdutos(String.valueOf(fornecedor.getCpf()));
        //txt.setText(String.valueOf(arrayListProdutos.size()));
        helper.close();

        if (listClientes != null) {
            ProdutosAdapter adapter = new ProdutosAdapter(this, R.layout.adapter_produtos_item, arrayListProdutos);
            //this.registerForContextMenu(listClientes);
            listClientes.setAdapter(adapter);
        } else {
            Toast.makeText(this, "afeeee", Toast.LENGTH_LONG);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        preencheLista();

        if (clienteLogado != null) {
            helper = new DBHelper(ProdutosDisponiveisActivity.this);
            arrayListCarrinho = helper.buscarCarrinho();
            helper.close();

            //Toast.makeText(this, String.valueOf(arrayListCarrinho), Toast.LENGTH_LONG);
            if (!arrayListCarrinho.isEmpty()) {
                btnAdd.setVisibility(View.VISIBLE);
            } else
                btnAdd.setVisibility(View.INVISIBLE);
        } else
            btnAdd.setVisibility(View.VISIBLE);
    }
/*
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
            intent.putExtra("infos", clienteLogado);
            startActivity(intent);
            return true;
        }/*else if (id == R.id.op_carrinho) {
            Intent intent= new Intent(this, CarrinhoActivity.class);
            startActivity(intent);
            return true;
        }*//*else if(id==R.id.op_sair) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

    private void alert(String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }
}