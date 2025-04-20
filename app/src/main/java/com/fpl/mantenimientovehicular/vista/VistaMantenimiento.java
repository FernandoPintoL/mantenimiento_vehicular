package com.fpl.mantenimientovehicular.vista;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.fpl.mantenimientovehicular.R;
import com.fpl.mantenimientovehicular.controller.MantenimientoDetController;
import com.fpl.mantenimientovehicular.negocio.NegocioMantenimiento;
import com.fpl.mantenimientovehicular.service.AlarmaKilometraje;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VistaMantenimiento extends AppCompatActivity {
    private AlarmaKilometraje alarmaKilometraje;
    private MantenimientoDetController mantenimientoDetController;
    private NegocioMantenimiento negocio;
    private List<String> listadoItem, listadoVehiculo, listadoMecanico, listadoMantenimiento, listadoDetalleMantenimiento;
    private ArrayAdapter<String> adapterMantenimientos, adapterMecanicos, adapterVehiculos, adapterItems, adapterDetalles;
    private ListView listViewDetalleMantenimiento, listViewMantenimientos;
    private Button btnGuardar, btnListar, btnAñadir;
    private EditText etFecha, etKilometrajeActual, etKilometrajeObjetivo, etDetalle, etCantidad, etPrecioItem;
    private Spinner spModVehiculo, spModMecanico, spModItem;
    private TextView txtTotal, txtTitleMantenimientos, idVehiculo, idMecanico, idItem;
    private LinearLayout vistaDetalleMantenimiento, vistaMantenimientos;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantenimiento_main);

        // Solicitar permiso para notificaciones (Android 13 o superior)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }

        // Configurar una notificación recurrente cada 24 horas
        int intervalo24Horas = 24 * 60 * 60 * 1000; // 24 horas en milisegundos
        int intervalo1min = 1 * 60 * 1000; // 1 minuto en milisegundos
        AlarmaKilometraje.programarAlarmaRecurrente(this, intervalo1min);


        negocio = new NegocioMantenimiento(this);
        mantenimientoDetController = new MantenimientoDetController(this, negocio);

        listadoVehiculo = new ArrayList<>();
        listadoMecanico = new ArrayList<>();
        listadoItem = new ArrayList<>();
        listadoMantenimiento = new ArrayList<>();
        listadoDetalleMantenimiento = new ArrayList<>();

        idItem = findViewById(R.id.idItem);
        idMecanico = findViewById(R.id.idMecanico);
        idVehiculo = findViewById(R.id.idVehiculo);
        etFecha = findViewById(R.id.etFecha);
        etKilometrajeActual = findViewById(R.id.etKilometrajeActualMant);
        etKilometrajeObjetivo = findViewById(R.id.etKilometrajeObjetivoMant);
        etDetalle = findViewById(R.id.etDetalleMantenimiento);
        etPrecioItem = findViewById(R.id.etPrecioItem);
        spModVehiculo = findViewById(R.id.spModVehiculo);
        spModMecanico = findViewById(R.id.spModMecanico);
        spModItem = findViewById(R.id.spModItem);
        etCantidad = findViewById(R.id.etCantidad);
        txtTotal = findViewById(R.id.txtTotal);
        txtTitleMantenimientos = findViewById(R.id.txtTitleMantenimientos);
        vistaDetalleMantenimiento = findViewById(R.id.vistaDetalleMantenimiento);
        vistaMantenimientos = findViewById(R.id.vistaMantenimientos);
        btnGuardar = findViewById(R.id.btnGuardarMantenimiento);
        btnListar = findViewById(R.id.btnListarMantenimientos);
        btnAñadir = findViewById(R.id.btnAnhadirItem);
        listViewMantenimientos = findViewById(R.id.listViewMantenimientos);
        listViewDetalleMantenimiento = findViewById(R.id.listViewDetalleMantenimientos);
        mantenimientoDetController.initEvents();
