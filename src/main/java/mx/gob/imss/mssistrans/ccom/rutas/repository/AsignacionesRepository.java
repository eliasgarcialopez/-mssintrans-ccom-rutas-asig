package mx.gob.imss.mssistrans.ccom.rutas.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.imss.mssistrans.ccom.rutas.model.AsignacionesEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface AsignacionesRepository extends JpaRepository<AsignacionesEntity, Integer> {
	
	@Query(value = ""
			+ "SELECT	DISTINCT(ASI.NUM_FOLIO_TJTA_COMBUSTIBLE)							"
			+ "FROM	SINTRANST_ASIGNACIONES ASI			"
			+ "INNER	JOIN SINTRANST_VEHICULOS VEH ON ASI.ID_VEHICULO = VEH.ID_VEHICULO	"
			+ "INNER	JOIN SINTRANSC_UNIDADES_ADSCRIPCION UNI ON UNI.ID_UNIDAD_ADSCRIPCION = VEH.ID_UNIDAD_ADSCRIPCION	"
			+ "WHERE	ASI.IND_ACTIVO = '1'	"
			+ "AND 	DATE(ASI.FEC_ALTA) >=CURRENT_DATE()	"
			+ "AND     ASI.DES_ESTATUS = '1'	"
			+ "AND		UNI.ID_OOAD = ?	"
			+ "",
		        nativeQuery = true)
    ArrayList<String> findTarjetasByOoad(Integer idOoad);
	
	@Query(value ="SELECT * FROM SINTRANST_ASIGNACIONES SA "
            + "LEFT JOIN SINTRANST_VEHICULOS SV USING (ID_VEHICULO) "
            + "LEFT JOIN SINTRANST_CHOFERES CH USING (ID_CHOFER) "
            + "LEFT JOIN SINTRANSC_UNIDADES_ADSCRIPCION UA ON UA.ID_UNIDAD_ADSCRIPCION = SV.ID_UNIDAD_ADSCRIPCION "
            + "WHERE UA.ID_OOAD = ? AND SA.IND_ACTIVO = 1 ",
            countQuery = "SELECT COUNT(*) FROM SINTRANST_ASIGNACIONES SA "
            + "LEFT JOIN SINTRANST_VEHICULOS SV USING (ID_VEHICULO) "
            + "LEFT JOIN SINTRANST_CHOFERES CH USING (ID_CHOFER) "
            + "LEFT JOIN SINTRANSC_UNIDADES_ADSCRIPCION UA ON UA.ID_UNIDAD_ADSCRIPCION = SV.ID_UNIDAD_ADSCRIPCION  "
            + "WHERE UA.ID_OOAD = ? AND SA.IND_ACTIVO = 1", nativeQuery = true)
    Page<List<AsignacionesEntity>> findAsignacionesByOoad(Integer idOoad, Pageable page);

	@Query(value ="SELECT * FROM SINTRANST_ASIGNACIONES SA "
			+ "LEFT JOIN SINTRANST_VEHICULOS SV USING (ID_VEHICULO) "
			+ "LEFT JOIN SINTRANST_CHOFERES CH USING (ID_CHOFER) "
			+ "LEFT JOIN SINTRANSC_UNIDADES_ADSCRIPCION UA ON UA.ID_UNIDAD_ADSCRIPCION = SV.ID_UNIDAD_ADSCRIPCION "
			+ "WHERE SA.IND_ACTIVO = 1 ",
			countQuery = "SELECT COUNT(*) FROM SINTRANST_ASIGNACIONES SA "
					+ "LEFT JOIN SINTRANST_VEHICULOS SV USING (ID_VEHICULO) "
					+ "LEFT JOIN SINTRANST_CHOFERES CH USING (ID_CHOFER) "
					+ "LEFT JOIN SINTRANSC_UNIDADES_ADSCRIPCION UA ON UA.ID_UNIDAD_ADSCRIPCION = SV.ID_UNIDAD_ADSCRIPCION  "
					+ "WHERE SA.IND_ACTIVO = 1", nativeQuery = true)
     Page<List<AsignacionesEntity>> findAsignacionesGenerales(Pageable page);
	
	
	@Query(value = "SELECT * FROM SINTRANST_ASIGNACIONES SA "
	         + "INNER JOIN SINTRANST_VEHICULOS SV USING (ID_VEHICULO) "
		     + "INNER JOIN SINTRANST_CHOFERES CH USING (ID_CHOFER) "
		     + "INNER JOIN SINTRANSC_UNIDADES_ADSCRIPCION UA ON UA.ID_UNIDAD_ADSCRIPCION = SV.ID_UNIDAD_ADSCRIPCION "
            + "WHERE UA.ID_OOAD = ? AND SA.IND_ACTIVO = 1 "
            + " AND SA.FEC_ALTA >=CURRENT_DATE ", 
            countQuery = "SELECT COUNT(*) FROM SINTRANST_ASIGNACIONES SA "
            + "INNER JOIN SINTRANST_VEHICULOS SV USING (ID_VEHICULO) "
            + "INNER JOIN SINTRANSC_UNIDADES_ADSCRIPCION USING (ID_UNIDAD_ADSCRIPCION) "
            + "WHERE UA.ID_OOAD = ? AND SA.IND_ACTIVO = 1 "
            + " AND SA.FEC_ALTA >=CURRENT_DATE ",
            nativeQuery = true)
	List<AsignacionesEntity> listaAsignacionesOoad(Integer idOoad);
	
	@Query(value = "SELECT * FROM SINTRANST_ASIGNACIONES SA "
	         + "INNER JOIN SINTRANST_VEHICULOS SV USING (ID_VEHICULO) "
		     + "INNER JOIN SINTRANST_CHOFERES CH USING (ID_CHOFER) "
		     + "INNER JOIN SINTRANSC_UNIDADES_ADSCRIPCION UA ON UA.ID_UNIDAD_ADSCRIPCION = SV.ID_UNIDAD_ADSCRIPCION "
           + "AND SA.IND_ACTIVO = 1 "
           + " AND SA.FEC_ALTA >=CURRENT_DATE ", 
           countQuery = "SELECT COUNT(*) FROM SINTRANST_ASIGNACIONES SA "
           + "INNER JOIN SINTRANST_VEHICULOS SV USING (ID_VEHICULO) "
           + "INNER JOIN SINTRANSC_UNIDADES_ADSCRIPCION USING (ID_UNIDAD_ADSCRIPCION) "
           + "AND SA.IND_ACTIVO = 1 "
           + " AND SA.FEC_ALTA >=CURRENT_DATE ",
           nativeQuery = true)
	List<AsignacionesEntity> listaAsignacionesAdmin();
	
	
	@Query(value = "select a from AsignacionesEntity a join a.vehiculo v join v.unidad u "
            + "where a.numAsignacion = ?1 and u.ooad.idOoad = ?2 and a.indActivo = 1 ")
    List<AsignacionesEntity> findAsignacionesByNumNoAdmin(String numAsignacion, Integer idOoad);
	
	@Query(value = "select a from AsignacionesEntity a join a.vehiculo v join v.unidad u "
            + "where a.numAsignacion = ?1 and a.indActivo = 1 ")
    List<AsignacionesEntity> findAsignacionesByNumAdmin(String numAsignacion);
	
	@Query(value = "select max(substring(a.numAsignacion,9,4)) from AsignacionesEntity a "
			     + "where substring(a.numAsignacion,6,2) = ?1 ")
	String findUltimoFolioByOoad(String clavePresup);
	
	@Query(value = "select a from AsignacionesEntity a join a.vehiculo v join v.unidad u "
			+ "where v.cveEcco = ?1 and u.ooad.idOoad = ?2 and v.indActivo = 1 "
			+ "order by a.fechaAlta desc")
	List<AsignacionesEntity> findVehiculoByEcco(String ecco, Integer idOoad);

    @Query(value = ""
			+ "SELECT	STA.*, STV.*, SUA.* "
			+ "FROM		SINTRANST_RUTAS_ASIGNACIONES STA "
			+ "INNER 	JOIN SINTRANST_VEHICULOS STV ON STV.ID_VEHICULO = STA.ID_VEHICULO "
			+ "INNER 	JOIN SINTRANSC_UNIDADES_ADSCRIPCION SUA ON SUA.ID_UNIDAD_ADSCRIPCION = STV.ID_UNIDAD_ADSCRIPCION "
			+ "WHERE    STA.ID_RUTA_ASIGNACION = ?1 "
			+ "AND   	STV.IND_ACTIVO 	= '1' "
			+ "",
			nativeQuery = true )
	AsignacionesEntity consultaPorIdRuta (Integer idRutaAs);
    
    @Query(value = "SELECT * FROM SINTRANST_ASIGNACIONES SA "
	         + "INNER JOIN SINTRANST_VEHICULOS SV USING (ID_VEHICULO) "
		     + "INNER JOIN SINTRANST_CHOFERES CH USING (ID_CHOFER) "
		     + "INNER JOIN SINTRANSC_UNIDADES_ADSCRIPCION UA ON UA.ID_UNIDAD_ADSCRIPCION = SV.ID_UNIDAD_ADSCRIPCION "
           + "WHERE SA.ID_VEHICULO = ? AND SA.IND_ACTIVO = 1 "
           + " AND SA.FEC_ALTA >=CURRENT_DATE "
           + " AND SA.DES_ESTATUS = 1",
           nativeQuery = true)
	List<AsignacionesEntity> listaAsignacionesVehiculos(Integer idVehiculo);
    
    @Query(value = "SELECT * FROM SINTRANST_ASIGNACIONES SA "
	         + "INNER JOIN SINTRANST_VEHICULOS SV USING (ID_VEHICULO) "
		     + "INNER JOIN SINTRANST_CHOFERES CH USING (ID_CHOFER) "
		     + "INNER JOIN SINTRANSC_UNIDADES_ADSCRIPCION UA ON UA.ID_UNIDAD_ADSCRIPCION = SV.ID_UNIDAD_ADSCRIPCION "
          + "WHERE SA.ID_CHOFER = ? AND SA.IND_ACTIVO = 1 "
          + " AND SA.FEC_ALTA >=CURRENT_DATE "
          + " AND SA.DES_ESTATUS = 1",
           nativeQuery = true)
	List<AsignacionesEntity> listaAsignacionesChofer(Long idChofer);
    
    @Query(value = "SELECT ASI.ID_ASIGNACION "
    		+ "FROM SINTRANST_ASIGNACIONES ASI "
    		+ "WHERE ASI.IND_ACTIVO = '1' "
    		+ "AND DATE(ASI.FEC_ALTA) >=CURRENT_DATE() "
    		+ "AND ASI.ID_RUTA = ? ",
          nativeQuery = true)
	List<Integer> listaPorIdRuta( Integer idRuta );
}
