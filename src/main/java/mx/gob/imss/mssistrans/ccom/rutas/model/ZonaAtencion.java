package mx.gob.imss.mssistrans.ccom.rutas.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SINTRANST_ZONAS_ATENCION")
public class ZonaAtencion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ZONAS_ATENCION", unique = false, nullable = true) 
	private Integer idZona;

	@Column(name = "ID_MODULO_AMBULANCIA", unique = false, nullable = true) 
	private Integer idModulo;
	
	
	@Basic
	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL) 
    @JoinColumn(name = "ID_ZONAS_ATENCION") 
    @JsonManagedReference
    
    private List<ZonaAtencionDetalle> detalle=new ArrayList();
	
	@Column(name = "CVE_MATRICULA")
	private String cveMatricula;
	   
	@Column(name = "FEC_ALTA", unique = false, nullable = true)
	private   LocalDate fecAlta;

	@Column(name = "FEC_ACTUALIZACION", unique = false, nullable = true)
	private LocalDate fecActualizacion;

	@Column(name = "FEC_BAJA", unique = false, nullable = true)
	private LocalDate fecBaja;
	
	@Column(name = "IND_ACTIVO", unique = false, nullable = true)
	private boolean activo;
	  
	@Column(name = "IND_SISTEMA", unique = false, nullable = true)
	private boolean indSistema;
}
