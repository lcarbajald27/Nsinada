package pe.gob.oefa.ws.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil {
	
	public static boolean validarNombre(String fileName) {
		return (!VO.isNullOrEmpty(fileName) && fileName.length()>50);
	}
	
	public static String copiarArchivo(String ruta, String fileName, InputStream inputStream) {
	    try {
	    	File directory = new File(ruta);
	    	if(!directory.exists()) { 
	    	    directory.mkdirs(); 
	    	}
	    	String fullFilePath = ruta + fileName;
	    	
	        OutputStream outputStream = new FileOutputStream(new File(fullFilePath));
	        
	        int read = 0;
	        
	        byte[] bytes = new byte[1024];

	        while ((read = inputStream.read(bytes)) != -1) {
	            outputStream.write(bytes, 0, read);
	        }

	        inputStream.close();
	        outputStream.flush();
	        outputStream.close();
	        return fullFilePath;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	public static boolean eliminarArchivo(String fullFilePath) {
		try {
           File file = new File(fullFilePath);
           file.delete();
           return true;
       } catch (Exception e) {
           e.printStackTrace();
           return false;
       }
	}
	
}
