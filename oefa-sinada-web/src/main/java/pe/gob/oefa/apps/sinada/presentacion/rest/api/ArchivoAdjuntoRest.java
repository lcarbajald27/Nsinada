package pe.gob.oefa.apps.sinada.presentacion.rest.api;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pe.gob.oefa.apps.base.presentacion.response.RespuestaHttp;
import pe.gob.oefa.apps.sinada.bean.proceso.ArchivoAtencion;
import pe.gob.oefa.apps.sinada.bean.proceso.ArchivoCaso;
import pe.gob.oefa.apps.sinada.bean.proceso.ArchivoDenuncia;
import pe.gob.oefa.apps.sinada.presentacion.util.AlfrescoUtil;
import pe.gob.oefa.apps.sinada.presentacion.util.ResourceUtil;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.ArchivoAtencionService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.ArchivoCasoService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.ArchivoDenunciaService;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value="/rest/api/archivo-adjunto")
public class ArchivoAdjuntoRest
{
	
	@Autowired
	private ArchivoAtencionService archivoAtencionService;
	
	@Autowired 
	private ArchivoCasoService archivoCasoService;
	
	@Autowired
	private ArchivoDenunciaService archivoDenunciaService;
	
	private String rutaBase =  ResourceUtil.getKey("file.path.base"); 
	
private String logBase = "oefa-sinada-web: ArchivoAdjuntoRest";
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	
	/*********************************** listar Tabla Archivo Atencion  *******************************************/
	// Tabla Atencion Denuncia - 1
	// Tabla Evaluacion Rechazo - 2
	//Tabla Evaluacion Informe -3
	// Tabla Informe Accion -4
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/listar-archivo-atencion", method=RequestMethod.GET)
	public RespuestaHttp listarArchivoAtencion(@RequestParam(value="prmCriterio",required=false)String prmCriterio)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try
		{
			ArchivoAtencion prmData = new ObjectMapper().readValue(prmCriterio, ArchivoAtencion.class);
			List<ArchivoAtencion> data = (List<ArchivoAtencion>) archivoAtencionService.listar(prmData);
			_RespuestaHttp.setValido(true);
			_RespuestaHttp.setData(data);
		}
		catch (Exception e)
		{
			logger.error(this.logBase+ " : listarArchivoAtencion" + e.getMessage());
			e.printStackTrace();
			_RespuestaHttp.setValido(false);
			_RespuestaHttp.setMensaje("Hubo un error al procesar la información");
		}
		return _RespuestaHttp;
	}

	
/***************************************************************************************************/	
	
	/**************************************** Archivo Denuncia *******************************************/
	// Buscar por id Denuncia
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/listar-archivo-denuncia", method=RequestMethod.GET)
	public RespuestaHttp listarArchivoDenuncia(@RequestParam(value="prmCriterio",required=false)String prmCriterio)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try
		{
			ArchivoDenuncia prmData = new ObjectMapper().readValue(prmCriterio, ArchivoDenuncia.class);
			List<ArchivoDenuncia> data = (List<ArchivoDenuncia>) archivoDenunciaService.listar(prmData);
			_RespuestaHttp.setValido(true);
			_RespuestaHttp.setData(data);
		}
		catch (Exception e)
		{
			logger.error(this.logBase+ " : listarArchivoDenuncia" + e.getMessage());
			e.printStackTrace();
			_RespuestaHttp.setValido(false);
			_RespuestaHttp.setMensaje("Hubo un error al procesar la información");
		}
		return _RespuestaHttp;
	}
	
/********************* Fin Archivo Denuncia ******************/	
	
	
	/**************************************** Archivo Caso *******************************************/
	// Buscar por id Denuncia
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/listar-archivo-Caso", method=RequestMethod.GET)
	public RespuestaHttp buscar(@RequestParam(value="prmCriterio",required=false)String prmCriterio)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try
		{
			ArchivoCaso prmData = new ObjectMapper().readValue(prmCriterio, ArchivoCaso.class);
			List<ArchivoCaso> data = (List<ArchivoCaso>) archivoCasoService.listar(prmData);
			_RespuestaHttp.setValido(true);
			_RespuestaHttp.setData(data);
		}
		catch (Exception e)
		{
			logger.error(this.logBase+ " : buscar" + e.getMessage());
			e.printStackTrace();
			_RespuestaHttp.setValido(false);
			_RespuestaHttp.setMensaje("Hubo un error al procesar la información");
		}
		return _RespuestaHttp;
	}
	
