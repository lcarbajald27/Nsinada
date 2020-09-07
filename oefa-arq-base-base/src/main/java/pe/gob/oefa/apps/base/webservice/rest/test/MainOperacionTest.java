package pe.gob.oefa.apps.base.webservice.rest.test;

import java.util.ArrayList;
import java.util.List;

import pe.gob.oefa.apps.base.webservice.rest.generico.Acceso;
import pe.gob.oefa.apps.base.webservice.rest.seguridad.operacion.servicio.OperacionREST;


public class MainOperacionTest {

	public static void main(String[] args) {
		
		OperacionREST operacionREST = new OperacionREST();
		/*
		List<OperacionBean> lstOperacionBean = operacionREST.getOperaciones("170", "51");
		for (OperacionBean operacionBean2 : lstOperacionBean) {
			System.out.println(operacionBean2);
		}
	    */
		List<Acceso> lstAccesos = new ArrayList<>();
		//String idUsuario, String idAplicacion, long idOpcion // Cambia porfa
		lstAccesos = operacionREST.getAccesos("16", "27",63);
		for (Acceso acceso : lstAccesos) {
			System.out.println(acceso);
		}

	}
	
}
