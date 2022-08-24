package mx.gob.imss.mssintetrans.ccom.rutas.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;


public class Utility {
	
	
	//06.01 a 14:00, vespertino horario del turno 14.01 a 19:00,nocturno o especial horario del turno 19.01 a 06:00 
	//pendiente solucionar el tema de 
	
	
	public static String getTurnoByHr(String timeIn) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
	   Date  d = dateFormat.parse(timeIn);
		
		
		Date matIni=dateFormat.parse("06:01");
		Date matFin=dateFormat.parse("14:00");
		
		Date vesIni=dateFormat.parse("14:01");
		Date vesFin=dateFormat.parse("19:00");
		
		Date nocIni=dateFormat.parse("19:01");
		Date nocFin=dateFormat.parse("24:00");
		
		Date nocIni2=dateFormat.parse("00:00");
		Date nocFin2=dateFormat.parse("06:00");
		
		if(d.after(matIni) &&  d.before(matFin)) return "Matutino";
		if(d.after(vesIni) &&  d.before(vesFin)) return "Vespertino";
		if(d.after(nocIni2) &&  d.before(nocFin2)) return "Nocturno o Especial";
		if(d.after(nocIni) &&  d.before(nocFin)) return "Nocturno o Especial";
		return "";
		
	}
	
	public static String getIdHorarioByTurno(Integer horario) {
		if(horario==1) 	return "Matutino";
		if(horario==2) 	return "Vespertino";
		if(horario==3) 	return "Nocturno o Especial";
		return "";
	}

	
public static ArrayList<String> getHorarioStringByTurno(Integer idHorario) {
		
		ArrayList<String> horarios=new ArrayList<String>();
		String  horario=getIdHorarioByTurno(idHorario);
		System.out.println(""+ horario);
		if(horario.equals("Matutino")) {
		String matIni="06:01";
		String matFin="14:00";
		horarios.add(matIni);
		horarios.add(matFin);
		return horarios;
		}
		if(horario.equals("Vespertino")) {
		String vesIni="14:01";
		String vesFin="19:00";
		horarios.add(vesIni);
		horarios.add(vesFin);
		return horarios;
		}
		if(horario.equals("Nocturno o Especial")) {
	// 19 and  24
	// 0  and  06		
		String nocIni1="19:01";
		String nocFin1="24:00";
	
		String nocIni2="00:00";
		String nocFin2="06:00";
		horarios.add(nocIni1);
		horarios.add(nocFin1);
		horarios.add(nocIni2);
		horarios.add(nocFin2);
		return horarios;
		}
		
		return horarios;
	}
	public static String generateFolio(String ooadNombre, Integer consecutivo)
			throws Exception {

//- ID de la ruta (alfanumérico, 15 caracteres, obligatorio)  número consecutivo + siglas de la OOAD el sufijo AMB 

		String siglas = getAbvFromOOAD(ooadNombre);
	    String sufijo= "AMB";
		String numeroStr = "" + consecutivo;
		String consecutivoCeros = "";
		String numeroFolio = "";
		String idGenerado = "";
		int longitudConsec = numeroStr.length();

		int longitud = 15 - siglas.length()-sufijo.length();
		if (longitudConsec < longitud) {

			for (int i = 1; i <= (longitud - longitudConsec); i++)
				consecutivoCeros = consecutivoCeros + "0";

			numeroFolio = consecutivoCeros + numeroStr;
		} else if (longitudConsec == longitud) {

			numeroFolio = "" + consecutivo;

		}
		idGenerado = siglas + numeroFolio + sufijo;
		if (idGenerado.length() > 15)
			throw new Exception("Se excedio la longitud del folio, debe ser de 15>Folio" + idGenerado);

		return idGenerado;
	}
public static void main(String[] args) {
	
	try {
		System.out.println(""+Utility.generateFolio("",1));
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
	public static String getAbvFromOOAD(String nombreOOAD) {
		String abrev = "";
		//pendiente validar las siglas de la ooad
		switch (nombreOOAD) {
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
			abrev="";
			break;
			
		}
		return abrev;
	}

	
}
