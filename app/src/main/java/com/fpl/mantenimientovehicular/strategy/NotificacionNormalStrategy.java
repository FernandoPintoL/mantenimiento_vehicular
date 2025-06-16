package com.fpl.mantenimientovehicular.strategy;
import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.fpl.mantenimientovehicular.R;
import com.fpl.mantenimientovehicular.controller.NotificationHelper;
public class NotificacionNormalStrategy implements NotificationStrategy {
    @Override
    public void execute(Context context, String titulo, String mensaje) {
        try {
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            // Crear la notificación con prioridad normal
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NotificationHelper.CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_notification_normal)
                    .setContentTitle(titulo)
                    .setContentText(mensaje)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true);

            // Enviar la notificación
            if (manager != null) {
                manager.notify(NotificationHelper.NOTIFICATION_ID, builder.build());
            } else {
                Log.e("NotificacionNormalStrategy", "NotificationManager es nulo");
            }
        } catch (Exception e) {
            Log.e("NotificacionNormalStrategy", "Error al enviar notificación: " + e.getMessage());
        }
    }
}
