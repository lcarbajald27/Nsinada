package pe.gob.oefa.apps.sinada.servicio.impl.proceso;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.sinada.bean.proceso.Notificaciones;
import pe.gob.oefa.apps.sinada.persistencia.inf.proceso.NormaCasoDAO;
import pe.gob.oefa.apps.sinada.persistencia.inf.proceso.NotificacionesDAO;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.NotificacionesService;

@Service("notificacionesService")
@Transactional(readOnly = true)
public class NotificacionesServiceImpl implements NotificacionesService {
	
	@Autowired
	private NotificacionesDAO notificacionesDAO;
	
	
	@Override
	public Long insertar(Notificaciones prmBean) throws ServicioException {
		try{
			return notificacionesDAO.insertar(prmBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public int actualizar(Notificaciones prmBean) throws ServicioException {
		try{
			return notificacionesDAO.actualizar(prmBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public int eliminar(Notificaciones prmBean) throws ServicioException {
		try{
			return notificacionesDAO.eliminar(prmBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public Notificaciones buscarPorId(Long prmIdBean) throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return notificacionesDAO.buscarPorId(prmIdBean);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

	@Override
	public List<?> listar(Notificaciones prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return notificacionesDAO.listar(prmBean);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

	@Override
	public List<Notificaciones> listarNotificaciones(Notificaciones prmBean)
			throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return notificacionesDAO.listarNotificaciones(prmBean);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

}
