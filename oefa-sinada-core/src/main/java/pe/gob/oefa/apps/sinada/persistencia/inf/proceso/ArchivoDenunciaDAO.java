package pe.gob.oefa.apps.sinada.persistencia.inf.proceso;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.persistencia.inf.BaseDAO;
import pe.gob.oefa.apps.sinada.bean.proceso.ArchivoDenuncia;
import pe.gob.oefa.apps.sinada.bean.sirefa.Efa;
import pe.gob.oefa.apps.sinada.bean.sirin.Normas;

public interface ArchivoDenunciaDAO extends BaseDAO<ArchivoDenuncia, Long>{

	Long insertarArchivoDenunciaNoTransaccional(ArchivoDenuncia prmBean)
			throws PersistenciaException;

	
	
}
