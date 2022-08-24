package mx.gob.imss.mssintetrans.ccom.rutas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.gob.imss.mssintetrans.ccom.rutas.model.Vehiculos;


@Repository
public interface VehiculosRepository extends JpaRepository<Vehiculos, Integer> {
	 /**
     * Recupera los ECCO vehiculos arrendados que esten en operacion y sean de centracom
     *
     * @param 
     * @param activo
     * @return
     */

	@Query(value = "select v.* from SINTRANST_VEHICULOS v INNER JOIN SINTRANST_ZONAS_DETALLE zd ON zd.ID_VEHICULO=v.ID_VEHICULO "
			+ " INNER JOIN  SINTRANST_ARRENDATARIOS a on a.ID_ARRENDATARIO =v.ID_ARRENDATARIO "
			+ "where zd.ID_ZONAS_ATENCION = ?1 and v.IND_ACTIVO = 1 "
			+ "and v.IND_SISTEMA=1 and v.DES_ESTATUS_VEHICULO in ('8') "
	        + "and (v.IND_ARRENDADO = 1 and a.FEC_FIN_CONTRATO > curdate()) ",nativeQuery =true)
	
	List<Vehiculos> findVehiculoArrendadosAsignables(Integer idZona);
	

	
	 /**
     * Recupera los ECCO vehiculos no arrendados que esten en operacion y sean de centracom
     *
     * @param 
     * @param activo
     * @return
     */

	@Query(value = "select v.* from SINTRANST_VEHICULOS v INNER JOIN SINTRANST_ZONAS_DETALLE zd ON zd.ID_VEHICULO=v.ID_VEHICULO "
			
			+ "where zd.ID_ZONAS_ATENCION = ?1 and v.IND_ACTIVO = 1 "
			+ "and v.IND_SISTEMA=1 and v.DES_ESTATUS_VEHICULO in ('8') "
	        + "and (v.IND_ARRENDADO = 0 ) ",nativeQuery =true)
	
	List<Vehiculos> findVehiculoAsignables(Integer idZona);

	 /**
     * los vehiculos que se trengan asignados   al mismo centracom o modulo de la misma OOAD PENDIENTE
     * se incorpora status de
     * @param idArrendadora
     * @param activo
     * @return
     */
	
	@Query(value = "select count(v.ID_VEHICULO) from SINTRANST_VEHICULOS v INNER JOIN SINTRANST_ZONAS_DETALLE zd ON zd.ID_VEHICULO=v.ID_VEHICULO "
			
			+ "where zd.ID_ZONAS_ATENCION = ?1 and v.IND_ACTIVO = 1 "
			+ "and v.IND_SISTEMA=1 "
	        ,nativeQuery =true)
	
	Integer countTotalVehiculoAsignados(Integer idZona);
	
	 /**
     * vehiculos siniestrado se totalizan los que  tenga ese estatus  al mismo centracom o modulo de la misma OOAD

     *
     * @param idArrendadora
     * @param activo
     * @return
     */
	
@Query(value = "select count(v.ID_VEHICULO) from SINTRANST_VEHICULOS v INNER JOIN SINTRANST_ZONAS_DETALLE zd ON zd.ID_VEHICULO=v.ID_VEHICULO "
			
			+ "where zd.ID_ZONAS_ATENCION = ?1 and v.IND_ACTIVO = 1 and v.DES_ESTATUS_VEHICULO in ('2') "
			+ "and v.IND_SISTEMA=1 "
	        ,nativeQuery =true)

	Integer countTotalVehiculoSiniestrados(Integer idZona);
	
	 /**
     * vehiculos en mantenimiento se totalizan  lo que tenga ese estatus   asignados al mismo centracom o modulo de la misma OOAD
     *
     * @param idArrendadora
     * @param activo
     * @return
     */

@Query(value = "select count(v.ID_VEHICULO) from SINTRANST_VEHICULOS v INNER JOIN SINTRANST_ZONAS_DETALLE zd ON zd.ID_VEHICULO=v.ID_VEHICULO "

			+ "where zd.ID_ZONAS_ATENCION = ?1 and v.IND_ACTIVO = 1 and v.DES_ESTATUS_VEHICULO in ('4') "
			+ "and v.IND_SISTEMA=1 "
	        ,nativeQuery =true)


	Integer countTotalVehiculoMantenimiento(Integer idZona);
	
	 /**
     * los vehiculos con esatus en Operacion  asignados al mismo centracom o modulo de la misma OOAD PENDIENTE
     *
     * @param idArrendadora
     * @param activo
     * @return
     */
@Query(value = "select count(v.ID_VEHICULO) from SINTRANST_VEHICULOS v INNER JOIN SINTRANST_ZONAS_DETALLE zd ON zd.ID_VEHICULO=v.ID_VEHICULO "

			+ "where zd.ID_ZONAS_ATENCION = ?1 and v.IND_ACTIVO = 1 and v.DES_ESTATUS_VEHICULO in ('8') "
			+ "and v.IND_SISTEMA=1 "
	        ,nativeQuery =true)

	Integer countTotalVehiculoDisponibles(Integer idZona);
}
