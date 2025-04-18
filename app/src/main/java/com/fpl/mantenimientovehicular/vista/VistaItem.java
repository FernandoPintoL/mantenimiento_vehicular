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
import com.fpl.mantenimientovehicular.controller.ItemController;
import com.fpl.mantenimientovehicular.model.ModeloItem;
import com.fpl.mantenimientovehicular.model.ModeloMecanico;
import com.fpl.mantenimientovehicular.negocio.NegocioItem;

import java.util.ArrayList;
import java.util.List;

public class VistaItem extends AppCompatActivity {
    private NegocioItem negocio;
    private ItemController controlador;
    private List<String> listado;
    private ListView listView;
    private Button btnGuardar, btnEditar, btnEliminar, btnListar;
    private EditText etNombre, etPrecio, etDetalle;
    private LinearLayout btnsActions;
    private TextView idItem;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_main);
        negocio = new NegocioItem(this);
        controlador = new ItemController(this, negocio);

        etNombre = findViewById(R.id.etNombre);
        etPrecio = findViewById(R.id.etPrecio);
        etDetalle = findViewById(R.id.etDetalle);
        btnGuardar = findViewById(R.id.btnGuardarItem);
        btnEditar = findViewById(R.id.btnEditarItem);
        btnEliminar = findViewById(R.id.btnEliminarItem);
        btnListar = findViewById(R.id.btnListarItem);
        listView = findViewById(R.id.listViewRespuestos);
        btnsActions = findViewById(R.id.btnsActionsItems);
        idItem = findViewById(R.id.idItem);

        controlador.initEvents();
        cargarDatosToList();
    }
    @Override
    protected void onResume() {
        super.onResume();
        cargarDatosToList();
    }
    public void limpiarFormulario() {
        etNombre.setText("");
        etPrecio.setText("");
        etDetalle.setText("");
        idItem.setText("");
        btnGuardar.setVisibility(View.VISIBLE);
        btnsActions.setVisibility(View.GONE);
    }
    public void cargarDatosToList() {
        listado = negocio.cargarDatos();
        if (listado.isEmpty()) {
            mostrarMensaje("No hay datos para mostrar");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listado);
        listView.setAdapter(adapter);
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
        return btnsActions;
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
    public Button getBtnListar() {
        return btnListar;
    }
    public EditText getEtNombre() {
        return etNombre;
    }
    public EditText getEtPrecio() {
        return etPrecio;
    }
    public EditText getEtDetalle() {
        return etDetalle;
    }
    public TextView getIdItem() {
        return idItem;
    }
    public void setNombre(String nombre) {
        etNombre.setText(nombre);
    }
    public void setPrecio(String precio) {
        etPrecio.setText(precio);
    }
    public void setDetalle(String detalle) {
        etDetalle.setText(detalle);
    }
    public void setIdItem(String id) {
        idItem.setText(id);
    }


}
