package mx.gob.imss.mssistrans.ccom.rutas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import mx.gob.imss.mssistrans.ccom.rutas.model.ReasignacionEccoEntity;


@Repository
public interface ReasignacionEccoRepository extends JpaRepository<ReasignacionEccoEntity, Integer> {

    @Query(value = "SELECT SA.ID_ASIGNACION, SV.ID_VEHICULO, SV.CVE_ECCO, SV.NUM_PLACAS,SCR.ID_RUTA,SCR.ID_SOLICITUD"
    		+ " FROM SINTRANST_RUTAS SR"
    		+ " INNER JOIN SINTRANST_CONTROL_RUTAS SCR ON SCR.ID_RUTA = SR.ID_RUTA"
    		+ " INNER JOIN SINTRANST_VEHICULOS SV ON SCR.ID_VEHICULO = SV.ID_VEHICULO"
    		+ " LEFT JOIN SINTRANST_ASIGNACIONES SA ON SA.ID_VEHICULO = SCR.ID_VEHICULO "
    		+ " WHERE SR.IND_ACTIVO = 1 AND SCR.IND_ACTIVO = 1 AND SV.IND_ACTIVO = 1"
    		+ " AND SCR.IND_SISTEMA = 1"
    		+ " AND SCR.FEC_INICIO_ASIGNA = CURRENT_DATE()"
            , countQuery = "SELECT COUNT(SV.ID_VEHICULO)"
            		+ " FROM SINTRANST_RUTAS SR"
            		+ " INNER JOIN SINTRANST_CONTROL_RUTAS SCR ON SCR.ID_RUTA = SR.ID_RUTA"
            		+ " INNER JOIN SINTRANST_VEHICULOS SV ON SCR.ID_VEHICULO = SV.ID_VEHICULO"
            		+ " LEFT JOIN SINTRANST_ASIGNACIONES SA ON SA.ID_VEHICULO = SCR.ID_VEHICULO"
            		+ " WHERE SR.IND_ACTIVO = 1 AND SCR.IND_ACTIVO = 1 AND SV.IND_ACTIVO = 1 "
            		+ " AND SCR.IND_SISTEMA = 1 "
            		+ " AND SCR.FEC_INICIO_ASIGNA = CURRENT_DATE()"
            ,nativeQuery = true)
    List<ReasignacionEccoEntity> getEcco();

}
