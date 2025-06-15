package com.fpl.mantenimientovehicular.strategy;
import android.content.Context;
public class NotificationContext {
    private NotificationStrategy strategy;
    public void setStrategy(NotificationStrategy strategy) {
        this.strategy = strategy;
    }
    public void executeStrategy(Context context, String titulo, String mensaje) {
        strategy.execute(context, titulo, mensaje);
    }
}
