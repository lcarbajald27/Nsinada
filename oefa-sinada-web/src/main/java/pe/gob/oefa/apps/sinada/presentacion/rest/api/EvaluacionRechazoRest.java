package pe.gob.oefa.apps.sinada.presentacion.rest.api;


import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import pe.gob.oefa.apps.base.presentacion.response.RespuestaHttp;
import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.base.util.CodeUtil;
import pe.gob.oefa.apps.base.util.EmailAttachmentSender;
import pe.gob.oefa.apps.base.util.UtilRequest;
import pe.gob.oefa.apps.sinada.bean.general.ContactoPersona;
import pe.gob.oefa.apps.sinada.bean.maestros.FormatoCorreo;
import pe.gob.oefa.apps.sinada.bean.proceso.ArchivoAtencion;
import pe.gob.oefa.apps.sinada.bean.proceso.AtencionDenuncia;
import pe.gob.oefa.apps.sinada.bean.proceso.Bandeja;
import pe.gob.oefa.apps.sinada.bean.proceso.BandejaDetalle;
import pe.gob.oefa.apps.sinada.bean.proceso.Denuncia;
import pe.gob.oefa.apps.sinada.bean.proceso.DerivacionDenuncia;
import pe.gob.oefa.apps.sinada.bean.proceso.EvaluacionRechazo;
import pe.gob.oefa.apps.sinada.bean.proceso.PersonaOefa;
import pe.gob.oefa.apps.sinada.bean.proceso.view.DenunciaEstado;
import pe.gob.oefa.apps.sinada.bean.proceso.view.EntidadDenuncia;
import pe.gob.oefa.apps.sinada.bean.sirefa.Efa;
import pe.gob.oefa.apps.sinada.presentacion.rest.api.bas.GenericController;
import pe.gob.oefa.apps.sinada.presentacion.util.AlfrescoUtil;
import pe.gob.oefa.apps.sinada.presentacion.util.ResourceUtil;
import pe.gob.oefa.apps.sinada.presentacion.util.VO;
import pe.gob.oefa.apps.sinada.servicio.inf.general.ContactoPersonaService;
import pe.gob.oefa.apps.sinada.servicio.inf.maestros.FormatoCorreoService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.ArchivoAtencionService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.AtencionDenunciaService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.BandejaDetalleService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.BandejaService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.DenunciaService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.DerivacionDenunciaService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.EvaluacionRechazoService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.PersonaOefaService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.view.DenunciaEstadoService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.view.EntidadDenunciaService;
import pe.gob.oefa.apps.sinada.servicio.inf.sirefa.EfaService;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value="/rest/api/evaluacion-rechazo")
public class EvaluacionRechazoRest
{
	@Autowired
	EvaluacionRechazoService evaluacionRechazoService;
	
	@Autowired
	AtencionDenunciaService atencionDenunciaService;
	
	@Autowired
	private ArchivoAtencionService archivoAtencionService;
	
	@Autowired
	private DerivacionDenunciaService derivacionDenunciaService;
	
	@Autowired
	private BandejaDetalleService bandejaDetalleService;
	
	@Autowired
	private EntidadDenunciaService entidadDenunciaService;
	
	@Autowired
	private EfaService efaService;
	
	@Autowired
	private FormatoCorreoService formatoCorreoService;
	
	@Autowired
	private BandejaService bandejaService;
	
	@Autowired
	private PersonaOefaService personaOefaService;
	
	@Autowired
	private ContactoPersonaService contactoPersonaService;
	
	@Autowired
	private DenunciaEstadoService denunciaEstadoService;
	
