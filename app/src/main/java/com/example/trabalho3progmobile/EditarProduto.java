package com.example.trabalho3progmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trabalho3progmobile.activities.PedidosRealizadosActivity;
import com.example.trabalho3progmobile.db.DBHelper;
import com.example.trabalho3progmobile.entities.Fornecedor;
import com.example.trabalho3progmobile.entities.Pedido;
import com.example.trabalho3progmobile.entities.Produto;

public class EditarProduto extends AppCompatActivity {

    DBHelper helper = new DBHelper(this);
    EditText edtNome;
    EditText edtDesc;
    EditText edtQuant;
    EditText edtPreco;
    Button btnSalvar;
    Produto produto;
    Fornecedor fornecedor;
    Button btnExcluir;
    LinearLayout botoes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_produto);

        Intent intent = getIntent();
        produto = (Produto) intent.getSerializableExtra("infos");
        fornecedor = (Fornecedor) intent.getSerializableExtra("fornecedor");


        edtNome = findViewById(R.id.nome);
        edtDesc = findViewById(R.id.descricao);
        edtQuant = findViewById(R.id.quantidade);
        edtPreco = findViewById(R.id.preco);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnExcluir = findViewById(R.id.btnExcluir);
        botoes = findViewById(R.id.botoes);



        if (produto != null) {
            edtNome.setText(produto.getNome());
            edtDesc.setText(produto.getDescricao());
            edtQuant.setText(String.valueOf(produto.getQuantidade()));
            edtPreco.setText(String.valueOf(produto.getPreco()));
            btnSalvar.setText("ALTERAR");
        } else {
            btnSalvar.setText("SALVAR");
            btnExcluir.setVisibility(View.INVISIBLE);
            botoes.setOrientation(LinearLayout.VERTICAL);
        }


        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int quantidade;
                double preco;

                try {
                    quantidade = Integer.parseInt(String.valueOf(edtQuant.getText()));
                } catch (Exception e) {
                    alert("Quantidade Inválida!");
                    return;
                }

                try {
                    preco = Double.parseDouble(String.valueOf(edtQuant.getText()));
                } catch (Exception e) {
                    alert("Preco Inválido!");
                    return;
                }

                Produto produtoNovo = new Produto();
                produtoNovo.setNome(String.valueOf(edtNome.getText()));
                produtoNovo.setDescricao(String.valueOf(edtDesc.getText()));
                produtoNovo.setPreco(Double.parseDouble(String.valueOf(edtPreco.getText())));
                produtoNovo.setQuantidade(Integer.parseInt(String.valueOf(edtQuant.getText())));

                if (produto != null) {
                    produtoNovo.setId(Integer.parseInt(String.valueOf(produto.getId())));
                    helper.atualizarProduto(produtoNovo);
                    helper.close();
                    alert("Produto Alterado com Sucesso!");

                } else {
                    produtoNovo.setFornecedorCpf(fornecedor.getCpf());
                    helper.insereProduto(produtoNovo);
                    helper.close();
                    alert("Produto Criado com Sucesso!");
                }

                limpar();
                finish();
            }
        });

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helper.excluirProduto(produto.getId());
                helper.close();
                alert("Produto Excluido com Sucesso!");
                finish();
            }
        });
    }

    public void limpar(){
        edtNome.setText("");
        edtDesc.setText("");
        edtPreco.setText("");
        edtQuant.setText("");
    }

    private void alert(String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }

}