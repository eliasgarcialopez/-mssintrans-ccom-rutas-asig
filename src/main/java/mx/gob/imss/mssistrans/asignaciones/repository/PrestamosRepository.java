package mx.gob.imss.mssistrans.asignaciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import mx.gob.imss.mssistrans.asignaciones.model.PrestamosEntity;

@Repository
public interface PrestamosRepository extends JpaRepository<PrestamosEntity, Integer> {

}
