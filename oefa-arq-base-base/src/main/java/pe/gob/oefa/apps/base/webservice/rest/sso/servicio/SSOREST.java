package pe.gob.oefa.apps.base.webservice.rest.sso.servicio;

import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import pe.gob.oefa.apps.base.util.properties.UtilProperties;
import pe.gob.oefa.apps.base.util.rest.UtilREST;
import pe.gob.oefa.apps.base.webservice.rest.base.servicio.BaseREST;
import pe.gob.oefa.apps.base.webservice.rest.sso.bean.SSOObjectREST;
import pe.gob.oefa.apps.base.webservice.rest.sso.bean.UsuarioSSO;

import com.google.gson.Gson;

@Service
public class SSOREST extends BaseREST {
	
	public SSOREST(){
	}
	
	private String logBase = "oefa-arq-base-base: SSOREST";
	
	private final Logger logger = Logger.getLogger(this.getClass());
		
	
	public SSOObjectREST buscar(long numero) throws Exception{
		
		super.URL=UtilProperties.getKey("URI_BuscarSSOREST");
		SSOObjectREST objectREST=new SSOObjectREST();
		
		try {
			String content = UtilREST.getREST(super.URL+numero);
			 
			objectREST = new Gson().fromJson(content, SSOObjectREST.class);

		} catch (Exception e) {
			logger.error(this.logBase+ " : buscar" + e.getMessage());
			throw new  Exception(e);
		}
		return objectREST;
		
	}
	public SSOObjectREST buscarUsuarioXAplicacion(String  codUsuario, long idAplicacion) throws Exception{
		
		super.URL=UtilProperties.getKey("URI_buscarUsuarioXAplicacionSSOREST");
		SSOObjectREST objectREST=new SSOObjectREST();
		
		try {
			String content = UtilREST.getREST(super.URL+codUsuario+"/"+idAplicacion);
			 
			objectREST = new Gson().fromJson(content, SSOObjectREST.class);

		} catch (Exception e) {
			logger.error(this.logBase+ " : buscarUsuarioXAplicacion" + e.getMessage());
			throw new  Exception(e);
		}
		return objectREST;
		
	}
	
	public SSOObjectREST validaCorreoElectronicoExitenteSSO(UsuarioSSO usuario) throws Exception, UnsupportedEncodingException {
		super.URL=UtilProperties.getKey("URI_ValidarCorreoElectronicoSSO");
		SSOObjectREST objectREST=new SSOObjectREST();
		
		String json="{"
				+"\"correoElectronico\": \""+usuario.getCorreoElectronico()+"\""
				+"}";
		System.out.println("json "+json);
		
		try {
			String response=UtilREST.postREST(super.URL, json);
			objectREST= new Gson().fromJson(response, SSOObjectREST.class);
		}
		
		catch (Exception e) {
			logger.error(this.logBase+ " : validaCorreoElectronicoExitenteSSO" + e.getMessage());
			throw new Exception(e);
		}
		return objectREST;
	}
	
	
	public SSOObjectREST modificar(UsuarioSSO usuario) throws Exception, UnsupportedEncodingException {
		super.URL=UtilProperties.getKey("URI_ModificarSSOREST");
		SSOObjectREST objectREST=new SSOObjectREST();
		
		String json="{"
				+"\"idUsuario\":"+usuario.getIdUsuario()+","
				+"\"correoElectronico\": \""+usuario.getCorreoElectronico()+"\""
				+"}";
		System.out.println("json "+json);
		
		try {
			String response=UtilREST.postREST(super.URL, json);
			objectREST= new Gson().fromJson(response, SSOObjectREST.class);
		}
		
		catch (Exception e) {
			logger.error(this.logBase+ " : modificar" + e.getMessage());
			throw new Exception(e);
		}
		return objectREST;
	}
	
}
