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
import java.util.Map;

public class VistaItem extends AppCompatActivity {
    private NegocioItem negocio;
    private ItemController controlador;
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

        cargarDatosToListView();
        controlador.initEvents();
    }
    @Override
    protected void onResume() {
        super.onResume();
        cargarDatosToListView();
    }
    public void limpiarFormulario() {
        etNombre.setText("");
        etPrecio.setText("");
        etDetalle.setText("");
        idItem.setText("");
        btnGuardar.setVisibility(View.VISIBLE);
        btnsActions.setVisibility(View.GONE);
    }
    public void cargarDatosToListView() {
        List<Map<String, String>> datos = negocio.cargarDatos();
        ArrayAdapter<Map<String, String>> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datos){
            @Override
            public View getView(int position, View convertView, android.view.ViewGroup parent) {
                Map<String, String> item = getItem(position);
                String displayText = "ID: " + item.get("id") +
                        "\nNombre: " + item.get("nombre") +
                        "\nPrecio: " + item.get("precio") +
                        "\nDetalle: " + item.get("detalle");
                //padding
                convertView = super.getView(position, convertView, parent);
                convertView.setPadding(10, 10, 10, 10);
                ((TextView) convertView).setText(displayText);
                return convertView;
            }
        };
        listView.setAdapter(adapter);
    }
    public void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
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
    public void setNombre(String nombre) {
        etNombre.setText(nombre);
    }
    public void setPrecio(String precio) {
        etPrecio.setText(precio);
    }
    public void setDetalle(String detalle) {
        etDetalle.setText(detalle);
    }
}
