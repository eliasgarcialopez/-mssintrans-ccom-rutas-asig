package mx.gob.imss.mssintetrans.ccom.rutas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mx.gob.imss.mssintetrans.ccom.rutas.model.BitacoraServiciosEntity;

public interface BitacoraServiciosRepository extends JpaRepository<BitacoraServiciosEntity, Integer> {

	@Query(value = "select b from BitacoraServiciosEntity b join b.controlRuta cr "
			 + "where cr.idControlRuta = ?1 and b.indActivo = 1 and b.indSistema = 1")
	List<BitacoraServiciosEntity> findBitacoraByCr(Integer idControlRuta);
	
	@Query(value = "select max(substring(b.numBitacora,7,4)) from BitacoraServiciosEntity b "
		     + "where b.idOoad = ?1 and  substring(b.numBitacora,1,2) = ?2")
	String findUltimoFolioByMesOoad(Integer idOoad, String mes);

}
