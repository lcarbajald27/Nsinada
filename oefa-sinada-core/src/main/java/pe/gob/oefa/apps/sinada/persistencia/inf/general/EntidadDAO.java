package pe.gob.oefa.apps.sinada.persistencia.inf.general;

import java.util.List;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.persistencia.inf.BaseDAO;
import pe.gob.oefa.apps.sinada.bean.general.Entidad;

public interface EntidadDAO extends BaseDAO<Entidad, Long>{

	public Entidad buscarPorNumeroDocumento(String prmIdBean) throws PersistenciaException;
	
	public List<Entidad> buscarPorRazonSocialSSO(String razonSocial) throws PersistenciaException;
}
