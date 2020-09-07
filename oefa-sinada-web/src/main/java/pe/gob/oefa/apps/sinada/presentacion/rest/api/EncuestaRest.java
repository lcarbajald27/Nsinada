package pe.gob.oefa.apps.sinada.presentacion.rest.api;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pe.gob.oefa.apps.base.presentacion.response.RespuestaHttp;
import pe.gob.oefa.apps.sinada.bean.proceso.Encuesta;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.EncuestaService;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value="/rest/api/encuesta")
public class EncuestaRest
{
	@Autowired
	private EncuestaService encuestaService;
	
	private List<Encuesta> lstEncuesta = null;
	
	// Excel work book
	private HSSFWorkbook workbook;
	//font
	private HSSFFont headerFont;
	private HSSFFont contentFont;

	// Styles
	private HSSFCellStyle headerStyle;
	private HSSFCellStyle oddRowStyle;
	private HSSFCellStyle evenRowStyle;
	
private HSSFCellStyle createStyle(HSSFFont font, short cellAlign,
		short cellColor, boolean cellBorder, short cellBorderColor) {

	HSSFCellStyle style = workbook.createCellStyle();
	style.setFont(font);
	style.setAlignment(cellAlign);
	style.setFillForegroundColor(cellColor);
	style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

	if (cellBorder) {
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);

		style.setTopBorderColor(cellBorderColor);
		style.setLeftBorderColor(cellBorderColor);
		style.setRightBorderColor(cellBorderColor);
		style.setBottomBorderColor(cellBorderColor);
	}

