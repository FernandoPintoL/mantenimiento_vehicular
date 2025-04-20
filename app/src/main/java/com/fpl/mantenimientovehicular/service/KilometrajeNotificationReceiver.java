package com.fpl.mantenimientovehicular.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.fpl.mantenimientovehicular.R;
import com.fpl.mantenimientovehicular.vista.VistaVehiculo;

public class KilometrajeNotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Aquí puedes manejar la notificación cuando se recibe la alarma
        // Por ejemplo, mostrar una notificación al usuario
        int vehiculoId = intent.getIntExtra("VEHICULO_ID", 0);
        int kilometrajeActual = intent.getIntExtra("KILOMETRAJE_ACTUAL", 0);
        int kilometrajeObjetivo = intent.getIntExtra("KILOMETRAJE_OBJETIVO", 0);

        mostrarNotificacion(context, vehiculoId, kilometrajeActual, kilometrajeObjetivo);
    }

    private void mostrarNotificacion(Context context, int vehiculoId, int kmActual, int kmObjetivo) {
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

        Intent intent = new Intent(context, VistaVehiculo.class);
        intent.putExtra("VEHICULO_ID", vehiculoId);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                vehiculoId,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel_kilometraje")
                .setSmallIcon(R.drawable.baseline_circle_notifications_24)
                .setContentTitle("Mantenimiento requerido")
                .setContentText("El vehículo ha alcanzado " + kmActual + " km. ¡Es hora de mantenimiento!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        notificationManager.notify(vehiculoId, builder.build());
    }

}
