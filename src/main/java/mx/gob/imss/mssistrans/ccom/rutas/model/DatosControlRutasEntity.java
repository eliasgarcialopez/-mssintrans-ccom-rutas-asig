package mx.gob.imss.mssistrans.ccom.rutas.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SINTRANST_CONTROL_RUTAS")
public class DatosControlRutasEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_CONTROL_RUTA", unique = false, nullable = false)
	private Integer idControlRuta;
	 
	@Column(name = "ID_VEHICULO", unique = false, nullable = false)
	private Integer idVehiculo;

	@Column(name = "ID_RUTA")
	private String idRuta;
	
	@Column(name = "ID_SOLICITUD", unique = false, nullable = true)
	private String idSolicitud;
	
	@Column(name = "CVE_ECCO")
	private String cveEcco;
	
	@Column(name = "NUM_PLACAS")
	private String numPlacas;
	
	@Column(name = "DES_ESTATUS_ASIGNA")
	private String desEstatus;
	
}
