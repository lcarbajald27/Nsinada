package pe.gob.oefa.apps.sinada.servicio.inf.proceso;

import java.util.List;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.persistencia.inf.BaseDAO;
import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.base.servicio.inf.BaseService;
import pe.gob.oefa.apps.sinada.bean.proceso.DetalleCaso;
import pe.gob.oefa.apps.sinada.bean.sirefa.Efa;
import pe.gob.oefa.apps.sinada.bean.sirin.Normas;

public interface DetalleCasoService extends BaseService<DetalleCaso, Long>{

	public DetalleCaso buscarPorTipoCasoCodigoPadreNivelAndDescripcion(DetalleCaso prmData)throws ServicioException;
	
	public List<DetalleCaso> listarDetalleCasoRegistradosDenuncia(DetalleCaso prmData)throws ServicioException;
	
	
}
