package pe.gob.oefa.apps.sinada.presentacion.rest.api;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import pe.gob.oefa.apps.base.presentacion.response.RespuestaHttp;
import pe.gob.oefa.apps.base.util.CodeUtil;
import pe.gob.oefa.apps.base.util.EmailAttachmentSender;
import pe.gob.oefa.apps.base.util.UtilEncrypt;
import pe.gob.oefa.apps.base.util.UtilRequest;
import pe.gob.oefa.apps.base.util.UtilValidator;
import pe.gob.oefa.apps.base.util.properties.UtilProperties;
import pe.gob.oefa.apps.base.webservice.rest.std.bean.STDDenunciaBean;
import pe.gob.oefa.apps.base.webservice.rest.std.bean.STDHojaTramiteBean;
import pe.gob.oefa.apps.base.webservice.rest.std.servicio.STDREST;
import pe.gob.oefa.apps.sinada.bean.general.ContactoPersona;
import pe.gob.oefa.apps.sinada.bean.general.Entidad;
import pe.gob.oefa.apps.sinada.bean.general.Persona;
import pe.gob.oefa.apps.sinada.bean.maestros.FormatoCorreo;
import pe.gob.oefa.apps.sinada.bean.maestros.Maestro;
import pe.gob.oefa.apps.sinada.bean.proceso.ArchivoCaso;
import pe.gob.oefa.apps.sinada.bean.proceso.ArchivoDenuncia;
import pe.gob.oefa.apps.sinada.bean.proceso.Bandeja;
import pe.gob.oefa.apps.sinada.bean.proceso.BandejaDetalle;
import pe.gob.oefa.apps.sinada.bean.proceso.CasoEfa;
import pe.gob.oefa.apps.sinada.bean.proceso.CasoOefa;
import pe.gob.oefa.apps.sinada.bean.proceso.Denuncia;
import pe.gob.oefa.apps.sinada.bean.proceso.Denunciante;
import pe.gob.oefa.apps.sinada.bean.proceso.DescripcionCaso;
import pe.gob.oefa.apps.sinada.bean.proceso.NormaCaso;
import pe.gob.oefa.apps.sinada.bean.proceso.PersonaOefa;
import pe.gob.oefa.apps.sinada.bean.proceso.view.DenuncianteDetalle;
import pe.gob.oefa.apps.sinada.bean.proceso.view.EntidadCaso;
import pe.gob.oefa.apps.sinada.bean.proceso.view.VistaDenuncia;
import pe.gob.oefa.apps.sinada.bean.seguridad.Perfil;
import pe.gob.oefa.apps.sinada.bean.seguridad.PerfilUsuario;
import pe.gob.oefa.apps.sinada.bean.seguridad.Usuario;
import pe.gob.oefa.apps.sinada.bean.sirefa.Efa;
import pe.gob.oefa.apps.sinada.presentacion.rest.api.bas.GenericController;
import pe.gob.oefa.apps.sinada.presentacion.util.AlfrescoUtil;
import pe.gob.oefa.apps.sinada.presentacion.util.GeneradorCodigo;
import pe.gob.oefa.apps.sinada.presentacion.util.ResourceUtil;
import pe.gob.oefa.apps.sinada.presentacion.util.VO;
import pe.gob.oefa.apps.sinada.servicio.inf.general.ContactoPersonaService;
import pe.gob.oefa.apps.sinada.servicio.inf.general.EntidadService;
import pe.gob.oefa.apps.sinada.servicio.inf.general.PersonaService;
import pe.gob.oefa.apps.sinada.servicio.inf.maestros.FormatoCorreoService;
import pe.gob.oefa.apps.sinada.servicio.inf.maestros.MaestroService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.ArchivoCasoService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.ArchivoDenunciaService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.BandejaDetalleService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.BandejaService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.CasoEfaService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.CasoOefaService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.DenunciaService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.DenuncianteService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.DescripcionCasoService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.NormaCasoService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.NotificacionesService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.PersonaOefaService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.view.EntidadCasoService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.view.VistaDenunciaService;
import pe.gob.oefa.apps.sinada.servicio.inf.seguridad.PerfilUsuarioService;
import pe.gob.oefa.apps.sinada.servicio.inf.seguridad.UsuarioService;
import pe.gob.oefa.apps.sinada.servicio.inf.sirefa.EfaService;
import pe.gob.oefa.apps.sinada.servicio.inf.sirin.NormasService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Controller
@RequestMapping(value="/rest/api/denuncia")
public class DenunciaRest extends GenericController
{
	@Autowired
	private DenunciaService denunciaService;
	
	@Autowired
	private DenuncianteService denuncianteService;
	
	@Autowired
	private ArchivoDenunciaService archivoDenunciaService;
	
	@Autowired
	private CasoEfaService casoEfaService;
	
	@Autowired
	private PersonaOefaService personaOefaService;
	
	@Autowired
	private CasoOefaService casoOefaService;
	
	@Autowired
	private BandejaService bandejaService;
	
	@Autowired
	private BandejaDetalleService bandejaDetalleService;
	
	@Autowired
	private DescripcionCasoService descripcionCasoService;
	
	@Autowired
	private ArchivoCasoService archivoCasoService;
	
	@Autowired
	private NotificacionesService notificacionesService;
	
	@Autowired
	private EntidadCasoService entidadCasoService;
	
	@Autowired
	private NormasService normasService;
	
	@Autowired
	private PersonaService personaService;
	
	@Autowired
	private EntidadService entidadService;
	
	@Autowired
	private EfaService efaService;
	
	@Autowired
	private NormaCasoService normaCasoService;
	
	@Autowired 
	private UsuarioService usuarioService;
	
	@Autowired
	private FormatoCorreoService formatoCorreoService;
	
	@Autowired
	private ContactoPersonaService contactoPersonaService;
	
	@Autowired
	private PerfilUsuarioService perfilUsuarioService;
	
	@Autowired
	private VistaDenunciaService vistaDenunciaService;
	
	@Autowired
	private MaestroService maestroService;
	
	private String rutaBaseServidor =  ResourceUtil.getKey("file.path.base"); 
	
//	public String dataHtmlBase = "<html xmlns='http://www.w3.org/1999/xhtml'><head> <meta http-equiv='Content-Type' content='text/html; charset=utf-8' /> <title>SINADA</title>  <link href='https://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' />  <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css'> <link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css' integrity='sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm' crossorigin='anonymous'> <style media='screen'>    .borde-vistaPrevia{     max-width: 700px;     border: 30px solid #666666;      font-size: 1em;     padding: 0 35px;} .cabecera_vistaPrevia{   padding: 10px;    border-bottom: 5px solid #144aa7;  margin-bottom: 10px;  } .sub-cabecera-vistaPrevia{    background:#eeeeef;    padding: 15px; }  .pd-vistaPrevia{    padding: 20px;  }</style><body id='principal'>      DataFichaDenunciaHTMLReemplazo </body> </html>";
	  
