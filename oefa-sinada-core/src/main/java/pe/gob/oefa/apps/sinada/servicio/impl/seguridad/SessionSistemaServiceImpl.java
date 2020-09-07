package pe.gob.oefa.apps.sinada.servicio.impl.seguridad;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.sinada.bean.seguridad.SessionSistema;
import pe.gob.oefa.apps.sinada.persistencia.inf.seguridad.SessionSistemaDAO;
import pe.gob.oefa.apps.sinada.servicio.inf.seguridad.SessionSistemaService;

@Service("sessionSistemaService")
@Transactional(readOnly = true)
public class SessionSistemaServiceImpl implements SessionSistemaService {
	
	@Autowired
	SessionSistemaDAO sessionSistemaDAO;
	@Override
	public Long insertar(SessionSistema prmBean) throws ServicioException {
		try{
			return sessionSistemaDAO.insertar(prmBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public int actualizar(SessionSistema prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int eliminar(SessionSistema prmBean) throws ServicioException {
		try{
			return sessionSistemaDAO.eliminar(prmBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public SessionSistema buscarPorId(Long prmIdBean) throws ServicioException {
		try{
			return sessionSistemaDAO.buscarPorId(prmIdBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public List<?> listar(SessionSistema prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return null;
	}

}
