package com.mpoo.promoking.admin.dominio;

import com.mpoo.promoking.usuario.dominio.TipoUsuario;
import com.mpoo.promoking.usuario.dominio.Usuario;

class Admin {
    private final TipoUsuario tipoUsuario = TipoUsuario.ADMIN;
    private Usuario usuario;

    public Admin(Usuario usuario) {
    }
}
