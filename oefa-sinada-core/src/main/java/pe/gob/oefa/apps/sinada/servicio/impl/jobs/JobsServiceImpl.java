package pe.gob.oefa.apps.sinada.servicio.impl.jobs;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.base.util.EmailAttachmentSender;
import pe.gob.oefa.apps.sinada.bean.general.ContactoPersona;
import pe.gob.oefa.apps.sinada.bean.maestros.FormatoCorreo;
import pe.gob.oefa.apps.sinada.bean.proceso.Bandeja;
import pe.gob.oefa.apps.sinada.bean.proceso.BandejaDetalle;
import pe.gob.oefa.apps.sinada.bean.proceso.Denuncia;
import pe.gob.oefa.apps.sinada.bean.proceso.PersonaOefa;
import pe.gob.oefa.apps.sinada.bean.proceso.view.AccionesRealizadas;
import pe.gob.oefa.apps.sinada.bean.proceso.view.BandejaDenuncia;
import pe.gob.oefa.apps.sinada.bean.seguridad.Usuario;
import pe.gob.oefa.apps.sinada.bean.sirefa.Efa;
import pe.gob.oefa.apps.sinada.persistencia.inf.maestros.FormatoCorreoDAO;
import pe.gob.oefa.apps.sinada.persistencia.inf.proceso.view.BandejaDenunciaDAO;
import pe.gob.oefa.apps.sinada.servicio.inf.general.ContactoPersonaService;
import pe.gob.oefa.apps.sinada.servicio.inf.jobs.JobsService;
import pe.gob.oefa.apps.sinada.servicio.inf.maestros.FormatoCorreoService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.BandejaDetalleService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.BandejaService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.PersonaOefaService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.view.AccionesRealizadasService;
import pe.gob.oefa.apps.sinada.servicio.inf.seguridad.UsuarioService;
import pe.gob.oefa.apps.sinada.servicio.inf.sirefa.EfaService;
import pe.gob.oefa.ws.util.ResourceUtil;

@Service("jobsService")
@Transactional(readOnly = true)
public class JobsServiceImpl implements JobsService {

	
	@Autowired
	BandejaDenunciaDAO bandejaDenunciaDAO;
	
	@Autowired 
	PersonaOefaService personaOefaService;
	
	@Autowired
	ContactoPersonaService contactoPersonaService;
	
	@Autowired
	FormatoCorreoService formatoCorreoService;
	
	@Autowired
	EfaService  efaService;
	
	@Autowired
	BandejaService bandejaService;
	
	@Autowired
	AccionesRealizadasService accionesRealizadasService;
	
