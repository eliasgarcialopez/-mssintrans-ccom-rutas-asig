package mx.gob.imss.mssistrans.ccom.rutas.model;

import java.time.LocalDate;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
* @author Itzi B. Enriquez R.
* @puesto DEV
* @date 16 ago. 2022
*/
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SINTRANST_CAMILLERO")
public class CamillerosEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CAMILLERO", unique = false, nullable = true)
    private Long idCamillero;
    
    @Column(name = "NOM_CAMILLERO", unique = false, nullable = true)
    private String nomCamillero;
    
    @Basic
    @Column(name = "CVE_MATRICULA", unique = false, nullable = true)
    private String cveMatricula;
   
    @Basic
    @Column(name = "CVE_MATRICULA_AUDITORIA", unique = false, nullable = true)
    private String cveMatriculaAlt;
    
    @Basic
    @Column(name = "FEC_ALTA", unique = false, nullable = true)
    private LocalDate fechaAlta;
        
    @Basic
    @Column(name = "FEC_ACTUALIZACION", unique = false, nullable = true)
    private LocalDate fechaActualizacion;
    
    @Basic
    @Column(name = "FEC_BAJA", unique = false, nullable = true)
    private LocalDate fechaBaja;
    
    @Basic
    @Column(name = "IND_ACTIVO", unique = false, nullable = true)
    private boolean intActivo;
    
    @Basic
    @Column(name = "IND_SISTEMA", unique = false, nullable = true)
    private boolean indSistema;
    
}
