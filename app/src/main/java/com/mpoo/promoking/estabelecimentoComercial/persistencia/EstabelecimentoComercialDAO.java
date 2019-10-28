package com.mpoo.promoking.estabelecimentoComercial.persistencia;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mpoo.promoking.infra.exception.IdTipoUsuarioInvalidoException;
import com.mpoo.promoking.infra.exception.UsuarioEmailInvalidoException;
import com.mpoo.promoking.infra.exception.UsuarioSenhaInvalidaException;
import com.mpoo.promoking.infra.exception.UsuarioUsernameInvalidoException;
import com.mpoo.promoking.infra.persistencia.BancoDadosHelper;
import com.mpoo.promoking.infra.persistencia.AbstractSQLite;
import com.mpoo.promoking.usuario.dominio.TipoUsuario;
import com.mpoo.promoking.usuario.dominio.Usuario;

import java.io.IOException;

public class EstabelecimentoComercialDAO extends AbstractSQLite {

    public void insert(Usuario usuario) throws IOException {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BancoDadosHelper.COLUNA_USERNAME_ESTABELECIMENTO_COMERCIAL, usuario.getUsername());
        values.put(BancoDadosHelper.COLUNA_SENHA_ESTABELECIMENTO_COMERCIAL, usuario.getSenha());
        values.put(BancoDadosHelper.COLUNA_EMAIL_ESTABELECIMENTO_COMERCIAL, usuario.getEmail());
        db.insert(BancoDadosHelper.TABELA_ESTABELECIMENTO_COMERCIAL, null, values);
        super.close(db);
    }

    public Usuario get(String username) throws IOException{
        SQLiteDatabase db = super.getReadableDatabase();
        String sqlUsername = "SELECT * FROM " + BancoDadosHelper.TABELA_ESTABELECIMENTO_COMERCIAL
                + " U WHERE U." + BancoDadosHelper.COLUNA_USERNAME_ESTABELECIMENTO_COMERCIAL + " LIKE ?;";
        Cursor cursor = db.rawQuery(sqlUsername, new String[]{username});
        Usuario result = null;
        if (cursor.moveToFirst()){
            result = create(cursor);
        }
        super.close(cursor, db);
        return result;
    }


    private Usuario create(Cursor cursor) {
        Usuario result = new Usuario();
        try {
            result.setUsername(cursor.getString(cursor.getColumnIndex(BancoDadosHelper.COLUNA_USERNAME_ESTABELECIMENTO_COMERCIAL)));
        } catch (UsuarioUsernameInvalidoException e) {
            e.printStackTrace();
        }
        try {
            result.setSenha(cursor.getString(cursor.getColumnIndex(BancoDadosHelper.COLUNA_SENHA_ESTABELECIMENTO_COMERCIAL)));
        } catch (UsuarioSenhaInvalidaException e) {
            e.printStackTrace();
        }
        try{
            result.setEmail(cursor.getString(cursor.getColumnIndex(BancoDadosHelper.COLUNA_EMAIL_ESTABELECIMENTO_COMERCIAL)));
        } catch (UsuarioEmailInvalidoException e) {
            e.printStackTrace();
        }
        return result;
    }
    public void update(Usuario usuario) throws IOException {
        SQLiteDatabase db = super.getWritableDatabase();
        String sql = "UPDATE " + BancoDadosHelper.TABELA_ESTABELECIMENTO_COMERCIAL + " SET " +
                BancoDadosHelper.COLUNA_SENHA_ESTABELECIMENTO_COMERCIAL + "=?, " +
                BancoDadosHelper.COLUNA_EMAIL_ESTABELECIMENTO_COMERCIAL + "=? " +
                " WHERE " + BancoDadosHelper.COLUNA_USERNAME_ESTABELECIMENTO_COMERCIAL + "=?;";

        db.execSQL(sql, new String[]{
                usuario.getSenha(),
                usuario.getEmail(),
                usuario.getUsername(),
        });

        super.close(db);
    }
    public void delete(Usuario usuario) throws IOException {
        SQLiteDatabase db = super.getWritableDatabase();
        String [] argumentos = {usuario.getUsername()};
        db.delete(BancoDadosHelper.TABELA_ESTABELECIMENTO_COMERCIAL, BancoDadosHelper.COLUNA_USERNAME_ESTABELECIMENTO_COMERCIAL + " =?", argumentos);
        super.close(db);
    }
}