//        etFecha.setOnClickListener(v -> openDateTime());

        /*spModItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        btnGuardar.setOnClickListener(v -> ejecutarAccion());
        btnAñadir.setOnClickListener(v -> anhadirItemDetalle());
        btnListar.setOnClickListener(v-> obtenerMantenimientos());*/
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    public void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
    public void limpiar(){
        etKilometrajeActual.setText("");
        etKilometrajeObjetivo.setText("");
        etDetalle.setText("");
        idItem.setText("");
        idMecanico.setText("");
        idVehiculo.setText("");
        txtTotal.setText("");
        spModVehiculo.setSelection(0);
        spModMecanico.setSelection(0);
        listadoDetalleMantenimiento = new ArrayList<>();
        adapterDetalles = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listadoDetalleMantenimiento);
        btnGuardar.setVisibility(View.GONE);
        vistaDetalleMantenimiento.setVisibility(View.GONE);
    }
    public void limpiarDetalleMantenimiento(){
        etCantidad.setText("");
        etCantidad.setHint("Cantidad");
        etPrecioItem.setText("");
        idItem.setText("");
    }
    public void openDateTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
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
    }
    public TextView getIdVehiculo() {
        return idVehiculo;
    }
    public TextView getIdMecanico() {
        return idMecanico;
    }
    public TextView getIdItem() {
        return idItem;
    }
    public TextView getTxtTotal() {
        return txtTotal;
    }
    public EditText getEtKilometrajeActual() {
        return etKilometrajeActual;
    }
    public EditText getEtKilometrajeObjetivo() {
        return etKilometrajeObjetivo;
    }
    public EditText getEtFecha() {
        return etFecha;
    }
    public EditText getEtDetalle() {
        return etDetalle;
    }
    public EditText getEtCantidad() {
        return etCantidad;
    }
    public EditText getEtPrecioItem() {
        return etPrecioItem;
    }
    public Spinner getSpModVehiculo() {
        return spModVehiculo;
    }
    public Spinner getSpModMecanico() {
        return spModMecanico;
    }
    public Spinner getSpModItem() {
        return spModItem;
    }
    public Button getBtnGuardar() {
        return btnGuardar;
    }
    public Button getBtnListar() {
        return btnListar;
    }
    public Button getBtnAñadir() {
        return btnAñadir;
    }
    public ArrayAdapter<String> getAdapterMantenimientos() {
        return adapterMantenimientos;
    }
    public ArrayAdapter<String> getAdapterMecanicos() {
        return adapterMecanicos;
    }
    public ArrayAdapter<String> getAdapterVehiculos() {
        return adapterVehiculos;
    }
    public ArrayAdapter<String> getAdapterItems() {
        return adapterItems;
    }
    public ArrayAdapter<String> getAdapterDetalles() {
        return adapterDetalles;
    }
    public ListView getListViewMantenimientos() {
        return listViewMantenimientos;
    }
    public ListView getListViewDetalleMantenimiento() {
        return listViewDetalleMantenimiento;
    }
    public LinearLayout getVistaDetalleMantenimiento() {
        return vistaDetalleMantenimiento;
    }
    public LinearLayout getVistaMantenimientos() {
        return vistaMantenimientos;
    }
    public List<String> getListadoMantenimiento() {
        return listadoMantenimiento;
    }
    public List<String> getListadoDetalleMantenimiento() {
        return listadoDetalleMantenimiento;
    }
    public List<String> getListadoItem() {
        return listadoItem;
    }
    public List<String> getListadoVehiculo() {
        return listadoVehiculo;
    }
    public List<String> getListadoMecanico() {
        return listadoMecanico;
    }
    public void setVistaMantenimientos(LinearLayout vistaMantenimientos) {
        this.vistaMantenimientos = vistaMantenimientos;
    }
    public void setVistaDetalleMantenimiento(LinearLayout vistaDetalleMantenimiento) {
        this.vistaDetalleMantenimiento = vistaDetalleMantenimiento;
    }
    public void setListViewMantenimientos(ListView listViewMantenimientos) {
        this.listViewMantenimientos = listViewMantenimientos;
    }
    public void setListViewDetalleMantenimiento(ListView listViewDetalleMantenimiento) {
        this.listViewDetalleMantenimiento = listViewDetalleMantenimiento;
    }
    public void setBtnGuardar(Button btnGuardar) {
        this.btnGuardar = btnGuardar;
    }
    public void setBtnListar(Button btnListar) {
        this.btnListar = btnListar;
    }
    public void setBtnAñadir(Button btnAñadir) {
        this.btnAñadir = btnAñadir;
    }
    public void setSpModVehiculo(Spinner spModVehiculo) {
        this.spModVehiculo = spModVehiculo;
    }
    public void setSpModMecanico(Spinner spModMecanico) {
        this.spModMecanico = spModMecanico;
    }
    public void setSpModItem(Spinner spModItem) {
        this.spModItem = spModItem;
    }
    public void setEtKilometrajeActual(String etKilometraje) {
        this.etKilometrajeActual.setText(etKilometraje);
    }
    public void setEtKilometrajeObjetivo(String etKilometraje) {
        this.etKilometrajeObjetivo.setText(etKilometraje);
    }
    public void setEtFecha(String etFecha) {
        this.etFecha.setText(etFecha);
    }
    public void setEtDetalle(String etDetalle) {
        this.etDetalle.setText(etDetalle);
    }
    public void setEtCantidad(String etCantidad) {
        this.etCantidad.setText(etCantidad);
    }
    public void setIdVehiculo(String idVehiculo) {
        this.idVehiculo.setText(idVehiculo);
    }
    public void setIdMecanico(String idMecanico) {
        this.idMecanico.setText(idMecanico);
    }
    public void setIdItem(String idItem) {
        this.idItem.setText(idItem);
    }
    public void setTxtTotal(String txtTotal) {
        this.txtTotal.setText(txtTotal);
    }
    public void setTxtTitleMantenimientos(String txtTitleMantenimientos) {
        this.txtTitleMantenimientos.setText(txtTitleMantenimientos);
    }
    public void setEtPrecioItem(String precioItem) {
        this.etPrecioItem.setText(precioItem);
    }
    public void setListadoItem(List<String> listadoItem) {
        this.listadoItem = listadoItem;
    }
    public void setListadoVehiculo(List<String> listadoVehiculo) {
        this.listadoVehiculo = listadoVehiculo;
    }
    public void setListadoMecanico(List<String> listadoMecanico) {
        this.listadoMecanico = listadoMecanico;
    }
    public void setListadoMantenimiento(List<String> listadoMantenimiento) {
        this.listadoMantenimiento = listadoMantenimiento;
    }
    public void setListadoDetalleMantenimiento(List<String> listadoDetalleMantenimiento) {
        this.listadoDetalleMantenimiento = listadoDetalleMantenimiento;
    }
    public void setAdapterMantenimientos(ArrayAdapter<String> adapterMantenimientos) {
        this.adapterMantenimientos = adapterMantenimientos;
    }
    public void setAdapterMecanicos(ArrayAdapter<String> adapterMecanicos) {
        this.adapterMecanicos = adapterMecanicos;
    }
    public void setAdapterVehiculos(ArrayAdapter<String> adapterVehiculos) {
        this.adapterVehiculos = adapterVehiculos;
    }
    public void setAdapterItems(ArrayAdapter<String> adapterItems) {
        this.adapterItems = adapterItems;
    }
    public void setAdapterDetalles(ArrayAdapter<String> adapterDetalles) {
        this.adapterDetalles = adapterDetalles;
    }

    /*
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
    }*/
}
