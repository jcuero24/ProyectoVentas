import java.io.*;
import java.nio.file.Path;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        try {

            //  Cargar productos
            Map<String, Producto> productos = LecturaDatos.cargarDatos(
                    "productos.csv",
                    linea -> {
                        String[] p = linea.split(";");
                        return new Producto(p[0], p[1], Double.parseDouble(p[2]));
                    },
                    Producto::getId
            );

            //  Cargar vendedores
            Map<String, Vendedor> vendedores = LecturaDatos.cargarDatos(
                    "vendedores.csv",
                    linea -> {
                        String[] v = linea.split(";");
                        return new Vendedor(v[0], v[1], v[2], v[3]);
                    },
                    Vendedor::getNumDoc
            );

            //  Procesar ventas
            for (Path archivo : LecturaDatos.obtenerArchivosVentas()) {

                BufferedReader br = new BufferedReader(new FileReader(archivo.toFile()));

                // Primera línea: vendedor
                String primeraLinea = br.readLine();
                String[] datosVendedor = primeraLinea.split(";");
                String numDoc = datosVendedor[1];

                Vendedor vendedor = vendedores.get(numDoc);

                String linea;

                while ((linea = br.readLine()) != null) {

                    String[] partes = linea.split(";");
                    String productoId = partes[0];
                    int cantidad = Integer.parseInt(partes[1]);

                    Producto producto = productos.get(productoId);

                    if (producto != null && vendedor != null) {
                        producto.cantidadVendida += cantidad;
                        vendedor.ventasTotales += cantidad * producto.precio;
                    }
                }

                br.close();
            }

            //  Reporte vendedores
            List<Vendedor> listaVendedores = new ArrayList<>(vendedores.values());
            listaVendedores.sort((a, b) -> Double.compare(b.ventasTotales, a.ventasTotales));

            PrintWriter writerV = new PrintWriter("reporte_vendedores.csv");

            for (Vendedor v : listaVendedores) {
                writerV.println(v.nombres + " " + v.apellidos + ";" + v.ventasTotales);
            }

            writerV.close();

            //  Reporte productos
            List<Producto> listaProductos = new ArrayList<>(productos.values());
            listaProductos.sort((a, b) -> b.cantidadVendida - a.cantidadVendida);

            PrintWriter writerP = new PrintWriter("reporte_productos.csv");

            for (Producto p : listaProductos) {
                writerP.println(p.nombre + ";" + p.precio + ";" + p.cantidadVendida);
            }

            writerP.close();

            System.out.println("Proceso completado correctamente");

        } catch (Exception e) {
            System.out.println("Error en la ejecución");
            e.printStackTrace();
        }
    }
}