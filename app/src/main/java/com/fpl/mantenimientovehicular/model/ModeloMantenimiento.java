package com.fpl.mantenimientovehicular.model;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fpl.mantenimientovehicular.database.DataBaseHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Locale;


public class ModeloMantenimiento {
    private int id;
    private String fecha;
    private Double kilometraje;
    private String detalle;
    private Double costo_total;
    private int vehiculo_id;
    private int mecanico_id;
    private String table = "Mantenimiento";
    private static DataBaseHelper dbHelper;
    private static SQLiteDatabase db;
    public ModeloMantenimiento(){}
    public ModeloMantenimiento(Context context){
        dbHelper = new DataBaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public ModeloMantenimiento(int id, String fecha, Double kilometraje, String detalle, Double costo_total) {
        this.id = id;
        this.fecha = fecha;
        this.kilometraje = kilometraje;
        this.detalle = detalle;
        this.costo_total = costo_total;
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

    public long agregar(ModeloMantenimiento modelo) {
//        try(SQLiteDatabase db = dbHelper.getWritableDatabase()) {
        try{
            if (db == null) {
                throw new Exception("Error al abrir la base de datos");
            }
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
            ContentValues values = new ContentValues();
//            values.put("fecha", sdf.format(modelo.getFecha()));
            values.put("fecha", modelo.getFecha().toString());
            values.put("kilometraje", modelo.getKilometraje());
            values.put("detalle", modelo.getDetalle());
            values.put("costo_total", modelo.getCosto_total());
            values.put("vehiculo_id", modelo.getVehiculo_id());
            values.put("mecanico_id", modelo.getMecanico_id());
            return db.insert(table, null, values);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    // READ (Todos los vehículos)
    @SuppressLint("Range")
    public List<ModeloMantenimiento> obtenerTodos() {
//        try(SQLiteDatabase db = dbHelper.getWritableDatabase()) {
        try{
            if (db == null) {
                throw new Exception("Error al abrir la base de datos");
            }
            List<ModeloMantenimiento> listado = new ArrayList<>();
            Cursor cursor = db.query(table, null, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    ModeloMantenimiento model = new ModeloMantenimiento();
                    model.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    model.setMecanico_id(cursor.getColumnIndex("mecanico_id"));
                    model.setVehiculo_id(cursor.getColumnIndex("vehiculo_id"));
                    model.setKilometraje(Double.parseDouble(cursor.getString(cursor.getColumnIndex("kilometraje"))));
                    model.setCosto_total(Double.parseDouble(cursor.getString(cursor.getColumnIndex("costo_total"))));
                    model.setDetalle(cursor.getString(cursor.getColumnIndex("detalle")));

                    /*String dateString =  cursor.getString(cursor.getColumnIndex("fecha"));
                    SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
                    Date date = format.parse(dateString);
                    model.setFecha(date);
                    Date date = getFechaFromCursor(cursor);*/
                    model.setFecha(cursor.getString(cursor.getColumnIndex("fecha")));
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
    public ModeloMantenimiento obtenerPorId(int id) {
//        try (SQLiteDatabase db = dbHelper.getWritableDatabase()){
        try {
            Cursor cursor = db.query(table, null, "id = ?",
                    new String[]{String.valueOf(id)}, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                ModeloMantenimiento model = new ModeloMantenimiento();
                model.setId(cursor.getInt(cursor.getColumnIndex("id")));
                model.setKilometraje(Double.parseDouble(cursor.getString(cursor.getColumnIndex("precio"))));
                model.setCosto_total(Double.parseDouble(cursor.getString(cursor.getColumnIndex("costo_total"))));
                model.setDetalle(cursor.getString(cursor.getColumnIndex("detalle")));
                // Convertir la fecha de String a Date
                /*String fechaString = cursor.getString(cursor.getColumnIndex("fecha"));
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date fecha = dateFormat.parse(fechaString);*/
                model.setFecha(cursor.getString(cursor.getColumnIndex("fecha")));
                cursor.close();
                return model;
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    // UPDATE
    public int actualizar(ModeloMantenimiento model) {
//        try(SQLiteDatabase db = dbHelper.getWritableDatabase()){
        try{
            ContentValues values = new ContentValues();
            values.put("fecha", model.getFecha().toString());
            values.put("kilometraje", model.getKilometraje());
            values.put("detalle", model.getDetalle());
            values.put("costo_total", model.getCosto_total());

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
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getFecha() {return fecha;}
    public void setFecha(String fecha) {this.fecha = fecha;}
    public Double getKilometraje() {return kilometraje;}
    public void setKilometraje(Double kilometraje) {this.kilometraje = kilometraje;}
    public String getDetalle() {return detalle;}
    public void setDetalle(String detalle) {this.detalle = detalle;}
    public Double getCosto_total() {return costo_total;}
    public void setCosto_total(Double costo_total) {this.costo_total = costo_total;}
    public int getVehiculo_id() {return vehiculo_id;}
    public void setVehiculo_id(int vehiculo_id) {this.vehiculo_id = vehiculo_id;}
    public int getMecanico_id() {return mecanico_id;}
    public void setMecanico_id(int item_id) {this.mecanico_id = item_id;}
}

