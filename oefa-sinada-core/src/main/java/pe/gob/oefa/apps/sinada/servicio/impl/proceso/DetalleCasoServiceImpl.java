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
import pe.gob.oefa.apps.sinada.bean.proceso.Denuncia;
import pe.gob.oefa.apps.sinada.bean.proceso.DetalleCaso;
import pe.gob.oefa.apps.sinada.persistencia.inf.proceso.DetalleCasoDAO;
import pe.gob.oefa.apps.sinada.servicio.inf.general.AuditoriaDetalleService;
import pe.gob.oefa.apps.sinada.servicio.inf.general.AuditoriaService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.DetalleCasoService;

@Service("detalleCasoService")
@Transactional(readOnly = true)
public class DetalleCasoServiceImpl implements DetalleCasoService {

	@Autowired
	private DetalleCasoDAO detalleCasoDAO;
	
	@Autowired
	AuditoriaService auditoriaService;
	
	@Autowired
	AuditoriaDetalleService auditoriaDetalleService;
	
	private static final long ID_REGISTRO = 12;
	
	
	
	@Override
	public Long insertar(DetalleCaso prmBean) throws ServicioException {
		try{
			Long idBean = null;
			
			idBean = detalleCasoDAO.insertar(prmBean);
			
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
	public int actualizar(DetalleCaso prmBean) throws ServicioException {
		try{
			
	/********************* Auditoria ***************************/
			
			long idAuditoria = 0;
			DetalleCaso prmDataOld = obtenerDatosAntiguos(prmBean);
			
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
				auditoria.setIdRegistro(prmBean.getIdDetalleCaso());
				
				idAuditoria = auditoriaService.insertar(auditoria);
			
				if(idAuditoria!=0){
					
					compararAntiguoNuevo(String.valueOf(prmBean.getDescripcion()),String.valueOf(prmDataOld.getDescripcion()),idAuditoria,116);
		
				}
			
			/*********************************************/
			
			
			return detalleCasoDAO.actualizar(prmBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public int eliminar(DetalleCaso prmBean) throws ServicioException {
		try{
			
			/******************* Auditoria ****************************/
			if(prmBean.getIdDetalleCaso()!=0){
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
				auditoria.setIdRegistro(prmBean.getIdDetalleCaso());
				
				auditoriaService.insertar(auditoria);
			
		}
	
			
			/******************* Auditoria ****************************/
			
			
			return detalleCasoDAO.eliminar(prmBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public DetalleCaso buscarPorId(Long prmIdBean) throws ServicioException {
		try {
			return detalleCasoDAO.buscarPorId(prmIdBean);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

	@Override
	public List<?> listar(DetalleCaso prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return detalleCasoDAO.listar(prmBean);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}
	
	
	
	public DetalleCaso obtenerDatosAntiguos(DetalleCaso prmData){
//		int id=Integer.valueOf(String.valueOf(prmData.getIdEfa()));
		DetalleCaso oBean = null;
		
				try {
					oBean = detalleCasoDAO.buscarPorId(prmData.getIdDetalleCaso());
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
	public DetalleCaso buscarPorTipoCasoCodigoPadreNivelAndDescripcion(
			DetalleCaso prmData) throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return detalleCasoDAO.buscarPorTipoCasoCodigoPadreNivelAndDescripcion(prmData);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

	@Override
	public List<DetalleCaso> listarDetalleCasoRegistradosDenuncia(
			DetalleCaso prmData) throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return detalleCasoDAO.listarDetalleCasoRegistradosDenuncia(prmData);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}
	

}
