package com.fpl.mantenimientovehicular.vista;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.fpl.mantenimientovehicular.strategy.NotificationContext;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class VistaNotificacion extends AppCompatActivity {
    private NotificationController controller;
    private NegocioNotificacion negocio;
    private TextView idNotificacion, idVehiculoNotificacion;
    private EditText etTitleNotificacion, etMensajeNotificacion, etIntervaloNotificacion, etKilometrajeObjetivoNotificacion;
    private EditText etCantidadHoras, etCantidadMinutos, etCantidadDias, etHoraDiaria;
    private Spinner spVehiculo, spTipoNotificacion, spTipoIntervalo;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch swActivoNotificacion;
    private ListView listViewNotificaciones;
    private LinearLayout btnsActionsNotify, idNotificacionLayout;
    private LinearLayout layoutIntervaloHoras, layoutIntervaloDias, layoutIntervaloDiario;
    private Button btnGuardar, btnEliminar, btnModificar, btnListar;

    // Constantes para los tipos de intervalo
    private static final String TIPO_INTERVALO_HORAS = "Cada X horas";
    private static final String TIPO_INTERVALO_DIAS = "Cada X días a una hora específica";
    private static final String TIPO_INTERVALO_DIARIO = "Todos los días a una hora específica";
//    private NotificacionAdapter adapterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_notificaciones);
        negocio = new NegocioNotificacion(this);
        controller = new NotificationController(this, negocio);

        spVehiculo = findViewById(R.id.spVehiculoNotificacion);
        spTipoNotificacion = findViewById(R.id.spTipoNotificacion);
        spTipoIntervalo = findViewById(R.id.spTipoIntervalo);
        swActivoNotificacion = findViewById(R.id.swActivoNotificacion);
        etTitleNotificacion = findViewById(R.id.etTitleNotificacion);
        etMensajeNotificacion = findViewById(R.id.etMensajeNotificacion);
        etIntervaloNotificacion = findViewById(R.id.etIntervaloNotificacion);
        etKilometrajeObjetivoNotificacion = findViewById(R.id.etKmObjetivoNotificacion);
        etCantidadHoras = findViewById(R.id.etCantidadHoras);
        etCantidadMinutos = findViewById(R.id.etCantidadMinutos);
        etCantidadDias = findViewById(R.id.etCantidadDias);
        etHoraDiaria = findViewById(R.id.etHoraDiaria);
        idNotificacion = findViewById(R.id.idNotificacion);
        idVehiculoNotificacion = findViewById(R.id.idVehiculoNotificacion);
        idNotificacionLayout = findViewById(R.id.idLinearNotify);
        listViewNotificaciones = findViewById(R.id.listViewNotificaciones);
        btnsActionsNotify = findViewById(R.id.btnsActionsNotify);
        btnEliminar = findViewById(R.id.btnEliminarNotify);
        btnModificar = findViewById(R.id.btnEditarNotify);
        btnListar = findViewById(R.id.btnListarNotify);
        btnGuardar = findViewById(R.id.btnGuardarNotify);

        // Inicializar los layouts de intervalo
        layoutIntervaloHoras = findViewById(R.id.layoutIntervaloHoras);
        layoutIntervaloDias = findViewById(R.id.layoutIntervaloDias);
        layoutIntervaloDiario = findViewById(R.id.layoutIntervaloDiario);

        // Configurar el spinner de tipo de intervalo
        configurarSpinnerTipoIntervalo();

        // Configurar el spinner de tipo de notificación
        configurarSpinnerTipoNotificacion();

        cargarAdapterView();
        controller.initEvents();
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
    public void limpiarCampos() {
        etTitleNotificacion.setText("");
        etMensajeNotificacion.setText("");
        etIntervaloNotificacion.setText("");
        etKilometrajeObjetivoNotificacion.setText("");
        etCantidadHoras.setText("");
        etCantidadMinutos.setText("");
        etCantidadDias.setText("");
        etHoraDiaria.setText("");
        idNotificacion.setText("");
        idVehiculoNotificacion.setText("");
        swActivoNotificacion.setChecked(false);

        // Restablecer el tipo de intervalo al valor predeterminado (Horas)
        spTipoIntervalo.setSelection(0);
        mostrarLayoutIntervaloSeleccionado(TIPO_INTERVALO_HORAS);
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
    public void setEtIntervaloNotificacion(String etIntervaloNotificacion) { this.etIntervaloNotificacion.setText(etIntervaloNotificacion); }
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
    public Spinner getSpTipoNotificacion() {return spTipoNotificacion;}
    public Spinner getSpTipoIntervalo() {return spTipoIntervalo;}
    public EditText getEtCantidadHoras() {return etCantidadHoras;}
    public EditText getEtCantidadMinutos() {return etCantidadMinutos;}
    public EditText getEtCantidadDias() {return etCantidadDias;}
    public EditText getEtHoraDiaria() {return etHoraDiaria;}
    public String getTIPO_INTERVALO_HORAS() {
        return TIPO_INTERVALO_HORAS;
    }
    public String getTIPO_INTERVALO_DIAS() {
        return TIPO_INTERVALO_DIAS;
    }
    public String getTIPO_INTERVALO_DIARIO() {
        return TIPO_INTERVALO_DIARIO;
    }
    private void configurarSpinnerTipoNotificacion() {
        // Crear un array con los nombres de los tipos de notificación
        String[] tiposNotificacion = negocio.getTiposNotificacion();
        // Crear un adaptador para el spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
            this, android.R.layout.simple_spinner_item, tiposNotificacion
        );
        // Especificar el layout para el dropdown
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Asignar el adaptador al spinner
        spTipoNotificacion.setAdapter(adapter);
        // Establecer el valor por defecto (Normal)
        spTipoNotificacion.setSelection(0);
    }
    private void configurarSpinnerTipoIntervalo() {
        // Crear un array con los nombres de los tipos de intervalo
        String[] tiposIntervalo = {
            TIPO_INTERVALO_HORAS,
            TIPO_INTERVALO_DIAS,
            TIPO_INTERVALO_DIARIO
        };
        // Crear un adaptador para el spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
            this, android.R.layout.simple_spinner_item, tiposIntervalo
        );
        // Especificar el layout para el dropdown
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Asignar el adaptador al spinner
        spTipoIntervalo.setAdapter(adapter);
        // Establecer el valor por defecto (Horas)
        spTipoIntervalo.setSelection(0);
        // Mostrar inicialmente solo el layout de horas
        mostrarLayoutIntervaloSeleccionado(TIPO_INTERVALO_HORAS);
        // Configurar el listener para cambiar el layout según el tipo seleccionado
        spTipoIntervalo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tipoSeleccionado = (String) parent.getItemAtPosition(position);
                mostrarLayoutIntervaloSeleccionado(tipoSeleccionado);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No hacer nada
            }
        });
        // Configurar los time pickers para los campos de hora
        etIntervaloNotificacion.setOnClickListener(v -> openTimePicker(etIntervaloNotificacion));
        etHoraDiaria.setOnClickListener(v -> openTimePicker(etHoraDiaria));
    }
    private void mostrarLayoutIntervaloSeleccionado(String tipoIntervalo) {
        // Ocultar todos los layouts
        layoutIntervaloHoras.setVisibility(View.GONE);
        layoutIntervaloDias.setVisibility(View.GONE);
        layoutIntervaloDiario.setVisibility(View.GONE);
        // Mostrar el layout correspondiente al tipo seleccionado
        switch (tipoIntervalo) {
            case TIPO_INTERVALO_HORAS:
                layoutIntervaloHoras.setVisibility(View.VISIBLE);
                break;
            case TIPO_INTERVALO_DIAS:
                layoutIntervaloDias.setVisibility(View.VISIBLE);
                break;
            case TIPO_INTERVALO_DIARIO:
                layoutIntervaloDiario.setVisibility(View.VISIBLE);
                break;
        }
    }
    private void openTimePicker(EditText editText) {
        // Obtener la hora actual
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Crear el TimePickerDialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, selectedHour, selectedMinute) -> {
            // Formatear la hora seleccionada y establecerla en el EditText
            @SuppressLint("DefaultLocale") String formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute);
            editText.setText(formattedTime);
        }, hour, minute, true); // true para formato de 24 horas

        // Mostrar el TimePickerDialog
        timePickerDialog.show();
    }
}
