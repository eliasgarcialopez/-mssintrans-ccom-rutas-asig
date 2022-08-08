package mx.gob.imss.mssistrans.rutas.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mx.gob.imss.mssistrans.rutas.model.Asignaciones;
import mx.gob.imss.mssistrans.rutas.model.CodigoPostal;
import mx.gob.imss.mssistrans.rutas.model.OOAD;
import mx.gob.imss.mssistrans.rutas.model.Rutas;
import mx.gob.imss.mssistrans.rutas.model.UnidadAdscripcion;

public interface AsignacionesRepository  extends JpaRepository<Asignaciones, Integer> {

 
    /**
     * Recupera una asignacion de ruta y activa por su Id.
     *
     * @param idCodigoPostal
     * @param activo
     * @return
     */
    Optional<Asignaciones> findByIdAsignacionAndActivoEquals(Integer idAsignacion, boolean activo);
  
   

}
