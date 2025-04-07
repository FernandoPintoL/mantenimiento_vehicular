package com.fpl.mantenimientovehicular.controller;

import android.content.Context;

import com.fpl.mantenimientovehicular.model.vehiculo.Vehiculo;
import com.fpl.mantenimientovehicular.model.vehiculo.VehiculoDAO;

import java.util.List;

public class VehiculoController {
    private VehiculoDAO vehiculoDAO;

    public VehiculoController(Context context) {
        vehiculoDAO = new VehiculoDAO(context);
        vehiculoDAO.open();
    }

    public long agregarVehiculo(Vehiculo vehiculo) {
        return vehiculoDAO.agregarVehiculo(vehiculo);
    }

    public List<Vehiculo> obtenerTodosVehiculos() {
        return vehiculoDAO.obtenerTodosVehiculos();
    }

    public Vehiculo obtenerVehiculoPorId(int id) {
        return vehiculoDAO.obtenerVehiculoPorId(id);
    }

    public int actualizarVehiculo(Vehiculo vehiculo) {
        return vehiculoDAO.actualizarVehiculo(vehiculo);
    }

    public boolean eliminarVehiculo(int id) {
        return vehiculoDAO.eliminarVehiculo(id) > 0;
    }

    public void close() {
        vehiculoDAO.close();
    }
}
