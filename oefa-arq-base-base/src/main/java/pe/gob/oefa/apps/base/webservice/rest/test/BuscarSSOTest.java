package pe.gob.oefa.apps.base.webservice.rest.test;


import pe.gob.oefa.apps.base.webservice.rest.sso.bean.SSOObjectREST;
import pe.gob.oefa.apps.base.webservice.rest.sso.servicio.SSOREST;


public class BuscarSSOTest {

	public static void main(String[] args) {
		
		SSOREST SSOREST = new SSOREST();

		try {
			SSOObjectREST  SSOObjectREST = SSOREST.buscarUsuarioXAplicacion("efa-nacional",44L);
			if (SSOObjectREST!=null) {
				System.out.println(SSOObjectREST);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
