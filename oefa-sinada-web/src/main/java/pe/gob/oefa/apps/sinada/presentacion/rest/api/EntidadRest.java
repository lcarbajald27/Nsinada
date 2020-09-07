package pe.gob.oefa.apps.sinada.presentacion.rest.api;


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
import pe.gob.oefa.apps.base.webservice.rest.entidad.bean.EntidadBean;
import pe.gob.oefa.apps.base.webservice.rest.entidad.servicio.EntidadREST;
import pe.gob.oefa.apps.base.webservice.rest.representante.bean.RepresentanteBean;
import pe.gob.oefa.apps.sinada.bean.general.Entidad;
import pe.gob.oefa.apps.sinada.bean.general.Persona;
import pe.gob.oefa.apps.sinada.bean.general.PersonaSSO;
import pe.gob.oefa.apps.sinada.servicio.inf.general.EntidadService;
import pe.gob.oefa.apps.sinada.servicio.inf.general.PersonaSSOService;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value="/rest/api/entidad")
public class EntidadRest
{
	@Autowired
	EntidadService entidadService;
	
	@Autowired
	EntidadREST entidadREST;
	
	@Autowired
	private PersonaSSOService personaSSOService;
	
private String logBase = "oefa-sinada-web: EntidadRest";
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/buscar", method=RequestMethod.GET)
	public RespuestaHttp buscar(@RequestParam(value="prmCriterio",required=false)String prmCriterio)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try
		{
			Entidad prmEntidad = new ObjectMapper().readValue(prmCriterio, Entidad.class);
			Entidad entidad =  entidadService.buscarPorId(prmEntidad.getIdEntidad());
		
			
			
			_RespuestaHttp.setValido(true);
			_RespuestaHttp.setData(entidad);
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
			Entidad prmEntidad = new ObjectMapper().readValue(prmCriterio, Entidad.class);
			Entidad entidad =  buscarEntidadServicio(prmEntidad.getRuc());
			 entidad =  entidadService.buscarPorNumeroDocumento(prmEntidad.getRuc());
			 
			 if(entidad!=null && entidad.getIdEntidad()!=0){
					_RespuestaHttp.setValido(true);
					_RespuestaHttp.setData(entidad);	
				}else{
					_RespuestaHttp.setValido(false);
//					_RespuestaHttp.setMensaje("Hubo un error al procesar la información");
				}
//			entidad = validacionRegistroEntidad(entidad, prmEntidad.getRuc());
//			_RespuestaHttp.setValido(true);
//			_RespuestaHttp.setData(entidad);
		}
		catch (Exception e)
		{
			logger.error(this.logBase+ " : buscarXNumeroDocumento" + e.getMessage());
			e.printStackTrace();
			_RespuestaHttp.setValido(false);
			_RespuestaHttp.setMensaje("Hubo un error al procesar la información");
		}
		return _RespuestaHttp;
	}
	
	
