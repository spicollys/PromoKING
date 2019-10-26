package com.mpoo.promoking.estabelecimentoComercial.negocios;

import com.mpoo.promoking.estabelecimentoComercial.persistencia.EstabelecimentoComercialDAO;
import com.mpoo.promoking.infra.exception.UsuarioJaCadastradoException;
import com.mpoo.promoking.usuario.dominio.Usuario;
import com.mpoo.promoking.usuario.negocios.UsuarioServices;

import java.io.IOException;

public class EstabelecimentoComercialServices {
    public Usuario cadastrar (Usuario username) throws IOException, UsuarioJaCadastradoException {
        UsuarioServices dao = new UsuarioServices();
        if (dao.getUsuario(username.getUsername()) != null){
            throw new UsuarioJaCadastradoException("Usuário já cadastrado");
        }
        EstabelecimentoComercialDAO daoEstabelecimentoComercial = new EstabelecimentoComercialDAO();
        daoEstabelecimentoComercial.insertEstabelecimentoComercial(username);
        return dao.getUsuario(username.getUsername());
    }
}
