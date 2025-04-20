package com.fpl.mantenimientovehicular.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.fpl.mantenimientovehicular.R;

public class KilometrajeNotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Aquí puedes manejar la notificación cuando se recibe la alarma
        // Por ejemplo, mostrar una notificación al usuario
        int vehiculoId = intent.getIntExtra("VEHICULO_ID", 0);
        int kilometrajeActual = intent.getIntExtra("KILOMETRAJE_ACTUAL", 0);
        int kilometrajeObjetivo = intent.getIntExtra("KILOMETRAJE_OBJETIVO", 0);

        mostrarNotificacion(context);
    }

    private void mostrarNotificacion(Context context) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "channel_kilometraje",
                    "Recordatorios de Kilometraje",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel_kilometraje")
                .setSmallIcon(R.drawable.baseline_circle_notifications_24)
                .setContentTitle("Revisar Kilometraje")
                .setContentText("No olvides revisar el kilometraje de tu vehículo.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        notificationManager.notify(0, builder.build());
    }

}
