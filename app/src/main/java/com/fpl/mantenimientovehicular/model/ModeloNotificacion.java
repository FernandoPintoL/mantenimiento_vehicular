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
    private String mensaje;
    private int kilometrajeObjetivo;
    private boolean activa;
    private String table = "Notificacion";
    private static DataBaseHelper dbHelper;
    private static SQLiteDatabase db;
    public ModeloNotificacion() {}
    public ModeloNotificacion(int id, int idVehiculo, String mensaje, int kilometrajeObjetivo, boolean activa) {
        this.id = id;
        this.idVehiculo = idVehiculo;
        this.mensaje = mensaje;
        this.kilometrajeObjetivo = kilometrajeObjetivo;
        this.activa = activa;
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
            values.put("idVehiculo", modelo.getIdVehiculo());
            values.put("mensaje", modelo.getMensaje());
            values.put("kilometrajeObjetivo", modelo.getKilometrajeObjetivo());
            values.put("activa", modelo.isActiva());
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
            values.put("idVehiculo", modelo.getIdVehiculo());
            values.put("mensaje", modelo.getMensaje());
            values.put("kilometrajeObjetivo", modelo.getKilometrajeObjetivo());
            values.put("activa", modelo.isActiva());
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
    public int getIdVehiculoById(int id) {
        try{
            if (db == null) {
                throw new Exception("Error al abrir la base de datos");
            }
            String query = "SELECT idVehiculo FROM " + table + " WHERE id=?";
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});
            if (cursor != null && cursor.moveToFirst()) {
                int idVehiculo = cursor.getInt(cursor.getColumnIndex("idVehiculo"));
                cursor.close();
                return idVehiculo;
            }
            return -1;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @SuppressLint("Range")
    public ModeloNotificacion obtenerPorId(int id) {
        try{
            if (db == null) {
                throw new Exception("Error al abrir la base de datos");
            }
            Cursor cursor = db.query(table, null, "id = ?",
                    new String[]{String.valueOf(id)}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                ModeloNotificacion model = new ModeloNotificacion();
                model.setId(cursor.getInt(cursor.getColumnIndex("id")));
                model.setIdVehiculo(cursor.getInt(cursor.getColumnIndex("idVehiculo")));
                model.setMensaje(cursor.getString(cursor.getColumnIndex("mensaje")));
                model.setKilometrajeObjetivo(cursor.getInt(cursor.getColumnIndex("kilometrajeObjetivo")));
                model.setActiva(cursor.getInt(cursor.getColumnIndex("activa")) > 0);
                cursor.close();
                return model;
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @SuppressLint("Range")
    public ModeloNotificacion obtenerPorIdVehiculo(int idVehiculo) {
        try{
            if (db == null) {
                throw new Exception("Error al abrir la base de datos");
            }
            Cursor cursor = db.query(table, null, "idVehiculo = ?",
                    new String[]{String.valueOf(idVehiculo)}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                ModeloNotificacion model = new ModeloNotificacion();
                model.setId(cursor.getInt(cursor.getColumnIndex("id")));
                model.setIdVehiculo(cursor.getInt(cursor.getColumnIndex("idVehiculo")));
                model.setMensaje(cursor.getString(cursor.getColumnIndex("mensaje")));
                model.setKilometrajeObjetivo(cursor.getInt(cursor.getColumnIndex("kilometrajeObjetivo")));
                model.setActiva(cursor.getInt(cursor.getColumnIndex("activa")) > 0);
                cursor.close();
                return model;
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @SuppressLint("Range")
    public boolean existeNotificacion(int idVehiculo) {
        try{
            if (db == null) {
                throw new Exception("Error al abrir la base de datos");
            }
            Cursor cursor = db.query(table, null, "idVehiculo = ?",
                    new String[]{String.valueOf(idVehiculo)}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                boolean existe = cursor.getCount() > 0;
                cursor.close();
                return existe;
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @SuppressLint("Range")
    public boolean existeNotificacionActiva(int idVehiculo) {
        try{
            if (db == null) {
                throw new Exception("Error al abrir la base de datos");
            }
            Cursor cursor = db.query(table, null, "idVehiculo = ? AND activa = 1",
                    new String[]{String.valueOf(idVehiculo)}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                boolean existe = cursor.getCount() > 0;
                cursor.close();
                return existe;
            }
            return false;
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
                    modelo.setIdVehiculo(cursor.getInt(cursor.getColumnIndex("idVehiculo")));
                    modelo.setMensaje(cursor.getString(cursor.getColumnIndex("mensaje")));
                    modelo.setKilometrajeObjetivo(cursor.getInt(cursor.getColumnIndex("kilometrajeObjetivo")));
                    modelo.setActiva(cursor.getInt(cursor.getColumnIndex("activa")) > 0);

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
}