	private String logBase = "oefa-sinada-web: DenunciaRest";
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@RequestMapping(value="/validar-documento-medio-probatorio",method=RequestMethod.POST, consumes ={"multipart/form-data"})
	public @ResponseBody RespuestaHttp registrarArchivo(
			@RequestParam("file")MultipartFile archivo,
			HttpServletRequest request)
			throws Exception {
   		
   	
		boolean result=true;
		RespuestaHttp respuesta = new RespuestaHttp();
		try {
		
//			Denuncia prmDenuncia = new ObjectMapper().readValue(archivoMedio, Denuncia.class);
		
//			if (!ArrayUtils.contains(Constantes.VALID_MEDIA_TYPES_DOCUMENT, archivo.getContentType())) {
//				respuesta.setMensaje("El documento debe ser un archivo PDF.");
//				return respuesta;
//			}
			if (archivo.getSize()/1024>5120) {
				respuesta.setMensaje("El tamaño del archivo no debe superar 5MB. "
						+ "Intente comprimir el tamaño del archivo y vuelva a intentarlo.");
				result=false;
				return respuesta;
				
			}
			String tmpNombreArchivo = archivo.getOriginalFilename(); 
			int caracteres = Integer.parseInt(ResourceUtil.getKey("maximo_caracteres_archivo"));
			if (tmpNombreArchivo.length()>caracteres) {
				respuesta.setMensaje("El nombre del archivo no debe superar los "+ResourceUtil.getKey("maximo_caracteres_archivo")+" caracteres. "
						+ "Intente renombrarlo y vuelva a cargarlo.");
				result=false;
				return respuesta;
			}
		
			if(result){
				respuesta.setValido(true);
				respuesta.setMensaje("");
	
			} else {
				respuesta.setMensaje("El archivo no es el correcto");
			}
		} catch (Exception e){
			logger.error(this.logBase+ " : registrarArchivo" + e.getMessage());
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al registrar el documento");
		}
		return respuesta;
	}
	
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/listar", method=RequestMethod.GET)
	public RespuestaHttp buscar(@RequestParam(value="prmCriterio",required=false)String prmCriterio)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try
		{
			Denuncia prmDenuncia = new ObjectMapper().readValue(prmCriterio, Denuncia.class);
			List<Denuncia> denuncia = (List<Denuncia>) denunciaService.listar(prmDenuncia);
			super.numerarItem(denuncia);
			_RespuestaHttp.setValido(true);
			_RespuestaHttp.setData(denuncia);
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
	@RequestMapping(value="/buscarDenunciaInvitado", method=RequestMethod.POST)
	public RespuestaHttp buscarDenunciaInvitado(@RequestBody Denuncia prmDenuncia, HttpServletRequest request)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try
		{
		
			List<Denuncia> denuncia = (List<Denuncia>) denunciaService.buscarDenunciaInvitado(prmDenuncia);
			
			if(denuncia!=null && denuncia.size()>0){
				_RespuestaHttp.setValido(true);
				_RespuestaHttp.setData(denuncia);
			}else{
				_RespuestaHttp.setValido(false);
				_RespuestaHttp.setMensaje("Su código de acceso y/o código de denuncia es incorrecto");
			}
		
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
	@RequestMapping(value = "/problematica-no-encontrada",method = RequestMethod.POST , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public RespuestaHttp problematicaNoEncontrada(
			/*MultipartHttpServletRequest request*/
			@RequestParam("file")MultipartFile[] archivo,
			@RequestParam("strContenido")String contenido,
			HttpServletRequest request
			)throws Exception{
		
		GenericController gn = new GenericController();
		RespuestaHttp respuesta = new RespuestaHttp();
		DescripcionCaso prmDescripcionCaso = new ObjectMapper().readValue(contenido, DescripcionCaso.class);
		List<String> archivosAdjuntos = new ArrayList<String>();
		 String[] attachFiles = new String[3];
		try {
			
			
			prmDescripcionCaso.setHostIp(UtilRequest.getClientIpAddress(request));
			
			prmDescripcionCaso.setDescripcionCaso(CodeUtil.parseEncode(prmDescripcionCaso.getDescripcionCaso()));
			prmDescripcionCaso.setDireccion(CodeUtil.parseEncode(prmDescripcionCaso.getDireccion()));
			long idDesCaso = descripcionCasoService.insertar(prmDescripcionCaso);
			
//			for(int i = 0; i<archivo.length;i++){
//				String tmpNombreArchivo = archivo[i].getOriginalFilename(); 
//				if (tmpNombreArchivo.length()>50) {
//					respuesta.setMensaje("El nombre del archivo no debe superar los 50 caracteres. "
//							+ "Intente renombrarlo y vuelva a cargarlo.");
//					return respuesta;
//				}
//		
//			}
			
		/********************** Registrar Archivos Problematica no Encontrada *************************/
			
			for(int e = 0; e<archivo.length;e++){		
		/***************************************************************************************************/
		/*																								   */			
		/***************************************************************************************************/
				String tmpNombreArchivo = archivo[e].getOriginalFilename();
				
				ArchivoCaso prmArchivoCaso = new ArchivoCaso();
				String folder = ResourceUtil.getKey("file.path.problematica-no-encontrada");
				folder = folder.replace("{id}", String.valueOf(idDesCaso));
				String rutaArchivoCopiado = gn.copiarArchivo(
						tmpNombreArchivo, 
						folder, 
						archivo[e].getInputStream());
				
				if (VO.isNullOrEmpty(rutaArchivoCopiado)) {
					respuesta.setMensaje("No se pudo archivar el documento.");
					return respuesta;
				}
//				prmDenuncia.setTipo
//				prmDenuncia.setArchivoMedio(tmpNombreArchivo);
//				prmDenuncia.setRutaArchivoMedio(rutaArchivoCopiado);
				prmArchivoCaso.setMimeTypeArchivo(archivo[e].getContentType());
				prmArchivoCaso.setIdDescripcionCaso(idDesCaso);
				prmArchivoCaso.setTipoArchivoCaso(0);
				prmArchivoCaso.setNombreArchivoCaso(tmpNombreArchivo);
				prmArchivoCaso.setRutaArchivoCaso(rutaArchivoCopiado);
				
				archivosAdjuntos.add(rutaArchivoCopiado);
				
				/********************************************************************************/
				/****																		****/
				/********************************************************************************/
				
				String flagAlfresco = ResourceUtil.getKey("file.alfreso");
				if(!flagAlfresco.equals("0")){
					
				  	/** EL SIGUIENTE CODIGO ES LA INTEGRACION CON WSALFRESCO **/
					String uiid = AlfrescoUtil.grabarArchivoAlfresco(archivo[e], folder);
											
					if (VO.isNullOrEmpty(uiid)) {
						respuesta.setMensaje("No se pudo archivar el documento en Alfresco.");
						return respuesta;
					}
					
					prmArchivoCaso.setUiid(uiid);
				}
				/********************************************************************************/
				/****																		****/
				/********************************************************************************/
				
				archivoCasoService.insertar(prmArchivoCaso);
			}
			
			/********************** Fin  Registrar Archivos Denuncia *************************/

				/**************************************************/
				
//			   String mailTo = Constantes.CORREO_PROBLEMATICA_NO_ENCONTRADA;
			
				String mailTo = ResourceUtil.getKey("correo_problematica_no_encontrada");
			   FormatoCorreo prmFormatoCorreo = new FormatoCorreo();
			   long idFormatoCorreo = 1;
			   prmFormatoCorreo =  formatoCorreoService.buscarPorId(idFormatoCorreo);
			   
			   	String mensaje = prmFormatoCorreo.getMensaje();
		        String subject = "Problemática Ambiental no Encontrada";
		        
		        String texto1 = "<div><b>DNI :</b></div>		<div>"+prmDescripcionCaso.getDocumento()+"</div>";
		        String texto2 = "<div ><b>Nombres :</b></div>	<div>"+prmDescripcionCaso.getNombreCompleto()+"</div>";
		        String texto3 = "<div ><b>Dirección :</b></div>	<div>"+prmDescripcionCaso.getDireccion()+"</div>";
		        String texto4 = "<div ><b>Telefono :</b></div>	<div>"+prmDescripcionCaso.getTelefono()+"</div>";
		        String texto5 = "<div ><b>Correo :</b></div>	<div>"+prmDescripcionCaso.getCorreo()+"</div>";
		        String texto6 = "<div ><b>Describenos tu caso :</b></div>";
		        String texto7 = "<div>"+prmDescripcionCaso.getDescripcionCaso()+"</div>";
		        
		    	mensaje = mensaje.replace("TextoCorreo1", texto1);
				mensaje = mensaje.replace("TextoCorreo2", texto2);
				mensaje = mensaje.replace("TextoCorreo3", texto3);
				mensaje = mensaje.replace("TextoCorreo4", texto4);
				mensaje = mensaje.replace("TextoCorreo5", texto5);
				mensaje = mensaje.replace("TextoCorreo6", texto6);
				mensaje = mensaje.replace("TextoCorreo7", texto7);
//		        String message =prmDescripcionCaso.getMensajeHtml();
		 
		        // attachments
//		       
//		        attachFiles[0] = "C:/sinada/problematica/7/archivo/bibliografiasReporte (29).pdf";
//		        attachFiles[1] = "C:/sinada/problematica/7/archivo/bibliografiasReporte (30).pdf";
//		        attachFiles[2] = "C:/sinada/problematica/7/archivo/bibliografiasReporte (31).pdf";
		 
		        try {
		        	EmailAttachmentSender.sendEmailWithAttachments( mailTo,subject, mensaje, archivosAdjuntos);
		        	
		        	for (int i = 0; i < archivosAdjuntos.size(); i++) {
		        		File fichero = new File(archivosAdjuntos.get(i));
		       			if (fichero.exists()){
		       				fichero.delete();
		       			}
					}
		        	
		            System.out.println("Email sent.");
		        } catch (Exception ex) {
		            System.out.println("Could not send email.");
		            ex.printStackTrace();
		        }
			
			/***********************************************************/
			if(idDesCaso>0){
					
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha registrado la Denuncia satisfactoriamente");
				respuesta.setData(idDesCaso);
				
			}else{
				
				respuesta.setMensaje("No se pudo registrar");
			}
			
		} catch (Exception e) {
			logger.error(this.logBase+ " : problematicaNoEncontrada" + e.getMessage());
			return new RespuestaHttp(false, null, "Error multiples archivos:\n"+e.getMessage());
		}
		
		return respuesta;
		
	}
	
	/**
	 * @throws Exception ******************************************************************************/
	private boolean validarExtension(String nombreArchivo,int tipoArchivo){
		String tipo_archivo=VO.getExtensionesValidas(tipoArchivo);
		String[] valores = tipo_archivo.split(",");
		String extension ="";
		int index = nombreArchivo.lastIndexOf('.');
        if (index > -1) {
              extension= nombreArchivo.substring(index);
        }
		for(int i=0;i<valores.length;i++){
			if(valores[i].equals(extension)){
				return true;
			}
		}
		return false;
	}
	
//	public RespuestaHttp registraDenunciaGenerico(Denuncia prmDenuncia,MultipartFile[] archivo, HttpServletRequest request) throws Exception{
//		//capturaIdCaso
//		//prmDenuncia.getCaso().getIdCaso();
//		String separador = ResourceUtil.getKey("file.path.separador");
//		
//		GenericController gn = new GenericController();
//		RespuestaHttp respuesta = new RespuestaHttp();
//		
//		if(prmDenuncia.getMedioRecepcion()==0){
//			prmDenuncia.setMedioRecepcion(5);
//		}
//		else if (prmDenuncia.getMedioRecepcion() != 1) {
//			prmDenuncia.setHojaTramite(null);
//		}
//		if(prmDenuncia.getCentroPoblado()==null){
//			prmDenuncia.setCentroPoblado(new Maestro());
//			prmDenuncia.getCentroPoblado().setCodigoRegistro(0);
//		}
//		GeneradorCodigo n = new GeneradorCodigo();
//		
//		if(archivo!=null && archivo.length>0){
//			for(int i = 0; i<archivo.length;i++){
//				String tmpNombreArchivo = archivo[i].getOriginalFilename(); 
//				int caracteres = Integer.parseInt(ResourceUtil.getKey("maximo_caracteres_archivo"));
//				if (tmpNombreArchivo.length()>caracteres) {
//					respuesta.setMensaje("El nombre del archivo no debe superar los "+ResourceUtil.getKey("maximo_caracteres_archivo")+" caracteres. "
//							+ "Intente renombrarlo y vuelva a cargarlo.");
//					return respuesta;
//				}
//				Long peso = Long.parseLong(ResourceUtil.getKey("peso_maximo_archivo"));
//				if(archivo[i].getSize()>peso){
//					
//				}
//				Maestro maestroTipoArchivo = prmDenuncia.getLstArchivoMedio().get(i).getTipoArchivo();
//				if(maestroTipoArchivo!=null){
//					boolean swValido = validarExtension(archivo[i].getOriginalFilename(),maestroTipoArchivo.getCodigoRegistro());
//					if(!swValido){
//						respuesta.setMensaje("El archivo "+archivo[i].getOriginalFilename()
//								+ "No es un tipo de archivo válido, reemplace el archivo antes de continuar.");
//						return respuesta;
//					}
//				}
//			}
//			
//			
//		}
//		
//		
//	
//		
//		
//		
//		/***********************************************************************************************/
//		/*********																				********/
//		/***********************************************************************************************/
//		
//		if(prmDenuncia.getPrm1()!=null && prmDenuncia.getTipoDenuncia()==1){
//			String PK_eIdUsuarioDecrypt = "0";
//				UtilEncrypt.init(UtilProperties.getKey("encrypt.key"));
//				
//				 PK_eIdUsuarioDecrypt = UtilEncrypt.decrypt(prmDenuncia.getPrm1());
//				System.out.println("PK_eIdUsuarioDecrypt " + PK_eIdUsuarioDecrypt);
//				Usuario prmUsuario = new Usuario();
//				prmUsuario = usuarioService.buscarPorId(Long.valueOf(PK_eIdUsuarioDecrypt));
//				
//				/**********************************/
//				
//				if(prmUsuario!=null ){
//					
//					String idPerfil = ResourceUtil.getKey("IdPerfilSSO_Denunciante");
//					PerfilUsuario prmPerfilUsuario = new PerfilUsuario();
//					prmPerfilUsuario.setUsuario(prmUsuario);
//					prmPerfilUsuario.setPerfil(new Perfil());
//					prmPerfilUsuario.getPerfil().setIdPerfil(Long.valueOf(idPerfil));
//					prmPerfilUsuario = 	perfilUsuarioService.validarUsuarioPorPerfil(prmPerfilUsuario);
//					
//					if(prmPerfilUsuario!=null){
//						Denunciante prmDenunciante = new Denunciante();
//						
//						prmDenunciante.setIdPersona(prmUsuario.getIdPersona());
//						prmDenunciante.setTipoPersona(prmUsuario.getTipoPersona());
//						prmDenunciante.setEstado(1);
//						
//						if(prmDenuncia.getLstDenunciante()==null){
//							prmDenuncia.setLstDenunciante(new ArrayList<Denunciante>());
//							prmDenuncia.getLstDenunciante().add(prmDenunciante);
//						}
//					}
//				}
//
//				/*********************************/
//		}
//		
//		/***********************************************************************************************/
//		/*********																				********/
//		/***********************************************************************************************/
//		
//		List<Denunciante>lstDenunciante=null;
//		
//		if(prmDenuncia.getLstDenunciante()!=null){
//			lstDenunciante=prmDenuncia.getLstDenunciante();
//		}
//	
//		prmDenuncia.setCodigoAcceso(n.getPassword(n.MAYUSCULAS+n.NUMEROS, 6));
//		System.out.println(prmDenuncia);
//		/***************** Auditar Registro denuncia *********************/
//		
//		prmDenuncia.setHostIp(UtilRequest.getClientIpAddress(request));
//		//prmDenuncia.setDireccion(CodeUtil.parseEncode(prmDenuncia.getDireccion()));
//		//prmDenuncia.setReferencia(CodeUtil.parseEncode(prmDenuncia.getReferencia()));
//		long idGenerado = denunciaService.insertar(prmDenuncia);
//		
//		/*************************************/
//		
//		/***********************************************************************************************/
//		/***																						 ***/
//		/***********************************************************************************************/
//	
//		
//
//
//		/********************** Registrar Archivos Denuncia *************************/
//		//RegistrarArchivoDenuncia
//		if(archivo!=null && archivo.length>0){
//			
//			int x= 0;
//			
//			for (ArchivoDenuncia itemArchivo : prmDenuncia.getLstArchivoMedio()) {
//				
//				String tmpNombreArchivo = archivo[Integer.parseInt(itemArchivo.getPosicionArchivo())].getOriginalFilename(); 
//				
//				String folder = ResourceUtil.getKey("file.path.informe-atencion");
//				
//				switch (itemArchivo.getTipoArchivo().getCodigoRegistro()) {
//					case 1:
//						folder = "imagenes" + separador + "{id}"+ separador;
//						break;
//					case 2:
//						folder = "videos" + separador + "{id}"+ separador;
//						break;
//					case 3:
//						folder = "audios" + separador + "{id}"+ separador;
//						break;
//					case 4:
//						folder = "documentos" + separador + "{id}"+ separador;
//						break;
//					case 5:
//						folder = "otros" + separador + "{id}"+ separador;
//						break;
//					default:
//						break;
//				}
//				
//				folder = folder.replace("{id}", String.valueOf(idGenerado));
//				
//				String flagAlfresco = ResourceUtil.getKey("file.alfreso");
//				
//				if(!flagAlfresco.equals("0")) {
//					
//				  	/** EL SIGUIENTE CODIGO ES LA INTEGRACION CON WSALFRESCO **/
//					String uiid = AlfrescoUtil.grabarArchivoAlfresco(archivo[Integer.parseInt(itemArchivo.getPosicionArchivo())], folder);
//					
//					if (VO.isNullOrEmpty(uiid))//if (VO.isNullOrEmpty(uiid) || "error".equals(uiid))
//					{
//						respuesta.setMensaje("No se pudo archivar el documento en Alfresco.");
//						return respuesta;
//					}
//					
//					itemArchivo.setUiid(uiid);
//
//				} else {
//
//					String rutaArchivoCopiado = gn.copiarArchivo(
//							tmpNombreArchivo, 
//							folder, 
//							archivo[Integer.parseInt(itemArchivo.getPosicionArchivo())].getInputStream());
//					
//					if (VO.isNullOrEmpty(rutaArchivoCopiado)) {
//						respuesta.setMensaje("No se pudo archivar el documento.");
//						return respuesta;
//					}
//				}
//								
//				itemArchivo.setRutaArchivoDenuncia(folder);
//				itemArchivo.setMimeTypeArchivo(archivo[Integer.parseInt(itemArchivo.getPosicionArchivo())].getContentType());
//				itemArchivo.setIdDenuncia(idGenerado);
//				itemArchivo.setTipoArchivoDenuncia(itemArchivo.getTipoArchivo().getCodigoRegistro());
//				itemArchivo.setNombreArchivo(CodeUtil.parseEncode(tmpNombreArchivo));
//			
//				itemArchivo.setDescripcionArchivo(CodeUtil.parseEncode(itemArchivo.getDescripcionArchivo()));
//				archivoDenunciaService.insertar(itemArchivo);
//
//				x=x+1;
//			}
//			
//			
//			/* */
//		}
//
//		/***********************************************************************************************/
//		/***																						 ***/
//		/***********************************************************************************************/
//	
//		
//		/********************** Fin  Registrar Archivos Denuncia *************************/
//		
//		
//		/************************ Generar Codigo ******************************/
//		Denuncia prmDataDenunciaObtenida = denunciaService.buscarPorId(idGenerado);
//		String CodigoDenuncia="";
//		
//		int year= Calendar.getInstance().get(Calendar.YEAR);
//		CodigoDenuncia= n.generarCodigo(8, "DEN-", String.valueOf(prmDataDenunciaObtenida.getCodigoDenuncia()))+"-"+String.valueOf(year);
//		prmDenuncia.setCodigoDenuncia(CodigoDenuncia);
//		
//		denunciaService.generarCodigo(prmDenuncia);
//		
//		/************************ Fin Generar Codigo ******************************/
//		
//		if(idGenerado>0)
//		{	
//			if(lstDenunciante!=null){
//				for(int i = 0; i<lstDenunciante.size();i++){
//					
//					lstDenunciante.get(i).setIdDenuncia(idGenerado);
//					denuncianteService.insertar(lstDenunciante.get(i));
//					
//					Bandeja prmBandeja = new Bandeja();
//					prmBandeja.setTipoResponsable(lstDenunciante.get(i).getTipoPersona());
//					prmBandeja.setIdResponsable(lstDenunciante.get(i).getIdPersona());
//					prmBandeja.setEstado(1);
//					prmBandeja =bandejaService.validarBandeja(prmBandeja);
//					
//					BandejaDetalle prmBandejaDetalle = new BandejaDetalle();
//					prmBandejaDetalle.setIdBandeja(prmBandeja.getIdBandeja());
//					prmBandejaDetalle.setTipoBandeja(1);
//					prmBandejaDetalle.setIdDenuncia(prmDenuncia.getIdDenuncia());
//					prmBandejaDetalle.setTipoAsignacion(0);
//					prmBandejaDetalle.setEstado(1);
//					
//					
//					prmBandejaDetalle.setHostIp(UtilRequest.getClientIpAddress(request));
//					prmBandejaDetalle.setPrm1(prmDenuncia.getPrm1());
//					bandejaDetalleService.insertar(prmBandejaDetalle);
//					
//					ContactoPersona contactoPersona = new ContactoPersona();
//					contactoPersona.setTipoPersona(lstDenunciante.get(i).getTipoPersona());
//					contactoPersona.setIdPersona(lstDenunciante.get(i).getIdPersona());
//					List<ContactoPersona> lstContactoPersona = null;
//					lstContactoPersona = (List<ContactoPersona>) contactoPersonaService.listar(contactoPersona);
//					
//					for (ContactoPersona contacto : lstContactoPersona) {
//						
//						if(contacto.getTipoContacto()==2){
//							FormatoCorreo formatoCorreo = new FormatoCorreo();
//							long idFormatoCorreo = 1;
//							formatoCorreo = formatoCorreoService.buscarPorId(idFormatoCorreo);
//							
//							String texto1 = "";
//							String texto2 = "";
//							String texto3 = "";
//							String texto4 = "";
//							
//							String mensaje = formatoCorreo.getMensaje();
//							if(lstDenunciante.get(i).getTipoPersona()==1){
//								texto1 ="Sr(a): " + lstDenunciante.get(i).getPersonaDenunciante().getPrimerNombre() + " " + lstDenunciante.get(i).getPersonaDenunciante().getSegundoNombre()+" " + lstDenunciante.get(i).getPersonaDenunciante().getApellidoPaterno() + " "+lstDenunciante.get(i).getPersonaDenunciante().getApellidoMaterno();
//							}else{
//								texto1 ="Sres. " +lstDenunciante.get(i).getEntidadDenunciante().getRazonSocial();
//							}
//							texto2 ="Ha registrado con exito su denuncia ambiental";
//							texto3 = "Codigo Denuncia 	 :" + prmDenuncia.getCodigoDenuncia();
//							texto4 = "Codigo Acceso :" + prmDenuncia.getCodigoAcceso();
//							mensaje = mensaje.replace("TextoCorreo1", texto1);
//							mensaje = mensaje.replace("TextoCorreo2", texto2);
//							mensaje = mensaje.replace("TextoCorreo3", texto3);
//							mensaje = mensaje.replace("TextoCorreo4", texto4);
//							mensaje = mensaje.replace("TextoCorreo5", "");
//							mensaje = mensaje.replace("TextoCorreo6", "");
//							mensaje = mensaje.replace("TextoCorreo7", "");
//							
//							EmailAttachmentSender.sendEmailWithAttachments(contacto.getValor(),  "Registro Denuncia "+prmDenuncia.getCodigoDenuncia(), mensaje, null);
//							
//							
//						}
//					
//					}
//					
//					
//				
//					
//				}
//			}
//			
//			CasoEfa prmCasoEfa = new CasoEfa();
//			
//			prmCasoEfa.getCaso().setIdCaso(prmDenuncia.getCaso().getIdCaso());
//			prmCasoEfa.getEfa().setDepartamento(prmDenuncia.getDepartamento());
//			prmCasoEfa.getEfa().setProvincia(prmDenuncia.getProvincia());
//			prmCasoEfa.getEfa().setDistrito(prmDenuncia.getDistrito());
//			
//			List<CasoEfa> lstEfasCaso = buscarEfasPorCaso(prmCasoEfa);
////			List<CasoEfa> lstEfasCaso = (List<CasoEfa>) casoEfaService.listarEfasXCaso(prmCasoEfa);
//		
//			if(lstEfasCaso!=null){
//			
//				for(int i =0;i<lstEfasCaso.size();i++){
//					
////					Efa prmEfa =  efaService.buscarPorId(lstEfasCaso.get(i).getEfa().getIdEfa());
//					Efa prmEfa = lstEfasCaso.get(i).getEfa();
//					
//					
//					/*********************************************/
//					Bandeja prmBandeja = new Bandeja();
//					prmBandeja.setTipoResponsable(4);  // 3 -- OEFA  -- 4 EFA
//					prmBandeja.setIdEFa(lstEfasCaso.get(i).getEfa().getIdEfa());
//					prmBandeja.setEstado(1);
//					prmBandeja =bandejaService.validarBandejaEfa(prmBandeja);
//					BandejaDetalle prmBandejaDetalle = new BandejaDetalle();
//					prmBandejaDetalle.setIdBandeja(prmBandeja.getIdBandeja());
//					prmBandejaDetalle.setTipoBandeja(3);
//					prmBandejaDetalle.setIdDenuncia(prmDenuncia.getIdDenuncia());
//					prmBandejaDetalle.setTipoAsignacion(lstEfasCaso.get(i).getTipoAsignacion().getCodigoRegistro());
//					prmBandejaDetalle.setEstado(1);
//					
//					if(lstEfasCaso.get(i).getTipoAsignacion().getCodigoRegistro()==2){
//						prmBandejaDetalle.setEstado(10);
//					}
//					
//					prmBandejaDetalle.setHostIp(UtilRequest.getClientIpAddress(request));
//					prmBandejaDetalle.setPrm1(prmDenuncia.getPrm1());
//					
//					bandejaDetalleService.insertar(prmBandejaDetalle);
//					
//					/**********************************************/
//										
//					/************************************************/
//					/*Notificaciones prmNotificaciones = new Notificaciones();
//					prmNotificaciones.setTipoNotificacion(1);
//					prmNotificaciones.setIdBandeja(prmBandeja.getIdBandeja());
//					prmNotificaciones.setDescripcionNotificacion("Se ha registrado la Denuncia Nº " + CodigoDenuncia + " para su Atencion");
//					notificacionesService.insertar(prmNotificaciones);*/
//					
//					/*************************************************/
//					
//					
//
//					if(prmEfa.getCorreo()!=null){
//						
//						
//						FormatoCorreo formatoCorreo = new FormatoCorreo();
//						long idFormatoCorreo = 1;
//						formatoCorreo = formatoCorreoService.buscarPorId(idFormatoCorreo);
//						
//						String texto1 = "";
//						String texto2 = "";
//						String texto3 = "";
//						String texto4 = "";
//						
//						String mensaje = formatoCorreo.getMensaje();
//
//						
//						texto1 ="Sres. " +prmEfa.getNombre();
//						
//						String dataTipoAtencion = "atención";
//						if(lstEfasCaso.get(i).getTipoAsignacion().getCodigoRegistro()==2){
//							dataTipoAtencion = "conocimiento";
//						}
//						texto2 ="Se ha Generado la Denuncia Nº " + prmDenuncia.getCodigoDenuncia() +" para su "+dataTipoAtencion;
//					
//						
//						
//						mensaje = mensaje.replace("TextoCorreo1", texto1);
//						mensaje = mensaje.replace("TextoCorreo2", texto2);
//						mensaje = mensaje.replace("TextoCorreo3", texto3);
//						mensaje = mensaje.replace("TextoCorreo4", texto4);
//						mensaje = mensaje.replace("TextoCorreo5", "");
//						mensaje = mensaje.replace("TextoCorreo6", "");
//						mensaje = mensaje.replace("TextoCorreo7", "");
//						
//					
//						
//						/************************ Registrar Correo **************************/
//						
//						   String mailTo = prmEfa.getCorreo();
//					     String subject =  "Registro Denuncia "+prmDenuncia.getCodigoDenuncia() +" para su " + dataTipoAtencion;
////					     FormatoCorreo_1 fr = new FormatoCorreo_1();
////					     String message =fr.formatoCorreoOrganosCompetentes(CodigoDenuncia);
//					
//					     // attachments
//					    
//					    /* attachFiles[0] = "C:/sinada/problematica/7/archivo/bibliografiasReporte (29).pdf";
//					     attachFiles[1] = "C:/sinada/problematica/7/archivo/bibliografiasReporte (30).pdf";
//					     attachFiles[2] = "C:/sinada/problematica/7/archivo/bibliografiasReporte (31).pdf";
//					*/
//					     try {
//					     	EmailAttachmentSender.sendEmailWithAttachments( mailTo,subject, mensaje, null);
//					         System.out.println("Email sent.");
//					     } catch (Exception ex) {
//					         System.out.println("Could not send email.");
//					         ex.printStackTrace();
//					     }
//						
//						/***********************************************************/
//					}
//				
//					
//				}
//			}
//			
//			int contadorCompetenciaOefa = 0;
//			/******************************************************************************/
//			/***  			ATENCION	CASOS OEFA 									*********/
//			/*******************************************************************************/
//			CasoOefa prmCasoOefa = new CasoOefa();
//			prmCasoOefa.getCaso().setIdCaso(prmDenuncia.getCaso().getIdCaso());
//			
//			List<CasoOefa> lstCasoOefa = (List<CasoOefa>) casoOefaService.listarEfasXCaso(prmCasoOefa);
//			if(lstCasoOefa!=null){
//				
//				for(int e = 0; e<lstCasoOefa.size();e++){
//					
//					
//					
//					Bandeja prmBandeja = new Bandeja();
//					prmBandeja.setTipoResponsable(3);// 3 -- OEFA  -- 4 EFA
////					prmBandeja.setIdResponsable(lstPersonaOefa.get(s).getIdPersonaOefa());
//					prmBandeja.setDireccion(lstCasoOefa.get(e).getDireccionSupervision().getCodigoRegistro());
//					prmBandeja.setSubDireccion(lstCasoOefa.get(e).getDireccionEvaluacion().getCodigoRegistro());
//					prmBandeja.setCoordinacion(0);
//					prmBandeja.setEstado(1);
//					prmBandeja =bandejaService.validarBandejaOefa(prmBandeja);
//					
//					BandejaDetalle prmBandejaDetalle = new BandejaDetalle();
//					prmBandejaDetalle.setIdBandeja(prmBandeja.getIdBandeja());
//					prmBandejaDetalle.setTipoBandeja(3);
//					prmBandejaDetalle.setIdDenuncia(prmDenuncia.getIdDenuncia());
//					prmBandejaDetalle.setTipoAsignacion(lstCasoOefa.get(e).getTipoAsignacion().getCodigoRegistro());
//					prmBandejaDetalle.setEstado(1);
//					// getTipoAsignacion().getCodigoRegistro() 1 Atencion
//					if(lstCasoOefa.get(e).getTipoAsignacion().getCodigoRegistro()==2){ // 2 : conocimiento
//						prmBandejaDetalle.setEstado(10);
//					}
//					
//					if(lstCasoOefa.get(e).getTipoAsignacion().getCodigoRegistro()==1){ // 1 : atencion
//						contadorCompetenciaOefa = contadorCompetenciaOefa+ 1;
//					}
//					
//					prmBandejaDetalle.setHostIp(UtilRequest.getClientIpAddress(request));
//					prmBandejaDetalle.setPrm1(prmDenuncia.getPrm1());
//					bandejaDetalleService.insertar(prmBandejaDetalle);
//					
//					PersonaOefa prmPersonaOefa = new PersonaOefa();
//					List<PersonaOefa> lstPersonaOefa = null;
//					prmPersonaOefa.getDireccion().setCodigoRegistro(prmBandeja.getDireccion());
//					prmPersonaOefa.getSubDireccion().setCodigoRegistro(prmBandeja.getSubDireccion());
//					lstPersonaOefa = (List<PersonaOefa>) personaOefaService.listar(prmPersonaOefa);
//					
//					for (PersonaOefa personaOefa : lstPersonaOefa) {
//						
//						ContactoPersona contactoPersona = new ContactoPersona();
//						List<ContactoPersona> lstContactoPersona = null;
//						contactoPersona.setTipoPersona(1);
//						contactoPersona.setIdPersona(personaOefa.getPersona().getIdPersona());
//						lstContactoPersona = (List<ContactoPersona>) contactoPersonaService.listar(contactoPersona);
//						
//						for (ContactoPersona contacto : lstContactoPersona) {
//							
//							if(contacto.getTipoContacto()==2){
//								FormatoCorreo formatoCorreo = new FormatoCorreo();
//								long idFormatoCorreo = 1;
//								formatoCorreo = formatoCorreoService.buscarPorId(idFormatoCorreo);
//								
//								String texto1 = "";
//								String texto2 = "";
//								String texto3 = "";
//								String texto4 = "";
//								
//								String mensaje = formatoCorreo.getMensaje();
//								
//								texto1 ="Sres. " +personaOefa.getPersona().getNombreCompleto();
//									
//								String dataTipoAtencionOefa="atención";
//								if(lstCasoOefa.get(e).getTipoAsignacion().getCodigoRegistro()==2){
//									dataTipoAtencionOefa = "conocimiento";
//								}
//								
//								texto2 ="Se ha Generado la Denuncia Nº " + prmDenuncia.getCodigoDenuncia() +" para su " + dataTipoAtencionOefa;
//							
//								mensaje = mensaje.replace("TextoCorreo1", texto1);
//								mensaje = mensaje.replace("TextoCorreo2", texto2);
//								mensaje = mensaje.replace("TextoCorreo3", texto3);
//								mensaje = mensaje.replace("TextoCorreo4", texto4);
//								mensaje = mensaje.replace("TextoCorreo5", "");
//								mensaje = mensaje.replace("TextoCorreo6", "");
//								mensaje = mensaje.replace("TextoCorreo7", "");
//								
//								EmailAttachmentSender.sendEmailWithAttachments(contacto.getValor(),  "Registro Denuncia "+prmDenuncia.getCodigoDenuncia(), mensaje, null);
//							}
//						}
//					}
//					
//				
//				/*	Notificaciones prmNotificaciones = new Notificaciones();
//					prmNotificaciones.setTipoNotificacion(1);
//					prmNotificaciones.setIdBandeja(prmBandeja.getIdBandeja());
//					prmNotificaciones.setDescripcionNotificacion("Se ha registrado la Denuncia Nº " + CodigoDenuncia + " para su Atencion");
//					notificacionesService.insertar(prmNotificaciones);
//					*/
//				}
//				
//			}
//			
//			/****************************************************/
//			// STDRegistrarDenuncia
//			if(contadorCompetenciaOefa!=0){
//				//prmDenuncia.getCodigoDenuncia()
//				STDDenunciaBean stdDenunciaResponse = STDRegistrarDenuncia(prmDenuncia);
//				if (stdDenunciaResponse != null) {
//					prmDenuncia.setHojaTramite(stdDenunciaResponse.getHOJANUEVA());
//					denunciaService.subirHojaTramite(prmDenuncia);
//				}
//			}
//			//STDRegistrarDenuncia(prmDenuncia);
//			
//			/***********************************************************************************************/
//			/*********																				********/
//			/***********************************************************************************************/
//			
//
//			/************************** Bandeja Coordinador sinada : Asignar Denuncias 2 ***********************************/
//			
////			Bandeja prmBandejaCoordinador = new Bandeja();
////			prmBandejaCoordinador.setTipoResponsable(1);
////			prmBandejaCoordinador.setIdResponsable(/*lstPersonaOefa.get(s).getPersona().getIdPersona()*/usuario.getIdPersona());
////			prmBandejaCoordinador.setEstado(1);
////			prmBandejaCoordinador =bandejaService.validarBandeja(prmBandejaCoordinador);
//			
//			BandejaDetalle prmBandejaDetalle = new BandejaDetalle();
//			prmBandejaDetalle.setIdBandeja(0);
//			prmBandejaDetalle.setTipoBandeja(2);
//			prmBandejaDetalle.setIdDenuncia(prmDenuncia.getIdDenuncia());
//			prmBandejaDetalle.setTipoAsignacion(0);
//			prmBandejaDetalle.setEstado(1);
//			
//			prmBandejaDetalle.setHostIp(UtilRequest.getClientIpAddress(request));
//			prmBandejaDetalle.setPrm1(prmDenuncia.getPrm1());
//			bandejaDetalleService.insertar(prmBandejaDetalle);
//			
//			
//			
//			Usuario prmUsuario = new Usuario();
//			String idPerfilCoordinadorSinada = ResourceUtil.getKey("IdPerfilSSO_Coordinador");
//			prmUsuario.setIdPerfil(Long.valueOf(idPerfilCoordinadorSinada));
//			
//			List<Usuario> lstUsuario = null;
//			lstUsuario = (List<Usuario>) usuarioService.listar(prmUsuario);
//			
//			for (Usuario usuario : lstUsuario) {
////				Bandeja prmBandejaCoordinador = new Bandeja();
////				prmBandejaCoordinador.setTipoResponsable(1);
////				prmBandejaCoordinador.setIdResponsable(/*lstPersonaOefa.get(s).getPersona().getIdPersona()*/usuario.getIdPersona());
////				prmBandejaCoordinador.setEstado(1);
////				prmBandejaCoordinador =bandejaService.validarBandeja(prmBandejaCoordinador);
////				
////				BandejaDetalle prmBandejaDetalle = new BandejaDetalle();
////				prmBandejaDetalle.setIdBandeja(prmBandejaCoordinador.getIdBandeja());
////				prmBandejaDetalle.setTipoBandeja(2);
////				prmBandejaDetalle.setIdDenuncia(prmDenuncia.getIdDenuncia());
////				prmBandejaDetalle.setTipoAsignacion(0);
////				prmBandejaDetalle.setEstado(1);
////				
////				prmBandejaDetalle.setHostIp(UtilRequest.getClientIpAddress(request));
////				prmBandejaDetalle.setPrm1(prmDenuncia.getPrm1());
////				bandejaDetalleService.insertar(prmBandejaDetalle);
//				
//				ContactoPersona contactoPersona = new ContactoPersona();
//				List<ContactoPersona> lstContactoPersona = null;
//				contactoPersona.setTipoPersona(1);
//				contactoPersona.setIdPersona(usuario.getIdPersona());
//				lstContactoPersona = (List<ContactoPersona>) contactoPersonaService.listar(contactoPersona);
//				
//				for (ContactoPersona contacto : lstContactoPersona) {
//					
//					if(contacto.getTipoContacto()==2){
//						FormatoCorreo formatoCorreo = new FormatoCorreo();
//						long idFormatoCorreo = 1;
//						formatoCorreo = formatoCorreoService.buscarPorId(idFormatoCorreo);
//						
//						String texto1 = "";
//						String texto2 = "";
//						String texto3 = "";
//						String texto4 = "";
//						
//						String mensaje = formatoCorreo.getMensaje();
//						
//						texto1 ="Sr(a): " +usuario.getNombrePersona();
//													
//						texto2 ="Se ha Generado la Denuncia Nº " + prmDenuncia.getCodigoDenuncia() +" para su asignación.";
//					
//						mensaje = mensaje.replace("TextoCorreo1", texto1);
//						mensaje = mensaje.replace("TextoCorreo2", texto2);
//						mensaje = mensaje.replace("TextoCorreo3", texto3);
//						mensaje = mensaje.replace("TextoCorreo4", texto4);
//						mensaje = mensaje.replace("TextoCorreo5", "");
//						mensaje = mensaje.replace("TextoCorreo6", "");
//						mensaje = mensaje.replace("TextoCorreo7", "");
//						
//						EmailAttachmentSender.sendEmailWithAttachments(contacto.getValor(), "Registro Denuncia "+prmDenuncia.getCodigoDenuncia(), mensaje, null);
//					}
//				
//				}
//			}
////			prmDenuncia.setRutaArchivoFicha(generarPDFDenuncia(prmDenuncia, 2, 0));
//			VistaDenuncia fichaDenuncia =	denunciaService.obtenerDatosDenunciaGenerico(prmDenuncia);
//			respuesta.setValido(true);
//			respuesta.setMensaje("Se ha registrado la Denuncia satisfactoriamente");
////			respuesta.setData(prmDenuncia);
//			respuesta.setData(fichaDenuncia);
//			
//			
//		}else 
//		{
//			respuesta.setMensaje("No se pudo registrar la Denuncia");
//		}
//		
//		
//		return respuesta;
//		
//	}
	
	private STDDenunciaBean STDRegistrarDenuncia(Denuncia prmDenuncia) {
		// TODO Auto-generated method stub
		STDREST stdService = new STDREST();
		
		STDDenunciaBean stdDenuncia= new STDDenunciaBean();
		
		stdDenuncia.setCODIGOSINADA(prmDenuncia.getCodigoDenuncia());
		
		if (prmDenuncia.getTipoDenuncia() == 1)
		{
			stdDenuncia.setREMITENTE("");
		}
		else
		{
			Persona personaDenunciante = prmDenuncia.getLstDenunciante().get(0).getPersonaDenunciante();
			stdDenuncia.setREMITENTE(
					personaDenunciante.getNombres() + " " +
					(UtilValidator.isNullOrEmpty(personaDenunciante.getApellidoPaterno()) ? "" : personaDenunciante.getApellidoPaterno()) + " " +
					(UtilValidator.isNullOrEmpty(personaDenunciante.getApellidoMaterno()) ? "" : personaDenunciante.getApellidoMaterno()));
			//stdDenuncia.setREMITENTE(prmDenuncia.getLstDenunciante().get(0).get);
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("Problemática: ");
		sb.append(prmDenuncia.getCaso().getProblematica().getDescripcion());
		sb.append(" - ¿Debido a? nivel I: ");
		sb.append(prmDenuncia.getCaso().getDebidoA1().getDescripcion());
		if (prmDenuncia.getCaso().getDebidoA2()!= null && !UtilValidator.isNullOrEmpty(prmDenuncia.getCaso().getDebidoA2().getDescripcion())) {
			sb.append(" - ¿Debido a? nivel II: ");
			sb.append(prmDenuncia.getCaso().getDebidoA2().getDescripcion());	
		}
		if (prmDenuncia.getCaso().getDebidoA3()!= null && !UtilValidator.isNullOrEmpty(prmDenuncia.getCaso().getDebidoA3().getDescripcion())) {
			sb.append(" - ¿Debido a? nivel III: ");
			sb.append(prmDenuncia.getCaso().getDebidoA3().getDescripcion());
		}
		sb.append(" - Donde sucede nivel I: ");
		sb.append(prmDenuncia.getCaso().getZonasuceso1().getDescripcion());
		if (prmDenuncia.getCaso().getZonasuceso2()!= null && prmDenuncia.getCaso().getZonasuceso2().getDescripcion()!= null) {
			sb.append(" - Donde sucede nivel II: ");
			sb.append(prmDenuncia.getCaso().getZonasuceso2().getDescripcion());	
		}
		if (prmDenuncia.getCaso().getZonasuceso3()!= null && prmDenuncia.getCaso().getZonasuceso3().getDescripcion()!= null) {
			sb.append(" - Donde sucede nivel III: ");
			sb.append(prmDenuncia.getCaso().getZonasuceso3().getDescripcion());	
		}
		
		stdDenuncia.setDESCRIPCION(sb.toString());
		
		try {
			
			STDHojaTramiteBean stdHojaTramite = stdService.registrarDenunciaBody(stdDenuncia);
			System.out.println(stdHojaTramite);
			if (stdHojaTramite!=null && stdHojaTramite.getPCURSOR()!=null && stdHojaTramite.getPCURSOR().length>0) {
				return UtilValidator.isNullOrEmpty(stdHojaTramite.getPCURSOR()[0].getHOJANUEVA()) ? null : stdHojaTramite.getPCURSOR()[0];
			}
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}


	/************************************************************************************/
	
	private  List<CasoEfa> buscarEfasPorCaso(CasoEfa prmCasoEfa)throws Exception{
		
		List<CasoEfa> lst = new ArrayList<CasoEfa>();
		prmCasoEfa.getEfa().setTipoNivel(1);
		List<CasoEfa> lstEfasCaso = (List<CasoEfa>) casoEfaService.listarEfasXCasoXNivelYUbigeo(prmCasoEfa);
		
		if(lstEfasCaso!=null && lstEfasCaso.size()>0){
			lst.addAll(lstEfasCaso);
		}

		prmCasoEfa.getEfa().setTipoNivel(2);
		List<CasoEfa> lstEfasCasoRegional = (List<CasoEfa>) casoEfaService.listarEfasXCasoXNivelYUbigeo(prmCasoEfa);
		
		if(lstEfasCasoRegional!=null && lstEfasCasoRegional.size()>0){
			lst.addAll(lstEfasCasoRegional);
		}
		
		
		
		
		prmCasoEfa.getEfa().setTipoNivel(3);
		prmCasoEfa.getEfa().setTipoGobierno(2);
		List<CasoEfa> lstEfasCasoLocalDistrital = (List<CasoEfa>) casoEfaService.listarEfasXCasoXNivelYUbigeo(prmCasoEfa);
		
		if(lstEfasCasoLocalDistrital!=null && lstEfasCasoLocalDistrital.size()>0){
			lst.addAll(lstEfasCasoLocalDistrital);
		}else{
			prmCasoEfa.getEfa().setTipoNivel(3);
			prmCasoEfa.getEfa().setTipoGobierno(1);
			List<CasoEfa> lstEfasCasoLocalProvincial= (List<CasoEfa>) casoEfaService.listarEfasXCasoXNivelYUbigeo(prmCasoEfa);
			
			if(lstEfasCasoLocalProvincial!=null && lstEfasCasoLocalProvincial.size()>0){
				lst.addAll(lstEfasCasoLocalProvincial);
			}
		}
				
		return lst;
		
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/registrar-denuncia",method = RequestMethod.POST)
	public RespuestaHttp registrarDenuncia(@RequestBody Denuncia prmDenuncia,HttpServletRequest request) throws Exception
	{
		RespuestaHttp respuesta = new RespuestaHttp();
		try
		{
			respuesta =	denunciaService.registraDenunciaGenerico(prmDenuncia,null,request);

		} 
		catch (Exception e) 
		{
			logger.error(this.logBase+ " : registrarDenuncia" + e.getMessage());
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al registrar la denuncia");
		}
		return respuesta;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/registrar",method = RequestMethod.POST)
	public RespuestaHttp registrar(
			@RequestParam("file")MultipartFile[] archivo,
			@RequestParam("strContenido")String contenido,
			HttpServletRequest request)
					throws Exception{
		
		GenericController gn = new GenericController();
		RespuestaHttp respuesta = new RespuestaHttp();
		Denuncia prmDenuncia = new ObjectMapper().readValue(contenido, Denuncia.class);
		try
		{
			
			respuesta =	denunciaService.registraDenunciaGenerico(prmDenuncia,archivo,request);
			

		} 
		catch (Exception e) 
		{
			logger.error(this.logBase+ " : registrar" + e.getMessage());
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al registrar la denuncia");
		}
		return respuesta;
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/preVisualPDF",method = RequestMethod.POST)
	public RespuestaHttp generarPreVisualPDF(@RequestBody Denuncia denuncia,HttpServletRequest request)
					throws Exception{
		GenericController gn = new GenericController();
		RespuestaHttp respuesta = new RespuestaHttp();
		
		String url = "";
		
		String identificadorArchivo = VO.dateToString(new Date());
		identificadorArchivo = identificadorArchivo.replace("/", "-");
   		//ByteArrayOutputStream pdf = new ByteArrayOutputStream();
   		HttpHeaders responseHeaders = new HttpHeaders();
   		
   		try {
   			
   			Image logoOefa =  Image.getInstance(getClass().getResource("img/oefa-logo-header.png"));
   			logoOefa.scaleAbsolute(180,42);
   			logoOefa.setAlignment(Element.ALIGN_LEFT);
   			logoOefa.setAbsolutePosition(35, 770);
            
   			String nombreArchivo="denuncia.pdf";
			
			Font fuenteBold = new Font(Font.FontFamily.COURIER, 50, Font.BOLD);
	   		Font fuenteBoldCons = new Font(Font.FontFamily.COURIER, 40, Font.BOLD);
	   		Font fuenteSubBold = new Font(Font.FontFamily.COURIER, 14, Font.BOLDITALIC);
			Font fuenteNombre = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
			Font fuenteLinea = new Font(Font.FontFamily.TIMES_ROMAN, 3, Font.NORMAL);
			Font fuenteNormal = new Font(Font.FontFamily.UNDEFINED, 12, Font.NORMAL);
			Font fuenteItalic = new Font(Font.FontFamily.COURIER, 14, Font.ITALIC);
			Font fuenteNumeracion = new Font(Font.FontFamily.UNDEFINED, 12, Font.BOLD);
			Font fuenteCargo = new Font(Font.FontFamily.COURIER, 10, Font.ITALIC);
			
			responseHeaders.setContentType(MediaType.valueOf("application/pdf;charset=utf-8"));
			
			String direccion = ResourceUtil.getKey("file.path.denuncia.archivos.windows.base"); //"D:\\oefa\\denuncia\\;
			
			String serve = ResourceUtil.getKey("file.server");
			
			if(serve!=null && serve.trim().equals("2")){
				direccion = ResourceUtil.getKey("file.path.denuncia.archivos.linux.base");
			}
			
			direccion = direccion.replace("{separador}", File.separator);
			
			direccion = direccion+File.separator+"temporales"+ File.separator +"medios"+File.separator+identificadorArchivo;
			
			/************************/
			
			
			/*************************/
			
			
   			String urlTemp = direccion + File.separator +nombreArchivo;
   			String id = "";
   			for (int i = 0; i < 100000; i++) {
   				File fichero = new File(url);
   	   			if (!fichero.exists()){
   	   				id= "_"+ String.valueOf(i);
   	   				break;
   	   			}
			}
   			nombreArchivo = "denuncia"+id+".pdf";
   			url = direccion + File.separator +nombreArchivo;
   			
   			File directorio = new File(direccion);
   			directorio.mkdirs();
   			
   			File fichero = new File(url);
   			if (fichero.exists()){
   				fichero.delete();
   			}
   			
			Document documento = new Document(PageSize.A4,100, 100, 100, 100);
			PdfWriter pdfwDemo = PdfWriter.getInstance(documento, new FileOutputStream(url));
			
   			documento.open();
   			documento.add(logoOefa);
   			
   			documento.add(getLinea("1. Características de la Denuncia", fuenteNumeracion, 10));
   				documento.add(getLinea("1.1. Tipo de Denuncia", fuenteNumeracion, 40));
	   				if(denuncia.getMaestroTipoDenuncia()!=null){
	   					
						documento.add(getLinea(denuncia.getMaestroTipoDenuncia().getDescripcion(), fuenteNormal, 80));
					}else{
						documento.add(getLinea(" ", fuenteNormal, 80));
					}
   				documento.add(getLinea("1.2. Medio de Recepción", fuenteNumeracion, 40));
	   				if(denuncia.getMedioRecepcion()==0){
	   					documento.add(getLinea("Virtual", fuenteNormal, 80));
	   				}else{
						List<Maestro> lstMaestroMedioRecepcion = null;
						Maestro prmMedioRecepcion = new Maestro();
						prmMedioRecepcion.setCodigoMaestro("MedioRecepcion");
						lstMaestroMedioRecepcion = (List<Maestro>) maestroService.buscarPorCodigoTabla(prmMedioRecepcion);
						for (Maestro maestro : lstMaestroMedioRecepcion) {
							
							if(maestro.getCodigoRegistro()==denuncia.getMedioRecepcion()){
								
								documento.add(getLinea(maestro.getDescripcion(), fuenteNormal, 80));
								
							}	
						}
					}
//	   				if(denuncia.getMaestroTipoDenuncia().getCodigoRegistro()==3){
//	   					
//	   				}
	   			if (denuncia.getMedioRecepcion() == 1)//Medio Fisico
	   			{
	   				documento.add(getLinea("1.3. Hoja de trámite", fuenteNumeracion, 40));
		   			documento.add(getLinea(denuncia.getHojaTramite(), fuenteNormal, 80));
				}
   			documento.add(getLinea("2. Datos del Denunciante", fuenteNumeracion, 10));
   			if(denuncia.getMaestroTipoDenuncia().getCodigoRegistro()!=1 && denuncia.getMaestroTipoDenuncia().getCodigoRegistro()!=2){
   				if(denuncia.getLstDenunciante()!=null && denuncia.getLstDenunciante().size()>0){
   	   				int cont = 1;
   	   				for (Denunciante denunciante : denuncia.getLstDenunciante()) {
   	   					documento.add(getLinea(" " , fuenteNumeracion, 80));
   	   					
   	   					documento.add(getLinea("Denunciante N° " + String.valueOf(cont), fuenteNumeracion, 80));
   	   					documento.add(getLinea(" " , fuenteNumeracion, 80));
   	   					
   	   					PdfPTable table = new PdfPTable(2);
   	   					table.setTotalWidth(350f);
   	   					if(denunciante.getTipoPersona()==1){
   	   					PdfPCell celda1 = new PdfPCell(new Phrase(getLinea("DNI",fuenteNormal,0)));
							celda1.setBorder(0);
							celda1.setHorizontalAlignment(Element.ALIGN_LEFT);
							
							PdfPCell celda2 = new PdfPCell(new Phrase(getLinea(denunciante.getPersonaDenunciante().getDocumento(),fuenteNormal,0)));
							celda2.setBorder(0);
							celda2.setHorizontalAlignment(Element.ALIGN_LEFT);
						
							PdfPCell celda3 = new PdfPCell(new Phrase(getLinea("Nombres",fuenteNormal,0)));
							celda3.setBorder(0);
							celda3.setHorizontalAlignment(Element.ALIGN_LEFT);
							
							PdfPCell celda4 = new PdfPCell(new Phrase(getLinea(denunciante.getPersonaDenunciante().getNombres(),fuenteNormal,0)));
							celda4.setBorder(0);
							celda4.setHorizontalAlignment(Element.ALIGN_LEFT);
	   						
							PdfPCell celda5 = new PdfPCell(new Phrase(getLinea("Apellido Paterno",fuenteNormal,0)));
							celda5.setBorder(0);
							celda5.setHorizontalAlignment(Element.ALIGN_LEFT);
							
							PdfPCell celda6 = new PdfPCell(new Phrase(getLinea(denunciante.getPersonaDenunciante().getApellidoPaterno(),fuenteNormal,0)));
							celda6.setBorder(0);
							celda6.setHorizontalAlignment(Element.ALIGN_LEFT);
							
							PdfPCell celda7 = new PdfPCell(new Phrase(getLinea("Apellido Materno",fuenteNormal,0)));
							celda7.setBorder(0);
							celda7.setHorizontalAlignment(Element.ALIGN_LEFT);
							
							PdfPCell celda8 = new PdfPCell(new Phrase(getLinea(denunciante.getPersonaDenunciante().getApellidoMaterno(),fuenteNormal,0)));
							celda8.setBorder(0);
							celda8.setHorizontalAlignment(Element.ALIGN_LEFT);
							
							PdfPCell celda9 = new PdfPCell(new Phrase(getLinea("Dirección",fuenteNormal,0)));
							celda9.setBorder(0);
							celda9.setHorizontalAlignment(Element.ALIGN_LEFT);
							
							PdfPCell celda10 = new PdfPCell(new Phrase(getLinea(denunciante.getPersonaDenunciante().getDireccion(),fuenteNormal,0)));
							celda10.setBorder(0);
							celda10.setHorizontalAlignment(Element.ALIGN_LEFT);
							
							table.addCell(celda1);
							table.addCell(celda2);
							table.addCell(celda3);
							table.addCell(celda4);
							table.addCell(celda5);
							table.addCell(celda6);
							table.addCell(celda7);
							table.addCell(celda8);
							table.addCell(celda9);
							table.addCell(celda10);
   	   						
   	   					}else{
   	   						
   	   					PdfPCell celda1 = new PdfPCell(new Phrase(getLinea("RUC",fuenteNormal,0)));
   	   					celda1.setBorder(0);
   	   					celda1.setHorizontalAlignment(Element.ALIGN_LEFT);
						
						PdfPCell celda2 = new PdfPCell(new Phrase(getLinea(denunciante.getEntidadDenunciante().getRuc(),fuenteNormal,0)));
						celda2.setBorder(0);
						celda2.setHorizontalAlignment(Element.ALIGN_LEFT);
					
						PdfPCell celda3 = new PdfPCell(new Phrase(getLinea("Razón Social",fuenteNormal,0)));
						celda3.setBorder(0);
						celda3.setHorizontalAlignment(Element.ALIGN_LEFT);
						
						PdfPCell celda4 = new PdfPCell(new Phrase(getLinea(denunciante.getEntidadDenunciante().getRazonSocial(),fuenteNormal,0)));
						celda4.setBorder(0);
						celda4.setHorizontalAlignment(Element.ALIGN_LEFT);
   						
						PdfPCell celda5 = new PdfPCell(new Phrase(getLinea("Direccion",fuenteNormal,0)));
						celda5.setBorder(0);
						celda5.setHorizontalAlignment(Element.ALIGN_LEFT);
						
						PdfPCell celda6 = new PdfPCell(new Phrase(getLinea(denunciante.getEntidadDenunciante().getDireccion(),fuenteNormal,0)));
						celda6.setBorder(0);
						celda6.setHorizontalAlignment(Element.ALIGN_LEFT);
						
						
						
						table.addCell(celda1);
						table.addCell(celda2);
						table.addCell(celda3);
						table.addCell(celda4);
						table.addCell(celda5);
						table.addCell(celda6);
						
						
						
//   	   					PdfPCell celda1 = new PdfPCell(new Phrase(getLinea("DNI",fuenteNormal,0)));
//						celda1.setBorder(0);
//						celda1.setHorizontalAlignment(Element.ALIGN_LEFT);
//						
//						PdfPCell celda2 = new PdfPCell(new Phrase(getLinea(denunciante.getEntidadDenunciante().getRuc(),fuenteNormal,0)));
//						celda2.setBorder(0);
//						celda2.setHorizontalAlignment(Element.ALIGN_LEFT);
//					
//						PdfPCell celda3 = new PdfPCell(new Phrase(getLinea("Nombres",fuenteNormal,0)));
//						celda3.setBorder(0);
//						celda3.setHorizontalAlignment(Element.ALIGN_LEFT);
//						
//						PdfPCell celda4 = new PdfPCell(new Phrase(getLinea(denunciante.getEntidadDenunciante().getNombres(),fuenteNormal,0)));
//						celda4.setBorder(0);
//						celda4.setHorizontalAlignment(Element.ALIGN_LEFT);
//   						
//						PdfPCell celda5 = new PdfPCell(new Phrase(getLinea("Apellido Paterno",fuenteNormal,0)));
//						celda5.setBorder(0);
//						celda5.setHorizontalAlignment(Element.ALIGN_LEFT);
//						
//						PdfPCell celda6 = new PdfPCell(new Phrase(getLinea(denunciante.getEntidadDenunciante().getApellidoPaterno(),fuenteNormal,0)));
//						celda6.setBorder(0);
//						celda6.setHorizontalAlignment(Element.ALIGN_LEFT);
//						
//						PdfPCell celda7 = new PdfPCell(new Phrase(getLinea("Apellido Materno",fuenteNormal,0)));
//						celda7.setBorder(0);
//						celda7.setHorizontalAlignment(Element.ALIGN_LEFT);
//						
//						PdfPCell celda8 = new PdfPCell(new Phrase(getLinea(denunciante.getEntidadDenunciante().getApellidoMaterno(),fuenteNormal,0)));
//						celda8.setBorder(0);
//						celda8.setHorizontalAlignment(Element.ALIGN_LEFT);
//						
//						PdfPCell celda9 = new PdfPCell(new Phrase(getLinea("Dirección",fuenteNormal,0)));
//						celda9.setBorder(0);
//						celda9.setHorizontalAlignment(Element.ALIGN_LEFT);
//						
//						PdfPCell celda10 = new PdfPCell(new Phrase(getLinea(denunciante.getEntidadDenunciante().getDireccion(),fuenteNormal,0)));
//						celda10.setBorder(0);
//						celda10.setHorizontalAlignment(Element.ALIGN_LEFT);
//						
//						table.addCell(celda1);
//						table.addCell(celda2);
//						table.addCell(celda3);
//						table.addCell(celda4);
//						table.addCell(celda5);
//						table.addCell(celda6);
//						table.addCell(celda7);
//						table.addCell(celda8);
//						table.addCell(celda9);
//						table.addCell(celda10);
   	   					}
   		   					
   	   						
   							documento.add(table);
   							
   	   						cont++;
   					}
   	   			}else{
   	   				
   	   				if(denuncia.getMaestroTipoDenuncia().getCodigoRegistro()==1){
   	   				documento.add(getLinea("Denunciante ANONIMO " , fuenteNormal, 80));
   	   				}else{
   	   				documento.add(getLinea("Denunciante con reserva de identidad " , fuenteNormal, 80));
   	   				}
   	   				
   	   			}
   			}else{
   				if(denuncia.getMaestroTipoDenuncia().getCodigoRegistro()==1){
   	   				documento.add(getLinea("Denunciante ANONIMO " , fuenteNormal, 80));
   	   				}else{
   	   				documento.add(getLinea("Denunciante con reserva de identidad " , fuenteNormal, 80));
   	   				}
//   				documento.add(getLinea("Denunciante ANONIMO " , fuenteNormal, 80));
   			}
   			
   			documento.add(getLinea(" " , fuenteNumeracion, 80));
   			documento.add(getLinea("3. Datos de la Denuncia", fuenteNumeracion, 10));
   				documento.add(getLinea("3.1. Hechos Denunciados", fuenteNumeracion, 40));
   					if(denuncia.getCaso()!=null && denuncia.getCaso().getProblematica()!=null){
   						documento.add(getLinea("- Problematica ", fuenteNumeracion, 80));
   							documento.add(getLinea(denuncia.getCaso().getProblematica().getDescripcion(), fuenteNormal, 100));
   						if(denuncia.getCaso().getDebidoA1()!=null && denuncia.getCaso().getDebidoA1().getDescripcion()!=null){
   							documento.add(getLinea("- Debido A ", fuenteNumeracion, 80));
   								documento.add(getLinea(denuncia.getCaso().getDebidoA1().getDescripcion(), fuenteNormal, 100));
   						}
   						if(denuncia.getCaso().getDebidoA2()!=null && denuncia.getCaso().getDebidoA2().getDescripcion()!=null){
   							documento.add(getLinea("- Debido A ", fuenteNumeracion, 80));
   								documento.add(getLinea(denuncia.getCaso().getDebidoA2().getDescripcion(), fuenteNormal, 100));
   						}
   						if(denuncia.getCaso().getDebidoA3()!=null && denuncia.getCaso().getDebidoA3().getDescripcion()!=null){
   							documento.add(getLinea("- Debido A ", fuenteNumeracion, 80));
   								documento.add(getLinea(denuncia.getCaso().getDebidoA3().getDescripcion(), fuenteNormal, 100));
   						}
   						
   						
   						
   						
   					}
   					
				documento.add(getLinea("3.2. Donde Ocurren", fuenteNumeracion, 40));
				
				if(denuncia.getCaso().getZonasuceso1()!=null && denuncia.getCaso().getZonasuceso1().getDescripcion()!=null){
						documento.add(getLinea("- Donde Sucede ", fuenteNumeracion, 80));
							documento.add(getLinea(denuncia.getCaso().getZonasuceso1().getDescripcion(), fuenteNormal, 100));
					}
				
				if(denuncia.getCaso().getZonasuceso2()!=null && denuncia.getCaso().getZonasuceso2().getDescripcion()!=null){
					documento.add(getLinea("- Donde Sucede ", fuenteNumeracion, 80));
						documento.add(getLinea(denuncia.getCaso().getZonasuceso2().getDescripcion(), fuenteNormal, 100));
				}
				
				if(denuncia.getCaso().getZonasuceso3()!=null && denuncia.getCaso().getZonasuceso3().getDescripcion()!=null){
					documento.add(getLinea("- Donde Sucede ", fuenteNumeracion, 80));
						documento.add(getLinea(denuncia.getCaso().getZonasuceso3().getDescripcion(), fuenteNormal, 100));
				}
				
					documento.add(getLinea(denuncia.getDireccion(), fuenteNormal, 80));
					
					documento.add(getLinea((denuncia.getUbDepartamento()!=null?denuncia.getUbDepartamento().getDescripcionDepartamento():"") + " - " + (denuncia.getUbProvincia()!=null?denuncia.getUbProvincia().getDescripcion():"") + " - " + (denuncia.getUbDistrito()!=null?denuncia.getUbDistrito().getDescripcionDistrito():""), fuenteNormal, 80));
					documento.add(getLinea(denuncia.getReferencia(), fuenteNormal, 80));
   			documento.add(getLinea("4. Datos del Denunciado", fuenteNumeracion, 10));
   				documento.add(getLinea(" " , fuenteNumeracion, 80));

	   			switch (denuncia.getTipo_responsable()) {
					case 0:
						documento.add(getLinea(" --" , fuenteNumeracion, 80));
						break;
					case 1:
						Persona denunciado = personaService.buscarPorId(Long.valueOf(denuncia.getResponsableProblema()));
						
						PdfPTable table = new PdfPTable(2);
	   					table.setTotalWidth(350f);
		   					PdfPCell celda1 = new PdfPCell(new Phrase(getLinea("DNI",fuenteNormal,0)));
							celda1.setBorder(0);
							celda1.setHorizontalAlignment(Element.ALIGN_LEFT);
							
							PdfPCell celda2 = new PdfPCell(new Phrase(getLinea(denunciado.getDocumento(),fuenteNormal,0)));
							celda2.setBorder(0);
							celda2.setHorizontalAlignment(Element.ALIGN_LEFT);
						
							PdfPCell celda3 = new PdfPCell(new Phrase(getLinea("Nombres",fuenteNormal,0)));
							celda3.setBorder(0);
							celda3.setHorizontalAlignment(Element.ALIGN_LEFT);
							
							PdfPCell celda4 = new PdfPCell(new Phrase(getLinea(denunciado.getNombres(),fuenteNormal,0)));
							celda4.setBorder(0);
							celda4.setHorizontalAlignment(Element.ALIGN_LEFT);
	   						
							PdfPCell celda5 = new PdfPCell(new Phrase(getLinea("Apellido Paterno",fuenteNormal,0)));
							celda5.setBorder(0);
							celda5.setHorizontalAlignment(Element.ALIGN_LEFT);
							
							PdfPCell celda6 = new PdfPCell(new Phrase(getLinea(denunciado.getApellidoPaterno(),fuenteNormal,0)));
							celda6.setBorder(0);
							celda6.setHorizontalAlignment(Element.ALIGN_LEFT);
							
							PdfPCell celda7 = new PdfPCell(new Phrase(getLinea("Apellido Materno",fuenteNormal,0)));
							celda7.setBorder(0);
							celda7.setHorizontalAlignment(Element.ALIGN_LEFT);
							
							PdfPCell celda8 = new PdfPCell(new Phrase(getLinea(denunciado.getApellidoMaterno(),fuenteNormal,0)));
							celda8.setBorder(0);
							celda8.setHorizontalAlignment(Element.ALIGN_LEFT);
							
							PdfPCell celda9 = new PdfPCell(new Phrase(getLinea("Dirección",fuenteNormal,0)));
							celda9.setBorder(0);
							celda9.setHorizontalAlignment(Element.ALIGN_LEFT);
							
							PdfPCell celda10 = new PdfPCell(new Phrase(getLinea(denunciado.getDireccion(),fuenteNormal,0)));
							celda10.setBorder(0);
							celda10.setHorizontalAlignment(Element.ALIGN_LEFT);
							
							table.addCell(celda1);
							table.addCell(celda2);
							table.addCell(celda3);
							table.addCell(celda4);
							table.addCell(celda5);
							table.addCell(celda6);
							table.addCell(celda7);
							table.addCell(celda8);
							table.addCell(celda9);
							table.addCell(celda10);
	   						
							documento.add(table);
						break;
					case 2:
						Entidad entidad = entidadService.buscarPorId(Long.valueOf(denuncia.getResponsableProblema()));
						
						PdfPTable tableD = new PdfPTable(2);
	   					tableD.setTotalWidth(350f);
		   					PdfPCell celdaR = new PdfPCell(new Phrase(getLinea("RUC",fuenteNormal,0)));
		   					celdaR.setBorder(0);
		   					celdaR.setHorizontalAlignment(Element.ALIGN_LEFT);
							
							PdfPCell celdaRD = new PdfPCell(new Phrase(getLinea(entidad.getRuc(),fuenteNormal,0)));
							celdaRD.setBorder(0);
							celdaRD.setHorizontalAlignment(Element.ALIGN_LEFT);
						
							PdfPCell celdaRS = new PdfPCell(new Phrase(getLinea("Razón Social",fuenteNormal,0)));
							celdaRS.setBorder(0);
							celdaRS.setHorizontalAlignment(Element.ALIGN_LEFT);
							
							PdfPCell celdaRSD = new PdfPCell(new Phrase(getLinea(entidad.getRazonSocial(),fuenteNormal,0)));
							celdaRSD.setBorder(0);
							celdaRSD.setHorizontalAlignment(Element.ALIGN_LEFT);
	   						
							PdfPCell celdaDir = new PdfPCell(new Phrase(getLinea("Direccion",fuenteNormal,0)));
							celdaDir.setBorder(0);
							celdaDir.setHorizontalAlignment(Element.ALIGN_LEFT);
							
								PdfPCell celdaDirD = new PdfPCell(new Phrase(getLinea((entidad.getDireccion()!=null?entidad.getDireccion():""),fuenteNormal,0)));
								celdaDirD.setBorder(0);
								celdaDirD.setHorizontalAlignment(Element.ALIGN_LEFT);
							
							
							
							
							
							tableD.addCell(celdaR);
							tableD.addCell(celdaRD);
							tableD.addCell(celdaRS);
							tableD.addCell(celdaRSD);
							tableD.addCell(celdaDir);
							tableD.addCell(celdaDirD);
	   						
							documento.add(tableD);
						break;
					default:
						break;
				}
	   			documento.add(getLinea(" " , fuenteNumeracion, 80));
   			documento.add(getLinea("5. Medios Probatorios", fuenteNumeracion, 10));
   			
   				if(denuncia.getLstArchivoMedio()!=null && denuncia.getLstArchivoMedio().size()>0){
   					
   					
   					
   					List<String> tipoMedio = new ArrayList<String>();
   					
   					
   					for (ArchivoDenuncia archivo : denuncia.getLstArchivoMedio()) {
   						boolean swNombre= false; 
						for (String nombreTipo : tipoMedio) {
							if(nombreTipo.equals(archivo.getTipoArchivo().getDescripcion())){
								swNombre = true;
							}
						}
						if(!swNombre){
							tipoMedio.add(archivo.getTipoArchivo().getDescripcion());
						}
					}
   					
   					for (String nombreTipo : tipoMedio) {
   						documento.add(getLinea("- "+nombreTipo, fuenteNormal, 40));
   					}
   					
   				}else{
   					documento.add(getLinea("- Sin medios probatorios", fuenteNumeracion, 40));
   				}
   				
   				
   			documento.add(getLinea("6. Entidades Competentes", fuenteNumeracion, 10));
   			List<EntidadCaso> lstEntidadesCompetentes = new ArrayList<EntidadCaso>();
   			EntidadCaso entCas = new EntidadCaso();
   			entCas.setIdCaso(denuncia.getCaso().getIdCaso());
   			lstEntidadesCompetentes = (List<EntidadCaso>) entidadCasoService.listar(entCas);
   			if(lstEntidadesCompetentes!=null && lstEntidadesCompetentes.size()>0){
   				for (EntidadCaso object : lstEntidadesCompetentes) {
   					if(object.getTipoAsignacion()!=2){
   						if(object.getTipoEntidad()==1){
   	   						documento.add(getLinea(object.getNombreEntidad(), fuenteNumeracion, 40));
   	   	   	   				
   	   						List<NormaCaso> lstNormaCaso = new ArrayList<NormaCaso>();
   	   						NormaCaso prmNormaCaso = new NormaCaso();
   	   						prmNormaCaso.setTipoNormaCaso(new Maestro());
   	   						prmNormaCaso.getTipoNormaCaso().setCodigoRegistro(1);
   	   						prmNormaCaso.setIdCasoOefa(object.getIdEntidad());
   	   						lstNormaCaso = (List<NormaCaso>) normaCasoService.listar(prmNormaCaso);
   	   						
   	   						for (NormaCaso normaCaso : lstNormaCaso) {
   	   							documento.add(getLinea("- "+normaCaso.getContenidoNorma().getNombreTipoDispositivo()+" Nº "+normaCaso.getContenidoNorma().getNumeroNorma() +", Articulo Nº " + normaCaso.getContenidoNorma().getNumeroArticulo()+" - "+ normaCaso.getContenidoNorma().getDescripcionArticulo(), fuenteNormal, 80));
   							}
   	   						
   	   	   	   				
   	   					}
   					}
   					
   					
   	   			
   	   				
   	   				
   	   				
   				}
   				
   				CasoEfa prmCasoEfa = new CasoEfa();
   				

   				prmCasoEfa.getCaso().setIdCaso(denuncia.getCaso().getIdCaso());
   				
   				prmCasoEfa.getEfa().setDepartamento(denuncia.getUbDepartamento().getCodigoDepartamento());
   				
   				prmCasoEfa.getEfa().setProvincia(denuncia.getUbProvincia().getCodigoProvincia());
   				prmCasoEfa.getEfa().setDistrito(denuncia.getUbDistrito().getCodigoDistrito());
   				
   				List<CasoEfa> lstEfasCaso = buscarEfasPorCaso(prmCasoEfa);
   				
   				/**************************************************************/
   					for (CasoEfa casoEfa : lstEfasCaso) {
   						if(casoEfa.getTipoAsignacion().getCodigoRegistro()!=2){
   							
   							documento.add(getLinea(casoEfa.getEfa().getNombre(), fuenteNumeracion, 40));

   	   						List<NormaCaso> lstNormaCaso = new ArrayList<NormaCaso>();
   	   						NormaCaso prmNormaCaso = new NormaCaso();
   	   						prmNormaCaso.setTipoNormaCaso(new Maestro());
   	   						prmNormaCaso.getTipoNormaCaso().setCodigoRegistro(2);
   	   						prmNormaCaso.setIdCasoOefa(casoEfa.getIdCasoEfa());
   	   						lstNormaCaso = (List<NormaCaso>) normaCasoService.listar(prmNormaCaso);
   	   						
   	   						for (NormaCaso normaCaso : lstNormaCaso) {
   	   							documento.add(getLinea("- "+normaCaso.getContenidoNorma().getNombreTipoDispositivo()+" Nº "+normaCaso.getContenidoNorma().getNumeroNorma() +", Articulo Nº " + normaCaso.getContenidoNorma().getNumeroArticulo()+" - "+ normaCaso.getContenidoNorma().getDescripcionArticulo(), fuenteNormal, 80));
   							}
   	   						
   							
   							
   						}
   						
   						
   						
   						
//   	   	   				List<Normas> lstNormas = new ArrayList<Normas>();
//   	   	   				Normas filtro = new Normas();
//   	   	   				filtro.setEntidadEmisora(casoEfa.getIdCasoEfa());
//   	   	   				lstNormas = normasService.buscarPorEntidad(filtro);
//   	   	   				for (Normas normas : lstNormas) {
//   	   	   					documento.add(getLinea("- "+normas.getDescripcion(), fuenteNormal, 80));
//   						}
//   	   	   				
//   	   	   				
						
					}
   				
   				
   				/**************************************************************/
   				
   				
   			}else{
   				documento.add(getLinea("- Sin entidades competentes", fuenteNormal, 40));
   			}
   			

   			
   			
   			//documento.add(listado);
   			documento.close();
   			respuesta.setData(VO.getBasicPath() + "/temporales/"+identificadorArchivo+"/"+ nombreArchivo);
   		} catch (Exception e) {
			e.printStackTrace();
		}
   		
		
		return respuesta;
	}
	
	
	private Paragraph getLinea(String titulo,Font fuente,int identacion){
		Paragraph p = new Paragraph();
		
		Chunk c= new Chunk();
		p.setAlignment(Element.ALIGN_JUSTIFIED);
		c.append(titulo);
		c.setFont(fuente);
		p.add(c);
		p.setIndentationLeft(identacion);
		return p;
	}
	
	@ResponseBody
	@RequestMapping(value = "/registrar-hoja-tramite",method = RequestMethod.POST , consumes ={"multipart/form-data"})
	public RespuestaHttp registrarHojaTramite(
			@RequestParam("file")MultipartFile archivo,
			@RequestParam("strContenido")String contenido,
			HttpServletRequest request)
					throws Exception{
		
		GenericController gn = new GenericController();
		RespuestaHttp respuesta = new RespuestaHttp();
		Denuncia prmDenuncia = new ObjectMapper().readValue(contenido, Denuncia.class);
		try
		{
			String tmpNombreArchivo = archivo.getOriginalFilename(); 
			int caracteres = Integer.parseInt(ResourceUtil.getKey("maximo_caracteres_archivo"));
			if (tmpNombreArchivo.length()>caracteres) {
				respuesta.setMensaje("El nombre del archivo no debe superar los "+ResourceUtil.getKey("maximo_caracteres_archivo")+" caracteres. "
						+ "Intente renombrarlo y vuelva a cargarlo.");
				return respuesta;
			}
			
			
			
			String folder = ResourceUtil.getKey("file.path.denuncias-hoja-tramite");
			folder = folder.replace("{id}", String.valueOf(prmDenuncia.getIdDenuncia()));
			String rutaArchivoCopiado = gn.copiarArchivo(
					tmpNombreArchivo, 
					folder, 
					archivo.getInputStream());
			
			if (VO.isNullOrEmpty(rutaArchivoCopiado)) {
				respuesta.setMensaje("No se pudo archivar el documento.");
				return respuesta;
			}
			
			
			prmDenuncia.setHojaTramite(tmpNombreArchivo);
			prmDenuncia.setRutaHojaTramite(rutaArchivoCopiado);
		
			
			int result = denunciaService.subirHojaTramite(prmDenuncia);
		
			
			if(result>0)
			{	
				
				
				
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha registrado la denuncia satisfactoriamente");
				respuesta.setData(prmDenuncia);
			} 
			else 
			{
				respuesta.setMensaje("No se pudo registrar la denuncia");
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al registrar la denuncia");
		}
		return respuesta;
	}
	
	
	@ResponseBody
	@RequestMapping(value ="/actualizar-estado", method = RequestMethod.POST)
	public  RespuestaHttp actualizar(@RequestBody Denuncia prmData,HttpServletRequest request)throws Exception {
		RespuestaHttp respuesta = new RespuestaHttp();
		try{
			int result = denunciaService.cambiarEstado(prmData);
			
			if(result>0){
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha actualizado	la denuncia satisfactoriamente");
			}else {
				respuesta.setMensaje("No se pudo actualizar la denuncia");
			}
		}catch (Exception e) {
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al actualizar la denuncia");
		}
		return respuesta;
	}
	


	@ResponseBody
	@RequestMapping(value = "/eliminar", method = RequestMethod.POST)
	public RespuestaHttp eliminar(
			@RequestBody Denuncia prmDenuncia,HttpServletRequest request)
			throws Exception{
		RespuestaHttp respuesta = new RespuestaHttp();
		try{
			int result = denunciaService.eliminar(prmDenuncia);
			if(result>0){
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha eliminado la denuncia correctamente");
			}else {
				respuesta.setMensaje("No se pudo eliminar la denuncia");
			}
		} catch (Exception e){
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al eliminar la denuncia");
		}
		return respuesta;
	}
	
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/buscarDenunciantes", method=RequestMethod.POST)
	public RespuestaHttp buscarDenunciantes(@RequestBody DenuncianteDetalle prmDenuncianteDetalle,HttpServletRequest request)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try
		{
		
			List<DenuncianteDetalle> denunciantes = (List<DenuncianteDetalle>) denuncianteService.listarDenunciantes(prmDenuncianteDetalle);
			_RespuestaHttp.setValido(true);
			_RespuestaHttp.setData(denunciantes);
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
	@RequestMapping(value="/obtenerDenuncia", method=RequestMethod.POST)
	public RespuestaHttp buscarDenuncia(@RequestBody Denuncia prmDenuncia,HttpServletRequest request)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		GenericController gn = new GenericController();
		try
		{
		
			String flagAlfresco = ResourceUtil.getKey("file.alfreso");  /// flag alfresco
			Denuncia denuncia =  denunciaService.buscarPorId(prmDenuncia.getIdDenuncia());
			
	if(denuncia != null && denuncia.getIdDenuncia()!=0){
				
				if(!flagAlfresco.equals("0")){
				   					
				   				   	/** EL SIGUIENTE CODIGO ES LA INTEGRACION CON WSALFRESCO **/
				   					if (	denuncia != null 
				   						&& 	denuncia.getUiid() != null
				   						&& !denuncia.getUiid().equals("")) {
				   					
				   						byte[] fileDescargado = AlfrescoUtil.getArchivo(denuncia.getUiid());

				   						InputStream archivo = new ByteArrayInputStream(fileDescargado);
				   						String nombreArchivo = denuncia.getCodigoDenuncia()+".pdf";
				   						String folder = denuncia.getRutaFicha();
				   						folder = folder.replace("fichaDenuncia", "fichas-denuncia");
				   						
				   						String rutaArchivoCopiado = gn.copiarArchivo(
				   								nombreArchivo, 
				   								folder, 
				   								archivo);
//				   						if (VO.isNullOrEmpty(rutaArchivoCopiado)) {
//				   							respuesta.setMensaje("No se pudo archivar el documento.");
//				   							return respuesta;
//				   						}
				   						
				   						String rutafichaDenuncia = VO.getBasicPath()+denuncia.getRutaFicha()+denuncia.getCodigoDenuncia()+".pdf";
					   					denuncia.setRutaFicha(rutafichaDenuncia);

				   					
				   						
//				   						response.getOutputStream().write(fileDescargado);
//				   						response.flushBuffer();
//				   						response.setContentType(prmArchivoAtencion.getMimeTypeArchivo());
				   					}
				   					
				   					
				   					
				   				
				   				}else{
				   					String rutafichaDenuncia = VO.getBasicPath()+denuncia.getRutaFicha()+denuncia.getCodigoDenuncia()+".pdf";
				   					denuncia.setRutaFicha(rutafichaDenuncia);
				   	   				
				   					
				   					
				   				}
				
			}
			_RespuestaHttp.setValido(true);
			_RespuestaHttp.setData(denuncia);
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
//	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
//	public RespuestaHttp buscarxNumeroNorma(@PathVariable("id")Long idArticulo)throws Exception 
//	{
//		RespuestaHttp respuesta = new  RespuestaHttp();
//		try
//		{
//			Articulo prmArticulo = new Articulo();
//			prmArticulo.setIdArticulo(idArticulo);
//			prmArticulo = articuloService.buscarPorId(prmArticulo.getIdArticulo());
//			
//			respuesta.setValido(true);
//			respuesta.setData(prmArticulo);
//		} 
//		catch (Exception e)
//		{
//			respuesta.setMensaje("Hubo un error al obtener la Articulo");
//			e.printStackTrace();
//		}
//		return respuesta;
//	}
	
	
	public String generarPDFDenuncia(Denuncia denuncia, Integer tipoPdf, Integer tipoPerfil)
			throws Exception{

		
		String url = "";
		
		String identificadorArchivo = "nombre";
		
		String folder_base_denuncia = ResourceUtil.getKey("file.path.denuncia.archivos.windows.base");
		
		String serve = ResourceUtil.getKey("file.server");
		
		if(serve!=null && serve.trim().equals("2")){
			folder_base_denuncia = ResourceUtil.getKey("file.path.denuncia.archivos.linux.base");
		}
		
		folder_base_denuncia = folder_base_denuncia.replace("{separador}", File.separator);
		
		//String folder = "D:\\oefa\\sinada\\denuncia\\temporales\\medios\\";
		String folder = folder_base_denuncia+File.separator+"temporales"+ File.separator +"medios"+ File.separator;
		
		String nombreArchivo = "denuncia.pdf";
		
		if(tipoPdf==2){ //Tipo de documento de denuncia final, cuando la denuncia ya ha sido registrada
			identificadorArchivo = VO.dateToString(new Date());
			identificadorArchivo = identificadorArchivo.replace("/", "-");
			//folder = "D:\\oefa\\fichas-denuncia\\";
			folder = ResourceUtil.getKey("file.path.base") + "fichas-denuncia"  + File.separator;
			nombreArchivo = denuncia.getCodigoDenuncia()+".pdf";
		}
		
			//ByteArrayOutputStream pdf = new ByteArrayOutputStream();
			HttpHeaders responseHeaders = new HttpHeaders();
			
			try {
				
				Image logoOefa =  Image.getInstance(getClass().getResource("img/oefa-logo-header.png"));
				logoOefa.scaleAbsolute(180,42);
				logoOefa.setAlignment(Element.ALIGN_LEFT);
				logoOefa.setAbsolutePosition(35, 770);
				
				Image logoSinada =  Image.getInstance(getClass().getResource("img/sinada-big.png"));
				logoSinada.scaleAbsolute(150,42);
				logoSinada.setAlignment(Element.ALIGN_RIGHT);
				logoSinada.setAbsolutePosition(PageSize.A4.getWidth()-185, 770);
				
			//BaseFont baseFont = BaseFont.createFont("C:\\Windows\\Fonts\\arialuni.ttf", BaseFont.IDENTITY_H,true);
			//Font fuenteNormal = new Font(baseFont,12, Font.NORMAL);
			Font fuenteNormal = new Font(Font.FontFamily.UNDEFINED, 12, Font.NORMAL);
			
			Font fuenteNumeracion = new Font(Font.FontFamily.UNDEFINED, 12, Font.BOLD);
			//Font fuenteNumeracion = new Font(baseFont, 12, Font.BOLD);
			
			//responseHeaders.setContentType(MediaType.valueOf(value)
			responseHeaders.setContentType(MediaType.valueOf("application/pdf;charset=utf-8"));
//			responseHeaders.set
			
			String direccion = folder +identificadorArchivo;
				//url = direccion +"\\"+ nombreArchivo;
				url = direccion + File.separator + nombreArchivo;
				
				File directorio = new File(direccion);
				directorio.mkdirs();
				
				File fichero = new File(url);
				if (fichero.exists()){
					fichero.delete();
				}
				
			Document documento = new Document(PageSize.A4,100, 100, 100, 100);
			FileOutputStream fos = new FileOutputStream(url);
	        //Writer w = new BufferedWriter();

	        //PdfWriter pdfwDemo = PdfWriter.getInstance(documento, new OutputStreamWriter(fos, StandardCharsets.UTF_8));
			PdfWriter pdfwDemo = PdfWriter.getInstance(documento, fos);
			
				documento.open();
				documento.add(logoOefa);
				
				if(tipoPdf==2){
					documento.add(logoSinada);
					
					PdfPTable table = new PdfPTable(2);
					table.setWidthPercentage(100);
						PdfPCell celda1 = new PdfPCell(new Phrase(getLinea("Codigo de Denuncia",fuenteNumeracion,0)));
					celda1.setBorder(0);
					celda1.setHorizontalAlignment(Element.ALIGN_LEFT);
					
					PdfPCell celda2 = new PdfPCell(new Phrase(getLinea(denuncia.getCodigoDenuncia(),fuenteNormal,0)));
					celda2.setBorder(0);
					celda2.setHorizontalAlignment(Element.ALIGN_CENTER);
				
					PdfPCell celda3 = new PdfPCell(new Phrase(getLinea("Codigo de Acceso",fuenteNumeracion,0)));
					celda3.setBorder(0);
					celda3.setHorizontalAlignment(Element.ALIGN_LEFT);
					
					
					PdfPCell celda4 = new PdfPCell(new Phrase(getLinea(denuncia.getCodigoAcceso(),fuenteNormal,0)));
					celda4.setBorder(0);
					celda4.setHorizontalAlignment(Element.ALIGN_CENTER);
					
					table.addCell(celda1);
					table.addCell(celda2);
					table.addCell(celda3);
					table.addCell(celda4);
					documento.add(table);
					documento.add(getLinea(" ", fuenteNumeracion, 10));
				}
				
				documento.add(getLinea("1. Características de la Denuncia", fuenteNumeracion, 10));
					documento.add(getLinea("1.1. Tipo de Denuncia", fuenteNumeracion, 40));
						if(denuncia.getMaestroTipoDenuncia()!=null){
							String str=denuncia.getMaestroTipoDenuncia().getDescripcion();
							//System.out.println(" str 1"+str);
							//str=CodeUtil.parseEncode(str);
						
							documento.add(getLinea("2 "+str, fuenteNormal, 80));
							//System.out.println(" str 2"+CodeUtil.parseEncode(str));
						}else{
							documento.add(getLinea(" ", fuenteNormal, 80));
						}
						
					documento.add(getLinea("1.2. Medio de Recepción", fuenteNumeracion, 40));
						if(denuncia.getMedioRecepcion()==0){
							documento.add(getLinea("Virtual", fuenteNormal, 80));
						}else{
							
							List<Maestro> lstMaestroMedioRecepcion = null;
							Maestro prmMedioRecepcion = new Maestro();
							prmMedioRecepcion.setCodigoMaestro("MedioRecepcion");
							lstMaestroMedioRecepcion = (List<Maestro>) maestroService.buscarPorCodigoTabla(prmMedioRecepcion);
							for (Maestro maestro : lstMaestroMedioRecepcion) {
								
								if(maestro.getCodigoRegistro()==denuncia.getMedioRecepcion()){
									
									documento.add(getLinea(maestro.getDescripcion(), fuenteNormal, 80));
									
								}
								
							}
							
							
						}
						
						
				documento.add(getLinea("2. Datos del Denunciante", fuenteNumeracion, 10));
				if(denuncia.getLstDenunciante()!=null && denuncia.getLstDenunciante().size()>0){
	   				if(denuncia.getLstDenunciante()!=null && denuncia.getLstDenunciante().size()>0){
	   	   				int cont = 1;
	   	   				for (Denunciante denunciante : denuncia.getLstDenunciante()) {
	   	   					documento.add(getLinea(" " , fuenteNumeracion, 80));
	   	   					
	   	   					documento.add(getLinea("Denunciante N° " + String.valueOf(cont), fuenteNumeracion, 80));
	   	   					documento.add(getLinea(" " , fuenteNumeracion, 80));
	   	   					
	   	   					PdfPTable table = new PdfPTable(2);
	   	   					table.setTotalWidth(350f);
	   	   					if(denunciante.getTipoPersona()==1){
	   	   					PdfPCell celda1 = new PdfPCell(new Phrase(getLinea("DNI",fuenteNormal,0)));
								celda1.setBorder(0);
								celda1.setHorizontalAlignment(Element.ALIGN_LEFT);
								
								PdfPCell celda2 = new PdfPCell(new Phrase(getLinea(denunciante.getPersonaDenunciante().getDocumento(),fuenteNormal,0)));
								celda2.setBorder(0);
								celda2.setHorizontalAlignment(Element.ALIGN_LEFT);
							
								PdfPCell celda3 = new PdfPCell(new Phrase(getLinea("Nombres",fuenteNormal,0)));
								celda3.setBorder(0);
								celda3.setHorizontalAlignment(Element.ALIGN_LEFT);
								
								PdfPCell celda4 = new PdfPCell(new Phrase(getLinea(denunciante.getPersonaDenunciante().getNombres(),fuenteNormal,0)));
								celda4.setBorder(0);
								celda4.setHorizontalAlignment(Element.ALIGN_LEFT);
		   						
								PdfPCell celda5 = new PdfPCell(new Phrase(getLinea("Apellido Paterno",fuenteNormal,0)));
								celda5.setBorder(0);
								celda5.setHorizontalAlignment(Element.ALIGN_LEFT);
								
								PdfPCell celda6 = new PdfPCell(new Phrase(getLinea(denunciante.getPersonaDenunciante().getApellidoPaterno(),fuenteNormal,0)));
								celda6.setBorder(0);
								celda6.setHorizontalAlignment(Element.ALIGN_LEFT);
								
								PdfPCell celda7 = new PdfPCell(new Phrase(getLinea("Apellido Materno",fuenteNormal,0)));
								celda7.setBorder(0);
								celda7.setHorizontalAlignment(Element.ALIGN_LEFT);
								
								PdfPCell celda8 = new PdfPCell(new Phrase(getLinea(denunciante.getPersonaDenunciante().getApellidoMaterno(),fuenteNormal,0)));
								celda8.setBorder(0);
								celda8.setHorizontalAlignment(Element.ALIGN_LEFT);
								
								PdfPCell celda9 = new PdfPCell(new Phrase(getLinea("Dirección",fuenteNormal,0)));
								celda9.setBorder(0);
								celda9.setHorizontalAlignment(Element.ALIGN_LEFT);
								
								PdfPCell celda10 = new PdfPCell(new Phrase(getLinea(denunciante.getPersonaDenunciante().getDireccion(),fuenteNormal,0)));
								celda10.setBorder(0);
								celda10.setHorizontalAlignment(Element.ALIGN_LEFT);
								
								table.addCell(celda1);
								table.addCell(celda2);
								table.addCell(celda3);
								table.addCell(celda4);
								table.addCell(celda5);
								table.addCell(celda6);
								table.addCell(celda7);
								table.addCell(celda8);
								table.addCell(celda9);
								table.addCell(celda10);
	   	   						
	   	   					}else{
	   	   						
	   	   					PdfPCell celda1 = new PdfPCell(new Phrase(getLinea("RUC",fuenteNormal,0)));
	   	   					celda1.setBorder(0);
	   	   					celda1.setHorizontalAlignment(Element.ALIGN_LEFT);
							
							PdfPCell celda2 = new PdfPCell(new Phrase(getLinea(denunciante.getEntidadDenunciante().getRuc(),fuenteNormal,0)));
							celda2.setBorder(0);
							celda2.setHorizontalAlignment(Element.ALIGN_LEFT);
						
							PdfPCell celda3 = new PdfPCell(new Phrase(getLinea("Razón Social",fuenteNormal,0)));
							celda3.setBorder(0);
							celda3.setHorizontalAlignment(Element.ALIGN_LEFT);
							
							PdfPCell celda4 = new PdfPCell(new Phrase(getLinea(denunciante.getEntidadDenunciante().getRazonSocial(),fuenteNormal,0)));
							celda4.setBorder(0);
							celda4.setHorizontalAlignment(Element.ALIGN_LEFT);
	   						
							PdfPCell celda5 = new PdfPCell(new Phrase(getLinea("Dirección",fuenteNormal,0)));
							celda5.setBorder(0);
							celda5.setHorizontalAlignment(Element.ALIGN_LEFT);
							
							PdfPCell celda6 = new PdfPCell(new Phrase(getLinea(denunciante.getEntidadDenunciante().getDireccion(),fuenteNormal,0)));
							celda6.setBorder(0);
							celda6.setHorizontalAlignment(Element.ALIGN_LEFT);
							
							
							
							table.addCell(celda1);
							table.addCell(celda2);
							table.addCell(celda3);
							table.addCell(celda4);
							table.addCell(celda5);
							table.addCell(celda6);
							
							
							
//	   	   					PdfPCell celda1 = new PdfPCell(new Phrase(getLinea("DNI",fuenteNormal,0)));
//							celda1.setBorder(0);
//							celda1.setHorizontalAlignment(Element.ALIGN_LEFT);
//							
//							PdfPCell celda2 = new PdfPCell(new Phrase(getLinea(denunciante.getEntidadDenunciante().getRuc(),fuenteNormal,0)));
//							celda2.setBorder(0);
//							celda2.setHorizontalAlignment(Element.ALIGN_LEFT);
//						
//							PdfPCell celda3 = new PdfPCell(new Phrase(getLinea("Nombres",fuenteNormal,0)));
//							celda3.setBorder(0);
//							celda3.setHorizontalAlignment(Element.ALIGN_LEFT);
//							
//							PdfPCell celda4 = new PdfPCell(new Phrase(getLinea(denunciante.getEntidadDenunciante().getNombres(),fuenteNormal,0)));
//							celda4.setBorder(0);
//							celda4.setHorizontalAlignment(Element.ALIGN_LEFT);
//	   						
//							PdfPCell celda5 = new PdfPCell(new Phrase(getLinea("Apellido Paterno",fuenteNormal,0)));
//							celda5.setBorder(0);
//							celda5.setHorizontalAlignment(Element.ALIGN_LEFT);
//							
//							PdfPCell celda6 = new PdfPCell(new Phrase(getLinea(denunciante.getEntidadDenunciante().getApellidoPaterno(),fuenteNormal,0)));
//							celda6.setBorder(0);
//							celda6.setHorizontalAlignment(Element.ALIGN_LEFT);
//							
//							PdfPCell celda7 = new PdfPCell(new Phrase(getLinea("Apellido Materno",fuenteNormal,0)));
//							celda7.setBorder(0);
//							celda7.setHorizontalAlignment(Element.ALIGN_LEFT);
//							
//							PdfPCell celda8 = new PdfPCell(new Phrase(getLinea(denunciante.getEntidadDenunciante().getApellidoMaterno(),fuenteNormal,0)));
//							celda8.setBorder(0);
//							celda8.setHorizontalAlignment(Element.ALIGN_LEFT);
//							
//							PdfPCell celda9 = new PdfPCell(new Phrase(getLinea("Dirección",fuenteNormal,0)));
//							celda9.setBorder(0);
//							celda9.setHorizontalAlignment(Element.ALIGN_LEFT);
//							
//							PdfPCell celda10 = new PdfPCell(new Phrase(getLinea(denunciante.getEntidadDenunciante().getDireccion(),fuenteNormal,0)));
//							celda10.setBorder(0);
//							celda10.setHorizontalAlignment(Element.ALIGN_LEFT);
//							
//							table.addCell(celda1);
//							table.addCell(celda2);
//							table.addCell(celda3);
//							table.addCell(celda4);
//							table.addCell(celda5);
//							table.addCell(celda6);
//							table.addCell(celda7);
//							table.addCell(celda8);
//							table.addCell(celda9);
//							table.addCell(celda10);
	   	   					}
	   		   					
	   	   						
	   							documento.add(table);
	   							
	   	   						cont++;
	   					}
	   	   			}else{
	   	   				
	   	   				if(denuncia.getMaestroTipoDenuncia().getCodigoRegistro()==1){
	   	   				documento.add(getLinea("Denunciante Anónimo " , fuenteNormal, 80));
	   	   				}else{
	   	   				documento.add(getLinea("Denunciante con reserva de identidad " , fuenteNormal, 80));
	   	   				}
	   	   				
	   	   			}
	   			}else{
	   				if(denuncia.getMaestroTipoDenuncia().getCodigoRegistro()==1){
	   	   				documento.add(getLinea("Denunciante Anónimo " , fuenteNormal, 80));
	   	   				}else{
	   	   				documento.add(getLinea("Denunciante con reserva de identidad " , fuenteNormal, 80));
	   	   				}
//					documento.add(getLinea("Denunciante ANONIMO " , fuenteNormal, 80));
				}
				documento.add(getLinea(" " , fuenteNumeracion, 80));
				documento.add(getLinea("3. Datos de la Denuncia", fuenteNumeracion, 10));
					documento.add(getLinea("3.1. Hechos Denunciados", fuenteNumeracion, 40));
						if(denuncia.getCaso()!=null && denuncia.getCaso().getProblematica()!=null){
							documento.add(getLinea("- Problemática ", fuenteNumeracion, 80));
								documento.add(getLinea(denuncia.getCaso().getProblematica().getDescripcion(), fuenteNormal, 100));
							if(denuncia.getCaso().getDebidoA1()!=null && denuncia.getCaso().getDebidoA1().getDescripcion()!=null){
								documento.add(getLinea("- Debido A ", fuenteNumeracion, 80));
									documento.add(getLinea(denuncia.getCaso().getDebidoA1().getDescripcion(), fuenteNormal, 100));
							}
							if(denuncia.getCaso().getDebidoA2()!=null && denuncia.getCaso().getDebidoA2().getDescripcion()!=null){
								documento.add(getLinea("- Debido A ", fuenteNumeracion, 80));
									documento.add(getLinea(denuncia.getCaso().getDebidoA2().getDescripcion(), fuenteNormal, 100));
							}
							if(denuncia.getCaso().getDebidoA3()!=null && denuncia.getCaso().getDebidoA3().getDescripcion()!=null){
								documento.add(getLinea("- Debido A ", fuenteNumeracion, 80));
									documento.add(getLinea(denuncia.getCaso().getDebidoA3().getDescripcion(), fuenteNormal, 100));
							}
						}
						
				documento.add(getLinea("3.2. Donde Ocurren", fuenteNumeracion, 40));
				
					documento.add(getLinea(denuncia.getUbDepartamento().getDescripcionDepartamento() + " - " + denuncia.getUbProvincia().getDescripcion() + " - " + denuncia.getUbDistrito().getDescripcionDistrito(), fuenteNormal, 80));
					
					//System.out.println(" str 1"+denuncia.getDireccion());
					//System.out.println(" str 2"+CodeUtil.parseEncode(denuncia.getDireccion()));
					documento.add(getLinea(denuncia.getDireccion(), fuenteNormal, 80));
					documento.add(getLinea(denuncia.getReferencia(), fuenteNormal, 80));
				documento.add(getLinea("4. Datos del Denunciado", fuenteNumeracion, 10));
				documento.add(getLinea(" " , fuenteNumeracion, 80));

	   			switch (denuncia.getTipo_responsable()) {
					case 0:
						documento.add(getLinea(" --" , fuenteNumeracion, 80));
						break;
					case 1:
						Persona denunciado = personaService.buscarPorId(Long.valueOf(denuncia.getResponsableProblema()));
						
						PdfPTable table = new PdfPTable(2);
	   					table.setTotalWidth(350f);
		   					PdfPCell celda1 = new PdfPCell(new Phrase(getLinea("DNI",fuenteNormal,0)));
							celda1.setBorder(0);
							celda1.setHorizontalAlignment(Element.ALIGN_LEFT);
							
							PdfPCell celda2 = new PdfPCell(new Phrase(getLinea(denunciado.getDocumento(),fuenteNormal,0)));
							celda2.setBorder(0);
							celda2.setHorizontalAlignment(Element.ALIGN_LEFT);
						
							PdfPCell celda3 = new PdfPCell(new Phrase(getLinea("Nombres",fuenteNormal,0)));
							celda3.setBorder(0);
							celda3.setHorizontalAlignment(Element.ALIGN_LEFT);
							
							PdfPCell celda4 = new PdfPCell(new Phrase(getLinea(denunciado.getNombres(),fuenteNormal,0)));
							celda4.setBorder(0);
							celda4.setHorizontalAlignment(Element.ALIGN_LEFT);
	   						
							PdfPCell celda5 = new PdfPCell(new Phrase(getLinea("Apellido Paterno",fuenteNormal,0)));
							celda5.setBorder(0);
							celda5.setHorizontalAlignment(Element.ALIGN_LEFT);
							
							PdfPCell celda6 = new PdfPCell(new Phrase(getLinea(denunciado.getApellidoPaterno(),fuenteNormal,0)));
							celda6.setBorder(0);
							celda6.setHorizontalAlignment(Element.ALIGN_LEFT);
							
							PdfPCell celda7 = new PdfPCell(new Phrase(getLinea("Apellido Materno",fuenteNormal,0)));
							celda7.setBorder(0);
							celda7.setHorizontalAlignment(Element.ALIGN_LEFT);
							
							PdfPCell celda8 = new PdfPCell(new Phrase(getLinea(denunciado.getApellidoMaterno(),fuenteNormal,0)));
							celda8.setBorder(0);
							celda8.setHorizontalAlignment(Element.ALIGN_LEFT);
							
							PdfPCell celda9 = new PdfPCell(new Phrase(getLinea("Dirección",fuenteNormal,0)));
							celda9.setBorder(0);
							celda9.setHorizontalAlignment(Element.ALIGN_LEFT);
							
							PdfPCell celda10 = new PdfPCell(new Phrase(getLinea(denunciado.getDireccion(),fuenteNormal,0)));
							celda10.setBorder(0);
							celda10.setHorizontalAlignment(Element.ALIGN_LEFT);
							
							table.addCell(celda1);
							table.addCell(celda2);
							table.addCell(celda3);
							table.addCell(celda4);
							table.addCell(celda5);
							table.addCell(celda6);
							table.addCell(celda7);
							table.addCell(celda8);
							table.addCell(celda9);
							table.addCell(celda10);
	   						
							documento.add(table);
						break;
					case 2:
						Entidad entidad = entidadService.buscarPorId(Long.valueOf(denuncia.getResponsableProblema()));
						
						PdfPTable tableD = new PdfPTable(2);
	   					tableD.setTotalWidth(350f);
		   					PdfPCell celdaR = new PdfPCell(new Phrase(getLinea("RUC",fuenteNormal,0)));
		   					celdaR.setBorder(0);
		   					celdaR.setHorizontalAlignment(Element.ALIGN_LEFT);
							
							PdfPCell celdaRD = new PdfPCell(new Phrase(getLinea(entidad.getRuc(),fuenteNormal,0)));
							celdaRD.setBorder(0);
							celdaRD.setHorizontalAlignment(Element.ALIGN_LEFT);
						
							PdfPCell celdaRS = new PdfPCell(new Phrase(getLinea("Razón Social",fuenteNormal,0)));
							celdaRS.setBorder(0);
							celdaRS.setHorizontalAlignment(Element.ALIGN_LEFT);
							
							PdfPCell celdaRSD = new PdfPCell(new Phrase(getLinea(entidad.getRazonSocial(),fuenteNormal,0)));
							celdaRSD.setBorder(0);
							celdaRSD.setHorizontalAlignment(Element.ALIGN_LEFT);
	   						
							PdfPCell celdaDir = new PdfPCell(new Phrase(getLinea("Direccion",fuenteNormal,0)));
							celdaDir.setBorder(0);
							celdaDir.setHorizontalAlignment(Element.ALIGN_LEFT);
							
							PdfPCell celdaDirD = new PdfPCell(new Phrase(getLinea((entidad.getDireccion()!=null?entidad.getDireccion():""),fuenteNormal,0)));
							celdaDirD.setBorder(0);
							celdaDirD.setHorizontalAlignment(Element.ALIGN_LEFT);
							
							
							
							tableD.addCell(celdaR);
							tableD.addCell(celdaRD);
							tableD.addCell(celdaRS);
							tableD.addCell(celdaRSD);
							tableD.addCell(celdaDir);
							tableD.addCell(celdaDirD);
	   						
							documento.add(tableD);
						break;
					default:
						break;
				}
	   			documento.add(getLinea(" " , fuenteNumeracion, 80));
				documento.add(getLinea("5. Medios Probatorios", fuenteNumeracion, 10));
				
					if(denuncia.getLstArchivoMedio()!=null && denuncia.getLstArchivoMedio().size()>0){
						
						
						
						List<String> tipoMedio = new ArrayList<String>();
						
						
						for (ArchivoDenuncia archivo : denuncia.getLstArchivoMedio()) {
							boolean swNombre= false; 
						for (String nombreTipo : tipoMedio) {
							if(nombreTipo.equals(archivo.getTipoArchivo().getDescripcion())){
								swNombre = true;
							}
						}
						if(!swNombre){
							tipoMedio.add(archivo.getTipoArchivo().getDescripcion());
						}
					}
						
						for (String nombreTipo : tipoMedio) {
							documento.add(getLinea("- "+nombreTipo, fuenteNormal, 40));
						}
						
					}else{
						documento.add(getLinea("- Sin medios probatorios", fuenteNumeracion, 40));
					}
					
					
					documento.add(getLinea("6. Entidades Competentes", fuenteNumeracion, 10));
		   			List<EntidadCaso> lstEntidadesCompetentes = new ArrayList<EntidadCaso>();
		   			EntidadCaso entCas = new EntidadCaso();
		   			entCas.setIdCaso(denuncia.getCaso().getIdCaso());
		   			lstEntidadesCompetentes = (List<EntidadCaso>) entidadCasoService.listar(entCas);
		   			if(lstEntidadesCompetentes!=null && lstEntidadesCompetentes.size()>0){
		   				for (EntidadCaso object : lstEntidadesCompetentes) {
		   					
		   					if(object.getTipoAsignacion()!=2){
		   						if(object.getTipoEntidad()==1){
			   						documento.add(getLinea(object.getNombreEntidad(), fuenteNumeracion, 40));
			   	   	   				
			   						List<NormaCaso> lstNormaCaso = new ArrayList<NormaCaso>();
			   						NormaCaso prmNormaCaso = new NormaCaso();
			   						prmNormaCaso.setTipoNormaCaso(new Maestro());
			   						prmNormaCaso.getTipoNormaCaso().setCodigoRegistro(1);
			   						prmNormaCaso.setIdCasoOefa(object.getIdCasoEntidad());
			   						lstNormaCaso = (List<NormaCaso>) normaCasoService.listar(prmNormaCaso);
			   						
			   						for (NormaCaso normaCaso : lstNormaCaso) {
			   							documento.add(getLinea("- "+normaCaso.getContenidoNorma().getNombreTipoDispositivo()
			   						+" Nº "+normaCaso.getContenidoNorma().getNumeroNorma() +
			   						", Articulo Nº " + CodeUtil.parseEncode(normaCaso.getContenidoNorma().getNumeroArticulo())
			   						+" - "+ CodeUtil.parseEncode(normaCaso.getContenidoNorma().getDescripcionArticulo()), fuenteNormal, 80));
									}
			   					}
		   					}
		   				}
		   				
		   				CasoEfa prmCasoEfa = new CasoEfa();
		   				

		   				prmCasoEfa.getCaso().setIdCaso(denuncia.getCaso().getIdCaso());
		   				prmCasoEfa.getEfa().setDepartamento(denuncia.getUbDepartamento().getCodigoDepartamento());
		   				prmCasoEfa.getEfa().setProvincia(denuncia.getUbProvincia().getCodigoProvincia());
		   				prmCasoEfa.getEfa().setDistrito(denuncia.getUbDistrito().getCodigoDistrito());
		   				
		   				List<CasoEfa> lstEfasCaso = buscarEfasPorCaso(prmCasoEfa);
		   				
		   				/**************************************************************/
		   					for (CasoEfa casoEfa : lstEfasCaso) {
		   						
		   						if(casoEfa.getTipoAsignacion().getCodigoRegistro()!=2){
		   							documento.add(getLinea(casoEfa.getEfa().getNombre(), fuenteNumeracion, 40));

			   						List<NormaCaso> lstNormaCaso = new ArrayList<NormaCaso>();
			   						NormaCaso prmNormaCaso = new NormaCaso();
			   						prmNormaCaso.setTipoNormaCaso(new Maestro());
			   						prmNormaCaso.getTipoNormaCaso().setCodigoRegistro(2);
			   						prmNormaCaso.setIdCasoEfa(casoEfa.getIdCasoEfa());
			   						lstNormaCaso = (List<NormaCaso>) normaCasoService.listar(prmNormaCaso);
			   						
			   						for (NormaCaso normaCaso : lstNormaCaso) {
			   							documento.add(getLinea("- "+CodeUtil.parseEncode(normaCaso.getContenidoNorma().getNombreTipoDispositivo())
			   									+" Nº "+CodeUtil.parseEncode(normaCaso.getContenidoNorma().getNumeroNorma())
			   									+", Articulo Nº " + CodeUtil.parseEncode(normaCaso.getContenidoNorma().getNumeroArticulo())
			   									+" - "+ CodeUtil.parseEncode(normaCaso.getContenidoNorma().getDescripcionArticulo()), fuenteNormal, 80));
									}
		   						}
//		   	   	   				List<Normas> lstNormas = new ArrayList<Normas>();
//		   	   	   				Normas filtro = new Normas();
//		   	   	   				filtro.setEntidadEmisora(casoEfa.getIdCasoEfa());
//		   	   	   				lstNormas = normasService.buscarPorEntidad(filtro);
//		   	   	   				for (Normas normas : lstNormas) {
//		   	   	   					documento.add(getLinea("- "+normas.getDescripcion(), fuenteNormal, 80));
//		   						}
//		   	   	   				
//		   	   	   				
							}
		   				
		   				/**************************************************************/
		   				
		   			}else{
		   				documento.add(getLinea("- Sin entidades competentes", fuenteNormal, 40));
		   			}
//				denuncia.setLstEntidadesCompetentes(lstEntidades);
				documento.close();
				
				if(tipoPdf==1){
					url = VO.getBasicPath() + "/temporales/"+identificadorArchivo+"/"+nombreArchivo;
				}else if(tipoPdf==2){
					url = VO.getBasicPath() + "/fichaDenuncia/"+identificadorArchivo+"/"+nombreArchivo;
				}
				denuncia.setRutaHojaTramite(url);
				int result = denunciaService.subirHojaTramite(denuncia);
				
			} catch (Exception e) {
			e.printStackTrace();
		}
			
		
		return url;
	}
	
	
//	public VistaDenuncia obtenerDatosDenuncia(Denuncia prmData) throws Exception{
//		
//		VistaDenuncia resBean = null;
//		
//		VistaDenuncia prmVistaDenuncia = new VistaDenuncia();
//		
//		if(prmData.getIdDenuncia()!=0){
//			
//			prmVistaDenuncia.setIdDenuncia(prmData.getIdDenuncia());
//			
//			resBean = vistaDenunciaService.buscarFichaDenunciaPorId(prmVistaDenuncia);
//			
//		}else{
//			if(prmData.getMedioRecepcion()==0){
//				prmData.setMedioRecepcion(5);
//			}
//			
//			prmVistaDenuncia.setTipoDenuncia(prmData.getTipoDenuncia());
//			prmVistaDenuncia.setMedioRecepcion(prmData.getMedioRecepcion());
//			prmVistaDenuncia.setHojaTramite(prmData.getHojaTramite());
//			prmVistaDenuncia.setDepartamento(prmData.getDepartamento());
//			prmVistaDenuncia.setProvincia(prmData.getProvincia());
//			prmVistaDenuncia.setDistrito(prmData.getDistrito());
//			
//			prmVistaDenuncia.setDireccion(prmData.getDireccion());
//			prmVistaDenuncia.setReferencia(prmData.getReferencia());
//			prmVistaDenuncia.setTipoResponsable(prmData.getTipo_responsable());
//			prmVistaDenuncia.setResponsableProblema(prmData.getResponsableProblema());
//			prmVistaDenuncia.setTipoMedio(prmData.getTipoMedio());
//			prmVistaDenuncia.setIdCaso(prmData.getCaso().getIdCaso());
//			prmVistaDenuncia.setCentroPoblado(prmData.getCodigoCentroPoblado());
//			
//			resBean = vistaDenunciaService.generarVisualizacionPreliminarFichaDenuncia(prmVistaDenuncia);
//			
//		}
//		
//		
//		if(resBean!=null){
//			prmVistaDenuncia = resBean;
//			if(resBean.getIdDenuncia()!=0){
//				
//				prmVistaDenuncia.setLstDenunciante(new ArrayList<Denunciante>());
//				Denunciante denunciante = new Denunciante();
//				denunciante.setIdDenuncia(prmData.getIdDenuncia());
//				
//				List<Denunciante> lstDenunciantes =  (List<Denunciante>) denuncianteService.listar(denunciante);
//				
//				for (Denunciante dataDenunciante : lstDenunciantes) {
//					
//					if(dataDenunciante.getTipoPersona()==1){
//						dataDenunciante.setPersonaDenunciante(personaService.buscarPorId(dataDenunciante.getIdPersona()));
//					}
//					if(dataDenunciante.getTipoPersona()==2){
//						dataDenunciante.setEntidadDenunciante(entidadService.buscarPorId(dataDenunciante.getIdPersona()));
//						
//					}
//					
//					prmVistaDenuncia.getLstDenunciante().add(dataDenunciante);
//				}
//				
//				/*******************************************************************/
//				
//				ArchivoDenuncia prmArchivoDenuncia = new ArchivoDenuncia();
//				prmArchivoDenuncia.setIdDenuncia(resBean.getIdDenuncia());
//				List<ArchivoDenuncia> lstArchivoDenuncia =  (List<ArchivoDenuncia>) archivoDenunciaService.listar(prmArchivoDenuncia);
//				
//				if(lstArchivoDenuncia!=null && lstArchivoDenuncia.size()>0){
//					prmVistaDenuncia.setLstArchivoDenuncia(lstArchivoDenuncia);
//				}
//				
//			}else{
//				prmVistaDenuncia.setLstDenunciante(new ArrayList<Denunciante>());
//				prmVistaDenuncia.setLstDenunciante(prmData.getLstDenunciante());
//				prmVistaDenuncia.setLstArchivoDenuncia(new ArrayList<ArchivoDenuncia>());
//				prmVistaDenuncia.setLstArchivoDenuncia(prmData.getLstArchivoMedio());
//			}
//		
//			
//			
//			
//			/*******************************************************************************/
//			
//			
//			List<EntidadCaso> lstEntidadesCompetentes = new ArrayList<EntidadCaso>();
//			
//   			EntidadCaso entCas = new EntidadCaso();
//   			entCas.setIdCaso(prmData.getCaso().getIdCaso());
//   			lstEntidadesCompetentes = (List<EntidadCaso>) entidadCasoService.listar(entCas);
//   			
//   			
//   			/******************************************************************************/
//   			
//   			if(lstEntidadesCompetentes!=null && lstEntidadesCompetentes.size()>0){
//   				prmVistaDenuncia.setLstEntidadCaso(new ArrayList<EntidadCaso>());
//   				for (EntidadCaso object : lstEntidadesCompetentes) {
//   					
//   					if(object.getTipoAsignacion()!=2){
//   						if(object.getTipoEntidad()==1){
//	   						List<NormaCaso> lstNormaCaso = new ArrayList<NormaCaso>();
//	   						NormaCaso prmNormaCaso = new NormaCaso();
//	   						prmNormaCaso.setTipoNormaCaso(new Maestro());
//	   						prmNormaCaso.getTipoNormaCaso().setCodigoRegistro(1);
//	   						prmNormaCaso.setIdCasoOefa(object.getIdCasoEntidad());
//	   						lstNormaCaso = (List<NormaCaso>) normaCasoService.listar(prmNormaCaso);
//	   						
//	   						if(lstNormaCaso!=null && lstNormaCaso.size()>0){
//	   							object.setLstNormaCaso(new ArrayList<NormaCaso>());
//	   							object.getLstNormaCaso().addAll(lstNormaCaso);
//	   							
//	   						}
//	   						
//	   						prmVistaDenuncia.getLstEntidadCaso().add(object);
//	   	   	   				
//	   					}
//   						
//   					}
//   				
//   					
//   				
//   	   			
//   	   				
//   					
//   					
//   	   				
//   	   				
//   				}
//   				
//   				
//   				
//   				
//   				CasoEfa prmCasoEfa = new CasoEfa();
//   				
//
//   				prmCasoEfa.getCaso().setIdCaso(prmData.getCaso().getIdCaso());
//   				prmCasoEfa.getEfa().setDepartamento(prmData.getDepartamento());
//   				prmCasoEfa.getEfa().setProvincia(prmData.getProvincia());
//   				prmCasoEfa.getEfa().setDistrito(prmData.getDistrito());
//   				
//   				List<CasoEfa> lstEfasCaso = buscarEfasPorCaso(prmCasoEfa);
//   				
//   				/**************************************************************/
//   					for (CasoEfa casoEfa : lstEfasCaso) {
//   						
//   				
//   						
//   						if(casoEfa.getTipoAsignacion().getCodigoRegistro()!=2){
//   							EntidadCaso prmEntidadCaso = new EntidadCaso();
//   							prmEntidadCaso.setTipoEntidad(2);
//   	   						prmEntidadCaso.setNombreEntidad(casoEfa.getEfa().getNombre());
//   	   						prmEntidadCaso.setIdCasoEntidad(casoEfa.getIdCasoEfa());
//   	   						prmEntidadCaso.setNombreTipoEntidad("EFA");
//   	   						
////   							documento.add(getLinea(casoEfa.getEfa().getNombre(), fuenteNumeracion, 40));
//
//	   						List<NormaCaso> lstNormaCaso = new ArrayList<NormaCaso>();
//	   						NormaCaso prmNormaCaso = new NormaCaso();
//	   						prmNormaCaso.setTipoNormaCaso(new Maestro());
//	   						prmNormaCaso.getTipoNormaCaso().setCodigoRegistro(2);
//	   						prmNormaCaso.setIdCasoEfa(casoEfa.getIdCasoEfa());
//	   						lstNormaCaso = (List<NormaCaso>) normaCasoService.listar(prmNormaCaso);
//	   						
//	   						if(lstNormaCaso!=null && lstNormaCaso.size()>0){
//	   							prmEntidadCaso.setLstNormaCaso(new ArrayList<NormaCaso>());
//	   							prmEntidadCaso.setLstNormaCaso(lstNormaCaso);
//	   						}
////	   						for (NormaCaso normaCaso : lstNormaCaso) {
////	   							documento.add(getLinea("- "+CodeUtil.parseEncode(normaCaso.getContenidoNorma().getNombreTipoDispositivo())
////	   									+" Nº "+CodeUtil.parseEncode(normaCaso.getContenidoNorma().getNumeroNorma())
////	   									+", Articulo Nº " + CodeUtil.parseEncode(normaCaso.getContenidoNorma().getNumeroArticulo())
////	   									+" - "+ CodeUtil.parseEncode(normaCaso.getContenidoNorma().getDescripcionArticulo()), fuenteNormal, 80));
////							}
//	   						
//	   						prmVistaDenuncia.getLstEntidadCaso().add(prmEntidadCaso); 
//   						}
//   						
//   						
//   						
//   				
//   				
//   				/**************************************************************/
//   				
//   				
//   			}
//   				
//   			}
//			
//		}else{
//			
//			
//			
//			
//			
//			
//		}
//		
//		
//		if(prmVistaDenuncia!=null){
//			resBean = prmVistaDenuncia;
//		}
//		return resBean;
//		
//	}
	
	@ResponseBody
	@RequestMapping(value = "/obtener-datos-ficha-denuncia",method = RequestMethod.POST)
	public RespuestaHttp obtenerDatosFichaDenuncia(@RequestBody Denuncia prmDenuncia,HttpServletRequest request) throws Exception
	{
		RespuestaHttp respuesta = new RespuestaHttp();
		try
		{
			VistaDenuncia prmData =	denunciaService.obtenerDatosDenunciaGenerico(prmDenuncia);
			
			if(prmData!=null){
				respuesta.setValido(true);
				respuesta.setData(prmData);
			}else{
				respuesta.setMensaje("Error al obtener la información");
			}
		
				
		

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			respuesta.setMensaje("Error al obtener la información");
		}
		return respuesta;
	}
	
	
	
	
	@ResponseBody
	@RequestMapping(value = "/generar-pdf-ficha-denuncia-html",method = RequestMethod.POST)
	public RespuestaHttp obtenerPDFFichaDenunciaHtml(@RequestBody VistaDenuncia denuncia,HttpServletRequest request) throws Exception
	{
		String url = "";
		RespuestaHttp respuesta = new RespuestaHttp();
		try
		{			
			String separador= ResourceUtil.getKey("file.path.separador");
			String identificadorArchivo = "nombre";
			String rutaBaseFichaDenuncia = ResourceUtil.getKey("file.path.denuncias-ficha");
			
//			String rutaBase = ResourceUtil.getKey("file.path.base");
//			String folderFichaDenuncia = ResourceUtil.getKey("file.path.denuncias-ficha");
			
//			String folder_base_denuncia = "";
			
//			String folder_base_denuncia = ResourceUtil.getKey("file.path.denuncia.archivos.windows.base");
			
//			String serve = ResourceUtil.getKey("file.server");
			
//			if(serve!=null && serve.trim().equals("2")){
//				folder_base_denuncia = ResourceUtil.getKey("file.path.denuncia.archivos.linux.base");
//			}
			
//			folder_base_denuncia = folder_base_denuncia.replace("{separador}", separador);
			
			//String folder = "D:\\oefa\\sinada\\denuncia\\temporales\\medios\\";
			
//			folder_base_denuncia =folderFichaDenuncia+separador+"temporales"+ separador +"medios"+ separador;
			
			String folder = "";
			
			String nombreArchivo = "denuncia.pdf";
			
			//Tipo de documento de denuncia final, cuando la denuncia ya ha sido registrada
			
				identificadorArchivo = VO.dateToString(new Date());
				identificadorArchivo = identificadorArchivo.replace("/", "-");
				//folder = "D:\\oefa\\fichas-denuncia\\";
				folder = ResourceUtil.getKey("file.path.base") + rutaBaseFichaDenuncia ;
				nombreArchivo = denuncia.getCodigoDenuncia()+".pdf";
	
			
				//ByteArrayOutputStream pdf = new ByteArrayOutputStream();
				HttpHeaders responseHeaders = new HttpHeaders();
				
				
					
					Image logoOefa =  Image.getInstance(getClass().getResource("img/oefa-logo-header.png"));
					logoOefa.scaleAbsolute(140,35);
					logoOefa.setAlignment(Element.ALIGN_LEFT);
					logoOefa.setAbsolutePosition(35, 770);
					
					Image logoSinada =  Image.getInstance(getClass().getResource("img/sinada-big.png"));
					logoSinada.scaleAbsolute(150,42);
					logoSinada.setAlignment(Element.ALIGN_RIGHT);
					logoSinada.setAbsolutePosition(PageSize.A4.getWidth()-185, 770);
					
				//BaseFont baseFont = BaseFont.createFont("C:\\Windows\\Fonts\\arialuni.ttf", BaseFont.IDENTITY_H,true);
				//Font fuenteNormal = new Font(baseFont,12, Font.NORMAL);
				//Font fuenteNormal = new Font(Font.FontFamily.UNDEFINED, 12, Font.NORMAL);
				
				//Font fuenteNumeracion = new Font(Font.FontFamily.UNDEFINED, 12, Font.BOLD);
				//Font fuenteNumeracion = new Font(baseFont, 12, Font.BOLD);
				
				//responseHeaders.setContentType(MediaType.valueOf(value)
				responseHeaders.setContentType(MediaType.valueOf("application/pdf;charset=utf-8"));
//				responseHeaders.set
				
				String direccion = folder +identificadorArchivo;
					//url = direccion +"\\"+ nombreArchivo;
					url = direccion + File.separator + nombreArchivo;
					
					File directorio = new File(direccion);
					directorio.mkdirs();
					
					File fichero = new File(url);
					if (fichero.exists()){
						fichero.delete();
					}
					
				//Fuente de Letra
				    Font fuenteNegrita = new Font(FontFamily.HELVETICA, 10, Font.BOLD);
				    Font fuenteNormal = new Font(FontFamily.HELVETICA, 10, Font.NORMAL);
					
			   
					
				Document documento = new Document(PageSize.A4,25, 25, 25, 25);
				
				FileOutputStream fos = new FileOutputStream(url);
		        //Writer w = new BufferedWriter();

		        //PdfWriter pdfwDemo = PdfWriter.getInstance(documento, new OutputStreamWriter(fos, StandardCharsets.UTF_8));
				PdfWriter pdfwDemo = PdfWriter.getInstance(documento, fos);
				
					documento.open();
//					documento.add(logoOefa);
					
					Paragraph saltoLinea = new Paragraph("\n");
					 
					PdfPCell celdaSaltoLinea = new PdfPCell(new Paragraph("",fuenteNormal));// CeldaTitulo de la Tabla DEnunciante
					celdaSaltoLinea.setBorder(0);
					/******************************************************************/
					
					Image logoCabecera =  Image.getInstance(getClass().getResource("img/logo_cabecera.jpg"));
					logoCabecera.scaleAbsolute(547,62);
					logoCabecera.setAlignment(Element.ALIGN_LEFT);
					//logoCabecera.setAbsolutePosition(33, 770);
					
					documento.add(logoCabecera);
					
					
					 PdfPTable tablaCabecera = new PdfPTable(2); // 2 Columnas
					 tablaCabecera.setWidthPercentage(100);
						 
						 PdfPTable tablaCabecera01 = new PdfPTable(2); // 2 Columnas
						 tablaCabecera01.setWidthPercentage(100);
						 tablaCabecera01.setWidths(new float[] { 32, 68 });
						 
							 PdfPCell celda01 = new PdfPCell(new Paragraph("Código Denuncia ",fuenteNormal));
							 celda01.setBorder(0);
							 celda01.setBackgroundColor(new BaseColor(238, 238, 239));
							 tablaCabecera01.addCell(celda01);
							 
							 PdfPCell celda01Dato = new PdfPCell(new Paragraph(denuncia.getCodigoDenuncia(),fuenteNegrita));
							 celda01Dato.setBorder(0);
							 celda01Dato.setBackgroundColor(new BaseColor(238, 238, 239));
							 tablaCabecera01.addCell(celda01Dato);
						 
						 PdfPCell celda1 = new PdfPCell(tablaCabecera01);
						 celda1.setBorder(0);
						 celda1.setBackgroundColor(new BaseColor(238, 238, 239));
						 celda1.setPaddingLeft(15);
						 celda1.setPaddingTop(10);
						 tablaCabecera.addCell(celda1);
						 
						 
						 PdfPCell celda2 = new PdfPCell(new Paragraph("Características de la denuncia ", fuenteNegrita));
						 celda2.setBorder(0);
						 celda2.setPaddingTop(10);
						 celda2.setBackgroundColor(new BaseColor(238, 238, 239));
						 tablaCabecera.addCell(celda2);
						 
						 
						 PdfPTable tablaCabecera03 = new PdfPTable(2); // 2 Columnas
						 tablaCabecera03.setWidthPercentage(100);
						 tablaCabecera03.setWidths(new float[] { 35, 65 });
						 
							 PdfPCell celda03 = new PdfPCell(new Paragraph("Código de acceso ",fuenteNormal));
							 celda03.setBorder(0);
							 celda03.setBackgroundColor(new BaseColor(238, 238, 239));
							 tablaCabecera03.addCell(celda03);
							 
							 PdfPCell celda03Dato = new PdfPCell(new Paragraph( denuncia.getCodigoacceso(),fuenteNegrita));
							 celda03Dato.setBorder(0);
							 celda03Dato.setBackgroundColor(new BaseColor(238, 238, 239));
							 tablaCabecera03.addCell(celda03Dato);
						 
						 PdfPCell celda3 = new PdfPCell(tablaCabecera03);
						 celda3.setBorder(0);
						 celda3.setBackgroundColor(new BaseColor(238, 238, 239));
						 celda3.setPaddingLeft(15);
						 celda3.setPaddingBottom(15);
						 tablaCabecera.addCell(celda3);
						 
						 
						 PdfPTable tablaCabecera04 = new PdfPTable(2); // 2 Columnas
						 tablaCabecera04.setWidthPercentage(100);
						 tablaCabecera04.setWidths(new float[] { 30, 70 });
						 
							 PdfPCell celda04 = new PdfPCell(new Paragraph("Tipo de denuncia ",fuenteNormal));
							 celda04.setBorder(0);
							 celda04.setBackgroundColor(new BaseColor(238, 238, 239));
							 tablaCabecera04.addCell(celda04);
							 
							 PdfPCell celda04Dato = new PdfPCell(new Paragraph( denuncia.getNombreTipoDenuncia(),fuenteNegrita));
							 celda04Dato.setBorder(0);
							 celda04Dato.setBackgroundColor(new BaseColor(238, 238, 239));
							 tablaCabecera04.addCell(celda04Dato);
						 
						 PdfPCell celda4 = new PdfPCell(tablaCabecera04);
						 celda4.setBorder(0);
						 celda4.setBackgroundColor(new BaseColor(238, 238, 239));
						 celda4.setPaddingBottom(15);
						 tablaCabecera.addCell(celda4);
						 
						 /*
						 PdfPCell celda5 = new PdfPCell(new Paragraph(""));
						 celda5.setBorder(0);
						 celda5.setBackgroundColor(new BaseColor(238, 238, 239));
						 tablaCabecera.addCell(celda5);
						 */
						 
						 if(denuncia.getHojaTramite()!=null){
							 PdfPTable tablaCabecera05 = new PdfPTable(2); // 2 Columnas
							 tablaCabecera05.setWidthPercentage(100);
							 tablaCabecera05.setWidths(new float[] { 35, 65 });
							 
								 PdfPCell celda05 = new PdfPCell(new Paragraph("Hoja de Tramite  ",fuenteNormal));
								 celda05.setBorder(0);
								 celda05.setBackgroundColor(new BaseColor(238, 238, 239));
								 tablaCabecera05.addCell(celda05);
								 
								 PdfPCell celda05Dato = new PdfPCell(new Paragraph( denuncia.getHojaTramite(),fuenteNegrita));
								 celda05Dato.setBorder(0);
								 celda05Dato.setBackgroundColor(new BaseColor(238, 238, 239));
								 tablaCabecera05.addCell(celda05Dato);
							 
							 PdfPCell celda5 = new PdfPCell(tablaCabecera05);
							 celda5.setBorder(0);
							 celda5.setBackgroundColor(new BaseColor(238, 238, 239));
							 celda5.setPaddingLeft(15);
							 tablaCabecera.addCell(celda5);
						 }
					
						 //
						 
						 PdfPTable tablaCabecera06 = new PdfPTable(2); // 2 Columnas
						 tablaCabecera06.setWidthPercentage(100);
						 tablaCabecera06.setWidths(new float[] { 35, 65 });
						 
							 PdfPCell celda06 = new PdfPCell(new Paragraph("Medio de recepción ",fuenteNormal));
							 celda06.setBorder(0);
							 celda06.setBackgroundColor(new BaseColor(238, 238, 239));
							 tablaCabecera06.addCell(celda06);
							 
							 PdfPCell celda06Dato = new PdfPCell(new Paragraph( denuncia.getNombreMedioRecepcion(),fuenteNegrita));
							 celda06Dato.setBorder(0);
							 celda06Dato.setBackgroundColor(new BaseColor(238, 238, 239));
							 tablaCabecera06.addCell(celda06Dato);
						 
						 PdfPCell celda6 = new PdfPCell(tablaCabecera06);
						 celda6.setBorder(0);
						 celda6.setBackgroundColor(new BaseColor(238, 238, 239));
						 celda6.setPaddingBottom(15);
						 tablaCabecera.addCell(celda6);
					 
						 
				    documento.add(tablaCabecera);
				    
				    documento.add(saltoLinea);
				    
				    PdfPTable tablaCuerpo = new PdfPTable(2); // 2 Columnas
				    tablaCuerpo.setWidthPercentage(100);
				    /***************************** Incio : Izquierod ******************************/
				    PdfPTable tablaCuerpoIzq = new PdfPTable(1); // 2 Columnas
				    tablaCuerpoIzq.setWidthPercentage(100);
				    //PdfPTable table = new PdfPTable(2); // 2 Columnas
					//PdfPCell celda7 = new PdfPCell(new Paragraph(""));   // Tabla GEneral vista previa 1
					//table.addCell(celda7);
						 /****************  Comienza Tabla DEnunciante ************************/
						 PdfPTable tablaDenunciantes = new PdfPTable(1);  // Tabla Denunciante
						 tablaDenunciantes.setWidthPercentage(100);
							
						 PdfPCell celdaDenunciante = new PdfPCell(new Paragraph("1- Datos del denunciante",fuenteNegrita));// CeldaTitulo de la Tabla DEnunciante
						 celdaDenunciante.setBorder(0);
						 tablaDenunciantes.addCell(celdaDenunciante);
						 if(denuncia.getLstDenunciante().size()>0 && denuncia.getTipoDenuncia()==3){
							 int numeroDenunciante = 1;
							 for (Denunciante denunciantes : denuncia.getLstDenunciante()) {
								 if(denunciantes.getTipoPersona()==1){
									 	
									 PdfPCell celdaDenunciante1 = new PdfPCell(new Paragraph("Denunciante Nº " + numeroDenunciante,fuenteNormal));// CeldaTitulo de la Tabla DEnunciante
									 celdaDenunciante1.setBorder(0);
									 celdaDenunciante1.setPaddingLeft(15);
									 tablaDenunciantes.addCell(celdaDenunciante1);
									 
									 PdfPCell celdaDenunciante2 = new PdfPCell(new Paragraph("Nombre : " + denunciantes.getPersonaDenunciante().getPrimerNombre()+ " " +denunciantes.getPersonaDenunciante().getSegundoNombre() + " "+denunciantes.getPersonaDenunciante().getApellidoPaterno()+" "+denunciantes.getPersonaDenunciante().getApellidoMaterno(),fuenteNormal));
									 celdaDenunciante2.setBorder(0);
									 celdaDenunciante2.setPaddingLeft(15);
									 tablaDenunciantes.addCell(celdaDenunciante2);
									 
									 PdfPCell celdaDenunciante3 = new PdfPCell(new Paragraph("Documento : " + denunciantes.getPersonaDenunciante().getDocumento(),fuenteNormal));
									 celdaDenunciante3.setBorder(0);
									 celdaDenunciante3.setPaddingLeft(15);
									 tablaDenunciantes.addCell(celdaDenunciante3);
									 
									 PdfPCell celdaDenunciante4 = new PdfPCell(new Paragraph("Ubigeo : ",fuenteNormal));
									 celdaDenunciante4.setBorder(0);
									 celdaDenunciante4.setPaddingLeft(15);
									 tablaDenunciantes.addCell(celdaDenunciante4);
									 
									 PdfPCell celdaDenunciante5 = new PdfPCell(new Paragraph(denunciantes.getPersonaDenunciante().getNombreDepartamento().toUpperCase()+" / "+denunciantes.getPersonaDenunciante().getNombreProvincia().toUpperCase()+" / "+denunciantes.getPersonaDenunciante().getNombreDistrito().toUpperCase(),fuenteNormal));
									 celdaDenunciante5.setBorder(0);
									 celdaDenunciante5.setPaddingBottom(15);
									 tablaDenunciantes.addCell(celdaDenunciante5);
									 
									 PdfPCell celdaDenunciante6 = new PdfPCell(new Paragraph("Dirección " + denunciantes.getPersonaDenunciante().getDireccion()));
									 celdaDenunciante6.setBorder(0);
									 celdaDenunciante6.setPaddingBottom(15);
									 tablaDenunciantes.addCell(celdaDenunciante6);
//									 PdfPCell celdaDenunciante7 = new PdfPCell(new Paragraph("Provincia : " + denunciantes.getPersonaDenunciante().getProvincia(),fuenteNormal));
//									 celdaDenunciante7.setBorder(0);
////									 celdaDenunciante7.setPaddingBottom(15);
//									 tablaDenunciantes.addCell(celdaDenunciante7);
//
//									 PdfPCell celdaDenunciante8 = new PdfPCell(new Paragraph("Distrito : " + denunciantes.getPersonaDenunciante().getDistrito(),fuenteNormal));
//									 celdaDenunciante8.setBorder(0);
////									 celdaDenunciante8.setPaddingBottom(15);
//									 tablaDenunciantes.addCell(celdaDenunciante8);
//
//									 PdfPCell celdaDenunciante9 = new PdfPCell(new Paragraph("Dirección :" + denunciantes.getPersonaDenunciante().getDireccion(),fuenteNormal));
//									 celdaDenunciante9.setBorder(0);
////									 celdaDenunciante9.setPaddingBottom(15);
//									 tablaDenunciantes.addCell(celdaDenunciante9);
									 
								 }else{
									 
									 PdfPCell celdaDenunciante1 = new PdfPCell(new Paragraph("Denunciante Nº " + numeroDenunciante,fuenteNormal));// CeldaTitulo de la Tabla DEnunciante
									 celdaDenunciante1.setBorder(0);
									 tablaDenunciantes.addCell(celdaDenunciante1);
									 
									 PdfPCell celdaDenunciante2 = new PdfPCell(new Paragraph("RUC : " + denunciantes.getEntidadDenunciante().getRuc(),fuenteNormal));
									 celdaDenunciante2.setBorder(0);
									 tablaDenunciantes.addCell(celdaDenunciante2);
									 
									 PdfPCell celdaDenunciante3 = new PdfPCell(new Paragraph("Razon social : " + denunciantes.getEntidadDenunciante().getRazonSocial(),fuenteNormal));
									 celdaDenunciante3.setBorder(0);
									 tablaDenunciantes.addCell(celdaDenunciante3);
									 
									 PdfPCell celdaDenunciante4 = new PdfPCell(new Paragraph("Representante legal : " + denunciantes.getEntidadDenunciante().getNomRepresentante(),fuenteNormal));
									 celdaDenunciante4.setBorder(0);
									 tablaDenunciantes.addCell(celdaDenunciante4);
									 
									 PdfPCell celdaDenunciante5 = new PdfPCell(new Paragraph("Cargo : " + denunciantes.getEntidadDenunciante().getNomCargo(),fuenteNormal));
									 celdaDenunciante5.setBorder(0);
									 tablaDenunciantes.addCell(celdaDenunciante5);
									 
//									 PdfPCell celdaDenunciante6 = new PdfPCell(new Paragraph("Dirección fiscal" + denunciantes.getEntidadDenunciante().getDireccion()));
//									 celdaDenunciante6.setBorder(0);
//									 tablaDenunciantes.addCell(celdaDenunciante6);

									 PdfPCell celdaDenunciante6 = new PdfPCell(new Paragraph("Ubigeo :",fuenteNormal));
									 celdaDenunciante6.setBorder(0);
									 tablaDenunciantes.addCell(celdaDenunciante6);

									 PdfPCell celdaDenunciante7 = new PdfPCell(new Paragraph(denunciantes.getEntidadDenunciante().getNombreDepartamento()+" / "+denunciantes.getEntidadDenunciante().getNombreProvincia()+" / "+denunciantes.getEntidadDenunciante().getNombreDistrito(),fuenteNormal));
									 celdaDenunciante7.setBorder(0);
									 tablaDenunciantes.addCell(celdaDenunciante7);

									 PdfPCell celdaDenunciante8 = new PdfPCell(new Paragraph("Dirección fiscal : " + denunciantes.getEntidadDenunciante().getDireccion(),fuenteNormal));
									 celdaDenunciante8.setBorder(0);
									 tablaDenunciantes.addCell(celdaDenunciante8);

									 
//									 PdfPCell celdaDenunciante6 = new PdfPCell(new Paragraph("Departamento : " + denunciantes.getEntidadDenunciante().getDepartamento(),fuenteNormal));
//									 celdaDenunciante6.setBorder(0);
//									 tablaDenunciantes.addCell(celdaDenunciante6);
//
//									 PdfPCell celdaDenunciante7 = new PdfPCell(new Paragraph("Provincia : " + denunciantes.getEntidadDenunciante().getProvincia(),fuenteNormal));
//									 celdaDenunciante7.setBorder(0);
//									 tablaDenunciantes.addCell(celdaDenunciante7);
//
//									 PdfPCell celdaDenunciante8 = new PdfPCell(new Paragraph("Distrito : " + denunciantes.getEntidadDenunciante().getDistrito(),fuenteNormal));
//									 celdaDenunciante8.setBorder(0);
//									 tablaDenunciantes.addCell(celdaDenunciante8);
//
//									 PdfPCell celdaDenunciante9 = new PdfPCell(new Paragraph("Dirección fiscal : " + denunciantes.getEntidadDenunciante().getDireccion(),fuenteNormal));
//									 celdaDenunciante9.setBorder(0);
//									 tablaDenunciantes.addCell(celdaDenunciante9);
									 
									 
								 }
								
								 numeroDenunciante = numeroDenunciante+1;
								}
							 
						 }else{
							 PdfPCell celdaDenunciante1 = new PdfPCell(new Paragraph(denuncia.getNombreTipoDenuncia(),fuenteNormal));// CeldaTitulo de la Tabla DEnunciante
							 celdaDenunciante1.setBorder(0);
							 celdaDenunciante1.setPaddingLeft(15);
							 tablaDenunciantes.addCell(celdaDenunciante1);
						 }
						 
						 tablaDenunciantes.addCell(celdaSaltoLinea);
						 
						 PdfPCell celda7 = new PdfPCell(tablaDenunciantes); 
						 celda7.setBorder(0);
						 tablaCuerpoIzq.addCell(celda7);
						 
						 //celda7.addElement(tablaDenunciantes);
						  /**************************************************************/
						 /*															   */
						 /***************************************************************/
						 
						 /****************  Comienza Tabla DAtos de la denuncia ************************/
						 PdfPTable tablaDatosDenuncia = new PdfPTable(1);  // Tabla Datos Denuncia
						 tablaDatosDenuncia.setWidthPercentage(100);
							
						 
						 PdfPCell celdaDatosDenuncia = new PdfPCell(new Paragraph("2- Datos de la denuncia",fuenteNegrita));// CeldaTitulo de Datos Denuncia
						 celdaDatosDenuncia.setBorder(0);
						 tablaDatosDenuncia.addCell(celdaDatosDenuncia);
						
						 /***********************************/
						 
						 PdfPCell hechosDenunciados = new PdfPCell(new Paragraph("Hechos Denunciados:",fuenteNegrita));//
						 hechosDenunciados.setBorder(0);
						 hechosDenunciados.setPaddingLeft(15);
						 tablaDatosDenuncia.addCell(hechosDenunciados);
						 
						 PdfPTable tablaProblematica = new PdfPTable(1); // 2 Columnas
						 tablaProblematica.setWidthPercentage(100);
						 //tablaProblematica.setWidths(new float[] { 35, 65 });
						 
							 PdfPCell celdaProblematicaTitulo = new PdfPCell(new Paragraph("Problemática ",fuenteNormal));
							 celdaProblematicaTitulo.setBorder(0);
							 tablaProblematica.addCell(celdaProblematicaTitulo);
							 
							 PdfPCell celdaProblematicaDato = new PdfPCell(new Paragraph( denuncia.getNombreProblematica(),fuenteNegrita));
							 celdaProblematicaDato.setBorder(0);
							 tablaProblematica.addCell(celdaProblematicaDato);
						 
						 PdfPCell celdaProblematica = new PdfPCell(tablaProblematica);
						 celdaProblematica.setBorder(0);
						 celdaProblematica.setPaddingLeft(15);
						 tablaDatosDenuncia.addCell(celdaProblematica);
						 
						 //PdfPCell problematica = new PdfPCell(new Paragraph("ProblemÃ¡tica " + denuncia.getNombreProblematica()));// 
						 //tablaDatosDenuncia.addCell(problematica);
						 
						 PdfPCell debidoA = new PdfPCell(new Paragraph("Debido a",fuenteNormal));//fuenteNegrita
						 debidoA.setBorder(0);
						 debidoA.setPaddingLeft(15);
						 tablaDatosDenuncia.addCell(debidoA);
						 
						 PdfPCell debidoA1 = new PdfPCell(new Paragraph(denuncia.getNombreDebidoaNivel1(),fuenteNegrita));//fuenteNormal
						 debidoA1.setBorder(0);
						 debidoA1.setPaddingLeft(15);
						 tablaDatosDenuncia.addCell(debidoA1);
						 
						 if(denuncia.getIdDebidoa2()!=0){
							 PdfPCell debidoA2 = new PdfPCell(new Paragraph(denuncia.getNombreDebidoaNivel2(),fuenteNegrita));//fuenteNormal
							 debidoA2.setBorder(0);
							 debidoA2.setPaddingLeft(15);
							 tablaDatosDenuncia.addCell(debidoA2);
						 }
						 
						 if(denuncia.getIdDebidoa3()!=0){
							 PdfPCell debidoA3 = new PdfPCell(new Paragraph(denuncia.getNombreDebidoaNivel3(),fuenteNegrita));//fuenteNormal
							 debidoA3.setBorder(0);
							 debidoA3.setPaddingLeft(15);
							 tablaDatosDenuncia.addCell(debidoA3);
						 }
						 
						 
						 
						 PdfPCell dondeSucede = new PdfPCell(new Paragraph("Donde Sucede",fuenteNormal));//fuenteNegrita
						 dondeSucede.setBorder(0);
						 dondeSucede.setPaddingLeft(15);
						 tablaDatosDenuncia.addCell(dondeSucede);
						 
						 PdfPCell dondeSucede1 = new PdfPCell(new Paragraph(denuncia.getNombreZonaSuceso1(),fuenteNegrita));// fuenteNormal
						 dondeSucede1.setBorder(0);
						 dondeSucede1.setPaddingLeft(15);
						 tablaDatosDenuncia.addCell(dondeSucede1);
						 
						 if(denuncia.getIdDebidoa2()!=0){
							 PdfPCell dondeSucede2 = new PdfPCell(new Paragraph(denuncia.getNombreZonaSuceso2(),fuenteNegrita));// fuenteNormal
							 dondeSucede2.setBorder(0);
							 dondeSucede2.setPaddingLeft(15);
							 tablaDatosDenuncia.addCell(dondeSucede2);
						 }
						 
						 if(denuncia.getIdDebidoa3()!=0){
							 
							 PdfPCell dondeSucede3 = new PdfPCell(new Paragraph(denuncia.getNombreZonaSuceso3(),fuenteNegrita));// fuenteNormal
							 dondeSucede3.setBorder(0);
							 dondeSucede3.setPaddingLeft(15);
							 tablaDatosDenuncia.addCell(dondeSucede3);
							 
						 }
						 
						 /***********************************/
						 
						 PdfPCell dondeOcurren = new PdfPCell(new Paragraph("Donde ocurren",fuenteNegrita));// 
						 dondeOcurren.setBorder(0);
						 dondeOcurren.setPaddingLeft(15);
						 tablaDatosDenuncia.addCell(dondeOcurren);
						 
						 PdfPCell dondeOcurren1 = new PdfPCell(new Paragraph(denuncia.getNombreDepartamento()+" - "+denuncia.getNombreProvincia()+" - " +denuncia.getNombreDistrito() ,fuenteNormal));// 
						 dondeOcurren1.setBorder(0);
						 dondeOcurren1.setPaddingLeft(15);
						 tablaDatosDenuncia.addCell(dondeOcurren1);
						 
						 PdfPCell dondeOcurren2 = new PdfPCell(new Paragraph("Centro poblado " + denuncia.getNombreCentroPoblado() ,fuenteNormal));// 
						 dondeOcurren2.setBorder(0);
						 dondeOcurren2.setPaddingLeft(15);
						 tablaDatosDenuncia.addCell(dondeOcurren2);
						 
						 PdfPCell dondeOcurren3 = new PdfPCell(new Paragraph(denuncia.getDireccion(),fuenteNormal));// 
						 dondeOcurren3.setBorder(0);
						 dondeOcurren3.setPaddingLeft(15);
						 tablaDatosDenuncia.addCell(dondeOcurren3);
						 
						 PdfPCell dondeOcurren4 = new PdfPCell(new Paragraph(denuncia.getReferencia() ,fuenteNormal));// 
						 dondeOcurren4.setBorder(0);
						 dondeOcurren4.setPaddingLeft(15);
						 tablaDatosDenuncia.addCell(dondeOcurren4);
						 
						 

						 //tablaDatosDenuncia.addCell(celdaSaltoLinea);
						 
						 celda7 = new PdfPCell(tablaDatosDenuncia); 
						 celda7.setBorder(0);
						 tablaCuerpoIzq.addCell(celda7);
						 

					 PdfPCell celdaCuerpoIzq = new PdfPCell(tablaCuerpoIzq); 
					 celdaCuerpoIzq.setBorder(0);
				//Mostrar tabla izquierda
				tablaCuerpo.addCell(celdaCuerpoIzq);
				/******************************* Final : Izquierda *************************/
				/******************************* Incio : Derecho *************************/
				
			    PdfPTable tablaCuerpoDer = new PdfPTable(1); // 2 Columnas
			    tablaCuerpoDer.setWidthPercentage(100);
				
					 //celda7.addElement(tablaDatosDenuncia);
					 //tablaCabecera.addCell(celda7);
					 
					 
					 /**************************************************************/
					 /*															   */
					 /***************************************************************/ 
					 
					 //PdfPCell celda8 = new PdfPCell(new Paragraph(""));
					 //tablaCabecera.addCell(celda8);
					 
					 
					 PdfPTable tablaDatosDenunciado = new PdfPTable(1);  // Tabla Datos denunciado
					 tablaDatosDenunciado.setWidthPercentage(100);
					 
					 PdfPCell celdaDatosDenunciado = new PdfPCell(new Paragraph("3- Datos del denunciado",fuenteNegrita));
					 celdaDatosDenunciado.setBorder(0);
					 tablaDatosDenunciado.addCell(celdaDatosDenunciado);
					 
					 if(denuncia.getTipoResponsable()!=0 && denuncia.getResponsableProblema()!=0){
						 /*******************************/
						 PdfPCell celdaDatosDenunciado1 = new PdfPCell(new Paragraph("Nombre : " + denuncia.getNombreDenunciado(),fuenteNormal));
						 celdaDatosDenunciado1.setBorder(0);
						 celdaDatosDenunciado1.setPaddingLeft(15);
						 tablaDatosDenunciado.addCell(celdaDatosDenunciado1);
						 
						 PdfPCell celdaDatosDenunciado2 = new PdfPCell(new Paragraph("Documento : " + denuncia.getDocumentoDenunciado(),fuenteNormal));
						 celdaDatosDenunciado2.setBorder(0);
						 celdaDatosDenunciado2.setPaddingLeft(15);
						 tablaDatosDenunciado.addCell(celdaDatosDenunciado2);
						 
						 PdfPCell celdaDatosDenunciado3 = new PdfPCell(new Paragraph("Ubigeo : ",fuenteNormal));
						 celdaDatosDenunciado3.setBorder(0);
						 celdaDatosDenunciado3.setPaddingLeft(15);
						 tablaDatosDenunciado.addCell(celdaDatosDenunciado3);
						 
						 PdfPCell celdaDatosDenunciado4 = new PdfPCell(new Paragraph(denuncia.getNombreDepartamentoDenunciado().toUpperCase()+" / " + denuncia.getNombreProvinciaDenunciado().toUpperCase()+" / "+denuncia.getNombreDistritoDenunciado().toUpperCase(),fuenteNormal));
						 celdaDatosDenunciado4.setBorder(0);
						 celdaDatosDenunciado4.setPaddingLeft(15);
						 tablaDatosDenunciado.addCell(celdaDatosDenunciado4);
						 
//						 PdfPCell celdaDatosDenunciado3 = new PdfPCell(new Paragraph("Departamento " + denuncia.getNombreDepartamentoDenunciado(),fuenteNormal));
//						 celdaDatosDenunciado3.setBorder(0);
//						 celdaDatosDenunciado3.setPaddingLeft(15);
//						 tablaDatosDenunciado.addCell(celdaDatosDenunciado3);
//						 
//						 PdfPCell celdaDatosDenunciado4 = new PdfPCell(new Paragraph("Provincia " + denuncia.getNombreProvinciaDenunciado(),fuenteNormal));
//						 celdaDatosDenunciado4.setBorder(0);
//						 celdaDatosDenunciado4.setPaddingLeft(15);
//						 tablaDatosDenunciado.addCell(celdaDatosDenunciado4);
//						 
//						 PdfPCell celdaDatosDenunciado5 = new PdfPCell(new Paragraph("Distrito " + denuncia.getNombreDistritoDenunciado(),fuenteNormal));
//						 celdaDatosDenunciado5.setBorder(0);
//						 celdaDatosDenunciado5.setPaddingLeft(15);
//						 tablaDatosDenunciado.addCell(celdaDatosDenunciado5);
//						 
						 if(denuncia.getDireccionDenunciado()!=null){
							 PdfPCell celdaDatosDenunciado6 = new PdfPCell(new Paragraph("Dirección : " + denuncia.getDireccionDenunciado(),fuenteNormal));
							 celdaDatosDenunciado6.setBorder(0);
							 celdaDatosDenunciado6.setPaddingLeft(15);
							 tablaDatosDenunciado.addCell(celdaDatosDenunciado6);
						 }
						 
					 }else{
						 
						 PdfPCell celdaDatosDenunciado1 = new PdfPCell(new Paragraph("No se identificó al denunciado",fuenteNormal));
						 celdaDatosDenunciado1.setBorder(0);
						 celdaDatosDenunciado1.setPaddingLeft(15);
						 tablaDatosDenunciado.addCell(celdaDatosDenunciado1);
						 
					 }

					 tablaDatosDenunciado.addCell(celdaSaltoLinea);
					 
					 PdfPCell celda8 = new PdfPCell(tablaDatosDenunciado);
					 celda8.setBorder(0);
					 tablaCuerpoDer.addCell(celda8);
					 
					 //celda8.addElement(tablaDatosDenunciado);
					 /******************************************************************************/
					 /******************************************************************************/
					 /******************************************************************************/
					 
					 
					
					 
					 PdfPTable tablaMediosProbatorios = new PdfPTable(1);  // Tabla Medios Probatorios
					 tablaMediosProbatorios.setWidthPercentage(100);
						
					 PdfPCell celdaDatosMediosProbatorios = new PdfPCell(new Paragraph("4- Medios probatorios",fuenteNegrita));
					 celdaDatosMediosProbatorios.setBorder(0);
					 tablaMediosProbatorios.addCell(celdaDatosMediosProbatorios);
					 
					 
					 if(denuncia.getLstArchivoDenuncia()!=null && denuncia.getLstArchivoDenuncia().size()>0 ){
						 
						 for (ArchivoDenuncia mediosProbatorios : denuncia.getLstArchivoDenuncia()) {
							 Maestro maestroMedioProbatorio = new Maestro();
							 
							
							 PdfPCell celdaDatosMediosProbatorios1 = new PdfPCell(new Paragraph(mediosProbatorios.getNombreTipoArchivo() + " - " + mediosProbatorios.getNombreArchivo(),fuenteNormal));
							 celdaDatosMediosProbatorios1.setBorder(0);
							 celdaDatosMediosProbatorios1.setPaddingLeft(15);
							 tablaMediosProbatorios.addCell(celdaDatosMediosProbatorios1);
							 
							 
							 
						}
						 
					 }else{
						 
						 PdfPCell celdaDatosMediosProbatorios1 = new PdfPCell(new Paragraph("No se subio Medios Probatorios",fuenteNormal));
						 celdaDatosMediosProbatorios1.setBorder(0);
						 celdaDatosMediosProbatorios1.setPaddingLeft(15);
						 tablaMediosProbatorios.addCell(celdaDatosMediosProbatorios1);
						 
					 }
					 /*******************************/
					 //celda8.addElement(tablaMediosProbatorios);
					 tablaMediosProbatorios.addCell(celdaSaltoLinea);
					 
					 celda8 = new PdfPCell(tablaMediosProbatorios);
					 celda8.setBorder(0);
					 tablaCuerpoDer.addCell(celda8);
					 
					 
				
					 
					 /*************************************************************************************/
					 
					 
					 PdfPTable tablaEntidadesCompetentes = new PdfPTable(1);  // Tabla Medios Probatorios
					 tablaEntidadesCompetentes.setWidthPercentage(100);
					 
					 PdfPCell celdaEntidadesCompetentes = new PdfPCell(new Paragraph("5- Entidades competentes",fuenteNegrita));
					 celdaEntidadesCompetentes.setBorder(0);
					 tablaEntidadesCompetentes.addCell(celdaEntidadesCompetentes);
					 
					 
					 if(denuncia.getLstEntidadCaso()!=null && denuncia.getLstEntidadCaso().size()>0 ){
						 int x =1;
						 for (EntidadCaso entidadCompetente : denuncia.getLstEntidadCaso()) {
							
							 PdfPCell celdaEntidadesCompetentes1 = new PdfPCell(new Paragraph(x +"-" + entidadCompetente.getNombreEntidad() + " - " + entidadCompetente.getNombreTipoEntidad(),fuenteNormal));
							 celdaEntidadesCompetentes1.setBorder(0);
							 celdaEntidadesCompetentes1.setPaddingLeft(15);
							 tablaEntidadesCompetentes.addCell(celdaEntidadesCompetentes1);
							 
							 if(entidadCompetente.getLstNormaCaso()!=null && entidadCompetente.getLstNormaCaso().size()>0){
								 for (NormaCaso normaCaso : entidadCompetente.getLstNormaCaso()) {
									 
									 PdfPCell celdaNormas = new PdfPCell(new Paragraph(normaCaso.getContenidoNorma().getNombreTipoDispositivo()+" Nº " +  normaCaso.getContenidoNorma().getNumeroNorma() +", " + " Articulo " + normaCaso.getContenidoNorma().getNumeroArticulo() + ", " +  normaCaso.getContenidoNorma().getDescripcionArticulo(),fuenteNormal));
									 celdaNormas.setPaddingLeft(15);
									 celdaNormas.setBorder(0);
									 tablaEntidadesCompetentes.addCell(celdaNormas);
									
								}
							 }
							
							x = x+1; 
						}
						 
					 }else{
						 
						 PdfPCell celdaEntidadesCompetentes1 = new PdfPCell(new Paragraph("No hay Entidades competentes",fuenteNormal));
						 celdaEntidadesCompetentes1.setBorder(0);
						 celdaEntidadesCompetentes1.setPaddingLeft(15);
						 tablaEntidadesCompetentes.addCell(celdaEntidadesCompetentes1);
						 
					 }
					 
				
					 /*******************************/
					 //celda8.addElement(tablaEntidadesCompetentes);

					 celda8 = new PdfPCell(tablaEntidadesCompetentes);
					 celda8.setBorder(0);
					 tablaCuerpoDer.addCell(celda8);
					 
				 PdfPCell celdaCuerpoDer = new PdfPCell(tablaCuerpoDer); 
				 celdaCuerpoDer.setBorder(0);
			//Mostrar tabla izquierda
			tablaCuerpo.addCell(celdaCuerpoDer);
					 //tablaCabecera.addCell(celda8);
					 
					 /**********************************************************************************/
					 
					 
					 
					 
					 /********************************************************************/
					 
					 documento.add(tablaCuerpo);
					/*********************************************************************/
					 
					 
					 
					 
//					FormatoCorreo formatoCorreo = new FormatoCorreo();
//					long idPlantilla = 5;
//					formatoCorreo =  formatoCorreoService.buscarPorId(idPlantilla);
//					 
//					String dataHTMLString = formatoCorreo.getMensaje();
					
					
					/*String dataHtml = denuncia.getDataHTML();
					ServletContext sc= request.getServletContext();
					String pathSO=sc.getRealPath("/");
					String logoOEfa =pathSO+ "assets/images/oefa-logo-header.png";
					String logoSinad =pathSO+ "assets/images/logo_sinada.png";
					
					
					
					dataHtml = dataHtml.replace("assets/images/oefa-logo-header.png", logoOEfa);
					dataHtml = dataHtml.replace("assets/images/logo_sinada.png", logoSinad);*/
					
//					dataHtmlBase = dataHtmlBase.replace("DataFichaDenunciaHTMLReemplazo", dataHtml);
//					HTMLWorker htmlWorker = new HTMLWorker(documento);
//					htmlWorker.parse(new StringReader(dataHTMLString));

					documento.close();
					
					String rutaFinal= "fichaDenuncia/"+identificadorArchivo+"/";
					String urlLocal = VO.getBasicPath() + "/"+ rutaFinal + nombreArchivo;
					
					String flagAlfresco = ResourceUtil.getKey("file.alfreso");
					
					
					if(!flagAlfresco.equals("0")) {
						File file = new File(url);
					  	/** EL SIGUIENTE CODIGO ES LA INTEGRACION CON WSALFRESCO **/
						String uiid = AlfrescoUtil.grabarArchivoAlfresco(file, rutaFinal);
												
						if (VO.isNullOrEmpty(uiid)) {
							respuesta.setMensaje("No se pudo archivar el documento en Alfresco.");
							return respuesta;
						}
						denuncia.setUiid(uiid);
					}
					
					Denuncia prmDenuncia = new Denuncia();
					
					prmDenuncia.setUiid(denuncia.getUiid());
					prmDenuncia.setIdDenuncia(denuncia.getIdDenuncia());
					prmDenuncia.setRutaFicha("/"+rutaFinal);
					
					int result = denunciaService.subirFichaDenuncia(prmDenuncia);
					
					respuesta.setData(urlLocal);
					respuesta.setValido(true);	
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			respuesta.setMensaje("Error al obtener la información");
		}
		return respuesta;
	}	
	
//	@ResponseBody
//	@RequestMapping(value = "/generar-pdf-ficha-denuncia-html",method = RequestMethod.POST)
//	public RespuestaHttp obtenerPDFFichaDenunciaHtml(@RequestBody VistaDenuncia denuncia,HttpServletRequest request) throws Exception
//	{
//		String url = "";
//		RespuestaHttp respuesta = new RespuestaHttp();
//		try
//		{			
//			String separador= ResourceUtil.getKey("file.path.separador");
//			String identificadorArchivo = "nombre";
//			String rutaBaseFichaDenuncia = ResourceUtil.getKey("file.path.denuncias-ficha");
//			
////			String rutaBase = ResourceUtil.getKey("file.path.base");
////			String folderFichaDenuncia = ResourceUtil.getKey("file.path.denuncias-ficha");
//			
////			String folder_base_denuncia = "";
//			
////			String folder_base_denuncia = ResourceUtil.getKey("file.path.denuncia.archivos.windows.base");
//			
////			String serve = ResourceUtil.getKey("file.server");
//			
////			if(serve!=null && serve.trim().equals("2")){
////				folder_base_denuncia = ResourceUtil.getKey("file.path.denuncia.archivos.linux.base");
////			}
//			
////			folder_base_denuncia = folder_base_denuncia.replace("{separador}", separador);
//			
//			//String folder = "D:\\oefa\\sinada\\denuncia\\temporales\\medios\\";
//			
////			folder_base_denuncia =folderFichaDenuncia+separador+"temporales"+ separador +"medios"+ separador;
//			
//			String folder = "";
//			
//			String nombreArchivo = "denuncia.pdf";
//			
//			//Tipo de documento de denuncia final, cuando la denuncia ya ha sido registrada
//			
//				identificadorArchivo = VO.dateToString(new Date());
//				identificadorArchivo = identificadorArchivo.replace("/", "-");
//				//folder = "D:\\oefa\\fichas-denuncia\\";
//				folder = ResourceUtil.getKey("file.path.base") + rutaBaseFichaDenuncia ;
//				nombreArchivo = denuncia.getCodigoDenuncia()+".pdf";
//	
//			
//				//ByteArrayOutputStream pdf = new ByteArrayOutputStream();
//				HttpHeaders responseHeaders = new HttpHeaders();
//				
//				
//					
//					Image logoOefa =  Image.getInstance(getClass().getResource("img/oefa-logo-header.png"));
//					logoOefa.scaleAbsolute(180,42);
//					logoOefa.setAlignment(Element.ALIGN_LEFT);
//					logoOefa.setAbsolutePosition(35, 770);
//					
//					Image logoSinada =  Image.getInstance(getClass().getResource("img/sinada-big.png"));
//					logoSinada.scaleAbsolute(150,42);
//					logoSinada.setAlignment(Element.ALIGN_RIGHT);
//					logoSinada.setAbsolutePosition(PageSize.A4.getWidth()-185, 770);
//					
//				//BaseFont baseFont = BaseFont.createFont("C:\\Windows\\Fonts\\arialuni.ttf", BaseFont.IDENTITY_H,true);
//				//Font fuenteNormal = new Font(baseFont,12, Font.NORMAL);
//				//Font fuenteNormal = new Font(Font.FontFamily.UNDEFINED, 12, Font.NORMAL);
//				
//				//Font fuenteNumeracion = new Font(Font.FontFamily.UNDEFINED, 12, Font.BOLD);
//				//Font fuenteNumeracion = new Font(baseFont, 12, Font.BOLD);
//				
//				//responseHeaders.setContentType(MediaType.valueOf(value)
//				responseHeaders.setContentType(MediaType.valueOf("application/pdf;charset=utf-8"));
////				responseHeaders.set
//				
//				String direccion = folder +identificadorArchivo;
//					//url = direccion +"\\"+ nombreArchivo;
//					url = direccion + File.separator + nombreArchivo;
//					
//					File directorio = new File(direccion);
//					directorio.mkdirs();
//					
//					File fichero = new File(url);
//					if (fichero.exists()){
//						fichero.delete();
//					}
//					
//				//Fuente de Letra
//				    Font fuenteNegrita = new Font(FontFamily.HELVETICA, 10, Font.BOLD);
//				    Font fuenteNormal = new Font(FontFamily.HELVETICA, 10, Font.NORMAL);
//					
//			   
//					
//				Document documento = new Document(PageSize.A4,25, 25, 25, 25);
//				
//				FileOutputStream fos = new FileOutputStream(url);
//		        //Writer w = new BufferedWriter();
//
//		        //PdfWriter pdfwDemo = PdfWriter.getInstance(documento, new OutputStreamWriter(fos, StandardCharsets.UTF_8));
//				PdfWriter pdfwDemo = PdfWriter.getInstance(documento, fos);
//				
//					documento.open();
////					documento.add(logoOefa);
//					
//					Paragraph saltoLinea = new Paragraph("\n");
//					 
//					PdfPCell celdaSaltoLinea = new PdfPCell(new Paragraph("",fuenteNormal));// CeldaTitulo de la Tabla DEnunciante
//					celdaSaltoLinea.setBorder(0);
//					/******************************************************************/
//					
//					Image logoCabecera =  Image.getInstance(getClass().getResource("img/logo_cabecera.jpg"));
//					logoOefa.scaleAbsolute(180,42);
//					logoOefa.setAlignment(Element.ALIGN_LEFT);
//					logoOefa.setAbsolutePosition(35, 770);
//					
//					documento.add(logoCabecera);
//					
//					
//					 PdfPTable tablaCabecera = new PdfPTable(2); // 2 Columnas
//					 tablaCabecera.setWidthPercentage(100);
//						 
//						 PdfPTable tablaCabecera01 = new PdfPTable(2); // 2 Columnas
//						 tablaCabecera01.setWidthPercentage(100);
//						 tablaCabecera01.setWidths(new float[] { 32, 68 });
//						 
//							 PdfPCell celda01 = new PdfPCell(new Paragraph("Código Denuncia ",fuenteNormal));
//							 celda01.setBorder(0);
//							 celda01.setBackgroundColor(new BaseColor(238, 238, 239));
//							 tablaCabecera01.addCell(celda01);
//							 
//							 PdfPCell celda01Dato = new PdfPCell(new Paragraph(denuncia.getCodigoDenuncia(),fuenteNegrita));
//							 celda01Dato.setBorder(0);
//							 celda01Dato.setBackgroundColor(new BaseColor(238, 238, 239));
//							 tablaCabecera01.addCell(celda01Dato);
//						 
//						 PdfPCell celda1 = new PdfPCell(tablaCabecera01);
//						 celda1.setBorder(0);
//						 celda1.setBackgroundColor(new BaseColor(238, 238, 239));
//						 celda1.setPaddingLeft(15);
//						 celda1.setPaddingTop(10);
//						 tablaCabecera.addCell(celda1);
//						 
//						 
//						 PdfPCell celda2 = new PdfPCell(new Paragraph("Características de la denuncia ", fuenteNegrita));
//						 celda2.setBorder(0);
//						 celda2.setPaddingTop(10);
//						 celda2.setBackgroundColor(new BaseColor(238, 238, 239));
//						 tablaCabecera.addCell(celda2);
//						 
//						 
//						 PdfPTable tablaCabecera03 = new PdfPTable(2); // 2 Columnas
//						 tablaCabecera03.setWidthPercentage(100);
//						 tablaCabecera03.setWidths(new float[] { 35, 65 });
//						 
//							 PdfPCell celda03 = new PdfPCell(new Paragraph("Código de acceso ",fuenteNormal));
//							 celda03.setBorder(0);
//							 celda03.setBackgroundColor(new BaseColor(238, 238, 239));
//							 tablaCabecera03.addCell(celda03);
//							 
//							 PdfPCell celda03Dato = new PdfPCell(new Paragraph( denuncia.getCodigoacceso(),fuenteNegrita));
//							 celda03Dato.setBorder(0);
//							 celda03Dato.setBackgroundColor(new BaseColor(238, 238, 239));
//							 tablaCabecera03.addCell(celda03Dato);
//						 
//						 PdfPCell celda3 = new PdfPCell(tablaCabecera03);
//						 celda3.setBorder(0);
//						 celda3.setBackgroundColor(new BaseColor(238, 238, 239));
//						 celda3.setPaddingLeft(15);
//						 tablaCabecera.addCell(celda3);
//						 
//						 
//						 PdfPTable tablaCabecera04 = new PdfPTable(2); // 2 Columnas
//						 tablaCabecera04.setWidthPercentage(100);
//						 tablaCabecera04.setWidths(new float[] { 30, 70 });
//						 
//							 PdfPCell celda04 = new PdfPCell(new Paragraph("Tipo de denuncia ",fuenteNormal));
//							 celda04.setBorder(0);
//							 celda04.setBackgroundColor(new BaseColor(238, 238, 239));
//							 tablaCabecera04.addCell(celda04);
//							 
//							 PdfPCell celda04Dato = new PdfPCell(new Paragraph( denuncia.getNombreTipoDenuncia(),fuenteNegrita));
//							 celda04Dato.setBorder(0);
//							 celda04Dato.setBackgroundColor(new BaseColor(238, 238, 239));
//							 tablaCabecera04.addCell(celda04Dato);
//						 
//						 PdfPCell celda4 = new PdfPCell(tablaCabecera04);
//						 celda4.setBorder(0);
//						 celda4.setBackgroundColor(new BaseColor(238, 238, 239));
//						 tablaCabecera.addCell(celda4);
//						 
//						 /*
//						 PdfPCell celda5 = new PdfPCell(new Paragraph(""));
//						 celda5.setBorder(0);
//						 celda5.setBackgroundColor(new BaseColor(238, 238, 239));
//						 tablaCabecera.addCell(celda5);
//						 */
//						 
//						 PdfPTable tablaCabecera05 = new PdfPTable(2); // 2 Columnas
//						 tablaCabecera05.setWidthPercentage(100);
//						 tablaCabecera05.setWidths(new float[] { 35, 65 });
//						 
//						 if(denuncia.getHojaTramite()!=null){
//							 PdfPCell celda05 = new PdfPCell(new Paragraph("Hoja de Trámite ",fuenteNormal));
//							 celda05.setBorder(0);
//							 celda05.setBackgroundColor(new BaseColor(238, 238, 239));
//							 tablaCabecera05.addCell(celda05);
//							 
//							 PdfPCell celda05Dato = new PdfPCell(new Paragraph( denuncia.getHojaTramite(),fuenteNegrita));
//							 celda05Dato.setBorder(0);
//							 celda05Dato.setBackgroundColor(new BaseColor(238, 238, 239));
//							 tablaCabecera05.addCell(celda05Dato);
//						 }
//							 
//						 
//						 PdfPCell celda5 = new PdfPCell(tablaCabecera05);
//						 celda5.setBorder(0);
//						 celda5.setBackgroundColor(new BaseColor(238, 238, 239));
//						 celda5.setPaddingLeft(15);
//						 tablaCabecera.addCell(celda5);
//						 //
//						 
//						 PdfPTable tablaCabecera06 = new PdfPTable(2); // 2 Columnas
//						 tablaCabecera06.setWidthPercentage(100);
//						 tablaCabecera06.setWidths(new float[] { 35, 65 });
//						 
//							 PdfPCell celda06 = new PdfPCell(new Paragraph("Medio de recepción ",fuenteNormal));
//							 celda06.setBorder(0);
//							 celda06.setBackgroundColor(new BaseColor(238, 238, 239));
//							 tablaCabecera06.addCell(celda06);
//							 
//							 PdfPCell celda06Dato = new PdfPCell(new Paragraph( denuncia.getNombreMedioRecepcion(),fuenteNegrita));
//							 celda06Dato.setBorder(0);
//							 celda06Dato.setBackgroundColor(new BaseColor(238, 238, 239));
//							 tablaCabecera06.addCell(celda06Dato);
//						 
//						 PdfPCell celda6 = new PdfPCell(tablaCabecera06);
//						 celda6.setBorder(0);
//						 celda6.setBackgroundColor(new BaseColor(238, 238, 239));
//						 celda6.setPaddingBottom(15);
//						 tablaCabecera.addCell(celda6);
//					 
//						 
//				    documento.add(tablaCabecera);
//				    
//				    documento.add(saltoLinea);
//				    
//				    PdfPTable tablaCuerpo = new PdfPTable(2); // 2 Columnas
//				    tablaCuerpo.setWidthPercentage(100);
//				    /***************************** Incio : Izquierod ******************************/
//				    PdfPTable tablaCuerpoIzq = new PdfPTable(1); // 2 Columnas
//				    tablaCuerpoIzq.setWidthPercentage(100);
//				    //PdfPTable table = new PdfPTable(2); // 2 Columnas
//					//PdfPCell celda7 = new PdfPCell(new Paragraph(""));   // Tabla GEneral vista previa 1
//					//table.addCell(celda7);
//						 /****************  Comienza Tabla DEnunciante ************************/
//						 PdfPTable tablaDenunciantes = new PdfPTable(1);  // Tabla Denunciante
//						 tablaDenunciantes.setWidthPercentage(100);
//							
//						 PdfPCell celdaDenunciante = new PdfPCell(new Paragraph("1- Datos del denunciante",fuenteNegrita));// CeldaTitulo de la Tabla DEnunciante
//						 celdaDenunciante.setBorder(0);
//						 tablaDenunciantes.addCell(celdaDenunciante);
//						 if(denuncia.getLstDenunciante().size()>0 && denuncia.getTipoDenuncia()==3){
//							 int numeroDenunciante = 1;
//							 for (Denunciante denunciantes : denuncia.getLstDenunciante()) {
//								 if(denunciantes.getTipoPersona()==1){
//									 	
//									 PdfPCell celdaDenunciante1 = new PdfPCell(new Paragraph("Denunciante Nº " + numeroDenunciante));// CeldaTitulo de la Tabla DEnunciante
//									 celdaDenunciante1.setBorder(0);
//									 tablaDenunciantes.addCell(celdaDenunciante1);
//									 
//									 PdfPCell celdaDenunciante2 = new PdfPCell(new Paragraph("DNI " + denunciantes.getPersonaDenunciante().getDocumento()));
//									 celdaDenunciante2.setBorder(0);
//									 tablaDenunciantes.addCell(celdaDenunciante2);
//									 
//									 PdfPCell celdaDenunciante3 = new PdfPCell(new Paragraph("Nombres " + denunciantes.getPersonaDenunciante().getPrimerNombre()+ " " +denunciantes.getPersonaDenunciante().getSegundoNombre() ));
//									 celdaDenunciante3.setBorder(0);
//									 tablaDenunciantes.addCell(celdaDenunciante3);
//									 
//									 PdfPCell celdaDenunciante4 = new PdfPCell(new Paragraph("Apellido paterno " + denunciantes.getPersonaDenunciante().getApellidoPaterno()));
//									 celdaDenunciante4.setBorder(0);
//									 tablaDenunciantes.addCell(celdaDenunciante4);
//									 
//									 PdfPCell celdaDenunciante5 = new PdfPCell(new Paragraph("Apellido materno " + denunciantes.getPersonaDenunciante().getApellidoMaterno()));
//									 celdaDenunciante5.setBorder(0);
//									 tablaDenunciantes.addCell(celdaDenunciante5);
//									 
//									 PdfPCell celdaDenunciante6 = new PdfPCell(new Paragraph("Dirección " + denunciantes.getPersonaDenunciante().getDireccion()));
//									 celdaDenunciante6.setBorder(0);
//									 celdaDenunciante6.setPaddingBottom(15);
//									 tablaDenunciantes.addCell(celdaDenunciante6);
//									 
//								 }else{
//									 
//									 PdfPCell celdaDenunciante1 = new PdfPCell(new Paragraph("Denunciante Nº " + numeroDenunciante));// CeldaTitulo de la Tabla DEnunciante
//									 celdaDenunciante1.setBorder(0);
//									 tablaDenunciantes.addCell(celdaDenunciante1);
//									 
//									 PdfPCell celdaDenunciante2 = new PdfPCell(new Paragraph("RUC " + denunciantes.getEntidadDenunciante().getRuc()));
//									 celdaDenunciante2.setBorder(0);
//									 tablaDenunciantes.addCell(celdaDenunciante2);
//									 
//									 PdfPCell celdaDenunciante3 = new PdfPCell(new Paragraph("Razon social " + denunciantes.getEntidadDenunciante().getRazonSocial()));
//									 celdaDenunciante3.setBorder(0);
//									 tablaDenunciantes.addCell(celdaDenunciante3);
//									 
//									 PdfPCell celdaDenunciante4 = new PdfPCell(new Paragraph("Representante legal " + denunciantes.getEntidadDenunciante().getNomRepresentante()));
//									 celdaDenunciante4.setBorder(0);
//									 tablaDenunciantes.addCell(celdaDenunciante4);
//									 
//									 PdfPCell celdaDenunciante5 = new PdfPCell(new Paragraph("Cargo representante " + denunciantes.getEntidadDenunciante().getNomCargo()));
//									 celdaDenunciante5.setBorder(0);
//									 tablaDenunciantes.addCell(celdaDenunciante5);
//									 
//									 PdfPCell celdaDenunciante6 = new PdfPCell(new Paragraph("Dirección fiscal" + denunciantes.getEntidadDenunciante().getDireccion()));
//									 celdaDenunciante6.setBorder(0);
//									 tablaDenunciantes.addCell(celdaDenunciante6);
//									 
//									 
//								 }
//								
//								 numeroDenunciante = numeroDenunciante+1;
//								}
//							 
//						 }else{
//							 PdfPCell celdaDenunciante1 = new PdfPCell(new Paragraph(denuncia.getNombreTipoDenuncia(),fuenteNormal));// CeldaTitulo de la Tabla DEnunciante
//							 celdaDenunciante1.setBorder(0);
//							 celdaDenunciante1.setPaddingLeft(15);
//							 tablaDenunciantes.addCell(celdaDenunciante1);
//						 }
//						 
//						 tablaDenunciantes.addCell(celdaSaltoLinea);
//						 
//						 PdfPCell celda7 = new PdfPCell(tablaDenunciantes); 
//						 celda7.setBorder(0);
//						 tablaCuerpoIzq.addCell(celda7);
//						 
//						 //celda7.addElement(tablaDenunciantes);
//						  /**************************************************************/
//						 /*															   */
//						 /***************************************************************/
//						 
//						 /****************  Comienza Tabla DAtos de la denuncia ************************/
//						 PdfPTable tablaDatosDenuncia = new PdfPTable(1);  // Tabla Datos Denuncia
//						 tablaDatosDenuncia.setWidthPercentage(100);
//							
//						 
//						 PdfPCell celdaDatosDenuncia = new PdfPCell(new Paragraph("2- Datos de la denuncia",fuenteNegrita));// CeldaTitulo de Datos Denuncia
//						 celdaDatosDenuncia.setBorder(0);
//						 tablaDatosDenuncia.addCell(celdaDatosDenuncia);
//						
//						 /***********************************/
//						 
//						 PdfPCell hechosDenunciados = new PdfPCell(new Paragraph("Hechos Denunciados:",fuenteNegrita));//
//						 hechosDenunciados.setBorder(0);
//						 hechosDenunciados.setPaddingLeft(15);
//						 tablaDatosDenuncia.addCell(hechosDenunciados);
//						 
//						 PdfPTable tablaProblematica = new PdfPTable(1); // 2 Columnas
//						 tablaProblematica.setWidthPercentage(100);
//						 //tablaProblematica.setWidths(new float[] { 35, 65 });
//						 
//							 PdfPCell celdaProblematicaTitulo = new PdfPCell(new Paragraph("Problemática ",fuenteNormal));
//							 celdaProblematicaTitulo.setBorder(0);
//							 tablaProblematica.addCell(celdaProblematicaTitulo);
//							 
//							 PdfPCell celdaProblematicaDato = new PdfPCell(new Paragraph( denuncia.getNombreProblematica(),fuenteNegrita));
//							 celdaProblematicaDato.setBorder(0);
//							 tablaProblematica.addCell(celdaProblematicaDato);
//						 
//						 PdfPCell celdaProblematica = new PdfPCell(tablaProblematica);
//						 celdaProblematica.setBorder(0);
//						 celdaProblematica.setPaddingLeft(15);
//						 tablaDatosDenuncia.addCell(celdaProblematica);
//						 
//						 //PdfPCell problematica = new PdfPCell(new Paragraph("Problemática " + denuncia.getNombreProblematica()));// 
//						 //tablaDatosDenuncia.addCell(problematica);
//						 
//						 PdfPCell debidoA = new PdfPCell(new Paragraph("Debido a",fuenteNormal));//fuenteNegrita
//						 debidoA.setBorder(0);
//						 debidoA.setPaddingLeft(15);
//						 tablaDatosDenuncia.addCell(debidoA);
//						 
//						 PdfPCell debidoA1 = new PdfPCell(new Paragraph(denuncia.getNombreDebidoaNivel1(),fuenteNegrita));//fuenteNormal
//						 debidoA1.setBorder(0);
//						 debidoA1.setPaddingLeft(15);
//						 tablaDatosDenuncia.addCell(debidoA1);
//						 
//						 if(denuncia.getIdDebidoa2()!=0){
//							 PdfPCell debidoA2 = new PdfPCell(new Paragraph(denuncia.getNombreDebidoaNivel2(),fuenteNegrita));//fuenteNormal
//							 debidoA2.setBorder(0);
//							 debidoA2.setPaddingLeft(15);
//							 tablaDatosDenuncia.addCell(debidoA2);
//						 }
//						 
//						 if(denuncia.getIdDebidoa3()!=0){
//							 PdfPCell debidoA3 = new PdfPCell(new Paragraph(denuncia.getNombreDebidoaNivel3(),fuenteNegrita));//fuenteNormal
//							 debidoA3.setBorder(0);
//							 debidoA3.setPaddingLeft(15);
//							 tablaDatosDenuncia.addCell(debidoA3);
//						 }
//						 
//						 
//						 
//						 PdfPCell dondeSucede = new PdfPCell(new Paragraph("Donde Sucede",fuenteNormal));//fuenteNegrita
//						 dondeSucede.setBorder(0);
//						 dondeSucede.setPaddingLeft(15);
//						 tablaDatosDenuncia.addCell(dondeSucede);
//						 
//						 PdfPCell dondeSucede1 = new PdfPCell(new Paragraph(denuncia.getNombreZonaSuceso1(),fuenteNegrita));// fuenteNormal
//						 dondeSucede1.setBorder(0);
//						 dondeSucede1.setPaddingLeft(15);
//						 tablaDatosDenuncia.addCell(dondeSucede1);
//						 
//						 if(denuncia.getIdDebidoa2()!=0){
//							 PdfPCell dondeSucede2 = new PdfPCell(new Paragraph(denuncia.getNombreZonaSuceso2(),fuenteNegrita));// fuenteNormal
//							 dondeSucede2.setBorder(0);
//							 dondeSucede2.setPaddingLeft(15);
//							 tablaDatosDenuncia.addCell(dondeSucede2);
//						 }
//						 
//						 if(denuncia.getIdDebidoa3()!=0){
//							 
//							 PdfPCell dondeSucede3 = new PdfPCell(new Paragraph(denuncia.getNombreZonaSuceso3(),fuenteNegrita));// fuenteNormal
//							 dondeSucede3.setBorder(0);
//							 dondeSucede3.setPaddingLeft(15);
//							 tablaDatosDenuncia.addCell(dondeSucede3);
//							 
//						 }
//						 
//						 /***********************************/
//						 
//						 PdfPCell dondeOcurren = new PdfPCell(new Paragraph("Donde ocurren",fuenteNegrita));// 
//						 dondeOcurren.setBorder(0);
//						 dondeOcurren.setPaddingLeft(15);
//						 tablaDatosDenuncia.addCell(dondeOcurren);
//						 
//						 PdfPCell dondeOcurren1 = new PdfPCell(new Paragraph(denuncia.getNombreDepartamento()+" - "+denuncia.getNombreProvincia()+" - " +denuncia.getNombreDistrito() ,fuenteNormal));// 
//						 dondeOcurren1.setBorder(0);
//						 dondeOcurren1.setPaddingLeft(15);
//						 tablaDatosDenuncia.addCell(dondeOcurren1);
//						 
//						 PdfPCell dondeOcurren2 = new PdfPCell(new Paragraph("Centro poblado " + denuncia.getNombreCentroPoblado() ,fuenteNormal));// 
//						 dondeOcurren2.setBorder(0);
//						 dondeOcurren2.setPaddingLeft(15);
//						 tablaDatosDenuncia.addCell(dondeOcurren2);
//						 
//						 PdfPCell dondeOcurren3 = new PdfPCell(new Paragraph(denuncia.getDireccion(),fuenteNormal));// 
//						 dondeOcurren3.setBorder(0);
//						 dondeOcurren3.setPaddingLeft(15);
//						 tablaDatosDenuncia.addCell(dondeOcurren3);
//						 
//						 PdfPCell dondeOcurren4 = new PdfPCell(new Paragraph(denuncia.getReferencia() ,fuenteNormal));// 
//						 dondeOcurren4.setBorder(0);
//						 dondeOcurren4.setPaddingLeft(15);
//						 tablaDatosDenuncia.addCell(dondeOcurren4);
//						 
//						 
//
//						 //tablaDatosDenuncia.addCell(celdaSaltoLinea);
//						 
//						 celda7 = new PdfPCell(tablaDatosDenuncia); 
//						 celda7.setBorder(0);
//						 tablaCuerpoIzq.addCell(celda7);
//						 
//
//					 PdfPCell celdaCuerpoIzq = new PdfPCell(tablaCuerpoIzq); 
//					 celdaCuerpoIzq.setBorder(0);
//				//Mostrar tabla izquierda
//				tablaCuerpo.addCell(celdaCuerpoIzq);
//				/******************************* Final : Izquierda *************************/
//				/******************************* Incio : Derecho *************************/
//				
//			    PdfPTable tablaCuerpoDer = new PdfPTable(1); // 2 Columnas
//			    tablaCuerpoDer.setWidthPercentage(100);
//				
//					 //celda7.addElement(tablaDatosDenuncia);
//					 //tablaCabecera.addCell(celda7);
//					 
//					 
//					 /**************************************************************/
//					 /*															   */
//					 /***************************************************************/ 
//					 
//					 //PdfPCell celda8 = new PdfPCell(new Paragraph(""));
//					 //tablaCabecera.addCell(celda8);
//					 
//					 
//					 PdfPTable tablaDatosDenunciado = new PdfPTable(1);  // Tabla Datos denunciado
//					 tablaDatosDenunciado.setWidthPercentage(100);
//					 
//					 PdfPCell celdaDatosDenunciado = new PdfPCell(new Paragraph("3- Datos del denunciado",fuenteNegrita));
//					 celdaDatosDenunciado.setBorder(0);
//					 tablaDatosDenunciado.addCell(celdaDatosDenunciado);
//					 
//					 if(denuncia.getTipoResponsable()!=0 && denuncia.getResponsableProblema()!=0){
//						 /*******************************/
//						 PdfPCell celdaDatosDenunciado1 = new PdfPCell(new Paragraph("Nombre " + denuncia.getNombreDenunciado(),fuenteNormal));
//						 celdaDatosDenunciado1.setBorder(0);
//						 celdaDatosDenunciado1.setPaddingLeft(15);
//						 tablaDatosDenunciado.addCell(celdaDatosDenunciado1);
//						 
//						 PdfPCell celdaDatosDenunciado2 = new PdfPCell(new Paragraph("Documento " + denuncia.getDocumentoDenunciado(),fuenteNormal));
//						 celdaDatosDenunciado2.setBorder(0);
//						 celdaDatosDenunciado2.setPaddingLeft(15);
//						 tablaDatosDenunciado.addCell(celdaDatosDenunciado2);
//						 
////						 PdfPCell celdaDatosDenunciado3 = new PdfPCell(new Paragraph("Departamento " + denuncia.getNombreDepartamentoDenunciado(),fuenteNormal));
////						 celdaDatosDenunciado3.setBorder(0);
////						 celdaDatosDenunciado3.setPaddingLeft(15);
////						 tablaDatosDenunciado.addCell(celdaDatosDenunciado3);
////						 
////						 PdfPCell celdaDatosDenunciado4 = new PdfPCell(new Paragraph("Provincia " + denuncia.getNombreProvinciaDenunciado(),fuenteNormal));
////						 celdaDatosDenunciado4.setBorder(0);
////						 celdaDatosDenunciado4.setPaddingLeft(15);
////						 tablaDatosDenunciado.addCell(celdaDatosDenunciado4);
////						 
////						 PdfPCell celdaDatosDenunciado5 = new PdfPCell(new Paragraph("Distrito " + denuncia.getNombreDistritoDenunciado(),fuenteNormal));
////						 celdaDatosDenunciado5.setBorder(0);
////						 celdaDatosDenunciado5.setPaddingLeft(15);
////						 tablaDatosDenunciado.addCell(celdaDatosDenunciado5);
//						 
//						 if(denuncia.getDireccionDenunciado()!=null){
//							 PdfPCell celdaDatosDenunciado6 = new PdfPCell(new Paragraph("Dirección " + denuncia.getDireccionDenunciado(),fuenteNormal));
//							 celdaDatosDenunciado6.setBorder(0);
//							 celdaDatosDenunciado6.setPaddingLeft(15);
//							 tablaDatosDenunciado.addCell(celdaDatosDenunciado6);
//						 }
//						
//						 
//						 
//						 
//					 }else{
//						 
//						 PdfPCell celdaDatosDenunciado1 = new PdfPCell(new Paragraph("No se identifico al denunciado",fuenteNormal));
//						 celdaDatosDenunciado1.setBorder(0);
//						 celdaDatosDenunciado1.setPaddingLeft(15);
//						 tablaDatosDenunciado.addCell(celdaDatosDenunciado1);
//						 
//					 }
//
//					 tablaDatosDenunciado.addCell(celdaSaltoLinea);
//					 
//					 PdfPCell celda8 = new PdfPCell(tablaDatosDenunciado);
//					 celda8.setBorder(0);
//					 tablaCuerpoDer.addCell(celda8);
//					 
//					 //celda8.addElement(tablaDatosDenunciado);
//					 /******************************************************************************/
//					 /******************************************************************************/
//					 /******************************************************************************/
//					 
//					 
//					
//					 
//					 PdfPTable tablaMediosProbatorios = new PdfPTable(1);  // Tabla Medios Probatorios
//					 tablaMediosProbatorios.setWidthPercentage(100);
//						
//					 PdfPCell celdaDatosMediosProbatorios = new PdfPCell(new Paragraph("4- Medios probatorios",fuenteNegrita));
//					 celdaDatosMediosProbatorios.setBorder(0);
//					 tablaMediosProbatorios.addCell(celdaDatosMediosProbatorios);
//					 
//					 
//					 if(denuncia.getLstArchivoDenuncia()!=null && denuncia.getLstArchivoDenuncia().size()>0 ){
//						 
//						 for (ArchivoDenuncia mediosProbatorios : denuncia.getLstArchivoDenuncia()) {
//							 Maestro maestroMedioProbatorio = new Maestro();
//							 
//							
//							 PdfPCell celdaDatosMediosProbatorios1 = new PdfPCell(new Paragraph(mediosProbatorios.getNombreTipoArchivo() + " - " + mediosProbatorios.getNombreArchivo(),fuenteNormal));
//							 celdaDatosMediosProbatorios1.setBorder(0);
//							 celdaDatosMediosProbatorios1.setPaddingLeft(15);
//							 tablaMediosProbatorios.addCell(celdaDatosMediosProbatorios1);
//							 
//							 
//							 
//						}
//						 
//					 }else{
//						 
//						 PdfPCell celdaDatosMediosProbatorios1 = new PdfPCell(new Paragraph("No se subio Medios Probatorios",fuenteNormal));
//						 celdaDatosMediosProbatorios1.setBorder(0);
//						 celdaDatosMediosProbatorios1.setPaddingLeft(15);
//						 tablaMediosProbatorios.addCell(celdaDatosMediosProbatorios1);
//						 
//					 }
//					 /*******************************/
//					 //celda8.addElement(tablaMediosProbatorios);
//					 tablaMediosProbatorios.addCell(celdaSaltoLinea);
//					 
//					 celda8 = new PdfPCell(tablaMediosProbatorios);
//					 celda8.setBorder(0);
//					 tablaCuerpoDer.addCell(celda8);
//					 
//					 
//				
//					 
//					 /*************************************************************************************/
//					 
//					 
//					 PdfPTable tablaEntidadesCompetentes = new PdfPTable(1);  // Tabla Medios Probatorios
//					 tablaEntidadesCompetentes.setWidthPercentage(100);
//					 
//					 PdfPCell celdaEntidadesCompetentes = new PdfPCell(new Paragraph("5- Entidades competentes",fuenteNegrita));
//					 celdaEntidadesCompetentes.setBorder(0);
//					 tablaEntidadesCompetentes.addCell(celdaEntidadesCompetentes);
//					 
//					 
//					 if(denuncia.getLstEntidadCaso()!=null && denuncia.getLstEntidadCaso().size()>0 ){
//						 int x =1;
//						 for (EntidadCaso entidadCompetente : denuncia.getLstEntidadCaso()) {
//							
//							 PdfPCell celdaEntidadesCompetentes1 = new PdfPCell(new Paragraph(x +"-" + entidadCompetente.getNombreEntidad() + " - " + entidadCompetente.getNombreTipoEntidad(),fuenteNormal));
//							 celdaEntidadesCompetentes1.setBorder(0);
//							 celdaEntidadesCompetentes1.setPaddingLeft(15);
//							 tablaEntidadesCompetentes.addCell(celdaEntidadesCompetentes1);
//							 
//							 if(entidadCompetente.getLstNormaCaso()!=null && entidadCompetente.getLstNormaCaso().size()>0){
//								 for (NormaCaso normaCaso : entidadCompetente.getLstNormaCaso()) {
//									 
//									 PdfPCell celdaNormas = new PdfPCell(new Paragraph(normaCaso.getContenidoNorma().getNombreTipoDispositivo()+" Nº " +  normaCaso.getContenidoNorma().getNumeroNorma() +", " + " Articulo " + normaCaso.getContenidoNorma().getNumeroArticulo() + ", " +  normaCaso.getContenidoNorma().getDescripcionArticulo(),fuenteNormal));
//									 celdaNormas.setPaddingLeft(15);
//									 celdaNormas.setBorder(0);
//									 tablaEntidadesCompetentes.addCell(celdaNormas);
//									
//								}
//							 }
//							
//							x = x+1; 
//						}
//						 
//					 }else{
//						 
//						 PdfPCell celdaEntidadesCompetentes1 = new PdfPCell(new Paragraph("No hay Entidades competentes",fuenteNormal));
//						 celdaEntidadesCompetentes1.setBorder(0);
//						 celdaEntidadesCompetentes1.setPaddingLeft(15);
//						 tablaEntidadesCompetentes.addCell(celdaEntidadesCompetentes1);
//						 
//					 }
//					 
//				
//					 /*******************************/
//					 //celda8.addElement(tablaEntidadesCompetentes);
//
//					 celda8 = new PdfPCell(tablaEntidadesCompetentes);
//					 celda8.setBorder(0);
//					 tablaCuerpoDer.addCell(celda8);
//					 
//				 PdfPCell celdaCuerpoDer = new PdfPCell(tablaCuerpoDer); 
//				 celdaCuerpoDer.setBorder(0);
//			//Mostrar tabla izquierda
//			tablaCuerpo.addCell(celdaCuerpoDer);
//					 //tablaCabecera.addCell(celda8);
//					 
//					 /**********************************************************************************/
//					 
//					 
//					 
//					 
//					 /********************************************************************/
//					 
//					 documento.add(tablaCuerpo);
//					/*********************************************************************/
//					 
//					 
//					 
//					 
////					FormatoCorreo formatoCorreo = new FormatoCorreo();
////					long idPlantilla = 5;
////					formatoCorreo =  formatoCorreoService.buscarPorId(idPlantilla);
////					 
////					String dataHTMLString = formatoCorreo.getMensaje();
//					
//					
//					/*String dataHtml = denuncia.getDataHTML();
//					ServletContext sc= request.getServletContext();
//					String pathSO=sc.getRealPath("/");
//					String logoOEfa =pathSO+ "assets/images/oefa-logo-header.png";
//					String logoSinad =pathSO+ "assets/images/logo_sinada.png";
//					
//					
//					
//					dataHtml = dataHtml.replace("assets/images/oefa-logo-header.png", logoOEfa);
//					dataHtml = dataHtml.replace("assets/images/logo_sinada.png", logoSinad);*/
//					
////					dataHtmlBase = dataHtmlBase.replace("DataFichaDenunciaHTMLReemplazo", dataHtml);
////					HTMLWorker htmlWorker = new HTMLWorker(documento);
////					htmlWorker.parse(new StringReader(dataHTMLString));
//
//					documento.close();
//					
//					String rutaFinal= "fichaDenuncia/"+identificadorArchivo+"/";
//					String urlLocal = VO.getBasicPath() + "/"+ rutaFinal + nombreArchivo;
//					
//					String flagAlfresco = ResourceUtil.getKey("file.alfreso");
//					
//					
//					if(!flagAlfresco.equals("0")) {
//						File file = new File(url);
//					  	/** EL SIGUIENTE CODIGO ES LA INTEGRACION CON WSALFRESCO **/
//						String uiid = AlfrescoUtil.grabarArchivoAlfresco(file, rutaFinal);
//												
//						if (VO.isNullOrEmpty(uiid)) {
//							respuesta.setMensaje("No se pudo archivar el documento en Alfresco.");
//							return respuesta;
//						}
//						denuncia.setUiid(uiid);
//					}
//					
//					Denuncia prmDenuncia = new Denuncia();
//					
//					prmDenuncia.setUiid(denuncia.getUiid());
//					prmDenuncia.setIdDenuncia(denuncia.getIdDenuncia());
//					prmDenuncia.setRutaFicha("/"+rutaFinal);
//					
//					int result = denunciaService.subirFichaDenuncia(prmDenuncia);
//					
//					respuesta.setData(urlLocal);
//					respuesta.setValido(true);	
//		} 
//		catch (Exception e) 
//		{
//			e.printStackTrace();
//			respuesta.setMensaje("Error al obtener la información");
//		}
//		return respuesta;
//	}
	
	@ResponseBody
	@RequestMapping(value="/consulta-std-hoja-tramite/{anio}/{tipo}/{numero}", method=RequestMethod.GET)
	public STDHojaTramiteBean ConsultarTramite(@PathVariable("anio")String anio, @PathVariable("tipo")String tipo, @PathVariable("numero")String numero)
	{
		STDREST stdService = new STDREST();
		
		STDHojaTramiteBean stdResponse = null;
		try {
			stdResponse = stdService.obtenerHojaTramite(anio, tipo, numero);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return stdResponse;
	}
}
