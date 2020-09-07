package pe.gob.oefa.apps.sinada.presentacion.rest.api;


import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
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
import pe.gob.oefa.apps.sinada.bean.maestros.Maestro;
import pe.gob.oefa.apps.sinada.bean.proceso.ArchivoAtencion;
import pe.gob.oefa.apps.sinada.bean.proceso.AtencionDenuncia;
import pe.gob.oefa.apps.sinada.bean.proceso.Bandeja;
import pe.gob.oefa.apps.sinada.bean.proceso.BandejaDetalle;
import pe.gob.oefa.apps.sinada.bean.proceso.Denuncia;
import pe.gob.oefa.apps.sinada.bean.proceso.PersonaOefa;
import pe.gob.oefa.apps.sinada.bean.proceso.view.AccionesRealizadas;
import pe.gob.oefa.apps.sinada.bean.proceso.view.DenunciaEstado;
import pe.gob.oefa.apps.sinada.bean.proceso.view.EntidadDenuncia;
import pe.gob.oefa.apps.sinada.bean.seguridad.Usuario;
import pe.gob.oefa.apps.sinada.bean.sirefa.Efa;
import pe.gob.oefa.apps.sinada.presentacion.rest.api.bas.GenericController;
import pe.gob.oefa.apps.sinada.presentacion.util.AlfrescoUtil;
import pe.gob.oefa.apps.sinada.presentacion.util.ResourceUtil;
import pe.gob.oefa.apps.sinada.presentacion.util.VO;
import pe.gob.oefa.apps.sinada.servicio.inf.general.ContactoPersonaService;
import pe.gob.oefa.apps.sinada.servicio.inf.maestros.FormatoCorreoService;
import pe.gob.oefa.apps.sinada.servicio.inf.maestros.MaestroService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.ArchivoAtencionService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.AtencionDenunciaService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.BandejaDetalleService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.BandejaService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.DenunciaService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.NotificacionesService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.PersonaOefaService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.view.AccionesRealizadasService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.view.DenunciaEstadoService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.view.EntidadDenunciaService;
import pe.gob.oefa.apps.sinada.servicio.inf.seguridad.UsuarioService;
import pe.gob.oefa.apps.sinada.servicio.inf.sirefa.EfaService;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value="/rest/api/atencion-denuncia")
public class AtencionDenunciaRest
{
	@Autowired
	private AtencionDenunciaService atencionDenunciaService;
	
	@Autowired
	private ArchivoAtencionService archivoAtencionService;
	
	@Autowired
	private DenunciaService denunciaService;
		
	@Autowired
	private BandejaDetalleService bandejaDetalleService;
	
	@Autowired
	private AccionesRealizadasService accionesRealizadasService;
	
	@Autowired
	private EntidadDenunciaService entidadDenunciaService;
	
	@Autowired
	private NotificacionesService notificacionService;
	
	@Autowired
	private DenunciaEstadoService denunciaEstadoService;
	
	@Autowired
	private BandejaService bandejaService;
	
	@Autowired
	private ContactoPersonaService contactoPersonaService;
	
	@Autowired 
	private FormatoCorreoService formatoCorreoService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private EfaService efaService;
	
	@Autowired
	private PersonaOefaService personaOefaService;
	
