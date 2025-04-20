package com.fpl.mantenimientovehicular.controller;

import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class NotificationController {
    private Context context;
    public NotificationController(Context context) {
        this.context = context;
        NotificationHelper.createNotificationChannel(context);
    }
    public void enviarNotificacion(String titulo, String mensaje) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Crear la notificación
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NotificationHelper.CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info) // Asegúrate de que este icono exista
                .setContentTitle(titulo)
                .setContentText(mensaje)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        // Enviar la notificación
        if (manager != null) {
            manager.notify(NotificationHelper.NOTIFICATION_ID, builder.build());
        } else {
            Log.e("NotificationController", "NotificationManager es nulo");
        }
    }
}
