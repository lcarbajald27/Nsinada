package pe.gob.oefa.apps.sinada.servicio.impl.proceso;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.sinada.bean.proceso.ArchivoDenuncia;
import pe.gob.oefa.apps.sinada.bean.proceso.ArchivoInforme;
import pe.gob.oefa.apps.sinada.persistencia.inf.proceso.ArchivoInformeDAO;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.ArchivoInformeService;

@Service("archivoInformeService")
@Transactional(readOnly = true)
public class ArchivoInformeServiceImpl implements ArchivoInformeService {

	@Autowired
	private ArchivoInformeDAO archivoInformeDAO;
	
	@Override
	public Long insertar(ArchivoInforme prmBean) throws ServicioException {
		try{
			return archivoInformeDAO.insertar(prmBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public int actualizar(ArchivoInforme prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int eliminar(ArchivoInforme prmBean) throws ServicioException {
		try{
			return archivoInformeDAO.eliminar(prmBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public ArchivoInforme buscarPorId(Long prmIdBean) throws ServicioException {

		ArchivoInforme oArchivoInforme= new ArchivoInforme();
		try 
		{ 
			oArchivoInforme = archivoInformeDAO.buscarPorId(prmIdBean);
		
		
		} 
		catch (PersistenciaException e) 
		{
			 
		}  
		return oArchivoInforme;
	}

	@Override
	public List<?> listar(ArchivoInforme prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return archivoInformeDAO.listar(prmBean);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

}
