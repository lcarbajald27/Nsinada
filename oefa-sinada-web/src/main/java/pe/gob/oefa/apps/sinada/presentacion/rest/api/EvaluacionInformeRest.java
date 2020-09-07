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
import pe.gob.oefa.apps.sinada.bean.proceso.BandejaDetalle;
import pe.gob.oefa.apps.sinada.bean.proceso.Denuncia;
import pe.gob.oefa.apps.sinada.bean.proceso.EvaluacionInforme;
import pe.gob.oefa.apps.sinada.bean.proceso.InformeAccion;
import pe.gob.oefa.apps.sinada.bean.proceso.PersonaOefa;
import pe.gob.oefa.apps.sinada.bean.proceso.view.AccionesRealizadas;
import pe.gob.oefa.apps.sinada.bean.proceso.view.DenunciaEstado;
import pe.gob.oefa.apps.sinada.bean.proceso.view.DenuncianteDetalle;
import pe.gob.oefa.apps.sinada.bean.proceso.view.EntidadDenuncia;
import pe.gob.oefa.apps.sinada.bean.seguridad.Usuario;
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
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.DenuncianteService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.DerivacionDenunciaService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.EvaluacionInformeService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.InformeAccionService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.PersonaOefaService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.view.AccionesRealizadasService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.view.BandejaDenunciaService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.view.DenunciaEstadoService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.view.EntidadDenunciaService;
import pe.gob.oefa.apps.sinada.servicio.inf.seguridad.UsuarioService;
import pe.gob.oefa.apps.sinada.servicio.inf.sirefa.EfaService;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value="/rest/api/evaluacion-informe")
public class EvaluacionInformeRest
{
	@Autowired
	EvaluacionInformeService evaluacionInformeService;
	
	@Autowired
	AtencionDenunciaService atencionDenunciaService;
	
	@Autowired
	private ArchivoAtencionService archivoAtencionService;
	
	@Autowired
	private DerivacionDenunciaService derivacionDenunciaService;
	
	@Autowired
	private BandejaDetalleService bandejaDetalleService;
	
	@Autowired
	private InformeAccionService informeAccionService;
	
	
	@Autowired
	private BandejaService bandejaService;
	
	@Autowired
	private DenunciaEstadoService denunciaEstadoService;
	
	@Autowired
	private DenunciaService denunciaService;
	
	@Autowired
	private ContactoPersonaService contactoPersonaService;
	
	@Autowired
	private AccionesRealizadasService accionesRealizadasService;
	
	@Autowired
	private FormatoCorreoService formatoCorreoService;
	
	@Autowired
	private BandejaDenunciaService bandejaDenunciaService;
	
	@Autowired
	private EntidadDenunciaService entidadDenunciaService;
	
	@Autowired
	private PersonaOefaService personaOefaService;
	
	@Autowired 
	private EfaService efaService;
	
	@Autowired
	private DenuncianteService denuncianteService;
	
