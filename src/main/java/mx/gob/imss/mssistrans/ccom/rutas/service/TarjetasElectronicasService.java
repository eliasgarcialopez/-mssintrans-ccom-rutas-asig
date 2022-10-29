package mx.gob.imss.mssistrans.ccom.rutas.service;

import mx.gob.imss.mssistrans.ccom.rutas.dto.CatalogoGenerico;
import mx.gob.imss.mssistrans.ccom.rutas.dto.Respuesta;

import java.util.List;


public interface TarjetasElectronicasService {
    /**
     * Consulta todas los folios de tarjeta de combustible disponibles
     *
     * @return
     */
    Respuesta<List<CatalogoGenerico>> busquedaTarjetasDigitales();


}
