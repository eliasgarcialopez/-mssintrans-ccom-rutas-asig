package mx.gob.imss.mssistrans.asignaciones.rutas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.imss.mssistrans.asignaciones.rutas.model.SolicitudTrasladoEntity;
import mx.gob.imss.mssistrans.asignaciones.rutas.model.VehiculoEntity;

@Repository
public interface SolicitudTrasladoRepository extends JpaRepository<SolicitudTrasladoEntity, Integer> {

    @Query(value = "SELECT ID_SOLICITUD"
    		+ " FROM SINTRANST_SOLICITUD_TRASLADO "
    		+ " WHERE  IND_ACTIVO = 1 AND ID_RUTA_ASIGNACION = ?"
            , countQuery = "SELECT COUNT(ID_SOLICITUD)"
            		+ " FROM SINTRANST_SOLICITUD_TRASLADO "
            		+ " WHERE  IND_ACTIVO = 1 AND ID_RUTA_ASIGNACION = ?"
            ,nativeQuery = true)
    List<SolicitudTrasladoEntity> getSolicitudTraslado(String idRutaAsignacion);

}
