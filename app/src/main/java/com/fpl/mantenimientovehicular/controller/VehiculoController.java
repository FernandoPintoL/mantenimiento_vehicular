package com.fpl.mantenimientovehicular.controller;

import android.content.Context;

import com.fpl.mantenimientovehicular.model.ModeloVehiculo;

import java.util.List;

public class VehiculoController {
    private final ModeloVehiculo modeloVehiculo;

    public VehiculoController(Context context) {
        modeloVehiculo = new ModeloVehiculo(context);
    }

    public long agregar(ModeloVehiculo model) {
        return modeloVehiculo.agregar(model);
    }

    public List<ModeloVehiculo> obtenerTodos() {
        return modeloVehiculo.obtenerTodos();
    }

    public ModeloVehiculo obtenerPorId(int id) {
        return modeloVehiculo.obtenerPorId(id);
    }

    public int actualizar(ModeloVehiculo model) {
        return modeloVehiculo.actualizar(model);
    }

    public boolean eliminar(int id) {
        return modeloVehiculo.eliminar(id) > 0;
    }

    public void close() {
        modeloVehiculo.close();
    }
}
