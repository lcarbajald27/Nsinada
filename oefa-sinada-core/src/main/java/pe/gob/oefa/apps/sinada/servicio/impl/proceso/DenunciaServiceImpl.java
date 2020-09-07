package pe.gob.oefa.apps.sinada.servicio.impl.proceso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.presentacion.response.RespuestaHttp;
import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.base.util.CodeUtil;
import pe.gob.oefa.apps.base.util.EmailAttachmentSender;
import pe.gob.oefa.apps.base.util.UtilEncrypt;
import pe.gob.oefa.apps.base.util.UtilRequest;
import pe.gob.oefa.apps.base.util.UtilStringParam;
import pe.gob.oefa.apps.base.util.UtilValidator;
import pe.gob.oefa.apps.base.util.properties.UtilProperties;
import pe.gob.oefa.apps.base.webservice.rest.std.bean.STDDenunciaBean;
import pe.gob.oefa.apps.base.webservice.rest.std.bean.STDHojaTramiteBean;
import pe.gob.oefa.apps.base.webservice.rest.std.servicio.STDREST;
import pe.gob.oefa.apps.sinada.bean.general.Auditoria;
import pe.gob.oefa.apps.sinada.bean.general.AuditoriaDetalle;
import pe.gob.oefa.apps.sinada.bean.general.ContactoPersona;
import pe.gob.oefa.apps.sinada.bean.general.Entidad;
import pe.gob.oefa.apps.sinada.bean.general.Persona;
import pe.gob.oefa.apps.sinada.bean.maestros.FormatoCorreo;
import pe.gob.oefa.apps.sinada.bean.maestros.Maestro;
import pe.gob.oefa.apps.sinada.bean.proceso.ArchivoDenuncia;
import pe.gob.oefa.apps.sinada.bean.proceso.Bandeja;
import pe.gob.oefa.apps.sinada.bean.proceso.BandejaDetalle;
import pe.gob.oefa.apps.sinada.bean.proceso.CasoEfa;
import pe.gob.oefa.apps.sinada.bean.proceso.CasoOefa;
import pe.gob.oefa.apps.sinada.bean.proceso.Denuncia;
import pe.gob.oefa.apps.sinada.bean.proceso.Denunciante;
import pe.gob.oefa.apps.sinada.bean.proceso.NormaCaso;
import pe.gob.oefa.apps.sinada.bean.proceso.PersonaOefa;
import pe.gob.oefa.apps.sinada.bean.proceso.view.EntidadCaso;
import pe.gob.oefa.apps.sinada.bean.proceso.view.VistaDenuncia;
import pe.gob.oefa.apps.sinada.bean.seguridad.Perfil;
import pe.gob.oefa.apps.sinada.bean.seguridad.PerfilUsuario;
import pe.gob.oefa.apps.sinada.bean.seguridad.Usuario;
import pe.gob.oefa.apps.sinada.bean.sirefa.Efa;
import pe.gob.oefa.apps.sinada.persistencia.inf.proceso.ArchivoDenunciaDAO;
import pe.gob.oefa.apps.sinada.persistencia.inf.proceso.BandejaDetalleDAO;
import pe.gob.oefa.apps.sinada.persistencia.inf.proceso.DenunciaDAO;
import pe.gob.oefa.apps.sinada.persistencia.inf.proceso.DenuncianteDAO;
import pe.gob.oefa.apps.sinada.persistencia.inf.sirefa.EfaDAO;
import pe.gob.oefa.apps.sinada.servicio.inf.general.AuditoriaDetalleService;
import pe.gob.oefa.apps.sinada.servicio.inf.general.AuditoriaService;
import pe.gob.oefa.apps.sinada.servicio.inf.general.ContactoPersonaService;
import pe.gob.oefa.apps.sinada.servicio.inf.general.EntidadService;
import pe.gob.oefa.apps.sinada.servicio.inf.general.PersonaService;
import pe.gob.oefa.apps.sinada.servicio.inf.maestros.FormatoCorreoService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.ArchivoDenunciaService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.BandejaService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.CasoEfaService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.CasoOefaService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.DenunciaService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.DenuncianteService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.NormaCasoService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.PersonaOefaService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.view.EntidadCasoService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.view.VistaDenunciaService;
import pe.gob.oefa.apps.sinada.servicio.inf.seguridad.PerfilUsuarioService;
import pe.gob.oefa.apps.sinada.servicio.inf.seguridad.UsuarioService;
import pe.gob.oefa.ws.util.AlfrescoUtil;
import pe.gob.oefa.ws.util.GeneradorCodigo;
import pe.gob.oefa.ws.util.ResourceUtil;
import pe.gob.oefa.ws.util.UtilResource;
import pe.gob.oefa.ws.util.VO;

@Service("denunciaService")
//@Transactional(readOnly = true)
public class DenunciaServiceImpl implements DenunciaService {

	// @Autowired
	// private DenunciaDAO denunciaDAO;

	/**************** Auditoria *************************/

	@Autowired
	AuditoriaService auditoriaService;

	@Autowired
	AuditoriaDetalleService auditoriaDetalleService;

	/*********************************************************/

	@Autowired
	DenunciaDAO denunciaDAO;

	@Autowired
	ArchivoDenunciaDAO archivoDenunciaDAO;

	@Autowired
	DenuncianteDAO denuncianteDAO;

	@Autowired
	EfaDAO efaDAO;

	@Autowired
	BandejaService bandejaService;

	@Autowired
	BandejaDetalleDAO bandejaDetalleDAO;

	/**********************************************************/
	@Autowired
	ContactoPersonaService contactoPersonaService;

	@Autowired
	FormatoCorreoService formatoCorreoService;

	@Autowired
	CasoEfaService casoEfaService;

	@Autowired
	CasoOefaService casoOefaService;

	@Autowired
	PersonaOefaService personaOefaService;

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	PerfilUsuarioService perfilUsuarioService;

	@Autowired
	NormaCasoService normaCasoService;

	@Autowired
	ArchivoDenunciaService archivoDenunciaService;

	@Autowired
	EntidadCasoService entidadCasoService;

	@Autowired
	EntidadService entidadService;

	@Autowired
	PersonaService personaService;

	@Autowired
	VistaDenunciaService vistaDenunciaService;

	@Autowired
	DenuncianteService denuncianteService;
	
	
	/**********************************************************/

	private static final long ID_REGISTRO = 17;

	/****************************************/
	
private String logBase = "oefa-sinada-core: DenunciaServiceImpl";
	
	private final Logger logger = Logger.getLogger(this.getClass());

	@Override
	public Long insertar(Denuncia prmBean) throws ServicioException {
		try {
			Long idBean = null;
			idBean = denunciaDAO.insertar(prmBean);

			/******************* Auditoria ****************************/
			if (idBean != 0) {
				String PK_eIdUsuarioDecrypt = "0";
				if (prmBean.getPrm1() != null) {
					UtilEncrypt.init(UtilProperties.getKey("encrypt.key"));

					PK_eIdUsuarioDecrypt = UtilEncrypt.decrypt(prmBean
							.getPrm1());
					System.out.println("PK_eIdUsuarioDecrypt "
							+ PK_eIdUsuarioDecrypt);
				}

				Auditoria auditoria = new Auditoria();
				auditoria.setIdTabla(ID_REGISTRO);
				auditoria.setTipoAuditoria("I");
				auditoria.setIdUsuario(Long.valueOf(PK_eIdUsuarioDecrypt));
				auditoria.setHostIp(prmBean.getHostIp());
				auditoria.setIdRegistro(idBean);

				auditoriaService.insertar(auditoria);

			}

			/******************* Auditoria ****************************/
			return idBean;

		} catch (Exception e) {
			throw new ServicioException(e);
		}

	}

