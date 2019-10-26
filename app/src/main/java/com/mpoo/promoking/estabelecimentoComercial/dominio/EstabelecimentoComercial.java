package com.mpoo.promoking.estabelecimentoComercial.dominio;

import com.mpoo.promoking.usuario.dominio.TipoUsuario;
import com.mpoo.promoking.usuario.dominio.Usuario;

public class EstabelecimentoComercial {
    private final TipoUsuario tipoUsuario = TipoUsuario.ESTABELECIMENTO_COMERCIAL;
    private Usuario usuario;

    public EstabelecimentoComercial(Usuario usuario) {
    }
}
