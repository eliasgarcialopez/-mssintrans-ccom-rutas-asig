package mx.gob.imss.mssistrans.ccom.rutas.util;

import lombok.experimental.UtilityClass;
import mx.gob.imss.mssistrans.ccom.rutas.exceptions.FileException;
import mx.gob.imss.mssistrans.ccom.rutas.exceptions.JasperException;
import net.sf.jasperreports.engine.*;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;

@UtilityClass
public class ReporteUtil {

    /**
     * Recupera los logos par colocarlos en el reporte.
     */
    public static final UnaryOperator<String> recuperarLogos = ruta -> {
        final ClassPathResource resource = new ClassPathResource(ruta);
        final String path;
        try {
            path = resource.getFile().getAbsolutePath();
            return Base64.getEncoder().encodeToString(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            throw new FileException(e);
        }
    };

    /**
     * Recupera el inputStream del formato para generar el jasper.
     *
     * @param rutaArchivo
     */
    public static final Function<String, InputStream> recuperarInputStream = rutaArchivo -> {
        try {
            return new ClassPathResource(rutaArchivo)
                    .getInputStream();
        } catch (IOException e) {
            throw new FileException(e);
        }
    };


    /**
     * Recupera el jasper con el inputStream del archivo .jrxml
     *
     * @param inputStream
     */
    public static final Function<InputStream, JasperReport> recuperarJasperReport = inputStream -> {
        try {
            return JasperCompileManager.compileReport(inputStream);
        } catch (JRException e) {
            throw new JasperException(e);
        }
    };

    /**
     * Genera el pdf con el formato jasper que especificado anteriormente.
     *
     * @param reporte
     * @param parameters
     */
    public static final BiFunction<JasperReport, Map<String, Object>, byte[]> generarPdf = (reporte, parameters) -> {
        try {
            return JasperRunManager.runReportToPdf(reporte, parameters, new JREmptyDataSource());
        } catch (JRException e) {
            throw new JasperException(e);
        }
    };

}
