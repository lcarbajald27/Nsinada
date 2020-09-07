package pe.gob.oefa.apps.base.webservice.rest.seguridad.usuario.servicio;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

import pe.gob.oefa.apps.base.util.properties.UtilProperties;
import pe.gob.oefa.apps.base.util.rest.UtilREST;
import pe.gob.oefa.apps.base.webservice.rest.base.servicio.BaseREST;
import pe.gob.oefa.apps.base.webservice.rest.entidad.bean.EntidadBean;
import pe.gob.oefa.apps.base.webservice.rest.entidad.servicio.EntidadREST;
import pe.gob.oefa.apps.base.webservice.rest.persona.bean.PersonaBean;
import pe.gob.oefa.apps.base.webservice.rest.persona.servicio.PersonaREST;
import pe.gob.oefa.apps.base.webservice.rest.seguridad.usuario.bean.UsuarioBean;
import pe.gob.oefa.apps.base.webservice.rest.seguridad.usuario.bean.UsuarioExternoBean;
import pe.gob.oefa.apps.base.webservice.rest.sso.bean.SSOObjectREST;
import pe.gob.oefa.apps.base.webservice.rest.sso.servicio.SSOREST;

public class UsuarioREST extends BaseREST {
	
	public UsuarioREST(){
		this.init();
	}
	
private String logBase = "oefa-arq-base-base: UsuarioREST";
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private void init(){
		super.URL=UtilProperties.getKey("URI_UsuarioREST");
		System.out.println("super.URL "+super.URL);
		super.authString= UtilProperties.getKey("Usuario_REST") + ":" + UtilProperties.getKey("Clave_REST");
	}
	
	public List<UsuarioBean> getUsuarios(String filtro, String numPag, String cantRegxPag) {
		String content;
		List<UsuarioBean> listUsuarioBean = new ArrayList<UsuarioBean>(); 
		try {
			content = UtilREST.getREST(super.URL+"Filtro="+filtro+"&NumPag="+numPag+"&CantRegxPag="+cantRegxPag,super.authString);
			Gson gson = new Gson();						
			UsuarioBean[] arregloUsuarioBean = gson.fromJson(content, UsuarioBean[].class);	
			
			for (int i = 0; i < arregloUsuarioBean.length; i++) {
				listUsuarioBean.add(arregloUsuarioBean[i]);
			}
			
		} catch (IOException e) {
			logger.error(this.logBase+ " : getUsuarios" + e.getMessage());
			e.printStackTrace();
		}
		
		return listUsuarioBean;
	}
	
