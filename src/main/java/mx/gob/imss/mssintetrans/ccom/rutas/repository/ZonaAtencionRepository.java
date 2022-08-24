package mx.gob.imss.mssintetrans.ccom.rutas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.imss.mssintetrans.ccom.rutas.model.ModuloAmbulancia;
import mx.gob.imss.mssintetrans.ccom.rutas.model.SolicitudTraslado;
import mx.gob.imss.mssintetrans.ccom.rutas.model.ZonaAtencion;


@Repository
public interface ZonaAtencionRepository extends JpaRepository<ZonaAtencion, Integer> {
	
	   /**
     * Recupera la zona de atencion del Modulo de Ambulancia activo  por IdOOAD.
     *
     * @param idArrendadora
     * @param activo
     * @return
     */
   
	Optional<ZonaAtencion> findByIdModuloAndActivoEquals(Integer idModulo,boolean activo);

	
}
