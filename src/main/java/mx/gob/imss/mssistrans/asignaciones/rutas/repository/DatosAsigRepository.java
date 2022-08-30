package mx.gob.imss.mssistrans.asignaciones.rutas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

	@Modifying(flushAutomatically = true)
	@Query(value = "UPDATE SINTRANST_CONTROL_RUTAS"
			+ " SET ID_VEHICULO = ?, ID_RUTA = ?, ID_SOLICITUD = ?, DES_ESTATUS_ASIGNA = ?"
			+ " , FEC_ACTUALIZACION  = CURRENT_TIMESTAMP()"
			+ " WHERE IND_SISTEMA = 1 AND IND_ACTIVO = 1"
			+ " AND ID_VEHICULO = ? AND ID_RUTA = ? AND ID_SOLICITUD = ? "
			,nativeQuery = true )
	void update (String idNuevoVehiculo, String idNuevaRuta, String idNuevaSolicitud, String desEstatus, String idVehiculo, String idRuta, String idSolicitud );
    
}
