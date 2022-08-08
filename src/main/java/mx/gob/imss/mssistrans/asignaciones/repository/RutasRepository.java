package mx.gob.imss.mssistrans.asignaciones.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.imss.mssistrans.asignaciones.model.RutasEntity;

@Repository
public interface RutasRepository extends JpaRepository<RutasEntity, Integer> {
	
	@Query(value = "select r from RutasEntity r "
			     + "where r.idOoad = ?1 and r.indActivo = 1")
	List<RutasEntity> findRutasActivas(Integer idOoad);

}
