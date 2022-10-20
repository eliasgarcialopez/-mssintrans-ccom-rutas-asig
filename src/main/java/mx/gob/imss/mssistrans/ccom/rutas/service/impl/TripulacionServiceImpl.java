package mx.gob.imss.mssistrans.ccom.rutas.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.gob.imss.mssistrans.ccom.rutas.dto.Respuesta;
import mx.gob.imss.mssistrans.ccom.rutas.dto.TripulacionInterfaceResponse;
import mx.gob.imss.mssistrans.ccom.rutas.dto.TripulacionResponse;
import mx.gob.imss.mssistrans.ccom.rutas.repository.TripulacionRepository;
import mx.gob.imss.mssistrans.ccom.rutas.repository.UsuarioRepository;
import mx.gob.imss.mssistrans.ccom.rutas.service.TripulacionService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class TripulacionServiceImpl implements TripulacionService {
    @Autowired
    private TripulacionRepository triRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Respuesta<TripulacionResponse> findByIdVehiculo(Integer idVehiculo) {
        Respuesta<TripulacionResponse> response = new Respuesta<>();

        try {
            String usuario = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            log.info("usuario {}", usuario);

            if (usuario.equals("denegado")) {

                response.setError(false);
                response.setCodigo(HttpStatus.UNAUTHORIZED.value());
                response.setMensaje(usuario);
                return response;
            }
            log.info("consultando la tripulacion por idVehiculo, {}", idVehiculo);

            TripulacionInterfaceResponse tripOp = triRepository.findTripulacionIdVehiculo(idVehiculo);
            if (tripOp != null) {
                TripulacionResponse tripRes = new TripulacionResponse();
                log.info(tripOp.getFecFecha());
                if (tripOp.getFecFecha() != null) {
                    tripRes.setFecFecha(tripOp.getFecFecha());
                }

                tripRes.setIdTripulacion(tripOp.getidTripulacion());
                tripRes.setIdVehiculo(tripOp.getidVehiculo());

                tripRes.setCveMatriculaCamillero1(tripOp.getcveMatriculaCamillero1());
                tripRes.setCveMatriculaCamillero2(tripOp.getcveMatriculaCamillero2());
                tripRes.setCveMatriculaChofer(tripOp.getcveMatriculaChofer());

                tripRes.setNombreCamillero1(tripOp.getnombreCamillero1());

                tripRes.setNombreCamillero2(tripOp.getnombreCamillero2());
                tripRes.setNombreChofer(tripOp.getnombreChofer());

                log.info("tripulacion  , {}", tripOp.getidTripulacion());

                response.setDatos(tripRes);
                response.setMensaje("Exito");
                response.setCodigo(HttpStatus.OK.value());


            } else {
                response.setDatos(null);
                response.setError(true);
                response.setMensaje("ECCO  sin tripulación");
                response.setCodigo(HttpStatus.OK.value());
            }
        } catch (Exception exception) {

            log.error("Ha ocurrido un error al consultar la tripulacion, {}", ExceptionUtils.getStackTrace(exception));
            response.setError(true);
            response.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMensaje(exception.getMessage());
        }
        return response;
    }

}
