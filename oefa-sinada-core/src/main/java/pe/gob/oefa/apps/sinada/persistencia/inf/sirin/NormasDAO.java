package pe.gob.oefa.apps.sinada.persistencia.inf.sirin;

import java.util.List;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.persistencia.inf.BaseDAO;
import pe.gob.oefa.apps.sinada.bean.sirefa.Efa;
import pe.gob.oefa.apps.sinada.bean.sirin.Normas;

public interface NormasDAO extends BaseDAO<Normas, Long>{

	public Normas buscarPorNumeroNorma(Normas prmNormas) throws PersistenciaException;
	public List<Normas> buscarPorEntidad(Normas prmNormas) throws PersistenciaException;
}
