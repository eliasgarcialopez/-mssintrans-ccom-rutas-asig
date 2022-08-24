package mx.gob.imss.mssintetrans.ccom.rutas.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity 
@Table(name = "SINTRANST_CONTROL_RUTAS")
@Getter
@Setter
public class ControlRutas implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CONTROL_RUTA")
    private Integer idControlRuta;
		

    @Basic
    @OneToOne
	@JoinColumn( name="ID_SOLICITUD", unique = false, nullable = true)
    private SolicitudTraslado idSolcitud;
	 

    @Basic
    @OneToOne
	@JoinColumn( name="ID_VEHICULO", unique = false, nullable = true)
    private Vehiculos idVehiculo;
    
   
    
    @Basic
    @Column(name = "IND_ESTATUS_ASIGNA")
    private Integer indEstatusAsigna;
    


    
    @Basic
    @OneToOne
	@JoinColumn( name="ID_TRIPULACION", unique = false, nullable = true)
    private Tripulacion tripulacion;
    
    
    @Basic
    @Column(name = "NUM_FOLIO_TJTA_COMBUSTIBLE")
    private String numFolioTarjetaCombustible;
    
    

    @Basic
    @Column(name = "FEC_INICIO_ASIGNA")
    private LocalDate fechaInicioAsigna;
    
    @Basic
    @Column(name = "TIM_INICIO_ASIGNA")
    private LocalTime timInicioAsigna;
    
    
    
    
    @Basic
    @OneToOne
	@JoinColumn( name="ID_RUTA", unique = false, nullable = true)
    
    private Rutas ruta;
    
    
    @Basic
    @OneToOne
	@JoinColumn( name="ID_MODULO_AMBULANCIA", unique = false, nullable = true)
    
    private ModuloAmbulancia modulo;
    
    @Basic
    @Column(name = "CVE_MATRICULA")
    private String cveMatricula;
        
	
    @Basic
    @Column(name = "FEC_ALTA")
    private LocalDate fechaAlta;
    @Basic
    @Column(name = "FEC_ACTUALIZACION")
    private LocalDate fechaActualizacion;
    @Basic
    @Column(name = "FEC_BAJA")
    private LocalDate fechaBaja;
    @Basic
    @Column(name = "IND_ACTIVO")
    private boolean activo;
    @Basic
    @Column(name = "IND_SISTEMA")
    private boolean indiceSistema;
}
