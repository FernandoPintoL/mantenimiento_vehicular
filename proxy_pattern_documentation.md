# Proxy Pattern Implementation Documentation

## Overview

This document explains the implementation of the Proxy design pattern in the MantenimientoVehicular application. The implementation follows the structure described in the pseudocode example from [refactoring.guru](https://refactoring.guru/es/design-patterns/proxy).

## Pattern Structure

The Proxy pattern provides a surrogate or placeholder for another object to control access to it. In our implementation:

1. **Interface (IMecanicoModel)**: Defines the contract that both the real service and its proxy must follow.
2. **Real Service (ModeloMecanico)**: The actual implementation that performs database operations.
3. **Proxy (MecanicoModelProxy)**: Controls access to the real service, adding validation and caching functionality.
4. **Client (NegocioMecanico)**: Uses the service through the interface, unaware of whether it's working with the real service or its proxy.

## Implementation Details

### Interface (IMecanicoModel)

The interface defines all operations that can be performed on mechanics data:

```java
public interface IMecanicoModel {
    // Database operations
    long agregar(ModeloMecanico modelo);
    int actualizar(ModeloMecanico model);
    int eliminar(int id);
    
    // Retrieval operations
    List<ModeloMecanico> obtenerTodos();
    ModeloMecanico obtenerPorId(int id);
    
    // Validation methods
    String getErrorMessage();
    
    // Getters and setters for model properties
    // ...
}
```

### Real Service (ModeloMecanico)

The real service implements the interface and performs actual database operations:

```java
public class ModeloMecanico implements IMecanicoModel {
    // Database connection fields
    private static DataBaseHelper dbHelper;
    private static SQLiteDatabase db;
    
    // Implementation of database operations
    public long agregar(ModeloMecanico modelo) {
        // Actual database insertion
    }
    
    public List<ModeloMecanico> obtenerTodos() {
        // Actual database query
    }
    
    // Other methods...
}
```

### Proxy (MecanicoModelProxy)

The proxy implements the same interface but adds validation and caching:

```java
public class MecanicoModelProxy implements IMecanicoModel {
    private ModeloMecanico realModelo;
    private String errorMessage = "";
    
    // Cache fields
    private List<ModeloMecanico> listCache;
    private ModeloMecanico videoCache;
    private int lastRequestedId = -1;
    private boolean needReset = false;
    
    // Implementation with validation and caching
    public long agregar(ModeloMecanico modelo) {
        if (!validarDatos(modelo)) {
            return -1;
        }
        needReset = true;
        return realModelo.agregar(modelo);
    }
    
    public List<ModeloMecanico> obtenerTodos() {
        if (listCache == null || needReset) {
            listCache = realModelo.obtenerTodos();
            needReset = false;
        }
        return listCache;
    }
    
    // Other methods...
}
```

### Client (NegocioMecanico)

The client uses the service through the interface:

```java
public class NegocioMecanico {
    private IMecanicoModel modelo;
    
    public NegocioMecanico(Context context) {
        modelo = new MecanicoModelProxy(context);
    }
    
    public List<Map<String,String>> cargarDatos() {
        List<ModeloMecanico> datos = modelo.obtenerTodos();
        // Process data...
    }
    
    // Other methods...
}
```

## Example Usage

The `ProxyExample` class demonstrates how to use the Proxy pattern:

```java
public class ProxyExample {
    public static void init(Context context) {
        // Create the real service
        ModeloMecanico realService = new ModeloMecanico(context);
        
        // Create the proxy
        MecanicoModelProxy proxy = new MecanicoModelProxy(context);
        
        // Create the business logic class
        NegocioMecanico negocio = new NegocioMecanico(context);
        
        // Use the business logic
        negocio.cargarDatos();
    }
}
```

## Benefits of the Implementation

1. **Validation**: The proxy validates data before passing it to the real service, preventing invalid data from reaching the database.
2. **Caching**: The proxy caches results from database queries, improving performance by reducing database access.
3. **Transparency**: Clients work with the same interface whether they're using the real service or its proxy.
4. **Control**: The proxy controls access to the real service, allowing for additional functionality like logging or access control.

## Comparison with Pseudocode Example

Our implementation follows the structure of the pseudocode example:

| Pseudocode | Our Implementation |
|------------|-------------------|
| ThirdPartyYouTubeLib (interface) | IMecanicoModel (interface) |
| ThirdPartyYouTubeClass (real service) | ModeloMecanico (real service) |
| CachedYouTubeClass (proxy) | MecanicoModelProxy (proxy) |
| YouTubeManager (client) | NegocioMecanico (client) |
| Application (setup) | ProxyExample (setup) |

The main difference is that our implementation includes validation in addition to caching, and it's adapted to work with an Android SQLite database.

## Conclusion

The Proxy pattern provides a flexible way to add functionality to existing classes without modifying their code. In our implementation, we've added validation and caching to database operations, improving both data integrity and performance.