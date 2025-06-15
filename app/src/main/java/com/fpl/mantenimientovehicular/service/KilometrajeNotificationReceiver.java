package com.fpl.mantenimientovehicular.service;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.fpl.mantenimientovehicular.R;
import com.fpl.mantenimientovehicular.controller.NotificationHelper;

public class KilometrajeNotificationReceiver extends BroadcastReceiver {
    private static final String TAG = "KilometrajeNotification";

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            String titulo = intent.getStringExtra("TITULO");
            String mensaje = intent.getStringExtra("MENSAJE");

            if (titulo == null || mensaje == null) {
                Log.e(TAG, "Título o mensaje nulos en la notificación");
                return;
            }

            mostrarNotificacion(context, titulo, mensaje);
        } catch (Exception e) {
            Log.e(TAG, "Error al recibir la notificación: " + e.getMessage());
        }
    }

    private void mostrarNotificacion(Context context, String titulo, String mensaje) {
        try {
            // Usar el NotificationHelper para crear el canal de notificación
            NotificationHelper.createNotificationChannel(context);

            NotificationManager notificationManager = (NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);

            if (notificationManager == null) {
                Log.e(TAG, "NotificationManager es nulo");
                return;
            }

            // Crear un estilo para mensajes largos
            NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle()
                    .bigText(mensaje)
                    .setBigContentTitle(titulo)
                    .setSummaryText("Mantenimiento Vehicular");

            // Construir la notificación con estilo mejorado
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NotificationHelper.CHANNEL_ID)
                    .setSmallIcon(R.drawable.baseline_circle_notifications_24)
                    .setContentTitle(titulo)
                    .setContentText(mensaje)
                    .setStyle(bigTextStyle)
                    .setColor(Color.BLUE)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

            // Generar un ID único para la notificación
            int notificationId = (titulo + mensaje).hashCode();
            notificationManager.notify(notificationId, builder.build());

            Log.d(TAG, "Notificación mostrada con éxito: " + titulo);
        } catch (Exception e) {
            Log.e(TAG, "Error al mostrar la notificación: " + e.getMessage());
        }
    }
}
