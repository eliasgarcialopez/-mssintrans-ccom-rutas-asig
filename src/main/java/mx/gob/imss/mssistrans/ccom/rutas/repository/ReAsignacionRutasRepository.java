package mx.gob.imss.mssistrans.ccom.rutas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.imss.mssistrans.ccom.rutas.dto.ReAsignacionRutasDTO;
import mx.gob.imss.mssistrans.ccom.rutas.model.AsigRutasEntity;
import mx.gob.imss.mssistrans.ccom.rutas.model.ReAsignacionRutasEntity;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface ReAsignacionRutasRepository extends JpaRepository<ReAsignacionRutasEntity, Integer> {

    @Query(value = "SELECT SR.NUM_FOLIO_RUTA, SCR.ID_SOLICITUD"
    		+ ", SV.CVE_ECCO, SCR.DES_ESTATUS_ASIGNA AS DES_ESTATUS_ASIGNA, SRR.ID_REASIGNACION AS ID_REASIGNACION"
    		+ " FROM SINTRANST_RUTAS SR"
    		+ " INNER JOIN SINTRANST_CONTROL_RUTAS SCR ON SCR.ID_RUTA = SR.ID_RUTA"
    		+ " INNER JOIN SINTRANST_VEHICULOS SV ON SV.ID_VEHICULO = SCR.ID_VEHICULO"
    		+ " INNER JOIN SINTRANST_REASIGNACION_RUTAS SRR ON SRR.ID_RUTA = SCR.ID_RUTA"
    		+ " WHERE SCR.ID_VEHICULO = SRR.ID_VEHICULO  AND  SRR.IND_ACTIVO = 1 AND SRR.IND_SISTEMA = 1 "
            , countQuery = "SELECT COUNT(SR.NUM_FOLIO_RUTA)"
            		+ " FROM SINTRANST_RUTAS SR"
            		+ " INNER JOIN SINTRANST_CONTROL_RUTAS SCR ON SCR.ID_RUTA = SR.ID_RUTA"
            		+ " INNER JOIN SINTRANST_VEHICULOS SV ON SV.ID_VEHICULO = SCR.ID_VEHICULO"
            		+ " INNER JOIN SINTRANST_REASIGNACION_RUTAS SRR ON SRR.ID_RUTA = SCR.ID_RUTA"
            		+ " WHERE SCR.ID_VEHICULO = SRR.ID_VEHICULO  AND  SRR.IND_ACTIVO = 1 AND SRR.IND_SISTEMA = 1 "
            ,nativeQuery = true)
    Page<List<ReAsignacionRutasEntity>> consultaGeneral1(Pageable page);
    
    @Query(value = "SELECT SR.ID_RUTA, SR.NUM_FOLIO_RUTA AS NUM_FOLIO_RUTA, SCR.ID_SOLICITUD AS ID_SOLICITUD, SV.CVE_ECCO AS CVE_ECCO"
    		+ " , SCR.DES_ESTATUS_ASIGNA AS DES_ESTATUS_ASIGNA, SCR.ID_CONTROL_RUTA AS ID_CONTROL_RUTA, SV.ID_VEHICULO AS ID_VEHICULO"
    		+ " FROM SINTRANST_RUTAS SR"
    		+ " INNER JOIN SINTRANST_CONTROL_RUTAS SCR ON SCR.ID_RUTA = SR.ID_RUTA"
    		+ " INNER JOIN SINTRANST_VEHICULOS SV ON SV.ID_VEHICULO = SCR.ID_VEHICULO"
    		+ " WHERE SCR.IND_ACTIVO  = SR.IND_ACTIVO AND SCR.IND_SISTEMA  = SR.IND_SISTEMA AND"
    		+ " SR.IND_SISTEMA = SV.IND_SISTEMA AND SR.IND_ACTIVO = SV.IND_ACTIVO AND"
    		+ " SCR.IND_ACTIVO = 1 AND SCR.IND_SISTEMA = 1 "
            , countQuery = "SELECT COUNT(SR.ID_RUTA)"
            		+ " FROM SINTRANST_RUTAS SR"
            		+ " INNER JOIN SINTRANST_CONTROL_RUTAS SCR ON SCR.ID_RUTA = SR.ID_RUTA"
            		+ " INNER JOIN SINTRANST_VEHICULOS SV ON SV.ID_VEHICULO = SCR.ID_VEHICULO"
            		+ " WHERE SCR.IND_ACTIVO  = SR.IND_ACTIVO AND SCR.IND_SISTEMA  = SR.IND_SISTEMA AND"
            		+ " SR.IND_SISTEMA = SV.IND_SISTEMA AND SR.IND_ACTIVO = SV.IND_ACTIVO AND"
            		+ " SCR.IND_ACTIVO = 1 AND SCR.IND_SISTEMA = 1 "
            ,nativeQuery = true)
    Page<List<AsigRutasEntity>> consultaGeneral(Pageable page);

    @Query(value = "SELECT SR.NUM_FOLIO_RUTA, SCR.ID_SOLICITUD"
    		+ ", SV.CVE_ECCO, SCR.DES_ESTATUS_ASIGNA AS DES_ESTATUS_ASIGNA, SRR.ID_REASIGNACION AS ID_REASIGNACION"
    		+ " FROM SINTRANST_RUTAS SR"
    		+ " INNER JOIN SINTRANST_CONTROL_RUTAS SCR ON SCR.ID_RUTA = SR.ID_RUTA"
    		+ " INNER JOIN SINTRANST_VEHICULOS SV ON SV.ID_VEHICULO = SCR.ID_VEHICULO"
    		+ " INNER JOIN SINTRANST_REASIGNACION_RUTAS SRR ON SRR.ID_RUTA = SCR.ID_RUTA"
    		+ " WHERE SCR.ID_VEHICULO = SRR.ID_VEHICULO  AND  SRR.IND_ACTIVO = 1 AND SRR.IND_SISTEMA = 1 "
    		+ " AND SRR.ID_REASIGNACION = ? AND SCA.ID_SOLICITUD = ?"
            , countQuery = "SELECT COUNT(SRR.ID_REASIGNACION)"
            		+ " FROM SINTRANST_RUTAS SR"
    	    		+ " INNER JOIN SINTRANST_CONTROL_RUTAS SCR ON SCR.ID_RUTA = SR.ID_RUTA"
    	    		+ " INNER JOIN SINTRANST_VEHICULOS SV ON SV.ID_VEHICULO = SCR.ID_VEHICULO"
    	    		+ " INNER JOIN SINTRANST_REASIGNACION_RUTAS SRR ON SRR.ID_RUTA = SCR.ID_RUTA"
    	    		+ " WHERE SCR.ID_VEHICULO = SRR.ID_VEHICULO  AND  SRR.IND_ACTIVO = 1 AND SRR.IND_SISTEMA = 1 "
            + " AND SR.NUM_FOLIO_RUTA = ? AND SCA.ID_SOLICITUD = ?"
            , nativeQuery = true)
    Page<List<ReAsignacionRutasEntity>> getConsultaById(String idAsignacion, String idSolicitud, Pageable pageable);

    @Query(value = "SELECT SR.NUM_FOLIO_RUTA, SCR.ID_SOLICITUD"
    	    		+ ", SV.CVE_ECCO, SCR.DES_ESTATUS_ASIGNA AS DES_ESTATUS_ASIGNA, SRR.ID_REASIGNACION AS ID_REASIGNACION"
    	    		+ " FROM SINTRANST_RUTAS SR"
    	    		+ " INNER JOIN SINTRANST_CONTROL_RUTAS SCR ON SCR.ID_RUTA = SR.ID_RUTA"
    	    		+ " INNER JOIN SINTRANST_VEHICULOS SV ON SV.ID_VEHICULO = SCR.ID_VEHICULO"
    	    		+ " INNER JOIN SINTRANST_REASIGNACION_RUTAS SRR ON SRR.ID_RUTA = SCR.ID_RUTA"
    	    		+ " WHERE SCR.ID_VEHICULO = SRR.ID_VEHICULO  AND  SRR.IND_ACTIVO = 1 AND SRR.IND_SISTEMA = 1 "
    		+ " AND SR.NUM_FOLIO_RUTA  = ? "
            , countQuery = "SELECT COUNT(SRR.ID_REASIGNACION)"
            		+ " FROM SINTRANST_RUTAS SR"
    	    		+ " INNER JOIN SINTRANST_CONTROL_RUTAS SCR ON SCR.ID_RUTA = SR.ID_RUTA"
    	    		+ " INNER JOIN SINTRANST_VEHICULOS SV ON SV.ID_VEHICULO = SCR.ID_VEHICULO"
    	    		+ " INNER JOIN SINTRANST_REASIGNACION_RUTAS SRR ON SRR.ID_RUTA = SCR.ID_RUTA"
    	    		+ " WHERE SCR.ID_VEHICULO = SRR.ID_VEHICULO  AND  SRR.IND_ACTIVO = 1 AND SRR.IND_SISTEMA = 1 "
            + " AND SRR.ID_REASIGNACION  = ? "
            , nativeQuery = true)
    Page<List<ReAsignacionRutasEntity>> getConsultaByIdAsignacion(String idRutaAsig, Pageable pageable);


    @Query(value = "SELECT SR.NUM_FOLIO_RUTA, SCR.ID_SOLICITUD"
    		+ ", SV.CVE_ECCO, SCR.DES_ESTATUS_ASIGNA AS DES_ESTATUS_ASIGNA, SRR.ID_REASIGNACION AS ID_REASIGNACION"
    		+ " FROM SINTRANST_RUTAS SR"
    		+ " INNER JOIN SINTRANST_CONTROL_RUTAS SCR ON SCR.ID_RUTA = SR.ID_RUTA"
    		+ " INNER JOIN SINTRANST_VEHICULOS SV ON SV.ID_VEHICULO = SCR.ID_VEHICULO"
    		+ " INNER JOIN SINTRANST_REASIGNACION_RUTAS SRR ON SRR.ID_RUTA = SCR.ID_RUTA"
    		+ " WHERE SCR.ID_VEHICULO = SRR.ID_VEHICULO  AND  SRR.IND_ACTIVO = 1 AND SRR.IND_SISTEMA = 1 "
    		+ " AND SCA.ID_SOLICITUD = ? "
            , countQuery = "SELECT COUNT(SRR.ID_REASIGNACION)"
            		+ " FROM SINTRANST_RUTAS SR"
            		+ " INNER JOIN SINTRANST_CONTROL_RUTAS SCR ON SCR.ID_RUTA = SR.ID_RUTA"
            		+ " INNER JOIN SINTRANST_VEHICULOS SV ON SV.ID_VEHICULO = SCR.ID_VEHICULO"
            		+ " INNER JOIN SINTRANST_REASIGNACION_RUTAS SRR ON SRR.ID_RUTA = SCR.ID_RUTA"
            		+ " WHERE SCR.ID_VEHICULO = SRR.ID_VEHICULO  AND  SRR.IND_ACTIVO = 1 AND SRR.IND_SISTEMA = 1 "
            		+ " AND SCA.ID_SOLICITUD = ? "
            , nativeQuery = true)
    Page<List<ReAsignacionRutasEntity>> getConsultaByIdSolicitud( String idSolicitud, Pageable pageable);

	@Modifying(flushAutomatically = true)
	@Query(value = "UPDATE SINTRANST_REASIGNACION_RUTAS  SET FEC_BAJA = CURRENT_TIMESTAMP(), IND_ACTIVO = 0"
			+ " WHERE IND_ACTIVO = 1 AND ID_REASIGNACION  = ? "
			,nativeQuery = true )
	void delete ( String idReAsignacion );
	@Modifying(flushAutomatically = true)
	@Query( value = "INSERT INTO SINTRANST_REASIGNACION_RUTAS (ID_VEHICULO, ID_RUTA, ID_CHOFER"
			+ ", DES_MOTIVO_REASIGNACION, DES_SINIESTRO, ID_VEHICULO_SUST, ID_CHOFER_SUST, ID_ASIGNACION, CVE_MATRICULA"
			+ ", FEC_ALTA, IND_ACTIVO, IND_SISTEMA) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP(), 1, 1)"
		,nativeQuery = true)
	void save (Integer idVehiculo, Integer idRuta, Integer idChofer, String desMotivoReasig, String desSiniestro, Integer idVehiculoSust, Integer idChoferSust, Integer idAsignacion, String cveMatricula);

	@Modifying(flushAutomatically = true)
	@Query( value = "UPDATE SINTRANST_REASIGNACION_RUTAS SET DES_SINIESTRO = ?, ID_VEHICULO_SUST = ?"
			+ " , DES_MOTIVO_REASIGNACION = ?, FEC_ACTUALIZACION = CURRENT_TIMESTAMP()"
			+ " WHERE ID_VEHICULO = ? AND ID_RUTA = ? AND ID_CHOFER = ? AND DATE(FEC_ALTA) = CURRENT_DATE()"
		,nativeQuery = true)
	void update (String desSiniestro, Integer idVehoculoSust, String desMotivoReasignacion, Integer idVehiculo, Integer idRuta, Integer idChofer);
	@Modifying(flushAutomatically = true)
	@Query( value = "UPDATE SINTRANST_REASIGNACION_RUTAS SET ID_VEHICULO_SUST = ?"
			+ " , DES_MOTIVO_REASIGNACION = ?, FEC_ACTUALIZACION = CURRENT_TIMESTAMP()"
			+ " WHERE ID_VEHICULO = ? AND ID_RUTA = ? AND ID_CHOFER = ? AND DATE(FEC_ALTA) = CURRENT_DATE()"
		,nativeQuery = true)
	void update ( Integer idVehoculoSust, String desMotivoReasignacion, Integer idVehiculo, Integer idRuta, Integer idChofer);
	@Modifying(flushAutomatically = true)
	@Query( value = "UPDATE SINTRANST_REASIGNACION_RUTAS SET ID_VEHICULO_SUST = ?"
			+ " , DES_MOTIVO_REASIGNACION = ?, FEC_ACTUALIZACION = CURRENT_TIMESTAMP()"
			+ " WHERE ID_VEHICULO = ? AND ID_RUTA = ? AND ID_CHOFER = ? AND DATE(FEC_ALTA) = CURRENT_DATE()"
		,nativeQuery = true)
	void update (String desSiniestro, String desMotivoReasignacion, Integer idVehiculo, Integer idRuta, Integer idChofer);
	@Modifying(flushAutomatically = true)
	@Query( value = "UPDATE SINTRANST_REASIGNACION_RUTAS SET DES_SINIESTRO = ?, ID_VEHICULO_SUST = ?"
			+ " , FEC_ACTUALIZACION = CURRENT_TIMESTAMP()"
			+ " WHERE ID_VEHICULO = ? AND ID_RUTA = ? AND ID_CHOFER = ? AND DATE(FEC_ALTA) = CURRENT_DATE()"
		,nativeQuery = true)
	void update (String desSiniestro, Integer idVehoculoSust, Integer idVehiculo, Integer idRuta, Integer idChofer);
	@Modifying(flushAutomatically = true)
	@Query( value = "UPDATE SINTRANST_REASIGNACION_RUTAS SET DES_SINIESTRO = ?, FEC_ACTUALIZACION = CURRENT_TIMESTAMP()"
			+ " WHERE ID_VEHICULO = ? AND ID_RUTA = ? AND ID_CHOFER = ? AND DATE(FEC_ALTA) = CURRENT_DATE()"
		,nativeQuery = true)
	void update (String desSiniestro, Integer idVehiculo, Integer idRuta, Integer idChofer);
	@Modifying(flushAutomatically = true)
	@Query( value = "UPDATE SINTRANST_REASIGNACION_RUTAS SET ID_VEHICULO_SUST = ?"
			+ " , FEC_ACTUALIZACION = CURRENT_TIMESTAMP()"
			+ " WHERE ID_VEHICULO = ? AND ID_RUTA = ? AND ID_CHOFER = ? AND DATE(FEC_ALTA) = CURRENT_DATE()"
		,nativeQuery = true)
	void update (Integer idVehoculoSust, Integer idVehiculo, Integer idRuta, Integer idChofer);
	@Modifying(flushAutomatically = true)
	@Query( value = "UPDATE SINTRANST_REASIGNACION_RUTAS SET DES_MOTIVO_REASIGNACION = ?, FEC_ACTUALIZACION = CURRENT_TIMESTAMP()"
			+ " WHERE ID_VEHICULO = ? AND ID_RUTA = ? AND ID_CHOFER = ? AND DATE(FEC_ALTA) = CURRENT_DATE()"
		,nativeQuery = true)
	void updateReasig ( String desMotivoReasignacion, Integer idVehiculo, Integer idRuta, Integer idChofer);

	
}
