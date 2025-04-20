package com.fpl.mantenimientovehicular.negocio;

import android.content.Context;

import com.fpl.mantenimientovehicular.model.ModeloVehiculo;

import java.util.ArrayList;
import java.util.List;

public class NegocioVehiculo {
    private ModeloVehiculo modelo;
    private int posicion;
    public NegocioVehiculo(Context context){
        modelo = new ModeloVehiculo(context);
    }
    public List<String> cargarDatos(){
        List<ModeloVehiculo> datos = modelo.obtenerTodos();
        List<String> listado = new ArrayList<>();
        for (ModeloVehiculo vehiculo : datos) {
            listado.add("ID: "+vehiculo.getId()+" Placa: "+vehiculo.getPlaca()+" Marca: "+vehiculo.getMarca() + " Tipo: " + vehiculo.getTipo()+" Año: "+vehiculo.getAño()+" Kilometraje Inicial: "+vehiculo.getKilometrajeActual()+" Kilometraje Esperado: "+vehiculo.getKilometrajeEsperado());
        }
        return listado;
    }
    public List<String> cargarDatosToSelect(){
        List<ModeloVehiculo> datos = modelo.obtenerTodos();
        List<String> listado = new ArrayList<>();
        for (ModeloVehiculo vehiculo : datos) {
            listado.add("Placa: "+vehiculo.getPlaca()+" | Tipo: " + vehiculo.getTipo());
        }
        return listado;
    }
    public List<ModeloVehiculo> obtenerTodos(){
        return modelo.obtenerTodos();
    }
    public void cargarFormulario(int id, String placa, String marca, String anho, String tipo, int kilometrajeActual, int kilometrajeEsperado){
        modelo = new ModeloVehiculo(id, placa, marca, anho, tipo, kilometrajeActual, kilometrajeEsperado);
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
    public int actualizarKilometrajes(int id, int kilometrajeActual, int kilometrajeEsperado){
        return modelo.actualizarKilometrajes(id, kilometrajeActual, kilometrajeEsperado);
    }
    public int obtenerIdPorPosicion(){
        List<ModeloVehiculo> datos = modelo.obtenerTodos();
        return datos.get(posicion).getId();
    }
    public ModeloVehiculo obtenerModeloPorPosicion(){
        List<ModeloVehiculo> datos = modelo.obtenerTodos();
        return datos.get(posicion);
    }
    public int getPosicion(){
        return posicion;
    }
    public void setPosicion(int posicion){
        this.posicion = posicion;
    }
}
