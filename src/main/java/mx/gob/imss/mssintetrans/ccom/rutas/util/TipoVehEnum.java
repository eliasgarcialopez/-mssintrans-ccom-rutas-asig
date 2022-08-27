package mx.gob.imss.mssintetrans.ccom.rutas.util;

public enum TipoVehEnum {
	
	Automóvil(1),
	SUV(2),
	Microbus(3),
	Autobus(4),
	Van_o_Vanette(5),
	Pick_up(6),
	Camión_mediano(7),
	Tortón_y_rabón(8),
	Tractocamión(9),
	Motocicletas(10),
	Otros(11);
	
	public int getTipoVeh() {
		return tipoVeh;
	}

	private final int tipoVeh;

	private TipoVehEnum(int tipoVeh) {
		this.tipoVeh = tipoVeh;
	}
	
}
