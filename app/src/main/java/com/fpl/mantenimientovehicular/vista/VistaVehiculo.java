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
import com.fpl.mantenimientovehicular.model.ModeloVehiculo;

import java.util.ArrayList;
import java.util.List;

public class VistaVehiculo extends AppCompatActivity {
    private VehiculoController controlador;
    private ModeloVehiculo modelo;
    private List<ModeloVehiculo> listado;
    private ListView listView;
    private Button btnAction, btnEliminar;
    private EditText etMarca, etAnho, etPlaca, etTipo, etKilometraje;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculo_main);

        controlador = new VehiculoController(this);
        modelo = new ModeloVehiculo();

        etMarca = findViewById(R.id.etMarca);
        etAnho = findViewById(R.id.etAnho);
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
        btnAction.setOnClickListener(v -> ejecutarAccion());
        btnEliminar.setOnClickListener( v -> eliminar());
        obtenerTodos();
    }
    @Override
    protected void onResume() {
        super.onResume();
        obtenerTodos();
    }
    private void obtenerTodos() {
        listado = controlador.obtenerTodos();
        if (listado.isEmpty()){
            Toast.makeText(this, "No hay registros", Toast.LENGTH_SHORT).show();
        }
        // Crear una lista de cadenas con la información que deseas mostrar
        List<String> vehiculosInfo = new ArrayList<>();
        for (ModeloVehiculo vehiculo : listado) {
            vehiculosInfo.add("ID: "+vehiculo.getId()+" Placa: "+vehiculo.getPlaca()+" Marca: "+vehiculo.getMarca() + " Tipo: " + vehiculo.getTipo()+" Año: "+vehiculo.getAño()+" Kilometraje: "+vehiculo.getKilometraje());
        }
        // Usar un ArrayAdapter simple para mostrar la información
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, vehiculosInfo);
        listView.setAdapter(adapter);
    }
    private void llenarFormulario(ModeloVehiculo vehiculo) {
        etMarca.setText(vehiculo.getMarca());
        etAnho.setText(vehiculo.getAño());
        etPlaca.setText(vehiculo.getPlaca());
        etTipo.setText(vehiculo.getTipo());
        etKilometraje.setText(String.valueOf(vehiculo.getKilometraje()));
    }
    private void ejecutarAccion() {
        modelo.setMarca(etMarca.getText().toString());
        modelo.setAño(etAnho.getText().toString());
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
        obtenerTodos();
        limpiarFormulario();
    }
    private void limpiarFormulario() {
        etMarca.setText("");
        etAnho.setText("");
        etPlaca.setText("");
        etTipo.setText("");
        etKilometraje.setText("");
        btnAction.setText("Guardar");
        btnEliminar.setVisibility(View.GONE);
        modelo = new ModeloVehiculo();
    }
    private void eliminar(){
        if (modelo.getId() > 0) {
            if (controlador.eliminar(modelo.getId())) {
                Toast.makeText(this, "Registro Eliminado", Toast.LENGTH_SHORT).show();
                limpiarFormulario();
            } else {
                Toast.makeText(this, "Error al eliminar", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Seleccione un registro para eliminar", Toast.LENGTH_SHORT).show();
        }
        obtenerTodos();
    }
}
