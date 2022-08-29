package mx.gob.imss.mssistrans.ccom.rutas.repository;

import java.util.List;
import java.util.Optional;

import mx.gob.imss.mssistrans.ccom.rutas.model.UnidadAdscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UnidadAdscripcionRepository  extends JpaRepository<UnidadAdscripcion, Integer> {

 
    /**
     * Recupera una UnidadAdscripcion activa por su Id.
     *
     * @param idUnidadAdscripcion
     * @param activo
     * @return
     */
    Optional<UnidadAdscripcion> findByIdUnidadAdscripcionAndActivoEquals(Integer idUnidadAdscripcion, boolean activo);
    
    /**
     * Recupera las UnidadAdscripcion activa, que sean pernocta y pertenezcan a la OOAD.
     *
     * @param idOOAD
     * @return
     */
    @Query( value=""
			+ " SELECT	u "
			+ " FROM	UnidadAdscripcion u   "
			+ " WHERE   u.activo 	= '1'	"
			+ " AND u.indUnidadPerNOCTA= '1' "
			+ " AND  u.ooad.idOoad  = ?1"
			)
    List<UnidadAdscripcion> findAllUnidadAdscripcionPernoctaActivo(Integer idOOAD);
    
    /**
     * Recupera las UnidadAdscripcion activa y pertenezcan a la OOAD.
     *
     * @param idOOAD
     * @return
     */
    @Query( value=""
			+ " SELECT	u "
			+ " FROM	UnidadAdscripcion u   "
			+ " WHERE   u.activo 	= '1'	"
		
			+ " AND  u.ooad.idOoad  = ?1"
			)
    List<UnidadAdscripcion> findAllUnidadAdscripcionActivo(Integer idOOAD);
    
    /**
     * Recupera todas UnidadAdscripcion activa
     *
     * @param idOOAD
     * @return
     */
    @Query( value=""
			+ " SELECT u"
			+ " FROM	UnidadAdscripcion u   "
			+ " WHERE   u.activo 	= '1'	"
		
			)
    List<UnidadAdscripcion> findAllUnidadAdscripcionActivo();
    
   

}