/********************* Fin Archivo Caso ******************/	
	
	
	
   	@RequestMapping(value="/descargar-archivo-atencion/{id}", method = RequestMethod.GET)
   	public void descargar(@PathVariable("id") long id, HttpServletResponse response) {
   		try {

   			String flagAlfresco = ResourceUtil.getKey("file.alfreso");  /// flag alfresco
   			
   			
   			ArchivoAtencion prmArchivoAtencion = new ArchivoAtencion();
   			prmArchivoAtencion.setIdArchivoAtencion(id);
   			
   			prmArchivoAtencion = archivoAtencionService.buscarPorId(prmArchivoAtencion.getIdArchivoAtencion());
   			
   			if (prmArchivoAtencion!=null) {
   				
   				if(!flagAlfresco.equals("0")){
   					
   				   	/** EL SIGUIENTE CODIGO ES LA INTEGRACION CON WSALFRESCO **/
   					if (	prmArchivoAtencion != null 
   						&& 	prmArchivoAtencion.getUiid() != null
   						&& !prmArchivoAtencion.getUiid().equals("")) {
   					
   						byte[] fileDescargado = AlfrescoUtil.getArchivo(prmArchivoAtencion.getUiid());
   						
   						response.getOutputStream().write(fileDescargado);
   						response.flushBuffer();
   						response.setContentType(prmArchivoAtencion.getMimeTypeArchivo());
   					}
   					
   					
   					
   				
   				}else{
   					
   					InputStream inputStream = new FileInputStream(new File(this.rutaBase+prmArchivoAtencion.getRutaArchivo()+prmArchivoAtencion.getNombreArchivo()));
   	   				IOUtils.copy(inputStream, response.getOutputStream());
//   	   				response.setContentType("application/pdf");
   	   				response.flushBuffer();
   	   				response.setContentType(prmArchivoAtencion.getMimeTypeArchivo());
   					
   					
   				}
   				
   				
   				
   				
   			
			}
			
		} catch (IOException e) {
			logger.error(this.logBase+ " : descargar" + e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e);
		}
   		catch (Exception e) {
   			logger.error(this.logBase+ " : descargar" + e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e);
		}
   	}
	
   	

   	@RequestMapping(value="/descargar-archivo-Denuncia/{id}", method = RequestMethod.GET)
   	public void descargarArchivoDenuncia(@PathVariable("id") long id, HttpServletResponse response) {
   		try {

   			
   			String flagAlfresco = ResourceUtil.getKey("file.alfreso");  /// flag alfresco
   			
   			
   			ArchivoDenuncia prmArchivoDenuncia = new ArchivoDenuncia();
   			prmArchivoDenuncia.setIdArchivoDenuncia(id);

   			
   			prmArchivoDenuncia = archivoDenunciaService.buscarPorId(prmArchivoDenuncia.getIdArchivoDenuncia());
   			
   			if (prmArchivoDenuncia!=null) {
   				
   				if(!flagAlfresco.equals("0")){
   					
   				   	/** EL SIGUIENTE CODIGO ES LA INTEGRACION CON WSALFRESCO **/
   					if (	prmArchivoDenuncia != null 
   						&& 	prmArchivoDenuncia.getUiid() != null
   						&& !prmArchivoDenuncia.getUiid().equals("")) {
   					
   						byte[] fileDescargado = AlfrescoUtil.getArchivo(prmArchivoDenuncia.getUiid());
   						
   						response.getOutputStream().write(fileDescargado);
   						response.flushBuffer();
   						response.setContentType(prmArchivoDenuncia.getMimeTypeArchivo());
   					}
   					
   					
   					
   				
   				}else {
					
   					InputStream inputStream = new FileInputStream(new File(this.rutaBase+prmArchivoDenuncia.getRutaArchivoDenuncia()+prmArchivoDenuncia.getNombreArchivo()));
   	   				IOUtils.copy(inputStream, response.getOutputStream());
   	   				response.flushBuffer();
   	   				response.setContentType(prmArchivoDenuncia.getMimeTypeArchivo());
   	   				
   	   				
				}
   				
   				
			}
			
		} catch (IOException e) {
			logger.error(this.logBase+ " : descargarArchivoDenuncia" + e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e);
		}
   		catch (Exception e) {
   			logger.error(this.logBase+ " : descargarArchivoDenuncia" + e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e);
		}
   	}
   	
   	
   	@RequestMapping(value="/descargar-archivo-Caso/{id}", method = RequestMethod.GET)
   	public void descargarArchivoCaso(@PathVariable("id") long id, HttpServletResponse response) {
   		try {

   			
   			String flagAlfresco = ResourceUtil.getKey("file.alfreso");
   		
   			ArchivoCaso prmArchivoCaso = new ArchivoCaso();
   			prmArchivoCaso.setIdArchivoCaso(id);;
   			
   			prmArchivoCaso = archivoCasoService.buscarPorId(prmArchivoCaso.getIdArchivoCaso());
   			
   		
   			if (prmArchivoCaso!=null) {
   				
   				
   				if(!flagAlfresco.equals("0")){
   	   				
   	
   				   	/** EL SIGUIENTE CODIGO ES LA INTEGRACION CON WSALFRESCO **/
   					if (	prmArchivoCaso != null 
   						&& 	prmArchivoCaso.getUiid() != null
   						&& !prmArchivoCaso.getUiid().equals("")) {
   					
   						byte[] fileDescargado = AlfrescoUtil.getArchivo(prmArchivoCaso.getUiid());
   						
   						response.getOutputStream().write(fileDescargado);
   						response.flushBuffer();
   						response.setContentType(prmArchivoCaso.getMimeTypeArchivo());
   					}
   					
   					
   					
   					
   	   			}else{
		   	   			InputStream inputStream = new FileInputStream(new File(prmArchivoCaso.getRutaArchivoCaso()));
		   				IOUtils.copy(inputStream, response.getOutputStream());
		   				response.flushBuffer();
		   				response.setContentType(prmArchivoCaso.getMimeTypeArchivo());
   	   			}
   				
   				
   			
			}
			
		} catch (IOException e) {
			logger.error(this.logBase+ " : descargarArchivoCaso" + e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e);
		}
   		catch (Exception e) {
   			logger.error(this.logBase+ " : descargarArchivoCaso" + e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e);
		}
   	}
   	
   	
}
