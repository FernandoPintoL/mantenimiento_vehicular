package com.fpl.mantenimientovehicular.negocio;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fpl.mantenimientovehicular.proxy.IMecanicoModel;
import com.fpl.mantenimientovehicular.proxy.MecanicoModelProxy;
import com.fpl.mantenimientovehicular.model.ModeloMecanico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NegocioMecanico {
    private IMecanicoModel modelo;
    private int posicion;
    public NegocioMecanico(Context context){
        modelo = new MecanicoModelProxy(context);
    }
    public List<Map<String,String>> cargarDatos(){
        List<ModeloMecanico> datos = modelo.obtenerTodos();
        List<Map<String,String>> listado = new ArrayList<>();
        for (ModeloMecanico mecanico : datos) {
            Map<String, String> item = new HashMap<>();
            item.put("id", String.valueOf(mecanico.getId()));
            item.put("nombre", mecanico.getNombre());
            item.put("taller", mecanico.getTaller());
            item.put("direccion", mecanico.getDireccion());
            item.put("telefono", mecanico.getTelefono());
            listado.add(item);
        }
        return listado;
    }
    public Map<String, String> getModeloMap(){
        List<ModeloMecanico> datos = modelo.obtenerTodos();
        if (datos.isEmpty()) {
            return null;
        }
        ModeloMecanico model = datos.get(posicion);
        Log.d("ModeloMecanico", "getModeloMap: "+model.toString());
        Map<String, String> item = new HashMap<>();
        item.put("id", String.valueOf(model.getId()));
        item.put("nombre", model.getNombre());
        item.put("taller", model.getTaller());
        item.put("direccion", model.getDireccion());
        item.put("telefono", model.getTelefono());
        return item;

    }
    public Map<String,String> getModeloForId(){
        ModeloMecanico model = modelo.obtenerPorId(modelo.getId());
        Map<String,String> item = new HashMap<>();
        item.put("id", String.valueOf(model.getId()));
        item.put("nombre", model.getNombre());
        item.put("taller", model.getTaller());
        item.put("direccion", model.getDireccion());
        item.put("telefono", model.getTelefono());
        return item;
    }
    public ArrayAdapter<Map<String,String>> getArrayAdapterSpinner(Context context){
        List<Map<String,String>> lista = cargarDatos();
        ArrayAdapter<Map<String,String>> adapter = new ArrayAdapter<Map<String,String>>(context, android.R.layout.simple_spinner_item, lista) {
            int padding = 16;
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view;
                textView.setText("Nombre: "+lista.get(position).get("nombre"));
                textView.setPadding(padding, padding, padding, padding);
                return view;
            }
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view;
                textView.setText("Nombre: "+lista.get(position).get("nombre"));
                textView.setPadding(padding, padding, padding, padding);
                return view;
            }
        };
        return adapter;
    }
    public void cargarFormulario(int id, String nombre, String taller, String direccion, String telefono){
        modelo = new MecanicoModelProxy(id, nombre, taller, direccion, telefono);
    }
    public long agregar(){
        // Create a new ModeloMecanico with the data from the proxy
        ModeloMecanico mecanicoData = new ModeloMecanico(
            modelo.getId(),
            modelo.getNombre(),
            modelo.getTaller(),
            modelo.getDireccion(),
            modelo.getTelefono()
        );
        return modelo.agregar(mecanicoData);
    }
    public int eliminar(){
        int id = obtenerIdPorPosicion();
        modelo.setId(id);
        return modelo.eliminar(modelo.getId());
    }
    public int editar(){
        int id = obtenerIdPorPosicion();
        modelo.setId(id);

        // Create a new ModeloMecanico with the data from the proxy
        ModeloMecanico mecanicoData = new ModeloMecanico(
            modelo.getId(),
            modelo.getNombre(),
            modelo.getTaller(),
            modelo.getDireccion(),
            modelo.getTelefono()
        );
        return modelo.actualizar(mecanicoData);
    }
    public int obtenerIdPorPosicion(){
        List<ModeloMecanico> datos = modelo.obtenerTodos();
        return datos.get(posicion).getId();
    }
    public ModeloMecanico obtenerModeloPorPosicion(){
        List<ModeloMecanico> datos = modelo.obtenerTodos();
        return datos.get(posicion);
    }
    public int getPosicion(){
        return posicion;
    }
    public void setPosicion(int posicion){
        this.posicion = posicion;
    }
    public IMecanicoModel getModelo() {
        return modelo;
    }
}
