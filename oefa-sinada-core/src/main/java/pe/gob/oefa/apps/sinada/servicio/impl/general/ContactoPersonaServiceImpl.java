package pe.gob.oefa.apps.sinada.servicio.impl.general;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.base.util.UtilEncrypt;
import pe.gob.oefa.apps.base.util.properties.UtilProperties;
import pe.gob.oefa.apps.sinada.bean.general.Auditoria;
import pe.gob.oefa.apps.sinada.bean.general.AuditoriaDetalle;
import pe.gob.oefa.apps.sinada.bean.general.ContactoPersona;
import pe.gob.oefa.apps.sinada.bean.proceso.CasoOefa;
import pe.gob.oefa.apps.sinada.bean.sirin.ContenidoNorma;
import pe.gob.oefa.apps.sinada.persistencia.inf.general.ContactoPersonaDAO;
import pe.gob.oefa.apps.sinada.servicio.inf.general.AuditoriaDetalleService;
import pe.gob.oefa.apps.sinada.servicio.inf.general.AuditoriaService;
import pe.gob.oefa.apps.sinada.servicio.inf.general.ContactoPersonaService;

@Service("contactoPersonaService")
@Transactional(readOnly = true)
public class ContactoPersonaServiceImpl implements ContactoPersonaService {
	
	
	@Autowired
	ContactoPersonaDAO contactoPersonaDAO;
	
	@Autowired
	AuditoriaService auditoriaService;
	
	@Autowired
	AuditoriaDetalleService auditoriaDetalleService;
	
	private static final long ID_REGISTRO = 19;
	
	@Override
	public Long insertar(ContactoPersona prmBean) throws ServicioException {
		try{
			
			Long idBean = contactoPersonaDAO.insertar(prmBean);
			/******************* Auditoria ****************************/
			if(idBean!=0){
				String PK_eIdUsuarioDecrypt = "0";
				if(prmBean.getPrm1()!=null){
					UtilEncrypt.init(UtilProperties.getKey("encrypt.key"));
					
					 PK_eIdUsuarioDecrypt = UtilEncrypt.decrypt(prmBean.getPrm1());
					System.out.println("PK_eIdUsuarioDecrypt " + PK_eIdUsuarioDecrypt);
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
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public int actualizar(ContactoPersona prmBean) throws ServicioException {
		try{
			
/********************* Auditoria ***************************/
			
			long idAuditoria = 0;
			ContactoPersona prmDataOld = obtenerDatosAntiguos(prmBean);
			
				String PK_eIdUsuarioDecrypt = "0";
				if(prmBean.getPrm1()!=null){
					UtilEncrypt.init(UtilProperties.getKey("encrypt.key"));
					
					 PK_eIdUsuarioDecrypt = UtilEncrypt.decrypt(prmBean.getPrm1());
					System.out.println("PK_eIdUsuarioDecrypt " + PK_eIdUsuarioDecrypt);
				}
				
		
				Auditoria auditoria = new Auditoria();
				auditoria.setIdTabla(ID_REGISTRO);
				auditoria.setTipoAuditoria("A");
				auditoria.setIdUsuario(Long.valueOf(PK_eIdUsuarioDecrypt));
				auditoria.setHostIp(prmBean.getHostIp());
				auditoria.setIdRegistro(prmBean.getIdContactoPersona());
				
				idAuditoria = auditoriaService.insertar(auditoria);
			
				if(idAuditoria!=0){
					
					compararAntiguoNuevo(String.valueOf(prmBean.getValor()),String.valueOf(prmDataOld.getValor()),idAuditoria,174);
		
				}
			
			/*********************************************/
				
				
			return contactoPersonaDAO.actualizar(prmBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public int eliminar(ContactoPersona prmBean) throws ServicioException {
		try{
			
			/******************* Auditoria ****************************/
			if(prmBean.getIdContactoPersona()!=0){
				String PK_eIdUsuarioDecrypt = "0";
				if(prmBean.getPrm1()!=null){
					UtilEncrypt.init(UtilProperties.getKey("encrypt.key"));
					
					 PK_eIdUsuarioDecrypt = UtilEncrypt.decrypt(prmBean.getPrm1());
					System.out.println("PK_eIdUsuarioDecrypt " + PK_eIdUsuarioDecrypt);
				}
				
		
				Auditoria auditoria = new Auditoria();
				auditoria.setIdTabla(ID_REGISTRO);
				auditoria.setTipoAuditoria("E");
				auditoria.setIdUsuario(Long.valueOf(PK_eIdUsuarioDecrypt));
				auditoria.setHostIp(prmBean.getHostIp());
				auditoria.setIdRegistro(prmBean.getIdContactoPersona());
				
				auditoriaService.insertar(auditoria);
			
		}
	
			
			/******************* Auditoria ****************************/
			
			
			return contactoPersonaDAO.eliminar(prmBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public ContactoPersona buscarPorId(Long prmIdBean) throws ServicioException {
		List<ContactoPersona> lst=null;
		ContactoPersona oContactoPersona= null;
		try 
		{ 
			oContactoPersona =  contactoPersonaDAO.buscarPorId(prmIdBean);
		
		} 
		catch (PersistenciaException e) 
		{
			 
		}  
		return oContactoPersona;
	}

	@Override
	public List<?> listar(ContactoPersona prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return contactoPersonaDAO.listar(prmBean);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}
	
	public ContactoPersona obtenerDatosAntiguos(ContactoPersona prmData){
//		int id=Integer.valueOf(String.valueOf(prmData.getIdEfa()));
		ContactoPersona oBean = null;
		
				try {
					oBean = contactoPersonaDAO.buscarPorId(prmData.getIdContactoPersona());
				} catch (PersistenciaException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
			return oBean;
		
		
	}
	
	public void compararAntiguoNuevo(String datoNuevo,String datoAntiguo,long codAudiCab,int codCampo ){
		
		if(!datoNuevo.equals(datoAntiguo)){
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
	public ContactoPersona validarContacto(ContactoPersona prmData)
			throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return contactoPersonaDAO.validarContacto(prmData);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

}
