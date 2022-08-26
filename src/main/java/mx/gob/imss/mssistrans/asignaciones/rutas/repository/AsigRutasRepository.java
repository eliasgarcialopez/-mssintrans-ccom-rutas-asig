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

    @Query(value = "SELECT SCA.ID_RUTA, SCA.ID_SOLICITUD, SV.CVE_ECCO, SCA.DES_ESTATUS_ASIGNA"
    		+ " FROM SINTRANST_CONTROL_RUTAS SCA"
    		+ " INNER JOIN SINTRANST_VEHICULOS SV ON SV.ID_VEHICULO = SCA.ID_VEHICULO"
    		+ " WHERE SCA.IND_ACTIVO = 1 AND SCA.IND_SISTEMA = 1"
            , countQuery = "SELECT COUNT(SCA.ID_SOLICITUD)"
            + " FROM SINTRANST_CONTROL_RUTAS SCA"
            + " INNER JOIN SINTRANST_VEHICULOS SV ON SV.ID_VEHICULO = SCA.ID_VEHICULO"
            + " WHERE SCA.IND_ACTIVO = 1 AND SCA.IND_SISTEMA = 1"
            ,nativeQuery = true)
    Page<List<AsigRutasEntity>> consultaGeneral(Pageable page);


    @Query(value = "SELECT SCA.ID_RUTA, SCA.ID_SOLICITUD, SV.CVE_ECCO, SCA.DES_ESTATUS_ASIGNA"
    		+ " FROM SINTRANST_CONTROL_RUTAS SCA"
    		+ " INNER JOIN SINTRANST_VEHICULOS SV ON SV.ID_VEHICULO = SCA.ID_VEHICULO"
    		+ " WHERE SCA.IND_ACTIVO = 1 AND SCA.IND_SISTEMA = 1"
    		+ " AND SCA.ID_RUTA = ? AND SCA.ID_SOLICITUD = ?"
            , countQuery = "SELECT COUNT(SCA.ID_RUTA)"
            + " FROM SINTRANST_CONTROL_RUTAS SCA"
            + " INNER JOIN SINTRANST_VEHICULOS SV ON SV.ID_VEHICULO = SCA.ID_VEHICULO"
            + " WHERE SCA.IND_ACTIVO = 1 AND SCA.IND_SISTEMA = 1"
            + " AND SCA.ID_RUTA = ? AND SCA.ID_SOLICITUD = ?"
            , nativeQuery = true)
    Page<List<AsigRutasEntity>> getConsultaById(String idAsignacion, String idSolicitud, Pageable pageable);

    @Query(value = "SELECT SCA.ID_RUTA, SCA.ID_SOLICITUD, SV.CVE_ECCO, SCA.DES_ESTATUS_ASIGNA"
    		+ " FROM SINTRANST_CONTROL_RUTAS SCA"
    		+ " INNER JOIN SINTRANST_VEHICULOS SV ON SV.ID_VEHICULO = SCA.ID_VEHICULO"
    		+ " WHERE SCA.IND_ACTIVO = 1 AND SCA.IND_SISTEMA = 1"
    		+ " AND SCA.ID_RUTA = ? "
            , countQuery = "SELECT COUNT(SCA.ID_RUTA)"
            + " FROM SINTRANST_CONTROL_RUTAS SCA"
            + " INNER JOIN SINTRANST_VEHICULOS SV ON SV.ID_VEHICULO = SCA.ID_VEHICULO"
            + " WHERE SCA.IND_ACTIVO = 1 AND SCA.IND_SISTEMA = 1"
            + " AND SCA.ID_RUTA = ? "
            , nativeQuery = true)
    Page<List<AsigRutasEntity>> getConsultaByIdAsignacion(String idRutaAsig, Pageable pageable);


    @Query(value =  "SELECT SCA.ID_RUTA, SCA.ID_SOLICITUD, SV.CVE_ECCO, SCA.DES_ESTATUS_ASIGNA"
    		+ " FROM SINTRANST_CONTROL_RUTAS SCA"
    		+ " INNER JOIN SINTRANST_VEHICULOS SV ON SV.ID_VEHICULO = SCA.ID_VEHICULO"
    		+ " WHERE SCA.IND_ACTIVO = 1 AND SCA.IND_SISTEMA = 1"
    		+ " AND SCA.ID_SOLICITUD = ? "
            , countQuery = "SELECT COUNT(SST.ID_SOLICITUD)"
            		+ " FROM SINTRANST_CONTROL_RUTAS SCA"
            		+ " INNER JOIN SINTRANST_VEHICULOS SV ON SV.ID_VEHICULO = SCA.ID_VEHICULO"
            		+ " WHERE SCA.IND_ACTIVO = 1 AND SCA.IND_SISTEMA = 1"
            		+ " AND SCA.ID_SOLICITUD = ? "
            , nativeQuery = true)
    Page<List<AsigRutasEntity>> getConsultaByIdSolicitud( String idSolicitud, Pageable pageable);

	@Modifying(flushAutomatically = true)
	@Query(value = "UPDATE SINTRANST_CONTROL_RUTAS SET FEC_BAJA = CURRENT_TIMESTAMP(), IND_ACTIVO = 0"
			+ " WHERE IND_ACTIVO = 1 AND ID_RUTA = ? "
			,nativeQuery = true )
	void delete ( String idAsignacion );
	
}
