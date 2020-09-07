package pe.gob.oefa.apps.sinada.presentacion.rest.api;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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
import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.base.util.EmailAttachmentSender;
import pe.gob.oefa.apps.base.util.UtilRequest;
import pe.gob.oefa.apps.sinada.bean.general.ContactoPersona;
import pe.gob.oefa.apps.sinada.bean.maestros.FormatoCorreo;
import pe.gob.oefa.apps.sinada.bean.proceso.Bandeja;
import pe.gob.oefa.apps.sinada.bean.proceso.BandejaDetalle;
import pe.gob.oefa.apps.sinada.bean.proceso.view.BandejaDenuncia;
import pe.gob.oefa.apps.sinada.bean.seguridad.Perfil;
import pe.gob.oefa.apps.sinada.bean.seguridad.PerfilUsuario;
import pe.gob.oefa.apps.sinada.bean.seguridad.Usuario;
import pe.gob.oefa.apps.sinada.presentacion.util.ResourceUtil;
import pe.gob.oefa.apps.sinada.servicio.inf.general.ContactoPersonaService;
import pe.gob.oefa.apps.sinada.servicio.inf.maestros.FormatoCorreoService;
import pe.gob.oefa.apps.sinada.servicio.inf.maestros.MaestroService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.BandejaDetalleService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.BandejaService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.view.BandejaDenunciaService;
import pe.gob.oefa.apps.sinada.servicio.inf.seguridad.PerfilUsuarioService;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value="/rest/api/bandeja")
public class BandejaRest
{	
//	@Autowired
//	BandejaService bandejaService;
	
//	@Autowired
//	BandejaEntidadService bandejaEntidadService;
	
	@Autowired
	private BandejaDenunciaService bandejaDenunciaService;
	
	@Autowired
	private BandejaDetalleService bandejaDetalleService;
	
	@Autowired
	private BandejaService bandejaService;
	
	private List<BandejaDenuncia> lstBandejaDenuncia = null;
	
	private List<BandejaDenuncia> lstDenunciasExportadasExcel = null;
	
	@Autowired
	private MaestroService maestroService;
	
	@Autowired
	private PerfilUsuarioService perfilUsuarioService;
	
	@Autowired
	private FormatoCorreoService formatoCorreoService;
	
	@Autowired
	private ContactoPersonaService contactoPersonaService;
	
