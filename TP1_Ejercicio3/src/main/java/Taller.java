/*
 * Taller:
 * Tipo concreto de Actividad. Según la consigna, el costo de materiales de
 * un taller depende de si requiere notebook o no: $5000 si la requiere,
 * $2000 si no la requiere.
 */
// [EJERCICIO 3] Herencia: Taller extiende de Actividad, convirtiéndose en una subclase concreta
public class Taller extends Actividad {

    // [EJERCICIO 1] Atributo privado específico encapsulado
    private boolean requiereNotebook;

    public Taller(int id, String titulo, int cupoMaximo, boolean requiereNotebook) {
        // [EJERCICIO 2 y 3] Invocación al constructor de la superclase para inicializar la parte heredada
        super(id, titulo, cupoMaximo);
        // [EJERCICIO 1] Inicialización del atributo propio de esta subclase
        this.requiereNotebook = requiereNotebook;
    }

    // [EJERCICIO 1] Getter con convención 'is' para valores booleanos
    public boolean isRequiereNotebook() {
        return requiereNotebook;
    }

    public void setRequiereNotebook(boolean requiereNotebook) {
        this.requiereNotebook = requiereNotebook;
    }

    // [EJERCICIO 3] Sobrescritura polimórfica: calcula el costo dinámicamente según 'requiereNotebook'
    @Override
    public double calcularCostoMateriales() {
        return requiereNotebook ? 5000 : 2000;
    }

    // [EJERCICIO 3] Sobrescritura polimórfica: retorna el identificador textual de este tipo concreto
    @Override
    public String getTipo() {
        return "Taller";
    }
}