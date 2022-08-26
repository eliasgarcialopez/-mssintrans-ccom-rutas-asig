package mx.gob.imss.mssintetrans.ccom.rutas.util;

import net.sf.jasperreports.engine.*;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ReporteUtil {

    /**
     * Recupera el arreglo de bytes con los par&aacute;metros necesarios
     * todo - no se va a usar
     *
     * @param rutaArchivo
     * @return byte[]
     * @deprecated
     */
    public static Function<String, byte[]> recuperarArchivo = (String rutaArchivo) -> {
        Path path = Paths.get(rutaArchivo);
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    };

    /**
     * Recupera los logos par colocarlos en el reporte.
     */
    public static Function<String, String> recuperarLogos = ruta -> {
        final ClassPathResource resource = new ClassPathResource(ruta);
        final String path;
        try {
            path = resource.getFile().getAbsolutePath();
            return Base64.getEncoder().encodeToString(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    };

    /**
     * todo - puede recuperarse desde el application properties
     * Coloca las im&aacute;genes de los logos en el reporte.
     * Esta funci&oacute;n puede ser manipulada para cambiar en donde son colocalos los logos
     */
    public static Function<Map<String, Object>, Void> colocarLogos = parameters -> {// (String key, Object value, Map<String, Object> parameters) throws IOException {
        parameters.put("logoImss", recuperarLogos.apply("reportes/logoImss.png"));
        parameters.put("logoSistrans", recuperarLogos.apply("reportes/logoSistema.png"));
        return null;
    };

    /**
     * Recupera el inputStream del formato para generar el jasper.
     *
     * @param rutaArchivo
     */
    public static Function<String, InputStream> recuperarInputStream = rutaArchivo -> {
        try {
            return new ClassPathResource(rutaArchivo)
                    .getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    };


    /**
     * Recupera el jasper con el inputStream del archivo .jrxml
     *
     * @param inputStream
     */
    public static Function<InputStream, JasperReport> recuperarJasperReport = inputStream -> {
        try {
            return JasperCompileManager.compileReport(inputStream);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    };

    /**
     * Genera el pdf con el formato jasper que especificado anteriormente.
     *
     * @param reporte
     * @param parameters
     */
    public static BiFunction<JasperReport, Map<String, Object>, byte[]> generarPdf = (reporte, parameters) -> {
        try {
            return JasperRunManager.runReportToPdf(reporte, parameters, new JREmptyDataSource());
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    };

}
