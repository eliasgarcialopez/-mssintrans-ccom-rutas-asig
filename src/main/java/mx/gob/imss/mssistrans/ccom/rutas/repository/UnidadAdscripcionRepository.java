package mx.gob.imss.mssistrans.ccom.rutas.repository;

import mx.gob.imss.mssistrans.ccom.rutas.model.UnidadAdscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UnidadAdscripcionRepository extends JpaRepository<UnidadAdscripcion, Integer> {


    /**
     * Recupera una UnidadAdscripcion activa por su Id.
     *
     * @param idOoad
     * @param activo
     * @return
     */
    Optional<UnidadAdscripcion> findByIdUnidadAdscripcionAndActivoEquals(Integer idUnidadAdscripcion, boolean activo);

    /**
     * Recupera las UnidadAdscripcion activa, que sean pernocta y pertenezcan a la OOAD.
     *
     * @param idOoad
     * @return
     */
    @Query(value = "SELECT u " +
            "FROM UnidadAdscripcion u WHERE u.activo = true " +
            "AND u.pernocta = true " +
            "AND u.ooad.idOoad = ?1")
    List<UnidadAdscripcion> findAllUnidadAdscripcionPernoctaActivo(Integer idOoad);

    /**
     * Recupera las UnidadAdscripcion activa y pertenezcan a la OOAD.
     *
     * @param idOoad
     * @return
     */
    @Query(value = "SELECT u " +
            "FROM UnidadAdscripcion u " +
            "WHERE u.activo = true " +
            "AND u.ooad.idOoad = ?1")
    List<UnidadAdscripcion> findAllUnidadAdscripcionActivo(Integer idOoad);

    /**
     * Recupera todas UnidadAdscripcion activa
     *
     * @return
     */
    @Query(value = "SELECT u " +
            "FROM UnidadAdscripcion u " +
            "WHERE u.activo = true")
    List<UnidadAdscripcion> findAllUnidadAdscripcionActivo();
}
