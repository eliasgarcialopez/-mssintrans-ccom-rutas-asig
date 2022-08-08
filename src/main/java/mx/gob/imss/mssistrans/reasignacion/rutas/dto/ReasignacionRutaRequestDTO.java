package mx.gob.imss.mssistrans.reasignacion.rutas.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.gob.imss.mssistrans.reasignacion.rutas.model.ChoferesEntity;
import mx.gob.imss.mssistrans.reasignacion.rutas.model.VehiculosEntity;

@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(value = "reasignacion")
public class ReasignacionRutaRequestDTO {
	
	@JsonProperty
	private Integer idRuta;
	
	@JsonProperty
	private Integer idChofer;
	
	@JsonProperty
	private String motivo;
	
	@JsonProperty
	private String siniestro;
	
	@JsonProperty
	private VehiculosEntity idVehiculoSust;
	
	@JsonProperty
	private ChoferesEntity idChoferSust;
	
	@JsonProperty
	private String cveMatricula;
	
	@JsonProperty
	private Date fecAlta;
	
	@JsonProperty
	private Date fecActualizacion;
	
	@JsonProperty
	private Date fecBaja;
	
	@JsonProperty
	private int indActivo;
	
	@JsonProperty
	private int indSistema;
	

}
