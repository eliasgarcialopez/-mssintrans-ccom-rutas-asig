package mx.gob.imss.mssintetrans.ccom.rutas.service;

import java.util.List;

import org.springframework.data.domain.Page;

import mx.gob.imss.mssintetrans.ccom.rutas.dto.CatalogoGenerico;
import mx.gob.imss.mssintetrans.ccom.rutas.dto.ModuloResponse;
import mx.gob.imss.mssintetrans.ccom.rutas.dto.Respuesta;



public interface ModuloService {
	  /**
     * Consulta el modulo disponible por idOOAD
     *
     * @param idControlRuta
     * @return
     */
	Respuesta<ModuloResponse> busquedaModulo();
	  

}
