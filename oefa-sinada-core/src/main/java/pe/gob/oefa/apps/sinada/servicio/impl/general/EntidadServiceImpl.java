package pe.gob.oefa.apps.sinada.servicio.impl.general;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.sinada.bean.general.Entidad;
import pe.gob.oefa.apps.sinada.bean.general.Persona;
import pe.gob.oefa.apps.sinada.persistencia.inf.general.EntidadDAO;
import pe.gob.oefa.apps.sinada.servicio.inf.general.EntidadService;

@Service("entidadService")
@Transactional(readOnly = true)
public class EntidadServiceImpl implements EntidadService {

	@Autowired
	private EntidadDAO entidadDAO;
	
	@Override
	public Long insertar(Entidad prmBean) throws ServicioException {
		try{
			return entidadDAO.insertar(prmBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public int actualizar(Entidad prmBean) throws ServicioException {
		try{
			return entidadDAO.actualizar(prmBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public int eliminar(Entidad prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Entidad buscarPorId(Long prmIdBean) throws ServicioException {
		List<Entidad> lst=null;
		Entidad oEntidad= new Entidad();
		try 
		{ 
			oEntidad =  entidadDAO.buscarPorId(prmIdBean);
	
		} 
		catch (PersistenciaException e) 
		{
			 
		}  
		return oEntidad;
	}

	@Override
	public List<?> listar(Entidad prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Entidad buscarPorNumeroDocumento(String prmIdBean)
			throws ServicioException {
		Entidad oEntidad= new Entidad();
		try 
		{ 
			oEntidad =  entidadDAO.buscarPorNumeroDocumento(prmIdBean);
		
		
		} 
		catch (PersistenciaException e) 
		{
			throw new ServicioException(e);
		}  
		return oEntidad;
	}

	@Override
	public List<Entidad> buscarPorRazonSocialSSO(String razonSocial)
			throws ServicioException {
		try {
			return entidadDAO.buscarPorRazonSocialSSO(razonSocial);
		} catch (PersistenciaException e) {
			throw new ServicioException(e);
		}
	}

}
