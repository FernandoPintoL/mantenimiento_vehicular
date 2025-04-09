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
import com.fpl.mantenimientovehicular.model.vehiculo.VehiculoDAO;

import java.util.ArrayList;
import java.util.List;

public class VistaVehiculo extends AppCompatActivity {
    private VehiculoController controlador;
    private VehiculoDAO modelo;
    private List<VehiculoDAO> listado;
    private ListView listView;
    private Button btnAction, btnEliminar;
    private EditText etMarca, etAnio, etPlaca, etTipo, etKilometraje;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculo_main);

        controlador = new VehiculoController(this);
        modelo = new VehiculoDAO(this);

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
            modelo = listado.get(position);
            llenarFormulario(modelo);
            btnEliminar.setVisibility(View.VISIBLE);
        });
        btnAction.setOnClickListener(v -> guardar());
        listar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        listar();
    }

    private void listar() {
        listado = controlador.obtenerTodos();
        if (listado.isEmpty()) return;
        // Crear una lista de cadenas con la información que deseas mostrar
        List<String> vehiculosInfo = new ArrayList<>();
        for (VehiculoDAO vehiculo : listado) {
            vehiculosInfo.add("ID: "+vehiculo.getId()+" Placa: "+vehiculo.getPlaca()+" Marca: "+vehiculo.getMarca() + " Tipo: " + vehiculo.getTipo()+" Año: "+vehiculo.getAño()+" Kilometraje: "+vehiculo.getKilometraje());
        }
        // Usar un ArrayAdapter simple para mostrar la información
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, vehiculosInfo);
        listView.setAdapter(adapter);
    }

    private void llenarFormulario(VehiculoDAO vehiculo) {
        etMarca.setText(vehiculo.getMarca());
        etAnio.setText(vehiculo.getAño());
        etPlaca.setText(vehiculo.getPlaca());
        etTipo.setText(vehiculo.getTipo());
        etKilometraje.setText(String.valueOf(vehiculo.getKilometraje()));
    }

    private void guardar() {
        modelo.setMarca(etMarca.getText().toString());
        modelo.setAño(etAnio.getText().toString());
        modelo.setPlaca(etPlaca.getText().toString());
        modelo.setTipo(etTipo.getText().toString());

        try {
            modelo.setKilometraje(Integer.parseInt(etKilometraje.getText().toString()));
        } catch (NumberFormatException e) {
            modelo.setKilometraje(0);
        }

        if (modelo.getId() > 0) {
            // Actualizar
            if (controlador.actualizar(modelo) > 0) {
                Toast.makeText(this, "Registro Actualizado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Crear nuevo
            long id = controlador.agregar(modelo);
            if (id > 0) {
                modelo.setId((int) id);
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
        modelo = new VehiculoDAO();
    }

}
