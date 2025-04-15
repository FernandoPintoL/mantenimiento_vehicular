package com.fpl.mantenimientovehicular.model;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fpl.mantenimientovehicular.database.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ModeloMecanico {
    private int id;
    private String nombre;
    private String taller;
    private String telefono;
    private String direccion;
    private static DataBaseHelper dbHelper;
    private static SQLiteDatabase db;
    private String table = "Mecanico";
    public ModeloMecanico() {
    }
    public ModeloMecanico(Context context) {
        dbHelper = new DataBaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public ModeloMecanico(int id, String nombre, String telefono, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
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
    public long agregar(ModeloMecanico modelo) {
//        try(SQLiteDatabase db = dbHelper.getWritableDatabase()) {
        try{
            if (db == null) {
                throw new Exception("Error al abrir la base de datos");
            }
            ContentValues values = new ContentValues();
            values.put("nombre", modelo.getNombre());
            values.put("taller", modelo.getTaller());
            values.put("direccion", modelo.getDireccion());
            values.put("telefono", modelo.getTelefono());
            return db.insert(table, null, values);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    // READ (Todos los vehículos)
    @SuppressLint("Range")
    public List<ModeloMecanico> obtenerTodos() {
//        try(SQLiteDatabase db = dbHelper.getWritableDatabase()) {
        try{
            if (db == null) {
                throw new Exception("Error al abrir la base de datos");
            }
            List<ModeloMecanico> listado = new ArrayList<>();
            Cursor cursor = db.query(table, null, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    ModeloMecanico model = new ModeloMecanico();
                    model.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    model.setNombre(cursor.getString(cursor.getColumnIndex("nombre")));
                    model.setTaller(cursor.getString(cursor.getColumnIndex("taller")));
                    model.setDireccion(cursor.getString(cursor.getColumnIndex("direccion")));
                    model.setTelefono(cursor.getString(cursor.getColumnIndex("telefono")));
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
    public ModeloMecanico obtenerPorId(int id) {
//        try (SQLiteDatabase db = dbHelper.getWritableDatabase()){
        try {
            Cursor cursor = db.query(table, null, "id = ?",
                    new String[]{String.valueOf(id)}, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                ModeloMecanico model = new ModeloMecanico();
                model.setId(cursor.getInt(cursor.getColumnIndex("id")));
                model.setNombre(cursor.getString(cursor.getColumnIndex("nombre")));
                model.setTaller(cursor.getString(cursor.getColumnIndex("taller")));
                model.setDireccion(cursor.getString(cursor.getColumnIndex("direccion")));
                model.setTelefono(cursor.getString(cursor.getColumnIndex("telefono")));
                cursor.close();
                return model;
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    // UPDATE
    public int actualizar(ModeloMecanico model) {
//        try(SQLiteDatabase db = dbHelper.getWritableDatabase()){
        try{
            ContentValues values = new ContentValues();
            values.put("nombre", model.getNombre());
            values.put("taller", model.getTaller());
            values.put("direccion", model.getDireccion());
            values.put("telefono", model.getTelefono());

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
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getTaller() {
        return taller;
    }
    public void setTaller(String taller) {
        this.taller = taller;
    }
}
