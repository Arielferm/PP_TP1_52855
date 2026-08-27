import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * App (Ejercicio 3)
 *  Al crear una actividad, se elige el tipo (Charla o Taller) y
 * se solicita su dato específico. Luego de crearla con crearActividad(id, titulo, cupo, tipo),
 * se completa mediante los setters de la subclase correspondiente.
 *
 * Se agrega Charla y Taller:
 *   a) se registran estudiantes
 *   b) se construyen eventos
 *   c) se asigna una sala a cada evento
 *   d) se crean actividades de tipo Charla y/o Taller para cada evento
 *   e) se inscriben estudiantes en cada actividad
 *   f) se muestra el resumen de cada evento y se recorren sus actividades mostrando la identificación de forma POLIMÓRFICA
 *   g) se muestra el total de eventos creados
 *
 * [EJERCICIO 1] Fundamentos de POO: Encapsulamiento de atributos booleanos de estado,
 *               constructores parametrizados y convenciones de acceso (getter tipo 'is').
 * [EJERCICIO 2] Relaciones entre Objetos: Propagación de parámetros al constructor
 *               de la superclase abstracta para mantener las colecciones asociadas.
 * [EJERCICIO 3] Herencia y Polimorfismo: Extensión de clase abstracta (extends),
 *               sobrescritura obligatoria (@Override) e implementación de lógica condicional polimórfica.
 *
 */

public class App {

