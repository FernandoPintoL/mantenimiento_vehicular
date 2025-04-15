package com.fpl.mantenimientovehicular.controller;

import android.content.Context;

import com.fpl.mantenimientovehicular.model.ModeloMecanico;
import com.fpl.mantenimientovehicular.model.ModeloVehiculo;

import java.util.List;

public class MecanicoController {
    private final ModeloMecanico modelo;
    public MecanicoController(Context context) {
        modelo = new ModeloMecanico(context);
    }
    public long agregar(ModeloMecanico model) {
        return modelo.agregar(model);
    }

    public List<ModeloMecanico> obtenerTodos() {
        return modelo.obtenerTodos();
    }

    public ModeloMecanico obtenerPorId(int id) {
        return modelo.obtenerPorId(id);
    }

    public int actualizar(ModeloMecanico model) {
        return modelo.actualizar(model);
    }

    public boolean eliminar(int id) {
        return modelo.eliminar(id) > 0;
    }

    public void close() {
        modelo.close();
    }
}
