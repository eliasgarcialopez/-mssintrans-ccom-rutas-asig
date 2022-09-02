package mx.gob.imss.mssistrans.ccom.rutas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.imss.mssistrans.ccom.rutas.model.TripulacionAsigEntity;

@Repository
public interface TripulacionAsigRepository extends JpaRepository<TripulacionAsigEntity, Integer> {
	
    @Query(value = "SELECT SCR.ID_CONTROL_RUTA,SPA.ID_PERSONAL_AMBULANCIA, SPA.DES_NOMBRE, SPA.DES_PUESTO"
    		+ ", SPA.CVE_MATRICULA_PERSONAL, SCR.NUM_FOLIO_TJTA_COMBUSTIBLE"
    		+ " FROM SINTRANST_TRIPULACION ST"
    		+ " INNER JOIN SINTRANST_CONTROL_RUTAS SCR ON SCR.ID_TRIPULACION = ST.ID_TRIPULACION"
    		+ " INNER JOIN SINTRANST_PERSONAL_AMBULANCIA SPA ON SPA.ID_PERSONAL_AMBULANCIA = ST.ID_PERSONAL_AMBULANCIA_CHOFER"
    		+ " WHERE SCR.ID_CONTROL_RUTA = ? "           
            ,nativeQuery = true)
    TripulacionAsigEntity getDatosChofer(Integer idControlRuta);
    

}
