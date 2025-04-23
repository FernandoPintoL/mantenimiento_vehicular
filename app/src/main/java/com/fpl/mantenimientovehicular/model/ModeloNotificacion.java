package com.fpl.mantenimientovehicular.model;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fpl.mantenimientovehicular.database.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ModeloNotificacion {
    private int id;
    private int idVehiculo;
    private String title;
    private String mensaje;
    private int kilometrajeObjetivo;
    private int intervalo_notificacion;
    private boolean activa;
    private String table = "Notificacion";
    private static DataBaseHelper dbHelper;
    private static SQLiteDatabase db;
    public ModeloNotificacion() {}
    public ModeloNotificacion(int id, int idVehiculo, String title, String mensaje, int kilometrajeObjetivo, int intervalo_notificacion, boolean activa) {
        this.id = id;
        this.idVehiculo = idVehiculo;
        this.mensaje = mensaje;
        this.kilometrajeObjetivo = kilometrajeObjetivo;
        this.activa = activa;
        this.title = title;
        this.intervalo_notificacion = intervalo_notificacion;
    }
    public ModeloNotificacion(Context context){
        dbHelper = new DataBaseHelper(context);
        db = dbHelper.getWritableDatabase();
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
    public long agregar(ModeloNotificacion modelo) {
        try{
            if (db == null) {
                throw new Exception("Error al abrir la base de datos");
            }
            ContentValues values = new ContentValues();
            values.put("vehiculo_id", modelo.getIdVehiculo());
            values.put("titulo", modelo.getTitle());
            values.put("mensaje", modelo.getMensaje());
            values.put("kilometraje_objetivo", modelo.getKilometrajeObjetivo());
            values.put("intervalo_notificacion", modelo.getIntervalo_notificacion());
            values.put("activo", modelo.isActiva());
            return db.insert(table, null, values);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public int modificar(ModeloNotificacion modelo) {
        try{
            if (db == null) {
                throw new Exception("Error al abrir la base de datos");
            }
            ContentValues values = new ContentValues();
            values.put("vehiculo_id", modelo.getIdVehiculo());
            values.put("titulo", modelo.getTitle());
            values.put("mensaje", modelo.getMensaje());
            values.put("kilometraje_objetivo", modelo.getKilometrajeObjetivo());
            values.put("intervalo_notificacion", modelo.getIntervalo_notificacion());
            values.put("activo", modelo.isActiva());
            return db.update(table, values, "id=?", new String[]{String.valueOf(modelo.getId())});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public int eliminar(int id) {
        try{
            if (db == null) {
                throw new Exception("Error al abrir la base de datos");
            }
            return db.delete(table, "id=?", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @SuppressLint("Range")
    public List<ModeloNotificacion> obtenerTodos() {
        try {
            if (db == null) {
                throw new Exception("Error al abrir la base de datos");
            }
            List<ModeloNotificacion> listado = new ArrayList<>();
            Cursor cursor = db.query(table, null, null, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    ModeloNotificacion modelo = new ModeloNotificacion();
                    modelo.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    modelo.setIdVehiculo(cursor.getInt(cursor.getColumnIndex("vehiculo_id")));
                    modelo.setTitle(cursor.getString(cursor.getColumnIndex("titulo")));
                    modelo.setMensaje(cursor.getString(cursor.getColumnIndex("mensaje")));
                    modelo.setKilometrajeObjetivo(cursor.getInt(cursor.getColumnIndex("kilometraje_objetivo")));
                    modelo.setIntervalo_notificacion(cursor.getInt(cursor.getColumnIndex("intervalo_notificacion")));
                    modelo.setActiva(cursor.getInt(cursor.getColumnIndex("activo")) > 0);

                    listado.add(modelo);
                } while (cursor.moveToNext());
            }
            cursor.close();
            return listado;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    @SuppressLint("Range")
    public List<ModeloNotificacion> getNotificacionesActivas() {
        try {
            if (db == null) {
                throw new Exception("Error al abrir la base de datos");
            }
            List<ModeloNotificacion> listado = new ArrayList<>();
            Cursor cursor = db.query(table, null, "activo = 1", null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    ModeloNotificacion modelo = new ModeloNotificacion();
                    modelo.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    modelo.setIdVehiculo(cursor.getInt(cursor.getColumnIndex("vehiculo_id")));
                    modelo.setTitle(cursor.getString(cursor.getColumnIndex("titulo")));
                    modelo.setMensaje(cursor.getString(cursor.getColumnIndex("mensaje")));
                    modelo.setKilometrajeObjetivo(cursor.getInt(cursor.getColumnIndex("kilometraje_objetivo")));
                    modelo.setIntervalo_notificacion(cursor.getInt(cursor.getColumnIndex("intervalo_notificacion")));
                    modelo.setActiva(cursor.getInt(cursor.getColumnIndex("activo")) > 0);

                    listado.add(modelo);
                } while (cursor.moveToNext());
            }
            cursor.close();
            return listado;
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
    public int getIdVehiculo() {
        return idVehiculo;
    }
    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }
    public String getMensaje() {
        return mensaje;
    }
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    public int getKilometrajeObjetivo() {
        return kilometrajeObjetivo;
    }
    public void setKilometrajeObjetivo(int kilometrajeObjetivo) {
        this.kilometrajeObjetivo = kilometrajeObjetivo;
    }
    public boolean isActiva() {
        return activa;
    }
    public void setActiva(boolean activa) {
        this.activa = activa;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIntervalo_notificacion() {
        return intervalo_notificacion;
    }

    public void setIntervalo_notificacion(int intervalo_notificacion) {
        this.intervalo_notificacion = intervalo_notificacion;
    }
}
