package com.mpoo.promoking.estabelecimentoComercial.dominio;

import com.mpoo.promoking.usuario.dominio.TipoUsuario;
import com.mpoo.promoking.usuario.dominio.Usuario;

import java.util.ArrayList;
import java.util.List;

public class EstabelecimentoComercial {
    private final TipoUsuario tipoUsuario = TipoUsuario.ESTABELECIMENTO_COMERCIAL;
    private Usuario usuario;
    private ArrayList<String> idPublicacoes;

    public EstabelecimentoComercial(Usuario usuario) { }

    public TipoUsuario getTipoUsuario() { return tipoUsuario; }

    public Usuario getUsuario() { return usuario; }

    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public ArrayList<String> getArrayListIdPublicacoes() { return idPublicacoes; }

    public void setArrayListIdPublicacoes(ArrayList<String> idPublicacoes) { this.idPublicacoes = idPublicacoes; }

}
