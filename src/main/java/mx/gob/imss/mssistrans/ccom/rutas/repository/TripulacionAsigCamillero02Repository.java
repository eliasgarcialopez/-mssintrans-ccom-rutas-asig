package mx.gob.imss.mssistrans.ccom.rutas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.imss.mssistrans.ccom.rutas.model.TripulacionAsigCam02Entity;

@Repository
public interface TripulacionAsigCamillero02Repository extends JpaRepository<TripulacionAsigCam02Entity, Integer> {

    @Query(value = "SELECT  SCR.ID_CONTROL_RUTA,SPA.ID_PERSONAL_AMBULANCIA, SC.NOM_CAMILLERO, SPA.DES_PUESTO"
    		+ " , SC.CVE_MATRICULA, SCR.NUM_FOLIO_TJTA_COMBUSTIBLE"
    		+ "  FROM SINTRANST_CONTROL_RUTAS SCR"
    		+ " INNER JOIN SINTRANST_TRIPULACION ST ON SCR.ID_TRIPULACION = ST.ID_TRIPULACION"
    		+ " INNER JOIN SINTRANST_PERSONAL_AMBULANCIA SPA ON ST.ID_PERSONAL_AMBULANCIA_C2 = SPA.ID_PERSONAL_AMBULANCIA"
    		+ " INNER JOIN SINTRANST_CAMILLERO SC ON SC.ID_CAMILLERO  = SPA.ID_CAMILLERO"
    		+ " WHERE SCR.ID_CONTROL_RUTA =  ?" 
            ,nativeQuery = true)
    TripulacionAsigCam02Entity getCamillero2(Integer idControlRuta);
    
    
    @Query(value = "SELECT  SCR.ID_CONTROL_RUTA,SPA.ID_PERSONAL_AMBULANCIA, SC.NOM_CAMILLERO, SPA.DES_PUESTO"
    		+ " , SC.CVE_MATRICULA, SCR.NUM_FOLIO_TJTA_COMBUSTIBLE"
    		+ "  FROM SINTRANST_CONTROL_RUTAS SCR"
    		+ " INNER JOIN SINTRANST_TRIPULACION ST ON SCR.ID_TRIPULACION = ST.ID_TRIPULACION"
    		+ " INNER JOIN SINTRANST_PERSONAL_AMBULANCIA SPA ON ST.ID_PERSONAL_AMBULANCIA_C2 = SPA.ID_PERSONAL_AMBULANCIA"
    		+ " INNER JOIN SINTRANST_CAMILLERO SC ON SC.ID_CAMILLERO  = SPA.ID_CAMILLERO"
    		+ " WHERE SCR.IND_SISTEMA = 1 AND SCR.IND_ACTIVO = 1 AND"
    		+ " SCR.ID_RUTA = ? AND SCR.ID_SOLICITUD = ? AND SCR.ID_VEHICULO = ? "
            ,nativeQuery = true)
    TripulacionAsigCam02Entity getCamillero2ByIdVehiculo(Integer idRuta, Integer idSolicitud, Integer idVehiculo);;
    
}
