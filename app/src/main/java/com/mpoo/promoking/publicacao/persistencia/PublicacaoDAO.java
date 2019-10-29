package com.mpoo.promoking.publicacao.persistencia;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.strictmode.SqliteObjectLeakedViolation;

import com.mpoo.promoking.infra.persistencia.AbstractSQLite;
import com.mpoo.promoking.infra.persistencia.BancoDadosHelper;
import com.mpoo.promoking.produto.dominio.Produto;
import com.mpoo.promoking.publicacao.dominio.Publicacao;
import com.mpoo.promoking.publicacao.dominio.UnidadeVenda;

import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class PublicacaoDAO extends AbstractSQLite {
    public long insert(Publicacao publicacao) throws IOException {
        SQLiteDatabase db = super.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BancoDadosHelper.COLUNA_PROD_PUBLICACAO, publicacao.getProduto().getTipo());
        values.put(BancoDadosHelper.COLUNA_MARCA_PUBLICACAO, publicacao.getMarca());
        values.put(BancoDadosHelper.COLUNA_VALIDADE_PRODUTO, publicacao.getValidadeProduto().getTimeInMillis());
        values.put(BancoDadosHelper.COLUNA_VALIDADE_PUBLICACAO, publicacao.getValidadePublicacao().getTimeInMillis());
        values.put(BancoDadosHelper.COLUNA_UNID_VENDA, publicacao.getUnidadeVenda().ordinal());
        long id = db.insert(BancoDadosHelper.TABELA_PUBLICACAO, null, values);
        super.close(db);
        return id;
    }
    public Publicacao get(long publicacaoId) throws IOException{
        Publicacao result = null;
        SQLiteDatabase db = super.getReadableDatabase();
        String sql = "SELECT * FROM " + BancoDadosHelper.TABELA_PUBLICACAO
                + " U WHERE U." + BancoDadosHelper.COLUNA_ID_PUBLICACAO + " LIKE ?;";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf((publicacaoId))});
        if (cursor.moveToFirst()) {
            result = createPublicacao(cursor);
        }
        super.close(cursor, db);
        return result;
    }
    public void update(Publicacao publicacao) throws IOException{
        SQLiteDatabase db = super.getWritableDatabase();
        String sql = "UPDATE "+ BancoDadosHelper.TABELA_PUBLICACAO + " SET " +
                BancoDadosHelper.COLUNA_PROD_PUBLICACAO  + "=?, " +
                BancoDadosHelper.COLUNA_MARCA_PUBLICACAO  + "=?, " +
                BancoDadosHelper.COLUNA_VALIDADE_PRODUTO  + "=?, " +
                BancoDadosHelper.COLUNA_VALIDADE_PUBLICACAO  + "=?, " +
                BancoDadosHelper.COLUNA_UNID_VENDA + "=? " +
                " WHERE " + BancoDadosHelper.COLUNA_ID_PUBLICACAO + "=?;";
        db.execSQL(sql, new String[]{
                publicacao.getProduto().getTipo(),
                publicacao.getMarca(),
                String.valueOf(publicacao.getValidadeProduto().getTimeInMillis()),
                String.valueOf(publicacao.getValidadePublicacao().getTimeInMillis()),
                String.valueOf(publicacao.getUnidadeVenda().ordinal()),
                String.valueOf(publicacao.getId())
        });
        super.close(db);
    }
    public void deletar(Publicacao publicacao) throws IOException {
        SQLiteDatabase db = super.getWritableDatabase();
        String[] argumentos = {String.valueOf(publicacao.getId())};
        db.delete(BancoDadosHelper.TABELA_PUBLICACAO, BancoDadosHelper.COLUNA_ID_PUBLICACAO + " =?", argumentos);
        super.close(db);
    }
    public List<Publicacao> list() throws IOException{
        ArrayList<Publicacao> listaPublicacao = new ArrayList<>();
        SQLiteDatabase db = super.getReadableDatabase();
        String sql = "SELECT * FROM " + BancoDadosHelper.TABELA_PUBLICACAO;
        Cursor cursor = db.rawQuery(sql, new String[]{});
        while (cursor.moveToNext()) {
            listaPublicacao.add(createPublicacao(cursor));
        }
        super.close(db, cursor);
        return listaPublicacao;
    }
    private Publicacao createPublicacao(Cursor cursorPublicacao) throws IOException{
        SQLiteDatabase db = super.getReadableDatabase();
        String sqlTipoProduto = "SELECT * FROM " + BancoDadosHelper.TABELA_PRODUTO
                + " U WHERE U." + BancoDadosHelper.COLUNA_TIPO_PRODUTO + " LIKE ?;";
        Cursor cursor = db.rawQuery(sqlTipoProduto, new String[]{cursorPublicacao.getString(cursorPublicacao.getColumnIndex(BancoDadosHelper.COLUNA_PROD_PUBLICACAO))});
        Produto produto = new Produto();
        produto.setTipo(cursor.getString(cursor.getColumnIndex(BancoDadosHelper.COLUNA_TIPO_PRODUTO)));
        produto.setMarcas(cursor.getString(cursorPublicacao.getColumnIndex(BancoDadosHelper.COLUNA_MARCAS_PRODUTO)));
        super.close(db, cursor);
        //
        Publicacao publicacao = new Publicacao();
        publicacao.setProduto(produto);
        publicacao.setMarca(cursorPublicacao.getString(cursorPublicacao.getColumnIndex(BancoDadosHelper.COLUNA_MARCA_PUBLICACAO)));
        GregorianCalendar validadeProduto = new GregorianCalendar();
        GregorianCalendar validadePublicacao = new GregorianCalendar();
        validadeProduto.setTimeInMillis(cursorPublicacao.getLong(cursorPublicacao.getColumnIndex(BancoDadosHelper.COLUNA_VALIDADE_PRODUTO)));
        validadePublicacao.setTimeInMillis(cursorPublicacao.getLong(cursorPublicacao.getColumnIndex(BancoDadosHelper.COLUNA_VALIDADE_PUBLICACAO)));
        publicacao.setValidadeProduto(validadeProduto);
        publicacao.setValidadePublicacao(validadePublicacao);
        publicacao.setUnidadeVenda(UnidadeVenda.values()[cursorPublicacao.getInt(cursorPublicacao.getColumnIndex(BancoDadosHelper.COLUNA_UNID_VENDA))]);

        return publicacao;
    }
}
