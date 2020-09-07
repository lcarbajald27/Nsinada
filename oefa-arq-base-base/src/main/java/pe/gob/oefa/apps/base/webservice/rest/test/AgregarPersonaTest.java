package pe.gob.oefa.apps.base.webservice.rest.test;

import pe.gob.oefa.apps.base.webservice.rest.persona.bean.PersonaBean;
import pe.gob.oefa.apps.base.webservice.rest.persona.servicio.PersonaREST;



public class AgregarPersonaTest {

	public static void main(String[] args) {
		
		PersonaREST personaREST= new PersonaREST();
		
		PersonaBean personaBean= new PersonaBean();

		personaBean.setApellidoMaterno("Arbildo");
		personaBean.setApellidoPaterno("Novoa");
		personaBean.setNombres("Aristedes");
		personaBean.setTipoDocumento("2");
		personaBean.setNroDocumento("73994013");
		personaBean.setDireccion("Av. Arequipa 3030");
		personaBean.setUbigeo("010101");
		
		if (personaREST.agregar(personaBean)) {
			System.out.println("Exito al Persona usuario");
		} else {
			System.out.println("Error al Persona usuario");
		}
	
		
		
	}

}
