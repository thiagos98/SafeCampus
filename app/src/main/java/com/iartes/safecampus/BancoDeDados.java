package com.iartes.safecampus;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.List;
import kotlin.collections.ArrayDeque;

public class BancoDeDados  extends SQLiteOpenHelper {

    public SQLiteDatabase database;

    public BancoDeDados(Context context) {
        super(context, "banco7.db", null, 1);
        Log.i("debug", "conexao com o banco..., vou tentar setar.");
        try{
//            this.database = this.getWritableDatabase();
//            this.database = this.getReadableDatabase();
        }catch (Exception e){
            Log.i("debug", "erro" + e.getMessage());
        }
//        Log.i("debug", "ta ligado? " + this.database.isOpen());
    }


    public void populaSemParar(String sistema){
        String query_insert = "INSERT INTO " + sistema + " VALUES (1,'teste1','12345678978945')";
        database.execSQL(query_insert);
    }


    public void populaSemPararUsuario(String usuario){
        String query_insert_usuario = "INSERT INTO " + usuario + " VALUES (1,'Maria','maria@gmail.com','12345')";
        database.execSQL(query_insert_usuario);
        this.database.execSQL(query_insert_usuario);
    }

    public void populaSemPararAdm(String administrador){
        String query_insert_administrador = "INSERT INTO " + administrador + " VALUES (1,'jose','jose@gmail.com','54123')";
        database.execSQL(query_insert_administrador);
        this.database.execSQL(query_insert_administrador);
    }


    public void populaSemPararOcorrenciaUpdate(String administrador){
        String query_update_administrador = "update" + administrador
                + " set = '' where cod_adm = '' ";
        database.execSQL(query_update_administrador);
        this.database.execSQL(query_update_administrador);
    }


    public void populaSemPararOcorrenciaDelete(String administrador){
        String query_delete_administrador = "delete" + "from"+ administrador
                +  "where  cod_adm = '' ";
        database.execSQL(query_delete_administrador);
        this.database.execSQL(query_delete_administrador);
    }


    public void populaSemPararOcorrencia(String ocorrencia){
        String query_insert_ocorrencia = "INSERT INTO " + ocorrencia + " VALUES (1,'2023-05-10','18:00:00','roubo','GRAVE',1,'Maria')";
        database.execSQL(query_insert_ocorrencia);
        this.database.execSQL(query_insert_ocorrencia);
    }

    @SuppressLint("Range")
    public void insere_sistema(String sistema){
        this.populaSemParar(sistema);
        String sql = "select * from sistema;";
        Cursor cursor = this.database.rawQuery(sql,null);

        Log.i("debug", "Tamanho da tabela: " + cursor.getCount());
        if(cursor.moveToFirst()) {
            do {
                Log.i("debug", "ELemento: " +
                        cursor.getString(cursor.getColumnIndex("cod_sistema"))
                );
                Log.i("debug", "ELemento: " +
                        cursor.getString(cursor.getColumnIndex("nome_sistema"))
                );
                Log.i("debug", "ELemento: " +
                        cursor.getString(cursor.getColumnIndex("cnpj"))
                );
            } while (cursor.moveToNext());
        }
    }


    @SuppressLint("Range")
    public void insere_usuario(String usuario){
        this.populaSemPararUsuario(usuario);
        String sql = "select * from usuario;";
        Cursor cursor = this.database.rawQuery(sql,null);

        Log.i("debug", "Tamanho da tabela: " + cursor.getCount());
        if(cursor.moveToFirst()) {
            do {
                Log.i("debug", "ELemento: " +
                        cursor.getString(cursor.getColumnIndex("cod_usuario"))
                );
                Log.i("debug", "ELemento: " +
                        cursor.getString(cursor.getColumnIndex("nome_usuario"))
                );
                Log.i("debug", "ELemento: " +
                        cursor.getString(cursor.getColumnIndex("email_usuario"))
                );
                Log.i("debug", "ELemento: " +
                        cursor.getString(cursor.getColumnIndex("senha_usuario"))
                );
            } while (cursor.moveToNext());
        }
    }

    @SuppressLint("Range")
    public void insere_administrador(String administrador){
        this.populaSemPararAdm(administrador);
        String sql = "select * from administrador;";
        Cursor cursor = this.database.rawQuery(sql,null);

        Log.i("debug", "Tamanho da tabela: " + cursor.getCount());
        if(cursor.moveToFirst()) {
            do {
                Log.i("debug", "ELemento: " +
                        cursor.getString(cursor.getColumnIndex("cod_adm"))
                );
                Log.i("debug", "ELemento: " +
                        cursor.getString(cursor.getColumnIndex("nome_adm"))
                );
                Log.i("debug", "ELemento: " +
                        cursor.getString(cursor.getColumnIndex("email_adm"))
                );
                Log.i("debug", "ELemento: " +
                        cursor.getString(cursor.getColumnIndex("senha_adm"))
                );
            } while (cursor.moveToNext());
        }
    }



