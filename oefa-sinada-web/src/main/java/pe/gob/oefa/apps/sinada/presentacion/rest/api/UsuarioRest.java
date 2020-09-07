package pe.gob.oefa.apps.sinada.presentacion.rest.api;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import pe.gob.oefa.apps.base.presentacion.response.RespuestaHttp;
import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.base.util.EmailAttachmentSender;
import pe.gob.oefa.apps.base.util.UtilEncrypt;
import pe.gob.oefa.apps.base.util.properties.UtilProperties;
import pe.gob.oefa.apps.base.webservice.rest.generico.Acceso;
import pe.gob.oefa.apps.base.webservice.rest.generico.Parametro;
import pe.gob.oefa.apps.base.webservice.rest.seguridad.opcion.servicio.OpcionREST;
import pe.gob.oefa.apps.base.webservice.rest.seguridad.perfil.bean.PerfilBean;
import pe.gob.oefa.apps.base.webservice.rest.seguridad.perfil.servicio.PerfilREST;
import pe.gob.oefa.apps.base.webservice.rest.seguridad.usuario.bean.UsuarioExternoBean;
import pe.gob.oefa.apps.base.webservice.rest.seguridad.usuario.servicio.UsuarioREST;
import pe.gob.oefa.apps.base.webservice.rest.sso.bean.SSOObjectREST;
import pe.gob.oefa.apps.base.webservice.rest.sso.servicio.SSOREST;
import pe.gob.oefa.apps.sinada.bean.general.ContactoPersona;
import pe.gob.oefa.apps.sinada.bean.general.Entidad;
import pe.gob.oefa.apps.sinada.bean.general.Persona;
import pe.gob.oefa.apps.sinada.bean.maestros.FormatoCorreo;
import pe.gob.oefa.apps.sinada.bean.proceso.Bandeja;
import pe.gob.oefa.apps.sinada.bean.proceso.Notificaciones;
import pe.gob.oefa.apps.sinada.bean.proceso.PersonaOefa;
import pe.gob.oefa.apps.sinada.bean.seguridad.Perfil;
import pe.gob.oefa.apps.sinada.bean.seguridad.PerfilUsuario;
import pe.gob.oefa.apps.sinada.bean.seguridad.Usuario;
import pe.gob.oefa.apps.sinada.bean.sirefa.Efa;
import pe.gob.oefa.apps.sinada.bean.sirin.Normas;
import pe.gob.oefa.apps.sinada.presentacion.util.GeneradorCodigo;
import pe.gob.oefa.apps.sinada.presentacion.util.ResourceUtil;
import pe.gob.oefa.apps.sinada.servicio.inf.general.ContactoPersonaService;
import pe.gob.oefa.apps.sinada.servicio.inf.general.EntidadService;
import pe.gob.oefa.apps.sinada.servicio.inf.general.PersonaService;
import pe.gob.oefa.apps.sinada.servicio.inf.maestros.FormatoCorreoService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.BandejaService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.NotificacionesService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.PersonaOefaService;
import pe.gob.oefa.apps.sinada.servicio.inf.seguridad.UsuarioService;
import pe.gob.oefa.apps.sinada.servicio.inf.sirefa.EfaService;
import pe.gob.oefa.apps.sinada.servicio.inf.sirin.NormasService;

@Controller
@RequestMapping(value="/rest/api/usuario")
public class UsuarioRest
{
	@Autowired
	private EntidadService entidadService;
	
	@Autowired
	private PersonaService personaService;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	BandejaService bandejaService;
	
	@Autowired
	PersonaOefaService personaOefaService;
	
	@Autowired
	EfaService efaService;
	
	@Autowired
	NotificacionesService NotificacionesService;
	
	@Autowired
	ContactoPersonaService contactoPersonaService;
	
