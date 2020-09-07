package pe.gob.oefa.apps.sinada.servicio.inf.general;

import java.util.List;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.persistencia.inf.BaseDAO;
import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.base.servicio.inf.BaseService;
import pe.gob.oefa.apps.sinada.bean.general.Persona;
import pe.gob.oefa.apps.sinada.bean.general.PersonaSSO;
import pe.gob.oefa.apps.sinada.bean.sirefa.Contacto;

public interface PersonaSSOService extends BaseService<PersonaSSO, Long>{

	public PersonaSSO buscarPorNumeroDocumento(PersonaSSO prmData) throws ServicioException;
	
	public List<PersonaSSO> listaPersonaSSO(PersonaSSO prmData) throws ServicioException;
	
}
