package com.fpl.mantenimientovehicular.vista;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fpl.mantenimientovehicular.R;
import com.fpl.mantenimientovehicular.controller.MecanicoController;
import com.fpl.mantenimientovehicular.model.ModeloMecanico;
import com.fpl.mantenimientovehicular.model.ModeloVehiculo;
import com.fpl.mantenimientovehicular.negocio.NegocioMecanico;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VistaMecanico extends AppCompatActivity {
    private NegocioMecanico negocio;
    private MecanicoController controlador;
    private ListView listView;
    private Button btnGuardar, btnEliminar, btnEditar, btnListar;
    private EditText etNombre, etTaller, etDireccion, etTelefono;
    private TextView idMecanico;
    private LinearLayout btnsAction;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mecanico_main);

        negocio = new NegocioMecanico(this);
        controlador = new MecanicoController(this, negocio);

        etNombre = findViewById(R.id.etNombre);
        etTaller = findViewById(R.id.etTaller);
        etDireccion = findViewById(R.id.etDireccion);
        etTelefono = findViewById(R.id.etTelefono);
        idMecanico = findViewById(R.id.idMecanico);
        btnGuardar = findViewById(R.id.btnGuardarMecanico);
        btnEliminar = findViewById(R.id.btnEliminarMecanico);
        btnEditar = findViewById(R.id.btnEditarMecanico);
        btnListar = findViewById(R.id.btnListarMecanico);
        listView = findViewById(R.id.listViewMecanicos);
        btnsAction = findViewById(R.id.btnsActionsMecanicos);

        controlador.initEvents();
        limpiarFormulario();
        cargarDatosToListView();
    }
    @Override
    protected void onResume() {
        super.onResume();
        limpiarFormulario();
        cargarDatosToListView();
    }
    public void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
    public void limpiarFormulario() {
        etNombre.setText("");
        etTaller.setText("");
        etDireccion.setText("");
        etTelefono.setText("");
        idMecanico.setText("");
    }
    public void cargarDatosToListView() {
        List<Map<String,String>> datos = negocio.cargarDatos();
        ArrayAdapter<Map<String,String>> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datos){
            @Override
            public View getView(int position, View convertView, android.view.ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = view.findViewById(android.R.id.text1);
                Map<String,String> item = datos.get(position);
                String displayText = "ID: " + item.get("id") +
                        "\nNombre: " + item.get("nombre") +
                        "\nTaller: " + item.get("taller") +
                        "\nDirección: " + item.get("direccion") +
                        "\nTeléfono: " + item.get("telefono");
                textView.setText(displayText);
                // padding
                textView.setPadding(10, 10, 10, 10);
                return view;
            }
        };
        listView.setAdapter(adapter);
    }
    public ListView getListView() {
        return listView;
    }
    public LinearLayout getBtnsAction() {
        return btnsAction;
    }
    public Button getBtnGuardar() {
        return btnGuardar;
    }
    public Button getBtnEditar() {
        return btnEditar;
    }
    public Button getBtnEliminar() {
        return btnEliminar;
    }
    public String getNombre() {
        return etNombre.getText().toString();
    }
    public String getTaller() {
        return etTaller.getText().toString();
    }
    public String getDireccion() {
        return etDireccion.getText().toString();
    }
    public String getTelefono() {
        return etTelefono.getText().toString();
    }
    public void setNombre(String nombre) {
        etNombre.setText(nombre);
    }
    public void setTaller(String taller) {
        etTaller.setText(taller);
    }
    public void setDireccion(String direccion) {
        etDireccion.setText(direccion);
    }
    public void setTelefono(String telefono) {
        etTelefono.setText(telefono);
    }
    public Button getBtnListar() {
        return btnListar;
    }
}
