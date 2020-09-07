package pe.gob.oefa.ws.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import pe.gob.oefa.ws.ContenidoECMBean;
import pe.gob.oefa.ws.RptaWsBean;
import pe.gob.oefa.ws.WsCmsCliente;

public class AlfrescoUtil {
	
	private static String logBase = "oefa-sinada-core: WsCmsCliente - core";
	
	private final static Logger logger = Logger.getLogger("pe.gob.oefa.ws.util.AlfrescoUtil");

	public static String grabarArchivoAlfresco(MultipartFile file, String nombreCarpeta) throws Exception {
		
		RptaWsBean rsp = null;
		
		String rpta 		= "";
		String nomArchTemp 	= "";
		String extension 	= "";
		
		int extIndex = file.getOriginalFilename().lastIndexOf(".");	
		
		nomArchTemp = file.getOriginalFilename().substring(0, extIndex);
		extension = file.getOriginalFilename().substring(extIndex);
		
//		nombreCarpeta = nombreCarpeta + "/";
		
//		String anio = getAnio();
		
//		if (file.getFileName().endsWith(".pdf")) {
//			nomArchTemp = file.getFileName().replace(".pdf", "");
//			extension = ".pdf";
//		}

//		if (file.getFileName().endsWith(".doc")) {
//			nomArchTemp = file.getFileName().replace(".doc", "");
//			extension = ".doc";
//		}
//
//		if (file.getFileName().endsWith(".docx")) {
//			nomArchTemp = file.getFileName().replace(".docx", "");
//			extension = ".docx";
//		}
		
		/**
		La descripción de los siguientes campos: 
		- Nombre del archivo (se le concatena la fecha hasta los segundos, ya que si existe un archivo con el mismo nombre el alfresco genera error).
		- Carpeta donde graba, por ejemplo NSINADA/DENUNCIA-001/
		- El archivo en sí.
		**/
		rsp = WsCmsCliente.registrarDocumento(nomArchTemp.concat(AlfrescoUtil.getUniqueIdByFecha()).concat(extension), 
											  nombreCarpeta, 
											  file.getBytes());
		
		if (	rsp != null 
			&& 	rsp.getCodMsg().trim().equals("OK")) {
			
			rpta = rsp.getUuid();
		}
		
		if(rpta.equals("error") || rpta=="error"){
			rpta = null;
		}
		
		
//		log.debug("ID que devuelve: " + rsp.getUuid());
//		log.debug("Mensaje que devuelve: " + rsp.getCadMsg());
//		log.debug("Cod mensaje que devuelve: " + rsp.getCodMsg());
		
		return rpta;
	}
	
	
	public static String grabarArchivoAlfresco(File file, String nombreCarpeta) throws Exception {
		
		RptaWsBean rsp = null;
		
		String rpta 		= "";
		String nomArchTemp 	= "";
		String extension 	= "";
		
		int extIndex = file.getName().lastIndexOf(".");	
		
		nomArchTemp = file.getName().substring(0, extIndex);
		extension = file.getName().substring(extIndex);
		
//		nombreCarpeta = nombreCarpeta + "/";
		
//		String anio = getAnio();
		
//		if (file.getFileName().endsWith(".pdf")) {
//			nomArchTemp = file.getFileName().replace(".pdf", "");
//			extension = ".pdf";
//		}

//		if (file.getFileName().endsWith(".doc")) {
//			nomArchTemp = file.getFileName().replace(".doc", "");
//			extension = ".doc";
//		}
//
//		if (file.getFileName().endsWith(".docx")) {
//			nomArchTemp = file.getFileName().replace(".docx", "");
//			extension = ".docx";
//		}
		
		/**
		La descripción de los siguientes campos: 
		- Nombre del archivo (se le concatena la fecha hasta los segundos, ya que si existe un archivo con el mismo nombre el alfresco genera error).
		- Carpeta donde graba, por ejemplo NSINADA/DENUNCIA-001/
		- El archivo en sí.
		**/
		rsp = WsCmsCliente.registrarDocumento(nomArchTemp.concat(AlfrescoUtil.getUniqueIdByFecha()).concat(extension), 
											  nombreCarpeta, 
											  getBytesFromFile(file));
		
		if (	rsp != null 
			&& 	rsp.getCodMsg().trim().equals("OK")) {
			
			rpta = rsp.getUuid();
		}
		
		if(rpta.equals("error") || rpta=="error"){
			rpta = null;
		}
		
//		log.debug("ID que devuelve: " + rsp.getUuid());
//		log.debug("Mensaje que devuelve: " + rsp.getCadMsg());
//		log.debug("Cod mensaje que devuelve: " + rsp.getCodMsg());
		
		return rpta;
	}
	
	
   	@SuppressWarnings("unused")
	private static byte[] getBytesFromFile(File file) throws IOException{
		InputStream is = new FileInputStream(file);
		// se obtiene el tamaño del archivo
		long length = file.length();
		// se vwerifica si el archivo es muy grande
		if (length > Integer.MAX_VALUE) {
			// directivas en caso el archivo sea muy pesado
		}
		// se crea la data en byte
		byte[] bytes = new byte[(int)length];
		// lectura de byte's
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length
			   && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
			offset += numRead;
		}

		if (offset < bytes.length) {
			logger.error(logBase+ " : getBytesFromFile" + file.getName());
			throw new IOException("Could not completely read file "+file.getName());
		}

		// se cierra el inputStream y se retorna los byte del archivo
		is.close();
		return bytes;
	}

	public static byte[] getArchivo(String uiid) {
		
		byte[] rpta = null;

		try {
			/**
			Donde el id es el UUID que se obtuvo.
			**/
			ContenidoECMBean r = WsCmsCliente.leerDocumento(uiid);
			rpta = r.getContenidoFile();

		} catch (Exception e) {
			logger.error(logBase+ " : getArchivo" + e.getMessage());
//			log.error(e.getMessage(), e.getCause());
			e.printStackTrace();
		}

		return rpta;
	}
	
	private static String getUniqueIdByFecha() {
		
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("ddMMyyyyHHmmss");
		String datetime = ft.format(dNow);
		
//		System.out.println(datetime);
		
		return datetime;
	}
	
	public static String getAnio() {
		
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy");
		String datetime = ft.format(dNow);
		
		return datetime;
	}
	
}
