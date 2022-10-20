package mx.gob.imss.mssistrans.ccom.rutas.service;

import mx.gob.imss.mssistrans.ccom.rutas.dto.CatalogoGenerico;
import mx.gob.imss.mssistrans.ccom.rutas.dto.Response;

import java.util.List;


public interface TarjetasElectronicasService {
    /**
     * Consulta todas los folios de tarjeta de combustible disponibles
     *
     * @return
     */
    Response<List<CatalogoGenerico>> busquedaTarjetasDigitales();


}
