package com.mpoo.promoking.infra.persistencia;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mpoo.promoking.infra.ui.PromoKINGApp;

public class BancoDadosHelper extends SQLiteOpenHelper {

    public static final String BANCO_DE_DADOS_NOME = "BANCO_DE_DADOS_NOME";
    public static final int VERSAO = 5;

    // TABELA CLIENTE
    public static final String TABELA_CLIENTE = "TB_CLIENTE";
    public static final String COLUNA_ID_CLIENTE = "ID_CLIENTE";
    public static final String COLUNA_USERNAME_CLIENTE = "USERNAME_CLIENTE";
    public static final String COLUNA_SENHA_CLIENTE = "SENHA";
    public static final String COLUNA_EMAIL_CLIENTE = "EMAIL";

    // TABELA ESTABELECIMENTO COMERCIAL
    public static final String TABELA_ESTABELECIMENTO_COMERCIAL = "TB_ESTABELECIMENTO";
    public static final String COLUNA_ID_ESTABELECIMENTO_COMERCIAL = "ID_ESTABELECIMENTO";
    public static final String COLUNA_USERNAME_ESTABELECIMENTO_COMERCIAL = "USERNAME_ESTABELECIMENTO";
    public static final String COLUNA_SENHA_ESTABELECIMENTO_COMERCIAL = "SENHA";
    public static final String COLUNA_EMAIL_ESTABELECIMENTO_COMERCIAL = "EMAIL";
    public static final String COLUNA_CNPJ = "CNPJ";

    // TABELA ADMINISTRADOR
    public static final String TABELA_ADMINISTRADOR = "TB_ADMINISTRADOR";
    public static final String COLUNA_ID_ADMINISTRADOR = "ID_ADMINISTRADOR";
    public static final String COLUNA_USERNAME_ADMINISTRADOR = "USERNAME_ADMINISTRADOR";
    public static final String COLUNA_SENHA_ADMINISTRADOR = "SENHA";

    // TABELA PRODUTO
    public static final String TABELA_PRODUTO = "TB_PRODUTO";
    public static final String COLUNA_ID_PRODUTO = "ID_PRODUTO";
    public static final String COLUNA_TIPO_PRODUTO = "TIPO";
    public static final String COLUNA_MARCAS_PRODUTO = "MARCAS";



    private static final String[] TABELAS = {
            TABELA_CLIENTE, TABELA_ESTABELECIMENTO_COMERCIAL, TABELA_ADMINISTRADOR, TABELA_PRODUTO,
    };

    public BancoDadosHelper() {
        super(PromoKINGApp.getContext(), BANCO_DE_DADOS_NOME, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTabelaCliente(db);
        createTabelaEstabelecimentoComercial(db);
        createTabelaAdministrador(db);
        createTabelaProduto(db);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropTables(db);
        onCreate(db);

    }
    private void createTabelaCliente(SQLiteDatabase db) {
        String sqlTbCliente =
                " CREATE TABLE " + TABELA_CLIENTE + " (" +
                        COLUNA_ID_CLIENTE + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUNA_USERNAME_CLIENTE + " TEXT, " +
                        COLUNA_SENHA_CLIENTE + " TEXT, " +
                        COLUNA_EMAIL_CLIENTE + " TEXT );";
        db.execSQL(sqlTbCliente);
    }

    private void createTabelaEstabelecimentoComercial(SQLiteDatabase db){
        String sqlTbEstabelecimentoComercial =
                " CREATE TABLE " + TABELA_ESTABELECIMENTO_COMERCIAL + " (" +
                        COLUNA_ID_ESTABELECIMENTO_COMERCIAL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUNA_USERNAME_ESTABELECIMENTO_COMERCIAL + " TEXT, " +
                        COLUNA_SENHA_ESTABELECIMENTO_COMERCIAL + " TEXT, " +
                        COLUNA_EMAIL_ESTABELECIMENTO_COMERCIAL + " TEXT, " +
                        COLUNA_CNPJ + " TEXT );";
        db.execSQL(sqlTbEstabelecimentoComercial);

    }

    private void createTabelaAdministrador(SQLiteDatabase db) {
        String sqlTbAdministrador =
                " CREATE TABLE " + TABELA_ADMINISTRADOR + " (" +
                        COLUNA_ID_ADMINISTRADOR + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUNA_USERNAME_ADMINISTRADOR + " TEXT, " +
                        COLUNA_SENHA_ADMINISTRADOR + " TEXT);";
        db.execSQL(sqlTbAdministrador);
    }

    private void createTabelaProduto(SQLiteDatabase db){
        String sqlTbProduto =
                " CREATE TABLE " + TABELA_PRODUTO + " (" +
                        COLUNA_ID_PRODUTO + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUNA_TIPO_PRODUTO + " TEXT, " +
                        COLUNA_MARCAS_PRODUTO + " TEXT);";
        db.execSQL(sqlTbProduto);

    }

    private void dropTables(SQLiteDatabase db) {
        StringBuilder dropTables = new StringBuilder();
        for (String tabela : TABELAS) {
            dropTables.append(" DROP TABLE IF EXISTS ");
            dropTables.append(tabela);
            dropTables.append("; ");
        }
        db.execSQL(dropTables.toString());
    }
}
