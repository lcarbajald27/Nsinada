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
import pe.gob.oefa.apps.sinada.bean.proceso.CasoOefa;
import pe.gob.oefa.apps.sinada.bean.proceso.PersonaOefa;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.CasoOefaService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.PersonaOefaService;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value="/rest/api/casoOefa")
public class CasoOefaRest
{
	@Autowired
	CasoOefaService casoOefaService;
	
	@Autowired
	PersonaOefaService personaOefaService;
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/listar", method=RequestMethod.GET)
	public RespuestaHttp buscar(@RequestParam(value="prmCriterio",required=false)String prmCriterio)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try
		{
			CasoOefa prmCasoOefa = new ObjectMapper().readValue(prmCriterio, CasoOefa.class);
			List<CasoOefa> casoOefa = (List<CasoOefa>) casoOefaService.listarEfasXCaso(prmCasoOefa);
			_RespuestaHttp.setValido(true);
			_RespuestaHttp.setData(casoOefa);
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
			CasoOefa prmCasoOefa = new ObjectMapper().readValue(prmCriterio, CasoOefa.class);
			List<CasoOefa> casoOefa = (List<CasoOefa>) casoOefaService.listarEfasXCaso(prmCasoOefa);
			_RespuestaHttp.setValido(true);
			_RespuestaHttp.setData(casoOefa);
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
	public RespuestaHttp registrar(@RequestBody CasoOefa prmCasoOefa,HttpServletRequest request)throws Exception
	{
		RespuestaHttp respuesta = new RespuestaHttp();
		try
		{
			PersonaOefa prmDataPersonaOEfa = new PersonaOefa();
			prmDataPersonaOEfa.getDireccion().setCodigoRegistro(prmCasoOefa.getDireccionSupervision().getCodigoRegistro());
			prmDataPersonaOEfa.getSubDireccion().setCodigoRegistro(prmCasoOefa.getDireccionEvaluacion().getCodigoRegistro());
			
			List<PersonaOefa> data = (List<PersonaOefa>) personaOefaService.listar(prmDataPersonaOEfa);
			
			if(data==null || data.size()==0){
				respuesta.setMensaje("La OEFA no tiene usuarios agregados.");
				return respuesta;
			}
			
			CasoOefa validaCasoOefa = casoOefaService.buscarPorIdCasoDireccionAndSubDireccion(prmCasoOefa);
			long idGenerado = 0;
			if(validaCasoOefa==null){
				prmCasoOefa.setHostIp(UtilRequest.getClientIpAddress(request));
				idGenerado = casoOefaService.insertar(prmCasoOefa);
			}
			
			
			if(idGenerado>0)
			{
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha registado la OEFA satisfactoriamente");
				respuesta.setData(prmCasoOefa.getIdCasoOefa());
			} 
			else 
			{
				respuesta.setMensaje("La OEFA ya se encuentra registrada.");
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al registrar la OEFA");
		}
		return respuesta;
	}
	
	@ResponseBody
	@RequestMapping(value ="/actualizar", method = RequestMethod.POST)
	public  RespuestaHttp actualizar(@RequestBody CasoOefa prmCasoOefa,HttpServletRequest request)throws Exception {
		RespuestaHttp respuesta = new RespuestaHttp();
		try{
			prmCasoOefa.setHostIp(UtilRequest.getClientIpAddress(request));
			int result = casoOefaService.actualizar(prmCasoOefa);
			
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
	public RespuestaHttp eliminar(@RequestBody CasoOefa prmCasoOefa,HttpServletRequest request)
			throws Exception{
		RespuestaHttp respuesta = new RespuestaHttp();
		try
		{
			prmCasoOefa.setHostIp(UtilRequest.getClientIpAddress(request));
			int result = casoOefaService.eliminar(prmCasoOefa);
			if(result>0)
			{
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha eliminado la OEFA correctamente");
			}
			else 
			{
				respuesta.setMensaje("No se pudo eliminar la OEFA");
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al eliminar la OEFA");
		}
		return respuesta;
	}
	
//	@ResponseBody
//	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
//	public RespuestaHttp buscarxNumeroNorma(@PathVariable("id")Long idOefa)throws Exception 
//	{
//		RespuestaHttp respuesta = new  RespuestaHttp();
//		try
//		{
//			Oefa prmOefa = new Oefa();
//			prmOefa.setIdOefa(idOefa);
//			prmOefa = casoOefaService.buscarPorId(prmOefa.getIdOefa());
//			
//			respuesta.setValido(true);
//			respuesta.setData(prmOefa);
//		} 
//		catch (Exception e)
//		{
//			respuesta.setMensaje("Hubo un error al obtener la Oefa");
//			e.printStackTrace();
//		}
//		return respuesta;
//	}

}