	private String logBase = "oefa-sinada-web: BandejaRest";
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
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
		
		
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/bandeja-entidad", method=RequestMethod.GET)
	public RespuestaHttp buscar(@RequestParam(value="prmCriterio",required=false)String prmCriterio)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try
		{
			BandejaDenuncia prmBandejaEntidad = new ObjectMapper().readValue(prmCriterio, BandejaDenuncia.class);
			List<BandejaDenuncia> bandejaEntidad = (List<BandejaDenuncia>) bandejaDenunciaService.listarBandejaOrganoCompetente(prmBandejaEntidad);
			if(bandejaEntidad!= null && bandejaEntidad.size()>0){
				lstBandejaDenuncia = bandejaEntidad;
			}
			
			_RespuestaHttp.setValido(true);
			_RespuestaHttp.setData(bandejaEntidad);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			_RespuestaHttp.setValido(false);
			_RespuestaHttp.setMensaje("Hubo un error al procesar la información");
		}
		return _RespuestaHttp;
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
	
	
	@ResponseBody
	@RequestMapping(value="/bandeja-denunciante", method=RequestMethod.GET)
	public RespuestaHttp listar(@RequestParam(value="prmCriterio",required=false)String prmCriterio)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try
		{
			BandejaDenuncia prmBandeja = new ObjectMapper().readValue(prmCriterio, BandejaDenuncia.class);
			List<BandejaDenuncia> bandejaDenuncia = (List<BandejaDenuncia>) bandejaDenunciaService.listar(prmBandeja);
			
			if(bandejaDenuncia!= null && bandejaDenuncia.size()>0){
				lstBandejaDenuncia = bandejaDenuncia;
			}
			
			_RespuestaHttp.setValido(true);
			_RespuestaHttp.setData(bandejaDenuncia);
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
	@RequestMapping(value="/buscar-denuncia-oefa-por-direccion-sub-direccion", method=RequestMethod.GET)
	public RespuestaHttp buscarDenunciaOefaPorIdDenunciaDireccionSupSubDireccion(@RequestParam(value="prmCriterio",required=false)String prmCriterio)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try
		{
			BandejaDenuncia prmBandejaEntidad = new ObjectMapper().readValue(prmCriterio, BandejaDenuncia.class);
			List<BandejaDenuncia> bandejaEntidad = (List<BandejaDenuncia>) bandejaDenunciaService.buscarDenunciaOefaPorIdDenunciaDireccionSupSubDireccion(prmBandejaEntidad);
			
			if(bandejaEntidad!=null && bandejaEntidad.size()>0){
				_RespuestaHttp.setValido(true);
				_RespuestaHttp.setData(bandejaEntidad);
			}else{
				_RespuestaHttp.setValido(false);
			}
			
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
	@RequestMapping(value="/validar-bandeja", method=RequestMethod.GET)
	public RespuestaHttp validarBandeja(@RequestParam(value="prmCriterio",required=false)String prmCriterio)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try
		{
			Bandeja prmBandeja = new ObjectMapper().readValue(prmCriterio, Bandeja.class);
			List<Bandeja> bandeja = (List<Bandeja>) bandejaService.validarBandeja(prmBandeja);
			
			
			_RespuestaHttp.setValido(true);
			_RespuestaHttp.setData(bandeja);
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
	@RequestMapping(value="/bandeja-asignacion-denuncia", method=RequestMethod.GET)
	public RespuestaHttp bandejaAsignacionDenuncia(@RequestParam(value="prmCriterio",required=false)String prmCriterio)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try
		{
			BandejaDenuncia prmBandeja = new ObjectMapper().readValue(prmCriterio, BandejaDenuncia.class);
			List<BandejaDenuncia> bandejaDenuncia = (List<BandejaDenuncia>) bandejaDenunciaService.listarBandejaAsignacionDeDenuncias(prmBandeja);
			if(bandejaDenuncia!= null && bandejaDenuncia.size()>0){
				lstBandejaDenuncia = bandejaDenuncia;
			}
			
			
			_RespuestaHttp.setValido(true);
			_RespuestaHttp.setData(bandejaDenuncia);
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
	@RequestMapping(value="/bandeja-especialista-sinada", method=RequestMethod.GET)
	public RespuestaHttp bandejaEspecialistaSinada(@RequestParam(value="prmCriterio",required=false)String prmCriterio)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try
		{
		
			String idPerfilCoordinadorSinada = ResourceUtil.getKey("IdPerfilSSO_Coordinador");
			BandejaDenuncia prmBandeja = new ObjectMapper().readValue(prmCriterio, BandejaDenuncia.class);
			/******************************/
			
			Bandeja oBandeja = new Bandeja();
			oBandeja =	bandejaService.buscarPorId(prmBandeja.getIdBandeja());
			if(oBandeja!= null){
				PerfilUsuario prmPerfilUsuario  = new PerfilUsuario();
				prmPerfilUsuario.setUsuario(new Usuario());
				prmPerfilUsuario.getUsuario().setIdUsuario(oBandeja.getIdResponsable());
				prmPerfilUsuario.setPerfil(new Perfil());
				prmPerfilUsuario.getPerfil().setIdPerfil(Long.valueOf(idPerfilCoordinadorSinada));
				prmPerfilUsuario = perfilUsuarioService.validarUsuarioPorPerfil(prmPerfilUsuario);
				
				if(prmPerfilUsuario!=null){
					
					prmBandeja.setTipoValidacionEspecialista(2);
					
				}
			}
		
			List<BandejaDenuncia> bandejaDenuncia = (List<BandejaDenuncia>) bandejaDenunciaService.listarBandejaEspecialistaSinada(prmBandeja);
			
			if(bandejaDenuncia!= null && bandejaDenuncia.size()>0){
				lstBandejaDenuncia = bandejaDenuncia;
			}
			_RespuestaHttp.setObjData(prmBandeja.getTipoValidacionEspecialista());
			_RespuestaHttp.setValido(true);
			_RespuestaHttp.setData(bandejaDenuncia);
		}
		catch (Exception e)
		{
			logger.error(this.logBase+ " : bandejaEspecialistaSinada" + e.getMessage());
			e.printStackTrace();
			_RespuestaHttp.setValido(false);
			_RespuestaHttp.setMensaje("Hubo un error al procesar la información");
		}
		return _RespuestaHttp;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/valida-bandeja",method = RequestMethod.POST)
	public RespuestaHttp registrar(@RequestBody Bandeja prmData,HttpServletRequest request)throws Exception
	{
		RespuestaHttp respuesta = new RespuestaHttp();
		try
		{
			
			 prmData = bandejaService.validarBandeja(prmData);
			
			if(prmData!=null)
			{
				respuesta.setValido(true);
				respuesta.setMensaje("Se ha registado el artículo satisfactoriamente");
				respuesta.setData(prmData);
			} 
			else 
			{
				respuesta.setMensaje("No se pudo registrar el artículo");
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al registrar el artículo");
		}
		return respuesta;
	}
	
	
	/******************************* Detalle Bandeja Cambiar de Estado  *********************************/
	
	@ResponseBody
	@RequestMapping(value = "/actualiza-estado-detalle-bandeja",method = RequestMethod.POST)
	public RespuestaHttp actualizaEstadoDetalleBandeja(@RequestBody BandejaDetalle prmData,HttpServletRequest request)throws Exception
	{
		RespuestaHttp respuesta = new RespuestaHttp();
		try
		{
			if(prmData.getDias()!=0){
//				Maestro prmMaestro = new Maestro();
//				prmMaestro.setCodigoMaestro("FechasNoLaborables");
//				
//				List<Maestro> lstMaestro = null;
//				
//				lstMaestro = (List<Maestro>) maestroService.buscarPorCodigoTabla(prmMaestro);
//				
//				Date date = Calendario.calcularDiasHabiles(prmData.getDias(), lstMaestro);
//				
//				SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
//		    	String fechaStr = format1.format(date);   
//		    	
				Date fecha = new Date();
				
				SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
		    	String fechaStr = format1.format(fecha); 
				
				prmData.setFechaPlazoAtencion(fechaStr);
				
			}
			
			
			
			prmData.setHostIp(UtilRequest.getClientIpAddress(request));
			 int data = bandejaDetalleService.actualizar(prmData);
			
			if(data>0)
			{
				respuesta.setValido(true);
				respuesta.setMensaje("Se actualizó");
				respuesta.setData(prmData);
			} 
			else 
			{
				respuesta.setMensaje("No se pudo actualizar");
			}
		} 
		catch (Exception e) 
		{
			logger.error(this.logBase+ " : actualizaEstadoDetalleBandeja" + e.getMessage());
			e.printStackTrace();
			respuesta.setMensaje("Hubo un error al actualizar");
		}
		return respuesta;
	}
	
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/listar-bandeja-organo-completo-excel-data", method=RequestMethod.GET)
	public RespuestaHttp listarBandejaOrganoCompententeCompletoExcelData(@RequestParam(value="prmCriterio",required=false)String prmCriterio)
	{
		RespuestaHttp _RespuestaHttp = new RespuestaHttp();
		try
		{
			listarBandejaOrganoCompententeCompleto();
			
			_RespuestaHttp.setValido(true);
			
		}
		catch (Exception e)
		{
			logger.error(this.logBase+ " : listarBandejaOrganoCompententeCompletoExcelData" + e.getMessage());
			e.printStackTrace();
			_RespuestaHttp.setValido(false);
			_RespuestaHttp.setMensaje("Hubo un error al procesar la información");
		}
		return _RespuestaHttp;
	}
	
	
	@SuppressWarnings("unused")
	public void  listarBandejaOrganoCompententeCompleto(){
		
		/*************************/
		this.lstDenunciasExportadasExcel = new ArrayList<BandejaDenuncia>();
		/**********************/
		
		BandejaDenuncia denuncia = new BandejaDenuncia();
		List<BandejaDenuncia> lstBandejaDenunciaOrgano = null;
		try {
			lstBandejaDenunciaOrgano = bandejaDenunciaService.listarBandejaEntidadBasicoCompleto(denuncia);
		} catch (ServicioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<BandejaDenuncia> datos = new ArrayList<BandejaDenuncia>();
		for (BandejaDenuncia bandejaDenuncia : lstBandejaDenuncia) {
			this.lstDenunciasExportadasExcel.add(bandejaDenuncia);

				for (BandejaDenuncia bandejaDenunciaOrgano : lstBandejaDenunciaOrgano) {
					
					
					if(bandejaDenuncia.getIdDenuncia()==bandejaDenunciaOrgano.getIdDenuncia() && 
							bandejaDenuncia.getIdBandejaDetalle()!=bandejaDenunciaOrgano.getIdBandejaDetalle()){
						this.lstDenunciasExportadasExcel.add(bandejaDenunciaOrgano);
//						datos.add(bandejaDenunciaOrgano);
						
					}
					
				}
				
				
				
			
			
		}
		
		
		
		
	
		
	}
	
//	@SuppressWarnings("unused")
//	public void  listarBandejaOrganoCompententeCompleto(){
//		
//		BandejaDenuncia denuncia = new BandejaDenuncia();
//		List<BandejaDenuncia> lstBandejaDenunciaOrgano = null;
//		try {
//			lstBandejaDenunciaOrgano = bandejaDenunciaService.listarBandejaOrganoCompententeCompleto(denuncia);
//		} catch (ServicioException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		List<BandejaDenuncia> datos = new ArrayList<BandejaDenuncia>();
//		for (BandejaDenuncia bandejaDenuncia : lstBandejaDenuncia) {
//			
//
//				for (BandejaDenuncia bandejaDenunciaOrgano : lstBandejaDenunciaOrgano) {
//					
//					
//					if(bandejaDenuncia.getIdDenuncia()==bandejaDenunciaOrgano.getIdDenuncia() && 
//							bandejaDenuncia.getIdBandejaDetalle()!=bandejaDenunciaOrgano.getIdBandejaDetalle()){
//						
//						datos.add(bandejaDenunciaOrgano);
//						
//					}
//					
//				}
//				
//				
//				
//			
//			
//		}
//		
//		
//		
//		
//		if(datos!=null && datos.size()>0){
//			
//			lstBandejaDenuncia.addAll(datos);
//			
//		}
//		
//	}
	
	 @RequestMapping(value = "/exportar-excel-coordinador",method = RequestMethod.GET)
	 public void exportarExcelCoordinador(HttpServletRequest request, HttpServletResponse response)
	 throws Exception{
//		 listarBandejaOrganoCompententeCompleto();

	 workbook = new HSSFWorkbook();
	 // Generate fonts
	 headerFont  = createFont(HSSFColor.WHITE.index, (short)12, true);
	 contentFont = createFont(HSSFColor.BLACK.index, (short)10, false);

	 // Generate styles
	 headerStyle  = createStyle(headerFont,  HSSFCellStyle.ALIGN_CENTER, HSSFColor.GREEN.index,       true, HSSFColor.LIGHT_GREEN.index);
	 oddRowStyle  = createStyle(contentFont, HSSFCellStyle.ALIGN_LEFT,   HSSFColor.WHITE.index, true, HSSFColor.GREY_80_PERCENT.index);
	 evenRowStyle = createStyle(contentFont, HSSFCellStyle.ALIGN_LEFT,   HSSFColor.WHITE.index, true, HSSFColor.GREY_80_PERCENT.index);

	 HSSFSheet sheet = workbook.createSheet("Reporte denuncias");
	 Row fila = sheet.createRow(0);
	 File archivo = new File("denuncias.xls");
	 Cell celda;
	 String[] titulos = { "Código denuncia", "Tipo de denuncia", "Medio Recepción",
			 
			 "Número de denunciantes", "Denunciante","Número caso","Problemática",
			 "Debido a (Nivel 1)","Debido a (Nivel 2)","Debido a (Nivel 3)",
			 "Zona Suceso (Nivel 1)","Zona Suceso (Nivel 2)","Zona Suceso (Nivel 3)",
			 
			 "Departamento","Provincia","Distrito","Centro Poblado","Dirección","Referencia",
			 
			 
			 
			 "Tipo Denunciado","Denunciado",
			 
			 "Documento denunciado","Dirección denunciado",
			 "Departamento denunciado","Provincia denunciado",
			 "Distrito denunciado",
			 
			 
			 
			 "Estado denuncia","Estado atención",
			 
			 "Fecha registro", "Tiempo transcurrido","fecha ulima acción",
			 "Tiempo ultima acción","Nombre Entidad" };
	 Double[] datos = { 1.0, 10.0, 45.0, 14.50, 30.50 };
	 int i;
	 for (i = 0; i < titulos.length; i++) {
	       celda = fila.createCell(i);
	       celda.setCellStyle(headerStyle);
	       celda.setCellValue(titulos[i]);
	 }
	 for (i = 0; i < lstDenunciasExportadasExcel.size(); i++) {
	 fila = sheet.createRow(i+1);

	       celda = fila.createCell(0);
	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getCodigoDenuncia());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(1);
	       /***************************************  *******************************************/
	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getNombreTipoDenuncia());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(2);
	       /***************************************  *******************************************/
	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getNombreMedioRecepcion());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(3);
	       /***************************************  *******************************************/
	       
	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getNumeroDenunciantes());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(4);
	       /***************************************  *******************************************/
	       
	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getNombreDenunciante());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(5);
	       /***************************************  *******************************************/
	       
	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getNumeroCaso());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(6);
	       /***************************************  *******************************************/
	       

	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getNombreProblematica());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(7);
	       /***************************************  *******************************************/
	       
	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getNombreDebidoA1());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(8);
	       /***************************************  *******************************************/
	       
	       
	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getNombreDebidoA2());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(9);
	       /***************************************  *******************************************/
	       
	       
	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getNombreDebidoA3());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(10);
	       /***************************************  *******************************************/
	       
	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getNombreZonaSuceso1());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(11);
	       /***************************************  *******************************************/
	       
	       
	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getNombreZonaSuceso2());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(12);
	       /***************************************  *******************************************/
	       
	       
	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getNombreZonaSuceso3());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(13);
	       /***************************************  *******************************************/
	       
	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getNombreDepartamento());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(14);
	       /***************************************  *******************************************/
	       
	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getNombreProvincia());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(15);
	       /***************************************  *******************************************/
	       
	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getNombreDistrito());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(16);
	       
	       /********************************************************************************/
	       
	       if(lstDenunciasExportadasExcel.get(i).getNombreCentroPoblado()!=null){
	    	   celda.setCellValue(lstDenunciasExportadasExcel.get(i).getNombreCentroPoblado());
	       }else{
	    
	    		   celda.setCellValue("");
	    	   
	       }
	       
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(17);
	       /***************************************  *******************************************/
	       
	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getDireccion());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(18);
	       /***************************************  *******************************************/
	       
	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getReferencia());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(19);
	       /***************************************  *******************************************/
	       
	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getNombreTipoResponsable());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(20);
	       /***************************************  *******************************************/
	       
	       
	       
	       
	       if(lstDenunciasExportadasExcel.get(i).getNombreDenunciado()!=null){
	    	   celda.setCellValue(lstDenunciasExportadasExcel.get(i).getNombreDenunciado());
	       }else{
	    	   
	    	   if(lstDenunciasExportadasExcel.get(i).getNombreDenunciadoDenuncia()!=null){
	    		   celda.setCellValue(lstDenunciasExportadasExcel.get(i).getNombreDenunciadoDenuncia());
	    	   }else{
	    		   celda.setCellValue("");
	    	   }
	       }
	      
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(21);
	       
	       
	       
	     /********************************************************************************/  
	       
	       
	       if(lstDenunciasExportadasExcel.get(i).getDocumentoDenunciado()!=null){
	    	   celda.setCellValue(lstDenunciasExportadasExcel.get(i).getDocumentoDenunciado());
	       }else{
	    		   celda.setCellValue("");
	       }
	      
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(22);
	       
	       /***************************************  *******************************************/
	       
	         
	       if(lstDenunciasExportadasExcel.get(i).getDireccionDenunciado()!=null){
	    	   celda.setCellValue(lstDenunciasExportadasExcel.get(i).getDireccionDenunciado());
	       }else{
	    	   
	    	   if(lstDenunciasExportadasExcel.get(i).getDireccionDenunciadoDenuncia()!=null){
	    		   celda.setCellValue(lstDenunciasExportadasExcel.get(i).getDireccionDenunciadoDenuncia());
	    	   }else{
	    		   celda.setCellValue("");
	    	   }
	       }
	      
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(23);
	       
	       /***************************************  *******************************************/
	       
	         
	       if(lstDenunciasExportadasExcel.get(i).getNombreDepartamentoDenunciado()!=null){
	    	   celda.setCellValue(lstDenunciasExportadasExcel.get(i).getNombreDepartamentoDenunciado());
	       }else{
	    		   celda.setCellValue("");
	       }
	      
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(24);
	       
	       /***************************************  *******************************************/
	       
	         
	       if(lstDenunciasExportadasExcel.get(i).getNombreProvinciaDenunciado()!=null){
	    	   celda.setCellValue(lstDenunciasExportadasExcel.get(i).getNombreProvinciaDenunciado());
	       }else{
	    		   celda.setCellValue("");
	       }
	      
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(25);
	       
	       /***************************************  *******************************************/
	       
	       
	       if(lstDenunciasExportadasExcel.get(i).getNombreDistritoDenunciado()!=null){
	    	   celda.setCellValue(lstDenunciasExportadasExcel.get(i).getNombreDistritoDenunciado());
	       }else{
	    		   celda.setCellValue("");
	       }
	      
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(26);
	       
	       /***************************************  *******************************************/
	       
	       
	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getNombreEstadoDenuncia());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(27);
	       /***************************************  *******************************************/
	       
	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getNombreEstadoBandejaDetalle());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(28);
	       /***************************************  *******************************************/
	       
	    
	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getFechaRegistroDenuncia());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(29);
	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getTiempoTranscurrido());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(30);
	       
	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getFechaUltimaAccion());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(31);
	       
	       
	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getTiempoUltimaAccion());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(32);
	       
	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getNombreEntidadCompentente());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(33);
	       
	       
