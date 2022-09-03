package mx.gob.imss.mssistrans.ccom.rutas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.imss.mssistrans.ccom.rutas.model.TripulacionAsigCam01Entity;
import mx.gob.imss.mssistrans.ccom.rutas.model.TripulacionAsigCam02Entity;
import mx.gob.imss.mssistrans.ccom.rutas.model.TripulacionAsigEntity;

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
    TripulacionAsigCam02Entity getDatosCamillero2(Integer idControlRuta);

}
