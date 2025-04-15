package com.fpl.mantenimientovehicular.controller;

import android.content.Context;

import com.fpl.mantenimientovehicular.model.ModeloItem;
import com.fpl.mantenimientovehicular.model.ModeloMecanico;

import java.util.List;

public class ItemController {
    private final ModeloItem modelo;
    public ItemController(Context context) {modelo = new ModeloItem(context);}

    public long agregar(ModeloItem model) {return modelo.agregar(model);}

    public List<ModeloItem> obtenerTodos() {return modelo.obtenerTodos();}

    public ModeloItem obtenerPorId(int id) {return modelo.obtenerPorId(id);}

    public int actualizar(ModeloItem model) {return modelo.actualizar(model);}

    public boolean eliminar(int id) {return modelo.eliminar(id) > 0;}

    public void close() {
        modelo.close();
    }
}
