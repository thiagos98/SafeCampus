package com.iartes.safecampus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "safe_campus.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Defina a estrutura da tabela para armazenar seus dados
        String createTableQuery = "CREATE TABLE incidentes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "data TEXT," +
                "hora TEXT," +
                "descricao TEXT," +
                "categoria TEXT," +
                "latitude TEXT," +
                "longitude TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Atualização do esquema do banco de dados, se necessário
    }

    public void inserirIncidente(String data, String hora, String descricao, String categoria, String latitude, String longitude) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("data", data);
        values.put("hora", hora);
        values.put("descricao", descricao);
        values.put("categoria", categoria);
        values.put("latitude", latitude);
        values.put("longitude", longitude);

        long newRowId = db.insert("incidentes", null, values);

        // Verifique newRowId para determinar se a inserção foi bem-sucedida
        // e faça qualquer tratamento de erro necessário
    }

    public List<Incidente> getAllIncidentes() {
        List<Incidente> incidentes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                "id",
                "data",
                "hora",
                "descricao",
                "categoria",
                "latitude",
                "longitude"
        };

        Cursor cursor = db.query(
                "incidentes",
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow("id"));
            String data = cursor.getString(cursor.getColumnIndexOrThrow("data"));
            String hora = cursor.getString(cursor.getColumnIndexOrThrow("hora"));
            String descricao = cursor.getString(cursor.getColumnIndexOrThrow("descricao"));
            String categoria = cursor.getString(cursor.getColumnIndexOrThrow("categoria"));
            String latitude = cursor.getString(cursor.getColumnIndexOrThrow("latitude"));
            String longitude = cursor.getString(cursor.getColumnIndexOrThrow("longitude"));

            Incidente incidente = new Incidente(id, data, hora, descricao, categoria, latitude, longitude);
            incidentes.add(incidente);
        }

        cursor.close();

        return incidentes;
    }


}

