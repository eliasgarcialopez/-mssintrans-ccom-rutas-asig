package mx.gob.imss.mssistrans.asignaciones.rutas.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.imss.mssistrans.asignaciones.rutas.model.RegistroRecorridoEntity;
import mx.gob.imss.mssistrans.asignaciones.rutas.model.SiniestrosEntity;

@Repository
public interface SiniestrosRepository extends JpaRepository<SiniestrosEntity, Integer> {
  
    @Query(value = "SELECT NUM_FOLIO FROM SINTRANST_SINIESTROS "
           ,nativeQuery = true)
    List<SiniestrosEntity> getSiniestro();

}
