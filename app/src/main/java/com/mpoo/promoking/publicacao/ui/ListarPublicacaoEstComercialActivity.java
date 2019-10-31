package com.mpoo.promoking.publicacao.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mpoo.promoking.R;

public class ListarPublicacaoEstComercialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_pubicacao_est_comercial);
        FloatingActionButton novaPublicacaoProduto = (FloatingActionButton)findViewById(R.id.novaPublicacaoProdutoButton);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewProdutos);

    }
}
