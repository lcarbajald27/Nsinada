package pe.gob.oefa.apps.base.webservice.rest.test;

import pe.gob.oefa.apps.base.webservice.rest.seguridad.usuario.servicio.UsuarioREST;

public class AsignarPerfilTest {
	
	
	public static void main(String[] args) {
		
		UsuarioREST uRest= new UsuarioREST();
		if(uRest.asignarPerfil(463,47)){
			System.out.println("Exito al asignar perfil");
		}else{
			System.out.println("Error al asignar perfil");
		}
	}

}
