# Mejora de la Interfaz de Notificaciones con Nuevos Iconos

## Resumen de Cambios

Se ha mejorado la interfaz de notificaciones de la aplicación mediante la implementación de iconos distintos para cada tipo de notificación. Esto permite a los usuarios identificar visualmente el tipo de notificación que están recibiendo.

## Iconos Implementados

Se han creado tres nuevos iconos vectoriales para los diferentes tipos de notificaciones:

1. **Notificaciones Normales** (`ic_notification_normal.xml`)
   - Icono: Campana de notificación dentro de un círculo
   - Color: Azul (#0000FF)
   - Uso: Notificaciones estándar de mantenimiento vehicular

2. **Notificaciones Urgentes** (`ic_notification_urgent.xml`)
   - Icono: Signo de exclamación dentro de un círculo
   - Color: Rojo (#FF0000)
   - Uso: Alertas urgentes que requieren atención inmediata

3. **Notificaciones Silenciosas** (`ic_notification_silent.xml`)
   - Icono: Signo de más dentro de un círculo
   - Color: Gris (#808080)
   - Uso: Notificaciones informativas de baja prioridad

## Archivos Modificados

Se han actualizado los siguientes archivos para utilizar los nuevos iconos:

1. `NotificacionNormalStrategy.java` - Ahora usa el icono `ic_notification_normal`
2. `NotificacionUrgenteStrategy.java` - Ahora usa el icono `ic_notification_urgent`
3. `NotificacionSilenciosaStrategy.java` - Ahora usa el icono `ic_notification_silent`
4. `KilometrajeNotificationReceiver.java` - Ahora usa el icono `ic_notification_normal`

## Beneficios

- **Mejor experiencia de usuario**: Los usuarios pueden identificar rápidamente el tipo de notificación por su icono
- **Consistencia visual**: Cada tipo de notificación tiene un estilo visual coherente
- **Accesibilidad mejorada**: El uso de colores distintos ayuda a diferenciar las notificaciones incluso para usuarios con dificultades visuales

## Compatibilidad

Los iconos se han implementado como drawables vectoriales, lo que garantiza:
- Compatibilidad con diferentes densidades de pantalla
- Tamaño de archivo reducido
- Escalabilidad sin pérdida de calidad

## Ejemplo de Uso

Para enviar una notificación con el icono apropiado, simplemente use la estrategia correspondiente:

```java
NotificationContext context = new NotificationContext();

// Para notificaciones normales
context.setStrategy(new NotificacionNormalStrategy());
context.executeStrategy(getApplicationContext(), "Título", "Mensaje");

// Para notificaciones urgentes
context.setStrategy(new NotificacionUrgenteStrategy());
context.executeStrategy(getApplicationContext(), "Título", "Mensaje");

// Para notificaciones silenciosas
context.setStrategy(new NotificacionSilenciosaStrategy());
context.executeStrategy(getApplicationContext(), "Título", "Mensaje");
```