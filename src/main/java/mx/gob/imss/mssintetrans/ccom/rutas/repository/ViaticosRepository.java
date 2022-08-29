package mx.gob.imss.mssintetrans.ccom.rutas.repository;

import mx.gob.imss.mssintetrans.ccom.rutas.model.Viaticos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViaticosRepository extends JpaRepository<Viaticos, Integer> {
    Viaticos findByControlRutasIdControlRuta(Integer idControlRuta);
}
