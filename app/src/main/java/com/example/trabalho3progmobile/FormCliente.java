package com.example.trabalho3progmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FormCliente extends AppCompatActivity {
    DBHelper helper = new DBHelper(this);
    private EditText edtNome;
    private EditText edtSobrenome;
    private EditText edtCpf;
    private EditText edtEmail;
    private EditText edtSenha;
    private EditText edtConfSenha;
    private Button btnSalvar;
    private Cliente cliente;
    private Cliente clienteAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cliente);

        edtNome = findViewById(R.id.edtNome);
        edtSobrenome = findViewById(R.id.edtSobrenome);
        edtCpf = findViewById(R.id.edtCpf);
        edtEmail = findViewById(R.id.edtEmailForm);
        edtSenha = findViewById(R.id.edtSenhaForm);
        edtConfSenha = findViewById(R.id.edtConfirmarSenha);
        btnSalvar = findViewById(R.id.btnSalvar);

        Intent intent = getIntent();
        clienteAtual = (Cliente) intent.getSerializableExtra("infos");
        cliente = new Cliente();

        if(clienteAtual != null){
            btnSalvar.setText("ALTERAR");
            edtNome.setText(clienteAtual.getNome());
            edtSobrenome.setText(clienteAtual.getSobrenome());
            edtCpf.setText(String.valueOf(clienteAtual.getCpf()));
            edtEmail.setText(clienteAtual.getEmail());
            edtSenha.setText(clienteAtual.getSenha());
            edtConfSenha.setText(clienteAtual.getSenha());
        }else{
            btnSalvar.setText("SALVAR");
        }

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (edtNome.getText().toString().length() == 0 ||
                   edtSobrenome.getText().toString().length() == 0 ||
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

        String nome = edtNome.getText().toString();
        String sobrenome = edtSobrenome.getText().toString();
        int cpf;
        try {
            cpf = Integer.parseInt(edtCpf.getText().toString());
        } catch(Exception e){
            alert("CPF inválido.");
            return;
        }
        if (helper.buscarClientePorCpfBoolean(cpf)) {
            if (clienteAtual != null && cpf == clienteAtual.getCpf()) {

            } else {
                alert("Esse cpf já está cadastrado!");
                return;
            }
        }

        String email = edtEmail.getText().toString();

        if (helper.buscarClientePorEmailBoolean(email)) {
            if (clienteAtual != null && email.equals(clienteAtual.getEmail())) {

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
            cliente.setNome(nome);
            cliente.setSobrenome(sobrenome);
            cliente.setCpf(cpf);
            cliente.setEmail(email);
            cliente.setSenha(senha);
            if (btnSalvar.getText().toString().equals("SALVAR")) {
                helper.insereCliente(cliente);
                alert("Cadastro realizado com sucesso!");
                helper.close();
            } else{
                helper.atualizarCliente(cliente, clienteAtual);
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