package pe.gob.oefa.apps.sinada.persistencia.inf.proceso;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;

import pe.gob.oefa.apps.base.persistencia.inf.BaseDAO;
import pe.gob.oefa.apps.sinada.bean.proceso.NormaCaso;
import pe.gob.oefa.apps.sinada.bean.proceso.Notificaciones;

public interface NotificacionesDAO extends BaseDAO<Notificaciones, Long>{

	public List<Notificaciones> listarNotificaciones(Notificaciones prmBean)throws PersistenceException;
	
}
