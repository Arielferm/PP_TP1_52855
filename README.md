# TP1 – Programación Orientada a Objetos en Java
**Paradigmas de Programación · UTN FRM**

Sistema de gestión de eventos universitarios. El proyecto está resuelto de forma
**incremental**, esta carpeta
contiene el código completo y ejecutable de ese punto del TP, agregando sobre sobre los anteriores ejercicios (Ejercicio 1 y 2)


## Modelo de clases (estado final, Ejercicio 3 y 4)

```
Estudiante            Sala
 - legajo               - id
 - nombre               - nombre

EventoUniversitario "1" ──agrega──> "1" Sala                (AGREGACIÓN)
EventoUniversitario "1" ──compone─> "1..*" Actividad (abstracta)   (COMPOSICIÓN)
Actividad "1" ──compone──> "0..*" Inscripcion                (COMPOSICIÓN)
Inscripcion "*" ──asocia──> "1" Estudiante                    (ASOCIACIÓN)

Actividad (abstracta)
   ├── Charla   (disertante; costo materiales = 0)
   └── Taller   (requiereNotebook; costo materiales = 5000 o 2000)
```

**¿Por qué cada relación es lo que es?**
- **Agregación (Evento–Sala):** la sala existe independientemente del evento. Si el evento
  se cancela, la sala sigue estando disponible para otro evento.
- **Composición (Evento–Actividad, Actividad–Inscripcion):** son relaciones de "todo/parte"
  fuertes. Una actividad no tiene sentido sin el evento al que pertenece, y una inscripción
  no existe si no es la inscripción de alguien a una actividad concreta.
- **Asociación (Inscripcion–Estudiante):** el estudiante existe de forma totalmente
  independiente de la inscripción (antes, durante y después de anotarse).
- **Herencia (Actividad–Charla/Taller):** Charla y Taller comparten datos y comportamiento
  (id, título, cupo, inscribir, mostrarInscripciones) pero difieren en cómo calculan su costo
  y cómo se identifican, así que ese comportamiento variable queda en manos de cada subclase
  (polimorfismo).

## Cómo se ejecuta cada versión

Desde la carpeta de cada ejercicio (necesitás Maven instalado; si no lo tenés, alcanza con
compilar/ejecutar a mano con `javac`/`java`, ver más abajo):

```bash
mvn compile exec:java
```

Sin Maven, usando el JDK directamente:

```bash
mkdir out
javac -encoding UTF-8 -d out src/main/java/*.java
java -cp out App
```

## Qué hace cada ejercicio (resumen)

- **Ejercicio 1:** crea eventos, los copia con el constructor de copia, muestra sus datos
  y el contador estático de eventos creados.
- **Ejercicio 2:** agrega estudiantes, salas y actividades; inscribe estudiantes en
  actividades y muestra el resumen completo de cada evento.
- **Ejercicio 3:** las actividades ahora son `Charla` o `Taller` (herencia). El costo del
  evento se recalcula sumando el costo de materiales de cada actividad. Se recorre la
  lista de actividades llamando a `mostrarIdentificacion()` de forma **polimórfica**.
- **Ejercicio 4:** mismo programa que el Ejercicio 3, ejecutado con un escenario puntual
  (3 estudiantes, 1 evento, 1 sala, 1 Charla + 1 Taller, 2 inscriptos en cada una) para
  poder dibujar el **mapa de memoria** de esa ejecución (`TP1_Ejercicio4_mapa_memoria.png`).


