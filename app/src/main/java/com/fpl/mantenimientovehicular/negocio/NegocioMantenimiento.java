package com.fpl.mantenimientovehicular.negocio;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fpl.mantenimientovehicular.model.ModeloDetalleMantenimiento;
import com.fpl.mantenimientovehicular.model.ModeloMantenimiento;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NegocioMantenimiento {
    private ModeloMantenimiento mantenimiento;
    private ModeloDetalleMantenimiento detalleMantenimiento;
    private List<ModeloDetalleMantenimiento> detalles;
    public NegocioMantenimiento(Context context){
        mantenimiento = new ModeloMantenimiento(context);
        detalleMantenimiento = new ModeloDetalleMantenimiento(context);
    }
    public List<Map<String,String>> mantenimientosMap(){
        List<ModeloMantenimiento> mantenimientos = mantenimiento.obtenerTodos();
        List<Map<String,String>> lista = new ArrayList<>();
        for (ModeloMantenimiento m : mantenimientos) {
            Map<String,String> itemMantenimiento = Map.of(
                    "ID", String.valueOf(m.getId()),
                    "vehiculo_id", String.valueOf(m.getVehiculo_id()),
                    "mecanico_id", String.valueOf(m.getMecanico_id()),
                    "Fecha", m.getFecha(),
                    "Kilometraje", String.valueOf(m.getKilometrajeMantenimineto()),
                    "Detalle", m.getDetalle(),
                    "Costo Total", String.valueOf(m.getCosto_total())
            );
            lista.add(itemMantenimiento);
        }
        return lista;
    }
    public List<Map<String, String>> detallesMap(int mantenimiento_id){
        List<ModeloDetalleMantenimiento> detalles = detalleMantenimiento.obtenerPorId(mantenimiento_id);
        List<Map<String, String>> lista = new ArrayList<>();
        for (ModeloDetalleMantenimiento d : detalles) {
            Map<String, String> itemDetalle = Map.of(
                    "id", String.valueOf(d.getId()),
                    "item_id", String.valueOf(d.getItem_id()),
                    "precio_unitario", String.valueOf(d.getPrecio_unitario()),
                    "cantidad", String.valueOf(d.getCantidad()),
                    "subtotal", String.valueOf(d.getSubtotal())
            );
            lista.add(itemDetalle);
        }
        return lista;

    }
    public List<Map<String,String>> getDetailsMantemimiento(){
        List<Map<String,String>> lista = new ArrayList<>();
        for (ModeloDetalleMantenimiento d : detalles) {
            Map<String,String> item = Map.of(
                    "id", String.valueOf(d.getId()),
                    "item_id", String.valueOf(d.getItem_id()),
                    "precio_unitario", String.valueOf(d.getPrecio_unitario()),
                    "cantidad", String.valueOf(d.getCantidad()),
                    "subtotal", String.valueOf(d.getSubtotal())
            );
            lista.add(item);
        }
        return lista;
    }
    public List<Integer> getIdsItems(){
        List<Integer> ids = new ArrayList<>();
        for (ModeloDetalleMantenimiento d : detalles) {
            ids.add(d.getItem_id());
        }
        return ids;
    }
    public void setDetalleMantenimientoSetToList(int item_id, double precio_unitario, double cantidad){
        double subtotal = precio_unitario * cantidad;
        ModeloDetalleMantenimiento det = new ModeloDetalleMantenimiento(0, 0, item_id, precio_unitario, cantidad, subtotal);
        if(detalles == null){
            detalles = new ArrayList<>();
        }
        detalles.add(det);
    }
    public Double calculoTotalDetalle(){
        Double total = 0.0;
        for (ModeloDetalleMantenimiento detalle : detalles) {
            total += detalle.getSubtotal();
        }
        return total;
    }
    public long registroMantenimientoCompleto(int idVehiculo, int idMecanico, String fecha, int kilometraje, String detalle){
        Double total = calculoTotalDetalle();
        ModeloMantenimiento mant = new ModeloMantenimiento(0, idVehiculo, idMecanico, fecha, kilometraje, detalle, total);
        long id = mant.agregar();
        if (id == -1) return -1;
        for (ModeloDetalleMantenimiento details : detalles) {
            details.setMantenimiento_id((int) id);
            long idDetalle = details.agregar();
            if (idDetalle == -1) return -1;
        }
        return id;
    }
    public void eliminarDetailsPos(int pos){
        if (pos >= 0 && pos < detalles.size()) {
            detalles.remove(pos);
        }
    }
    public void limpiarDetalles() {
        if (detalles != null) {
            detalles.clear();
        }
    }
}
