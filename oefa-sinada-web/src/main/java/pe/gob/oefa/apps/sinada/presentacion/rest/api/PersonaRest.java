package pe.gob.oefa.apps.sinada.presentacion.rest.api;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pe.gob.oefa.apps.base.presentacion.response.RespuestaHttp;
import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.base.webservice.rest.persona.bean.PersonaBean;
import pe.gob.oefa.apps.base.webservice.rest.persona.servicio.PersonaREST;
import pe.gob.oefa.apps.sinada.bean.general.Persona;
import pe.gob.oefa.apps.sinada.bean.general.PersonaSSO;
import pe.gob.oefa.apps.sinada.servicio.inf.general.PersonaSSOService;
import pe.gob.oefa.apps.sinada.servicio.inf.general.PersonaService;

import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
@RequestMapping(value="/rest/api/persona")
public class PersonaRest
{
	@Autowired
	private PersonaService personaService;
	
	@Autowired
	private PersonaREST personaREST;
	
	@Autowired
	private PersonaSSOService personaSSOService;
	
	
private String logBase = "oefa-sinada-web: PersonaRest";
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/buscar", method=RequestMethod.GET)
	public RespuestaHttp buscar(@RequestParam(value="prmCriterio",required=false)String prmCriterio)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try
		{
			Persona prmPersona = new ObjectMapper().readValue(prmCriterio, Persona.class);
			Persona persona =  personaService.buscarPorId(prmPersona.getIdPersona());
			_RespuestaHttp.setValido(true);
			_RespuestaHttp.setData(persona);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			_RespuestaHttp.setValido(false);
			_RespuestaHttp.setMensaje("Hubo un error al procesar la información");
		}
		return _RespuestaHttp;
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/buscar-documento", method=RequestMethod.GET)
	public RespuestaHttp buscarXNumeroDocumento(@RequestParam(value="prmCriterio",required=false)String prmCriterio)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try
		{
			Persona prmPersona = new ObjectMapper().readValue(prmCriterio, Persona.class);
			
			Persona persona = buscarPersonaServicio(prmPersona.getDocumento());
			
			 persona =  personaService.buscarPorNumeroDocumento(prmPersona.getDocumento());
			
//			persona = validacionRegistroPersona(persona, prmPersona.getDocumento());
			
			
			
			if(persona!=null && persona.getIdPersona()!=0){
				_RespuestaHttp.setValido(true);
				_RespuestaHttp.setData(persona);	
			}else{
				_RespuestaHttp.setValido(false);
				_RespuestaHttp.setMensaje("Hubo un error al procesar la información");
			}
			
		}
		catch (Exception e)
		{
			logger.error(this.logBase+ " : buscarXNumeroDocumento" + e.getMessage());
			e.printStackTrace();
			_RespuestaHttp.setValido(false);
			_RespuestaHttp.setMensaje("Hubo un error al procesaar la información");
		}
		return _RespuestaHttp;
	}
	
	

	
	public static String corregirTexto(String data){
			
			String[] textoFormateado = data.split(" ");
			String textoCorregido ="";
			for (int i = 0; i < textoFormateado.length; i++) {
				if(!textoFormateado[i].equals("")){
					textoCorregido = textoCorregido + textoFormateado[i]+ " ";
				}
			}
			return textoCorregido;
		}
	

	
	public static List<String> descomponerNombreCompleto(String[] nombreCompleto){
		List<String> nombreCompletoSeparado = new ArrayList<String>();
		
		
		int i = 0;
		while ( i < nombreCompleto.length) {
			
			int res = 0;
			if(nombreCompleto[i].toUpperCase().equals("DE") && nombreCompleto[i+1].toUpperCase().equals("LA")){
				nombreCompletoSeparado.add(nombreCompleto[i].toUpperCase()+ " " + nombreCompleto[i+1].toUpperCase() +" " +  nombreCompleto[i+2].toUpperCase());
				res = 3;
				i= i + res;
				continue;
			}
			
			if(nombreCompleto[i].toUpperCase().equals("DE") && !nombreCompleto[i+1].toUpperCase().equals("LA")){
				nombreCompletoSeparado.add(nombreCompleto[i].toUpperCase()+ " " + nombreCompleto[i+1].toUpperCase());
				res =2;
				i= i + res;
				continue;
			}
			
			if(nombreCompleto[i].toUpperCase().equals("LA")){
				nombreCompletoSeparado.add(nombreCompleto[i].toUpperCase()+ " " + nombreCompleto[i+1].toUpperCase());
				res =2;
				i= i + res;
				continue;
			}
			
			
			if(nombreCompleto[i].toUpperCase().equals("DEL")){
				nombreCompletoSeparado.add(nombreCompleto[i].toUpperCase()+ " " + nombreCompleto[i+1].toUpperCase());
				res =2;
				i= i + res;
				continue;
			}
			
			if(nombreCompleto[i].toUpperCase().equals("Y")){
				nombreCompletoSeparado.add(nombreCompleto[i].toUpperCase()+ " " + nombreCompleto[i+1].toUpperCase());
				res =2;
				i= i + res;
				continue;
			}
			
			
			
			if(!nombreCompleto[i].toUpperCase().equals("DE")){
				nombreCompletoSeparado.add(nombreCompleto[i].toUpperCase());
				res =1;
				i= i + res;
				continue;
			}
	
			
			
		}
		return nombreCompletoSeparado;
		
	}
	
	
	private Persona validacionRegistroPersona(Persona persona, String dni) throws ServicioException {
		
		
		if (	persona != null 
			&& 	persona.getIdPersona() == 0) {
			
			/** USO DEL SERVICIO A SUNAT POR RUC Y REGISTRO **/
			PersonaBean personaBean = personaREST.getPersona(dni);
			
			if(personaBean!=null){
				/** REGISTRAR ENTIDAD A BD **/
				persona.setIdPersona(Long.parseLong(personaBean.getCodigo()));
				
				if (personaBean.getNombreCompleto() != null) {
					
					String nombreCompletoCorregido = corregirTexto(personaBean.getNombreCompleto());
					personaBean.setNombreCompleto(nombreCompletoCorregido);
					
					
					
					String[] nombreCompleto = personaBean.getNombreCompleto().split(" ");
					
					List<String> nombresCompletoDescompuesto =  descomponerNombreCompleto(nombreCompleto);
					
					if(nombresCompletoDescompuesto.get(0)!=null && nombresCompletoDescompuesto.get(0)!=""){
						persona.setApellidoPaterno(nombresCompletoDescompuesto.get(0));
					}
					
					if(nombresCompletoDescompuesto.get(1)!=null && nombresCompletoDescompuesto.get(1)!=""){
						persona.setApellidoMaterno(nombresCompletoDescompuesto.get(1));
					}
					
					if(nombresCompletoDescompuesto.get(2)!=null && nombresCompletoDescompuesto.get(2)!=""){
						persona.setPrimerNombre(nombresCompletoDescompuesto.get(2));
					}
					
					String nombres = "";
					
					for (int i = 3; i < nombresCompletoDescompuesto.size(); i++) {
						nombres += nombresCompletoDescompuesto.get(i) + "";
					}
					
					persona.setSegundoNombre(nombres);
					
//					persona.setApellidoPaterno(nombreCompleto[0]);
//					persona.setApellidoMaterno(nombreCompleto.length > 1 ? nombreCompleto[1] : "");
//					
//					persona.setPrimerNombre(nombreCompleto.length > 2 ? nombreCompleto[2] : "");

				
				}
				
				
				if(personaBean.getUbigeo()!=null && personaBean.getUbigeo().length()==6){
					String ubigeo = personaBean.getUbigeo();
					
					persona.setDepartamento(ubigeo.substring(0,2));
					persona.setProvincia(ubigeo.substring(2,4));
					persona.setDistrito(ubigeo.substring(4,6));
				}
				persona.setDocumento(dni);//personaBean.getNroDocumento()); // EL SERVICIO NO ESTA DEVOLVIENDO EL NRO DOCUMENTO (DNI)
				persona.setDireccion(personaBean.getDireccion());
				System.out.println("persona:+"+persona);
				
				long idPersona = registrarPersona(persona);
				
				/** TRAER A LA ENTIDAD REGISTRADA DESDE LA BD **/
				persona =  personaService.buscarPorId(idPersona);
				
			}else{
				System.out.println("es igual a null");
				
				
  			}

			
		}
		
		return persona;
	}
	
	
	
	private Persona buscarPersonaServicio(String dni) throws ServicioException {
		Persona persona = null;
		if (dni.length() == 8) {

			/** USO DEL SERVICIO A SUNAT POR RUC Y REGISTRO **/
			PersonaBean personaBean = personaREST.getPersona(dni);

			if (personaBean != null) {
				 persona = new Persona();
				/** REGISTRAR ENTIDAD A BD **/
				persona.setIdPersona(Long.parseLong(personaBean.getCodigo()));

				if (personaBean.getNombreCompleto() != null) {
					persona.setApellidoMaterno(personaBean.getApellidoMaterno());
					persona.setApellidoPaterno(personaBean.getApellidoPaterno());
					String nombresCorregido = corregirTexto(personaBean
							.getNombres());
					String[] nombres = nombresCorregido
							.split(" ");
					
					if (nombres[0] != null
							&& nombres[0] != "") {
						persona.setPrimerNombre(nombres[0]);
					}

					String segundoNombre = "";

					for (int i = 1; i < nombres.length; i++) {
						segundoNombre += nombres[i] + "";
					}

					persona.setSegundoNombre(segundoNombre);
					
					
				}
				
//				if (personaBean.getNombreCompleto() != null) {
//
//					String nombreCompletoCorregido = corregirTexto(personaBean
//							.getNombreCompleto());
//					personaBean.setNombreCompleto(nombreCompletoCorregido);
//
//					String[] nombreCompleto = personaBean.getNombreCompleto()
//							.split(" ");
//
//					List<String> nombresCompletoDescompuesto = descomponerNombreCompleto(nombreCompleto);
//
//					if (nombresCompletoDescompuesto.get(0) != null
//							&& nombresCompletoDescompuesto.get(0) != "") {
//						persona.setApellidoPaterno(nombresCompletoDescompuesto
//								.get(0));
//					}
//
//					if (nombresCompletoDescompuesto.get(1) != null
//							&& nombresCompletoDescompuesto.get(1) != "") {
//						persona.setApellidoMaterno(nombresCompletoDescompuesto
//								.get(1));
//					}
//
//					if (nombresCompletoDescompuesto.get(2) != null
//							&& nombresCompletoDescompuesto.get(2) != "") {
//						persona.setPrimerNombre(nombresCompletoDescompuesto
//								.get(2));
//					}
//
//					String nombres = "";
//
//					for (int i = 3; i < nombresCompletoDescompuesto.size(); i++) {
//						nombres += nombresCompletoDescompuesto.get(i) + " ";
//					}
//
//					persona.setSegundoNombre(nombres);
//
//				}

				if (personaBean.getUbigeo() != null
						&& personaBean.getUbigeo().length() == 6) {
					String ubigeo = personaBean.getUbigeo();

					persona.setDepartamento(ubigeo.substring(0, 2));
					persona.setProvincia(ubigeo.substring(2, 4));
					persona.setDistrito(ubigeo.substring(4, 6));
				}
				persona.setDocumento(dni);// personaBean.getNroDocumento()); //
											// EL SERVICIO NO ESTA DEVOLVIENDO
											// EL NRO DOCUMENTO (DNI)
				persona.setDireccion(personaBean.getDireccion());
				System.out.println("persona:+" + persona);

				
				
				
				/********************************************************************************/
				/***																		  ***/
				/********************************************************************************/
				Persona personaExiste = personaService.buscarPorId(persona.getIdPersona());
				if(personaExiste!=null && personaExiste.getIdPersona()==0){
					
					registrarPersona(persona);
					
				}else{
					persona.setPrimerNombre(validaDatosAModificar(persona.getPrimerNombre(),personaExiste.getPrimerNombre()));
					persona.setSegundoNombre(validaDatosAModificar(persona.getSegundoNombre(),personaExiste.getSegundoNombre()));
					persona.setApellidoPaterno(validaDatosAModificar(persona.getApellidoPaterno(),personaExiste.getApellidoPaterno()));
					persona.setApellidoMaterno(validaDatosAModificar(persona.getApellidoMaterno(),personaExiste.getApellidoMaterno()));
					persona.setDireccion(validaDatosAModificar(persona.getDireccion(),personaExiste.getDireccion()));
					persona.setReferencia(validaDatosAModificar(persona.getReferencia(),personaExiste.getReferencia()));
					
					persona.setUbigeo(validaDatosAModificar(persona.getDepartamento()+persona.getProvincia()+persona.getDistrito(),personaExiste.getDepartamento()+personaExiste.getProvincia()+personaExiste.getDistrito()));
					
					
					
					if (persona.getPrimerNombre() != null
							|| persona.getSegundoNombre() != null
							|| persona.getApellidoPaterno() != null
							|| persona.getApellidoMaterno() != null
							|| persona.getDireccion() != null
							|| persona.getReferencia() != null
							|| (persona.getUbigeo()!=null && persona.getUbigeo().length()==6)) {
						actualizarPersona(persona);

					}
				
				}
				
				

				/** TRAER A LA ENTIDAD REGISTRADA DESDE LA BD **/
			

			} else {
				System.out.println("No se encontro persona en el servicio");

			}

		} else {

		}

		return persona;
	}
	
	private String validaDatosAModificar(String datoNuevo, String datoAntiguo){
		String data = null;
		
		if(datoAntiguo==null){
			datoAntiguo = "";
		}
		if(datoNuevo== null || datoNuevo.length()==0){
			return data;
		}
		
		if(!datoNuevo.equals(datoAntiguo)){
			data = datoNuevo;
		}
		
		
		return data;
		
	}
	
	private long registrarPersona(Persona persona) throws ServicioException {
		System.out.println("persona:::"+persona);
		long idPersona = personaService.insertar(persona);
		
		return idPersona;
	}
	
	private long actualizarPersona(Persona persona) throws ServicioException {
		System.out.println("persona:::"+persona);
		long idPersona = personaService.actualizar(persona);
		
		return idPersona;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/actualizar",method = RequestMethod.POST)
	public RespuestaHttp actualizar(@RequestBody Persona prmData,HttpServletRequest request)throws Exception
	{
		RespuestaHttp respuesta = new RespuestaHttp();
		try
		{
			
			int result = personaService.actualizar(prmData);
			
			if(result>0)
			{
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha actualizado la persona");
				respuesta.setData(prmData);
			} 
			else 
			{
				respuesta.setMensaje("No se pudo actualizar la persona");
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al actualizar a la persona");
		}
		return respuesta;
	}
	
//	@ResponseBody
//	@RequestMapping(value = "/registrar",method = RequestMethod.POST)
//	public RespuestaHttp registrar(@RequestBody Contenido prmContenido,HttpServletRequest request)throws Exception
//	{
//		RespuestaHttp respuesta = new RespuestaHttp();
//		try
//		{
//			System.out.println(prmContenido);
//			long idGenerado = contenidoService.insertar(prmContenido);
//			
//			if(idGenerado>0)
//			{
//				respuesta.setValido(true);
//				respuesta.setMensaje("Se ha registrado la Contenido satisfactoriamente");
//				respuesta.setData(prmContenido.getIdContenido());
//			} 
//			else 
//			{
//				respuesta.setMensaje("No se pudo registrar la Contenido");
//			}
//		} 
//		catch (Exception e) 
//		{
//			e.printStackTrace();
//			respuesta.setMensaje("Hubo un error al registrar la Contenido");
//		}
//		return respuesta;
//	}
	
//	@ResponseBody
//	@RequestMapping(value ="/actualizar", method = RequestMethod.POST)
//	public  RespuestaHttp actualizar(@RequestBody Contenido prmContenido,HttpServletRequest request)throws Exception {
//		RespuestaHttp respuesta = new RespuestaHttp();
//		try{
//			int result = contenidoService.actualizar(prmContenido);
//			
//			if(result>0){
//				respuesta.setValido(true);
//				respuesta.setMensaje("Se ha actualizado	la Contenido satisfactoriamente");
//			}else {
//				respuesta.setMensaje("No se pudo actualizar la Contenido");
//			}
//		}catch (Exception e) {
//			e.printStackTrace();
//			respuesta.setMensaje("Hubo un error al actualizar la Contenido");
//		}
//		return respuesta;
//	}
	
//	@ResponseBody
//	@RequestMapping(value = "/eliminar", method = RequestMethod.POST)
//	public RespuestaHttp eliminar(
//			@RequestBody Contenido prmContenido,HttpServletRequest request)
//			throws Exception{
//		RespuestaHttp respuesta = new RespuestaHttp();
//		try{
//			int result = contenidoService.eliminar(prmContenido);
//			if(result>0){
//				respuesta.setValido(true);
//				respuesta.setMensaje("Se ha eliminado la Contenido correctamente");
//			}else {
//				respuesta.setMensaje("No se pudo eliminar la Contenido");
//			}
//		} catch (Exception e){
//			e.printStackTrace();
//			respuesta.setMensaje("Hubo un error al eliminar la Contenido");
//		}
//		return respuesta;
//	}
	
//	@ResponseBody
//	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
//	public RespuestaHttp buscarxNumeroNorma(@PathVariable("id")Long idContenido)throws Exception 
//	{
//		RespuestaHttp respuesta = new  RespuestaHttp();
//		try
//		{
//			Contenido prmContenido = new Contenido();
//			prmContenido.setIdContenido(idContenido);
//			prmContenido = contenidoService.buscarPorId(prmContenido.getIdContenido());
//			
//			respuesta.setValido(true);
//			respuesta.setData(prmContenido);
//		} 
//		catch (Exception e)
//		{
//			respuesta.setMensaje("Hubo un error al obtener la Contenido");
//			e.printStackTrace();
//		}
//		return respuesta;
//	}

	@RequestMapping(value="/insertar-persona", method=RequestMethod.GET)
	public RespuestaHttp insertarPersona(@RequestParam(value="prmCriterio",required=false)String prmCriterio)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try
		{
			Persona prmPersona = new ObjectMapper().readValue(prmCriterio, Persona.class);
			
			long idPersona = personaService.insertar(prmPersona);
			
			if(idPersona>0){
				_RespuestaHttp.setValido(true);
				_RespuestaHttp.setMensaje("Se ha actualizado la persona");
				_RespuestaHttp.setData(idPersona);	
			}else{
				_RespuestaHttp.setValido(false);
				_RespuestaHttp.setMensaje("Hubo un error al procesar la información");
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			_RespuestaHttp.setValido(false);
			_RespuestaHttp.setMensaje("Hubo un error al procesar la información");
		}
		return _RespuestaHttp;
	}
	
	/********************************************************************************/
	/**																			   **/
	/********************************************************************************/
	
	
	@ResponseBody
	@RequestMapping(value = "/insertar-persona-oefa",method = RequestMethod.POST)
	public RespuestaHttp insertarPersonaOefa(@RequestBody Persona prmPersona,HttpServletRequest request)throws Exception
	{
		RespuestaHttp respuesta = new RespuestaHttp();
		try
		{
			PersonaSSO prmPersonaSSO = new PersonaSSO();
			prmPersonaSSO.setDocumento(prmPersona.getDocumento());
			
			
			prmPersonaSSO = personaSSOService.buscarPorNumeroDocumento(prmPersonaSSO);
			
			
			if(prmPersonaSSO==null){

				PersonaREST personaREST= new PersonaREST();
				
				PersonaBean personaBean= new PersonaBean();

				personaBean.setApellidoMaterno(prmPersona.getApellidoMaterno());
				personaBean.setApellidoPaterno(prmPersona.getApellidoPaterno());
				personaBean.setNombres(prmPersona.getNombres());
				personaBean.setTipoDocumento("2");
				personaBean.setNroDocumento(prmPersona.getDocumento());
				personaBean.setDireccion(prmPersona.getDireccion());
				if(prmPersona.getDepartamento()!=null && prmPersona.getDepartamento().length()==2 && prmPersona.getDepartamento()!="00"
				   && prmPersona.getProvincia()!=null && prmPersona.getProvincia().length()==2 && prmPersona.getProvincia()!="00"
				   && prmPersona.getDistrito()!=null && prmPersona.getDistrito().length()==2 && prmPersona.getDistrito()!="00"){
					personaBean.setUbigeo(prmPersona.getDepartamento()+prmPersona.getProvincia()+prmPersona.getDistrito());
				}else{
					personaBean.setUbigeo("");
					
				}
				
				
				if (personaREST.agregar(personaBean)) {
					respuesta.setValido(true);
					System.out.println("Exito al Persona usuario");
				} else {
					respuesta.setValido(false);
					respuesta.setMensaje("Hubo un error al procesar la información");
					System.out.println("Error al Persona usuario");
				}
			
			}else{
				
				respuesta.setValido(false);
				respuesta.setMensaje("La persona ya se encuentra registrada");
				System.out.println("Error al Persona usuario");
				
			}
			
//			if(idGenerado>0)
//			{
//				respuesta.setValido(true);
//				respuesta.setMensaje("Se ha registrado la Información satisfactoriamente");
//				respuesta.setData(prmCasos.getIdDetalleCaso());
//			} 
//			else 
//			{
//				respuesta.setMensaje("No se pudo registrar la Información");
//			}
		} 
		catch (Exception e) 
		{
			logger.error(this.logBase+ " : insertarPersonaOefa" + e.getMessage());
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al registrar la información");
		}
		return respuesta;
	}
	
	
	

	
	
	
	@ResponseBody
	@RequestMapping(value="/listar-persona-sso", method=RequestMethod.POST)
	public RespuestaHttp listarPersonaSSO(@RequestBody PersonaSSO prmPersona,HttpServletRequest request) throws Exception
	{
		RespuestaHttp respuesta = new RespuestaHttp();
		try
		{

			List<PersonaSSO> data = null;
			data = personaSSOService.listaPersonaSSO(prmPersona);
	
			
			respuesta.setValido(true);
			
			respuesta.setData(data);	
//			if(data!=null && data.size()>0){
//				respuesta.setValido(true);
//	
//				respuesta.setData(data);	
//			}else{
//				respuesta.setValido(false);
//				
//			}
			
			
		}
		catch (Exception e)
		{
			logger.error(this.logBase+ " : listarPersonaSSO" + e.getMessage());
			e.printStackTrace();
			respuesta.setValido(false);
			respuesta.setMensaje("Hubo un error al procesaar la información");
		}
		return respuesta;
	}
}
