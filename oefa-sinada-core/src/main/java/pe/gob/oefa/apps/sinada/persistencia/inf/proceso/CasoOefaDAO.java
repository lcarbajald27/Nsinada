package pe.gob.oefa.apps.sinada.persistencia.inf.proceso;

import java.util.List;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.persistencia.inf.BaseDAO;
import pe.gob.oefa.apps.sinada.bean.proceso.CasoOefa;

public interface CasoOefaDAO extends BaseDAO<CasoOefa, Long>
{
	public List<CasoOefa> listarEfasXCaso(CasoOefa prmBean) throws PersistenciaException;
	
	public CasoOefa buscarPorIdCasoDireccionAndSubDireccion(CasoOefa prmBean)throws PersistenciaException;
	
}
