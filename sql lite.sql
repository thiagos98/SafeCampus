package com.example.aula_25_09;

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






public class sistema_seguranca  extends SQLiteOpenHelper {

    public SQLiteDatabase database;
    public sistema_seguranca(Context context) {
        super(context, "banco2.db", null, 1);
        Log.i("debug", "conexao com o banco...");
        database = this.getWritableDatabase();
    }

    public void populaSemParar(String usuario){
        String inserir = "Insert into sistema(cod_sistema, nome_sistema, cnpj) values(1, '" +usuario + "', '" + usuario + "');";

        this.database.execSQL(inserir);
    }

    @SuppressLint("Range")
    public void insere(String usuario){
        this.populaSemParar(usuario);
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

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String x = "create table sistema(\n" +
                "cod_sistema int not null  primary key,\n" +
                "nome_sistema TEXT,\n" +
                "cnpj TEXT\n" +
                ");\n" +
                "\n" +
                "delimiter $$\n" +
                "create procedure sp_cadastro_sistema(nome TEXT,cnpj TEXT)\n" +
                "begin\n" +
                "insert into sistema (nome_sistema,cnpj) values ( nome,cnpj);\n" +
                "end $$\n" +
                "delimiter ;\n" +
                "\n" +
                "call sp_cadastro_sistema();\n" +
                "select * from sistema;\n" +
                "\n" +
                "create table administrador(\n" +
                "cod_adm int  not null primary key,\n" +
                "nome_adm TEXT,\n" +
                "email_adm TEXT,\n" +
                "senha_adm TEXT,\n" +
                "cod_sistema int,\n" +
                "constraint fk1_cod_sistema\n" +
                "foreign key (cod_sistema)\n" +
                "references sistema (cod_sistema)\n" +
                ");\n" +
                "delimiter $$\n" +
                "\n" +
                "create procedure sp_cadastro_administrador(nome_adm TEXT,email_adm TEXT,\n" +
                "senha_adm TEXT,cod_sistema int)\n" +
                "begin\n" +
                "insert into administrador (nome_adm,email_adm,senha_adm,cod_sistema ) values\n" +
                "(nome_adm,email_adm,senha_adm,cod_sistema );\n" +
                "end $$\n" +
                "delimiter ;\n" +
                "\n" +
                "call sp_cadastro_administrador();\n" +
                "select * from administrador;\n" +
                "\n" +
                "delimiter $$\n" +
                "create procedure alterar_informação_do_administrador( cod int, nome_adm TEXT,email_adm TEXT,senha_adm TEXT)\n" +
                "begin\n" +
                "if exists(select cod_adm from administrador where (cod_adm = cod_adm )) then\n" +
                "update administrador\n" +
                "set nome_adm = @nome_adm,\n" +
                "email_adm = @email_adm,\n" +
                "senha_adm = @senha_adm\n" +
                "where cod_adm = cod;\n" +
                "end if;\n" +
                "end $$\n" +
                "delimiter ;\n" +
                "\n" +
                "set @nome_adm = '';\n" +
                "set @email_adm ='';\n" +
                "set @senha_adm = '';\n" +
                "call alterar_informação_do_administrador('',@nome_adm,@email_adm,@senha_adm);\n" +
                "select * from administrador\n" +
                "where cod_adm = '';\n" +
                "\n" +
                "delimiter $$\n" +
                "create procedure excluir_o_administrador( cod int)\n" +
                "begin\n" +
                "if exists(select cod_adm from administrador where (cod_adm = cod_adm )) then\n" +
                "delete from administrador\n" +
                "where cod_adm = cod;\n" +
                "end if;\n" +
                "end $$\n" +
                "delimiter ;\n" +
                "\n" +
                "SET FOREIGN_KEY_CHECKS = 0;\n" +
                "set @cod= '';\n" +
                "call excluir_o_administrador(@cod);\n" +
                "select * from administrador;\n" +
                "\n" +
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
                "\n" +
                "create trigger verifica_status_usuario before insert\n" +
                "on usuario\n" +
                "for each row\n" +
                "set new.status_usuario ='ATIVO';\n" +
                "delimiter $$\n" +
                "\n" +
                "create procedure sp_cadastro_usuario (nome_usuario TEXT,email_usuario TEXT,senha_usuario TEXT,cod_sistema int )\n" +
                "begin\n" +
                "insert into usuario (nome_usuario,email_usuario,senha_usuario,cod_sistema)\n" +
                "values (nome_usuario,email_usuario,senha_usuario,cod_sistema);\n" +
                "end $$\n" +
                "delimiter ;\n" +
                "\n" +
                "call sp_cadastro_usuario ();\n" +
                "select * from usuario;\n" +
                "\n" +
                "delimiter $$\n" +
                "create procedure alterar_informação_do_usuario( cod int, nome_usuario TEXT,email_usuario TEXT,senha_usuario TEXT,status_usuario TEXT)\n" +
                "begin\n" +
                "if exists(select cod_usuario from usuario where (cod_usuario = cod_usuario )) then\n" +
                "update usuario\n" +
                "set nome_usuario = @nome_usuario,\n" +
                "email_usuario = @email_usuario,\n" +
                "senha_usuario = @senha_usuario,\n" +
                "status_usuario = @status_usuario\n" +
                "where cod_usuario = cod;\n" +
                "end if;\n" +
                "end $$\n" +
                "delimiter ;\n" +
                "\n" +
                "set @nome_usuario = '';\n" +
                "set @email_usuario = '';\n" +
                "set @senha_usuario = '';\n" +
                "set @status_usuario = '';\n" +
                "call alterar_informação_do_usuario('',@nome_usuario,@email_usuario,@senha_usuario,@status_usuario);\n" +
                "select * from usuario\n" +
                "where cod_usuario = '';\n" +
                "\n" +
                "delimiter $$\n" +
                "create procedure excluir_o_usuario( cod int)\n" +
                "begin\n" +
                "if exists(select cod_usuario from usuario where (cod_usuario = cod_usuario )) then\n" +
                "delete from usuario\n" +
                "where cod_usuario = cod;\n" +
                "end if;\n" +
                "end $$\n" +
                "delimiter ;\n" +
                "SET FOREIGN_KEY_CHECKS = 0;\n" +
                "set @cod= '';\n" +
                "call excluir_o_usuario(@cod);\n" +
                "select * from usuario;\n" +
                "\n" +
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
                "\n" +
                "create trigger verifica_status_ocorrencia before insert\n" +
                "on ocorrencia\n" +
                "for each row\n" +
                "set new.status_ocorrencia ='OCORRENCIA REGISTRADA NO SISTEMA !';\n" +
                "\n" +
                "delimiter $$\n" +
                "create procedure sp_cadastro_ocorrencia (data date,hora time,descricao TEXT,\n" +
                "categoria TEXT,cod_sistema int,cod_usuario int,nome_usuario TEXT)\n" +
                "begin\n" +
                "insert into ocorrencia (data,hora,descricao,categoria,\n" +
                "cod_sistema,cod_usuario,nome_usuario)\n" +
                "values (data,hora,descricao,categoria,\n" +
                "cod_sistema,cod_usuario,nome_usuario);\n" +
                "end $$\n" +
                "delimiter ;\n" +
                "\n" +
                "call sp_cadastro_ocorrencia ();\n" +
                "select * from ocorrencia;\n" +
                "\n" +
                "delimiter $$\n" +
                "create procedure excluir_a_ocorrencia( cod int)\n" +
                "begin\n" +
                "if exists(select cod_ocorrencia from ocorrencia where (cod_ocorrencia = cod_ocorrencia )) then\n" +
                "delete from ocorrencia\n" +
                "where cod_ocorrencia = cod;\n" +
                "end if;\n" +
                "end $$\n" +
                "delimiter ;\n" +
                "\n" +
                "set @cod= '';\n" +
                "call excluir_a_ocorrencia(@cod);\n" +
                "select * from ocorrencia;\n" +
                "\n" +
                "select nome_usuario\n" +
                "from usuario\n" +
                "order by nome_usuario asc;\n" +
                "\n" +
                "delimiter $$\n" +
                "create function verificar_administradores_existentes(email_adm varchar (30),senha_adm TEXT)\n" +
                "returns TEXT\n" +
                "begin\n" +
                "declare mensagem TEXT;\n" +
                "case\n" +
                "when email_adm = 'email cadastrado aqui' and senha_adm = 'senha cadastrada aqui' then\n" +
                "set mensagem ='ACESSO PERMITIDO.VOCÊ POSSUI PRIVILEGIOS DE ADMINISTRADOR';\n" +
                "when email_adm = 'email cadastrado aqui'  and senha_adm = 'senha cadastrada aqui' then\n" +
                "set mensagem ='ACESSO PERMITIDO.VOCÊ POSSUI PRIVILEGIOS DE ADMINISTRADOR';\n" +
                "else\n" +
                "set mensagem ='ACESSO NEGADO. SEM PRIVILEGIOS DE ADMINISTRADOR';\n" +
                "end case;\n" +
                "return mensagem;\n" +
                "end $$\n" +
                "delimiter ;\n" +
                "\n" +
                "select verificar_administradores_existentes() as 'STATUS DA SITUAÇÃO';\n" +
                "\n" +
                "select usuario.nome_usuario,email_usuario,data,hora,descricao\n" +
                "from usuario\n" +
                "inner join ocorrencia\n" +
                "on usuario.cod_usuario = ocorrencia.cod_usuario;";

        sqLiteDatabase.execSQL(x);





        //String inserir = "Insert into sistema(cod_sistema, nome_sistema, cnpj) values(1, 'kkk', 'zzzZZZ');";




        //sqLiteDatabase.execSQL(inserir);
        //sqLiteDatabase.execSQL(x2);
        //sqLiteDatabase.execSQL(inserir2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
