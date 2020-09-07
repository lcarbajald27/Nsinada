package pe.gob.oefa.apps.base.webservice.rest.seguridad.perfil.servicio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

import pe.gob.oefa.apps.base.util.properties.UtilProperties;
import pe.gob.oefa.apps.base.util.rest.UtilREST;
import pe.gob.oefa.apps.base.webservice.rest.base.servicio.BaseREST;
import pe.gob.oefa.apps.base.webservice.rest.seguridad.perfil.bean.PerfilBean;

public class PerfilREST extends BaseREST {
	
	public PerfilREST(){
		this.init();
	}
private String logBase = "oefa-arq-base-base: PerfilREST";
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private void init(){
		super.URL=UtilProperties.getKey("URI_PerfilREST");
		super.authString= UtilProperties.getKey("Usuario_REST") + ":" + UtilProperties.getKey("Clave_REST");
	}
	
	public PerfilBean getPerfil(String idUsuario, String idAplicacion) {
		String content;
		PerfilBean perfilBean = new PerfilBean(); 
		try {
			content = UtilREST.getREST(super.URL+"IdUsuario="+idUsuario+"&IdAplicacion="+idAplicacion,super.authString);
			Gson gson = new Gson();						
			PerfilBean[] arregloPerfilBean = gson.fromJson(content, PerfilBean[].class);	
			
			if (	arregloPerfilBean != null
				&& 	arregloPerfilBean.length > 0) {
				
				perfilBean = arregloPerfilBean[0];
			}
			
		} catch (IOException e) {
			logger.error(this.logBase+ " : getPerfil" + e.getMessage());
			e.printStackTrace();
		}

		return perfilBean;
	}
	
	public List<PerfilBean> getPerfiles(String idUsuario, String idAplicacion) {
		String content;
		List<PerfilBean> lstPerfilBean= new ArrayList<PerfilBean>();
		try {
			content = UtilREST.getREST(super.URL+"IdUsuario="+idUsuario+"&IdAplicacion="+idAplicacion,super.authString);
			Gson gson = new Gson();						
			PerfilBean[] argsPerfilBean = gson.fromJson(content, PerfilBean[].class);	
			
			for (PerfilBean oPerfilBean : argsPerfilBean) {
				lstPerfilBean.add(oPerfilBean);
			}
			
		} catch (IOException e) {
			logger.error(this.logBase+ " : getPerfiles" + e.getMessage());
			e.printStackTrace();
		}

		return lstPerfilBean;
	}
	
}
