package mx.gob.imss.mssistrans.ccom.rutas.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(value = "DatosRegistroRecorridoDTO")
public class DatosRegistroRecorridoDTO {
    @JsonProperty
    private Integer idVehiculo;
    @JsonProperty
    private LocalDate fechaInicioAsigna;
    @JsonProperty
    private LocalTime timInicioAsigna;
    @JsonProperty
    private String desEstatusAsigna;
    @JsonProperty
    private Integer idDestino;
    @JsonProperty
    private String timHoraInicio;
    @JsonProperty
    private String timHoraFin;
    @JsonProperty
    private Integer idOrigen;
    @JsonProperty
    private String timHorarioInicial;
    @JsonProperty
    private String timHorarioFinal;

}
