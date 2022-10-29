package mx.gob.imss.mssistrans.ccom.rutas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mx.gob.imss.mssistrans.ccom.rutas.model.DatosControlRutasEntity;

public interface DatosControlRutasRepository extends JpaRepository<DatosControlRutasEntity, Integer> {
	
	@Query(value = "SELECT SCR.ID_CONTROL_RUTA, SCR.ID_VEHICULO, SCR.ID_RUTA, SCR.ID_SOLICITUD, SV.CVE_ECCO, SV.NUM_PLACAS, RR.DES_SINIESTRO AS DES_ESTATUS_ASIGNA"
    		+ " FROM SINTRANST_CONTROL_RUTAS SCR"
    		+ " INNER JOIN SINTRANST_VEHICULOS SV ON SV.ID_VEHICULO = SCR.ID_VEHICULO"
			+ " INNER JOIN SINTRANST_REASIGNACION_RUTAS RR ON (RR.ID_RUTA=SCR.ID_RUTA AND RR.ID_VEHICULO=SCR.ID_VEHICULO) "
    		+ " WHERE SCR.IND_ACTIVO = 1 "
    		+ " AND SCR.ID_CONTROL_RUTA = ? "
            ,nativeQuery = true)
	DatosControlRutasEntity getDatosAsigByIdCtrlRuta(Integer idControlRuta);

    @Query(value = "SELECT SRC.ID_CONTROL_RUTA, SCR.ID_VEHICULO, SCR.ID_RUTA, SCR.ID_SOLICITUD, SV.CVE_ECCO, SV.NUM_PLACAS, SCR.DES_ESTATUS_ASIGNA"
    		+ " FROM SINTRANST_CONTROL_RUTAS SCR"
    		+ " INNER JOIN SINTRANST_VEHICULOS SV ON SV.ID_VEHICULO = SCR.ID_VEHICULO"
    		+ " WHERE SCR.IND_ACTIVO = 1 AND"
    		+ " AND SCR.ID_RUTA = ? AND SCR.ID_SOLICITUD = ? AND SCR.ID_VEHICULO = ? "
            ,nativeQuery = true)
    DatosControlRutasEntity getDatosAsigByidVehiculo(Integer idRuta, Integer idSolicitud, Integer idVehiculo );

}
