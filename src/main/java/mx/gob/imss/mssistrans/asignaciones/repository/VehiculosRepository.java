package mx.gob.imss.mssistrans.asignaciones.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.imss.mssistrans.asignaciones.model.VehiculosEntity;

@Repository
public interface VehiculosRepository extends JpaRepository<VehiculosEntity, Integer> {
	
	@Query(value = "select v from VehiculosEntity v join v.unidad u left join v.arrendatarios a "
			+ "where u.ooad.idOoad = ?1 and v.indActivo = 1 "
			+ "and v.desEstatusVehiculo in ('En operación','Siniestrado en transito','Sustituto') "
	        + "and (v.indArrendado = 0 or a.fecFinContrato > curdate()) ")
	List<VehiculosEntity> findVehiculoAsignables(Integer idOoad);
	
}
