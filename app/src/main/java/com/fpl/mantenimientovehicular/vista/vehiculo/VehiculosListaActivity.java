package com.fpl.mantenimientovehicular.vista.vehiculo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;


import com.fpl.mantenimientovehicular.R;
import com.fpl.mantenimientovehicular.controller.VehiculoController;
import com.fpl.mantenimientovehicular.model.vehiculo.Vehiculo;

import java.util.List;

public class VehiculosListaActivity extends AppCompatActivity {
    private ListView listViewVehiculos;
    private Button btnAgregarVehiculo;
    private VehiculoController vehiculoController;
    private List<Vehiculo> listaVehiculos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculos_lista);

        vehiculoController = new VehiculoController(this);
        listViewVehiculos = findViewById(R.id.listViewVehiculos);
        btnAgregarVehiculo = findViewById(R.id.btnAgregarVehiculo);

        btnAgregarVehiculo.setOnClickListener(v -> {
            Intent intent = new Intent(VehiculosListaActivity.this, VehiculoFormActivity.class);
            startActivity(intent);
        });

        listViewVehiculos.setOnItemClickListener((parent, view, position, id) -> {
            Vehiculo vehiculoSeleccionado = listaVehiculos.get(position);
            Intent intent = new Intent(VehiculosListaActivity.this, VehiculoFormActivity.class);
            intent.putExtra("VEHICULO_ID", vehiculoSeleccionado.getId());
            startActivity(intent);
        });

        cargarVehiculos();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarVehiculos();
    }

    private void cargarVehiculos() {
        listaVehiculos = vehiculoController.obtenerTodosVehiculos();
        VehiculoAdapter adapter = new VehiculoAdapter(this, listaVehiculos);
        listViewVehiculos.setAdapter(adapter);
    }

}
