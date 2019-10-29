package com.mpoo.promoking.publicacao.dominio;

import com.mpoo.promoking.produto.dominio.Produto;
import java.util.Date;
import com.mpoo.promoking.publicacao.dominio.UnidadeVenda;

public class Publicacao {
    private final Produto produto;
    private final Date validadeProduto;
    private final Date validadePublicacao;
    private final UnidadeVenda unidadeVenda;

    public Publicacao(Produto produto, Date validadeProduto, Date validadePublicacao, UnidadeVenda unidadeVenda) {
        this.produto = produto;
        this.validadeProduto = validadeProduto;
        this.validadePublicacao = validadePublicacao;
        this.unidadeVenda = unidadeVenda;
    }
    public Produto getProduto() {
        return produto;
    }
    public Date getValidadeProduto() {
        return validadeProduto;
    }
    public Date getValidadePublicacao() {
        return validadePublicacao;
    }
    public UnidadeVenda getUnidadeVenda() {
        return unidadeVenda;
    }
}
