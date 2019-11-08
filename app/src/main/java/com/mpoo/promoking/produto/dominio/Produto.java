package com.mpoo.promoking.produto.dominio;

public class Produto {
    private String produto;
    private String marcas;

    public Produto() {
    }

    public String getProduto() {
        return produto;
    }
    public String getMarcas() {
        return marcas;
    }
    public void setProduto(String produto) {
        this.produto = produto;
    }

    public void setMarcas(String marcas) {
        this.marcas = marcas;
    }
}
