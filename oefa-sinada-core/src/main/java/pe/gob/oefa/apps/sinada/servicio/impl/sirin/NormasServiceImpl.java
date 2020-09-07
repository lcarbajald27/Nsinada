package pe.gob.oefa.apps.sinada.servicio.impl.sirin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.sinada.bean.maestros.Maestro;
import pe.gob.oefa.apps.sinada.bean.sirin.Normas;
import pe.gob.oefa.apps.sinada.persistencia.inf.sirin.NormasDAO;
import pe.gob.oefa.apps.sinada.servicio.inf.sirin.NormasService;

@Service("normasService")
@Transactional(readOnly = true)
public class NormasServiceImpl implements NormasService {
	
	@Autowired
	private NormasDAO normasDAO;

	@Override
	public Long insertar(Normas prmBean) throws ServicioException {
		try{
			return normasDAO.insertar(prmBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public int actualizar(Normas prmBean) throws ServicioException {
		try{
			return normasDAO.actualizar(prmBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public int eliminar(Normas prmBean) throws ServicioException {
		try{
			return normasDAO.eliminar(prmBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public Normas buscarPorId(Long prmIdBean) throws ServicioException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> listar(Normas prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return normasDAO.listar(prmBean);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

	@Override
	public Normas buscarPorNumeroNorma(Normas prmNormas)
			throws ServicioException {
		List<Normas> lst=null;
		Normas oNormas= new Normas();
		try 
		{ 
			lst = (List<Normas>) normasDAO.buscarPorNumeroNorma(prmNormas);
			oNormas=lst.get(0);
		} 
		catch (PersistenciaException e) 
		{
			 
		}  
		return oNormas;
	}

	@Override
	public List<Normas> buscarPorEntidad(Normas prmNormas)
			throws ServicioException {
		List<Normas> lst=null;
		try 
		{ 
			lst =  normasDAO.buscarPorEntidad(prmNormas);
		
		} 
		catch (PersistenciaException e) 
		{
			 
		}  
		return lst;
	}

}
