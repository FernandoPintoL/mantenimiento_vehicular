package com.fpl.mantenimientovehicular.model;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fpl.mantenimientovehicular.database.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ModeloItem {
    private int id;
    private String nombre;
    private double precio;
    private String detalle;
    private String table = "Item";
    private static DataBaseHelper dbHelper;
    private static SQLiteDatabase db;
    public ModeloItem() {
    }
    public ModeloItem(Context context){
        dbHelper = new DataBaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public ModeloItem(int id, String nombre, double precio, String detalle) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.detalle = detalle;
    }
    public static void initDatabase(Context context) {
        dbHelper = new DataBaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public void open() {
        db = dbHelper.getWritableDatabase();
    }
    public void close() {
        dbHelper.close();
    }
    public long agregar(ModeloItem modelo) {
//        try(SQLiteDatabase db = dbHelper.getWritableDatabase()) {
        try{
            if (db == null) {
                throw new Exception("Error al abrir la base de datos");
            }
            ContentValues values = new ContentValues();
            values.put("nombre", modelo.getNombre());
            values.put("precio", modelo.getPrecio());
            values.put("detalle", modelo.getDetalle());
            return db.insert(table, null, values);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    // READ (Todos los vehículos)
    @SuppressLint("Range")
    public List<ModeloItem> obtenerTodos() {
//        try(SQLiteDatabase db = dbHelper.getWritableDatabase()) {
        try{
            if (db == null) {
                throw new Exception("Error al abrir la base de datos");
            }
            List<ModeloItem> listado = new ArrayList<>();
            Cursor cursor = db.query(table, null, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    ModeloItem model = new ModeloItem();
                    model.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    model.setNombre(cursor.getString(cursor.getColumnIndex("nombre")));
                    model.setPrecio(Double.parseDouble(cursor.getString(cursor.getColumnIndex("precio"))));
                    model.setDetalle(cursor.getString(cursor.getColumnIndex("detalle")));
                    listado.add(model);
                } while (cursor.moveToNext());
            }
            cursor.close();
            return listado;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    // READ (Un vehículo por ID)
    @SuppressLint("Range")
    public ModeloItem obtenerPorId(int id) {
//        try (SQLiteDatabase db = dbHelper.getWritableDatabase()){
        try {
            Cursor cursor = db.query(table, null, "id = ?",
                    new String[]{String.valueOf(id)}, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                ModeloItem model = new ModeloItem();
                model.setId(cursor.getInt(cursor.getColumnIndex("id")));
                model.setNombre(cursor.getString(cursor.getColumnIndex("nombre")));
                model.setPrecio(Double.parseDouble(cursor.getString(cursor.getColumnIndex("precio"))));
                model.setDetalle(cursor.getString(cursor.getColumnIndex("detalle")));
                cursor.close();
                return model;
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    // UPDATE
    public int actualizar(ModeloItem model) {
//        try(SQLiteDatabase db = dbHelper.getWritableDatabase()){
        try{
            ContentValues values = new ContentValues();
            values.put("nombre", model.getNombre());
            values.put("precio", model.getPrecio());
            values.put("detalle", model.getDetalle());

            return db.update(table, values, "id = ?",
                    new String[]{String.valueOf(model.getId())});
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    // DELETE
    public int eliminar(int id) {
//        try (SQLiteDatabase db = dbHelper.getWritableDatabase()){
        try {
            if (db == null) {
                throw new Exception("Error al abrir la base de datos");
            }
            return db.delete(table, "id = ?",
                    new String[]{String.valueOf(id)});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public double getPrecio() {
        return precio;
    }
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    public String getDetalle() {
        return detalle;
    }
    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
}
