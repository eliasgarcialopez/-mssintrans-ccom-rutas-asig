package mx.gob.imss.mssistrans.ccom.rutas.repository;

import mx.gob.imss.mssistrans.ccom.rutas.model.AsigRutasEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AsigRutasRepository extends JpaRepository<AsigRutasEntity, Integer> {

    @Query(value = "SELECT SR.ID_RUTA, SR.NUM_FOLIO_RUTA AS NUM_FOLIO_RUTA, SCR.ID_SOLICITUD AS ID_SOLICITUD, SV.CVE_ECCO AS CVE_ECCO, "
            + " REA.DES_SINIESTRO AS DES_ESTATUS_ASIGNA, SCR.ID_CONTROL_RUTA AS ID_CONTROL_RUTA, SV.ID_VEHICULO AS ID_VEHICULO, REA.ID_REASIGNACION "
            + " FROM SINTRANST_REASIGNACION_RUTAS REA"
            + " INNER JOIN SINTRANST_RUTAS SR ON SR.ID_RUTA = REA.ID_RUTA"
            + " INNER JOIN SINTRANST_CONTROL_RUTAS SCR ON SCR.ID_RUTA = SR.ID_RUTA"
            + " INNER JOIN SINTRANST_VEHICULOS SV ON SV.ID_VEHICULO = SCR.ID_VEHICULO"
            + " WHERE REA.IND_ACTIVO = 1 "
            + " AND   REA.IND_SISTEMA = 1 "
            + " AND SCR.ID_SOLICITUD = ? "
            , countQuery = "SELECT COUNT(SR.ID_RUTA)"
            + " FROM SINTRANST_REASIGNACION_RUTAS REA"
            + " INNER JOIN SINTRANST_RUTAS SR ON SR.ID_RUTA = REA.ID_RUTA"
            + " INNER JOIN SINTRANST_CONTROL_RUTAS SCR ON SCR.ID_RUTA = SR.ID_RUTA"
            + " INNER JOIN SINTRANST_VEHICULOS SV ON SV.ID_VEHICULO = SCR.ID_VEHICULO"
            + " WHERE REA.IND_ACTIVO = 1 "
            + " AND   REA.IND_SISTEMA = 1 "
            + " AND SCR.ID_SOLICITUD = ? "
            , nativeQuery = true)
    Page<AsigRutasEntity> getConsultaByIdSolicitudReasignaciones(String idSolicitud, Pageable pageable);


    @Query(value = "SELECT SR.ID_RUTA, SR.NUM_FOLIO_RUTA AS NUM_FOLIO_RUTA, SCR.ID_SOLICITUD AS ID_SOLICITUD, SV.CVE_ECCO AS CVE_ECCO"
            + " , REA.DES_SINIESTRO AS DES_ESTATUS_ASIGNA, SCR.ID_CONTROL_RUTA AS ID_CONTROL_RUTA, SV.ID_VEHICULO AS ID_VEHICULO, REA.ID_REASIGNACION"
            + " FROM SINTRANST_REASIGNACION_RUTAS REA"
            + " INNER JOIN SINTRANST_RUTAS SR ON SR.ID_RUTA = REA.ID_RUTA"
            + " INNER JOIN SINTRANST_CONTROL_RUTAS SCR ON SCR.ID_RUTA = SR.ID_RUTA"
            + " INNER JOIN SINTRANST_VEHICULOS SV ON SV.ID_VEHICULO = SCR.ID_VEHICULO"
            + " WHERE REA.IND_ACTIVO = 1 "
            + " AND   REA.IND_SISTEMA = 1 "
            , countQuery = "SELECT COUNT(SR.ID_RUTA) "
            + "FROM SINTRANST_REASIGNACION_RUTAS REA "
            + "INNER JOIN SINTRANST_RUTAS SR ON SR.ID_RUTA = REA.ID_RUTA "
            + "INNER JOIN SINTRANST_CONTROL_RUTAS SCR ON SCR.ID_RUTA = SR.ID_RUTA "
            + "INNER JOIN SINTRANST_VEHICULOS SV ON SV.ID_VEHICULO = SCR.ID_VEHICULO "
            + "WHERE REA.IND_ACTIVO = 1 "
            + "AND  REA.IND_SISTEMA = 1",
            nativeQuery = true)
    Page<AsigRutasEntity> consultaGeneralRasignaciones(Pageable page);

    @Query(value = "SELECT SR.ID_RUTA, SR.NUM_FOLIO_RUTA AS NUM_FOLIO_RUTA, SCR.ID_SOLICITUD AS ID_SOLICITUD, SV.CVE_ECCO AS CVE_ECCO"
            + " , SCR.DES_ESTATUS_ASIGNA AS DES_ESTATUS_ASIGNA, SCR.ID_CONTROL_RUTA AS ID_CONTROL_RUTA, SV.ID_VEHICULO AS ID_VEHICULO, REA.ID_REASIGNACION"
            + " FROM SINTRANST_REASIGNACION_RUTAS REA"
            + " INNER JOIN SINTRANST_RUTAS SR ON SR.ID_RUTA = REA.ID_RUTA"
            + " INNER JOIN SINTRANST_CONTROL_RUTAS SCR ON SCR.ID_RUTA = SR.ID_RUTA"
            + " INNER JOIN SINTRANST_VEHICULOS SV ON SV.ID_VEHICULO = SCR.ID_VEHICULO"
            + " WHERE REA.IND_ACTIVO = 1 "
            + " AND   REA.IND_SISTEMA = 1 "
            + " AND SR.ID_RUTA  = ? "
            , countQuery = "SELECT COUNT(SR.ID_RUTA)"
            + " FROM SINTRANST_REASIGNACION_RUTAS REA"
            + " INNER JOIN SINTRANST_RUTAS SR ON SR.ID_RUTA = REA.ID_RUTA"
            + " INNER JOIN SINTRANST_CONTROL_RUTAS SCR ON SCR.ID_RUTA = SR.ID_RUTA"
            + " INNER JOIN SINTRANST_VEHICULOS SV ON SV.ID_VEHICULO = SCR.ID_VEHICULO"
            + " WHERE REA.IND_ACTIVO = 1 "
            + " AND   REA.IND_SISTEMA = 1 "
            + " AND SR.ID_RUTA  = ? "
            , nativeQuery = true)
    Page<AsigRutasEntity> getConsultaByIdAsignacionReasignaciones(String idRuta, Pageable pageable);

    @Query(value = "SELECT SR.ID_RUTA, SR.NUM_FOLIO_RUTA AS NUM_FOLIO_RUTA, SCR.ID_SOLICITUD AS ID_SOLICITUD, SV.CVE_ECCO AS CVE_ECCO"
            + " , REA.DES_SINIESTRO AS DES_ESTATUS_ASIGNA, SCR.ID_CONTROL_RUTA AS ID_CONTROL_RUTA, SV.ID_VEHICULO AS ID_VEHICULO, REA.ID_REASIGNACION"
            + " FROM SINTRANST_REASIGNACION_RUTAS REA"
            + " INNER JOIN SINTRANST_RUTAS SR ON SR.ID_RUTA = REA.ID_RUTA"
            + " INNER JOIN SINTRANST_CONTROL_RUTAS SCR ON SCR.ID_RUTA = SR.ID_RUTA"
            + " INNER JOIN SINTRANST_VEHICULOS SV ON SV.ID_VEHICULO = SCR.ID_VEHICULO"
            + " WHERE REA.IND_ACTIVO = 1 "
            + " AND  REA.IND_SISTEMA = 1 "
            + " AND SR.ID_RUTA = ? AND SCR.ID_SOLICITUD = ?"
            , countQuery = "SELECT COUNT(SR.ID_RUTA)"
            + " FROM SINTRANST_REASIGNACION_RUTAS REA"
            + " INNER JOIN SINTRANST_RUTAS SR ON SR.ID_RUTA = REA.ID_RUTA"
            + " INNER JOIN SINTRANST_CONTROL_RUTAS SCR ON SCR.ID_RUTA = SR.ID_RUTA"
            + " INNER JOIN SINTRANST_VEHICULOS SV ON SV.ID_VEHICULO = SCR.ID_VEHICULO"
            + " WHERE REA.IND_ACTIVO = 1 "
            + " AND  REA.IND_SISTEMA = 1 "
            + " AND SR.ID_RUTA = ? AND SCR.ID_SOLICITUD = ?"
            , nativeQuery = true)
    Page<AsigRutasEntity> getConsultaByIdReasignaciones(String idAsignacion, String idSolicitud, Pageable pageable);

    @Query(value = "SELECT SR.ID_RUTA, SR.NUM_FOLIO_RUTA AS NUM_FOLIO_RUTA, SCA.ID_SOLICITUD AS ID_SOLICITUD, SV.CVE_ECCO AS CVE_ECCO"
            + ", SCA.DES_ESTATUS_ASIGNA AS DES_ESTATUS_ASIGNA, SCA.ID_CONTROL_RUTA AS ID_CONTROL_RUTA, " +
            "SV.ID_VEHICULO, " +
            "NULL AS ID_REASIGNACION"
            + " FROM SINTRANST_RUTAS SR"
            + " INNER JOIN SINTRANST_CONTROL_RUTAS SCA ON SCA.ID_RUTA = SR.ID_RUTA"
            + " INNER JOIN SINTRANST_VEHICULOS SV ON SV.ID_VEHICULO = SCA.ID_VEHICULO"
            + " WHERE SCA.IND_ACTIVO = 1 AND SCA.IND_SISTEMA = 1"
            , countQuery = "SELECT COUNT(SR.NUM_FOLIO_RUTA)"
            + " FROM SINTRANST_RUTAS SR"
            + " INNER JOIN SINTRANST_CONTROL_RUTAS SCA ON SCA.ID_RUTA = SR.ID_RUTA"
            + " INNER JOIN SINTRANST_VEHICULOS SV ON SV.ID_VEHICULO = SCA.ID_VEHICULO"
            + " WHERE SCA.IND_ACTIVO = 1 AND SCA.IND_SISTEMA = 1"
            , nativeQuery = true)
    Page<AsigRutasEntity> consultaGeneral(Pageable page);

    @Query(value = "SELECT SR.ID_RUTA, SR.NUM_FOLIO_RUTA AS NUM_FOLIO_RUTA, SCR.ID_SOLICITUD AS ID_SOLICITUD, SV.CVE_ECCO AS CVE_ECCO"
            + " , SCR.DES_ESTATUS_ASIGNA AS DES_ESTATUS_ASIGNA, SCR.ID_CONTROL_RUTA AS ID_CONTROL_RUTA, SV.ID_VEHICULO AS ID_VEHICULO, " +
            "NULL AS ID_REASIGNACION"
            + " FROM SINTRANST_RUTAS SR"
            + " INNER JOIN SINTRANST_CONTROL_RUTAS SCR ON SCR.ID_RUTA = SR.ID_RUTA"
            + " INNER JOIN SINTRANST_VEHICULOS SV ON SV.ID_VEHICULO = SCR.ID_VEHICULO"
            + " WHERE SCR.IND_ACTIVO  = SR.IND_ACTIVO AND SCR.IND_SISTEMA  = SR.IND_SISTEMA AND"
            + " SR.IND_SISTEMA = SV.IND_SISTEMA AND SR.IND_ACTIVO = SV.IND_ACTIVO AND"
            + " SCR.IND_ACTIVO = 1 AND SCR.IND_SISTEMA = 1 "
            + " AND SR.NUM_FOLIO_RUTA = ? AND SCR.ID_SOLICITUD = ?"
            , countQuery = "SELECT COUNT(SR.ID_RUTA)"
            + " FROM SINTRANST_RUTAS SR"
            + " INNER JOIN SINTRANST_CONTROL_RUTAS SCR ON SCR.ID_RUTA = SR.ID_RUTA"
            + " INNER JOIN SINTRANST_VEHICULOS SV ON SV.ID_VEHICULO = SCR.ID_VEHICULO"
            + " WHERE SCR.IND_ACTIVO  = SR.IND_ACTIVO AND SCR.IND_SISTEMA  = SR.IND_SISTEMA AND"
            + " SR.IND_SISTEMA = SV.IND_SISTEMA AND SR.IND_ACTIVO = SV.IND_ACTIVO AND"
            + " SCR.IND_ACTIVO = 1 AND SCR.IND_SISTEMA = 1 "
            + " AND SR.NUM_FOLIO_RUTA = ? AND SCR.ID_SOLICITUD = ?"
            , nativeQuery = true)
    Page<AsigRutasEntity> getConsultaById(String idAsignacion, String idSolicitud, Pageable pageable);

    @Query(value = "SELECT SR.ID_RUTA, SR.NUM_FOLIO_RUTA AS NUM_FOLIO_RUTA, SCR.ID_SOLICITUD AS ID_SOLICITUD, SV.CVE_ECCO AS CVE_ECCO"
            + " , REA.DES_SINIESTRO AS DES_ESTATUS_ASIGNA, SCR.ID_CONTROL_RUTA AS ID_CONTROL_RUTA, SV.ID_VEHICULO AS ID_VEHICULO, " +
            "NULL AS ID_REASIGNACION"
            + " FROM SINTRANST_RUTAS SR"
            + " INNER JOIN SINTRANST_CONTROL_RUTAS SCR ON SCR.ID_RUTA = SR.ID_RUTA"
            + " INNER JOIN SINTRANST_VEHICULOS SV ON SV.ID_VEHICULO = SCR.ID_VEHICULO"
            + " WHERE SCR.IND_ACTIVO  = SR.IND_ACTIVO AND SCR.IND_SISTEMA  = SR.IND_SISTEMA AND"
            + " SR.IND_SISTEMA = SV.IND_SISTEMA AND SR.IND_ACTIVO = SV.IND_ACTIVO AND"
            + " SCR.IND_ACTIVO = 1 AND SCR.IND_SISTEMA = 1 "
            + " AND SR.ID_RUTA  = ? "
            , countQuery = "SELECT COUNT(SR.ID_RUTA)"
            + " FROM SINTRANST_RUTAS SR"
            + " INNER JOIN SINTRANST_CONTROL_RUTAS SCR ON SCR.ID_RUTA = SR.ID_RUTA"
            + " INNER JOIN SINTRANST_VEHICULOS SV ON SV.ID_VEHICULO = SCR.ID_VEHICULO"
            + " WHERE SCR.IND_ACTIVO  = SR.IND_ACTIVO AND SCR.IND_SISTEMA  = SR.IND_SISTEMA AND"
            + " SR.IND_SISTEMA = SV.IND_SISTEMA AND SR.IND_ACTIVO = SV.IND_ACTIVO AND"
            + " SCR.IND_ACTIVO = 1 AND SCR.IND_SISTEMA = 1 "
            + " AND SR.ID_RUTA  = ? "
            , nativeQuery = true)
    Page<AsigRutasEntity> getConsultaByIdAsignacion(String idRuta, Pageable pageable);

    @Query(value = "SELECT SR.ID_RUTA, SR.NUM_FOLIO_RUTA AS NUM_FOLIO_RUTA, SCR.ID_SOLICITUD AS ID_SOLICITUD, SV.CVE_ECCO AS CVE_ECCO"
            + " , SCR.DES_ESTATUS_ASIGNA AS DES_ESTATUS_ASIGNA, SCR.ID_CONTROL_RUTA AS ID_CONTROL_RUTA, SV.ID_VEHICULO AS ID_VEHICULO, " +
            "NULL AS ID_REASIGNACION"
            + " FROM SINTRANST_RUTAS SR"
            + " INNER JOIN SINTRANST_CONTROL_RUTAS SCR ON SCR.ID_RUTA = SR.ID_RUTA"
            + " INNER JOIN SINTRANST_VEHICULOS SV ON SV.ID_VEHICULO = SCR.ID_VEHICULO"
            + " WHERE SR.IND_ACTIVO =1 "
            + " AND SCR.IND_ACTIVO = 1 "
            + " AND SV.IND_ACTIVO =1 "
            + " AND SCR.IND_SISTEMA = 1 "
            + " AND SCR.ID_SOLICITUD = ? "
            , countQuery = "SELECT COUNT(SR.ID_RUTA)"
            + " FROM SINTRANST_RUTAS SR"
            + " INNER JOIN SINTRANST_CONTROL_RUTAS SCR ON SCR.ID_RUTA = SR.ID_RUTA"
            + " INNER JOIN SINTRANST_VEHICULOS SV ON SV.ID_VEHICULO = SCR.ID_VEHICULO"
            + " WHERE SR.IND_ACTIVO =1 "
            + " AND SCR.IND_ACTIVO = 1 "
            + " AND SV.IND_ACTIVO =1 "
            + " AND SCR.IND_SISTEMA = 1 "
            + " AND SCR.ID_SOLICITUD = ? "
            , nativeQuery = true)
    Page<AsigRutasEntity> getConsultaByIdSolicitud(String idSolicitud, Pageable pageable);

    @Query(value = "SELECT SR.ID_RUTA, SR.NUM_FOLIO_RUTA AS NUM_FOLIO_RUTA, SCR.ID_SOLICITUD AS ID_SOLICITUD, SV.CVE_ECCO AS CVE_ECCO"
            + " , SCR.DES_ESTATUS_ASIGNA AS DES_ESTATUS_ASIGNA, SCR.ID_CONTROL_RUTA AS ID_CONTROL_RUTA, SV.ID_VEHICULO AS ID_VEHICULO, " +
            "NULL AS ID_REASIGNACION"
            + " FROM SINTRANST_RUTAS SR"
            + " INNER JOIN SINTRANST_CONTROL_RUTAS SCR ON SCR.ID_RUTA = SR.ID_RUTA"
            + " INNER JOIN SINTRANST_VEHICULOS SV ON SV.ID_VEHICULO = SCR.ID_VEHICULO"
            + " WHERE SR.IND_ACTIVO =1 "
            + " AND SCR.IND_ACTIVO = 1 "
            + " AND SV.IND_ACTIVO =1 "
            + " AND SCR.IND_SISTEMA = 1 "
            + " AND SCR.ID_CONTROL_RUTA  = ? "
            , countQuery = "SELECT COUNT(SR.ID_RUTA)"
            + " FROM SINTRANST_RUTAS SR"
            + " INNER JOIN SINTRANST_CONTROL_RUTAS SCR ON SCR.ID_RUTA = SR.ID_RUTA"
            + " INNER JOIN SINTRANST_VEHICULOS SV ON SV.ID_VEHICULO = SCR.ID_VEHICULO"
            + " WHERE SR.IND_ACTIVO =1 "
            + " AND SCR.IND_ACTIVO = 1 "
            + " AND SV.IND_ACTIVO =1 "
            + " AND SCR.IND_SISTEMA = 1 "
            + " AND SCR.ID_CONTROL_RUTA  = ? "
            , nativeQuery = true)
    Page<AsigRutasEntity> getConsultaByIdControlRuta(String idControlRuta, Pageable pageable);

    @Modifying(flushAutomatically = true)
    @Query(value = "UPDATE SINTRANST_CONTROL_RUTAS SET FEC_BAJA = CURRENT_TIMESTAMP(), IND_ACTIVO = 0"
            + " WHERE IND_ACTIVO = 1 AND ID_CONTROL_RUTA = ? "
            , nativeQuery = true)
    void delete(String idControlRuta);

    @Modifying(flushAutomatically = true)
    @Query(value = "UPDATE SINTRANST_REASIGNACION_RUTAS SET FEC_BAJA = CURRENT_TIMESTAMP(), IND_ACTIVO = 0"
            + " WHERE IND_ACTIVO = 1 AND ID_REASIGNACION = ? "
            , nativeQuery = true)
    void deleteReasignacion(String idReasignacion);

    @Modifying(flushAutomatically = true)
    @Query(value = "UPDATE SINTRANST_CONTROL_RUTAS SET DES_ESTATUS_ASIGNA='1' "
            + " WHERE ID_CONTROL_RUTA IN (SELECT DISTINCT  scr.ID_CONTROL_RUTA  FROM SINTRANST_REASIGNACION_RUTAS sra JOIN SINTRANST_CONTROL_RUTAS scr ON (scr.ID_RUTA=sra.ID_RUTA) WHERE sra.IND_ACTIVO =1 AND scr.IND_ACTIVO =1 AND sra.ID_REASIGNACION=?) "
            , nativeQuery = true)
    void actalizarCOntrolRutasReasignacion(String idReasignacion);

    @Query(value = "SELECT SR.ID_RUTA, SR.NUM_FOLIO_RUTA AS NUM_FOLIO_RUTA, SCR.ID_SOLICITUD AS ID_SOLICITUD, " +
            "SV.CVE_ECCO AS CVE_ECCO, SCR.DES_ESTATUS_ASIGNA AS DES_ESTATUS_ASIGNA, " +
            "SCR.ID_CONTROL_RUTA AS ID_CONTROL_RUTA, SV.ID_VEHICULO AS ID_VEHICULO, " +
            "NULL AS ID_REASIGNACION "
            + "FROM SINTRANST_RUTAS SR "
            + "INNER JOIN SINTRANST_CONTROL_RUTAS SCR ON SCR.ID_RUTA = SR.ID_RUTA "
            + "INNER JOIN SINTRANST_VEHICULOS SV ON SV.ID_VEHICULO = SCR.ID_VEHICULO "
            + "WHERE SR.IND_ACTIVO =1 "
            + "AND SCR.IND_ACTIVO = 1 "
            + "AND SV.IND_ACTIVO =1 "
            + "AND SCR.IND_SISTEMA = 1 "
            + "AND SCR.ID_CONTROL_RUTA  = ? "
            + "AND SCR.ID_SOLICITUD = ?",
            countQuery = "SELECT COUNT(SR.ID_RUTA) "
                    + "FROM SINTRANST_RUTAS SR "
                    + "INNER JOIN SINTRANST_CONTROL_RUTAS SCR ON SCR.ID_RUTA = SR.ID_RUTA "
                    + "INNER JOIN SINTRANST_VEHICULOS SV ON SV.ID_VEHICULO = SCR.ID_VEHICULO "
                    + "WHERE SR.IND_ACTIVO =1 "
                    + "AND SCR.IND_ACTIVO = 1 "
                    + "AND SV.IND_ACTIVO =1 "
                    + "AND SCR.IND_SISTEMA = 1 "
                    + "AND SCR.ID_CONTROL_RUTA = ? "
                    + "AND SCR.ID_SOLICITUD = ?",
            nativeQuery = true)
    Page<AsigRutasEntity> getConsultaByIdControlRutaAndIdSolicitud(String idAsignacion, String idControlRuta, Pageable pageable);

}
