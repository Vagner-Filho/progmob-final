package com.example.trabalho3progmobile.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.trabalho3progmobile.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loginCliente(View view) {
        Intent intent= new Intent(this, LoginActivity.class);
        intent.putExtra("tipo", "cliente");
        startActivity(intent);
    }

    public void loginFornecedor(View view) {
        Intent it = new Intent(this, LoginActivity.class);
        it.putExtra("tipo", "fornecedor");
        startActivity(it);
    }
}