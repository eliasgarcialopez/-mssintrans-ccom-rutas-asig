package mx.gob.imss.mssistrans.rutas.util;

public class Utility {

	public String generateFolio(String servicio, Integer consecutivo, String clavePresupuestal)
			throws Exception {
		// - ID de la ruta (AUTOMATICO que se compone de Siglas tipo de servicios
		// + número consecutivo + los primeros 2 digios de la clave presupuestal de la
		// OOAD
		// ejemplo AC-001-20)
		String siglas = getAbvFromServicio(servicio);
		String cp = clavePresupuestal.substring(0, 2);
		String numeroStr = "" + consecutivo;
		String consecutivoCeros = "";
		String numeroFolio = "";
		String idGenerado = "";
		int longitudConsec = numeroStr.length();

		int longitud = Constants.LONGITUD_FOLIO - siglas.length() - 2;
		if (longitudConsec < longitud) {

			for (int i = 1; i <= (longitud - longitudConsec); i++)
				consecutivoCeros = consecutivoCeros + "0";

			numeroFolio = consecutivoCeros + numeroStr;
		} else if (longitudConsec == longitud) {

			numeroFolio = "" + consecutivo;

		}
		idGenerado = siglas + numeroFolio + cp;
		if (idGenerado.length() > 10)
			throw new Exception("Se excedio la longitud del folio, debe ser de 10>Folio" + idGenerado);

		return idGenerado;
	}

	public static String getAbvFromServicio(String servicio) {
		String abrev = "";
		switch (servicio) {
		case "Acrobacia":
			abrev = "AC";
			break;
		case "Apoyo Administrativo":
			abrev = "AA";
			break;
		case "Apoyo Administrativo U M":
			abrev = "AAUM";
			break;
		case "Estafeta":
			abrev = "ESTA";
			break;
		case "Programa Adec":
			abrev = "PR";
			break;
		case "Servicios Generales":
			abrev = "SG";
			break;
		case "Traslado de funcionarios":
			abrev = "TF";
			break;
		case "Traslado de Personal":
			abrev = "TPR";
			break;
		case "Abastecimientos":
			abrev = "ABS";
			break;
		case "Suministro de Agua":
			abrev = "SA";
			break;
		case "Carga":
			abrev = "C";
			break;
		case "Traslado de Ropa":
			abrev = "TR";
			break;
		default:
			break;
		}
		return abrev;
	}

//	public void main(String[] args) {
//		try {
//			System.out.println(Utility.generateFolio("Servicios Generales", 10, "010901252110"));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
}
