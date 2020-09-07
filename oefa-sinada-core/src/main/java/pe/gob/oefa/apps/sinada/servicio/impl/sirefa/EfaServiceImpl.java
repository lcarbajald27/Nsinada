package pe.gob.oefa.apps.sinada.servicio.impl.sirefa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.sinada.bean.proceso.DescripcionCaso;
import pe.gob.oefa.apps.sinada.bean.sirefa.Efa;
import pe.gob.oefa.apps.sinada.persistencia.inf.sirefa.EfaDAO;
import pe.gob.oefa.apps.sinada.servicio.inf.sirefa.EfaService;

@Service("efaService")
@Transactional(readOnly = true)
public class EfaServiceImpl implements EfaService{
	
	@Autowired
	private EfaDAO efaDAO;

	@Override
	public int actualizar(Efa prmEfa) throws ServicioException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Efa buscarPorId(Long prmEfa) throws ServicioException {
		try{
			return efaDAO.buscarPorId(prmEfa);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public int eliminar(Efa prmEfa) throws ServicioException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Long insertar(Efa prmEfa) throws ServicioException {
		try{
			return efaDAO.insertar(prmEfa);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<?> listar(Efa prmEfa) throws ServicioException 
	{
		List<Efa> beans= null;
		try 
		{ 
			beans = (List<Efa>) efaDAO.listar(prmEfa);
		} 
		catch (PersistenciaException e)
		{
			e.printStackTrace();
		}  
		return beans;
	}

	@Override
	public List<Efa> buscarPorFiltros(Efa efa) throws ServicioException
	{
		List<Efa> beans= null;
		try 
		{ 
			beans = efaDAO.buscarPorFiltros(efa);
		} 
		catch (PersistenciaException e) 
		{
			throw new ServicioException(e);
		}  
		return beans;
	}

	@Override
	public Efa buscarEfaPorIdEntidad(Efa efa) throws ServicioException {
	
		Efa oBean= new Efa();
		try 
		{ 
			oBean = efaDAO.buscarEfaPorIdEntidad(efa);
		
		
		} 
		catch (PersistenciaException e) 
		{
			 
		}  
		return oBean;
	}

	@Override
	public List<Efa> listarEfaDerivar(Efa efa) throws ServicioException {
		List<Efa> lst= null;
		try 
		{ 
			lst = efaDAO.listarEfaDerivar(efa);
		} 
		catch (PersistenciaException e) 
		{
			throw new ServicioException(e);
		}  
		return lst;
	}

	@Override
	public List<Efa> buscarPorIdEfaPorUbigeo(Efa efa) throws ServicioException {
		List<Efa> lst= null;
		try 
		{ 
			lst = efaDAO.buscarPorIdEfaPorUbigeo(efa);
		} 
		catch (PersistenciaException e) 
		{
			throw new ServicioException(e);
		}  
		return lst;
	}

}
