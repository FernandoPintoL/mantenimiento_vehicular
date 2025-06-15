# Implementación del Patrón Strategy en MantenimientoVehicular

## Descripción del Patrón Strategy

El patrón Strategy es un patrón de diseño de comportamiento que permite definir una familia de algoritmos, encapsular cada uno de ellos y hacerlos intercambiables. Este patrón permite que el algoritmo varíe independientemente de los clientes que lo utilizan.

## Implementación en el Proyecto

En este proyecto, hemos aplicado el patrón Strategy para gestionar diferentes tipos de notificaciones en la aplicación de Mantenimiento Vehicular. La implementación permite enviar notificaciones con diferentes prioridades y estilos según las necesidades del usuario.

### Estructura de la Implementación

1. **Interfaz Strategy**: `NotificationStrategy`
   - Define el contrato para todas las estrategias de notificación
   - Métodos: `enviarNotificacion()`, `getNombreEstrategia()`

2. **Estrategias Concretas**:
   - `NotificacionNormalStrategy`: Envía notificaciones con prioridad normal
   - `NotificacionUrgenteStrategy`: Envía notificaciones con alta prioridad, sonido y vibración
   - `NotificacionSilenciosaStrategy`: Envía notificaciones sin sonido ni vibración

3. **Contexto**: `NotificationContext`
   - Mantiene una referencia a una estrategia concreta
   - Permite cambiar la estrategia en tiempo de ejecución
   - Delega la ejecución del algoritmo a la estrategia

4. **Integración con MVC**:
   - `NegocioNotificacion`: Utiliza el contexto para enviar notificaciones
   - `VistaNotificacion`: Permite al usuario seleccionar el tipo de notificación
   - `NotificationController`: Actualiza la estrategia según la selección del usuario

### Diagrama de Clases

```
+-------------------+       +----------------------+
| NegocioNotificacion|<>---->| NotificationContext |
+-------------------+       +----------------------+
                                      |
                                      |
                                      v
                            +--------------------+
                            | NotificationStrategy|
                            +--------------------+
                                      ^
                                      |
                    +----------------+----------------+
                    |                |                |
        +-----------------------+  +---------------------+  +-------------------------+
        |NotificacionNormalStrategy| |NotificacionUrgenteStrategy| |NotificacionSilenciosaStrategy|
        +-----------------------+  +---------------------+  +-------------------------+
```

## Beneficios de la Implementación

1. **Flexibilidad**: Permite añadir nuevos tipos de notificaciones sin modificar el código existente.
2. **Mantenibilidad**: Cada estrategia está encapsulada en su propia clase, lo que facilita su mantenimiento.
3. **Extensibilidad**: Es fácil añadir nuevas estrategias de notificación según las necesidades futuras.
4. **Desacoplamiento**: La lógica de notificación está desacoplada de la lógica de negocio.
5. **Intercambiabilidad**: Las estrategias pueden cambiarse en tiempo de ejecución según las preferencias del usuario.

## Ejemplo de Uso

```java
// Crear una instancia de NegocioNotificacion con una estrategia específica
NegocioNotificacion negocio = new NegocioNotificacion(context, NotificationContext.TipoNotificacion.URGENTE);

// Enviar una notificación utilizando la estrategia actual
negocio.enviarNotificacion(context, "Título", "Mensaje");

// Cambiar la estrategia en tiempo de ejecución
negocio.cambiarEstrategiaNotificacion(NotificationContext.TipoNotificacion.SILENCIOSA);

// Enviar otra notificación con la nueva estrategia
negocio.enviarNotificacion(context, "Otro título", "Otro mensaje");
```

## Integración con el Patrón MVC Existente

El patrón Strategy se ha integrado perfectamente con el patrón MVC existente en la aplicación:

1. **Modelo (M)**: Las estrategias de notificación encapsulan la lógica de cómo enviar diferentes tipos de notificaciones.
2. **Vista (V)**: La vista permite al usuario seleccionar el tipo de notificación a través de un Spinner.
3. **Controlador (C)**: El controlador actualiza la estrategia según la selección del usuario y coordina la interacción entre la vista y el modelo.

Esta integración demuestra cómo diferentes patrones de diseño pueden trabajar juntos para crear una arquitectura más flexible y mantenible.

## Conclusión

La implementación del patrón Strategy en este proyecto ha mejorado significativamente la flexibilidad y mantenibilidad del sistema de notificaciones. Ahora es posible añadir nuevos tipos de notificaciones sin modificar el código existente, y los usuarios pueden seleccionar el tipo de notificación que mejor se adapte a sus necesidades.