	@Autowired 
	BandejaDetalleService bandejaDetalleService;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Override
	public Long insertar(Denuncia prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int actualizar(Denuncia prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int eliminar(Denuncia prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Denuncia buscarPorId(Long prmIdBean) throws ServicioException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> listar(Denuncia prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int EnviCorreoNotificacionDenuncias() throws ServicioException {
		
		try {
			
			/*****************************************************************************************/
			/**							         Lista Denuncias rechazadas						    **/
			/*****************************************************************************************/
			// 3 rechazada
			BandejaDenuncia prmDataRechazada = new BandejaDenuncia();
			prmDataRechazada.setEstadoBandejaDetalle(3);
			List<BandejaDenuncia> lstBandejaDenunciaAsignadasRechazadas = bandejaDenunciaDAO.buscarBandejaDeDenunciasEntidadPorEstado(prmDataRechazada);
			for (BandejaDenuncia bandejaDenunciaRechazada : lstBandejaDenunciaAsignadasRechazadas) {
				// al 3er dia habil notifica que aun no acepta o rechaza la denuncia hasta el sexto dia habil
//				if(validarFechaCorreo(bandejaDenunciaRevision)==1){
//					obtenerCorreoEntidades(bandejaDenunciaRevision);
//				}
				// apartir del 6to dia habil notifica al especialista que no atendio la denuncia hasta el 9no dia habil
				if(validarFechaCorreo(bandejaDenunciaRechazada)==2){
					obtenerCorreoEntidades(bandejaDenunciaRechazada);
				}
			
				
			}
			
			
			
			
			
			/*****************************************************************************************/
			/**							         Lista Pendientes   								**/
			/*****************************************************************************************/
			
			BandejaDenuncia prmDataPendiente = new BandejaDenuncia();
			prmDataPendiente.setEstadoBandejaDetalle(1);
			List<BandejaDenuncia> lstBandejaDenunciaAsignadasesPendientes = bandejaDenunciaDAO.buscarBandejaDeDenunciasEntidadPorEstado(prmDataPendiente);
		
			for (BandejaDenuncia bandejaDenunciaPendiente : lstBandejaDenunciaAsignadasesPendientes) {
				
				if(validarFechaCorreo(bandejaDenunciaPendiente)==1){
					//envia correo a ala entidad y especialista que la denuncia esta pendiente para su revision
					obtenerCorreoEntidades(bandejaDenunciaPendiente);
				}
				if(validarFechaCorreo(bandejaDenunciaPendiente)==2){
					obtenerCorreoEntidades(bandejaDenunciaPendiente);
					// Enviar correo al especialista para que informe la no atencion de la denuncia
				} 
//				if(bandejaDenunciaPendiente.getDiasHabilesDenuncia()>=9 && bandejaDenunciaPendiente.getDiasHabilesDenuncia()<=9){
//					// Enviar correo al especialista para que informe la no atencion de la denuncia
//				}
				
			}
			
			
			/*****************************************************************************************/
			/**							         Lista Revision   								    **/
			/*****************************************************************************************/
			BandejaDenuncia prmDataRevision = new BandejaDenuncia();
			prmDataRevision.setEstadoBandejaDetalle(2);
			List<BandejaDenuncia> lstBandejaDenunciaAsignadasesRevision = bandejaDenunciaDAO.buscarBandejaDeDenunciasEntidadPorEstado(prmDataRevision);
			for (BandejaDenuncia bandejaDenunciaRevision : lstBandejaDenunciaAsignadasesRevision) {
				// al 3er dia habil notifica que aun no acepta o rechaza la denuncia hasta el sexto dia habil
				if(validarFechaCorreo(bandejaDenunciaRevision)==1){
					obtenerCorreoEntidades(bandejaDenunciaRevision);
				}
				// apartir del 6to dia habil notifica al especialista que no atendio la denuncia hasta el 9no dia habil
				if(validarFechaCorreo(bandejaDenunciaRevision)==2){
					obtenerCorreoEntidades(bandejaDenunciaRevision);
				}
			
				
			}
		
			/*****************************************************************************************/
			/**							         Lista Atencion   							    	**/
			/*****************************************************************************************/
			
			BandejaDenuncia prmDataAtencion = new BandejaDenuncia();
			prmDataAtencion.setEstadoBandejaDetalle(5);
			List<BandejaDenuncia> lstBandejaDenunciaAsignadasesAtencion = bandejaDenunciaDAO.buscarBandejaDeDenunciasEntidadPorEstado(prmDataAtencion);
		
			for (BandejaDenuncia bandejaDenunciaAtencion : lstBandejaDenunciaAsignadasesAtencion) {
				
				if(validarFechaCorreo(bandejaDenunciaAtencion)==1){
					obtenerCorreoEntidades(bandejaDenunciaAtencion);
				}
				// apartir del 6to dia habil notifica al especialista que no atendio la denuncia hasta el 9no dia habil
				if(validarFechaCorreo(bandejaDenunciaAtencion)==2){
					obtenerCorreoEntidades(bandejaDenunciaAtencion);
				}
				
				
//				if( bandejaDenunciaAtencion.getDiasHabilesPlazo()<=6 ){
//					generarCorreoEntidades(bandejaDenunciaAtencion);
//				}
				
				
			}
			
			
			/*****************************************************************************************/
			/**							         Lista de denuncia en estado con acciones    							    	**/
			/*****************************************************************************************/
			
			BandejaDenuncia prmDataDenunciaConAcciones = new BandejaDenuncia();
			prmDataDenunciaConAcciones.setEstadoBandejaDetalle(6);
			List<BandejaDenuncia> lstBandejaDenunciaAsignadasesConAcciones= bandejaDenunciaDAO.buscarBandejaDeDenunciasEntidadPorEstado(prmDataDenunciaConAcciones);
		
			for (BandejaDenuncia bandejaDenunciaConAcciones : lstBandejaDenunciaAsignadasesConAcciones) {
				
				
				/*************Listar acciones realizadas pendientes a revision por especialista*******************/
				// estado  Pendiente : 1
			AccionesRealizadas prmAccionesRealizadasPendientes = new AccionesRealizadas();	
			prmAccionesRealizadasPendientes.setIdBandejaDetalle(bandejaDenunciaConAcciones.getIdBandejaDetalle());
			prmAccionesRealizadasPendientes.setEstado(1);
			prmAccionesRealizadasPendientes.setTipoAtencionAccion(1);
			
			List<AccionesRealizadas> listaAccionesRealizadasPendientes =  accionesRealizadasService.listarAccionesRealizadasPorEstados(prmAccionesRealizadasPendientes);
				if(listaAccionesRealizadasPendientes!=null && listaAccionesRealizadasPendientes.size()>0){
					bandejaDenunciaConAcciones.setLstAccionesRealizadas(new ArrayList<AccionesRealizadas>());
					bandejaDenunciaConAcciones.setLstAccionesRealizadas(listaAccionesRealizadasPendientes);
					
					obtenerCorreoEntidades(bandejaDenunciaConAcciones);
				}
				
				
				
				
				
				/*************Listar acciones realizadas pendientes a revision por especialista*******************/
				// Estado  observada : 2
			AccionesRealizadas prmAccionesRealizadasObservadas = new AccionesRealizadas();	
			prmAccionesRealizadasObservadas.setIdBandejaDetalle(bandejaDenunciaConAcciones.getIdBandejaDetalle());
			prmAccionesRealizadasObservadas.setEstado(2);
			prmAccionesRealizadasObservadas.setTipoAtencionAccion(1);
			
			List<AccionesRealizadas> listaAccionesRealizadasObservadas =  accionesRealizadasService.listarAccionesRealizadasPorEstados(prmAccionesRealizadasObservadas);
			if(listaAccionesRealizadasObservadas!=null && listaAccionesRealizadasObservadas.size()>0){
				
				bandejaDenunciaConAcciones.setLstAccionesRealizadas(new ArrayList<AccionesRealizadas>());
				bandejaDenunciaConAcciones.setLstAccionesRealizadas(listaAccionesRealizadasObservadas);
				
				obtenerCorreoEntidades(bandejaDenunciaConAcciones);
			}
	

			
			
			
			
			
			/*************Listar ATENCIONES realizadas pendientes a revision por especialista*******************/
			// estado  Pendiente : 1
		AccionesRealizadas prmAtencionesRealizadasPendientes = new AccionesRealizadas();	
		prmAtencionesRealizadasPendientes.setIdBandejaDetalle(bandejaDenunciaConAcciones.getIdBandejaDetalle());
		prmAtencionesRealizadasPendientes.setEstado(1);
		prmAtencionesRealizadasPendientes.setTipoAtencionAccion(2);
		
		List<AccionesRealizadas> listaAtencionesRealizadasPendientes =  accionesRealizadasService.listarAccionesRealizadasPorEstados(prmAtencionesRealizadasPendientes);
			if(listaAtencionesRealizadasPendientes!=null && listaAtencionesRealizadasPendientes.size()>0){
				bandejaDenunciaConAcciones.setLstAccionesRealizadas(new ArrayList<AccionesRealizadas>());
				bandejaDenunciaConAcciones.setLstAccionesRealizadas(listaAtencionesRealizadasPendientes);
				
				obtenerCorreoEntidades(bandejaDenunciaConAcciones);
			}
			
			
			
			
			
			/*************Listar ATENCIONES realizadas pendientes a revision por especialista*******************/
			// Estado  observada : 2
		AccionesRealizadas prmAtencionesRealizadasObservadas = new AccionesRealizadas();	
		prmAtencionesRealizadasObservadas.setIdBandejaDetalle(bandejaDenunciaConAcciones.getIdBandejaDetalle());
		prmAtencionesRealizadasObservadas.setEstado(2);
		prmAtencionesRealizadasObservadas.setTipoAtencionAccion(2);
		
		List<AccionesRealizadas> listaAtencionesRealizadasObservadas =  accionesRealizadasService.listarAccionesRealizadasPorEstados(prmAtencionesRealizadasObservadas);
		if(listaAtencionesRealizadasObservadas!=null && listaAtencionesRealizadasObservadas.size()>0){
			
			bandejaDenunciaConAcciones.setLstAccionesRealizadas(new ArrayList<AccionesRealizadas>());
			bandejaDenunciaConAcciones.setLstAccionesRealizadas(listaAtencionesRealizadasObservadas);
			
			obtenerCorreoEntidades(bandejaDenunciaConAcciones);
		}
				
				
			}
			
			
		
		
		} catch (PersistenciaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return 0;
	}
	
	

	public int generarCorreoEntidades(BandejaDenuncia prmData) throws ServicioException{
		
		
		
		if(prmData.getTipoResponsableBandeja()==3){ // OEFA
			PersonaOefa prmPersonaOefa = new PersonaOefa();
			List<PersonaOefa> lstPersonaOefa = null;
			prmPersonaOefa.getDireccion().setCodigoRegistro(prmData.getDireccionSupervicion());
			prmPersonaOefa.getSubDireccion().setCodigoRegistro(prmData.getSubDireccion());
			lstPersonaOefa = (List<PersonaOefa>) personaOefaService.listar(prmPersonaOefa);
			
			if(lstPersonaOefa!=null){
				
				for (PersonaOefa personaOefa : lstPersonaOefa) {
					
					
					ContactoPersona contactoPersona = new ContactoPersona();
					List<ContactoPersona> lstContactoPersona = null;
					contactoPersona.setTipoPersona(1);
					contactoPersona.setIdPersona(personaOefa.getPersona().getIdPersona());
					lstContactoPersona = (List<ContactoPersona>) contactoPersonaService.listar(contactoPersona);
					
					for (ContactoPersona contacto : lstContactoPersona) {
						
						if(contacto.getTipoContacto()==2){
							enviarCorreo(contacto.getValor(),prmData,personaOefa.getPersona().getNombreCompleto());
							
							
						}
					
					}
					
				}
				
			}
			
		}
		
		
		if(prmData.getTipoResponsableBandeja()==4){  // EFA
			Efa prmEfa =  efaService.buscarPorId(prmData.getIdEfa());
			if(prmEfa!=null && prmEfa.getCorreo()!=null && prmEfa.getNombre()!=null){
				enviarCorreo(prmEfa.getCorreo(),prmData,prmEfa.getNombre());
			}
			
			
			
		}
		
		
		
		
		
		return 0;
		
	}
	
	
	
	
	public int enviarCorreo(String  correo, BandejaDenuncia prmData,String nombreEntidad) throws ServicioException{
		FormatoCorreo formatoCorreo = new FormatoCorreo();
		long idFormatoCorreo = 1;
		formatoCorreo = formatoCorreoService.buscarPorId(idFormatoCorreo);
		
		String texto1 = "";
		String texto2 = "";
		String texto3 = "";
		String texto4 = "";
		String mensaje = formatoCorreo.getMensaje();
		texto1 ="Sres. " +nombreEntidad ;
		//personaOefa.getPersona().getNombreCompleto()
		String asunto = "";
		if(prmData.getEstadoBandejaDetalle() == 1){
			asunto = "Denuncia  "+ prmData.getCodigoDenuncia() +" pendiente para su revisión";
			texto2 ="La denuncia  "+ prmData.getCodigoDenuncia()+" se encuentra pendiente para su revisión.";
	
		}
		
		if(prmData.getEstadoBandejaDetalle() == 2){
			asunto = "Denuncia  "+ prmData.getCodigoDenuncia() +" no Atendida";
			texto2 ="Se encuentra pendiente para su atención, la denuncia  "+ prmData.getCodigoDenuncia()+".";
	
		}
		
		if(prmData.getEstadoBandejaDetalle() == 5){
			asunto = "No se han realizado acciones en la Denuncia  "+ prmData.getCodigoDenuncia() +".";
			texto2 ="Se verifica que aun no se han realizado acciones a la denuncia  "+ prmData.getCodigoDenuncia()+".";
	
		}
		
		
		
		
	   
		
		
		
		mensaje = mensaje.replace("TextoCorreo1", texto1);
		mensaje = mensaje.replace("TextoCorreo2", texto2);
		mensaje = mensaje.replace("TextoCorreo3", texto3);
		mensaje = mensaje.replace("TextoCorreo4", texto4);
		mensaje = mensaje.replace("TextoCorreo5", "");
		mensaje = mensaje.replace("TextoCorreo6", "");
		mensaje = mensaje.replace("TextoCorreo7", "");
		
		
		
		
		
		
		
		try {
			EmailAttachmentSender.sendEmailWithAttachments(correo,  asunto, mensaje, null);
			
			enviarCorreoEspecialistaSinada(prmData.getIdDenuncia(),asunto,mensaje);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
		
		
	}
	
	
	
	public int enviarCorreoEspecialistaSinada(long idDenuncia,String asunto,String mensaje) throws ServicioException, AddressException, MessagingException{
		
		
		List<BandejaDetalle> lstBandejaDetalle = null;
		BandejaDetalle prmBandejaDetalle = new BandejaDetalle();
		prmBandejaDetalle.setIdDenuncia(idDenuncia);
		lstBandejaDetalle = bandejaDetalleService.buscarEspecialistaSinada(prmBandejaDetalle);
		
		
		for (BandejaDetalle bandejaDetalle : lstBandejaDetalle) {
			Bandeja bandeja = new Bandeja();
			bandeja = bandejaService.buscarPorId(bandejaDetalle.getIdBandeja());
			List<ContactoPersona> lstContactoPersona = null;
			if(bandeja!=null){
				
				ContactoPersona contactoPersona = new ContactoPersona();
				
				contactoPersona.setTipoPersona(bandeja.getTipoResponsable());
				contactoPersona.setIdPersona(bandeja.getIdResponsable());
			
				lstContactoPersona = (List<ContactoPersona>) contactoPersonaService.listar(contactoPersona);
				
				for (ContactoPersona contacto : lstContactoPersona) {
					
					if(contacto.getTipoContacto()==2){
					
						EmailAttachmentSender.sendEmailWithAttachments(contacto.getValor(), asunto , mensaje, null);
						
						
					}
				
				}
				
			}
	
			
		
			
		
			
		}
		
		enviarCorreoCoordinadorSinada(asunto,mensaje);
		return 0;
	}
	
	
	public int enviarCorreoCoordinadorSinada(String asunto,String mensaje) throws AddressException, MessagingException, ServicioException{
		
		int res = 0;
		
		Usuario prmUsuario = new Usuario();
		String idPerfilCoordinadorSinada = ResourceUtil
				.getKey("IdPerfilSSO_Coordinador");
		prmUsuario.setIdPerfil(Long.valueOf(idPerfilCoordinadorSinada));

		List<Usuario> lstUsuario = null;
		lstUsuario = (List<Usuario>) usuarioService.listar(prmUsuario);

		for (Usuario usuario : lstUsuario) {
		
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

					try {
						
						EmailAttachmentSender.sendEmailWithAttachments(
								contacto.getValor(),
								asunto,
								mensaje, null);
						res = 1;
					} catch (Exception e) {
						res = 0;
						// TODO: handle exception
					}
					//Identificar Denuncia Anonima
				
					


				}

			}
		}
		return res;
	}
	
	
	
	/**************************************************************************************/
	
public int obtenerCorreoEntidades(BandejaDenuncia prmData) throws ServicioException{
		
		
		
		if(prmData.getTipoResponsableBandeja()==3){ // OEFA
			PersonaOefa prmPersonaOefa = new PersonaOefa();
			List<PersonaOefa> lstPersonaOefa = null;
			prmPersonaOefa.getDireccion().setCodigoRegistro(prmData.getDireccionSupervicion());
			prmPersonaOefa.getSubDireccion().setCodigoRegistro(prmData.getSubDireccion());
			lstPersonaOefa = (List<PersonaOefa>) personaOefaService.listar(prmPersonaOefa);
			
			if(lstPersonaOefa!=null){
				
				for (PersonaOefa personaOefa : lstPersonaOefa) {
					
					
					ContactoPersona contactoPersona = new ContactoPersona();
					List<ContactoPersona> lstContactoPersona = null;
					contactoPersona.setTipoPersona(1);
					contactoPersona.setIdPersona(personaOefa.getPersona().getIdPersona());
					lstContactoPersona = (List<ContactoPersona>) contactoPersonaService.listar(contactoPersona);
					
					int x = 0;
					for (ContactoPersona contacto : lstContactoPersona) {
						
						if(contacto.getTipoContacto()==2){
							enviarCorreoPorTipo(contacto.getValor(),prmData,personaOefa.getPersona().getNombreCompleto(),x,1);
							
						x = x+1;	
						}
					
					}
					
				}
				
			}
			
		}
		
		
		if(prmData.getTipoResponsableBandeja()==4){  // EFA
			Efa prmEfa =  efaService.buscarPorId(prmData.getIdEfa());
			if(prmEfa!=null && prmEfa.getCorreo()!=null && prmEfa.getNombre()!=null){
				enviarCorreoPorTipo(prmEfa.getCorreo(),prmData,prmEfa.getNombre(),0,2);
			}
			
			
			
		}
		
		
		
		
		
		return 0;
		
	}
	

public int validarFechaCorreo(BandejaDenuncia prmData){
	int res= 0;
	int diasHabilesdeAtencion = 0;
	

		
	/**************************** cuando la denuncia esta rechazada *************/
		// para enviar solo al especialista
		if(prmData.getEstadoBandejaDetalle() == 3 && prmData.getDiasHabilesTranscurridosDenunciaAsignada()>=3){
			res=2;
		}
		
		
	/**************************** cuando la denuncia esta pendiente *************/
	// para enviar a la efa / oefa  / especialista
	if(prmData.getEstadoBandejaDetalle() == 1 && prmData.getDiasHabilesTranscurridosDenunciaAsignada()>=3 && prmData.getDiasHabilesTranscurridosDenunciaAsignada()<6){
		res=1;
	}
	// para enviar solo al especialista
	if(prmData.getEstadoBandejaDetalle() == 1 && prmData.getDiasHabilesTranscurridosDenunciaAsignada()>=6 && prmData.getDiasHabilesTranscurridosDenunciaAsignada()<=9){
		res=2;
	}
	
	
	/**************************** cuando la denuncia esta en revision *************/
	
	// para enviar a la efa / oefa / especialista
	if(prmData.getEstadoBandejaDetalle() == 2 && prmData.getDiasHabilesTranscurridosDenunciaAsignada()>=3 && prmData.getDiasHabilesTranscurridosDenunciaAsignada()<6){
		res=1;
	}
	// para enviar solo al especialista
	if(prmData.getEstadoBandejaDetalle() == 2 && prmData.getDiasHabilesTranscurridosDenunciaAsignada()>=6 && prmData.getDiasHabilesTranscurridosDenunciaAsignada()<=9){
		res=2;
	}
	
	
	/**************************** cuando la denuncia esta en atencion *************/
	
	if(prmData.getTipoResponsableBandeja()==3){
		diasHabilesdeAtencion = 30;
	}
	if(prmData.getTipoResponsableBandeja()==4){
		diasHabilesdeAtencion = 10;
	}
	// para enviar a la efa / oefa
	if(prmData.getEstadoBandejaDetalle() == 5 && prmData.getDiasHabilesPlazo()>=diasHabilesdeAtencion && prmData.getDiasHabilesPlazo()<(diasHabilesdeAtencion+6)){
		res=1;
	}
	// para enviar a l especialista
	if(prmData.getEstadoBandejaDetalle() == 5 && prmData.getDiasHabilesPlazo()>=(diasHabilesdeAtencion+6)){
		res=2;
	}
	
		if (prmData.getEstadoBandejaDetalle() == 6) {

			for (AccionesRealizadas accionesRealizadas : prmData
					.getLstAccionesRealizadas()) {
				/// pendiente para la evaluacion del especialista - de la accion
				if (accionesRealizadas.getEstado() == 1 && accionesRealizadas.getTipoAtencionAccion()==1 && accionesRealizadas.getDiasHabilesTranscurridosAccion()>=5) {
					res = 2;	
				}
				
				// pendiente para la correccion por parte de la efa / oefa - de la accion
				if (accionesRealizadas.getEstado() == 2 && accionesRealizadas.getTipoAtencionAccion()==1 && accionesRealizadas.getDiasHabilesTranscurridosAccion()>=3) {
					res = 1;	
				}
				
				/// pendiente para la evaluacion del especialista - de la atencion
				if (accionesRealizadas.getEstado() == 1 && accionesRealizadas.getTipoAtencionAccion()==2 && accionesRealizadas.getDiasHabilesTranscurridosAccion()>=5) {
					res = 4;	
				}
				
				// pendiente para la correccion por parte de la efa / oefa - de la atencion
				if (accionesRealizadas.getEstado() == 2 && accionesRealizadas.getTipoAtencionAccion()==2 && accionesRealizadas.getDiasHabilesTranscurridosAccion()>=3) {
					res = 3;	
				}

			}

		}
	
	/**************************** cuando la denuncia tiene acciones *************/
	
	
	return res;
	
}

public int enviarCorreoPorTipo(String  correo, BandejaDenuncia prmData,String nombreEntidad,int tipo,int tipoEntidadCompentente) throws ServicioException{
	String nombreTipoEntidadCompentente= "OEFA";
	if(tipoEntidadCompentente==2){
		nombreTipoEntidadCompentente= "EFA";
	}
	
	int enviarCorreoEspecialista = 0;
	int enviarCorreoEntidadCompetente = 0;
	FormatoCorreo formatoCorreo = new FormatoCorreo();
	long idFormatoCorreo = 1;
	formatoCorreo = formatoCorreoService.buscarPorId(idFormatoCorreo);
	
	String texto1 = "";
	String texto2 = "";
	String texto3 = "";
	String texto4 = "";
	String mensaje = formatoCorreo.getMensaje();
	texto1 ="Sres. " +nombreEntidad;
	
	String texto1Especialista = "";
	String texto2Especialista = "";
	String texto3Especialista = "";
	String texto4Especialista = "";
	String mensajeEspecialista = formatoCorreo.getMensaje();
	/****/
	texto1 ="Nombre : " +nombreEntidad;
	/****/
	texto1Especialista =nombreTipoEntidadCompentente +" responsable : " +nombreEntidad;
	
	
	//personaOefa.getPersona().getNombreCompleto()
	String asunto = "";
	String asuntoEspecialista = "";
	
	
	if(prmData.getEstadoBandejaDetalle() == 3){
	
		
		if(validarFechaCorreo(prmData)==2){
			asuntoEspecialista = "Evaluación de rechazo pendiente de la denuncia  "+prmData.getCodigoDenuncia() + " emitida por la " + nombreTipoEntidadCompentente + " " + nombreEntidad;
			texto2Especialista ="Se verifica que el rechazo de la denuncia  " + prmData.getCodigoDenuncia() +" se encuentra pendiente para su evaluación.";
			enviarCorreoEspecialista = 1;
		}
		

	}
	
	
	
	if(prmData.getEstadoBandejaDetalle() == 1){
		if(validarFechaCorreo(prmData)==1){
			asunto = "Denuncia  "+ prmData.getCodigoDenuncia() +" pendiente para su revisión";
			texto2 ="La denuncia  "+ prmData.getCodigoDenuncia()+" se encuentra pendiente para su revisión.";
			/**************************/
			asuntoEspecialista = "La "+ nombreTipoEntidadCompentente + " " + nombreEntidad + " no ha revisado la denuncia  " +prmData.getCodigoDenuncia();
			texto2Especialista ="Denuncia pendiente : " + prmData.getCodigoDenuncia();
			
			enviarCorreoEntidadCompetente = 1;
			enviarCorreoEspecialista = 1;
		}
		
		if(validarFechaCorreo(prmData)==2){
			asuntoEspecialista = "La "+ nombreTipoEntidadCompentente + " " + nombreEntidad +" no ha atendido la denuncia  " +prmData.getCodigoDenuncia();
			texto2Especialista ="Denuncia no atendida : " + prmData.getCodigoDenuncia();
			enviarCorreoEspecialista = 1;
		}
		

	}
	
if(prmData.getEstadoBandejaDetalle() == 2){
	if(validarFechaCorreo(prmData)==1){
		asunto = "Denuncia  "+ prmData.getCodigoDenuncia() +" no Atendida";
		texto2 ="Se encuentra pendiente para su atención, la denuncia  "+ prmData.getCodigoDenuncia()+".";
		
		asuntoEspecialista = "La "+ nombreTipoEntidadCompentente + " " + nombreEntidad +" no ha atendido la denuncia  " +prmData.getCodigoDenuncia();
		texto2Especialista ="Denuncia en revisión : " + prmData.getCodigoDenuncia();
		
		enviarCorreoEntidadCompetente = 1;
		enviarCorreoEspecialista = 1;
		
	}
	
	if(validarFechaCorreo(prmData)==2){
		asuntoEspecialista = "La "+ nombreTipoEntidadCompentente + " " + nombreEntidad +" no ha atendido la denuncia  " +prmData.getCodigoDenuncia();
		texto2Especialista ="Denuncia no atendida : " + prmData.getCodigoDenuncia();
		enviarCorreoEspecialista = 1;
		
	}
		
	}

	if(prmData.getEstadoBandejaDetalle() == 5){
		
		if(validarFechaCorreo(prmData)==1){
			asunto = "No se han realizado acciones en la Denuncia  "+ prmData.getCodigoDenuncia() +".";
			texto2 ="Se verifica que aun no se han realizado acciones a la denuncia  "+ prmData.getCodigoDenuncia()+".";
				
//			
//			asuntoEspecialista = "La "+ nombreTipoEntidadCompentente + " " + nombreEntidad +" no ha realizado acciones a la denuncia  " +prmData.getCodigoDenuncia();
//			texto2Especialista =texto2;
			
			enviarCorreoEntidadCompetente = 1;

		}

			if (validarFechaCorreo(prmData) == 2) {
				
				asuntoEspecialista = "La "+ nombreTipoEntidadCompentente + " " + nombreEntidad +" no ha realizado acciones a la denuncia  " +prmData.getCodigoDenuncia();
				texto2Especialista ="Se verifica que aun no se han realizado acciones a la denuncia  "+ prmData.getCodigoDenuncia()+".";
			
				enviarCorreoEspecialista = 1;
			}
	
	}
	
	
	
	if(prmData.getEstadoBandejaDetalle() == 6){
		//correo de correcion por parte de la efa
		//nombreTipoDeAtencion;
		if(validarFechaCorreo(prmData)==1){
			asunto = "Levantar observaciones de las acciones registradas en la denuncia  "+ prmData.getCodigoDenuncia() +".";
			texto2="Se verifica que que esta pendiente levantar las observaciones emitidas al informe de acciòn de la Denuncia  "+ prmData.getCodigoDenuncia() +".";
			
			
			 
			 asuntoEspecialista =  "La "+ nombreTipoEntidadCompentente + " " + nombreEntidad +" no ha levantado la observación realizada al informe de acción de la denuncia  " +prmData.getCodigoDenuncia();
			 texto2Especialista="Se verifica que que no se han levantado  las observaciones emitidas al informe de acción de la Denuncia  "+ prmData.getCodigoDenuncia() +".";

			
			enviarCorreoEspecialista = 1;
			enviarCorreoEntidadCompetente = 1;

		}

			if (validarFechaCorreo(prmData) == 2) {
				
				asuntoEspecialista = "Evaluar acciones emitidas por la "+ nombreTipoEntidadCompentente +"  "+nombreEntidad+" - Denuncia  " +prmData.getCodigoDenuncia();
				texto2Especialista ="Se verifica que aun no se ha evaluado la acción emitida a la denuncia  " + prmData.getCodigoDenuncia();
			
				enviarCorreoEspecialista = 1;
			}
			
			
			if(validarFechaCorreo(prmData)==3){
				asunto = "Levantar observaciones de las atenciones registradas en la denuncia  "+ prmData.getCodigoDenuncia() +".";
				texto2="Se verifica que que esta pendiente levantar la observacion emitida al informe de atención de la Denuncia  "+ prmData.getCodigoDenuncia() +".";

				 asuntoEspecialista =  "La "+ nombreTipoEntidadCompentente + " " + nombreEntidad +" no ha levantado la observación realizada al informe de  atención de la denuncia  " +prmData.getCodigoDenuncia();
				 texto2Especialista="Se verifica que que no se han levantado  las observaciones emitidas al informe de atención de la Denuncia  "+ prmData.getCodigoDenuncia() +".";
				 
				 enviarCorreoEspecialista = 1;
				enviarCorreoEntidadCompetente = 1;

			}
	
			if (validarFechaCorreo(prmData) == 4) {
				
				asuntoEspecialista = "Evaluar atenciones emitidas por la "+ nombreTipoEntidadCompentente +"  "+nombreEntidad+" - Denuncia  " +prmData.getCodigoDenuncia();
				texto2Especialista ="Se verifica que aun no se ha evaluado la atención emitida a la denuncia  " + prmData.getCodigoDenuncia();
			
				enviarCorreoEspecialista = 1;
			}

	}
	
	
	
	
   
	mensaje = mensaje.replace("TextoCorreo1", texto1);
	mensaje = mensaje.replace("TextoCorreo2", texto2);
	mensaje = mensaje.replace("TextoCorreo3", texto3);
	mensaje = mensaje.replace("TextoCorreo4", texto4);
	mensaje = mensaje.replace("TextoCorreo5", "");
	mensaje = mensaje.replace("TextoCorreo6", "");
	mensaje = mensaje.replace("TextoCorreo7", "");
	
	
	mensajeEspecialista = mensajeEspecialista.replace("TextoCorreo1", texto1Especialista);
	mensajeEspecialista = mensajeEspecialista.replace("TextoCorreo2", texto2Especialista);
	mensajeEspecialista = mensajeEspecialista.replace("TextoCorreo3", texto3Especialista);
	mensajeEspecialista = mensajeEspecialista.replace("TextoCorreo4", texto4Especialista);
	mensajeEspecialista = mensajeEspecialista.replace("TextoCorreo5", "");
	mensajeEspecialista = mensajeEspecialista.replace("TextoCorreo6", "");
	mensajeEspecialista = mensajeEspecialista.replace("TextoCorreo7", "");
	
	
	
	
	
	
	
	try {
		if(enviarCorreoEntidadCompetente==1){
			EmailAttachmentSender.sendEmailWithAttachments(correo,  asunto, mensaje, null);
		}
		
		
		
		if(tipo==0 && enviarCorreoEspecialista==1){
			enviarCorreoEspecialistaSinada(prmData.getIdDenuncia(),asuntoEspecialista,mensajeEspecialista);
		}

	} catch (AddressException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (MessagingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return 0;
	
	
}
	/************************************************************************************/

}
