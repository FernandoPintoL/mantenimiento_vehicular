package com.fpl.mantenimientovehicular.model;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fpl.mantenimientovehicular.database.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ModeloVehiculo {
    private int id;
    private String marca;
    private String anho;
    private String placa;
    private String tipo;
    private int kilometraje;
    private static DataBaseHelper dbHelper;
    private static SQLiteDatabase db;
    private String table = "Vehiculo";
    public ModeloVehiculo() {
    }
    public ModeloVehiculo(Context context) {
        dbHelper = new DataBaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public ModeloVehiculo(int id, String marca, String anho, String placa, String tipo, int kilometraje) {
        this.id = id;
        this.marca = marca;
        this.anho = anho;
        this.placa = placa;
        this.tipo = tipo;
        this.kilometraje = kilometraje;
    }
    public void open() {
        db = dbHelper.getWritableDatabase();
    }
    public void close() {
        dbHelper.close();
    }
    public static void initDatabase(Context context) {
        dbHelper = new DataBaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    // CREATE
    public long agregar(ModeloVehiculo vehiculo) {
//        try(SQLiteDatabase db = dbHelper.getWritableDatabase()) {
        try{
            ContentValues values = new ContentValues();
            values.put("marca", vehiculo.getMarca());
            values.put("anho", vehiculo.getAño());
            values.put("placa", vehiculo.getPlaca());
            values.put("tipo", vehiculo.getTipo());
            values.put("kilometraje", vehiculo.getKilometraje());

            return db.insert(table, null, values);
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }
    // UPDATE
    public int actualizar(ModeloVehiculo vehiculo) {
//        try(SQLiteDatabase db = dbHelper.getWritableDatabase()){
        try {
            ContentValues values = new ContentValues();
            values.put("marca", vehiculo.getMarca());
            values.put("anho", vehiculo.getAño());
            values.put("placa", vehiculo.getPlaca());
            values.put("tipo", vehiculo.getTipo());
            values.put("kilometraje", vehiculo.getKilometraje());

            return db.update(table, values, "id = ?",
                    new String[]{String.valueOf(vehiculo.getId())});
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    // READ (Todos los vehículos)
    @SuppressLint("Range")
    public List<ModeloVehiculo> obtenerTodos() {
//        try(SQLiteDatabase db = dbHelper.getWritableDatabase()){
        try {
            List<ModeloVehiculo> vehiculos = new ArrayList<>();
            Cursor cursor = db.query(table, null, null, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    ModeloVehiculo vehiculo = new ModeloVehiculo();
                    vehiculo.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    vehiculo.setMarca(cursor.getString(cursor.getColumnIndex("marca")));
                    vehiculo.setAño(cursor.getString(cursor.getColumnIndex("anho")));
                    vehiculo.setPlaca(cursor.getString(cursor.getColumnIndex("placa")));
                    vehiculo.setTipo(cursor.getString(cursor.getColumnIndex("tipo")));
                    vehiculo.setKilometraje(cursor.getInt(cursor.getColumnIndex("kilometraje")));
                    vehiculos.add(vehiculo);
                } while (cursor.moveToNext());
            }
            cursor.close();
            return vehiculos;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    // READ (Un vehículo por ID)
    @SuppressLint("Range")
    public ModeloVehiculo obtenerPorId(int id) {
//        try (SQLiteDatabase db = dbHelper.getWritableDatabase()){
        try {
            Cursor cursor = db.query(table, null, "id = ?",
                    new String[]{String.valueOf(id)}, null, null, null);

            if (cursor.moveToFirst()) {
                ModeloVehiculo vehiculo = new ModeloVehiculo();
                vehiculo.setId(cursor.getInt(cursor.getColumnIndex("id")));
                vehiculo.setMarca(cursor.getString(cursor.getColumnIndex("marca")));
                vehiculo.setAño(cursor.getString(cursor.getColumnIndex("anho")));
                vehiculo.setPlaca(cursor.getString(cursor.getColumnIndex("placa")));
                vehiculo.setTipo(cursor.getString(cursor.getColumnIndex("tipo")));
                vehiculo.setKilometraje(cursor.getInt(cursor.getColumnIndex("kilometraje")));
                cursor.close();
                return vehiculo;
            }
            return null;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    // DELETE
    public int eliminar(int id) {
//        try(SQLiteDatabase db = dbHelper.getWritableDatabase()){
        try {
            return db.delete(table, "id = ?",
                    new String[]{String.valueOf(id)});
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getMarca() {
        return marca;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }
    public String getAño() {
        return anho;
    }
    public void setAño(String anho) {
        this.anho = anho;
    }
    public String getPlaca() {
        return placa;
    }
    public void setPlaca(String placa) {
        this.placa = placa;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public int getKilometraje() {
        return kilometraje;
    }
    public void setKilometraje(int kilometraje) {
        this.kilometraje = kilometraje;
    }
}