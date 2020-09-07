package pe.gob.oefa.apps.sinada.servicio.impl.proceso.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.sinada.bean.proceso.view.EntidadDenuncia;
import pe.gob.oefa.apps.sinada.persistencia.inf.proceso.view.EntidadDenunciaDAO;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.view.EntidadDenunciaService;

@Service("entidadDenunciaService")
@Transactional(readOnly = true)
public class EntidadDenunciaServiceImpl implements EntidadDenunciaService {

	@Autowired
	private EntidadDenunciaDAO entidadDenunciaDAO;
	
	@Override
	public Long insertar(EntidadDenuncia prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int actualizar(EntidadDenuncia prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int eliminar(EntidadDenuncia prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public EntidadDenuncia buscarPorId(Long prmIdBean) throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return entidadDenunciaDAO.buscarPorId(prmIdBean);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

	@Override
	public List<?> listar(EntidadDenuncia prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return entidadDenunciaDAO.listar(prmBean);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

}
