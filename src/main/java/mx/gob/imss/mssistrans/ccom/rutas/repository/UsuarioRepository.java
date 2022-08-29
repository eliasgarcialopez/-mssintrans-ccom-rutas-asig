package mx.gob.imss.mssistrans.ccom.rutas.repository;
 

import mx.gob.imss.mssistrans.ccom.rutas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

	@Query(value = " SELECT U FROM Usuario U WHERE U.CVE_MATRICULA = ?1 AND U.IND_ACTIVO = 1 ")
	Usuario getUsuario(String matricula);



}
