package com.fpl.mantenimientovehicular.controller;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fpl.mantenimientovehicular.negocio.NegocioNotificacion;
import com.fpl.mantenimientovehicular.negocio.NegocioVehiculo;
import com.fpl.mantenimientovehicular.service.AlarmaKilometraje;
import com.fpl.mantenimientovehicular.vista.VistaNotificacion;

import java.text.SimpleDateFormat;
import java.util.*;

public class NotificationController {
    private VistaNotificacion vista;
    private NegocioNotificacion negocio;
    private NegocioVehiculo negocioVehiculo;
    public NotificationController(VistaNotificacion vista, NegocioNotificacion negocio) {
        this.vista = vista;
        this.negocio = negocio;
        this.negocioVehiculo = new NegocioVehiculo(vista.getApplicationContext());
        NotificationHelper.createNotificationChannel(vista.getApplicationContext());
        listenNotificaciones();
    }
    public void initEvents(){
        cargarTime();
        cargarVehiculoAdapterToSpinner();
        vista.getSpVehiculo().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                negocioVehiculo.setPosicion(position);
                Map<String, String> selectedItem = negocioVehiculo.getModelForPos();
                vista.getIdVehiculoNotificacion().setText(selectedItem.get("id"));
                vista.getEtTitleNotificacion().setText("Recordatorio para: "+selectedItem.get("placa"));
                vista.getEtMensajeNotificacion().setText("Recuerda revisar los mantenimientos al kilometraje: "+selectedItem.get("kilometrajeEsperado")+" km");
                vista.getEtKilometrajeObjetivoNotificacion().setText(selectedItem.get("kilometrajeEsperado"));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        vista.getListViewNotificaciones().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, String> selectedItem = (Map<String, String>) parent.getItemAtPosition(position);
                if (selectedItem != null) {
                    vista.getIdNotificacion().setText(selectedItem.get("id"));
                    vista.getIdVehiculoNotificacion().setText(selectedItem.get("vehiculo_id"));
                    vista.getEtTitleNotificacion().setText(selectedItem.get("titulo"));
                    vista.getEtMensajeNotificacion().setText(selectedItem.get("mensaje"));
                    vista.getEtKilometrajeObjetivoNotificacion().setText(selectedItem.get("kilometraje_objetivo"));
                    vista.setEtIntervaloNotificacion(selectedItem.get("intervalo_notificacion"));
                    vista.getSwActivoNotificacion().setChecked(Boolean.parseBoolean(selectedItem.get("activo")));

                    vista.getBtnsActionsNotify().setVisibility(View.VISIBLE);
                    vista.getIdNotificacionLayout().setVisibility(View.VISIBLE);
                    vista.getBtnGuardar().setVisibility(View.GONE);
                }
            }
        });
        vista.getBtnGuardar().setOnClickListener(v->{
            getDatosOfForm();
            long result = negocio.agregar();
            if (result > 0) {
                negocio.enviarNotificacion(
                        "NORMAL",
                        vista.getApplicationContext(),
                        "Notificación Guardada",
                        "La notificación se ha guardado correctamente.");
                vista.limpiarCampos();
                vista.cargarAdapterView();
                listenNotificaciones();
            } else {
                negocio.enviarNotificacion(
                        "NORMAL",
                        vista.getApplicationContext(),
                        "Error",
                        "No se pudo guardar la notificación.");
            }
        });
        vista.getBtnEliminar().setOnClickListener(v->{
            getDatosOfForm();
            int result = negocio.eliminar();
            if (result > 0) {
                negocio.enviarNotificacion(
                        "NORMAL",
                        vista.getApplicationContext(),
                        "Notificación Eliminada",
                        "La notificación se ha eliminado correctamente.");
                vista.limpiarCampos();
                vista.cargarAdapterView();
            } else {
                negocio.enviarNotificacion(
                        "NORMAL",
                        vista.getApplicationContext(),
                        "Error",
                        "No se pudo eliminar la notificación.");
            }
        });
        vista.getBtnModificar().setOnClickListener(v->{
            getDatosOfForm();
            int result = negocio.editar();
            if (result > 0) {
                negocio.enviarNotificacion(
                        "NORMAL",
                        vista.getApplicationContext(),
                        "Notificación Modificada",
                        "La notificación se ha modificado correctamente.");
                vista.limpiarCampos();
                vista.cargarAdapterView();
            } else {
                negocio.enviarNotificacion(
                        "NORMAL",
                        vista.getApplicationContext(),
                        "Error",
                        "No se pudo modificar la notificación."
                );
            }
        });
        vista.getBtnListar().setOnClickListener(v->{
            vista.cargarAdapterView();
            vista.getBtnsActionsNotify().setVisibility(View.GONE);
            vista.getIdNotificacionLayout().setVisibility(View.GONE);
        });
    }
    public void cargarVehiculoAdapterToSpinner() {
        // Cargar el adaptador para el Spinner de vehículos
        ArrayAdapter<Map<String, String>> adapter = new ArrayAdapter<>(
                vista.getApplicationContext(),
                android.R.layout.simple_spinner_item,
                negocioVehiculo.cargarDatosMap()){
            final int padding = 16; // Padding en píxeles
            @SuppressLint("SetTextI18n")
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view;
                Map<String, String> item = getItem(position);
                if (item != null) {
                    textView.setText("Placa: "+item.get("placa")+" Tipo: "+item.get("tipo"));
                }
                //modificar padding
                textView.setPadding(padding, padding, padding, padding);
                // Cambiar el color de fondo del Spinner según el que este seleccionado
                if (position == vista.getSpVehiculo().getSelectedItemPosition()) {
                    textView.setBackgroundColor(vista.getResources().getColor(android.R.color.darker_gray));
                } else {
                    textView.setBackgroundColor(vista.getResources().getColor(android.R.color.transparent));
                }
                return view;
            }
            @SuppressLint("SetTextI18n")
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view;
                Map<String, String> item = getItem(position);
                if (item != null) {
                    textView.setText("Placa: "+item.get("placa")+" Tipo: "+item.get("tipo"));
                }
                //modificar padding
                textView.setPadding(padding, padding, padding, padding);
                return view;
            }
        };
        vista.getSpVehiculo().setAdapter(adapter);
    }
    public void cargarTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        vista.setEtIntervaloNotificacion(sdf.format(new Date()));
    }
    public void getDatosOfForm(){
        // obtener el id de la notificacion si existe
        if (vista.getIdNotificacion().getText().toString().trim().isEmpty()) {
            vista.getIdNotificacion().setText("0");
        }else{
            vista.mostrarMensaje("El ID seleccionado: "+vista.getIdNotificacion().getText().toString());
        }
        // obtener el id del vehiculo
        if (vista.getIdVehiculoNotificacion().getText().toString().trim().isEmpty()) {
            vista.mostrarMensaje("El ID del vehículo no puede estar vacío");
            return;
        }
        int id = Integer.parseInt(vista.getIdNotificacion().getText().toString().trim()); // Obtener el ID de la notificación
        negocio.cargarFormulario(
                id,
                Integer.parseInt(vista.getIdVehiculoNotificacion().getText().toString()),
                vista.getEtTitleNotificacion().getText().toString(),
                vista.getEtMensajeNotificacion().getText().toString(),
                Integer.parseInt(vista.getEtKilometrajeObjetivoNotificacion().getText().toString()),
                vista.getEtIntervaloNotificacion().getText().toString(),
                vista.getSwActivoNotificacion().isChecked()
        );
        // Actualizar la estrategia de notificación según el tipo seleccionado
        String tipoNotificacion = vista.getSpTipoNotificacion().getSelectedItem().toString();
        negocio.enviarNotificacion(tipoNotificacion,
                vista.getApplicationContext(),
                vista.getEtTitleNotificacion().getText().toString(),
                vista.getEtMensajeNotificacion().getText().toString());

        // Mostrar el tipo de notificación seleccionado
        vista.mostrarMensaje("Tipo de notificación: " + tipoNotificacion);
    }
    public void listenNotificaciones(){
        AlarmaKilometraje.cancelarTodasLasAlarmas(vista.getApplicationContext());
        List<Map<String, String>> notificaciones = negocio.getNotificacionesActivas();
        for (Map<String, String> n : notificaciones) {
            if (Objects.equals(n.get("activo"), "1")) {
                negocio.enviarNotificacion(
                        "NORMAL",
                        vista.getApplicationContext(),
                        n.get("titulo"),
                        n.get("mensaje")
                );
            }
            // cual seria el valor para 30segundos
            // 30 segundos = 30 * 1000 milisegundos
            int interval = negocio.convertirTiempoAMilisegundos(n.get("intervalo_notificacion"));
            AlarmaKilometraje.programarAlarmaRecurrente(
                    vista.getApplicationContext(),
                    n.get("titulo"),
                    n.get("mensaje"),
                    interval);
        }
    }
}
