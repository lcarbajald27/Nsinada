package pe.gob.oefa.apps.sinada.persistencia.inf.proceso;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.persistencia.inf.BaseDAO;
import pe.gob.oefa.apps.sinada.bean.proceso.Caso;
import pe.gob.oefa.apps.sinada.bean.proceso.DetalleCaso;
import pe.gob.oefa.apps.sinada.bean.sirefa.Efa;
import pe.gob.oefa.apps.sinada.bean.sirin.Normas;

public interface CasoDAO extends BaseDAO<Caso, Long>{

	public long generarCodigoCaso() throws PersistenciaException;
	
	public Caso validaCasoRegistrado(Caso prmCaso)throws PersistenciaException;
	
}
