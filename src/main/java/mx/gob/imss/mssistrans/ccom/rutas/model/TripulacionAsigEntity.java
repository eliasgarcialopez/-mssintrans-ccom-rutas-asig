package mx.gob.imss.mssistrans.ccom.rutas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SINTRANST_TRIPULACION")
public class TripulacionAsigEntity {
    // todo - esta entidad esta repetida, hacer que los repositories apunten a una sola entidad de tripulacion
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CONTROL_RUTA", unique = false, nullable = true)
    private String idControlRuta;
    @Column(name = "NOM_CHOFER", unique = false, nullable = true)
    private String nomTripulante;
    @Column(name = "CVE_MATRICULA_CHOFER", unique = false, nullable = true)
    private String cveMatricula;
    @Column(name = "NUM_FOLIO_TJTA_COMBUSTIBLE", unique = false, nullable = true)
    private String numTarjetaDig;
    @Column(name = "ID_PERSONAL_AMBULANCIA", unique = false, nullable = true)
    private String idPersonalAmbulancia;
}

