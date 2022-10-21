package mx.gob.imss.mssistrans.ccom.rutas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SINTRANST_VEHICULOS")
public class ReasignacionEccoEntity {
    // todo - usar las clases correctamente, porque se estan repitiendo las entidades con distinto nombre
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

    @Column(name = "ID_ASIGNACION", unique = false, nullable = true)
    private String idAsignacion;

}

