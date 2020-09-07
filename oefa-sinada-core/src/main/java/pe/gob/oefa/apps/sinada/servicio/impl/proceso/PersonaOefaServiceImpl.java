package pe.gob.oefa.apps.sinada.servicio.impl.proceso;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.base.util.UtilEncrypt;
import pe.gob.oefa.apps.base.util.properties.UtilProperties;
import pe.gob.oefa.apps.sinada.bean.general.Auditoria;
import pe.gob.oefa.apps.sinada.bean.proceso.PersonaOefa;
import pe.gob.oefa.apps.sinada.persistencia.inf.proceso.PersonaOefaDAO;
import pe.gob.oefa.apps.sinada.servicio.inf.general.AuditoriaDetalleService;
import pe.gob.oefa.apps.sinada.servicio.inf.general.AuditoriaService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.PersonaOefaService;

@Service("personaOefaService")
@Transactional(readOnly = true)
public class PersonaOefaServiceImpl implements PersonaOefaService {

	@Autowired
	private PersonaOefaDAO personaOefaDAO;
	
	@Autowired
	AuditoriaService auditoriaService;
	
	@Autowired
	AuditoriaDetalleService auditoriaDetalleService;
	
	private static final long ID_REGISTRO = 15;
	
	
	@Override
	public Long insertar(PersonaOefa prmBean) throws ServicioException {
		try{
			
			Long idBean = personaOefaDAO.insertar(prmBean);
			
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
	public int actualizar(PersonaOefa prmBean) throws ServicioException {
		try{
			return personaOefaDAO.actualizar(prmBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public int eliminar(PersonaOefa prmBean) throws ServicioException {
		try{
			/******************* Auditoria ****************************/
			if(prmBean.getIdPersonaOefa()!=0){
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
				auditoria.setIdRegistro(prmBean.getIdPersonaOefa());
				
				auditoriaService.insertar(auditoria);
			
		}
	
			
			/******************* Auditoria ****************************/
			
			return personaOefaDAO.eliminar(prmBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public PersonaOefa buscarPorId(Long prmIdBean) throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return personaOefaDAO.buscarPorId(prmIdBean);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

	@Override
	public List<?> listar(PersonaOefa prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return personaOefaDAO.listar(prmBean);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

	@Override
	public PersonaOefa buscarPorIdPersona(PersonaOefa prmBean)
			throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return personaOefaDAO.buscarPorIdPersona(prmBean);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

}
