package pe.gob.oefa.apps.sinada.persistencia.inf.general;

import java.util.List;
import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.persistencia.inf.BaseDAO;
import pe.gob.oefa.apps.sinada.bean.general.Persona;

public interface PersonaDAO extends BaseDAO<Persona, Long>{

	public Persona buscarPorNumeroDocumento(String prmIdBean) throws PersistenciaException;
	
	public List<Persona> buscarPorNombreCompletoSSO(String nombreCompleto) throws PersistenciaException;
}
