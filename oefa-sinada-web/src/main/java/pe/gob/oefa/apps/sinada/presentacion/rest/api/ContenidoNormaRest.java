package pe.gob.oefa.apps.sinada.presentacion.rest.api;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pe.gob.oefa.apps.base.presentacion.response.RespuestaHttp;
import pe.gob.oefa.apps.sinada.bean.sirin.ContenidoNorma;
import pe.gob.oefa.apps.sinada.servicio.inf.sirin.ContenidoNormaService;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value="/rest/api/contenidonorma")
public class ContenidoNormaRest
{
	@Autowired
	ContenidoNormaService contenidoNormaService;
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/listar", method=RequestMethod.GET)
	public RespuestaHttp buscar(@RequestParam(value="prmCriterio",required=false)String prmCriterio)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try
		{
			ContenidoNorma prmContenidoNorma = new ObjectMapper().readValue(prmCriterio, ContenidoNorma.class);
			List<ContenidoNorma> contenidoNorma = (List<ContenidoNorma>) contenidoNormaService.listar(prmContenidoNorma);
			_RespuestaHttp.setValido(true);
			_RespuestaHttp.setData(contenidoNorma);
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
	@RequestMapping(value = "buscarPorId/{id}",method = RequestMethod.GET)
	public RespuestaHttp buscarxNumeroNorma(@PathVariable("id")Long idContenidoNorma)throws Exception 
	{
		RespuestaHttp respuesta = new  RespuestaHttp();
		try
		{
			ContenidoNorma prmContenidoNorma = new ContenidoNorma();
			prmContenidoNorma.setIdContenidoNorma(idContenidoNorma);
			prmContenidoNorma = contenidoNormaService.buscarPorId(prmContenidoNorma.getIdContenidoNorma());
			
			respuesta.setValido(true);
			respuesta.setData(prmContenidoNorma);
		} 
		catch (Exception e)
		{
			respuesta.setMensaje("Hubo un error al obtener la contenido");
			e.printStackTrace();
		}
		return respuesta;
	}

}