//	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getUnidadOrganica().getNombreUnidad());
//	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	    
	 }
	 for (int e = 0; e < titulos.length; sheet.autoSizeColumn(e++));



		try {
			ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			workbook.write(outByteStream);
			byte[] outArray = outByteStream.toByteArray();
			response.setContentType("application/ms-excel");
			response.setContentLength(outArray.length);
			response.setHeader("Expires:", "0"); // eliminates browser caching
			response.setHeader("Content-Disposition", "attachment; filename=Denuncia.xls");
			OutputStream outStream = response.getOutputStream();
			outStream.write(outArray);
			outStream.flush();

			System.out.println("Archivo creado exitosamente!");
		} catch (IOException e) {
			logger.error(this.logBase+ " : exportarExcelCoordinador" + e.getMessage());
			System.out.println("Error de escritura");
			e.printStackTrace();
		}
	}
	
	 
	 
	 @RequestMapping(value = "/exportar-excel-especialista",method = RequestMethod.GET)
	 public void exportarExcelEspecialista(HttpServletRequest request, HttpServletResponse response)
	 throws Exception{
//		 listarBandejaOrganoCompententeCompleto();

	 workbook = new HSSFWorkbook();
	 // Generate fonts
	 headerFont  = createFont(HSSFColor.WHITE.index, (short)12, true);
	 contentFont = createFont(HSSFColor.BLACK.index, (short)10, false);

	 // Generate styles
	 headerStyle  = createStyle(headerFont,  HSSFCellStyle.ALIGN_CENTER, HSSFColor.GREEN.index,       true, HSSFColor.LIGHT_GREEN.index);
	 oddRowStyle  = createStyle(contentFont, HSSFCellStyle.ALIGN_LEFT,   HSSFColor.WHITE.index, true, HSSFColor.GREY_80_PERCENT.index);
	 evenRowStyle = createStyle(contentFont, HSSFCellStyle.ALIGN_LEFT,   HSSFColor.WHITE.index, true, HSSFColor.GREY_80_PERCENT.index);

	 HSSFSheet sheet = workbook.createSheet("Reporte denuncias");
	 Row fila = sheet.createRow(0);
	 File archivo = new File("denuncias.xls");
	 Cell celda;
	 String[] titulos = { "Código denuncia", "Tipo de denuncia", "Medio Recepción",
			 
			 "Número de denunciantes", "Denunciante","Número caso","Problemática",
			 "Debido a (Nivel 1)","Debido a (Nivel 2)","Debido a (Nivel 3)",
			 "Zona Suceso (Nivel 1)","Zona Suceso (Nivel 2)","Zona Suceso (Nivel 3)",
			 
			 "Departamento","Provincia","Distrito","Centro poblado","Dirección","Referencia",
			 
			 
			 
			 "Tipo Denunciado","Denunciado",
			 
			 "Documento denunciado","Dirección denunciado",
			 "Departamento denunciado","Provincia denunciado",
			 "Distrito denunciado",
			 
			 "Estado denuncia","Estado atención",
			 
			 "Fecha registro", "Tiempo transcurrido","fecha ulima acción",
			 "Tiempo ultima acción","Nombre Entidad","Especialista asignado" };
	 Double[] datos = { 1.0, 10.0, 45.0, 14.50, 30.50 };
	 int i;
	 for (i = 0; i < titulos.length; i++) {
	       celda = fila.createCell(i);
	       celda.setCellStyle(headerStyle);
	       celda.setCellValue(titulos[i]);
	 }
	 for (i = 0; i < lstDenunciasExportadasExcel.size(); i++) {
	 fila = sheet.createRow(i+1);

	       celda = fila.createCell(0);
	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getCodigoDenuncia());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(1);
	       /***************************************  *******************************************/
	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getNombreTipoDenuncia());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(2);
	       /***************************************  *******************************************/
	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getNombreMedioRecepcion());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(3);
	       /***************************************  *******************************************/
	       
	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getNumeroDenunciantes());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(4);
	       /***************************************  *******************************************/
	       
	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getNombreDenunciante());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(5);
	       /***************************************  *******************************************/
	       
	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getNumeroCaso());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(6);
	       /***************************************  *******************************************/
	       

	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getNombreProblematica());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(7);
	       /***************************************  *******************************************/
	       
	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getNombreDebidoA1());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(8);
	       /***************************************  *******************************************/
	       
	       
	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getNombreDebidoA2());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(9);
	       /***************************************  *******************************************/
	       
	       
	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getNombreDebidoA3());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(10);
	       /***************************************  *******************************************/
	       
	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getNombreZonaSuceso1());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(11);
	       /***************************************  *******************************************/
	       
	       
	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getNombreZonaSuceso2());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(12);
	       /***************************************  *******************************************/
	       
	       
	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getNombreZonaSuceso3());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(13);
	       /***************************************  *******************************************/
	       
	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getNombreDepartamento());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(14);
	       /***************************************  *******************************************/
	       
	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getNombreProvincia());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(15);
	       /***************************************  *******************************************/
	       
	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getNombreDistrito());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(16);
	       /***************************************  *******************************************/
	       
	       if(lstDenunciasExportadasExcel.get(i).getNombreCentroPoblado()!=null){
	    	   celda.setCellValue(lstDenunciasExportadasExcel.get(i).getNombreCentroPoblado());
	       }else{
	    
	    		   celda.setCellValue("");
	    	   
	       }
	       
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(17);
	       
	    
	       
	       /*************************************************************************************/
	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getDireccion());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(18);
	       /***************************************  *******************************************/
	       
	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getReferencia());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(19);
	       /***************************************  *******************************************/
	       
	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getNombreTipoResponsable());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(20);
	       /***************************************  *******************************************/
	       
	       
	       if(lstDenunciasExportadasExcel.get(i).getNombreDenunciado()!=null){
	    	   celda.setCellValue(lstDenunciasExportadasExcel.get(i).getNombreDenunciado());
	       }else{
	    	   
	    	   if(lstDenunciasExportadasExcel.get(i).getNombreDenunciadoDenuncia()!=null){
	    		   celda.setCellValue(lstDenunciasExportadasExcel.get(i).getNombreDenunciadoDenuncia());
	    	   }else{
	    		   celda.setCellValue("");
	    	   }
	       }
	      
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(21);
	       
	       
	       
	     /********************************************************************************/  
	       
	       
	       if(lstDenunciasExportadasExcel.get(i).getDocumentoDenunciado()!=null){
	    	   celda.setCellValue(lstDenunciasExportadasExcel.get(i).getDocumentoDenunciado());
	       }else{
	    		   celda.setCellValue("");
	       }
	      
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(22);
	       
	       /***************************************  *******************************************/
	       
	         
	       if(lstDenunciasExportadasExcel.get(i).getDireccionDenunciado()!=null){
	    	   celda.setCellValue(lstDenunciasExportadasExcel.get(i).getDireccionDenunciado());
	       }else{
	    	   
	    	   if(lstDenunciasExportadasExcel.get(i).getDireccionDenunciadoDenuncia()!=null){
	    		   celda.setCellValue(lstDenunciasExportadasExcel.get(i).getDireccionDenunciadoDenuncia());
	    	   }else{
	    		   celda.setCellValue("");
	    	   }
	       }
	      
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(23);
	       
	       /***************************************  *******************************************/
	       
	         
	       if(lstDenunciasExportadasExcel.get(i).getNombreDepartamentoDenunciado()!=null){
	    	   celda.setCellValue(lstDenunciasExportadasExcel.get(i).getNombreDepartamentoDenunciado());
	       }else{
	    		   celda.setCellValue("");
	       }
	      
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(24);
	       
	       /***************************************  *******************************************/
	       
	         
	       if(lstDenunciasExportadasExcel.get(i).getNombreProvinciaDenunciado()!=null){
	    	   celda.setCellValue(lstDenunciasExportadasExcel.get(i).getNombreProvinciaDenunciado());
	       }else{
	    		   celda.setCellValue("");
	       }
	      
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(25);
	       
	       /***************************************  *******************************************/
	       
	       
	       if(lstDenunciasExportadasExcel.get(i).getNombreDistritoDenunciado()!=null){
	    	   celda.setCellValue(lstDenunciasExportadasExcel.get(i).getNombreDistritoDenunciado());
	       }else{
	    		   celda.setCellValue("");
	       }
	      
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(26);
	       /***************************************  *******************************************/
	       
	       
	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getNombreEstadoDenuncia());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(27);
	       /***************************************  *******************************************/
	       
	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getNombreEstadoBandejaDetalle());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(28);
	       /***************************************  *******************************************/
	       
	    
	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getFechaRegistroDenuncia());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(29);
	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getTiempoTranscurrido());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(30);
	       
	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getFechaUltimaAccion());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(31);
	       
	       
	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getTiempoUltimaAccion());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(32);
	       
	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getNombreEntidadCompentente());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(33);
	       
	       
	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getNombreEspecialistaSinada());
	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	       celda = fila.createCell(34);
	       
	       
