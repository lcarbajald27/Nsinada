package pe.gob.oefa.apps.base.webservice.rest.test;

import pe.gob.oefa.apps.base.webservice.rest.persona.bean.PersonaBean;
import pe.gob.oefa.apps.base.webservice.rest.persona.servicio.PersonaREST;



public class PersonaNaturalTest {

	public static void main(String[] args) {
		

		PersonaREST personaREST = new PersonaREST();
		PersonaBean personaBean = personaREST.getPersona("42903800");
		System.out.println(personaBean);


		
	}
	
}
