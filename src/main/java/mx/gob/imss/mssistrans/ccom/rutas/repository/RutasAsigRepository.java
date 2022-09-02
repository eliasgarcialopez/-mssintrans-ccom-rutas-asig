package mx.gob.imss.mssistrans.ccom.rutas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.imss.mssistrans.ccom.rutas.model.RutasAsigEntity;

@Repository
public interface RutasAsigRepository extends JpaRepository<RutasAsigEntity, Integer> {

	@Query(value = "SELECT SR.ID_RUTA, SR.NUM_FOLIO_RUTA "
			+ " FROM SINTRANST_CONTROL_RUTAS SCA"
			+ " INNER JOIN SINTRANST_RUTAS SR ON SR.ID_RUTA = SCA.ID_RUTA"
			+ " WHERE SR.IND_SISTEMA = SCA.IND_SISTEMA AND SR.IND_ACTIVO = SCA.IND_ACTIVO"
			+ " AND SCA.IND_ACTIVO = 1 AND SCA.IND_SISTEMA = 1"
			,countQuery = "SELECT COUNT(SR.ID_RUTA)"
					+ " FROM SINTRANST_CONTROL_RUTAS SCA"
					+ " INNER JOIN SINTRANST_RUTAS SR ON SR.ID_RUTA = SCA.ID_RUTA"
					+ " WHERE SR.IND_SISTEMA = SCA.IND_SISTEMA AND SR.IND_ACTIVO = SCA.IND_ACTIVO"
					+ " AND SCA.IND_ACTIVO = 1 AND SCA.IND_SISTEMA = 1"
			,nativeQuery = true)
	List<RutasAsigEntity> getRutas();
	
    @Query(value = "SELECT ID_RUTA, DES_SERVICIO, NULL AS ID_SOLICITUD FROM SINTRANST_RUTAS "
    		+ " WHERE IND_ACTIVO = 1 AND IND_SISTEMA = 1 AND ID_OOAD = ?"
            , countQuery = "SELECT COUNT(ID_RUTA)"
            + " FROM SINTRANST_RUTAS"
            + " WHERE IND_ACTIVO = 1 AND IND_SISTEMA = 1 AND ID_OOAD = ?"
            ,nativeQuery = true)
    List<RutasAsigEntity> getRutasByOoad(Integer idOoad);

}
