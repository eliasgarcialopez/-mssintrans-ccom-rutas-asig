package mx.gob.imss.mssistrans.ccom.rutas.repository;

import java.util.Optional;

import mx.gob.imss.mssistrans.ccom.rutas.model.ModuloAmbulancia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuloAmbulanciaRepository extends JpaRepository<ModuloAmbulancia, Integer> {
	
	   /**
     * Recupera un Modulo de Ambulancia activo  por IdOOAD.
     *
     * @param idArrendadora
     * @param activo
     * @return
     */
   
	Optional<ModuloAmbulancia> findByIdOOADAndActivoEquals(Integer idOOAD,boolean activo);

	
}
