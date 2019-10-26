package com.mpoo.promoking.usuario.negocios;

import android.database.sqlite.SQLiteDatabase;

import com.mpoo.promoking.cliente.persistencia.ClienteDAO;
import com.mpoo.promoking.estabelecimentoComercial.persistencia.EstabelecimentoComercialDAO;
import com.mpoo.promoking.infra.exception.UsuarioNaoCadastrado;
import com.mpoo.promoking.infra.persistencia.AbstractSQLite;
import com.mpoo.promoking.usuario.dominio.TipoUsuario;
import com.mpoo.promoking.usuario.dominio.Usuario;

import java.io.IOException;

public class UsuarioServices extends AbstractSQLite{

    public void login (String username, String senha, TipoUsuario idTipoUsuario) throws IOException, UsuarioNaoCadastrado {
        Usuario usuario = validacaoLogin(username, senha, idTipoUsuario);
        if (usuario == null) {
            throw new UsuarioNaoCadastrado("Usuário não cadastrado");
        }
        //sessão
    }

    public Usuario getUsuario(String username, TipoUsuario idTipoUsuario) throws IOException {
        Usuario result = null;
        SQLiteDatabase db = super.getReadableDatabase();
        if(idTipoUsuario.equals(TipoUsuario.CLIENTE)){
            ClienteDAO clienteDAO = new ClienteDAO();
            result = clienteDAO.getCliente(username, db);
        }else if (idTipoUsuario.equals(TipoUsuario.ESTABELECIMENTO_COMERCIAL)){
            EstabelecimentoComercialDAO estabelecimentoComercialDAO = new EstabelecimentoComercialDAO();
            result = estabelecimentoComercialDAO.getEstabelecimentoComercial(username, db);
        }
        super.close(db);
        return result;
    }

    public Usuario validacaoLogin(String username, String senha, TipoUsuario idTipoUsuario) throws IOException{
        Usuario result = getUsuario(username, idTipoUsuario);
        if (result != null && !senha.equals((result.getSenha()))){
            result = null;
        }
        return result;
    }
}

