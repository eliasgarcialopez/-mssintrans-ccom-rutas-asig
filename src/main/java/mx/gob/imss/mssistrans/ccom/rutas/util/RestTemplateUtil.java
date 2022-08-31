package mx.gob.imss.mssistrans.ccom.rutas.util;

import lombok.extern.slf4j.Slf4j;
import mx.gob.imss.mssistrans.ccom.rutas.dto.RecuperarImagenesRequest;
import mx.gob.imss.mssistrans.ccom.rutas.dto.Respuesta;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class RestTemplateUtil {

    private final RestTemplate restTemplate;

    public RestTemplateUtil(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Env&iacute;a una petici&oacute;n de tipo POST a la url que se seleccione
     *
     * @param url
     * @param clazz
     * @return
     */
    public Respuesta<?> sendGetRequest(String url, Class<?> clazz) {
        // todo - crear headers para el tema de seguridad
        Respuesta<?> response = new Respuesta<>();
        try {
            final ResponseEntity<?> responseEntity = restTemplate.getForEntity(url, clazz);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                response = (Respuesta<?>) responseEntity.getBody();
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            log.error("Ha ocurrido un error al hacer la peticion. {}", e.getMessage());
            response.setError(true);
            response.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMensaje("Ha ocurrido un error al hacer la peticion");
        }
        return response;
    }

    /**
     * Env&iacute;a una petici&oacute;n con Body.
     *
     * @param url
     * @param clazz
     * @return
     */
    public Respuesta<?> sendPostRequest(String url, Object body, Class<?> clazz) throws IOException {
        Respuesta<?> responseBody = new Respuesta<>();
        HttpHeaders headers = RestTemplateUtil.createHttpHeaders();

        HttpEntity<Object> request = new HttpEntity<>(body, headers);
        ResponseEntity<?> responseEntity = null;
        try {
            responseEntity = (ResponseEntity<?>) restTemplate
                    .postForEntity(
                            url,
                            request,
                            clazz
                    );
            if (responseEntity.getStatusCode() == HttpStatus.OK &&
                    responseEntity.getBody() != null) {
                //noinspection unchecked
                responseBody = (Respuesta<List<String>>) responseEntity.getBody();
            } else {
                throw new IOException("Ha ocurrido un error al guardar el archivo");
            }
        } catch (IOException ioException) {
            throw ioException;
        } catch (Exception e) {
            log.error("Fallo al consumir el servicio, {}", e.getMessage());
            responseBody.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseBody.setError(true);
            responseBody.setMensaje(e.getMessage());
        }

        return responseBody;
    }

    /**
     * Env&iacute;a una petici&oacute;n con Body.
     *
     * @param url
     * @param clazz
     * @return
     */
    public Respuesta<?> sendPostRequestByteArray(String url, RecuperarImagenesRequest body, Class<?> clazz) throws IOException {
        Respuesta<?> responseBody = new Respuesta<>();
        HttpHeaders headers = RestTemplateUtil.createHttpHeaders();

        HttpEntity<Object> request = new HttpEntity<>(body, headers);
        ResponseEntity<?> responseEntity = null;
        try {
            responseEntity = restTemplate
                    .postForEntity(
                            url,
                            request,
                            clazz
                    );
            if (responseEntity.getStatusCode() == HttpStatus.OK &&
                    responseEntity.getBody() != null) {
                //noinspection unchecked
                responseBody = (Respuesta<List<String>>) responseEntity.getBody();
            } else {
                throw new IOException("Ha ocurrido un error al guardar el archivo");
            }
        } catch (IOException ioException) {
            throw ioException;
        } catch (Exception e) {
            log.error("Fallo al consumir el servicio, {}", e.getMessage());
            responseBody.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseBody.setError(true);
            responseBody.setMensaje(e.getMessage());
        }

        return responseBody;
    }

    public Respuesta<?> sendPostRequest(String url, Map<String, MultipartFile> mapaArchivos, Class<?> clazz) throws IOException {
        Respuesta<?> responseBody = new Respuesta<>();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<>();


        for (Map.Entry<String, MultipartFile> entry : mapaArchivos.entrySet()) {
            log.info(entry.getKey() + ":" + entry.getValue());
            if (mapaArchivos.get(entry.getKey()) != null)
                body.put(entry.getKey(),
                        Collections.singletonList(
                                new MultipartInputStreamFileResource(
                                        mapaArchivos.get(entry.getKey()).getInputStream(),
                                        mapaArchivos.get(entry.getKey()).getOriginalFilename())));
        }

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<?> responseEntity = null;
        try {
            responseEntity = (ResponseEntity<?>) restTemplate
                    .postForEntity(
                            url,
                            request,
                            clazz
                    );
            if (responseEntity.getStatusCode() == HttpStatus.OK &&
                    responseEntity.getBody() != null) {
                //noinspection unchecked
                responseBody = (Respuesta<List<String>>) responseEntity.getBody();
            } else {
                throw new IOException("Ha ocurrido un error al guardar el archivo");
            }
        } catch (IOException ioException) {
            throw ioException;
        } catch (Exception e) {
            log.error("Fallo al consumir el servicio, {}", e.getMessage());
            responseBody.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseBody.setError(true);
            responseBody.setMensaje(e.getMessage());
        }

        return responseBody;
    }

    /**
     * Crea los headers para la petici&oacute;n
     * todo - falta agregar el tema de seguridad para las peticiones
     *
     * @return
     */
    private static HttpHeaders createHttpHeaders() {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return header;
    }

    /**
     * Env&iacute;a una petici&oacute;n DELETE al endpoint solicitado
     *
     * @param url
     * @param body
     * @param clazz
     * @return
     */
    public Respuesta<?> sendDeleteRequest(String url, Object body, Class<?> clazz) throws IOException {
        Respuesta<?> responseBody = new Respuesta<>();
        HttpHeaders headers = RestTemplateUtil.createHttpHeaders();
        HttpEntity<Object> request = new HttpEntity<>(body, headers);
        ResponseEntity<?> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, request, clazz);
            if (responseEntity.getStatusCode() == HttpStatus.OK &&
                    responseEntity.getBody() != null) {
                //noinspection unchecked
                responseBody = (Respuesta<List<String>>) responseEntity.getBody();
            } else {
                throw new IOException("Ha ocurrido un error al guardar el archivo");
            }
        } catch (IOException ioException) {
            throw ioException;
        } catch (Exception e) {
            log.error("Fallo al consumir el servicio, {}", e.getMessage());
            responseBody = new Respuesta<>();
            responseBody.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseBody.setError(true);
            responseBody.setMensaje(e.getMessage());
        }

        return responseBody;
    }

//    public Respuesta<?> sendPostRequest(String recuperar_streams_endpoint, Map<String, String> rutas) {
//    }
}
