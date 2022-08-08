package mx.gob.imss.mssistrans.rutas.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mx.gob.imss.mssistrans.rutas.model.Rutas;
import mx.gob.imss.mssistrans.rutas.model.RutasDestinos;

public interface RutasDestinosRepository  extends JpaRepository<RutasDestinos, Integer> {


}
