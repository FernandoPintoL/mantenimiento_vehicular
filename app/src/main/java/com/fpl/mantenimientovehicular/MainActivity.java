package com.fpl.mantenimientovehicular;

import androidx.activity.EdgeToEdge;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fpl.mantenimientovehicular.controller.NotificationController;
import com.fpl.mantenimientovehicular.negocio.NegocioNotificacion;
import com.fpl.mantenimientovehicular.vista.VistaNotificacion;
import com.fpl.mantenimientovehicular.vista.VistaItem;
import com.fpl.mantenimientovehicular.vista.VistaMantenimiento;
import com.fpl.mantenimientovehicular.vista.VistaMecanico;
import com.fpl.mantenimientovehicular.vista.VistaVehiculo;

public class MainActivity extends AppCompatActivity {
    NegocioNotificacion negocioNotificacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnVehiculos = findViewById(R.id.btnGestionarVehiculos);
        Button btnMecanicos = findViewById(R.id.btnGestionarMecanicos);
        Button btnItems = findViewById(R.id.btnGestionarItems);
        Button btnMantenimientos = findViewById(R.id.btnGestionarMantenimientos);
        Button btnNotificaciones = findViewById(R.id.btnGestionarNotificaciones);
//        Button btnGenerarDiagramas = findViewById(R.id.btnGenerarDiagramas);
        btnVehiculos.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, VistaVehiculo.class);
            startActivity(intent);
        });
        btnMecanicos.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, VistaMecanico.class);
            startActivity(intent);
        });
        btnItems.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, VistaItem.class);
            startActivity(intent);
        });
        btnMantenimientos.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, VistaMantenimiento.class);
            startActivity(intent);
        });
        btnNotificaciones.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, VistaNotificacion.class);
            startActivity(intent);
        });

        /*btnGenerarDiagramas.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, VistaDiagramaSecuencia.class);
            startActivity(intent);
        });*/
        // Solicitar permiso para notificaciones (Android 13 o superior)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }
        /*negocioNotificacion = new NegocioNotificacion(this);
        negocioNotificacion.activarNotificacionesRecurrentes(this);*/
    }
}
