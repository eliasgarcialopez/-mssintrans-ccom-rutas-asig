package mx.gob.imss.mssintetrans.ccom.rutas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.imss.mssintetrans.ccom.rutas.model.ModuloAmbulancia;

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
