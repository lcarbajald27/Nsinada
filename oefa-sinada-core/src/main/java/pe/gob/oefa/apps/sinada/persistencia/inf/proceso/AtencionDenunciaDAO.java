package pe.gob.oefa.apps.sinada.persistencia.inf.proceso;

import java.util.List;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.persistencia.inf.BaseDAO;
import pe.gob.oefa.apps.sinada.bean.proceso.AtencionDenuncia;
import pe.gob.oefa.apps.sinada.bean.proceso.Bandeja;
import pe.gob.oefa.apps.sinada.bean.proceso.CasoOefa;

public interface AtencionDenunciaDAO extends BaseDAO<AtencionDenuncia, Long>
{

	public List<AtencionDenuncia> buscarAtencionDenunciaRechazada(AtencionDenuncia prmData) throws PersistenciaException;
	
}
