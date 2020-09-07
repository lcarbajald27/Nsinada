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
import pe.gob.oefa.apps.sinada.bean.proceso.Bandeja;
import pe.gob.oefa.apps.sinada.bean.proceso.BandejaDetalle;
import pe.gob.oefa.apps.sinada.bean.proceso.InformeAccion;
import pe.gob.oefa.apps.sinada.bean.proceso.view.AccionesRealizadas;
import pe.gob.oefa.apps.sinada.bean.proceso.view.EntidadDenuncia;
import pe.gob.oefa.apps.sinada.bean.seguridad.Usuario;
import pe.gob.oefa.apps.sinada.presentacion.rest.api.bas.GenericController;
import pe.gob.oefa.apps.sinada.presentacion.util.AlfrescoUtil;
import pe.gob.oefa.apps.sinada.presentacion.util.ResourceUtil;
import pe.gob.oefa.apps.sinada.presentacion.util.VO;
import pe.gob.oefa.apps.sinada.servicio.inf.general.ContactoPersonaService;
import pe.gob.oefa.apps.sinada.servicio.inf.maestros.FormatoCorreoService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.ArchivoAtencionService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.BandejaDetalleService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.BandejaService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.DenunciaService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.InformeAccionService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.NotificacionesService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.view.AccionesRealizadasService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.view.EntidadDenunciaService;
//import sun.org.mozilla.javascript.internal.debug.Debugger;


import pe.gob.oefa.apps.sinada.servicio.inf.seguridad.UsuarioService;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value="/rest/api/informe-accion")
public class InformeAccionRest
{
//	@Autowired
//	AtencionDenunciaService informeAccionService;
	
	@Autowired
	InformeAccionService informeAccionService;
	
	@Autowired
	DenunciaService denunciaService;
	
//	@Autowired
//	ArchivoInformeService archivoInformeService;
	
	@Autowired
	ArchivoAtencionService archivoAtencionService;
	
	@Autowired
	private EntidadDenunciaService entidadDenunciaService;
	
	@Autowired
	private BandejaDetalleService bandejaDetalleService;
	
	@Autowired
	private NotificacionesService notificacionService;
	
	@Autowired
	private BandejaService bandejaService;
	
	@Autowired
	private ContactoPersonaService contactoPersonaService;
	
	@Autowired FormatoCorreoService formatoCorreoService;
	
	@Autowired
	private AccionesRealizadasService accionesRealizadasService;
	
	@Autowired
	private UsuarioService usuarioService;
	
private String logBase = "oefa-sinada-web: InformeAccionRest";
	