private Entidad buscarEntidadServicio(String ruc) throws ServicioException {
	Entidad entidad = null;
		if (ruc.length()==11) {
			entidad = new Entidad();
			
			/** USO DEL SERVICIO A SUNAT POR RUC Y REGISTRO **/
			EntidadBean entidadBean = entidadREST.getEntidad(ruc,true);
			
			if(entidadBean!=null){
				/** REGISTRAR ENTIDAD A BD **/
				entidad.setIdEntidad(Long.parseLong(entidadBean.getCodigo()));
				entidad.setRazonSocial(entidadBean.getNombreCompleto());
				entidad.setRuc(entidadBean.getRUC());
				entidad.setDireccion(entidadBean.getDireccion());
				
				if(entidadBean.getUbigeo()!=null && entidadBean.getUbigeo().length()==6){
					String ubigeo = entidadBean.getUbigeo();
					
					entidad.setDepartamento(ubigeo.substring(0,2));
					entidad.setProvincia(ubigeo.substring(2,4));
					entidad.setDistrito(ubigeo.substring(4,6));
				}

				
				entidad.setNomRepresentante(entidadBean.getRepresentanteBean().getNombreCompleto());
				entidad.setNomCargo(entidadBean.getRepresentanteBean().getNombreCargo());
				
				
				/********************************************************************************/
				/***																		  ***/
				/********************************************************************************/
				
				Entidad entidadExiste =  entidadService.buscarPorId(entidad.getIdEntidad());
				
				if(entidadExiste!=null && entidadExiste.getIdEntidad()==0){
					long idEntidad = registrarEntidad(entidad);
				}else{
					
					entidad.setRazonSocial(validaDatosAModificar(entidad.getRazonSocial(),entidadExiste.getRazonSocial()));
					entidad.setDireccion(validaDatosAModificar(entidad.getDireccion(),entidadExiste.getDireccion()));
					entidad.setUbigeo(validaDatosAModificar(entidad.getDepartamento()+entidad.getProvincia()+entidad.getDistrito(),entidadExiste.getDepartamento()+entidadExiste.getProvincia()+entidadExiste.getDistrito()));
					
					entidad.setNomRepresentante(validaDatosAModificar(entidad.getNomRepresentante(),entidadExiste.getNomRepresentante()));
					entidad.setNomCargo(validaDatosAModificar(entidad.getNomCargo(),entidadExiste.getNomCargo()));
					
					
					if (entidad.getRazonSocial() != null
							|| entidad.getDireccion() != null
							|| entidad.getNomRepresentante() != null
							|| entidad.getNomCargo() != null
							|| (entidad.getUbigeo() != null && entidad
									.getUbigeo().length() == 6)) {
						
						actualizarEntidad(entidad);

					}
					
					
				}
				
				
				
				
				
				/** TRAER A LA ENTIDAD REGISTRADA DESDE LA BD **/
				
				
				
				
			}else{
				System.out.println("es igual a null");
				
				
  			}

			
		}
		
		return entidad;
	}

private long actualizarEntidad(Entidad entidad) throws ServicioException {

	long idPersona = entidadService.actualizar(entidad);
	
	return idPersona;
}
private String validaDatosAModificar(String datoNuevo, String datoAntiguo){
	String data = null;
	if(datoNuevo== null || datoNuevo.length()==0){
		return data;
	}
	
	if(!datoNuevo.equals(datoAntiguo)){
		data = datoNuevo;
	}
	
	
	return data;
	
}

