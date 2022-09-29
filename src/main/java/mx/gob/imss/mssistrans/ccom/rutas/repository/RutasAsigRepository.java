package mx.gob.imss.mssistrans.ccom.rutas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.imss.mssistrans.ccom.rutas.model.RutasAsigEntity;

@Repository
public interface RutasAsigRepository extends JpaRepository<RutasAsigEntity, Integer> {

	@Query(value = "SELECT SR.ID_RUTA, SR.NUM_FOLIO_RUTA, " +
			"SR.TIM_HORARIO_INICIAL, SR.TIM_HORARIO_FINAL"
			+ " FROM SINTRANST_CONTROL_RUTAS SCR"
			+ " INNER JOIN SINTRANST_RUTAS SR ON SR.ID_RUTA = SCR.ID_RUTA"
			+ " WHERE SR.IND_SISTEMA = SCR.IND_SISTEMA AND SR.IND_ACTIVO = SCR.IND_ACTIVO"
			+ " AND SCR.IND_ACTIVO = 1 AND SCR.IND_SISTEMA = 1"
			,countQuery = "SELECT COUNT(SR.ID_RUTA)"
					+ " FROM SINTRANST_CONTROL_RUTAS SCR"
					+ " INNER JOIN SINTRANST_RUTAS SR ON SR.ID_RUTA = SCR.ID_RUTA"
					+ " WHERE SR.IND_SISTEMA = SCR.IND_SISTEMA AND SR.IND_ACTIVO = SCR.IND_ACTIVO"
					+ " AND SCR.IND_ACTIVO = 1 AND SCR.IND_SISTEMA = 1"
			,nativeQuery = true)
	List<RutasAsigEntity> getRutas();
	
    @Query(value =  "SELECT SR.ID_RUTA, SR.NUM_FOLIO_RUTA, " +
			"SR.TIM_HORARIO_INICIAL, SR.TIM_HORARIO_FINAL"
			+ " FROM SINTRANST_CONTROL_RUTAS SCR"
			+ " INNER JOIN SINTRANST_RUTAS SR ON SR.ID_RUTA = SCR.ID_RUTA"
			+ " INNER JOIN SINTRANST_MODULO_AMBULANCIA MA ON MA.ID_MODULO_AMBULANCIA  = SCR.ID_MODULO_AMBULANCIA"
			+ " WHERE SCR.IND_ACTIVO = 1 AND SR.IND_ACTIVO = 1 AND SCR.IND_SISTEMA = 1"
			+ " AND MA.IND_ACTIVO = 1 AND MA.ID_OOAD = ?"
            , countQuery = "SELECT COUNT(SR.ID_RUTA)"
        			+ " FROM SINTRANST_CONTROL_RUTAS SCR"
        			+ " INNER JOIN SINTRANST_RUTAS SR ON SR.ID_RUTA = SCR.ID_RUTA"
        			+ " INNER JOIN SINTRANST_MODULO_AMBULANCIA MA ON MA.ID_MODULO_AMBULANCIA  = SCR.ID_MODULO_AMBULANCIA"
        			+ " WHERE SCR.IND_ACTIVO = 1 AND SR.IND_ACTIVO = 1 AND SCR.IND_SISTEMA = 1"
        			+ " AND MA.IND_ACTIVO = 1 AND MA.ID_OOAD = ?"
            ,nativeQuery = true)
    List<RutasAsigEntity> getRutasByOoad(Integer idOoad);
    

}
