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
import com.fpl.mantenimientovehicular.controller.VehiculoController;
import com.fpl.mantenimientovehicular.negocio.NegocioVehiculo;

import java.util.List;

public class VistaVehiculo extends AppCompatActivity {
    private VehiculoController controlador;
    private NegocioVehiculo negocio;
    private List<String> listado;
    private ListView listView;
    private Button btnGuardar, btnEliminar, btnEditar;
    private EditText etMarca, etAnho, etPlaca, etTipo, etKilometraje;
    private TextView idVehiculo;
    private LinearLayout btnsAction;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculo_main);

        negocio = new NegocioVehiculo(this);
        controlador = new VehiculoController(this, negocio);

        etMarca = findViewById(R.id.etMarca);
        etAnho = findViewById(R.id.etAnho);
        etPlaca = findViewById(R.id.etPlaca);
        etTipo = findViewById(R.id.etTipo);
        etKilometraje = findViewById(R.id.etKilometraje);
        idVehiculo = findViewById(R.id.idVehiculo);
        btnGuardar = findViewById(R.id.btnGuardarVehiculo);
        btnEditar = findViewById(R.id.btnEditarVehiculo);
        btnEliminar = findViewById(R.id.btnEliminarVehiculo);
        listView = findViewById(R.id.listViewVehiculos);
        btnsAction = findViewById(R.id.btnsActionsVehiculos);

        controlador.initEvents();
        cargarDatosToList();
    }
    @Override
    protected void onResume() {
        super.onResume();
        cargarDatosToList();
        limpiarFormulario();
    }
    public void cargarDatosToList(){
        listado = negocio.cargarDatos();
        if (listado.isEmpty()){
            mostrarMensaje("No hay datos disponibles");
        }
        // Usar un ArrayAdapter simple para mostrar la información
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                listado);
        listView.setAdapter(adapter);
    }
    public void limpiarFormulario() {
        etMarca.setText("");
        etAnho.setText("");
        etPlaca.setText("");
        etTipo.setText("");
        etKilometraje.setText("");
        idVehiculo.setText("");
        btnsAction.setVisibility(View.GONE);
    }
    public void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
    public List<String> listados(){
        return listado;
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
    public EditText getEtMarca() {
        return etMarca;
    }
    public EditText getEtAnho() {
        return etAnho;
    }
    public EditText getEtPlaca() {
        return etPlaca;
    }
    public EditText getEtTipo() {
        return etTipo;
    }
    public EditText getEtKilometraje() {
        return etKilometraje;
    }
    public void setEtMarca(String marca) {
        etMarca.setText(marca);
    }
    public void setEtAnho(String anho) {
        etAnho.setText(anho);
    }
    public void setEtPlaca(String placa) {etPlaca.setText(placa);}
    public void setEtTipo(String tipo) {
        etTipo.setText(tipo);
    }
    public void setEtKilometraje(String kilometraje) {
        etKilometraje.setText(kilometraje);
    }
    public void setIdVehiculo(String id) {idVehiculo.setText(id);}
    public String getIdVehiculo() {
        return idVehiculo.getText().toString();
    }
}
