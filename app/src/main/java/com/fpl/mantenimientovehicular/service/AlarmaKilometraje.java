package com.fpl.mantenimientovehicular.service;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import com.fpl.mantenimientovehicular.model.ModeloVehiculo;
import java.util.Calendar;
public class AlarmaKilometraje {
    /**
     * Programa una alarma recurrente con un intervalo específico
     * @param context Contexto de la aplicación
     * @param titulo Título de la notificación
     * @param mensaje Mensaje de la notificación
     * @param intervaloMillis Intervalo en milisegundos entre cada notificación
     */
    public static void programarAlarmaRecurrente(Context context, String titulo, String mensaje, int intervaloMillis) {
        Intent intent = new Intent(context, KilometrajeNotificationReceiver.class);
        intent.putExtra("TITULO", titulo);
        intent.putExtra("MENSAJE", mensaje);

        // Generar un ID único basado en el título y mensaje para evitar duplicados
        int requestCode = (titulo + mensaje).hashCode();

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            // Si el intervalo es menor a un día, usar setRepeating para mayor precisión
            if (intervaloMillis < 24 * 60 * 60 * 1000) {
                alarmManager.setRepeating(
                        AlarmManager.RTC_WAKEUP,
                        System.currentTimeMillis() + intervaloMillis,
                        intervaloMillis,
                        pendingIntent
                );
            } else {
                // Para intervalos más largos, usar setExactAndAllowWhileIdle para asegurar que se active
                // incluso si el dispositivo está en modo de ahorro de energía
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(
                            AlarmManager.RTC_WAKEUP,
                            System.currentTimeMillis() + intervaloMillis,
                            pendingIntent
                    );
                } else {
                    alarmManager.setExact(
                            AlarmManager.RTC_WAKEUP,
                            System.currentTimeMillis() + intervaloMillis,
                            pendingIntent
                    );
                }
            }
        }
    }

    /**
     * Programa una alarma para una hora específica del día
     * @param context Contexto de la aplicación
     * @param titulo Título de la notificación
     * @param mensaje Mensaje de la notificación
     * @param horaEspecifica Hora específica en formato HH:mm
     */
    public static void programarAlarmaHoraEspecifica(Context context, String titulo, String mensaje, String horaEspecifica) {
        if (horaEspecifica == null || !horaEspecifica.matches("\\d{2}:\\d{2}")) {
            return;
        }

        // Calcular el tiempo hasta la próxima ocurrencia de la hora específica
        Calendar ahora = Calendar.getInstance();
        Calendar objetivo = Calendar.getInstance();

        // Establecer la hora objetivo
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

        // Calcular el tiempo hasta la próxima ocurrencia
        long tiempoHastaSiguiente = objetivo.getTimeInMillis() - ahora.getTimeInMillis();

        // Programar la alarma
        programarAlarmaRecurrente(context, titulo, mensaje, (int) tiempoHastaSiguiente);
    }
    public static void cancelarTodasLasAlarmas(Context context) {
        Intent intent = new Intent(context, KilometrajeNotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                intent,
                PendingIntent.FLAG_NO_CREATE | PendingIntent.FLAG_IMMUTABLE // Agregar FLAG_IMMUTABLE
        );

        if (pendingIntent != null) {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            if (alarmManager != null) {
                alarmManager.cancel(pendingIntent);
            }
        }
    }
    private static long calcularMomentoNotificacion(ModeloVehiculo vehiculo, int kilometrajeObjetivo) {
        // Aquí deberías implementar tu lógica para estimar cuándo se alcanzará el km
        // Esto es un ejemplo simplificado:
        int kmRestantes = kilometrajeObjetivo - vehiculo.getKilometrajeActual();
        double kmPorDia = 50; // Estimación de km diarios
        long diasRestantes = (long) (kmRestantes / kmPorDia);

        return System.currentTimeMillis() + (diasRestantes * 24 * 60 * 60 * 1000);
    }
}
