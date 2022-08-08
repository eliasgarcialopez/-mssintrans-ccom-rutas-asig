package mx.gob.imss.mssistrans.reasignacion.rutas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.imss.mssistrans.reasignacion.rutas.model.VehiculosEntity;

@Repository
public interface VehiculosRepository extends JpaRepository<VehiculosEntity, Integer> {

	@Query(value = "SELECT VE FROM VehiculosEntity VE "
			+ "WHERE VE.indActivo = 1 "
			+ "AND VE.desEstatusVehiculo IN ('En operación','En resguardo') ")
	List<VehiculosEntity> findVehiculoAsignables();
}
