package pe.gob.oefa.apps.sinada.servicio.inf.general;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.persistencia.inf.BaseDAO;
import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.base.servicio.inf.BaseService;
import pe.gob.oefa.apps.sinada.bean.general.ContactoPersona;
import pe.gob.oefa.apps.sinada.bean.sirefa.Contacto;

public interface ContactoPersonaService extends BaseService<ContactoPersona, Long>{

	public ContactoPersona validarContacto(ContactoPersona prmData)throws ServicioException;
	
	
}