	private final Logger logger = Logger.getLogger(this.getClass());
		
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/listar", method=RequestMethod.GET)
	public RespuestaHttp buscar(@RequestParam(value="prmCriterio",required=false)String prmCriterio)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try
		{
			InformeAccion prmData = new ObjectMapper().readValue(prmCriterio, InformeAccion.class);
			List<InformeAccion> data = (List<InformeAccion>) informeAccionService.listar(prmData);
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

	

	
//	@ResponseBody
//	@RequestMapping(value = "/registrar",method = RequestMethod.POST)
//	public RespuestaHttp registrar(@RequestBody InformeAccion prmData,HttpServletRequest request)throws Exception
//	{
//		RespuestaHttp respuesta = new RespuestaHttp();
//		try
//		{
//					
//			long idGenerado = informeAccionService.insertar(prmData);
//			
//			if(prmData.getTipoAtencion().getCodigoRegistro()==1){
//				Denuncia prmDenuncia = new Denuncia();
//				prmDenuncia.setIdDenuncia(prmData.getDenuncia().getIdDenuncia());
//				prmDenuncia.setEstado(2);
//				denunciaService.cambiarEstado(prmDenuncia);
//			}
//			
//			if(prmData.getTipoAtencion().getCodigoRegistro()==2){
//				Denuncia prmDenuncia = new Denuncia();
//				prmDenuncia.setIdDenuncia(prmData.getDenuncia().getIdDenuncia());
//				prmDenuncia.setEstado(3);
//				denunciaService.cambiarEstado(prmDenuncia);
//			}
//			
//			
//			if(prmData.getTipoAtencion().getCodigoRegistro()==3){
//				Denuncia prmDenuncia = new Denuncia();
//				prmDenuncia.setIdDenuncia(prmData.getDenuncia().getIdDenuncia());
//				prmDenuncia.setEstado(5);
//				denunciaService.cambiarEstado(prmDenuncia);
//			}
//			
//			if(idGenerado>0)
//			{
//				respuesta.setValido(true);
//				respuesta.setMensaje("Se ha registrado la Información satisfactoriamente");
//				respuesta.setData(prmData.getIdAtencionDenuncia());
//			} 
//			else 
//			{
//				respuesta.setMensaje("No se pudo registrar la Información");
//			}
//		} 
//		catch (Exception e) 
//		{
//			e.printStackTrace();
//			respuesta.setMensaje("Hubo un error al registrar la Información");
//		}
//		return respuesta;
//	}
	
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
	@RequestMapping(value = "/registrar",method = RequestMethod.POST)
	public RespuestaHttp registrar(
			@RequestParam("file")MultipartFile[] archivo,
			@RequestParam("strContenido")String contenido,
			HttpServletRequest request
			)throws Exception
	{
		RespuestaHttp respuesta = new RespuestaHttp();
		GenericController gn = new GenericController();
		InformeAccion prmData = new ObjectMapper().readValue(contenido, InformeAccion.class);
		
		
		try
		{
			
			
/****************************************************************/
			int flagMetodoProceso=0;
			
			String flagAlfresco = ResourceUtil.getKey("file.alfreso");
			List<String> lstArchivoGenericoAlfresco = new ArrayList<String>();
			if(archivo!=null && archivo.length!=0 && !flagAlfresco.equals("0")){
				/********* Ruta ****************/
				String folderGeneric = ResourceUtil.getKey("file.path.informe.alfresco.generico");
				
				/********* Ruta ****************/
				lstArchivoGenericoAlfresco =	 genericArchivoAlfresco(archivo,folderGeneric);
				
				if(lstArchivoGenericoAlfresco==null || lstArchivoGenericoAlfresco.size()==0){
					flagMetodoProceso = 1;
					respuesta.setMensaje("Hubo un error al registrar el archivo, intentelo mas tarde.");
					return respuesta;
					
				}
				
			}
			
			/*********************************************************************/
			long idGenerado = 0;
			
			if(flagMetodoProceso==0){
				prmData.setHostIp(UtilRequest.getClientIpAddress(request));
				
				String str=			prmData.getDescripcionAccion();
				System.out.println("A "+str);
				str= CodeUtil.parseEncode(str);
				System.out.println("B "+str);
				prmData.setDescripcionAccion(str);
				idGenerado = informeAccionService.insertar(prmData);
				
		/********************** Registrar Archivos Denuncia *************************/
				// Informe Accion = 4 en el tipo de tabla de T_GEND_ARCHIVO_ATENCION
				for (ArchivoAtencion prmArchivoInforme : prmData.getLstArchivoInformeAccion()) {
					
//					ArchivoInforme prmArchivoInforme = new ArchivoInforme();
					prmArchivoInforme.setIdAtencionDenuncia(idGenerado);
					String tmpNombreArchivo = archivo[prmArchivoInforme.getPosicionArchivo()].getOriginalFilename(); 
					String folder = "";
					String rutaArchivoCopiado = "";
					
					
					
					
					if(prmData.getTipoInforme().getCodigoRegistro()==1){
						
						 folder = ResourceUtil.getKey("file.path.informe-accion");
						folder = folder.replace("{id}", String.valueOf(idGenerado));
						
						
					}
					
					
					if(prmData.getTipoInforme().getCodigoRegistro()==2){
						
						 folder = ResourceUtil.getKey("file.path.informe-atencion");
						folder = folder.replace("{id}", String.valueOf(idGenerado));
					
						
					}
					
					
					if(prmData.getTipoInforme().getCodigoRegistro()==3){
						
						 folder = ResourceUtil.getKey("file.path.informe-accion");
						folder = folder.replace("{id}", String.valueOf(idGenerado));
					
						
					}
					
					
					if(prmData.getTipoInforme().getCodigoRegistro()==4){
						
						 folder = ResourceUtil.getKey("file.path.informe-atencion");
						folder = folder.replace("{id}", String.valueOf(idGenerado));
					
						
					}
					
					
					/*********************************************************************************************/
					/**																							**/
					/*********************************************************************************************/
					
//					String flagAlfresco = ResourceUtil.getKey("file.alfreso");
					if(!flagAlfresco.equals("0")){
						
//					  	/** EL SIGUIENTE CODIGO ES LA INTEGRACION CON WSALFRESCO **/
//						String uiid = AlfrescoUtil.grabarArchivoAlfresco(archivo[prmArchivoInforme.getPosicionArchivo()], folder);
//												
//						if (VO.isNullOrEmpty(uiid)) {
//							respuesta.setMensaje("No se pudo archivar el documento en alfresco.");
//							return respuesta;
//						}
//						
//						prmArchivoInforme.setUiid(uiid);
//						
//						
						prmArchivoInforme.setUiid(lstArchivoGenericoAlfresco.get(prmArchivoInforme.getPosicionArchivo()));
						
						
					}else{
						 rutaArchivoCopiado = gn.copiarArchivo(
									tmpNombreArchivo, 
									folder, 
									archivo[prmArchivoInforme.getPosicionArchivo()].getInputStream());
						
						
						if (VO.isNullOrEmpty(rutaArchivoCopiado)) {
							respuesta.setMensaje("No se pudo archivar el documento.");
							return respuesta;
						}
					}
					

					/***************** util decode****************/
				
					prmArchivoInforme.setNombreArchivo(CodeUtil.parseEncode(tmpNombreArchivo));
					prmArchivoInforme.setRutaArchivo(folder);
					prmArchivoInforme.setDescripcionArchivo(CodeUtil.parseEncode(prmArchivoInforme.getDescripcionArchivo()));
					prmArchivoInforme.setTipoTabla(4);
					prmArchivoInforme.setMimeTypeArchivo(archivo[prmArchivoInforme.getPosicionArchivo()].getContentType());
					
					/**********************************/
					archivoAtencionService.insertar(prmArchivoInforme);
					
				}
				if(prmData.getTipoInforme().getCodigoRegistro()==1 || prmData.getTipoInforme().getCodigoRegistro()==2){
					BandejaDetalle prmBandejaDetalle = new BandejaDetalle();
					prmBandejaDetalle.setIdBandejaDetalle(prmData.getDetalleBandeja().getIdBandejaDetalle());
					prmBandejaDetalle.setEstado(6);
					prmBandejaDetalle.setHostIp(UtilRequest.getClientIpAddress(request));
					prmBandejaDetalle.setPrm1(prmData.getPrm1());
					bandejaDetalleService.actualizar(prmBandejaDetalle);
					
				}
				
				
				if(prmData.getIdInformeCorregido()!=0){
					InformeAccion prmInformeAccion = new InformeAccion();
					prmInformeAccion.setIdInformeAccion(prmData.getIdInformeCorregido());
					prmInformeAccion.getEstado().setCodigoRegistro(4);
					prmInformeAccion.setHostIp(UtilRequest.getClientIpAddress(request));
					prmInformeAccion.setPrm1(prmData.getPrm1());
					informeAccionService.actualizar(prmInformeAccion);
					
				}
				
				
				
				/********************* Notificacion  *************************/
				
				
				
					/********************************************************************/
				
				/***********Busca Datos de la Entidad***********/
					EntidadDenuncia prmEntidadDenuncia = new EntidadDenuncia();
					prmEntidadDenuncia.setIdBandejaDetalle(prmData.getDetalleBandeja().getIdBandejaDetalle());
					prmEntidadDenuncia = 	entidadDenunciaService.buscarPorId(prmEntidadDenuncia.getIdBandejaDetalle());
					
					
					/***************************/
					AccionesRealizadas accionesRealizadas = new AccionesRealizadas();
					accionesRealizadas.setTipoTablaAccion(2);
					accionesRealizadas.setIdAccion(idGenerado);
					accionesRealizadas = accionesRealizadasService.buscarPorTipoTablaIdAccion(accionesRealizadas);
					/************/
					if(prmEntidadDenuncia!=null){
						
						
						/***************************************************************************/
						/**********               Obtener mensaje para correo              *********/
						/***************************************************************************/
						
						FormatoCorreo formatoCorreo = new FormatoCorreo();
						long idFormatoCorreo = 1;
						formatoCorreo = formatoCorreoService.buscarPorId(idFormatoCorreo);
						String titulo = "";
						String tipoAccionAtencion ="";
						if(	prmData.getIdInformeCorregido() ==0){	
							
							if(prmData.getTipoInforme().getCodigoRegistro()==1){
								
								 titulo = " Realizó informe acción de la  denuncia ";
								 tipoAccionAtencion ="Informe acción";
								}
								
								if(prmData.getTipoInforme().getCodigoRegistro()==2){
								 titulo = " Realizó informe atención de la  denuncia ";
								 tipoAccionAtencion ="Informe atención";
								}
							
						}
						if(	prmData.getIdInformeCorregido() !=0){
							
							if(prmData.getTipoInforme().getCodigoRegistro()==1){
								 titulo = " Se corrigió informe acción de la  denuncia ";
								 tipoAccionAtencion ="Informe acción";
							}
								
								if(prmData.getTipoInforme().getCodigoRegistro()==2){
									 titulo = " Se corrigió informe atención de la  denuncia ";
									 tipoAccionAtencion ="Informe atención";
								}
							
						}
						
						
						String texto1 = "";
						String texto2 = "";
						String texto3 = "";
						String texto4 = "";
						String texto5 = "";
						String texto6 = "";
						String texto7 = "";
						
						String mensaje = formatoCorreo.getMensaje();
						
						texto1 ="<center><h3> "+ tipoAccionAtencion+" denuncia "+prmEntidadDenuncia.getCodigoDenuncia()+"</h3></center>";
				
						
						texto2 ="Tipo Acción : "+accionesRealizadas.getNombreTipoAccion() ;
						
					if(prmData.getTipoAccion().getCodigoRegistro()==2){
						
						texto3 = "Tipo Supervición : " + accionesRealizadas.getNombreTipoSupervicion();
						texto4 = "Fecha Inicio : " + accionesRealizadas.getFechaInicio();
						texto5 = "Fecha Fin : " + accionesRealizadas.getFechaInicio();
						
					}
					
						texto6 = "Descripción de la acción :";
						texto7 = "" + accionesRealizadas.getDescripcionAccion();
						mensaje = mensaje.replace("TextoCorreo1", texto1);
						mensaje = mensaje.replace("TextoCorreo2", texto2);
						mensaje = mensaje.replace("TextoCorreo3", texto3);
						mensaje = mensaje.replace("TextoCorreo4", texto4);
						mensaje = mensaje.replace("TextoCorreo5", texto5);
						mensaje = mensaje.replace("TextoCorreo6", texto6);
						mensaje = mensaje.replace("TextoCorreo7", texto7);
						
						enviarCorreoCoordinadorSinada(prmEntidadDenuncia.getNombreEntidad() + titulo +prmEntidadDenuncia.getCodigoDenuncia(), mensaje);
						/****************************************************************************/
						/*****																    *****/
						/****************************************************************************/
						
							
						/******* Obtener Datos de los Especialistas Asignados a la Denuncia**********/
						List<BandejaDetalle> lstBandejaDetalle = null;
						BandejaDetalle prmBandejaDetalle = new BandejaDetalle();
						prmBandejaDetalle.setIdDenuncia(prmEntidadDenuncia.getIdDenuncia());
						lstBandejaDetalle = bandejaDetalleService.buscarEspecialistaSinada(prmBandejaDetalle);
						
						
						/********************/
						
						
						/************ Especialista Asignado****************/
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
			
			
			
//			if(prmData.getTipoInforme().getCodigoRegistro()==2){
//				/********************************************************************/
//				EntidadDenuncia prmEntidadDenuncia = new EntidadDenuncia();
//				prmEntidadDenuncia.setIdBandejaDetalle(prmData.getDetalleBandeja().getIdBandejaDetalle());
//				prmEntidadDenuncia = 	entidadDenunciaService.buscarPorId(prmEntidadDenuncia.getIdBandejaDetalle());
//					
//				if(prmEntidadDenuncia!=null){
//					List<BandejaDetalle> lstBandejaDetalle = null;
//					BandejaDetalle prmBandejaDetalle = new BandejaDetalle();
//					prmBandejaDetalle.setIdDenuncia(prmEntidadDenuncia.getIdDenuncia());
//					lstBandejaDetalle = bandejaDetalleService.buscarEspecialistaSinada(prmBandejaDetalle);
//					if(lstBandejaDetalle!=null && lstBandejaDetalle.size()>0){
//						
//						for (BandejaDetalle bandejaDetalle : lstBandejaDetalle) {
//							Notificaciones prmNotificacion = new Notificaciones();
//							prmNotificacion.setIdBandeja(bandejaDetalle.getIdBandeja());
//							prmNotificacion.setDescripcionNotificacion(prmEntidadDenuncia.getNombreEntidad() + " ha realizado una Atencion a la denuncia Nº " + prmEntidadDenuncia.getCodigoDenuncia());
//							notificacionService.insertar(prmNotificacion);
//							
//						}
//						
//					}
//					
//					
//				}
//			/***********************************************************************************/
//			}
			
			
			
			/***************************************************************/
		
//			for(int e = 0; e<archivo.length;e++){
//				
//				ArchivoInforme prmArchivoInforme = new ArchivoInforme();
//				prmArchivoInforme.getInformeAccion().setIdInformeAccion(idGenerado);
//				String tmpNombreArchivo = archivo[e].getOriginalFilename(); 
//				String folder = "";
//				String rutaArchivoCopiado = "";
//				if(prmData.getTipoInforme().getCodigoRegistro()==1){
//					
//					 folder = ResourceUtil.getKey("file.path.informe-accion");
//					folder = folder.replace("{id}", String.valueOf(idGenerado));
//					 rutaArchivoCopiado = gn.copiarArchivo(
//							tmpNombreArchivo, 
//							folder, 
//							archivo[e].getInputStream());
//					
//				}
//				
//				
//				if(prmData.getTipoInforme().getCodigoRegistro()==2){
//					
//					 folder = ResourceUtil.getKey("file.path.informe-atencion");
//					folder = folder.replace("{id}", String.valueOf(idGenerado));
//					 rutaArchivoCopiado = gn.copiarArchivo(
//							tmpNombreArchivo, 
//							folder, 
//							archivo[e].getInputStream());
//					
//				}
//				
//				
//				
//				
//				
//				if (VO.isNullOrEmpty(rutaArchivoCopiado)) {
//					respuesta.setMensaje("No se pudo archivar el documento.");
//					return respuesta;
//				}
//
//				prmArchivoInforme.setArchivoInforme(tmpNombreArchivo);
//				prmArchivoInforme.setRutaArchivoInforme(rutaArchivoCopiado);
//				
//				archivoInformeService.insertar(prmArchivoInforme);
//				
//			}
		
			/********************** Fin  Registrar Archivos Denuncia *************************/
			
			
//		if(prmData.getTipoInforme().getCodigoRegistro()==1 || prmData.getTipoInforme().getCodigoRegistro()==2){
//			Denuncia prmDenuncia = new Denuncia();
//			prmDenuncia.setIdDenuncia(prmData.getDenuncia().getIdDenuncia());
//			prmDenuncia.setEstado(6);
//			denunciaService.cambiarEstado(prmDenuncia);
//		}
			
		
			
		
			
			if(idGenerado>0)
			{
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha registrado la información satisfactoriamente");
				respuesta.setData(prmData.getIdInformeAccion());
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
			respuesta.setMensaje("Hubo un error al registrar la información");
		}
		return respuesta;
	}
	
	
	
	@ResponseBody
	@RequestMapping(value ="/actualizar", method = RequestMethod.POST)
	public  RespuestaHttp actualizar(@RequestBody InformeAccion prmData,HttpServletRequest request)throws Exception {
		RespuestaHttp respuesta = new RespuestaHttp();
		try{
			
			prmData.setHostIp(UtilRequest.getClientIpAddress(request));
	
			int result = informeAccionService.actualizar(prmData);
			
		
			
			
			if(result>0){
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha actualizado	el informe  satisfactoriamente");
			}else {
				respuesta.setMensaje("No se pudo actualizar el informe");
			}
		}catch (Exception e) {
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al actualizar el informe");
		}
		return respuesta;
	}
	
	@ResponseBody
	@RequestMapping(value = "/eliminar", method = RequestMethod.POST)
	public RespuestaHttp eliminar(
			@RequestBody InformeAccion prmData,HttpServletRequest request)
			throws Exception{
		RespuestaHttp respuesta = new RespuestaHttp();
		try{
			prmData.setHostIp(UtilRequest.getClientIpAddress(request));
			
			int result = informeAccionService.eliminar(prmData);
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
			InformeAccion prmData = new ObjectMapper().readValue(prmCriterio, InformeAccion.class);
			
			prmData = informeAccionService.buscarPorId(prmData.getIdInformeAccion());
			
		
			
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
	
	
	/*****************************  VISTA ENTIDAD DENUNCIA  *************************/

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/listar-entidad-denuncia", method=RequestMethod.GET)
	public RespuestaHttp listarEntidadDenuncia(@RequestParam(value="prmCriterio",required=false)String prmCriterio)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try
		{
			EntidadDenuncia prmData = new ObjectMapper().readValue(prmCriterio, EntidadDenuncia.class);
			List<EntidadDenuncia> data = (List<EntidadDenuncia>) entidadDenunciaService.listar(prmData);
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
