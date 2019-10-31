package com.mpoo.promoking.produto.dominio;

public class Produto {
    private String tipo;
    private String marcas;

    public Produto() {
    }

    public String getTipo() {
        return tipo;
    }
    public String getMarcas() {
        return marcas;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setMarcas(String marcas) {
        this.marcas = marcas;
    }
}
