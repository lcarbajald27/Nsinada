package pe.gob.oefa.apps.sinada.servicio.inf.proceso;

import java.util.List;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.base.servicio.inf.BaseService;
import pe.gob.oefa.apps.sinada.bean.proceso.CasoOefa;

public interface CasoOefaService extends BaseService<CasoOefa, Long>{

	public List<CasoOefa> listarEfasXCaso(CasoOefa prmBean) throws ServicioException;
	
	public CasoOefa buscarPorIdCasoDireccionAndSubDireccion(CasoOefa prmBean)throws ServicioException;
	
}
