package com.mpoo.promoking.usuario.negocios;

import com.mpoo.promoking.cliente.persistencia.ClienteDAO;
import com.mpoo.promoking.estabelecimentoComercial.persistencia.EstabelecimentoComercialDAO;
import com.mpoo.promoking.infra.Sessao;
import com.mpoo.promoking.infra.exception.UsuarioNaoCadastradoException;
import com.mpoo.promoking.infra.persistencia.AbstractSQLite;
import com.mpoo.promoking.usuario.dominio.TipoUsuario;
import com.mpoo.promoking.usuario.dominio.Usuario;

import java.io.IOException;

public class UsuarioServices extends AbstractSQLite{


    public Usuario getUsuario(String username, TipoUsuario idTipoUsuario) throws IOException {
        Usuario result = null;
        if(idTipoUsuario.equals(TipoUsuario.CLIENTE)){
            ClienteDAO clienteDAO = new ClienteDAO();
            result = clienteDAO.get(username);
        }else if (idTipoUsuario.equals(TipoUsuario.ESTABELECIMENTO_COMERCIAL)){
            EstabelecimentoComercialDAO estabelecimentoComercialDAO = new EstabelecimentoComercialDAO();
            result = estabelecimentoComercialDAO.get(username);
        }
        return result;
    }

    public void login (String username, String senha, TipoUsuario idTipoUsuario) throws IOException, UsuarioNaoCadastradoException {
        Usuario usuario = validacaoLogin(username, senha, idTipoUsuario);
        if (usuario == null) {
            throw new UsuarioNaoCadastradoException("Usuário não cadastrado");
        }
        Sessao.instance.setUsuario(usuario);
    }

    public Usuario validacaoLogin(String username, String senha, TipoUsuario idTipoUsuario) throws IOException{
        Usuario result = getUsuario(username, idTipoUsuario);
        if (result != null && !senha.equals((result.getSenha()))){
            result = null;
        }
        return result;
    }

    public void logout() {
        Sessao sessao = Sessao.instance;
        sessao.reset();
    }

}