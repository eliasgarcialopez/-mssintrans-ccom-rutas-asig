package mx.gob.imss.mssistrans.asignaciones.rutas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.imss.mssistrans.asignaciones.rutas.model.DatosAsigEntity;

@Repository
public interface DatosAsigRepository extends JpaRepository<DatosAsigEntity, Integer> {
	
    @Query(value = "SELECT SCA.ID_VEHICULO, SCA.ID_RUTA, SCA.ID_SOLICITUD, SV.CVE_ECCO, SV.NUM_PLACAS, SCA.DES_ESTATUS_ASIGNA"
    		+ " FROM SINTRANST_CONTROL_RUTAS SCA"
    		+ " INNER JOIN SINTRANST_VEHICULOS SV ON SV.ID_VEHICULO = SCA.ID_VEHICULO"
    		+ " WHERE SCA.IND_SISTEMA = 1 AND SCA.IND_ACTIVO = 1"
    		+ " AND SCA.ID_VEHICULO = ? AND SCA.ID_RUTA = ? AND SCA.ID_SOLICITUD = ? "
            , countQuery = "SELECT COUNT(SA.ID_VEHICULO)"
            + " FROM SINTRANST_CONTROL_RUTAS SCA"
            + " INNER JOIN SINTRANST_VEHICULOS SV ON SV.ID_VEHICULO = SCA.ID_VEHICULO"
            + " WHERE SCA.IND_SISTEMA = 1 AND SCA.IND_ACTIVO = 1"
            + " AND SCA.ID_VEHICULO = ? AND SCA.ID_RUTA = ? AND SCA.ID_SOLICITUD = ? "
            ,nativeQuery = true)
    List<DatosAsigEntity> getDatosAsignacion(Integer idVehiculo, Integer idRuta, Integer idSolicitud );
}
