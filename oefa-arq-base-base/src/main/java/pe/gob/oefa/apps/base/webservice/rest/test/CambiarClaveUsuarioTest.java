package pe.gob.oefa.apps.base.webservice.rest.test;


import pe.gob.oefa.apps.base.webservice.rest.seguridad.usuario.bean.UsuarioExternoBean;
import pe.gob.oefa.apps.base.webservice.rest.seguridad.usuario.servicio.UsuarioREST;


public class CambiarClaveUsuarioTest {

	public static void main(String[] args) {
		
		UsuarioREST usuarioREST = new UsuarioREST();
		UsuarioExternoBean usuario=usuarioREST.cambiarClave(133, "12345678", "12345678");
		System.out.println(usuario);
		

		
	}
	
}
