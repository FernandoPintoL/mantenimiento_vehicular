package com.fpl.mantenimientovehicular.controller;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.util.Log;

public class NotificationHelper {
    // Constants for notification channels
    public static final String CHANNEL_ID = "mantenimiento_channel";
    public static final String CHANNEL_NAME = "Mantenimiento Vehicular";
    public static final String CHANNEL_DESCRIPTION = "Notifications for vehicle maintenance reminders";

    // Constants for notification IDs
    public static final int NOTIFICATION_ID = 1001;

    // Other constants can be added here as needed
    public static void createNotificationChannel(Context context) {
        if(context instanceof Activity) {
            // Check if the permission is granted
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // Request the permission
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription(CHANNEL_DESCRIPTION);

            NotificationManager manager = context.getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
                Log.d("NotificationHelper", "Canal de notificación creado: " + CHANNEL_ID);
            } else {
                Log.e("NotificationHelper", "Error al obtener NotificationManager");
            }
        }
    }
}
