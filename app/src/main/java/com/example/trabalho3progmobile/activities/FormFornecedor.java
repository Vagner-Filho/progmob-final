package com.example.trabalho3progmobile.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.trabalho3progmobile.R;
import com.example.trabalho3progmobile.db.DBHelper;
import com.example.trabalho3progmobile.entities.Fornecedor;

public class FormFornecedor extends AppCompatActivity {

    DBHelper helper = new DBHelper(this);
    private EditText edtNome;
    private EditText edtNomeLoja;
    private EditText edtSobrenome;
    private EditText edtLocalizacao;
    private EditText edtCpf;
    private EditText edtEmail;
    private EditText edtSenha;
    private EditText edtConfSenha;
    private Button btnSalvar;
    private Fornecedor fornecedor;
    private Fornecedor fornecedorAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_fornecedor);

        edtNomeLoja = findViewById(R.id.edtNomeLoja);
        edtNome = findViewById(R.id.edtNome);
        edtSobrenome = findViewById(R.id.edtSobrenome);
        edtLocalizacao = findViewById(R.id.edtLocalizacao);
        edtCpf = findViewById(R.id.edtCpf);
        edtEmail = findViewById(R.id.edtEmailForm);
        edtSenha = findViewById(R.id.edtSenhaForm);
        edtConfSenha = findViewById(R.id.edtConfirmarSenha);
        btnSalvar = findViewById(R.id.btnSalvar);

        Intent intent = getIntent();
        fornecedorAtual = (Fornecedor) intent.getSerializableExtra("infos");
        fornecedor = new Fornecedor();

        if(fornecedorAtual != null){
            btnSalvar.setText("ALTERAR");
            edtNomeLoja.setText(fornecedorAtual.getNomeLoja());
            edtNome.setText(fornecedorAtual.getNome());
            edtSobrenome.setText(fornecedorAtual.getSobrenome());
            edtLocalizacao.setText(fornecedorAtual.getLocalizacao());
            edtCpf.setText(String.valueOf(fornecedorAtual.getCpf()));
            edtEmail.setText(fornecedorAtual.getEmail());
            edtSenha.setText(fornecedorAtual.getSenha());
            edtConfSenha.setText(fornecedorAtual.getSenha());
        }else{
            btnSalvar.setText("SALVAR");
        }

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtNomeLoja.getText().toString().length() == 0 ||
                        edtNome.getText().toString().length() == 0 ||
                        edtSobrenome.getText().toString().length() == 0 ||
                        edtLocalizacao.getText().toString().length() == 0 ||
                        edtCpf.getText().toString().length() == 0 ||
                        edtEmail.getText().toString().length() == 0 ||
                        edtSenha.getText().toString().length() == 0 ||
                        edtConfSenha.getText().toString().length() == 0) {

                    alert("Por favor, preencha todos os campos!");
                } else {
                    salvar();
                }
            }
        });
    }

    public void salvar() {
        String nomeLoja = edtNomeLoja.getText().toString();

        String nome = edtNome.getText().toString();
        String sobrenome = edtSobrenome.getText().toString();

        String localizacao = edtLocalizacao.getText().toString();

        int cpf;
        try {
            cpf = Integer.parseInt(edtCpf.getText().toString());
        } catch(Exception e){
            alert("CPF inválido.");
            return;
        }
        if (helper.buscarFornecedorPorCpfBoolean(cpf)) {
            if (fornecedorAtual != null && cpf == fornecedorAtual.getCpf()) {

            } else {
                alert("Esse cpf já está cadastrado!");
                return;
            }
        }

        String email = edtEmail.getText().toString();

        if (helper.buscarFornecedorPorEmailBoolean(email)) {
            if (fornecedorAtual != null && email.equals(fornecedorAtual.getEmail())) {

            }
            else {
                alert("Esse email já está cadastrado!");
                return;
            }
        }
        String senha = edtSenha.getText().toString();
        String confSenha = edtConfSenha.getText().toString();

        if (!senha.equals(confSenha)) {
            alert("Senha diferente da confirmação de senha!");
            edtSenha.setText("");
            edtConfSenha.setText("");
        } else {
            fornecedor.setNomeLoja(nomeLoja);
            fornecedor.setNome(nome);
            fornecedor.setSobrenome(sobrenome);
            fornecedor.setLocalizacao(localizacao);
            fornecedor.setCpf(cpf);
            fornecedor.setEmail(email);
            fornecedor.setSenha(senha);
            if (btnSalvar.getText().toString().equals("SALVAR")) {
                helper.insereFornecedor(fornecedor);
                alert("Cadastro realizado com sucesso!");
                helper.close();
            } else{
                helper.atualizarFornecedor(fornecedor, fornecedorAtual);
                helper.close();
            }

            limpar();
            finish();
        }
    }

    public void limpar(){
        edtNome.setText("");
        edtSobrenome.setText("");
        edtCpf.setText("");
        edtEmail.setText("");
        edtSenha.setText("");
        edtConfSenha.setText("");
    }

    private void alert(String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }

}