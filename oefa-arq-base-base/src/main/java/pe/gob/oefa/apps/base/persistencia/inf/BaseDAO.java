package pe.gob.oefa.apps.base.persistencia.inf;

import java.util.List;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;

public interface BaseDAO<B,K>
{
	
	K insertar(B prmBean) throws PersistenciaException;
	 
	int actualizar(B prmBean) throws PersistenciaException;
	 
	int eliminar(B prmBean) throws PersistenciaException;
	 
	B buscarPorId(K prmIdBean) throws PersistenciaException;
		
	List<?> listar(B prmBean) throws PersistenciaException;
}