	public boolean agregar(UsuarioExternoBean usuarioExternoBean){
		boolean res = false;
		if (usuarioExternoBean.getTipo()==1) {//Persona
			PersonaREST personaREST = new PersonaREST();
			PersonaBean personaBean = personaREST.getPersona(usuarioExternoBean.getNroDoc());
			if (personaBean!=null) {
				usuarioExternoBean.setNombreUsuario(personaBean.getNombreCompleto());
				usuarioExternoBean.setIdUsuario(Integer.valueOf(personaBean.getCodigo()));
			}
		}else if (usuarioExternoBean.getTipo()==2) {//Entidad
			EntidadREST entidadREST = new EntidadREST();
			EntidadBean entidadBean = entidadREST.getEntidad(usuarioExternoBean.getNroDoc());
			if (entidadBean!=null) {
				usuarioExternoBean.setNombreUsuario(entidadBean.getNombreCompleto());
				usuarioExternoBean.setIdUsuario(Integer.valueOf(entidadBean.getCodigo()));
			}
		}
		
		String rest="{\"IdUsuario\": "+usuarioExternoBean.getIdUsuario()+","
				+"\"CodUsuario\": \""+usuarioExternoBean.getNroDoc() +"\","
				+"\"NombreUsuario\": \""+usuarioExternoBean.getNombreUsuario()+"\","
				+"\"CorreoElectronico\": \""+usuarioExternoBean.getCorreoElectronico()+"\","
				+"\"Clave\": \""+usuarioExternoBean.getClave()+"\","
				+"\"IdSesion\": "+0+","
				+"\"Observacion\": \""+usuarioExternoBean.getObservacion()+"\","
				+"}";
		try {
			String ret=UtilREST.postREST(super.URL, rest, super.authString);
			System.out.println("ret: "+ret);
			
			if (usuarioExternoBean.getIdPerfil()!=0) {
				this.asignarPerfil(usuarioExternoBean.getIdUsuario(), usuarioExternoBean.getIdPerfil());
			}
			Gson gson = new Gson();
			UsuarioExternoBean usuario = gson.fromJson(ret, UsuarioExternoBean.class);	
			
			if(usuario.getIdUsuario()>0){
				res = true;
			}
			
		} catch (IOException e) {
			logger.error(this.logBase+ " : agregar" + e.getMessage());
			e.printStackTrace();
			return false;
		}
		return res;
	}
	

//	public SSOObjectREST agregarUsuarioGenerico(UsuarioExternoBean usuarioExternoBean) throws Exception{
//		/*************************************/
//		SSOREST SSOREST = new SSOREST();
//		/*************************************/
//		
//		SSOObjectREST res = null;
//		if (usuarioExternoBean.getTipo()==1) {//Persona
//			PersonaREST personaREST = new PersonaREST();
//			PersonaBean personaBean = personaREST.getPersona(usuarioExternoBean.getNroDoc());
//			if (personaBean!=null) {
//				usuarioExternoBean.setNombreUsuario(personaBean.getNombreCompleto());
//				usuarioExternoBean.setIdUsuario(Integer.valueOf(personaBean.getCodigo()));
//			}
//		}else if (usuarioExternoBean.getTipo()==2) {//Entidad
//			EntidadREST entidadREST = new EntidadREST();
//			EntidadBean entidadBean = entidadREST.getEntidad(usuarioExternoBean.getNroDoc());
//			if (entidadBean!=null) {
//				usuarioExternoBean.setNombreUsuario(entidadBean.getNombreCompleto());
//				usuarioExternoBean.setIdUsuario(Integer.valueOf(entidadBean.getCodigo()));
//			}
//		}
//		
//		SSOObjectREST  SSOObjectREST = null;
//
//			SSOObjectREST = SSOREST.buscar(usuarioExternoBean.getIdUsuario());
//			
//		
//		
//		String rest="{\"IdUsuario\": "+usuarioExternoBean.getIdUsuario()+","
//				+"\"CodUsuario\": \""+usuarioExternoBean.getNroDoc() +"\","
//				+"\"NombreUsuario\": \""+usuarioExternoBean.getNombreUsuario()+"\","
//				+"\"CorreoElectronico\": \""+usuarioExternoBean.getCorreoElectronico()+"\","
//				+"\"Clave\": \""+usuarioExternoBean.getClave()+"\","
//				+"\"IdSesion\": "+0+","
//				+"\"Observacion\": \""+usuarioExternoBean.getObservacion()+"\","
//				+"}";
//		try {
//			
//			if(SSOObjectREST.getData()==null || SSOObjectREST.getData().getIdUsuario()==null || SSOObjectREST.getData().getIdUsuario()==0){
//				String ret=UtilREST.postREST(super.URL, rest, super.authString);
//				System.out.println("ret: "+ret);
//			}
//			
//			
//			if (usuarioExternoBean.getIdPerfil()!=0) {
//				this.asignarPerfil(usuarioExternoBean.getIdUsuario(), usuarioExternoBean.getIdPerfil());
//			}
//			
//			SSOObjectREST = SSOREST.buscar(usuarioExternoBean.getIdUsuario());
//			
////			Gson gson = new Gson();
////			UsuarioExternoBean usuario = gson.fromJson(ret, UsuarioExternoBean.class);	
//			
//			if(SSOObjectREST.getData()!=null && SSOObjectREST.getData().getIdUsuario()!=null && SSOObjectREST.getData().getIdUsuario()!=0){
//				res = SSOObjectREST;
//			}
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//			return null;
//		}
//		return res;
//	}
	
	public boolean asignarPerfil(int IdUsuario,int IdPerfil ){
		try {
			super.URL=UtilProperties.getKey("URI_AsignarPerfilUsuarioREST");
			super.URL+="IdUsuario="+IdUsuario+"&IdPerfil="+IdPerfil+"&IdSesion=0";
			String ret = UtilREST.getREST(super.URL,super.authString);
			if (ret!=null) {
				return ret.equals("1")?true:false;
			}
		} catch (Exception e) {
			logger.error(this.logBase+ " : asignarPerfil" + e.getMessage());
			return false;
		}
		return false;
	}
	public UsuarioExternoBean cambiarClave(int IdUsuario,String ClaveActual, String ClaveNueva){
		try {
			super.URL=UtilProperties.getKey("URI_CambiarClaveUsuarioREST");
			String params="IdUsuario="+IdUsuario+"&ClaveActual="+ClaveActual+
					"&ClaveNueva="+ClaveNueva+"&IdSesion=0";
//			System.out.println(super.URL);
			String ret = UtilREST.postRESTWithParams(super.URL,params,super.authString);
//			System.out.println(ret);
			if (ret!=null) {
				Gson gson = new Gson();
				UsuarioExternoBean usuario = gson.fromJson(ret, UsuarioExternoBean.class);
				return usuario;
			}
		} catch (Exception e) {
			logger.error(this.logBase+ " : cambiarClave" + e.getMessage());
			return null;
		}
		return null;
	}
}
