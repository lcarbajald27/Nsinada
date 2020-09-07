package pe.gob.oefa.apps.sinada.persistencia.inf.proceso;

import java.util.List;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.persistencia.inf.BaseDAO;
import pe.gob.oefa.apps.sinada.bean.proceso.Denunciante;
import pe.gob.oefa.apps.sinada.bean.proceso.view.DenuncianteDetalle;
import pe.gob.oefa.apps.sinada.bean.sirefa.Efa;
import pe.gob.oefa.apps.sinada.bean.sirin.Normas;

public interface DenuncianteDAO extends BaseDAO<Denunciante, Long>{

	public List<DenuncianteDetalle> listarDenunciantes(DenuncianteDetalle prmBean) throws PersistenciaException;

	Long insertarDenuncianteNoTransaccional(Denunciante prmBean)throws PersistenciaException;
	
}
