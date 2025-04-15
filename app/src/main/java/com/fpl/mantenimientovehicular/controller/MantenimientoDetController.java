package com.fpl.mantenimientovehicular.controller;

import android.content.Context;

import com.fpl.mantenimientovehicular.model.ModeloDetalleMantenimiento;
import com.fpl.mantenimientovehicular.model.ModeloMantenimiento;

import java.util.List;

public class MantenimientoDetController {
    private final ModeloMantenimiento mantenimiento;
    private final ModeloDetalleMantenimiento detalleMantenimiento;
    public MantenimientoDetController(Context context) {
        mantenimiento = new ModeloMantenimiento(context);
        detalleMantenimiento = new ModeloDetalleMantenimiento(context);
    }
    public long agregarMantenimiento(ModeloMantenimiento mant, List<ModeloDetalleMantenimiento> details) {
        long idMantenimiento = mantenimiento.agregar(mant);
        if (idMantenimiento != -1) {
            for (ModeloDetalleMantenimiento detalle : details) {
                detalle.setId((int) idMantenimiento);
                detalleMantenimiento.agregar(detalle);
            }
        }
        return idMantenimiento;
    }
    public List<ModeloMantenimiento> obtenerMantenimientos() {
        return mantenimiento.obtenerTodos();
    }
    public List<ModeloDetalleMantenimiento> obtenerDetallesMantenimientos() {
        return detalleMantenimiento.obtenerTodos();
    }
    public ModeloMantenimiento obtenerPorIdMantenimiento(int id) {
        return mantenimiento.obtenerPorId(id);
    }
    public ModeloDetalleMantenimiento obtenerPorIdDetalleMantenimiento(int id) {
        return detalleMantenimiento.obtenerPorId(id);
    }
    public int actualizarMantenimientoDetalle(ModeloMantenimiento mant, List<ModeloDetalleMantenimiento> details) {
        int idMantenimiento = mantenimiento.actualizar(mant);
        if (idMantenimiento != -1) {
            for (ModeloDetalleMantenimiento detalle : details) {
                detalle.setId((int) idMantenimiento);
                detalleMantenimiento.actualizar(detalle);
            }
        }
        return idMantenimiento;
    }
    public boolean eliminarMantenimiento(int mantenimiento_id) {
        int result = mantenimiento.eliminar(mantenimiento_id);
        if (result > 0) {
            detalleMantenimiento.eliminar(mantenimiento_id);
        }
        return result > 0;
    }
    public void closeMantenimiento() {mantenimiento.close();}
    public void closeDetalleMantenimiento() {
        detalleMantenimiento.close();
    }
}
