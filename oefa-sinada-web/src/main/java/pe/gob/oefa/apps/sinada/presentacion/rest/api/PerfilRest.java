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
import pe.gob.oefa.apps.sinada.bean.seguridad.Perfil;
import pe.gob.oefa.apps.sinada.servicio.inf.seguridad.PerfilService;

@Controller
@RequestMapping(value="/rest/api/perfil")
public class PerfilRest
{
	@Autowired
	PerfilService perfilService;
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/listar", method=RequestMethod.GET)
	public RespuestaHttp buscar(@RequestParam(value="prmCriterio",required=false)String prmCriterio)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try
		{
//			Perfil prmPerfil = new ObjectMapper().readValue(prmCriterio, Perfil.class);
			Perfil prmPerfil = new Perfil();
			List<Perfil> perfil = (List<Perfil>) perfilService.listar(prmPerfil);
			_RespuestaHttp.setValido(true);
			_RespuestaHttp.setData(perfil);
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
	public RespuestaHttp registrar(@RequestBody Perfil prmPerfil,HttpServletRequest request)throws Exception
	{
		RespuestaHttp respuesta = new RespuestaHttp();
		try
		{
			System.out.println(prmPerfil);
			long idGenerado = perfilService.insertar(prmPerfil);
			
			if(idGenerado>0)
			{
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha registrado el perfil satisfactoriamente");
				respuesta.setData(prmPerfil.getIdPerfil());
			} 
			else 
			{
				respuesta.setMensaje("No se pudo registrar el perfil");
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al registrar el perfil");
		}
		return respuesta;
	}
	
	@ResponseBody
	@RequestMapping(value ="/actualizar", method = RequestMethod.POST)
	public  RespuestaHttp actualizar(@RequestBody Perfil prmPerfil,HttpServletRequest request)throws Exception {
		RespuestaHttp respuesta = new RespuestaHttp();
		try{
			int result = perfilService.actualizar(prmPerfil);
			
			if(result>0){
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha actualizado	el perfil satisfactoriamente");
			}else {
				respuesta.setMensaje("No se pudo actualizar el perfil");
			}
		}catch (Exception e) {
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al actualizar el perfil");
		}
		return respuesta;
	}
	
	@ResponseBody
	@RequestMapping(value = "/eliminar", method = RequestMethod.POST)
	public RespuestaHttp eliminar(
			@RequestBody Perfil prmPerfil,HttpServletRequest request)
			throws Exception{
		RespuestaHttp respuesta = new RespuestaHttp();
		try{
			int result = perfilService.eliminar(prmPerfil);
			if(result>0){
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha eliminado el perfil correctamente");
			}else {
				respuesta.setMensaje("No se pudo eliminar el perfil");
			}
		} catch (Exception e){
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al eliminar el perfil");
		}
		return respuesta;
	}
	
	@ResponseBody
	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
	public RespuestaHttp buscarxNumeroNorma(@PathVariable("id")Long idPerfil)throws Exception 
	{
		RespuestaHttp respuesta = new  RespuestaHttp();
		try
		{
			Perfil prmPerfil = new Perfil();
			prmPerfil.setIdPerfil(idPerfil);
			prmPerfil = perfilService.buscarPorId(prmPerfil.getIdPerfil());
			
			respuesta.setValido(true);
			respuesta.setData(prmPerfil);
		} 
		catch (Exception e)
		{
			respuesta.setMensaje("Hubo un error al obtener el artículo");
			e.printStackTrace();
		}
		return respuesta;
	}

}
