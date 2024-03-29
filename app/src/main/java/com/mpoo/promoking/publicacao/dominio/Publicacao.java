package com.mpoo.promoking.publicacao.dominio;

import com.mpoo.promoking.produto.dominio.Produto;
import java.util.GregorianCalendar;

public class Publicacao {
    private long id;
    private Produto produto;
    private Double preco;
    private String marca;
    private GregorianCalendar validadeProduto;
    private GregorianCalendar validadePublicacao;
    private UnidadeVenda unidadeVenda;

    public Publicacao(){
    }

    public long getId() { return id; }
    public Produto getProduto() {
        return produto;
    }
    public Double getPreco() { return preco; }
    public String getMarca() { return marca; }
    public GregorianCalendar getValidadeProduto() {
        return validadeProduto;
    }
    public GregorianCalendar getValidadePublicacao() {
        return validadePublicacao;
    }
    public UnidadeVenda getUnidadeVenda() {
        return unidadeVenda;
    }

    public void setId(long id) { this.id = id; }
    public void setProduto(Produto produto) { this.produto = produto; }
    public void setPreco(Double preco) { this.preco = preco;}
    public void setMarca(String marca) { this.marca = marca; }
    public void setValidadeProduto(GregorianCalendar validadeProduto) { this.validadeProduto = validadeProduto; }
    public void setValidadePublicacao(GregorianCalendar validadePublicacao) { this.validadePublicacao = validadePublicacao; }
    public void setUnidadeVenda(UnidadeVenda unidadeVenda) { this.unidadeVenda = unidadeVenda; }
}

