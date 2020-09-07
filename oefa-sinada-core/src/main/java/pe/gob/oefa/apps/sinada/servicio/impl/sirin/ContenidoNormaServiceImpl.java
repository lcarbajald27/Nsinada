package pe.gob.oefa.apps.sinada.servicio.impl.sirin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.sinada.bean.sirin.ContenidoNorma;
import pe.gob.oefa.apps.sinada.persistencia.inf.sirin.ContenidoNormaDAO;
import pe.gob.oefa.apps.sinada.servicio.inf.sirin.ContenidoNormaService;

@Service("contenidoNormaService")
@Transactional(readOnly = true)
public class ContenidoNormaServiceImpl implements ContenidoNormaService {

	@Autowired
	private ContenidoNormaDAO contenidoNormaDAO;
	@Override
	public Long insertar(ContenidoNorma prmBean) throws ServicioException {
		return null;
		
		
	}

	@Override
	public int actualizar(ContenidoNorma prmBean) throws ServicioException {
		return 0;

	}

	@Override
	public int eliminar(ContenidoNorma prmBean) throws ServicioException {
		return 0;
		
		
	}

	@Override
	public ContenidoNorma buscarPorId(Long prmIdBean) throws ServicioException {
		List<ContenidoNorma> lst=null;
		ContenidoNorma oContenidoNorma= new ContenidoNorma();
		try 
		{ 
			lst = (List<ContenidoNorma>) contenidoNormaDAO.buscarPorId(prmIdBean);
			if(lst!=null){
				oContenidoNorma=lst.get(0);
			}
		
		} 
		catch (PersistenciaException e) 
		{
			 
		}  
		return oContenidoNorma;
	}

	@Override
	public List<?> listar(ContenidoNorma prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return contenidoNormaDAO.listar(prmBean);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

}
