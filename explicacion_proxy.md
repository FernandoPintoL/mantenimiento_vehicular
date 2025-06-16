# Implementación del Patrón Proxy

## Comparación con el Pseudocódigo

El pseudocódigo de [refactoring.guru](https://refactoring.guru/es/design-patterns/proxy) describe el patrón Proxy de la siguiente manera:

```
// La interfaz de un servicio remoto.
interface ThirdPartyYouTubeLib is
    method listVideos()
    method getVideoInfo(id)
    method downloadVideo(id)

// La implementación concreta de un conector de servicio.
class ThirdPartyYouTubeClass implements ThirdPartyYouTubeLib is
    method listVideos() is
        // Envía una solicitud API a YouTube.

    method getVideoInfo(id) is
        // Obtiene metadatos de algún video.

    method downloadVideo(id) is
        // Descarga un archivo de video de YouTube.

// Proxy que implementa la misma interfaz y añade caché
class CachedYouTubeClass implements ThirdPartyYouTubeLib is
    private field service: ThirdPartyYouTubeLib
    private field listCache, videoCache
    field needReset

    constructor CachedYouTubeClass(service: ThirdPartyYouTubeLib) is
        this.service = service

    method listVideos() is
        if (listCache == null || needReset)
            listCache = service.listVideos()
        return listCache

    method getVideoInfo(id) is
        if (videoCache == null || needReset)
            videoCache = service.getVideoInfo(id)
        return videoCache

    method downloadVideo(id) is
        if (!downloadExists(id) || needReset)
            service.downloadVideo(id)
```

## Nuestra Implementación

Hemos implementado el patrón Proxy en nuestra aplicación de la siguiente manera:

### 1. Interfaz (IMecanicoModel)

```java
public interface IMecanicoModel {
    // Operaciones de base de datos
    long agregar(ModeloMecanico modelo);
    int actualizar(ModeloMecanico model);
    int eliminar(int id);
    
    // Operaciones de consulta
    List<ModeloMecanico> obtenerTodos();
    ModeloMecanico obtenerPorId(int id);
    
    // Métodos de validación
    String getErrorMessage();
    
    // Getters y setters
    // ...
}
```

### 2. Servicio Real (ModeloMecanico)

```java
public class ModeloMecanico implements IMecanicoModel {
    // Implementación real que accede a la base de datos
    
    public List<ModeloMecanico> obtenerTodos() {
        // Consulta real a la base de datos
        // ...
    }
    
    public ModeloMecanico obtenerPorId(int id) {
        // Consulta real a la base de datos
        // ...
    }
    
    // Otros métodos...
}
```

### 3. Proxy (MecanicoModelProxy)

```java
public class MecanicoModelProxy implements IMecanicoModel {
    private ModeloMecanico realModelo;
    private String errorMessage = "";
    
    // Campos para caché
    private List<ModeloMecanico> listCache;
    private ModeloMecanico videoCache;
    private int lastRequestedId = -1;
    private boolean needReset = false;
    
    // Constructor
    public MecanicoModelProxy(Context context) {
        this.realModelo = new ModeloMecanico(context);
    }
    
    // Implementación con validación y caché
    public List<ModeloMecanico> obtenerTodos() {
        if (listCache == null || needReset) {
            listCache = realModelo.obtenerTodos();
            needReset = false;
        }
        return listCache;
    }
    
    public ModeloMecanico obtenerPorId(int id) {
        if (videoCache == null || lastRequestedId != id || needReset) {
            videoCache = realModelo.obtenerPorId(id);
            lastRequestedId = id;
            needReset = false;
        }
        return videoCache;
    }
    
    // Métodos que invalidan la caché
    public long agregar(ModeloMecanico modelo) {
        if (!validarDatos(modelo)) {
            return -1;
        }
        needReset = true;
        return realModelo.agregar(modelo);
    }
    
    // Otros métodos...
}
```

### 4. Cliente (NegocioMecanico)

```java
public class NegocioMecanico {
    private IMecanicoModel modelo;
    
    public NegocioMecanico(Context context) {
        modelo = new MecanicoModelProxy(context);
    }
    
    // Métodos que utilizan el proxy
    public List<Map<String,String>> cargarDatos() {
        List<ModeloMecanico> datos = modelo.obtenerTodos();
        // Procesar datos...
    }
    
    // Otros métodos...
}
```

## Correspondencia con el Pseudocódigo

| Pseudocódigo | Nuestra Implementación |
|--------------|------------------------|
| ThirdPartyYouTubeLib | IMecanicoModel |
| ThirdPartyYouTubeClass | ModeloMecanico |
| CachedYouTubeClass | MecanicoModelProxy |
| listVideos() | obtenerTodos() |
| getVideoInfo(id) | obtenerPorId(id) |
| downloadVideo(id) | agregar(modelo) / actualizar(modelo) |
| listCache | listCache |
| videoCache | videoCache |
| needReset | needReset |

## Mejoras Adicionales

Nuestra implementación incluye algunas mejoras sobre el pseudocódigo original:

1. **Validación de datos**: El proxy valida los datos antes de pasarlos al servicio real.
2. **Registro de errores**: Se registran mensajes de error detallados.
3. **Seguimiento de caché**: Se utiliza logging para mostrar cuándo se usa la caché y cuándo se actualiza.
4. **Reseteo explícito**: Se proporciona un método `resetCache()` para forzar la actualización de la caché.

## Ejemplo de Uso

```java
public class ProxyExample {
    public static void init(Context context) {
        // Crear el servicio real
        ModeloMecanico realService = new ModeloMecanico(context);
        
        // Crear el proxy
        MecanicoModelProxy proxy = new MecanicoModelProxy(context);
        
        // Crear la lógica de negocio
        NegocioMecanico negocio = new NegocioMecanico(context);
        
        // Usar la lógica de negocio
        negocio.cargarDatos();
    }
}
```

Esta implementación sigue fielmente el patrón Proxy descrito en el pseudocódigo, adaptándolo a las necesidades específicas de nuestra aplicación de mantenimiento vehicular.