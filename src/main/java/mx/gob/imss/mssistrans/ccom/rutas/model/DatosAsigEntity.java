package mx.gob.imss.mssistrans.ccom.rutas.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SINTRANST_ASIGNACIONES")
public class DatosAsigEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_VEHICULO", unique = false, nullable = true)
    private String idVehiculo;
    @Column(name = "CVE_ECCO", unique = false, nullable = true)
    private String cveEcco;
    @Column(name = "NUM_PLACAS", unique = false, nullable = true)
    private String numPlacas;
    @Column(name = "ID_RUTA", unique = false, nullable = true)
    private String idRuta;
    @Column(name = "ID_SOLICITUD", unique = false, nullable = true)
    private String idSolicitud;
    @Column(name = "DES_ESTATUS_ASIGNA", unique = false, nullable = true)
    private String desEstatus;

    @Column(name = "ID_ASIGNACION", unique = false, nullable = true)
    private String idAsignacion;
}

