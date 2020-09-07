package pe.gob.oefa.apps.base.webservice.rest.entidad.servicio;

import java.io.IOException;

import org.apache.commons.lang3.text.WordUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import pe.gob.oefa.apps.base.util.UtilStringParam;
import pe.gob.oefa.apps.base.util.properties.UtilProperties;
import pe.gob.oefa.apps.base.util.rest.UtilREST;
import pe.gob.oefa.apps.base.webservice.rest.base.servicio.BaseREST;
import pe.gob.oefa.apps.base.webservice.rest.entidad.bean.EntidadBean;
import pe.gob.oefa.apps.base.webservice.rest.representante.bean.RepresentanteBean;
import pe.gob.oefa.apps.base.webservice.rest.representante.servicio.RepresentanteREST;

@Service
public class EntidadREST extends BaseREST{
	
	public EntidadREST(){
//		this.init();
//
	}
	
	private String logBase = "oefa-arq-base-base: EntidadREST";
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	
	private void init(){
		super.URL=UtilProperties.getKey("URI_EntidadREST");
		//System.out.println("super.URL "+super.URL);
		super.authString= UtilProperties.getKey("Usuario_REST") + ":" + UtilProperties.getKey("Clave_REST");
		System.out.println("super.authString "+super.authString);
	}
	
	public EntidadBean getEntidad(String ruc,boolean swRepresentante){
		
		this.init();
		
		String content;
		EntidadBean entidadBean= new EntidadBean(); 
		try {
			String dniAutorizado = UtilProperties.getKey("DNI_Autorizado");
			String idAplicacion=UtilProperties.getKey("aplicacion.id");
			content = UtilREST.getREST(super.URL+"Ruc="+ruc+"&nroDniAutorizado="+dniAutorizado+"&incluirRepLegal=true&IdAplicacion="+idAplicacion+"&IdSesion=0",super.authString);
						
			Gson gson = new Gson();						
			entidadBean = gson.fromJson(content, EntidadBean.class);
			if(entidadBean!=null){
				RepresentanteREST representanteREST = new RepresentanteREST();
				RepresentanteBean representanteBean = representanteREST.getRepresentante(entidadBean.getCodigo());
				if(representanteBean==null){
					representanteBean= new RepresentanteBean();
				}
				entidadBean.setRepresentanteBean(representanteBean);
				
//				entidadBean.setNombreCompleto(UtilStringParam.capitalize(entidadBean.getNombreCompleto()));
//				entidadBean.setDireccion(UtilStringParam.mayusculaPrimeraLetra(entidadBean.getDireccion()));
//				if(entidadBean.getRepresentanteBean()!=null){
//					entidadBean.getRepresentanteBean().setNombreCompleto(UtilStringParam.capitalize(entidadBean.getRepresentanteBean().getNombreCompleto()));	
//					entidadBean.getRepresentanteBean().setNombreCargo(UtilStringParam.capitalize(entidadBean.getRepresentanteBean().getNombreCargo()));	
//				}
				
			}
			
		} catch (IOException e) {
			logger.error(this.logBase+ " : getEntidad" + e.getMessage());
			e.printStackTrace();
		}

		return entidadBean;
	}
	
	
	public EntidadBean getEntidad(String ruc){
		
		this.init();
		
		String content;
		EntidadBean entidadBean= new EntidadBean(); 
		try {
			String dniAutorizado = UtilProperties.getKey("DNI_Autorizado");
			String idAplicacion=UtilProperties.getKey("aplicacion.id");
			content = UtilREST.getREST(super.URL+"Ruc="+ruc+"&nroDniAutorizado="+dniAutorizado+"&incluirRepLegal=true&IdAplicacion="+idAplicacion+"&IdSesion=0",super.authString);
						
			Gson gson = new Gson();						
			entidadBean = gson.fromJson(content, EntidadBean.class);
			if(entidadBean!=null){
				RepresentanteREST representanteREST = new RepresentanteREST();
				RepresentanteBean representanteBean = representanteREST.getRepresentante(entidadBean.getCodigo());
				if(representanteBean==null){
					representanteBean= new RepresentanteBean();
				}
				entidadBean.setRepresentanteBean(representanteBean);
			}
		} catch (IOException e) {
			logger.error(this.logBase+ " : getEntidad" + e.getMessage());
			e.printStackTrace();
		}

		return entidadBean;
	}
	
//public EntidadBean getEntidad(String ruc,boolean swRepresentante){		
//		init();		
//		String content;
//		EntidadBean entidadBean= new EntidadBean(); 
//		try {
//			String idAplicacion=UtilProperties.getKey("aplicacion.id");
//			content = UtilREST.getREST(super.URL+"Ruc="+ruc+"&incluirRepLegal=true&IdAplicacion="+idAplicacion,super.authString);
//						
//			Gson gson = new Gson();						
//			entidadBean = gson.fromJson(content, EntidadBean.class);
//			if (swRepresentante) {
//				RepresentanteREST representanteREST = new RepresentanteREST();
//				RepresentanteBean representanteBean = representanteREST.getRepresentante(entidadBean.getCodigo());
//				if(representanteBean==null){
//					representanteBean= new RepresentanteBean();
//				}
//				entidadBean.setRepresentanteBean(representanteBean);
//			}
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return entidadBean;
//}
public boolean agregar(EntidadBean entidadBean){
	this.init();
	super.URL=UtilProperties.getKey("URI_Agregar_EntidadREST");
	System.out.println("URL "+URL);
	String rest="{"
			+ "\"NombreCompleto\": \""+entidadBean.getNombreCompleto()+"\","
			+"\"RUC\": \""+entidadBean.getRUC() +"\","
			+"\"Ubigeo\": \""+entidadBean.getUbigeo()+"\","
			+"\"Direccion\": \""+entidadBean.getDireccion()+"\","
			+"\"RepLegal\":"
				+"["
					+"{"
						+"\"CodCargo\": \""+entidadBean.getRepresentanteBean().getCodCargo()+"\","
						+"\"ApellidoMaterno\": \""+entidadBean.getRepresentanteBean().getApellidoMaterno()+"\","
						+"\"ApellidoPaterno\": \""+entidadBean.getRepresentanteBean().getApellidoPaterno()+"\","
						+"\"Direccion\": \""+entidadBean.getRepresentanteBean().getDireccion()+"\","
						+"\"NombreCargo\": \""+entidadBean.getRepresentanteBean().getNombreCargo()+"\","
						+"\"Nombres\": \""+entidadBean.getRepresentanteBean().getNombres()+"\","
						+"\"NroDocumento\": \""+entidadBean.getRepresentanteBean().getNroDocumento()+"\","
						+"\"TipoDocumento\": \""+entidadBean.getRepresentanteBean().getTipoDocumento()+"\","
						+"\"Ubigeo\": \""+entidadBean.getRepresentanteBean().getUbigeo()+"\""
						
					+"}"
				+"]"
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
