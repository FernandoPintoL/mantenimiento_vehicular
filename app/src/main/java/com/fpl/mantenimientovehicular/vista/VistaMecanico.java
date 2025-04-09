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
import com.fpl.mantenimientovehicular.controller.MecanicoController;
import com.fpl.mantenimientovehicular.model.ModeloMecanico;
import com.fpl.mantenimientovehicular.model.ModeloVehiculo;

import java.util.ArrayList;
import java.util.List;

public class VistaMecanico extends AppCompatActivity {
    private MecanicoController controlador;
    private ModeloMecanico modelo;
    private List<ModeloMecanico> listado;
    private ListView listView;
    private Button btnAction, btnEliminar;
    private EditText etNombre, etTaller, etDireccion, etTelefono;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mecanico_main);

        controlador = new MecanicoController(this);
        modelo = new ModeloMecanico(this);
        modelo.initDatabase(this);

        etNombre = findViewById(R.id.etNombre);
        etTaller = findViewById(R.id.etTaller);
        etDireccion = findViewById(R.id.etDireccion);
        etTelefono = findViewById(R.id.etTelefono);
        btnAction = findViewById(R.id.btnActionMecanico);
        btnEliminar = findViewById(R.id.btnEliminarMecanico);
        listView = findViewById(R.id.listViewMecanicos);

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
        List<String> modelInfos = new ArrayList<>();
        for (ModeloMecanico model : listado) {
            modelInfos.add("ID: "+model.getId()+" Nombre: "+model.getNombre()+" Taller: "+model.getTaller() + " Direccion: " + model.getDireccion()+" Telefono: "+model.getTelefono());
        }
        // Usar un ArrayAdapter simple para mostrar la información
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, modelInfos);
        listView.setAdapter(adapter);
    }
    private void llenarFormulario(ModeloMecanico modelo) {
        etNombre.setText(modelo.getNombre());
        etTaller.setText(modelo.getTaller());
        etDireccion.setText(modelo.getDireccion());
        etTelefono.setText(modelo.getTelefono());
    }
    private void ejecutarAccion() {
        modelo.setNombre(etNombre.getText().toString());
        modelo.setTaller(etTaller.getText().toString());
        modelo.setDireccion(etDireccion.getText().toString());
        modelo.setTelefono(etTelefono.getText().toString());

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
        etNombre.setText("");
        etTaller.setText("");
        etDireccion.setText("");
        etTelefono.setText("");
        btnAction.setText("Guardar");
        btnEliminar.setVisibility(View.GONE);
        modelo = new ModeloMecanico();
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
