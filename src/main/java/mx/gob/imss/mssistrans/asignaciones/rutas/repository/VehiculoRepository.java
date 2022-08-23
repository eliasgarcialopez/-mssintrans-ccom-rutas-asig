package mx.gob.imss.mssistrans.asignaciones.rutas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.imss.mssistrans.asignaciones.rutas.model.SolicitudTrasladoEntity;
import mx.gob.imss.mssistrans.asignaciones.rutas.model.VehiculoEntity;

@Repository
public interface VehiculoRepository extends JpaRepository<VehiculoEntity, Integer> {
	
    @Query(value = "SELECT SRA.ID_VEHICULO, SRA.ID_RUTA AS IDRUTA, SST.ID_SOLICITUD AS IDSOLICITUDTRASLADO, SV.CVE_ECCO, SV.NUM_PLACAS"
    		+ ", SR.ID_OOAD"
    		+ " FROM SINTRANST_RUTAS_ASIGNACIONES SRA"
    		+ " INNER JOIN SINTRANST_SOLICITUD_TRASLADO SST ON SST.ID_RUTA_ASIGNACION = SRA.ID_RUTA_ASIGNACION"
    		+ " INNER JOIN SINTRANST_VEHICULOS SV ON SV.ID_VEHICULO = SRA.ID_VEHICULO"
    		+ " INNER JOIN SINTRANST_RUTAS SR ON SRA.ID_RUTA3 = SR.ID_RUTA"
    		+ " WHERE SRA.ID_VEHICULO = ? AND SR.ID_OOAD = ?"
            , countQuery = "SELECT COUNT(SA.ID_VEHICULO)"
            + " FROM SINTRANST_RUTAS_ASIGNACIONES SRA"
            + " INNER JOIN SINTRANST_SOLICITUD_TRASLADO SST ON SST.ID_RUTA_ASIGNACION = SRA.ID_RUTA_ASIGNACION"
            + " INNER JOIN SINTRANST_VEHICULOS SV ON SV.ID_VEHICULO = SRA.ID_VEHICULO"
            + " INNER JOIN SINTRANST_RUTAS SR ON SRA.ID_RUTA3 = SR.ID_RUTA"
            + " WHERE SRA.ID_VEHICULO = ? AND SR.ID_OOAD = ?"
            ,nativeQuery = true)
    List<VehiculoEntity> getDatosAsignacion(String idVehiculo, Integer idOoad );
}
