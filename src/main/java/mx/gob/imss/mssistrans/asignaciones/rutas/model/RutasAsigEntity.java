package mx.gob.imss.mssistrans.asignaciones.rutas.model;

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
@Table(name = "SINTRANST_RUTAS")
public class RutasAsigEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_RUTA", unique = false, nullable = true)
	private String idRuta;

	@Column(name = "DES_SERVICIO", unique = false, nullable = true)
	private String desServicio;
	

	@Column(name = "ID_SOLICITUD", unique = false, nullable = true)
	private String idSolicitud;
}

