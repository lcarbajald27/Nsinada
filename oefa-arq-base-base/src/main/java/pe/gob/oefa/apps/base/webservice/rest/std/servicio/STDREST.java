package pe.gob.oefa.apps.base.webservice.rest.std.servicio;

import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import pe.gob.oefa.apps.base.util.properties.UtilProperties;
import pe.gob.oefa.apps.base.util.rest.UtilREST;
import pe.gob.oefa.apps.base.webservice.rest.base.servicio.BaseREST;
import pe.gob.oefa.apps.base.webservice.rest.std.bean.STDHojaTramiteBean;
import pe.gob.oefa.apps.base.webservice.rest.std.bean.STDDenunciaBean;

import com.google.gson.Gson;

@Service
public class STDREST extends BaseREST {
	
	public STDREST(){
	}
	
	private String logBase = "oefa-arq-base-base: STDREST";
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private void init(){
		super.URL=UtilProperties.getKey("URI_ConsultarSTDREST");
		System.out.println("super.URL "+super.URL);
	}
	
	public STDHojaTramiteBean obtenerHojaTramite(String anio, String tipo, String numero) throws Exception{
		
		this.init();
		
		String content;
		if(tipo!=null && tipo.length()!=0){
			tipo = tipo.toUpperCase();
		}
		try {
			content = UtilREST.getREST(super.URL+anio+"/"+tipo+"/"+numero);
			STDHojaTramiteBean hojaTramite = new STDHojaTramiteBean();
			hojaTramite = new Gson().fromJson(content, STDHojaTramiteBean.class);
			return hojaTramite;
		} catch (Exception e) {
			logger.error(this.logBase+ " : obtenerHojaTramite" + e.getMessage());
			throw new  Exception(e);
		}

		
	}
	
	public STDHojaTramiteBean registrarDenuncia(STDDenunciaBean STDDenunciaBean) throws Exception, UnsupportedEncodingException {
		this.init();
		STDHojaTramiteBean STDBean= new STDHojaTramiteBean();
		super.URL=UtilProperties.getKey("URI_RegistrarSTDREST");
		
		String params = "CODSINADA=\""+STDDenunciaBean.getCODIGOSINADA()+"\"";
		params += "&REMITENTE=\""+STDDenunciaBean.getREMITENTE()+"\"";
		params += "&DESCRIPCION=\""+STDDenunciaBean.getDESCRIPCION()+"\"";
		
		try {
			String response=UtilREST.postRESTWithParams(super.URL, params);
			STDBean= new Gson().fromJson(response, STDHojaTramiteBean.class);
			return STDBean;
		}
		catch (UnsupportedEncodingException e) {
			throw new Exception(e);
		} 
		catch (Exception e) {
			logger.error(this.logBase+ " : registrarDenuncia" + e.getMessage());
			throw new Exception(e);
		}
	}
	
	public STDHojaTramiteBean registrarDenunciaBody(STDDenunciaBean STDDenunciaBean) throws Exception, UnsupportedEncodingException {
		this.init();
		STDHojaTramiteBean STDBean= new STDHojaTramiteBean();
		super.URL=UtilProperties.getKey("URI_RegistrarSTDREST");
		
		String json="{\"remitente\": \""+STDDenunciaBean.getREMITENTE()+"\","
				+"\"descripcion\": \""+STDDenunciaBean.getDESCRIPCION() +"\","
				+"\"codsinada\": \""+STDDenunciaBean.getCODIGOSINADA()+"\""
				+"}";
		System.out.println("json "+json);
		
		try {
			String response=UtilREST.postREST(super.URL, json);
			/*
			int iHOJANUEVA=response.indexOf("HOJANUEVA");
			STDDenunciaBean oSTDDenunciaBean[]= new STDDenunciaBean[1];
			if (iHOJANUEVA>0) {
				oSTDDenunciaBean[0].setHOJANUEVA(response.substring(iHOJANUEVA+10, iHOJANUEVA+15));
			}
			int iFLGEXITO=response.indexOf("FLGEXITO");
			
			if (iFLGEXITO>0) {
				oSTDDenunciaBean[0].setFLGEXITO(response.substring(iFLGEXITO+9, iHOJANUEVA+10));
			}
			STDBean.setPCURSOR(oSTDDenunciaBean);
			return STDBean;
			*/
			// Comentado hasta que se solucione el tema de JSON por OEFA
			STDBean= new Gson().fromJson(response, STDHojaTramiteBean.class);
			return STDBean;
		}
		catch (UnsupportedEncodingException e) {
			logger.error(this.logBase+ " : registrarDenunciaBody" + e.getMessage());
			throw new Exception(e);
		} 
		catch (Exception e) {
			logger.error(this.logBase+ " : registrarDenunciaBody" + e.getMessage());
			throw new Exception(e);
		}
	}
	
}
