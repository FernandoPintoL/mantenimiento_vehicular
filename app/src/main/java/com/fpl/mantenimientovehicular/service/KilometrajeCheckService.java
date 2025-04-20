package com.fpl.mantenimientovehicular.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.fpl.mantenimientovehicular.controller.NotificacionController;
import com.fpl.mantenimientovehicular.model.ModeloVehiculo;
import com.fpl.mantenimientovehicular.negocio.NegocioVehiculo;

import java.util.List;

public class KilometrajeCheckService extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Lógica para verificar el kilometraje
        // Aquí puedes implementar la lógica para verificar el kilometraje del vehículo
        // y enviar una notificación si es necesario.
        // Lógica periódica para verificar kilometraje
        List<ModeloVehiculo> vehiculos = new NegocioVehiculo(this).obtenerTodos();

        for (ModeloVehiculo v : vehiculos) {
            if (v.necesitaMantenimiento(v.getId())) {
//                new NotificacionController(this).mostrarNotificacion(v);
            }
        }

        return START_STICKY;

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
