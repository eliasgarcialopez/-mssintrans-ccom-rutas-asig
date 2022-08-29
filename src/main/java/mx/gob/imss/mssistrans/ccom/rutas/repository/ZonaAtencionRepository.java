package mx.gob.imss.mssistrans.ccom.rutas.repository;

import java.util.Optional;

import mx.gob.imss.mssistrans.ccom.rutas.model.ZonaAtencion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


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
