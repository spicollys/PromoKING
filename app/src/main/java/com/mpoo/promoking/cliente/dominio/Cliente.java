package com.mpoo.promoking.cliente.dominio;

import com.mpoo.promoking.usuario.dominio.TipoUsuario;
import com.mpoo.promoking.usuario.dominio.Usuario;

public class Cliente {
    private final TipoUsuario tipoUsuario = TipoUsuario.CLIENTE;
    private Usuario usuario;

    public Cliente(Usuario usuario) {
    }

}
