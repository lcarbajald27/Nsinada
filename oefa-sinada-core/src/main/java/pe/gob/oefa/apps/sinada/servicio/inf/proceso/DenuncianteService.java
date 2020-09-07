package pe.gob.oefa.apps.sinada.servicio.inf.proceso;

import java.util.List;

import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.base.servicio.inf.BaseService;
import pe.gob.oefa.apps.sinada.bean.proceso.Denunciante;
import pe.gob.oefa.apps.sinada.bean.proceso.view.DenuncianteDetalle;


public interface DenuncianteService extends BaseService<Denunciante, Long>{

	public List<DenuncianteDetalle> listarDenunciantes(DenuncianteDetalle prmBean) throws ServicioException;
	
	
}
