package mx.gob.imss.mssistrans.ccom.rutas.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.imss.mssistrans.ccom.rutas.model.RegistroRecorridoEntity;

@Repository
public interface RegistroRecorridoRepository extends JpaRepository<RegistroRecorridoEntity, Integer> {
  
    @Query(value = "SELECT SRA.TIM_HORA_INICIO_ASIGNACION, SRA.ID_RUTA1, SRA.TIM_HORA_INICIO_RUTA_1, SRA.TIM_HORA_FIN_RUTA_1"
    		+ " , SRA.ID_RUTA2, SRA.TIM_HORA_INICIO_RUTA_2, SRA.TIM_HORA_FIN_RUTA_2, SRA.ID_RUTA3, SRA.TIM_HORA_INICIO_RUTA_3"
    		+ " , SRA.TIM_HORA_FIN_RUTA_3, SRA.ID_VEHICULO, SRA.ID_RUTA_ASIGNACION, SRA.DES_TIPO_INCIDENTE"
    		+ " FROM SINTRANST_RUTAS_ASIGNACIONES SRA "
    		+ " WHERE SRA.ID_VEHICULO = ? AND SRA.ID_RUTA = ?  "
           ,nativeQuery = true)
    RegistroRecorridoEntity getRegistroRecorrido(Integer idVehiculo, Integer idRuta);

}
