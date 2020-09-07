package pe.gob.oefa.apps.sinada.presentacion.rest.api;

import java.util.ArrayList;
import java.util.HashMap;
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
import pe.gob.oefa.apps.sinada.bean.maestros.Maestro;

import pe.gob.oefa.apps.sinada.presentacion.rest.api.bas.GenericController;

import pe.gob.oefa.apps.sinada.presentacion.util.ResourceUtil;
import pe.gob.oefa.apps.sinada.presentacion.util.VO;

import pe.gob.oefa.apps.sinada.servicio.inf.maestros.MaestroService;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value="/rest/api/maestro")
public class MaestroRest extends GenericController
{
	@Autowired
	MaestroService maestroService;
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/listarHijos", method=RequestMethod.GET)
	public RespuestaHttp buscar(@RequestParam(value="prmCriterio",required=false)String prmCriterio)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try
		{
			System.out.println("prmCriterio" + prmCriterio);
			Maestro prmMaestro = new ObjectMapper().readValue(prmCriterio, Maestro.class);
			List<Maestro> dataListaMaestro = (List<Maestro>) maestroService.listarHijos(prmMaestro);
			_RespuestaHttp.setValido(true);
			super.numerarItem(dataListaMaestro);
			_RespuestaHttp.setData(dataListaMaestro);
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
	@RequestMapping(value="/buscar-maestro", method=RequestMethod.GET)
	public RespuestaHttp BuscarXCodigoMaestro(@RequestParam(value="prmCriterio",required=false)String prmCriterio)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try
		{
			Maestro prmMaestro = new ObjectMapper().readValue(prmCriterio, Maestro.class);
			List<Maestro> maestro = (List<Maestro>) maestroService.buscarPorCodigoTabla(prmMaestro);
			_RespuestaHttp.setValido(true);
			super.numerarItem(maestro);
			_RespuestaHttp.setData(maestro);
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
	@RequestMapping(value="/buscarMaestros", method = RequestMethod.GET)
	@ResponseBody
	public RespuestaHttp buscarMaestros(@RequestParam("prmCriterio") String prmCriterio)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try {
			String[] prmStringListaMaestros = new ObjectMapper().readValue(prmCriterio, String[].class);
			List<String> lstExtensiones= new ArrayList<String>();
			List<String[]> lstMimeType= new ArrayList<String[]>();
			List<HashMap<String, Object>> mapListaMaestros = new ArrayList<>();
			if (prmStringListaMaestros!=null) 
			{
				for (int i = 0; i < prmStringListaMaestros.length; i++) 
				{
					List<Maestro> dataListaMaestro = new ArrayList<>();
					Maestro prmMaestro = new Maestro();
					prmMaestro.setCodigoMaestro(prmStringListaMaestros[i]);
					dataListaMaestro = (List<Maestro>)this.maestroService.listar(prmMaestro);
					if (dataListaMaestro!=null) 
					{
						HashMap<String, Object> mapItemMaestro = new HashMap<>();
						mapItemMaestro.put("Key", prmStringListaMaestros[i]);
						mapItemMaestro.put("Value", dataListaMaestro);
						if(prmStringListaMaestros[i].trim().equals("TipoArchivoDenuncia")){
							for(int j=0; j<dataListaMaestro.size();j++){
								lstExtensiones.add(VO.getExtensionesValidas(dataListaMaestro.get(j).getCodigoRegistro()));
								String mimeType = VO.getMimeTypeValidos(dataListaMaestro.get(j).getCodigoRegistro());
								lstMimeType.add(mimeType.split(","));
							}
							mapItemMaestro.put("extensiones", lstExtensiones);
							mapItemMaestro.put("mimetypes", lstMimeType);
						}
						
						
						mapListaMaestros.add(mapItemMaestro);
					}
				}
			}
			_RespuestaHttp.setValido(true);
			_RespuestaHttp.setData(mapListaMaestros);
			
		} catch (Exception e) {
			e.printStackTrace();
			_RespuestaHttp.setValido(false);
			_RespuestaHttp.setMensaje("Hubo un error al obtener la información de registros maestros.");
		}
		return _RespuestaHttp;
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/obtenerParametros", method = RequestMethod.GET)
	@ResponseBody
	public RespuestaHttp obtenerParametros()
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try {
			
			List<HashMap<String, Object>> mapListaMaestros = new ArrayList<>();
			
			
			HashMap<String, Object> mapItemMaestro = new HashMap<>();
			long peso = Long.parseLong(ResourceUtil.getKey("peso_maximo_archivo"));
			mapItemMaestro.put("tamanioArchivo", peso/1000 );
			mapItemMaestro.put("maximoCaracteresArchivo", ResourceUtil.getKey("maximo_caracteres_archivo") );
			mapItemMaestro.put("extensionArchivoDocumento", ResourceUtil.getKey("extension_archivo_documento") );
			mapItemMaestro.put("tipoArchivoDocumento", ResourceUtil.getKey("tipo_archivo_documento") );
			mapListaMaestros.add(mapItemMaestro);
			
			
			_RespuestaHttp.setValido(true);
			_RespuestaHttp.setData(mapListaMaestros);
			
		} catch (Exception e) {
			e.printStackTrace();
			_RespuestaHttp.setValido(false);
			_RespuestaHttp.setMensaje("Hubo un error al obtener la información de registros maestros.");
		}
		return _RespuestaHttp;
	}
	
	@ResponseBody
	@RequestMapping(value="/listar-codigo-maestro", method=RequestMethod.GET)
	public RespuestaHttp listarCodigoMaestro()
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try
		{
//			Maestro prmMaestro = new ObjectMapper().readValue(prmCriterio, Maestro.class);
			Maestro prmMaestro = new Maestro();
			List<Maestro> maestro = (List<Maestro>) maestroService.listarCodigoMaestro(prmMaestro);
			_RespuestaHttp.setValido(true);
			super.numerarItem(maestro);
			_RespuestaHttp.setData(maestro);
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
	@RequestMapping(value="/listar-registro-hijo", method=RequestMethod.GET)
	public RespuestaHttp listarRegistroHijo(@RequestParam(value="prmCriterio",required=false) String prmCriterio)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try
		{
			Maestro prmMaestro = new ObjectMapper().readValue(prmCriterio, Maestro.class);
			System.out.println(" prmMaestro.getCodigoRegistroPadre()" + prmMaestro.getCodigoRegistroPadre());
			System.out.println(" prmMaestro.getCodigoMaestro()" + prmMaestro.getCodigoMaestro());
			List<Maestro> maestro = (List<Maestro>) maestroService.listarRegistroHijo(prmMaestro);
			_RespuestaHttp.setValido(true);
			super.numerarItem(maestro);
			_RespuestaHttp.setData(maestro);
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
	public RespuestaHttp registrar(@RequestBody Maestro prmMaestro,HttpServletRequest request)throws Exception
	{
		RespuestaHttp respuesta = new RespuestaHttp();
		try
		{
			System.out.println(prmMaestro);
			long idGenerado = maestroService.insertar(prmMaestro);
			
			if(idGenerado>0)
			{
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha registrado el dato correctamente");
				respuesta.setData(prmMaestro.getIdMaestro());
			} 
			else 
			{
				respuesta.setMensaje("No se pudo registrar el campo");
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al registrar el campo");
		}
		return respuesta;
	}
	
	
	@ResponseBody
	@RequestMapping(value ="/actualizar", method = RequestMethod.POST)
	public  RespuestaHttp actualizar(@RequestBody Maestro prmMaestro,HttpServletRequest request)throws Exception {
		RespuestaHttp respuesta = new RespuestaHttp();
		try{
			int result = maestroService.actualizar(prmMaestro);
			
			if(result>0){
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha actualizado	la tabla satisfactoriamente");
			}else {
				respuesta.setMensaje("No se pudo actualizar la tabla");
			}
		}catch (Exception e) {
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al actualizar la tabla");
		}
		return respuesta;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/eliminar", method = RequestMethod.POST)
	public RespuestaHttp eliminar(
			@RequestBody Maestro prmMaestro,HttpServletRequest request)
			throws Exception{
		RespuestaHttp respuesta = new RespuestaHttp();
		try{
			int result = maestroService.eliminar(prmMaestro);
			if(result>0){
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha eliminado el campo de la tabla correctamente");
			}else {
				respuesta.setMensaje("No se pudo eliminar el campo de la tabla ");
			}
		} catch (Exception e){
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al eliminar el campo de la tabla ");
		}
		return respuesta;
	}
	
}
