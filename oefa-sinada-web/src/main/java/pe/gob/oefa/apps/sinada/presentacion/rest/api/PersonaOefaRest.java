package pe.gob.oefa.apps.sinada.presentacion.rest.api;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pe.gob.oefa.apps.base.presentacion.response.RespuestaHttp;
import pe.gob.oefa.apps.base.util.UtilRequest;
import pe.gob.oefa.apps.sinada.bean.proceso.PersonaOefa;
import pe.gob.oefa.apps.sinada.bean.seguridad.Usuario;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.PersonaOefaService;
import pe.gob.oefa.apps.sinada.servicio.inf.seguridad.UsuarioService;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value="/rest/api/persona-oefa")
public class PersonaOefaRest
{
	@Autowired
	PersonaOefaService personaOefaService;
	
	@Autowired
	UsuarioService usuarioService;
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/listar", method=RequestMethod.GET)
	public RespuestaHttp buscar(@RequestParam(value="prmCriterio",required=false)String prmCriterio)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try
		{
			PersonaOefa prmData = new ObjectMapper().readValue(prmCriterio, PersonaOefa.class);
			List<PersonaOefa> data = (List<PersonaOefa>) personaOefaService.listar(prmData);
			_RespuestaHttp.setValido(true);
			_RespuestaHttp.setData(data);
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
	@RequestMapping(value="/listar-usuario-sinada", method=RequestMethod.GET)
	public RespuestaHttp listarUsuarioSinada(@RequestParam(value="prmCriterio",required=false)String prmCriterio)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try
		{
			Usuario prmData = new ObjectMapper().readValue(prmCriterio, Usuario.class);
			
			List<Usuario> data = (List<Usuario>) usuarioService.listarUsuariosSinada(prmData);
			
			_RespuestaHttp.setValido(true);
			_RespuestaHttp.setData(data);
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
	public RespuestaHttp registrar(@RequestBody PersonaOefa prmData,HttpServletRequest request)throws Exception
	{
		RespuestaHttp respuesta = new RespuestaHttp();
		try
		{
			prmData.setHostIp(UtilRequest.getClientIpAddress(request));
			long idGenerado = personaOefaService.insertar(prmData);
			
			if(idGenerado>0)
			{
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha asignado a la persona al oefa");
				respuesta.setData(prmData.getIdPersonaOefa());
			} 
			else 
			{
				respuesta.setMensaje("No se pudo asignar la persona a la OEFA");
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al asignar la persona al OEFA");
		}
		return respuesta;
	}
	
//	@ResponseBody
//	@RequestMapping(value ="/actualizar", method = RequestMethod.POST)
//	public  RespuestaHttp actualizar(@RequestBody PersonaOefa prmData,HttpServletRequest request)throws Exception {
//		RespuestaHttp respuesta = new RespuestaHttp();
//		try{
//			int result = personaOefaService.actualizar(prmData);
//			
//			if(result>0){
//				respuesta.setValido(true);
//				respuesta.setMensaje("Se ha actualizado	la norma satisfactoriamente");
//			}else {
//				respuesta.setMensaje("No se pudo actualizar la Norma");
//			}
//		}catch (Exception e) {
//			e.printStackTrace();
//			respuesta.setMensaje("Hubo un error al actualizar la Norma");
//		}
//		return respuesta;
//	}
	
	@ResponseBody
	@RequestMapping(value = "/eliminar", method = RequestMethod.POST)
	public RespuestaHttp eliminar(
			@RequestBody PersonaOefa prmData,HttpServletRequest request)
			throws Exception{
		RespuestaHttp respuesta = new RespuestaHttp();
		try{
			prmData.setHostIp(UtilRequest.getClientIpAddress(request));
			int result = personaOefaService.eliminar(prmData);
			if(result>0){
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha eliminado el registro correctamente");
			}else {
				respuesta.setMensaje("No se pudo eliminar el registro");
			}
		} catch (Exception e){
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al eliminar el registro");
		}
		return respuesta;
	}
	

}
