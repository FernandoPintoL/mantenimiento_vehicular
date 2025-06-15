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
import java.util.Map;
public class VistaVehiculo extends AppCompatActivity {
    private VehiculoController controlador;
    private NegocioVehiculo negocio;
    private ListView listView;
    private Button btnGuardar, btnEliminar, btnEditar, btnListar;
    private EditText etMarca, etAnho, etPlaca, etTipo, etKilometrajeActual, etKilometrajeEsperado;
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
        etKilometrajeActual = findViewById(R.id.etKilometrajeActual);
        etKilometrajeEsperado = findViewById(R.id.etKilometrajeEsperado);
        idVehiculo = findViewById(R.id.idVehiculo);
        btnGuardar = findViewById(R.id.btnGuardarVehiculo);
        btnEditar = findViewById(R.id.btnEditarVehiculo);
        btnEliminar = findViewById(R.id.btnEliminarVehiculo);
        btnListar = findViewById(R.id.btnListarVehiculo);
        listView = findViewById(R.id.listViewVehiculos);
        btnsAction = findViewById(R.id.btnsActionsVehiculos);

        controlador.initEvents();
        cargarDatosToListView();
    }
    @Override
    protected void onResume() {
        super.onResume();
        cargarDatosToListView();
        limpiarFormulario();
    }
    public void cargarDatosToListView(){
        List<Map<String,String>> datos = negocio.cargarDatosMap();
        ArrayAdapter<Map<String,String>> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datos){
            @Override
            public View getView(int position, View convertView, android.view.ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                Map<String, String> item = getItem(position);
                if (item != null) {
                    String text = "ID: " + item.get("id") + "\n" +
                            "Marca: " + item.get("marca") + "\n" +
                            "Año: " + item.get("anho") + "\n" +
                            "Placa: " + item.get("placa") + "\n" +
                            "Tipo: " + item.get("tipo") + "\n" +
                            "Kilometraje Actual: " + item.get("kilometrajeActual") + "\n" +
                            "Kilometraje Esperado: " + item.get("kilometrajeEsperado");
                    ((TextView) view).setText(text);
                    // como cambiarle el padding
                    ((TextView) view).setPadding(16, 16, 16, 16);
                }
                return view;
            }
        };
        listView.setAdapter(adapter);
    }
    public void limpiarFormulario() {
        etMarca.setText("");
        etAnho.setText("");
        etPlaca.setText("");
        etTipo.setText("");
        etKilometrajeActual.setText("");
        etKilometrajeEsperado.setText("");
        idVehiculo.setText("");
        btnsAction.setVisibility(View.GONE);
    }
    public void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
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
    public EditText getEtKilometrajeActual() {
        return etKilometrajeActual;
    }
    public EditText getEtKilometrajeEsperado() {
        return etKilometrajeEsperado;
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
    public void setEtKilometrajeActual(String kilometraje) {
        etKilometrajeActual.setText(kilometraje);
    }
    public void setIdVehiculo(String id) {idVehiculo.setText(id);}
    public void setEtKilometrajeEsperado(String kilometraje) {
        etKilometrajeEsperado.setText(kilometraje);
    }
    public Button getBtnListar() {
        return btnListar;
    }
}
