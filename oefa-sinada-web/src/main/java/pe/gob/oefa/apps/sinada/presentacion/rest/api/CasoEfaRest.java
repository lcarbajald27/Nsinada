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
import pe.gob.oefa.apps.sinada.bean.proceso.CasoEfa;
import pe.gob.oefa.apps.sinada.bean.sirefa.Efa;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.CasoEfaService;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value="/rest/api/casoEfa")
public class CasoEfaRest
{
	@Autowired
	CasoEfaService casoEfaService;
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/listar", method=RequestMethod.GET)
	public RespuestaHttp buscar(@RequestParam(value="prmCriterio",required=false)String prmCriterio)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try
		{
			CasoEfa prmCasoEfa = new ObjectMapper().readValue(prmCriterio, CasoEfa.class);
			List<CasoEfa> casoEfa = (List<CasoEfa>) casoEfaService.listar(prmCasoEfa);
			_RespuestaHttp.setValido(true);
			_RespuestaHttp.setData(casoEfa);
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
	@RequestMapping(value="/listar-efas-caso", method=RequestMethod.GET)
	public RespuestaHttp listarEfasXCaso(@RequestParam(value="prmCriterio",required=false)String prmCriterio)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try
		{
			CasoEfa prmCasoEfa = new ObjectMapper().readValue(prmCriterio, CasoEfa.class);
			List<CasoEfa> casoEfa = (List<CasoEfa>) casoEfaService.listarEfasXCaso(prmCasoEfa);
			_RespuestaHttp.setValido(true);
			_RespuestaHttp.setData(casoEfa);
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
	public RespuestaHttp registrar(@RequestBody CasoEfa prmCasoEfa,HttpServletRequest request)throws Exception
	{
		RespuestaHttp respuesta = new RespuestaHttp();
		try
		{
		
			prmCasoEfa.setHostIp(UtilRequest.getClientIpAddress(request));
			long idGenerado = casoEfaService.insertar(prmCasoEfa);
			
			if(idGenerado>0)
			{
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha registrado la entidad satisfactoriamente");
				respuesta.setData(prmCasoEfa.getIdCasoEfa());
			} 
			else 
			{
				respuesta.setMensaje("No se pudo registrar la entidad");
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al registrar la entidad");
		}
		return respuesta;
	}
	
	@SuppressWarnings("unused")
	@ResponseBody
	@RequestMapping(value = "/registrar-lista-efa",method = RequestMethod.POST)
	public RespuestaHttp registrarListaEfa(@RequestBody CasoEfa prmCasoEfa,HttpServletRequest request)throws Exception
	{
		RespuestaHttp respuesta = new RespuestaHttp();
		try
		{
			
			for (Efa efa : prmCasoEfa.getLstEfa()) {
				CasoEfa dataCasoEfa = new CasoEfa();
				dataCasoEfa.setEfa(efa);
				dataCasoEfa.setCaso(prmCasoEfa.getCaso());
				dataCasoEfa.setTipoAsignacion(prmCasoEfa.getTipoAsignacion());
				
				/***/
				dataCasoEfa.setPrm1(prmCasoEfa.getPrm1());
				dataCasoEfa.setHostIp(UtilRequest.getClientIpAddress(request));
				
				long idGenerado = casoEfaService.insertar(dataCasoEfa);
				
			}
		
			
			
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha registrado las entidades satisfactoriamente");
				respuesta.setData(prmCasoEfa.getIdCasoEfa());
			 
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al registrar la Entidad");
		}
		return respuesta;
	}
	
	@ResponseBody
	@RequestMapping(value ="/actualizar", method = RequestMethod.POST)
	public  RespuestaHttp actualizar(@RequestBody CasoEfa prmCasoEfa,HttpServletRequest request)throws Exception {
		RespuestaHttp respuesta = new RespuestaHttp();
		try{
			prmCasoEfa.setHostIp(UtilRequest.getClientIpAddress(request));
			int result = casoEfaService.actualizar(prmCasoEfa);
			
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
	public RespuestaHttp eliminar(@RequestBody CasoEfa prmCasoEfa,HttpServletRequest request)
			throws Exception{
		RespuestaHttp respuesta = new RespuestaHttp();
		try{
			
			prmCasoEfa.setHostIp(UtilRequest.getClientIpAddress(request));
			int result = casoEfaService.eliminar(prmCasoEfa);
			if(result>0)
			{
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha eliminado la entidad asociada a este caso.");
			}
			else 
			{
				respuesta.setMensaje("No se pudo eliminar la entidad asociada a este caso.");
			}
		} catch (Exception e){
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al eliminar la entidad asociada a este caso.");
		}
		return respuesta;
	}
	
//	@ResponseBody
//	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
//	public RespuestaHttp buscarxNumeroNorma(@PathVariable("id")Long idEntidad)throws Exception 
//	{
//		RespuestaHttp respuesta = new  RespuestaHttp();
//		try
//		{
//			Entidad prmEntidad = new Entidad();
//			prmEntidad.setIdEntidad(idEntidad);
//			prmEntidad = casoEfaService.buscarPorId(prmEntidad.getIdEntidad());
//			
//			respuesta.setValido(true);
//			respuesta.setData(prmEntidad);
//		} 
//		catch (Exception e)
//		{
//			respuesta.setMensaje("Hubo un error al obtener la Entidad");
//			e.printStackTrace();
//		}
//		return respuesta;
//	}

}
