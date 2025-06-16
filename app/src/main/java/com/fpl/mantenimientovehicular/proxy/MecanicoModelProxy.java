package com.fpl.mantenimientovehicular.proxy;

import android.content.Context;
import android.util.Log;
import com.fpl.mantenimientovehicular.model.ModeloMecanico;
import java.util.List;
import java.util.regex.Pattern;

public class MecanicoModelProxy implements IMecanicoModel {
    private static final String TAG = "MecanicoModelProxy";
    private final ModeloMecanico realModelo;
    private String errorMessage = "";

    // Constructor with context
    public MecanicoModelProxy(Context context) {
        this.realModelo = new ModeloMecanico(context);
    }

    // Constructor que instancia al modelo real con los parámetros necesarios
    public MecanicoModelProxy(int id, String nombre, String taller, String direccion, String telefono) {
        this.realModelo = new ModeloMecanico(id, nombre, taller, direccion, telefono);
    }
    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
    @Override
    public long agregar(ModeloMecanico modelo) {
        if (!checkAccess(modelo)) {
            Log.e(TAG, "Datos inválidos para agregar mecánico: " + errorMessage);
            return -1;
        }
        return realModelo.agregar(modelo);
    }
    @Override
    public int actualizar(ModeloMecanico model) {
        if (!checkAccess(model)) {
            Log.e(TAG, "Datos inválidos para actualizar mecánico: " + errorMessage);
            return -1;
        }
        return realModelo.actualizar(model);
    }
    public boolean checkAccess(ModeloMecanico modelo) {
        // Reset error message
        errorMessage = "";

        // Validate nombre (name)
        if (modelo.getNombre() == null || modelo.getNombre().trim().isEmpty()) {
            errorMessage = "El nombre del mecanico no puede estar vacío";
            Log.e(TAG, errorMessage);
            return false;
        }

        // Validate taller (workshop)
        if (modelo.getTaller() == null || modelo.getTaller().trim().isEmpty()) {
            errorMessage = "El taller no puede estar vacío";
            Log.e(TAG, errorMessage);
            return false;
        }

        // Validate direccion (address)
        if (modelo.getDireccion() == null || modelo.getDireccion().trim().isEmpty()) {
            errorMessage = "La dirección no puede estar vacía";
            Log.e(TAG, errorMessage);
            return false;
        }

        // Validate telefono (phone)
        if (modelo.getTelefono() == null || modelo.getTelefono().trim().isEmpty()) {
            errorMessage = "El teléfono no puede estar vacío";
            Log.e(TAG, errorMessage);
            return false;
        }

        // Validate phone format (simple validation for numeric values)
        if (!Pattern.matches("^[0-9+\\-\\s()]*$", modelo.getTelefono()) || modelo.getTelefono().length() <= 7) {
            errorMessage = "El formato del teléfono no es válido";
            Log.e(TAG, errorMessage);
            return false;
        }

        return true;
    }

    @Override
    public int eliminar(int id) {
        return realModelo.eliminar(id);
    }
    @Override
    public List<ModeloMecanico> obtenerTodos() {
        return realModelo.obtenerTodos();
    }
    @Override
    public ModeloMecanico obtenerPorId(int id) {
        return realModelo.obtenerPorId(id);
    }
    @Override
    public int getId() {
        return realModelo.getId();
    }
    @Override
    public void setId(int id) {
        realModelo.setId(id);
    }
    @Override
    public String getNombre() {
        return realModelo.getNombre();
    }
    @Override
    public void setNombre(String nombre) {
        realModelo.setNombre(nombre);
    }
    @Override
    public String getTaller() {
        return realModelo.getTaller();
    }
    @Override
    public void setTaller(String taller) {
        realModelo.setTaller(taller);
    }
    @Override
    public String getDireccion() {
        return realModelo.getDireccion();
    }
    @Override
    public void setDireccion(String direccion) {
        realModelo.setDireccion(direccion);
    }
    @Override
    public String getTelefono() {
        return realModelo.getTelefono();
    }
    @Override
    public void setTelefono(String telefono) {
        realModelo.setTelefono(telefono);
    }
    @Override
    public String toString() {
        return "MecanicoModelProxy{" +
                "id=" + realModelo.getId() +
                ", nombre='" + realModelo.getNombre() + '\'' +
                ", taller='" + realModelo.getTaller() + '\'' +
                ", direccion='" + realModelo.getDireccion() + '\'' +
                ", telefono='" + realModelo.getTelefono() + '\'' +
                '}';
    }
}
