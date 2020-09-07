package pe.gob.oefa.apps.sinada.servicio.impl.proceso;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.sinada.bean.general.ContactoPersona;
import pe.gob.oefa.apps.sinada.bean.proceso.Bandeja;
import pe.gob.oefa.apps.sinada.persistencia.inf.proceso.BandejaDAO;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.BandejaService;

@Service("bandejaService")
@Transactional(readOnly = true)
public class BandejaServiceImpl implements BandejaService {

	@Autowired
	private BandejaDAO bandejaDAO;
	
	@Override
	public Long insertar(Bandeja prmBean) throws ServicioException {
		try{
			return bandejaDAO.insertar(prmBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public int actualizar(Bandeja prmBean) throws ServicioException {
		try{
			return bandejaDAO.actualizar(prmBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public int eliminar(Bandeja prmBean) throws ServicioException {
		try{
			return bandejaDAO.eliminar(prmBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public Bandeja buscarPorId(Long prmIdBean) throws ServicioException {
		try{
			return bandejaDAO.buscarPorId(prmIdBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public List<?> listar(Bandeja prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("null")
	@Override
	public Bandeja validarBandeja(Bandeja prmBandeja) throws ServicioException {
		Bandeja oBandeja= new Bandeja();
		try 
		{ 
			long idData=0;
			oBandeja =  bandejaDAO.validarBandeja(prmBandeja);
			
			if(oBandeja==null){
				idData = insertar(prmBandeja);
				prmBandeja.setIdBandeja(idData);
				oBandeja =prmBandeja;
			}
			
			
		} 
		catch (PersistenciaException e) 
		{
			 
		}  
		return oBandeja;
	}

	@Override
	public Bandeja validarBandejaEfa(Bandeja prmBandeja)
			throws ServicioException {
		Bandeja oBandeja= new Bandeja();
		try 
		{ 
			long idData=0;
			oBandeja =  bandejaDAO.validarBandejaEfa(prmBandeja);
			
			if(oBandeja==null){
				idData = insertar(prmBandeja);
				prmBandeja.setIdBandeja(idData);
				oBandeja =prmBandeja;
			}
			
			
		} 
		catch (PersistenciaException e) 
		{
			 
		}  
		return oBandeja;
	}

	@Override
	public Bandeja validarBandejaOefa(Bandeja prmBandeja)
			throws ServicioException {
		Bandeja oBandeja= new Bandeja();
		try 
		{ 
			long idData=0;
			oBandeja =  bandejaDAO.validarBandejaOefa(prmBandeja);
			
			if(oBandeja==null){
				idData = insertar(prmBandeja);
				prmBandeja.setIdBandeja(idData);
				oBandeja =prmBandeja;
			}
			
			
		} 
		catch (PersistenciaException e) 
		{
			 
		}  
		return oBandeja;
	}

}
