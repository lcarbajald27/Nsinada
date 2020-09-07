package pe.gob.oefa.apps.sinada.servicio.inf.sirefa;

import java.util.List;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.base.servicio.inf.BaseService;
import pe.gob.oefa.apps.sinada.bean.sirefa.Efa;

public interface EfaService extends BaseService<Efa, Long>
{
	
	public List<Efa> buscarPorFiltros(Efa efa) throws ServicioException; 

	public Efa buscarEfaPorIdEntidad(Efa efa) throws ServicioException;
	
	public List<Efa> listarEfaDerivar(Efa efa) throws ServicioException;
	
	public List<Efa> buscarPorIdEfaPorUbigeo(Efa efa) throws ServicioException;
	
}


