package mx.gob.imss.mssintetrans.ccom.rutas.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.gob.imss.mssintetrans.ccom.rutas.dto.CatalogoGenerico;
import mx.gob.imss.mssintetrans.ccom.rutas.dto.Respuesta;
import mx.gob.imss.mssintetrans.ccom.rutas.model.TarjetasElectronicas;
import mx.gob.imss.mssintetrans.ccom.rutas.repository.ControlRutasRepository;
import mx.gob.imss.mssintetrans.ccom.rutas.repository.SolicitudTrasladoRepository;
import mx.gob.imss.mssintetrans.ccom.rutas.repository.TarjetasElectronicasRepository;
import mx.gob.imss.mssintetrans.ccom.rutas.service.TarjetasElectronicasService;


@Service
@AllArgsConstructor
@Slf4j
public class TarjetasElectronicasServiceImpl implements TarjetasElectronicasService {
	@Autowired
	private TarjetasElectronicasRepository tarjetasRepository;
	@Autowired
	private ControlRutasRepository controlRutasRepository;

	@Override
	public Respuesta<List<CatalogoGenerico>> busquedaTarjetasDigitales(Integer idOoad) {
		Respuesta<List<CatalogoGenerico>> response = new Respuesta<>();
        List<CatalogoGenerico> lstTarjetas = new ArrayList<CatalogoGenerico>();

        try {
            TarjetasElectronicas tarjetaEntity = tarjetasRepository.findTarjetasDigitalesByOoad(idOoad);
            ArrayList<String> lstTarjetasUsadas = controlRutasRepository.findTarjetasByOoad(idOoad); // Para descartar tarjetas utilizadas
            int folioInicial = Integer.parseInt(tarjetaEntity.getNumFolioInicial());
            int folioFinal = Integer.parseInt(tarjetaEntity.getNumFolioFinal());
            for (int i = folioInicial; i <= folioFinal; i++) {
                CatalogoGenerico catalogo = new CatalogoGenerico(i, String.format("%010d", i), tarjetaEntity.getIdTarjetaElectronica());
                if (lstTarjetasUsadas.contains(String.format("%010d", i))) continue;
                lstTarjetas.add(catalogo);
            }
            response.setDatos(lstTarjetas);
            response.setCodigo(HttpStatus.OK.value());
            response.setMensaje("Exito");
            response.setError(false);

        } catch (Exception exc) {
            log.error("Error en busqueda de tarjetas digitales ", exc.getMessage());
            response.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMensaje("Error en busqueda de tarjetas digitales " + exc.getMessage());
            response.setError(true);
        }

        return response;
	}

}
