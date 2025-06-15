# Generador de Diagramas de Secuencia

Esta funcionalidad permite generar diagramas de secuencia a partir del código de la aplicación. Los diagramas se generan utilizando la sintaxis de PlantUML y se guardan como archivos .puml que pueden ser visualizados con herramientas compatibles con PlantUML.

## Características

- Generación de diagramas de secuencia para clases específicas
- Generación de diagramas para métodos específicos o para todos los métodos de una clase
- Configuración de la profundidad de análisis de las llamadas a métodos
- Generación de diagramas de flujo predefinidos para procesos comunes de la aplicación

## Cómo usar

1. Desde la pantalla principal, haga clic en el botón "Generar Diagramas de Secuencia"
2. En la pantalla de generación de diagramas, tiene dos opciones:

### Generar diagrama para una clase específica:
   - Seleccione una clase de la lista desplegable
   - Opcionalmente, seleccione un método específico o deje "Todos los métodos"
   - Ajuste la profundidad de análisis con el control deslizante
   - Haga clic en "Generar Diagrama de Secuencia"

### Generar diagrama para un flujo predefinido:
   - Seleccione un flujo de la lista desplegable
   - Haga clic en "Generar Diagrama de Flujo"

## Ubicación de los diagramas generados

Los diagramas se guardan en el directorio externo de la aplicación, en la carpeta "diagramas". La ruta completa se muestra en la pantalla después de generar el diagrama.

## Visualización de los diagramas

Para visualizar los diagramas generados, puede utilizar:

1. **Herramientas en línea**:
   - [PlantUML Web Server](http://www.plantuml.com/plantuml/uml/)
   - [PlantText](https://www.planttext.com/)

2. **Aplicaciones de escritorio**:
   - PlantUML para Visual Studio Code
   - PlantUML para IntelliJ IDEA
   - PlantUML Viewer

3. **Aplicaciones móviles**:
   - PlantUML Viewer para Android

## Ejemplo de diagrama generado

```
@startuml
title Diagrama de Secuencia: VistaMantenimiento

actor Usuario
participant "VistaMantenimiento" as VistaMantenimiento
participant "MantenimientoDetController" as MantenimientoDetController
participant "NegocioMantenimiento" as NegocioMantenimiento

Usuario -> VistaMantenimiento: inicia
VistaMantenimiento -> MantenimientoDetController: obtenerMantenimientos()
MantenimientoDetController -> NegocioMantenimiento: listarMantenimientos()
NegocioMantenimiento --> MantenimientoDetController: return
MantenimientoDetController --> VistaMantenimiento: return

@enduml
```

## Limitaciones

- La generación de diagramas se basa en análisis estático del código y reflection, por lo que puede no capturar todas las interacciones dinámicas.
- Los diagramas generados son aproximaciones y pueden requerir ajustes manuales para representar con precisión el flujo real de la aplicación.
- La profundidad de análisis está limitada para evitar diagramas excesivamente complejos.

## Notas técnicas

Esta funcionalidad utiliza:
- Java Reflection API para analizar la estructura de clases
- PlantUML para la sintaxis de los diagramas
- Almacenamiento externo de Android para guardar los archivos generados