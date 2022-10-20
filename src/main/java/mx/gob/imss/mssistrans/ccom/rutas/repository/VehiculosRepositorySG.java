package mx.gob.imss.mssistrans.ccom.rutas.repository;

import java.util.List;

import mx.gob.imss.mssistrans.ccom.rutas.model.Vehiculos;
import mx.gob.imss.mssistrans.ccom.rutas.model.VehiculosEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface VehiculosRepositorySG extends JpaRepository<VehiculosEntity, Integer> {
	 
	 
}
