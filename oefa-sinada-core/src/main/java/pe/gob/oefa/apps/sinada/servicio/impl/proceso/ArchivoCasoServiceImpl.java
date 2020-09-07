package pe.gob.oefa.apps.sinada.servicio.impl.proceso;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.sinada.bean.proceso.ArchivoCaso;
import pe.gob.oefa.apps.sinada.bean.proceso.DescripcionCaso;
import pe.gob.oefa.apps.sinada.persistencia.inf.proceso.ArchivoCasoDAO;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.ArchivoCasoService;

@Service("archivoCasoService")
@Transactional(readOnly = true)
public class ArchivoCasoServiceImpl implements ArchivoCasoService {
	
	@Autowired
	private ArchivoCasoDAO archivoCasoDAO;

	@Override
	public Long insertar(ArchivoCaso prmBean) throws ServicioException {
		try{
			return archivoCasoDAO.insertar(prmBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public int actualizar(ArchivoCaso prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int eliminar(ArchivoCaso prmBean) throws ServicioException {
		try{
			return archivoCasoDAO.eliminar(prmBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public ArchivoCaso buscarPorId(Long prmIdBean) throws ServicioException {
		List<ArchivoCaso> lst=null;
		ArchivoCaso oArchivoCaso= new ArchivoCaso();
		try 
		{ 
			oArchivoCaso = archivoCasoDAO.buscarPorId(prmIdBean);
		
		
		} 
		catch (PersistenciaException e) 
		{
			 
		}  
		return oArchivoCaso;
	}

	@Override
	public List<?> listar(ArchivoCaso prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return archivoCasoDAO.listar(prmBean);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

}
