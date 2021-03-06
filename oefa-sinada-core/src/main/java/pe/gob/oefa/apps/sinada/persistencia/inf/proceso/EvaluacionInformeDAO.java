package pe.gob.oefa.apps.sinada.persistencia.inf.proceso;


import org.apache.ibatis.exceptions.PersistenceException;

import pe.gob.oefa.apps.base.persistencia.inf.BaseDAO;
import pe.gob.oefa.apps.sinada.bean.proceso.ArchivoAtencion;
import pe.gob.oefa.apps.sinada.bean.proceso.EvaluacionInforme;
import pe.gob.oefa.apps.sinada.bean.proceso.EvaluacionRechazo;


public interface EvaluacionInformeDAO extends BaseDAO<EvaluacionInforme, Long>{

	public EvaluacionInforme buscarPorIdInformeAccion(EvaluacionInforme prmBean)throws PersistenceException;
	
}
