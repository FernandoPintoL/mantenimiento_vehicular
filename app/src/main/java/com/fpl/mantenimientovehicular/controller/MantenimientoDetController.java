package com.fpl.mantenimientovehicular.controller;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.fpl.mantenimientovehicular.negocio.NegocioItem;
import com.fpl.mantenimientovehicular.negocio.NegocioMantenimiento;
import com.fpl.mantenimientovehicular.negocio.NegocioMecanico;
import com.fpl.mantenimientovehicular.negocio.NegocioVehiculo;
import com.fpl.mantenimientovehicular.vista.VistaMantenimiento;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class MantenimientoDetController {
    private VistaMantenimiento vista;
    private NegocioMantenimiento negocioMantenimiento;
    private NegocioVehiculo negocioVehiculo;
    private NegocioItem negocioItem;
    private NegocioMecanico negocioMecanico;
    SimpleDateFormat sdf;
    public MantenimientoDetController(VistaMantenimiento vista, NegocioMantenimiento negocio) {
        this.vista = vista;
        this.negocioMantenimiento = negocio;
        this.negocioMecanico = new NegocioMecanico(vista.getApplicationContext());
        this.negocioVehiculo = new NegocioVehiculo(vista.getApplicationContext());
        this.negocioItem = new NegocioItem(vista.getApplicationContext());
    }
    public void initEvents() {
        cargarFecha();
        cargarMantenimientos();
        cargarItems();
        cargarMecanicos();
        cargarVehiculos();
        vista.getEtFecha().setOnClickListener(v -> vista.openDateTime());
        vista.getSpModItem().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0 && position < vista.getListadoItem().size()) {
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
                if (position >= 0 && position < vista.getListadoVehiculo().size()) {
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
                if (position >= 0 && position < vista.getListadoMecanico().size()) {
                    negocioMecanico.setPosicion(position);
                    int idMecanico = negocioMecanico.obtenerModeloPorPosicion().getId();
                    vista.setIdMecanico(String.valueOf(idMecanico));
                } else {
                    vista.getEtKilometrajeActual().setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        vista.getListViewDetalleMantenimiento().setOnItemClickListener((parent, view, position, id) -> {
            negocioMantenimiento.eliminarDetailsPos(position);
            vista.getListadoDetalleMantenimiento().remove(position);
            vista.getAdapterDetalles().notifyDataSetChanged();
            vista.getTxtTotal().setText(String.valueOf(negocioMantenimiento.calculoTotalDetalle()));
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
                vista.mostrarMensaje("Datos guardados correctamente");
                vista.mostrarMensaje("Kilometraje actualizado correctamente");
                cargarVehiculos();
                vista.limpiar();
                cargarMantenimientos();
                vista.getVistaMantenimientos().setVisibility(View.VISIBLE);
                negocioMantenimiento.limpiarDetalles();
            } else {
                vista.mostrarMensaje("Error al guardar datos");
            }
        });
        vista.getBtnAñadir().setOnClickListener(v -> {
            getFormToDetalleMantenimiento();
            vista.getTxtTotal().setText(String.valueOf(negocioMantenimiento.calculoTotalDetalle()));
            vista.limpiarDetalleMantenimiento();
            actListDetMantenimiento();
            vista.getBtnGuardar().setVisibility(View.VISIBLE);
            vista.getVistaDetalleMantenimiento().setVisibility(View.VISIBLE);
        });
        vista.getBtnListar().setOnClickListener(v -> {
            cargarMantenimientos();
            vista.getVistaMantenimientos().setVisibility(View.VISIBLE);
        });
    }

    public void cargarMantenimientos() {
        List<String> list = negocioMantenimiento.cargarDatosMantenimientoToListStr();
        vista.setListadoMantenimiento(list);
        vista.setAdapterMantenimientos(new ArrayAdapter<>(vista.getApplicationContext(), android.R.layout.simple_list_item_1, list));
        vista.getListViewMantenimientos().setAdapter(vista.getAdapterMantenimientos());
        if (list.isEmpty()) {
            vista.mostrarMensaje("No hay mantenimientos registrados");
            vista.getVistaMantenimientos().setVisibility(View.GONE);
        }else{
            vista.getVistaMantenimientos().setVisibility(View.VISIBLE);
        }
    }
    public void cargarVehiculos() {
        List<String> list = negocioVehiculo.cargarDatosToSelect();
        vista.setListadoVehiculo(list);
        vista.setAdapterVehiculos(new ArrayAdapter<>(vista.getApplicationContext(), android.R.layout.simple_list_item_1, list));
        vista.getSpModVehiculo().setAdapter(vista.getAdapterVehiculos());
        if (list.isEmpty()) {
            vista.mostrarMensaje("No hay vehiculos registrados");
        } else {
            vista.getSpModVehiculo().setSelection(0);
            negocioVehiculo.setPosicion(0);
            int idVehiculo = negocioVehiculo.obtenerModeloPorPosicion().getId();
            int kilometraje = negocioVehiculo.obtenerModeloPorPosicion().getKilometrajeActual();
            int calculado = kilometraje + 5000;
            vista.setIdVehiculo(String.valueOf(idVehiculo));
            vista.setEtKilometrajeObjetivo(String.valueOf(calculado));
        }
    }
    public void cargarItems() {
        List<String> list = negocioItem.cargarDatos();
        vista.setListadoItem(list);
        vista.setAdapterItems(new ArrayAdapter<>(vista.getApplicationContext(), android.R.layout.simple_list_item_1, list));
        vista.getSpModItem().setAdapter(vista.getAdapterItems());
        if (list.isEmpty()) {
            vista.mostrarMensaje("No hay items registrados");
        }
    }

    public void cargarMecanicos() {
        List<String> list = negocioMecanico.cargarDatos();
        vista.setListadoMecanico(list);
        vista.setAdapterMecanicos(new ArrayAdapter<>(vista.getApplicationContext(), android.R.layout.simple_list_item_1, list));
        vista.getSpModMecanico().setAdapter(vista.getAdapterMecanicos());
        if (list.isEmpty()) {
            vista.mostrarMensaje("No hay mecanicos registrados");
        } else {
            vista.getSpModMecanico().setSelection(0);
            negocioMecanico.setPosicion(0);
            int idMecanico = negocioMecanico.obtenerModeloPorPosicion().getId();
            vista.setIdMecanico(String.valueOf(idMecanico));
        }
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
        negocioMantenimiento.setDetalleMantenimientoSetToList(
                0,
                0,
                Integer.parseInt(idItem),
                Double.parseDouble(precioItem),
                Double.parseDouble(cantidad)
        );
    }
    public void actListDetMantenimiento() {
        List<String> list = negocioMantenimiento.cargarDatosDetMantToListStr();
        if (list.isEmpty()) {
            vista.mostrarMensaje("No hay detalles de mantenimiento registrados");
        }
        vista.setListadoDetalleMantenimiento(list);
        vista.setAdapterDetalles(new ArrayAdapter<>(vista.getApplicationContext(), android.R.layout.simple_list_item_1, list));
        vista.getListViewDetalleMantenimiento().setAdapter(vista.getAdapterDetalles());
    }
}
