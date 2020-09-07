package pe.gob.oefa.apps.sinada.servicio.impl.proceso;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.base.util.UtilEncrypt;
import pe.gob.oefa.apps.base.util.properties.UtilProperties;
import pe.gob.oefa.apps.sinada.bean.general.Auditoria;
import pe.gob.oefa.apps.sinada.bean.proceso.Denunciante;
import pe.gob.oefa.apps.sinada.bean.proceso.view.DenuncianteDetalle;
import pe.gob.oefa.apps.sinada.persistencia.inf.proceso.DenuncianteDAO;
import pe.gob.oefa.apps.sinada.servicio.inf.general.AuditoriaDetalleService;
import pe.gob.oefa.apps.sinada.servicio.inf.general.AuditoriaService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.DenuncianteService;

@Service("denuncianteService")
@Transactional(readOnly = true)
public class DenuncianteServiceImpl implements DenuncianteService {

	@Autowired
	private DenuncianteDAO denuncianteDAO;
	
	@Autowired
	AuditoriaService auditoriaService;
	
	@Autowired
	AuditoriaDetalleService auditoriaDetalleService;
	
	private static final long ID_REGISTRO = 18;
	
	
	@Override
	public Long insertar(Denunciante prmBean) throws ServicioException {
		try{
			
			Long idBean = denuncianteDAO.insertar(prmBean);
			
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
	public int actualizar(Denunciante prmBean) throws ServicioException {
		try{
			return denuncianteDAO.actualizar(prmBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public int eliminar(Denunciante prmBean) throws ServicioException {
		try{
			
			/******************* Auditoria ****************************/
			if(prmBean.getIdDenunciante()!=0){
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
				auditoria.setIdRegistro(prmBean.getIdDenunciante());
				
				auditoriaService.insertar(auditoria);
			
		}
	
			
			/******************* Auditoria ****************************/
			
			return denuncianteDAO.eliminar(prmBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public Denunciante buscarPorId(Long prmIdBean) throws ServicioException {
		List<Denunciante> lst=null;
		Denunciante oDenunciante= new Denunciante();
		try 
		{ 
			lst = (List<Denunciante>) denuncianteDAO.buscarPorId(prmIdBean);
			if(lst!=null){
				oDenunciante=lst.get(0);
			}
		
		} 
		catch (PersistenciaException e) 
		{
			 
		}  
		return oDenunciante;
	}

	@Override
	public List<?> listar(Denunciante prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return denuncianteDAO.listar(prmBean);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

	@Override
	public List<DenuncianteDetalle> listarDenunciantes(
			DenuncianteDetalle prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return denuncianteDAO.listarDenunciantes(prmBean);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

}
