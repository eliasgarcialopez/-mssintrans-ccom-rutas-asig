package mx.gob.imss.mssistrans.asignaciones.rutas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.imss.mssistrans.asignaciones.rutas.model.TripulacionAsigCam01Entity;
import mx.gob.imss.mssistrans.asignaciones.rutas.model.TripulacionAsigEntity;

@Repository
public interface TripulacionAsigCamillero01Repository extends JpaRepository<TripulacionAsigCam01Entity, Integer> {
	
    @Query(value = "SELECT SCA.ID_VEHICULO, SPA.DES_NOMBRE,  SPA.CVE_MATRICULA_PERSONAL, SCA.NUM_FOLIO_TJTA_COMBUSTIBLE"
    		+ " FROM SINTRANST_CONTROL_RUTAS SCA"
    		+ " INNER JOIN SINTRANST_TRIPULACION ST ON ST.ID_TRIPULACION = SCA.ID_TRIPULACION"
    		+ " LEFT OUTER JOIN SINTRANST_CHOFERES SC ON SC.ID_CHOFER = ST.ID_PERSONAL_AMBULANCIA_CHOFER"
    		+ " INNER JOIN SINTRANST_PERSONAL_AMBULANCIA SPA ON SPA.ID_PERSONAL_AMBULANCIA = ST.ID_PERSONAL_AMBULANCIA_C1"
    		+ " WHERE SCA.IND_SISTEMA = 1 AND SCA.IND_ACTIVO = 1"
    		+ " AND SCA.ID_VEHICULO = ? AND SCA.ID_RUTA = ? AND SCA.ID_SOLICITUD = ?"  
            ,nativeQuery = true)
    TripulacionAsigCam01Entity getCamillero1(Integer idVehiculo, Integer idRuta, Integer idSolicitud);
 
}
