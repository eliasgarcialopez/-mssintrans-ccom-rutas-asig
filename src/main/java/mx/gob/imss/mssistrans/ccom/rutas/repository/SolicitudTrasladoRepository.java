package mx.gob.imss.mssistrans.ccom.rutas.repository;

import java.util.List;

import mx.gob.imss.mssistrans.ccom.rutas.model.SolicitudTraslado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;

@Repository
public interface SolicitudTrasladoRepository extends JpaRepository<SolicitudTraslado, Integer> {


/**
  * Recupera las solicitudes aceptadas de tipo de Servicio TRASLADO PACIENTES y activo por turno.
  *turno sela Matutino horario del turno 06.01 a 14:00, 
  *vespertino horario del turno 14.01 a 19:00,
  *nocturno o especial horario del turno 19.01 a 06:00
  * 1 acepta 2 rechazada 3 cancelada 4 asignada.

  * @param idArrendadora
  * @param activo
  * @return
  */
	@Query(value = "SELECT SO FROM SolicitudTraslado SO "
			+ "WHERE SO.activo = 1 and SO.indiceSistema = 1"
			+ " AND  SO.timSolicitud between ?1 and ?2 "
			+ "AND SO.desEstatusSolicitud IN ('1') ")
	List<SolicitudTraslado> findSolicitudTrasladoAceptadas(LocalTime hrInicio,LocalTime hrfin );
	
	
	@Query(value = "SELECT SO FROM SolicitudTraslado SO "
			+ "WHERE SO.activo = 1 and SO.indiceSistema = 1"
			+ " AND  SO.timSolicitud between ?1 and ?2 "
			+ "AND SO.desEstatusSolicitud IN ('1') ")
	List<SolicitudTraslado> findSolicitudTrasladoAceptadas(String hrInicio,String hrfin );

	
}
