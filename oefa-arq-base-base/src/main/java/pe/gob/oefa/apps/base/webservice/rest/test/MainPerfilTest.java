package pe.gob.oefa.apps.base.webservice.rest.test;

import java.util.List;

import pe.gob.oefa.apps.base.webservice.rest.seguridad.perfil.bean.PerfilBean;
import pe.gob.oefa.apps.base.webservice.rest.seguridad.perfil.servicio.PerfilREST;


public class MainPerfilTest {

	public static void main(String[] args) {
		
		PerfilREST perfilREST = new PerfilREST();
		/*
		PerfilBean perfilBean = perfilREST.getPerfil("158", "50");
		System.out.println(perfilBean);
		*/
		List<PerfilBean> lstPerfilBean = perfilREST.getPerfiles("1", "44");
		for (PerfilBean perfilBean2 : lstPerfilBean) {
			System.out.println(perfilBean2);
		}

		
	}
	
}
