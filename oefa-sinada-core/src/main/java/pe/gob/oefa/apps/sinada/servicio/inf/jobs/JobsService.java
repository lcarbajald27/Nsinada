package pe.gob.oefa.apps.sinada.servicio.inf.jobs;

import java.util.List;



import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.base.servicio.inf.BaseService;
import pe.gob.oefa.apps.sinada.bean.proceso.AtencionDenuncia;
import pe.gob.oefa.apps.sinada.bean.proceso.Denuncia;


public interface JobsService extends BaseService<Denuncia, Long>
{
	public int EnviCorreoNotificacionDenuncias() throws ServicioException;
	
	
}
