package pe.gob.oefa.apps.base.webservice.rest.test;

import java.io.UnsupportedEncodingException;

import pe.gob.oefa.apps.base.webservice.rest.std.bean.STDHojaTramiteBean;
import pe.gob.oefa.apps.base.webservice.rest.std.bean.STDDenunciaBean;
import pe.gob.oefa.apps.base.webservice.rest.std.servicio.STDREST;

public class RegistrarSTDTest {

	public static void main(String[] args) {
		
		STDREST STDREST = new STDREST();
		
		STDDenunciaBean STDDenunciaBean= new STDDenunciaBean();

		STDDenunciaBean.setCODIGOSINADA("DEN-0008-2018");
		//STDDenunciaBean.setDESCRIPCION("Problemática");

		STDDenunciaBean.setDESCRIPCION("Problemática: PROBLEMA AMBIENTAL - ¿Debido a? nivel I: RUIDOS EN LA CALLE - Donde sucede nivel I: ZONA");
		//STDDenunciaBean.setDESCRIPCION("Problematica: Problemas de contaminación del agua - ¿Debido a? nivel I: Por inadecuada gestión de residuos sólidos no municipales que afectan cuerpos de agua - ¿Debido a? nivel II: Actividades de la industria manufacturera - ¿Debido a? nivel III:Actividades de la industria manufacturera bajo la competencia del OEFA: industria cementera, industria papelera, industria cervecera, industria de curtiembre, de fabricación de artículos de hormigón, cemento y yeso, industria de biocombustibles, industria básica de hierro y acero,  industria petroquímica (intermedia y final), industria de fundición de metales no ferrosos,  industria de fundición de hierro y acero, industria de elaboración de vinos, industria de destilación y mezcla de bebidas alcohólicas, producción de alcohol etílico a partir de sustancias fermentadas,  industria de elaboración de bebidas no alcohólicas, producción de aguas minerales, industria de elaboración de azúcar - Donde sucede nivel I: Área Natural Protegida de administración nacional (excepto en áreas de conservación privadas) (Incluye Parques Nacionales, Santuarios Nacionales, Santuarios Históricos, Reservas Paisajísticas, Refugios de vida silvestre, Reservas Nacionales, Bosques de Protección y Cotos de Caza - Donde sucede nivel II: Sí comprende cuerpo de agua - Donde sucede nivel III: Río o lago no navegable");
		STDDenunciaBean.setREMITENTE("JHON JULIAN  GUTIERREZ PERALTA");
		
		STDHojaTramiteBean STDBean = null;
		try {
			STDBean = STDREST.registrarDenunciaBody(STDDenunciaBean);
			System.out.println("STDBean respuesa de STD:"+STDBean.getDenuncia() );
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println("STDBean "+STDBean);
		/*
		if (STDBean!=null) {
			System.out.println("Denuncia "+STDBean.getDenuncia());
		
			if (STDBean.getDenuncia().getFLGEXITO().equals("1")) {
			System.out.println("Exito al registar STDDenunciaBean");
			
		} else {
			System.out.println("Error al registar STDDenunciaBean");
		}}
	*/
		
		
	}

}
