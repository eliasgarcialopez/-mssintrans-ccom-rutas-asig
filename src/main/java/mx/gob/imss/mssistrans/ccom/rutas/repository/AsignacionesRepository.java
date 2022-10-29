package mx.gob.imss.mssistrans.ccom.rutas.repository;

import mx.gob.imss.mssistrans.ccom.rutas.model.AsignacionesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsignacionesRepository extends JpaRepository<AsignacionesEntity, Integer> {
	

}
