package mx.gob.imss.mssistrans.rutas.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mx.gob.imss.mssistrans.rutas.model.Rutas;

public interface RutasRepository  extends JpaRepository<Rutas, Integer> {

    /**
     * Consulta de Rutas por filtros y por la bandera de activo
     *
     * @param folioRuta
     * @param pageable
     * @return
     */
    @Query(value = "select a from Rutas a " +
            "where  a.numFolioRuta like CONCAT(?1,'%')    " +
                  "and a.activo = true")
    Page<Rutas> findAll(String folioRuta, Pageable pageable);
    
    

    /**
     * Consulta de Rutas por filtros,ooad y por la bandera de activo
     *
     * @param folioRuta
     * @param pageable
     * @return
     */
    @Query(value = "select a from Rutas a " +
            "where  a.numFolioRuta like CONCAT(?1,'%') and  a.idOOADFiltro=?2   " +
                  "and a.activo = true")
    Page<Rutas> findAll(String folioRuta,Integer iOOAD, Pageable pageable);
    
    

    /**
     * Recupera una Ruta activa por su Id.
     *
     * @param idArrendadora
     * @param activo
     * @return
     */
    Optional<Rutas> findByIdRutaAndActivoEquals(Integer idRuta, boolean activo);
    
    /**
     * Consulta de Rutas y por la bandera de activo
     *
     *
     * @param pageable
     * @return
     */
    @Query(value = "select a from Rutas a " +
            "where " +
                  "a.activo = true")
    Page<Rutas> findAll(Pageable pageable);

    
    
    /**
     * Consulta de Rutas  por la bandera de activo y OOAD
     *
     *
     * @param pageable
     * @return
     */
    @Query(value = "select a from Rutas a " +
            "where " +
    		" a.idOOADFiltro=?1 and "+    
                  "a.activo = true")
    Page<Rutas> findAll(Pageable pageable, Integer idOOADFiltro);

}
