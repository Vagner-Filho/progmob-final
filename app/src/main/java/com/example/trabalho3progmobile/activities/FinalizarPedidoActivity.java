package com.example.trabalho3progmobile.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trabalho3progmobile.entities.Pedido;
import com.example.trabalho3progmobile.entities.ProdutosPedido;
import com.example.trabalho3progmobile.entities.Carrinho;
import com.example.trabalho3progmobile.entities.Cliente;
import com.example.trabalho3progmobile.db.DBHelper;
import com.example.trabalho3progmobile.entities.Fornecedor;
import com.example.trabalho3progmobile.R;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class FinalizarPedidoActivity extends AppCompatActivity {

    private Cliente clienteLogado;
    private Fornecedor fornecedor;
    Carrinho carrinho;
    ArrayList<Carrinho> arrayListCarrinho;
    Pedido pedido;
    DBHelper helper;
    TextView precoTxt;
    double preco;
    Button btnConfirmar;
    EditText edtHorario;
    EditText edtData;
    TextView txt1;
    TextView txt2;
    TextView txt3;
    TextView txt4;
    TextView txt5;
    TextView txt6;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalizar_pedido);

        Intent intent = getIntent();
        fornecedor = (Fornecedor) intent.getSerializableExtra("fornecedor");
        clienteLogado = (Cliente) intent.getSerializableExtra("clienteLogado");

        Bundle args = getIntent().getExtras();
        preco = args.getDouble("preco");



        precoTxt = findViewById(R.id.precoTotal);
        btnConfirmar = findViewById(R.id.btnConfirmarPedido);
        edtHorario = findViewById(R.id.edtHorario);
        edtData = findViewById(R.id.edtData);

        txt1 = findViewById(R.id.textView2);
        txt2 = findViewById(R.id.textView3);
        txt3 = findViewById(R.id.textView4);
        txt4 = findViewById(R.id.textView5);
        txt5 = findViewById(R.id.textView6);
        txt6 = findViewById(R.id.textView7);


        precoTxt.setText("Total: R$ "+String.valueOf(preco)+"0");

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                String[] horarioPedido = String.valueOf(edtHorario.getText()).split(":");
                String[] dataPedido = String.valueOf(edtData.getText()).split("/");


                if (edtHorario.getText().toString().length() == 0 ||
                        edtData.getText().toString().length() == 0) {

                    alert("Por favor, preencha todos os campos!");
                } else if (!(edtHorario.getText().toString().length() >= 3 &&
                        edtHorario.getText().toString().length() <= 5) ||
                        edtData.getText().toString().length() != 8) {

                    alert("Preencha corretamente, por favor!");
                    edtHorario.setHint("Ex: 15:30");
                    edtData.setHint("Ex: 31/02/22");
                } else {

                    int horaPedido;
                    int minutoPedido;

                    int anoPedido;
                    int mesPedido;
                    int diaPedido;

                    try {
                        horaPedido = Integer.parseInt(horarioPedido[0]);
                        minutoPedido = Integer.parseInt(horarioPedido[1]);

                        anoPedido = Integer.parseInt(dataPedido[2]);
                        mesPedido = Integer.parseInt(dataPedido[1]);
                        diaPedido = Integer.parseInt(dataPedido[0]);
                    } catch (Exception e){
                        alert("Dados Inválidos! Tente AAAAAAAAAAAAAAAA");
                        edtHorario.setHint("Ex: 15:30");
                        edtData.setHint("Ex: 31/02/22");
                        return;
                    }


                    if (diaPedido <=0) {
                        alert("Dados Inválidos! Tente BBBBBBBBBBBBBB");
                    } else if (diaPedido > 31){
                        alert("Dados Inválidos! Tente CCCCC");
                    } else if (mesPedido <= 0) {
                        alert("Dados Inválidos! Tente DDDDD");
                    } else if (mesPedido > 12) {
                        alert("Dados Inválidos! Tente EEEEE");
                    } else  if (anoPedido != 22 ) {
                        alert("Dados Inválidos! Tente FFFFF");
                    } else if ( mesPedido < mes ){
                        alert("Dados Inválidos! Tente GGGGGG");
                    } else if (diaPedido < dia && mesPedido == mes) {
                        alert("Dados Inválidos! Tente HHHHH");
                    } else if (horaPedido == hora && minutoPedido <= minuto && diaPedido == dia) {
                        alert("Dados Inválidos! Tente IIIIIII");
                    } else if (horaPedido < 8 || horaPedido >= 21) {
                        alert("Dados Inválidos! Tente KJJJJJJJ");
                    } else if (diaPedido == dia && hora > horaPedido) {
                        alert("Dados Inválidos! Tente KJJJJJJJ");
                    } else {
                        alert("Pedido solicitado!");

                        helper = new DBHelper(FinalizarPedidoActivity.this);
                        arrayListCarrinho = helper.buscarCarrinho();
                        pedido = new Pedido();

                        ArrayList<Pedido> arrayListPedidos = helper.buscarPedidos(String.valueOf(clienteLogado.getCpf()));

                        txt1.setText(String.valueOf(arrayListPedidos.size()));
                        txt2.setText(String.valueOf(arrayListCarrinho.size()));
                        txt3.setText(String.valueOf(fornecedor.getCpf()));

                        pedido.setClienteCpf(clienteLogado.getCpf());
                        pedido.setFornecedorCpf(fornecedor.getCpf());
                        pedido.setPreco(preco);
                        pedido.setHorarioRetirada(String.valueOf(edtHorario.getText()));
                        pedido.setDataRetirada(String.valueOf(edtData.getText()));
                        pedido.setHorarioRealizado(String.valueOf(dataTime));
                        pedido.setStatus(0);

                        helper.inserePedido(pedido);
                        helper.close();

                        //helper = new DBHelper(FinalizarPedidoActivity.this);

                        for (Carrinho c : arrayListCarrinho) {
                            txt4.setText(String.valueOf(c.getId_produto()));
                            helper = new DBHelper(FinalizarPedidoActivity.this);
                            ProdutosPedido produtosPedido = new ProdutosPedido();

                            Pedido pedidoFeito;
                            pedidoFeito = helper.buscarPedidoPorHoraPedidoRelizado(pedido.getHorarioRealizado());

                            produtosPedido.setPedidoId(pedidoFeito.getId());
                            produtosPedido.setProdutoId(c.getId_produto());
                            helper.insereProdutosPedido(produtosPedido);
                            helper.close();
                        }

                        helper = new DBHelper(FinalizarPedidoActivity.this);
                        ArrayList<Pedido> arrayListPedidos2 = helper.buscarPedidos(String.valueOf(clienteLogado.getCpf()));
                        ArrayList<ProdutosPedido> pp = helper.buscarProdutosPedido();

                        //txt4.setText(arrayListCarrinho.get(0).getId_produto());

                        txt1.append("   novo =" + String.valueOf(arrayListPedidos2.size()));

                        helper.excluirCarrinho();
                        arrayListCarrinho = helper.buscarCarrinho();
                        txt2.append("   novo =" + String.valueOf(arrayListCarrinho.size()));

                        txt3.setText("   novo =" + String.valueOf(pp.size()));
                        txt6.setText(dtf.toString());
                        txt5.setText(String.valueOf(dataTime));

                        helper.close();
                        finish();
                    }
                }
            }
        });
    }

    private void alert(String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }

}