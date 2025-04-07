package com.fpl.mantenimientovehicular;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fpl.mantenimientovehicular.vista.vehiculo.VehiculosListaActivity;

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

        // Botón para acceder al módulo de vehículos
        Button btnVehiculos = findViewById(R.id.btnVehiculos);
        btnVehiculos.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, VehiculosListaActivity.class);
            startActivity(intent);
        });

        // Puedes agregar más botones para otros módulos aquí
    }
}