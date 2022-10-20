package mx.gob.imss.mssistrans.ccom.rutas.util;

import lombok.extern.slf4j.Slf4j;
import mx.gob.imss.mssistrans.ccom.rutas.dto.Response;
import mx.gob.imss.mssistrans.ccom.rutas.exceptions.RequestUtilException;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@Component
@Slf4j
public class RestTemplateUtil {

    private static final String REQUEST_ERROR_MESSAGE = "Ha ocurrido un error al guardar el archivo";
    private static final String GENERIC_ERROR_MESSAGE = "Fallo al consumir el servicio, {}";
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
    public <T> Response<T> sendGetRequest(String url) {
        Response<T> response = new Response<>();
        try {
            //noinspection unchecked
            final ResponseEntity<T> responseEntity = (ResponseEntity<T>) restTemplate
                    .getForEntity(url, Response.class);
            response = validaRespuesta(responseEntity);
        } catch (Exception e) {
            crearErrorResponse(e, response);
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
    public <T> Response<T> sendPostRequest(String url, T body, Class<T> clazz) throws RequestUtilException {
        Response<T> responseBody = new Response<>();
        HttpHeaders headers = RestTemplateUtil.createHttpHeaders();

        HttpEntity<T> request = new HttpEntity<>(body, headers);
        ResponseEntity<T> responseEntity;
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
                responseBody = (Response<T>) responseEntity.getBody();
            } else {
                throw new RequestUtilException(REQUEST_ERROR_MESSAGE);
            }
        } catch (RequestUtilException exception) {
            throw exception;
        } catch (Exception e) {
            crearErrorResponse(e, responseBody);
        }

        return responseBody;
    }

    /**
     * Env&iacute;a una petici&oacute;n con Body.
     *
     * @param url
     * @param body
     * @param <T>
     * @param <G>
     * @return
     * @throws IOException
     */
    public <T, G extends Map<String, String>> Response<G> sendPostRequestByteArray(
            String url, T body) throws RequestUtilException {
        Response<G> responseBody = new Response<>();
        HttpHeaders headers = RestTemplateUtil.createHttpHeaders();
        HttpEntity<T> request = new HttpEntity<>(body, headers);
        ResponseEntity<T> responseEntity;
        try {
            //noinspection unchecked
            responseEntity = (ResponseEntity<T>) restTemplate
                    .postForEntity(
                            url,
                            request,
                            Response.class
                    );
            responseBody = validaRespuesta(responseEntity);
        } catch (RequestUtilException ex) {
            throw ex;
        } catch (Exception e) {
            crearErrorResponse(e, responseBody);
        }
        return responseBody;
    }

    /**
     * Crea la respuesta de error para contestar al servicio
     *
     * @param e
     * @param responseBody
     * @param <G>
     */
    private <G> void crearErrorResponse(Exception e, Response<G> responseBody) {
        final String errorMessage = e.getMessage();
        log.error(GENERIC_ERROR_MESSAGE, errorMessage);
        responseBody.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseBody.setError(true);
        responseBody.setMensaje(e.getMessage());
    }

    /**
     * Valida la respuesta del servicio que se consume.
     *
     * @param responseEntity
     * @param <G>
     * @param <T>
     * @return
     * @throws RequestUtilException
     */
    private <G, T> Response<G> validaRespuesta(ResponseEntity<T> responseEntity) throws RequestUtilException {
        if (responseEntity.getStatusCode() == HttpStatus.OK &&
                responseEntity.getBody() != null) {
            //noinspection unchecked
            return (Response<G>) responseEntity.getBody();
        } else {
            throw new RequestUtilException(REQUEST_ERROR_MESSAGE);
        }
    }

    public <T> Response<T> sendPostRequest(
            String url, Map<String, MultipartFile> mapaArchivos) throws IOException, RequestUtilException {
        Response<T> responseBody = new Response<>();

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
        ResponseEntity<T> responseEntity;
        try {
            //noinspection unchecked
            responseEntity = (ResponseEntity<T>) restTemplate
                    .postForEntity(
                            url,
                            request,
                            Response.class
                    );
            responseBody = validaRespuesta(responseEntity);
        } catch (RequestUtilException e) {
            throw e;
        } catch (Exception e) {
            crearErrorResponse(e, responseBody);
        }

        return responseBody;
    }

    /**
     * Crea los headers para la petici&oacute;n
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
    public <T> Response<T> sendDeleteRequest(String url, T body, Class<T> clazz) throws IOException {
        Response<T> responseBody = new Response<>();
        HttpHeaders headers = RestTemplateUtil.createHttpHeaders();
        HttpEntity<T> request = new HttpEntity<>(body, headers);
        ResponseEntity<T> responseEntity;
        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, request, clazz);
            if (responseEntity.getStatusCode() == HttpStatus.OK &&
                    responseEntity.getBody() != null) {
                responseBody = (Response<T>) responseEntity.getBody();
            } else {
                throw new IOException(REQUEST_ERROR_MESSAGE);
            }
        } catch (IOException ioException) {
            throw ioException;
        } catch (Exception e) {
            crearErrorResponse(e, responseBody);
        }

        return responseBody;
    }

}
