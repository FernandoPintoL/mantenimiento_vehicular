package com.fpl.mantenimientovehicular.negocio;
import android.annotation.SuppressLint;
import android.content.Context;
import com.fpl.mantenimientovehicular.model.ModeloNotificacion;
import com.fpl.mantenimientovehicular.strategy.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NegocioNotificacion {
    private final ModeloNotificacion modelo;
    private final NotificationContext notificationContext;
    private final Map<String, NotificationStrategy> estrategias = new HashMap<>();
    public NegocioNotificacion(Context context) {
        modelo = new ModeloNotificacion(context);
        // Inicializar el contexto con la estrategia normal por defecto
        notificationContext = new NotificationContext();
        // cargar tipos de notificación
        cargarEstrategias();
    }
    public void cargarEstrategias() {
        // Cargar las estrategias de notificación
        estrategias.put("NORMAL", new NotificacionNormalStrategy());
        estrategias.put("URGENTE", new NotificacionUrgenteStrategy());
        estrategias.put("SILENCIOSA", new NotificacionSilenciosaStrategy());
    }
    public void cargarFormulario(int id, int idVehiculo, String title ,String mensaje, int kilometrajeObjetivo, String intervalo_notificacion,boolean activa) {
        int intervalo = convertirTiempoAMilisegundos(intervalo_notificacion);
        modelo.setId(id);
        modelo.setIdVehiculo(idVehiculo);
        modelo.setTitle(title);
        modelo.setMensaje(mensaje);
        modelo.setKilometrajeObjetivo(kilometrajeObjetivo);
        modelo.setIntervalo_notificacion(intervalo);
        modelo.setActiva(activa);
    }
    //cargar datos a la vista
    public List<Map<String, String>> cargarDatos() {
        List<ModeloNotificacion> datos = modelo.obtenerTodos();
        List<Map<String, String>> list = new ArrayList<>();

        for (ModeloNotificacion notificacion : datos) {
            Map<String, String> map = new HashMap<>();
            String intervalo = convertirMilisegundosATiempo(notificacion.getIntervalo_notificacion());
            map.put("id", String.valueOf(notificacion.getId()));
            map.put("vehiculo_id", String.valueOf(notificacion.getIdVehiculo()));
            map.put("titulo", notificacion.getTitle());
            map.put("mensaje", notificacion.getMensaje());
            map.put("kilometraje_objetivo", String.valueOf(notificacion.getKilometrajeObjetivo()));
            map.put("intervalo_notificacion", intervalo);
            map.put("activo", String.valueOf(notificacion.isActiva()));
            list.add(map);
        }

        return list;
    }
    public long agregar() {
        return modelo.agregar(modelo);
    }
    public int eliminar() {
        return modelo.eliminar(modelo.getId());
    }
    public int editar() {
        return modelo.modificar(modelo);
    }
    public int convertirTiempoAMilisegundos(String tiempo) {
        // Verificar si el tiempo está en el formato correcto (HH:mm) o si es nulo o vacío retorna  0
        if (tiempo == null || tiempo.isEmpty() || !tiempo.matches("\\d{2}:\\d{2}")) {
            return 0;
        }
        // Dividir el tiempo en horas y minutos
        String[] partes = tiempo.split(":");
        int horas = Integer.parseInt(partes[0]);
        int minutos = Integer.parseInt(partes[1]);

        // Convertir a milisegundos
        return (horas * 60 * 60 * 1000) + (minutos * 60 * 1000);
    }
    @SuppressLint("DefaultLocale")
    public String convertirMilisegundosATiempo(int milisegundos) {
        // Calcular horas y minutos
        int horas = milisegundos / (60 * 60 * 1000);
        int minutos = (milisegundos % (60 * 60 * 1000)) / (60 * 1000);

        // Formatear el tiempo en HH:mm
        return String.format("%02d:%02d", horas, minutos);
    }
    public List<Map<String,String>> getNotificacionesActivas() {
        List<ModeloNotificacion> datos = modelo.getNotificacionesActivas();
        List<Map<String, String>> list = new ArrayList<>();
        for (ModeloNotificacion notificacion : datos) {
            Map<String, String> map = new HashMap<>();
            String intervalo = convertirMilisegundosATiempo(notificacion.getIntervalo_notificacion());
            map.put("id", String.valueOf(notificacion.getId()));
            map.put("vehiculo_id", String.valueOf(notificacion.getIdVehiculo()));
            map.put("titulo", notificacion.getTitle());
            map.put("mensaje", notificacion.getMensaje());
            map.put("kilometraje_objetivo", String.valueOf(notificacion.getKilometrajeObjetivo()));
            map.put("intervalo_notificacion", intervalo);
            map.put("activo", String.valueOf(notificacion.isActiva()));
            list.add(map);
        }
        return list;
    }
    public void ajustarEstrategias(String tipo) {
        NotificationStrategy strategy = estrategias.get(tipo.toUpperCase());
        if (strategy == null) {
            strategy = new NotificacionNormalStrategy();
        }
        notificationContext.setStrategy(strategy);
    }
    public void enviarNotificacion(String tipoNotificacion, Context context, String titulo, String mensaje) {
        ajustarEstrategias(tipoNotificacion);
        notificationContext.executeStrategy(context, titulo, mensaje);
    }
    public String[] getTiposNotificacion() {
        return estrategias.keySet().toArray(new String[0]);
    }
}
