package pe.gob.oefa.apps.base.webservice.rest.persona.servicio;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.text.WordUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;





import pe.gob.oefa.apps.base.util.UtilStringParam;
//import pe.gob.oefa.apps.base.util.properties.UtilProperties;
  import pe.gob.oefa.apps.base.util.properties.UtilProperties;
import pe.gob.oefa.apps.base.util.rest.UtilREST;
import pe.gob.oefa.apps.base.webservice.rest.base.servicio.BaseREST;
import pe.gob.oefa.apps.base.webservice.rest.entidad.bean.EntidadBean;
import pe.gob.oefa.apps.base.webservice.rest.persona.bean.PersonaBean;

@Service
public class PersonaREST extends BaseREST {
	
	public PersonaREST(){
//		this.init();
	}
	
	private String logBase = "oefa-arq-base-base: PersonaREST";
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private void init(){
		super.URL=UtilProperties.getKey("URI_PersonaREST");
		super.URL_2=UtilProperties.getKey("URI_PersonaWSAutorizado");
		System.out.println("super.URL "+super.URL);
		System.out.println("super.URL_2 "+super.URL_2);
		super.authString= UtilProperties.getKey("Usuario_REST") + ":" + UtilProperties.getKey("Clave_REST");
		System.out.println(authString);
	}
	
//	public PersonaBean getPersona(String dni){
//		
//		init();
//		
//		String content;
//		PersonaBean personaBean= new PersonaBean(); 
//		try {
//			String idAplicacion=UtilProperties.getKey("aplicacion.id");
//			System.out.println("idAplicacion "+idAplicacion);
//			content = UtilREST.getREST(super.URL+"nroDNIConsulta="+dni+"&IdAplicacion="+idAplicacion,super.authString);
//			Gson gson = new Gson();
//			System.out.println("content "+content);
//			personaBean = gson.fromJson(content, PersonaBean.class);	
//			
//			
//			if(personaBean==null || (personaBean!=null && personaBean.getCodigo().equals("0") ) ){
//				personaBean = getPersonaWSAutorizada(dni);
//			}
//			
//			if((personaBean!=null && personaBean.getCodigo().equals("0") ) ){
//				
//				personaBean=null;
//				
//			}
//			
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return personaBean;
//	}
	
	
	public PersonaBean getPersona(String dni){
		
		init();
		
		String content;
		PersonaBean personaBean= new PersonaBean(); 
		try {
			String idAplicacion=UtilProperties.getKey("aplicacion.id");
			String dniAutorizado = UtilProperties.getKey("DNI_Autorizado");
			System.out.println("idAplicacion "+idAplicacion);
			content = UtilREST.getREST(super.URL_2+"nroDNIAutorizado="+dniAutorizado+"&nroDNIConsulta="+dni+"&IdAplicacion="+idAplicacion+"&IdTipoDocumento=2",super.authString);
			Gson gson = new Gson();
			System.out.println("content "+content);
			personaBean = gson.fromJson(content, PersonaBean.class);	
			
//			if(personaBean!=null && !personaBean.getCodigo().equals("0")){
//				personaBean.setNombreCompleto(UtilStringParam.capitalize(personaBean.getNombreCompleto()));
//				personaBean.setNombres(UtilStringParam.capitalize(personaBean.getNombres()));
//				
//				personaBean.setApellidoPaterno(UtilStringParam.capitalize(personaBean.getApellidoPaterno()));
//				personaBean.setApellidoMaterno(UtilStringParam.capitalize(personaBean.getApellidoMaterno()));
//				
//				personaBean.setDireccion(UtilStringParam.mayusculaPrimeraLetra(personaBean.getDireccion()));
//			}
			
			
		} catch (IOException e) {
			logger.error(this.logBase+ " : getPersona" + e.getMessage());
			e.printStackTrace();
		}
		
		

		return personaBean;
	}
	
	public boolean agregar(PersonaBean personaBean){
		this.init();
		super.URL=UtilProperties.getKey("URI_Agregar_PersonaREST");
		System.out.println("URL "+URL);
		String rest="{"
				+ "\"ApellidoMaterno\": \""+personaBean.getApellidoMaterno()+"\","
				+"\"ApellidoPaterno\": \""+personaBean.getApellidoPaterno() +"\","
				+"\"Nombres\": \""+personaBean.getNombres()+"\","
				+"\"TipoDocumento\": \""+personaBean.getTipoDocumento()+"\","
				+"\"NroDocumento\": \""+personaBean.getNroDocumento()+"\","
				+"\"Ubigeo\": \""+personaBean.getUbigeo()+"\","
				+"\"Direccion\": \""+personaBean.getDireccion()+"\""
				+"}";

		System.out.println("rest "+rest);
		
		try {
			String ret=UtilREST.postREST(super.URL, rest, super.authString);
			System.out.println("ret: "+ret);
			
		} catch (IOException e) {
			logger.error(this.logBase+ " : agregar" + e.getMessage());
			e.printStackTrace();
			return false;
		}
			return true;
	}
	
}
