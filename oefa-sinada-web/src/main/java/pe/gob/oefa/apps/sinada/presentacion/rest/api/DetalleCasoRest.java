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
import pe.gob.oefa.apps.sinada.bean.proceso.DetalleCaso;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.DetalleCasoService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.NormaCasoService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.view.EntidadCasoService;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value="/rest/api/detalle-caso")
public class DetalleCasoRest
{
	@Autowired
	DetalleCasoService detalleCasoService;
	
	@Autowired
	EntidadCasoService entidadCasoService;
	
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
			DetalleCaso prmCasos = new ObjectMapper().readValue(prmCriterio, DetalleCaso.class);
			List<DetalleCaso> casos = (List<DetalleCaso>) detalleCasoService.listar(prmCasos);
			_RespuestaHttp.setValido(true);
			_RespuestaHttp.setData(casos);
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
	@RequestMapping(value="/listar-detalle-caso-registrado", method=RequestMethod.GET)
	public RespuestaHttp listarDetalleCasoRegistrado(@RequestParam(value="prmCriterio",required=false)String prmCriterio)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try
		{
			DetalleCaso prmCasos = new ObjectMapper().readValue(prmCriterio, DetalleCaso.class);
			List<DetalleCaso> casos = (List<DetalleCaso>) detalleCasoService.listarDetalleCasoRegistradosDenuncia(prmCasos);
			_RespuestaHttp.setValido(true);
			_RespuestaHttp.setData(casos);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			_RespuestaHttp.setValido(false);
			_RespuestaHttp.setMensaje("Hubo un error al procesar la información");
		}
		return _RespuestaHttp;
	}

	
//	@SuppressWarnings("unchecked")
//	@ResponseBody
//	@RequestMapping(value="/listar-entidad-caso", method=RequestMethod.GET)
//	public RespuestaHttp listarEntidadCaso(@RequestParam(value="prmCriterio",required=false)String prmCriterio)
//	{
//		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
//		try
//		{
//			EntidadCaso prmEntidadCaso = new ObjectMapper().readValue(prmCriterio, EntidadCaso.class);
//			List<EntidadCaso> entidadCaso = (List<EntidadCaso>) entidadCasoService.listar(prmEntidadCaso);
//			
//			if(entidadCaso!=null)
//			{
//				for(int i =0; i<entidadCaso.size();i++)
//				{
//					if(entidadCaso.get(i).getTipoEntidad()==2)
//					{
//						NormaCaso prmNorma = new NormaCaso();
//						prmNorma.setIdCasoEfa(entidadCaso.get(i).getIdCasoEntidad());
//						List<NormaCaso> normaCaso = (List<NormaCaso>) normaCasoService.listar(prmNorma);
//						entidadCaso.get(i).setLstNormasCaso(normaCaso);
//					}
//				}
//			}
//			_RespuestaHttp.setValido(true);
//			_RespuestaHttp.setData(entidadCaso);
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//			_RespuestaHttp.setValido(false);
//			_RespuestaHttp.setMensaje("Hubo un error al procesar la información");
//		}
//		return _RespuestaHttp;
//	}

	@ResponseBody
	@RequestMapping(value = "/registrar",method = RequestMethod.POST)
	public RespuestaHttp registrar(@RequestBody DetalleCaso prmCasos,HttpServletRequest request)throws Exception
	{
		RespuestaHttp respuesta = new RespuestaHttp();
		try
		{
			DetalleCaso detalleCasoValida = detalleCasoService.buscarPorTipoCasoCodigoPadreNivelAndDescripcion(prmCasos);
			long idGenerado = 0;
			if(detalleCasoValida==null){
				prmCasos.setHostIp(UtilRequest.getClientIpAddress(request));
				idGenerado = detalleCasoService.insertar(prmCasos);
			}
			
			
			if(idGenerado>0)
			{
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha registrado la información satisfactoriamente");
				respuesta.setData(prmCasos.getIdDetalleCaso());
			} 
			else 
			{
				respuesta.setMensaje("El ítem ingresado ya se encuentra registrado.");
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al registar la información");
		}
		return respuesta;
	}
	
	@ResponseBody
	@RequestMapping(value ="/actualizar", method = RequestMethod.POST)
	public  RespuestaHttp actualizar(@RequestBody DetalleCaso prmCasos,HttpServletRequest request)throws Exception {
		RespuestaHttp respuesta = new RespuestaHttp();
		try{
			
			DetalleCaso detalleCasoValida = detalleCasoService.buscarPorTipoCasoCodigoPadreNivelAndDescripcion(prmCasos);
			long result = 0;
			if(detalleCasoValida==null){
				prmCasos.setHostIp(UtilRequest.getClientIpAddress(request));
				 result = detalleCasoService.actualizar(prmCasos);
			}
		
			
			if(result>0){
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha actualizado	el caso satisfactoriamente.");
			}else {
				respuesta.setMensaje("El ítem ingresado ya se encuentra registrado.");
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
			@RequestBody DetalleCaso prmCasos,HttpServletRequest request)
			throws Exception{
		RespuestaHttp respuesta = new RespuestaHttp();
		try{
			prmCasos.setHostIp(UtilRequest.getClientIpAddress(request));
			int result = detalleCasoService.eliminar(prmCasos);
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
	public RespuestaHttp buscarXId(@RequestParam(value="prmCriterio",required=false)String prmCriterio)throws Exception 
	{
		RespuestaHttp respuesta = new  RespuestaHttp();
		try
		{
			DetalleCaso prmCasos = new ObjectMapper().readValue(prmCriterio, DetalleCaso.class);
			
			prmCasos = detalleCasoService.buscarPorId(prmCasos.getIdDetalleCaso());
			
			respuesta.setValido(true);
			respuesta.setData(prmCasos);
		} 
		catch (Exception e)
		{
			respuesta.setMensaje("Hubo un error al obtener la información");
			e.printStackTrace();
		}
		return respuesta;
	}

}
