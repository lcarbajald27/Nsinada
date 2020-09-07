package pe.gob.oefa.apps.base.webservice.rest.test;

import java.io.IOException;

import pe.gob.oefa.apps.base.util.rest.UtilREST;

public class MainPOSTTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String uri="http://localhost:8082/SpringCRUDRESTRepaso/clienteService/api/agregar";
		String rest="{\"idCliente\": 15,"
				+"\"razonSocial\": \"GALAXY BUSINESS xxx15\","
				+"\"ruc\": \"20544987415\","
				+"\"estado\": \"1\""
				+"}";
		try {
			String ret=UtilREST.postREST(uri, rest, "");
			System.out.println("ret: "+ret);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}

}
