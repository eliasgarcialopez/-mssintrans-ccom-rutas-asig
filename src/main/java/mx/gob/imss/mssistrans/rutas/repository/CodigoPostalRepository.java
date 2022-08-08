package mx.gob.imss.mssistrans.rutas.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mx.gob.imss.mssistrans.rutas.model.CodigoPostal;
import mx.gob.imss.mssistrans.rutas.model.OOAD;
import mx.gob.imss.mssistrans.rutas.model.Rutas;
import mx.gob.imss.mssistrans.rutas.model.UnidadAdscripcion;

public interface CodigoPostalRepository  extends JpaRepository<CodigoPostal, Integer> {

 
    /**
     * Recupera codigo postal activa por su Id.
     *
     * @param idCodigoPostal
     * @param activo
     * @return
     */
    Optional<CodigoPostal> findByIdCodigoPostalAndActivoEquals(Integer idCodigoPostal, boolean activo);
    /**
     * Recupera una OOAD activa por su Id.
     *
     * @param idOOAD
     * @param activo
     * @return
     */
    Optional<CodigoPostal> findByCveCodPostalAndActivoEquals(String cveCodPostal, boolean activo);
    
   
   

}
