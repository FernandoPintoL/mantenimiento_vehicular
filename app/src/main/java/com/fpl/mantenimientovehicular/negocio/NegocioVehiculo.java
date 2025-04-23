package com.fpl.mantenimientovehicular.negocio;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fpl.mantenimientovehicular.model.ModeloVehiculo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NegocioVehiculo {
    private ModeloVehiculo modelo;
    private int posicion;
    public NegocioVehiculo(Context context){
        modelo = new ModeloVehiculo(context);
    }
    public List<Map<String,String>> cargarDatosMap(){
        List<ModeloVehiculo> datos = modelo.obtenerTodos();
        List<Map<String,String>> listado = new ArrayList<>();
        for (ModeloVehiculo vehiculo : datos) {
            Map<String,String> map = new java.util.HashMap<>();
            map.put("id", String.valueOf(vehiculo.getId()));
            map.put("placa", vehiculo.getPlaca());
            map.put("marca", vehiculo.getMarca());
            map.put("tipo", vehiculo.getTipo());
            map.put("anho", vehiculo.getAño());
            map.put("kilometrajeActual", String.valueOf(vehiculo.getKilometrajeActual()));
            map.put("kilometrajeEsperado", String.valueOf(vehiculo.getKilometrajeEsperado()));
            listado.add(map);
        }
        return listado;
    }
    public ArrayAdapter<Map<String,String>> getSpinnerAdapter(Context context){
        List<Map<String,String>> lista = cargarDatosMap();
        ArrayAdapter<Map<String,String>> adapter = new ArrayAdapter<Map<String,String>>(context, android.R.layout.simple_spinner_item, lista) {
            int padding = 16;
            @SuppressLint("SetTextI18n")
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view;
                textView.setText("Placa: "+lista.get(position).get("placa"));
                textView.setPadding(padding, padding, padding, padding);
                return view;
            }
            @SuppressLint("SetTextI18n")
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view;
                textView.setText("Placa: "+lista.get(position).get("placa"));
                textView.setPadding(padding, padding, padding, padding);
                return view;
            }
            @Override
            public int getCount() {
                return lista.size();
            }
            @Override
            public boolean isEnabled(int position) {
                return true;
            }
            @Override
            public int getItemViewType(int position) {
                return 0;
            }
            @Override
            public int getViewTypeCount() {
                return 1;
            }

        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }
    public void cargarFormulario(int id, String placa, String marca, String anho, String tipo, int kilometrajeActual, int kilometrajeEsperado){
        modelo = new ModeloVehiculo(id, placa, marca, anho, tipo, kilometrajeActual, kilometrajeEsperado);
    }
    public long agregar(){
        return modelo.agregar(modelo);
    }
    public int eliminar(){
        return modelo.eliminar(modelo.getId());
    }
    public int editar(){
        int id = obtenerIdPorPosicion();
        modelo.setId(id);
        return modelo.actualizar(modelo);
    }
    public int obtenerIdPorPosicion(){
        List<ModeloVehiculo> datos = modelo.obtenerTodos();
        return datos.get(posicion).getId();
    }
    public Map<String, String> getModelForPos(){
        List<ModeloVehiculo> datos = modelo.obtenerTodos();
        ModeloVehiculo vehiculo = datos.get(posicion);
        Map<String, String> map = new java.util.HashMap<>();
        map.put("id", String.valueOf(vehiculo.getId()));
        map.put("placa", vehiculo.getPlaca());
        map.put("marca", vehiculo.getMarca());
        map.put("tipo", vehiculo.getTipo());
        map.put("anho", vehiculo.getAño());
        map.put("kilometrajeActual", String.valueOf(vehiculo.getKilometrajeActual()));
        map.put("kilometrajeEsperado", String.valueOf(vehiculo.getKilometrajeEsperado()));
        return map;
    }
    public ModeloVehiculo obtenerModeloPorPosicion(){
        List<ModeloVehiculo> datos = modelo.obtenerTodos();
        return datos.get(posicion);
    }
    public Map<String,String> getModelForId(){
        ModeloVehiculo vehiculo = modelo.obtenerPorId(modelo.getId());
        Map<String,String> map = new java.util.HashMap<>();
        map.put("id", String.valueOf(vehiculo.getId()));
        map.put("placa", vehiculo.getPlaca());
        map.put("marca", vehiculo.getMarca());
        map.put("tipo", vehiculo.getTipo());
        map.put("anho", vehiculo.getAño());
        map.put("kilometraje_actual", String.valueOf(vehiculo.getKilometrajeActual()));
        map.put("kilometraje_esperado", String.valueOf(vehiculo.getKilometrajeEsperado()));
        return map;
    }
    public int getPosicion(){
        return posicion;
    }
    public void setPosicion(int posicion){
        this.posicion = posicion;
    }
    public ModeloVehiculo getModelo() {
        return modelo;
    }
}