	return style;
}


	private HSSFFont createFont(short fontColor, short fontHeight,
			boolean fontBold) {

		HSSFFont font = workbook.createFont();
		font.setBold(fontBold);
		font.setColor(fontColor);
		font.setFontName("Arial");
		font.setFontHeightInPoints(fontHeight);

		return font;
	}
	
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/listar", method=RequestMethod.GET)
	public RespuestaHttp buscar(@RequestParam(value="prmCriterio",required=false)String prmCriterio)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try
		{
			
			Encuesta prmData = new ObjectMapper().readValue(prmCriterio, Encuesta.class);
			if(prmData!=null){
				if(prmData.getFechaInicio()==null){
					prmData.setFechaInicio("01/01/1900");
				}
				
				if(prmData.getFechaFin()==null){
					prmData.setFechaFin("01/01/3000");
				}
			}
			
			
			
			List<Encuesta> data = (List<Encuesta>) encuestaService.listar(prmData);
			if(data!=null && data.size()>0){
				lstEncuesta = data;
			}
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
	public RespuestaHttp registrar(@RequestBody Encuesta prmData,HttpServletRequest request)throws Exception
	{
		RespuestaHttp respuesta = new RespuestaHttp();
		try
		{
			System.out.println(prmData);
			long idGenerado = encuestaService.insertar(prmData);
			
			if(idGenerado>0)
			{
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha registrado la encuesta satisfactoriamente");
				respuesta.setData(prmData.getIdEncuesta());
			} 
			else 
			{
				respuesta.setMensaje("No se pudo registrar la encuesta");
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al registar la encuesta");
		}
		return respuesta;
	}
	
	@ResponseBody
	@RequestMapping(value ="/actualizar", method = RequestMethod.POST)
	public  RespuestaHttp actualizar(@RequestBody Encuesta prmData,HttpServletRequest request)throws Exception {
		RespuestaHttp respuesta = new RespuestaHttp();
		try{
			int result = encuestaService.actualizar(prmData);
			
			if(result>0){
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha actualizado	la encuesta satisfactoriamente");
			}else {
				respuesta.setMensaje("No se pudo actualizar la encuesta");
			}
		}catch (Exception e) {
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al actualizar la encuesta");
		}
		return respuesta;
	}
	
	@ResponseBody
	@RequestMapping(value = "/eliminar", method = RequestMethod.POST)
	public RespuestaHttp eliminar(
			@RequestBody Encuesta prmData,HttpServletRequest request)
			throws Exception{
		RespuestaHttp respuesta = new RespuestaHttp();
		try{
			int result = encuestaService.eliminar(prmData);
			if(result>0){
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha eliminado la encuesta correctamente");
			}else {
				respuesta.setMensaje("No se pudo eliminar la encuesta");
			}
		} catch (Exception e){
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al eliminar la encuesta");
		}
		return respuesta;
	}
	
	
	

	
	 @RequestMapping(value = "/exportar-excel",method = RequestMethod.GET)
	 public void exportarExcel(HttpServletRequest request, HttpServletResponse response)
	 throws Exception{


	 workbook = new HSSFWorkbook();
	 // Generate fonts
	 headerFont  = createFont(HSSFColor.WHITE.index, (short)12, true);
	 contentFont = createFont(HSSFColor.BLACK.index, (short)10, false);

	 // Generate styles
	 headerStyle  = createStyle(headerFont,  HSSFCellStyle.ALIGN_CENTER, HSSFColor.BLUE_GREY.index,       true, HSSFColor.WHITE.index);
	 oddRowStyle  = createStyle(contentFont, HSSFCellStyle.ALIGN_LEFT,   HSSFColor.WHITE.index, true, HSSFColor.GREY_80_PERCENT.index);
	 evenRowStyle = createStyle(contentFont, HSSFCellStyle.ALIGN_LEFT,   HSSFColor.WHITE.index, true, HSSFColor.GREY_80_PERCENT.index);

	 HSSFSheet sheet = workbook.createSheet("Reporte de Bibliografias mas Solicitadas");
	 Row fila = sheet.createRow(0);
	 File archivo = new File("ejemplo.xls");
	 Cell celda;
	 String[] titulos = { "Item", "Tipo Encuesta", "Codigo Denuncia", "Pregunta 1","Pregunta 2","Pregunta 3","Fecha de Registro" };
	 Double[] datos = { 1.0, 10.0, 45.0, 14.50, 30.50 };
	 int i;
	 for (i = 0; i < titulos.length; i++) {
	       celda = fila.createCell(i);
	       celda.setCellStyle(headerStyle);
	       celda.setCellValue(titulos[i]);
	 }
	 for (i = 0; i < lstEncuesta.size(); i++) {
	 fila = sheet.createRow(i+1);

	       celda = fila.createCell(0);
	       celda.setCellValue(i+1);
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(1);
	       celda.setCellValue(lstEncuesta.get(i).getTipoEncuesta().getDescripcion());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(2);
	       celda.setCellValue(lstEncuesta.get(i).getCodigoDenuncia());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(3);
	       celda.setCellValue(lstEncuesta.get(i).getPregunta1().getDescripcion());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(4);
	       
	       celda.setCellValue(lstEncuesta.get(i).getPregunta2().getDescripcion());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(5);
	       
	       celda.setCellValue(lstEncuesta.get(i).getPregunta3().getDescripcion());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(6);
	       
	       celda.setCellValue(lstEncuesta.get(i).getFechaRegistro());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(7);
	       

	    
	 }
	 for (int e = 0; e < titulos.length; sheet.autoSizeColumn(e++));



		try {
			ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			workbook.write(outByteStream);
			byte[] outArray = outByteStream.toByteArray();
			response.setContentType("application/ms-excel");
			response.setContentLength(outArray.length);
			response.setHeader("Expires:", "0"); // eliminates browser caching
			response.setHeader("Content-Disposition", "attachment; filename=encuesta-excel.xls");
			OutputStream outStream = response.getOutputStream();
			outStream.write(outArray);
			outStream.flush();

			System.out.println("Archivo creado exitosamente!");
		} catch (IOException e) {
			System.out.println("Error de escritura");
			e.printStackTrace();
		}
	}

}
