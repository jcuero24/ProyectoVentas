/**
 * Representa un producto del sistema.
 */
public class Producto {

    String id;
    String nombre;
    double precio;
    int cantidadVendida = 0;

    /**
     * Constructor de Producto
     */
    public Producto(String id, String nombre, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    /**
     * Retorna el ID del producto
     */
    public String getId() {
        return id;
    }
}