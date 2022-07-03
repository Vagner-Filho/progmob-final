package com.example.trabalho3progmobile.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trabalho3progmobile.R;
import com.example.trabalho3progmobile.adapters.ProdutosPedidoAdapter;
import com.example.trabalho3progmobile.db.DBHelper;
import com.example.trabalho3progmobile.entities.Cliente;
import com.example.trabalho3progmobile.entities.Fornecedor;
import com.example.trabalho3progmobile.entities.Pedido;
import com.example.trabalho3progmobile.entities.Produto;
import com.example.trabalho3progmobile.entities.ProdutosPedido;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ProdutosPedidoActivity extends AppCompatActivity {
    private Cliente clienteLogado;
    private Fornecedor fornecedor;

    private Pedido pedido;
    int cpf;
    Button btnCancelar;
    Button btnRecusar;
    LinearLayout botoes;
    ListView listProdutos;
    DBHelper helper = new DBHelper(ProdutosPedidoActivity.this);
    ArrayList<Pedido> arrayListPedidos;
    ArrayList<Produto> arrayListProdutos = new ArrayList<Produto>();
    ArrayList<ProdutosPedido> arrayListProdutosPedido;
    TextView txtPedido;
    TextView txtTotal;

    TextView txtretirada;
    TextView txthorarioRealizado;
    TextView txtstatus;
    TextView txtfornecedor;
    String tipoPedidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos_pedido);

        Intent it=getIntent();
        pedido = (Pedido) it.getSerializableExtra("pedido");
        clienteLogado = (Cliente) it.getSerializableExtra("cliente");
        int posicao = (int) it.getSerializableExtra("posicao");
        tipoPedidos = (String) it.getSerializableExtra("tipoPedidos");

        txtPedido = findViewById(R.id.pedido);
        txtTotal = findViewById(R.id.total);
        txtPedido.append(" "+String.valueOf(posicao+1));
        txtTotal.append("R$ "+String.valueOf(pedido.getPreco())+"0");

        txtretirada = findViewById(R.id.retirada);
        txthorarioRealizado = findViewById(R.id.pedidoRealizado);
        txtstatus = findViewById(R.id.status);
        txtfornecedor = findViewById(R.id.fornecedor);
        btnCancelar = findViewById(R.id.cancelarPedido);
        btnRecusar = findViewById(R.id.btnRecusar);
        botoes = findViewById(R.id.botoes);

        /*if(pedido.getStatus() == -1) {
            btnCancelar.setVisibility(View.INVISIBLE);
        }*/

        btnCancelar.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if(clienteLogado!=null) {
                    alert("uiiiiiiiiiiiiiiiiiiiiii!");


                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yy/MM/dd HH:mm:ss");

                    String dataTime = dtf.format(LocalDateTime.now());
                    String[] dataHora = dataTime.split(" ");
                    String[] data = dataHora[0].split("/");
                    String[] horario = dataHora[1].split(":");

                    int ano = Integer.parseInt(data[0]);
                    int mes = Integer.parseInt(data[1]);
                    int dia = Integer.parseInt(data[2]);
                    int hora = Integer.parseInt(horario[0]);
                    int minuto = Integer.parseInt(horario[1]);

                    String[] dataPedido = pedido.getDataRetirada().split("/");
                    String[] horarioPedido = pedido.getHorarioRetirada().split(":");


                    int mesPedido = Integer.parseInt(data[1]);
                    int diaPedido = Integer.parseInt(data[0]);
                    int horaPedido = Integer.parseInt(horario[0]);
                    int minutoPedido = Integer.parseInt(horario[1]);


                    if (pedido.getStatus() == 0 || pedido.getStatus() == -1) {
                        helper.excluirPedido(pedido.getId());
                        alert("Pedido Cancelado com Sucesso!");
                        finish();
                    } else if (pedido.getStatus() == 1) {
                        if (dia == diaPedido) {
                            if (hora == horaPedido) {
                                if (minutoPedido - minuto < 30) {
                                    alert("Cancelamento recusado! Faltam menos de 30 minutos para a retirada do pedido!");
                                    return;
                                }
                            }
                            if (horaPedido - hora == 1) {
                                minutoPedido += 60;
                                if (minutoPedido - minuto <= 30) {
                                    alert("Cancelamento recusado! Faltam menos de 30 minutos para a retirada do pedido!");
                                    return;
                                }
                            }
                        }
                        helper.excluirPedido(pedido.getId());
                        alert("Pedido Cancelado com Sucesso!");
                        finish();
                    }
                } else {
                    boolean existe;
                    existe = helper.existemUnidadesSuficientes(pedido);
                    alert(String.valueOf(existe));
                    if(existe) {
                        //helper.aceitarPedido(pedido);
                        alert("Pedido Aceito com Sucesso!");

                        Intent intent=new Intent(ProdutosPedidoActivity.this,PedidosRealizadosActivity.class);
                        /*PendingIntent pendingIntent= PendingIntent.getActivity(
                                ProdutosPedidoActivity.this,0,it,0);*/
                        String CHANNEL_ID="com.example.trabalho3progmobile";
                        String CHANNEL_NAME="CHANNEL_NAME_APP_NOTIFICATION";
                        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                            NotificationChannel channel=new NotificationChannel(CHANNEL_ID,
                                    CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
                            final  NotificationManager notificationManager=(NotificationManager)
                                    ProdutosPedidoActivity.this.getSystemService(Context.NOTIFICATION_SERVICE);
                            notificationManager.createNotificationChannel(channel);
                        }
                        NotificationCompat.Builder mBuilder=new NotificationCompat.Builder(
                                ProdutosPedidoActivity.this, CHANNEL_ID).
                                setSmallIcon(android.R.drawable.stat_notify_more).
                                setContentTitle("Pedido Aceito").
                                setContentText("Cliente "+pedido.getClienteCpf()).
                                setPriority(NotificationCompat.PRIORITY_DEFAULT)/*.
                                setContentIntent(pendingIntent).setAutoCancel(true)*/;
                        NotificationManagerCompat notif=NotificationManagerCompat.from(ProdutosPedidoActivity.this);
                        notif.notify(0,mBuilder.build());


                        finish();
                    } if(!existe){
                        alert("Você não possui unidades suficientes para aceitar esse pedido!");
                    }
                }
            }
        });

        btnRecusar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helper.recusarPedido(pedido.getId());
                alert("Pedido Recusado com Sucesso!");
                finish();
            }
        });


        String[] dataHora = pedido.getHorarioRealizado().split(" ");
        String[] data = dataHora[0].split("/");

        txtretirada.setText("Retirar as "+pedido.getHorarioRetirada()+" do dia "+pedido.getDataRetirada());
        txthorarioRealizado.setText("Pedido feito em "+data[2]+"/"+data[1]+"/"+data[0]+" "+dataHora[1]);

        if(pedido.getStatus() == 0)  {
            txtstatus.setText("Status: Em espera");
        } else if (pedido.getStatus() == 1) {
            txtstatus.setText("Status: Pedido Aprovado");
        } else
            txtstatus.setText("Status: Pedido Negado");

        helper = new DBHelper(ProdutosPedidoActivity.this);

        if (clienteLogado!=null) {
            Fornecedor fornecedor = helper.buscarFornecedorPorCpf(pedido.getFornecedorCpf());
            txtfornecedor.setText("Fornecedor: " + fornecedor.getNome());
        } else {
            Cliente cliente = helper.buscarClientePorCpf(pedido.getClienteCpf());
            txtfornecedor.setText("Cliente: " + cliente.getNome());
            btnCancelar.setText("ACEITAR");
            btnRecusar.setVisibility(View.VISIBLE);
            botoes.setOrientation(LinearLayout.HORIZONTAL);
        }


        if(tipoPedidos.equals("aceitos")) {
            btnCancelar.setVisibility(View.INVISIBLE);
            btnRecusar.setVisibility(View.INVISIBLE);

        }
        helper.close();


        listProdutos = findViewById(R.id.listProdutos);

        helper = new DBHelper(ProdutosPedidoActivity.this);
        //helper.excluirCarrinho();
        arrayListProdutosPedido = helper.buscarProdutosPedidoPorId(pedido.getId());
        helper.close();
        //arrayListProdutos = helper.buscarProdutosGeral();
        //txt.setText(String.valueOf(arrayListProdutosPedido.get(0).getProdutoId()));

        /*Produto produto = new Produto();
        helper = new DBHelper(ProdutosPedidoActivity.this);
        produto = helper.buscarProdutoPorId(String.valueOf(arrayListProdutosPedido.get(0).getProdutoId()));
        helper.close();
        txt.setText(String.valueOf(produto.getNome()));*/


        for(ProdutosPedido pp:arrayListProdutosPedido) {
            Produto produto = new Produto();
            helper = new DBHelper(ProdutosPedidoActivity.this);
            produto = helper.buscarProdutoPorId(String.valueOf(pp.getProdutoId()));
            //txt.setText(String.valueOf(produto.getDescricao()));
            arrayListProdutos.add(produto);
            helper.close();
        }

        if (arrayListProdutos != null) {
            ProdutosPedidoAdapter adapter = new ProdutosPedidoAdapter(this, R.layout.adapter_carrinho, arrayListProdutos);
            //this.registerForContextMenu(listClientes);
            listProdutos.setAdapter(adapter);
        } else {
            alert("afeeee");
        }
    }

    private void alert(String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }
}