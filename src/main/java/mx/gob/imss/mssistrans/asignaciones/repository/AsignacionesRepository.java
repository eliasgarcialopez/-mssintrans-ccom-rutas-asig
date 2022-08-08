package mx.gob.imss.mssistrans.asignaciones.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.imss.mssistrans.asignaciones.model.AsignacionesEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface AsignacionesRepository extends JpaRepository<AsignacionesEntity, Integer> {
	
	@Query(value = "select a.numFolioTarjeta from AsignacionesEntity a join a.vehiculo v join v.unidad u "
		        +  "where u.ooad.idOoad = ?1 and a.indActivo = 1 ")
    ArrayList<String> findTarjetasByOoad(Integer idOoad);
	
	//@Query(value = "select a from AsignacionesEntity a join a.vehiculo v join v.unidad u "
    //        + "where u.ooad.idOoad = ?1 and a.indActivo = 1 ")
    //List<AsignacionesEntity> findAsignacionesByOoad(Integer idOoad);
	
	@Query(value = "SELECT * FROM SINTRANST_ASIGNACIONES SA "
		         + "INNER JOIN SINTRANST_VEHICULOS SV USING (ID_VEHICULO) "
			     + "INNER JOIN SINTRANST_CHOFERES CH USING (ID_CHOFER) "
			     + "INNER JOIN SINTRANSC_UNIDADES_ADSCRIPCION UA ON UA.ID_UNIDAD_ADSCRIPCION = SV.ID_UNIDAD_ADSCRIPCION "
                 + "WHERE ID_OOAD = ? AND SA.IND_ACTIVO = 1 ", 
                 countQuery = "SELECT COUNT(*) FROM SINTRANST_ASIGNACIONES SA "
                 + "INNER JOIN SINTRANST_VEHICULOS SV USING (ID_VEHICULO) "
                 + "INNER JOIN SINTRANSC_UNIDADES_ADSCRIPCION USING (ID_UNIDAD_ADSCRIPCION) "
                 + "WHERE ID_OOAD = ? AND SA.IND_ACTIVO = 1 ", nativeQuery = true)
    Page<List<AsignacionesEntity>> findAsignacionesByOoad(Integer idOoad, Pageable page);
	
	@Query(value = "select a from AsignacionesEntity a join a.vehiculo v join v.unidad u "
            + "where a.numAsignacion = ?1 and u.ooad.idOoad = ?2 and a.indActivo = 1 ")
    List<AsignacionesEntity> findAsignacionesByNum(String numAsignacion, Integer idOoad);
	
	@Query(value = "select max(substring(a.numAsignacion,9,4)) from AsignacionesEntity a "
			     + "where substring(a.numAsignacion,6,2) = ?1 ")
	String findUltimoFolioByOoad(String clavePresup);

}
