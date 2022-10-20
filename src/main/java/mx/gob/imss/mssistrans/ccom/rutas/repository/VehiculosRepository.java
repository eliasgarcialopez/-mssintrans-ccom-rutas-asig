package mx.gob.imss.mssistrans.ccom.rutas.repository;

import java.util.List;

import mx.gob.imss.mssistrans.ccom.rutas.model.Vehiculos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface VehiculosRepository extends JpaRepository<Vehiculos, Integer> {
	 /**
     * Recupera los ECCO vehiculos arrendados que esten en operacion y sean de centracom
     *
     * @param 
     * @param activo
     * @return
     */

	@Query(value = "select v.* from SINTRANST_VEHICULOS v "
			+ "INNER JOIN SINTRANST_ZONAS_DETALLE zd ON zd.ID_VEHICULO=v.ID_VEHICULO "
			+ "INNER JOIN  SINTRANST_ARRENDATARIOS a on a.ID_ARRENDATARIO =v.ID_ARRENDATARIO "
			+ "where zd.ID_ZONAS_ATENCION = ?1 "
			+ "and v.IND_ACTIVO = 1 "
			+ "and zd.IND_ACTIVO = 1 "
			+ "and v.DES_ESTATUS_VEHICULO in ('8') "
			+ "and v.DES_TIPO_SERVICIO IN ('9', '10', '11') " 
	        + "and (v.IND_ARRENDADO = 1 and a.FEC_FIN_CONTRATO > curdate()) ",nativeQuery =true)
	List<Vehiculos> findVehiculoArrendadosAsignables(Integer idZona);
	
	 /**
     * Recupera los ECCO vehiculos no arrendados que esten en operacion y sean de centracom
     *
     * @param 
     * @param activo
     * @return
     */

	@Query(value = "select v.* from SINTRANST_VEHICULOS v " +
			"INNER JOIN SINTRANST_ZONAS_DETALLE zd ON zd.ID_VEHICULO=v.ID_VEHICULO "
			+ "where zd.ID_ZONAS_ATENCION = ?1 "
			+ "and v.IND_ACTIVO = 1 "
			+ "and zd.IND_ACTIVO = 1 "
			+ "and v.DES_ESTATUS_VEHICULO in ('8') "
			+ "and v.DES_TIPO_SERVICIO IN ('9', '10', '11') " 
	        + "and v.IND_ARRENDADO = 0 ",nativeQuery =true)
	List<Vehiculos> findVehiculoAsignables(Integer idZona);

	@Query(value = "SELECT v.* " +
			" FROM SINTRANST_MODULO_AMBULANCIA MA " +
			" INNER JOIN SINTRANSC_UNIDADES_ADSCRIPCION UA ON (MA.DES_NOMBRE=UA.NOM_UNIDAD_ADSCRIPCION) " +
			" INNER JOIN SINTRANST_VEHICULOS v ON (v.ID_UNIDAD_ADSCRIPCION=UA.ID_UNIDAD_ADSCRIPCION) " +
			" where MA.IND_ACTIVO =1 AND v.IND_ACTIVO =1 AND UA.ID_OOAD=?1 " +
			" and v.DES_ESTATUS_VEHICULO in ('8') and v.DES_TIPO_SERVICIO in ('9', '10', '11')",nativeQuery =true)
	List<Vehiculos> findVehiculoAsignablesByOaad(Integer idOaad);

	 /**
     * los vehiculos que se trengan asignados al mismo centracom o modulo de la misma OOAD PENDIENTE
     * se incorpora status de
     * @param activo
     * @return
     */

	 @Query(value = "select count(v.ID_VEHICULO) from SINTRANST_VEHICULOS v "
			 + "INNER JOIN SINTRANST_ZONAS_DETALLE zd ON zd.ID_VEHICULO=v.ID_VEHICULO "
			 + "where zd.ID_ZONAS_ATENCION = ?1 "
			 + "and v.IND_ACTIVO = 1 "
			 + "and zd.IND_ACTIVO = 1 "
			 + "and v.DES_TIPO_SERVICIO IN ('9', '10', '11') "
			 + "and v.DES_ESTATUS_VEHICULO in ('2', '4', '8') "
			 , nativeQuery = true)
	Integer countTotalVehiculoAsignados(Integer idZona);

	@Query(value = "select count(v.ID_VEHICULO) from SINTRANST_VEHICULOS v "
			+ "where v.ID_UNIDAD_ADSCRIPCION = ?1 "
			+ "and v.IND_ACTIVO = 1 "
			+ "and v.DES_TIPO_SERVICIO IN ('9', '10', '11') "
			, nativeQuery = true)
	Integer countTotalVehiculoAsignadosByUnidad(Integer idUnidad);
	
	 /**
     * vehiculos siniestrado se totalizan los que  tenga ese estatus  al mismo centracom o modulo de la misma OOAD
     *
     * @param activo
     * @return
     */

	 @Query(value = "select count(v.ID_VEHICULO) from SINTRANST_VEHICULOS v "
			 + "INNER JOIN SINTRANST_ZONAS_DETALLE zd ON zd.ID_VEHICULO=v.ID_VEHICULO "
			 + "where zd.ID_ZONAS_ATENCION = ?1 "
			 + "and v.IND_ACTIVO = 1 "
			 + "and zd.IND_ACTIVO = 1 "
			 + "and v.DES_TIPO_SERVICIO IN ('9', '10', '11') "
			 + "and v.DES_ESTATUS_VEHICULO in ('2') "
			 , nativeQuery = true)
	Integer countTotalVehiculoSiniestrados(Integer idZona);

	@Query(value = "select count(v.ID_VEHICULO) from SINTRANST_VEHICULOS v "
			+ "where v.ID_UNIDAD_ADSCRIPCION = ?1 "
			+ "and v.IND_ACTIVO = 1 "
			+ "and v.DES_TIPO_SERVICIO IN ('9', '10', '11') "
			+ "and v.DES_ESTATUS_VEHICULO in ('2') "
			, nativeQuery = true)
	Integer countTotalVehiculoSiniestradosByUnidad(Integer idUnidad);
	
	 /**
     * vehiculos en mantenimiento se totalizan  lo que tenga ese estatus   asignados al mismo centracom o modulo de la misma OOAD
     *
     * @param activo
     * @return
     */

	 @Query(value = "select count(v.ID_VEHICULO) from SINTRANST_VEHICULOS v "
			 + "INNER JOIN SINTRANST_ZONAS_DETALLE zd ON zd.ID_VEHICULO=v.ID_VEHICULO "
			 + "where zd.ID_ZONAS_ATENCION = ?1 "
			 + "and v.IND_ACTIVO = 1 "
			 + "and zd.IND_ACTIVO = 1 "
			 + "and v.DES_TIPO_SERVICIO IN ('9', '10', '11') "
			 + "and v.DES_ESTATUS_VEHICULO in ('4') "
			 , nativeQuery = true)
	 Integer countTotalVehiculoMantenimiento(Integer idZona);

	@Query(value = "select count(v.ID_VEHICULO) from SINTRANST_VEHICULOS v "
			+ "where v.ID_UNIDAD_ADSCRIPCION = ?1 "
			+ "and v.IND_ACTIVO = 1 "
			+ "and v.DES_TIPO_SERVICIO IN ('9', '10', '11') "
			+ "and v.DES_ESTATUS_VEHICULO in ('4') "
			, nativeQuery = true)
	Integer countTotalVehiculoMantenimientoByUnidad(Integer idUnidad);
	
	 /**
     * los vehiculos con esatus en Operacion  asignados al mismo centracom o modulo de la misma OOAD PENDIENTE
     *
     * @param activo
     * @return
     */
	 @Query(value = "select count(v.ID_VEHICULO) from SINTRANST_VEHICULOS v "
			 + "INNER JOIN SINTRANST_ZONAS_DETALLE zd ON zd.ID_VEHICULO=v.ID_VEHICULO "
			 + "where zd.ID_ZONAS_ATENCION = ?1 "
			 + "and v.IND_ACTIVO = 1 "
			 + "and zd.IND_ACTIVO = 1 "
			 + "and v.DES_TIPO_SERVICIO IN ('9', '10', '11') "
			 + "and v.DES_ESTATUS_VEHICULO in ('8') "
			 , nativeQuery = true)
	 Integer countTotalVehiculoDisponibles(Integer idZona);

	@Query(value = "select count(v.ID_VEHICULO) from SINTRANST_VEHICULOS v "
			+ "where v.ID_UNIDAD_ADSCRIPCION = ?1 "
			+ "and v.IND_ACTIVO = 1 "
			+ "and v.DES_TIPO_SERVICIO IN ('9', '10', '11') "
			+ "and v.DES_ESTATUS_VEHICULO in ('8','9') "
			, nativeQuery = true)
	Integer countTotalVehiculoDisponiblesByUnidad(Integer idUnidad);
	 
}
