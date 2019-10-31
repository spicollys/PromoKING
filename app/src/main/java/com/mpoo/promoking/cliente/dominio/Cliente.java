package com.mpoo.promoking.cliente.dominio;

import com.mpoo.promoking.usuario.dominio.TipoUsuario;
import com.mpoo.promoking.usuario.dominio.Usuario;

import java.util.ArrayList;

public class Cliente {
    private final TipoUsuario tipoUsuario = TipoUsuario.CLIENTE;
    private Usuario usuario;
    private ArrayList<String> idPublicacoes;

    public Cliente(Usuario usuario) { }

    public TipoUsuario getTipoUsuario() { return tipoUsuario; }

    public Usuario getUsuario() { return usuario; }

    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public ArrayList<String> getArrayListIdPublicacoes() { return idPublicacoes; }

    public void setArrayListIdPublicacoes(ArrayList<String> idPublicacoes) { this.idPublicacoes = idPublicacoes; }
}
