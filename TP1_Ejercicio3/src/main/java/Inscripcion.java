import java.time.LocalDate;

/*
 * Inscripcion:
 * Es la "clase asociativa" entre Actividad y Estudiante: existe porque UN estudiante
 * se anotó en UNA actividad concreta, en UNA fecha concreta y con UN estado concreto.
 * Esos tres datos (fecha, estado, y el "para quién"/"en qué") no son propios ni de
 * Actividad ni de Estudiante por separado, así que necesitan su propia clase.
 */
// [EJERCICIO 2] Clase Asociación que actúa como nexo entre Actividad y Estudiante
public class Inscripcion {

    // [EJERCICIO 3] Referencia polimórfica al tipo abstracto base (soporta instancias de Charla o Taller)
    private Actividad actividad;

    // [EJERCICIO 2] Referencia directa al objeto estudiante inscripto
    private Estudiante estudiante;

    // [EJERCICIO 1] Atributos propios de estado y auditoría encapsulados
    private LocalDate fecha;
    private String estado;

    public Inscripcion(Actividad actividad, Estudiante estudiante, LocalDate fecha, String estado) {
        // [EJERCICIO 2] Enlace en memoria RAM de los dos extremos de la relación
        this.actividad = actividad;
        this.estudiante = estudiante;
        // [EJERCICIO 1] Inicialización de atributos locales del registro de inscripción
        this.fecha = fecha;
        this.estado = estado;
    }

    // [EJERCICIO 2] Métodos de navegación para obtener las entidades participantes de la relación
    public Actividad getActividad() {
        return actividad;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getEstado() {
        return estado;
    }

    // [EJERCICIO 1] Método mutador de regla de negocio que modifica el estado interno
    public void confirmar() {
        this.estado = "CONFIRMADA";
    }
}