package mx.gob.imss.mssistrans.ccom.rutas.util;

public enum TipoServicioEnum {

	Apoyo_administrativo(1),
	Apoyo_administrativo_UM(2),
	Estafeta(3),
	Abastecimiento(4),
	Suministro_de_agua(5),
	Traslado_de_funcionarios(6),
	Programa_ADEC(7),
	Traslado_de_Personal(8),
	Traslado_de_paciente(9),
	Traslado_de_pacientes_terapia_intensiva(10),
	Traslado_de_pacientes_urgencias(11);
	
	public int getTipoServicio() {
		return tipoServicio;
	}

	private final int tipoServicio;

	private TipoServicioEnum(int tipoServicio) {
		this.tipoServicio = tipoServicio;
	}
	
}
