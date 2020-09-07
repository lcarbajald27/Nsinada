package pe.gob.oefa.apps.sinada.presentacion.rest.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pe.gob.oefa.apps.base.presentacion.response.RespuestaHttp;
import pe.gob.oefa.apps.sinada.bean.maestros.CentroPoblado;
import pe.gob.oefa.apps.sinada.bean.maestros.Ubigeo;
import pe.gob.oefa.apps.sinada.servicio.inf.maestros.UbigeoService;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value="/rest/api/ubigeo")
public class UbigeoRest {
   
	@Autowired
	UbigeoService ubigeoService;
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="listar-departamento", method=RequestMethod.GET)
	public RespuestaHttp buscar(@RequestParam("prmCriterio")String prmCriterio)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try
		{
			List<Ubigeo> data = (List<Ubigeo>) ubigeoService.listarDepartamento();
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
	@RequestMapping(value="listar-provincia", method=RequestMethod.GET)
	public RespuestaHttp listarProvincia(@RequestParam("prmCriterio")String prmCriterio)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try
		{
			Ubigeo prmData = new ObjectMapper().readValue(prmCriterio, Ubigeo.class);
			List<Ubigeo> data = (List<Ubigeo>) ubigeoService.listarProvincia(prmData);
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
	@RequestMapping(value="listar-distrito", method=RequestMethod.GET)
	public RespuestaHttp listarDistrito(@RequestParam("prmCriterio")String prmCriterio)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try
		{
			Ubigeo prmData = new ObjectMapper().readValue(prmCriterio, Ubigeo.class);
			List<Ubigeo> data = (List<Ubigeo>) ubigeoService.listarDistrito(prmData);
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
	@RequestMapping(value="listar-centro-poblado", method=RequestMethod.GET)
	public RespuestaHttp listarCentroPoblado(@RequestParam("prmCriterio")String prmCriterio)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try
		{
			CentroPoblado prmData = new ObjectMapper().readValue(prmCriterio, CentroPoblado.class);
			List<CentroPoblado> data =  ubigeoService.listarCentroPoblado(prmData);
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
