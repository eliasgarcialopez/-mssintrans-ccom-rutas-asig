package mx.gob.imss.mssistrans.shared.util;

import net.bytebuddy.ByteBuddy;
import net.sf.jasperreports.engine.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ReporteUtil {
    private final RestTemplate restTemplate;

    public ReporteUtil(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // todo - recuperar el tipo de reporte que se va a generar
    // todo - hacer una funcion para recuperar el archivo que se va a usar
    // todo - se recupera el reporte que se va a usar
    /**
     * Recupera el arreglo de bytes con los par&aacute;metros necesarios
     * @param rutaArchivo
     * @return byte[]
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
     *
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
     *
     */
    public static Function<InputStream, JasperReport> recuperarJasperReport = inputStream -> {
        try {
            return JasperCompileManager.compileReport(inputStream);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    };
    public static BiFunction<JasperReport, Map<String, Object>, byte[]> generarPdf = (reporte, parameters) -> {
        try {
            return JasperRunManager.runReportToPdf(reporte, parameters, new JREmptyDataSource());
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    };

}
