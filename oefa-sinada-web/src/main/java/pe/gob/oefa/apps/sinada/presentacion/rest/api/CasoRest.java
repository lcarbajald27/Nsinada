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
import pe.gob.oefa.apps.sinada.bean.proceso.Caso;
import pe.gob.oefa.apps.sinada.bean.proceso.view.EntidadCaso;
import pe.gob.oefa.apps.sinada.presentacion.util.GeneradorCodigo;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.CasoService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.view.EntidadCasoService;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value="/rest/api/caso")
public class CasoRest
{
	@Autowired
	CasoService casoService;
	
	@Autowired
	private EntidadCasoService entidadCasoService;
		
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/listar", method=RequestMethod.GET)
	public RespuestaHttp buscar(@RequestParam(value="prmCriterio",required=false)String prmCriterio)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try
		{
			Caso prmData = new ObjectMapper().readValue(prmCriterio, Caso.class);
			List<Caso> data = (List<Caso>) casoService.listar(prmData);
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
	@RequestMapping(value="/listar-entidad-caso", method=RequestMethod.GET)
	public RespuestaHttp listarEntidadCaso(@RequestParam(value="prmCriterio",required=false)String prmCriterio)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try
		{
			EntidadCaso prmData = new ObjectMapper().readValue(prmCriterio, EntidadCaso.class);
			List<EntidadCaso> data = (List<EntidadCaso>) entidadCasoService.listar(prmData);
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
	public RespuestaHttp registrar(@RequestBody Caso prmData,HttpServletRequest request)throws Exception
	{
		RespuestaHttp respuesta = new RespuestaHttp();
		try
		{
			Caso prmValidaCaso = new Caso();
			long idGenerado = 0;
			prmValidaCaso = casoService.validaCasoRegistrado(prmData);
			if(prmValidaCaso==null){
					
			//	GeneradorCodigo n = new GeneradorCodigo();
		//		long idCaso = casoService.generarCodigoCaso();
	//			prmData.setIdCaso(idCaso);
	//			prmData.setNumeroCaso(numeroCaso);
//				prmData.setNumeroCaso(n.generarCodigo(10, "CAS", String.valueOf(idCaso)));
//				
				
				prmData.setHostIp(UtilRequest.getClientIpAddress(request));
				
				
				 idGenerado = casoService.insertar(prmData);
				
			}
		
			
			if(idGenerado>0)
			{
				respuesta.setValido(true);
				
				
				/*+casoService.buscarPorId(idGenerado).getNumeroCaso()*/
								
				respuesta.setMensaje("Se ha registrado la información satisfactoriamente");
				respuesta.setData(casoService.buscarPorId(idGenerado).getNumeroCaso());
			} 
			else 
			{
				respuesta.setMensaje("El caso que intenta registrar ya existe");
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al registrar la información");
			
		}
		return respuesta;
	}
	
	@ResponseBody
	@RequestMapping(value ="/actualizar", method = RequestMethod.POST)
	public  RespuestaHttp actualizar(@RequestBody Caso prmData,HttpServletRequest request)throws Exception {
		RespuestaHttp respuesta = new RespuestaHttp();
		try{
			
			prmData.setHostIp(UtilRequest.getClientIpAddress(request));
			int result = casoService.actualizar(prmData);
			
			if(result>0){
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha actualizado	el caso satisfactoriamente");
			}else {
				respuesta.setMensaje("No se pudo actualizar el caso");
			}
		}catch (Exception e) {
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al actualizar el caso");
		}
		return respuesta;
	}
	
	@ResponseBody
	@RequestMapping(value = "/eliminar", method = RequestMethod.POST)
	public RespuestaHttp eliminar(
			@RequestBody Caso prmData,HttpServletRequest request)
			throws Exception{
		RespuestaHttp respuesta = new RespuestaHttp();
		try{
			prmData.setHostIp(UtilRequest.getClientIpAddress(request));
			int result = casoService.eliminar(prmData);
			
			if(result>0){
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha eliminado la información correctamente");
			}else {
				respuesta.setMensaje("No se pudo eliminar la información");
			}
		} catch (Exception e){
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al eliminar la información");
		}
		return respuesta;
	}
	
	@ResponseBody
	@RequestMapping(value = "/buscarPorId",method = RequestMethod.GET)
	public RespuestaHttp buscarxNumeroNorma(@RequestParam(value="prmCriterio",required=false)String prmCriterio)throws Exception 
	{
		RespuestaHttp respuesta = new  RespuestaHttp();
		try
		{
			Caso prmData = new ObjectMapper().readValue(prmCriterio, Caso.class);
			
			prmData = casoService.buscarPorId(prmData.getIdCaso());
			
			respuesta.setValido(true);
			respuesta.setData(prmData);
		} 
		catch (Exception e)
		{
			respuesta.setMensaje("Hubo un error al obtener la información");
			e.printStackTrace();
		}
		return respuesta;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/valida-caso-registrado",method = RequestMethod.POST)
	public RespuestaHttp validaCasoRegistrado(@RequestBody Caso prmData,HttpServletRequest request)throws Exception
	{
		RespuestaHttp respuesta = new RespuestaHttp();
		try
		{
			
			
			 prmData = casoService.validaCasoRegistrado(prmData);
			
			if(prmData!=null)
			{
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha encontrado el caso");
				respuesta.setData(prmData);
			} 
			else 
			{
				respuesta.setMensaje("No se encontro el caso");
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al registrar la información");
		}
		return respuesta;
	}

}
