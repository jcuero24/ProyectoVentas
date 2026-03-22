/**
 * Representa un vendedor.
 */
public class Vendedor {

    String tipoDoc;
    String numDoc;
    String nombres;
    String apellidos;
    double ventasTotales = 0;

    public Vendedor(String tipoDoc, String numDoc, String nombres, String apellidos) {
        this.tipoDoc = tipoDoc;
        this.numDoc = numDoc;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }

    public String getNumDoc() {
        return numDoc;
    }
}