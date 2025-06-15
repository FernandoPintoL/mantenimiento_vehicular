package com.fpl.mantenimientovehicular.negocio;
import android.annotation.SuppressLint;
import android.content.Context;
import com.fpl.mantenimientovehicular.model.ModeloNotificacion;
import com.fpl.mantenimientovehicular.strategy.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NegocioNotificacion {
    private final ModeloNotificacion modelo;
    private final NotificationContext notificationContext;
    private final NotificacionNormalStrategy notificationNormal;
    private final NotificacionUrgenteStrategy notificationUrgente;
    private final NotificacionSilenciosaStrategy notificationSilenciosa;
    private final NotificacionRecurrenteStrategy notificationRecurrente;
    private final Map<String, NotificationStrategy> estrategias = new HashMap<>();
    public NegocioNotificacion(Context context) {
        modelo = new ModeloNotificacion(context);
        // Inicializar el contexto con la estrategia normal por defecto
        notificationContext = new NotificationContext();
        notificationNormal = new NotificacionNormalStrategy();
        notificationUrgente = new NotificacionUrgenteStrategy();
        notificationSilenciosa = new NotificacionSilenciosaStrategy();
        notificationRecurrente = new NotificacionRecurrenteStrategy(); // Inicialmente sin intervalo
        // cargar tipos de notificación
        cargarEstrategias();
    }
    public void cargarEstrategias() {
        // Cargar las estrategias de notificación
        estrategias.put("NORMAL", notificationNormal);
        estrategias.put("URGENTE", notificationUrgente);
        estrategias.put("SILENCIOSA", notificationSilenciosa);
        estrategias.put("RECURRENTE", notificationRecurrente); // Añadir la estrategia recurrente
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
    /**
     * Convierte un tiempo en formato HH:mm a milisegundos
     * @param tiempo Tiempo en formato HH:mm
     * @return Tiempo en milisegundos
     */
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
    /**
     * Calcula el intervalo en milisegundos para notificaciones cada X horas y Y minutos
     * @param cantidadHoras Número de horas entre cada notificación
     * @param cantidadMinutos Número de minutos entre cada notificación
     * @return Intervalo en milisegundos
     */
    public int calcularIntervaloHoras(int cantidadHoras, int cantidadMinutos) {
        // Convertir horas y minutos a milisegundos
        return (cantidadHoras * 60 * 60 * 1000) + (cantidadMinutos * 60 * 1000);
    }
    /**
     * Calcula el intervalo en milisegundos para notificaciones cada X días a una hora específica
     * @param cantidadDias Número de días entre cada notificación
     * @param horaEspecifica Hora específica en formato HH:mm
     * @return Intervalo en milisegundos
     */
    public long calcularIntervaloDias(int cantidadDias, String horaEspecifica) {
        // Convertir días a milisegundos
        long intervaloBase = cantidadDias * 24 * 60 * 60 * 1000L;

        // Calcular el tiempo hasta la próxima ocurrencia de la hora específica
        Calendar ahora = Calendar.getInstance();
        Calendar objetivo = Calendar.getInstance();

        // Establecer la hora objetivo
        if (horaEspecifica != null && horaEspecifica.matches("\\d{2}:\\d{2}")) {
            String[] partes = horaEspecifica.split(":");
            int horas = Integer.parseInt(partes[0]);
            int minutos = Integer.parseInt(partes[1]);

            objetivo.set(Calendar.HOUR_OF_DAY, horas);
            objetivo.set(Calendar.MINUTE, minutos);
            objetivo.set(Calendar.SECOND, 0);
            objetivo.set(Calendar.MILLISECOND, 0);

            // Si la hora objetivo ya pasó hoy, establecerla para mañana
            if (objetivo.before(ahora)) {
                objetivo.add(Calendar.DAY_OF_MONTH, 1);
            }
        }

        // Calcular el tiempo hasta la próxima ocurrencia
        long tiempoHastaSiguiente = objetivo.getTimeInMillis() - ahora.getTimeInMillis();

        // Para la primera notificación, usar el tiempo hasta la próxima ocurrencia
        // Para las siguientes, usar el intervalo base
        return tiempoHastaSiguiente;
    }
    /**
     * Calcula el intervalo en milisegundos para notificaciones diarias a una hora específica
     * @param horaEspecifica Hora específica en formato HH:mm
     * @return Intervalo en milisegundos
     */
    public long calcularIntervaloDiario(String horaEspecifica) {
        // Para notificaciones diarias, el intervalo base es 24 horas
        return calcularIntervaloDias(1, horaEspecifica);
    }
    @SuppressLint("DefaultLocale")
    public String convertirMilisegundosATiempo(int milisegundos) {
        // Calcular horas y minutos
        int horas = milisegundos / (60 * 60 * 1000);
        int minutos = (milisegundos % (60 * 60 * 1000)) / (60 * 1000);

        // Si el tiempo es solo en minutos (menos de una hora)
        if (horas == 0 && minutos > 0) {
            return String.format("%d minutos", minutos);
        }
        // Si el tiempo es solo en horas (sin minutos)
        else if (horas > 0 && minutos == 0) {
            return String.format("%d horas", horas);
        }
        // Si el tiempo tiene horas y minutos
        else if (horas > 0 && minutos > 0) {
            return String.format("%d horas y %d minutos", horas, minutos);
        }
        // Formatear el tiempo en HH:mm para otros casos (como cuando se usa para tiempos específicos)
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
        // Si es tipo RECURRENTE, usar la estrategia recurrente con el intervalo actual
        if ("RECURRENTE".equals(tipoNotificacion)) {
            // Usar el intervalo almacenado en el modelo
            int intervalo = modelo.getIntervalo_notificacion();
            // mostrar un mensaje en consola para depuración
            if (intervalo > 0) {
                notificationRecurrente.setIntervaloMillis(intervalo);
                enviarNotificacionRecurrente(context, titulo, mensaje, intervalo);
            } else {
                // Si no hay intervalo válido, usar la estrategia normal
                ajustarEstrategias("NORMAL");
                notificationContext.executeStrategy(context, titulo, mensaje);
            }
        } else {
            // Para otros tipos, usar la estrategia correspondiente
            ajustarEstrategias(tipoNotificacion);
            notificationContext.executeStrategy(context, titulo, mensaje);
        }
    }
    public void enviarNotificacionRecurrente(Context context, String titulo, String mensaje, int intervaloMillis) {
        notificationContext.setStrategy(notificationRecurrente);
        notificationContext.executeStrategy(context, titulo, mensaje);
    }
    public String[] getTiposNotificacion() {
        // Añadir "RECURRENTE" a la lista de tipos de notificación
        /*String[] tiposEstrategias = estrategias.keySet().toArray(new String[0]);
        String[] tiposConRecurrente = new String[tiposEstrategias.length + 1];
        System.arraycopy(tiposEstrategias, 0, tiposConRecurrente, 0, tiposEstrategias.length);
        tiposConRecurrente[tiposEstrategias.length] = "RECURRENTE";
        return tiposConRecurrente;*/
        return estrategias.keySet().toArray(new String[0]);
    }
}
