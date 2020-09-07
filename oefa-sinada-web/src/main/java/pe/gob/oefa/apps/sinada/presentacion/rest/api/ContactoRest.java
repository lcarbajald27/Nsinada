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
import pe.gob.oefa.apps.sinada.bean.sirefa.Contacto;
import pe.gob.oefa.apps.sinada.servicio.inf.sirefa.ContactoService;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value="/rest/api/contacto")
public class ContactoRest {
	
	@Autowired
	ContactoService contactoService;
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="", method=RequestMethod.GET)
	public RespuestaHttp buscar(@RequestParam("prmCriterio")String prmCriterio)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try
		{
			Contacto prmContacto = new ObjectMapper().readValue(prmCriterio, Contacto.class);
			List<Contacto> clientes = (List<Contacto>) contactoService.listar(prmContacto);
			_RespuestaHttp.setValido(true);
			_RespuestaHttp.setData(clientes);
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
	@RequestMapping(value = "/registrar",method = RequestMethod.POST)
	public RespuestaHttp registrar(@RequestBody Contacto prmContacto,HttpServletRequest request) throws Exception
	{
		RespuestaHttp respuesta = new RespuestaHttp();
		try
		{
			long idGenerado = contactoService.insertar(prmContacto);
			
			if(idGenerado>0)
			{
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha registrado el contacto satisfactoriamente");
				respuesta.setData(prmContacto.getIdContacto());
			} else {
				respuesta.setMensaje("No se pudo registrar el contacto");
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al registrar el contacto");
		}
		return respuesta;
	}
	
	@ResponseBody
	@RequestMapping(value ="/actualizar", method = RequestMethod.POST)
	public RespuestaHttp actualizar(@RequestBody Contacto prmContacto,HttpServletRequest request)throws Exception 
	{
		RespuestaHttp respuesta = new RespuestaHttp();
		try
		{
			int result = contactoService.actualizar(prmContacto);
			
			if(result>0)
			{
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha actualizado	el contacto satisfactoriamente");
			}
			else 
			{
				respuesta.setMensaje("No se pudo actualizar el contacto");
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al actualizar el contacto");
		}
		return respuesta;
	}
	
	@ResponseBody
	@RequestMapping(value = "/eliminar", method = RequestMethod.POST)
	public RespuestaHttp eliminar(@RequestBody Contacto prmContacto,HttpServletRequest request)throws Exception
	{
		RespuestaHttp respuesta = new RespuestaHttp();
		try
		{
			int result = contactoService.eliminar(prmContacto);
			
			if(result>0){
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha eliminado el contacto correctamente");
			}
			else 
			{
				respuesta.setMensaje("No se pudo eliminar el contacto");
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al eliminar el contacto");
		}
		return respuesta;
	}
	
	@ResponseBody
	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
	public RespuestaHttp buscar(@PathVariable("id")long idContacto)throws Exception 
	{
		RespuestaHttp respuesta = new  RespuestaHttp();
		try
		{
			Contacto prmContacto = new Contacto();
			prmContacto = contactoService.buscarPorId(idContacto);
			
			respuesta.setValido(true);
			respuesta.setData(prmContacto);
		} 
		catch (Exception e)
		{
			respuesta.setMensaje("Hubo un error al obtener el contacto");
			e.printStackTrace();
		}
		return respuesta;
	}
	

}
