package mx.gob.imss.mssistrans.ccom.rutas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.imss.mssistrans.ccom.rutas.model.EccoEntity;


@Repository
public interface EccoRepository extends JpaRepository<EccoEntity, Integer> {

    @Query(value = "SELECT SV.ID_VEHICULO, SV.CVE_ECCO, SV.NUM_PLACAS"
    		+ " FROM SINTRANST_VEHICULOS SV"
    		+ " INNER JOIN SINTRANST_CONTROL_RUTAS SCA ON SCA.ID_VEHICULO = SV.ID_VEHICULO"
    		+ " WHERE SV.IND_ACTIVO = 1 AND SV.IND_SISTEMA = 1"
    		+ " AND SCA.ID_RUTA = ?"
            , countQuery = "SELECT COUNT(SV.ID_VEHICULO)"
            		+ " FROM SINTRANST_VEHICULOS SV"
            		+ " WHERE SV.IND_ACTIVO = 1 AND SV.IND_SISTEMA = 1"
            ,nativeQuery = true)
    List<EccoEntity> getEcco(Integer idRuta);

}
