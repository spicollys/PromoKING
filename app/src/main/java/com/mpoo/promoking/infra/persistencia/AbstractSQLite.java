package com.mpoo.promoking.infra.persistencia;

import android.database.sqlite.SQLiteDatabase;

import java.io.Closeable;
import java.io.IOException;

public abstract class AbstractSQLite {
    protected void close(Closeable... args) throws IOException {
        for (Closeable closable : args) {
            closable.close();
        }
    }

    protected SQLiteDatabase getReadableDatabase() {
        BancoDadosHelper bancoDadosHelper = new BancoDadosHelper();
        System.out.println(bancoDadosHelper);
        return bancoDadosHelper.getReadableDatabase();
    }

    protected SQLiteDatabase getWritableDatabase() {
        BancoDadosHelper bancoDadosHelper = new BancoDadosHelper();
        return bancoDadosHelper.getWritableDatabase();
    }
}