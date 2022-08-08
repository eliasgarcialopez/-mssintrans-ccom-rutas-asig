package mx.gob.imss.mssistrans.reasignacion.rutas.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.imss.mssistrans.reasignacion.rutas.model.ReasignacionRutasEntity;

@Repository
public interface ReasignacionRutasRepository extends JpaRepository<ReasignacionRutasEntity, Integer> {
	
	@Query(value = "SELECT RR FROM ReasignacionRutasEntity RR WHERE RR.indActivo = 1")
	Page<List<ReasignacionRutasEntity>> consulta(Pageable page);
	
	@Query(value = "SELECT * FROM SINTRANST_REASIGNACION_RUTAS WHERE ID_REASIGNACION = ? AND IND_ACTIVO = 1", nativeQuery = true)
	ReasignacionRutasEntity consultaId(int id);
	
	@Modifying(flushAutomatically = true)
	@Query(value = "UPDATE SINTRANST_REASIGNACION_RUTAS SET IND_ACTIVO = 0, FEC_BAJA = CURRENT_DATE() WHERE ID_REASIGNACION = ?", nativeQuery = true)
	void delete(int id);
	
	@Modifying(flushAutomatically = true)
	@Query(value = "UPDATE SINTRANST_REASIGNACION_RUTAS SET DES_MOTIVO_REASIGNACION = ?, "
			+ "SINIESTRO = ?, ID_VEHICULO_SUST = ?, ID_CHOFER_SUST = ?, "
			+ "FEC_ACTUALIZACION = CURRENT_DATE(), CVE_MATRICULA = ? "
			+ "WHERE ID_REASIGNACION = ? AND IND_ACTIVO = 1", nativeQuery = true)
	void actualizar(String motivo, String siniestro, Integer idVehSust, Integer idChoSust, String cveMatricula, Integer idReasignacion);
	
}
