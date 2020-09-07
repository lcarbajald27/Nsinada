package pe.gob.oefa.apps.sinada.persistencia.inf.proceso;

import java.util.List;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.persistencia.inf.BaseDAO;
import pe.gob.oefa.apps.sinada.bean.proceso.DetalleCaso;
import pe.gob.oefa.apps.sinada.bean.sirefa.Efa;
import pe.gob.oefa.apps.sinada.bean.sirin.Normas;

public interface DetalleCasoDAO extends BaseDAO<DetalleCaso, Long>{

	public DetalleCaso buscarPorTipoCasoCodigoPadreNivelAndDescripcion(DetalleCaso prmData)throws PersistenciaException;
	
	public List<DetalleCaso> listarDetalleCasoRegistradosDenuncia(DetalleCaso prmData)throws PersistenciaException;
	
}
