package com.fpl.mantenimientovehicular;

import androidx.activity.EdgeToEdge;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fpl.mantenimientovehicular.vista.VistaItem;
import com.fpl.mantenimientovehicular.vista.VistaMantenimiento;
import com.fpl.mantenimientovehicular.vista.VistaMecanico;
import com.fpl.mantenimientovehicular.vista.VistaVehiculo;

public class MainActivity extends AppCompatActivity {

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
    }
}