package pe.gob.oefa.apps.base.webservice.rest.test;

import pe.gob.oefa.apps.base.webservice.rest.entidad.bean.EntidadBean;
import pe.gob.oefa.apps.base.webservice.rest.entidad.servicio.EntidadREST;


public class PersonaJuridicaTest {
	public static void main(String[] args) {
		EntidadREST entidadREST = new EntidadREST();
		EntidadBean entidadBean = entidadREST.getEntidad("20460972621",true);
		System.out.println(entidadBean);
		System.out.println(entidadBean.getRepresentanteBean());
		/*
		RepresentanteREST representanteREST = new RepresentanteREST();
		RepresentanteBean eepresentanteBean = representanteREST.getRepresentante(entidadBean.getCodigo());
		System.out.println(eepresentanteBean);
		*/
	}
	
}
