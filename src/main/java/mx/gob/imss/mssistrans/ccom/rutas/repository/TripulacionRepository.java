package mx.gob.imss.mssistrans.ccom.rutas.repository;

import java.util.Optional;

import mx.gob.imss.mssistrans.ccom.rutas.model.Tripulacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TripulacionRepository extends JpaRepository<Tripulacion, Integer> {
	 
    /**
     * Recupera la tripulacion por id Vehiculo activa .
     *
     * @param idVehiculo
     * @param activo
     * @return
     */
	@Query(value = "select t from Tripulacion t "
			     + "where t.idVehiculo = ?1 and t.activo = 1 and t.indiceSistema=1 ")
	Optional<Tripulacion> findByIdVehiculo(Integer idVehiculo);

}
