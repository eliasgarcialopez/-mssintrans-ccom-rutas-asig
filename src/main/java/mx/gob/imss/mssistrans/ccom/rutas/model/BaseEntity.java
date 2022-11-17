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
    @Column(name = "CVE_MATRICULA", insertable = false, updatable = false)
    private String matricula="1";
    @Column(name = "FEC_ALTA")
    private LocalDate fechaAlta;
    @Column(name = "FEC_ACTUALIZACION")
    private LocalDate fechaActualizacion;
    @Column(name = "FEC_BAJA")
    private LocalDate fechaBaja;
    @Column(name = "IND_ACTIVO", insertable = false, updatable = false)
    private Boolean activo;
    @Column(name = "IND_ACTIVO", insertable = false, updatable = false)
    private Boolean sistema;
}
