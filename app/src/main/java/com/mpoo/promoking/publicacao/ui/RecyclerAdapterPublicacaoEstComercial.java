package com.mpoo.promoking.publicacao.ui;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mpoo.promoking.R;
import com.mpoo.promoking.publicacao.dominio.Publicacao;
import org.apache.commons.lang3.StringUtils;
import java.util.List;

public class RecyclerAdapterPublicacaoEstComercial extends RecyclerView.Adapter {
    private List<Publicacao> listaPublicacoes;
    private Context context;

    public RecyclerAdapterPublicacaoEstComercial(List<Publicacao> listaPublicacoes, Context context){
        this.listaPublicacoes = listaPublicacoes;
        this.context = context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_produto_card, parent, false);
        ViewHolderPublicacaoEstComercial holder = new ViewHolderPublicacaoEstComercial(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ViewHolderPublicacaoEstComercial holder = (ViewHolderPublicacaoEstComercial) viewHolder;
        Publicacao publicacao = listaPublicacoes.get(position);
        holder.tipoProduto.setText(StringUtils.capitalize((publicacao.getProduto().getTipo().replaceAll("_", " "))));
        holder.marcaProduto.setText(StringUtils.capitalize(publicacao.getMarca().replaceAll("_", " ")));
        holder.preco.setText(publicacao.getPreco().toString());
        holder.unidadeVenda.setText(StringUtils.capitalize(publicacao.getUnidadeVenda().toString().replaceAll("_", " ")));
    }

    @Override
    public int getItemCount() {
        return listaPublicacoes.size();
    }
}
