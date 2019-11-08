package com.mpoo.promoking.produto.persistencia;

import com.mpoo.promoking.infra.persistencia.AbstractSQLite;
import com.mpoo.promoking.infra.persistencia.BancoDadosHelper;
import com.mpoo.promoking.produto.dominio.Produto;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO extends AbstractSQLite {
    public void insert(Produto produto) throws IOException {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BancoDadosHelper.COLUNA_PRODUTO, produto.getProduto());
        values.put(BancoDadosHelper.COLUNA_MARCAS_PRODUTO, produto.getMarcas());
        db.insert(BancoDadosHelper.TABELA_PRODUTO, null, values);
        super.close(db);
    }

    public Produto get(String tipoProduto) throws IOException{
        SQLiteDatabase db = super.getReadableDatabase();
        String sqlTipoProduto = "SELECT * FROM " + BancoDadosHelper.TABELA_PRODUTO
                + " U WHERE U." + BancoDadosHelper.COLUNA_PRODUTO + " LIKE ?;";
        Cursor cursor = db.rawQuery(sqlTipoProduto, new String[]{tipoProduto});
        Produto result = null;
        if(cursor.moveToFirst()){
            result = create(cursor);
        }
        super.close(cursor, db);
        return result;
    }

    public Produto create(Cursor cursor) {
        Produto result = new Produto();
        result.setProduto(cursor.getString(cursor.getColumnIndex(BancoDadosHelper.COLUNA_PRODUTO)));
        result.setMarcas(cursor.getString(cursor.getColumnIndex(BancoDadosHelper.COLUNA_MARCAS_PRODUTO)));
        return result;
    }
    public void update(Produto produto) throws IOException{
        SQLiteDatabase db = super.getWritableDatabase();
        String sql = "UPDATE " + BancoDadosHelper.TABELA_PRODUTO + " SET " +
                BancoDadosHelper.COLUNA_MARCAS_PRODUTO + "=? " +
                " WHERE " + BancoDadosHelper.COLUNA_PRODUTO + "=?;";
        db.execSQL(sql, new String[]{
            produto.getMarcas(),
            produto.getProduto()
        });
        super.close(db);
    }
    public void delete(Produto produto) throws IOException{
        SQLiteDatabase db = super.getWritableDatabase();
        String [] argumentos = {produto.getProduto()};
        db.delete(BancoDadosHelper.TABELA_PRODUTO, BancoDadosHelper.COLUNA_PRODUTO + " =?", argumentos);
        super.close(db);
    }
    public List<Produto> list() throws IOException {
        ArrayList<Produto> listaProdutos = new ArrayList<>();
        SQLiteDatabase db = super.getReadableDatabase();
        String sql = "SELECT * FROM " + BancoDadosHelper.TABELA_PRODUTO;
        Cursor cursor = db.rawQuery(sql, new String[]{});
        while (cursor.moveToNext()) {
            listaProdutos.add(create(cursor));
        }
        super.close(cursor, db);
        return listaProdutos;
    }
}