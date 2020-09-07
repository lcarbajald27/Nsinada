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
import pe.gob.oefa.apps.sinada.bean.sirin.Normas;
import pe.gob.oefa.apps.sinada.servicio.inf.sirin.NormasService;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value="/rest/api/normas")
public class NormasRest
{
	@Autowired
	NormasService normasService;
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/listar", method=RequestMethod.GET)
	public RespuestaHttp buscar(@RequestParam(value="prmCriterio",required=false)String prmCriterio)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try
		{

			Normas prmNormas = new ObjectMapper().readValue(prmCriterio, Normas.class);
			List<Normas> normas = (List<Normas>) normasService.listar(prmNormas);
			_RespuestaHttp.setValido(true);
			_RespuestaHttp.setData(normas);
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
	public RespuestaHttp registrar(@RequestBody Normas prmNormas,HttpServletRequest request)throws Exception
	{
		RespuestaHttp respuesta = new RespuestaHttp();
		try
		{
			System.out.println(prmNormas);
			long idGenerado = normasService.insertar(prmNormas);
			
			if(idGenerado>0)
			{
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha registrado la norma satisfactoriamente");
				respuesta.setData(prmNormas.getIdNorma());
			} 
			else 
			{
				respuesta.setMensaje("No se pudo registrar la norma");
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al registrar la norma");
		}
		return respuesta;
	}
	
	@ResponseBody
	@RequestMapping(value ="/actualizar", method = RequestMethod.POST)
	public  RespuestaHttp actualizar(@RequestBody Normas prmNormas,HttpServletRequest request)throws Exception {
		RespuestaHttp respuesta = new RespuestaHttp();
		try{
			int result = normasService.actualizar(prmNormas);
			
			if(result>0){
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha actualizado	la norma satisfactoriamente");
			}else {
				respuesta.setMensaje("No se pudo actualizar la norma");
			}
		}catch (Exception e) {
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al actualizar la norma");
		}
		return respuesta;
	}
	
	@ResponseBody
	@RequestMapping(value = "/eliminar", method = RequestMethod.POST)
	public RespuestaHttp eliminar(
			@RequestBody Normas prmNormas,HttpServletRequest request)
			throws Exception{
		RespuestaHttp respuesta = new RespuestaHttp();
		try{
			int result = normasService.eliminar(prmNormas);
			if(result>0){
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha eliminado la norma correctamente");
			}else {
				respuesta.setMensaje("No se pudo eliminar la norma");
			}
		} catch (Exception e){
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al eliminar la norma");
		}
		return respuesta;
	}
	
	@ResponseBody
	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
	public RespuestaHttp buscarxNumeroNorma(@PathVariable("id")String numeroNorma)throws Exception 
	{
		RespuestaHttp respuesta = new  RespuestaHttp();
		try
		{
			Normas prmNormas = new Normas();
			prmNormas.setNumeroNorma(numeroNorma);
			prmNormas = normasService.buscarPorNumeroNorma(prmNormas);
			
			respuesta.setValido(true);
			respuesta.setData(prmNormas);
		} 
		catch (Exception e)
		{
			respuesta.setMensaje("Hubo un error al obtener la norma");
			e.printStackTrace();
		}
		return respuesta;
	}

}
