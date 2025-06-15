package com.fpl.mantenimientovehicular.strategy;

import android.content.Context;
import android.util.Log;

import com.fpl.mantenimientovehicular.service.AlarmaKilometraje;

/**
 * Estrategia para enviar notificaciones recurrentes para advertir sobre futuros mantenimientos.
 * Esta estrategia programa una alarma que se repetirá según el intervalo especificado.
 */
public class NotificacionRecurrenteStrategy implements NotificationStrategy {
    private static final String TAG = "NotificacionRecurrente";
    private int intervaloMillis;
    public void setIntervaloMillis(int intervaloMillis) {
        this.intervaloMillis = intervaloMillis;
    }
    @Override
    public void execute(Context context, String titulo, String mensaje) {
        try {
            // Mostrar una notificación inmediata
            NotificationStrategy normalStrategy = new NotificacionNormalStrategy();
            normalStrategy.execute(context, titulo, mensaje);
            
            // Programar notificaciones recurrentes
            Log.d(TAG, "Programando notificación recurrente con intervalo: " + intervaloMillis + " ms");
            AlarmaKilometraje.programarAlarmaRecurrente(
                    context,
                    titulo,
                    mensaje,
                    intervaloMillis
            );
        } catch (Exception e) {
            Log.e(TAG, "Error al programar notificación recurrente: " + e.getMessage());
        }
    }
}