package mx.gob.imss.mssistrans.ccom.rutas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.imss.mssistrans.ccom.rutas.model.TripulacionAsigCam01Entity;
import mx.gob.imss.mssistrans.ccom.rutas.model.TripulacionAsigEntity;

@Repository
public interface TripulacionAsigCamillero01Repository extends JpaRepository<TripulacionAsigCam01Entity, Integer> {
	
    @Query(value = "SELECT SCR.ID_CONTROL_RUTA,SPA.ID_PERSONAL_AMBULANCIA, SPA.DES_NOMBRE"
    		+ " , SPA.DES_PUESTO, SPA.CVE_MATRICULA_PERSONAL, SCR.NUM_FOLIO_TJTA_COMBUSTIBLE"
    		+ " FROM SINTRANST_TRIPULACION ST"
    		+ " INNER JOIN SINTRANST_CONTROL_RUTAS SCR ON SCR.ID_TRIPULACION = ST.ID_TRIPULACION"
    		+ " INNER JOIN SINTRANST_PERSONAL_AMBULANCIA SPA ON SPA.ID_PERSONAL_AMBULANCIA = ST.ID_PERSONAL_AMBULANCIA_C1"
    		+ " WHERE SCR.ID_CONTROL_RUTA = ?"  
            ,nativeQuery = true)
    TripulacionAsigCam01Entity getCamillero1(Integer idControlRuta);
 
}
