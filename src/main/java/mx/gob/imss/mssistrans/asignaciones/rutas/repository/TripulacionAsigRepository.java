package mx.gob.imss.mssistrans.asignaciones.rutas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.imss.mssistrans.asignaciones.rutas.model.TripulacionAsigEntity;

@Repository
public interface TripulacionAsigRepository extends JpaRepository<TripulacionAsigEntity, Integer> {
	
    @Query(value = "SELECT SCA.ID_VEHICULO, SC.NOM_CHOFER,  SC.CVE_MATRICULA_CHOFER, SCA.NUM_FOLIO_TJTA_COMBUSTIBLE"
    		+ " FROM SINTRANST_CONTROL_RUTAS SCA"
    		+ " INNER JOIN SINTRANST_TRIPULACION ST ON ST.ID_TRIPULACION = SCA.ID_TRIPULACION"
    		+ " INNER JOIN SINTRANST_CHOFERES SC ON SC.ID_CHOFER = ST.ID_PERSONAL_AMBULANCIA_CHOFER"
    		+ " INNER JOIN SINTRANST_PERSONAL_AMBULANCIA SPA ON SPA.ID_PERSONAL_AMBULANCIA = ST.ID_PERSONAL_AMBULANCIA_CHOFER"
    		+ " WHERE SCA.IND_ACTIVO = 1 AND SCA.IND_SISTEMA = 1  AND SCA.ID_RUTA = ? AND SCA.ID_VEHICULO = ? "
           
            ,nativeQuery = true)
    TripulacionAsigEntity getDatosChofer( Integer idRuta, Integer idVehiculo);
    

}
