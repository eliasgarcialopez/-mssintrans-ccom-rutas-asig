package mx.gob.imss.mssistrans.ccom.rutas.service;

import java.util.List;

import mx.gob.imss.mssistrans.ccom.rutas.dto.CatalogoGenerico;
import mx.gob.imss.mssistrans.ccom.rutas.dto.Respuesta;


public interface TarjetasElectronicasService {
	  /**
     * Consulta todas los folios de tarjeta de combustible disponibles
     *
     * @param idControlRuta
     * @return
     */
	Respuesta<List<CatalogoGenerico>> busquedaTarjetasDigitales();
	  

}
