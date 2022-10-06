package mx.gob.imss.mssistrans.ccom.rutas.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "SINTRANST_RUTAS_DESTINOS")
@Getter
@Setter
public class RutasDestinos implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 389043993860977285L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DESTINO")
    private Integer idDestino;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ID_RUTA", nullable = false)
    private Rutas ruta;
    @Basic
    @Column(name = "ID_UNIDAD_DESTINO")
    private Integer idUnidadDestino;

    @Basic
    @Column(name = "TIM_HORA_INICIO")
    private String timHoraInicio;

    @Basic
    @Column(name = "TIM_HORA_FIN")
    private String timHoraFin;

    @Basic
    @Column(name = "IND_ACTIVO")
    private boolean activo;
    @Basic
    @Column(name = "IND_SISTEMA")
    private boolean indiceSistema;
}
