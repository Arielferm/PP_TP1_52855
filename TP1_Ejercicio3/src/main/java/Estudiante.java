/*
 * Estudiante:
 * Clase simple (sin relaciones propias): solo guarda los datos básicos
 * de un estudiante. La usan Actividad e Inscripcion para saber "quién" se inscribió.
 */
// [EJERCICIO 1] Definición de la entidad concreta Estudiante
public class Estudiante {

    // [EJERCICIO 1] Atributos privados encapsulados
    private String legajo;
    private String nombre;

    public Estudiante(String legajo, String nombre) {
        this.legajo = legajo;
        // [EJERCICIO 1] Delegación en el setter para garantizar la validación al instanciar
        setNombre(nombre);
    }

    public String getLegajo() {
        return legajo;
    }

    public void setLegajo(String legajo) {
        this.legajo = legajo;
    }

    public String getNombre() {
        return nombre;
    }

    // [EJERCICIO 1] Encapsulamiento con control de integridad (evita valores nulos o vacíos)
    public void setNombre(String nombre) {
        if (nombre != null && !nombre.isBlank()) {
            this.nombre = nombre;
        }
    }
}