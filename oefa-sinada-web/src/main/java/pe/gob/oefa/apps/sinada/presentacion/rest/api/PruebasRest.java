package pe.gob.oefa.apps.sinada.presentacion.rest.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import pe.gob.oefa.apps.base.presentacion.response.RespuestaHttp;
import pe.gob.oefa.apps.sinada.presentacion.util.AlfrescoUtil;
import pe.gob.oefa.apps.sinada.presentacion.util.ResourceUtil;
import pe.gob.oefa.apps.sinada.presentacion.util.VO;

@Controller
@RequestMapping("/rest/api/pruebas")
public class PruebasRest {
	
	@ResponseBody
	@RequestMapping(value = "/subir-archivo", method = RequestMethod.POST)
	public RespuestaHttp subirArchivo(@RequestParam("archivo") MultipartFile[] archivo, @RequestParam("tipo")int tipo)
	{
		RespuestaHttp respuesta = new RespuestaHttp();
		
		try {
			long idGenerado = 23;//Codigo de Denuncia Prueba
			//String folder = ResourceUtil.getKey("file.path.informe-atencion");
			String separador = ResourceUtil.getKey("file.path.separador");
			
			String folder = "";
			
			//String folder = "videos" + separador + "{id}"+ separador;
			switch (tipo) {
				case 1:
					folder = "imagenes" + separador + "{id}"+ separador;
					break;
				case 2:
					folder = "videos" + separador + "{id}"+ separador;
					break;
				case 3:
					folder = "audios" + separador + "{id}"+ separador;
					break;
				case 4:
					folder = "documentos" + separador + "{id}"+ separador;
					break;
				case 5:
					folder = "otros" + separador + "{id}"+ separador;
					break;
				default:
					break;
			}
			
			folder = folder.replace("{id}", String.valueOf(idGenerado));
			
			//String flagAlfresco = ResourceUtil.getKey("file.alfreso");
			
			/** EL SIGUIENTE CODIGO ES LA INTEGRACION CON WSALFRESCO **/
			String uiid = AlfrescoUtil.grabarArchivoAlfresco(archivo[0], folder);
			
			if (VO.isNullOrEmpty(uiid))
			{
				respuesta.setMensaje("No se pudo archivar el documento en Alfresco.");
				return respuesta;
			}
			if ("error".equals(uiid))
			{
				respuesta.setMensaje("Error al subir documento a Alfresco.");
			}
			else
			{
				respuesta.setMensaje("Archivo grabado correctamente");
				respuesta.setData(uiid);
				respuesta.setValido(true);
			}
			//itemArchivo.setUiid(uiid);
			
		} catch (Exception e) {
			respuesta.setMensaje(e.getMessage());
		}
		
		return respuesta;
	}
	
}
