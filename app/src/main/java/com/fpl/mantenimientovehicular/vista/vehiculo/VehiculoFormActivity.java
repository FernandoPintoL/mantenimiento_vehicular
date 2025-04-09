/*
package com.fpl.mantenimientovehicular.vista.vehiculo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fpl.mantenimientovehicular.R;
import com.fpl.mantenimientovehicular.controller.VehiculoController;
import com.fpl.mantenimientovehicular.model.vehiculo.Vehiculo;

public class VehiculoFormActivity extends AppCompatActivity {
    private EditText etMarca, etModelo, etAnio, etPlaca, etTipo, etKilometraje;
    private Button btnGuardar, btnEliminar;
    private VehiculoController vehiculoController;
    private Vehiculo vehiculo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculo_form);

        vehiculoController = new VehiculoController(this);

        etMarca = findViewById(R.id.etMarca);
        etAnio = findViewById(R.id.etAnio);
        etPlaca = findViewById(R.id.etPlaca);
        etTipo = findViewById(R.id.etTipo);
        etKilometraje = findViewById(R.id.etKilometraje);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnEliminar = findViewById(R.id.btnEliminar);

        // Verificar si estamos editando o creando un nuevo vehículo
        int vehiculoId = getIntent().getIntExtra("VEHICULO_ID", -1);
        if (vehiculoId != -1) {
            // Estamos editando
            vehiculo = vehiculoController.obtenerVehiculoPorId(vehiculoId);
            if (vehiculo != null) {
                llenarFormulario(vehiculo);
                btnEliminar.setVisibility(View.VISIBLE);
            }
        } else {
            // Estamos creando
            vehiculo = new Vehiculo();
        }

        btnGuardar.setOnClickListener(v -> guardarVehiculo());
        btnEliminar.setOnClickListener(v -> eliminarVehiculo());
    }

    private void llenarFormulario(Vehiculo vehiculo) {
        etMarca.setText(vehiculo.getMarca());
        etModelo.setText(vehiculo.getModelo());
        etAnio.setText(vehiculo.getAño());
        etPlaca.setText(vehiculo.getPlaca());
        etTipo.setText(vehiculo.getTipo());
        etKilometraje.setText(String.valueOf(vehiculo.getKilometraje()));
    }

    private void guardarVehiculo() {
        vehiculo.setMarca(etMarca.getText().toString());
        vehiculo.setModelo(etModelo.getText().toString());
        vehiculo.setAño(etAnio.getText().toString());
        vehiculo.setPlaca(etPlaca.getText().toString());
        vehiculo.setTipo(etTipo.getText().toString());

        try {
            vehiculo.setKilometraje(Integer.parseInt(etKilometraje.getText().toString()));
        } catch (NumberFormatException e) {
            vehiculo.setKilometraje(0);
        }

        if (vehiculo.getId() > 0) {
            // Actualizar
            if (vehiculoController.actualizarVehiculo(vehiculo) > 0) {
                Toast.makeText(this, "Vehículo actualizado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error al actualizar vehículo", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Crear nuevo
            long id = vehiculoController.agregarVehiculo(vehiculo);
            if (id > 0) {
                vehiculo.setId((int) id);
                Toast.makeText(this, "Vehículo guardado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error al guardar vehículo", Toast.LENGTH_SHORT).show();
            }
        }
        finish();
    }

    private void eliminarVehiculo() {
        if (vehiculoController.eliminarVehiculo(vehiculo.getId())) {
            Toast.makeText(this, "Vehículo eliminado", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error al eliminar vehículo", Toast.LENGTH_SHORT).show();
        }
    }
}
*/
