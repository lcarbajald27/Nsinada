package pe.gob.oefa.apps.base.webservice.rest.seguridad.opcion.servicio;

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
import pe.gob.oefa.apps.base.webservice.rest.generico.Acceso;
import pe.gob.oefa.apps.base.webservice.rest.seguridad.opcion.bean.OpcionBean;

public class OpcionREST extends BaseREST {
	
	public OpcionREST(){
		this.init();
	}
	
	private String logBase = "oefa-arq-base-base: OpcionREST";
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private void init(){
		super.URL=UtilProperties.getKey("URI_OpcionREST");
		System.out.println("super.URL "+super.URL);
		super.authString= UtilProperties.getKey("Usuario_REST") + ":" + UtilProperties.getKey("Clave_REST");
	}
	
	public List<OpcionBean> getOpciones(String idUsuario, String idAplicacion) {
		String content;
		List<OpcionBean> listOpcionBean = new ArrayList<OpcionBean>(); 
		try {
			content = UtilREST.getREST(super.URL+"IdUsuario="+idUsuario+"&IdAplicacion="+idAplicacion,super.authString);
			Gson gson = new Gson();						
			OpcionBean[] arregloOperacionBean = gson.fromJson(content, OpcionBean[].class);	
			
			for (int i = 0; i < arregloOperacionBean.length; i++) {
				listOpcionBean.add(arregloOperacionBean[i]);
			}
			
		} catch (IOException e) {
			logger.error(this.logBase+ " : getOpciones" + e.getMessage());
			e.printStackTrace();
		}
		
		return listOpcionBean;
	}
	public List<Acceso> getAccesos(String idUsuario, String idAplicacion) {
		List<Acceso> accesos= new ArrayList<>();
		List<OpcionBean> lstOpcionBean = this.getOpciones(idUsuario,idAplicacion);
		for (OpcionBean opcionBean : lstOpcionBean) {
			accesos.add(new Acceso(opcionBean.getuNombreOpcion()));
		}
		return accesos;
	}
}
