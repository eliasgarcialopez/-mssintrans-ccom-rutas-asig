package mx.gob.imss.mssistrans.rutas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mx.gob.imss.mssistrans.rutas.model.Municipio;
import mx.gob.imss.mssistrans.rutas.model.OOAD;
import mx.gob.imss.mssistrans.rutas.model.Rutas;
import mx.gob.imss.mssistrans.rutas.model.UnidadAdscripcion;

public interface MunicipioRepository  extends JpaRepository<Municipio, Integer> {


    /**
     * Recupera todos los Municipios activo
     *
     * @param 
     * @return
     */
    @Query( value=""
			+ " SELECT	m "
			+ " FROM	Municipio m   "
			+ " WHERE   m.activo 	= '1'	"
		
			)
    List<Municipio> findAllMunicipiosADActivo();
    
   

}
