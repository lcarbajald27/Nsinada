package pe.gob.oefa.apps.base.webservice.rest.test;

import pe.gob.oefa.apps.base.webservice.rest.entidad.bean.EntidadBean;
import pe.gob.oefa.apps.base.webservice.rest.entidad.servicio.EntidadREST;

public class MainEntidadTest {

	public static void main(String[] args) {
		
		EntidadREST entidadREST = new EntidadREST();
		EntidadBean entidadBean = entidadREST.getEntidad("20600973445");
//		I Next (20600973445)-- 20544987306
		System.out.println(entidadBean);
		
//		PersonaREST personaREST = new PersonaREST();
//		PersonaBean personaBean = personaREST.getPersona("08631532");
//		System.out.println(personaBean);
		
//		PerfilREST perfilREST = new PerfilREST();
//		PerfilBean perfilBean = perfilREST.getPerfil("9", "1");
//		System.out.println(perfilBean);

//		OperacionREST operacionREST = new OperacionREST();
//		List<OperacionBean> listOperacionBean = operacionREST.getOperaciones("9", "1");
//		System.out.println(listOperacionBean);

//		OpcionREST opcionREST = new OpcionREST();
//		List<OpcionBean> listOpcionBean = opcionREST.getOpciones("9", "1");
//		System.out.println(listOpcionBean);

//		UsuarioREST usuarioREST = new UsuarioREST();
//		List<UsuarioBean> listUsuarioBean = usuarioREST.getUsuarios("S", "1", "4");
//		System.out.println(listUsuarioBean);
		
	}
	
}
