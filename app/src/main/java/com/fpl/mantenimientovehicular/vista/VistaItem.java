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
import com.fpl.mantenimientovehicular.controller.ItemController;
import com.fpl.mantenimientovehicular.model.ModeloItem;
import com.fpl.mantenimientovehicular.model.ModeloMecanico;

import java.util.ArrayList;
import java.util.List;

public class VistaItem extends AppCompatActivity {
    private ItemController controlador;
    private ModeloItem modelo;
    private List<ModeloItem> listado;
    private ListView listView;
    private Button btnAction, btnEliminar, btnListar;
    private EditText etNombre, etPrecio, etDetalle;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_main);

        controlador = new ItemController(this);
        modelo = new ModeloItem();

        etNombre = findViewById(R.id.etNombre);
        etPrecio = findViewById(R.id.etPrecio);
        etDetalle = findViewById(R.id.etDetalle);
        btnAction = findViewById(R.id.btnActionItem);
        btnEliminar = findViewById(R.id.btnEliminarItem);
        listView = findViewById(R.id.listViewRespuestos);

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
        for (ModeloItem model : listado) {
            modelInfos.add("ID: "+model.getId()+" Nombre: "+model.getNombre()+" Precio : "+model.getPrecio() + " Detalle: " + model.getDetalle());
        }
        // Usar un ArrayAdapter simple para mostrar la información
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, modelInfos);
        listView.setAdapter(adapter);
    }
    private void llenarFormulario(ModeloItem modelo) {
        etNombre.setText(modelo.getNombre());
        etPrecio.setText(String.valueOf(modelo.getPrecio()));
        etDetalle.setText(modelo.getDetalle());
    }
    private void ejecutarAccion() {
        modelo.setNombre(etNombre.getText().toString());
        modelo.setPrecio(Double.parseDouble(etPrecio.getText().toString()));
        modelo.setDetalle(etDetalle.getText().toString());

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
        etPrecio.setText("");
        etDetalle.setText("");
        btnAction.setText("Guardar");
        btnEliminar.setVisibility(View.GONE);
        modelo = new ModeloItem();
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
