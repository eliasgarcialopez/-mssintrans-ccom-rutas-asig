package mx.gob.imss.mssintetrans.ccom.rutas.service;

import java.util.List;

import org.springframework.data.domain.Page;

import mx.gob.imss.mssintetrans.ccom.rutas.dto.CatalogoGenerico;
import mx.gob.imss.mssintetrans.ccom.rutas.dto.Respuesta;



public interface TarjetasElectronicasService {
	  /**
     * Consulta todas los folios de tarjeta de combustible disponibles
     *
     * @param idControlRuta
     * @return
     */
	Respuesta<List<CatalogoGenerico>> busquedaTarjetasDigitales(Integer idOoad);
	  

}
