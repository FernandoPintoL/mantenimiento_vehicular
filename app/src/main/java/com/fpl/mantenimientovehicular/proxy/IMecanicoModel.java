package com.fpl.mantenimientovehicular.proxy;
import com.fpl.mantenimientovehicular.model.ModeloMecanico;
import java.util.List;
public interface IMecanicoModel {
    // Database operations
    long agregar(ModeloMecanico modelo);
    int actualizar(ModeloMecanico model);
    int eliminar(int id);

    // Retrieval operations
    List<ModeloMecanico> obtenerTodos();
    ModeloMecanico obtenerPorId(int id);

    // Validation methods
    String getErrorMessage();

    // Getters and setters for model properties
    int getId();
    void setId(int id);
    String getNombre();
    void setNombre(String nombre);
    String getTaller();
    void setTaller(String taller);
    String getDireccion();
    void setDireccion(String direccion);
    String getTelefono();
    void setTelefono(String telefono);
}
