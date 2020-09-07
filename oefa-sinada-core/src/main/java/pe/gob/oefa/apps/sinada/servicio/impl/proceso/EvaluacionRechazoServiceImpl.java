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
import pe.gob.oefa.apps.sinada.bean.general.AuditoriaDetalle;
import pe.gob.oefa.apps.sinada.bean.proceso.EvaluacionInforme;
import pe.gob.oefa.apps.sinada.bean.proceso.EvaluacionRechazo;
import pe.gob.oefa.apps.sinada.persistencia.inf.proceso.EvaluacionRechazoDAO;
import pe.gob.oefa.apps.sinada.servicio.inf.general.AuditoriaDetalleService;
import pe.gob.oefa.apps.sinada.servicio.inf.general.AuditoriaService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.EvaluacionRechazoService;

@Service("evaluacionRechazoService")
@Transactional(readOnly = true)
public class EvaluacionRechazoServiceImpl implements EvaluacionRechazoService {

	@Autowired
	private EvaluacionRechazoDAO evaluacionRechazoDao;
	
	@Autowired
	AuditoriaService auditoriaService;
	
	@Autowired
	AuditoriaDetalleService auditoriaDetalleService;
	
	private static final long ID_REGISTRO = 31;
	
	@Override
	public Long insertar(EvaluacionRechazo prmBean) throws ServicioException {
		try{
			
			Long idBean =  evaluacionRechazoDao.insertar(prmBean);
			 
			
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
	public int actualizar(EvaluacionRechazo prmBean) throws ServicioException {
		try{
			
/********************* Auditoria ***************************/
			
			long idAuditoria = 0;
			EvaluacionRechazo prmDataOld = obtenerDatosAntiguos(prmBean);
			
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
				auditoria.setIdRegistro(prmBean.getIdEvaluacionRechazo());
				
				idAuditoria = auditoriaService.insertar(auditoria);
			
				if(idAuditoria!=0){
					
					compararAntiguoNuevo(String.valueOf(prmBean.getEstado()),String.valueOf(prmDataOld.getEstado()),idAuditoria,281);
		
				}
			
			/*********************************************/
				
				
			return evaluacionRechazoDao.actualizar(prmBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public int eliminar(EvaluacionRechazo prmBean) throws ServicioException {
		try{
			
			
			/******************* Auditoria ****************************/
			if(prmBean.getIdEvaluacionRechazo()!=0){
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
				auditoria.setIdRegistro(prmBean.getIdEvaluacionRechazo());
				
				auditoriaService.insertar(auditoria);
			
		}
	
			
			/******************* Auditoria ****************************/
			
			return evaluacionRechazoDao.eliminar(prmBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public EvaluacionRechazo buscarPorId(Long prmIdBean)
			throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return evaluacionRechazoDao.buscarPorId(prmIdBean);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

	@Override
	public List<?> listar(EvaluacionRechazo prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return evaluacionRechazoDao.listar(prmBean);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

	@Override
	public EvaluacionRechazo buscarXIdAtencionDenuncia(EvaluacionRechazo prmBean)
			throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return evaluacionRechazoDao.buscarXIdAtencionDenuncia(prmBean);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}
	
	public EvaluacionRechazo obtenerDatosAntiguos(EvaluacionRechazo prmData){
//		int id=Integer.valueOf(String.valueOf(prmData.getIdEfa()));
		EvaluacionRechazo oBean = null;
		
				try {
					oBean = evaluacionRechazoDao.buscarPorId(prmData.getIdEvaluacionRechazo());
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

}
