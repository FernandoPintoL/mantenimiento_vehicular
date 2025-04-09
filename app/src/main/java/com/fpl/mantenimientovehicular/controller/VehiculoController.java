package com.fpl.mantenimientovehicular.controller;

import android.content.Context;

import com.fpl.mantenimientovehicular.model.ModeloVehiculo;

import java.util.List;

public class VehiculoController {
    private ModeloVehiculo modeloVehiculo;

    public VehiculoController(Context context) {
        modeloVehiculo = new ModeloVehiculo(context);
        modeloVehiculo.open();
    }

    public long agregar(ModeloVehiculo vehiculo) {
        return modeloVehiculo.agregar(vehiculo);
    }

    public List<ModeloVehiculo> obtenerTodos() {
        return modeloVehiculo.obtenerTodos();
    }

    public ModeloVehiculo obtenerPorId(int id) {
        return modeloVehiculo.obtenerPorId(id);
    }

    public int actualizar(ModeloVehiculo vehiculo) {
        return modeloVehiculo.actualizar(vehiculo);
    }

    public boolean eliminar(int id) {
        return modeloVehiculo.eliminar(id) > 0;
    }

    public void close() {
        modeloVehiculo.close();
    }
}
