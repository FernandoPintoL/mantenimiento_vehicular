package com.fpl.mantenimientovehicular.model.vehiculo;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fpl.mantenimientovehicular.database.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;

public class VehiculoDAO {
    private DataBaseHelper dbHelper;
    private SQLiteDatabase db;

    public VehiculoDAO(Context context) {
        dbHelper = new DataBaseHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // CREATE
    public long agregarVehiculo(Vehiculo vehiculo) {
        ContentValues values = new ContentValues();
        values.put("marca", vehiculo.getMarca());
        values.put("modelo", vehiculo.getModelo());
        values.put("año", vehiculo.getAño());
        values.put("placa", vehiculo.getPlaca());
        values.put("tipo", vehiculo.getTipo());
        values.put("kilometraje", vehiculo.getKilometraje());

        return db.insert("vehiculos", null, values);
    }

    // READ (Todos los vehículos)
    @SuppressLint("Range")
    public List<Vehiculo> obtenerTodosVehiculos() {
        List<Vehiculo> vehiculos = new ArrayList<>();
        Cursor cursor = db.query("vehiculos", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Vehiculo vehiculo = new Vehiculo();
                vehiculo.setId(cursor.getInt(cursor.getColumnIndex("id")));
                vehiculo.setMarca(cursor.getString(cursor.getColumnIndex("marca")));
                vehiculo.setModelo(cursor.getString(cursor.getColumnIndex("modelo")));
                vehiculo.setAño(cursor.getString(cursor.getColumnIndex("año")));
                vehiculo.setPlaca(cursor.getString(cursor.getColumnIndex("placa")));
                vehiculo.setTipo(cursor.getString(cursor.getColumnIndex("tipo")));
                vehiculo.setKilometraje(cursor.getInt(cursor.getColumnIndex("kilometraje")));
                vehiculos.add(vehiculo);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return vehiculos;
    }

    // READ (Un vehículo por ID)
    @SuppressLint("Range")
    public Vehiculo obtenerVehiculoPorId(int id) {
        Cursor cursor = db.query("vehiculos", null, "id = ?",
                new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Vehiculo vehiculo = new Vehiculo();
            vehiculo.setId(cursor.getInt(cursor.getColumnIndex("id")));
            vehiculo.setMarca(cursor.getString(cursor.getColumnIndex("marca")));
            vehiculo.setModelo(cursor.getString(cursor.getColumnIndex("modelo")));
            vehiculo.setAño(cursor.getString(cursor.getColumnIndex("año")));
            vehiculo.setPlaca(cursor.getString(cursor.getColumnIndex("placa")));
            vehiculo.setTipo(cursor.getString(cursor.getColumnIndex("tipo")));
            vehiculo.setKilometraje(cursor.getInt(cursor.getColumnIndex("kilometraje")));
            cursor.close();
            return vehiculo;
        }
        return null;
    }

    // UPDATE
    public int actualizarVehiculo(Vehiculo vehiculo) {
        ContentValues values = new ContentValues();
        values.put("marca", vehiculo.getMarca());
        values.put("modelo", vehiculo.getModelo());
        values.put("año", vehiculo.getAño());
        values.put("placa", vehiculo.getPlaca());
        values.put("tipo", vehiculo.getTipo());
        values.put("kilometraje", vehiculo.getKilometraje());

        return db.update("vehiculos", values, "id = ?",
                new String[]{String.valueOf(vehiculo.getId())});
    }

    // DELETE
    public int eliminarVehiculo(int id) {
        return db.delete("vehiculos", "id = ?",
                new String[]{String.valueOf(id)});
    }
}
