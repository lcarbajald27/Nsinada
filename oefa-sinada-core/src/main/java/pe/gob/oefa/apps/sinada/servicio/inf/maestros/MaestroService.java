package pe.gob.oefa.apps.sinada.servicio.inf.maestros;

import java.util.List;

import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.base.servicio.inf.BaseService;
import pe.gob.oefa.apps.sinada.bean.maestros.Maestro;

public interface MaestroService extends BaseService<Maestro, Long>
{

	public List<?> buscarPorCodigoTabla(Maestro maestro) throws ServicioException; 
	
	public List<Maestro> listarCodigoMaestro(Maestro maestro) throws ServicioException;
	
	public List<?> listarHijos(Maestro maestro) throws ServicioException; 
	
	public List<Maestro> listarRegistroHijo(Maestro maestro) throws ServicioException;
	
}
