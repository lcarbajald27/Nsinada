package pe.gob.oefa.apps.base.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.text.WordUtils;

public class UtilStringParam {
	

	
	public static String getFechaActual() {
	    Date ahora = new Date();
	    SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
	    return formateador.format(ahora);
	}
	
	private static String convertirMinuscula(String s){
		String res="";
		 if(s==null || s.length()==0){
			 return "";
		 }
		 res = s.toLowerCase();
	
		return res;
		
	}
    public static String capitalize(String s) {
    	String res ="";
    	res = convertirMinuscula(s);
    	if(res!=null && res.length()>0){
    		res = WordUtils.capitalize(res);
    	}
    	
    	return res;
//        if (s.length() == 0) return s;
//        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }
    public static String mayusculaPrimeraLetra(String s){
    	String res ="";
    	res = convertirMinuscula(s);
    	if(res!=null && res.length()>0){
    		res = res.substring(0,1).toUpperCase().concat(res.substring(1,res.length()).toLowerCase()).concat(" ");
    	}
    	return res;
    	
    }
    
    public static String espaciadoTexto(String data){
		
		String[] textoFormateado = data.split(" ");
		String textoCorregido ="";
		for (int i = 0; i < textoFormateado.length; i++) {
			if(!textoFormateado[i].equals("")){
				textoCorregido = textoCorregido + textoFormateado[i]+ " ";
			}
		}
		return textoCorregido;
	}
    
    
    public static String CapitalizeTexto(String s){
    	String texto = 	espaciadoTexto(s);
    	
    	
    	
    	
		return s;
    	
    }

    
    public static void main(String[] args) {
        String FinalStringIs = "";
        String testNames = "sireesh yarlagadda test";
        String[] name = testNames.split("\\s");

        for(String nameIs :name){
            FinalStringIs += getIntiCapString(nameIs) + ",";
        }
        System.out.println("Final Result "+ FinalStringIs);
    }

    public static String getIntiCapString(String param) {
        if(param != null && param.length()>0){          
            char[] charArray = param.toCharArray(); 
            charArray[0] = Character.toUpperCase(charArray[0]); 
            return new String(charArray); 
        }
        else {
            return "";
        }
    }
    
}
