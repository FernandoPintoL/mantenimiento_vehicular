package com.fpl.mantenimientovehicular.service;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.fpl.mantenimientovehicular.model.ModeloVehiculo;

public class AlarmaKilometraje {
    public static void programarAlarmaRecurrente(Context context, int intervaloMillis) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, KilometrajeNotificationReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                0, // ID único para esta alarma
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        // Configura la alarma recurrente
        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + intervaloMillis, // Primera ejecución
                intervaloMillis, // Intervalo de repetición
                pendingIntent
        );
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
