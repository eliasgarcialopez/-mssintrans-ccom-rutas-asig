package mx.gob.imss.mssistrans.asignaciones.rutas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.imss.mssistrans.asignaciones.rutas.model.AsigRutasEntity;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface AsigRutasRepository extends JpaRepository<AsigRutasEntity, Integer> {

    @Query(value = "SELECT SRA.ID_RUTA_ASIGNACION, SST.ID_SOLICITUD, SV.CVE_ECCO, SST.DES_ESTATUS_SOLICITUD"
    		+ " FROM SINTRANST_RUTAS_ASIGNACIONES SRA"
    		+ " INNER JOIN SINTRANST_SOLICITUD_TRASLADO SST ON SST.ID_RUTA_ASIGNACION = SRA.ID_RUTA_ASIGNACION"
    		+ " INNER JOIN SINTRANST_VEHICULOS SV ON  SV.ID_VEHICULO = SRA.ID_VEHICULO"
    		+ " WHERE SRA.IND_ACTIVO = 1 AND SRA.IND_SISTEMA = 1"
            , countQuery = "SELECT COUNT(SST.ID_SOLICITUD)"
            + " FROM SINTRANST_RUTAS_ASIGNACIONES SRA"
            + " INNER JOIN SINTRANST_SOLICITUD_TRASLADO SST ON SST.ID_RUTA_ASIGNACION = SRA.ID_RUTA_ASIGNACION"
            + " INNER JOIN SINTRANST_VEHICULOS SV ON  SV.ID_VEHICULO = SRA.ID_VEHICULO"
            + " WHERE SRA.IND_ACTIVO = 1 AND SRA.IND_SISTEMA = 1"
            ,nativeQuery = true)
    Page<List<AsigRutasEntity>> consultaGeneral(Pageable page);


    @Query(value = "SELECT SRA.ID_RUTA_ASIGNACION, SST.ID_SOLICITUD, SV.CVE_ECCO, SST.DES_ESTATUS_SOLICITUD"
    		+ " FROM SINTRANST_RUTAS_ASIGNACIONES SRA"
    		+ " INNER JOIN SINTRANST_SOLICITUD_TRASLADO SST ON SST.ID_RUTA_ASIGNACION = SRA.ID_RUTA_ASIGNACION"
    		+ " INNER JOIN SINTRANST_VEHICULOS SV ON  SV.ID_VEHICULO = SRA.ID_VEHICULO"
    		+ " WHERE SRA.IND_ACTIVO = 1 AND SRA.IND_SISTEMA = 1"
    		+ " AND SRA.ID_RUTA_ASIGNACION = ? AND SST.ID_SOLICITUD = ? "
            , countQuery = "SELECT COUNT(SST.ID_SOLICITUD)"
            		+ " FROM SINTRANST_RUTAS_ASIGNACIONES SRA"
            		+ " INNER JOIN SINTRANST_SOLICITUD_TRASLADO SST ON SST.ID_RUTA_ASIGNACION = SRA.ID_RUTA_ASIGNACION"
            		+ " INNER JOIN SINTRANST_VEHICULOS SV ON  SV.ID_VEHICULO = SRA.ID_VEHICULO"
            		+ " WHERE SRA.IND_ACTIVO = 1 AND SRA.IND_SISTEMA = 1"
            		+ " AND SRA.ID_RUTA_ASIGNACION = ? AND SST.ID_SOLICITUD = ? "
            , nativeQuery = true)
    Page<List<AsigRutasEntity>> getConsultaById(String idAsignacion, String idSolicitud, Pageable pageable);

    @Query(value = "SELECT SRA.ID_RUTA_ASIGNACION, SST.ID_SOLICITUD, SV.CVE_ECCO, SST.DES_ESTATUS_SOLICITUD"
    		+ " FROM SINTRANST_RUTAS_ASIGNACIONES SRA"
    		+ " INNER JOIN SINTRANST_SOLICITUD_TRASLADO SST ON SST.ID_RUTA_ASIGNACION = SRA.ID_RUTA_ASIGNACION"
    		+ " INNER JOIN SINTRANST_VEHICULOS SV ON  SV.ID_VEHICULO = SRA.ID_VEHICULO"
    		+ " WHERE SRA.IND_ACTIVO = 1 AND SRA.IND_SISTEMA = 1"
    		+ " AND SRA.ID_RUTA_ASIGNACION = ? AND SST.ID_SOLICITUD = ? "
            , countQuery = "SELECT COUNT(SST.ID_SOLICITUD)"
            		+ " FROM SINTRANST_RUTAS_ASIGNACIONES SRA"
            		+ " INNER JOIN SINTRANST_SOLICITUD_TRASLADO SST ON SST.ID_RUTA_ASIGNACION = SRA.ID_RUTA_ASIGNACION"
            		+ " INNER JOIN SINTRANST_VEHICULOS SV ON  SV.ID_VEHICULO = SRA.ID_VEHICULO"
            		+ " WHERE SRA.IND_ACTIVO = 1 AND SRA.IND_SISTEMA = 1"
            		+ " AND SRA.ID_RUTA_ASIGNACION = ? "
            , nativeQuery = true)
    Page<List<AsigRutasEntity>> getConsultaByIdAsignacion(String idAsignacion, Pageable pageable);


    @Query(value = "SELECT SRA.ID_RUTA_ASIGNACION, SST.ID_SOLICITUD, SV.CVE_ECCO, SST.DES_ESTATUS_SOLICITUD"
    		+ " FROM SINTRANST_RUTAS_ASIGNACIONES SRA"
    		+ " INNER JOIN SINTRANST_SOLICITUD_TRASLADO SST ON SST.ID_RUTA_ASIGNACION = SRA.ID_RUTA_ASIGNACION"
    		+ " INNER JOIN SINTRANST_VEHICULOS SV ON  SV.ID_VEHICULO = SRA.ID_VEHICULO"
    		+ " WHERE SRA.IND_ACTIVO = 1 AND SRA.IND_SISTEMA = 1"
    		+ " AND SRA.ID_RUTA_ASIGNACION = ? AND SST.ID_SOLICITUD = ? "
            , countQuery = "SELECT COUNT(SST.ID_SOLICITUD)"
            		+ " FROM SINTRANST_RUTAS_ASIGNACIONES SRA"
            		+ " INNER JOIN SINTRANST_SOLICITUD_TRASLADO SST ON SST.ID_RUTA_ASIGNACION = SRA.ID_RUTA_ASIGNACION"
            		+ " INNER JOIN SINTRANST_VEHICULOS SV ON  SV.ID_VEHICULO = SRA.ID_VEHICULO"
            		+ " WHERE SRA.IND_ACTIVO = 1 AND SRA.IND_SISTEMA = 1"
            		+ " AND SST.ID_SOLICITUD = ? "
            , nativeQuery = true)
    Page<List<AsigRutasEntity>> getConsultaByIdSolicitud( String idSolicitud, Pageable pageable);

	@Modifying(flushAutomatically = true)
	@Query(value = "UPDATE SINTRANST_RUTAS_ASIGNACIONES SET FEC_BAJA = CURRENT_TIMESTAMP(), IND_ACTIVO = 0"
			+ " WHERE IND_ACTIVO = 1 AND ID_RUTA_ASIGNACION = ? "
			,nativeQuery = true )
	void delete ( String idAsignacion );
	
}
