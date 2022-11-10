package mx.gob.imss.mssistrans.ccom.rutas.repository;


import mx.gob.imss.mssistrans.ccom.rutas.dto.DatosRegistroRecorridoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.gob.imss.mssistrans.ccom.rutas.model.RegistroRecorridoEntity;

@Repository
public interface RegistroRecorridoRepository extends JpaRepository<RegistroRecorridoEntity, Integer> {

	@Query(value = " SELECT SCR.ID_VEHICULO, "
			+ "SCR.FEC_INICIO_ASIGNA, "
			+ "SCR.TIM_INICIO_ASIGNA, SCR.DES_ESTATUS_ASIGNA, "
			+ "SR.TIM_HORARIO_INICIAL,"
			+ "SR.TIM_HORARIO_FINAL,"
			+ ", SRD.ID_DESTINO, SRD.TIM_HORA_INICIO, SRD.TIM_HORA_FIN"
			+ " FROM SINTRANST_CONTROL_RUTAS SCR"
			+ " INNER JOIN SINTRANST_RUTAS SR ON SR.ID_RUTA = SCR.ID_RUTA"
			+ " INNER JOIN SINTRANST_RUTAS_DESTINOS SRD ON SRD.ID_RUTA = SCR.ID_RUTA"
			+ " WHERE SCR.ID_CONTROL_RUTA = ? "
			, nativeQuery = true)
    RegistroRecorridoEntity getRegistroRecorrido(Integer idControlRuta);


    @Query(value = " SELECT SCR.ID_VEHICULO, SCR.FEC_INICIO_ASIGNA, SCR.TIM_INICIO_ASIGNA, SCR.DES_ESTATUS_ASIGNA"
    		+ " , SRD.ID_DESTINO, SRD.TIM_HORA_INICIO, SRD.TIM_HORA_FIN"
    		+ " FROM SINTRANST_CONTROL_RUTAS SCR"
    		+ " INNER JOIN SINTRANST_RUTAS_DESTINOS SRD ON SRD.ID_RUTA = SCR.ID_RUTA"
    		+ " WHERE SCR.ID_RUTA = ? AND SCR.ID_SOLICITUD = ? AND SCR.ID_VEHICULO = ?  "
           ,nativeQuery = true)
    RegistroRecorridoEntity getRegistroRecorridoByIdRuta(Integer idRuta, Integer idSolicitud,Integer idVehiculo);
    
    
	@Modifying(flushAutomatically = true)
	@Query(value = "UPDATE SINTRANST_RUTAS_ASIGNACIONES SET TIM_HORA_INICIO_ASIGNACION = ?"
			+ " , ID_RUTA1 = ?, TIM_HORA_INICIO_RUTA_1 = ?, TIM_HORA_FIN_RUTA_1 = ?"
			+ " , ID_RUTA2 = ?, TIM_HORA_INICIO_RUTA_2 = ?, TIM_HORA_FIN_RUTA_2 = ?"
			+ " , ID_RUTA3 = ?, TIM_HORA_INICIO_RUTA_3 = ?, TIM_HORA_FIN_RUTA_3 = ?"
			+ " , DES_TIPO_INCIDENTE = ?, FEC_ACTUALIZACION = CURRENT_TIMESTAMP()"
			+ " WHERE ID_VEHICULO = ? AND ID_RUTA = ?"
			,nativeQuery = true )
	void update (String horaInicioAsignacion
			, String idRuta1, String hrInicio1, String hrFin1
			, String idRuta2, String hrInicio2, String hrFin2
			, String idRuta3, String hrInicio3, String hrFin3
			, String estatusTraslado, String idVehiculo, String idRuta);

    @Query("SELECT new mx.gob.imss.mssistrans.ccom.rutas.dto.DatosRegistroRecorridoDTO("
            + " cr.idVehiculo.idVehiculo, cr.fechaInicioAsigna, cr.timInicioAsigna, cr.desEstatusAsigna, "
            + " rd.idDestino, "
            + " (SELECT uni.nomUnidadAdscripcion FROM UnidadAdscripcion uni where uni.idUnidadAdscripcion = ru.idUnidadDestino ) as nomUnidadDestino, "
            + " (SELECT CONCAT(sol.desColonia,', ',sol.desCalle,', ',sol.numExterior,', ',sol.numInterior) FROM SolicitudTraslado sol WHERE sol.idSolicitud = cr.idSolcitud) as destinoParticular, "
            + " rd.timHoraInicio, rd.timHoraFin, ru.idOrigen, "
            + " (SELECT uni.nomUnidadAdscripcion FROM UnidadAdscripcion uni where uni.idUnidadAdscripcion = ru.idOrigen ) as nomUnidadOrigen, "
            + " ru.timHorarioInicial, ru.timHorarioFinal) "
            + " FROM ControlRutas AS cr "
            + " INNER JOIN Rutas AS ru ON ru.idRuta = cr.ruta.idRuta "
            + " INNER JOIN RutasDestinos AS rd ON rd.ruta.idRuta = cr.ruta.idRuta "
            + " WHERE cr.ruta.idRuta = :idRuta "
            + " AND cr.idSolcitud.idSolicitud = :idSolicitud "
            + " AND cr.idVehiculo.idVehiculo = :idVehiculo ")
    DatosRegistroRecorridoDTO getRegistroRecorridoByIdRutaIdSolicitudIdVehiculo(@Param("idRuta") Integer idRuta, 
            @Param("idSolicitud") Integer idSolicitud, @Param("idVehiculo") Integer idVehiculo);
    
    @Query("SELECT new mx.gob.imss.mssistrans.ccom.rutas.dto.DatosRegistroRecorridoDTO("
            + " cr.idVehiculo.idVehiculo, cr.fechaInicioAsigna, cr.timInicioAsigna, cr.desEstatusAsigna, "
            + " rd.idDestino, "
            + " (SELECT uni.nomUnidadAdscripcion FROM UnidadAdscripcion uni where uni.idUnidadAdscripcion = ru.idUnidadDestino ) as nomUnidadDestino, "
            + " (SELECT CONCAT(sol.desColonia,', ',sol.desCalle,', ',sol.numExterior,', ',sol.numInterior) FROM SolicitudTraslado sol WHERE sol.idSolicitud = cr.idSolcitud) as destinoParticular, "
            + " rd.timHoraInicio, rd.timHoraFin, ru.idOrigen, "
            + " (SELECT uni.nomUnidadAdscripcion FROM UnidadAdscripcion uni where uni.idUnidadAdscripcion = ru.idOrigen ) as nomUnidadOrigen, "
            + " ru.timHorarioInicial, ru.timHorarioFinal) "
            + " FROM ControlRutas AS cr "
            + " INNER JOIN Rutas AS ru ON ru.idRuta = cr.ruta.idRuta "
            + " INNER JOIN RutasDestinos AS rd ON rd.ruta.idRuta = cr.ruta.idRuta "
            + " WHERE cr.idControlRuta = :idControlRuta ")
    DatosRegistroRecorridoDTO getRegistroRecorridoByRuta(@Param("idControlRuta") Integer idControlRuta);

}
