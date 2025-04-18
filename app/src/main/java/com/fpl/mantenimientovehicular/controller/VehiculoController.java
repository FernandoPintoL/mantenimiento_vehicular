package com.fpl.mantenimientovehicular.controller;

import android.util.Log;
import android.view.View;

import com.fpl.mantenimientovehicular.negocio.NegocioVehiculo;
import com.fpl.mantenimientovehicular.vista.VistaVehiculo;

public class VehiculoController {
    private VistaVehiculo vista;
    private NegocioVehiculo negocio;
    public VehiculoController(VistaVehiculo vista, NegocioVehiculo negocio) {
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
        String marca = vista.getEtMarca().getText().toString();
        String anho = vista.getEtAnho().getText().toString();
        String placa = vista.getEtPlaca().getText().toString();
        String tipo = vista.getEtTipo().getText().toString();
        int kilometraje = Integer.parseInt(vista.getEtKilometraje().getText().toString());
        negocio.cargarFormulario(0, marca, anho, placa, tipo, kilometraje);
    }
    public void cargarFormToList() {
        if(negocio.getPosicion() == -1) return;
        vista.setIdVehiculo("ID : "+String.valueOf(negocio.obtenerModeloPorPosicion().getId()));
        vista.setEtMarca(negocio.obtenerModeloPorPosicion().getMarca());
        vista.setEtAnho(negocio.obtenerModeloPorPosicion().getAño());
        vista.setEtPlaca(negocio.obtenerModeloPorPosicion().getPlaca());
        vista.setEtTipo(negocio.obtenerModeloPorPosicion().getTipo());
        vista.setEtKilometraje(String.valueOf(negocio.obtenerModeloPorPosicion().getKilometraje()));
    }
}
