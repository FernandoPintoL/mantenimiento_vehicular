package com.fpl.mantenimientovehicular.negocio;

import android.content.Context;

import com.fpl.mantenimientovehicular.model.ModeloMecanico;

import java.util.ArrayList;
import java.util.List;

public class NegocioMecanico {
    private ModeloMecanico modelo;
    private int posicion;
    public NegocioMecanico(Context context){
        modelo = new ModeloMecanico(context);
    }
    public List<String> cargarDatos(){
        List<ModeloMecanico> datos = modelo.obtenerTodos();
        List<String> listado = new ArrayList<>();
        for (ModeloMecanico vehiculo : datos) {
            listado.add(vehiculo.getNombre() + " - " + vehiculo.getTaller());
        }
        return listado;
    }
    public void cargarFormulario(int id, String nombre, String taller, String direccion, String telefono){
        modelo = new ModeloMecanico(id, nombre, taller, direccion, telefono);
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
        List<ModeloMecanico> datos = modelo.obtenerTodos();
        return datos.get(posicion).getId();
    }
    public ModeloMecanico obtenerModeloPorPosicion(){
        List<ModeloMecanico> datos = modelo.obtenerTodos();
        return datos.get(posicion);
    }
    public int getPosicion(){
        return posicion;
    }
    public void setPosicion(int posicion){
        this.posicion = posicion;
    }
}
