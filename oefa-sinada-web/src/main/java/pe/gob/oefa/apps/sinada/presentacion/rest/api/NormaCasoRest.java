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
import pe.gob.oefa.apps.sinada.bean.proceso.NormaCaso;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.NormaCasoService;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value="/rest/api/normaCaso")
public class NormaCasoRest
{
	@Autowired
	NormaCasoService normaCasoService;
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/listar", method=RequestMethod.GET)
	public RespuestaHttp buscar(@RequestParam(value="prmCriterio",required=false)String prmCriterio)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try
		{
			NormaCaso prmNormaCaso = new ObjectMapper().readValue(prmCriterio, NormaCaso.class);
			List<NormaCaso> normaCaso = (List<NormaCaso>) normaCasoService.listar(prmNormaCaso);
			_RespuestaHttp.setValido(true);
			_RespuestaHttp.setData(normaCaso);
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
	public RespuestaHttp registrar(@RequestBody NormaCaso prmNormaCaso,HttpServletRequest request)throws Exception
	{
		RespuestaHttp respuesta = new RespuestaHttp();
		try
		{
			System.out.println(prmNormaCaso);
			prmNormaCaso.setHostIp(UtilRequest.getClientIpAddress(request));
			long idGenerado = normaCasoService.insertar(prmNormaCaso);
			
			if(idGenerado>0)
			{
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha registrado la norma satisfactoriamente");
				respuesta.setData(prmNormaCaso.getIdNormaCaso());
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
	public  RespuestaHttp actualizar(@RequestBody NormaCaso prmNormaCaso,HttpServletRequest request)throws Exception {
		RespuestaHttp respuesta = new RespuestaHttp();
		try{
			prmNormaCaso.setHostIp(UtilRequest.getClientIpAddress(request));
			int result = normaCasoService.actualizar(prmNormaCaso);
			
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
			@RequestBody NormaCaso prmNormaCaso,HttpServletRequest request)
			throws Exception{
		RespuestaHttp respuesta = new RespuestaHttp();
		try{
			prmNormaCaso.setHostIp(UtilRequest.getClientIpAddress(request));
			int result = normaCasoService.eliminar(prmNormaCaso);
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
	

}
