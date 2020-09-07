package pe.gob.oefa.apps.sinada.presentacion.rest.api;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pe.gob.oefa.apps.base.presentacion.response.RespuestaHttp;
import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.base.util.UtilEncrypt;
import pe.gob.oefa.apps.base.util.UtilRequest;
import pe.gob.oefa.apps.base.util.properties.UtilProperties;
import pe.gob.oefa.apps.base.webservice.rest.entidad.bean.EntidadBean;
import pe.gob.oefa.apps.base.webservice.rest.entidad.servicio.EntidadREST;
import pe.gob.oefa.apps.base.webservice.rest.generico.Acceso;
import pe.gob.oefa.apps.base.webservice.rest.generico.Parametro;
import pe.gob.oefa.apps.base.webservice.rest.persona.bean.PersonaBean;
import pe.gob.oefa.apps.base.webservice.rest.persona.servicio.PersonaREST;
import pe.gob.oefa.apps.base.webservice.rest.seguridad.opcion.servicio.OpcionREST;
import pe.gob.oefa.apps.base.webservice.rest.seguridad.operacion.servicio.OperacionREST;
import pe.gob.oefa.apps.base.webservice.rest.seguridad.perfil.bean.PerfilBean;
import pe.gob.oefa.apps.base.webservice.rest.seguridad.perfil.servicio.PerfilREST;
import pe.gob.oefa.apps.base.webservice.rest.seguridad.sso.bean.SecurityIntegration;
import pe.gob.oefa.apps.base.webservice.rest.seguridad.usuario.bean.UsuarioExternoBean;
import pe.gob.oefa.apps.base.webservice.rest.seguridad.usuario.servicio.UsuarioREST;
import pe.gob.oefa.apps.base.webservice.rest.sso.bean.SSOObjectREST;
import pe.gob.oefa.apps.base.webservice.rest.sso.bean.UsuarioSSO;
import pe.gob.oefa.apps.base.webservice.rest.sso.servicio.SSOREST;
import pe.gob.oefa.apps.sinada.bean.general.Entidad;
import pe.gob.oefa.apps.sinada.bean.general.Persona;
import pe.gob.oefa.apps.sinada.bean.seguridad.Perfil;
import pe.gob.oefa.apps.sinada.bean.seguridad.PerfilUsuario;
import pe.gob.oefa.apps.sinada.bean.seguridad.SessionSistema;
import pe.gob.oefa.apps.sinada.bean.seguridad.Usuario;
import pe.gob.oefa.apps.sinada.persistencia.inf.seguridad.SessionSistemaDAO;
import pe.gob.oefa.apps.sinada.presentacion.rest.api.jobs.InitializingService;
import pe.gob.oefa.apps.sinada.presentacion.rest.api.jobs.MailJob;
import pe.gob.oefa.apps.sinada.presentacion.rest.api.jobs.MailJobListener;
import pe.gob.oefa.apps.sinada.presentacion.util.ResourceUtil;
import pe.gob.oefa.apps.sinada.servicio.inf.general.EntidadService;
import pe.gob.oefa.apps.sinada.servicio.inf.general.PersonaService;
import pe.gob.oefa.apps.sinada.servicio.inf.seguridad.PerfilService;
import pe.gob.oefa.apps.sinada.servicio.inf.seguridad.PerfilUsuarioService;
import pe.gob.oefa.apps.sinada.servicio.inf.seguridad.SessionSistemaService;
import pe.gob.oefa.apps.sinada.servicio.inf.seguridad.UsuarioService;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@Scope(value = "session")
@RequestMapping(value = "/rest/api/seguridad")
public class SeguridadRest implements Serializable {

	private static final long serialVersionUID = 108529797665381442L;
	
	@Autowired
	InitializingService initializingService;
	
	@Autowired
	SessionSistemaService sessionSistemaService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private PersonaService personaService;
	
	@Autowired
	private EntidadService entidadService;
	
