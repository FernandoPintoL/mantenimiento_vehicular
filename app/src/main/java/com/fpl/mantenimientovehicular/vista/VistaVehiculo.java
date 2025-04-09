package com.fpl.mantenimientovehicular.vista;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fpl.mantenimientovehicular.R;
import com.fpl.mantenimientovehicular.controller.VehiculoController;
import com.fpl.mantenimientovehicular.model.vehiculo.Vehiculo;

import java.util.ArrayList;
import java.util.List;

public class VistaVehiculo extends AppCompatActivity {
    private EditText etMarca, etAnio, etPlaca, etTipo, etKilometraje;
    private ListView listView;
    private Button btnAction, btnEliminar;
    private VehiculoController vehiculoController;
    private Vehiculo vehiculo;
    private List<Vehiculo> listado;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculo_main);

        vehiculoController = new VehiculoController(this);
        vehiculo = new Vehiculo();

        etMarca = findViewById(R.id.etMarca);
        etAnio = findViewById(R.id.etAnio);
        etPlaca = findViewById(R.id.etPlaca);
        etTipo = findViewById(R.id.etTipo);
        etKilometraje = findViewById(R.id.etKilometraje);
        btnAction = findViewById(R.id.btnActionVehiculo);
        btnEliminar = findViewById(R.id.btnEliminarVehiculo);
        listView = findViewById(R.id.listViewVehiculos);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            btnAction.setText("Editar");
            vehiculo = listado.get(position);
            llenarFormulario(vehiculo);
            btnEliminar.setVisibility(View.VISIBLE);
        });
        btnAction.setOnClickListener(v -> guardarVehiculo());
        listar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        listar();
    }

    private void listar() {
        listado = vehiculoController.obtenerTodosVehiculos();
        if (listado.isEmpty()) return;
        // Crear una lista de cadenas con la información que deseas mostrar
        List<String> vehiculosInfo = new ArrayList<>();
        for (Vehiculo vehiculo : listado) {
            vehiculosInfo.add("ID: "+vehiculo.getId()+" Placa: "+vehiculo.getPlaca()+" Marca: "+vehiculo.getMarca() + " Tipo: " + vehiculo.getTipo()+" Año: "+vehiculo.getAño()+" Kilometraje: "+vehiculo.getKilometraje());
        }

        // Usar un ArrayAdapter simple para mostrar la información
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, vehiculosInfo);
        listView.setAdapter(adapter);
    }

    private void llenarFormulario(Vehiculo vehiculo) {
        etMarca.setText(vehiculo.getMarca());
        etAnio.setText(vehiculo.getAño());
        etPlaca.setText(vehiculo.getPlaca());
        etTipo.setText(vehiculo.getTipo());
        etKilometraje.setText(String.valueOf(vehiculo.getKilometraje()));
    }

    private void guardarVehiculo() {
        vehiculo.setMarca(etMarca.getText().toString());
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
                Toast.makeText(this, "Registro Actualizado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Crear nuevo
            long id = vehiculoController.agregarVehiculo(vehiculo);
            if (id > 0) {
                vehiculo.setId((int) id);
                Toast.makeText(this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show();
            }
        }
        listar();
        limpiarFormulario();
    }

    private void limpiarFormulario() {
        etMarca.setText("");
        etAnio.setText("");
        etPlaca.setText("");
        etTipo.setText("");
        etKilometraje.setText("");
        btnAction.setText("Guardar");
        btnEliminar.setVisibility(View.GONE);
        vehiculo = new Vehiculo();
    }

}
