package com.fpl.mantenimientovehicular.service;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import com.fpl.mantenimientovehicular.model.ModeloVehiculo;
public class AlarmaKilometraje {
    public static void programarAlarmaRecurrente(Context context,String titulo, String mensaje, int intervaloMillis) {
        Intent intent = new Intent(context, KilometrajeNotificationReceiver.class);
        intent.putExtra("TITULO", titulo);
        intent.putExtra("MENSAJE", mensaje);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                0, // ID único para esta alarma
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis() + intervaloMillis,
                    intervaloMillis,
                    pendingIntent
            );
        }
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
