package pe.gob.oefa.apps.sinada.persistencia.inf.proceso;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.persistencia.inf.BaseDAO;
import pe.gob.oefa.apps.sinada.bean.general.Persona;
import pe.gob.oefa.apps.sinada.bean.proceso.ArchivoCaso;
import pe.gob.oefa.apps.sinada.bean.proceso.ArchivoDenuncia;
import pe.gob.oefa.apps.sinada.bean.proceso.DescripcionCaso;
import pe.gob.oefa.apps.sinada.bean.proceso.PersonaOefa;
import pe.gob.oefa.apps.sinada.bean.sirefa.Efa;	
import pe.gob.oefa.apps.sinada.bean.sirin.Normas;

public interface PersonaOefaDAO extends BaseDAO<PersonaOefa, Long>{

	public PersonaOefa buscarPorIdPersona(PersonaOefa prmBean) throws PersistenciaException;
	
	
}
