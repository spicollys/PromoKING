package com.mpoo.promoking.login.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.mpoo.promoking.R;
import com.mpoo.promoking.cadastro.ui.CadastroActivity;
import android.content.Intent;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void abrirCadastro(View view){
        Intent i = new Intent(LoginActivity.this, CadastroActivity.class);
        startActivity(i); //esse método redireciona a tela de login para a de cadastro após o apertar o botão "cadastre-se"

    }
}
