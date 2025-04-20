package com.fpl.mantenimientovehicular.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "mantenimiento_vehicular.db";
    public static final int DATABASE_VERSION = 1;

    // Sentencia SQL para crear la tabla de vehículos
    private static final String CREATE_TABLE_VEHICULOS =
            "CREATE TABLE Vehiculo (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "marca TEXT NOT NULL, " +
                    "anho TEXT NOT NULL, " +
                    "placa TEXT NOT NULL UNIQUE, " +
                    "tipo TEXT NOT NULL, " +
                    "kilometraje_esperado INTEGER NOT NULL, " +
                    "kilometraje_actual INTEGER NOT NULL);";
    private static final String CREATE_TABLE_MECANICO =
            "CREATE TABLE Mecanico (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nombre TEXT NOT NULL, " +
                    "taller TEXT NOT NULL, " +
                    "direccion TEXT NOT NULL, " +
                    "telefono TEXT NOT NULL);";
    private static final String CREATE_TABLE_ITEM =
            "CREATE TABLE Item (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nombre TEXT NOT NULL, " +
                    "precio REAL NOT NULL, " +
                    "detalle TEXT NOT NULL); ";

    private static final String CREATE_TABLE_MANTENIMIENTO =
            "CREATE TABLE Mantenimiento (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "vehiculo_id INTEGER, " +
                    "mecanico_id INTEGER, " +
                    "fecha TEXT NOT NULL, " +
                    "detalle TEXT NOT NULL, " +
                    "costo_total REAL NOT NULL, " +
                    "kilometraje_mantenimiento INTEGER NOT NULL, " +
                    "FOREIGN KEY(vehiculo_id) REFERENCES Vehiculo (id), " +
                    "FOREIGN KEY(mecanico_id) REFERENCES Mecanico (id))";
    private static final String CREATE_TABLE_DETALLE_MANTENIMIENTO =
            "CREATE TABLE DetalleMantenimiento (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "mantenimiento_id INTEGER, " +
                    "item_id INTEGER, " +
                    "cantidad Real NOT NULL, " +
                    "precio_unitario REAL NOT NULL, " +
                    "subtotal REAL NOT NULL, " +
                    "FOREIGN KEY(mantenimiento_id) REFERENCES Mantenimiento (id), " +
                    "FOREIGN KEY(item_id) REFERENCES Item (id));";
    private static final String CREATE_TABLE_NOTIFICACION =
            "CREATE TABLE Notificacion (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "vehiculo_id INTEGER, " +
                    "kilometraje_objetivo INTEGER, " +
                    "mensaje TEXT, " +
                    "activo BOOLEAN, " +
                    "FOREIGN KEY(vehiculo_id) REFERENCES Vehiculo (id));";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables here
        db.execSQL(CREATE_TABLE_VEHICULOS);
        db.execSQL(CREATE_TABLE_MECANICO);
        db.execSQL(CREATE_TABLE_ITEM);
        db.execSQL(CREATE_TABLE_MANTENIMIENTO);
        db.execSQL(CREATE_TABLE_DETALLE_MANTENIMIENTO);
        db.execSQL(CREATE_TABLE_NOTIFICACION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrade here
        db.execSQL("DROP TABLE IF EXISTS Vehiculo");
        db.execSQL("DROP TABLE IF EXISTS Mecanico");
        db.execSQL("DROP TABLE IF EXISTS Item");
        db.execSQL("DROP TABLE IF EXISTS Mantenimiento");
        db.execSQL("DROP TABLE IF EXISTS DetalleMantenimiento");
        db.execSQL("DROP TABLE IF EXISTS Notificacion");
        onCreate(db);
    }
}
