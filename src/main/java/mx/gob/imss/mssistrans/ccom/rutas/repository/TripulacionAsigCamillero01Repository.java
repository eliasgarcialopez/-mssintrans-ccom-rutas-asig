package mx.gob.imss.mssistrans.ccom.rutas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.imss.mssistrans.ccom.rutas.model.TripulacionAsigCam01Entity;

@Repository
public interface TripulacionAsigCamillero01Repository extends JpaRepository<TripulacionAsigCam01Entity, Integer> {

    @Query(value = "SELECT  SCR.ID_CONTROL_RUTA,SPA.ID_PERSONAL_AMBULANCIA, SC.NOM_CAMILLERO, SPA.DES_PUESTO"
            + " , SC.CVE_MATRICULA, SCR.NUM_FOLIO_TJTA_COMBUSTIBLE"
            + " FROM SINTRANST_CONTROL_RUTAS SCR"
            + " INNER JOIN SINTRANST_TRIPULACION ST ON SCR.ID_TRIPULACION = ST.ID_TRIPULACION"
            + " INNER JOIN SINTRANST_PERSONAL_AMBULANCIA SPA ON ST.ID_PERSONAL_AMBULANCIA_C1 = SPA.ID_PERSONAL_AMBULANCIA"
            + " INNER JOIN SINTRANST_CAMILLERO SC ON SC.ID_CAMILLERO  = SPA.ID_CAMILLERO"
            + " LEFT JOIN SINTRANST_REASIGNACION_RUTAS SRR ON (SRR.ID_RUTA = SCR.ID_RUTA AND SRR.ID_CHOFER = ST.ID_PERSONAL_AMBULANCIA_CHOFER AND SRR.ID_VEHICULO = SCR.ID_VEHICULO)"
            + " WHERE SCR.IND_ACTIVO  = 1 AND SCR.IND_SISTEMA = 1"
            + " AND SCR.ID_CONTROL_RUTA = ?"
            , nativeQuery = true)
    TripulacionAsigCam01Entity getCamillero1(Integer idControlRuta);


    @Query(value = "SELECT  SCR.ID_CONTROL_RUTA,SPA.ID_PERSONAL_AMBULANCIA, SC.NOM_CAMILLERO, SPA.DES_PUESTO, " +
            "SC.CVE_MATRICULA, SCR.NUM_FOLIO_TJTA_COMBUSTIBLE "
            + "FROM SINTRANST_CONTROL_RUTAS SCR "
            + "INNER JOIN SINTRANST_TRIPULACION ST ON SCR.ID_TRIPULACION = ST.ID_TRIPULACION "
            + "INNER JOIN SINTRANST_PERSONAL_AMBULANCIA SPA ON ST.ID_PERSONAL_AMBULANCIA_C1 = SPA.ID_PERSONAL_AMBULANCIA "
            + "INNER JOIN SINTRANST_CAMILLERO SC ON SC.ID_CAMILLERO  = SPA.ID_CAMILLERO "
            + "LEFT JOIN SINTRANST_REASIGNACION_RUTAS SRR ON (SRR.ID_RUTA = SCR.ID_RUTA AND SRR.ID_CHOFER = ST.ID_PERSONAL_AMBULANCIA_CHOFER AND SRR.ID_VEHICULO = SCR.ID_VEHICULO) "
            + "WHERE SCR.IND_ACTIVO = 1  AND SCR.IND_SISTEMA = 1 "
            + "AND SCR.ID_RUTA = ? AND SCR.ID_SOLICITUD = ? AND SCR.ID_VEHICULO = ?"
            , nativeQuery = true)
    TripulacionAsigCam01Entity getCamillero1ByIdVehiculo(Integer idRuta, Integer idSolicitud, Integer idVehiculo);
}
