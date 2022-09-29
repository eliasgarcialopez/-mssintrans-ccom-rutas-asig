package mx.gob.imss.mssistrans.ccom.rutas.repository;

import java.util.Optional;

import mx.gob.imss.mssistrans.ccom.rutas.dto.TripulacionInterfaceResponse;
import mx.gob.imss.mssistrans.ccom.rutas.model.Tripulacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TripulacionRepository extends JpaRepository<Tripulacion, Integer> {
	 
    /**
     * Recupera la tripulacion por id Vehiculo activa .
     *
     * @param idVehiculo
     * @param activo
     * @return
     */
	@Query(value = "select t from Tripulacion t "
			     + "where t.idVehiculo = ?1 and t.activo = 1 and t.indiceSistema=1 ")
	Optional<Tripulacion> findByIdVehiculo(Integer idVehiculo);
	
	
	
	@Query(value = "SELECT\n" +
    		"	SINTRANST_CHOFERES.CVE_MATRICULA_CHOFER AS cveMatriculaChofer,\n" +
    		"	SINTRANST_CHOFERES.NOM_CHOFER AS nombreChofer,\n" +
    		"	SINTRANST_CAMILLERO.NOM_CAMILLERO AS nombreCamillero1,\n" +
    		"	SINTRANST_CAMILLERO.CVE_MATRICULA AS cveMatriculaCamillero1,\n" +
    		"	cam1.NOM_CAMILLERO AS nombreCamillero2,\n" +
    		"	cam1.CVE_MATRICULA AS cveMatriculaCamillero2,\n" +
    		"	SINTRANST_TRIPULACION.ID_VEHICULO AS idVehiculo,\n" +
    		"	SINTRANST_TRIPULACION.ID_TRIPULACION as idTripulacion,\n" +
    		"	SINTRANST_TRIPULACION.FEC_FECHA as fecFecha\n" +
    		"FROM\n" +
    		"	SINTRANST_TRIPULACION\n" +
    		"	INNER JOIN SINTRANST_PERSONAL_AMBULANCIA AS c1 ON SINTRANST_TRIPULACION.ID_PERSONAL_AMBULANCIA_C1 = c1.ID_PERSONAL_AMBULANCIA\n" +
    		"	INNER JOIN SINTRANST_PERSONAL_AMBULANCIA AS c2 ON SINTRANST_TRIPULACION.ID_PERSONAL_AMBULANCIA_C2 = c2.ID_PERSONAL_AMBULANCIA\n" +
    		"	INNER JOIN SINTRANST_PERSONAL_AMBULANCIA AS ch ON SINTRANST_TRIPULACION.ID_PERSONAL_AMBULANCIA_CHOFER = ch.ID_PERSONAL_AMBULANCIA\n" +
    		"	INNER JOIN SINTRANST_CHOFERES ON ch.ID_CHOFER = SINTRANST_CHOFERES.ID_CHOFER\n" +
    		"	INNER JOIN SINTRANST_CAMILLERO ON c1.ID_CAMILLERO = SINTRANST_CAMILLERO.ID_CAMILLERO \n" +
    		"	AND c1.ID_PERSONAL_AMBULANCIA = SINTRANST_TRIPULACION.ID_PERSONAL_AMBULANCIA_C1\n" +
    		"	INNER JOIN SINTRANST_CAMILLERO AS cam1 ON c2.ID_CAMILLERO = cam1.ID_CAMILLERO \n" +
    		"WHERE\n" +
    		"	SINTRANST_TRIPULACION.ID_VEHICULO = :idVehiculo \n" +
    	    "AND SINTRANST_TRIPULACION.IND_ACTIVO = 1 \n" +
    		"GROUP BY\n" +
    		"	SINTRANST_TRIPULACION.ID_TRIPULACION",
  		 nativeQuery = true)
    TripulacionInterfaceResponse findTripulacionIdVehiculo(@Param(value ="idVehiculo")Integer idVehiculo);

}
