package com.fpl.mantenimientovehicular.vista;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fpl.mantenimientovehicular.R;
import com.fpl.mantenimientovehicular.controller.ItemController;
import com.fpl.mantenimientovehicular.controller.MantenimientoDetController;
import com.fpl.mantenimientovehicular.controller.MecanicoController;
import com.fpl.mantenimientovehicular.model.ModeloDetalleMantenimiento;
import com.fpl.mantenimientovehicular.model.ModeloItem;
import com.fpl.mantenimientovehicular.model.ModeloMantenimiento;
import com.fpl.mantenimientovehicular.model.ModeloMecanico;
import com.fpl.mantenimientovehicular.model.ModeloVehiculo;
import com.fpl.mantenimientovehicular.negocio.NegocioVehiculo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VistaMantenimiento extends AppCompatActivity {
    private MantenimientoDetController mantenimientoDetController;
    private NegocioVehiculo negocioVehiculo;
    private ItemController itemController;
    private MecanicoController mecanicoController;
    private ModeloMantenimiento modeloMantenimiento;
    private ModeloDetalleMantenimiento modeloDetalleMantenimiento;
    private ModeloVehiculo modeloVehiculo;
    private ModeloItem modeloItem;
    private ModeloMecanico modeloMecanico;
    private List<ModeloMantenimiento> listadoMantenimiento;
    private List<ModeloDetalleMantenimiento> listadoDetalleMantenimiento;
    private List<ModeloItem> listadoItem;
    private List<String> listadoVehiculo;
    private List<ModeloMecanico> listadoMecanico;
    private ListView listViewDetalleMantenimiento, listViewMantenimientos;
    private Button btnAction, btnListar, btnAñadir;
    private EditText etFecha, etKilometraje, etDetalle, etCantidad;
    private Spinner spModVehiculo, spModMecanico, spModItem;
    private Double total;
    private TextView txtTotal, txtTitleMantenimientos;
    private LinearLayout vistaDetalleMantenimiento, vistaKilometraje, vistaMantenimientos;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantenimiento_main);

        mantenimientoDetController = new MantenimientoDetController(this);
        negocioVehiculo = new NegocioVehiculo(this);

        modeloDetalleMantenimiento = new ModeloDetalleMantenimiento();
        modeloMantenimiento = new ModeloMantenimiento();

        etFecha = findViewById(R.id.etFecha);
        etKilometraje = findViewById(R.id.etKilometraje);
        etDetalle = findViewById(R.id.etDetalleMantenimiento);
        spModVehiculo = findViewById(R.id.spModVehiculo);
        spModMecanico = findViewById(R.id.spModMecanico);
        spModItem = findViewById(R.id.spModItem);
        etCantidad = findViewById(R.id.etCantidad);
        txtTotal = findViewById(R.id.txtTotal);
        txtTitleMantenimientos = findViewById(R.id.txtTitleMantenimientos);
        vistaDetalleMantenimiento = findViewById(R.id.vistaDetalleMantenimiento);
        vistaKilometraje = findViewById(R.id.vistaKilometraje);
        vistaMantenimientos = findViewById(R.id.vistaMantenimientos);
        btnAction = findViewById(R.id.btnActionMantenimiento);
        btnListar = findViewById(R.id.btnListarMantenimientos);
        btnAñadir = findViewById(R.id.btnAnhadirItem);
        listViewMantenimientos = findViewById(R.id.listViewMantenimientos);
        listViewDetalleMantenimiento = findViewById(R.id.listViewDetalleMantenimientos);

        spModItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position >= 0 && position < listadoItem.size()) {
                    // Obtener el item seleccionado
                    ModeloItem itemSeleccionado = listadoItem.get(position);
                    etCantidad.setHint("Cantidad de " + itemSeleccionado.getNombre());
                    etCantidad.setText("1");
                } else {
                    etCantidad.setHint("Cantidad");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //colocar la fecha del dia en etFecha
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        etFecha.setText(sdf.format(new Date()));
        etFecha.setOnClickListener(v -> {
            final Calendar calendario = Calendar.getInstance();
            int año = calendario.get(Calendar.YEAR);
            int mes = calendario.get(Calendar.MONTH);
            int dia = calendario.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    (view, year, monthOfYear, dayOfMonth) -> {
                        // Guardamos la fecha seleccionada
                        calendario.set(Calendar.YEAR, year);
                        calendario.set(Calendar.MONTH, monthOfYear);
                        calendario.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        // 2. Después de seleccionar la fecha, abrimos el TimePicker
                        int hora = calendario.get(Calendar.HOUR_OF_DAY);
                        int minuto = calendario.get(Calendar.MINUTE);

                        TimePickerDialog timePickerDialog = new TimePickerDialog(
                                this,
                                (timeView, hourOfDay, minute) -> {
                                    // Actualizamos la hora seleccionada
                                    calendario.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                    calendario.set(Calendar.MINUTE, minute);

                                    // Formateamos fecha + hora y la mostramos en el EditText
                                    String fechaHoraSeleccionada = sdf.format(calendario.getTime());
                                    etFecha.setText(fechaHoraSeleccionada);
                                },
                                hora, minuto, true // true = formato 24 horas
                        );
                        timePickerDialog.show();
                    },
                    año, mes, dia);
            datePickerDialog.show();
        });

        btnAction.setOnClickListener(v -> ejecutarAccion());
