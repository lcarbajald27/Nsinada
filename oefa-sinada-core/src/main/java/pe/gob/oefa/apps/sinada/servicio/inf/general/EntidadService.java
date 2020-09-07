package pe.gob.oefa.apps.sinada.servicio.inf.general;

import java.util.List;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.base.servicio.inf.BaseService;
import pe.gob.oefa.apps.sinada.bean.general.Entidad;

public interface EntidadService extends BaseService<Entidad, Long>{

	public Entidad buscarPorNumeroDocumento(String prmIdBean) throws ServicioException;
	
	public List<Entidad> buscarPorRazonSocialSSO(String razonSocial) throws ServicioException;
	
}
