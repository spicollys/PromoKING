package com.mpoo.promoking.infra.persistencia;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mpoo.promoking.infra.ui.PromoKINGApp;

public class BancoDadosHelper extends SQLiteOpenHelper {

    public static final String BANCO_DE_DADOS_NOME = "BANCO_DE_DADOS_NOME";
    public static final int VERSAO = 9;

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
    public static final String COLUNA_PRODUTO = "PRODUTO";
    public static final String COLUNA_MARCAS_PRODUTO = "MARCAS";

    // TABELA PUBLICACAO
    public static final String TABELA_PUBLICACAO = "TB_PUBLICACAO";
    public static final String COLUNA_ID_PUBLICACAO = "ID_PUBLICACAO";
    public static final String COLUNA_PROD_PUBLICACAO = "PRODUTO";
    public static final String COLUNA_PRECO_PROD_PUBLICACAO = "PRECO";
    public static final String COLUNA_MARCA_PUBLICACAO = "MARCA";
    public static final String COLUNA_VALIDADE_PRODUTO = "VAL_PRODUTO";
    public static final String COLUNA_VALIDADE_PUBLICACAO = "VAL_PUBLICACAO";
    public static final String COLUNA_UNID_VENDA = "UNID_VENDA";
    public static final String COLUNA_USUARIO = "USUARIO";

    // TABELA PUBLICACAO CLIENTE
    public static final String TABELA_PUBLICACAO_CLIENTE = "TB_PRODUTO_CLIENTE";
    public static final String COLUNA_ID_PUBLICACAO_CLIENTE = "ID_PUBLICACAO";
    public static final String COLUNA_ID_USUARIO_CLIENTE = "ID_CLIENTE";

    // TABELA PUBLICACAO ESTABELECIMENTO COMERCIAL
    public static final String TABELA_PUBLICACAO_ESTABELECIMENTO_COMERCIAL = "TB_PRODUTO_CLIENTE";
    public static final String COLUNA_ID_PUBLICACAO_ESTABELECIMENTO_COMERCIAL = "ID_PUBLICACAO";
    public static final String COLUNA_ID_USUARIO_ESTABELECIMENTO_COMERCIAL = "ID_ESTABELECIMENTO_COMERCIAL";

    private static final String[] TABELAS = {
            TABELA_CLIENTE, TABELA_ESTABELECIMENTO_COMERCIAL, TABELA_ADMINISTRADOR,
            TABELA_PRODUTO, TABELA_PUBLICACAO,
            TABELA_PUBLICACAO_CLIENTE, TABELA_PUBLICACAO_ESTABELECIMENTO_COMERCIAL
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
        createTabelaPublicacao(db);
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
                        COLUNA_EMAIL_CLIENTE + " TEXT);";
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
                        COLUNA_PRODUTO + " TEXT, " +
                        COLUNA_MARCAS_PRODUTO + " TEXT);";
        db.execSQL(sqlTbProduto);

    }

    private void createTabelaPublicacao(SQLiteDatabase db) {
        String sqlTbPublicacao =
                " CREATE TABLE " + TABELA_PUBLICACAO + " (" +
                        COLUNA_ID_PUBLICACAO + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUNA_PROD_PUBLICACAO + " TEXT, " +
                        COLUNA_PRECO_PROD_PUBLICACAO + " REAL, " +
                        COLUNA_MARCA_PUBLICACAO + " TEXT, " +
                        COLUNA_VALIDADE_PRODUTO + " REAL, " +
                        COLUNA_VALIDADE_PUBLICACAO + " REAL, " +
                        COLUNA_UNID_VENDA + " INTEGER, " +
                        COLUNA_USUARIO + " TEXT);";
        db.execSQL(sqlTbPublicacao);
    }

    private void createTabelaPublicacaoCliente(SQLiteDatabase db){
        String sqlTbPublicacaoCliente =
                " CREATE TABLE " + TABELA_PUBLICACAO_CLIENTE + " (" +
                        COLUNA_ID_PUBLICACAO_CLIENTE + " INTEGER, " +
                        COLUNA_ID_USUARIO_CLIENTE + " INTEGER);";
        db.execSQL(sqlTbPublicacaoCliente);
    }

    private void createTabelaPublicacaoEstabelecimentoComercial(SQLiteDatabase db){
        String sqlTbPublicacaoEstabelecimentoComercial =
                " CREATE TABLE " + TABELA_PUBLICACAO_ESTABELECIMENTO_COMERCIAL + " (" +
                        COLUNA_ID_PUBLICACAO_ESTABELECIMENTO_COMERCIAL + " INTEGER, " +
                        COLUNA_ID_USUARIO_ESTABELECIMENTO_COMERCIAL + " INTEGER);";
        db.execSQL(sqlTbPublicacaoEstabelecimentoComercial);
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
