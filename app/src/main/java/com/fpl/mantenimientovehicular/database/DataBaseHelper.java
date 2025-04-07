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
                    "modelo TEXT NOT NULL, " +
                    "año TEXT NOT NULL, " +
                    "placa TEXT NOT NULL UNIQUE, " +
                    "tipo TEXT NOT NULL, " +
                    "kilometraje INTEGER NOT NULL);";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables here
        db.execSQL(CREATE_TABLE_VEHICULOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrade here
        db.execSQL("DROP TABLE IF EXISTS vehiculos");
        onCreate(db);
    }
}
