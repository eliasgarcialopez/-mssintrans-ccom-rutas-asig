package mx.gob.imss.mssintetrans.ccom.rutas.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity 
@Table(name = "SINTRANSC_CODIGO_POSTAL")
@Getter
@Setter
public class CodigoPostal implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CODIGO_POSTAL")
    private Integer idCodigoPostal;	 
	 
    @Basic
    @Column(name = "CVE_COD_POSTAL")
    private String cveCodPostal;
    
    @JsonBackReference
   	@Basic
   	 @OneToOne(cascade = CascadeType.PERSIST)
   	    @JoinColumn(name="ID_MUNICIPIO", nullable=false)
      private Municipio municipio;
      
    
    @Basic
    @Column(name = "IND_ACTIVO")
    private boolean activo;
    @Basic
    @Column(name = "IND_SISTEMA")
    private boolean indiceSistema;
}