	@Autowired
	MaestroService maestroService;
	
private String logBase = "oefa-sinada-web: AtencionDenunciaRest";
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/listar", method=RequestMethod.GET)
	public RespuestaHttp buscar(@RequestParam(value="prmCriterio",required=false)String prmCriterio)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try
		{
			AtencionDenuncia prmData = new ObjectMapper().readValue(prmCriterio, AtencionDenuncia.class);
			List<AtencionDenuncia> data = (List<AtencionDenuncia>) atencionDenunciaService.listar(prmData);
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

	
	@ResponseBody
	@RequestMapping(value = "/registrar",method = RequestMethod.POST)
	public RespuestaHttp registrar(@RequestBody AtencionDenuncia prmData,HttpServletRequest request)throws Exception
	{
		RespuestaHttp respuesta = new RespuestaHttp();
		try
		{
			prmData.setHostIp(UtilRequest.getClientIpAddress(request));
			long idGenerado = atencionDenunciaService.insertar(prmData);
			
			if(prmData.getTipoAtencion().getCodigoRegistro()==3){
				/********************************************************************/
				EntidadDenuncia prmEntidadDenuncia = new EntidadDenuncia();
				prmEntidadDenuncia.setIdBandejaDetalle(prmData.getDetalleBandeja().getIdBandejaDetalle());
				prmEntidadDenuncia = 	entidadDenunciaService.buscarPorId(prmEntidadDenuncia.getIdBandejaDetalle());
				
				/**********************************************************************/
				/****																***/
				/**********************************************************************/
				String titulo = "";
				String mensaje = "";
				
				FormatoCorreo formatoCorreo = new FormatoCorreo();
				long idFormatoCorreo = 1;
				formatoCorreo = formatoCorreoService.buscarPorId(idFormatoCorreo);
				 titulo = "La denuncia "+prmEntidadDenuncia.getCodigoDenuncia()+" ha sido aceptada";
				
				
				
				String texto1 = "";
				String texto2 = "";
				String texto3 = "";
				String texto4 = "";
				String texto5 = "";
				String texto6 = "";
				String texto7 = "";
				
				 mensaje = formatoCorreo.getMensaje();
				
				texto1 ="La entidad " + prmEntidadDenuncia.getNombreEntidad() + " ha aceptado la denuncia " + prmEntidadDenuncia.getCodigoDenuncia()+"." ;
		
				
			
				mensaje = mensaje.replace("TextoCorreo1", texto1);
				mensaje = mensaje.replace("TextoCorreo2", texto2);
				mensaje = mensaje.replace("TextoCorreo3", texto3);
				mensaje = mensaje.replace("TextoCorreo4", texto4);
				mensaje = mensaje.replace("TextoCorreo5", texto5);
				mensaje = mensaje.replace("TextoCorreo6", texto6);
				mensaje = mensaje.replace("TextoCorreo7", texto7);
				
				
				/**********************************************************************/
				/****							enviando correo						***/
				/**********************************************************************/
				
				enviarCorreoCoordinadorSinada(titulo,mensaje);
			
				/**********************************************************************/
				/****						fin	enviando correo						***/
				/**********************************************************************/
				
				if(prmEntidadDenuncia!=null){
					List<BandejaDetalle> lstBandejaDetalle = null;
					BandejaDetalle prmBandejaDetalle = new BandejaDetalle();
					prmBandejaDetalle.setIdDenuncia(prmEntidadDenuncia.getIdDenuncia());
					lstBandejaDetalle = bandejaDetalleService.buscarEspecialistaSinada(prmBandejaDetalle);
					
	/*****************************************************************************************/
	/****																				  ****/
	/*****************************************************************************************/
					
					if(lstBandejaDetalle!= null && lstBandejaDetalle.size()>0){
						
						for (BandejaDetalle bandejaDetalle : lstBandejaDetalle) {
							Bandeja bandeja = new Bandeja();
							bandeja = bandejaService.buscarPorId(bandejaDetalle.getIdBandeja());
							List<ContactoPersona> lstContactoPersona = null;
							if(bandeja!=null){
								
								ContactoPersona contactoPersona = new ContactoPersona();
								
								contactoPersona.setTipoPersona(bandeja.getTipoResponsable());
								contactoPersona.setIdPersona(bandeja.getIdResponsable());
							
								lstContactoPersona = (List<ContactoPersona>) contactoPersonaService.listar(contactoPersona);
								
								for (ContactoPersona contacto : lstContactoPersona) {
									
									if(contacto.getTipoContacto()==2){
										
										EmailAttachmentSender.sendEmailWithAttachments(contacto.getValor(), titulo , mensaje, null);
										
										
									}
								
								}
								
							}
					
							
						
							
						
							
						}
						
					}
					
					
					/*****************************************************************************************/
					/****																				  ****/
					/*****************************************************************************************/
//					if(lstBandejaDetalle!=null && lstBandejaDetalle.size()>0){
//						
//						for (BandejaDetalle bandejaDetalle : lstBandejaDetalle) {
//							Notificaciones prmNotificacion = new Notificaciones();
//							prmNotificacion.setIdBandeja(bandejaDetalle.getIdBandeja());
//							prmNotificacion.setDescripcionNotificacion(prmEntidadDenuncia.getNombreEntidad() + " ha aceptado la denuncia Nº " + prmEntidadDenuncia.getCodigoDenuncia());
//							
//							prmNotificacion.setPrm1(prmData);
//							notificacionService.insertar(prmNotificacion);
//							
//						}
//						
//					}
					
					
				}
			/***********************************************************************************/
			}
			
			
			/*******************************************************************/
			
//			ContactoPersona contactoPersona = new ContactoPersona();
//			contactoPersona.setTipoPersona(lstDenunciante.get(i).getTipoPersona());
//			contactoPersona.setIdPersona(lstDenunciante.get(i).getIdPersona());
//			List<ContactoPersona> lstContactoPersona = null;
//			lstContactoPersona = (List<ContactoPersona>) contactoPersonaService.listar(contactoPersona);
//			
//			for (ContactoPersona contacto : lstContactoPersona) {
//				
//				if(contacto.getTipoContacto()==2){
//					FormatoCorreo formatoCorreo = new FormatoCorreo();
//					long idFormatoCorreo = 1;
//					formatoCorreo = formatoCorreoService.buscarPorId(idFormatoCorreo);
//					
//					String texto1 = "";
//					String texto2 = "";
//					String texto3 = "";
//					String texto4 = "";
//					
//					String mensaje = formatoCorreo.getMensaje();
//					if(lstDenunciante.get(i).getTipoPersona()==1){
//						texto1 ="Sr(a): " + lstDenunciante.get(i).getPersonaDenunciante().getPrimerNombre() + " " + lstDenunciante.get(i).getPersonaDenunciante().getSegundoNombre()+" " + lstDenunciante.get(i).getPersonaDenunciante().getApellidoPaterno() + " "+lstDenunciante.get(i).getPersonaDenunciante().getApellidoMaterno();
//					}else{
//						texto1 ="Sres. " +lstDenunciante.get(i).getEntidadDenunciante().getRazonSocial();
//					}
//					texto2 ="Ha registrado con exito su denuncia ambiental";
//					texto3 = "Codigo Denuncia 	 :" + prmDenuncia.getCodigoDenuncia();
//					texto4 = "Codigo Acceso :" + prmDenuncia.getCodigoAcceso();
//					mensaje = mensaje.replace("TextoCorreo1", texto1);
//					mensaje = mensaje.replace("TextoCorreo2", texto2);
//					mensaje = mensaje.replace("TextoCorreo3", texto3);
//					mensaje = mensaje.replace("TextoCorreo4", texto4);
//					mensaje = mensaje.replace("TextoCorreo5", "");
//					mensaje = mensaje.replace("TextoCorreo6", "");
//					mensaje = mensaje.replace("TextoCorreo7", "");
//					
//					EmailAttachmentSender.sendEmailWithAttachments(contacto.getValor(),  "Registro Denuncia "+prmDenuncia.getCodigoDenuncia(), mensaje, null);
//					
//					
//				}
//			
//			}
			
			
			/************************************************************************/
	
		/*	if(prmData.getTipoAtencion().getCodigoRegistro()==1){
				Denuncia prmDenuncia = new Denuncia();
				prmDenuncia.setIdDenuncia(prmData.getDenuncia().getIdDenuncia());
				prmDenuncia.setEstado(2);
//				denunciaService.cambiarEstado(prmDenuncia);
			}
			
			if(prmData.getTipoAtencion().getCodigoRegistro()==2){
				Denuncia prmDenuncia = new Denuncia();
				prmDenuncia.setIdDenuncia(prmData.getDenuncia().getIdDenuncia());
				prmDenuncia.setEstado(3);
//				denunciaService.cambiarEstado(prmDenuncia);
			}
			
			
			if(prmData.getTipoAtencion().getCodigoRegistro()==3){
				Denuncia prmDenuncia = new Denuncia();
				prmDenuncia.setIdDenuncia(prmData.getDenuncia().getIdDenuncia());
				prmDenuncia.setEstado(5);
//				denunciaService.cambiarEstado(prmDenuncia);
			}*/
			
			if(idGenerado>0)
			{
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha registrado la Información satisfactoriamente");
				respuesta.setData(prmData.getIdAtencionDenuncia());
			} 
			else 
			{
				respuesta.setMensaje("No se pudo registrar la información");
			}
		} 
		catch (Exception e) 
		{
			logger.error(this.logBase+ " : registrar" + e.getMessage());
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al registar la información");
		}
		return respuesta;
	}
	
	public int enviarCorreoCoordinadorSinada(String asunto,String mensaje) throws AddressException, MessagingException, ServicioException{
		
		int res = 0;
		
		Usuario prmUsuario = new Usuario();
		String idPerfilCoordinadorSinada = ResourceUtil
				.getKey("IdPerfilSSO_Coordinador");
		prmUsuario.setIdPerfil(Long.valueOf(idPerfilCoordinadorSinada));

		List<Usuario> lstUsuario = null;
		lstUsuario = (List<Usuario>) usuarioService.listar(prmUsuario);

		for (Usuario usuario : lstUsuario) {
		
			ContactoPersona contactoPersona = new ContactoPersona();
			List<ContactoPersona> lstContactoPersona = null;
			contactoPersona.setTipoPersona(1);
			contactoPersona.setIdPersona(usuario.getIdPersona());
			lstContactoPersona = (List<ContactoPersona>) contactoPersonaService
					.listar(contactoPersona);


			FormatoCorreo formatoCorreoBD = new FormatoCorreo();
			long idFormatoCorreo = 1;
			formatoCorreoBD = formatoCorreoService.buscarPorId(idFormatoCorreo);
			
			for (ContactoPersona contacto : lstContactoPersona) {

				if (contacto.getTipoContacto() == 2) {
					FormatoCorreo formatoCorreo = new FormatoCorreo();
					formatoCorreo = formatoCorreoBD;

					String texto1 = "";
					String texto2 = "";
					String texto3 = "";
					String texto4 = "";

					try {
						
						EmailAttachmentSender.sendEmailWithAttachments(
								contacto.getValor(),
								asunto,
								mensaje, null);
						res = 1;
					} catch (Exception e) {
						res = 0;
						// TODO: handle exception
					}
					//Identificar Denuncia Anonima
				
					


				}

			}
		}
		return res;
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
	@RequestMapping(value = "/no-atencion-denuncia",method = RequestMethod.POST)
	public RespuestaHttp noAtencionDenuncia(
			@RequestParam("file")MultipartFile[] archivo,
			@RequestParam("strContenido")String contenido,
			HttpServletRequest request
			)//throws Exception
	{
		RespuestaHttp respuesta = new RespuestaHttp();
		GenericController gn = new GenericController();
		try
		{
//			logger.error(this.logBase+ " : ------------------------------- contenido: " + contenido +"\n ----------------------------------");
			AtencionDenuncia prmData = new ObjectMapper().readValue(contenido, AtencionDenuncia.class);
			
/****************************************************************/
			int flagMetodoProceso=0;
			
			String flagAlfresco = ResourceUtil.getKey("file.alfreso"); //1
			
			List<String> lstArchivoGenericoAlfresco = new ArrayList<String>();
//			logger.error(this.logBase+ " : flagAlfresco: " + flagAlfresco +"\n");
//			logger.error(this.logBase+ " : archivo: " + archivo == null?"-": archivo.length +"\n");
			if(archivo!=null && archivo.length!=0 && flagAlfresco.equals("1")){
				
				/********* Ruta ****************/
				String folderGeneric = ResourceUtil.getKey("file.path.informe-atencion");
				folderGeneric = folderGeneric.replace("{id}", String.valueOf(0));
//				logger.error(this.logBase+ " : folderGeneric: " + folderGeneric +"\n");
				/********* Ruta ****************/
				lstArchivoGenericoAlfresco =	 genericArchivoAlfresco(archivo,folderGeneric);
//				logger.error(this.logBase+ " : lstArchivoGenericoAlfresco: " + lstArchivoGenericoAlfresco +"\n");
				if(lstArchivoGenericoAlfresco==null || lstArchivoGenericoAlfresco.size()==0){
					RespuestaHttp respuestaAlfresco = new RespuestaHttp();
					respuestaAlfresco.setMensaje("Hubo un error al registrar el archivo, intentelo mas tarde.");
					flagMetodoProceso=1;
//					logger.error(this.logBase+ " : flagMetodoProceso: " + flagMetodoProceso +"\n");
//					logger.info(this.logBase+"respuesta" +respuestaAlfresco+"\n");
					return respuestaAlfresco;
					
				}
				logger.error(this.logBase+ " : paso metodo - no retorno: " );
				
			}
			
			/*********************************************************************/
			long idGenerado = 0;
			logger.error(this.logBase+ " paso return y ahora muestra  - flagMetodoProceso : "+ flagMetodoProceso);
			if(flagMetodoProceso==0){
				logger.error(this.logBase+ " ultimo log  : ");
				prmData.setHostIp(UtilRequest.getClientIpAddress(request));
				idGenerado = atencionDenunciaService.insertar(prmData);
				
				
				BandejaDetalle prmBandejaDetalleAc = new BandejaDetalle();
				prmBandejaDetalleAc.setIdBandejaDetalle(prmData.getDetalleBandeja().getIdBandejaDetalle());
				prmBandejaDetalleAc.setEstado(9);
				prmBandejaDetalleAc.setHostIp(UtilRequest.getClientIpAddress(request));
				prmBandejaDetalleAc.setPrm1(prmData.getPrm1());
				bandejaDetalleService.actualizar(prmBandejaDetalleAc);
			
		/**************************************************************************************************/		
		/***																						    ***/
		/**************************************************************************************************/
				
				if(prmData.getTipoAtencion().getCodigoRegistro()==4){
					
//					denunciaService.cambiarEstado(prmDenuncia);
					int x=0;
					for (ArchivoAtencion dataArchivo : prmData.getLstArchivoAtencion()) {
						
						String tmpNombreArchivo = CodeUtil.parseEncode(archivo[dataArchivo.getPosicionArchivo()].getOriginalFilename()); 
						
						
						String folder = ResourceUtil.getKey("file.path.informe-atencion");
						folder = folder.replace("{id}", String.valueOf(idGenerado));
						
						

		/*****************************************************************************************************/
		/***																							   ***/				
		/*****************************************************************************************************/	
						
						
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
						
						
						
		/******************************************************************************************************/
		/***																								***/				
		/******************************************************************************************************/				
						
						
						dataArchivo.setTipoTabla(1);
						dataArchivo.setNombreArchivo(tmpNombreArchivo);
						dataArchivo.setRutaArchivo(folder);
					
						dataArchivo.setMimeTypeArchivo(archivo[dataArchivo.getPosicionArchivo()].getContentType());
						dataArchivo.setDescripcionArchivo(CodeUtil.parseEncode(dataArchivo.getDescripcionArchivo()));
						dataArchivo.setIdAtencionDenuncia(idGenerado);
						archivoAtencionService.insertar(dataArchivo);
						
						x=x+1;
					}
				
					/*******************************************************************/
					
					
					
					/**********************************************************************/
					/*****   enviar correo oefa / efa ********************/
					/*********************************************************************/
					
					String asunto = " se informó la no atención de ";
					enviarCorreo(prmData.getDetalleBandeja().getIdBandejaDetalle(),asunto,"","","","","","","");
					
					

//					
					
					/************************************************************************/
					


				/********************** Fin  Registrar Archivos Denuncia *************************/
					
					
					/***********************************************************************************/
					/***			VERIFICAR SI LA DENUNCIA VA A PASAR A NO ATENDIDA				  */				
					/***********************************************************************************/	
				BandejaDetalle prmDataBandejaDetalle = 	bandejaDetalleService.buscarPorId(prmData.getDetalleBandeja().getIdBandejaDetalle());
									DenunciaEstado prmDenunciaEstado = new DenunciaEstado();
								
									prmDenunciaEstado =	denunciaEstadoService.buscarPorId(prmDataBandejaDetalle.getIdDenuncia());
									
									if(prmDenunciaEstado!=null){
										if(prmDenunciaEstado.getTotalAtenciones()==prmDenunciaEstado.getDenunciasNoAtendidas()){
											
											Denuncia prmDenuncia = new Denuncia();
											prmDenuncia.setIdDenuncia(prmDenunciaEstado.getIdDenuncia());
											prmDenuncia.setEstado(9);
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
			}
			
		
			
			
			if(idGenerado>0)
			{
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha registrado la información satisfactoriamente");
				respuesta.setData(prmData.getIdAtencionDenuncia());
			} 
			else 
			{
				respuesta.setMensaje("No se pudo registrar la información");
			}
		} 
		catch (Exception e) 
		{
			logger.error(this.logBase+ " : noAtencionDenuncia" + e.getMessage());
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al registrar la información");
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
					String titulo =prmEntidadDenuncia.getNombreEntidad()  +  asunto +" la denuncia  "+prmEntidadDenuncia.getCodigoDenuncia();
					
					
					
					String mensaje = formatoCorreo.getMensaje();
					
					texto1="Se generó el informe de no atención de la denuncia " + prmEntidadDenuncia.getCodigoDenuncia();
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
							String titulo =prmEntidadDenuncia.getNombreEntidad()  + asunto +" la denuncia "+prmEntidadDenuncia.getCodigoDenuncia();
							
							
							String mensaje = formatoCorreo.getMensaje();
							
							texto1="Se generó el informe de no atención de la denuncia " + prmEntidadDenuncia.getCodigoDenuncia();
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
	
	@ResponseBody
	@RequestMapping(value = "/rechazarDenuncia",method = RequestMethod.POST)
	public RespuestaHttp rechazarDenuncia(
			@RequestParam("file")MultipartFile[] archivo,
			@RequestParam("strContenido")String contenido,
			HttpServletRequest request
			)throws Exception
	{
		RespuestaHttp respuesta = new RespuestaHttp();
		GenericController gn = new GenericController();
		AtencionDenuncia prmData = new ObjectMapper().readValue(contenido, AtencionDenuncia.class);
		
		
		try
		{
			
/****************************************************************/
			int flagMetodoProceso = 0;
			
			String flagAlfresco = ResourceUtil.getKey("file.alfreso");
			List<String> lstArchivoGenericoAlfresco = new ArrayList<String>();
			if(archivo!=null && archivo.length!=0 && !flagAlfresco.equals("0")){
				/********* Ruta ****************/
				String folderGeneric = ResourceUtil.getKey("file.path.informe-atencion");
				folderGeneric = folderGeneric.replace("{id}", String.valueOf(0));
				/********* Ruta ****************/
				lstArchivoGenericoAlfresco = genericArchivoAlfresco(archivo,folderGeneric);
				
				if(lstArchivoGenericoAlfresco==null || lstArchivoGenericoAlfresco.size()==0){
					flagMetodoProceso = 1;
					respuesta.setMensaje("Hubo un error al registrar el archivo, intentelo mas tarde.");
					return respuesta;
					
				}
				
			}
			
			long idGenerado = 0;
			if(flagMetodoProceso==0){
				/*********************************************************************/
				
				prmData.setHostIp(UtilRequest.getClientIpAddress(request));
		
				
				
				 idGenerado = atencionDenunciaService.insertar(prmData);
		/********************** Registrar Archivos Denuncia *************************/
				
				int x =0;
				for (MultipartFile prmArchivo : archivo) {
					
					ArchivoAtencion dataArchivo = new  ArchivoAtencion();
					String tmpNombreArchivo = prmArchivo.getOriginalFilename(); 
					
					
					String folder = ResourceUtil.getKey("file.path.informe-atencion");
					folder = folder.replace("{id}", String.valueOf(idGenerado));
					
					
		/*****************************************************************************************************/
		/***																							   ***/				
		/*****************************************************************************************************/	
			
					
//					String flagAlfresco = ResourceUtil.getKey("file.alfreso");
					if(!flagAlfresco.equals("0")){
						
//					  	/** EL SIGUIENTE CODIGO ES LA INTEGRACION CON WSALFRESCO **/
//						String uiid = AlfrescoUtil.grabarArchivoAlfresco(prmArchivo, folder);
//												
//						if (VO.isNullOrEmpty(uiid)) {
//							respuesta.setMensaje("No se pudo archivar el documento en alfresco.");
//							return respuesta;
//						}
//						
//						dataArchivo.setUiid(uiid);
						
						dataArchivo.setUiid(lstArchivoGenericoAlfresco.get(x));
						
						
					}else{
						
						String rutaArchivoCopiado = gn.copiarArchivo(
								tmpNombreArchivo, 
								folder, 
								prmArchivo.getInputStream());
						
						if (VO.isNullOrEmpty(rutaArchivoCopiado)) {
							respuesta.setMensaje("No se pudo archivar el documento.");
							return respuesta;
						}
						
					}
					
		/*****************************************************************************************************/
		/***																							   ***/				
		/*****************************************************************************************************/					
					
					
				
				
					dataArchivo.setTipoTabla(1);
					dataArchivo.setNombreArchivo(tmpNombreArchivo);
					dataArchivo.setRutaArchivo(folder);
					dataArchivo.setMimeTypeArchivo(prmArchivo.getContentType());
					dataArchivo.setIdAtencionDenuncia(idGenerado);
					archivoAtencionService.insertar(dataArchivo);
					x=x+1;
				}
			
				/********************************************************************/
				/*****															*****/
				/********************************************************************/
					EntidadDenuncia prmEntidadDenuncia = new EntidadDenuncia();
					prmEntidadDenuncia.setIdBandejaDetalle(prmData.getDetalleBandeja().getIdBandejaDetalle());
					prmEntidadDenuncia = 	entidadDenunciaService.buscarPorId(prmEntidadDenuncia.getIdBandejaDetalle());
						
					if(prmEntidadDenuncia!=null){
						/********************************************************************/
						/*****															*****/
						/********************************************************************/
						Maestro maestro=new Maestro();
						maestro.setCodigoMaestro("MotivoRechazoDenuncia");
						List<Maestro> lstMotivoAtencionDenuncia = (List<Maestro>) maestroService.buscarPorCodigoTabla(maestro);
						Maestro motivoMaestro = new Maestro();
						for (Maestro maestroMotivoRechazo : lstMotivoAtencionDenuncia) {
							
							if(maestroMotivoRechazo.getCodigoRegistro()==prmData.getMotivo().getCodigoRegistro()){
								motivoMaestro = maestroMotivoRechazo;
							}
							
						}
						
						
						//MotivoRechazoDenuncia
						FormatoCorreo formatoCorreo = new FormatoCorreo();
						long idFormatoCorreo = 1;
						formatoCorreo = formatoCorreoService.buscarPorId(idFormatoCorreo);
						String titulo = "La denuncia "+prmEntidadDenuncia.getCodigoDenuncia()+" ha sido rechazada";
						
						
						
						String texto1 = "";
						String texto2 = "";
						String texto3 = "";
						String texto4 = "";
						String texto5 = "";
						String texto6 = "";
						String texto7 = "";
						
						String mensaje = formatoCorreo.getMensaje();
						
						texto1 ="La entidad " + prmEntidadDenuncia.getNombreEntidad() + " ha rechazado la denuncia " + prmEntidadDenuncia.getCodigoDenuncia()+"." ;
						if(motivoMaestro.getDescripcion()!=null){
							texto2 = "<b>Motivo: </b> " + motivoMaestro.getDescripcion();
						}
						
						
					
						mensaje = mensaje.replace("TextoCorreo1", texto1);
						mensaje = mensaje.replace("TextoCorreo2", texto2);
						mensaje = mensaje.replace("TextoCorreo3", texto3);
						mensaje = mensaje.replace("TextoCorreo4", texto4);
						mensaje = mensaje.replace("TextoCorreo5", texto5);
						mensaje = mensaje.replace("TextoCorreo6", texto6);
						mensaje = mensaje.replace("TextoCorreo7", texto7);
						
						/********************************************************************/
						/*****															*****/
						/********************************************************************/
						
						
						enviarCorreoCoordinadorSinada(titulo, mensaje);
						
						/***********************************************************************/
						
						
						List<BandejaDetalle> lstBandejaDetalle = null;
						BandejaDetalle prmBandejaDetalle = new BandejaDetalle();
						prmBandejaDetalle.setIdDenuncia(prmEntidadDenuncia.getIdDenuncia());
						lstBandejaDetalle = bandejaDetalleService.buscarEspecialistaSinada(prmBandejaDetalle);
						if(lstBandejaDetalle!=null && lstBandejaDetalle.size()>0){
							
							for (BandejaDetalle bandejaDetalle : lstBandejaDetalle) {
								Bandeja bandeja = new Bandeja();
								bandeja = bandejaService.buscarPorId(bandejaDetalle.getIdBandeja());
								List<ContactoPersona> lstContactoPersona = null;
								if(bandeja!=null){
									
									ContactoPersona contactoPersona = new ContactoPersona();
									
									contactoPersona.setTipoPersona(bandeja.getTipoResponsable());
									contactoPersona.setIdPersona(bandeja.getIdResponsable());
								
									lstContactoPersona = (List<ContactoPersona>) contactoPersonaService.listar(contactoPersona);
									
									for (ContactoPersona contacto : lstContactoPersona) {
										
										if(contacto.getTipoContacto()==2){
											
											EmailAttachmentSender.sendEmailWithAttachments(contacto.getValor(), prmEntidadDenuncia.getNombreEntidad() + titulo +prmEntidadDenuncia.getCodigoDenuncia(), mensaje, null);
											
											
										}
									
									}
									
								}
						
								
							
								
							
								
							}
							
						}
						
						
					}
			}
			
			
			/***********************************************************************************/
			
//			for (ArchivoAtencion dataArchivo : prmData.getLstArchivoAtencion()) {
//				
//				String tmpNombreArchivo = archivo[dataArchivo.getPosicionArchivo()].getOriginalFilename(); 
//				
//				
//				String folder = ResourceUtil.getKey("file.path.informe-atencion");
//				folder = folder.replace("{id}", String.valueOf(idGenerado));
//				String rutaArchivoCopiado = gn.copiarArchivo(
//						tmpNombreArchivo, 
//						folder, 
//						archivo[dataArchivo.getPosicionArchivo()].getInputStream());
//				
//				if (VO.isNullOrEmpty(rutaArchivoCopiado)) {
//					respuesta.setMensaje("No se pudo archivar el documento.");
//					return respuesta;
//				}
//
//				dataArchivo.setNombreArchivo(tmpNombreArchivo);
//				dataArchivo.setRutaArchivo(rutaArchivoCopiado);
//				dataArchivo.setMimeTypeArchivo(archivo[dataArchivo.getPosicionArchivo()].getContentType());
//				
//				archivoAtencionService.insertar(dataArchivo);
//				
//			}
		
				

			/********************** Fin  Registrar Archivos Denuncia *************************/
			
			
					
			/*
			
			if(prmData.getTipoAtencion().getCodigoRegistro()==1){
				Denuncia prmDenuncia = new Denuncia();
				prmDenuncia.setIdDenuncia(prmData.getDenuncia().getIdDenuncia());
				prmDenuncia.setEstado(2);
				denunciaService.cambiarEstado(prmDenuncia);
			}
			
			if(prmData.getTipoAtencion().getCodigoRegistro()==2){
				Denuncia prmDenuncia = new Denuncia();
				prmDenuncia.setIdDenuncia(prmData.getDenuncia().getIdDenuncia());
				prmDenuncia.setEstado(3);
				denunciaService.cambiarEstado(prmDenuncia);
			}
			
			
			if(prmData.getTipoAtencion().getCodigoRegistro()==3){
				Denuncia prmDenuncia = new Denuncia();
				prmDenuncia.setIdDenuncia(prmData.getDenuncia().getIdDenuncia());
				prmDenuncia.setEstado(5);
				denunciaService.cambiarEstado(prmDenuncia);
			}
			*/
			if(idGenerado>0)
			{
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha registrado la información satisfactoriamente");
				respuesta.setData(prmData.getIdAtencionDenuncia());
			} 
			else 
			{
				respuesta.setMensaje("No se pudo registrar la información");
			}
		} 
		catch (Exception e) 
		{
			logger.error(this.logBase+ " : rechazarDenuncia" + e.getMessage());
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al registar la información");
		}
		return respuesta;
	}
	
	
	
	@ResponseBody
	@RequestMapping(value ="/actualizar", method = RequestMethod.POST)
	public  RespuestaHttp actualizar(@RequestBody AtencionDenuncia prmData,HttpServletRequest request)throws Exception {
		RespuestaHttp respuesta = new RespuestaHttp();
		try{
			int result = atencionDenunciaService.actualizar(prmData);
			
		
			
			
			if(result>0){
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha actualizado	la atención de la denuncia satisfactoriamente");
			}else {
				respuesta.setMensaje("No se pudo actualizar la atención de la denuncia");
			}
		}catch (Exception e) {
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al actualizar la atención de la denuncia");
		}
		return respuesta;
	}
	
	@ResponseBody
	@RequestMapping(value = "/eliminar", method = RequestMethod.POST)
	public RespuestaHttp eliminar(
			@RequestBody AtencionDenuncia prmData,HttpServletRequest request)
			throws Exception{
		RespuestaHttp respuesta = new RespuestaHttp();
		try{
			
			prmData.setHostIp(UtilRequest.getClientIpAddress(request));
			int result = atencionDenunciaService.eliminar(prmData);
			if(result>0){
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha eliminado la información correctamente");
			}else {
				respuesta.setMensaje("No se pudo eliminar la información");
			}
		} catch (Exception e){
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al eliminar la información");
		}
		return respuesta;
	}
	
	@ResponseBody
	@RequestMapping(value = "/buscarPorId",method = RequestMethod.GET)
	public RespuestaHttp buscarxNumeroNorma(@RequestParam(value="prmCriterio",required=false)String prmCriterio)throws Exception 
	{
		RespuestaHttp respuesta = new  RespuestaHttp();
		try
		{
			AtencionDenuncia prmData = new ObjectMapper().readValue(prmCriterio, AtencionDenuncia.class);
			
			prmData = atencionDenunciaService.buscarPorId(prmData.getIdAtencionDenuncia());
			
		
			
			respuesta.setValido(true);
			respuesta.setData(prmData);
		} 
		catch (Exception e)
		{
			respuesta.setMensaje("Hubo un error al obtener la información");
			e.printStackTrace();
		}
		return respuesta;
	}
	
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/buscar-denuncia-rechazada", method=RequestMethod.POST)
	public RespuestaHttp buscarDenunciaRechazada(@RequestBody AtencionDenuncia prmData,HttpServletRequest request)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try
		{
//			AtencionDenuncia prmData = new ObjectMapper().readValue(prmCriterio, AtencionDenuncia.class);
			List<AtencionDenuncia> data = (List<AtencionDenuncia>) atencionDenunciaService.buscarAtencionDenunciaRechazada(prmData);
			
			if(data!=null && data.size()>0){
				
				for (int i = 0 ; i<data.size();i++) {
					ArchivoAtencion prmArchivoAtencion = new ArchivoAtencion();
					prmArchivoAtencion.setIdAtencionDenuncia(data.get(i).getIdAtencionDenuncia());
					prmArchivoAtencion.setTipoTabla(1);
					List<ArchivoAtencion> lst = (List<ArchivoAtencion>) archivoAtencionService.listar(prmArchivoAtencion);
					
					if(lst!=null && lst.size()>0){
						data.get(i).getLstArchivoAtencion().addAll(lst);
						
					}
				}
				
			}
			
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
	@RequestMapping(value="/listarAccionPorIdBandejaDetalle", method=RequestMethod.GET)
	public RespuestaHttp listarAccionPorIdBandejaDetalle(@RequestParam(value="prmCriterio",required=false)String prmCriterio)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try
		{
			AccionesRealizadas prmData = new ObjectMapper().readValue(prmCriterio, AccionesRealizadas.class);
			List<AccionesRealizadas> data = (List<AccionesRealizadas>) accionesRealizadasService.listarAccionesPorIdBandeja(prmData);
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
	@RequestMapping(value="/listarAccionPorIdDenuncia", method=RequestMethod.GET)
	public RespuestaHttp listarAccionPorIdDenuncia(@RequestParam(value="prmCriterio",required=false)String prmCriterio)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try
		{
			AccionesRealizadas prmData = new ObjectMapper().readValue(prmCriterio, AccionesRealizadas.class);
			List<AccionesRealizadas> data = (List<AccionesRealizadas>) accionesRealizadasService.listar(prmData);
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


}
