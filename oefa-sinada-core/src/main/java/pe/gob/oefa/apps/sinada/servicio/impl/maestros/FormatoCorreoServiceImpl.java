package pe.gob.oefa.apps.sinada.servicio.impl.maestros;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.sinada.bean.maestros.FormatoCorreo;
import pe.gob.oefa.apps.sinada.persistencia.inf.maestros.FormatoCorreoDAO;
import pe.gob.oefa.apps.sinada.servicio.inf.maestros.FormatoCorreoService;

@Service("formatoCorreoService")
@Transactional(readOnly = true)
public class FormatoCorreoServiceImpl implements FormatoCorreoService {
	
	@Autowired
	private FormatoCorreoDAO formatoCorreoDAO;
	
	@Override
	public Long insertar(FormatoCorreo prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int actualizar(FormatoCorreo prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int eliminar(FormatoCorreo prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public FormatoCorreo buscarPorId(Long prmIdBean) throws ServicioException {
		
		FormatoCorreo oBean = new FormatoCorreo();
		try {
			oBean = formatoCorreoDAO.buscarPorId(prmIdBean);

		} catch (PersistenciaException e) {

		}
		return oBean;
	}

	@Override
	public List<?> listar(FormatoCorreo prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return null;
	}

}
