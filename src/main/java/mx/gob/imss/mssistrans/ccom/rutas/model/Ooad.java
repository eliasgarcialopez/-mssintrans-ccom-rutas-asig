package mx.gob.imss.mssistrans.ccom.rutas.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SINTRANSC_OOAD")
public class Ooad implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_OOAD", unique = false, nullable = true) 
	private Integer idOoad;

	@Column(name = "NOM_OOAD", unique = false, nullable = true) 
	private String nomOoad;

	@Column(name = "FEC_ALTA", unique = false, nullable = true)
	private String fecAlta;

	@Column(name = "FEC_ACTUALIZACION", unique = false, nullable = true)
	private String fecActualizacion;

	@Column(name = "FEC_BAJA", unique = false, nullable = true)
	private String fecBaja;
	
	@Column(name = "IND_ACTIVO", unique = false, nullable = true)
	private Integer indActivo;

	@Column(name = "IND_SISTEMA", unique = false, nullable = true)
	private Integer indSistema;
}
