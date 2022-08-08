package mx.gob.imss.mssistrans.reasignacion.rutas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.imss.mssistrans.reasignacion.rutas.model.ChoferesEntity;

/**
 * @author Brayan Ramiro Quezada Hernandez
 *
 */
@Repository
public interface ChoferesRepository extends JpaRepository<ChoferesEntity, Integer> {

//		@Query(value = "select c from ChoferesEntity c join c.unidad u "
//		             + "where u.ooad.idOoad = ?1 and c.indActivo = 1 "
//				     + "and c.tipoLicencia != 'E' and c.estatusChofer = 'Activo' ")
//		List<ChoferesEntity> findChoferesActivos(Integer idOoad);
		
		@Query("SELECT CE FROM ChoferesEntity CE WHERE CE.tipoLicencia != 'E' AND CE.estatusChofer = 'Activo' ")
		List<ChoferesEntity> choferesActivos();
}
