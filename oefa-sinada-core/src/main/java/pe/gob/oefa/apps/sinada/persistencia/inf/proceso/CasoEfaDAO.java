package pe.gob.oefa.apps.sinada.persistencia.inf.proceso;

import java.util.List;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.persistencia.inf.BaseDAO;
import pe.gob.oefa.apps.sinada.bean.proceso.CasoEfa;

public interface CasoEfaDAO extends BaseDAO<CasoEfa, Long>
{
	public List<CasoEfa> listarEfasXCaso(CasoEfa prmBean) throws PersistenciaException;
	
	public List<CasoEfa> listarEfasXCasoPorUbigeo(CasoEfa prmBean) throws PersistenciaException;
	
	public List<CasoEfa> listarEfasXCasoXNivelYUbigeo(CasoEfa prmBean) throws PersistenciaException;
	
	
}
