package com.mpoo.promoking.usuario.negocios;

import android.database.sqlite.SQLiteDatabase;

import com.mpoo.promoking.cliente.persistencia.ClienteDAO;
import com.mpoo.promoking.estabelecimentoComercial.persistencia.EstabelecimentoComercialDAO;
import com.mpoo.promoking.infra.exception.UsuarioNaoCadastrado;
import com.mpoo.promoking.infra.persistencia.AbstractSQLite;
import com.mpoo.promoking.usuario.dominio.Usuario;

import java.io.IOException;

public class UsuarioServices extends AbstractSQLite{
    public void login (String username, String senha) throws IOException, UsuarioNaoCadastrado {
        Usuario usuario = validacaoLogin(username, senha);
        if (usuario == null) {
            throw new UsuarioNaoCadastrado("Usuário não cadastrado");
        }
        //sessão
    }

    public Usuario getUsuario(String username) throws IOException {
        Usuario result = null;
        ClienteDAO clienteDAO = new ClienteDAO();
        EstabelecimentoComercialDAO estabelecimentoComercialDAO = new EstabelecimentoComercialDAO();
        SQLiteDatabase db = super.getReadableDatabase();
        result = clienteDAO.getCliente(username, db);
        if (result == null) {
            result = estabelecimentoComercialDAO.getEstabelecimentoComercial(username, db);
        }
        super.close(db);
        return result;
    }

    public Usuario validacaoLogin(String username, String senha) throws IOException{
        Usuario result = getUsuario(username);
        if (result != null && !senha.equals((result.getSenha()))){
            result = null;
        }
        return result;
    }
}

