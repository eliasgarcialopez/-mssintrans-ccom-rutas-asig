package mx.gob.imss.mssintetrans.ccom.rutas.repository;
 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.gob.imss.mssintetrans.ccom.rutas.model.Usuario;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

	@Query(value = " SELECT U FROM Usuario U WHERE U.CVE_MATRICULA = ?1 AND U.IND_ACTIVO = 1 ")
	Usuario getUsuario(String matricula);



}