//	       celda.setCellValue(lstDenunciasExportadasExcel.get(i).getUnidadOrganica().getNombreUnidad());
//	       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
	    
	 }
	 for (int e = 0; e < titulos.length; sheet.autoSizeColumn(e++));



		try {
			ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			workbook.write(outByteStream);
			byte[] outArray = outByteStream.toByteArray();
			response.setContentType("application/ms-excel");
			response.setContentLength(outArray.length);
			response.setHeader("Expires:", "0"); // eliminates browser caching
			response.setHeader("Content-Disposition", "attachment; filename=Denuncia.xls");
			OutputStream outStream = response.getOutputStream();
			outStream.write(outArray);
			outStream.flush();

			System.out.println("Archivo creado exitosamente!");
		} catch (IOException e) {
			logger.error(this.logBase+ " : exportarExcelEspecialista" + e.getMessage());
			System.out.println("Error de escritura");
			e.printStackTrace();
		}
	}
	
	 @RequestMapping(value = "/exportar-excel",method = RequestMethod.GET)
	 public void exportarExcel(HttpServletRequest request, HttpServletResponse response)
	 throws Exception{


		 workbook = new HSSFWorkbook();
		 // Generate fonts
		 headerFont  = createFont(HSSFColor.WHITE.index, (short)12, true);
		 contentFont = createFont(HSSFColor.BLACK.index, (short)10, false);

		 // Generate styles
		 headerStyle  = createStyle(headerFont,  HSSFCellStyle.ALIGN_CENTER, HSSFColor.GREEN.index,       true, HSSFColor.LIGHT_GREEN.index);
		 oddRowStyle  = createStyle(contentFont, HSSFCellStyle.ALIGN_LEFT,   HSSFColor.WHITE.index, true, HSSFColor.GREY_80_PERCENT.index);
		 evenRowStyle = createStyle(contentFont, HSSFCellStyle.ALIGN_LEFT,   HSSFColor.WHITE.index, true, HSSFColor.GREY_80_PERCENT.index);

		 HSSFSheet sheet = workbook.createSheet("Reporte denuncias");
		 Row fila = sheet.createRow(0);
		 File archivo = new File("ejemplo.xls");
		 Cell celda;
		 String[] titulos = { "Código denuncia", "Tipo de denuncia", "Medio Recepción",
				 
				 "Número de denunciantes", "Denunciante","Número caso","Problemática",
				 "Debido a (Nivel 1)","Debido a (Nivel 2)","Debido a (Nivel 3)",
				 "Zona Suceso (Nivel 1)","Zona Suceso (Nivel 2)","Zona Suceso (Nivel 3)",
				 
				 "Departamento","Provincia","Distrito","Centro poblado","Dirección","Referencia",
				 
				 
				 
				 "Tipo Denunciado","Denunciado",
				 "Documento denunciado","Dirección denunciado",
				 "Departamento denunciado","Provincia denunciado",
				 "Distrito denunciado",
				 
				 
				 "Estado denuncia","Estado atención",
				 
				 "Fecha registro", "Tiempo transcurrido","fecha ulima acción",
				 "Tiempo ultima acción" };
		 Double[] datos = { 1.0, 10.0, 45.0, 14.50, 30.50 };
		 int i;
		 for (i = 0; i < titulos.length; i++) {
		       celda = fila.createCell(i);
		       celda.setCellStyle(headerStyle);
		       celda.setCellValue(titulos[i]);
		 }
		 for (i = 0; i < lstBandejaDenuncia.size(); i++) {
		 fila = sheet.createRow(i+1);

		       celda = fila.createCell(0);
		       celda.setCellValue(lstBandejaDenuncia.get(i).getCodigoDenuncia());
		       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
		       celda = fila.createCell(1);
		       /***************************************  *******************************************/
		       celda.setCellValue(lstBandejaDenuncia.get(i).getNombreTipoDenuncia());
		       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
		       celda = fila.createCell(2);
		       /***************************************  *******************************************/
		       celda.setCellValue(lstBandejaDenuncia.get(i).getNombreMedioRecepcion());
		       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
		       celda = fila.createCell(3);
		       /***************************************  *******************************************/
		       
		       celda.setCellValue(lstBandejaDenuncia.get(i).getNumeroDenunciantes());
		       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
		       celda = fila.createCell(4);
		       /***************************************  *******************************************/
		       
		       celda.setCellValue(lstBandejaDenuncia.get(i).getNombreDenunciante());
		       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
		       celda = fila.createCell(5);
		       /***************************************  *******************************************/
		       
		       celda.setCellValue(lstBandejaDenuncia.get(i).getNumeroCaso());
		       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
		       celda = fila.createCell(6);
		       /***************************************  *******************************************/
		       

		       celda.setCellValue(lstBandejaDenuncia.get(i).getNombreProblematica());
		       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
		       celda = fila.createCell(7);
		       /***************************************  *******************************************/
		       
		       celda.setCellValue(lstBandejaDenuncia.get(i).getNombreDebidoA1());
		       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
		       celda = fila.createCell(8);
		       /***************************************  *******************************************/
		       
		       
		       celda.setCellValue(lstBandejaDenuncia.get(i).getNombreDebidoA2());
		       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
		       celda = fila.createCell(9);
		       /***************************************  *******************************************/
		       
		       
		       celda.setCellValue(lstBandejaDenuncia.get(i).getNombreDebidoA3());
		       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
		       celda = fila.createCell(10);
		       /***************************************  *******************************************/
		       
		       celda.setCellValue(lstBandejaDenuncia.get(i).getNombreZonaSuceso1());
		       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
		       celda = fila.createCell(11);
		       /***************************************  *******************************************/
		       
		       
		       celda.setCellValue(lstBandejaDenuncia.get(i).getNombreZonaSuceso2());
		       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
		       celda = fila.createCell(12);
		       /***************************************  *******************************************/
		       
		       
		       celda.setCellValue(lstBandejaDenuncia.get(i).getNombreZonaSuceso3());
		       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
		       celda = fila.createCell(13);
		       /***************************************  *******************************************/
		       
		       celda.setCellValue(lstBandejaDenuncia.get(i).getNombreDepartamento());
		       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
		       celda = fila.createCell(14);
		       /***************************************  *******************************************/
		       
		       celda.setCellValue(lstBandejaDenuncia.get(i).getNombreProvincia());
		       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
		       celda = fila.createCell(15);
		       /***************************************  *******************************************/
		       
		       celda.setCellValue(lstBandejaDenuncia.get(i).getNombreDistrito());
		       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
		       celda = fila.createCell(16);
		       /***************************************  *******************************************/
		       
		       if(lstBandejaDenuncia.get(i).getNombreCentroPoblado()!=null){
		    	   celda.setCellValue(lstBandejaDenuncia.get(i).getNombreCentroPoblado());
		       }else{
		    
		    		   celda.setCellValue("");
		    	   
		       }
		       
		       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
		       celda = fila.createCell(17);
		       
		       
		
		       
		       /************************************************************************************/
		       celda.setCellValue(lstBandejaDenuncia.get(i).getDireccion());
		       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
		       celda = fila.createCell(18);
		       /***************************************  *******************************************/
		       
		       celda.setCellValue(lstBandejaDenuncia.get(i).getReferencia());
		       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
		       celda = fila.createCell(19);
		       /***************************************  *******************************************/
		       
		       celda.setCellValue(lstBandejaDenuncia.get(i).getNombreTipoResponsable());
		       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
		       celda = fila.createCell(20);
		       /***************************************  *******************************************/
		       
		       
		       if(lstBandejaDenuncia.get(i).getNombreDenunciado()!=null){
		    	   celda.setCellValue(lstBandejaDenuncia.get(i).getNombreDenunciado());
		       }else{
		    	   
		    	   if(lstBandejaDenuncia.get(i).getNombreDenunciadoDenuncia()!=null){
		    		   celda.setCellValue(lstBandejaDenuncia.get(i).getNombreDenunciadoDenuncia());
		    	   }else{
		    		   celda.setCellValue("");
		    	   }
		       }
		      
		       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
		       celda = fila.createCell(21);
		       
		       
		       
		     /********************************************************************************/  
		       
		       
		       if(lstBandejaDenuncia.get(i).getDocumentoDenunciado()!=null){
		    	   celda.setCellValue(lstBandejaDenuncia.get(i).getDocumentoDenunciado());
		       }else{
		    		   celda.setCellValue("");
		       }
		      
		       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
		       celda = fila.createCell(22);
		       
		       /***************************************  *******************************************/
		       
		         
		       if(lstBandejaDenuncia.get(i).getDireccionDenunciado()!=null){
		    	   celda.setCellValue(lstBandejaDenuncia.get(i).getDireccionDenunciado());
		       }else{
		    	   
		    	   if(lstBandejaDenuncia.get(i).getDireccionDenunciadoDenuncia()!=null){
		    		   celda.setCellValue(lstBandejaDenuncia.get(i).getDireccionDenunciadoDenuncia());
		    	   }else{
		    		   celda.setCellValue("");
		    	   }
		       }
		      
		       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
		       celda = fila.createCell(23);
		       
		       /***************************************  *******************************************/
		       
		         
		       if(lstBandejaDenuncia.get(i).getNombreDepartamentoDenunciado()!=null){
		    	   celda.setCellValue(lstBandejaDenuncia.get(i).getNombreDepartamentoDenunciado());
		       }else{
		    		   celda.setCellValue("");
		       }
		      
		       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
		       celda = fila.createCell(24);
		       
		       /***************************************  *******************************************/
		       
		         
		       if(lstBandejaDenuncia.get(i).getNombreProvinciaDenunciado()!=null){
		    	   celda.setCellValue(lstBandejaDenuncia.get(i).getNombreProvinciaDenunciado());
		       }else{
		    		   celda.setCellValue("");
		       }
		      
		       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
		       celda = fila.createCell(25);
		       
		       /***************************************  *******************************************/
		       
		       
		       if(lstBandejaDenuncia.get(i).getNombreDistritoDenunciado()!=null){
		    	   celda.setCellValue(lstBandejaDenuncia.get(i).getNombreDistritoDenunciado());
		       }else{
		    		   celda.setCellValue("");
		       }
		      
		       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
		       celda = fila.createCell(26);
		       /***************************************  *******************************************/
		       
		       
		       celda.setCellValue(lstBandejaDenuncia.get(i).getNombreEstadoDenuncia());
		       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
		       celda = fila.createCell(27);
		       /***************************************  *******************************************/
		       
		       celda.setCellValue(lstBandejaDenuncia.get(i).getNombreEstadoBandejaDetalle());
		       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
		       celda = fila.createCell(28);
		       /***************************************  *******************************************/
		       
		    
		       celda.setCellValue(lstBandejaDenuncia.get(i).getFechaRegistroDenuncia());
		       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
		       celda = fila.createCell(29);
		       celda.setCellValue(lstBandejaDenuncia.get(i).getTiempoTranscurrido());
		       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
		       celda = fila.createCell(30);
		       
		       celda.setCellValue(lstBandejaDenuncia.get(i).getFechaUltimaAccion());
		       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
		       celda = fila.createCell(31);
		       
		       
		       celda.setCellValue(lstBandejaDenuncia.get(i).getTiempoUltimaAccion());
		       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
		       celda = fila.createCell(32);
		       
		       
//		       celda.setCellValue(lstBandejaDenuncia.get(i).getUnidadOrganica().getNombreUnidad());
//		       celda.setCellStyle( (i+1) % 2 == 0 ? oddRowStyle : evenRowStyle );
		    
		 }
		 for (int e = 0; e < titulos.length; sheet.autoSizeColumn(e++));



			try {
				ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
				workbook.write(outByteStream);
				byte[] outArray = outByteStream.toByteArray();
				response.setContentType("application/ms-excel");
				response.setContentLength(outArray.length);
				response.setHeader("Expires:", "0"); // eliminates browser caching
				response.setHeader("Content-Disposition", "attachment; filename=Denuncia.xls");
				OutputStream outStream = response.getOutputStream();
				outStream.write(outArray);
				outStream.flush();

				System.out.println("Archivo creado exitosamente!");
			} catch (IOException e) {
				logger.error(this.logBase+ " : exportarExcel" + e.getMessage());
				System.out.println("Error de escritura");
				e.printStackTrace();
			}
		}
	
