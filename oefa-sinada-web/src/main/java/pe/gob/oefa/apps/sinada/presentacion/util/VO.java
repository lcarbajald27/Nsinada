package pe.gob.oefa.apps.sinada.presentacion.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.URL;

public class VO {
	
	/********************************************************************************************
	 * Empty Verification Methods
	 ********************************************************************************************/

	public static boolean isNullOrEmpty(Object obj) {
		return isNull(obj) || isEmpty(obj);
	}

	public static boolean isNull(Object obj) {
		return obj == null;
	}
	
	public static boolean isEmpty(Object obj) {
		return (obj.toString().trim().length() == 0);
	}
	
	@SuppressWarnings("rawtypes")
	public static boolean isEmptyList(List obj) {
		return (obj == null) || (obj.size() == 0);
	}	
	
	public static boolean isObjectsEquals(Object obj,Object object) {
		return obj.equals(object);
	}
	public static boolean isEquals(String obj,String object) {
		return obj.equals(object);
	}
	
	public static boolean isValidId(Object id) {
		if (id instanceof Long) {
			return !isNull(id) && (Long)id>0;	
		}
		if (id instanceof Integer) {
			return !isNull(id) && (Integer)id>0;	
		}
		if (id instanceof String) {
			return !isNull(id) && (((String)id).trim()!="0" && ((String)id).trim()!="");
		}
		return false;
	}
	
	public static String dateToString(Date date) {

		try {

			if (date != null) {

				String result = "";

				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

				result = sdf.format(date);

				return result;

			} else {
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public static String getBasicPath() {
		String rs = "";
		try {
			ServletRequestAttributes sra = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
			HttpServletRequest request = sra.getRequest();
			URL reconstructedURL = new URL(request.getScheme(),
	                request.getServerName(),
	                request.getServerPort(),
	                //"");
					request.getContextPath());
			
			rs = reconstructedURL.toString();
		} catch (Exception e) {
			e.getMessage();
			rs = null;
		}
		return rs;
	}
	
	public static String getExtensionesValidas(int tipoArchivo){
		String valores ="";
		switch (tipoArchivo) {
		case 1:
			valores = ResourceUtil.getKey("extension_archivo_fotografia");
			break;
		case 2:
			valores = ResourceUtil.getKey("extension_archivo_audio");
			break;
		case 3:
			valores = ResourceUtil.getKey("extension_archivo_video");
			break;
		case 4:
			valores = ResourceUtil.getKey("extension_archivo_documento");
			break;
		case 5:
			valores = ResourceUtil.getKey("extension_archivo_otros");
			break;
		}
		
		return valores;
	}
	
	public static String getMimeTypeValidos(int tipoArchivo){
		String valores ="";
		switch (tipoArchivo) {
		case 1:
			valores = ResourceUtil.getKey("tipo_archivo_fotografia");
			break;
		case 2:
			valores = ResourceUtil.getKey("tipo_archivo_audio");
			break;
		case 3:
			valores = ResourceUtil.getKey("tipo_archivo_video");
			break;
		case 4:
			valores = ResourceUtil.getKey("tipo_archivo_documento");
			break;
		case 5:
			valores = ResourceUtil.getKey("tipo_archivo_otros");
			break;
		}
		
		return valores;
	}
}
