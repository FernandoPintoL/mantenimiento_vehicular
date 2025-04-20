package com.fpl.mantenimientovehicular.negocio;

import android.content.Context;

import com.fpl.mantenimientovehicular.model.ModeloNotificacion;

import java.util.List;

public class NegocioNotificacion {
    private ModeloNotificacion modelo;
    private int posicion;
    public NegocioNotificacion(Context context) {
        modelo = new ModeloNotificacion(context);
    }
    public void cargarFormulario(int id, int idVehiculo, String mensaje, int kilometrajeObjetivo, boolean activa) {
        modelo = new ModeloNotificacion(id, idVehiculo, mensaje, kilometrajeObjetivo, activa);
    }
    public long agregar() {
        return modelo.agregar(modelo);
    }
    public int eliminar() {
        return modelo.eliminar(modelo.getId());
    }
    public int editar() {
        int id = obtenerIdPorPosicion();
        modelo.setId(id);
        return modelo.modificar(modelo);
    }
    public int obtenerIdPorPosicion() {
        List<ModeloNotificacion> datos = modelo.obtenerTodos();
        return datos.get(posicion).getId();
    }
    public int getPosicion() {
        return posicion;
    }
    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }
}
