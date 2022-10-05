package mx.gob.imss.mssistrans.ccom.rutas.repository;

import mx.gob.imss.mssistrans.ccom.rutas.model.TarjetasElectronicas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarjetasElectronicasRepository extends JpaRepository<TarjetasElectronicas, Integer> {
	
	@Query(value = "select t from TarjetasElectronicas t  "
			     + "where t.ooad.idOoad = ?1 and t.indActivo = 1 and t.desEstatusTarjeta ='Activa' ")
	List<TarjetasElectronicas> findTarjetasDigitalesByOoad(Integer idOoad);

}
