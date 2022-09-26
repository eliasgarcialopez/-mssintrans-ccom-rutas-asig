package mx.gob.imss.mssistrans.ccom.rutas.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.imss.mssistrans.ccom.rutas.model.DatosAsigEntity;

@Repository
public interface DatosAsigRepository extends JpaRepository<DatosAsigEntity, Integer> {
	
    @Query(value = "SELECT  SA.ID_ASIGNACION, SCR.ID_VEHICULO, SCR.ID_RUTA, SCR.ID_SOLICITUD, SV.CVE_ECCO, SV.NUM_PLACAS, SCR.DES_ESTATUS_ASIGNA"
    		+ " , SCR.ID_CONTROL_RUTA"
    		+ " FROM SINTRANST_CONTROL_RUTAS SCR"
    		+ " INNER JOIN SINTRANST_VEHICULOS SV ON SV.ID_VEHICULO = SCR.ID_VEHICULO"
    		+ " INNER JOIN SINTRANST_ASIGNACIONES SA ON SA.ID_VEHICULO = SCR.ID_VEHICULO "
    		+ " WHERE SCR.IND_SISTEMA = 1 AND SCR.IND_ACTIVO = 1 AND"
    		+ " SCR.IND_ACTIVO  = SA.IND_ACTIVO AND SCR.IND_SISTEMA  = SA.IND_SISTEMA AND SCR.IND_SISTEMA = 1 AND SCR.IND_ACTIVO = 1 AND"
    		+ " SCR.ID_CONTROL_RUTA = ? "
            ,nativeQuery = true)
    DatosAsigEntity getDatosAsigByIdCtrlRuta( Integer idControlRuta );

    @Query(value = "SELECT  SA.ID_ASIGNACION, SCR.ID_VEHICULO, SCR.ID_RUTA, SCR.ID_SOLICITUD, SV.CVE_ECCO, SV.NUM_PLACAS, SCR.DES_ESTATUS_ASIGNA"
    		+ " , SCR.ID_CONTROL_RUTA"
    		+ " FROM SINTRANST_CONTROL_RUTAS SCR"
    		+ " INNER JOIN SINTRANST_VEHICULOS SV ON SV.ID_VEHICULO = SCR.ID_VEHICULO"
    		+ " INNER JOIN SINTRANST_ASIGNACIONES SA ON SA.ID_VEHICULO = SCR.ID_VEHICULO "
    		+ " WHERE SCR.IND_SISTEMA = 1 AND SCR.IND_ACTIVO = 1 AND"
    		+ " SCR.IND_ACTIVO  = SA.IND_ACTIVO AND SCR.IND_SISTEMA  = SA.IND_SISTEMA AND SCR.IND_SISTEMA = 1 AND SCR.IND_ACTIVO = 1 AND"
    		+ " SCR.ID_RUTA = ? AND SCR.ID_SOLICITUD = ? AND SCR.ID_VEHICULO = ? "
            ,nativeQuery = true)
    DatosAsigEntity getDatosAsigByidVehiculo(Integer idRuta, Integer idSolicitud, Integer idVehiculo );
    
	@Modifying(flushAutomatically = true)
	@Query(value = "UPDATE SINTRANST_CONTROL_RUTAS"
			+ " SET ID_VEHICULO = ?, ID_RUTA = ?, ID_SOLICITUD = ?, DES_ESTATUS_ASIGNA = ?"
			+ " , FEC_ACTUALIZACION  = CURRENT_TIMESTAMP()"
			+ " WHERE IND_SISTEMA = 1 AND IND_ACTIVO = 1"
			+ " AND ID_VEHICULO = ? AND ID_RUTA = ? AND ID_SOLICITUD = ? "
			,nativeQuery = true )
	void update (String idNuevoVehiculo, String idNuevaRuta, String idNuevaSolicitud, String desEstatus, String idVehiculo, String idRuta, String idSolicitud );
    
    @Query(value = "SELECT SCR.ID_CONTROL_RUTA as ID_ASIGNACION, SCR.ID_VEHICULO, SCR.ID_RUTA, SCR.ID_SOLICITUD, SV.CVE_ECCO, "
    		+ "	SV.NUM_PLACAS, SCR.DES_ESTATUS_ASIGNA, SCR.ID_CONTROL_RUTA"
    		+ " FROM SINTRANST_CONTROL_RUTAS SCR"
    		+ " INNER JOIN SINTRANST_VEHICULOS SV ON SV.ID_VEHICULO = SCR.ID_VEHICULO"
    		+ " WHERE SCR.IND_SISTEMA = 1 "
    		+ "	AND SCR.IND_ACTIVO = 1 AND SCR.IND_SISTEMA = 1 "
    		+ "	AND SCR.IND_ACTIVO = 1 AND SCR.ID_CONTROL_RUTA = ? "
            ,nativeQuery = true)
    DatosAsigEntity getDatosByIdCtrlRuta( Integer idControlRuta );
}
