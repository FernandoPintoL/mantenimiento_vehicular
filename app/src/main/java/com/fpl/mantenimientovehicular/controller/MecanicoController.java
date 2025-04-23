package com.fpl.mantenimientovehicular.controller;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.fpl.mantenimientovehicular.negocio.NegocioMecanico;
import com.fpl.mantenimientovehicular.vista.VistaMecanico;

import java.util.List;

public class MecanicoController {
    private VistaMecanico vista;
    private NegocioMecanico negocio;
    public MecanicoController(VistaMecanico vista, NegocioMecanico negocio) {
        this.vista = vista;
        this.negocio = negocio;
    }
    public void initEvents() {
        vista.getBtnGuardar().setOnClickListener(v -> {
            cargarModeloFromFormulario();
            long result = negocio.agregar();
            if (result > 0) {
                vista.limpiarFormulario();
                vista.cargarDatosToListView();
                vista.mostrarMensaje("Datos guardados correctamente");
            } else {
                vista.mostrarMensaje("Error al guardar datos");
                Log.d("TAG", "Error al guardar datos");
            }
        });
        vista.getBtnEditar().setOnClickListener(v -> {
            cargarModeloFromFormulario();
            int result = negocio.editar();
            if (result > 0) {
                vista.limpiarFormulario();
                vista.cargarDatosToListView();
                vista.mostrarMensaje("Datos editados correctamente");
                vista.getBtnGuardar().setVisibility(View.VISIBLE);
                vista.getBtnsAction().setVisibility(View.GONE);
            } else {
                vista.mostrarMensaje("Error al editar datos");
                Log.d("TAG", "Error al editar datos");
            }
        });
        vista.getBtnEliminar().setOnClickListener(v -> {
            int result = negocio.eliminar();
            if (result > 0) {
                vista.limpiarFormulario();
                vista.cargarDatosToListView();
                vista.mostrarMensaje("Datos eliminados correctamente");
                vista.getBtnGuardar().setVisibility(View.VISIBLE);
                vista.getBtnsAction().setVisibility(View.GONE);
            } else {
                vista.mostrarMensaje("Error al eliminar datos");
                Log.d("TAG", "Error al eliminar datos");
            }
        });
        vista.getBtnListar().setOnClickListener(v -> {
            vista.cargarDatosToListView();
            vista.limpiarFormulario();
            vista.getListView().setVisibility(View.VISIBLE);
            vista.getBtnGuardar().setVisibility(View.VISIBLE);
            vista.getBtnsAction().setVisibility(View.GONE);
        });
        vista.getListView().setOnItemClickListener((parent, view, position, id) -> {
            Log.d("TAG", "Item clicked: " + position);
            vista.getBtnsAction().setVisibility(View.VISIBLE);
            vista.getBtnGuardar().setVisibility(View.GONE);
            negocio.setPosicion(position);
            cargarFormToList();
        });
    }
    public void cargarModeloFromFormulario() {
        String nombre = vista.getNombre();
        String taller = vista.getTaller();
        String direccion = vista.getDireccion();
        String telefono = vista.getTelefono();
        negocio.cargarFormulario(0, nombre, taller, direccion, telefono);
    }
    public void cargarFormToList() {
        if(negocio.getPosicion() == -1) return;
        String nombre = negocio.obtenerModeloPorPosicion().getNombre();
        String taller = negocio.obtenerModeloPorPosicion().getTaller();
        String direccion = negocio.obtenerModeloPorPosicion().getDireccion();
        String telefono = negocio.obtenerModeloPorPosicion().getTelefono();
        vista.setNombre(nombre);
        vista.setTaller(taller);
        vista.setDireccion(direccion);
        vista.setTelefono(telefono);
    }
}
