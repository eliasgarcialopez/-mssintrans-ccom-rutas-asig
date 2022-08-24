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
@Table(name = "SINTRANSC_MUNICIPIOS")
@Getter
@Setter
public class Municipio implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MUNICIPIO")
    private Integer idMunicipio;	 
	 
    @Basic
    @Column(name = "NOM_MUNICIPIO")
    private String nomMunicipio;
    
    @JsonBackReference
 	@Basic
 	 @OneToOne(cascade = CascadeType.PERSIST)
 	    @JoinColumn(name="ID_ENTIDAD", nullable=false)
    private Entidad entidad;
    
    @Basic
    @Column(name = "IND_ACTIVO")
    private boolean activo;
    @Basic
    @Column(name = "IND_SISTEMA")
    private boolean indiceSistema;
}