	@Autowired
	private UsuarioService usuarioService;
	
private String logBase = "oefa-sinada-web: BandejaRest";
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/listar", method=RequestMethod.GET)
	public RespuestaHttp buscar(@RequestParam(value="prmCriterio",required=false)String prmCriterio)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try
		{
			EvaluacionInforme prmData = new ObjectMapper().readValue(prmCriterio, EvaluacionInforme.class);
			List<EvaluacionInforme> data = (List<EvaluacionInforme>) evaluacionInformeService.listar(prmData);
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
	
	public List<String> genericArchivoAlfresco(MultipartFile[] multipartFileArray)
			throws Exception {
		try {
			List<String> lstUiidArchivo = new ArrayList<String>();

			for (MultipartFile objArchivo : multipartFileArray) {

				// Obtiene el nombre del archivo
				String tmpNombreArchivo = objArchivo.getOriginalFilename();

				String folder = ResourceUtil
						.getKey("file.path.informe-evaluacion-informe");
				folder = folder.replace("{id}", String.valueOf(0));

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
		
		// Tabla Evaluacion Informe = 3
		RespuestaHttp respuesta = new RespuestaHttp();
		
		GenericController gn = new GenericController();
		EvaluacionInforme prmData = new ObjectMapper().readValue(contenido, EvaluacionInforme.class);
		
		
		try
		{
			int flagMetodoProceso=0;
			
			String flagAlfresco = ResourceUtil.getKey("file.alfreso");
			List<String> lstArchivoGenericoAlfresco = new ArrayList<String>();
			if(archivo!=null && archivo.length!=0 && !flagAlfresco.equals("0")){
				
				lstArchivoGenericoAlfresco = genericArchivoAlfresco(archivo);
				
				if(lstArchivoGenericoAlfresco==null || lstArchivoGenericoAlfresco.size()==0){
					flagMetodoProceso = 1;
					respuesta.setMensaje("Hubo un error al registrar el archivo, intentelo mas tarde.");
					return respuesta;
					
				}
				
			}
			long idGenerado = 0;
			if(flagMetodoProceso==0){
				
				prmData.setHostIp(UtilRequest.getClientIpAddress(request));
				String str=			prmData.getMotivoDescripcion();
				System.out.println("A "+str);
				str= CodeUtil.parseEncode(str);
				System.out.println("B "+str);
				prmData.setMotivoDescripcion(str);
		
				idGenerado = evaluacionInformeService.insertar(prmData);
				
				if(prmData.getTipoInforme().getCodigoRegistro()==1){
					InformeAccion prmInformeAccion = new InformeAccion();
					prmInformeAccion.setIdInformeAccion(prmData.getInformeAccion().getIdInformeAccion());
					prmInformeAccion.getEstado().setCodigoRegistro(2);

					/******* Auditoria ******/
					
					prmInformeAccion.setHostIp(UtilRequest.getClientIpAddress(request));
					prmInformeAccion.setPrm1(prmData.getPrm1());
					/************************/
					informeAccionService.actualizar(prmInformeAccion);
					
					int x = 0;
					for (ArchivoAtencion dataArchivo : prmData.getLstArchivoAtencion()) {
						
						String tmpNombreArchivo = archivo[dataArchivo.getPosicionArchivo()].getOriginalFilename(); 
						
						
						String folder = ResourceUtil.getKey("file.path.informe-evaluacion-informe");
						folder = folder.replace("{id}", String.valueOf(idGenerado));
						
		/***********************************************************************************************/
		/**																							  **/				
		/***********************************************************************************************/				
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
						
						
						/***********************************************************************************************/
						/**																							  **/				
						/***********************************************************************************************/	
					
						dataArchivo.setTipoTabla(3);
						dataArchivo.setNombreArchivo(CodeUtil.parseEncode(tmpNombreArchivo));
						dataArchivo.setRutaArchivo(folder);
						dataArchivo.setDescripcionArchivo(CodeUtil.parseEncode(dataArchivo.getDescripcionArchivo()));
					    dataArchivo.setMimeTypeArchivo(archivo[dataArchivo.getPosicionArchivo()].getContentType());
						dataArchivo.setIdAtencionDenuncia(idGenerado);
						archivoAtencionService.insertar(dataArchivo);
						x =x+1;
					}
					
				}
				
//				if(prmData.getTipoInforme().getCodigoRegistro()==2){
//					AtencionDenuncia prmAtencionDenuncia = new AtencionDenuncia();
//					prmAtencionDenuncia.setIdAtencionDenuncia(prmData.getInformeAccion().getIdInformeAccion());
//					prmAtencionDenuncia.getEstado().setCodigoRegistro(3);
//					atencionDenunciaService.actualizar(prmAtencionDenuncia);
//					
//				}
				
				
				if(prmData.getTipoInforme().getCodigoRegistro()==2){
					InformeAccion prmInformeAccion = new InformeAccion();
					prmInformeAccion.setIdInformeAccion(prmData.getInformeAccion().getIdInformeAccion());
					prmInformeAccion.getEstado().setCodigoRegistro(2);
					/******* Auditoria ******/
					
					prmInformeAccion.setHostIp(UtilRequest.getClientIpAddress(request));
					prmInformeAccion.setPrm1(prmData.getPrm1());
					/************************/
					informeAccionService.actualizar(prmInformeAccion);
					
					int b =0;
							for (MultipartFile archivoEvaluacion : archivo) {
								
								
								ArchivoAtencion dataArchivo = new ArchivoAtencion();
						String tmpNombreArchivo = archivoEvaluacion.getOriginalFilename(); 
						
						
						String folder = ResourceUtil.getKey("file.path.informe-evaluacion-informe");
						folder = folder.replace("{id}", String.valueOf(idGenerado));
						
			/*********************************************************************************/
			/***																		   ***/			
			/*********************************************************************************/
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
							
							dataArchivo.setUiid(lstArchivoGenericoAlfresco.get(b));
							
							
						}else {
							String rutaArchivoCopiado = gn.copiarArchivo(
									tmpNombreArchivo, 
									folder, 
									archivoEvaluacion.getInputStream());
							
							if (VO.isNullOrEmpty(rutaArchivoCopiado)) {
								respuesta.setMensaje("No se pudo archivar el documento.");
								return respuesta;
							}
						}
						
						
		
						/*********************************************************************************/
						/***																		   ***/			
						/*********************************************************************************/
					
						dataArchivo.setTipoTabla(3);
						dataArchivo.setNombreArchivo(CodeUtil.parseEncode(tmpNombreArchivo));
						dataArchivo.setRutaArchivo(folder);
						dataArchivo.setDescripcionArchivo(CodeUtil.parseEncode(dataArchivo.getDescripcionArchivo()));
						dataArchivo.setMimeTypeArchivo(archivoEvaluacion.getContentType());
						dataArchivo.setIdAtencionDenuncia(idGenerado);
						archivoAtencionService.insertar(dataArchivo);
						b=b+1;
						
					}
					
							
					
				}
				
				
				/********************* Correo ***********************/
				
				AccionesRealizadas accionesRealizadas = new AccionesRealizadas();
				accionesRealizadas.setTipoTablaAccion(2);
				accionesRealizadas.setIdAccion(prmData.getInformeAccion().getIdInformeAccion());
				accionesRealizadas = accionesRealizadasService.buscarPorTipoTablaIdAccion(accionesRealizadas); // tiene codigo de Denuncia
				
				if(accionesRealizadas!=null){
					EntidadDenuncia prmEntidadDenuncia = new EntidadDenuncia();
					
					prmEntidadDenuncia = entidadDenunciaService.buscarPorId(accionesRealizadas.getIdBandejaDetalle());
			
					if(prmEntidadDenuncia!=null ){
						
						/*************************************************************************/
						
						

						
						FormatoCorreo formatoCorreoCoordinador = new FormatoCorreo();
						long idFormatoCorreo1 = 1;
						formatoCorreoCoordinador = formatoCorreoService.buscarPorId(idFormatoCorreo1);
						String tituloCoordinador ="Se ha Observado la acción realizada en la denuncia "+prmEntidadDenuncia.getCodigoDenuncia() +" de la entidad "+ prmEntidadDenuncia.getNombreEntidad() ;
						
						String textoCoordinador1 = "Observación Denuncia " + prmEntidadDenuncia.getCodigoDenuncia();
						String textoCoordinador2 = "<b>Motivo :</b>";
						String textoCoordinador3 = prmData.getMotivoDescripcion();
						String textoCoordinador4 = "";
						String textoCoordinador5 = "";
						String textoCoordinador6 = "";
						String textoCoordinador7 = "";
						
						String mensajeCoordinador = formatoCorreoCoordinador.getMensaje();
						
						textoCoordinador6 = "Descripción de la Acción :";
						textoCoordinador7 = "" + accionesRealizadas.getDescripcionAccion();
						mensajeCoordinador = mensajeCoordinador.replace("TextoCorreo1", textoCoordinador1);
						mensajeCoordinador = mensajeCoordinador.replace("TextoCorreo2", textoCoordinador2);
						mensajeCoordinador = mensajeCoordinador.replace("TextoCorreo3", textoCoordinador3);
						mensajeCoordinador = mensajeCoordinador.replace("TextoCorreo4", textoCoordinador4);
						mensajeCoordinador = mensajeCoordinador.replace("TextoCorreo5", textoCoordinador5);
						mensajeCoordinador = mensajeCoordinador.replace("TextoCorreo6", textoCoordinador6);
						mensajeCoordinador = mensajeCoordinador.replace("TextoCorreo7", textoCoordinador7);
						
						
						
						enviarCorreoCoordinadorSinada(tituloCoordinador, mensajeCoordinador);
						
						
						
						
						
						/************************************************************************/
						
						/**************************************************/
						if(prmEntidadDenuncia.getTipoEntidadComponente()==2){ // EFA
							
							 // EFa
							Efa efa = new Efa();
							efa = efaService.buscarPorId(prmEntidadDenuncia.getIdEfa());
							
							if(efa.getCorreo()!=null){
								
								FormatoCorreo formatoCorreo = new FormatoCorreo();
								long idFormatoCorreo = 1;
								formatoCorreo = formatoCorreoService.buscarPorId(idFormatoCorreo);
								String titulo =prmEntidadDenuncia.getNombreEntidad()  + " se ha Observado la accion realizada en la denuncia  "+prmEntidadDenuncia.getCodigoDenuncia();
								
								String texto1 = "Observación Denuncia  " + prmEntidadDenuncia.getCodigoDenuncia();
								String texto2 = "<b>Motivo :</b>";
								String texto3 = prmData.getMotivoDescripcion();
								String texto4 = "";
								String texto5 = "";
								String texto6 = "";
								String texto7 = "";
								
								String mensaje = formatoCorreo.getMensaje();
								
//								texto1 ="<center><h3> Observación de Acción Denuncia "+accionesRealizadas.getCodigoDenuncia()+"</h3></center>";
//						
//								
//								texto2 ="Tipo Acción : "+accionesRealizadas.getNombreTipoAccion() ;
//			
							
								texto6 = "Descripción de la Acción :";
								texto7 = "" + accionesRealizadas.getDescripcionAccion();
								mensaje = mensaje.replace("TextoCorreo1", texto1);
								mensaje = mensaje.replace("TextoCorreo2", texto2);
								mensaje = mensaje.replace("TextoCorreo3", texto3);
								mensaje = mensaje.replace("TextoCorreo4", texto4);
								mensaje = mensaje.replace("TextoCorreo5", texto5);
								mensaje = mensaje.replace("TextoCorreo6", texto6);
								mensaje = mensaje.replace("TextoCorreo7", texto7);
								
								EmailAttachmentSender.sendEmailWithAttachments(efa.getCorreo(), titulo, mensaje, null);
								
								
							
								
								
							}
							
						
					
							
						}

						if (prmEntidadDenuncia.getTipoEntidadComponente()==1) {// OEFA
							
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
										String titulo =prmEntidadDenuncia.getNombreEntidad()  + " se ha Observado la accion realizada en la denuncia "+prmEntidadDenuncia.getCodigoDenuncia();
										
										String texto1 = "Observación Denuncia " + prmEntidadDenuncia.getCodigoDenuncia();
										String texto2 = "<b>Motivo :</b>";
										String texto3 = prmData.getMotivoDescripcion();
										String texto4 = "";
										String texto5 = "";
										String texto6 = "";
										String texto7 = "";
										
										String mensaje = formatoCorreo.getMensaje();
										
//										texto1 ="<center><h3> Observación de Acción Denuncia "+accionesRealizadas.getCodigoDenuncia()+"</h3></center>";
//								
//										
//										texto2 ="Tipo Acción : "+accionesRealizadas.getNombreTipoAccion() ;
//					
									
										texto6 = "Descripción de la Acción :";
										texto7 = "" + accionesRealizadas.getDescripcionAccion();
										mensaje = mensaje.replace("TextoCorreo1", texto1);
										mensaje = mensaje.replace("TextoCorreo2", texto2);
										mensaje = mensaje.replace("TextoCorreo3", texto3);
										mensaje = mensaje.replace("TextoCorreo4", texto4);
										mensaje = mensaje.replace("TextoCorreo5", texto5);
										mensaje = mensaje.replace("TextoCorreo6", texto6);
										mensaje = mensaje.replace("TextoCorreo7", texto7);
										
										EmailAttachmentSender.sendEmailWithAttachments(contacto.getValor(), titulo, mensaje, null);
										
										
									
										
										
									}
								
								}
								
								
							}
							
							
							
						}
						/********************************************/
				
						
						
					}
					
				}
				
			}
			
			
			
			
			
			
			/******************************************************/
			
			
			if(idGenerado>0)
			{
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha registrado la Evaluacion correctamente");
				respuesta.setData(prmData.getIdEvaluacionInforme());
			} 
			else 
			{
				respuesta.setMensaje("No se pudo registrar la evaluacion");
			}
		} 
		catch (Exception e) 
		{
			logger.error(this.logBase+ " : registrar" + e.getMessage());
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al registrar la evaluacion");
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


	@ResponseBody
	@RequestMapping(value = "/aprobarAccion",method = RequestMethod.POST)
	public RespuestaHttp aprobarAccion(@RequestBody EvaluacionInforme prmData,HttpServletRequest request)throws Exception
	{
		RespuestaHttp respuesta = new RespuestaHttp();
		try
		{
			System.out.println(prmData);
			
			/******* Auditoria ******/
			
			prmData.setHostIp(UtilRequest.getClientIpAddress(request));
		
			/************************/
			
			long idGenerado = evaluacionInformeService.insertar(prmData);
			
			InformeAccion prmInformeAccion = new InformeAccion();
			prmInformeAccion.setIdInformeAccion(prmData.getInformeAccion().getIdInformeAccion());
			prmInformeAccion.getEstado().setCodigoRegistro(3);
			/******* Auditoria ******/
			
			prmInformeAccion.setHostIp(UtilRequest.getClientIpAddress(request));
			prmInformeAccion.setPrm1(prmData.getPrm1());
			/************************/
			
			informeAccionService.actualizar(prmInformeAccion);
			
			
			prmInformeAccion = informeAccionService.buscarPorId(prmInformeAccion.getIdInformeAccion());
			if(prmInformeAccion!=null){
				
				if(prmInformeAccion.getTipoInforme().getCodigoRegistro()==2){
					BandejaDetalle prmBandejaDetalle = new BandejaDetalle();
					
					prmBandejaDetalle.setIdBandejaDetalle(prmInformeAccion.getDetalleBandeja().getIdBandejaDetalle());
					prmBandejaDetalle.setEstado(8);
					/******* Auditoria ******/
					
					prmBandejaDetalle.setHostIp(UtilRequest.getClientIpAddress(request));
					prmBandejaDetalle.setPrm1(prmData.getPrm1());
					/************************/
					bandejaDetalleService.actualizar(prmBandejaDetalle);
					
					
					/********************************************************/
					DenunciaEstado prmDenunciaEstado = new DenunciaEstado();
					
					prmDenunciaEstado =	denunciaEstadoService.buscarPorId(prmInformeAccion.getDenuncia().getIdDenuncia());
					
					if(prmDenunciaEstado!=null){
						if(prmDenunciaEstado.getTotalAtenciones()==1 || prmDenunciaEstado.getTotalAtenciones()==prmDenunciaEstado.getDenunciasAtendidas()){
							
							Denuncia prmDenuncia = new Denuncia();
							prmDenuncia.setIdDenuncia(prmDenunciaEstado.getIdDenuncia());
							prmDenuncia.setEstado(8);
							/******* Auditoria ******/
							
							prmDenuncia.setHostIp(UtilRequest.getClientIpAddress(request));
							prmDenuncia.setPrm1(prmData.getPrm1());
							/************************/
							denunciaService.cambiarEstado(prmDenuncia);
							
						}
					}
					
					/********************************************************/
				}
				
			}
			
			
			
	/********************* Correo ***********************/
			
			AccionesRealizadas accionesRealizadas = new AccionesRealizadas();
			accionesRealizadas.setTipoTablaAccion(2);
			accionesRealizadas.setIdAccion(prmData.getInformeAccion().getIdInformeAccion());
			accionesRealizadas = accionesRealizadasService.buscarPorTipoTablaIdAccion(accionesRealizadas); // tiene codigo de Denuncia
			EntidadDenuncia prmEntidadDenuncia = new EntidadDenuncia();
			if(accionesRealizadas!=null){
			
				
				prmEntidadDenuncia = entidadDenunciaService.buscarPorId(accionesRealizadas.getIdBandejaDetalle());
		
				if(prmEntidadDenuncia!=null ){
					
					/**********************************************************************/
					
					FormatoCorreo formatoCorreoCoordinador = new FormatoCorreo();
					long idFormatoCorreo1 = 1;
					formatoCorreoCoordinador = formatoCorreoService.buscarPorId(idFormatoCorreo1);
					String tituloCoordinador ="Se ha aprobado la acción realizada en la denuncia "+prmEntidadDenuncia.getCodigoDenuncia() +" de la entidad "+ prmEntidadDenuncia.getNombreEntidad() ;
					
					String textoCoordinador1 = "La acción realizada a la Denuncia " + prmEntidadDenuncia.getCodigoDenuncia() +" por la entidad " + prmEntidadDenuncia.getNombreEntidad() +" ha sido aprobada." ;
					String textoCoordinador2 = "";
					String textoCoordinador3 = "";
					String textoCoordinador4 = "";
					String textoCoordinador5 = "";
					String textoCoordinador6 = "";
					String textoCoordinador7 = "";
					
					String mensajeCoordinador = formatoCorreoCoordinador.getMensaje();
					

					mensajeCoordinador = mensajeCoordinador.replace("TextoCorreo1", textoCoordinador1);
					mensajeCoordinador = mensajeCoordinador.replace("TextoCorreo2", textoCoordinador2);
					mensajeCoordinador = mensajeCoordinador.replace("TextoCorreo3", textoCoordinador3);
					mensajeCoordinador = mensajeCoordinador.replace("TextoCorreo4", textoCoordinador4);
					mensajeCoordinador = mensajeCoordinador.replace("TextoCorreo5", textoCoordinador5);
					mensajeCoordinador = mensajeCoordinador.replace("TextoCorreo6", textoCoordinador6);
					mensajeCoordinador = mensajeCoordinador.replace("TextoCorreo7", textoCoordinador7);
					
					
					
					enviarCorreoCoordinadorSinada(tituloCoordinador, mensajeCoordinador);
					
					
					
					/************************************************************************/
					
					/**************************************************/
					if(prmEntidadDenuncia.getTipoEntidadComponente()==2){ // EFA
						
						 // EFa
						Efa efa = new Efa();
						efa = efaService.buscarPorId(prmEntidadDenuncia.getIdEfa());
						
						if(efa.getCorreo()!=null){
							
							FormatoCorreo formatoCorreo = new FormatoCorreo();
							long idFormatoCorreo = 1;
							formatoCorreo = formatoCorreoService.buscarPorId(idFormatoCorreo);
							String titulo =prmEntidadDenuncia.getNombreEntidad()  + " se ha aprobado la acción realizada a la denuncia "+prmEntidadDenuncia.getCodigoDenuncia();
							
							String texto1 = "Sres : " + prmEntidadDenuncia.getNombreEntidad();
							String texto2 = "La accion realizada a la denuncia " + prmEntidadDenuncia.getCodigoDenuncia() + " ha sido aprobada.";
							String texto3 = "";
							String texto4 = "";
							String texto5 = "";
							String texto6 = "";
							String texto7 = "";
							
							String mensaje = formatoCorreo.getMensaje();
							
//							texto1 ="<center><h3> Observación de Acción Denuncia "+accionesRealizadas.getCodigoDenuncia()+"</h3></center>";
//					
//							
//							texto2 ="Tipo Acción : "+accionesRealizadas.getNombreTipoAccion() ;
//		
						
							texto6 = "Descripción de la acción :";
							texto7 = "" + accionesRealizadas.getDescripcionAccion();
							mensaje = mensaje.replace("TextoCorreo1", texto1);
							mensaje = mensaje.replace("TextoCorreo2", texto2);
							mensaje = mensaje.replace("TextoCorreo3", texto3);
							mensaje = mensaje.replace("TextoCorreo4", texto4);
							mensaje = mensaje.replace("TextoCorreo5", texto5);
							mensaje = mensaje.replace("TextoCorreo6", texto6);
							mensaje = mensaje.replace("TextoCorreo7", texto7);
							
							EmailAttachmentSender.sendEmailWithAttachments(efa.getCorreo(), titulo, mensaje, null);
							
							
						
							
							
						}
						
					
				
						
					}

					if (prmEntidadDenuncia.getTipoEntidadComponente()==1) {// OEFA
						
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
									String titulo =prmEntidadDenuncia.getNombreEntidad()  + " se ha aprobado la acción realizada a la denuncia "+prmEntidadDenuncia.getCodigoDenuncia();
									
									String texto1 = "Sres :" + prmEntidadDenuncia.getNombreEntidad();
									String texto2 = "La acción realizada a la denuncia " + prmEntidadDenuncia.getCodigoDenuncia() + " ha sido aprobada.";
									String texto3 = "";
									String texto4 = "";
									String texto5 = "";
									String texto6 = "";
									String texto7 = "";
									
									String mensaje = formatoCorreo.getMensaje();
									
//									texto1 ="<center><h3> Observación de Acción Denuncia "+accionesRealizadas.getCodigoDenuncia()+"</h3></center>";
//							
//									
//									texto2 ="Tipo Acción : "+accionesRealizadas.getNombreTipoAccion() ;
//				
								
									texto6 = "Descripción de la Acción :";
									texto7 = "" + accionesRealizadas.getDescripcionAccion();
									mensaje = mensaje.replace("TextoCorreo1", texto1);
									mensaje = mensaje.replace("TextoCorreo2", texto2);
									mensaje = mensaje.replace("TextoCorreo3", texto3);
									mensaje = mensaje.replace("TextoCorreo4", texto4);
									mensaje = mensaje.replace("TextoCorreo5", texto5);
									mensaje = mensaje.replace("TextoCorreo6", texto6);
									mensaje = mensaje.replace("TextoCorreo7", texto7);
									
									EmailAttachmentSender.sendEmailWithAttachments(contacto.getValor(), titulo, mensaje, null);
									
									
								
									
									
								}
							
							}
							
							
						}
						
						
						
					}
					/********************************************/
			
				}
				
			}
			
