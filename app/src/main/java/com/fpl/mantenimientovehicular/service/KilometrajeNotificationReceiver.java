package com.fpl.mantenimientovehicular.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.fpl.mantenimientovehicular.R;
import com.fpl.mantenimientovehicular.controller.NotificationHelper;

public class KilometrajeNotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String titulo = intent.getStringExtra("TITULO");
        String mensaje = intent.getStringExtra("MENSAJE");
        mostrarNotificacion(context,titulo, mensaje);
    }

    private void mostrarNotificacion(Context context, String titulo, String mensaje) {
        // aqui falta pasar el mensaje y titulo a la notificacion
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    NotificationHelper.CHANNEL_ID,
                    NotificationHelper.CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NotificationHelper.CHANNEL_ID)
                .setSmallIcon(R.drawable.baseline_circle_notifications_24)
                .setContentTitle(titulo)
                .setContentText(mensaje)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        // Generar un ID único para la notificación
        int notificationId = (int) System.currentTimeMillis(); // Usar el tiempo actual como ID único
        notificationManager.notify(notificationId, builder.build());
    }

}
