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
import pe.gob.oefa.apps.sinada.bean.proceso.ArchivoDenuncia;
import pe.gob.oefa.apps.sinada.persistencia.inf.proceso.ArchivoDenunciaDAO;
import pe.gob.oefa.apps.sinada.servicio.inf.general.AuditoriaDetalleService;
import pe.gob.oefa.apps.sinada.servicio.inf.general.AuditoriaService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.ArchivoDenunciaService;

@Service("archivoDenunciaService")
@Transactional(readOnly = true)
public class ArchivoDenunciaServiceImpl implements ArchivoDenunciaService {

	@Autowired
	private ArchivoDenunciaDAO archivoDenunciaDAO;
	
	@Autowired
	AuditoriaService auditoriaService;
	
	@Autowired
	AuditoriaDetalleService auditoriaDetalleService;
	
	private static final long ID_REGISTRO = 20;
	
	@Override
	public Long insertar(ArchivoDenuncia prmBean) throws ServicioException {
		try{
			Long idBean = archivoDenunciaDAO.insertar(prmBean);
			
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
	public int actualizar(ArchivoDenuncia prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int eliminar(ArchivoDenuncia prmBean) throws ServicioException {
		try{
			
			/******************* Auditoria ****************************/
			if(prmBean.getIdArchivoDenuncia()!=0){
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
				auditoria.setIdRegistro(prmBean.getIdArchivoDenuncia());
				
				auditoriaService.insertar(auditoria);
			
		}
	
			
			/******************* Auditoria ****************************/
			
			
			return archivoDenunciaDAO.eliminar(prmBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public ArchivoDenuncia buscarPorId(Long prmIdBean)
			throws ServicioException {

		ArchivoDenuncia oArchivoDenuncia= new ArchivoDenuncia();
		try 
		{ 
			oArchivoDenuncia = archivoDenunciaDAO.buscarPorId(prmIdBean);
		
		
		} 
		catch (PersistenciaException e) 
		{
			 
		}  
		return oArchivoDenuncia;
	}

	@Override
	public List<?> listar(ArchivoDenuncia prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return archivoDenunciaDAO.listar(prmBean);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

}
