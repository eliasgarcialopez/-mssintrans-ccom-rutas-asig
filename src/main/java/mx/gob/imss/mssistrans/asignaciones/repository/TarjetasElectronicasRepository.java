package mx.gob.imss.mssistrans.asignaciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.imss.mssistrans.asignaciones.model.TarjetasElectronicasEntity;

@Repository
public interface TarjetasElectronicasRepository extends JpaRepository<TarjetasElectronicasEntity, Integer> {
	
	@Query(value = "select t from TarjetasElectronicasEntity t join t.ooad o "
			     + "where o.idOoad = ?1 and t.indActivo = 1 ")
	TarjetasElectronicasEntity findTarjetasDigitalesByOoad(Integer idOoad);

}
