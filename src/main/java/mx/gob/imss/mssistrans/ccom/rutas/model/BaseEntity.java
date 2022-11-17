package mx.gob.imss.mssistrans.ccom.rutas.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@Setter
@Getter
@MappedSuperclass
public class BaseEntity {
    @Column(name = "CVE_MATRICULA")
    private String matricula;
    @Column(name = "FEC_ALTA")
    private LocalDate fechaAlta;
    @Column(name = "FEC_ACTUALIZACION")
    private LocalDate fechaActualizacion;
    @Column(name = "FEC_BAJA")
    private LocalDate fechaBaja;
    @Column(name = "IND_ACTIVO")
    private Boolean activo;
    @Column(name = "IND_ACTIVO")
    private Boolean sistema;
}
