package com.example.trabalho3progmobile.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trabalho3progmobile.PedidosRecebidosAdapter;
import com.example.trabalho3progmobile.R;
import com.example.trabalho3progmobile.adapters.PedidosFeitosAdapter;
import com.example.trabalho3progmobile.db.DBHelper;
import com.example.trabalho3progmobile.entities.Cliente;
import com.example.trabalho3progmobile.entities.Fornecedor;
import com.example.trabalho3progmobile.entities.Pedido;
import com.example.trabalho3progmobile.entities.Produto;

import java.util.ArrayList;

public class PedidosRealizadosActivity extends AppCompatActivity {
    private Cliente clienteLogado;
    Fornecedor fornecedorLogado;
    int cpf;
    String tipoPedidos;
    private ListView listPedidos;
    DBHelper helper;
    ArrayList<Pedido> arrayListPedidos;
    ArrayList<Pedido> arrayListPedidosCorretos;
    ArrayAdapter<Cliente> arrayAdapterClientes;
    ArrayList<Produto> arrayListProdutos;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos_realizados);

        Intent it=getIntent();
        clienteLogado = (Cliente) it.getSerializableExtra("clienteLogado");
        fornecedorLogado = (Fornecedor) it.getSerializableExtra("fornecedor");
        tipoPedidos = (String) it.getSerializableExtra("tipoPedidos");

        //txt = findViewById(R.id.teste);

        listPedidos = findViewById(R.id.listPedidos);

        listPedidos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (clienteLogado!=null) {
                    Intent it = new Intent(PedidosRealizadosActivity.this, ProdutosPedidoActivity.class);
                    it.putExtra("pedido", arrayListPedidos.get(i));
                    it.putExtra("cliente", clienteLogado);
                    it.putExtra("posicao", i);
                    startActivity(it);
                } else {
                    Intent it = new Intent(PedidosRealizadosActivity.this, ProdutosPedidoActivity.class);
                    it.putExtra("pedido", arrayListPedidosCorretos.get(i));
                    it.putExtra("posicao", i);
                    it.putExtra("tipoPedidos", tipoPedidos);
                    startActivity(it);
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        preencheLista();
    }

    public void preencheLista() {

        if (clienteLogado != null) {
            helper = new DBHelper(PedidosRealizadosActivity.this);
            //helper.excluirCarrinho();
            arrayListPedidos = helper.buscarPedidos(String.valueOf(clienteLogado.getCpf()));
            //txt.setText(String.valueOf(arrayListPedidos.size()));
            helper.close();

            if (listPedidos != null) {
                PedidosFeitosAdapter adapter = new PedidosFeitosAdapter(this, R.layout.adapter_pedidos_feitos, arrayListPedidos);
                //this.registerForContextMenu(listClientes);
                listPedidos.setAdapter(adapter);
            } else {
                Toast.makeText(this, "afeeee", Toast.LENGTH_LONG);
            }

        } else {
            helper = new DBHelper(PedidosRealizadosActivity.this);
            arrayListPedidos = helper.buscarPedidosDoFornecedor(String.valueOf(fornecedorLogado.getCpf()));
            //arrayListPedidos = helper.buscarPedidosEmEspera();

            /*if(fornecedorLogado != null) {
                txt.setText("AAAAAAAAAAAAAA");
            }
            if(fornecedorLogado == null) {
                txt.setText("NBBBBBBBBBBBBB");
            }*/
            //txt.setText(String.valueOf(fornecedorLogado.getCpf()));
            helper.close();

            arrayListPedidosCorretos = new ArrayList<Pedido>();

            if(tipoPedidos.equals("aceitos")) {
                for (Pedido p:arrayListPedidos) {
                    if (p.getStatus() == 1) {
                        arrayListPedidosCorretos.add(p);
                    }
                }
            } else {
                for (Pedido p:arrayListPedidos) {
                    if (p.getStatus() == 0) {
                        arrayListPedidosCorretos.add(p);
                        //txt.setText("AAAAAAAAAAAAAA");

                    }
                }
            }
            if (listPedidos != null) {
                PedidosRecebidosAdapter adapter = new PedidosRecebidosAdapter(this, R.layout.adapter_pedidos_feitos, arrayListPedidosCorretos);
                //this.registerForContextMenu(listClientes);
                listPedidos.setAdapter(adapter);
            } else {
                Toast.makeText(this, "afeeee", Toast.LENGTH_LONG);
            }

        }
    }
}