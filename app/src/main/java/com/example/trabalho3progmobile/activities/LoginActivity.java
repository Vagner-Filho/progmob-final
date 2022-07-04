package com.example.trabalho3progmobile.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trabalho3progmobile.entities.Cliente;
import com.example.trabalho3progmobile.db.DBHelper;
import com.example.trabalho3progmobile.R;
import com.example.trabalho3progmobile.entities.Fornecedor;

public class LoginActivity extends AppCompatActivity {

    DBHelper helper = new DBHelper(this);
    private EditText edtEmail;
    private EditText edtSenha;
    String tipo;

    TextView t1;
    TextView t2;
    TextView t3;
    TextView t4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent intent = getIntent();

        Bundle args = getIntent().getExtras();
        tipo = args.getString("tipo");

        edtEmail=findViewById(R.id.edtEmail);
        edtSenha=findViewById(R.id.edtSenha);
    }

    public void entrar(View view) {
        String email = edtEmail.getText().toString();
        String senha = edtSenha.getText().toString();

        if (tipo.equals("cliente")) {
            String password=helper.buscarSenhaCliente(email);
            if(senha.equals(password)){

                Intent intent= new Intent(this, ClienteLogado.class);
                Cliente cliente = new Cliente();
                cliente = helper.buscarClientePorEmail(email);
                intent.putExtra("infos", cliente);
                startActivity(intent);
            }
            else{
                Toast toast = Toast.makeText(LoginActivity.this,
                        "Usuário ou senha inválido",Toast.LENGTH_LONG);
                toast.show();
            }
        } else if (tipo.equals("fornecedor")) {
            String password=helper.buscarSenhaFornecedor(email);

            /*t1.setText(email);
            t2.setText(senha);
            t3.setText(password);
            t4.setText(String.valueOf(senha.equals(password)));*/
            if(senha.equals(password)){

                Intent intent= new Intent(this, FornecedorLogado.class);
                Fornecedor fornecedor = new Fornecedor();
                fornecedor = helper.buscarFornecedorPorEmail(email);
                intent.putExtra("infos", fornecedor);
                startActivity(intent);
            }
            else{
                Toast toast = Toast.makeText(LoginActivity.this,
                        "Usuário ou senha inválido",Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

    public void cadastrar(View view) {
        if (tipo.equals("cliente")) {

            Intent it = new Intent(this, FormCliente.class);
            startActivity(it);

        } else if (tipo.equals("fornecedor")) {

            Intent it = new Intent(this, FormFornecedor.class);
            startActivity(it);
        }
    }
}