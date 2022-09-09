package mx.gob.imss.mssistrans.ccom.rutas.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.imss.mssistrans.ccom.rutas.model.SiniestrosEntity;

@Repository
public interface SiniestrosRepository extends JpaRepository<SiniestrosEntity, Integer> {
  
    @Query(value = "SELECT ID_SINIESTRO,NUM_FOLIO FROM SINTRANST_SINIESTROS "
           ,nativeQuery = true)
    List<SiniestrosEntity> getSiniestro();

}
