package com.mpoo.promoking.cliente.persistencia;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.mpoo.promoking.infra.persistencia.AbstractSQLite;
import com.mpoo.promoking.infra.persistencia.BancoDadosHelper;

import java.io.IOException;

public class PublicacaoClienteDAO extends AbstractSQLite {

    public void insert(long idCliente, long idPublicacao) throws IOException {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BancoDadosHelper.COLUNA_ID_PUBLICACAO_CLIENTE, idCliente);
        values.put(BancoDadosHelper.COLUNA_ID_USUARIO_CLIENTE, idPublicacao);
        db.insert(BancoDadosHelper.TABELA_PUBLICACAO_CLIENTE, null, values);
        super.close(db);
    }
}
