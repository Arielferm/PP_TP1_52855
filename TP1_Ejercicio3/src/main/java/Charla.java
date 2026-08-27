/*
 * Charla:
 * Tipo concreto de Actividad. Según la consigna, las charlas son gratuitas
 * (no generan costo de materiales) y agregan un dato propio: quién la dicta.
 */
// [EJERCICIO 3] Especialización mediante herencia (extends)
public class Charla extends Actividad {

    // [EJERCICIO 1] Atributo específico encapsulado privadamente
    private String disertante;

    public Charla(int id, String titulo, int cupoMaximo, String disertante) {
        // [EJERCICIO 2 y 3] Reutilización del constructor de la superclase abstracta
        super(id, titulo, cupoMaximo);
        // [EJERCICIO 1] Asignación del atributo propio de la subclase
        this.disertante = disertante;
    }

    public String getDisertante() {
        return disertante;
    }

    // [EJERCICIO 1] Control de encapsulamiento con validación de cadenas
    public void setDisertante(String disertante) {
        if (disertante != null && !disertante.isBlank()) {
            this.disertante = disertante;
        }
    }

    // [EJERCICIO 3] Implementación polimórfica: define costo 0 para esta clase concreta
    @Override
    public double calcularCostoMateriales() {
        return 0; // Las charlas no tienen costo de materiales
    }

    // [EJERCICIO 3] Implementación polimórfica: identifica el tipo concreto para la vista
    @Override
    public String getTipo() {
        return "Charla";
    }
}