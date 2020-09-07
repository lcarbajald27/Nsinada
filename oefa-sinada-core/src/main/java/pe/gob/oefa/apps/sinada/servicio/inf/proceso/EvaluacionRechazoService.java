package pe.gob.oefa.apps.sinada.servicio.inf.proceso;


import org.apache.ibatis.exceptions.PersistenceException;

import pe.gob.oefa.apps.base.persistencia.inf.BaseDAO;
import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.base.servicio.inf.BaseService;
import pe.gob.oefa.apps.sinada.bean.proceso.ArchivoAtencion;
import pe.gob.oefa.apps.sinada.bean.proceso.EvaluacionRechazo;


public interface EvaluacionRechazoService extends BaseService<EvaluacionRechazo, Long>{

	public EvaluacionRechazo buscarXIdAtencionDenuncia(EvaluacionRechazo prmBean) throws ServicioException;
	
	
}
