import java.util.ArrayList;
import java.util.List;

/*
 * EventoUniversitario (Ejercicio 3)
 * crearActividad() recibe el tipo y crea automáticamente la subclase correspondiente (Charla o Taller),
 * funcionando como una fábrica simple.
 * calcularCostoEstimado() ahora incluye el costo de materiales antes de aplicar el 21% de impuestos.
 */

public class EventoUniversitario {
    // [EJERCICIO 1] Atributo inmutable 'final': se asigna en la construcción y no puede cambiar
    private final String id;
    private String titulo;
    private double costoBase;
    private boolean gratuito;

    // [EJERCICIO 2] Agregación: 'sala' es una referencia a un objeto independiente que puede o no existir
    private Sala sala;

    // [EJERCICIO 2] Composición: lista que gestiona y destruye las actividades asociadas al ciclo de vida del evento
    private List<Actividad> actividades;

    // [EJERCICIO 1] Atributo estático: variable de clase compartida por todas las instancias
    private static int cantidadEventos;

    // [EJERCICIO 1] Bloque de inicialización estático para cargar variables globales de la clase
    static {
        cantidadEventos = 0;
        System.out.println("Inicializador estático: se cargó la clase EventoUniversitario.");
    }

    public EventoUniversitario(String id, String titulo, double costoBase, boolean gratuito) {
        this.id = id;
        setTitulo(titulo);
        this.gratuito = gratuito;
        this.costoBase = gratuito ? 0 : costoBase;
        // [EJERCICIO 2] Inicialización de la colección para soporte de la composición
        this.actividades = new ArrayList<>();
        // [EJERCICIO 1] Incremento del contador estático
        cantidadEventos++;
    }

    // [EJERCICIO 1] Sobrecarga de constructores: Constructor de copia que delega en el principal mediante 'this(...)'
    public EventoUniversitario(EventoUniversitario otro) {
        this(otro.id + "-COPIA", otro.titulo, otro.costoBase, otro.gratuito);
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    // [EJERCICIO 1] Control de encapsulamiento con validación de estado
    public void setTitulo(String titulo) {
        if (titulo != null && !titulo.isBlank()) {
            this.titulo = titulo;
        }
    }

    public double getCostoBase() {
        return costoBase;
    }

    public boolean isGratuito() {
        return gratuito;
    }

    /**
     * Si el evento es gratuito, el costo total sigue siendo 0.
     * Si no, el costo total es (costoBase + costo de materiales de cada actividad) * 1.21.
     */
    public double calcularCostoEstimado() {
        if (gratuito) {
            return 0;
        }
        double costoActividades = 0;
        // [EJERCICIO 3] Tratamiento polimórfico: llama a calcularCostoMateriales() en el bucle
        // sin importar si cada elemento es una 'Charla' o un 'Taller'
        for (Actividad actividad : actividades) {
            costoActividades += actividad.calcularCostoMateriales();
        }
        return (costoBase + costoActividades) * 1.21;
    }

    public Sala getSala() {
        return sala;
    }

    // [EJERCICIO 2] Método que concreta la relación de Agregación asignando la Sala
    public void asignarSala(Sala sala) {
        this.sala = sala;
    }

    /**
     * crearActividad() crea y agrega una Charla o Taller según el tipo indicado.
     * Como no recibe los datos específicos de cada subclase, los inicializa con valores por defecto,
     * que luego pueden modificarse mediante sus setters.
     */
    // [EJERCICIO 3] Patrón de fábrica simple: crea instancias de las subclases concretas
    // ('Taller' o 'Charla') y las guarda referenciadas como la superclase abstracta 'Actividad'
    public void crearActividad(int id, String titulo, int cupo, String tipoActividad) {
        Actividad actividad;
        if ("Taller".equalsIgnoreCase(tipoActividad)) {
            actividad = new Taller(id, titulo, cupo, true); // por defecto, se asume que requiere notebook
        } else if ("Charla".equalsIgnoreCase(tipoActividad)) {
            actividad = new Charla(id, titulo, cupo, "A confirmar");
        } else {
            throw new IllegalArgumentException("Tipo de actividad no soportado: " + tipoActividad);
        }
        this.actividades.add(actividad);
    }

    public List<Actividad> getActividades() {
        return actividades;
    }

    public void mostrarDatos() {
        System.out.println("=====================================================");
        System.out.println("Evento id      : " + id);
        System.out.println("Título         : " + titulo);
        System.out.println("¿Es gratuito?  : " + (gratuito ? "Sí" : "No"));
        System.out.printf("Costo estimado : $ %.2f%n", calcularCostoEstimado());
        // [EJERCICIO 2] Navegación entre objetos: accede a la propiedad del objeto agregado 'Sala'
        System.out.println("Sala asignada  : " + (sala != null ? sala.getNombre() : "Sin sala asignada"));
        System.out.println("Actividades:");
        if (actividades.isEmpty()) {
            System.out.println("   (todavía no tiene actividades cargadas)");
        }
        for (Actividad actividad : actividades) {
            // [EJERCICIO 3] Invocaciones polimórficas: se resuelven dinámicamente según la clase real
            actividad.mostrarIdentificacion();
            System.out.printf("      Cupo máximo: %d | Costo materiales: $ %.2f%n",
                    actividad.getCupoMaximo(), actividad.calcularCostoMateriales());
            // [EJERCICIO 2] Delegación del recorrido de asociaciones a la clase Actividad
            actividad.mostrarInscripciones();
        }
        System.out.println("=====================================================");
    }

    // [EJERCICIO 1] Getter estático para consultar variables globales del sistema
    public static int getCantidadEventos() {
        return cantidadEventos;
    }
}