package com.fpl.mantenimientovehicular.negocio;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fpl.mantenimientovehicular.model.ModeloItem;
import com.fpl.mantenimientovehicular.model.ModeloMecanico;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NegocioItem {
    private ModeloItem modelo;
    private int posicion;
    public NegocioItem(Context context){
        modelo = new ModeloItem(context);
    }
    public List<Map<String,String>> cargarDatos(){
        List<ModeloItem> datos = modelo.obtenerTodos();
        List<Map<String,String>> listado = new ArrayList<>();
        for (ModeloItem item : datos) {
            Map<String, String> map = Map.of(
                    "id", String.valueOf(item.getId()),
                    "nombre", item.getNombre(),
                    "precio", String.valueOf(item.getPrecio()),
                    "detalle", item.getDetalle()
            );
            listado.add(map);
        }
        return listado;
    }
    public Map<String,String> obtenerModeloPorId(){
        ModeloItem item = modelo.obtenerPorId(modelo.getId());
        return Map.of(
                "id", String.valueOf(item.getId()),
                "nombre", item.getNombre(),
                "precio", String.valueOf(item.getPrecio()),
                "detalle", item.getDetalle()
        );
    }
    public ArrayAdapter<Map<String,String>> getArrayAdapterSpinner(Context context){
        List<Map<String,String>> lista = cargarDatos();
        ArrayAdapter<Map<String,String>> adapter = new ArrayAdapter<Map<String,String>>(context, android.R.layout.simple_spinner_item, lista) {
            int padding = 16;
            @SuppressLint("SetTextI18n")
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view;
                textView.setText("Nombre: "+lista.get(position).get("nombre")+" | Precio: "+lista.get(position).get("precio"));
                textView.setPadding(padding, padding, padding, padding);
                return view;
            }
            @SuppressLint("SetTextI18n")
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view;
                textView.setText("Nombre: "+lista.get(position).get("nombre")+" | Precio: "+lista.get(position).get("precio"));
                textView.setPadding(padding, padding, padding, padding);
                return view;
            }
        };
        return adapter;
    }
    public void cargarFormulario(int id, String nombre, double precio, String detalle){
        modelo = new ModeloItem(id, nombre, precio, detalle);
    }
    public List<Map<String, String>> getItemOfList(List<Integer> ids){
        List<Map<String, String>> lista = new ArrayList<>();
        for (int id : ids) {
            ModeloItem item = modelo.obtenerPorId(id);
            Map<String, String> map = Map.of(
                    "id", String.valueOf(item.getId()),
                    "nombre", item.getNombre(),
                    "precio", String.valueOf(item.getPrecio()),
                    "detalle", item.getDetalle()
            );
            lista.add(map);
        }
        return lista;

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
        List<ModeloItem> datos = modelo.obtenerTodos();
        return datos.get(posicion).getId();
    }
    public ModeloItem obtenerModeloPorPosicion(){
        List<ModeloItem> datos = modelo.obtenerTodos();
        return datos.get(posicion);
    }
    public int getPosicion(){
        return posicion;
    }
    public void setPosicion(int posicion){
        this.posicion = posicion;
    }
    public ModeloItem getModelo() {
        return modelo;
    }
}
