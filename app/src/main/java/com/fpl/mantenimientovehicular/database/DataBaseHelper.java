package com.fpl.mantenimientovehicular.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "mantenimiento_vehicular.db";
    public static final int DATABASE_VERSION = 1;

    // Sentencia SQL para crear la tabla de vehículos
    private static final String CREATE_TABLE_VEHICULOS =
            "CREATE TABLE vehiculos (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "marca TEXT NOT NULL, " +
                    "anho TEXT NOT NULL, " +
                    "placa TEXT NOT NULL UNIQUE, " +
                    "tipo TEXT NOT NULL, " +
                    "kilometraje INTEGER NOT NULL);";
    private static final String CREATE_TABLE_MECANICO =
            "CREATE TABLE mecanico (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nombre TEXT NOT NULL, " +
                    "taller TEXT NOT NULL, " +
                    "direccion TEXT NOT NULL, " +
                    "telefono TEXT NOT NULL);";
    private static final String CREATE_TABLE_ITEM =
            "CREATE TABLE item (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nombre TEXT NOT NULL, " +
                    "precio REAL NOT NULL, " +
                    "detalle TEXT NOT NULL); ";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables here
        db.execSQL(CREATE_TABLE_VEHICULOS);
        db.execSQL(CREATE_TABLE_MECANICO);
        db.execSQL(CREATE_TABLE_ITEM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrade here
        db.execSQL("DROP TABLE IF EXISTS vehiculos");
        db.execSQL("DROP TABLE IF EXISTS mecanico");
        db.execSQL("DROP TABLE IF EXISTS item");
        onCreate(db);
    }
}
