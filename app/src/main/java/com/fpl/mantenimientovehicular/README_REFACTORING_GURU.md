# Implementación del Patrón Strategy según Refactoring.guru

## Introducción

Este documento describe cómo se ha implementado el patrón Strategy en la aplicación de Mantenimiento Vehicular siguiendo el pseudocódigo proporcionado por [Refactoring.guru](https://refactoring.guru/es/design-patterns/strategy).

## Pseudocódigo Original

El pseudocódigo proporcionado por Refactoring.guru muestra una implementación clásica del patrón Strategy:

```
// La interfaz estrategia declara operaciones comunes a todas
// las versiones soportadas de algún algoritmo. El contexto
// utiliza esta interfaz para invocar el algoritmo definido por
// las estrategias concretas.
interface Strategy is
    method execute(a, b)

// Las estrategias concretas implementan el algoritmo mientras
// siguen la interfaz estrategia base. La interfaz las hace
// intercambiables en el contexto.
class ConcreteStrategyAdd implements Strategy is
    method execute(a, b) is
        return a + b

class ConcreteStrategySubtract implements Strategy is
    method execute(a, b) is
        return a - b

class ConcreteStrategyMultiply implements Strategy is
    method execute(a, b) is
        return a * b

// El contexto define la interfaz de interés para los clientes.
class Context is
    // El contexto mantiene una referencia a uno de los objetos
    // de estrategia. El contexto no conoce la clase concreta de
    // una estrategia. Debe trabajar con todas las estrategias a
    // través de la interfaz estrategia.
    private strategy: Strategy

    // Normalmente, el contexto acepta una estrategia a través
    // del constructor y también proporciona un setter
    // (modificador) para poder cambiar la estrategia durante el
    // tiempo de ejecución.
    method setStrategy(Strategy strategy) is
        this.strategy = strategy

    // El contexto delega parte del trabajo al objeto de
    // estrategia en lugar de implementar varias versiones del
    // algoritmo por su cuenta.
    method executeStrategy(int a, int b) is
        return strategy.execute(a, b)
```

## Implementación en la Aplicación

Hemos adaptado este pseudocódigo a nuestra aplicación de Mantenimiento Vehicular, específicamente para el sistema de notificaciones. A continuación se detalla cómo se ha implementado cada parte del patrón:

### 1. Interfaz Strategy

```java
public interface NotificationStrategy {
    boolean execute(Context context, String titulo, String mensaje);
    String getNombreEstrategia();
}
```

Esta interfaz define el método `execute()` que todas las estrategias concretas deben implementar, siguiendo el patrón del pseudocódigo. También incluye un método adicional `getNombreEstrategia()` para obtener información descriptiva sobre la estrategia.

### 2. Estrategias Concretas

Hemos implementado tres estrategias concretas:

```java
public class NotificacionNormalStrategy implements NotificationStrategy {
    @Override
    public boolean execute(Context context, String titulo, String mensaje) {
        // Implementación para enviar notificaciones normales
    }
    
    @Override
    public String getNombreEstrategia() {
        return "Notificación Normal";
    }
}

public class NotificacionUrgenteStrategy implements NotificationStrategy {
    @Override
    public boolean execute(Context context, String titulo, String mensaje) {
        // Implementación para enviar notificaciones urgentes
    }
    
    @Override
    public String getNombreEstrategia() {
        return "Notificación Urgente";
    }
}

public class NotificacionSilenciosaStrategy implements NotificationStrategy {
    @Override
    public boolean execute(Context context, String titulo, String mensaje) {
        // Implementación para enviar notificaciones silenciosas
    }
    
    @Override
    public String getNombreEstrategia() {
        return "Notificación Silenciosa";
    }
}
```

Estas clases implementan diferentes algoritmos para enviar notificaciones, siguiendo el mismo patrón que las estrategias concretas del pseudocódigo.

### 3. Contexto

```java
public class NotificationContext {
    private NotificationStrategy strategy;
    
    public NotificationContext() {
        this.strategy = new NotificacionNormalStrategy();
    }
    
    public NotificationContext(NotificationStrategy strategy) {
        this.strategy = strategy;
    }
    
    public void setStrategy(NotificationStrategy strategy) {
        this.strategy = strategy;
    }
    
    public NotificationStrategy getStrategy() {
        return strategy;
    }
    
    public boolean executeStrategy(Context context, String titulo, String mensaje) {
        return strategy.execute(context, titulo, mensaje);
    }
    
    // Método de compatibilidad
    @Deprecated
    public boolean enviarNotificacion(Context context, String titulo, String mensaje) {
        return executeStrategy(context, titulo, mensaje);
    }
    
    // Método auxiliar para crear estrategias
    public static NotificationStrategy crearEstrategia(TipoNotificacion tipo) {
        // Implementación
    }
    
    public enum TipoNotificacion {
        NORMAL, URGENTE, SILENCIOSA
    }
}
```

El contexto sigue el mismo patrón que en el pseudocódigo:
- Mantiene una referencia a una estrategia
- Proporciona un constructor que acepta una estrategia
- Proporciona un método setter para cambiar la estrategia
- Delega la ejecución al método `execute()` de la estrategia a través del método `executeStrategy()`

### 4. Cliente

En nuestra aplicación, la clase `NegocioNotificacion` actúa como cliente:

```java
public class NegocioNotificacion {
    private NotificationContext notificationContext;
    
    public NegocioNotificacion(Context context) {
        // Inicialización
        notificationContext = new NotificationContext();
    }
    
    public boolean enviarNotificacion(Context context, String titulo, String mensaje) {
        return notificationContext.executeStrategy(context, titulo, mensaje);
    }
    
    public void cambiarEstrategiaNotificacion(NotificationContext.TipoNotificacion tipoNotificacion) {
        notificationContext.setStrategy(NotificationContext.crearEstrategia(tipoNotificacion));
    }
}
```

El cliente crea un contexto, puede cambiar la estrategia según sea necesario, y utiliza el método `executeStrategy()` del contexto para ejecutar la estrategia actual.

## Comparación con el Pseudocódigo

Nuestra implementación sigue fielmente el patrón Strategy como se muestra en el pseudocódigo de Refactoring.guru:

1. **Interfaz Strategy**: Hemos creado una interfaz `NotificationStrategy` con un método `execute()` similar al del pseudocódigo.
2. **Estrategias Concretas**: Hemos implementado varias estrategias concretas que implementan la interfaz `NotificationStrategy`.
3. **Contexto**: Nuestra clase `NotificationContext` mantiene una referencia a una estrategia, proporciona métodos para cambiarla, y delega la ejecución a la estrategia a través del método `executeStrategy()`.
4. **Cliente**: Nuestra clase `NegocioNotificacion` utiliza el contexto para ejecutar la estrategia seleccionada.

## Beneficios de la Implementación

Esta implementación proporciona varios beneficios:

1. **Flexibilidad**: Podemos cambiar el comportamiento de las notificaciones en tiempo de ejecución.
2. **Extensibilidad**: Podemos añadir nuevas estrategias de notificación sin modificar el código existente.
3. **Encapsulación**: Cada estrategia encapsula su propio algoritmo.
4. **Desacoplamiento**: El código que utiliza las notificaciones no necesita conocer los detalles de implementación de cada estrategia.
5. **Mantenibilidad**: Es más fácil mantener y extender el código.

## Conclusión

La implementación del patrón Strategy en nuestra aplicación de Mantenimiento Vehicular sigue fielmente el pseudocódigo proporcionado por Refactoring.guru, adaptándolo a nuestras necesidades específicas. Esto ha resultado en un diseño más flexible, extensible y mantenible para el sistema de notificaciones.