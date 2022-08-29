package mx.gob.imss.mssistrans.asignaciones.rutas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.imss.mssistrans.asignaciones.rutas.model.RutasAsigEntity;

@Repository
public interface RutasAsigRepository extends JpaRepository<RutasAsigEntity, Integer> {

    @Query(value = "SELECT ID_RUTA, DES_SERVICIO, NULL AS ID_SOLICITUD FROM SINTRANST_RUTAS "
    		+ " WHERE IND_ACTIVO = 1 AND IND_SISTEMA = 1 AND ID_OOAD = ?"
            , countQuery = "SELECT COUNT(ID_RUTA)"
            + " FROM SINTRANST_RUTAS"
            + " WHERE IND_ACTIVO = 1 AND IND_SISTEMA = 1 AND ID_OOAD = ?"
            ,nativeQuery = true)
    List<RutasAsigEntity> getRutas(Integer idOoad);

}