//        btnEliminar.setOnClickListener( v -> eliminar());
        btnAñadir.setOnClickListener(v -> anhadirItemDetalle());
        btnListar.setOnClickListener(v-> obtenerMantenimientos());

        cargarVehiculos();
        cargarItems();
        obtenerMantenimientos();
        cargarMecanicos();
    }
    @Override
    protected void onResume() {
        super.onResume();
        obtenerMantenimientos();
        cargarItems();
        cargarVehiculos();
        cargarMecanicos();
    }
    private void cargarVehiculos(){
        listadoVehiculo = negocioVehiculo.cargarDatos();

        ArrayAdapter<String> adapterVehiculo = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listadoVehiculo);
        adapterVehiculo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spModVehiculo.setAdapter(adapterVehiculo);
    }
    private void cargarItems(){
        //listadoItem = itemController.obtenerTodos();
        List<String> modelosItems = new ArrayList<>();
        for (ModeloItem model : listadoItem) {
            modelosItems.add(model.getNombre().toUpperCase()+" / "+model.getPrecio() + "Bs");
        }
        ArrayAdapter<String> adapterItem = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, modelosItems);
        adapterItem.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spModItem.setAdapter(adapterItem);
    }
    private void cargarMecanicos(){
        //listadoMecanico = mecanicoController.obtenerTodos();
        List<String> modelosMecanicos = new ArrayList<>();
        for (ModeloMecanico model : listadoMecanico) {
            modelosMecanicos.add("Nom.: "+model.getNombre()+" Taller: "+model.getTaller());
        }
        ArrayAdapter<String> adapterItem = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, modelosMecanicos);
        adapterItem.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spModMecanico.setAdapter(adapterItem);
        if(!listadoMecanico.isEmpty()){
            spModMecanico.setSelection(0);
        }
    }
    @SuppressLint("SetTextI18n")
    private void obtenerMantenimientos() {
        listadoMantenimiento = mantenimientoDetController.obtenerMantenimientos();
        // Crear una lista de cadenas con la información que deseas mostrar
        List<String> modelInfos = new ArrayList<>();
        for (ModeloMantenimiento model : listadoMantenimiento) {
            modelInfos.add("ID: "+model.getId()+" Fecha: "+model.getFecha()+" Kilometraje: "+model.getKilometraje().toString() + " Detalle: " + model.getDetalle()+" Monto Total: "+model.getCosto_total()+"Bs");
        }

        // Usar un ArrayAdapter simple para mostrar la información
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, modelInfos);
        listViewMantenimientos.setAdapter(adapter);

        if (listadoMantenimiento.isEmpty()){
            Toast.makeText(this, "No hay registros de mantenimientos", Toast.LENGTH_SHORT).show();
            vistaMantenimientos.setVisibility(View.GONE);
        }else{
            txtTitleMantenimientos.setText("MANTENIMIENTOS REGISTRADOS ("+listadoMantenimiento.size()+")");
            vistaMantenimientos.setVisibility(View.VISIBLE);
        }
    }
    private double calcularTotalDetalles() {
        double total = 0.0;
        if (listadoDetalleMantenimiento != null && !listadoDetalleMantenimiento.isEmpty()) {
            for (ModeloDetalleMantenimiento detalle : listadoDetalleMantenimiento) {
                total += detalle.getSubtotal();
            }
        }
        return total;
    }
    // Implementa el método anhadirItemDetalle() así:
    @SuppressLint("SetTextI18n")
    private void anhadirItemDetalle(){
        try {
            // Obtener la posición seleccionada en el Spinner
            int selectedPosition = spModItem.getSelectedItemPosition();

            if (selectedPosition >= 0 && selectedPosition < listadoItem.size()) {
                // Obtener el item seleccionado
                ModeloItem itemSeleccionado = listadoItem.get(selectedPosition);

                // Obtener la cantidad
                double cantidad = Double.parseDouble(etCantidad.getText().toString());

                // Crear el detalle de mantenimiento
                ModeloDetalleMantenimiento detalle = new ModeloDetalleMantenimiento();
                detalle.setItem_id(itemSeleccionado.getId());
                detalle.setCantidad(cantidad);
                detalle.setPrecio_unitario(itemSeleccionado.getPrecio());
                detalle.setSubtotal(cantidad * itemSeleccionado.getPrecio());

                // Agregar a la lista de detalles
                if (listadoDetalleMantenimiento == null) {
                    listadoDetalleMantenimiento = new ArrayList<>();
                }
                listadoDetalleMantenimiento.add(detalle);

                // Actualizar la lista visual
                actualizarListaDetalles();

                // Limpiar campos
                etCantidad.setText("");
                listViewDetalleMantenimiento.setVisibility(View.VISIBLE);
                btnAction.setVisibility(View.VISIBLE);

                //calcular el total de los subtotales
                total = calcularTotalDetalles();
                txtTotal.setText("Total: "+total.toString()+"Bs");
                txtTotal.setVisibility(View.VISIBLE);
                vistaDetalleMantenimiento.setVisibility(View.VISIBLE);

                Toast.makeText(this, "Item añadido al detalle", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Seleccione un item válido", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Ingrese una cantidad válida", Toast.LENGTH_SHORT).show();
        }
    }
    // Método para actualizar la lista visual de detalles
    private void actualizarListaDetalles() {
        if (listadoDetalleMantenimiento != null && !listadoDetalleMantenimiento.isEmpty()) {
            List<String> modelInfos = new ArrayList<>();
            for (ModeloDetalleMantenimiento model : listadoDetalleMantenimiento) {

                modelInfos.add("ID-Item: "+model.getItem_id()+
                        " Cantidad: "+model.getCantidad()+
                        " Precio: "+model.getPrecio_unitario() +
                        " Subtotal: " + model.getSubtotal()
                );
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, modelInfos);
            listViewDetalleMantenimiento.setAdapter(adapter);
        }
    }
    private void ejecutarAccion() {
        if (etFecha.getText().toString().isEmpty()){
            Toast.makeText(this, "La fecha es requerida", Toast.LENGTH_SHORT).show();
            return;
        }
        modeloMantenimiento.setFecha(etFecha.getText().toString());
        modeloMantenimiento.setKilometraje(Double.parseDouble(etKilometraje.getText().toString()));
        modeloMantenimiento.setDetalle(etDetalle.getText().toString());
        modeloMantenimiento.setCosto_total(total);

        if (modeloMantenimiento.getId() > 0) {
            // Actualizar
            if (mantenimientoDetController.actualizarMantenimientoDetalle(modeloMantenimiento, listadoDetalleMantenimiento) > 0) {
                Toast.makeText(this, "Registro Actualizado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Crear nuevo
            long id = mantenimientoDetController.agregarMantenimiento(modeloMantenimiento, listadoDetalleMantenimiento);
            if (id > 0) {
                modeloMantenimiento.setId((int) id);
                Toast.makeText(this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show();
            }
        }
        obtenerMantenimientos();
        limpiarFormulario();
    }
    private void limpiarFormulario() {
        listadoDetalleMantenimiento = new ArrayList<>();
        etFecha.setText("");
        etKilometraje.setText("");
        etDetalle.setText("");
        etCantidad.setText("");
        btnAction.setText("Guardar");
        etKilometraje.setVisibility(View.GONE);
        btnAction.setVisibility(View.GONE);
        vistaDetalleMantenimiento.setVisibility(View.GONE);
        modeloMantenimiento = new ModeloMantenimiento();
    }
}
