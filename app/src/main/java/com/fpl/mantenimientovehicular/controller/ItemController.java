package com.fpl.mantenimientovehicular.controller;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.fpl.mantenimientovehicular.model.ModeloItem;
import com.fpl.mantenimientovehicular.model.ModeloMecanico;
import com.fpl.mantenimientovehicular.negocio.NegocioItem;
import com.fpl.mantenimientovehicular.negocio.NegocioMecanico;
import com.fpl.mantenimientovehicular.vista.VistaItem;
import com.fpl.mantenimientovehicular.vista.VistaMecanico;

import java.util.List;

public class ItemController {
    private VistaItem vista;
    private NegocioItem negocio;
    public ItemController(VistaItem vista, NegocioItem negocio) {
        this.vista = vista;
        this.negocio = negocio;
    }
    public void initEvents() {
        vista.getBtnGuardar().setOnClickListener(v -> {
            cargarModeloFromFormulario();
            long result = negocio.agregar();
            if (result > 0) {
                vista.limpiarFormulario();
                vista.cargarDatosToList();
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
                vista.cargarDatosToList();
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
                vista.cargarDatosToList();
                vista.mostrarMensaje("Datos eliminados correctamente");
                vista.getBtnGuardar().setVisibility(View.VISIBLE);
                vista.getBtnsAction().setVisibility(View.GONE);
            } else {
                vista.mostrarMensaje("Error al eliminar datos");
                Log.d("TAG", "Error al eliminar datos");
            }
        });
        vista.getListView().setOnItemClickListener((parent, view, position, id) -> {
            Log.d("TAG", "Item clicked: " + position);
            vista.getBtnsAction().setVisibility(View.VISIBLE);
            vista.getBtnGuardar().setVisibility(View.GONE);
            negocio.setPosicion(position);
            cargarFormToList();
        });
        vista.cargarDatosToList();
        vista.limpiarFormulario();
    }
    public void cargarModeloFromFormulario() {
        String nombre = vista.getEtNombre().getText().toString();
        double precio = Double.parseDouble(vista.getEtPrecio().getText().toString());
        String detalle = vista.getEtDetalle().getText().toString();
        negocio.cargarFormulario(0, nombre, precio, detalle);
    }
    public void cargarFormToList() {
        if(negocio.getPosicion() == -1) return;
        String nombre = negocio.obtenerModeloPorPosicion().getNombre();
        double precio = negocio.obtenerModeloPorPosicion().getPrecio();
        String detalle = negocio.obtenerModeloPorPosicion().getDetalle();
        vista.setNombre(nombre);
        vista.setPrecio(String.valueOf(precio));
        vista.setDetalle(detalle);
    }
}
