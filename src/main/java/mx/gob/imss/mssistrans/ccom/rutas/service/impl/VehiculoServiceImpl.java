package mx.gob.imss.mssistrans.ccom.rutas.service.impl;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.gob.imss.mssistrans.ccom.rutas.dto.DatosUsuario;
import mx.gob.imss.mssistrans.ccom.rutas.dto.Response;
import mx.gob.imss.mssistrans.ccom.rutas.dto.VehiculoResponse;
import mx.gob.imss.mssistrans.ccom.rutas.model.ModuloAmbulancia;
import mx.gob.imss.mssistrans.ccom.rutas.model.Vehiculos;
import mx.gob.imss.mssistrans.ccom.rutas.model.ZonaAtencion;
import mx.gob.imss.mssistrans.ccom.rutas.repository.ModuloAmbulanciaRepository;
import mx.gob.imss.mssistrans.ccom.rutas.repository.VehiculosRepository;
import mx.gob.imss.mssistrans.ccom.rutas.repository.ZonaAtencionRepository;
import mx.gob.imss.mssistrans.ccom.rutas.service.VehiculoService;
import mx.gob.imss.mssistrans.ccom.rutas.util.VehiculoMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class VehiculoServiceImpl implements VehiculoService {
    private final VehiculosRepository vehiculosRepository;
    private final ModuloAmbulanciaRepository moAmbulanciaRepository;
    private final ZonaAtencionRepository zonaAtencionRepository;

    @Override
    public Response<List<VehiculoResponse>> findVehiculoAsignables() {
        Response<List<VehiculoResponse>> response = new Response<>();
        // todo - arrojar error cuando haya un usuario denegado y que lo cache el controller advice
        try {
            String usuario = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            log.info("usuario {}", usuario);

            if (usuario.equals("denegado")) {

                response.setError(false);
                response.setCodigo(HttpStatus.UNAUTHORIZED.value());
                response.setMensaje(usuario);
                return response;
            }

            Gson gson = new Gson();
            DatosUsuario datosUsuarios = gson.fromJson(usuario, DatosUsuario.class);

            log.info("consultando los vehiculos disponibles, {}", datosUsuarios.getIdOoad());

            Optional<ModuloAmbulancia> modOp = moAmbulanciaRepository.findByIdOOADAndActivoEquals(datosUsuarios.getIdOoad(), true);
            if (modOp.isPresent()) {

                Optional<ZonaAtencion> zonaOp = zonaAtencionRepository.findByIdModuloAndActivoEquals(modOp.get().getIdModulo(), true);
                if (zonaOp.isPresent()) {
                    ZonaAtencion zona = zonaOp.get();
                    log.info("zona atencion del modulo  , {}", zona.getIdZona());

                    //List<Vehiculos> result1 = vehiculosRepository.findVehiculoAsignables(zona.getIdZona());
                    List<Vehiculos> result1 = vehiculosRepository.findVehiculoAsignablesByOaad(datosUsuarios.getIdOoad());
                    //List<Vehiculos> result2 = vehiculosRepository.findVehiculoArrendadosAsignables(zona.getIdZona());

                    List<Vehiculos> result = new ArrayList<>();
                    result.addAll(result1);
                    //result.addAll(result2);

                    log.info("vehiculos obtenidos , {}", result.size());
                    List<VehiculoResponse> content = new ArrayList<>();

                    if (!result.isEmpty()) {
                        content = result
                                .stream()
                                .map(VehiculoMapper.INSTANCE::vehiculoEntityToJsonTo)
                                .collect(Collectors.toList());
                    }

                    response.setDatos(content);
                    response.setMensaje("Exito");
                    response.setCodigo(HttpStatus.OK.value());
                }


            } else {
                response.setDatos(new ArrayList<>());
                response.setError(false);
                response.setMensaje("Exito");
                response.setCodigo(HttpStatus.OK.value());
            }
        } catch (Exception exception) {

            log.error("Ha ocurrido un error al consultar los vehiculo, {}", ExceptionUtils.getStackTrace(exception));
            response.setError(true);
            response.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMensaje(exception.getMessage());
        }
        return response;
    }

}