	@Autowired
	private EntidadREST entidadREST;
	
	@Autowired
	private PersonaREST personaREST;
	
	@Autowired
	private PerfilService perfilService;
	
	@Autowired
	private PerfilUsuarioService perfilUsuarioService;
	
private String logBase = "oefa-sinada-web: EntidadRest";
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@RequestMapping("/index")
	public @ResponseBody RespuestaHttp showIndex(Model model,
			HttpSession session) {
		RespuestaHttp respuesta = new RespuestaHttp();
		System.out.println("En Index");
		session.removeAttribute("sessionSSO");
		session.invalidate();
		respuesta.setValido(true);
		respuesta.setMensaje("Sesion depurada - Index");
		return respuesta;
	}

	@SuppressWarnings("null")
	@RequestMapping(value = "/confirmsso", method = RequestMethod.POST)
	public void confirmSSO(SecurityIntegration reqIntegration, Model model,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws JSONException, IOException,
			Exception {
		//System.out.println("estams en el confirmSSO");
		//logger.info("El luchisss");
		initJob();
		initInitializingService();
		String referer = request.getHeader("referer");

		System.out.println("Desde: " + referer);
		System.out.println("Wresult :" + reqIntegration.getWresult());
		String trama = reqIntegration.getWresult();
		System.out.println(trama);
		int indIdUsu = trama.indexOf("PK_eIdUsuario") + 15;
		int indC1 = trama.indexOf(",", indIdUsu + 1);
		String PK_eIdUsuario = trama.substring((indIdUsu), indC1);
		System.out.println("PK_eIdUsuario :" + PK_eIdUsuario);

		int indIdCod = trama.indexOf("uCodUsuario") + 14;
		int indC2 = trama.indexOf(",", indIdCod)-1;
		String uCodUsuario = trama.substring((indIdCod), indC2);
		System.out.println("uCodUsuario :" + uCodUsuario);

		
		
		int inIdNombre = trama.indexOf("uNombreCompleto")+17;
		int indCnombre = trama.indexOf(",", inIdNombre);
		String uNombreCompleto = trama.substring((inIdNombre),indCnombre);
		//System.out.println("uNombreCompleto :" + uNombreCompleto);
		

		// /http://199.89.53.22:8084/oefa-sinada-web
//		UtilEncrypt.init(UtilProperties.getKey("encrypt.key"));
//		String ref = UtilEncrypt.encrypt(PK_eIdUsuario) + "&wk"
//				+ UtilEncrypt.encrypt(uCodUsuario);
//		System.out.println("ref : " + ref);
		
		UtilEncrypt.init(UtilProperties.getKey("encrypt.key"));
		String strPK_UsuarioEncrp = UtilEncrypt.encrypt(PK_eIdUsuario);

		
		
	/******************* Registrar Usuario - Persona **********************/
		
		int inTipoPersona = trama.indexOf("TIPO_PERSONA")+15;
		int indTipoPersona = trama.indexOf(",", inTipoPersona)-1;
		String eTipoPersona = trama.substring((inTipoPersona),indTipoPersona);
		System.out.println("eTipoPersona :" + eTipoPersona);
		
		if(eTipoPersona.equals("20")){
			
			int inNroDocumento = trama.indexOf("uNroDocumento")+16;
			int indNroDocumento = trama.indexOf(",", inNroDocumento)-1;
			String uNroDocumento = trama.substring((inNroDocumento),indNroDocumento);
			System.out.println("uNroDocumento :" + uNroDocumento);
			
			
			Persona prmPersona = new Persona();
			prmPersona = personaService.buscarPorNumeroDocumento(uNroDocumento);
			
			prmPersona = validacionRegistroPersona(prmPersona, uNroDocumento);
			
			
			/***** validar si usuario existe ****/
			
			if(prmPersona!= null){
				
				Usuario usuario = new Usuario();
				usuario = usuarioService.buscarPorId(prmPersona.getIdPersona());
				
				if(usuario==null){
					
					
					usuario = new Usuario();
					usuario.setIdUsuario(prmPersona.getIdPersona());
					usuario.setTipoPersona(1);
					usuario.setIdPersona(prmPersona.getIdPersona());
					usuario.setNombreUsuario(uCodUsuario);
					usuario.setValidaExiste(0);
//					usuario.getPerfil().setIdPerfil(perfilBean.getPK_eIdPerfil());
					registrarUsuario(usuario);
				
				}else{
					usuario.setValidaExiste(1);
					registrarUsuario(usuario);
				}
				
				
				
			
				
			}
			
			

			
		}else{
			
			int inNroRuc = trama.indexOf("uRuc")+7;
			int indNroRuc = trama.indexOf(",", inNroRuc)-1;
			String uNroRuc = trama.substring((inNroRuc),indNroRuc);
			System.out.println("uNroRuc :" + uNroRuc);

			Entidad entidad =  entidadService.buscarPorNumeroDocumento(uNroRuc);
			entidad = validacionRegistroEntidad(entidad, uNroRuc);

			if(entidad!=null){
				
				Usuario usuario = new Usuario();
				usuario = usuarioService.buscarPorId(entidad.getIdEntidad());
				
				if(usuario==null){
					usuario  = new Usuario();
					
					usuario.setIdUsuario(entidad.getIdEntidad());
					usuario.setIdPersona(entidad.getIdEntidad());
					usuario.setTipoPersona(2);
					usuario.setNombreUsuario(uCodUsuario);
					usuario.setValidaExiste(0);
					registrarUsuario(usuario);

				}else{
					usuario.setValidaExiste(1);
					registrarUsuario(usuario);
				}
				
				
				
			
				
			}
			
			
		}
		/************************* Fin Cambios Jhon ******************************/
		
		/*******************************************/
		SessionSistema prmSession = new SessionSistema();
		prmSession.setRef(strPK_UsuarioEncrp);
		long idSession = sessionSistemaService.insertar(prmSession);
		
		UtilEncrypt.init(UtilProperties.getKey("encrypt.key"));
//		String ref = UtilEncrypt.encrypt(String.valueOf(idSession)) + "&wk"
//				+ UtilEncrypt.encrypt(uCodUsuario);
		String ref = idSession + "&wk"
				+ UtilEncrypt.encrypt(uCodUsuario);
		
		
		String url = "http://localhost:3000/#/login?ref=" + ref;
		//System.out.println("url:"+url);
		//logger.info("url:"+url);
		//String url = UtilRequest.getBasicPath()+"/#/login?ref=" + ref;
		response.sendRedirect(url);
	}

	@ResponseBody
	@RequestMapping(value ="/obtener-session", method = RequestMethod.POST)
	public  RespuestaHttp ObtenerSession(@RequestBody Parametro prmData,HttpServletRequest request,HttpServletResponse response)throws Exception {
		RespuestaHttp respuesta = new RespuestaHttp();
		try{
			
//			UtilEncrypt.init(UtilProperties.getKey("encrypt.key"));
			
//			String PK_eIdUsuarioDecrypt = UtilEncrypt.decrypt(prmData.getPrm1());
			SessionSistema prmSession =  sessionSistemaService.buscarPorId(Long.valueOf(prmData.getPrm1()));
//			SessionSistema prmSession =  sessionSistemaService.buscarPorId(Long.valueOf(PK_eIdUsuarioDecrypt));
			if(prmSession!=null){
				respuesta.setValido(true);
				respuesta.setData(prmSession);
				sessionSistemaService.eliminar(prmSession);
			}else{
//				String url = "http://localhost:3000/";
////				String url = UtilRequest.getBasicPath();
//				response.sendRedirect(url);
			}
			
			
		
		}catch (Exception e) {
			logger.error(this.logBase+ " : ObtenerSession" + e.getMessage());
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al obtener la información del usuario");
		}
		return respuesta;
	}
	
	
	@RequestMapping(value = "/cambiarClave",method = RequestMethod.POST)
	public @ResponseBody RespuestaHttp doCambiarClve(@RequestBody Usuario prmUsuario, HttpServletRequest request)
			throws Exception{
		RespuestaHttp respuesta = new RespuestaHttp();
		try{

			
	
			String PK_eIdUsuarioDecrypt = UtilEncrypt.decrypt(prmUsuario.getPrm1());
						
						UsuarioREST usuarioREST = new UsuarioREST();
						UsuarioExternoBean usuario=usuarioREST.cambiarClave(Integer.valueOf(PK_eIdUsuarioDecrypt), prmUsuario.getClaveAntigua(), prmUsuario.getClaveNueva());
						
						
						
						if(usuario.getIdUsuario()>0){
							respuesta.setMensaje(usuario.getObservacion());
							respuesta.setValido(true);
						
						}else{
							respuesta.setMensaje(usuario.getObservacion());
						}
						
					
			
	
	
	

		}catch (Exception e){
			logger.error(this.logBase+ " : doCambiarClve" + e.getMessage());
			respuesta.setMensaje("Hubo un error al cambiar la clave");
			e.printStackTrace();
		}
		return respuesta;
		
	}

	@RequestMapping(value = "/opciones", method = RequestMethod.GET)
	public @ResponseBody List<Acceso> getOpciones(@RequestParam(value = "prmCriterio", required = false) String prmCriterio) {
		List<Acceso> lstAccesos = new ArrayList<>();
		try {
			UtilEncrypt.init(UtilProperties.getKey("encrypt.key"));
			Parametro prmParametro = new ObjectMapper().readValue(prmCriterio,Parametro.class);
			OpcionREST opcionREST = new OpcionREST();
			//System.out.println("ref " + prmParametro);
			String PK_eIdUsuarioDecrypt = UtilEncrypt.decrypt(prmParametro.getPrm1());
			System.out.println("PK_eIdUsuarioDecrypt " + PK_eIdUsuarioDecrypt);
			lstAccesos = opcionREST.getAccesos(PK_eIdUsuarioDecrypt,UtilProperties.getKey("aplicacion.id"));
			
		} catch (Exception e) {
			logger.error(this.logBase+ " : getOpciones" + e.getMessage());
			e.printStackTrace();
		}
		return lstAccesos;
	}
	@RequestMapping(value = "/operaciones", method = RequestMethod.GET)
	public @ResponseBody List<Acceso> getOperaciones(@RequestParam(value = "prmCriterio", required = false) String prmCriterio) {
		List<Acceso> lstAccesos = new ArrayList<>();
		List<Acceso> lstAccesosMayuscula= new ArrayList<>();
		try {
			UtilEncrypt.init(UtilProperties.getKey("encrypt.key"));
			Parametro prmParametro = new ObjectMapper().readValue(prmCriterio,Parametro.class);
			OperacionREST operacionREST = new OperacionREST();
			System.out.println("ref " + prmParametro);
			String PK_eIdUsuarioDecrypt = UtilEncrypt.decrypt(prmParametro.getPrm1());
			System.out.println("PK_eIdUsuarioDecrypt " + PK_eIdUsuarioDecrypt);
			lstAccesos = operacionREST.getAccesos(PK_eIdUsuarioDecrypt,UtilProperties.getKey("aplicacion.id"),Long.valueOf(prmParametro.getPrm2()));
			
		
			if(lstAccesos!=null && lstAccesos.size()>0){
				
				for (Acceso acceso : lstAccesos) {
					acceso.setElemento(acceso.getElemento().toUpperCase());
					lstAccesosMayuscula.add(acceso);
				}
				
			}
		} catch (Exception e) {
			logger.error(this.logBase+ " : getOperaciones" + e.getMessage());
			e.printStackTrace();
		}
		return lstAccesosMayuscula;
	}
	
	public static String corregirTexto(String data){
		
		String[] textoFormateado = data.split(" ");
		String textoCorregido ="";
		for (int i = 0; i < textoFormateado.length; i++) {
			if(!textoFormateado[i].equals("")){
				textoCorregido = textoCorregido + textoFormateado[i]+ " ";
			}
		}
		return textoCorregido;
	}
	

	private Persona validacionRegistroPersona(Persona persona, String dni) throws ServicioException {
		
		
		if (	persona != null 
			&& 	persona.getIdPersona() == 0) {
			
			/** USO DEL SERVICIO A SUNAT POR RUC Y REGISTRO **/
			PersonaBean personaBean = personaREST.getPersona(dni);
			
			if(personaBean!=null){
				/** REGISTRAR ENTIDAD A BD **/
				 persona = new Persona();
					/** REGISTRAR ENTIDAD A BD **/
					persona.setIdPersona(Long.parseLong(personaBean.getCodigo()));

					if (personaBean.getNombreCompleto() != null) {
						persona.setApellidoMaterno(personaBean.getApellidoMaterno());
						persona.setApellidoPaterno(personaBean.getApellidoPaterno());
						String nombresCorregido = corregirTexto(personaBean
								.getNombres());
						String[] nombres = nombresCorregido
								.split(" ");
						
						if (nombres[0] != null
								&& nombres[0] != "") {
							persona.setPrimerNombre(nombres[0]);
						}

						String segundoNombre = "";

						for (int i = 1; i < nombres.length; i++) {
							segundoNombre += nombres[i] + " ";
						}

						persona.setSegundoNombre(segundoNombre);
						
						
					}
					


					if (personaBean.getUbigeo() != null
							&& personaBean.getUbigeo().length() == 6) {
						String ubigeo = personaBean.getUbigeo();

						persona.setDepartamento(ubigeo.substring(0, 2));
						persona.setProvincia(ubigeo.substring(2, 4));
						persona.setDistrito(ubigeo.substring(4, 6));
					}
					persona.setDocumento(dni);// personaBean.getNroDocumento()); //
												// EL SERVICIO NO ESTA DEVOLVIENDO
												// EL NRO DOCUMENTO (DNI)
					persona.setDireccion(personaBean.getDireccion());
					System.out.println("persona:+" + persona);
				
				long idPersona = registrarPersona(persona);
				
				/** TRAER A LA ENTIDAD REGISTRADA DESDE LA BD **/
				persona =  personaService.buscarPorId(idPersona);
				
			}else{
				
				System.out.println("es igual a null");
				
				
  			}

			
		}
		
		return persona;
	}
	
	
	private long registrarPersona(Persona persona) throws ServicioException {
		System.out.println("persona:::"+persona);
		long idPersona = personaService.insertar(persona);
		
		return idPersona;
	}
	
	
	
	

private Entidad validacionRegistroEntidad(Entidad entidad, String ruc) throws ServicioException {
		
		if (	entidad == null ) {
			entidad = new Entidad();
			
			/** USO DEL SERVICIO A SUNAT POR RUC Y REGISTRO **/
			EntidadBean entidadBean = entidadREST.getEntidad(ruc,true);
			
			if(entidadBean!=null){
				/** REGISTRAR ENTIDAD A BD **/
				entidad.setIdEntidad(Long.parseLong(entidadBean.getCodigo()));
				entidad.setRazonSocial(entidadBean.getNombreCompleto());
				entidad.setRuc(entidadBean.getRUC());
				entidad.setDireccion(entidadBean.getDireccion());
				
				if(entidadBean.getUbigeo()!=null && entidadBean.getUbigeo().length()==6){
					String ubigeo = entidadBean.getUbigeo();
					
					entidad.setDepartamento(ubigeo.substring(0,2));
					entidad.setProvincia(ubigeo.substring(2,4));
					entidad.setDistrito(ubigeo.substring(4,6));
				}
//				entidad.setUbigeo(entidadBean.getUbigeo());
				
				
				entidad.setNomRepresentante(entidadBean.getRepresentanteBean().getNombreCompleto());
				entidad.setNomCargo(entidadBean.getRepresentanteBean().getNombreCargo());
				long idEntidad = registrarEntidad(entidad);
				
//				this.actualizarRepresentante(entidad);
				
				
				/** TRAER A LA ENTIDAD REGISTRADA DESDE LA BD **/
				entidad =  entidadService.buscarPorId(idEntidad);
				
				
				
			}else{
				System.out.println("es igual a null");
				
				
  			}

			
		}
		
		return entidad;
	}

	private long registrarEntidad(Entidad entidad) throws ServicioException {
		
		long idEntidad = entidadService.insertar(entidad);
		
		return idEntidad;
	}
	
	
	
	public int registrarUsuario(Usuario usuario)throws ServicioException{
		
		if(usuario.getValidaExiste()==0){
			long idUsuario =	usuarioService.insertar(usuario);
			usuario.setIdUsuario(idUsuario);
		}
		
		
		
		PerfilREST perfilREST = new PerfilREST();
		List<PerfilBean> lstPerfilBean = (List<PerfilBean>) perfilREST.getPerfiles(String.valueOf(usuario.getIdUsuario()), UtilProperties.getKey("aplicacion.id"));
	
		/**  **/
		PerfilUsuario prmPerfil = new PerfilUsuario();		
		prmPerfil.setUsuario(usuario);
		List<PerfilUsuario> lstPerfilUsuario =  (List<PerfilUsuario>) perfilUsuarioService.listar(prmPerfil);
		
		for (PerfilBean perfilBean : lstPerfilBean) {
			int x = 0;
			for (PerfilUsuario perfilUsuario : lstPerfilUsuario) {
				
				if(perfilUsuario.getPerfil().getIdPerfil() ==perfilBean.getPK_eIdPerfil()){
					x = x+1;
				}
				
			}
			
			if(x==0){
				PerfilUsuario prmPerfilUsuario = new PerfilUsuario();
				prmPerfilUsuario.setUsuario(new Usuario());
				prmPerfilUsuario.getUsuario().setIdUsuario(usuario.getIdUsuario());
				prmPerfilUsuario.setPerfil(new Perfil());
				prmPerfilUsuario.getPerfil().setIdPerfil(perfilBean.getPK_eIdPerfil());
				perfilUsuarioService.insertar(prmPerfilUsuario);
			}
			
			
		}
		
		List<PerfilUsuario> lstPerfilUsuarioEliminado = new ArrayList<PerfilUsuario>();
		for (PerfilUsuario perfilUsuario : lstPerfilUsuario) {
			
			int x = 0;
			for (PerfilBean perfilBean : lstPerfilBean) {
				
				if(perfilUsuario.getPerfil().getIdPerfil() ==perfilBean.getPK_eIdPerfil()){
					x = x+1;
				}
				
			
				
			}
			
			if(x==0){
				perfilUsuarioService.eliminar(perfilUsuario);
			}
			
			
		}
		
		
		
		return 0;
		
	}
	
	@ResponseBody
	@RequestMapping(value ="/buscarUsuarioPorIdAplicacion", method = RequestMethod.POST)
	public  RespuestaHttp buscarUsuarioPorIdAplicacion(@RequestBody Parametro prmData,HttpServletRequest request,HttpServletResponse response)throws Exception {
		RespuestaHttp respuesta = new RespuestaHttp();
		try{
			SSOREST SSOREST = new SSOREST();
			String idAplicacion = ResourceUtil.getKey("aplicacion.id");
			SSOObjectREST  usuarioSSO= SSOREST.buscarUsuarioXAplicacion(prmData.getPrm2(),Long.valueOf(idAplicacion));
			
		

			if(usuarioSSO.getData().getIdUsuario()!=null && usuarioSSO.getData().getIdUsuario()!=0){
				respuesta.setValido(true);
				respuesta.setData(usuarioSSO);
				respuesta.setMensaje("El usuario ya se encuentra registrado.");
			}else{
					
			}
			
			
		
		}catch (Exception e) {
			logger.error(this.logBase+ " : buscarUsuarioPorIdAplicacion" + e.getMessage());
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al obtener la información del usuario");
		}
		return respuesta;
	}
	
	
	@ResponseBody
	@RequestMapping(value ="/validarCorreoElectronicoSSO", method = RequestMethod.POST)
	public  RespuestaHttp validarCorreoElectronicoSSO(@RequestBody Parametro prmData,HttpServletRequest request,HttpServletResponse response)throws Exception {
		RespuestaHttp respuesta = new RespuestaHttp();
		try{
			SSOREST SSOREST = new SSOREST();

			UsuarioSSO usuario= new UsuarioSSO();
			usuario.setCorreoElectronico(prmData.getPrm2());
			SSOObjectREST  usuarioSSO = SSOREST.validaCorreoElectronicoExitenteSSO(usuario);
			
		

			if(usuarioSSO.getData().getIdUsuario()!=null && usuarioSSO.getData().getIdUsuario()!=0){
				respuesta.setValido(true);
				respuesta.setData(usuarioSSO);
				respuesta.setMensaje("El correo ingresado ya se encuentra registrado.");
			}else{
					
			}
			
			
		
		}catch (Exception e) {
			logger.error(this.logBase+ " : validarCorreoElectronicoSSO" + e.getMessage());
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al obtener la información del usuario");
		}
		return respuesta;
	}
	
	@ResponseBody
	@RequestMapping(value ="/obtener-perfiles", method = RequestMethod.POST)
	public  RespuestaHttp obtenerPerfiles(@RequestBody Parametro prmData,HttpServletRequest request)throws Exception {
		RespuestaHttp respuesta = new RespuestaHttp();
		try{
			
			UtilEncrypt.init(UtilProperties.getKey("encrypt.key"));
			
			String PK_eIdUsuarioDecrypt = UtilEncrypt.decrypt(prmData.getPrm1());
			
			PerfilREST perfilREST = new PerfilREST();
			List<PerfilBean> lstPerfilBean = (List<PerfilBean>) perfilREST.getPerfiles(String.valueOf(PK_eIdUsuarioDecrypt), UtilProperties.getKey("aplicacion.id"));
								
		
		
				respuesta.setValido(true);
				respuesta.setData(lstPerfilBean);
			
		
		}catch (Exception e) {
			logger.error(this.logBase+ " : obtenerPerfiles" + e.getMessage());
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al obtener la información del usuario");
		}
		return respuesta;
	}
	
	
	
	
	/**********************************************/
	
	
    private void initJob(){
        try {

          Scheduler scheduler = new StdSchedulerFactory().getScheduler();

          JobDetail jobDetail = JobBuilder
                  .newJob(MailJob.class)
                  .withIdentity("MailJob")
                  .build();
          String frecuencia=UtilProperties.getKey("Job_Frecuencia");//"0 23 19 ? * MON-FRI *";
          //System.out.println(" frecuencia "+frecuencia);
          
          Trigger trigger = TriggerBuilder
                  .newTrigger()
                  .withIdentity("MailJob")
                  .withSchedule(CronScheduleBuilder.cronSchedule(frecuencia))
                  .build();
  
          if(!scheduler.checkExists(jobDetail.getKey())){            
                scheduler.getListenerManager().addJobListener(new MailJobListener());
                scheduler.start();
                scheduler.scheduleJob(jobDetail, trigger);
          }
        
        } catch (Exception e) {
              e.printStackTrace();
        }
  }   
    
    
  private void initInitializingService(){
        
        if (initializingService!=null) {
              try {
                    initializingService.initializeAndStartScheduler();
              } catch (SchedulerException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
              }
        }
  }
	
}
