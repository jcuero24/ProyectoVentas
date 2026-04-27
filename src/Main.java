import java.io.*;
import java.nio.file.Path;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        try {

            // Cargar productos
            Map<String, Producto> productos = LecturaDatos.cargarDatos(
                    "productos.csv",
                    linea -> {
                        String[] p = linea.split(";");
                        return new Producto(p[0], p[1], Double.parseDouble(p[2]));
                    },
                    Producto::getId
            );

            // Cargar vendedores
            Map<String, Vendedor> vendedores = LecturaDatos.cargarDatos(
                    "vendedores.csv",
                    linea -> {
                        String[] v = linea.split(";");
                        return new Vendedor(v[0], v[1], v[2], v[3]);
                    },
                    Vendedor::getNumDoc
            );

            // Procesar ventas
            for (Path archivo : LecturaDatos.obtenerArchivosVentas()) {

                try (BufferedReader br = new BufferedReader(new FileReader(archivo.toFile()))) {

                    // Primera línea: vendedor
                    String primeraLinea = br.readLine();

                    if (primeraLinea == null) continue;

                    String[] datosVendedor = primeraLinea.split(";");

                    if (datosVendedor.length < 2) {
                        System.out.println("Formato incorrecto en archivo: " + archivo);
                        continue;
                    }

                    String numDoc = datosVendedor[1];
                    Vendedor vendedor = vendedores.get(numDoc);

                    if (vendedor == null) {
                        System.out.println("Vendedor no encontrado: " + numDoc);
                        continue;
                    }

                    String linea;

                    while ((linea = br.readLine()) != null) {

                        String[] partes = linea.split(";");

                        // Validación de formato
                        if (partes.length < 2) {
                            System.out.println("Línea inválida en archivo " + archivo + ": " + linea);
                            continue;
                        }

                        String productoId = partes[0];
                        int cantidad;

                        try {
                            cantidad = Integer.parseInt(partes[1]);
                        } catch (NumberFormatException e) {
                            System.out.println("Cantidad inválida: " + partes[1]);
                            continue;
                        }

                        // Validación cantidad negativa
                        if (cantidad < 0) {
                            System.out.println("Cantidad negativa: " + cantidad);
                            continue;
                        }

                        Producto producto = productos.get(productoId);

                        // Validación producto inexistente
                        if (producto == null) {
                            System.out.println("Producto no existe: " + productoId);
                            continue;
                        }

                        // Procesamiento
                        producto.cantidadVendida += cantidad;
                        vendedor.ventasTotales += cantidad * producto.precio;
                    }
                }
            }

            // Reporte vendedores
            List<Vendedor> listaVendedores = new ArrayList<>(vendedores.values());
            listaVendedores.sort((a, b) -> Double.compare(b.ventasTotales, a.ventasTotales));

            try (PrintWriter writerV = new PrintWriter("reporte_vendedores.csv")) {
                for (Vendedor v : listaVendedores) {
                    writerV.println(v.nombres + " " + v.apellidos + ";" + v.ventasTotales);
                }
            }

            // Reporte productos
            List<Producto> listaProductos = new ArrayList<>(productos.values());
            listaProductos.sort((a, b) -> b.cantidadVendida - a.cantidadVendida);

            try (PrintWriter writerP = new PrintWriter("reporte_productos.csv")) {
                for (Producto p : listaProductos) {
                    writerP.println(p.nombre + ";" + p.precio + ";" + p.cantidadVendida);
                }
            }

            System.out.println("Proceso completado correctamente");

        } catch (Exception e) {
            System.out.println("Error en la ejecución");
            e.printStackTrace();
        }
    }
}