			/*********************** CORREO DENUNCIANTE ******************************/
			DenuncianteDetalle prmDenuncianteDetalle  = new DenuncianteDetalle();
			prmDenuncianteDetalle.setIdDenuncia(accionesRealizadas.getIdDenuncia());
			
			List<DenuncianteDetalle> lstDenuncianteDetalle= null;
			
			lstDenuncianteDetalle = denuncianteService.listarDenunciantes(prmDenuncianteDetalle);
			
			if(lstDenuncianteDetalle!=null && lstDenuncianteDetalle.size()>0){
				
				for (DenuncianteDetalle denuncianteDetalle : lstDenuncianteDetalle) {
					
					/******************************************************/


					ContactoPersona contactoPersona = new ContactoPersona();
					contactoPersona.setTipoPersona(denuncianteDetalle.getTipoPersona());
					contactoPersona.setIdPersona(denuncianteDetalle.getIdPersona());
					List<ContactoPersona> lstContactoPersona = null;
					lstContactoPersona = (List<ContactoPersona>) contactoPersonaService.listar(contactoPersona);
					
					for (ContactoPersona contacto : lstContactoPersona) {
						
						if(contacto.getTipoContacto()==2){
							FormatoCorreo formatoCorreo = new FormatoCorreo();
							long idFormatoCorreo = 1;
							formatoCorreo = formatoCorreoService.buscarPorId(idFormatoCorreo);
							
							String texto1 = "";
							String texto2 = "";
							String texto3 = "";
							String texto4 = "";
							
							String mensaje = formatoCorreo.getMensaje();
							if(denuncianteDetalle.getTipoPersona()==1){
								texto1 ="Sr(a): " + denuncianteDetalle.getNombreCompleto();
							}else{
								texto1 ="Sres. " +denuncianteDetalle.getNombreCompleto();
							}
							texto2 ="Se han registrado acciones a la denuncia " + prmEntidadDenuncia.getCodigoDenuncia();
						
							mensaje = mensaje.replace("TextoCorreo1", texto1);
							mensaje = mensaje.replace("TextoCorreo2", texto2);
							mensaje = mensaje.replace("TextoCorreo3", texto3);
							mensaje = mensaje.replace("TextoCorreo4", texto4);
							mensaje = mensaje.replace("TextoCorreo5", "");
							mensaje = mensaje.replace("TextoCorreo6", "");
							mensaje = mensaje.replace("TextoCorreo7", "");
							
							EmailAttachmentSender.sendEmailWithAttachments(contacto.getValor(), "Se Registraron acciones en la denuncia " + prmEntidadDenuncia.getCodigoDenuncia(), mensaje, null);
							
							
						}
					
					}
					
					
					
					/*************************************************************/
					
				}
				
			}
			
