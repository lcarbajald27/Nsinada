package pe.gob.oefa.apps.base.webservice.rest.representante.servicio;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import pe.gob.oefa.apps.base.util.properties.UtilProperties;
import pe.gob.oefa.apps.base.util.rest.UtilREST;
import pe.gob.oefa.apps.base.webservice.rest.base.servicio.BaseREST;
import pe.gob.oefa.apps.base.webservice.rest.representante.bean.RepresentanteBean;

@Service
public class RepresentanteREST extends BaseREST{
	
	public RepresentanteREST(){
//		this.init();
	}
	
	private void init(){
		super.URL=UtilProperties.getKey("URI_RepresentanteREST");
		//System.out.println("super.URL "+super.URL);
		super.authString= UtilProperties.getKey("Usuario_REST") + ":" + UtilProperties.getKey("Clave_REST");
		System.out.println("super.authString "+super.authString);
	}
	
	public RepresentanteBean getRepresentante(String idEmpresa){
		
		init();
		
		String content;
		RepresentanteBean[] representanteBean= new RepresentanteBean[10]; 
		try {
			String idAplicacion=UtilProperties.getKey("aplicacion.id");
			content = UtilREST.getREST(super.URL+"IdEmpresa="+idEmpresa+"&IdAplicacion="+idAplicacion,super.authString);
			if(content!=null){
							Gson gson = new Gson();						
							representanteBean = gson.fromJson(content, RepresentanteBean[].class);	
			}
		
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (representanteBean.length>0) {
			return representanteBean[0];
		}
		return null;
	}
	
}
