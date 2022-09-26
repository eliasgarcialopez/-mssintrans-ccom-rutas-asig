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
@Table(name = "SINTRANST_SOLICITUD_TRASLADO")
public class SolTrasladoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_SOLICITUD", unique = false, nullable = true)
	private String idSolicitud;
	
	@Column(name = "NUM_FOLIO_ACEPTACION", unique = false, nullable = true)
	private String numFolioAceptacion;
}

