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
import java.util.Map;

public class VistaMantenimiento extends AppCompatActivity {
    private MantenimientoDetController mantenimientoDetController;
    private NegocioMantenimiento negocio;
    private ListView listViewDetalleMantenimiento, listViewMantenimientos;
    private Button btnGuardar, btnListar, btnAñadir;
    private EditText etFecha, etKilometrajeActual, etKilometrajeObjetivo, etDetalle, etCantidad, etPrecioItem;
    private Spinner spModVehiculo, spModMecanico, spModItem;
    private TextView txtTotal, idVehiculo, idMecanico, idItem;
    private LinearLayout vistaDetalleMantenimiento, vistaMantenimientos;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantenimiento_main);

        negocio = new NegocioMantenimiento(this);
        mantenimientoDetController = new MantenimientoDetController(this, negocio);

        idItem = findViewById(R.id.idItemMantenimiento);
        idMecanico = findViewById(R.id.idMecanicoMantenimiento);
        idVehiculo = findViewById(R.id.idVehiculoMantenimiento);
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
        vistaDetalleMantenimiento = findViewById(R.id.vistaDetalleMantenimiento);
        vistaMantenimientos = findViewById(R.id.vistaMantenimientos);
        btnGuardar = findViewById(R.id.btnGuardarMantenimiento);
        btnListar = findViewById(R.id.btnListarMantenimientos);
        btnAñadir = findViewById(R.id.btnAnhadirItem);
        listViewMantenimientos = findViewById(R.id.listViewMantenimientos);
        listViewDetalleMantenimiento = findViewById(R.id.listViewDetalleMantenimientos);
        mantenimientoDetController.initEvents();
        cargarMantenimientos();
    }
    @Override
    protected void onResume() {
        super.onResume();
        cargarMantenimientos();
    }
    public void cargarMantenimientos(){
        ArrayAdapter<Map<String,String>> adapter = mantenimientoDetController.getAapaterListView(this);
        listViewMantenimientos.setAdapter(adapter);
        if (adapter.getCount() == 0) {
            Toast.makeText(this, "No hay mantenimientos registrados", Toast.LENGTH_SHORT).show();
            vistaMantenimientos.setVisibility(View.GONE);
        } else {
            vistaMantenimientos.setVisibility(View.VISIBLE);
        }
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
    public ListView getListViewDetalleMantenimiento() {
        return listViewDetalleMantenimiento;
    }
    public LinearLayout getVistaDetalleMantenimiento() {
        return vistaDetalleMantenimiento;
    }
    public LinearLayout getVistaMantenimientos() {
        return vistaMantenimientos;
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
    public void setEtPrecioItem(String precioItem) {
        this.etPrecioItem.setText(precioItem);
    }
}
