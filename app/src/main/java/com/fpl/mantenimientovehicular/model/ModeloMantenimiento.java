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
    private int kilometraje_mantenimiento;
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
    public ModeloMantenimiento(int id,int vehiculo_id, int mecanico_id, String fecha, int kilometraje_mantenimiento, String detalle, Double costo_total) {
        this.id = id;
        this.vehiculo_id = vehiculo_id;
        this.mecanico_id = mecanico_id;
        this.fecha = fecha;
        this.kilometraje_mantenimiento = kilometraje_mantenimiento;
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

    public long agregar() {
        try{
            if (db == null) {
                throw new Exception("Error al abrir la base de datos");
            }
            ContentValues values = new ContentValues();
            values.put("fecha", getFecha());
            values.put("kilometraje_mantenimiento", getKilometrajeMantenimineto());
            values.put("detalle", getDetalle());
            values.put("costo_total", getCosto_total());
            values.put("vehiculo_id", getVehiculo_id());
            values.put("mecanico_id", getMecanico_id());
            return db.insert(table, null, values);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    // READ (Todos los vehículos)
    @SuppressLint("Range")
    public List<ModeloMantenimiento> obtenerTodos() {
        try{
            if (db == null) {
                throw new Exception("Error al abrir la base de datos");
            }
            List<ModeloMantenimiento> listado = new ArrayList<>();
            Cursor cursor = db.query(table, null, null, null, null, null, "id DESC");

            if (cursor.moveToFirst()) {
                do {
                    ModeloMantenimiento model = new ModeloMantenimiento();
                    model.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    model.setMecanico_id(cursor.getInt(cursor.getColumnIndex("mecanico_id")));
                    model.setVehiculo_id(cursor.getInt(cursor.getColumnIndex("vehiculo_id")));
                    model.setKilometrajeMantenimiento(cursor.getInt(cursor.getColumnIndex("kilometraje_mantenimiento")));
                    model.setCosto_total(Double.parseDouble(cursor.getString(cursor.getColumnIndex("costo_total"))));
                    model.setDetalle(cursor.getString(cursor.getColumnIndex("detalle")));
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
        try {
            Cursor cursor = db.query(table, null, "id = ?",
                    new String[]{String.valueOf(id)}, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                ModeloMantenimiento model = new ModeloMantenimiento();
                model.setId(cursor.getInt(cursor.getColumnIndex("id")));
                model.setMecanico_id(cursor.getColumnIndex("mecanico_id"));
                model.setVehiculo_id(cursor.getColumnIndex("vehiculo_id"));
                model.setFecha(cursor.getString(cursor.getColumnIndex("fecha")));
                model.setKilometrajeMantenimiento(cursor.getInt(cursor.getColumnIndex("kilometraje_mantenimiento")));
                model.setDetalle(cursor.getString(cursor.getColumnIndex("detalle")));
                model.setCosto_total(Double.parseDouble(cursor.getString(cursor.getColumnIndex("costo_total"))));
                cursor.close();
                return model;
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    // DELETE
    public int eliminar(int id) {
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
    public int getKilometrajeMantenimineto() {return kilometraje_mantenimiento;}
    public void setKilometrajeMantenimiento(int kilometraje) {this.kilometraje_mantenimiento = kilometraje;}
    public String getDetalle() {return detalle;}
    public void setDetalle(String detalle) {this.detalle = detalle;}
    public Double getCosto_total() {return costo_total;}
    public void setCosto_total(Double costo_total) {this.costo_total = costo_total;}
    public int getVehiculo_id() {return vehiculo_id;}
    public void setVehiculo_id(int vehiculo_id) {this.vehiculo_id = vehiculo_id;}
    public int getMecanico_id() {return mecanico_id;}
    public void setMecanico_id(int mecanico_id) {this.mecanico_id = mecanico_id;}
}

