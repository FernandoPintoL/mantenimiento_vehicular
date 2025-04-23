package com.fpl.mantenimientovehicular.controller;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fpl.mantenimientovehicular.model.ModeloDetalleMantenimiento;
import com.fpl.mantenimientovehicular.negocio.NegocioItem;
import com.fpl.mantenimientovehicular.negocio.NegocioMantenimiento;
import com.fpl.mantenimientovehicular.negocio.NegocioMecanico;
import com.fpl.mantenimientovehicular.negocio.NegocioVehiculo;
import com.fpl.mantenimientovehicular.vista.VistaMantenimiento;

import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MantenimientoDetController {
    private VistaMantenimiento vista;
    private NegocioMantenimiento negocioMantenimiento;
    private NegocioVehiculo negocioVehiculo;
    private NegocioItem negocioItem;
    private NegocioMecanico negocioMecanico;
    private SimpleDateFormat sdf;
    public MantenimientoDetController(VistaMantenimiento vista, NegocioMantenimiento negocio) {
        this.vista = vista;
        this.negocioMantenimiento = negocio;
        this.negocioMecanico = new NegocioMecanico(vista.getApplicationContext());
        this.negocioVehiculo = new NegocioVehiculo(vista.getApplicationContext());
        this.negocioItem = new NegocioItem(vista.getApplicationContext());
    }
    public void initEvents() {
        cargarFecha();
        cargarItems();
        cargarMecanicos();
        cargarVehiculos();
        vista.getEtFecha().setOnClickListener(v -> vista.openDateTime());
        vista.getSpModItem().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0 && position < vista.getSpModItem().getCount()) {
                    negocioItem.setPosicion(position);
                    int idItem = negocioItem.obtenerModeloPorPosicion().getId();
                    String nombre = negocioItem.obtenerModeloPorPosicion().getNombre();
                    Double precioItem = negocioItem.obtenerModeloPorPosicion().getPrecio();
                    vista.setIdItem(String.valueOf(idItem));
                    vista.getEtCantidad().setHint("Cantidad de: " + nombre);
                    vista.setEtCantidad("1");
                    vista.setEtPrecioItem(precioItem.toString());
                } else {
                    vista.getEtCantidad().setHint("Cantidad");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        vista.getSpModVehiculo().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0 && position < vista.getSpModVehiculo().getCount()) {
                    negocioVehiculo.setPosicion(position);
                    int idVehiculo = negocioVehiculo.obtenerModeloPorPosicion().getId();
                    int kilometraje = negocioVehiculo.obtenerModeloPorPosicion().getKilometrajeActual();
                    vista.setIdVehiculo(String.valueOf(idVehiculo));
                    vista.setEtKilometrajeActual(String.valueOf(kilometraje));
                    vista.setEtKilometrajeObjetivo(String.valueOf(kilometraje + 5000));
                } else {
                    vista.getEtKilometrajeActual().setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        vista.getSpModMecanico().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0 && position < vista.getSpModMecanico().getCount()) {
                    negocioMecanico.setPosicion(position);
                    Map<String,String> selectedItem = negocioMecanico.getModeloMap();
                    int idMecanico = Integer.parseInt(selectedItem.get("id"));
                    String nombre = selectedItem.get("nombre");
                    vista.setIdMecanico(String.valueOf(idMecanico));
                    vista.mostrarMensaje("Mecánico seleccionado: " + position);
                    vista.mostrarMensaje("ID Mecanico: " + idMecanico);
                    vista.mostrarMensaje("Mecanico Selecionado:"+nombre);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        vista.getListViewDetalleMantenimiento().setOnItemClickListener((parent, view, position, id) -> {
            negocioMantenimiento.eliminarDetailsPos(position);
            vista.getTxtTotal().setText(String.valueOf(negocioMantenimiento.calculoTotalDetalle()));
            actListDetMantenimiento();
            vista.mostrarMensaje("Detalle eliminado correctamente");
        });
        vista.getBtnGuardar().setOnClickListener(v -> {
            String fecha = vista.getEtFecha().getText().toString();
            if (fecha.isEmpty()) {
                vista.mostrarMensaje("Por favor seleccione una fecha");
                return;
            }
            String idVehiculo = vista.getIdVehiculo().getText().toString();
            String idMecanico = vista.getIdMecanico().getText().toString();
            String detalle = vista.getEtDetalle().getText().toString();
            if (detalle.isEmpty()) {
                detalle = "Sin Detalle";
            }
            String kilometrajeActual = vista.getEtKilometrajeActual().getText().toString();
            String kilometrajeObjetivo = vista.getEtKilometrajeObjetivo().getText().toString();
            if (idVehiculo.isEmpty() || idMecanico.isEmpty() || kilometrajeActual.isEmpty() || kilometrajeObjetivo.isEmpty()) {
                vista.mostrarMensaje("Por favor complete todos los campos");
                return;
            }
            long result = negocioMantenimiento.registroMantenimientoCompleto(
                    Integer.parseInt(idVehiculo),
                    Integer.parseInt(idMecanico),
                    fecha,
                    Integer.parseInt(kilometrajeActual),
                    detalle
            );
            if (result > 0) {
                /*vista.mostrarMensaje("Datos guardados correctamente");
                vista.mostrarMensaje("Kilometraje actualizado correctamente");*/
                //notificarMantenimientoGuardado();

                cargarVehiculos();
                vista.limpiar();
                vista.cargarMantenimientos();
                vista.getVistaMantenimientos().setVisibility(View.VISIBLE);
                negocioMantenimiento.limpiarDetalles();
                actListDetMantenimiento();
            } else {
                vista.mostrarMensaje("Error al guardar datos");
            }
        });
        vista.getBtnAñadir().setOnClickListener(v -> {
            getFormToDetalleMantenimiento();
            vista.getBtnGuardar().setVisibility(View.VISIBLE);
            vista.getVistaDetalleMantenimiento().setVisibility(View.VISIBLE);
            vista.limpiarDetalleMantenimiento();
        });
        vista.getBtnListar().setOnClickListener(v -> {
            vista.cargarMantenimientos();
            vista.limpiar();
            vista.getVistaMantenimientos().setVisibility(View.VISIBLE);
        });
    }
    public ArrayAdapter<Map<String, String>> getAapaterListView(Context context){
        List<Map<String,String>> lista = negocioMantenimiento.mantenimientosMap();
        ArrayAdapter<Map<String,String>> adapter = new ArrayAdapter<Map<String,String>>(context, android.R.layout.simple_list_item_1, lista) {
            int padding = 16;
            @SuppressLint("SetTextI18n")
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view;
                negocioVehiculo.getModelo().setId(Integer.parseInt(lista.get(position).get("vehiculo_id")));
                Map<String, String> vehiculo = negocioVehiculo.getModelForId();
                negocioMecanico.getModelo().setId(Integer.parseInt(lista.get(position).get("mecanico_id")));
                Map<String, String> mecanico = negocioMecanico.getModeloForId();
                String textMantenimiento = "ID: "+lista.get(position).get("ID")+
                        " | Fecha: "+lista.get(position).get("Fecha")+
                        "\nVehiculo: "+vehiculo.get("placa")+
                        "\nMecanico: "+mecanico.get("nombre")+
                        "\nKilometraje de Mantenimiento: "+lista.get(position).get("Kilometraje")+
                        "\nDetalle: "+lista.get(position).get("Detalle")+
                        "\nCosto Total: "+lista.get(position).get("Costo Total");

                List<Map<String,String>> detalles = negocioMantenimiento.detallesMap(Integer.parseInt(lista.get(position).get("ID")));
                String detStr = "";
                if(!detalles.isEmpty()) {
                    for (Map<String,String> d : detalles){
                        negocioItem.getModelo().setId(Integer.parseInt(d.get("item_id")));
                        Map<String,String> item = negocioItem.obtenerModeloPorId();
                        detStr = detStr +item.get("nombre")+
                                " | Precio: "+d.get("precio_unitario")+
                                " | Cant.: "+d.get("cantidad")+
                                " | Subtotal: "+d.get("subtotal")+"\n";
                    }
                }

                textMantenimiento = textMantenimiento +" \nLista Detalle: [ \n"+detStr+" ]";
                textView.setText(textMantenimiento);
                textView.setPadding(padding, padding, padding, padding);
                return view;
            }
        };
        return adapter;
    }
    public void cargarVehiculos() {
        ArrayAdapter<Map<String,String>> adapter = negocioVehiculo.getSpinnerAdapter(vista.getApplicationContext());
        vista.getSpModVehiculo().setAdapter(adapter);
    }
    public void cargarItems() {
        ArrayAdapter<Map<String,String>> adapter = negocioItem.getArrayAdapterSpinner(vista.getApplicationContext());
        vista.getSpModItem().setAdapter(adapter);
    }
    public void cargarMecanicos() {
        ArrayAdapter<Map<String,String>> adapter = negocioMecanico.getArrayAdapterSpinner(vista.getApplicationContext());
        vista.getSpModMecanico().setAdapter(adapter);
    }
    public void cargarFecha() {
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        vista.setEtFecha(sdf.format(new Date()));
    }
    public void getFormToDetalleMantenimiento() {
        String idItem = vista.getIdItem().getText().toString();
        String cantidad = vista.getEtCantidad().getText().toString();
        String precioItem = vista.getEtPrecioItem().getText().toString();
        if (idItem.isEmpty() || cantidad.isEmpty() || precioItem.isEmpty()) {
            vista.mostrarMensaje("Por favor complete todos los campos");
            return;
        }
        negocioMantenimiento.setDetalleMantenimientoSetToList(Integer.parseInt(idItem), Double.parseDouble(precioItem), Double.parseDouble(cantidad));
        actListDetMantenimiento();
        vista.setTxtTotal(String.valueOf(negocioMantenimiento.calculoTotalDetalle()));
    }
    public void actListDetMantenimiento() {
        List<Map<String,String>> details = negocioMantenimiento.getDetailsMantemimiento();
        if (details.isEmpty()) {
            vista.mostrarMensaje("No hay detalles de mantenimiento registrados");
        }
        List<Map<String,String>> items = negocioItem.getItemOfList(negocioMantenimiento.getIdsItems());
        List<Map<String,String>> detailsCompleto = new ArrayList<>();
        for (int i = 0; i < details.size(); i++) {
            Map<String,String> item = Map.of(
                    "id", details.get(i).get("id"),
                    "nombre", items.get(i).get("nombre"),
                    "precio_unitario", details.get(i).get("precio_unitario"),
                    "cantidad", details.get(i).get("cantidad"),
                    "subtotal", details.get(i).get("subtotal")
            );
            detailsCompleto.add(item);
        }
        ArrayAdapter<Map<String,String>> adapter = new ArrayAdapter<>(vista.getApplicationContext(), android.R.layout.simple_list_item_1, detailsCompleto){
            int padding = 16;
            @SuppressLint("SetTextI18n")
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view;
                String textDetalle = detailsCompleto.get(position).get("nombre")+
                        " | Precio: "+detailsCompleto.get(position).get("precio_unitario")+
                        " | Cant.: "+detailsCompleto.get(position).get("cantidad")+
                        " | Subtotal: "+detailsCompleto.get(position).get("subtotal");
                textView.setText(textDetalle);
                textView.setPadding(padding, padding, padding, padding);
                return view;
            }
        };
        vista.getListViewDetalleMantenimiento().setAdapter(adapter);
    }
}
