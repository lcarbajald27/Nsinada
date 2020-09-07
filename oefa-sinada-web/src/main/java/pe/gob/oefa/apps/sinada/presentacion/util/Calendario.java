package pe.gob.oefa.apps.sinada.presentacion.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import pe.gob.oefa.apps.sinada.bean.maestros.Maestro;
import pe.gob.oefa.apps.sinada.servicio.inf.maestros.MaestroService;

public class Calendario {
	
	
	

	 public static Date sumarRestarDiasFecha(Date fecha, int dias){
		 	
		       Calendar calendar = Calendar.getInstance();
		       calendar.setTime(fecha); // Configuramos la fecha que se recibe
		       calendar.add(Calendar.DAY_OF_YEAR, dias);  // numero de días a añadir, o restar en caso de días<0
		       return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos
	 	
		  }
	 
	 
	public static Date calcularDiasHabiles(int dias,List<Maestro> lstDiasFeriados) {
		
		Date date = new Date();
		int i = 0;
		while (i<dias) {
			
			date = sumarRestarDiasFecha(date,1);	
			SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
	    	String date1 = format1.format(date);   
	    	
	    	Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			int num_dia = calendar.get(Calendar.DAY_OF_WEEK);
	    	int mes = calendar.get(Calendar.MONTH);
	    	int dia = calendar.get(Calendar.DAY_OF_MONTH);
	    	
	    	if(num_dia != 1 && num_dia != 7){
	    		/***************************/
	    		int x = 0;
	    	for (Maestro diasFeriado : lstDiasFeriados) {
				
	    		if(diasFeriado.getValorGeneral01().equals(String.valueOf(dia))  && diasFeriado.getValorGeneral02().equals(String.valueOf(mes))){
	    			 x = x+1;
	    		}
			}
	    		
	    	
	    		/*********i*******************/
	    	if(x==0){
	    		i=i+1;
	    		System.out.println(i +"Laborable  " + date1);
	    	}else{
	    		System.out.println( "Feriado  " + date1);
	    		
	    	}
	    		
	    		
	    	}else{
	    		System.out.println("No Laborable  " + date1);
	    	}
	    
	    	
			
		}
		return date;
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(date);
//		int num_dia = calendar.get(Calendar.DAY_OF_WEEK);
//		if (num_dia == 1 || num_dia == 7) {
//			return true;
//		} else {
//			return false;
//		}
	}
	
	
	public static boolean esFinDeSemana(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int num_dia = calendar.get(Calendar.DAY_OF_WEEK);
		if (num_dia == 1 || num_dia == 7) {
			return true;
		} else {
			return false;
		}
	}
	
	public int getDiasHabiles(Calendar fechaInicial, Calendar fechaFinal) {
		  int diffDays= 0;
		  //mientras la fecha inicial sea menor o igual que la fecha final se cuentan los dias
		  while (fechaInicial.before(fechaFinal) || fechaInicial.equals(fechaFinal)) {

		  //si el dia de la semana de la fecha minima es diferente de sabado o domingo
		  if (fechaInicial.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY || fechaInicial.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
		   //se aumentan los días de diferencia entre min y max
		   diffDays++;
		   }
		  //se suma 1 dia para hacer la validación del siguiente dia.
		  fechaInicial.add(Calendar.DATE, 1);
		  }
		return diffDays;
		}
	
	
    public static void main(String[] args) {
//    	Date date = calcularDiasHabiles(30);
    	
//    	SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
//    	String date1 = format1.format(date);   
    	
//    	System.out.println("Fecha Final "+date1);
     
    }
    
	
	

}
