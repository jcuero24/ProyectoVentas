import java.io.PrintWriter;
import java.util.Random;

public class GenerateInfoFiles {

    private static final String[] NOMBRES = {"Juan", "Maria", "Pedro", "Ana", "Luis"};
    private static final String[] APELLIDOS = {"Perez", "Gomez", "Rodriguez", "Lopez"};

    public static void main(String[] args) {

        try {
            createProductsFile(10);
            createSalesManInfoFile(5);

            for (int i = 0; i < 5; i++) {
                createSalesMenFile(10, NOMBRES[i % NOMBRES.length], 1000 + i);
            }

            System.out.println("Archivos generados correctamente");
        } catch (Exception e) {
            System.out.println("Error en la generación");
        }
    }

    public static void createProductsFile(int productsCount) {
        try (PrintWriter writer = new PrintWriter("productos.txt")) {

            for (int i = 1; i <= productsCount; i++) {
                String nombre = "Producto" + i;
                double precio = (Math.random() * 100) + 1;

                writer.println(i + ";" + nombre + ";" + precio);
            }

        } catch (Exception e) {
            System.out.println("Error creando productos");
        }
    }

    public static void createSalesManInfoFile(int salesmanCount) {
        try (PrintWriter writer = new PrintWriter("vendedores.txt")) {

            for (int i = 0; i < salesmanCount; i++) {
                String tipoDoc = "CC";
                long id = 1000 + i;
                String nombre = NOMBRES[i % NOMBRES.length];
                String apellido = APELLIDOS[i % APELLIDOS.length];

                writer.println(tipoDoc + ";" + id + ";" + nombre + ";" + apellido);
            }

        } catch (Exception e) {
            System.out.println("Error creando vendedores");
        }
    }

    public static void createSalesMenFile(int randomSalesCount, String name, long id) {

        Random rand = new Random();
        String fileName = "ventas_" + id + ".txt";

        try (PrintWriter writer = new PrintWriter(fileName)) {

            writer.println("CC;" + id);

            for (int i = 0; i < randomSalesCount; i++) {
                int productId = rand.nextInt(10) + 1;
                int quantity = rand.nextInt(20) + 1;

                writer.println(productId + ";" + quantity + ";");
            }

        } catch (Exception e) {
            System.out.println("Error creando ventas");
        }
    }
}