package com.example.sqlcrud.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.CaseMap;

import androidx.annotation.Nullable;

import com.example.sqlcrud.modelo.Pessoa;

import java.util.ArrayList;

public class PessoaDao extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "DBPessoa.db";
    private static final int VERSAO = 2;
    private static final String TABELA = "tbpessoa";
    private static final String ID = "id";
    private static final String NOME = "nome";
    private static final String IDADE = "idade";
    private static final String EMAIL = "email";
    private static final String ENDERECO = "endereco";

    //Novos campos para atualização
    //private static final String NASCIMENTO = "email";
    //private static final String TELEFONE = "endereco";


    public PessoaDao(Context context) {

        //Criação do banco de dados -> Create DataBase NOME_BANCO
        super(context, NOME_BANCO, null, VERSAO);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //Criação da tabela na instalação do app
        String sql = "CREATE TABLE " + TABELA + " ( " + "  " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "  " + NOME + " TEXT, " + IDADE + " INTEGER , " +
                "  " + EMAIL + " TEXT, " + ENDERECO + " TEXT );";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //ATUALIZAÇÃO DA TABELA PELA VERSÃO DO BANCO
        String sql = "DROP TABLE IF EXISTS " + TABELA;
        db.execSQL(sql);
        onCreate(db);
        //SE ACRESCENTAR NOVOS ATRIBUTOS APÓS A INSTALAÇÃO DO APP
        // CRIAR AS CONSTANTES DOS NOVOS CAMPOS/ATRIBUTOS
        //MODIFICAR A VERSÃO PARA 2 (EM DIANTE)
        //ALTERAR O CREATE TABLE DO METODO onCreate
    }

    public long salvarPessoa(Pessoa p){
        ContentValues valores = new ContentValues();
        long retornoDB;
        //COMANDO EQUIVALENTE AO INSERT INTO DO SQL SERVER
        valores.put(NOME,p.getNome());
        valores.put(IDADE, p.getIdade());
        valores.put(EMAIL, p.getEmail());
        valores.put(ENDERECO, p.getEndereco());

        retornoDB = getWritableDatabase().insert(TABELA, null, valores);

        return retornoDB;

    }
    public long alterarPessoa(Pessoa p){
        ContentValues valores = new ContentValues();
        long retornoDB;
        //COMANDO EQUIVALENTE AO INSERT INTO DO SQL SERVER
        valores.put(NOME,p.getNome());
        valores.put(IDADE, p.getIdade());
        valores.put(EMAIL, p.getEmail());
        valores.put(ENDERECO, p.getEndereco());

        //captura do banco o ID do registro que será alterado
        String[] args = {String.valueOf(p.getId())};
        retornoDB = getWritableDatabase().update(TABELA, valores, "id=?", args);


        return retornoDB;

    }

    public long excluirPessoa(Pessoa p){

        //EQUIVALE AO DELETE FROM DO SQL SERVER
        long retornoDB;

        //captura do banco o ID do registro que será excluido
        String[] args = {String.valueOf(p.getId())};
        retornoDB = getWritableDatabase().delete(TABELA, "id=?", args);

        return retornoDB;
    }

    //SELECIONAR REGISTROS PARA PREENCHER A LISTA DA TELA PRINCIPAL
    public ArrayList<Pessoa> selectAllPessoa(){
        String[] colunas = {ID, NOME , IDADE, EMAIL , ENDERECO};

        //EXIBIR EM ORGEM DE CADASTRO
        Cursor cursor = getWritableDatabase().query(TABELA, colunas, null, null, null, null, null, null);


        //EXIBIR EM ORDEM ALFABETICA
        /*Cursor cursor = getWritableDatabase()
                .query(TABELA, colunas, null, null, null, null, "upper(nome)", null);

         */

        ArrayList <Pessoa> listPessoa = new ArrayList<Pessoa>();

        while( cursor.moveToNext()){
            Pessoa p = new Pessoa();
            p.setId(cursor.getInt(0));
            p.setNome(cursor.getString(1));
            p.setIdade(cursor.getInt(2));
            p.setEmail(cursor.getString(3));
            p.setEndereco(cursor.getString(4));
            
            listPessoa.add(p);
        }

        return listPessoa;


    }
}
