package pe.gob.oefa.apps.base.webservice.rest.test;

import pe.gob.oefa.apps.base.webservice.rest.seguridad.usuario.bean.UsuarioExternoBean;
import pe.gob.oefa.apps.base.webservice.rest.seguridad.usuario.servicio.UsuarioREST;



public class UsuarioExternoTest {

	public static void main(String[] args) {
		
		UsuarioREST usuarioREST= new UsuarioREST();
		
		UsuarioExternoBean usuarioExternoBean= new UsuarioExternoBean();
		
		usuarioExternoBean.setTipo(1);
		usuarioExternoBean.setNroDoc("75844493");
		usuarioExternoBean.setClave("123456");
		usuarioExternoBean.setCorreoElectronico("lgarcia@prueba.com");
		usuarioExternoBean.setIdPerfil(274);
		
		if (usuarioREST.agregar(usuarioExternoBean)) {
			System.out.println("Exito al insertar usuario");
		} else {
			System.out.println("Error al insertar usuario");
		}
	
		
		
	}

}
