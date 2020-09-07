package pe.gob.oefa.apps.sinada.servicio.impl.proceso;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.sinada.bean.proceso.Encuesta;
import pe.gob.oefa.apps.sinada.persistencia.inf.proceso.EncuestaDAO;
import pe.gob.oefa.apps.sinada.persistencia.inf.proceso.NotificacionesDAO;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.EncuestaService;
@Service("encuestaService")
@Transactional(readOnly = true)
public class EncuestaServiceImpl implements EncuestaService {

	
	@Autowired
	private EncuestaDAO encuestaDAO;
	
	@Override
	public Long insertar(Encuesta prmBean) throws ServicioException {
		try{
			return encuestaDAO.insertar(prmBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public int actualizar(Encuesta prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int eliminar(Encuesta prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Encuesta buscarPorId(Long prmIdBean) throws ServicioException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> listar(Encuesta prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return encuestaDAO.listar(prmBean);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

}
