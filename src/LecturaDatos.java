import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Clase utilitaria para lectura de archivos del sistema.
 */
public class LecturaDatos {

    /**
     * Carga datos desde un archivo y los convierte en objetos.
     *
     * @param archivo ruta del archivo
     * @param constructor función que convierte línea en objeto
     * @param getKey función que obtiene la clave del objeto
     * @return mapa con los datos cargados
     */
    public static <T> Map<String, T> cargarDatos(
            String archivo,
            Function<String, T> constructor,
            Function<T, String> getKey
    ) throws IOException {

        return Files.lines(Paths.get(archivo))
                .map(constructor)
                .collect(Collectors.toMap(getKey, item -> item));
    }

    /**
     * Obtiene todos los archivos de ventas.
     * Funciona si están en raíz o en carpeta "ventas".
     */
    public static List<Path> obtenerArchivosVentas() throws IOException {

        Path ruta = Files.exists(Paths.get("ventas"))
                ? Paths.get("ventas")
                : Paths.get(".");

        return Files.walk(ruta)
                .filter(Files::isRegularFile)
                .filter(p -> p.getFileName().toString().startsWith("ventas_")
                        && p.getFileName().toString().endsWith(".csv"))
                .toList();
    }
}