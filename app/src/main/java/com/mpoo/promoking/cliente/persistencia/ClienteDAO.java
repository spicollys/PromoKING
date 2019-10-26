package com.mpoo.promoking.cliente.persistencia;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mpoo.promoking.infra.exception.UsuarioEmailInvalidoException;
import com.mpoo.promoking.infra.exception.UsuarioSenhaInvalidaException;
import com.mpoo.promoking.infra.exception.UsuarioUsernameInvalidoException;
import com.mpoo.promoking.infra.persistencia.BancoDadosHelper;
import com.mpoo.promoking.infra.persistencia.AbstractSQLite;
import com.mpoo.promoking.usuario.dominio.Usuario;

import java.io.IOException;

public class ClienteDAO extends AbstractSQLite {

    public void insertCliente(Usuario usuario) throws IOException {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BancoDadosHelper.COLUNA_USERNAME_CLIENTE, usuario.getUsername());
        values.put(BancoDadosHelper.COLUNA_SENHA_CLIENTE, usuario.getSenha());
        values.put(BancoDadosHelper.COLUNA_EMAIL_CLIENTE, usuario.getEmail());
        db.insert(BancoDadosHelper.TABELA_CLIENTE, null, values);
        super.close(db);
    }

    public Usuario getCliente(String username, SQLiteDatabase db) {
        String sqlUsername = "SELECT * FROM " + BancoDadosHelper.TABELA_CLIENTE
                + " U WHERE U." + BancoDadosHelper.COLUNA_USERNAME_CLIENTE + " LIKE ?;";
        Cursor cursor = db.rawQuery(sqlUsername, new String[]{username});
        Usuario result = null;
        if (cursor.moveToFirst()){
            result = createCliente(cursor);
        }
        return result;
    }

    private Usuario createCliente(Cursor cursor) {
        Usuario result = new Usuario();
        try {
            result.setUsername(cursor.getString(cursor.getColumnIndex(BancoDadosHelper.COLUNA_USERNAME_CLIENTE)));
        } catch (UsuarioUsernameInvalidoException e) {
            e.printStackTrace();
        }
        try {
            result.setSenha(cursor.getString(cursor.getColumnIndex(BancoDadosHelper.COLUNA_SENHA_CLIENTE)));
        } catch (UsuarioSenhaInvalidaException e) {
            e.printStackTrace();
        }
        try{
            result.setEmail(cursor.getString(cursor.getColumnIndex(BancoDadosHelper.COLUNA_EMAIL_CLIENTE)));
        } catch (UsuarioEmailInvalidoException e) {
            e.printStackTrace();
        }
        return result;
    }
}
