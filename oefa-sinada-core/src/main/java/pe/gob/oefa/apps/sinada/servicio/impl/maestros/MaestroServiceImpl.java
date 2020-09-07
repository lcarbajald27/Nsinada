package pe.gob.oefa.apps.sinada.servicio.impl.maestros;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.sinada.bean.maestros.Maestro;
import pe.gob.oefa.apps.sinada.persistencia.inf.maestros.MaestroDAO;
import pe.gob.oefa.apps.sinada.servicio.inf.maestros.MaestroService;

@Service("maestroService")
@Transactional(readOnly = true)
public class MaestroServiceImpl implements MaestroService{

	@Autowired
	MaestroDAO maestroDAO;
	
	@Override
	public Long insertar(Maestro prmBean) throws ServicioException {
		try{
			return maestroDAO.insertar(prmBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public int actualizar(Maestro prmBean) throws ServicioException {
		try{
			return maestroDAO.actualizar(prmBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public int eliminar(Maestro prmBean) throws ServicioException {
		try{
			return maestroDAO.eliminar(prmBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public Maestro buscarPorId(Long prmIdBean) throws ServicioException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> listar(Maestro prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return maestroDAO.listar(prmBean);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Maestro> buscarPorCodigoTabla(Maestro maestro) throws ServicioException 
	{
		List<Maestro> lst=null;
		try 
		{ 
			lst = (List<Maestro>) maestroDAO.buscarPorCodigoTabla(maestro);
		} 
		catch (PersistenciaException e) 
		{
			throw new ServicioException(e);
		}  
		return lst;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<?> listarHijos(Maestro maestro) throws ServicioException
	{
		List<Maestro> lst=null;
		try 
		{ 
			lst = (List<Maestro>) maestroDAO.listarHijos(maestro);
		} 
		catch (PersistenciaException e) 
		{
			throw new ServicioException(e);
		}  
		return lst;
	}

	@Override
	public List<Maestro> listarCodigoMaestro(Maestro maestro)
			throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return maestroDAO.listarCodigoMaestro(maestro);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

	@Override
	public List<Maestro> listarRegistroHijo(Maestro maestro)
			throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return maestroDAO.listarRegistroHijo(maestro);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

}
