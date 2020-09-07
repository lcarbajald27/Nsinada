package pe.gob.oefa.apps.base.webservice.rest.test;

import pe.gob.oefa.apps.base.webservice.rest.entidad.bean.EntidadBean;
import pe.gob.oefa.apps.base.webservice.rest.entidad.servicio.EntidadREST;
import pe.gob.oefa.apps.base.webservice.rest.representante.bean.RepresentanteBean;

public class AgregarEntidadTest {

	public static void main(String[] args) {
		
		EntidadREST entidadREST= new EntidadREST();
		
		EntidadBean entidadBean= new EntidadBean();
		
		entidadBean.setNombreCompleto("Galaxy Business115");
		entidadBean.setRUC("20544987317");
		entidadBean.setUbigeo("010101");
		entidadBean.setDireccion("Av. Arequipa 3030");
	
		RepresentanteBean representanteBean= new RepresentanteBean();
//
		representanteBean.setApellidoMaterno("Arbildo");
		representanteBean.setApellidoPaterno("Novoa");
		representanteBean.setNombres("Aristedes");
		representanteBean.setCodCargo(0);
					representanteBean.setNombreCargo("Gerente");// S/C
							//representanteBean.setDireccion("Av. Arequipa 3030");
//								representanteBean.setTipoDocumento("2");
								/*						representanteBean.setNroDocumento("10611694");
		representanteBean.setUbigeo("010101");
		*/
		entidadBean.setRepresentanteBean(representanteBean);
		
		if (entidadREST.agregar(entidadBean)) {
			System.out.println("Exito al Entidad usuario");
		} else {
			System.out.println("Error al Entidad usuario");
		}
	
		
		
	}

}