	@Autowired
	FormatoCorreoService formatoCorreoService;
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value  = "/validar-datos-usuario-persona",method = RequestMethod.GET)
	public  RespuestaHttp validarDatosUsuarioPersona(@RequestParam(value = "prmCriterio", required = false) String prmCriterio)
			throws Exception{
		
		
		
		RespuestaHttp respuesta = new RespuestaHttp();
		try{
			/************** ***************/
			UtilEncrypt.init(UtilProperties.getKey("encrypt.key"));
			Parametro prmParametro = new ObjectMapper().readValue(prmCriterio,Parametro.class);
			OpcionREST opcionREST = new OpcionREST();
			//System.out.println("ref " + prmParametro);
			String PK_eIdUsuarioDecrypt = UtilEncrypt.decrypt(prmParametro.getPrm1());
			
			/******************************/

			
			long idUsuario = Long.valueOf(PK_eIdUsuarioDecrypt); 
			
			
			
			Usuario prmUsuario = new Usuario();
			
			prmUsuario = usuarioService.buscarPorId(idUsuario);
			Usuario prmData = new Usuario();
			Bandeja prmBandeja = new Bandeja();
			prmData.setTipoPersona(prmUsuario.getTipoPersona());
			if(prmUsuario.getTipoPersona()==1){
				prmData.setPersona(personaService.buscarPorId(prmUsuario.getIdPersona()));
				
				if(prmData.getPersona()!=null){
					prmData.setIdPersona(prmData.getPersona().getIdPersona());
				}
				
			}else if(prmUsuario.getTipoPersona()==2){
				prmData.setEntidad(entidadService.buscarPorId(prmUsuario.getIdPersona()));
				
				if(prmData.getEntidad()!=null){
					prmData.setIdPersona(prmData.getEntidad().getIdEntidad());
				}
				
			}
			prmBandeja.setIdResponsable(idUsuario);
			

			
			
			if(prmUsuario.getTipoPersona()!=0){
				prmBandeja.setTipoResponsable(prmUsuario.getTipoPersona());
				
				
			/******************************  **********************************/
				
				prmBandeja = bandejaService.validarBandeja(prmBandeja);
				prmData.setBandeja(prmBandeja);
				
				
				if(prmUsuario.getTipoPersona()==1){
					PersonaOefa prmPersonaOEfa = new PersonaOefa();
					prmPersonaOEfa.getPersona().setIdPersona(prmUsuario.getIdPersona());
					prmPersonaOEfa = 	personaOefaService.buscarPorIdPersona(prmPersonaOEfa);
					if(prmPersonaOEfa != null){
						
						prmData.setTipoEntidadCompetente(3);
						prmData.setIdPersonaOefa(prmPersonaOEfa.getIdPersonaOefa());
						Bandeja prmBandejaOefa = new Bandeja();
						prmBandejaOefa.setTipoResponsable(3);
						prmBandejaOefa.setDireccion(prmPersonaOEfa.getDireccion().getCodigoRegistro());
						prmBandejaOefa.setSubDireccion(prmPersonaOEfa.getSubDireccion().getCodigoRegistro());
//						prmBandejaOefa.setCoordinacion(0);
						prmData.setBandejaEntidad(bandejaService.validarBandejaOefa(prmBandejaOefa));
						
					}
					
				}
				
				if(prmUsuario.getTipoPersona()==2){
					
					Efa prmEfa = new Efa();
					prmEfa.setIdEntidad(prmUsuario.getIdPersona());
					prmEfa = efaService.buscarEfaPorIdEntidad(prmEfa);
					if(prmEfa!=null){
						prmData.setTipoEntidadCompetente(4);
						prmData.setIdEfa(prmEfa.getIdEfa());
						Bandeja prmBandejaEfa = new Bandeja();
						prmBandejaEfa.setTipoResponsable(4);
						prmBandejaEfa.setIdEFa(prmEfa.getIdEfa());
						prmData.setBandejaEntidad(bandejaService.validarBandejaEfa(prmBandejaEfa));
						
					}
					
					
				}
				
				
//				Notificaciones prmNotificacion = new Notificaciones();
//				prmNotificacion.setIdBandeja(prmData.getBandeja().getIdBandeja());
//				prmNotificacion.setIdBandejaEntidad(prmData.getBandejaEntidad().getIdBandeja());
//				List<Notificaciones> data = null;
//				 data = (List<Notificaciones>) NotificacionesService.listar(prmNotificacion);
//				 
//				 if(data!=null && data.size()>0){
//					 prmData.setLstNotificaciones(data);
//				 }
				
				
				/*******************************************************/
				
			}
			
			
			
			
		
			
			if(prmData!=null){
			
				respuesta.setValido(true);
				respuesta.setData(prmData);
			}else{
				respuesta.setMensaje("Hubo un error al obtener el usuario");
			}
			
			
			
			
		} catch (Exception e) {
			respuesta.setMensaje("Hubo un error al obtener el usuario");
			e.printStackTrace();
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
			Usuario prmUsuario = new ObjectMapper().readValue(prmCriterio, Usuario.class);
			List<Usuario> usuario =  (List<Usuario>) usuarioService.listar(prmUsuario);
			_RespuestaHttp.setValido(true);
			_RespuestaHttp.setData(usuario);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			_RespuestaHttp.setValido(false);
			_RespuestaHttp.setMensaje("Hubo un error al procesar la información");
		}
		return _RespuestaHttp;
	}
	
	public RespuestaHttp registrarDatosUsuarioGenerico(Usuario prmUsuario,int tipoRegistro,UsuarioExternoBean usuarioExternoBean,List<String> lstCorreos) throws AddressException, MessagingException, ServicioException{
		RespuestaHttp respuesta = new RespuestaHttp();
		
		
		
		for (ContactoPersona contactoPersona : prmUsuario.getLstContactoPersona()) {
			
			contactoPersona.setTipoPersona(prmUsuario.getTipoPersona());
			
			if(prmUsuario.getTipoPersona()==1){
				contactoPersona.setIdPersona(prmUsuario.getPersona().getIdPersona());
	
			}else{
				contactoPersona.setIdPersona(prmUsuario.getEntidad().getIdEntidad());
			}
			
			contactoPersonaService.insertar(contactoPersona);
		}
		
		/***************************** Correo ******************************************/
		FormatoCorreo prmFormatoCorreo = new FormatoCorreo();
		long idFormatoCorreo =1 ; 
	
		prmFormatoCorreo =formatoCorreoService.buscarPorId(idFormatoCorreo);
		
		String texto1 = "";
		String texto2 = "";
		String texto3 = "";
		String texto4 = "";
		String texto5 = "";
		
		String mensaje = prmFormatoCorreo.getMensaje();
		if(prmUsuario.getTipoPersona()==1){
			texto1 ="Sr(a): " + prmUsuario.getPersona().getPrimerNombre() + " " + prmUsuario.getPersona().getSegundoNombre()+" " + prmUsuario.getPersona().getApellidoPaterno() + " "+prmUsuario.getPersona().getApellidoMaterno();
		}else{
			texto1 ="Sres. " +prmUsuario.getEntidad().getRazonSocial();
		}
		
		if(tipoRegistro==1){
			texto2 ="Se ha registrado con éxito en el Servicio de Información Nacional y Denuncias Ambientales - Sinada ";
			texto3 = "Credenciales :";
			texto4 = "Usuario 	 :" + usuarioExternoBean.getNroDoc();
			texto5 = "Nota: ingresar con los datos de su usuario, de haber olvidado su contraseña dirigirse a la opción <a href='http://200.37.65.227/Externos2/RestaurarClave'><b>¿Olvidaste tu clave?</b></a>";
		}
		if(tipoRegistro==2){
			texto2 ="Se ha registrado con éxito en el Servicio de Información Nacional y Denuncias Ambientales - Sinada ";
			texto3 = "Usuario 	 :" + usuarioExternoBean.getNroDoc();
			texto4 = "Contraseña :" + usuarioExternoBean.getClave();
			
		}
		
		mensaje = mensaje.replace("TextoCorreo1", texto1);
		mensaje = mensaje.replace("TextoCorreo2", texto2);
		mensaje = mensaje.replace("TextoCorreo3", texto3);
		mensaje = mensaje.replace("TextoCorreo4", texto4);
		mensaje = mensaje.replace("TextoCorreo5", texto5);
		mensaje = mensaje.replace("TextoCorreo6", "");
		mensaje = mensaje.replace("TextoCorreo7", "");
		
		for (String correo : lstCorreos) {
			EmailAttachmentSender.sendEmailWithAttachments(correo, "Registro Sinada", mensaje, null);
		}
		
		
		respuesta.setValido(true);
		respuesta.setMensaje("Se ha registado el usuario satisfactoriamente");
		respuesta.setData(prmUsuario.getIdUsuario());
		return respuesta;
		
	}
	
	@ResponseBody
	@RequestMapping(value = "/registrar",method = RequestMethod.POST)
	public RespuestaHttp registrar(@RequestBody Usuario prmUsuario,HttpServletRequest request)throws Exception
	{
		RespuestaHttp respuesta = new RespuestaHttp();
		
		try
		{
		
		
			
			String correo = "";
			List<String> lstCorreos = new ArrayList<String>();
			/*****************************************************************/
			for (ContactoPersona contactoPersona : prmUsuario.getLstContactoPersona()) {
				if(contactoPersona.getTipoContacto()==2){
					
					lstCorreos.add(contactoPersona.getValor());
					
				}
				
			}
			
			correo = lstCorreos.get(0);
			/*****************************************************************/
			String pwd = GeneradorCodigo.getPassword(8);
			UsuarioREST usuarioREST= new UsuarioREST();
			
			UsuarioExternoBean usuarioExternoBean= new UsuarioExternoBean();
			usuarioExternoBean.setTipo(prmUsuario.getTipoPersona());
			long idUsuario = 0;
			if(prmUsuario.getTipoPersona()==1){
				idUsuario = prmUsuario.getPersona().getIdPersona();

				usuarioExternoBean.setNroDoc(prmUsuario.getPersona().getDocumento());
			}else{
				idUsuario = prmUsuario.getEntidad().getIdEntidad();
				usuarioExternoBean.setNroDoc(prmUsuario.getEntidad().getRuc());


			}
		
			usuarioExternoBean.setClave(pwd);
			usuarioExternoBean.setCorreoElectronico(correo);
			String idPerfil = ResourceUtil.getKey("IdPerfilSSO_Denunciante");
			usuarioExternoBean.setIdPerfil(Integer.valueOf(idPerfil));
			
			
	/*******************************************************************************************/
	/*****																				   *****/	
	/*******************************************************************************************/
						
			SSOREST SSOREST = new SSOREST();

			try {
				SSOObjectREST  usuarioExistente = SSOREST.buscar(idUsuario);
				
					if(usuarioExistente.getData()!=null && usuarioExistente.getData().getIdUsuario()!=null && usuarioExistente.getData().getIdUsuario()!=0){
						UsuarioREST uRest= new UsuarioREST();
						usuarioExternoBean.setNroDoc(usuarioExistente.getData().getCodUsuario());
						if(uRest.asignarPerfil(Integer.valueOf(String.valueOf(idUsuario)),Integer.valueOf(idPerfil))){
							respuesta =	registrarDatosUsuarioGenerico(prmUsuario,1,usuarioExternoBean,lstCorreos);
							System.out.println("Exito al asignar perfil");
						}else{
							System.out.println("Error al asignar perfil");
							respuesta.setValido(false);
							respuesta.setMensaje("Error al registrar al usuario, intentelo mas tarde.");
						
							return respuesta;
							
						}
						
						
					}else{
						
						/***************************************************************************/
						

						if (usuarioREST.agregar(usuarioExternoBean)) {
							
							respuesta =		registrarDatosUsuarioGenerico(prmUsuario,2,usuarioExternoBean,lstCorreos);
							
						} else {
							
							respuesta.setValido(false);
							respuesta.setMensaje("Error al registrar al usuario, intentelo mas tarde.");
							respuesta.setData(prmUsuario.getIdUsuario());
							
							System.out.println("Error al insertar usuario");
						}
						
						/****************************************************************************/
						
					}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				respuesta.setMensaje("Hubo un error al registrar el usuario");
			}
			
	/*******************************************************************************************/
	/*****																				   *****/	
	/*******************************************************************************************/			
			
			
			
			
			
			

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al registrar el usuario");
		}
		return respuesta;
	}
	
	@ResponseBody
	@RequestMapping(value ="/actualizar", method = RequestMethod.POST)
	public  RespuestaHttp actualizar(@RequestBody Usuario prmUsuario,HttpServletRequest request)throws Exception {
		RespuestaHttp respuesta = new RespuestaHttp();
		try{
			int result = usuarioService.actualizar(prmUsuario);
			
			if(result>0){
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha actualizado	el usuario satisfactoriamente");
			}else {
				respuesta.setMensaje("No se pudo actualizar el usuario");
			}
		}catch (Exception e) {
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al actualizar el usuario");
		}
		return respuesta;
	}
	
	@ResponseBody
	@RequestMapping(value = "/eliminar", method = RequestMethod.POST)
	public RespuestaHttp eliminar(
			@RequestBody Usuario prmUsuario,HttpServletRequest request)
			throws Exception{
		RespuestaHttp respuesta = new RespuestaHttp();
		try{
			int result = usuarioService.eliminar(prmUsuario);
			if(result>0){
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha eliminado el usuario correctamente");
			}else {
				respuesta.setMensaje("No se pudo eliminar el usuario");
			}
		} catch (Exception e){
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al eliminar el usuario");
		}
		return respuesta;
	}
	
	@ResponseBody
	@RequestMapping(value = "/buscar-id/{id}",method = RequestMethod.GET)
	public RespuestaHttp buscarxNumeroNorma(@PathVariable("id")Long idUsuario)throws Exception 
	{
		RespuestaHttp respuesta = new  RespuestaHttp();
		try
		{
			Usuario prmUsuario = new Usuario();
			prmUsuario.setIdUsuario(idUsuario);
			prmUsuario = usuarioService.buscarPorId(prmUsuario.getIdUsuario());
			
			respuesta.setValido(true);
			respuesta.setData(prmUsuario);
		} 
		catch (Exception e)
		{
			respuesta.setMensaje("Hubo un error al obtener el usuario");
			e.printStackTrace();
		}
		return respuesta;
	}

	@RequestMapping(value  = "/login",method = RequestMethod.POST)
	public @ResponseBody RespuestaHttp doLogin(@RequestBody Usuario prmUsuario, HttpServletRequest request)
			throws Exception{
		
		RespuestaHttp respuesta = new RespuestaHttp();
		try{
		

			
			Usuario prmVwUsuario =usuarioService.loginUsuario(prmUsuario);
			
		
			
			if(prmVwUsuario!=null){
			
				respuesta.setValido(true);
				respuesta.setData(prmVwUsuario);
			}else{
				respuesta.setMensaje("Hubo un error al obtener el usuario");
			}
			
			
			
			
		} catch (Exception e) {
			respuesta.setMensaje("Hubo un error al obtener el usuario");
			e.printStackTrace();
		}
		return respuesta;
	}
	
	
	
	
	

	
}
