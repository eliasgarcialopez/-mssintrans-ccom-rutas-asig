package mx.gob.imss.mssistrans.ccom.rutas.repository;

import java.util.List;

import mx.gob.imss.mssistrans.ccom.rutas.model.BitacoraServiciosAsignacionEntity;
import mx.gob.imss.mssistrans.ccom.rutas.model.BitacoraServiciosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BitacoraServiciosAsigRepository extends JpaRepository<BitacoraServiciosAsignacionEntity, Integer> {

	

}