    @SuppressLint("Range")
    public void insere_ocorrencia(String ocorrencia){
        this.populaSemPararOcorrencia(ocorrencia);
        String sql = "select * from ocorrencia;";
        Cursor cursor = this.database.rawQuery(sql,null);

        Log.i("debug", "Tamanho da tabela: " + cursor.getCount());
        if(cursor.moveToFirst()) {
            do {
                Log.i("debug", "ELemento: " +
                        cursor.getString(cursor.getColumnIndex("cod_ocorrencia"))
                );
                Log.i("debug", "ELemento: " +
                        cursor.getString(cursor.getColumnIndex("data"))
                );
                Log.i("debug", "ELemento: " +
                        cursor.getString(cursor.getColumnIndex("hora"))
                );
                Log.i("debug", "ELemento: " +
                        cursor.getString(cursor.getColumnIndex("descricao"))
                );
                Log.i("debug", "ELemento: " +
                        cursor.getString(cursor.getColumnIndex("categoria"))
                );
            } while (cursor.moveToNext());
        }
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        this.database = this.getWritableDatabase();

        Log.i("debug", "ccriando...");

        String x = "create table sistema(\n" +
                "cod_sistema int not null  primary key,\n" +
                "nome_sistema TEXT,\n" +
                "cnpj TEXT\n" +
                ");\n";
        this.database.execSQL(x);
        String k =
                "insert into sistema (nome_sistema,cnpj) values ( nome,cnpj);\n" +
                        "\n" +
                        "\n" ;
        database.execSQL(k);
        k =          "create table administrador(\n" +
                "cod_adm int  not null primary key,\n" +
                "nome_adm TEXT,\n" +
                "email_adm TEXT,\n" +
                "senha_adm TEXT,\n" +
                "cod_sistemapopulaSemPararOcorrencia int,\n" +
                "constraint fk1_cod_sistema\n" +
                "foreign key (cod_sistema)\n" +
                "references sistema (cod_sistema)\n" +
                ");\n";
        database.execSQL(k);
        k =
                "insert into administrador (nome_adm,email_adm,senha_adm,cod_sistema ) values\n" +
                        "(nome_adm,email_adm,senha_adm,cod_sistema );\n" +
                        "\n" +
                        "\n";
        database.execSQL(k);
        k =
                "update administrador\n" +
                        "set nome_adm = ''\n" +
                        "where cod_adm = '';\n" +
                        "\n" +
                        "\n";
        database.execSQL(k);
        k =
                "delete from administrador\n" +
                        "where cod_adm = cod;\n" +
                        "\n" +
                        "\n";
        database.execSQL(k);
        k =
                "create table usuario (\n" +
                        "cod_usuario int not null  primary key,\n" +
                        "nome_usuario TEXT,\n" +
                        "email_usuario TEXT,\n" +
                        "senha_usuario TEXT,\n" +
                        "status_usuario TEXT,\n" +
                        "cod_sistema int,\n" +
                        "constraint fk2_cod_sistema\n" +
                        "foreign key (cod_sistema)\n" +
                        "references sistema (cod_sistema)\n" +
                        ");\n" +
                        "\n";
        database.execSQL(k);
        k =


                "create trigger verifica_status_usuario before insert\n" +
                        "on usuario\n" +
                        "for each row\n" +
                        "begin update ocorrencia set status_ocorrencia ='ATIVO'; END\n" +
                        "\n";



        database.execSQL(k);
        k = "insert into usuario (nome_usuario,email_usuario,senha_usuario,cod_sistema)\n" +
                "values (nome_usuario,email_usuario,senha_usuario,cod_sistema);\n" +
                "\n";
        database.execSQL(k);
        k =
                "update usuario\n" +
                        "set nome_usuario = ''\n" +
                        "where cod_usuario = '';\n" +
                        "\n";
        database.execSQL(k);
        k =
                "delete from usuario\n" +
                        "where cod_usuario = cod;\n" ;
        database.execSQL(k);
        k =
                "create table ocorrencia (\n" +
                        "cod_ocorrencia int not null  primary key,\n" +
                        "data date,\n" +
                        "hora time,\n" +
                        "descricao TEXT,\n" +
                        "categoria TEXT,\n" +
                        "cod_sistema int,\n" +
                        "cod_usuario int,\n" +
                        "nome_usuario TEXT,\n" +
                        "status_ocorrencia TEXT,\n" +
                        "constraint fk3_cod_sistema\n" +
                        "foreign key (cod_sistema)\n" +
                        "references sistema (cod_sistema),\n" +
                        "constraint fk3_cod_usuario\n" +
                        "foreign key (cod_usuario)\n" +
                        "references usuario (cod_usuario)\n" +
                        ");\n" +
                        "\n";
        database.execSQL(k);
        k =

                "create trigger verifica_status_ocorrencia before insert\n" +
                        "on ocorrencia\n" +
                        "for each row\n" +
                        "begin update ocorrencia set status_ocorrencia ='OCORRENCIA REGISTRADA NO SISTEMA !'; END\n";

        database.execSQL(k);
        k =
                "insert into ocorrencia (data,hora,descricao,categoria,\n" +
                        "cod_sistema,cod_usuario,nome_usuario)\n" +
                        "values (data,hora,descricao,categoria,\n" +
                        "cod_sistema,cod_usuario,nome_usuario);\n" +
                        "\n" +
                        "\n" ;
        database.execSQL(k);
        k =
                "delete from ocorrencia\n" +
                        "where cod_ocorrencia = '';\n" +
                        "\n" ;
        database.execSQL(k);



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insere(String usuario) {
        Log.i("debug","teste de procedure");




    }
}