	@Override
	public int actualizar(Denuncia prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int eliminar(Denuncia prmBean) throws ServicioException {
		try {
			return denunciaDAO.eliminar(prmBean);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

	@Override
	public Denuncia buscarPorId(Long prmIdBean) throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return denunciaDAO.buscarPorId(prmIdBean);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

	@Override
	public List<?> listar(Denuncia prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return denunciaDAO.listar(prmBean);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

	@Override
	public int generarCodigo(Denuncia prmDenuncia) throws ServicioException {
		try {
			return denunciaDAO.generarCodigo(prmDenuncia);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

	@Override
	public int subirHojaTramite(Denuncia prmDenuncia) throws ServicioException {
		try {
			return denunciaDAO.subirHojaTramite(prmDenuncia);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

	@Override
	public List<Denuncia> buscarDenunciaInvitado(Denuncia prmBean)
			throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return denunciaDAO.buscarDenunciaInvitado(prmBean);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

	@Override
	public int cambiarEstado(Denuncia prmDenuncia) throws ServicioException {
		try {

			/********************* Auditoria ***************************/

			long idAuditoria = 0;
			Denuncia prmDataOld = obtenerDatosAntiguos(prmDenuncia);

			String PK_eIdUsuarioDecrypt = "0";
			if (prmDenuncia.getPrm1() != null) {
				UtilEncrypt.init(UtilProperties.getKey("encrypt.key"));

				PK_eIdUsuarioDecrypt = UtilEncrypt.decrypt(prmDenuncia
						.getPrm1());
				System.out.println("PK_eIdUsuarioDecrypt "
						+ PK_eIdUsuarioDecrypt);
			}

			Auditoria auditoria = new Auditoria();
			auditoria.setIdTabla(ID_REGISTRO);
			auditoria.setTipoAuditoria("A");
			auditoria.setIdUsuario(Long.valueOf(PK_eIdUsuarioDecrypt));
			auditoria.setHostIp(prmDenuncia.getHostIp());
			auditoria.setIdRegistro(prmDenuncia.getIdDenuncia());

			idAuditoria = auditoriaService.insertar(auditoria);

			if (idAuditoria != 0) {

				compararAntiguoNuevo(String.valueOf(prmDenuncia.getEstado()),
						String.valueOf(prmDataOld.getEstado()), idAuditoria,
						160);

			}

			/*********************************************/

			return denunciaDAO.cambiarEstado(prmDenuncia);

		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

	public Denuncia obtenerDatosAntiguos(Denuncia prmData) {
		// int id=Integer.valueOf(String.valueOf(prmData.getIdEfa()));
		Denuncia oBean = null;

		try {
			oBean = denunciaDAO.buscarPorId(prmData.getIdDenuncia());
		} catch (PersistenciaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return oBean;

	}

	public void compararAntiguoNuevo(String datoNuevo, String datoAntiguo,
			long codAudiCab, int codCampo) {

		if (!datoNuevo.equals(datoAntiguo)) {
			AuditoriaDetalle audDetalle = new AuditoriaDetalle();
			audDetalle.setIdAuditoria(codAudiCab);
			audDetalle.setIdCampo(codCampo);
			audDetalle.setValor(datoAntiguo);
			try {
				auditoriaDetalleService.insertar(audDetalle);
			} catch (ServicioException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	@Override
	public int subirFichaDenuncia(Denuncia prmDenuncia)
			throws ServicioException {
		try {
			return denunciaDAO.subirFichaDenuncia(prmDenuncia);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

	/**
	 * @throws Exception
	 ******************************************************************************/
	private boolean validarExtension(String nombreArchivo, int tipoArchivo) {
		String tipo_archivo = VO.getExtensionesValidas(tipoArchivo);
		String[] valores = tipo_archivo.split(",");
		String extension = "";
		int index = nombreArchivo.lastIndexOf('.');
		if (index > -1) {
			extension = nombreArchivo.substring(index);
		}
		for (int i = 0; i < valores.length; i++) {
			if (valores[i].equals(extension)) {
				return true;
			}
		}
		return false;
	}

	@Transactional
	@Override
	public RespuestaHttp registraDenunciaGenerico(Denuncia prmDenuncia,
			MultipartFile[] archivo, HttpServletRequest request)
			throws Exception {

		// TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		// return 0;

		try {

			// capturaIdCaso
			// prmDenuncia.getCaso().getIdCaso();

			/**********************************************************************************************/
			/****** Verificacion de Datos y/o archivos *******/
			/**********************************************************************************************/
			String separador = ResourceUtil.getKey("file.path.separador");

			UtilResource gn = new UtilResource();
			RespuestaHttp respuesta = new RespuestaHttp();

			if (prmDenuncia.getMedioRecepcion() == 0) {
				prmDenuncia.setMedioRecepcion(5);
			} else if (prmDenuncia.getMedioRecepcion() != 1) {
				prmDenuncia.setHojaTramite(null);
			}
			if (prmDenuncia.getCentroPoblado() == null) {
				prmDenuncia.setCentroPoblado(new Maestro());
				prmDenuncia.getCentroPoblado().setCodigoRegistro(0);
			}
			GeneradorCodigo n = new GeneradorCodigo();

			if (archivo != null && archivo.length > 0) {
				for (int i = 0; i < archivo.length; i++) {
					String tmpNombreArchivo = archivo[i].getOriginalFilename();
					int caracteres = Integer.parseInt(ResourceUtil
							.getKey("maximo_caracteres_archivo"));
					if (tmpNombreArchivo.length() > caracteres) {
						respuesta
								.setMensaje("El nombre del archivo no debe superar los "
										+ ResourceUtil
												.getKey("maximo_caracteres_archivo")
										+ " caracteres. "
										+ "Intente renombrarlo y vuelva a cargarlo.");
						return respuesta;
					}
					Long peso = Long.parseLong(ResourceUtil
							.getKey("peso_maximo_archivo"));
					if (archivo[i].getSize() > peso) {

					}
					Maestro maestroTipoArchivo = prmDenuncia
							.getLstArchivoMedio().get(i).getTipoArchivo();
					if (maestroTipoArchivo != null) {
						boolean swValido = validarExtension(
								archivo[i].getOriginalFilename(),
								maestroTipoArchivo.getCodigoRegistro());
						if (!swValido) {
							logger.error(this.logBase+ " : log:1 -- swValido "  );
							respuesta
									.setMensaje("El archivo "
											+ archivo[i].getOriginalFilename()
											+ " No es un tipo de archivo válido, reemplace el archivo antes de continuar.");
							return respuesta;
						}
					}
				}

			}
			
			logger.error(this.logBase+ " : log:2"  );

			/***********************************************************************************************/
			/*********																				********/
			/***********************************************************************************************/

			if (prmDenuncia.getPrm1() != null
					&& prmDenuncia.getTipoDenuncia() == 1) {
				String PK_eIdUsuarioDecrypt = "0";
				UtilEncrypt.init(UtilProperties.getKey("encrypt.key"));

				PK_eIdUsuarioDecrypt = UtilEncrypt.decrypt(prmDenuncia
						.getPrm1());
				System.out.println("PK_eIdUsuarioDecrypt "
						+ PK_eIdUsuarioDecrypt);
				Usuario prmUsuario = new Usuario();
				prmUsuario = usuarioService.buscarPorId(Long
						.valueOf(PK_eIdUsuarioDecrypt));
				
				logger.error(this.logBase+ " : log:3"  );
				/**********************************/

				if (prmUsuario != null) {
					logger.error(this.logBase+ " : log:4"  );
					String idPerfil = ResourceUtil
							.getKey("IdPerfilSSO_Denunciante");
					PerfilUsuario prmPerfilUsuario = new PerfilUsuario();
					prmPerfilUsuario.setUsuario(prmUsuario);
					prmPerfilUsuario.setPerfil(new Perfil());
					prmPerfilUsuario.getPerfil().setIdPerfil(
							Long.valueOf(idPerfil));
					prmPerfilUsuario = perfilUsuarioService
							.validarUsuarioPorPerfil(prmPerfilUsuario);
					
					if (prmPerfilUsuario != null) {
						logger.error(this.logBase+ " : log:5"  );
						Denunciante prmDenunciante = new Denunciante();

						prmDenunciante.setIdPersona(prmUsuario.getIdPersona());
						prmDenunciante.setTipoPersona(prmUsuario
								.getTipoPersona());
						prmDenunciante.setEstado(1);

						if (prmDenuncia.getLstDenunciante() == null) {
							prmDenuncia
									.setLstDenunciante(new ArrayList<Denunciante>());
							prmDenuncia.getLstDenunciante().add(prmDenunciante);
						}
					}
				}

				/*********************************/
			}

			/***********************************************************************************************/
			/*********																				********/
			/***********************************************************************************************/

			List<Denunciante> lstDenunciante = null;

			if (prmDenuncia.getLstDenunciante() != null) {
				logger.error(this.logBase+ " : log:6 -- denuncainte"  );
				lstDenunciante = prmDenuncia.getLstDenunciante();
			}

			prmDenuncia.setCodigoAcceso(n.getPassword(n.MAYUSCULAS + n.NUMEROS,
					6));
			System.out.println(prmDenuncia);
			/***************** Auditar Registro denuncia *********************/

			prmDenuncia.setHostIp(UtilRequest.getClientIpAddress(request));
//			 prmDenuncia.setDireccion(CodeUtil.parseEncode(prmDenuncia.getDireccion()));
//			 prmDenuncia.setReferencia(CodeUtil.parseEncode(prmDenuncia.getReferencia()));
//			  
//			 prmDenuncia.setDireccionDenunciado(CodeUtil.parseEncode(prmDenuncia.getDireccionDenunciado()));
//			 prmDenuncia.setNombreDenunciado(CodeUtil.parseEncode(prmDenuncia.getNombreDenunciado()));
			/***********************************************************************************************/
			/*** Manejando Transacciones Denuncia ***/
			/***********************************************************************************************/
			 denunciaDAO.bloquearTablaParaTransaccion();
			long idGenerado = denunciaDAO
					.insertarDenunciaNoTransaccional(prmDenuncia);

			if (idGenerado < 1) {
				logger.error(this.logBase+ " : log:7 -- se bloquea tabla y se inseta la denuncia"  );
				TransactionAspectSupport.currentTransactionStatus()
						.setRollbackOnly();
				respuesta.setMensaje("Hubo un error al registrar la denuncia.");
				return respuesta;
			}

			/***********************************************************************************************/
			/***																						 ***/
			/***********************************************************************************************/

			/********************** Registrar Archivos Denuncia *************************/
			// RegistrarArchivoDenuncia
			if (archivo != null && archivo.length > 0) {
				logger.error(this.logBase+ " : log:8 -- archivos"  );
				int x = 0;

				for (ArchivoDenuncia itemArchivo : prmDenuncia
						.getLstArchivoMedio()) {

					String tmpNombreArchivo = archivo[Integer
							.parseInt(itemArchivo.getPosicionArchivo())]
							.getOriginalFilename();

					String folder = ResourceUtil
							.getKey("file.path.informe-atencion");

					switch (itemArchivo.getTipoArchivo().getCodigoRegistro()) {
					case 1:
						folder = "imagenes" + separador + "{id}" + separador;
						break;
					case 2:
						folder = "audios" + separador + "{id}" + separador;
						break;
					case 3:
						folder = "videos" + separador + "{id}" + separador;
						break;
					case 4:
						folder = "documentos" + separador + "{id}" + separador;
						break;
					case 5:
						folder = "otros" + separador + "{id}" + separador;
						break;
					default:
						break;
					}

					folder = folder.replace("{id}", String.valueOf(idGenerado));

					String flagAlfresco = ResourceUtil.getKey("file.alfreso");

					if (!flagAlfresco.equals("0")) {
						logger.error(this.logBase+ " : log:9 -- valida alfresco"  );
						/** EL SIGUIENTE CODIGO ES LA INTEGRACION CON WSALFRESCO **/
						String uiid = AlfrescoUtil.grabarArchivoAlfresco(
								archivo[Integer.parseInt(itemArchivo
										.getPosicionArchivo())], folder);

						if (VO.isNullOrEmpty(uiid)) {
							respuesta
									.setMensaje("No se pudo archivar el documento en Alfresco.");
							TransactionAspectSupport.currentTransactionStatus()
									.setRollbackOnly();

							return respuesta;
						}
						if (uiid.equals("error") || uiid == "error") {
							respuesta
									.setMensaje("No se pudo archivar el documento en Alfresco.");
							TransactionAspectSupport.currentTransactionStatus()
									.setRollbackOnly();

							return respuesta;

						}

						itemArchivo.setUiid(uiid);

					} else {
						logger.error(this.logBase+ " : log:10 -- sin alfresco"  );
						String rutaArchivoCopiado = gn.copiarArchivo(
								tmpNombreArchivo, folder, archivo[Integer
										.parseInt(itemArchivo
												.getPosicionArchivo())]
										.getInputStream());

						if (VO.isNullOrEmpty(rutaArchivoCopiado)) {
							respuesta
									.setMensaje("No se pudo archivar el documento.");
							TransactionAspectSupport.currentTransactionStatus()
									.setRollbackOnly();
							return respuesta;
						}
					}

					itemArchivo.setRutaArchivoDenuncia(folder);
					itemArchivo.setMimeTypeArchivo(archivo[Integer
							.parseInt(itemArchivo.getPosicionArchivo())]
							.getContentType());
					itemArchivo.setIdDenuncia(idGenerado);
					itemArchivo.setTipoArchivoDenuncia(itemArchivo
							.getTipoArchivo().getCodigoRegistro());
					itemArchivo.setNombreArchivo(CodeUtil
							.parseEncode(tmpNombreArchivo));

					itemArchivo.setDescripcionArchivo(CodeUtil
							.parseEncode(itemArchivo.getDescripcionArchivo()));

					/***********************************************************************************************/
					/*** Manejando Transacciones Archivo Denuncia ***/
					/***********************************************************************************************/
					long idArchivoDenuncia = archivoDenunciaDAO
							.insertar(itemArchivo);

					if (idArchivoDenuncia < 1) {
						TransactionAspectSupport.currentTransactionStatus()
								.setRollbackOnly();
						logger.error(this.logBase+ " : log:11 --  registra archivo"  );
						respuesta
								.setMensaje("No se pudo registrar el archivo.");

						return respuesta;
					}
					/***********************************************************************************************/
					/*** Fin Manejando Transacciones Denuncia ***/
					/***********************************************************************************************/
					x = x + 1;
				}

				/* */
			}

			/***********************************************************************************************/
			/***																						 ***/
			/***********************************************************************************************/

			/********************** Fin Registrar Archivos Denuncia *************************/

			/************************ Generar Codigo ******************************/
			Denuncia prmDataDenunciaObtenida = this.buscarPorId(idGenerado);
			String CodigoDenuncia = "";

			int year = Calendar.getInstance().get(Calendar.YEAR);
			CodigoDenuncia = n
					.generarCodigo(8, "DEN-", String
							.valueOf(prmDataDenunciaObtenida
									.getCodigoDenuncia()))
					+ "-" + String.valueOf(year);
			prmDenuncia.setCodigoDenuncia(CodigoDenuncia);
			/***********************************************************************************************/
			/*** Manejando Transacciones Denuncia Actualizar ***/
			/***********************************************************************************************/
			long resultCodigoDenuncia = denunciaDAO
					.generarCodigoDenunciaNoTransaccional(prmDenuncia);

			if (resultCodigoDenuncia < 1) {
				TransactionAspectSupport.currentTransactionStatus()
						.setRollbackOnly();
				logger.error(this.logBase+ " : log:12 --  no actualiza el codgo de denuncia"  );
				respuesta.setMensaje("Hubo un error al registrar la denuncia.");

				return respuesta;
			}

			/***********************************************************************************************/
			/*** Fin Manejando Transacciones Denuncia Actualizar ***/
			/***********************************************************************************************/

			/************************ Fin Generar Codigo ******************************/

			if (idGenerado > 0) {
				if (lstDenunciante != null && lstDenunciante.size() > 0) {
					for (int i = 0; i < lstDenunciante.size(); i++) {

						lstDenunciante.get(i).setIdDenuncia(idGenerado);
						/***********************************************************************************************/
						/*** Manejando Transacciones Denunciante ***/
						/***********************************************************************************************/

						long idDenunciante = denuncianteDAO
								.insertarDenuncianteNoTransaccional(lstDenunciante
										.get(i));

						if (idDenunciante < 1) {
							TransactionAspectSupport.currentTransactionStatus()
									.setRollbackOnly();
							logger.error(this.logBase+ " : log:13 --  no registra denunciante"  );
							respuesta
									.setMensaje("Hubo un error al registrar al denunciante.");

							return respuesta;
						}
						/***********************************************************************************************/
						/*** Fin Manejando Transacciones Denunciante ***/
						/***********************************************************************************************/

						Bandeja prmBandeja = new Bandeja();
						prmBandeja.setTipoResponsable(lstDenunciante.get(i)
								.getTipoPersona());
						prmBandeja.setIdResponsable(lstDenunciante.get(i)
								.getIdPersona());
						prmBandeja.setEstado(1);

						/*********************************************************************/
						/*** Esto no es transaccional ***/
						/*********************************************************************/
						prmBandeja = bandejaService.validarBandeja(prmBandeja);

						/** Manejando Transaccion **/
						if (prmBandeja.getIdBandeja() < 1) {
							TransactionAspectSupport.currentTransactionStatus()
									.setRollbackOnly();
							logger.error(this.logBase+ " : log:14 --  no al enviar el mensaje al denunciante"  );
							respuesta
									.setMensaje("Hubo un error al obtener al denunciante.");

							return respuesta;
						}

						/*********************************************************************/

						BandejaDetalle prmBandejaDetalle = new BandejaDetalle();
						prmBandejaDetalle.setIdBandeja(prmBandeja
								.getIdBandeja());
						prmBandejaDetalle.setTipoBandeja(1);
						prmBandejaDetalle.setIdDenuncia(prmDenuncia
								.getIdDenuncia());
						prmBandejaDetalle.setTipoAsignacion(0);
						prmBandejaDetalle.setEstado(1);

						prmBandejaDetalle.setHostIp(UtilRequest
								.getClientIpAddress(request));
						prmBandejaDetalle.setPrm1(prmDenuncia.getPrm1());

						/*********************************************************************/
						/*** Esto es transaccional ***/
						/*********************************************************************/

						long idBandejaDenunciante = bandejaDetalleDAO
								.insertarBandejaDetalleNoTransaccional(prmBandejaDetalle);

						if (idBandejaDenunciante < 1) {
							TransactionAspectSupport.currentTransactionStatus()
									.setRollbackOnly();
							logger.error(this.logBase+ " : log:15 --  no al enviar el mensaje al denunciante"  );
							respuesta
									.setMensaje("Ocurrió un error al registrar al denunciante.");

							return respuesta;
						}

						/*********************************************************************/
						/*** Esto es transaccional ***/
						/*********************************************************************/

						ContactoPersona contactoPersona = new ContactoPersona();
						contactoPersona.setTipoPersona(lstDenunciante.get(i)
								.getTipoPersona());
						contactoPersona.setIdPersona(lstDenunciante.get(i)
								.getIdPersona());
						List<ContactoPersona> lstContactoPersona = null;
						lstContactoPersona = (List<ContactoPersona>) contactoPersonaService
								.listar(contactoPersona);

						FormatoCorreo formatoCorreoDB = new FormatoCorreo();
						long idFormatoCorreo = 1;
						formatoCorreoDB = formatoCorreoService
								.buscarPorId(idFormatoCorreo);
						
						for (ContactoPersona contacto : lstContactoPersona) {

							if (contacto.getTipoContacto() == 2) {
								FormatoCorreo formatoCorreo = formatoCorreoDB;

								String texto1 = "";
								String texto2 = "";
								String texto3 = "";
								String texto4 = "";

								String mensaje = formatoCorreo.getMensaje();
								if (lstDenunciante.get(i).getTipoPersona() == 1) {
									texto1 = "Sr(a): "
											+ lstDenunciante.get(i)
													.getPersonaDenunciante()
													.getPrimerNombre()
											+ " "
											+ lstDenunciante.get(i)
													.getPersonaDenunciante()
													.getSegundoNombre()
											+ " "
											+ lstDenunciante.get(i)
													.getPersonaDenunciante()
													.getApellidoPaterno()
											+ " "
											+ lstDenunciante.get(i)
													.getPersonaDenunciante()
													.getApellidoMaterno();
								} else {
									texto1 = "Sres. "
											+ lstDenunciante.get(i)
													.getEntidadDenunciante()
													.getRazonSocial();
								}
								texto2 = "Ha registrado con exito su denuncia ambiental";
								texto3 = "Código denuncia 	 :"
										+ prmDenuncia.getCodigoDenuncia();
								texto4 = "Código acceso :"
										+ prmDenuncia.getCodigoAcceso();
								mensaje = mensaje.replace("TextoCorreo1",
										texto1);
								mensaje = mensaje.replace("TextoCorreo2",
										texto2);
								mensaje = mensaje.replace("TextoCorreo3",
										texto3);
								mensaje = mensaje.replace("TextoCorreo4",
										texto4);
								mensaje = mensaje.replace("TextoCorreo5", "");
								mensaje = mensaje.replace("TextoCorreo6", "");
								mensaje = mensaje.replace("TextoCorreo7", "");

								try {
									EmailAttachmentSender
											.sendEmailWithAttachments(
													contacto.getValor(),
													"Registro Denuncia "
															+ prmDenuncia
																	.getCodigoDenuncia(),
													mensaje, null);

								} catch (Exception ex) {

									TransactionAspectSupport
											.currentTransactionStatus()
											.setRollbackOnly();
									logger.error(this.logBase+ " : log:16 --  no se envio el correo al denunciante"  );
									respuesta
											.setMensaje("Hubo un error al enviar correo al denunciante.");

									System.out.println("Could not send email.");
									ex.printStackTrace();
									return respuesta;
								}

							}

						}

					}
				}
				
				/***************************** Inicio: Correo Anonimo ************************************/
//				if (prmDenuncia.getPrm1() != null && prmDenuncia.getTipoDenuncia() == 1) {
//					String PK_eIdUsuarioDecrypt = "0";
//					UtilEncrypt.init(UtilProperties.getKey("encrypt.key"));
//
//					PK_eIdUsuarioDecrypt = UtilEncrypt.decrypt(prmDenuncia
//							.getPrm1());
//					System.out.println("PK_eIdUsuarioDecrypt "
//							+ PK_eIdUsuarioDecrypt);
//					Usuario prmUsuario = new Usuario();
//					prmUsuario = usuarioService.buscarPorId(Long
//							.valueOf(PK_eIdUsuarioDecrypt));
//
//					if (prmUsuario != null) {
//						
//						ContactoPersona contactoPersona = new ContactoPersona();
//						contactoPersona.setTipoPersona(prmUsuario.getTipoPersona());
//						contactoPersona.setIdPersona(prmUsuario.getIdPersona());
//							
//						List<ContactoPersona> lstContactoPersona = null;
//						lstContactoPersona = (List<ContactoPersona>) contactoPersonaService.listar(contactoPersona);
//						if (lstContactoPersona != null && lstContactoPersona.size() > 0) {
//							//Obtener el formato del correo
//							FormatoCorreo formatoCorreoDB = new FormatoCorreo();
//							long idFormatoCorreo = 1;
//							formatoCorreoDB = formatoCorreoService.buscarPorId(idFormatoCorreo);
//							
//							for (ContactoPersona contacto : lstContactoPersona) {
//								
//								if (contacto.getTipoContacto() == 2) {
//									
//									FormatoCorreo formatoCorreo = formatoCorreoDB;
//									
//									String texto1 = "";
//									String texto2 = "";
//									String texto3 = "";
//									String texto4 = "";
//
//									String mensaje = formatoCorreo.getMensaje();
//									/*if (lstDenunciante.get(i).getTipoPersona() == 1) {
//										texto1 = "Sr(a): "
//												+ lstDenunciante.get(i)
//														.getPersonaDenunciante()
//														.getPrimerNombre()
//												+ " "
//												+ lstDenunciante.get(i)
//														.getPersonaDenunciante()
//														.getSegundoNombre()
//												+ " "
//												+ lstDenunciante.get(i)
//														.getPersonaDenunciante()
//														.getApellidoPaterno()
//												+ " "
//												+ lstDenunciante.get(i)
//														.getPersonaDenunciante()
//														.getApellidoMaterno();
//									} else {
//										texto1 = "Sres. "
//												+ lstDenunciante.get(i)
//														.getEntidadDenunciante()
//														.getRazonSocial();
//									}*/
//									texto1 = "<b>El Denunciante:</b> Anónimo";
//									texto2 = "Ha registrado con éxito la denuncia "+prmDenuncia.getCodigoDenuncia()+" para su atención.";
//									/*
//									texto3 = "Codigo Denuncia 	 :"
//											+ prmDenuncia.getCodigoDenuncia();
//									texto4 = "Codigo Acceso :"
//											+ prmDenuncia.getCodigoAcceso();
//									*/
//									mensaje = mensaje.replace("TextoCorreo1",
//											texto1);
//									mensaje = mensaje.replace("TextoCorreo2",
//											texto2);
//									mensaje = mensaje.replace("TextoCorreo3",
//											texto3);
//									mensaje = mensaje.replace("TextoCorreo4",
//											texto4);
//									mensaje = mensaje.replace("TextoCorreo5", "");
//									mensaje = mensaje.replace("TextoCorreo6", "");
//									mensaje = mensaje.replace("TextoCorreo7", "");
//
//									try {
//										EmailAttachmentSender
//												.sendEmailWithAttachments(
//														contacto.getValor(),
//														"La denuncia "+prmDenuncia.getCodigoDenuncia()+" ha sido registrada.",
//														mensaje, null);
//
//									} catch (Exception ex) {
//
//										TransactionAspectSupport
//												.currentTransactionStatus()
//												.setRollbackOnly();
//
//										respuesta
//												.setMensaje("Hubo un error al registrar al denunciante.");
//
//										System.out.println("Could not send email.");
//										ex.printStackTrace();
//										return respuesta;
//									}
//
//								}
//							}
//						}
//					}
//				}
				/***************************** Final: Correo Anonimo ************************************/
				
				CasoEfa prmCasoEfa = new CasoEfa();

				prmCasoEfa.getCaso().setIdCaso(
						prmDenuncia.getCaso().getIdCaso());
				prmCasoEfa.getEfa().setDepartamento(
						prmDenuncia.getDepartamento());
				prmCasoEfa.getEfa().setProvincia(prmDenuncia.getProvincia());
				prmCasoEfa.getEfa().setDistrito(prmDenuncia.getDistrito());

				List<CasoEfa> lstEfasCaso = buscarEfasPorCaso(prmCasoEfa);
				// List<CasoEfa> lstEfasCaso = (List<CasoEfa>)
				// casoEfaService.listarEfasXCaso(prmCasoEfa);

				int OEFA_ID = Integer.parseInt(ResourceUtil.getKey("OEFA_EFA_ID"));//1990;
				int SERNANP_ID = Integer.parseInt(ResourceUtil.getKey("SERNANP_EFA_ID"));//1914;
				int ANA_ID = Integer.parseInt(ResourceUtil.getKey("ANA_EFA_ID"));//34;

				if (lstEfasCaso != null) {

					for (int i = 0; i < lstEfasCaso.size(); i++) {

						// Efa prmEfa =
						// efaService.buscarPorId(lstEfasCaso.get(i).getEfa().getIdEfa());
						Efa prmEfa = lstEfasCaso.get(i).getEfa();

						/*********************************************/
						Bandeja prmBandeja = new Bandeja();
						prmBandeja.setTipoResponsable(4); // 3 -- OEFA -- 4 EFA
						prmBandeja.setIdEFa(lstEfasCaso.get(i).getEfa()
								.getIdEfa());
						prmBandeja.setEstado(1);
						prmBandeja = bandejaService
								.validarBandejaEfa(prmBandeja);
						BandejaDetalle prmBandejaDetalle = new BandejaDetalle();
						prmBandejaDetalle.setIdBandeja(prmBandeja
								.getIdBandeja());
						prmBandejaDetalle.setTipoBandeja(3);
						prmBandejaDetalle.setIdDenuncia(prmDenuncia
								.getIdDenuncia());
						prmBandejaDetalle.setTipoAsignacion(lstEfasCaso.get(i)
								.getTipoAsignacion().getCodigoRegistro());
						prmBandejaDetalle.setEstado(1);

						// Establecer valor de unicad organica para la bandeja detalle
						
						if (prmEfa.getIdEfa() == OEFA_ID) {
							if (prmDenuncia.getOficinasDesconcentradas() != "") {
								prmBandejaDetalle.setIdUnidadOrganica(efaDAO.buscarCodigoUnidadOrganicaPorOD(prmDenuncia.getOficinasDesconcentradas()));
							}
						}
						if (prmEfa.getIdEfa() == SERNANP_ID) {
							if (prmDenuncia.getAreaConservacion() != "") {
								prmBandejaDetalle.setIdUnidadOrganica(efaDAO.buscarCodigoUnidadOrganicaPorANP(prmDenuncia.getAreaConservacion()));
							} else if (prmDenuncia.getZonaAmortiguamiento() != "") {
								prmBandejaDetalle.setIdUnidadOrganica(efaDAO.buscarCodigoUnidadOrganicaPorZA(prmDenuncia.getZonaAmortiguamiento()));
							}
						}
						if (prmEfa.getIdEfa() == ANA_ID) {
							if (prmDenuncia.getAdminLocales() != "") {
								prmBandejaDetalle.setIdUnidadOrganica(efaDAO.buscarCodigoUnidadOrganicaPorALA(prmDenuncia.getAdminLocales()));
							}
						}						


						if (lstEfasCaso.get(i).getTipoAsignacion()
								.getCodigoRegistro() == 2) {
							prmBandejaDetalle.setEstado(10);
						}

						prmBandejaDetalle.setHostIp(UtilRequest
								.getClientIpAddress(request));
						prmBandejaDetalle.setPrm1(prmDenuncia.getPrm1());

						/*********************************************************************/
						/*** Esto es transaccional ***/
						/*********************************************************************/

						long idBandejaDetalleEfa = bandejaDetalleDAO
								.insertarBandejaDetalleNoTransaccional(prmBandejaDetalle);

						if (idBandejaDetalleEfa < 1) {
							TransactionAspectSupport.currentTransactionStatus()
									.setRollbackOnly();
							logger.error(this.logBase+ " : log:17 -- enviar a la entidades"  );
							respuesta
									.setMensaje("Hubo un error al registrar la Denuncia.");

							return respuesta;
						}

						/*********************************************************************/
						/*** Esto es transaccional ***/
						/*********************************************************************/

						if (prmEfa.getCorreo() != null) {

							FormatoCorreo formatoCorreo = new FormatoCorreo();
							long idFormatoCorreo = 1;
							formatoCorreo = formatoCorreoService
									.buscarPorId(idFormatoCorreo);

							String texto1 = "";
							String texto2 = "";
							String texto3 = "";
							String texto4 = "";

							String mensaje = formatoCorreo.getMensaje();

							texto1 = "Sres. " + prmEfa.getNombre();

							String dataTipoAtencion = "atención";
							if (lstEfasCaso.get(i).getTipoAsignacion()
									.getCodigoRegistro() == 2) {
								dataTipoAtencion = "conocimiento";
							}
							texto2 = "Se ha generado la denuncia "
									+ prmDenuncia.getCodigoDenuncia()
									+ " para su " + dataTipoAtencion;

							mensaje = mensaje.replace("TextoCorreo1", texto1);
							mensaje = mensaje.replace("TextoCorreo2", texto2);
							mensaje = mensaje.replace("TextoCorreo3", texto3);
							mensaje = mensaje.replace("TextoCorreo4", texto4);
							mensaje = mensaje.replace("TextoCorreo5", "");
							mensaje = mensaje.replace("TextoCorreo6", "");
							mensaje = mensaje.replace("TextoCorreo7", "");

							/************************ Registrar Correo **************************/

							String mailTo = prmEfa.getCorreo();
							String subject = "Registro Denuncia "
									+ prmDenuncia.getCodigoDenuncia()
									+ " para su " + dataTipoAtencion;
							// FormatoCorreo_1 fr = new FormatoCorreo_1();
							// String message
							// =fr.formatoCorreoOrganosCompetentes(CodigoDenuncia);

							try {
								EmailAttachmentSender.sendEmailWithAttachments(
										mailTo, subject, mensaje, null);
								System.out.println("Email sent.");
							} catch (Exception ex) {

								TransactionAspectSupport
										.currentTransactionStatus()
										.setRollbackOnly();
								logger.error(this.logBase+ " : log:18 -- no se envio correo a la entidades"  );
								respuesta
										.setMensaje("Hubo un error al registrar la Denuncia.");

								System.out.println("Could not send email.");
								ex.printStackTrace();
								return respuesta;
							}

							/***********************************************************/
						}

					}
				}

				int contadorCompetenciaOefa = 0;
				/******************************************************************************/
				/*** ATENCION CASOS OEFA *********/
				/*******************************************************************************/
				CasoOefa prmCasoOefa = new CasoOefa();
				prmCasoOefa.getCaso().setIdCaso(
						prmDenuncia.getCaso().getIdCaso());

				List<CasoOefa> lstCasoOefa = (List<CasoOefa>) casoOefaService
						.listarEfasXCaso(prmCasoOefa);
				if (lstCasoOefa != null) {

					for (int e = 0; e < lstCasoOefa.size(); e++) {

						Bandeja prmBandeja = new Bandeja();
						prmBandeja.setTipoResponsable(3);// 3 -- OEFA -- 4 EFA
						// prmBandeja.setIdResponsable(lstPersonaOefa.get(s).getIdPersonaOefa());
						prmBandeja.setDireccion(lstCasoOefa.get(e)
								.getDireccionSupervision().getCodigoRegistro());
						prmBandeja.setSubDireccion(lstCasoOefa.get(e)
								.getDireccionEvaluacion().getCodigoRegistro());
						prmBandeja.setCoordinacion(0);
						prmBandeja.setEstado(1);
						prmBandeja = bandejaService
								.validarBandejaOefa(prmBandeja);

						BandejaDetalle prmBandejaDetalle = new BandejaDetalle();
						prmBandejaDetalle.setIdBandeja(prmBandeja
								.getIdBandeja());
						prmBandejaDetalle.setTipoBandeja(3);
						prmBandejaDetalle.setIdDenuncia(prmDenuncia
								.getIdDenuncia());
						prmBandejaDetalle.setTipoAsignacion(lstCasoOefa.get(e)
								.getTipoAsignacion().getCodigoRegistro());
						prmBandejaDetalle.setEstado(1);
						// getTipoAsignacion().getCodigoRegistro() 1 Atencion
						if (lstCasoOefa.get(e).getTipoAsignacion()
								.getCodigoRegistro() == 2) { // 2 : conocimiento
							prmBandejaDetalle.setEstado(10);
						}

						if (lstCasoOefa.get(e).getTipoAsignacion()
								.getCodigoRegistro() == 1) { // 1 : atencion
							contadorCompetenciaOefa = contadorCompetenciaOefa + 1;
						}

						prmBandejaDetalle.setHostIp(UtilRequest
								.getClientIpAddress(request));
						prmBandejaDetalle.setPrm1(prmDenuncia.getPrm1());

						/*********************************************************************/
						/*** Esto es transaccional ***/
						/*********************************************************************/

						long idBandejaDetalleOEFA = bandejaDetalleDAO
								.insertarBandejaDetalleNoTransaccional(prmBandejaDetalle);

						if (idBandejaDetalleOEFA < 1) {
							TransactionAspectSupport.currentTransactionStatus()
									.setRollbackOnly();
							logger.error(this.logBase+ " : log:18 -- se envia OEFA"  );
							respuesta
									.setMensaje("Hubo un error al registrar la Denuncia.");

							return respuesta;
						}

						/*********************************************************************/
						/*** Esto es transaccional ***/
						/*********************************************************************/

						PersonaOefa prmPersonaOefa = new PersonaOefa();
						List<PersonaOefa> lstPersonaOefa = null;
						prmPersonaOefa.getDireccion().setCodigoRegistro(
								prmBandeja.getDireccion());
						prmPersonaOefa.getSubDireccion().setCodigoRegistro(
								prmBandeja.getSubDireccion());
						lstPersonaOefa = (List<PersonaOefa>) personaOefaService
								.listar(prmPersonaOefa);

						for (PersonaOefa personaOefa : lstPersonaOefa) {

							ContactoPersona contactoPersona = new ContactoPersona();
							List<ContactoPersona> lstContactoPersona = null;
							contactoPersona.setTipoPersona(1);
							contactoPersona.setIdPersona(personaOefa
									.getPersona().getIdPersona());
							lstContactoPersona = (List<ContactoPersona>) contactoPersonaService
									.listar(contactoPersona);

							for (ContactoPersona contacto : lstContactoPersona) {

								if (contacto.getTipoContacto() == 2) {
									FormatoCorreo formatoCorreo = new FormatoCorreo();
									long idFormatoCorreo = 1;
									formatoCorreo = formatoCorreoService
											.buscarPorId(idFormatoCorreo);

									String texto1 = "";
									String texto2 = "";
									String texto3 = "";
									String texto4 = "";

									String mensaje = formatoCorreo.getMensaje();

									texto1 = "Sres. "
											+ personaOefa.getPersona()
													.getNombreCompleto();

									String dataTipoAtencionOefa = "atención";
									if (lstCasoOefa.get(e).getTipoAsignacion()
											.getCodigoRegistro() == 2) {
										dataTipoAtencionOefa = "conocimiento";
									}

									texto2 = "Se ha generado la denuncia "
											+ prmDenuncia.getCodigoDenuncia()
											+ " para su "
											+ dataTipoAtencionOefa;

									mensaje = mensaje.replace("TextoCorreo1",
											texto1);
									mensaje = mensaje.replace("TextoCorreo2",
											texto2);
									mensaje = mensaje.replace("TextoCorreo3",
											texto3);
									mensaje = mensaje.replace("TextoCorreo4",
											texto4);
									mensaje = mensaje.replace("TextoCorreo5",
											"");
									mensaje = mensaje.replace("TextoCorreo6",
											"");
									mensaje = mensaje.replace("TextoCorreo7",
											"");
									try {
										EmailAttachmentSender
												.sendEmailWithAttachments(
														contacto.getValor(),
														"Registro Denuncia "
																+ prmDenuncia
																		.getCodigoDenuncia(),
														mensaje, null);
									} catch (Exception e2) {

										TransactionAspectSupport
												.currentTransactionStatus()
												.setRollbackOnly();
										logger.error(this.logBase+ " : log:18 -- correo OEFA"  );
										respuesta
												.setMensaje("Hubo un error al registrar la Denuncia.");
										System.out
												.println("Could not send email.");
										e2.printStackTrace();
										return respuesta;

										// TODO: handle exception
									}

								}
							}
						}

					}

				}

				/****************************************************/
				// STDRegistrarDenuncia
				if (contadorCompetenciaOefa != 0 && (prmDenuncia.getHojaTramite()==null || prmDenuncia.getHojaTramite().length()==0)) {
					// prmDenuncia.getCodigoDenuncia()
					STDDenunciaBean stdDenunciaResponse = STDRegistrarDenuncia(prmDenuncia);
					if (stdDenunciaResponse != null) {
						prmDenuncia.setHojaTramite(stdDenunciaResponse
								.getHOJANUEVA());

						/*********************************************************************/
						/*** Esto es transaccional ***/
						/*********************************************************************/
						int resultHojaTramite = denunciaDAO
								.subirHojaTramiteDenunciaNoTransaccional(prmDenuncia);

						if (resultHojaTramite < 1) {
							logger.error(this.logBase+ " : log:19 -- se registra hoja de tramite"  );
							TransactionAspectSupport.currentTransactionStatus()
									.setRollbackOnly();

							respuesta
									.setMensaje("Hubo un error al registrar la Denuncia.");

							return respuesta;
						}
						/*********************************************************************/
						/*** Esto es transaccional ***/
						/*********************************************************************/
					}
				}
				// STDRegistrarDenuncia(prmDenuncia);

				/***********************************************************************************************/
				/*********																				********/
				/***********************************************************************************************/

				/************************** Bandeja Coordinador sinada : Asignar Denuncias 2 ***********************************/

				BandejaDetalle prmBandejaDetalle = new BandejaDetalle();
				prmBandejaDetalle.setIdBandeja(0);
				prmBandejaDetalle.setTipoBandeja(2);
				prmBandejaDetalle.setIdDenuncia(prmDenuncia.getIdDenuncia());
				prmBandejaDetalle.setTipoAsignacion(0);
				prmBandejaDetalle.setEstado(1);

				prmBandejaDetalle.setHostIp(UtilRequest
						.getClientIpAddress(request));
				prmBandejaDetalle.setPrm1(prmDenuncia.getPrm1());

				/*********************************************************************/
				/*** Esto es transaccional ***/
				/*********************************************************************/
				
				long idBandejaDetalleCoordinador = bandejaDetalleDAO
						.insertarBandejaDetalleNoTransaccional(prmBandejaDetalle);

				if (idBandejaDetalleCoordinador < 1) {
					TransactionAspectSupport.currentTransactionStatus()
							.setRollbackOnly();
					logger.error(this.logBase+ " : log:20 -- se envia a andeja coordinador sinada"  );
					respuesta
							.setMensaje("Hubo un error al registrar la Denuncia.");

					return respuesta;
				}

				/*********************************************************************/
				/*** Esto es transaccional ***/
				/*********************************************************************/

				Usuario prmUsuario = new Usuario();
				String idPerfilCoordinadorSinada = ResourceUtil
						.getKey("IdPerfilSSO_Coordinador");
				prmUsuario.setIdPerfil(Long.valueOf(idPerfilCoordinadorSinada));

				List<Usuario> lstUsuario = null;
				lstUsuario = (List<Usuario>) usuarioService.listar(prmUsuario);

				for (Usuario usuario : lstUsuario) {
					// Bandeja prmBandejaCoordinador = new Bandeja();
					// prmBandejaCoordinador.setTipoResponsable(1);
					// prmBandejaCoordinador.setIdResponsable(/*lstPersonaOefa.get(s).getPersona().getIdPersona()*/usuario.getIdPersona());
					// prmBandejaCoordinador.setEstado(1);
					// prmBandejaCoordinador
					// =bandejaService.validarBandeja(prmBandejaCoordinador);
					//
					// BandejaDetalle prmBandejaDetalle = new BandejaDetalle();
					// prmBandejaDetalle.setIdBandeja(prmBandejaCoordinador.getIdBandeja());
					// prmBandejaDetalle.setTipoBandeja(2);
					// prmBandejaDetalle.setIdDenuncia(prmDenuncia.getIdDenuncia());
					// prmBandejaDetalle.setTipoAsignacion(0);
					// prmBandejaDetalle.setEstado(1);
					//
					// prmBandejaDetalle.setHostIp(UtilRequest.getClientIpAddress(request));
					// prmBandejaDetalle.setPrm1(prmDenuncia.getPrm1());
					// bandejaDetalleService.insertar(prmBandejaDetalle);

					ContactoPersona contactoPersona = new ContactoPersona();
					List<ContactoPersona> lstContactoPersona = null;
					contactoPersona.setTipoPersona(1);
					contactoPersona.setIdPersona(usuario.getIdPersona());
					lstContactoPersona = (List<ContactoPersona>) contactoPersonaService
							.listar(contactoPersona);


					FormatoCorreo formatoCorreoBD = new FormatoCorreo();
					long idFormatoCorreo = 1;
					formatoCorreoBD = formatoCorreoService.buscarPorId(idFormatoCorreo);
					
					for (ContactoPersona contacto : lstContactoPersona) {

						if (contacto.getTipoContacto() == 2) {
							FormatoCorreo formatoCorreo = new FormatoCorreo();
							formatoCorreo = formatoCorreoBD;

							String texto1 = "";
							String texto2 = "";
							String texto3 = "";
							String texto4 = "";

							String mensaje = formatoCorreo.getMensaje();
							//Identificar Denuncia Anonima
							if(prmDenuncia.getTipoDenuncia() == 1){
								texto1 = "<b>El Denunciante:</b> Anónimo";
								texto2 = "Ha registrado con éxito la denuncia "+prmDenuncia.getCodigoDenuncia()+" para su atención.";
								
								mensaje = mensaje.replace("TextoCorreo1", texto1);
								mensaje = mensaje.replace("TextoCorreo2", texto2);
								mensaje = mensaje.replace("TextoCorreo3", texto3);
								mensaje = mensaje.replace("TextoCorreo4", texto4);
								mensaje = mensaje.replace("TextoCorreo5", "");
								mensaje = mensaje.replace("TextoCorreo6", "");
								mensaje = mensaje.replace("TextoCorreo7", "");

								try {
									EmailAttachmentSender.sendEmailWithAttachments(
											contacto.getValor(),
											"La denuncia "+prmDenuncia.getCodigoDenuncia()+" ha sido registrada.",
											mensaje, null);
								} catch (Exception e) {

									TransactionAspectSupport
											.currentTransactionStatus()
											.setRollbackOnly();
									logger.error(this.logBase+ " : log:21 -- se envia a correo coordinador sinada"  );
									respuesta
											.setMensaje("Hubo un error al registrar la Denuncia.");
									e.printStackTrace();
									return respuesta;

								}
							}else{
								/*texto1 = "Sr(a): " + usuario.getNombrePersona();
								texto2 = "Se ha Generado la Denuncia Nº "
										+ prmDenuncia.getCodigoDenuncia()
										+ " para su asignación.";*/
								if (lstDenunciante != null && lstDenunciante.size() > 0) {
									for (int i = 0; i < lstDenunciante.size(); i++) {
										
										formatoCorreo = formatoCorreoBD;
										mensaje = formatoCorreo.getMensaje();
										
										String nombre = "";
										if (lstDenunciante.get(i).getTipoPersona() == 1) {
											nombre = lstDenunciante.get(i)
															.getPersonaDenunciante()
															.getPrimerNombre()
													+ " "
													+ lstDenunciante.get(i)
															.getPersonaDenunciante()
															.getSegundoNombre()
													+ " "
													+ lstDenunciante.get(i)
															.getPersonaDenunciante()
															.getApellidoPaterno()
													+ " "
													+ lstDenunciante.get(i)
															.getPersonaDenunciante()
															.getApellidoMaterno();
										} else {
											nombre = lstDenunciante.get(i)
															.getEntidadDenunciante()
															.getRazonSocial();
										}
										texto1 = "<b>El Denunciante:</b> "+nombre;
										texto2 = "Ha registrado con éxito la denuncia "+prmDenuncia.getCodigoDenuncia()+" para su atención.";
										
										mensaje = mensaje.replace("TextoCorreo1", texto1);
										mensaje = mensaje.replace("TextoCorreo2", texto2);
										mensaje = mensaje.replace("TextoCorreo3", texto3);
										mensaje = mensaje.replace("TextoCorreo4", texto4);
										mensaje = mensaje.replace("TextoCorreo5", "");
										mensaje = mensaje.replace("TextoCorreo6", "");
										mensaje = mensaje.replace("TextoCorreo7", "");

										try {
											EmailAttachmentSender.sendEmailWithAttachments(
													contacto.getValor(),
													"La denuncia "+prmDenuncia.getCodigoDenuncia()+" ha sido registrada.",
													mensaje, null);
										} catch (Exception e) {

											TransactionAspectSupport
													.currentTransactionStatus()
													.setRollbackOnly();
											logger.error(this.logBase+ " : log:22 -- se envia a correo al denunciante"  );
											respuesta
													.setMensaje("Hubo un error al registrar la Denuncia.");
											e.printStackTrace();
											return respuesta;

										}
									}
								}
								
							}
							
//							mensaje = mensaje.replace("TextoCorreo1", texto1);
//							mensaje = mensaje.replace("TextoCorreo2", texto2);
//							mensaje = mensaje.replace("TextoCorreo3", texto3);
//							mensaje = mensaje.replace("TextoCorreo4", texto4);
//							mensaje = mensaje.replace("TextoCorreo5", "");
//							mensaje = mensaje.replace("TextoCorreo6", "");
//							mensaje = mensaje.replace("TextoCorreo7", "");
//
//							try {
//								EmailAttachmentSender.sendEmailWithAttachments(
//										contacto.getValor(),
//										"Registro Denuncia "
//												+ prmDenuncia
//														.getCodigoDenuncia(),
//										mensaje, null);
//							} catch (Exception e) {
//
//								TransactionAspectSupport
//										.currentTransactionStatus()
//										.setRollbackOnly();
//
//								respuesta
//										.setMensaje("Hubo un error al registrar la Denuncia.");
//								e.printStackTrace();
//								return respuesta;
//
//							}

						}

					}
				}
				// prmDenuncia.setRutaArchivoFicha(generarPDFDenuncia(prmDenuncia,
				// 2, 0));
				
				
				
				VistaDenuncia fichaDenuncia = obtenerDatosDenuncia(prmDenuncia);
				RespuestaHttp resPDF=null;
				if(fichaDenuncia!=null){
					logger.error(this.logBase+ " : log:23 -- se genera ficha de denuncia"  );
					resPDF =	obtenerPDFFichaDenunciaHtml(fichaDenuncia);
				}else{
					logger.error(this.logBase+ " : log:24 -- no se registro ficha denuncia"  );
					respuesta.setMensaje("No se pudo registrar la Denuncia");
					TransactionAspectSupport.currentTransactionStatus()
							.setRollbackOnly();
				}
				
				if(resPDF!=null){
					fichaDenuncia.setRutaFichaDenuncia(String.valueOf(resPDF.getData()));
					respuesta.setValido(true);
					respuesta
							.setMensaje("Se ha registado la Denuncia satisfactoriamente");
					// respuesta.setData(prmDenuncia);
					respuesta.setData(fichaDenuncia);
					
				}else{
					logger.error(this.logBase+ " : log:25 -- no se registro denuncia por que no se genero el pdf"  );
					respuesta.setMensaje("No se pudo registrar la Denuncia, inténtelo mas tarde");
					TransactionAspectSupport.currentTransactionStatus()
							.setRollbackOnly();
					return respuesta;
				}
				
				

			} else {
				logger.error(this.logBase+ " : log:26 -- no se inserto la denuncia a la bd "  );
				respuesta.setMensaje("No se pudo registrar la Denuncia, inténtelo mas tarde");
				TransactionAspectSupport.currentTransactionStatus()
						.setRollbackOnly();
				return respuesta;
			}

			return respuesta;

		} catch (Exception e) {
			
			logger.error(this.logBase+ " : registraDenunciaGenerico" + e.getMessage());
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();

			throw new ServicioException(e);

			// TODO: handle exception
		}

	}
	
	
	public RespuestaHttp obtenerPDFFichaDenunciaHtml(VistaDenuncia denuncia) throws Exception
	{
		String url = "";
		RespuestaHttp respuesta = new RespuestaHttp();
		try
		{			
			String separador= ResourceUtil.getKey("file.path.separador");
			String identificadorArchivo = "nombre";
			String rutaBaseFichaDenuncia = ResourceUtil.getKey("file.path.denuncias-ficha");
			
//			String rutaBase = ResourceUtil.getKey("file.path.base");
//			String folderFichaDenuncia = ResourceUtil.getKey("file.path.denuncias-ficha");
			
//			String folder_base_denuncia = "";
			
//			String folder_base_denuncia = ResourceUtil.getKey("file.path.denuncia.archivos.windows.base");
			
//			String serve = ResourceUtil.getKey("file.server");
			
//			if(serve!=null && serve.trim().equals("2")){
//				folder_base_denuncia = ResourceUtil.getKey("file.path.denuncia.archivos.linux.base");
//			}
			
//			folder_base_denuncia = folder_base_denuncia.replace("{separador}", separador);
			
			//String folder = "D:\\oefa\\sinada\\denuncia\\temporales\\medios\\";
			
//			folder_base_denuncia =folderFichaDenuncia+separador+"temporales"+ separador +"medios"+ separador;
			
			String folder = "";
			
			String nombreArchivo = "denuncia.pdf";
			
			//Tipo de documento de denuncia final, cuando la denuncia ya ha sido registrada
			
				identificadorArchivo = VO.dateToString(new Date());
				identificadorArchivo = identificadorArchivo.replace("/", "-");
				//folder = "D:\\oefa\\fichas-denuncia\\";
				folder = ResourceUtil.getKey("file.path.base") + rutaBaseFichaDenuncia ;
				nombreArchivo = denuncia.getCodigoDenuncia()+".pdf";
	
			
				//ByteArrayOutputStream pdf = new ByteArrayOutputStream();
				HttpHeaders responseHeaders = new HttpHeaders();
				
				
					
					Image logoOefa =  Image.getInstance(getClass().getResource("img/oefa-logo-header.png"));
					logoOefa.scaleAbsolute(140,35);
					logoOefa.setAlignment(Element.ALIGN_LEFT);
					logoOefa.setAbsolutePosition(35, 770);
					
					Image logoSinada =  Image.getInstance(getClass().getResource("img/sinada-big.png"));
					logoSinada.scaleAbsolute(150,42);
					logoSinada.setAlignment(Element.ALIGN_RIGHT);
					logoSinada.setAbsolutePosition(PageSize.A4.getWidth()-185, 770);
					
				//BaseFont baseFont = BaseFont.createFont("C:\\Windows\\Fonts\\arialuni.ttf", BaseFont.IDENTITY_H,true);
				//Font fuenteNormal = new Font(baseFont,12, Font.NORMAL);
				//Font fuenteNormal = new Font(Font.FontFamily.UNDEFINED, 12, Font.NORMAL);
				
				//Font fuenteNumeracion = new Font(Font.FontFamily.UNDEFINED, 12, Font.BOLD);
				//Font fuenteNumeracion = new Font(baseFont, 12, Font.BOLD);
				
				//responseHeaders.setContentType(MediaType.valueOf(value)
				responseHeaders.setContentType(MediaType.valueOf("application/pdf;charset=utf-8"));
//				responseHeaders.set
				
				String direccion = folder +identificadorArchivo;
					//url = direccion +"\\"+ nombreArchivo;
					url = direccion + File.separator + nombreArchivo;
					
					File directorio = new File(direccion);
					directorio.mkdirs();
					
					File fichero = new File(url);
					if (fichero.exists()){
						fichero.delete();
					}
					
				//Fuente de Letra
				    Font fuenteNegrita = new Font(FontFamily.HELVETICA, 10, Font.BOLD);
				    Font fuenteNormal = new Font(FontFamily.HELVETICA, 10, Font.NORMAL);
				    Font fuenteCursiva = new Font(FontFamily.HELVETICA, 10, Font.ITALIC);
					
			   
					
				Document documento = new Document(PageSize.A4,25, 25, 25, 25);
				
				FileOutputStream fos = new FileOutputStream(url);
		        //Writer w = new BufferedWriter();

		        //PdfWriter pdfwDemo = PdfWriter.getInstance(documento, new OutputStreamWriter(fos, StandardCharsets.UTF_8));
				PdfWriter pdfwDemo = PdfWriter.getInstance(documento, fos);
				
					documento.open();
//					documento.add(logoOefa);
					
					Paragraph saltoLinea = new Paragraph("\n");
					 
					PdfPCell celdaSaltoLinea = new PdfPCell(new Paragraph("",fuenteNormal));// CeldaTitulo de la Tabla DEnunciante
					celdaSaltoLinea.setBorder(0);
					/******************************************************************/

					Image logoCheck =  Image.getInstance(getClass().getResource("img/check.JPG"));
					logoCheck.scaleAbsolute(12,12);
					
					Image logoCabecera =  Image.getInstance(getClass().getResource("img/logo_cabecera.jpg"));
					logoCabecera.scaleAbsolute(547,80);
					logoCabecera.setAlignment(Element.ALIGN_LEFT);
					//logoCabecera.setAbsolutePosition(33, 770);
					
				    PdfPTable tablaCuerpo = new PdfPTable(2); // 2 Columnas
				    tablaCuerpo.setWidthPercentage(100);
				
						PdfPCell celdaLogo = new PdfPCell(logoCabecera);
						celdaLogo.setBorder(0);
						celdaLogo.setColspan(2);	
						
				    tablaCuerpo.addCell(celdaLogo);
//					 PdfPTable tablaCabecera = new PdfPTable(2); // 2 Columnas
//					 tablaCabecera.setWidthPercentage(100);
						 
						 PdfPTable tablaCabecera01 = new PdfPTable(2); // 2 Columnas
						 tablaCabecera01.setWidthPercentage(100);
						 tablaCabecera01.setWidths(new float[] { 35, 65 });
						 
							 PdfPCell celda01 = new PdfPCell(new Paragraph("Código denuncia ",fuenteNegrita));
							 celda01.setBorder(0);
							 celda01.setBackgroundColor(new BaseColor(238, 238, 239));
							 tablaCabecera01.addCell(celda01);
							 
							 PdfPCell celda01Dato = new PdfPCell(new Paragraph(denuncia.getCodigoDenuncia(),fuenteNormal));
							 celda01Dato.setBorder(0);
							 celda01Dato.setBackgroundColor(new BaseColor(238, 238, 239));
							 tablaCabecera01.addCell(celda01Dato);
						 
						 PdfPCell celda1 = new PdfPCell(tablaCabecera01);
						 celda1.setBorder(0);
						 celda1.setBackgroundColor(new BaseColor(238, 238, 239));
						 celda1.setPaddingLeft(15);
						 celda1.setPaddingTop(10);
						 tablaCuerpo.addCell(celda1);
						 
						 
						 PdfPCell celda2 = new PdfPCell(new Paragraph("Características de la denuncia ", fuenteNegrita));
						 celda2.setBorder(0);
						 celda2.setPaddingTop(10);
						 celda2.setBackgroundColor(new BaseColor(238, 238, 239));
						 tablaCuerpo.addCell(celda2);
						 
						 /*********************************************************/
						 PdfPTable tablaCabecera03 = new PdfPTable(2); // 2 Columnas
						 tablaCabecera03.setWidthPercentage(100);
						 tablaCabecera03.setWidths(new float[] { 35, 65 });
						 
							 PdfPCell celda03_1 = new PdfPCell(new Paragraph("Código de acceso ",fuenteNegrita));
							 celda03_1.setBorder(0);
							 celda03_1.setBackgroundColor(new BaseColor(238, 238, 239));
							 tablaCabecera03.addCell(celda03_1);
							 
							 PdfPCell celda03_1Dato = new PdfPCell(new Paragraph( denuncia.getCodigoacceso(),fuenteNormal));
							 celda03_1Dato.setBorder(0);
							 celda03_1Dato.setBackgroundColor(new BaseColor(238, 238, 239));
							 tablaCabecera03.addCell(celda03_1Dato);
							 
						 PdfPCell celda3 = new PdfPCell(tablaCabecera03);
						 celda3.setBorder(0);
						 celda3.setBackgroundColor(new BaseColor(238, 238, 239));
						 celda3.setPaddingLeft(15);
						 tablaCuerpo.addCell(celda3);
						 
						 
						 PdfPTable tablaCabecera04 = new PdfPTable(2); // 2 Columnas
						 tablaCabecera04.setWidthPercentage(100);
						 tablaCabecera04.setWidths(new float[] { 35, 65 });
						 
							 PdfPCell celda04_1 = new PdfPCell(new Paragraph("Tipo de denuncia ",fuenteNegrita));
							 celda04_1.setBorder(0);
							 celda04_1.setBackgroundColor(new BaseColor(238, 238, 239));
							 tablaCabecera04.addCell(celda04_1);
							 
							 PdfPCell celda04_1Dato = new PdfPCell(new Paragraph( denuncia.getNombreTipoDenuncia(),fuenteNormal));
							 celda04_1Dato.setBorder(0);
							 celda04_1Dato.setBackgroundColor(new BaseColor(238, 238, 239));
							 tablaCabecera04.addCell(celda04_1Dato);
							 
						 PdfPCell celda4 = new PdfPCell(tablaCabecera04);
						 celda4.setBorder(0);
						 celda4.setBackgroundColor(new BaseColor(238, 238, 239));
						 tablaCuerpo.addCell(celda4);
						 
 /****************************************************************************/
						 
						 
						 PdfPTable tablaCabeceraFechaReg = new PdfPTable(2); // 2 Columnas
						 tablaCabeceraFechaReg.setWidthPercentage(100);
						 tablaCabeceraFechaReg.setWidths(new float[] { 35, 65 });
						 
							 PdfPCell celdaFechaCabecera = new PdfPCell(new Paragraph("Fecha de registro",fuenteNegrita));
							 celdaFechaCabecera.setBorder(0);
							 celdaFechaCabecera.setBackgroundColor(new BaseColor(238, 238, 239));
							 tablaCabeceraFechaReg.addCell(celdaFechaCabecera);
							 
							 PdfPCell celdaCabeceraDato = new PdfPCell(new Paragraph(UtilStringParam.getFechaActual(),fuenteNormal));
							 celdaCabeceraDato.setBorder(0);
							 celdaCabeceraDato.setBackgroundColor(new BaseColor(238, 238, 239));
							 tablaCabeceraFechaReg.addCell(celdaCabeceraDato);
							 
						 PdfPCell celdaCabeFecha = new PdfPCell(tablaCabeceraFechaReg);
						 celdaCabeFecha.setBorder(0);
						 celdaCabeFecha.setBackgroundColor(new BaseColor(238, 238, 239));
						 celdaCabeFecha.setPaddingLeft(15);
						
						 tablaCuerpo.addCell(celdaCabeFecha);
						 
						 
						 PdfPTable tablaCabeceraSinDatos = new PdfPTable(2); // 2 Columnas
						 tablaCabeceraSinDatos.setWidthPercentage(100);
						 tablaCabeceraSinDatos.setWidths(new float[] { 35, 65 });
						 
							 PdfPCell celda04_1SinDatos = new PdfPCell(new Paragraph("Medio de recepción ",fuenteNegrita));
							 celda04_1SinDatos.setBorder(0);
							 celda04_1SinDatos.setBackgroundColor(new BaseColor(238, 238, 239));
							 tablaCabeceraSinDatos.addCell(celda04_1SinDatos);
							 
							 PdfPCell celda04_1DatoSinDatos = new PdfPCell(new Paragraph(denuncia.getNombreMedioRecepcion(),fuenteNormal));
							 celda04_1DatoSinDatos.setBorder(0);
							 celda04_1DatoSinDatos.setBackgroundColor(new BaseColor(238, 238, 239));
							 tablaCabeceraSinDatos.addCell(celda04_1DatoSinDatos);
							 
						 PdfPCell celda4SinDatos = new PdfPCell(tablaCabeceraSinDatos);
				
						 celda4SinDatos.setBorder(0);
						 celda4SinDatos.setBackgroundColor(new BaseColor(238, 238, 239));
					
						 tablaCuerpo.addCell(celda4SinDatos);
						 
						 
						 /***************************************************************/
						 
						 PdfPTable tablaCabecera03_2 = new PdfPTable(2); // 2 Columnas
						 tablaCabecera03_2.setWidthPercentage(100);
						 tablaCabecera03_2.setWidths(new float[] { 35, 65 });
						 	 
							 PdfPCell celda003_2 = new PdfPCell(new Paragraph((denuncia.getHojaTramite()!=null) ? "Hoja de trámite " : "",fuenteNegrita));
							 celda003_2.setBorder(0);
							 celda003_2.setBackgroundColor(new BaseColor(238, 238, 239));
							 tablaCabecera03_2.addCell(celda003_2);
							 
							 PdfPCell celda03_2Dato = new PdfPCell(new Paragraph((denuncia.getHojaTramite()!=null) ? denuncia.getHojaTramite() : "",fuenteNormal));
							 celda03_2Dato.setBorder(0);
							 celda03_2Dato.setBackgroundColor(new BaseColor(238, 238, 239));
							 tablaCabecera03_2.addCell(celda03_2Dato);
							 
						 PdfPCell celda3_2 = new PdfPCell(tablaCabecera03_2);
						 celda3_2.setBorder(0);
						 celda3_2.setBackgroundColor(new BaseColor(238, 238, 239));
						 celda3_2.setPaddingLeft(15);
						 celda3_2.setPaddingBottom(10);
						 tablaCuerpo.addCell(celda3_2);
						 
						 
						 PdfPTable tablaCabecera04_2 = new PdfPTable(2); // 2 Columnas
						 tablaCabecera04_2.setWidthPercentage(100);
						 tablaCabecera04_2.setWidths(new float[] { 40, 60 });
						 
							 PdfPCell celda04_2 = new PdfPCell(new Paragraph("",fuenteNegrita));
							 celda04_2.setBorder(0);
							 celda04_2.setBackgroundColor(new BaseColor(238, 238, 239));
							 tablaCabecera04_2.addCell(celda04_2);
							 
							 PdfPCell celda04_2Dato = new PdfPCell(new Paragraph( "",fuenteNormal));
							 celda04_2Dato.setBorder(0);
							 celda04_2Dato.setBackgroundColor(new BaseColor(238, 238, 239));
							 tablaCabecera04_2.addCell(celda04_2Dato);
							 
						 PdfPCell celda4_2 = new PdfPCell(tablaCabecera04_2);
						 celda4_2.setBorder(0);
						 celda4_2.setPaddingBottom(10);
						 celda4_2.setBackgroundColor(new BaseColor(238, 238, 239));
						 tablaCuerpo.addCell(celda4_2);
						 
						 


						
						 
						 
						 /************************************************************************/
						 
				    //documento.add(tablaCabecera);
				    //documento.add(saltoLinea);
				    
				    /***************************** Incio : Izquierod ******************************/
				    PdfPTable tablaCuerpoIzq = new PdfPTable(1); // 2 Columnas
				    tablaCuerpoIzq.setWidthPercentage(100);
				    //PdfPTable table = new PdfPTable(2); // 2 Columnas
					//PdfPCell celda7 = new PdfPCell(new Paragraph(""));   // Tabla GEneral vista previa 1
					//table.addCell(celda7);
						 /****************  Comienza Tabla DEnunciante ************************/
						 PdfPTable tablaDenunciantes = new PdfPTable(1);  // Tabla Denunciante
						 tablaDenunciantes.setWidthPercentage(100);
							
						 PdfPCell celdaDenunciante = new PdfPCell(new Paragraph("1- Datos del denunciante",fuenteNegrita));// CeldaTitulo de la Tabla DEnunciante
						 celdaDenunciante.setBorder(0);
						 tablaDenunciantes.addCell(celdaDenunciante);
						 if(denuncia.getLstDenunciante().size()>0 && denuncia.getTipoDenuncia()==3){
							 int numeroDenunciante = 1;
							 for (Denunciante denunciantes : denuncia.getLstDenunciante()) {
								 if(denunciantes.getTipoPersona()==1){
									 	
									 PdfPTable tablaCabeceraP = new PdfPTable(2);
									 tablaCabeceraP.setWidthPercentage(100);
									 PdfPCell celdaP = new PdfPCell(new Paragraph("Denunciante Nº " + numeroDenunciante,fuenteNegrita));
									 celdaP.setBorder(0);
									 celdaP.setColspan(2);
									 tablaCabeceraP.addCell(celdaP);
									 
									 PdfPCell celdaDenunciante1 = new PdfPCell(tablaCabeceraP);// CeldaTitulo de la Tabla DEnunciante
									 celdaDenunciante1.setBorder(0);
									 celdaDenunciante1.setPaddingLeft(15);
									 tablaDenunciantes.addCell(celdaDenunciante1);
									 
									 /*********************************/
									 PdfPTable tablaCabecera05 = new PdfPTable(2); // 2 Columnas
									 tablaCabecera05.setWidthPercentage(100);
									 tablaCabecera05.setWidths(new float[] { 20, 80 });
									 
										 PdfPCell celda05_1 = new PdfPCell(new Paragraph("Nombre : ",fuenteNegrita));
										 celda05_1.setBorder(0);
										 tablaCabecera05.addCell(celda05_1);
										 
										 PdfPCell celda05_1Dato = new PdfPCell(new Paragraph( denunciantes.getPersonaDenunciante().getPrimerNombre()+ " " +denunciantes.getPersonaDenunciante().getSegundoNombre() + " "+denunciantes.getPersonaDenunciante().getApellidoPaterno()+" "+denunciantes.getPersonaDenunciante().getApellidoMaterno(),fuenteNormal));
										 celda05_1Dato.setBorder(0);
										 tablaCabecera05.addCell(celda05_1Dato);
										 
									 /********************************/
									 PdfPCell celdaDenunciante2 = new PdfPCell(tablaCabecera05);
									 celdaDenunciante2.setBorder(0);
									 celdaDenunciante2.setPaddingLeft(15);
									 tablaDenunciantes.addCell(celdaDenunciante2);
									 
									 /*********************************/
									 PdfPTable tablaCabecera06 = new PdfPTable(2); // 2 Columnas
									 tablaCabecera06.setWidthPercentage(100);
									 tablaCabecera06.setWidths(new float[] { 27, 73 });
									 
										 PdfPCell celda06_1 = new PdfPCell(new Paragraph("Documento : ",fuenteNegrita));
										 celda06_1.setBorder(0);
										 tablaCabecera06.addCell(celda06_1);
										 
										 PdfPCell celda06_1Dato = new PdfPCell(new Paragraph( denunciantes.getPersonaDenunciante().getDocumento(),fuenteNormal));
										 celda06_1Dato.setBorder(0);
										 tablaCabecera06.addCell(celda06_1Dato);
									 /********************************/
									 PdfPCell celdaDenunciante3 = new PdfPCell(tablaCabecera06);
									 celdaDenunciante3.setBorder(0);
									 celdaDenunciante3.setPaddingLeft(15);
									 tablaDenunciantes.addCell(celdaDenunciante3);
									 
									 PdfPTable tablaCabeceraU1 = new PdfPTable(2);
									 tablaCabeceraU1.setWidthPercentage(100);
									 PdfPCell celdaU1 = new PdfPCell(new Paragraph("Ubigeo : ",fuenteNegrita));
									 celdaU1.setBorder(0);
									 celdaU1.setColspan(2);
									 tablaCabeceraU1.addCell(celdaU1);
									 
									 PdfPCell celdaDenunciante4 = new PdfPCell(tablaCabeceraU1);
									 celdaDenunciante4.setBorder(0);
									 celdaDenunciante4.setPaddingLeft(15);
									 tablaDenunciantes.addCell(celdaDenunciante4);
									 
									 PdfPTable tablaCabeceraU1Dato = new PdfPTable(2);
									 tablaCabeceraU1Dato.setWidthPercentage(100);
									 PdfPCell celdaU1Dato = new PdfPCell(new Paragraph(denunciantes.getPersonaDenunciante().getNombreDepartamento().toUpperCase()+" / "+denunciantes.getPersonaDenunciante().getNombreProvincia().toUpperCase()+" / "+denunciantes.getPersonaDenunciante().getNombreDistrito().toUpperCase(),fuenteNormal));
									 celdaU1Dato.setBorder(0);
									 celdaU1Dato.setColspan(2);
									 tablaCabeceraU1Dato.addCell(celdaU1Dato);
									 
									 PdfPCell celdaDenunciante5 = new PdfPCell(tablaCabeceraU1Dato);
									 celdaDenunciante5.setBorder(0);
									 celdaDenunciante5.setPaddingLeft(15);
									 tablaDenunciantes.addCell(celdaDenunciante5);
									 
									 /*********************************/
									 PdfPTable tablaCabecera07 = new PdfPTable(2); // 2 Columnas
									 tablaCabecera07.setWidthPercentage(100);
									 tablaCabecera07.setWidths(new float[] { 22, 78 });
									 	 
										 PdfPCell celda07_1 = new PdfPCell(new Paragraph("Dirección : ",fuenteNegrita));
										 celda07_1.setBorder(0);
										 tablaCabecera07.addCell(celda07_1);
										 
										 PdfPCell celda07_1Dato = new PdfPCell(new Paragraph(denunciantes.getPersonaDenunciante().getDireccion(),fuenteNormal));
										 celda07_1Dato.setBorder(0);
										 tablaCabecera07.addCell(celda07_1Dato);
									 /********************************/
									 PdfPCell celdaDenunciante6 = new PdfPCell(tablaCabecera07);
									 celdaDenunciante6.setBorder(0);
									 celdaDenunciante6.setPaddingLeft(15);
									 tablaDenunciantes.addCell(celdaDenunciante6);
									 
								 }else{
									 PdfPTable tablaCabeceraP = new PdfPTable(2);
									 tablaCabeceraP.setWidthPercentage(100);
									 PdfPCell celdaP = new PdfPCell(new Paragraph("Denunciante Nº " + numeroDenunciante,fuenteNegrita));
									 celdaP.setBorder(0);
									 celdaP.setColspan(2);
									 tablaCabeceraP.addCell(celdaP);
									 
									 PdfPCell celdaDenunciante1 = new PdfPCell(tablaCabeceraP);// CeldaTitulo de la Tabla DEnunciante
									 celdaDenunciante1.setBorder(0);
									 celdaDenunciante1.setPaddingLeft(15);
									 tablaDenunciantes.addCell(celdaDenunciante1);
									 
									 /*********************************/
									 PdfPTable tablaCabecera05 = new PdfPTable(2); // 2 Columnas
									 tablaCabecera05.setWidthPercentage(100);
									 tablaCabecera05.setWidths(new float[] { 15, 85 });
									 	 
										 PdfPCell celda05_1 = new PdfPCell(new Paragraph("RUC : ",fuenteNegrita));
										 celda05_1.setBorder(0);
										 tablaCabecera05.addCell(celda05_1);
										 
										 PdfPCell celda05_1Dato = new PdfPCell(new Paragraph(denunciantes.getEntidadDenunciante().getRuc(),fuenteNormal));
										 celda05_1Dato.setBorder(0);
										 tablaCabecera05.addCell(celda05_1Dato);
									 /********************************/
									 
									 PdfPCell celdaDenunciante2 = new PdfPCell(tablaCabecera05);
									 celdaDenunciante2.setBorder(0);
									 celdaDenunciante2.setPaddingLeft(15);
									 tablaDenunciantes.addCell(celdaDenunciante2);
									 
									 /*********************************/
									 PdfPTable tablaCabecera06 = new PdfPTable(2); // 2 Columnas
									 tablaCabecera06.setWidthPercentage(100);
									 tablaCabecera06.setWidths(new float[] { 30, 70 });
									 	 
										 PdfPCell celda06_1 = new PdfPCell(new Paragraph("Razon social : ",fuenteNegrita));
										 celda06_1.setBorder(0);
										 tablaCabecera06.addCell(celda06_1);
										 
										 PdfPCell celda06_1Dato = new PdfPCell(new Paragraph(denunciantes.getEntidadDenunciante().getRazonSocial(),fuenteNormal));
										 celda06_1Dato.setBorder(0);
										 tablaCabecera06.addCell(celda06_1Dato);
									 /********************************/
										 
									 PdfPCell celdaDenunciante3 = new PdfPCell(tablaCabecera06);
									 celdaDenunciante3.setBorder(0);
									 celdaDenunciante3.setPaddingLeft(15);
									 tablaDenunciantes.addCell(celdaDenunciante3);
									 
									 /*********************************/
									 PdfPTable tablaCabecera07 = new PdfPTable(2); // 2 Columnas
									 tablaCabecera07.setWidthPercentage(100);
									 tablaCabecera07.setWidths(new float[] { 45, 55 });
									 	 
										 PdfPCell celda07_1 = new PdfPCell(new Paragraph("Representante legal :",fuenteNegrita));
										 celda07_1.setBorder(0);
										 tablaCabecera07.addCell(celda07_1);
										 
										 PdfPCell celda07_1Dato = new PdfPCell(new Paragraph(denunciantes.getEntidadDenunciante().getNomRepresentante(),fuenteNormal));
										 celda07_1Dato.setBorder(0);
										 tablaCabecera07.addCell(celda07_1Dato);
									 /********************************/
									 
									 PdfPCell celdaDenunciante4 = new PdfPCell(tablaCabecera07);
									 celdaDenunciante4.setBorder(0);
									 celdaDenunciante4.setPaddingLeft(15);
									 tablaDenunciantes.addCell(celdaDenunciante4);
									 /*********************************/
									 PdfPTable tablaCabecera08 = new PdfPTable(2); // 2 Columnas
									 tablaCabecera08.setWidthPercentage(100);
									 tablaCabecera08.setWidths(new float[] { 20, 80 });
									 	 
										 PdfPCell celda08_1 = new PdfPCell(new Paragraph("Cargo : ",fuenteNegrita));
										 celda08_1.setBorder(0);
										 tablaCabecera08.addCell(celda08_1);
										 
										 PdfPCell celda08_1Dato = new PdfPCell(new Paragraph(denunciantes.getEntidadDenunciante().getNomCargo(),fuenteNormal));
										 celda08_1Dato.setBorder(0);
										 tablaCabecera08.addCell(celda08_1Dato);
									 /********************************/
									 
									 PdfPCell celdaDenunciante5 = new PdfPCell(tablaCabecera08);
									 celdaDenunciante5.setBorder(0);
									 celdaDenunciante5.setPaddingLeft(15);
									 tablaDenunciantes.addCell(celdaDenunciante5);
									 
//									 PdfPCell celdaDenunciante6 = new PdfPCell(new Paragraph("Dirección fiscal" + denunciantes.getEntidadDenunciante().getDireccion()));
//									 celdaDenunciante6.setBorder(0);
//									 tablaDenunciantes.addCell(celdaDenunciante6);

									 PdfPTable tablaCabeceraU1 = new PdfPTable(2);
									 tablaCabeceraU1.setWidthPercentage(100);
									 PdfPCell celdaU1 = new PdfPCell(new Paragraph("Ubigeo : ",fuenteNegrita));
									 celdaU1.setBorder(0);
									 celdaU1.setColspan(2);
									 tablaCabeceraU1.addCell(celdaU1);

									 PdfPCell celdaDenunciante6 = new PdfPCell(tablaCabeceraU1);
									 celdaDenunciante6.setBorder(0);
									 celdaDenunciante6.setPaddingLeft(15);
									 tablaDenunciantes.addCell(celdaDenunciante6);
									 
									 PdfPTable tablaCabeceraU1Dato = new PdfPTable(2);
									 tablaCabeceraU1Dato.setWidthPercentage(100);
									 PdfPCell celdaU1Dato = new PdfPCell(new Paragraph(denunciantes.getEntidadDenunciante().getNombreDepartamento()+" / "+denunciantes.getEntidadDenunciante().getNombreProvincia()+" / "+denunciantes.getEntidadDenunciante().getNombreDistrito(),fuenteNormal));
									 celdaU1Dato.setColspan(2);
									 celdaU1Dato.setBorder(0);
									 tablaCabeceraU1Dato.addCell(celdaU1Dato);
									 
									 PdfPCell celdaDenunciante7 = new PdfPCell(tablaCabeceraU1Dato);
									 celdaDenunciante7.setBorder(0);
									 celdaDenunciante7.setPaddingLeft(15);
									 tablaDenunciantes.addCell(celdaDenunciante7);

									 /*********************************/
									 PdfPTable tablaCabecera09 = new PdfPTable(2); // 2 Columnas
									 tablaCabecera09.setWidthPercentage(100);
									 tablaCabecera09.setWidths(new float[] { 35, 65 });
									 	 
										 PdfPCell celda09_1 = new PdfPCell(new Paragraph("Dirección fiscal : ",fuenteNegrita));
										 celda09_1.setBorder(0);
										 tablaCabecera09.addCell(celda09_1);
										 
										 PdfPCell celda09_1Dato = new PdfPCell(new Paragraph(denunciantes.getEntidadDenunciante().getDireccion(),fuenteNormal));
										 celda09_1Dato.setBorder(0);
										 tablaCabecera09.addCell(celda09_1Dato);
									 /********************************/
									 
									 PdfPCell celdaDenunciante8 = new PdfPCell(tablaCabecera09);
									 celdaDenunciante8.setBorder(0);
									 celdaDenunciante8.setPaddingLeft(15);
									 tablaDenunciantes.addCell(celdaDenunciante8);									 
									 
								 }
								
								 numeroDenunciante = numeroDenunciante+1;
								}
							 
						 }else{
							 PdfPCell celdaDenunciante1 = new PdfPCell(new Paragraph(denuncia.getNombreTipoDenuncia(),fuenteNormal));// CeldaTitulo de la Tabla DEnunciante
							 celdaDenunciante1.setBorder(0);
							 celdaDenunciante1.setPaddingLeft(15);
							 tablaDenunciantes.addCell(celdaDenunciante1);
						 }
						 
						 tablaDenunciantes.addCell(celdaSaltoLinea);
						 
						 PdfPCell celda7 = new PdfPCell(tablaDenunciantes); 
						 celda7.setBorder(0);
						 tablaCuerpoIzq.addCell(celda7);
						 
						 //celda7.addElement(tablaDenunciantes);
						  /**************************************************************/
						 /*															   */
						 /***************************************************************/
						 
						 /****************  Comienza Tabla DAtos de la denuncia ************************/
						 PdfPTable tablaDatosDenuncia = new PdfPTable(1);  // Tabla Datos Denuncia
						 tablaDatosDenuncia.setWidthPercentage(100);
							
						 
						 PdfPCell celdaDatosDenuncia = new PdfPCell(new Paragraph("2- Datos de la denuncia",fuenteNegrita));// CeldaTitulo de Datos Denuncia
						 celdaDatosDenuncia.setBorderColor(BaseColor.WHITE);
						 tablaDatosDenuncia.addCell(celdaDatosDenuncia);
						
						 /***********************************/
						 
						 PdfPTable tablaCabeceraHD = new PdfPTable(2);
						 tablaCabeceraHD.setWidthPercentage(100);
						 PdfPCell celdaHD = new PdfPCell(new Paragraph("Hechos denunciados :",fuenteNegrita));
						 celdaHD.setBorderColor(BaseColor.WHITE);
						 celdaHD.setColspan(2);
						 tablaCabeceraHD.addCell(celdaHD);
						 
						 PdfPCell hechosDenunciados = new PdfPCell(tablaCabeceraHD);//
						 hechosDenunciados.setBorderColor(BaseColor.WHITE);
						 hechosDenunciados.setPaddingLeft(15);
						 
						 tablaDatosDenuncia.addCell(hechosDenunciados);
						 
						 PdfPTable tablaProblematica = new PdfPTable(1); // 2 Columnas
						 tablaProblematica.setWidthPercentage(100);
						 //tablaProblematica.setWidths(new float[] { 35, 65 });
						 
							 PdfPCell celdaProblematicaTitulo = new PdfPCell(new Paragraph("Problemática ",fuenteNegrita));
							 celdaProblematicaTitulo.setBorderColor(BaseColor.WHITE);
							 tablaProblematica.addCell(celdaProblematicaTitulo);
							 
							 PdfPCell celdaProblematicaDato = new PdfPCell(new Paragraph( denuncia.getNombreProblematica(),fuenteNormal));
							 celdaProblematicaDato.setBorderColor(BaseColor.WHITE);
							 tablaProblematica.addCell(celdaProblematicaDato);
						 
						 PdfPCell celdaProblematica = new PdfPCell(tablaProblematica);
						 celdaProblematica.setBorder(0);
						 celdaProblematica.setPaddingLeft(15);
						 tablaDatosDenuncia.addCell(celdaProblematica);
						 
						 //PdfPCell problematica = new PdfPCell(new Paragraph("ProblemÃ¡tica " + denuncia.getNombreProblematica()));// 
						 //tablaDatosDenuncia.addCell(problematica);
						 
						 PdfPCell debidoA = new PdfPCell(new Paragraph("¿Debido a?",fuenteNegrita));//fuenteNegrita
						 debidoA.setBorderColor(BaseColor.WHITE);
						 debidoA.setPaddingLeft(15);
						 tablaDatosDenuncia.addCell(debidoA);
						 
						 /*********************************/
						 PdfPTable tablaCabeceraX = new PdfPTable(2); // 2 Columnas
						 tablaCabeceraX.setWidthPercentage(100);
						 tablaCabeceraX.setWidths(new float[] { 5, 95 });
							  
							 PdfPCell celda_X = new PdfPCell(logoCheck);
							 celda_X.setBorder(0);
							 tablaCabeceraX.addCell(celda_X);
							 
							 PdfPCell celdaXDato = new PdfPCell(new Paragraph(denuncia.getNombreDebidoaNivel1(),fuenteNormal));
							 celdaXDato.setBorder(0);
							 tablaCabeceraX.addCell(celdaXDato);
						 /********************************/
						 
						 PdfPCell debidoA1 = new PdfPCell(tablaCabeceraX);//fuenteNormal
						 debidoA1.setBorder(0);
						 debidoA1.setPaddingLeft(15);
						 tablaDatosDenuncia.addCell(debidoA1);
						 
						 if(denuncia.getIdDebidoa2()!=0L){
							 
							 /*********************************/
							 PdfPTable tablaCabeceraY = new PdfPTable(2); // 2 Columnas
							 tablaCabeceraY.setWidthPercentage(100);
							 tablaCabeceraY.setWidths(new float[] { 5, 95 });
							 	 
								 PdfPCell celda_Y = new PdfPCell(logoCheck);
								 celda_Y.setBorder(0);
								 tablaCabeceraY.addCell(celda_Y);
								 
								 PdfPCell celdaYDato = new PdfPCell(new Paragraph(denuncia.getNombreDebidoaNivel2(),fuenteNormal));
								 celdaYDato.setBorder(0);
								 tablaCabeceraY.addCell(celdaYDato);
							 /********************************/
							 
							 PdfPCell debidoA2 = new PdfPCell(tablaCabeceraY);//fuenteNormal
							 debidoA2.setBorder(0);
							 debidoA2.setPaddingLeft(15);
							 tablaDatosDenuncia.addCell(debidoA2);
						 }
						 
						 if(denuncia.getIdDebidoa3()!=0L){
							 /*********************************/
							 PdfPTable tablaCabeceraY = new PdfPTable(2); // 2 Columnas
							 tablaCabeceraY.setWidthPercentage(100);
							 tablaCabeceraY.setWidths(new float[] { 5, 95 });
							 	 
								 PdfPCell celda_Y = new PdfPCell(logoCheck);
								 celda_Y.setBorder(0);
								 tablaCabeceraY.addCell(celda_Y);
								 
								 PdfPCell celdaYDato = new PdfPCell(new Paragraph(denuncia.getNombreDebidoaNivel3(),fuenteNormal));
								 celdaYDato.setBorder(0);
								 tablaCabeceraY.addCell(celdaYDato);
							 /********************************/
							 PdfPCell debidoA3 = new PdfPCell(tablaCabeceraY);//fuenteNormal
							 debidoA3.setBorder(0);
							 debidoA3.setPaddingLeft(15);
							 tablaDatosDenuncia.addCell(debidoA3);
						 }
						 
						 
						 
						 PdfPCell dondeSucede = new PdfPCell(new Paragraph("¿Dónde sucede?",fuenteNegrita));//fuenteNegrita
						 dondeSucede.setBorder(0);
						 dondeSucede.setPaddingLeft(15);
						 tablaDatosDenuncia.addCell(dondeSucede);
						 
						 /*********************************/
						 PdfPTable tablaCabeceraA = new PdfPTable(2); // 2 Columnas
						 tablaCabeceraA.setWidthPercentage(100);
						 tablaCabeceraA.setWidths(new float[] { 5, 95 });
						 	 
							 PdfPCell celda_A = new PdfPCell(logoCheck);
							 celda_A.setBorder(0);
							 tablaCabeceraA.addCell(celda_A);
							 
							 PdfPCell celdaADato = new PdfPCell(new Paragraph(denuncia.getNombreZonaSuceso1(),fuenteNormal));
							 celdaADato.setBorder(0);
							 tablaCabeceraA.addCell(celdaADato);
						 /********************************/
						 
						 PdfPCell dondeSucede1 = new PdfPCell(tablaCabeceraA);// fuenteNormal
						 dondeSucede1.setBorder(0);
						 dondeSucede1.setPaddingLeft(15);
						 tablaDatosDenuncia.addCell(dondeSucede1);
						 
						 if(denuncia.getIdZonaSuceso2()!=0L){
							 
							 /*********************************/
							 PdfPTable tablaCabeceraB = new PdfPTable(2); // 2 Columnas
							 tablaCabeceraB.setWidthPercentage(100);
							 tablaCabeceraB.setWidths(new float[] { 5, 95 });
							 	 
								 PdfPCell celda_B = new PdfPCell(logoCheck);
								 celda_B.setBorder(0);
								 tablaCabeceraB.addCell(celda_B);
								 
								 PdfPCell celdaBDato = new PdfPCell(new Paragraph(denuncia.getNombreZonaSuceso2(),fuenteNormal));
								 celdaBDato.setBorder(0);
								 tablaCabeceraB.addCell(celdaBDato);
							 /********************************/
							 
							 PdfPCell dondeSucede2 = new PdfPCell(tablaCabeceraB);// fuenteNormal
							 dondeSucede2.setBorder(0);
							 dondeSucede2.setPaddingLeft(15);
							 tablaDatosDenuncia.addCell(dondeSucede2);
						 }
						 
						 if(denuncia.getIdZonaSuceso3()!=0L){
							 
							 /*********************************/
							 PdfPTable tablaCabeceraC = new PdfPTable(2); // 2 Columnas
							 tablaCabeceraC.setWidthPercentage(100);
							 tablaCabeceraC.setWidths(new float[] { 5, 95 });
							 	 
								 PdfPCell celda_C = new PdfPCell(logoCheck);
								 celda_C.setBorder(0);
								 tablaCabeceraC.addCell(celda_C);
								 
								 PdfPCell celdaCDato = new PdfPCell(new Paragraph(denuncia.getNombreZonaSuceso3(),fuenteNormal));
								 celdaCDato.setBorder(0);
								 tablaCabeceraC.addCell(celdaCDato);
							 /********************************/
							 
							 PdfPCell dondeSucede3 = new PdfPCell(tablaCabeceraC);// fuenteNormal
							 dondeSucede3.setBorder(0);
							 dondeSucede3.setPaddingLeft(15);
							 tablaDatosDenuncia.addCell(dondeSucede3);
							 
						 }
						 
						 /***********************************/
						 
						 PdfPCell dondeOcurren = new PdfPCell(new Paragraph("¿Dónde ocurren?",fuenteNegrita));// 
						 dondeOcurren.setBorder(0);
						 dondeOcurren.setPaddingLeft(15);
						 tablaDatosDenuncia.addCell(dondeOcurren);
						 
						 PdfPCell dondeOcurren1 = new PdfPCell(new Paragraph(denuncia.getNombreDepartamento()+" - "+denuncia.getNombreProvincia()+" - " +denuncia.getNombreDistrito() ,fuenteNormal));// 
						 dondeOcurren1.setBorder(0);
						 dondeOcurren1.setPaddingLeft(17);
						 tablaDatosDenuncia.addCell(dondeOcurren1);
						 
						 /*********************************/
						 if(denuncia.getNombreCentroPoblado()!=null){
							 PdfPTable tablaCabecera10 = new PdfPTable(2); // 2 Columnas
							 tablaCabecera10.setWidthPercentage(100);
							 tablaCabecera10.setWidths(new float[] { 33, 67 });
							 	 
								 PdfPCell celda10_1 = new PdfPCell(new Paragraph("Centro poblado : ",fuenteNegrita));
								 celda10_1.setBorder(0);
								 tablaCabecera10.addCell(celda10_1);
								 
								 PdfPCell celda10_1Dato = new PdfPCell(new Paragraph(denuncia.getNombreCentroPoblado(),fuenteNormal));
								 celda10_1Dato.setBorder(0);
								 tablaCabecera10.addCell(celda10_1Dato);
								 
								 
								 /********************************/
								 
								 PdfPCell dondeOcurren2 = new PdfPCell(tablaCabecera10);// 
								 dondeOcurren2.setBorder(0);
								 dondeOcurren2.setPaddingLeft(16);
								 tablaDatosDenuncia.addCell(dondeOcurren2);
						 }
				
						 
						 /*******************************/
						 PdfPTable tablaCabecera13_1 = new PdfPTable(2); // 2 Columnas
						 tablaCabecera13_1.setWidthPercentage(100);
						 tablaCabecera13_1.setWidths(new float[] { 26, 74 });
						 	 
							 PdfPCell celda13_3 = new PdfPCell(new Paragraph("Dirección : ",fuenteNegrita));
							 celda13_3.setBorder(0);
							 tablaCabecera13_1.addCell(celda13_3);
							 
							 PdfPCell celda13_3Dato = new PdfPCell(new Paragraph(denuncia.getDireccion(),fuenteNormal));
							 celda13_3Dato.setBorder(0);
							 tablaCabecera13_1.addCell(celda13_3Dato);
						 /********************************/
							 
						 PdfPCell dondeOcurren3 = new PdfPCell(tablaCabecera13_1);// 
						 dondeOcurren3.setBorder(0);
						 dondeOcurren3.setPaddingLeft(16);
						 tablaDatosDenuncia.addCell(dondeOcurren3);
						 
						 /*******************************/
						 PdfPTable tablaCabecera13_2 = new PdfPTable(2); // 2 Columnas
						 tablaCabecera13_2.setWidthPercentage(100);
						 tablaCabecera13_2.setWidths(new float[] { 27, 73 });
						 	 
							 PdfPCell celda13_4 = new PdfPCell(new Paragraph("Referencia : ",fuenteNegrita));
							 celda13_4.setBorder(0);
							 tablaCabecera13_2.addCell(celda13_4);
							 
							 PdfPCell celda13_4Dato = new PdfPCell(new Paragraph(denuncia.getReferencia(),fuenteNormal));
							 celda13_4Dato.setBorder(0);
							 tablaCabecera13_2.addCell(celda13_4Dato);
						 /********************************/
						 PdfPCell dondeOcurren4 = new PdfPCell(tablaCabecera13_2); 
						 dondeOcurren4.setBorder(0);
						 dondeOcurren4.setPaddingLeft(16);
						 tablaDatosDenuncia.addCell(dondeOcurren4);
						 
						 

						 //tablaDatosDenuncia.addCell(celdaSaltoLinea);
						 
						 celda7 = new PdfPCell(tablaDatosDenuncia); 
						 celda7.setBorder(0);
						 tablaCuerpoIzq.addCell(celda7);
						 

					 PdfPCell celdaCuerpoIzq = new PdfPCell(tablaCuerpoIzq); 
					 celdaCuerpoIzq.setBorder(0);
				//Mostrar tabla izquierda
				tablaCuerpo.addCell(celdaCuerpoIzq);
				/******************************* Final : Izquierda *************************/
				/******************************* Incio : Derecho *************************/
				
			    PdfPTable tablaCuerpoDer = new PdfPTable(1); // 2 Columnas
			    tablaCuerpoDer.setWidthPercentage(100);
				
					 //celda7.addElement(tablaDatosDenuncia);
					 //tablaCabecera.addCell(celda7);
					 
					 
					 /**************************************************************/
					 /*															   */
					 /***************************************************************/ 
					 
					 //PdfPCell celda8 = new PdfPCell(new Paragraph(""));
					 //tablaCabecera.addCell(celda8);
					 
					 
					 PdfPTable tablaDatosDenunciado = new PdfPTable(1);  // Tabla Datos denunciado
					 tablaDatosDenunciado.setWidthPercentage(100);
					 
					 PdfPCell celdaDatosDenunciado = new PdfPCell(new Paragraph("3- Datos del denunciado",fuenteNegrita));
					 celdaDatosDenunciado.setBorder(0);
					 tablaDatosDenunciado.addCell(celdaDatosDenunciado);
					 
					 if(denuncia.getTipoResponsable()!=0 && denuncia.getResponsableProblema()!=0){
						 /*******************************/
						 PdfPTable tablaCabecera11 = new PdfPTable(2); // 2 Columnas
						 tablaCabecera11.setWidthPercentage(100);
						 tablaCabecera11.setWidths(new float[] { 20, 80 });
						 	 
							 PdfPCell celda11_1 = new PdfPCell(new Paragraph("Nombre : ",fuenteNegrita));
							 celda11_1.setBorder(0);
							 tablaCabecera11.addCell(celda11_1);
							 
							 PdfPCell celda11_1Dato = new PdfPCell(new Paragraph(denuncia.getNombreDenunciado(),fuenteNormal));
							 celda11_1Dato.setBorder(0);
							 tablaCabecera11.addCell(celda11_1Dato);
						 /********************************/
						 PdfPCell celdaDatosDenunciado1 = new PdfPCell(tablaCabecera11);
						 celdaDatosDenunciado1.setBorder(0);
						 celdaDatosDenunciado1.setPaddingLeft(15);
						 tablaDatosDenunciado.addCell(celdaDatosDenunciado1);
						 /*******************************/
						 PdfPTable tablaCabecera12 = new PdfPTable(2); // 2 Columnas
						 tablaCabecera12.setWidthPercentage(100);
						 tablaCabecera12.setWidths(new float[] { 27, 73  });
						 	 
							 PdfPCell celda12_1 = new PdfPCell(new Paragraph("Documento : ",fuenteNegrita));
							 celda12_1.setBorder(0);
							 tablaCabecera12.addCell(celda12_1);
							 
							 PdfPCell celda12_1Dato = new PdfPCell(new Paragraph(denuncia.getDocumentoDenunciado(),fuenteNormal));
							 celda12_1Dato.setBorder(0);
							 tablaCabecera12.addCell(celda12_1Dato);
						 /********************************/
						 PdfPCell celdaDatosDenunciado2 = new PdfPCell(tablaCabecera12);
						 celdaDatosDenunciado2.setBorder(0);
						 celdaDatosDenunciado2.setPaddingLeft(15);
						 tablaDatosDenunciado.addCell(celdaDatosDenunciado2);
						 
						 PdfPTable tablaCabeceraU1 = new PdfPTable(2);
						 tablaCabeceraU1.setWidthPercentage(100);
						 PdfPCell celdaU1 = new PdfPCell(new Paragraph("Ubigeo : ",fuenteNegrita));
						 celdaU1.setBorder(0);
						 celdaU1.setColspan(2);
						 tablaCabeceraU1.addCell(celdaU1);
						 
						 PdfPCell celdaDatosDenunciado3 = new PdfPCell(tablaCabeceraU1);
						 celdaDatosDenunciado3.setBorder(0);
						 celdaDatosDenunciado3.setPaddingLeft(15);
						 tablaDatosDenunciado.addCell(celdaDatosDenunciado3);
						 
						 PdfPTable tablaCabeceraU1Dato = new PdfPTable(2);
						 tablaCabeceraU1Dato.setWidthPercentage(100);
						 PdfPCell celdaU1Dato = new PdfPCell(new Paragraph(denuncia.getNombreDepartamentoDenunciado().toUpperCase()+" / " + denuncia.getNombreProvinciaDenunciado().toUpperCase()+" / "+denuncia.getNombreDistritoDenunciado().toUpperCase(),fuenteNormal));
						 celdaU1Dato.setBorder(0);
						 celdaU1Dato.setColspan(2);
						 tablaCabeceraU1Dato.addCell(celdaU1Dato);
						 
						 PdfPCell celdaDatosDenunciado4 = new PdfPCell(tablaCabeceraU1Dato);
						 celdaDatosDenunciado4.setBorder(0);
						 celdaDatosDenunciado4.setPaddingLeft(15);
						 tablaDatosDenunciado.addCell(celdaDatosDenunciado4);
							 
						 if(denuncia.getDireccionDenunciado()!=null){
							 /*******************************/
							 PdfPTable tablaCabecera13 = new PdfPTable(2); // 2 Columnas
							 tablaCabecera13.setWidthPercentage(100);
							 tablaCabecera13.setWidths(new float[] { 22, 78 });
							 	 
								 PdfPCell celda13_1 = new PdfPCell(new Paragraph("Dirección : ",fuenteNegrita));
								 celda13_1.setBorder(0);
								 tablaCabecera13.addCell(celda13_1);
								 
								 PdfPCell celda13_1Dato = new PdfPCell(new Paragraph(denuncia.getDireccionDenunciado(),fuenteNormal));
								 celda13_1Dato.setBorder(0);
								 tablaCabecera13.addCell(celda13_1Dato);
							 /********************************/
								 
							 PdfPCell celdaDatosDenunciado6 = new PdfPCell(tablaCabecera13);
							 celdaDatosDenunciado6.setBorder(0);
							 celdaDatosDenunciado6.setPaddingLeft(15);
							 tablaDatosDenunciado.addCell(celdaDatosDenunciado6);
						 }
						
						 
						 
						 
					 }else{
						 
						 if(denuncia.getNombreDenunciadoDenuncia()!= null && denuncia.getNombreDenunciadoDenuncia().toString().trim().length() != 0){
							 
							 /*******************************/
							 PdfPTable tablaCabecera11 = new PdfPTable(2); // 2 Columnas
							 tablaCabecera11.setWidthPercentage(100);
							 tablaCabecera11.setWidths(new float[] { 20, 80 });
							 	 
								 PdfPCell celda11_1 = new PdfPCell(new Paragraph("Nombre : ",fuenteNegrita));
								 celda11_1.setBorder(0);
								 tablaCabecera11.addCell(celda11_1);
								 
								 PdfPCell celda11_1Dato = new PdfPCell(new Paragraph(denuncia.getNombreDenunciadoDenuncia(),fuenteNormal));
								 celda11_1Dato.setBorder(0);
								 tablaCabecera11.addCell(celda11_1Dato);
							 /********************************/
							 PdfPCell celdaDatosDenunciado1 = new PdfPCell(tablaCabecera11);
							 celdaDatosDenunciado1.setBorder(0);
							 celdaDatosDenunciado1.setPaddingLeft(15);
							 tablaDatosDenunciado.addCell(celdaDatosDenunciado1);
							 /*******************************/
							 
							 
							 /*******************************/
							 PdfPTable tablaCabecera13 = new PdfPTable(2); // 2 Columnas
							 tablaCabecera13.setWidthPercentage(100);
							 tablaCabecera13.setWidths(new float[] { 22, 78 });
							 	 
								 PdfPCell celda13_1 = new PdfPCell(new Paragraph("Dirección : ",fuenteNegrita));
								 celda13_1.setBorder(0);
								 tablaCabecera13.addCell(celda13_1);
								 
								 PdfPCell celda13_1Dato = new PdfPCell(new Paragraph(denuncia.getDireccionDenunciadoDenuncia(),fuenteNormal));
								 celda13_1Dato.setBorder(0);
								 tablaCabecera13.addCell(celda13_1Dato);
							 /********************************/
								 
							 PdfPCell celdaDatosDenunciado6 = new PdfPCell(tablaCabecera13);
							 celdaDatosDenunciado6.setBorder(0);
							 celdaDatosDenunciado6.setPaddingLeft(15);
							 tablaDatosDenunciado.addCell(celdaDatosDenunciado6);
						
							 
						 }else{

							 PdfPCell celdaDatosDenunciado1 = new PdfPCell(new Paragraph("No se identificó al denunciado",fuenteNormal));
							 celdaDatosDenunciado1.setBorder(0);
							 celdaDatosDenunciado1.setPaddingLeft(15);
							 tablaDatosDenunciado.addCell(celdaDatosDenunciado1);
							 
						 }
						 
					 }

					 tablaDatosDenunciado.addCell(celdaSaltoLinea);
					 
					 PdfPCell celda8 = new PdfPCell(tablaDatosDenunciado);
					 celda8.setBorder(0);
					 tablaCuerpoDer.addCell(celda8);
					 
					 //celda8.addElement(tablaDatosDenunciado);
					 /******************************************************************************/
					 /******************************************************************************/
					 /******************************************************************************/
					 
					 
					
					 
					 PdfPTable tablaMediosProbatorios = new PdfPTable(1);  // Tabla Medios Probatorios
					 tablaMediosProbatorios.setWidthPercentage(100);
						
					 PdfPCell celdaDatosMediosProbatorios = new PdfPCell(new Paragraph("4- Medios probatorios",fuenteNegrita));
					 celdaDatosMediosProbatorios.setBorderColor(BaseColor.WHITE);
					 tablaMediosProbatorios.addCell(celdaDatosMediosProbatorios);
					 
					 
					 if(denuncia.getLstArchivoDenuncia()!=null && denuncia.getLstArchivoDenuncia().size()>0 ){
						 
						 for (ArchivoDenuncia mediosProbatorios : denuncia.getLstArchivoDenuncia()) {
							 Maestro maestroMedioProbatorio = new Maestro();
							 
							
							 PdfPCell celdaDatosMediosProbatorios1 = new PdfPCell(new Paragraph(mediosProbatorios.getNombreTipoArchivo() + " - " + mediosProbatorios.getNombreArchivo(),fuenteNormal));
							 celdaDatosMediosProbatorios1.setBorderColor(BaseColor.WHITE);
							 celdaDatosMediosProbatorios1.setPaddingLeft(16);
							 tablaMediosProbatorios.addCell(celdaDatosMediosProbatorios1);
							 
							 
							 
						}
						 
					 }else{
						 
						 PdfPCell celdaDatosMediosProbatorios1 = new PdfPCell(new Paragraph("No se subió medios probatorios",fuenteNormal));
						 celdaDatosMediosProbatorios1.setBorderColor(BaseColor.WHITE);
						 celdaDatosMediosProbatorios1.setPaddingLeft(16);
						 tablaMediosProbatorios.addCell(celdaDatosMediosProbatorios1);
						 
					 }
					 /*******************************/
					 //celda8.addElement(tablaMediosProbatorios);
					 tablaMediosProbatorios.addCell(celdaSaltoLinea);
					 
					 celda8 = new PdfPCell(tablaMediosProbatorios);
					 celda8.setBorderColor(BaseColor.WHITE);
					 tablaCuerpoDer.addCell(celda8);
					 
					 
				
					 
					 /*************************************************************************************/
					 
					 
					 PdfPTable tablaEntidadesCompetentes = new PdfPTable(1);  // Tabla Medios Probatorios
					 tablaEntidadesCompetentes.setWidthPercentage(100);
					 
					 PdfPCell celdaEntidadesCompetentes = new PdfPCell(new Paragraph("5- Entidades competentes",fuenteNegrita));
					 celdaEntidadesCompetentes.setBorder(0);
					 tablaEntidadesCompetentes.addCell(celdaEntidadesCompetentes);
					 
					 
					 if(denuncia.getLstEntidadCaso()!=null && denuncia.getLstEntidadCaso().size()>0 ){
						 int x =1;
						 for (EntidadCaso entidadCompetente : denuncia.getLstEntidadCaso()) {
							
							 PdfPCell celdaEntidadesCompetentes1 = new PdfPCell(new Paragraph(x +"-" + entidadCompetente.getNombreEntidad(),fuenteNormal));
							 celdaEntidadesCompetentes1.setBorder(0);
							 celdaEntidadesCompetentes1.setPaddingLeft(16);
							 tablaEntidadesCompetentes.addCell(celdaEntidadesCompetentes1);
							 
							 if(entidadCompetente.getLstNormaCaso()!=null && entidadCompetente.getLstNormaCaso().size()>0){
								 for (NormaCaso normaCaso : entidadCompetente.getLstNormaCaso()) {
									 String descripcionArticulo = "";
									 if(normaCaso.getContenidoNorma().getDescripcionArticulo()!=null && normaCaso.getContenidoNorma().getDescripcionArticulo().length()>1){
										  descripcionArticulo = ", " +  normaCaso.getContenidoNorma().getDescripcionArticulo();
									 }
									 
									 PdfPCell celdaNormas = new PdfPCell(new Paragraph(normaCaso.getContenidoNorma().getNombreTipoDispositivo()+" Nº " +  normaCaso.getContenidoNorma().getNumeroNorma() +", " + " Articulo " + normaCaso.getContenidoNorma().getNumeroArticulo() + descripcionArticulo +".",fuenteCursiva));
									 celdaNormas.setPaddingLeft(16);
									 celdaNormas.setBorder(0);
									 tablaEntidadesCompetentes.addCell(celdaNormas);
									
								}
							 }
							
							x = x+1; 
						}
						 
					 }else{
						 
						 PdfPCell celdaEntidadesCompetentes1 = new PdfPCell(new Paragraph("No hay Entidades competentes",fuenteNormal));
						 celdaEntidadesCompetentes1.setBorder(0);
						 celdaEntidadesCompetentes1.setPaddingLeft(15);
						 tablaEntidadesCompetentes.addCell(celdaEntidadesCompetentes1);
						 
					 }
					 
				
					 /*******************************/
					 //celda8.addElement(tablaEntidadesCompetentes);

					 celda8 = new PdfPCell(tablaEntidadesCompetentes);
					 celda8.setBorder(0);
					 tablaCuerpoDer.addCell(celda8);
					 
				 PdfPCell celdaCuerpoDer = new PdfPCell(tablaCuerpoDer); 
				 celdaCuerpoDer.setBorder(0);
			//Mostrar tabla izquierda
			tablaCuerpo.addCell(celdaCuerpoDer);
					 //tablaCabecera.addCell(celda8);
					 
					 /**********************************************************************************/
					 
					 
					 
					 
					 /********************************************************************/
					 
					 documento.add(tablaCuerpo);
					/*********************************************************************/
					 
					 
					 
					 
//					FormatoCorreo formatoCorreo = new FormatoCorreo();
//					long idPlantilla = 5;
//					formatoCorreo =  formatoCorreoService.buscarPorId(idPlantilla);
//					 
//					String dataHTMLString = formatoCorreo.getMensaje();
					
					
					/*String dataHtml = denuncia.getDataHTML();
					ServletContext sc= request.getServletContext();
					String pathSO=sc.getRealPath("/");
					String logoOEfa =pathSO+ "assets/images/oefa-logo-header.png";
					String logoSinad =pathSO+ "assets/images/logo_sinada.png";
					
					
					
					dataHtml = dataHtml.replace("assets/images/oefa-logo-header.png", logoOEfa);
					dataHtml = dataHtml.replace("assets/images/logo_sinada.png", logoSinad);*/
					
//					dataHtmlBase = dataHtmlBase.replace("DataFichaDenunciaHTMLReemplazo", dataHtml);
//					HTMLWorker htmlWorker = new HTMLWorker(documento);
//					htmlWorker.parse(new StringReader(dataHTMLString));

					documento.close();
					
					String rutaFinal= "fichaDenuncia/"+identificadorArchivo+"/";
					String urlLocal = VO.getBasicPath() + "/"+ rutaFinal + nombreArchivo;
					
					String flagAlfresco = ResourceUtil.getKey("file.alfreso");
					
					
					if(!flagAlfresco.equals("0")) {
						logger.error(this.logBase+ " : log:27 -- se registra al alfreso "  );
						File file = new File(url);
					  	/** EL SIGUIENTE CODIGO ES LA INTEGRACION CON WSALFRESCO **/
						String uiid = AlfrescoUtil.grabarArchivoAlfresco(file, rutaFinal);
												
						if (VO.isNullOrEmpty(uiid)) {
							logger.error(this.logBase+ " : log:28 --  no se registra al alfreso "  );
							TransactionAspectSupport.currentTransactionStatus()
							.setRollbackOnly();
//							respuesta.setMensaje("No se pudo archivar el documento en Alfresco.");
							return null;
						}
						denuncia.setUiid(uiid);
					}
					
					Denuncia prmDenuncia = new Denuncia();
					
					prmDenuncia.setUiid(denuncia.getUiid());
					prmDenuncia.setIdDenuncia(denuncia.getIdDenuncia());
					prmDenuncia.setRutaFicha("/"+rutaFinal);
					
					int result = denunciaDAO.subirFichaDenunciaNoTransaccional(prmDenuncia);
					
					if(result<1){
						logger.error(this.logBase+ " : log:29 --  no se registra a bd la ficha "  );
						TransactionAspectSupport.currentTransactionStatus()
						.setRollbackOnly();
						return null;
					}
					
					
					
					respuesta.setData(urlLocal);
					respuesta.setValido(true);	
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error(this.logBase+ " : log:30  -- error  "  );
			respuesta.setMensaje("Error al obtener la información");
			return null;
		}
		return respuesta;
	}	

	public VistaDenuncia obtenerDatosDenuncia(Denuncia prmData)
			throws Exception {
		
		VistaDenuncia resBean = null;
		
		VistaDenuncia prmVistaDenuncia = new VistaDenuncia();
		
		if(prmData.getIdDenuncia()!=0){
			
			prmVistaDenuncia.setIdDenuncia(prmData.getIdDenuncia());
			
			resBean = vistaDenunciaService.buscarFichaDenunciaPorId(prmVistaDenuncia);
			
		}else{
			if(prmData.getMedioRecepcion()==0){
				prmData.setMedioRecepcion(5);
			}
			
			prmVistaDenuncia.setTipoDenuncia(prmData.getTipoDenuncia());
			prmVistaDenuncia.setMedioRecepcion(prmData.getMedioRecepcion());
			prmVistaDenuncia.setHojaTramite(prmData.getHojaTramite());
			prmVistaDenuncia.setDepartamento(prmData.getDepartamento());
			prmVistaDenuncia.setProvincia(prmData.getProvincia());
			prmVistaDenuncia.setDistrito(prmData.getDistrito());

			if (prmData.getOficinasDesconcentradas() != "" && !efaDAO.existeOD(prmData.getOficinasDesconcentradas())){
				prmData.setOficinasDesconcentradas("");
				prmData.setOficinasDesconcentradasNombre("");
			}
			if (prmData.getAreaConservacion() != "" && !efaDAO.existeANP(prmData.getAreaConservacion())){
				prmData.setAreaConservacion("");
				prmData.setAreaConservacionNombre("");
			}
			if (prmData.getAdminLocales() != "" && !efaDAO.existeALA(prmData.getAdminLocales())){
				prmData.setAdminLocales("");
				prmData.setAdminLocalesNombre("");
			}
			if (prmData.getZonaAmortiguamiento() != "" && !efaDAO.existeZA(prmData.getZonaAmortiguamiento())){
				prmData.setZonaAmortiguamiento("");
				prmData.setZonaAmortiguamientoNombre("");
			}

			prmVistaDenuncia.setOficinasDesconcentradas(prmData.getOficinasDesconcentradas());
			prmVistaDenuncia.setOficinasDesconcentradasNombre(prmData.getOficinasDesconcentradasNombre());
			prmVistaDenuncia.setAreaConservacion(prmData.getAreaConservacion());
			prmVistaDenuncia.setAreaConservacionNombre(prmData.getAreaConservacionNombre());
			prmVistaDenuncia.setAdminLocales(prmData.getAdminLocales());
			prmVistaDenuncia.setAdminLocalesNombre(prmData.getAdminLocalesNombre());
			prmVistaDenuncia.setZonaAmortiguamiento(prmData.getZonaAmortiguamiento());
			prmVistaDenuncia.setZonaAmortiguamientoNombre(prmData.getZonaAmortiguamientoNombre());
			prmVistaDenuncia.setLatitude(prmData.getLatitude());
			prmVistaDenuncia.setLongitude(prmData.getLongitude());
            prmVistaDenuncia.setCoord_x_utm(prmData.getCoord_x_utm());
            prmVistaDenuncia.setCoord_y_utm(prmData.getCoord_y_utm());
            prmVistaDenuncia.setZona(prmData.getZona());

			prmVistaDenuncia.setDireccion(prmData.getDireccion());
			prmVistaDenuncia.setReferencia(prmData.getReferencia());
			prmVistaDenuncia.setTipoResponsable(prmData.getTipo_responsable());
			prmVistaDenuncia.setResponsableProblema(prmData.getResponsableProblema());
			prmVistaDenuncia.setTipoMedio(prmData.getTipoMedio());
			prmVistaDenuncia.setIdCaso(prmData.getCaso().getIdCaso());
			prmVistaDenuncia.setCentroPoblado(prmData.getCodigoCentroPoblado());
			prmVistaDenuncia.setNombreDenunciadoDenuncia(prmData.getNombreDenunciado());
			prmVistaDenuncia.setDireccionDenunciadoDenuncia(prmData.getDireccionDenunciado());
			resBean = vistaDenunciaService.generarVisualizacionPreliminarFichaDenuncia(prmVistaDenuncia);
			
		}
		
		
		if(resBean!=null){
			prmVistaDenuncia = resBean;
			if(resBean.getIdDenuncia()!=0){
				
				prmVistaDenuncia.setLstDenunciante(new ArrayList<Denunciante>());
				Denunciante denunciante = new Denunciante();
				denunciante.setIdDenuncia(prmData.getIdDenuncia());
				
				List<Denunciante> lstDenunciantes =  (List<Denunciante>) denuncianteService.listar(denunciante);
				
				for (Denunciante dataDenunciante : lstDenunciantes) {
					
					if(dataDenunciante.getTipoPersona()==1){
						dataDenunciante.setPersonaDenunciante(personaService.buscarPorId(dataDenunciante.getIdPersona()));
					}
					if(dataDenunciante.getTipoPersona()==2){
						dataDenunciante.setEntidadDenunciante(entidadService.buscarPorId(dataDenunciante.getIdPersona()));
						
					}
					
					prmVistaDenuncia.getLstDenunciante().add(dataDenunciante);
				}
				
				/*******************************************************************/
				
				ArchivoDenuncia prmArchivoDenuncia = new ArchivoDenuncia();
				prmArchivoDenuncia.setIdDenuncia(resBean.getIdDenuncia());
				List<ArchivoDenuncia> lstArchivoDenuncia =  (List<ArchivoDenuncia>) archivoDenunciaService.listar(prmArchivoDenuncia);
				
				if(lstArchivoDenuncia!=null && lstArchivoDenuncia.size()>0){
					prmVistaDenuncia.setLstArchivoDenuncia(lstArchivoDenuncia);
				}
				
			}else{
				prmVistaDenuncia.setLstDenunciante(new ArrayList<Denunciante>());
				prmVistaDenuncia.setLstDenunciante(prmData.getLstDenunciante());
				prmVistaDenuncia.setLstArchivoDenuncia(new ArrayList<ArchivoDenuncia>());
				prmVistaDenuncia.setLstArchivoDenuncia(prmData.getLstArchivoMedio());
			}
		
			
			
			
			/*******************************************************************************/
			
			
			List<EntidadCaso> lstEntidadesCompetentes = new ArrayList<EntidadCaso>();
			
   			EntidadCaso entCas = new EntidadCaso();
   			entCas.setIdCaso(prmData.getCaso().getIdCaso());
   			lstEntidadesCompetentes = (List<EntidadCaso>) entidadCasoService.listar(entCas);
   			
   			
   			/******************************************************************************/
   			
   			if(lstEntidadesCompetentes!=null && lstEntidadesCompetentes.size()>0){
   				prmVistaDenuncia.setLstEntidadCaso(new ArrayList<EntidadCaso>());
   				for (EntidadCaso object : lstEntidadesCompetentes) {
   					
   					if(object.getTipoAsignacion()!=2){
   						if(object.getTipoEntidad()==1){
	   						List<NormaCaso> lstNormaCaso = new ArrayList<NormaCaso>();
	   						NormaCaso prmNormaCaso = new NormaCaso();
	   						prmNormaCaso.setTipoNormaCaso(new Maestro());
	   						prmNormaCaso.getTipoNormaCaso().setCodigoRegistro(1);
	   						prmNormaCaso.setIdCasoOefa(object.getIdCasoEntidad());
	   						lstNormaCaso = (List<NormaCaso>) normaCasoService.listar(prmNormaCaso);
	   						
	   						if(lstNormaCaso!=null && lstNormaCaso.size()>0){
	   							object.setLstNormaCaso(new ArrayList<NormaCaso>());
	   							object.getLstNormaCaso().addAll(lstNormaCaso);
	   							
	   						}
	   						
	   						prmVistaDenuncia.getLstEntidadCaso().add(object);
	   	   	   				
	   					}
   						
   					}
   				
   					
   				
   	   			
   	   				
   					
   					
   	   				
   	   				
   				}
   				
   				
   				
   				
   				CasoEfa prmCasoEfa = new CasoEfa();
   				

   				prmCasoEfa.getCaso().setIdCaso(prmData.getCaso().getIdCaso());
   				prmCasoEfa.getEfa().setDepartamento(prmData.getDepartamento());
   				prmCasoEfa.getEfa().setProvincia(prmData.getProvincia());
   				prmCasoEfa.getEfa().setDistrito(prmData.getDistrito());
   				
   				List<CasoEfa> lstEfasCaso = buscarEfasPorCaso(prmCasoEfa);
   				// Para validacion SINADA
				boolean existe_EFA_OEFA = false;
				boolean existe_EFA_SERNANP = false;
				boolean existe_EFA_ANA = false;
				int OEFA_ID = Integer.parseInt(ResourceUtil.getKey("OEFA_EFA_ID"));
				int SERNANP_ID = Integer.parseInt(ResourceUtil.getKey("SERNANP_EFA_ID"));
				int ANA_ID = Integer.parseInt(ResourceUtil.getKey("ANA_EFA_ID"));
   				/**************************************************************/
   					for (CasoEfa casoEfa : lstEfasCaso) {
   						
   				
   						
   						if(casoEfa.getTipoAsignacion().getCodigoRegistro()!=2){
   							EntidadCaso prmEntidadCaso = new EntidadCaso();
   							prmEntidadCaso.setTipoEntidad(2);
   	   						prmEntidadCaso.setNombreEntidad(casoEfa.getEfa().getNombre());
   	   						prmEntidadCaso.setIdCasoEntidad(casoEfa.getIdCasoEfa());
   	   						prmEntidadCaso.setNombreTipoEntidad("EFA");

   	   						if (casoEfa.getEfa().getIdEfa() == OEFA_ID){
   	   							existe_EFA_OEFA = true;
							}
							if (casoEfa.getEfa().getIdEfa() == SERNANP_ID){
								existe_EFA_SERNANP = true;
							}
							if (casoEfa.getEfa().getIdEfa() == ANA_ID){
								existe_EFA_ANA = true;
							}
//   							documento.add(getLinea(casoEfa.getEfa().getNombre(), fuenteNumeracion, 40));

	   						List<NormaCaso> lstNormaCaso = new ArrayList<NormaCaso>();
	   						NormaCaso prmNormaCaso = new NormaCaso();
	   						prmNormaCaso.setTipoNormaCaso(new Maestro());
	   						prmNormaCaso.getTipoNormaCaso().setCodigoRegistro(2);
	   						prmNormaCaso.setIdCasoEfa(casoEfa.getIdCasoEfa());
	   						lstNormaCaso = (List<NormaCaso>) normaCasoService.listar(prmNormaCaso);
	   						
	   						if(lstNormaCaso!=null && lstNormaCaso.size()>0){
	   							prmEntidadCaso.setLstNormaCaso(new ArrayList<NormaCaso>());
	   							prmEntidadCaso.setLstNormaCaso(lstNormaCaso);
	   						}
//	   						for (NormaCaso normaCaso : lstNormaCaso) {
//	   							documento.add(getLinea("- "+CodeUtil.parseEncode(normaCaso.getContenidoNorma().getNombreTipoDispositivo())
//	   									+" Nº "+CodeUtil.parseEncode(normaCaso.getContenidoNorma().getNumeroNorma())
//	   									+", Articulo Nº " + CodeUtil.parseEncode(normaCaso.getContenidoNorma().getNumeroArticulo())
//	   									+" - "+ CodeUtil.parseEncode(normaCaso.getContenidoNorma().getDescripcionArticulo()), fuenteNormal, 80));
//							}
	   						
	   						prmVistaDenuncia.getLstEntidadCaso().add(prmEntidadCaso); 
   						}
   						
   						
   						
   				
   				
   				/**************************************************************/
   				
   				
   			}

			// Validacion SINADA
   			if (!existe_EFA_OEFA){
   				prmVistaDenuncia.setOficinasDesconcentradas("");
   				prmVistaDenuncia.setOficinasDesconcentradasNombre("");
			}
   			if (!existe_EFA_SERNANP){
   				prmVistaDenuncia.setZonaAmortiguamiento("");
   				prmVistaDenuncia.setZonaAmortiguamientoNombre("");
   				prmVistaDenuncia.setAreaConservacion("");
   				prmVistaDenuncia.setAreaConservacionNombre("");
			}
   			if(!existe_EFA_ANA){
   				prmVistaDenuncia.setAdminLocales("");
   				prmVistaDenuncia.setAdminLocalesNombre("");
			}


   			}
			
		}else{
			
			
			
			
			
			
		}
		
		
		if(prmVistaDenuncia!=null){
			resBean = prmVistaDenuncia;
		}
		return resBean;
		
	}

	private List<CasoEfa> buscarEfasPorCaso(CasoEfa prmCasoEfa)
			throws Exception {

		List<CasoEfa> lst = new ArrayList<CasoEfa>();
		prmCasoEfa.getEfa().setTipoNivel(1);
		List<CasoEfa> lstEfasCaso = (List<CasoEfa>) casoEfaService
				.listarEfasXCasoXNivelYUbigeo(prmCasoEfa);

		if (lstEfasCaso != null && lstEfasCaso.size() > 0) {
			lst.addAll(lstEfasCaso);
		}

		prmCasoEfa.getEfa().setTipoNivel(2);
		List<CasoEfa> lstEfasCasoRegional = (List<CasoEfa>) casoEfaService
				.listarEfasXCasoXNivelYUbigeo(prmCasoEfa);

		if (lstEfasCasoRegional != null && lstEfasCasoRegional.size() > 0) {
			lst.addAll(lstEfasCasoRegional);
		}

		prmCasoEfa.getEfa().setTipoNivel(3);
		prmCasoEfa.getEfa().setTipoGobierno(2);
		List<CasoEfa> lstEfasCasoLocalDistrital = (List<CasoEfa>) casoEfaService
				.listarEfasXCasoXNivelYUbigeo(prmCasoEfa);

		if (lstEfasCasoLocalDistrital != null
				&& lstEfasCasoLocalDistrital.size() > 0) {
			lst.addAll(lstEfasCasoLocalDistrital);
		} else {
			prmCasoEfa.getEfa().setTipoNivel(3);
			prmCasoEfa.getEfa().setTipoGobierno(1);
			List<CasoEfa> lstEfasCasoLocalProvincial = (List<CasoEfa>) casoEfaService
					.listarEfasXCasoXNivelYUbigeo(prmCasoEfa);

			if (lstEfasCasoLocalProvincial != null
					&& lstEfasCasoLocalProvincial.size() > 0) {
				lst.addAll(lstEfasCasoLocalProvincial);
			}
		}

		return lst;

	}

	private STDDenunciaBean STDRegistrarDenuncia(Denuncia prmDenuncia) {
		// TODO Auto-generated method stub
		STDREST stdService = new STDREST();

		STDDenunciaBean stdDenuncia = new STDDenunciaBean();

		stdDenuncia.setCODIGOSINADA(prmDenuncia.getCodigoDenuncia());

		if (prmDenuncia.getTipoDenuncia() == 1) {
			stdDenuncia.setREMITENTE("Anónimo");
		} else {
			Denunciante denunciante = prmDenuncia.getLstDenunciante().get(0);
			if(denunciante.getTipoPersona()==1){
				Persona personaDenunciante = denunciante
						.getPersonaDenunciante();
				stdDenuncia.setREMITENTE(personaDenunciante.getNombres()
						+ " "
						+ (UtilValidator.isNullOrEmpty(personaDenunciante
								.getApellidoPaterno()) ? "" : personaDenunciante
								.getApellidoPaterno())
						+ " "
						+ (UtilValidator.isNullOrEmpty(personaDenunciante
								.getApellidoMaterno()) ? "" : personaDenunciante
								.getApellidoMaterno()));
			}else{
				Entidad entidadDenunciante = denunciante.getEntidadDenunciante();
				
				stdDenuncia.setREMITENTE(entidadDenunciante.getRazonSocial());
			}
			
			// stdDenuncia.setREMITENTE(prmDenuncia.getLstDenunciante().get(0).get);
		}

		StringBuilder sb = new StringBuilder();
		sb.append("Problemática: ");
		sb.append(prmDenuncia.getCaso().getProblematica().getDescripcion());
		sb.append(" - ¿Debido a? nivel I: ");
		sb.append(prmDenuncia.getCaso().getDebidoA1().getDescripcion());
		if (prmDenuncia.getCaso().getDebidoA2() != null
				&& !UtilValidator.isNullOrEmpty(prmDenuncia.getCaso()
						.getDebidoA2().getDescripcion())) {
			sb.append(" - ¿Debido a? nivel II: ");
			sb.append(prmDenuncia.getCaso().getDebidoA2().getDescripcion());
		}
		if (prmDenuncia.getCaso().getDebidoA3() != null
				&& !UtilValidator.isNullOrEmpty(prmDenuncia.getCaso()
						.getDebidoA3().getDescripcion())) {
			sb.append(" - ¿Debido a? nivel III: ");
			sb.append(prmDenuncia.getCaso().getDebidoA3().getDescripcion());
		}
		sb.append(" - Donde sucede nivel I: ");
		sb.append(prmDenuncia.getCaso().getZonasuceso1().getDescripcion());
		if (prmDenuncia.getCaso().getZonasuceso2() != null
				&& prmDenuncia.getCaso().getZonasuceso2().getDescripcion() != null) {
			sb.append(" - Donde sucede nivel II: ");
			sb.append(prmDenuncia.getCaso().getZonasuceso2().getDescripcion());
		}
		if (prmDenuncia.getCaso().getZonasuceso3() != null
				&& prmDenuncia.getCaso().getZonasuceso3().getDescripcion() != null) {
			sb.append(" - Donde sucede nivel III: ");
			sb.append(prmDenuncia.getCaso().getZonasuceso3().getDescripcion());
		}

		stdDenuncia.setDESCRIPCION(sb.toString());

		try {

			STDHojaTramiteBean stdHojaTramite = stdService
					.registrarDenunciaBody(stdDenuncia);
			System.out.println(stdHojaTramite);
			if (stdHojaTramite != null && stdHojaTramite.getPCURSOR() != null
					&& stdHojaTramite.getPCURSOR().length > 0) {
				return UtilValidator
						.isNullOrEmpty(stdHojaTramite.getPCURSOR()[0]
								.getHOJANUEVA()) ? null : stdHojaTramite
						.getPCURSOR()[0];
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public VistaDenuncia obtenerDatosDenunciaGenerico(Denuncia prmData)
			throws ServicioException {
		 
		try {
			return obtenerDatosDenuncia(prmData);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return null;
		
	}

}
