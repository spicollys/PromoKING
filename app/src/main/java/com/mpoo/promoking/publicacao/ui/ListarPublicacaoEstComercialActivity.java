package com.mpoo.promoking.publicacao.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mpoo.promoking.R;
import com.mpoo.promoking.publicacao.dominio.Publicacao;
import com.mpoo.promoking.publicacao.negocio.PublicacaoServices;

import java.io.IOException;
import java.util.List;

public class ListarPublicacaoEstComercialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_pubicacao_est_comercial);
        FloatingActionButton novaPublicacaoProduto = (FloatingActionButton)findViewById(R.id.novaPublicacaoProdutoButton);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewProdutos);
        PublicacaoServices publicacaoServices = new PublicacaoServices();
        List<Publicacao> listaPublicacoes = null;
        try {
            listaPublicacoes = publicacaoServices.retornarListaObjetosPublicacoes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        recyclerView.setAdapter(new RecyclerAdapterPublicacaoEstComercial(listaPublicacoes, this));
        RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layout);
    }
}
