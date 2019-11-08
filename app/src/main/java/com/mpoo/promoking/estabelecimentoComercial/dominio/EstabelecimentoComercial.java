package com.mpoo.promoking.estabelecimentoComercial.dominio;

import com.mpoo.promoking.usuario.dominio.TipoUsuario;
import com.mpoo.promoking.usuario.dominio.Usuario;

import java.util.ArrayList;
import java.util.List;

public class EstabelecimentoComercial {
    private final TipoUsuario tipoUsuario = TipoUsuario.ESTABELECIMENTO_COMERCIAL;
    private Usuario usuario;

    public EstabelecimentoComercial(Usuario usuario) { }

    public TipoUsuario getTipoUsuario() { return tipoUsuario; }

    public Usuario getUsuario() { return usuario; }

    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

}
