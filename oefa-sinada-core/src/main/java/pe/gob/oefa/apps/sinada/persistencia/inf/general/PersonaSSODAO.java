package pe.gob.oefa.apps.sinada.persistencia.inf.general;

import java.util.List;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.persistencia.inf.BaseDAO;
import pe.gob.oefa.apps.sinada.bean.general.Persona;
import pe.gob.oefa.apps.sinada.bean.general.PersonaSSO;
import pe.gob.oefa.apps.sinada.bean.sirefa.Contacto;

public interface PersonaSSODAO extends BaseDAO<PersonaSSO, Long>{

	public PersonaSSO buscarPorNumeroDocumento(PersonaSSO prmData) throws PersistenciaException;
	
	public List<PersonaSSO> listaPersonaSSO(PersonaSSO prmData) throws PersistenciaException;
	
}
