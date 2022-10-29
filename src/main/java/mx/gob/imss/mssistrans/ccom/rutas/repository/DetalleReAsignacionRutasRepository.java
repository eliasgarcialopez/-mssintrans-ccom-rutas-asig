package mx.gob.imss.mssistrans.ccom.rutas.repository;

import mx.gob.imss.mssistrans.ccom.rutas.model.DetalleReAsignacionRutasEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleReAsignacionRutasRepository extends JpaRepository<DetalleReAsignacionRutasEntity, Integer> {
	@Query(value = "SELECT REAR.* FROM SINTRANST_REASIGNACION_RUTAS REAR\n" +
			"WHERE REAR.ID_VEHICULO =:idVehiculo AND REAR.ID_RUTA=:idRuta AND REAR.ID_CHOFER=:idChofer AND REAR.IND_ACTIVO =1"
			,nativeQuery = true)
	DetalleReAsignacionRutasEntity getReasignacionByVehiculoChoferRuta(@Param("idVehiculo") Integer idVehiculo, @Param("idRuta") Integer idRuta, @Param("idChofer") Integer idChofer);

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
    Page<List<DetalleReAsignacionRutasEntity>> consultaGeneral(Pageable page);


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
    Page<List<DetalleReAsignacionRutasEntity>> getConsultaById(String idAsignacion, String idSolicitud, Pageable pageable);

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
    Page<List<DetalleReAsignacionRutasEntity>> getConsultaByIdAsignacion(String idRutaAsig, Pageable pageable);


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
    Page<List<DetalleReAsignacionRutasEntity>> getConsultaByIdSolicitud( String idSolicitud, Pageable pageable);

	@Modifying(flushAutomatically = true)
	@Query(value = "UPDATE SINTRANST_REASIGNACION_RUTAS  SET FEC_BAJA = CURRENT_TIMESTAMP(), IND_ACTIVO = 0"
			+ " WHERE IND_ACTIVO = 1 AND ID_REASIGNACION  = ? "
			,nativeQuery = true )
	void delete ( String idReAsignacion );


	
}
