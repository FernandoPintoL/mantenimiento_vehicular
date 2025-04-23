package com.fpl.mantenimientovehicular.vista;

import android.Manifest;
import android.app.TimePickerDialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.fpl.mantenimientovehicular.R;
import com.fpl.mantenimientovehicular.controller.NotificationController;
import com.fpl.mantenimientovehicular.negocio.NegocioNotificacion;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class VistaNotificacion extends AppCompatActivity {
    private NotificationController controller;
    private NegocioNotificacion negocio;
    private TextView idNotificacion, idVehiculoNotificacion;
    private EditText etTitleNotificacion, etMensajeNotificacion, etIntervaloNotificacion, etKilometrajeObjetivoNotificacion;
    private Spinner spVehiculo;
    private Switch swActivoNotificacion;
    private ListView listViewNotificaciones;
    private LinearLayout btnsActionsNotify, idNotificacionLayout;
    private Button btnGuardar, btnEliminar, btnModificar, btnListar;
//    private NotificacionAdapter adapterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_notificaciones);
        negocio = new NegocioNotificacion(this);
        controller = new NotificationController(this, negocio);

        spVehiculo = findViewById(R.id.spVehiculoNotificacion);
        swActivoNotificacion = findViewById(R.id.swActivoNotificacion);
        etTitleNotificacion = findViewById(R.id.etTitleNotificacion);
        etMensajeNotificacion = findViewById(R.id.etMensajeNotificacion);
        etIntervaloNotificacion = findViewById(R.id.etIntervaloNotificacion);
        etKilometrajeObjetivoNotificacion = findViewById(R.id.etKmObjetivoNotificacion);
        idNotificacion = findViewById(R.id.idNotificacion);
        idVehiculoNotificacion = findViewById(R.id.idVehiculoNotificacion);
        idNotificacionLayout = findViewById(R.id.idLinearNotify);
        listViewNotificaciones = findViewById(R.id.listViewNotificaciones);
        btnsActionsNotify = findViewById(R.id.btnsActionsNotify);
        btnEliminar = findViewById(R.id.btnEliminarNotify);
        btnModificar = findViewById(R.id.btnEditarNotify);
        btnListar = findViewById(R.id.btnListarNotify);
        btnGuardar = findViewById(R.id.btnGuardarNotify);

        cargarAdapterView();
        controller.initEvents();
        etIntervaloNotificacion.setOnClickListener(v -> openTime());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }
        controller.listenNotificaciones();
    }
    @Override
    protected void onResume() {
        super.onResume();
        cargarAdapterView();
    }
    public void cargarAdapterView(){
        List<Map<String,String>> datos = negocio.cargarDatos();
        ArrayAdapter<Map<String,String>> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datos){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                Map<String, String> item = getItem(position);
                if (item != null) {
                    String displayText = "ID: " + item.get("id") + "\n" +
                            "Título: " + item.get("titulo") + "\n" +
                            "Mensaje: " + item.get("mensaje") + "\n" +
                            "Kilometraje Objetivo: " + item.get("kilometraje_objetivo") + "\n" +
                            "Intervalo Notificación: " + item.get("intervalo_notificacion") + "\n" +
                            "Activo: " + item.get("activo");
                    ((TextView) view).setText(displayText);
                    // padding
                    int padding = 16; // Padding en píxeles
                    ((TextView) view).setPadding(padding, padding, padding, padding);
                }
                return view;
            }
        };
        listViewNotificaciones.setAdapter(adapter);
    }
    public void openTime(){
        // Implementación para abrir el TimePicker
        // Obtener la hora actual
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Crear el TimePickerDialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, selectedHour, selectedMinute) -> {
            // Formatear la hora seleccionada y establecerla en el EditText
            String formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute);
            etIntervaloNotificacion.setText(formattedTime);
        }, hour, minute, true); // true para formato de 24 horas

        // Mostrar el TimePickerDialog
        timePickerDialog.show();
    }
    public void limpiarCampos() {
        etTitleNotificacion.setText("");
        etMensajeNotificacion.setText("");
        etIntervaloNotificacion.setText("");
        etKilometrajeObjetivoNotificacion.setText("");
        idNotificacion.setText("");
        idVehiculoNotificacion.setText("");
        swActivoNotificacion.setChecked(false);
    }
    public void mostrarMensaje(String mensaje) {
        // Implementación para mostrar un mensaje al usuario
        // Puedes usar un Toast, Snackbar o cualquier otro método que prefieras
        // Por ejemplo, usando un Toast:
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
    public TextView getIdNotificacion() {
        return idNotificacion;
    }
    public EditText getEtTitleNotificacion() {
        return etTitleNotificacion;
    }
    public EditText getEtMensajeNotificacion() {
        return etMensajeNotificacion;
    }
    public EditText getEtIntervaloNotificacion() {
        return etIntervaloNotificacion;
    }
    public void setEtIntervaloNotificacion(String etIntervaloNotificacion) {
        this.etIntervaloNotificacion.setText(etIntervaloNotificacion);
    }
    public Switch getSwActivoNotificacion() {
        return swActivoNotificacion;
    }
    public ListView getListViewNotificaciones() {
        return listViewNotificaciones;
    }
    public LinearLayout getBtnsActionsNotify() {
        return btnsActionsNotify;
    }
    public Button getBtnGuardar() {
        return btnGuardar;
    }
    public Button getBtnEliminar() {
        return btnEliminar;
    }
    public Button getBtnModificar() {
        return btnModificar;
    }
    public Button getBtnListar() {
        return btnListar;
    }
    public Spinner getSpVehiculo() {
        return spVehiculo;
    }
    public TextView getIdVehiculoNotificacion() {
        return idVehiculoNotificacion;
    }
    public EditText getEtKilometrajeObjetivoNotificacion() {
        return etKilometrajeObjetivoNotificacion;
    }
    public LinearLayout getIdNotificacionLayout() {
        return idNotificacionLayout;
    }
}
