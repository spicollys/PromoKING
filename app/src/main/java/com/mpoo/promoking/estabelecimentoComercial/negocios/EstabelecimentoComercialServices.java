package com.mpoo.promoking.estabelecimentoComercial.negocios;

import com.mpoo.promoking.cliente.persistencia.ClienteDAO;
import com.mpoo.promoking.estabelecimentoComercial.persistencia.EstabelecimentoComercialDAO;
import com.mpoo.promoking.infra.exception.UsuarioJaCadastradoException;
import com.mpoo.promoking.usuario.dominio.TipoUsuario;
import com.mpoo.promoking.usuario.dominio.Usuario;
import com.mpoo.promoking.usuario.negocios.UsuarioServices;

import java.io.IOException;

public class EstabelecimentoComercialServices {
    public Usuario cadastrar (Usuario usuario) throws IOException, UsuarioJaCadastradoException {
        UsuarioServices usuarioServices = new UsuarioServices();
        if (usuarioServices.getUsuario(usuario.getUsername(), TipoUsuario.ESTABELECIMENTO_COMERCIAL) != null){
            throw new UsuarioJaCadastradoException("Usuário já cadastrado");
        }
        EstabelecimentoComercialDAO daoEstabelecimentoComercial = new EstabelecimentoComercialDAO();
        daoEstabelecimentoComercial.insert(usuario);
        return usuarioServices.getUsuario(usuario.getUsername(), TipoUsuario.ESTABELECIMENTO_COMERCIAL);
    }
    public void atualizarDados(Usuario usuario) throws IOException {
        EstabelecimentoComercialDAO estabelecimentoComercialDAO = new EstabelecimentoComercialDAO();
        estabelecimentoComercialDAO.update(usuario);
    }

}
