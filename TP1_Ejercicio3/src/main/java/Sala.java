/*
 * Sala:
 * Representa el lugar físico donde se dicta un evento.
 * Es importante: una Sala EXISTE por sí misma, con o sin eventos asignados
 * (por eso la relación con EventoUniversitario es de AGREGACIÓN y no de composición).
 */
// [EJERCICIO 1] Clase concreta que representa un espacio físico del dominio
public class Sala {

    // [EJERCICIO 1] Atributos privados encapsulados
    private int id;
    private String nombre;

    public Sala(int id, String nombre) {
        this.id = id;
        // [EJERCICIO 1] Delegación en el setter para aplicar la lógica de validación
        setNombre(nombre);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    // [EJERCICIO 1] Control de encapsulamiento para evitar cadenas nulas o vacías
    public void setNombre(String nombre) {
        if (nombre != null && !nombre.isBlank()) {
            this.nombre = nombre;
        }
    }
}