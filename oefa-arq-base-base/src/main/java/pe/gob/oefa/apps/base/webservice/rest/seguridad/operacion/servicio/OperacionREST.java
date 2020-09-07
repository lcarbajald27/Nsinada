package pe.gob.oefa.apps.base.webservice.rest.seguridad.operacion.servicio;

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
import pe.gob.oefa.apps.base.webservice.rest.seguridad.operacion.bean.OperacionBean;

public class OperacionREST extends BaseREST {
	
	public OperacionREST(){
		this.init();
	}
private String logBase = "oefa-arq-base-base: OperacionREST";
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private void init(){
		super.URL=UtilProperties.getKey("URI_OperacionREST");
		System.out.println("super.URL "+super.URL);
		super.authString= UtilProperties.getKey("Usuario_REST") + ":" + UtilProperties.getKey("Clave_REST");
	}
	
	public List<OperacionBean> getOperaciones(String idUsuario, String idAplicacion) {
		String content;
		List<OperacionBean> listOperacionBean = new ArrayList<OperacionBean>(); 
		try {
			content = UtilREST.getREST(super.URL+"IdUsuario="+idUsuario+"&IdAplicacion="+idAplicacion,super.authString);
			Gson gson = new Gson();						
			OperacionBean[] arregloOperacionBean = gson.fromJson(content, OperacionBean[].class);	
			
			for (int i = 0; i < arregloOperacionBean.length; i++) {
				listOperacionBean.add(arregloOperacionBean[i]);
			}
			
		} catch (IOException e) {
			logger.error(this.logBase+ " : getOpciones" + e.getMessage());
			e.printStackTrace();
		}
		
		return listOperacionBean;
	}
	public List<Acceso> getAccesos(String idUsuario, String idAplicacion) {
		List<Acceso> accesos= new ArrayList<>();
		List<OperacionBean> lstOperacionBean = this.getOperaciones(idUsuario,idAplicacion);
		for (OperacionBean operacionBean : lstOperacionBean) {
			accesos.add(new Acceso(operacionBean.getuNombreOperacion(),operacionBean.getFK_eIdOpcion()));
		}
		return accesos;
	}
	public List<Acceso> getAccesos(String idUsuario, String idAplicacion, long idOpcion) {
		List<Acceso> accesos= new ArrayList<>();
		List<OperacionBean> lstOperacionBean = this.getOperaciones(idUsuario,idAplicacion);
		for (OperacionBean operacionBean : lstOperacionBean) {
			if (operacionBean.getFK_eIdOpcion()==idOpcion) {
				if(operacionBean.getOptionSelected()==1){
					System.out.println("operacionBean.getuNombreOperacion() "+operacionBean.getuNombreOperacion());
					accesos.add(new Acceso(operacionBean.getuNombreOperacion()));
				}
			}
			
		}
		return accesos;
	}
}
