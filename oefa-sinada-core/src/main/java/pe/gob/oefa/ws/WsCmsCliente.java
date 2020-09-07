package pe.gob.oefa.ws;

import java.net.ConnectException;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;

import pe.gob.oefa.ws.util.ResourceUtil;


public final class WsCmsCliente {

	public static final String WS_ALFRESCO;
	public static final String KEY_ALFRESCO;
	public static final String SPACE_INNER_SISUD_ALFRESCO;
	public static final String SEPARADOR;
	
	private static String logBase = "oefa-sinada-core: WsCmsCliente";
	
	private final static Logger logger = Logger.getLogger("pe.gob.oefa.ws.WsCmsCliente");
	
	
	
		
	static {
		
		try {
			
			System.out.println("ws_alfresco --> "+ResourceUtil.getKey("ws_alfresco"));
			System.out.println("key_alfresco --> "+ResourceUtil.getKey("key_alfresco"));
			
			WS_ALFRESCO = ResourceUtil.getKey("ws_alfresco");
			KEY_ALFRESCO = ResourceUtil.getKey("key_alfresco");
			SPACE_INNER_SISUD_ALFRESCO = ResourceUtil.getKey("space_inner_sisud_alfresco");
			SEPARADOR = ResourceUtil.getKey("file.path.separador");
			
		} catch (Exception e) {
			logger.error(logBase+ " : WsCmsCliente" + e.getMessage());
			throw new RuntimeException("Error: " + e);
		}	
	}

	public static pe.gob.oefa.ws.ContenidoECMBean leerDocumento(String uuid) throws RemoteException, ServiceException{
		//log.info("leerDocumento");
		Ws_cmsProxy proxy2 = new Ws_cmsProxy();
		proxy2.setEndpoint(WS_ALFRESCO);
		return proxy2.leerDocumento(KEY_ALFRESCO,uuid);
	}

	public static pe.gob.oefa.ws.RptaWsBean registrarDocumento(String nomFile, String ruta, byte[] file) /*throws ConnectException throws RemoteException*/{
		/*log.info("registrarDocumento");
		log.info("key "+key);
		log.info("nomFile "+nomFile);
		log.info("ruta "+ruta);*/
		try {
			//log.info("accediendo registrarDocumento");
			Ws_cmsProxy proxy2 = new Ws_cmsProxy();
			proxy2.setEndpoint(WS_ALFRESCO);
			
			if (	SPACE_INNER_SISUD_ALFRESCO != null 
				&& !SPACE_INNER_SISUD_ALFRESCO.equalsIgnoreCase("")){
				
				if (	ruta != null 
					&& !ruta.equalsIgnoreCase("")) {
					
					ruta = SPACE_INNER_SISUD_ALFRESCO+SEPARADOR+ruta;
				
				} else {
					ruta = SPACE_INNER_SISUD_ALFRESCO;
				}
			}
			System.out.println("KEY_ALFRESCO"+ KEY_ALFRESCO);
			System.out.println("nomFile"+ nomFile);
			System.out.println("ruta"+ ruta);
			System.out.println("file"+ file);
			return proxy2.registrarDocumento(KEY_ALFRESCO, nomFile, ruta, file);
		
		} catch (RemoteException e) {
			
			logger.error(logBase+ " : registrarDocumento (El servicio es inaccesible.)" + e.getMessage());
			//log.info("NO accede registrarDocumento");
			e.printStackTrace();
		} catch (Exception e) {
		
			logger.error(logBase+ " : registrarDocumento" + e.getMessage());
			//log.info("NO accede registrarDocumento");
			e.printStackTrace();
		}
		
		return null;
	}

	public static pe.gob.oefa.ws.RptaWsBean eliminarDocumento(String uuid) throws RemoteException{
		//log.info("eliminarDocumento");
		Ws_cmsProxy proxy2 = new Ws_cmsProxy();
		proxy2.setEndpoint(WS_ALFRESCO);
		return proxy2.eliminarDocumento(KEY_ALFRESCO, uuid);
	}
	
	public static pe.gob.oefa.ws.RptaBcBean busquedaContenido(java.lang.String rutaSubCarpeta, java.lang.String parametro1, java.lang.String parametro2, java.lang.String parametro3, java.lang.String parametro4) throws java.rmi.RemoteException{
		Ws_cmsProxy proxy2 = new Ws_cmsProxy();
		proxy2.setEndpoint(WS_ALFRESCO);
		return proxy2.busquedaContenido(KEY_ALFRESCO, rutaSubCarpeta, parametro1, parametro2, parametro3, parametro4);
	}

}