package pe.gob.oefa.apps.sinada.servicio.inf.sirin;

import java.util.List;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.persistencia.inf.BaseDAO;
import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.base.servicio.inf.BaseService;
import pe.gob.oefa.apps.sinada.bean.sirefa.Efa;
import pe.gob.oefa.apps.sinada.bean.sirin.Normas;

public interface NormasService extends BaseService<Normas, Long>{

	public Normas buscarPorNumeroNorma(Normas prmNormas) throws ServicioException;
	public List<Normas> buscarPorEntidad(Normas prmNormas) throws ServicioException;
}
