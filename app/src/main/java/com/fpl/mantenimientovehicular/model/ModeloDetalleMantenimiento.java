package com.fpl.mantenimientovehicular.model;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fpl.mantenimientovehicular.database.DataBaseHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ModeloDetalleMantenimiento {
    private int id;
    private int mantenimiento_id;
    private int item_id;
    private double precio_unitario;
    private double cantidad;
    private double subtotal;
    private String table = "DetalleMantenimiento";
    private static DataBaseHelper dbHelper;
    private static SQLiteDatabase db;
    public ModeloDetalleMantenimiento() {}
    public ModeloDetalleMantenimiento(Context context) {
        dbHelper = new DataBaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public static void initDatabase(DataBaseHelper dbHelper) {
        ModeloDetalleMantenimiento.dbHelper = dbHelper;
        db = dbHelper.getWritableDatabase();
    }
    public ModeloDetalleMantenimiento(int id, int mantenimiento_id, int item_id, double precio_unitario, double cantidad, double subtotal) {
        this.id = id;
        this.mantenimiento_id = mantenimiento_id;
        this.item_id = item_id;
        this.precio_unitario = precio_unitario;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }
    public void open() {
        db = dbHelper.getWritableDatabase();
    }
    public void close() {
        dbHelper.close();
    }
    public long agregar() {
        try {
            if (db == null) {
                throw new Exception("Error al abrir la base de datos");
            }
            ContentValues values = new ContentValues();
            values.put("mantenimiento_id", getMantenimiento_id());
            values.put("item_id", getItem_id());
            values.put("cantidad", getCantidad());
            values.put("precio_unitario", getPrecio_unitario());
            values.put("subtotal", getSubtotal());
            return db.insert(table, null, values);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    // READ (Todos los vehículos)
    @SuppressLint("Range")
    public List<ModeloDetalleMantenimiento> obtenerTodos() {
        try {
            if (db == null) {
                throw new Exception("Error al abrir la base de datos");
            }
            List<ModeloDetalleMantenimiento> listado = new ArrayList<>();
            Cursor cursor = db.query(table, null, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    ModeloDetalleMantenimiento model = new ModeloDetalleMantenimiento();
                    model.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    model.setMantenimiento_id(cursor.getColumnIndex("mantenimiento_id"));
                    model.setItem_id(cursor.getColumnIndex("item_id"));
                    model.setCantidad(Double.parseDouble(cursor.getString(cursor.getColumnIndex("cantidad"))));
                    model.setPrecio_unitario(Double.parseDouble(cursor.getString(cursor.getColumnIndex("precio_unitario"))));
                    model.setSubtotal(Double.parseDouble(cursor.getString(cursor.getColumnIndex("subtotal"))));

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
    public List<ModeloDetalleMantenimiento> obtenerPorId(int mantenimientoId) {
        try {
            if (db == null) {
                throw new Exception("Error al abrir la base de datos");
            }
            List<ModeloDetalleMantenimiento> listado = new ArrayList<>();
            Cursor cursor = db.query(table, null, "mantenimiento_id = ?",
                    new String[]{String.valueOf(mantenimientoId)}, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    ModeloDetalleMantenimiento model = new ModeloDetalleMantenimiento();
                    model.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    model.setMantenimiento_id(cursor.getInt(cursor.getColumnIndex("mantenimiento_id")));
                    model.setItem_id(cursor.getInt(cursor.getColumnIndex("item_id")));
                    model.setCantidad(Double.parseDouble(cursor.getString(cursor.getColumnIndex("cantidad"))));
                    model.setPrecio_unitario(Double.parseDouble(cursor.getString(cursor.getColumnIndex("precio_unitario"))));
                    model.setSubtotal(Double.parseDouble(cursor.getString(cursor.getColumnIndex("subtotal"))));
                    listado.add(model);
                } while (cursor.moveToNext());
            }

            cursor.close();
            return listado;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getMantenimiento_id() {
        return mantenimiento_id;
    }
    public void setMantenimiento_id(int mantenimiento_id) {this.mantenimiento_id = mantenimiento_id;}
    public int getItem_id() {
        return item_id;
    }
    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }
    public double getPrecio_unitario() {
        return precio_unitario;
    }
    public void setPrecio_unitario(double precio_unitario) {this.precio_unitario = precio_unitario;}
    public double getCantidad() {
        return cantidad;
    }
    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }
    public double getSubtotal() {
        return subtotal;
    }
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}
