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
import pe.gob.oefa.apps.sinada.bean.proceso.CasoEfa;
import pe.gob.oefa.apps.sinada.bean.proceso.CasoOefa;
import pe.gob.oefa.apps.sinada.persistencia.inf.proceso.CasoOefaDAO;
import pe.gob.oefa.apps.sinada.servicio.inf.general.AuditoriaDetalleService;
import pe.gob.oefa.apps.sinada.servicio.inf.general.AuditoriaService;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.CasoOefaService;

@Service("casoOefaService")
@Transactional(readOnly = true)
public class CasoOefaServiceImpl implements CasoOefaService {

	@Autowired
	private CasoOefaDAO casoOefaDAO;
	
	@Autowired
	AuditoriaService auditoriaService;
	
	@Autowired
	AuditoriaDetalleService auditoriaDetalleService;
	
	private static final long ID_REGISTRO = 14;
	
	@Override
	public Long insertar(CasoOefa prmBean) throws ServicioException {
		try{
			
			Long idBean = null;
			idBean = casoOefaDAO.insertar(prmBean);
		
			
			
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
	
			return idBean;
			/******************* Auditoria ****************************/
			
			
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public int actualizar(CasoOefa prmBean) throws ServicioException {
		try{
			
			
/********************* Auditoria ***************************/
			
			long idAuditoria = 0;
			CasoOefa prmDataOld = obtenerDatosAntiguos(prmBean);
			
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
				auditoria.setIdRegistro(prmBean.getIdCasoOefa());
				
				idAuditoria = auditoriaService.insertar(auditoria);
			
				if(idAuditoria!=0){
					
					compararAntiguoNuevo(String.valueOf(prmBean.getTipoAsignacion().getCodigoRegistro()),String.valueOf(prmDataOld.getTipoAsignacion().getCodigoRegistro()),idAuditoria,131);
		
				}
			
			/*********************************************/
				
			return casoOefaDAO.actualizar(prmBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public int eliminar(CasoOefa prmBean) throws ServicioException {
		try{
			
			
			/******************* Auditoria ****************************/
			if(prmBean.getIdCasoOefa()!=0){
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
				auditoria.setIdRegistro(prmBean.getIdCasoOefa());
				
				auditoriaService.insertar(auditoria);
			
		}
	
			
			/******************* Auditoria ****************************/
			
			
			return casoOefaDAO.eliminar(prmBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public CasoOefa buscarPorId(Long prmIdBean) throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return casoOefaDAO.buscarPorId(prmIdBean);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

	@Override
	public List<?> listar(CasoOefa prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return casoOefaDAO.listar(prmBean);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

	@Override
	public List<CasoOefa> listarEfasXCaso(CasoOefa prmBean)
			throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return casoOefaDAO.listarEfasXCaso(prmBean);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}
	
	public CasoOefa obtenerDatosAntiguos(CasoOefa prmData){
//		int id=Integer.valueOf(String.valueOf(prmData.getIdEfa()));
		CasoOefa oBean = null;
		
				try {
					oBean = casoOefaDAO.buscarPorId(prmData.getIdCasoOefa());
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
	public CasoOefa buscarPorIdCasoDireccionAndSubDireccion(CasoOefa prmBean)
			throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return casoOefaDAO.buscarPorIdCasoDireccionAndSubDireccion(prmBean);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

}
