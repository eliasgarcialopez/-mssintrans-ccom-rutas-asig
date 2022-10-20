package mx.gob.imss.mssistrans.ccom.rutas.repository;

import mx.gob.imss.mssistrans.ccom.rutas.dto.TripulacionInterfaceResponse;
import mx.gob.imss.mssistrans.ccom.rutas.model.Tripulacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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
            + "where t.idVehiculo = ?1 and t.activo = true and t.sistema = true")
    Optional<Tripulacion> findByIdVehiculo(Integer idVehiculo);


    @Query(value = "SELECT " +
            "CH.CVE_MATRICULA_CHOFER AS cveMatriculaChofer, " +
            "CH.NOM_CHOFER AS nombreChofer, " +
            "CAM1.NOM_CAMILLERO AS nombreCamillero1, " +
            "CAM1.CVE_MATRICULA AS cveMatriculaCamillero1, " +
            "CAM2.NOM_CAMILLERO AS nombreCamillero2, " +
            "CAM2.CVE_MATRICULA AS cveMatriculaCamillero2, " +
            "T.ID_VEHICULO AS idVehiculo, " +
            "T.ID_TRIPULACION as idTripulacion, " +
            "T.FEC_FECHA as fecFecha " +
            "FROM SINTRANST_TRIPULACION T " +
            "INNER JOIN SINTRANST_PERSONAL_AMBULANCIA AS c1 ON T.ID_PERSONAL_AMBULANCIA_C1 = c1.ID_PERSONAL_AMBULANCIA " +
            "INNER JOIN SINTRANST_PERSONAL_AMBULANCIA AS c2 ON T.ID_PERSONAL_AMBULANCIA_C2 = c2.ID_PERSONAL_AMBULANCIA " +
            "INNER JOIN SINTRANST_PERSONAL_AMBULANCIA AS ch ON T.ID_PERSONAL_AMBULANCIA_CHOFER = ch.ID_PERSONAL_AMBULANCIA " +
            "INNER JOIN SINTRANST_CHOFERES AS CH ON ch.ID_CHOFER = CH.ID_CHOFER " +
            "INNER JOIN SINTRANST_CAMILLERO AS CAM1 ON c1.ID_CAMILLERO = CAM1.ID_CAMILLERO " +
            "AND c1.ID_PERSONAL_AMBULANCIA = SINTRANST_TRIPULACION.ID_PERSONAL_AMBULANCIA_C1 " +
            "INNER JOIN SINTRANST_CAMILLERO AS CAM2 ON c2.ID_CAMILLERO = CAM2.ID_CAMILLERO " +
            "WHERE " +
            "T.ID_VEHICULO = :idVehiculo " +
            "AND T.IND_ACTIVO = 1 " +
            "GROUP BY " +
            "T.ID_TRIPULACION",
            nativeQuery = true)
    TripulacionInterfaceResponse findTripulacionIdVehiculo(@Param(value = "idVehiculo") Integer idVehiculo);

}
