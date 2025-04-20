package com.fpl.mantenimientovehicular.negocio;

import android.content.Context;

import com.fpl.mantenimientovehicular.model.ModeloDetalleMantenimiento;
import com.fpl.mantenimientovehicular.model.ModeloMantenimiento;

import java.util.ArrayList;
import java.util.List;

public class NegocioMantenimiento {
    private ModeloMantenimiento mantenimiento;
    private ModeloDetalleMantenimiento detalleMantenimiento;
    private List<ModeloDetalleMantenimiento> detalles;
    public NegocioMantenimiento(Context context){
        mantenimiento = new ModeloMantenimiento(context);
        detalleMantenimiento = new ModeloDetalleMantenimiento(context);
        detalles = new ArrayList<>();
    }
    public List<String> cargarDatosMantenimientoToListStr(){
        List<ModeloMantenimiento> mantenimientos = mantenimiento.obtenerTodos();
        List<String> lista = new ArrayList<>();
        for (ModeloMantenimiento m : mantenimientos) {
            String item = "ID: " + m.getId() + ", Fecha: " + m.getFecha() + ", Kilometraje: " + m.getKilometrajeMantenimineto() +
                    ", Detalle: " + m.getDetalle() + ", Costo Total: " + m.getCosto_total();
            lista.add(item);
        }
        return lista;
    }
    public List<String> cargarDatosDetMantToListStr(){
        List<String> lista = new ArrayList<>();
        for (ModeloDetalleMantenimiento d : detalles) {
            String item = "Item ID: " + d.getItem_id() +
                    ", Precio Unitario: " + d.getPrecio_unitario() +
                    ", Cantidad: " + d.getCantidad() +
                    ", Subtotal: " + d.getSubtotal();
            lista.add(item);
        }
        return lista;
    }
    public void setDetalleMantenimientoSetToList(int id, int mantenimiento_id, int item_id, double precio_unitario, double cantidad){
        double subtotal = precio_unitario * cantidad;
        ModeloDetalleMantenimiento det = new ModeloDetalleMantenimiento(id, mantenimiento_id, item_id, precio_unitario, cantidad, subtotal);
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
        ModeloMantenimiento mant = new ModeloMantenimiento(
                0,
                idVehiculo,
                idMecanico,
                fecha,
                kilometraje,
                detalle,
                total);
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
    //GETTERS Y SETTERS
    /*public ModeloMantenimiento getMantenimiento() {
        return mantenimiento;
    }
    public void setMantenimiento(ModeloMantenimiento mantenimiento) {
        this.mantenimiento = mantenimiento;
    }
    public ModeloDetalleMantenimiento getDetalleMantenimiento() {
        return detalleMantenimiento;
    }
    public void setDetalleMantenimiento(ModeloDetalleMantenimiento detalleMantenimiento) {
        this.detalleMantenimiento = detalleMantenimiento;
    }
    public List<ModeloDetalleMantenimiento> getDetalles() {
        return detalles;
    }
    public void setDetalles(List<ModeloDetalleMantenimiento> detalles) {
        this.detalles = detalles;
    }*/
    @Override
    public String toString() {
        return "NegocioMantenimiento{" +
                "mantenimiento=" + mantenimiento +
                ", detalleMantenimiento=" + detalleMantenimiento +
                ", detalles=" + detalles +
                '}';
    }

    public void limpiarDetalles() {
        if (detalles != null) {
            detalles.clear();
        }
    }
}
