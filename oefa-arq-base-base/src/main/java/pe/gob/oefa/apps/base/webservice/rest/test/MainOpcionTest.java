package pe.gob.oefa.apps.base.webservice.rest.test;

import java.util.List;

import pe.gob.oefa.apps.base.webservice.rest.seguridad.opcion.bean.OpcionBean;
import pe.gob.oefa.apps.base.webservice.rest.seguridad.opcion.servicio.OpcionREST;


public class MainOpcionTest {

	public static void main(String[] args) {
		
		OpcionREST opcionREST = new OpcionREST();
		
		List<OpcionBean> lstOpcionBean = opcionREST.getOpciones("136", "7");
		//String idUsuario, String idAplicacion
		for (OpcionBean opcionBean2 : lstOpcionBean) {
			System.out.println(opcionBean2);
		}	
	}
	
}
