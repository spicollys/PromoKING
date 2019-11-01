package com.mpoo.promoking.publicacao.ui;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mpoo.promoking.R;

public class ViewHolderPublicacaoEstComercial extends RecyclerView.ViewHolder {
    final TextView tipoProduto;
    final TextView marcaProduto;
    final TextView preco;
    final TextView unidadeVenda;
    final View editPub;
    final View deletePub;

    public ViewHolderPublicacaoEstComercial(@NonNull View view) {
        super(view);
        tipoProduto = (TextView) view.findViewById(R.id.produtoTipoCardEstComercial);
        marcaProduto = (TextView) view.findViewById(R.id.produtoMarcaCardEstComercial);
        preco = (TextView) view.findViewById(R.id.produtoPrecoCardEstComercial);
        unidadeVenda = (TextView) view.findViewById(R.id.produtoUnCardEstComercial);
        editPub = (View) view.findViewById(R.id.editPubCardEstComercial);
        deletePub = (View) view.findViewById(R.id.deletePubCardEstComercial);
    }
}
