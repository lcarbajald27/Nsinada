package pe.gob.oefa.apps.sinada.persistencia.inf.maestros;

import java.util.List;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.persistencia.inf.BaseDAO;
import pe.gob.oefa.apps.sinada.bean.maestros.Maestro;

public interface MaestroDAO extends BaseDAO<Maestro, Long>{

	public List<?> buscarPorCodigoTabla(Maestro maestro) throws PersistenciaException;

	public List<Maestro> listarCodigoMaestro(Maestro maestro) throws PersistenciaException;

	public List<?> listarHijos(Maestro maestro) throws PersistenciaException;
	
	public List<Maestro> listarRegistroHijo(Maestro maestro) throws PersistenciaException;
	
}