	@Autowired
	private DenunciaService denunciaService;
	
private String logBase = "oefa-sinada-web: EvaluacionRechazoRest";
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/listar", method=RequestMethod.GET)
	public RespuestaHttp buscar(@RequestParam(value="prmCriterio",required=false)String prmCriterio)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try
		{
			EvaluacionRechazo prmData = new ObjectMapper().readValue(prmCriterio, EvaluacionRechazo.class);
			List<EvaluacionRechazo> data = (List<EvaluacionRechazo>) evaluacionRechazoService.listar(prmData);
			_RespuestaHttp.setValido(true);
			_RespuestaHttp.setData(data);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			_RespuestaHttp.setValido(false);
			_RespuestaHttp.setMensaje("Hubo un error al procesar la información");
		}
		return _RespuestaHttp;
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/buscar-por-atencion", method=RequestMethod.GET)
	public RespuestaHttp buscarXIdAtencion(@RequestParam(value="prmCriterio",required=false)String prmCriterio)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try
		{
			EvaluacionRechazo prmData = new ObjectMapper().readValue(prmCriterio, EvaluacionRechazo.class);
			EvaluacionRechazo data = evaluacionRechazoService.buscarXIdAtencionDenuncia(prmData);
			
			
			if(data!=null && data.getTipoEvaluacionRechazo().getCodigoRegistro()==2){
				DerivacionDenuncia prmDenuncia = new DerivacionDenuncia();
				prmDenuncia.getEvaluacionRechazo().setIdEvaluacionRechazo(data.getIdEvaluacionRechazo());
				List<DerivacionDenuncia> dataDerivacion = (List<DerivacionDenuncia>) derivacionDenunciaService.listar(prmDenuncia);
				data.getLstDerivacionDenuncia().addAll(dataDerivacion);
			}
			
			_RespuestaHttp.setValido(true);
			_RespuestaHttp.setData(data);
		}
		catch (Exception e)
		{
			logger.error(this.logBase+ " : buscarXIdAtencion" + e.getMessage());
			e.printStackTrace();
			_RespuestaHttp.setValido(false);
			_RespuestaHttp.setMensaje("Hubo un error al procesar la información");
		}
		return _RespuestaHttp;
	}
	
	public List<String> genericArchivoAlfresco(MultipartFile[] multipartFileArray,String folder)
			throws Exception {
		try {
			List<String> lstUiidArchivo = new ArrayList<String>();

			for (MultipartFile objArchivo : multipartFileArray) {

				// Obtiene el nombre del archivo
				String tmpNombreArchivo = objArchivo.getOriginalFilename();

				/** EL SIGUIENTE CODIGO ES LA INTEGRACION CON WSALFRESCO **/
				String uiid = AlfrescoUtil
						.grabarArchivoAlfresco(objArchivo, folder);

				if (VO.isNullOrEmpty(uiid)) {

					return null;
				}

				lstUiidArchivo.add(uiid);

			}
			return lstUiidArchivo;
		} catch (Exception e) {
			return null;
			// TODO: handle exception
		}
		

	}

	@ResponseBody
	@RequestMapping(value = "/registrar",method = RequestMethod.POST)
	public RespuestaHttp registrar(
			@RequestParam("file")MultipartFile[] archivo,
			@RequestParam("strContenido")String contenido,
			HttpServletRequest request
			)throws Exception
	{
		
		// Tabla Evaluacion Rechazo = 2
		RespuestaHttp respuesta = new RespuestaHttp();
		
		GenericController gn = new GenericController();
		EvaluacionRechazo prmData = new ObjectMapper().readValue(contenido, EvaluacionRechazo.class);
		
		
		try
		{
			
			
			
/****************************************************************/
			int flagMetodoProceso=0;
			
			String flagAlfresco = ResourceUtil.getKey("file.alfreso");
			List<String> lstArchivoGenericoAlfresco = new ArrayList<String>();
			if(archivo!=null && archivo.length!=0 && !flagAlfresco.equals("0")){
				/********* Ruta ****************/
				String folderGeneric = ResourceUtil.getKey("file.path.informe-evaluacion-rechazo");
				folderGeneric = folderGeneric.replace("{id}", String.valueOf(0));
				
				/********* Ruta ****************/
				lstArchivoGenericoAlfresco =	genericArchivoAlfresco(archivo,folderGeneric);
				
				if(lstArchivoGenericoAlfresco==null || lstArchivoGenericoAlfresco.size()==0){
					flagMetodoProceso = 1;
					respuesta.setMensaje("Hubo un error al registrar el archivo, intentelo mas tarde.");
					return respuesta;
					
				}
				
			}
			
			long idGenerado = 0;
			/*********************************************************************/
			if(flagMetodoProceso==0){
				prmData.setHostIp(UtilRequest.getClientIpAddress(request));
				
				String str=			prmData.getMotivoDescripcion();
				System.out.println("A "+str);
				str= CodeUtil.parseEncode(str);
				System.out.println("B "+str);
				prmData.setMotivoDescripcion(str);
				
				idGenerado = evaluacionRechazoService.insertar(prmData);
				/********************* Recuperar Datos del objeto Atencion Denuncia   *********************/
				AtencionDenuncia prmDataAtencionDenuncia  = new AtencionDenuncia();
				
				prmDataAtencionDenuncia = atencionDenunciaService.buscarPorId(prmData.getAtencionDenuncia().getIdAtencionDenuncia());
				/**** Fin Recuperar datos  ****/
				
				if(prmData.getTipoEvaluacionRechazo().getCodigoRegistro()==1){
					/**** Actualizar Estado Bandeja ****/
					BandejaDetalle prmBandejaDetalle = new BandejaDetalle();
					
				
					prmBandejaDetalle.setIdBandejaDetalle(prmDataAtencionDenuncia.getDetalleBandeja().getIdBandejaDetalle());
					prmBandejaDetalle.setEstado(1);
					prmBandejaDetalle.setHostIp(UtilRequest.getClientIpAddress(request));
					prmBandejaDetalle.setPrm1(prmData.getPrm1());
					bandejaDetalleService.actualizar(prmBandejaDetalle);
					
					/*********************** Fin Actualizar Estado Bandeja  **********************/
					AtencionDenuncia prmAtencionDenuncia = new AtencionDenuncia();
					prmAtencionDenuncia.setIdAtencionDenuncia(prmData.getAtencionDenuncia().getIdAtencionDenuncia());
					prmAtencionDenuncia.getEstado().setCodigoRegistro(2);
					prmAtencionDenuncia.setHostIp(UtilRequest.getClientIpAddress(request));
					prmAtencionDenuncia.setPrm1(prmData.getPrm1());
					atencionDenunciaService.actualizar(prmAtencionDenuncia);
					
					
					for (ArchivoAtencion dataArchivo : prmData.getLstArchivoAtencion()) {
						
						String tmpNombreArchivo = archivo[dataArchivo.getPosicionArchivo()].getOriginalFilename(); 
						
						
						String folder = ResourceUtil.getKey("file.path.informe-evaluacion-rechazo");
						folder = folder.replace("{id}", String.valueOf(idGenerado));
		/*************************************************************************************************/
		/***																						   ***/				
		/*************************************************************************************************/
						
						
//						String flagAlfresco = ResourceUtil.getKey("file.alfreso");
						if(!flagAlfresco.equals("0")){
							
//						  	/** EL SIGUIENTE CODIGO ES LA INTEGRACION CON WSALFRESCO **/
//							String uiid = AlfrescoUtil.grabarArchivoAlfresco(archivo[dataArchivo.getPosicionArchivo()], folder);
//													
//							if (VO.isNullOrEmpty(uiid)) {
//								respuesta.setMensaje("No se pudo archivar el documento en alfresco.");
//								return respuesta;
//							}
//							
//							dataArchivo.setUiid(uiid);
							
							dataArchivo.setUiid(lstArchivoGenericoAlfresco.get(dataArchivo.getPosicionArchivo()));
							
							
							
							
						}else{
							
							
							String rutaArchivoCopiado = gn.copiarArchivo(
									tmpNombreArchivo, 
									folder, 
									archivo[dataArchivo.getPosicionArchivo()].getInputStream());
							
							if (VO.isNullOrEmpty(rutaArchivoCopiado)) {
								respuesta.setMensaje("No se pudo archivar el documento.");
								return respuesta;
							}
						}
						
		/*************************************************************************************************/
		/***																						   ***/				
		/*************************************************************************************************/
							
						
					
						dataArchivo.setTipoTabla(2);
						dataArchivo.setNombreArchivo(CodeUtil.parseEncode(tmpNombreArchivo));
						dataArchivo.setRutaArchivo(folder);
						dataArchivo.setDescripcionArchivo(CodeUtil.parseEncode(dataArchivo.getDescripcionArchivo()));
						dataArchivo.setMimeTypeArchivo(archivo[dataArchivo.getPosicionArchivo()].getContentType());
						dataArchivo.setIdAtencionDenuncia(idGenerado);
						archivoAtencionService.insertar(dataArchivo);
						
					}
					
				}
				
				if(prmData.getTipoEvaluacionRechazo().getCodigoRegistro()==2){
					AtencionDenuncia prmAtencionDenuncia = new AtencionDenuncia();
					prmAtencionDenuncia.setIdAtencionDenuncia(prmData.getAtencionDenuncia().getIdAtencionDenuncia());
					prmAtencionDenuncia.getEstado().setCodigoRegistro(3);
					
					/***/
					prmAtencionDenuncia.setHostIp(UtilRequest.getClientIpAddress(request));
					prmAtencionDenuncia.setPrm1(prmData.getPrm1());
					/***/
					atencionDenunciaService.actualizar(prmAtencionDenuncia);
					
				}
				
				
				if(prmData.getTipoEvaluacionRechazo().getCodigoRegistro()==3){
					/**** Actualizar Estado Bandeja ****/
					BandejaDetalle prmBandejaDetalle = new BandejaDetalle();
					
				
					prmBandejaDetalle.setIdBandejaDetalle(prmDataAtencionDenuncia.getDetalleBandeja().getIdBandejaDetalle());
					prmBandejaDetalle.setEstado(4);
					/*****/
					prmBandejaDetalle.setHostIp(UtilRequest.getClientIpAddress(request));
					prmBandejaDetalle.setPrm1(prmData.getPrm1());
					/*****/
					bandejaDetalleService.actualizar(prmBandejaDetalle);
					
					
					AtencionDenuncia prmAtencionDenuncia = new AtencionDenuncia();
					prmAtencionDenuncia.setIdAtencionDenuncia(prmData.getAtencionDenuncia().getIdAtencionDenuncia());
					prmAtencionDenuncia.getEstado().setCodigoRegistro(4);
					/*****/
					prmAtencionDenuncia.setHostIp(UtilRequest.getClientIpAddress(request));
					prmAtencionDenuncia.setPrm1(prmData.getPrm1());
					/*****/
					atencionDenunciaService.actualizar(prmAtencionDenuncia);
					
					int z =0;
							for (MultipartFile archivoEvaluacion : archivo) {
								
								
								ArchivoAtencion dataArchivo = new ArchivoAtencion();
						String tmpNombreArchivo = archivoEvaluacion.getOriginalFilename(); 
						
						
						String folder = ResourceUtil.getKey("file.path.informe-evaluacion-rechazo");
						folder = folder.replace("{id}", String.valueOf(idGenerado));
						
						
		/*************************************************************************************************/				
		/***																						  ***/
		/************************************************************************************************/					
//						String flagAlfresco = ResourceUtil.getKey("file.alfreso");
						if(!flagAlfresco.equals("0")){
							
//						  	/** EL SIGUIENTE CODIGO ES LA INTEGRACION CON WSALFRESCO **/
//							String uiid = AlfrescoUtil.grabarArchivoAlfresco(archivoEvaluacion, folder);
//													
//							if (VO.isNullOrEmpty(uiid)) {
//								respuesta.setMensaje("No se pudo archivar el documento en alfresco.");
//								return respuesta;
//							}
//							
//							dataArchivo.setUiid(uiid);
							
							dataArchivo.setUiid(lstArchivoGenericoAlfresco.get(z));
							
							
						}else{
							
							String rutaArchivoCopiado = gn.copiarArchivo(
									tmpNombreArchivo, 
									folder, 
									archivoEvaluacion.getInputStream());
							
							if (VO.isNullOrEmpty(rutaArchivoCopiado)) {
								respuesta.setMensaje("No se pudo archivar el documento.");
								return respuesta;
							}
						}
						
						
	   /*************************************************************************************************/				
	   /***																						      ***/
	   /*************************************************************************************************/	
						
						dataArchivo.setTipoTabla(2);
						dataArchivo.setNombreArchivo(CodeUtil.parseEncode(tmpNombreArchivo));
						dataArchivo.setRutaArchivo(folder);
						dataArchivo.setDescripcionArchivo(CodeUtil.parseEncode(dataArchivo.getDescripcionArchivo()));
						dataArchivo.setMimeTypeArchivo(archivoEvaluacion.getContentType());
						dataArchivo.setIdAtencionDenuncia(idGenerado);
						
						archivoAtencionService.insertar(dataArchivo);
						z=z+1;
						
					}
					
							
							
							
							
			/***********************************************************************************/
			/***			VERIFICAR SI LA DENUNCIA DE SER ARCHIVADA						   */				
			/***********************************************************************************/	
							
							DenunciaEstado prmDenunciaEstado = new DenunciaEstado();
							
							prmDenunciaEstado =	denunciaEstadoService.buscarPorId(prmDataAtencionDenuncia.getDenuncia().getIdDenuncia());
							
							if(prmDenunciaEstado!=null){
								if(prmDenunciaEstado.getTotalAtenciones()==prmDenunciaEstado.getDenunciasArchivadas()){
									
									Denuncia prmDenuncia = new Denuncia();
									prmDenuncia.setIdDenuncia(prmDenunciaEstado.getIdDenuncia());
									prmDenuncia.setEstado(4);
									/******* Auditoria ******/
									
									prmDenuncia.setHostIp(UtilRequest.getClientIpAddress(request));
									prmDenuncia.setPrm1(prmData.getPrm1());
									/************************/
									denunciaService.cambiarEstado(prmDenuncia);
									
								}
							}	
							
							
			/***********************************************************************************/
			/***			FIN VERIFICAR SI LA DENUNCIA DE SER ARCHIVADA					   */				
			/***********************************************************************************/			
							
							
							
							
							
							
				}
				
				EvaluacionRechazo prmEvaluacionRechazo = null;
				prmEvaluacionRechazo = evaluacionRechazoService.buscarPorId(idGenerado);
				
				/************************ Correo ***************************************/
				
				String texto1="";
				String texto2="";
				String texto3="";
				String texto4="";
				String texto5="";
				String texto6="";
				String texto7="";
				
				if(prmEvaluacionRechazo!=null){
					String asunto = "";
					if(prmEvaluacionRechazo.getTipoEvaluacionRechazo().getCodigoRegistro()==1){
						asunto = "Reiterado";
						
						texto2="<b>Motivo : </b> ";
						texto3=prmEvaluacionRechazo.getMotivoDescripcion();
						
					}
				if(prmEvaluacionRechazo.getTipoEvaluacionRechazo().getCodigoRegistro()==2){
					asunto = "Derivado";	
					
					
					
					}
				
				if(prmEvaluacionRechazo.getTipoEvaluacionRechazo().getCodigoRegistro()==3){
					asunto = "Archivado";
					
					texto2="<b>Motivo : </b> ";
					texto3=prmEvaluacionRechazo.getMotivo().getDescripcion();
					}
				
				
				enviarCorreo(prmDataAtencionDenuncia.getDetalleBandeja().getIdBandejaDetalle(),asunto,texto1,texto2,texto3,texto4,texto5,texto6,texto7);
				}
				
				
				
			}
			
			
		
			
			
			
			
			
			
			
			
			/************************ Fin Correo ***************************************/
			
			
			if(idGenerado>0)
			{
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha registrado la evaluación correctamente");
				respuesta.setData(prmData.getIdEvaluacionRechazo());
			} 
			else 
			{
				respuesta.setMensaje("No se pudo registrar la evaluación");
			}
		} 
		catch (Exception e) 
		{
			logger.error(this.logBase+ " : registrar" + e.getMessage());
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al registrar la evaluación");
		}
		return respuesta;
	}
	
	public void enviarCorreo(long idBandejaDetalle, String asunto,String texto1,String texto2,String texto3,String texto4,
			String texto5,String texto6,String texto7 ) throws ServicioException{
		
		EntidadDenuncia prmEntidadDenuncia = new EntidadDenuncia();
		prmEntidadDenuncia.setIdBandejaDetalle(idBandejaDetalle);
		prmEntidadDenuncia = 	entidadDenunciaService.buscarPorId(prmEntidadDenuncia.getIdBandejaDetalle());
	
		if(prmEntidadDenuncia!=null){
			
			if(prmEntidadDenuncia.getTipoEntidadComponente()==2){
				Efa efa = new Efa();
				efa = efaService.buscarPorId(prmEntidadDenuncia.getIdEfa());
				
				
				if(efa.getCorreo()!=null){
					
					FormatoCorreo formatoCorreo = new FormatoCorreo();
					long idFormatoCorreo = 1;
					formatoCorreo = formatoCorreoService.buscarPorId(idFormatoCorreo);
					String titulo =prmEntidadDenuncia.getNombreEntidad()  + " se ha "+ asunto +" la denuncia "+prmEntidadDenuncia.getCodigoDenuncia();
					
					
					
					String mensaje = formatoCorreo.getMensaje();
					
					texto1="Denuncia "+ prmEntidadDenuncia.getCodigoDenuncia()+ " se ha " + asunto;
//					texto1 ="<center><h3> Observación de Acción Denuncia "+accionesRealizadas.getCodigoDenuncia()+"</h3></center>";
//			
//					
//					texto2 ="Tipo Acción : "+accionesRealizadas.getNombreTipoAccion() ;
//
				
					
					mensaje = mensaje.replace("TextoCorreo1", texto1);
					mensaje = mensaje.replace("TextoCorreo2", texto2);
					mensaje = mensaje.replace("TextoCorreo3", texto3);
					mensaje = mensaje.replace("TextoCorreo4", texto4);
					mensaje = mensaje.replace("TextoCorreo5", texto5);
					mensaje = mensaje.replace("TextoCorreo6", texto6);
					mensaje = mensaje.replace("TextoCorreo7", texto7);
					
					try {
						EmailAttachmentSender.sendEmailWithAttachments(efa.getCorreo(), titulo, mensaje, null);
					} catch (AddressException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (MessagingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				
					
					
				}
				
				
			}
			
			
			if(prmEntidadDenuncia.getTipoEntidadComponente()==1){// OEFA
				
				PersonaOefa prmPersonaOefa = new PersonaOefa();
				List<PersonaOefa> lstPersonaOefa = null;
				prmPersonaOefa.getDireccion().setCodigoRegistro(prmEntidadDenuncia.getDireccionSupervicion());
				prmPersonaOefa.getSubDireccion().setCodigoRegistro(prmEntidadDenuncia.getSubDireccion());
				lstPersonaOefa = (List<PersonaOefa>) personaOefaService.listar(prmPersonaOefa);
				
				for (PersonaOefa personaOefa : lstPersonaOefa) {
					
					
					ContactoPersona contactoPersona = new ContactoPersona();
					List<ContactoPersona> lstContactoPersona = null;
					contactoPersona.setTipoPersona(1);
					contactoPersona.setIdPersona(personaOefa.getPersona().getIdPersona());
					lstContactoPersona = (List<ContactoPersona>) contactoPersonaService.listar(contactoPersona);
					
					for (ContactoPersona contacto : lstContactoPersona) {
						
						if(contacto.getTipoContacto()==2){
							
							FormatoCorreo formatoCorreo = new FormatoCorreo();
							long idFormatoCorreo = 1;
							formatoCorreo = formatoCorreoService.buscarPorId(idFormatoCorreo);
							String titulo =prmEntidadDenuncia.getNombreEntidad()  + " se ha "+ asunto +" la denuncia "+prmEntidadDenuncia.getCodigoDenuncia();
							
							
							String mensaje = formatoCorreo.getMensaje();
							
//							texto1 ="<center><h3> Observación de Acción Denuncia "+accionesRealizadas.getCodigoDenuncia()+"</h3></center>";
//					
//							
//							texto2 ="Tipo Acción : "+accionesRealizadas.getNombreTipoAccion() ;
//		
						
						
							mensaje = mensaje.replace("TextoCorreo1", texto1);
							mensaje = mensaje.replace("TextoCorreo2", texto2);
							mensaje = mensaje.replace("TextoCorreo3", texto3);
							mensaje = mensaje.replace("TextoCorreo4", texto4);
							mensaje = mensaje.replace("TextoCorreo5", texto5);
							mensaje = mensaje.replace("TextoCorreo6", texto6);
							mensaje = mensaje.replace("TextoCorreo7", texto7);
							
							try {
								EmailAttachmentSender.sendEmailWithAttachments(contacto.getValor(), titulo, mensaje, null);
							} catch (AddressException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (MessagingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							
						
							
							
						}
					
					}
					
					
				}
				
				
				
			}
			
		
			
		}
		
		
	}
	
	
	/*********************************** Derivacion Denuncia    *********************************/
	
	@ResponseBody
	@RequestMapping(value = "/derivarDenuncia",method = RequestMethod.POST)
	public RespuestaHttp derivarDenuncia(@RequestBody EvaluacionRechazo prmData,HttpServletRequest request)throws Exception
	{
		RespuestaHttp respuesta = new RespuestaHttp();
		try
		{
			AtencionDenuncia prmDataAtencionDenuncia = new AtencionDenuncia();
			prmDataAtencionDenuncia.setIdAtencionDenuncia(prmData.getAtencionDenuncia().getIdAtencionDenuncia());
			prmDataAtencionDenuncia = atencionDenunciaService.buscarPorId(prmDataAtencionDenuncia.getIdAtencionDenuncia());
			
			
			/************ cambiar de estado a archivada ***************/
		
			prmData.setHostIp(UtilRequest.getClientIpAddress(request));
		
			long idGenerado = evaluacionRechazoService.insertar(prmData);
			
			
			
			long idBandejaDetalle = 0;
			
			if(idGenerado!= 0){
				if(prmDataAtencionDenuncia!=null){
					
					BandejaDetalle prmBandejaDetalle = new BandejaDetalle();
					
					prmBandejaDetalle.setIdBandejaDetalle(prmDataAtencionDenuncia.getDetalleBandeja().getIdBandejaDetalle());
					prmBandejaDetalle.setEstado(4);
					prmBandejaDetalle.setHostIp(UtilRequest.getClientIpAddress(request));
					prmBandejaDetalle.setPrm1(prmData.getPrm1());
					bandejaDetalleService.actualizar(prmBandejaDetalle);
					
				}
			
				
				
				/**********************************/
				
				for (DerivacionDenuncia prmDerivacion : prmData.getLstDerivacionDenuncia()) {
					
					prmDerivacion.getEvaluacionRechazo().setIdEvaluacionRechazo(idGenerado);

					/**** Auditoria ****/
					prmDerivacion.setHostIp(UtilRequest.getClientIpAddress(request));
					prmDerivacion.setPrm1(prmData.getPrm1());
					
					/****/
					derivacionDenunciaService.insertar(prmDerivacion);
					
					if(prmDerivacion.getTipoDestinatario().getCodigoRegistro()==1){
						
						
						
						Bandeja prmBandeja = new Bandeja();
						prmBandeja.setTipoResponsable(3);// 3 -- OEFA  -- 4 EFA
//						prmBandeja.setIdResponsable(lstPersonaOefa.get(s).getIdPersonaOefa());
						prmBandeja.setDireccion(prmDerivacion.getDireccionSupervicion().getCodigoRegistro());
						prmBandeja.setSubDireccion(prmDerivacion.getSubDireccion().getCodigoRegistro());
						prmBandeja.setCoordinacion(0);
						prmBandeja.setEstado(1);
						prmBandeja =bandejaService.validarBandejaOefa(prmBandeja);
						
						BandejaDetalle prmBandejaDetalle = new BandejaDetalle();
						prmBandejaDetalle.setIdBandeja(prmBandeja.getIdBandeja());
						prmBandejaDetalle.setTipoBandeja(3);
						prmBandejaDetalle.setIdDenuncia(prmDataAtencionDenuncia.getDenuncia().getIdDenuncia());
						prmBandejaDetalle.setTipoAsignacion(1);
						prmBandejaDetalle.setEstado(1);

						/**** Auditoria ****/	
						
						prmBandejaDetalle.setHostIp(UtilRequest.getClientIpAddress(request));
						prmBandejaDetalle.setPrm1(prmData.getPrm1());
						
						/*********************/
						idBandejaDetalle = 	bandejaDetalleService.insertar(prmBandejaDetalle);
						
						
						
						/*************************************************/
						
							
						
						PersonaOefa prmPersonaOefa = new PersonaOefa();
						List<PersonaOefa> lstPersonaOefa = null;
						prmPersonaOefa.getDireccion().setCodigoRegistro(prmBandeja.getDireccion());
						prmPersonaOefa.getSubDireccion().setCodigoRegistro(prmBandeja.getSubDireccion());
						lstPersonaOefa = (List<PersonaOefa>) personaOefaService.listar(prmPersonaOefa);
						
						for (PersonaOefa personaOefa : lstPersonaOefa) {
							
							EntidadDenuncia prmEntidadDenuncia = new EntidadDenuncia();
							prmEntidadDenuncia.setIdBandejaDetalle(idBandejaDetalle);
							prmEntidadDenuncia = 	entidadDenunciaService.buscarPorId(prmEntidadDenuncia.getIdBandejaDetalle());
						
							
							
							ContactoPersona contactoPersona = new ContactoPersona();
							List<ContactoPersona> lstContactoPersona = null;
							contactoPersona.setTipoPersona(1);
							contactoPersona.setIdPersona(personaOefa.getPersona().getIdPersona());
							lstContactoPersona = (List<ContactoPersona>) contactoPersonaService.listar(contactoPersona);
							
							for (ContactoPersona contacto : lstContactoPersona) {
								
								if(contacto.getTipoContacto()==2 && idBandejaDetalle!=0){
									FormatoCorreo formatoCorreo = new FormatoCorreo();
									long idFormatoCorreo = 1;
									formatoCorreo = formatoCorreoService.buscarPorId(idFormatoCorreo);
									
									String texto1 = "";
									String texto2 = "";
									String texto3 = "";
									String texto4 = "";
									
									String mensaje = formatoCorreo.getMensaje();
									
									texto1 ="Sres. " +personaOefa.getPersona().getNombreCompleto();
																
									texto2 ="Se ha generado la Denuncia " + prmEntidadDenuncia.getCodigoDenuncia() +" para su atención.";
								
									mensaje = mensaje.replace("TextoCorreo1", texto1);
									mensaje = mensaje.replace("TextoCorreo2", texto2);
									mensaje = mensaje.replace("TextoCorreo3", texto3);
									mensaje = mensaje.replace("TextoCorreo4", texto4);
									mensaje = mensaje.replace("TextoCorreo5", "");
									mensaje = mensaje.replace("TextoCorreo6", "");
									mensaje = mensaje.replace("TextoCorreo7", "");
									
									EmailAttachmentSender.sendEmailWithAttachments(contacto.getValor(),  "Registro Denuncia "+prmEntidadDenuncia.getCodigoDenuncia(), mensaje, null);
									
									
								}
							
							}
							
							
						}
						
						/************************************************/
					
					}
					
					
					if(prmDerivacion.getTipoDestinatario().getCodigoRegistro()==2){
						
						Bandeja prmBandeja = new Bandeja();
						prmBandeja.setTipoResponsable(4);  // 3 -- OEFA  -- 4 EFA
						prmBandeja.setIdEFa(prmDerivacion.getEfa().getIdEfa());
						prmBandeja.setEstado(1);
						prmBandeja =bandejaService.validarBandejaEfa(prmBandeja);
						
						BandejaDetalle prmBandejaDetalle = new BandejaDetalle();
						prmBandejaDetalle.setIdBandeja(prmBandeja.getIdBandeja());
						prmBandejaDetalle.setTipoBandeja(3);
						prmBandejaDetalle.setIdDenuncia(prmDataAtencionDenuncia.getDenuncia().getIdDenuncia());
						prmBandejaDetalle.setTipoAsignacion(1);
						prmBandejaDetalle.setEstado(1);
						
						/**** Auditoria ****/	
						
						prmBandejaDetalle.setHostIp(UtilRequest.getClientIpAddress(request));
						prmBandejaDetalle.setPrm1(prmData.getPrm1());
						
						/*********************/
						idBandejaDetalle =	bandejaDetalleService.insertar(prmBandejaDetalle);
						
						/******************************************/
						Efa prmEfa = new Efa();
						prmEfa = efaService.buscarPorId(prmDerivacion.getEfa().getIdEfa());
						
						if(prmEfa!=null && idBandejaDetalle!=0){
							if(prmEfa.getCorreo()!=null){
								
								EntidadDenuncia prmEntidadDenuncia = new EntidadDenuncia();
								prmEntidadDenuncia.setIdBandejaDetalle(idBandejaDetalle);
								prmEntidadDenuncia = 	entidadDenunciaService.buscarPorId(prmEntidadDenuncia.getIdBandejaDetalle());
							
								
								
								FormatoCorreo formatoCorreo = new FormatoCorreo();
								long idFormatoCorreo = 1;
								formatoCorreo = formatoCorreoService.buscarPorId(idFormatoCorreo);
								
								String texto1 = "";
								String texto2 = "";
								String texto3 = "";
								String texto4 = "";
								
								String mensaje = formatoCorreo.getMensaje();

								
								texto1 ="Sres. " +prmEfa.getNombre();
								
								texto2 ="Se ha generado la Denuncia " + prmEntidadDenuncia.getCodigoDenuncia() +" para su atención.";
							
								mensaje = mensaje.replace("TextoCorreo1", texto1);
								mensaje = mensaje.replace("TextoCorreo2", texto2);
								mensaje = mensaje.replace("TextoCorreo3", texto3);
								mensaje = mensaje.replace("TextoCorreo4", texto4);
								mensaje = mensaje.replace("TextoCorreo5", "");
								mensaje = mensaje.replace("TextoCorreo6", "");
								mensaje = mensaje.replace("TextoCorreo7", "");
								
							
								
								/************************ Registrar Correo **************************/
								
								   String mailTo = prmEfa.getCorreo();
							     String subject =  "Registro Denuncia "+prmEntidadDenuncia.getCodigoDenuncia() +" para su Atención";
//							     FormatoCorreo_1 fr = new FormatoCorreo_1();
//							     String message =fr.formatoCorreoOrganosCompetentes(CodigoDenuncia);
							
							     // attachments
							    
							    /* attachFiles[0] = "C:/sinada/problematica/7/archivo/bibliografiasReporte (29).pdf";
							     attachFiles[1] = "C:/sinada/problematica/7/archivo/bibliografiasReporte (30).pdf";
							     attachFiles[2] = "C:/sinada/problematica/7/archivo/bibliografiasReporte (31).pdf";
							*/
							     try {
							     	EmailAttachmentSender.sendEmailWithAttachments( mailTo,subject, mensaje, null);
							         System.out.println("Email sent.");
							     } catch (Exception ex) {
							         System.out.println("Could not send email.");
							         ex.printStackTrace();
							     }
								
								/***********************************************************/
							}
						}
						
						
						
						/********************************************/
						
						
						
						
						
					}
					
				}
			
				
			}
			
			AtencionDenuncia prmAtencionDenuncia = new AtencionDenuncia();
			prmAtencionDenuncia.setIdAtencionDenuncia(prmData.getAtencionDenuncia().getIdAtencionDenuncia());
			prmAtencionDenuncia.getEstado().setCodigoRegistro(3);
			/**** Auditoria ****/	
			
			prmAtencionDenuncia.setHostIp(UtilRequest.getClientIpAddress(request));
			prmAtencionDenuncia.setPrm1(prmData.getPrm1());
			
			/*********************/
			atencionDenunciaService.actualizar(prmAtencionDenuncia);
			
			if(idGenerado>0)
			{
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha registrado la información satisfactoriamente");
				respuesta.setData(prmData.getIdEvaluacionRechazo());
			} 
			else 
			{
				respuesta.setMensaje("No se pudo registrar la información");
			}
		} 
		catch (Exception e) 
		{
			logger.error(this.logBase+ " : derivarDenuncia" + e.getMessage());
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al registrar la información");
		}
		return respuesta;
	}
	
	@ResponseBody
	@RequestMapping(value ="/actualizar", method = RequestMethod.POST)
	public  RespuestaHttp actualizar(@RequestBody EvaluacionRechazo prmData,HttpServletRequest request)throws Exception {
		RespuestaHttp respuesta = new RespuestaHttp();
		try{
			
			/**** Auditoria ****/	
			
			prmData.setHostIp(UtilRequest.getClientIpAddress(request));
	
			
			/*********************/
			int result = evaluacionRechazoService.actualizar(prmData);
			
			if(result>0){
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha actualizado	la evaluación satisfactoriamente");
			}else {
				respuesta.setMensaje("No se pudo actualizar la evaluación");
			}
		}catch (Exception e) {
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al actualizar la evaluación");
		}
		return respuesta;
	}
	
	@ResponseBody
	@RequestMapping(value = "/eliminar", method = RequestMethod.POST)
	public RespuestaHttp eliminar(
			@RequestBody EvaluacionRechazo prmData,HttpServletRequest request)
			throws Exception{
		RespuestaHttp respuesta = new RespuestaHttp();
		try{
			
			
			prmData.setHostIp(UtilRequest.getClientIpAddress(request));
	
			int result = evaluacionRechazoService.eliminar(prmData);
			if(result>0){
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha eliminado la evaluación correctamente");
			}else {
				respuesta.setMensaje("No se pudo eliminar la evaluación");
			}
		} catch (Exception e){
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al eliminar la evaluación");
		}
		return respuesta;
	}
	
	@ResponseBody
	@RequestMapping(value = "/buscarPorId", method = RequestMethod.POST)
	public RespuestaHttp buscarPorId(
			@RequestBody EvaluacionRechazo prmData,HttpServletRequest request)
			throws Exception{
		RespuestaHttp respuesta = new RespuestaHttp();
		try{
			
			prmData = evaluacionRechazoService.buscarPorId(prmData.getIdEvaluacionRechazo());
			
			
			if(prmData!=null){
				respuesta.setValido(true);
				respuesta.setMensaje("Se encontró la evaluación");
			}else {
				respuesta.setMensaje("No se encontró el dato");
			}
		} catch (Exception e){
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al procesar la información");
		}
		return respuesta;
	}
	
	
	
}
