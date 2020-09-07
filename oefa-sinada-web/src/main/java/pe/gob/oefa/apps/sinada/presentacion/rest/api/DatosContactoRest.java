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
import pe.gob.oefa.apps.sinada.bean.sirefa.DatosContacto;
import pe.gob.oefa.apps.sinada.servicio.inf.sirefa.DatosContactoService;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value="/rest/api/datosContacto")
public class DatosContactoRest {
	
	@Autowired
	DatosContactoService datosContactoService;
	
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="", method=RequestMethod.GET)
	public RespuestaHttp buscar(@RequestParam("prmCriterio")String prmCriterio)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try
		{
			DatosContacto prmDatosContacto = new ObjectMapper().readValue(prmCriterio, DatosContacto.class);
			List<DatosContacto> datosContactos = (List<DatosContacto>) datosContactoService.listar(prmDatosContacto);
			_RespuestaHttp.setValido(true);
			_RespuestaHttp.setData(datosContactos);
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
	public RespuestaHttp registrar(@RequestBody DatosContacto prmDatosContacto,HttpServletRequest request)throws Exception
	{
		RespuestaHttp respuesta = new RespuestaHttp();
		try
		{
//			prmDatosContacto.setIdContacto(prmDatosContacto.getIdContacto());
			long idGenerado = datosContactoService.insertar(prmDatosContacto);
			
			if(idGenerado>0)
			{
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha registrado el dato de contacto satisfactoriamente");
				respuesta.setData(prmDatosContacto.getIdDatosContacto());
			} 
			else 
			{
				respuesta.setMensaje("No se pudo registrar el dato de contacto");
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
	public RespuestaHttp actualizar(@RequestBody DatosContacto prmDatosContacto,HttpServletRequest request)throws Exception 
	{
		RespuestaHttp respuesta = new RespuestaHttp();
		try
		{
			int result = datosContactoService.actualizar(prmDatosContacto);
			
			if(result>0)
			{
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha actualizado	el dato de contacto satisfactoriamente");
				respuesta.setData(prmDatosContacto);
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
	public RespuestaHttp eliminar(@RequestBody DatosContacto prmDatosContacto,HttpServletRequest request)throws Exception
	{
		RespuestaHttp respuesta = new RespuestaHttp();
		try
		{
			int result = datosContactoService.eliminar(prmDatosContacto);
			
			if(result>0)
			{
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha eliminado el dato de contacto correctamente");
			}
			else 
			{
				respuesta.setMensaje("No se pudo eliminar el dato contacto");
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
	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
	public RespuestaHttp buscar(@PathVariable("id")long idDatosContacto)throws Exception 
	{
		RespuestaHttp respuesta = new  RespuestaHttp();
		try
		{
			DatosContacto prmDatosContacto = new DatosContacto();
			prmDatosContacto = datosContactoService.buscarPorId(idDatosContacto);
			
			respuesta.setValido(true);
			respuesta.setData(prmDatosContacto);
		} 
		catch (Exception e)
		{
			respuesta.setMensaje("Hubo un error al obtener el dato de contacto");
			e.printStackTrace();
		}
		return respuesta;
	}
	


}
