package com.fpl.mantenimientovehicular.controller;

import android.content.Context;

import com.fpl.mantenimientovehicular.model.vehiculo.VehiculoDAO;

import java.util.List;

public class VehiculoController {
    private VehiculoDAO vehiculoDAO;

    public VehiculoController(Context context) {
        vehiculoDAO = new VehiculoDAO(context);
        vehiculoDAO.open();
    }

    public long agregar(VehiculoDAO vehiculo) {
        return vehiculoDAO.agregar(vehiculo);
    }

    public List<VehiculoDAO> obtenerTodos() {
        return vehiculoDAO.obtenerTodos();
    }

    public VehiculoDAO obtenerPorId(int id) {
        return vehiculoDAO.obtenerPorId(id);
    }

    public int actualizar(VehiculoDAO vehiculo) {
        return vehiculoDAO.actualizar(vehiculo);
    }

    public boolean eliminar(int id) {
        return vehiculoDAO.eliminar(id) > 0;
    }

    public void close() {
        vehiculoDAO.close();
    }
}