private Entidad validacionRegistroEntidad(Entidad entidad, String ruc) throws ServicioException {
		
		if (	entidad == null ) {
			entidad = new Entidad();
			
			/** USO DEL SERVICIO A SUNAT POR RUC Y REGISTRO **/
			EntidadBean entidadBean = entidadREST.getEntidad(ruc,true);
			
			if(entidadBean!=null){
				/** REGISTRAR ENTIDAD A BD **/
				entidad.setIdEntidad(Long.parseLong(entidadBean.getCodigo()));
				entidad.setRazonSocial(entidadBean.getNombreCompleto());
				entidad.setRuc(entidadBean.getRUC());
				entidad.setDireccion(entidadBean.getDireccion());
				
				if(entidadBean.getUbigeo()!=null && entidadBean.getUbigeo().length()==6){
					String ubigeo = entidadBean.getUbigeo();
					
					entidad.setDepartamento(ubigeo.substring(0,2));
					entidad.setProvincia(ubigeo.substring(2,4));
					entidad.setDistrito(ubigeo.substring(4,6));
				}
//				entidad.setUbigeo(entidadBean.getUbigeo());
				
				
				entidad.setNomRepresentante(entidadBean.getRepresentanteBean().getNombreCompleto());
				entidad.setNomCargo(entidadBean.getRepresentanteBean().getNombreCargo());
				long idEntidad = registrarEntidad(entidad);
				
//				this.actualizarRepresentante(entidad);
				
				
				/** TRAER A LA ENTIDAD REGISTRADA DESDE LA BD **/
				entidad =  entidadService.buscarPorId(idEntidad);
				
				
				
			}else{
				System.out.println("es igual a null");
				
				
  			}

			
		}
		
		return entidad;
	}

	private long registrarEntidad(Entidad entidad) throws ServicioException {
		
		long idEntidad = entidadService.insertar(entidad);
		
		return idEntidad;
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
//			respuesta.setMensaje("Hubo un error al registar la Contenido");
//		}
//		return respuesta;
//	}
	
	@ResponseBody
	@RequestMapping(value = "/actualizar",method = RequestMethod.POST)
	public RespuestaHttp actualizar(@RequestBody Entidad prmData,HttpServletRequest request)throws Exception
	{
		RespuestaHttp respuesta = new RespuestaHttp();
		try
		{
			
			int result = entidadService.actualizar(prmData);
			
			if(result>0)
			{
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha actualizado la entidad");
				respuesta.setData(prmData);
			} 
			else 
			{
				respuesta.setMensaje("No se pudo actualizar la entidad");
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al actualizar a la entidad");
		}
		return respuesta;
	}
	
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

	@ResponseBody
	@RequestMapping(value="/insertar-entidad-oefa", method=RequestMethod.POST)
	public RespuestaHttp insertarEntidadOefa(@RequestBody Entidad prmData,HttpServletRequest request)  throws Exception
 {
		RespuestaHttp respuesta = new RespuestaHttp();
		try {
			// Persona prmPersona = new ObjectMapper().readValue(prmCriterio,
			// Persona.class);

			PersonaSSO prmPersonaSSO = new PersonaSSO();
			prmPersonaSSO.setDocumento(prmData.getRuc());

			prmPersonaSSO = personaSSOService
					.buscarPorNumeroDocumento(prmPersonaSSO);
			if (prmPersonaSSO == null) {

				EntidadREST entidadREST = new EntidadREST();

				EntidadBean entidadBean = new EntidadBean();

				entidadBean.setNombreCompleto(prmData.getRazonSocial());
				entidadBean.setRUC(prmData.getRuc());
				entidadBean.setDireccion(prmData.getDireccion());

				if (prmData.getDepartamento() != null
						&& prmData.getDepartamento().length() == 2
						&& prmData.getDepartamento() != "00"
						&& prmData.getProvincia() != null
						&& prmData.getProvincia().length() == 2
						&& prmData.getProvincia() != "00"
						&& prmData.getDistrito() != null
						&& prmData.getDistrito().length() == 2
						&& prmData.getDistrito() != "00") {

					entidadBean.setUbigeo(prmData.getDepartamento()
							+ prmData.getProvincia() + prmData.getDistrito());
				} else {
					// entidadBean.setUbigeo("");
				}

				RepresentanteBean representanteBean = new RepresentanteBean();
				if (prmData.getRepresentanteLegal() != null) {
					representanteBean.setCodCargo(0);
					representanteBean.setNroDocumento(prmData.getRepresentanteLegal().getDocumento());
					representanteBean.setApellidoMaterno(prmData
							.getRepresentanteLegal().getApellidoMaterno());
					representanteBean.setApellidoPaterno(prmData
							.getRepresentanteLegal().getApellidoPaterno());
					representanteBean.setNombres(prmData
							.getRepresentanteLegal().getNombres());
					representanteBean.setCodCargo(0);
					
					representanteBean.setNombreCargo(prmData.getNomCargo());

					representanteBean.setTipoDocumento("2");

					representanteBean.setUbigeo(prmData.getRepresentanteLegal()
							.getDepartamento()
							+ prmData.getRepresentanteLegal().getProvincia()
							+ prmData.getRepresentanteLegal().getDistrito());
					representanteBean.setDireccion(prmData
							.getRepresentanteLegal().getDireccion());
					entidadBean.setRepresentanteBean(representanteBean);
				}

				if (entidadREST.agregar(entidadBean)) {
					respuesta.setValido(true);

					System.out.println("Exito al Entidad usuario");
				} else {
					respuesta.setValido(false);
					respuesta
							.setMensaje("Hubo un error al procesar la información");
					System.out.println("Error al entidad usuario");
				}

			} else {
				respuesta.setValido(false);
				respuesta.setMensaje("La entidad ya se encuentra registrada");
			}

		} catch (Exception e) {
			logger.error(this.logBase+ " : insertarEntidadOefa" + e.getMessage());
			e.printStackTrace();
			respuesta.setValido(false);
			respuesta.setMensaje("Hubo un error al procesaar la información");
		}
		return respuesta;
	}
	
}
