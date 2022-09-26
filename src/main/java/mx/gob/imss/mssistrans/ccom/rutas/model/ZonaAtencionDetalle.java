package mx.gob.imss.mssistrans.ccom.rutas.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.io.Serializable;

@Entity 
@Table(name = "SINTRANST_ZONAS_DETALLE")
@Getter
@Setter
public class ZonaAtencionDetalle implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ZONAS_DETALLE")
    private Integer idZonaAtencionDetalle;
	
	@JsonBackReference
	@Basic
	 @ManyToOne(cascade = CascadeType.PERSIST)
	    @JoinColumn(name="ID_ZONAS_ATENCION", nullable=false)
    private ZonaAtencion zona;
    

    @Basic
    @Column(name = "ID_OOAD")
    private Integer idOOAD;
    
    @Basic
    @Column(name = "ID_UNIDAD")
    private Integer idUnidad;
    
    @Basic
    @OneToOne
	@JoinColumn( name="ID_VEHICULO", unique = false, nullable = true)
    private Vehiculos idVehiculo;
    @Basic
	@Column(name = "IND_ACTIVO", unique = false, nullable = true)
	private boolean indActivo;
    @Basic 
	@Column(name = "IND_SISTEMA", unique = false, nullable = true)
	private boolean indSistema;
}
