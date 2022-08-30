package mx.gob.imss.mssistrans.ccom.rutas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.imss.mssistrans.ccom.rutas.model.SolTrasladoEntity;



@Repository
public interface SolTrasladoRepository extends JpaRepository<SolTrasladoEntity, Integer> {

    @Query(value = "SELECT ID_SOLICITUD"
            + " FROM SINTRANST_SOLICITUD_TRASLADO "
            + " WHERE  IND_ACTIVO = 1 AND IND_SISTEMA = 1 "
            + "AND ID_UNIDAD_ADSCRIPCION = ? AND ID_UNIDAD_SOLICITANTE = ?"
            , countQuery = "SELECT COUNT(ID_SOLICITUD)"
            + " FROM SINTRANST_SOLICITUD_TRASLADO "
            + " WHERE  IND_ACTIVO = 1 AND IND_SISTEMA = 1 "
            + "AND ID_UNIDAD_ADSCRIPCION = ? AND ID_UNIDAD_SOLICITANTE = ?"
            ,nativeQuery = true)
    List<SolTrasladoEntity> getSolicitudTraslado(Integer idUnidadAdscripcion, Integer idVehiculo);

}
