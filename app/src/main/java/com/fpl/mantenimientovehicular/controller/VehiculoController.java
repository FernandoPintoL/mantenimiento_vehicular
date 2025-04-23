package com.fpl.mantenimientovehicular.controller;

import android.text.Editable;
import android.text.TextWatcher;
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
                vista.cargarDatosToListView();
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
                vista.cargarDatosToListView();
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
                vista.cargarDatosToListView();
                vista.mostrarMensaje("Datos eliminados correctamente");
                vista.getBtnGuardar().setVisibility(View.VISIBLE);
                vista.getBtnsAction().setVisibility(View.GONE);
            } else {
                Log.d("TAG", "Error al eliminar datos");
            }
        });
        vista.getBtnListar().setOnClickListener(v -> {
            vista.cargarDatosToListView();
            vista.getBtnsAction().setVisibility(View.GONE);
            vista.getBtnGuardar().setVisibility(View.VISIBLE);
            vista.limpiarFormulario();
        });
        vista.getListView().setOnItemClickListener((parent, view, position, id) -> {
            Log.d("TAG", "Item clicked: " + position);
            vista.getBtnsAction().setVisibility(View.VISIBLE);
            vista.getBtnGuardar().setVisibility(View.GONE);
            negocio.setPosicion(position);
            cargarFormToList();
        });
        vista.getEtKilometrajeActual().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    // Obtener el valor del EditTextKilometrajeActual
                    int kilometrajeActual = Integer.parseInt(s.toString());
                    // Incrementar 5000 y establecer el valor en EditTextKilometrajeObjetivo
                    int kilometrajeObjetivo = kilometrajeActual + 5000;
                    vista.getEtKilometrajeEsperado().setText(String.valueOf(kilometrajeObjetivo));
                } catch (NumberFormatException e) {
                    // Manejar el caso en que el texto no sea un número válido
                    vista.getEtKilometrajeEsperado().setText("");
                }
            }
        });
        vista.limpiarFormulario();
    }
    public void cargarModeloFromFormulario() {
        String marca = vista.getEtMarca().getText().toString();
        String anho = vista.getEtAnho().getText().toString();
        String placa = vista.getEtPlaca().getText().toString();
        String tipo = vista.getEtTipo().getText().toString();
        int kilometrajeActual = Integer.parseInt(vista.getEtKilometrajeActual().getText().toString());
        int kilometrajeEsperado = Integer.parseInt(vista.getEtKilometrajeEsperado().getText().toString());
        negocio.cargarFormulario(0, marca, anho, placa, tipo, kilometrajeActual, kilometrajeEsperado);
    }
    public void cargarFormToList() {
        if(negocio.getPosicion() == -1) return;
        vista.setIdVehiculo("ID : "+String.valueOf(negocio.obtenerModeloPorPosicion().getId()));
        vista.setEtMarca(negocio.obtenerModeloPorPosicion().getMarca());
        vista.setEtAnho(negocio.obtenerModeloPorPosicion().getAño());
        vista.setEtPlaca(negocio.obtenerModeloPorPosicion().getPlaca());
        vista.setEtTipo(negocio.obtenerModeloPorPosicion().getTipo());
        vista.setEtKilometrajeActual(String.valueOf(negocio.obtenerModeloPorPosicion().getKilometrajeActual()));
        vista.setEtKilometrajeEsperado(String.valueOf(negocio.obtenerModeloPorPosicion().getKilometrajeEsperado()));
    }
}
