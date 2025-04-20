package com.fpl.mantenimientovehicular.service;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.fpl.mantenimientovehicular.model.ModeloVehiculo;

public class AlarmaKilometraje {
    @SuppressLint("ScheduleExactAlarm")
    public static void programarAlarma(Context context, ModeloVehiculo vehiculo, int kilometrajeObjetivo) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, KilometrajeNotificationReceiver.class);
        intent.putExtra("VEHICULO_ID", vehiculo.getId());
        intent.putExtra("KILOMETRAJE_ACTUAL", vehiculo.getKilometrajeActual());
        intent.putExtra("KILOMETRAJE_OBJETIVO", kilometrajeObjetivo);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                vehiculo.getId(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        // Configura la alarma para cuando se alcance el kilometraje objetivo
        // Nota: En realidad necesitarías verificar periódicamente el odómetro
        long triggerAtMillis = calcularMomentoNotificacion(vehiculo, kilometrajeObjetivo);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    triggerAtMillis,
                    pendingIntent
            );
        } else {
            alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    triggerAtMillis,
                    pendingIntent
            );
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
