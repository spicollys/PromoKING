package com.mpoo.promoking.cliente.negocios;

import com.mpoo.promoking.cliente.persistencia.ClienteDAO;
import com.mpoo.promoking.infra.exception.UsuarioJaCadastradoException;
import com.mpoo.promoking.usuario.dominio.Usuario;
import com.mpoo.promoking.usuario.negocios.UsuarioServices;

import java.io.IOException;

public class ClienteServices {

    public Usuario cadastrar(Usuario username) throws IOException, UsuarioJaCadastradoException {
        UsuarioServices dao = new UsuarioServices();
        if (dao.getUsuario(username.getUsername()) != null){
            throw new UsuarioJaCadastradoException("Usuário já cadastrado");
        }
        ClienteDAO daoCliente = new ClienteDAO();
        daoCliente.insertCliente(username);
        return dao.getUsuario(username.getUsername());
    }
}
