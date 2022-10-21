package mx.gob.imss.mssistrans.ccom.rutas.service.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.imss.mssistrans.ccom.rutas.dto.CatalogoGenerico;
import mx.gob.imss.mssistrans.ccom.rutas.dto.DatosUsuario;
import mx.gob.imss.mssistrans.ccom.rutas.dto.Response;
import mx.gob.imss.mssistrans.ccom.rutas.model.TarjetasElectronicas;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.gob.imss.mssistrans.ccom.rutas.repository.ControlRutasRepository;
import mx.gob.imss.mssistrans.ccom.rutas.repository.TarjetasElectronicasRepository;
import mx.gob.imss.mssistrans.ccom.rutas.service.TarjetasElectronicasService;


@Service
@AllArgsConstructor
@Slf4j
public class TarjetasElectronicasServiceImpl implements TarjetasElectronicasService {
	@Autowired
	private TarjetasElectronicasRepository tarjetasRepository;
	@Autowired
	private ControlRutasRepository controlRutasRepository;

	@Override
	public Response<List<CatalogoGenerico>> busquedaTarjetasDigitales() {
		Response<List<CatalogoGenerico>> response = new Response<>();
        List<CatalogoGenerico> lstTarjetas = new ArrayList<>();

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
			log.info("usuario {}", usuario);
			
            List<TarjetasElectronicas> tarjetaEntity = tarjetasRepository.findTarjetasDigitalesByOoad(datosUsuarios.getIdOoad());
            
            log.info("consultando folios "+tarjetaEntity);
			
            ArrayList<String> lstTarjetasUsadas = controlRutasRepository.findTarjetasByOoad(datosUsuarios.getIdOoad()); // Para descartar tarjetas utilizadas
            if(!tarjetaEntity.isEmpty()){
                for(TarjetasElectronicas tarj: tarjetaEntity) {

                    int folioInicial = Integer.parseInt(tarj.getNumFolioInicial());
                    int folioFinal = Integer.parseInt(tarj.getNumFolioFinal());
                    for (int i = folioInicial; i <= folioFinal; i++) {
                        CatalogoGenerico catalogo = new CatalogoGenerico(i, String.format("%010d", i), tarj.getIdTarjetaElectronica());
                        if (lstTarjetasUsadas.contains(String.format("%010d", i))) continue;
                        lstTarjetas.add(catalogo);
                    }
                }
            response.setDatos(lstTarjetas);
            response.setCodigo(HttpStatus.OK.value());
            response.setMensaje("Exito");
            response.setError(false);
        	
            }else {
            	 response.setDatos(lstTarjetas);
                 response.setCodigo(HttpStatus.OK.value());
                 response.setMensaje("Exito");
                 response.setError(false);
             	
            }

        } catch (Exception exc) {
            log.error("Ha ocurrido un error al tarjetas , {}", ExceptionUtils.getStackTrace(exc));
            response.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMensaje("Error en busqueda de tarjetas digitales " + exc.getMessage());
            response.setError(true);
        }

        return response;
	}

}
