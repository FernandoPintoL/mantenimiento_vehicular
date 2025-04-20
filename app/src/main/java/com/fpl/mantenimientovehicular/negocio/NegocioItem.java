package com.fpl.mantenimientovehicular.negocio;

import android.content.Context;

import com.fpl.mantenimientovehicular.model.ModeloItem;
import com.fpl.mantenimientovehicular.model.ModeloMecanico;

import java.util.ArrayList;
import java.util.List;

public class NegocioItem {
    private ModeloItem modelo;
    private int posicion;
    public NegocioItem(Context context){
        modelo = new ModeloItem(context);
    }
    public List<String> cargarDatos(){
        List<ModeloItem> datos = modelo.obtenerTodos();
        List<String> listado = new ArrayList<>();
        for (ModeloItem vehiculo : datos) {
            listado.add(vehiculo.getNombre()+ " | Det.: "+vehiculo.getDetalle() + " - Precio: " + vehiculo.getPrecio());
        }
        return listado;
    }
    public void cargarFormulario(int id, String nombre, double precio, String detalle){
        modelo = new ModeloItem(id, nombre, precio, detalle);
    }
    public long agregar(){
        return modelo.agregar(modelo);
    }
    public int eliminar(){
        return modelo.eliminar(modelo.getId());
    }
    public int editar(){
        int id = obtenerIdPorPosicion();
        modelo.setId(id);
        return modelo.actualizar(modelo);
    }
    public int obtenerIdPorPosicion(){
        List<ModeloItem> datos = modelo.obtenerTodos();
        return datos.get(posicion).getId();
    }
    public ModeloItem obtenerModeloPorPosicion(){
        List<ModeloItem> datos = modelo.obtenerTodos();
        return datos.get(posicion);
    }
    public int getPosicion(){
        return posicion;
    }
    public void setPosicion(int posicion){
        this.posicion = posicion;
    }
}
