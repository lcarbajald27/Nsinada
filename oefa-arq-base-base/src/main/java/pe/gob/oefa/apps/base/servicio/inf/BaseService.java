package pe.gob.oefa.apps.base.servicio.inf;

import java.util.List;

import pe.gob.oefa.apps.base.servicio.exception.ServicioException;

public interface BaseService<B,K>
{
	
	K insertar(B prmBean) throws ServicioException;
	 
	int actualizar(B prmBean) throws ServicioException;
	 
	int eliminar(B prmBean) throws ServicioException;
	 
	B buscarPorId(K prmIdBean) throws ServicioException;
		
	List<?> listar(B prmBean) throws ServicioException;
}
