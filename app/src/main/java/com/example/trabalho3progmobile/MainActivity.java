package com.example.trabalho3progmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DBHelper helper = new DBHelper(this);
    private EditText edtEmail;
    private EditText edtSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtEmail=findViewById(R.id.edtEmail);
        edtSenha=findViewById(R.id.edtSenha);
    }

    public void entrar(View view) {
        String email = edtEmail.getText().toString();
        String senha = edtSenha.getText().toString();
        String password=helper.buscarSenha(email);
        if(senha.equals(password)){

            Intent intent= new Intent(this, ClienteLogado.class);
            Cliente cliente = new Cliente();
            cliente = helper.buscarClientePorEmail(email);
            intent.putExtra("cpf", cliente.getCpf());
            startActivity(intent);
        }
        else{
            Toast toast = Toast.makeText(MainActivity.this,
                    "Usuário ou senha inválido",Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void cadastrar(View view) {
        Intent it = new Intent(this, FormCliente.class);
        startActivity(it);
    }
}