package mx.gob.imss.mssistrans.ccom.rutas.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "SINTRANST_TRIPULACION")
@Getter
@Setter
public class Tripulacion extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TRIPULACION")
    private Integer idTripulacion;

    @Basic
    @Column(name = "FEC_FECHA")
    private LocalDate fecFecha;

    @Basic
    @Column(name = "ID_VEHICULO")
    private Integer idVehiculo;

    @OneToOne
    @JoinColumn(name = "ID_PERSONAL_AMBULANCIA_CHOFER")
    private PersonalAmbulancia personalChofer;

    @OneToOne
    @JoinColumn(name = "ID_PERSONAL_AMBULANCIA_C1")
    private PersonalAmbulancia personalCamillero1;

    @OneToOne
    @JoinColumn(name = "ID_PERSONAL_AMBULANCIA_C2")
    private PersonalAmbulancia personalCamillero2;
}