//	@ResponseBody
//	@RequestMapping(value ="/actualizar", method = RequestMethod.POST)
//	public  RespuestaHttp actualizar(@RequestBody Articulo prmArticulo,HttpServletRequest request)throws Exception {
//		RespuestaHttp respuesta = new RespuestaHttp();
//		try{
//			int result = articuloService.actualizar(prmArticulo);
//			
//			if(result>0){
//				respuesta.setValido(true);
//				respuesta.setMensaje("Se ha actualizado	la Articulo satisfactoriamente");
//			}else {
//				respuesta.setMensaje("No se pudo actualizar la Articulo");
//			}
//		}catch (Exception e) {
//			e.printStackTrace();
//			respuesta.setMensaje("Hubo un error al actualizar la Articulo");
//		}
//		return respuesta;
//	}
//	
//	@ResponseBody
//	@RequestMapping(value = "/eliminar", method = RequestMethod.POST)
//	public RespuestaHttp eliminar(
//			@RequestBody Articulo prmArticulo,HttpServletRequest request)
//			throws Exception{
//		RespuestaHttp respuesta = new RespuestaHttp();
//		try{
//			int result = articuloService.eliminar(prmArticulo);
//			if(result>0){
//				respuesta.setValido(true);
//				respuesta.setMensaje("Se ha eliminado la Articulo correctamente");
//			}else {
//				respuesta.setMensaje("No se pudo eliminar la Articulo");
//			}
//		} catch (Exception e){
//			e.printStackTrace();
//			respuesta.setMensaje("Hubo un error al eliminar la Articulo");
//		}
//		return respuesta;
//	}
	
