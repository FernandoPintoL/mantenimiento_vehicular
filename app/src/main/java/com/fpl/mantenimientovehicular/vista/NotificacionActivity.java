package com.fpl.mantenimientovehicular.vista;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.fpl.mantenimientovehicular.R;
import com.fpl.mantenimientovehicular.controller.NotificacionController;
import com.fpl.mantenimientovehicular.negocio.NegocioNotificacion;

public class NotificacionActivity extends AppCompatActivity {
    private NotificacionController controller;
    private NegocioNotificacion negocio;
    private EditText etIdVehiculo, etIntervaloKm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_notificaciones);
        negocio = new NegocioNotificacion(this);
        controller = new NotificacionController(negocio, this);

        etIdVehiculo = findViewById(R.id.etIdVehiculo);
        etIntervaloKm = findViewById(R.id.etIntervaloKm);

        Button btnGuardar = findViewById(R.id.btnGuardarConfig);
        btnGuardar.setOnClickListener(v -> {
            int intervaloKm = Integer.parseInt(etIntervaloKm.getText().toString()
            );

            /*ModeloVehiculo vehiculo = obtenerVehiculoActual(); // Método para obtener el vehículo
            vehiculo.setProximoMantenimientoKm(vehiculo.getKilometraje() + intervaloKm);
            notifController.programarNotificacion(vehiculo);*/
        });
    }
}
