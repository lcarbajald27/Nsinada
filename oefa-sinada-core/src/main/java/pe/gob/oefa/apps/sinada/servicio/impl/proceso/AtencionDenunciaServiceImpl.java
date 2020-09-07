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
import pe.gob.oefa.apps.sinada.bean.proceso.AtencionDenuncia;
import pe.gob.oefa.apps.sinada.bean.proceso.BandejaDetalle;
import pe.gob.oefa.apps.sinada.persistencia.inf.proceso.AtencionDenunciaDAO;
import pe.gob.oefa.apps.sinada.servicio.inf.general.AuditoriaDetalleService;
import pe.gob.oefa.apps.sinada.servicio.inf.general.AuditoriaService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.AtencionDenunciaService;

@Service("atencionDenunciaService")
@Transactional(readOnly = true)
public class AtencionDenunciaServiceImpl implements AtencionDenunciaService {

	@Autowired
	private AtencionDenunciaDAO atencionDenunciaDAO;

	@Autowired
	AuditoriaService auditoriaService;
	
	@Autowired
	AuditoriaDetalleService auditoriaDetalleService;
	
	private static final long ID_REGISTRO = 25;
	
	@Override
	public Long insertar(AtencionDenuncia prmBean) throws ServicioException {
		try{
			Long idBean = atencionDenunciaDAO.insertar(prmBean);
			
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
	public int actualizar(AtencionDenuncia prmBean) throws ServicioException {
		try{
			/********************* Auditoria ***************************/
			
			long idAuditoria = 0;
			AtencionDenuncia prmDataOld = obtenerDatosAntiguos(prmBean);
			
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
				auditoria.setIdRegistro(prmBean.getIdAtencionDenuncia());
				
				idAuditoria = auditoriaService.insertar(auditoria);
			
				if(idAuditoria!=0){
					
					compararAntiguoNuevo(String.valueOf(prmBean.getEstado()),String.valueOf(prmDataOld.getEstado()),idAuditoria,225);
		
				}
			
			/*********************************************/
				
			return atencionDenunciaDAO.actualizar(prmBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public int eliminar(AtencionDenuncia prmBean) throws ServicioException {
		try{
			
			/******************* Auditoria ****************************/
			if(prmBean.getIdAtencionDenuncia()!=0){
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
				auditoria.setIdRegistro(prmBean.getIdAtencionDenuncia());
				
				auditoriaService.insertar(auditoria);
			
		}
	
			
			/******************* Auditoria ****************************/
			return atencionDenunciaDAO.eliminar(prmBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public AtencionDenuncia buscarPorId(Long prmIdBean)
			throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return atencionDenunciaDAO.buscarPorId(prmIdBean);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

	@Override
	public List<?> listar(AtencionDenuncia prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return atencionDenunciaDAO.listar(prmBean);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

	@Override
	public List<AtencionDenuncia> buscarAtencionDenunciaRechazada(
			AtencionDenuncia prmData) throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return atencionDenunciaDAO.buscarAtencionDenunciaRechazada(prmData);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}
	
	public AtencionDenuncia obtenerDatosAntiguos(AtencionDenuncia prmData){
//		int id=Integer.valueOf(String.valueOf(prmData.getIdEfa()));
		AtencionDenuncia oBean = null;
		
				try {
					oBean = atencionDenunciaDAO.buscarPorId(prmData.getIdAtencionDenuncia());
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
