import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/*
 * Actividad (Ejercicio 3)
 * Clase abstracta que centraliza atributos y comportamiento común.
 * No se instancia directamente.
 * Las subclases (Charla, Taller) implementan calcularCostoMateriales() y getTipo().
 * mostrarIdentificacion() es final para mantener un formato único, pero usa getTipo() de forma polimórfica.
 * Esto implementa Template Method.
 */
// [EJERCICIO 3] Clase abstracta base: define la interfaz común pero no permite instanciación directa
public abstract class Actividad {

    // [EJERCICIO 1] Visibilidad protected para permitir acceso directo únicamente a subclases (Charla/Taller)
    protected int id;
    protected String titulo;
    protected int cupoMaximo;

    // [EJERCICIO 2] Colección privada para encapsular los objetos de la relación con Inscripcion
    private List<Inscripcion> inscripciones;

    // [EJERCICIO 1] Constante estática compartida por todas las instancias de la clase
    public static final int CUPO_MINIMO;

    // [EJERCICIO 1] Bloque de inicialización estático para cargar constantes del sistema
    static {
        CUPO_MINIMO = 5;
        System.out.println("Inicializador estático: se cargó la clase Actividad.");
    }

    public Actividad(int id, String titulo, int cupoMaximo) {
        this.id = id;
        this.titulo = titulo;
        // [EJERCICIO 1] Lógica de validación de estado en el constructor
        this.cupoMaximo = (cupoMaximo > CUPO_MINIMO) ? cupoMaximo : CUPO_MINIMO;
        // [EJERCICIO 2] Inicialización de la estructura de datos contenedora de referencias
        this.inscripciones = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    // [EJERCICIO 1] Control de encapsulamiento mediante validación de datos
    public void setTitulo(String titulo) {
        if (titulo != null && !titulo.isBlank()) {
            this.titulo = titulo;
        }
    }

    public int getCupoMaximo() {
        return cupoMaximo;
    }

    public void setCupoMaximo(int cupoMaximo) {
        this.cupoMaximo = (cupoMaximo > CUPO_MINIMO) ? cupoMaximo : CUPO_MINIMO;
    }

    // [EJERCICIO 2] Metodo asociativo: vincula esta Actividad (this) con un Estudiante creando una Inscripcion
    public Inscripcion inscribir(Estudiante estudiante) {
        if (inscripciones.size() >= cupoMaximo) {
            System.out.println("  No se pudo inscribir a " + estudiante.getNombre()
                    + ": la actividad \"" + titulo + "\" ya alcanzó su cupo máximo (" + cupoMaximo + ").");
            return null;
        }
        // [EJERCICIO 2] Instanciación de la Clase Asociación
        Inscripcion inscripcion = new Inscripcion(this, estudiante, LocalDate.now(), "REGISTRADA");
        inscripciones.add(inscripcion);
        return inscripcion;
    }

    public List<Inscripcion> getInscripciones() {
        return inscripciones;
    }

    // [EJERCICIO 2] Navegabilidad: recorre la colección de inscripciones para acceder a los datos del estudiante
    public void mostrarInscripciones() {
        if (inscripciones.isEmpty()) {
            System.out.println("     Sin inscripciones registradas.");
            return;
        }
        System.out.println("     Inscripciones registradas:");
        for (Inscripcion inscripcion : inscripciones) {
            System.out.println("       - " + inscripcion.getEstudiante().getNombre()
                    + " (legajo " + inscripcion.getEstudiante().getLegajo() + ") | "
                    + inscripcion.getEstado() + " | " + inscripcion.getFecha());
        }
    }

    /**
     * Metodo final: define el ÚNICO formato válido para identificar una actividad.
     * No puede ser redefinido por Charla ni por Taller, pero SÍ se apoya en el
     * metodo polimórfico getTipo() para que el contenido cambie según la subclase.
     */
    // [EJERCICIO 3] Método final (inmutable): aplica Template Method apoyándose en la llamada polimórfica getTipo()
    public final void mostrarIdentificacion() {
        System.out.println("   [" + getTipo() + "] " + titulo + " (id=" + id + ")");
    }

    /** Cada subclase concreta calcula su propio costo de materiales. */
    // [EJERCICIO 3] Contrato abstracto: obliga a las subclases a proveer la lógica concreta de cálculo
    public abstract double calcularCostoMateriales();

    /** Cada subclase concreta dice qué tipo de actividad es. */
    // [EJERCICIO 3] Contrato abstracto: metodo polimórfico para obtener la identificación específica de la subclase
    public abstract String getTipo();
}
