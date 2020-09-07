package pe.gob.oefa.apps.base.webservice.rest.test;

import pe.gob.oefa.apps.base.webservice.rest.std.bean.STDHojaTramiteBean;
import pe.gob.oefa.apps.base.webservice.rest.std.servicio.STDREST;

public class ConsultarSTDTest {

	public static void main(String[] args) {
		

		STDREST STDREST = new STDREST();
		STDHojaTramiteBean STDBean;
		try {
			STDBean = STDREST.obtenerHojaTramite("2018","e01","000807");
			System.out.println(STDBean.getDenuncia());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	


		
	}
	
}
