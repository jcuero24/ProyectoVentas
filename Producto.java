/**
 * Representa un producto.
 */
public class Producto {

    String id;
    String nombre;
    double precio;
    int cantidadVendida = 0;

    public Producto(String id, String nombre, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getId() {
        return id;
    }
}