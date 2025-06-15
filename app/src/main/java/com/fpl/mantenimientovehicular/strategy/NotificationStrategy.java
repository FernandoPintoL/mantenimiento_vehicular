package com.fpl.mantenimientovehicular.strategy;
import android.content.Context;
public interface NotificationStrategy {
    void execute(Context context, String titulo, String mensaje);
}
