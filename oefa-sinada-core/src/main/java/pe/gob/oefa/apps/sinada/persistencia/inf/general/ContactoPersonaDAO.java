package pe.gob.oefa.apps.sinada.persistencia.inf.general;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.persistencia.inf.BaseDAO;
import pe.gob.oefa.apps.sinada.bean.general.ContactoPersona;
import pe.gob.oefa.apps.sinada.bean.sirefa.Contacto;

public interface ContactoPersonaDAO extends BaseDAO<ContactoPersona, Long>{

	public ContactoPersona validarContacto(ContactoPersona prmData)throws PersistenciaException;
	
}
