package pe.gob.oefa.apps.sinada.presentacion.rest.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pe.gob.oefa.apps.base.presentacion.response.RespuestaHttp;
import pe.gob.oefa.apps.base.util.UtilRequest;
import pe.gob.oefa.apps.sinada.bean.general.ContactoPersona;
import pe.gob.oefa.apps.sinada.servicio.inf.general.ContactoPersonaService;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value="/rest/api/contactoPersona")
public class ContactoPersonaRest {
	
	@Autowired
	ContactoPersonaService contactoPersonaService;
	
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/listar", method=RequestMethod.GET)
	public RespuestaHttp buscar(@RequestParam("prmCriterio")String prmCriterio)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try
		{
			ContactoPersona prmContactoPersona = new ObjectMapper().readValue(prmCriterio, ContactoPersona.class);
			List<ContactoPersona> lstContactoPersona = (List<ContactoPersona>) contactoPersonaService.listar(prmContactoPersona);
			_RespuestaHttp.setValido(true);
			_RespuestaHttp.setData(lstContactoPersona);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			_RespuestaHttp.setValido(false);
			_RespuestaHttp.setMensaje("Hubo un error al procesar la información");
		}
		return _RespuestaHttp;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/validarContacto",method = RequestMethod.POST)
	public RespuestaHttp validarContacto(@RequestBody ContactoPersona prmContactoPersona,HttpServletRequest request)throws Exception
	{
		RespuestaHttp respuesta = new RespuestaHttp();
		try
		{

			ContactoPersona data = contactoPersonaService.validarContacto(prmContactoPersona);
			
			if(data!=null && data.getIdContactoPersona()!=0)
			{
				respuesta.setValido(true);
				respuesta.setMensaje("El contacto ya se encuentra registrado");
				respuesta.setData(prmContactoPersona.getIdContactoPersona());
			} else{
				respuesta.setValido(false);
			}
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al registrar el dato de contacto");
		}
		return respuesta;
	}
	
	@ResponseBody
	@RequestMapping(value = "/registrar",method = RequestMethod.POST)
	public RespuestaHttp registrar(@RequestBody ContactoPersona prmContactoPersona,HttpServletRequest request)throws Exception
	{
		RespuestaHttp respuesta = new RespuestaHttp();
		try
		{
			prmContactoPersona.setHostIp(UtilRequest.getClientIpAddress(request));
			ContactoPersona data = contactoPersonaService.validarContacto(prmContactoPersona);
			long idGenerado = 0;
			if(data==null || data.getIdContactoPersona()==0){
				 idGenerado = contactoPersonaService.insertar(prmContactoPersona);
			}
		
			
			if(idGenerado>0)
			{
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha registrado el dato de contacto satisfactoriamente");
				respuesta.setData(prmContactoPersona.getIdContactoPersona());
			} 
			else 
			{
				respuesta.setMensaje("El contacto ya se encuentra registrado.");
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al registrar el dato de contacto");
		}
		return respuesta;
	}
	
	@ResponseBody
	@RequestMapping(value ="/actualizar", method = RequestMethod.POST)
	public RespuestaHttp actualizar(@RequestBody ContactoPersona prmContactoPersona,HttpServletRequest request)throws Exception 
	{
		RespuestaHttp respuesta = new RespuestaHttp();
		try
		{
			prmContactoPersona.setHostIp(UtilRequest.getClientIpAddress(request));
			int result = contactoPersonaService.actualizar(prmContactoPersona);
			
			if(result>0)
			{
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha actualizado	el dato de contacto satisfactoriamente");
				respuesta.setData(prmContactoPersona);
			}
			else 
			{
				respuesta.setMensaje("No se pudo actualizar el dato de contacto");
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al actualizar el dato de contacto");
		}
		return respuesta;
	}
	
	@ResponseBody
	@RequestMapping(value = "/eliminar", method = RequestMethod.POST)
	public RespuestaHttp eliminar(@RequestBody ContactoPersona prmContactoPersona,HttpServletRequest request)throws Exception
	{
		RespuestaHttp respuesta = new RespuestaHttp();
		try
		{
			prmContactoPersona.setHostIp(UtilRequest.getClientIpAddress(request));
			int result = contactoPersonaService.eliminar(prmContactoPersona);
			
			if(result>0)
			{
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha eliminado el dato de contacto correctamente");
			}
			else 
			{
				respuesta.setMensaje("No se pudo eliminar el dato de contacto");
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al eliminar el dato de contacto");
		}
		return respuesta;
	}
	
	@ResponseBody
	@RequestMapping(value = "/buscar-id/{id}",method = RequestMethod.GET)
	public RespuestaHttp buscar(@PathVariable("id")long idDatosContacto)throws Exception 
	{
		RespuestaHttp respuesta = new  RespuestaHttp();
		try
		{
			ContactoPersona prmContactoPersona = new ContactoPersona();
			prmContactoPersona = contactoPersonaService.buscarPorId(idDatosContacto);
			
			respuesta.setValido(true);
			respuesta.setData(prmContactoPersona);
		} 
		catch (Exception e)
		{
			respuesta.setMensaje("Hubo un error al obtener el dato de contacto");
			e.printStackTrace();
		}
		return respuesta;
	}
	


}
