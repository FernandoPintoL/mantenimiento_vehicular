package com.fpl.mantenimientovehicular.strategy;
import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.fpl.mantenimientovehicular.R;
import com.fpl.mantenimientovehicular.controller.NotificationHelper;
public class NotificacionUrgenteStrategy implements NotificationStrategy {

    @Override
    public void execute(Context context, String titulo, String mensaje) {
        try {
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            // Sonido de alerta para notificaciones urgentes
            Uri sonidoAlerta = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            if (sonidoAlerta == null) {
                sonidoAlerta = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            }

            // Crear la notificación con alta prioridad
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NotificationHelper.CHANNEL_ID)
                    .setSmallIcon(R.drawable.baseline_circle_notifications_24)
                    .setContentTitle("¡URGENTE! " + titulo)
                    .setContentText(mensaje)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_ALARM)
                    .setSound(sonidoAlerta)
                    .setVibrate(new long[] { 0, 500, 250, 500 }) // Patrón de vibración
                    .setAutoCancel(true);

            // Enviar la notificación
            if (manager != null) {
                // Usar un ID único para que no reemplace otras notificaciones
                int notificationId = (int) System.currentTimeMillis();
                manager.notify(notificationId, builder.build());
            } else {
                Log.e("NotificacionUrgenteStrategy", "NotificationManager es nulo");
            }
        } catch (Exception e) {
            Log.e("NotificacionUrgenteStrategy", "Error al enviar notificación: " + e.getMessage());
        }
    }
}