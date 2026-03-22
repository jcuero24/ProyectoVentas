import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Clase para cargar datos desde archivos.
 */
public class LecturaDatos {

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
     * Obtiene todos los archivos de ventas generados.
     */
    public static List<Path> obtenerArchivosVentas() throws IOException {

        return Files.walk(Paths.get("."))
                .filter(path -> path.getFileName().toString().startsWith("ventas_"))
                .toList();
    }
}