    // [EJERCICIO 1] Metodo estático auxiliar para procesamiento de lógica interna de la clase
    private static boolean esRespuestaAfirmativa(String respuesta) {
        respuesta = respuesta.trim().toLowerCase();
        return respuesta.equals("s") || respuesta.equals("si") || respuesta.equals("sí");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // a) se registran estudiantes
        System.out.println("=== REGISTRO DE ESTUDIANTES ===");
        // [EJERCICIO 2] Gestión de colecciones para almacenar objetos del modelo
        List<Estudiante> estudiantes = new ArrayList<>();
        boolean seguirCreandoEstudiantes = true;
        while (seguirCreandoEstudiantes) {
            System.out.print("Ingrese legajo del estudiante: ");
            String legajo = scanner.nextLine();
            System.out.print("Ingrese nombre y apellido del estudiante: ");
            String nombre = scanner.nextLine();
            // [EJERCICIO 1] Instanciación básica del objeto de dominio con sus atributos de estado
            estudiantes.add(new Estudiante(legajo, nombre));

            System.out.print("¿Desea cargar otro estudiante? (s/n): ");
            seguirCreandoEstudiantes = esRespuestaAfirmativa(scanner.nextLine());
        }

        // b) a g)
        System.out.println("\n=== REGISTRO DE EVENTOS ===");
        List<EventoUniversitario> eventosCreados = new ArrayList<>();
        int idEvento = 1;
        boolean seguirCreandoEventos = true;
        while (seguirCreandoEventos) {

            System.out.print("\nIngrese el título del evento: ");
            String titulo = scanner.nextLine();
            System.out.print("Ingrese el costo base: ");
            double costoBase = Double.parseDouble(scanner.nextLine());
            System.out.print("¿El evento tiene costo para los participantes? (s/n): ");
            boolean gratuito = !esRespuestaAfirmativa(scanner.nextLine());

            // [EJERCICIO 1] Instanciación del objeto principal contenedor de la lógica
            EventoUniversitario evento = new EventoUniversitario("EVT-" + idEvento, titulo, costoBase, gratuito);
            eventosCreados.add(evento);

            // c) asignación de sala
            System.out.print("Ingrese el nombre de la sala donde se realizará el evento: ");
            String nombreSala = scanner.nextLine();
            // [EJERCICIO 2] Materialización de la relación de Agregación entre EventoUniversitario y Sala
            evento.asignarSala(new Sala(idEvento, nombreSala));

            // d) creación de actividades (Charla o Taller)
            System.out.println("-- Actividades del evento \"" + evento.getTitulo() + "\" --");
            int idActividad = 1;
            boolean seguirCreandoActividades = true;
            while (seguirCreandoActividades) {
                System.out.print("Ingrese el título de la actividad: ");
                String tituloActividad = scanner.nextLine();
                System.out.print("Ingrese el cupo máximo de estudiantes admitidos: ");
                int cupo = Integer.parseInt(scanner.nextLine());

                String tipo;
                do {
                    System.out.print("¿Qué tipo de actividad es? (Charla/Taller): ");
                    tipo = scanner.nextLine().trim();
                } while (!tipo.equalsIgnoreCase("Charla") && !tipo.equalsIgnoreCase("Taller"));

                // [EJERCICIO 3] Invocación de la fábrica para instanciar polimórficamente subclases de Actividad
                evento.crearActividad(idActividad, tituloActividad, cupo, tipo);

                // Se completa el dato propio de la subclase recién creada.
                Actividad actividadCreada = evento.getActividades().get(evento.getActividades().size() - 1);

                // [EJERCICIO 3] Pattern Matching con instanceof: evaluación de tipo en tiempo de ejecución
                // para acceder de forma segura a miembros exclusivos de Charla o Taller
                if (actividadCreada instanceof Charla charla) {
                    System.out.print("Ingrese el nombre del disertante: ");
                    charla.setDisertante(scanner.nextLine());
                } else if (actividadCreada instanceof Taller taller) {
                    System.out.print("¿El taller requiere notebook? (s/n): ");
                    taller.setRequiereNotebook(esRespuestaAfirmativa(scanner.nextLine()));
                }

                System.out.print("¿Desea cargar otra actividad para este evento? (s/n): ");
                seguirCreandoActividades = esRespuestaAfirmativa(scanner.nextLine());
                idActividad++;
            }

            // e) inscripción de estudiantes en actividades
            System.out.println("-- Inscripciones para el evento \"" + evento.getTitulo() + "\" --");
            boolean seguirInscribiendo = true;
            while (seguirInscribiendo) {
                System.out.print("Ingrese legajo del estudiante a inscribir: ");
                String legajo = scanner.nextLine();
                System.out.print("Ingrese id de la actividad (según el orden en que la cargó, empezando en 1): ");
                int idActividadElegida = Integer.parseInt(scanner.nextLine());

                Estudiante estudianteEncontrado = null;
                // [EJERCICIO 2] Búsqueda y navegación sobre colecciones asociadas
                for (Estudiante estudiante : estudiantes) {
                    if (estudiante.getLegajo().equals(legajo)) {
                        estudianteEncontrado = estudiante;
                        break;
                    }
                }
                if (estudianteEncontrado == null) {
                    System.out.println("  No existe ningún estudiante con ese legajo.");
                } else {
                    // [EJERCICIO 2] Delegación de la inscripción para establecer la relación entre entidades
                    evento.getActividades().get(idActividadElegida - 1).inscribir(estudianteEncontrado);
                }

                System.out.print("¿Desea generar otra inscripción? (s/n): ");
                seguirInscribiendo = esRespuestaAfirmativa(scanner.nextLine());
            }

            // f) resumen del evento recién cargado
            System.out.println("\n-- Resumen del evento cargado --");
            // [EJERCICIO 2 y 3] Invocación de visualización que delega en cómputos polimórficos e impresiones
            evento.mostrarDatos();

            System.out.print("\n¿Desea cargar otro evento? (s/n): ");
            seguirCreandoEventos = esRespuestaAfirmativa(scanner.nextLine());
            idEvento++;
        }

        // f) recorrido polimórfico explícito de todas las actividades de todos los eventos
        System.out.println("\n=== RECORRIDO POLIMÓRFICO DE ACTIVIDADES ===");
        System.out.println("(el mismo bucle llama a mostrarIdentificacion() sin importar si es Charla o Taller)\n");
        // [EJERCICIO 3] Recorrido Polimórfico: Tratamiento homogéneo de elementos mediante
        // la interfaz/superclase abstracta 'Actividad', resolviendo en tiempo de ejecución
        for (EventoUniversitario evento : eventosCreados) {
            for (Actividad actividad : evento.getActividades()) {
                actividad.mostrarIdentificacion();
            }
        }

        // g) total de eventos creados
        System.out.println("\n=== TOTAL DE EVENTOS CREADOS ===");
        // [EJERCICIO 1] Acceso directo al metodo estático que devuelve el contador global de la clase
        System.out.println("TOTAL DE EVENTOS CREADOS: " + EventoUniversitario.getCantidadEventos());

        scanner.close();
    }
}