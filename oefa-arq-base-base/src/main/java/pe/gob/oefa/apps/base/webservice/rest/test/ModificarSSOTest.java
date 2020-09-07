package pe.gob.oefa.apps.base.webservice.rest.test;


import pe.gob.oefa.apps.base.webservice.rest.sso.bean.SSOObjectREST;
import pe.gob.oefa.apps.base.webservice.rest.sso.bean.UsuarioSSO;
import pe.gob.oefa.apps.base.webservice.rest.sso.servicio.SSOREST;


public class ModificarSSOTest {

	public static void main(String[] args) {
		
		SSOREST SSOREST = new SSOREST();

		try {
			UsuarioSSO usuario= new UsuarioSSO();
			usuario.setIdUsuario(25L);
			usuario.setCorreoElectronico("fsantos@oefa.gob.pez");
			SSOObjectREST  SSOObjectREST = SSOREST.modificar(usuario);
			if (SSOObjectREST!=null) {
				System.out.println(SSOObjectREST);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
