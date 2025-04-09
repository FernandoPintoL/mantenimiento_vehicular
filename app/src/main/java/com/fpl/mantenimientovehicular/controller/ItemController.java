package com.fpl.mantenimientovehicular.controller;

import android.content.Context;

import com.fpl.mantenimientovehicular.model.ModeloItem;
import com.fpl.mantenimientovehicular.model.ModeloMecanico;

import java.util.List;

public class ItemController {
    private ModeloItem modelo;
    public ItemController(Context context) {
        modelo = new ModeloItem();
//        modelo.open();
        modelo.initDatabase(context);
    }
    public long agregar(ModeloItem modelo) {return modelo.agregar(modelo);}
    public List<ModeloItem> obtenerTodos() {return modelo.obtenerTodos();}
    public ModeloItem obtenerPorId(int id) {return modelo.obtenerPorId(id);}
    public int actualizar(ModeloItem vehiculo) {return modelo.actualizar(vehiculo);}
    public boolean eliminar(int id) {return modelo.eliminar(id) > 0;}
    public void close() {
        modelo.close();
    }
}
