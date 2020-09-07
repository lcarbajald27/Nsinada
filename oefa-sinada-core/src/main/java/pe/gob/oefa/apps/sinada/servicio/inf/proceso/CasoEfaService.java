package pe.gob.oefa.apps.sinada.servicio.inf.proceso;

import java.util.List;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.base.servicio.inf.BaseService;
import pe.gob.oefa.apps.sinada.bean.proceso.CasoEfa;

public interface CasoEfaService extends BaseService<CasoEfa, Long>{

	public List<CasoEfa> listarEfasXCaso(CasoEfa prmBean) throws ServicioException;
	
	public List<CasoEfa> listarEfasXCasoPorUbigeo(CasoEfa prmBean) throws ServicioException;
	
	public List<CasoEfa> listarEfasXCasoXNivelYUbigeo(CasoEfa prmBean) throws ServicioException;
	
	
}
