package com.fpl.mantenimientovehicular.strategy;
import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.fpl.mantenimientovehicular.R;
import com.fpl.mantenimientovehicular.controller.NotificationHelper;
public class NotificacionSilenciosaStrategy implements NotificationStrategy {
    @Override
    public void execute(Context context, String titulo, String mensaje) {
        try {
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            // Crear la notificación silenciosa
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NotificationHelper.CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_notification_silent)
                    .setContentTitle(titulo)
                    .setContentText(mensaje)
                    .setPriority(NotificationCompat.PRIORITY_LOW)
                    .setCategory(NotificationCompat.CATEGORY_STATUS)
                    .setSound(null) // Sin sonido
                    .setVibrate(null) // Sin vibración
                    .setAutoCancel(true);

            // Enviar la notificación
            if (manager != null) {
                // Usar un ID fijo para que las notificaciones silenciosas se reemplacen entre sí
                int notificationId = NotificationHelper.NOTIFICATION_ID + 100;
                manager.notify(notificationId, builder.build());
            } else {
                Log.e("NotificacionSilenciosaStrategy", "NotificationManager es nulo");
            }
        } catch (Exception e) {
            Log.e("NotificacionSilenciosaStrategy", "Error al enviar notificación: " + e.getMessage());
        }
    }
}
