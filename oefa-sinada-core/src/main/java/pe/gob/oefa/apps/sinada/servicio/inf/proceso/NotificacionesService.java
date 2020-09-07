package pe.gob.oefa.apps.sinada.servicio.inf.proceso;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;

import pe.gob.oefa.apps.base.persistencia.inf.BaseDAO;
import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.base.servicio.inf.BaseService;
import pe.gob.oefa.apps.sinada.bean.proceso.NormaCaso;
import pe.gob.oefa.apps.sinada.bean.proceso.Notificaciones;

public interface NotificacionesService extends BaseService<Notificaciones, Long>{

	public List<Notificaciones> listarNotificaciones(Notificaciones prmBean)throws ServicioException;
	
}
