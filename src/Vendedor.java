/**
 * Representa un vendedor del sistema.
 */
public class Vendedor {

    String tipoDoc;
    String numDoc;
    String nombres;
    String apellidos;
    double ventasTotales = 0.0;

    /**
     * Constructor de Vendedor
     */
    public Vendedor(String tipoDoc, String numDoc, String nombres, String apellidos) {
        this.tipoDoc = tipoDoc;
        this.numDoc = numDoc;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }

    /**
     * Retorna el número de documento del vendedor
     */
    public String getNumDoc() {
        return numDoc;
    }
}