package mx.gob.imss.mssistrans.ccom.rutas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.imss.mssistrans.ccom.rutas.model.SolTrasladoEntity;



@Repository
public interface SolTrasladoRepository extends JpaRepository<SolTrasladoEntity, Integer> {
	@Query(value = "SELECT SST.ID_SOLICITUD , SST.NUM_FOLIO_ACEPTACION , SCR.ID_RUTA"
			+ " FROM SINTRANST_CONTROL_RUTAS SCR"
			+ " INNER JOIN SINTRANST_SOLICITUD_TRASLADO SST ON SST.ID_SOLICITUD = SCR.ID_SOLICITUD"
			+ " WHERE SST.IND_ACTIVO = 1 AND  SST.IND_SISTEMA = 1 AND"
			+ " SST.IND_ACTIVO = SCR.IND_ACTIVO AND SST.IND_SISTEMA = SCR.IND_SISTEMA"
			+ " AND SCR.ID_RUTA = ?"
			,countQuery = "SELECT COUNT(SCR.ID_CONTROL_RUTA)"
					+ " FROM SINTRANST_CONTROL_RUTAS SCR"
					+ " INNER JOIN SINTRANST_SOLICITUD_TRASLADO SST ON SST.ID_SOLICITUD = SCR.ID_SOLICITUD"
					+ " WHERE SST.IND_ACTIVO = 1 AND  SST.IND_SISTEMA = 1 AND"
					+ " SST.IND_ACTIVO = SCR.IND_ACTIVO AND SST.IND_SISTEMA = SCR.IND_SISTEMA"
					+ " AND SCR.ID_RUTA = ?"
			, nativeQuery = true)
	List<SolTrasladoEntity> getSolicitudTraslado(Integer idRuta);
	
	
    @Query(value = "SELECT SST.ID_SOLICITUD , SST.NUM_FOLIO_ACEPTACION "
    		+ " FROM SINTRANST_CONTROL_RUTAS SCA"
    		+ " INNER JOIN SINTRANST_SOLICITUD_TRASLADO SST ON SST.ID_SOLICITUD = SCA.ID_SOLICITUD"
    		+ "	WHERE SST.IND_ACTIVO = 1 AND  SST.IND_SISTEMA = 1 "
    		+ " AND SR.ID_OOAD = ? AND SR.ID_RUTA = ? "
            , countQuery = "SELECT COUNT(SST.ID_SOLICITUD)"
            		+ " FROM SINTRANST_CONTROL_RUTAS SCA"
            		+ " INNER JOIN SINTRANST_SOLICITUD_TRASLADO SST ON SST.ID_SOLICITUD = SCA.ID_SOLICITUD"
            		+ "	WHERE SST.IND_ACTIVO = 1 AND  SST.IND_SISTEMA = 1 "
            		+ " AND SR.ID_OOAD = ? AND SR.ID_RUTA = ? "
            ,nativeQuery = true)
    List<SolTrasladoEntity> getSolicitudTraslado(Integer idOoad, Integer idRuta);

}
