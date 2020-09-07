package pe.gob.oefa.apps.sinada.servicio.inf.proceso;

import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.base.servicio.inf.BaseService;
import pe.gob.oefa.apps.sinada.bean.proceso.PersonaOefa;

public interface PersonaOefaService extends BaseService<PersonaOefa, Long>{

	public PersonaOefa buscarPorIdPersona(PersonaOefa prmBean) throws ServicioException;
	
	
}
