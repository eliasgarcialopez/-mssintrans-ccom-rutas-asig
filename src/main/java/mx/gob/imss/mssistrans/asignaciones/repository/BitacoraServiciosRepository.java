package mx.gob.imss.mssistrans.asignaciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mx.gob.imss.mssistrans.asignaciones.model.BitacoraServiciosEntity;

public interface BitacoraServiciosRepository extends JpaRepository<BitacoraServiciosEntity, Integer> {

	@Query(value = "select max(substring(b.numBitacora,7,4)) from BitacoraServiciosEntity b "
		     + "where b.idOoad = ?1 and  substring(b.numBitacora,1,2) = ?2")
	String findUltimoFolioByMesOoad(Integer idOoad, String mes);
	
}
