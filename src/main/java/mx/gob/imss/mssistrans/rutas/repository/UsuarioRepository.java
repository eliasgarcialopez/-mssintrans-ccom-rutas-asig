package mx.gob.imss.mssistrans.rutas.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.gob.imss.mssistrans.rutas.model.Usuario;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Usuario findByCveMatricula(String desMatricula);


}
	