			/****************************************************/
			
			if(idGenerado>0)
			{
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha aprobado la denuncia satisfactoriamente");
				respuesta.setData(prmData.getIdEvaluacionInforme());
			} 
			else 
			{
				respuesta.setMensaje("No se pudo registrar la información");
			}
		} 
		catch (Exception e) 
		{
			logger.error(this.logBase+ " : aprobarAccion" + e.getMessage());
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al registrar la información");
		}
		return respuesta;
	}
	
//	
//	/*********************************** Derivacion Denuncia    *********************************/
//	
//	@ResponseBody
//	@RequestMapping(value = "/derivarDenuncia",method = RequestMethod.POST)
//	public RespuestaHttp derivarDenuncia(@RequestBody EvaluacionInforme prmData,HttpServletRequest request)throws Exception
//	{
//		RespuestaHttp respuesta = new RespuestaHttp();
//		try
//		{
//			AtencionDenuncia prmDataAtencionDenuncia = new AtencionDenuncia();
//			prmDataAtencionDenuncia.setIdAtencionDenuncia(prmData.getAtencionDenuncia().getIdAtencionDenuncia());
//			prmDataAtencionDenuncia = atencionDenunciaService.buscarPorId(prmDataAtencionDenuncia.getIdAtencionDenuncia());
//			
//			long idGenerado = evaluacionInformeService.insertar(prmData);
//			
//			
//			
//			if(idGenerado!= 0){
//				
//				for (DerivacionDenuncia prmDerivacion : prmData.getLstDerivacionDenuncia()) {
//					
//					prmDerivacion.getEvaluacionInforme().setIdEvaluacionInforme(idGenerado);
//					
//					derivacionDenunciaService.insertar(prmDerivacion);
//					
//					if(prmDerivacion.getTipoDestinatario().getCodigoRegistro()==1){
//						
//						
//						
//						Bandeja prmBandeja = new Bandeja();
//						prmBandeja.setTipoResponsable(3);// 3 -- OEFA  -- 4 EFA
////						prmBandeja.setIdResponsable(lstPersonaOefa.get(s).getIdPersonaOefa());
//						prmBandeja.setDireccion(prmDerivacion.getDireccionSupervicion().getCodigoRegistro());
//						prmBandeja.setSubDireccion(prmDerivacion.getSubDireccion().getCodigoRegistro());
//						prmBandeja.setCoordinacion(0);
//						prmBandeja.setEstado(1);
//						prmBandeja =bandejaService.validarBandejaOefa(prmBandeja);
//						
//						BandejaDetalle prmBandejaDetalle = new BandejaDetalle();
//						prmBandejaDetalle.setIdBandeja(prmBandeja.getIdBandeja());
//						prmBandejaDetalle.setTipoBandeja(3);
//						prmBandejaDetalle.setIdDenuncia(prmDataAtencionDenuncia.getDenuncia().getIdDenuncia());
//						prmBandejaDetalle.setTipoAsignacion(1);
//						prmBandejaDetalle.setEstado(1);
//						
//						bandejaDetalleService.insertar(prmBandejaDetalle);
//						
//					
//					}
//					
//					
//					if(prmDerivacion.getTipoDestinatario().getCodigoRegistro()==2){
//						
//						Bandeja prmBandeja = new Bandeja();
//						prmBandeja.setTipoResponsable(4);  // 3 -- OEFA  -- 4 EFA
//						prmBandeja.setIdEFa(prmDerivacion.getEfa().getIdEfa());
//						prmBandeja.setEstado(1);
//						prmBandeja =bandejaService.validarBandejaEfa(prmBandeja);
//						
//						BandejaDetalle prmBandejaDetalle = new BandejaDetalle();
//						prmBandejaDetalle.setIdBandeja(prmBandeja.getIdBandeja());
//						prmBandejaDetalle.setTipoBandeja(3);
//						prmBandejaDetalle.setIdDenuncia(prmDataAtencionDenuncia.getDenuncia().getIdDenuncia());
//						prmBandejaDetalle.setTipoAsignacion(1);
//						prmBandejaDetalle.setEstado(1);
//						
//						
//						bandejaDetalleService.insertar(prmBandejaDetalle);
//						
//					}
//					
//				}
//			
//				
//			}
//			
//			AtencionDenuncia prmAtencionDenuncia = new AtencionDenuncia();
//			prmAtencionDenuncia.setIdAtencionDenuncia(prmData.getAtencionDenuncia().getIdAtencionDenuncia());
//			prmAtencionDenuncia.getEstado().setCodigoRegistro(3);
//			atencionDenunciaService.actualizar(prmAtencionDenuncia);
//			
//			if(idGenerado>0)
//			{
//				respuesta.setValido(true);
//				respuesta.setMensaje("Se ha registrado la Información satisfactoriamente");
//				respuesta.setData(prmData.getIdEvaluacionInforme());
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
	
	@ResponseBody
	@RequestMapping(value ="/actualizar", method = RequestMethod.POST)
	public  RespuestaHttp actualizar(@RequestBody EvaluacionInforme prmData,HttpServletRequest request)throws Exception {
		RespuestaHttp respuesta = new RespuestaHttp();
		try{
			
			/******* Auditoria ******/
			
			prmData.setHostIp(UtilRequest.getClientIpAddress(request));
	
			/************************/
			int result = evaluacionInformeService.actualizar(prmData);
			
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
			@RequestBody EvaluacionInforme prmData,HttpServletRequest request)
			throws Exception{
		RespuestaHttp respuesta = new RespuestaHttp();
		try{
			prmData.setHostIp(UtilRequest.getClientIpAddress(request));
			int result = evaluacionInformeService.eliminar(prmData);
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
			@RequestBody EvaluacionInforme prmData,HttpServletRequest request)
			throws Exception{
		RespuestaHttp respuesta = new RespuestaHttp();
		try{
			
			prmData = evaluacionInformeService.buscarPorId(prmData.getIdEvaluacionInforme());
			
			
			if(prmData!=null){
				respuesta.setData(prmData);
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
	
	
	@ResponseBody
	@RequestMapping(value = "/buscarPorIdInformeAccion", method = RequestMethod.POST)
	public RespuestaHttp buscarPorIdInformeAccion(
			@RequestBody EvaluacionInforme prmData,HttpServletRequest request)
			throws Exception{
		RespuestaHttp respuesta = new RespuestaHttp();
		try{
			
			prmData = evaluacionInformeService.buscarPorIdInformeAccion(prmData);
			
			
			if(prmData!=null){
				respuesta.setValido(true);
				respuesta.setData(prmData);
				respuesta.setMensaje("Se Encontro la evaluación");
			}else {
				respuesta.setMensaje("No se encontro el dato");
			}
		} catch (Exception e){
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al procesar la información");
		}
		return respuesta;
	}
	
	
}
