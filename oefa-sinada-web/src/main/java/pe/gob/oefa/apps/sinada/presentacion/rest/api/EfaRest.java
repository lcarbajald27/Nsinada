package pe.gob.oefa.apps.sinada.presentacion.rest.api;


import java.util.ArrayList;
import java.util.HashMap;
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
import pe.gob.oefa.apps.sinada.bean.sirefa.Efa;
import pe.gob.oefa.apps.sinada.servicio.inf.sirefa.EfaService;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value="/rest/api/efa")
public class EfaRest
{
	@Autowired
	EfaService efaService;
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/listar", method=RequestMethod.GET)
	public RespuestaHttp buscar(@RequestParam(value="prmCriterio",required=false)String prmCriterio)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try
		{
			System.out.println("EFA REST!!!");
			Efa prmEfa = new ObjectMapper().readValue(prmCriterio, Efa.class);
			List<Efa> efas = (List<Efa>) efaService.listar(prmEfa);
			List<HashMap<String, Object>> mapListaEfa = new ArrayList<>();
			if (efas!=null) 
			{
				for (Efa bean : efas) 
				{
					HashMap<String, Object> mapItemEfa = new HashMap<>();
					mapItemEfa.put("Key", bean.getIdEfa());
					mapItemEfa.put("Value", bean);
					mapListaEfa.add(mapItemEfa);
				}
				System.out.println("contactos.size() "+ efas.size());
			}
			_RespuestaHttp.setValido(true);
			_RespuestaHttp.setData(mapListaEfa);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			_RespuestaHttp.setValido(false);
			_RespuestaHttp.setMensaje("Hubo un error al procesar la información");
		}
		return _RespuestaHttp;
	}


//	@ResponseBody
//	@RequestMapping(value = "/registrar",method = RequestMethod.POST)
//	public RespuestaHttp registrar(@RequestBody Efa prmEfa,HttpServletRequest request)throws Exception
//	{
//		RespuestaHttp respuesta = new RespuestaHttp();
//		try
//		{
//			System.out.println(prmEfa);
//			long idGenerado = efaService.insertar(prmEfa);
//			
//			if(idGenerado>0)
//			{
//				respuesta.setValido(true);
//				respuesta.setMensaje("Se ha registrado la efa satisfactoriamente");
//				respuesta.setData(prmEfa.getIdEfa());
//			} 
//			else 
//			{
//				respuesta.setMensaje("No se pudo registrar la efa");
//			}
//		} 
//		catch (Exception e) 
//		{
//			e.printStackTrace();
//			respuesta.setMensaje("Hubo un error al registrar la efa");
//		}
//		return respuesta;
//	}
	
//	@ResponseBody
//	@RequestMapping(value ="/actualizar", method = RequestMethod.POST)
//	public  RespuestaHttp actualizar(@RequestBody Efa prmEfa,HttpServletRequest request)throws Exception {
//		RespuestaHttp respuesta = new RespuestaHttp();
//		try{
//			int result = efaService.actualizar(prmEfa);
//			
//			if(result>0){
//				respuesta.setValido(true);
//				respuesta.setMensaje("Se ha actualizado	la efa satisfactoriamente");
//			}else {
//				respuesta.setMensaje("No se pudo actualizar la efa");
//			}
//		}catch (Exception e) {
//			e.printStackTrace();
//			respuesta.setMensaje("Hubo un error al actualizar la efa");
//		}
//		return respuesta;
//	}
//	
//	@ResponseBody
//	@RequestMapping(value = "/eliminar", method = RequestMethod.POST)
//	public RespuestaHttp eliminar(
//			@RequestBody Efa prmEfa,HttpServletRequest request)
//			throws Exception{
//		RespuestaHttp respuesta = new RespuestaHttp();
//		try{
//			int result = efaService.eliminar(prmEfa);
//			if(result>0){
//				respuesta.setValido(true);
//				respuesta.setMensaje("Se ha eliminado la efa correctamente");
//			}else {
//				respuesta.setMensaje("No se pudo eliminar la efa");
//			}
//		} catch (Exception e){
//			e.printStackTrace();
//			respuesta.setMensaje("Hubo un error al eliminar la efa");
//		}
//		return respuesta;
//	}
//	
	@ResponseBody
	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
	public RespuestaHttp buscar(@PathVariable("id")long idProducto)throws Exception 
	{
		RespuestaHttp respuesta = new  RespuestaHttp();
		try
		{
			Efa prmEfa = new Efa();
			prmEfa = efaService.buscarPorId(idProducto);
			
			respuesta.setValido(true);
			respuesta.setData(prmEfa);
		} 
		catch (Exception e)
		{
			respuesta.setMensaje("Hubo un error al obtener la efa");
			e.printStackTrace();
		}
		return respuesta;
	}
	
	
	
	/**************************************    Evaluacion Rechazo - Derivar Denuncia   ************************************/
		
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/listar-efas-derivar", method=RequestMethod.POST)
	public RespuestaHttp listarEfasDerivar(@RequestBody Efa prmData,HttpServletRequest request)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try
		{
			System.out.println("EFA REST!!!");

			List<Efa> data = (List<Efa>) efaService.listarEfaDerivar(prmData);
			
			
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
}
