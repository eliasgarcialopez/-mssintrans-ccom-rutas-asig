package mx.gob.imss.mssistrans.rutas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mx.gob.imss.mssistrans.rutas.model.OOAD;
import mx.gob.imss.mssistrans.rutas.model.Rutas;
import mx.gob.imss.mssistrans.rutas.model.UnidadAdscripcion;

public interface OOADRepository  extends JpaRepository<OOAD, Integer> {

 
    /**
     * Recupera una OOAD activa por su Id.
     *
     * @param idOOAD
     * @param activo
     * @return
     */
    Optional<OOAD> findByIdOOADAndActivoEquals(Integer idOOAD, boolean activo);
    
    /**
     * Recupera todas OOAD activa
     *
     * @param 
     * @return
     */
    @Query( value=""
			+ " SELECT	o "
			+ " FROM	OOAD o   "
			+ " WHERE   o.activo 	= '1'	"
		
			)
    List<OOAD> findAllOOADActivo();
    
   

}
