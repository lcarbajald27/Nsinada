package pe.gob.oefa.apps.sinada.servicio.impl.proceso;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.base.util.UtilEncrypt;
import pe.gob.oefa.apps.base.util.properties.UtilProperties;
import pe.gob.oefa.apps.sinada.bean.general.Auditoria;
import pe.gob.oefa.apps.sinada.bean.proceso.NormaCaso;
import pe.gob.oefa.apps.sinada.persistencia.inf.proceso.NormaCasoDAO;
import pe.gob.oefa.apps.sinada.servicio.inf.general.AuditoriaDetalleService;
import pe.gob.oefa.apps.sinada.servicio.inf.general.AuditoriaService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.NormaCasoService;

@Service("normaCasoService")
@Transactional(readOnly = true)
public class NormaCasoServiceImpl implements NormaCasoService {

	@Autowired
	private NormaCasoDAO normaCasoDAO;
	
	@Autowired
	AuditoriaService auditoriaService;
	
	@Autowired
	AuditoriaDetalleService auditoriaDetalleService;
	
	private static final long ID_REGISTRO = 16;
	
	@Override
	public Long insertar(NormaCaso prmBean) throws ServicioException {
		try{
			Long idBean = normaCasoDAO.insertar(prmBean);
			
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
	public int actualizar(NormaCaso prmBean) throws ServicioException {
		try{
			return normaCasoDAO.actualizar(prmBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public int eliminar(NormaCaso prmBean) throws ServicioException {
		try{
			
			/******************* Auditoria ****************************/
			if(prmBean.getIdNormaCaso()!=0){
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
				auditoria.setIdRegistro(prmBean.getIdNormaCaso());
				
				auditoriaService.insertar(auditoria);
			
		}
	
			
			/******************* Auditoria ****************************/
			
			
			return normaCasoDAO.eliminar(prmBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public NormaCaso buscarPorId(Long prmIdBean) throws ServicioException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> listar(NormaCaso prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return normaCasoDAO.listar(prmBean);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

}
