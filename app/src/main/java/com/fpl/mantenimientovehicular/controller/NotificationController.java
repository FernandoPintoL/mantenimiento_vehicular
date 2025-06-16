package com.fpl.mantenimientovehicular.controller;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.core.content.ContextCompat;

import com.fpl.mantenimientovehicular.negocio.NegocioNotificacion;
import com.fpl.mantenimientovehicular.negocio.NegocioVehiculo;
import com.fpl.mantenimientovehicular.service.AlarmaKilometraje;
import com.fpl.mantenimientovehicular.vista.VistaNotificacion;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map;

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
                @SuppressWarnings("unchecked")
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
            // Verificar que el ID no esté vacío antes de intentar eliminar
            String idStr = vista.getIdNotificacion().getText().toString().trim();
            if (idStr.isEmpty() || idStr.equals("0")) {
                negocio.enviarNotificacion(
                        "NORMAL",
                        vista.getApplicationContext(),
                        "Error",
                        "No se ha seleccionado una notificación para eliminar.");
                return;
            }

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
                // Ocultar los botones de acción después de eliminar
                vista.getBtnsActionsNotify().setVisibility(View.GONE);
                vista.getIdNotificacionLayout().setVisibility(View.GONE);
                vista.getBtnGuardar().setVisibility(View.VISIBLE);
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
    @SuppressWarnings("unchecked")
    public void cargarVehiculoAdapterToSpinner() {
        // Cargar el adaptador para el Spinner de vehículos
        ArrayAdapter<Map<String, String>> adapter = new ArrayAdapter<Map<String, String>>(
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
                    textView.setBackgroundColor(ContextCompat.getColor(vista.getApplicationContext(), android.R.color.darker_gray));
                } else {
                    textView.setBackgroundColor(ContextCompat.getColor(vista.getApplicationContext(), android.R.color.transparent));
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
        String horaActual = sdf.format(new Date());

        // Establecer la hora actual en todos los campos de hora
        vista.setEtIntervaloNotificacion(horaActual);
        vista.getEtHoraDiaria().setText(horaActual);

        // Establecer valores predeterminados para los campos numéricos
        vista.getEtCantidadHoras().setText("1");
        vista.getEtCantidadMinutos().setText("0");
        vista.getEtCantidadDias().setText("1");
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
        // Determinar el intervalo según el tipo seleccionado
        String tipoIntervalo = vista.getSpTipoIntervalo().getSelectedItem().toString();
        int intervaloMillis = 0;
        String descripcionIntervalo = "";

        try {
            if (tipoIntervalo.equals(vista.getTIPO_INTERVALO_HORAS())) {
                // Intervalo en horas y minutos
                String cantidadHorasStr = vista.getEtCantidadHoras().getText().toString();
                String cantidadMinutosStr = vista.getEtCantidadMinutos().getText().toString();

                if (cantidadHorasStr.isEmpty() && cantidadMinutosStr.isEmpty()) {
                    vista.mostrarMensaje("Debe ingresar la cantidad de horas o minutos");
                    return;
                }

                int cantidadHoras = cantidadHorasStr.isEmpty() ? 0 : Integer.parseInt(cantidadHorasStr);
                int cantidadMinutos = cantidadMinutosStr.isEmpty() ? 0 : Integer.parseInt(cantidadMinutosStr);

                if (cantidadHoras == 0 && cantidadMinutos == 0) {
                    vista.mostrarMensaje("El intervalo debe ser mayor a cero");
                    return;
                }

                intervaloMillis = negocio.calcularIntervaloHoras(cantidadHoras, cantidadMinutos);

                String horasTexto = cantidadHoras > 0 ? cantidadHoras + " horas" : "";
                String minutosTexto = cantidadMinutos > 0 ? cantidadMinutos + " minutos" : "";
                String conector = cantidadHoras > 0 && cantidadMinutos > 0 ? " y " : "";

                descripcionIntervalo = "cada " + horasTexto + conector + minutosTexto;

            } else if (tipoIntervalo.equals(vista.getTIPO_INTERVALO_DIAS())) {
                // Intervalo en días a una hora específica
                String cantidadDiasStr = vista.getEtCantidadDias().getText().toString();
                String horaEspecifica = vista.getEtIntervaloNotificacion().getText().toString();
                if (cantidadDiasStr.isEmpty()) {
                    vista.mostrarMensaje("Debe ingresar la cantidad de días");
                    return;
                }
                if (horaEspecifica.isEmpty()) {
                    vista.mostrarMensaje("Debe seleccionar una hora específica");
                    return;
                }
                int cantidadDias = Integer.parseInt(cantidadDiasStr);
                intervaloMillis = (int) negocio.calcularIntervaloDias(cantidadDias, horaEspecifica);
                descripcionIntervalo = "cada " + cantidadDias + " días a las " + horaEspecifica;

            } else if (tipoIntervalo.equals(vista.getTIPO_INTERVALO_DIARIO())) {
                // Intervalo diario a una hora específica
                String horaEspecifica = vista.getEtHoraDiaria().getText().toString();

                if (horaEspecifica.isEmpty()) {
                    vista.mostrarMensaje("Debe seleccionar una hora específica");
                    return;
                }

                intervaloMillis = (int) negocio.calcularIntervaloDiario(horaEspecifica);
                descripcionIntervalo = "todos los días a las " + horaEspecifica;
            }
        } catch (NumberFormatException e) {
            vista.mostrarMensaje("Error en el formato de los números ingresados");
            return;
        }
        // Guardar el intervalo calculado en el modelo
        negocio.cargarFormulario(
                id,
                Integer.parseInt(vista.getIdVehiculoNotificacion().getText().toString()),
                vista.getEtTitleNotificacion().getText().toString(),
                vista.getEtMensajeNotificacion().getText().toString(),
                Integer.parseInt(vista.getEtKilometrajeObjetivoNotificacion().getText().toString()),
                String.valueOf(intervaloMillis), // Guardar directamente en milisegundos
                vista.getSwActivoNotificacion().isChecked()
        );
        // Actualizar la estrategia de notificación según el tipo seleccionado
        String tipoNotificacion = vista.getSpTipoNotificacion().getSelectedItem().toString();
        String titulo = vista.getEtTitleNotificacion().getText().toString();
        String mensaje = vista.getEtMensajeNotificacion().getText().toString();

        // Si es tipo RECURRENTE, mostrar mensaje específico con el intervalo
        if ("RECURRENTE".equals(tipoNotificacion)) {
            vista.mostrarMensaje("Configurando notificación recurrente para mantenimientos futuros " + descripcionIntervalo);
        }
        // Enviar la notificación con la estrategia seleccionada
        negocio.enviarNotificacion(
                tipoNotificacion,
                vista.getApplicationContext(),
                titulo,
                mensaje);
        // Mostrar el tipo de notificación seleccionado
        vista.mostrarMensaje("Tipo de notificación: " + tipoNotificacion);
    }
    @SuppressWarnings("unchecked")
    public void listenNotificaciones(){
        AlarmaKilometraje.cancelarTodasLasAlarmas(vista.getApplicationContext());
        List<Map<String, String>> notificaciones = negocio.getNotificacionesActivas();
        for (Map<String, String> n : notificaciones) {
            if (Objects.equals(n.get("activo"), "1")) {
                // Mostrar notificación inmediata
                negocio.enviarNotificacion(
                        "NORMAL",
                        vista.getApplicationContext(),
                        n.get("titulo"),
                        n.get("mensaje")
                );

                // Programar notificaciones recurrentes para mantenimientos futuros
                int interval = 0;
                try {
                    // Usar el valor en milisegundos directamente
                    if (n.containsKey("intervalo_notificacion_ms")) {
                        interval = Integer.parseInt(n.get("intervalo_notificacion_ms"));
                    } else {
                        // Fallback al método anterior si no está disponible el valor en ms
                        interval = negocio.convertirTiempoAMilisegundos(n.get("intervalo_notificacion"));
                    }
                } catch (NumberFormatException e) {
                    // Si hay un error al parsear, intentar con el método anterior
                    interval = negocio.convertirTiempoAMilisegundos(n.get("intervalo_notificacion"));
                }

                if (interval > 0) {
                    negocio.enviarNotificacion(
                            "RECURRENTE",
                            vista.getApplicationContext(),
                            n.get("titulo"),
                            n.get("mensaje")
                    );
                }
            }
        }
    }
}
