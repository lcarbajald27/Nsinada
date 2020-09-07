package pe.gob.oefa.apps.sinada.presentacion.rest.api.bas;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;








import pe.gob.oefa.apps.base.bean.BaseBean;
import pe.gob.oefa.apps.sinada.presentacion.util.FileUtil;
import pe.gob.oefa.apps.sinada.presentacion.util.ResourceUtil;



public class GenericController {

	
	
	public String copiarArchivo(String fileName, String folder, InputStream inputStream) {
		String rutaBase = ResourceUtil.getKey("file.path.base");
		rutaBase = FileUtil.copiarArchivo(rutaBase+folder, fileName, inputStream);
		return rutaBase;
	}
	
	public boolean eliminarArchivo(String fullFilePath) {
		return FileUtil.eliminarArchivo(fullFilePath);
	}
	
	public InputStream getDefaultImageStream() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest()
				.getServletContext()
				.getResourceAsStream("/assets/images/default/no_image_available.jpg");
	}
	
	public String getContextPath()
	{
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest()
				.getServletContext()
				.getContextPath();
	}
	/*
	public String getRealPath()
	{
	//obtiene direccion fisica del servidor
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest()
				.getServletContext()
				.getRealPath("/");
	}*/
	
	public String getURLBase(HttpServletRequest request) throws MalformedURLException {

	    URL requestURL = new URL(request.getRequestURL().toString());
	    String port = requestURL.getPort() == -1 ? "" : ":" + requestURL.getPort();
	    return requestURL.getProtocol() + "://" + requestURL.getHost() + port;
	}
	
	public String getURLApp(HttpServletRequest request) throws MalformedURLException {
	    URL requestURL = new URL(request.getRequestURL().toString());
	    String port = requestURL.getPort() == -1 ? "" : ":" + requestURL.getPort();
	    return requestURL.getProtocol() + "://" + requestURL.getHost() + port +this.getContextPath();
	}
	
	public String copiarArchivoDenuncia(String fileName, String folder, InputStream inputStream) {
		
		String rutaBase = ResourceUtil.getKey("file.path.denuncia.archivos.windows.base");
		String serve = ResourceUtil.getKey("file.server");
		
		if(serve!=null && serve.trim().equals("2")){
			rutaBase = ResourceUtil.getKey("file.path.denuncia.archivos.linux.base");
		}
		rutaBase = rutaBase.replace("{separador}", File.separator);
		rutaBase = FileUtil.copiarArchivo(rutaBase+folder, fileName, inputStream);
		return rutaBase;
	}
	/*
	public String getURLWebApp(HttpServletRequest request) throws MalformedURLException {

	    URL requestURL = new URL(request.getRequestURL().toString());
	    String port = requestURL.getPort() == -1 ? "" : ":" + requestURL.getPort();
	    return requestURL.getProtocol() + "://" + requestURL.getHost() + port +this.getContextPath();
	}*/
	
	protected void numerarItem(List<?> lst){
		int i=1;
		for (Iterator iterator = lst.iterator(); iterator.hasNext();) {
			BaseBean object = (BaseBean) iterator.next();
			object.setNumeroItem(i++);
		}
	
	}
}