//	@ResponseBody
//	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
//	public RespuestaHttp buscarxNumeroNorma(@PathVariable("id")Long idArticulo)throws Exception 
//	{
//		RespuestaHttp respuesta = new  RespuestaHttp();
//		try
//		{
//			Articulo prmArticulo = new Articulo();
//			prmArticulo.setIdArticulo(idArticulo);
//			prmArticulo = articuloService.buscarPorId(prmArticulo.getIdArticulo());
//			
//			respuesta.setValido(true);
//			respuesta.setData(prmArticulo);
//		} 
//		catch (Exception e)
//		{
//			respuesta.setMensaje("Hubo un error al obtener la Articulo");
//			e.printStackTrace();
//		}
//		return respuesta;
//	}

	 
	 /**************************************************************************/
	 
	 
		@ResponseBody
		@RequestMapping(value = "/asignar-denuncia-bandeja",method = RequestMethod.POST)
		public RespuestaHttp asignarBandejaBandeja(@RequestBody BandejaDetalle prmData,HttpServletRequest request)throws Exception
		{
			RespuestaHttp respuesta = new RespuestaHttp();
			try
			{
				Bandeja prmBandeja = new Bandeja();
				prmBandeja.setTipoResponsable(1);  // 3 -- OEFA  -- 4 EFA
				prmBandeja.setIdResponsable(prmData.getBandeja().getIdResponsable());
				prmBandeja.setEstado(1);
				prmBandeja =bandejaService.validarBandeja(prmBandeja);
				
				BandejaDetalle prmBandejaDetalle = new BandejaDetalle();
				prmBandejaDetalle.setIdBandeja(prmBandeja.getIdBandeja());
				prmBandejaDetalle.setTipoBandeja(4);
				prmBandejaDetalle.setIdDenuncia(prmData.getIdDenuncia());
				prmBandejaDetalle.setTipoAsignacion(0);
				prmBandejaDetalle.setEstado(1);
				
				
				prmBandejaDetalle.setPrm1(prmData.getPrm1());
				prmBandejaDetalle.setHostIp(UtilRequest.getClientIpAddress(request));
			long idBandeja = 	bandejaDetalleService.insertar(prmBandejaDetalle);
				
		   BandejaDenuncia prmDataBandejaEspecialista = 	bandejaDenunciaService.buscarPorIdBandejaDetalle(idBandeja);
//				  bandejaDetalleService.insertar(prmData);
		   if(prmDataBandejaEspecialista!= null && prmDataBandejaEspecialista.getIdBandejaDetalle()!=0){
			   enviarCorreoEspecialista(prmDataBandejaEspecialista);
		   }
		   
				if(prmData!=null)
				{
					respuesta.setValido(true);
					respuesta.setMensaje("Se ha registado el artículo satisfactoriamente");
					respuesta.setData(prmData);
				} 
				else 
				{
					respuesta.setMensaje("No se pudo asignar al especialista");
				}
			} 
			catch (Exception e) 
			{
				logger.error(this.logBase+ " : asignarBandejaBandeja" + e.getMessage());
				e.printStackTrace();
				respuesta.setMensaje("Hubo un error al asignar al especialista");
			}
			return respuesta;
		}
		
		
		public int enviarCorreoEspecialista(BandejaDenuncia prmDataBandejaEspecialista) throws ServicioException{
			
				
				
				/***************************************************************************/
				/**********               Obtener mensaje para correo              *********/
				/***************************************************************************/
				
				FormatoCorreo formatoCorreo = new FormatoCorreo();
				long idFormatoCorreo = 1;
				formatoCorreo = formatoCorreoService.buscarPorId(idFormatoCorreo);
//				String titulo = "";
			
				String asunto = "Se ha asignado la denuncia "+ prmDataBandejaEspecialista.getCodigoDenuncia() +" para su seguimiento.";
				
				
				String texto1 = "";
				String texto2 = "";
				String texto3 = "";
				String texto4 = "";
				String texto5 = "";
				String texto6 = "";
				String texto7 = "";
				
				String mensaje = formatoCorreo.getMensaje();
				
				texto1 ="La denuncia " + prmDataBandejaEspecialista.getCodigoDenuncia() + " ha sido asignada para su seguimiento.";
			
			
				mensaje = mensaje.replace("TextoCorreo1", texto1);
				mensaje = mensaje.replace("TextoCorreo2", texto2);
				mensaje = mensaje.replace("TextoCorreo3", texto3);
				mensaje = mensaje.replace("TextoCorreo4", texto4);
				mensaje = mensaje.replace("TextoCorreo5", texto5);
				mensaje = mensaje.replace("TextoCorreo6", texto6);
				mensaje = mensaje.replace("TextoCorreo7", texto7);
				
			
					
				
				
				/************ Especialista Asignado****************/
				
					
					
						Bandeja bandeja = new Bandeja();
						bandeja = bandejaService.buscarPorId(prmDataBandejaEspecialista.getIdBandeja());
						List<ContactoPersona> lstContactoPersona = null;
						if(bandeja!=null){
							
							ContactoPersona contactoPersona = new ContactoPersona();
							
							contactoPersona.setTipoPersona(bandeja.getTipoResponsable());
							contactoPersona.setIdPersona(bandeja.getIdResponsable());
						
							lstContactoPersona = (List<ContactoPersona>) contactoPersonaService.listar(contactoPersona);
							
							for (ContactoPersona contacto : lstContactoPersona) {
								
								if(contacto.getTipoContacto()==2){
								
									
									try {
										EmailAttachmentSender.sendEmailWithAttachments(contacto.getValor(), asunto, mensaje, null);
									} catch (AddressException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (MessagingException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
									
								}
							
							}
							
						}
				
						
					
						
					
						
				
					
				
				
				
		
			return 0;
			
		}

		@ResponseBody
		@RequestMapping(value = "/eliminar-especialista-asignado",method = RequestMethod.POST)
		public RespuestaHttp eliminarEspecialistaAsignado(@RequestBody BandejaDetalle prmData,HttpServletRequest request)throws Exception
		{
			RespuestaHttp respuesta = new RespuestaHttp();
			try
			{
				Bandeja prmBandeja = new Bandeja();
				prmBandeja.setTipoResponsable(1);  // 3 -- OEFA  -- 4 EFA
				prmBandeja.setIdResponsable(prmData.getBandeja().getIdResponsable());
				prmBandeja.setEstado(1);
				prmBandeja =bandejaService.validarBandeja(prmBandeja);
				
				
				
				BandejaDetalle prmBandejaDetalle = new BandejaDetalle();
				prmBandejaDetalle.setIdBandeja(prmBandeja.getIdBandeja());
				prmBandejaDetalle.setTipoBandeja(4);
				prmBandejaDetalle.setIdDenuncia(prmData.getIdDenuncia());
			
				
				prmBandejaDetalle = bandejaDetalleService.buscarDenunciaPorIdDenunciaTipoBandejaIdBandeja(prmBandejaDetalle);
				
				if(prmBandejaDetalle!=null){
					
					prmBandejaDetalle.setPrm1(prmData.getPrm1());
					prmBandejaDetalle.setHostIp(UtilRequest.getClientIpAddress(request));
					bandejaDetalleService.eliminar(prmBandejaDetalle);
					respuesta.setValido(true);
					respuesta.setMensaje("Se ha eliminado satisfactoriamente ");
					respuesta.setData(prmData);
				}else{
					respuesta.setMensaje("No se pudo eliminar");
				}
			
				
//				  bandejaDetalleService.insertar(prmData);
				
//				if(prmData!=null)
//				{
//					respuesta.setValido(true);
//					respuesta.setMensaje("Se ha registado la Articulo satisfactoriamente");
//					respuesta.setData(prmData);
//				} 
//				else 
//				{
//					respuesta.setMensaje("No se pudo registrar la Articulo");
//				}
			} 
			catch (Exception e) 
			{
				logger.error(this.logBase+ " : eliminarEspecialistaAsignado" + e.getMessage());
				e.printStackTrace();
				respuesta.setMensaje("Hubo un error al registrar el artículo");
			}
			return respuesta;
		}
		
		
		
}
