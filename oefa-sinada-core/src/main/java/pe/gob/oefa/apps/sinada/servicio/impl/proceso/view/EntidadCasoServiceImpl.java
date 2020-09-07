package pe.gob.oefa.apps.sinada.servicio.impl.proceso.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.sinada.bean.proceso.view.EntidadCaso;
import pe.gob.oefa.apps.sinada.persistencia.inf.proceso.view.EntidadCasoDAO;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.view.EntidadCasoService;

@Service("entidadCasoService")
@Transactional(readOnly = true)
public class EntidadCasoServiceImpl implements EntidadCasoService {

	@Autowired
	private EntidadCasoDAO entidadCasoDAO;
	
	@Override
	public Long insertar(EntidadCaso prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int actualizar(EntidadCaso prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int eliminar(EntidadCaso prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public EntidadCaso buscarPorId(Long prmIdBean) throws ServicioException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> listar(EntidadCaso prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return entidadCasoDAO.listar(prmBean);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

}
