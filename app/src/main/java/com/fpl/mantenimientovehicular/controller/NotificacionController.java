package com.fpl.mantenimientovehicular.controller;

import android.content.Context;

import com.fpl.mantenimientovehicular.model.ModeloVehiculo;
import com.fpl.mantenimientovehicular.model.ModeloNotificacion;
import com.fpl.mantenimientovehicular.negocio.NegocioNotificacion;
import com.fpl.mantenimientovehicular.service.AlarmaKilometraje;
import com.fpl.mantenimientovehicular.vista.NotificacionActivity;

public class NotificacionController {
    private NegocioNotificacion negocio;
    private NotificacionActivity vista;

    public NotificacionController(NegocioNotificacion negocio, NotificacionActivity vista) {
        this.vista = vista;
        this.negocio = negocio;
    }

    /*public void programarNotificacion(ModeloVehiculo vehiculo) {
        // Lógica para calcular el próximo kilometraje
        int kmObjetivo = vehiculo.getKilometrajeActual() + 5000;

        // Guardar en modelo
        ModeloNotificacion notif = new ModeloNotificacion();
        notif.setIdVehiculo(vehiculo.getId());
        notif.setKilometrajeObjetivo(kmObjetivo);
        notif.agregar();

        // Programar alarma
        AlarmaKilometraje.programar(context, vehiculo, kmObjetivo);
    }

    public void cancelarNotificacion(int idVehiculo) {
        // Lógica para cancelar notificación
    }*/
}
