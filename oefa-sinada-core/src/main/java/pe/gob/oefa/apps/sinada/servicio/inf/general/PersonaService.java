package pe.gob.oefa.apps.sinada.servicio.inf.general;

import java.util.List;
import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.base.servicio.inf.BaseService;
import pe.gob.oefa.apps.sinada.bean.general.Persona;


public interface PersonaService extends BaseService<Persona, Long>{

	public Persona buscarPorNumeroDocumento(String prmIdBean) throws ServicioException;
	public List<Persona> buscarPorNombreCompletoSSO(String nombreCompleto) throws ServicioException;
	
}
