package pe.gob.oefa.apps.base.webservice.rest.test;

import pe.gob.oefa.apps.base.webservice.rest.entidad.bean.EntidadBean;
import pe.gob.oefa.apps.base.webservice.rest.entidad.servicio.EntidadREST;
import pe.gob.oefa.apps.base.webservice.rest.representante.bean.RepresentanteBean;
import pe.gob.oefa.apps.base.webservice.rest.representante.servicio.RepresentanteREST;

public class MainRepresentanteTest {

	public static void main(String[] args) {
		
		EntidadREST entidadREST = new EntidadREST();
		EntidadBean entidadBean = entidadREST.getEntidad("20600973445");
		//I Next (20600973445)
		System.out.println(entidadBean);
		RepresentanteREST representanteREST = new RepresentanteREST();
		RepresentanteBean representanteBean = representanteREST.getRepresentante(entidadBean.getCodigo());
		System.out.println(representanteBean);
		
	}
	
}
