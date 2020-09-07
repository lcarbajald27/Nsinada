package pe.gob.oefa.apps.sinada.servicio.impl.proceso;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.sinada.bean.proceso.ArchivoAtencion;
import pe.gob.oefa.apps.sinada.bean.proceso.ArchivoCaso;
import pe.gob.oefa.apps.sinada.persistencia.inf.proceso.ArchivoAtencionDAO;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.ArchivoAtencionService;

@Service("archivoAtencionService")
@Transactional(readOnly = true)
public class ArchivoAtencionServiceImpl implements ArchivoAtencionService {

	@Autowired
	private ArchivoAtencionDAO archivoAtencionDAO;
	
	@Override
	public Long insertar(ArchivoAtencion prmBean) throws ServicioException {
		try{
			return archivoAtencionDAO.insertar(prmBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public int actualizar(ArchivoAtencion prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int eliminar(ArchivoAtencion prmBean) throws ServicioException {
		try{
			return archivoAtencionDAO.eliminar(prmBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public ArchivoAtencion buscarPorId(Long prmIdBean) throws ServicioException {
	
		ArchivoAtencion oBean= new ArchivoAtencion();
		try 
		{ 
			oBean = archivoAtencionDAO.buscarPorId(prmIdBean);
		
		
		} 
		catch (PersistenciaException e) 
		{
			 
		}  
		return oBean;
	}

	@Override
	public List<?> listar(ArchivoAtencion prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return archivoAtencionDAO.listar(prmBean);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

}
