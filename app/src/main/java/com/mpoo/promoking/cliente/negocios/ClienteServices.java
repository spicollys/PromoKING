package com.mpoo.promoking.cliente.negocios;

import com.mpoo.promoking.cliente.persistencia.ClienteDAO;
import com.mpoo.promoking.infra.exception.UsuarioJaCadastradoException;
import com.mpoo.promoking.usuario.dominio.TipoUsuario;
import com.mpoo.promoking.usuario.dominio.Usuario;
import com.mpoo.promoking.usuario.negocios.UsuarioServices;

import java.io.IOException;

public class ClienteServices {

    public Usuario cadastrar(Usuario usuario) throws IOException, UsuarioJaCadastradoException {
        UsuarioServices usuarioServices = new UsuarioServices();
        if (usuarioServices.getUsuario(usuario.getUsername(), TipoUsuario.CLIENTE) != null){
            throw new UsuarioJaCadastradoException("Usuário já cadastrado");
        }
        ClienteDAO daoCliente = new ClienteDAO();
        daoCliente.insert(usuario);
        return usuarioServices.getUsuario(usuario.getUsername(), TipoUsuario.CLIENTE);
    }

    public void atualizarDados(Usuario usuario) throws IOException {
        ClienteDAO clienteDAO = new ClienteDAO();
        clienteDAO.update(usuario);
    }
}