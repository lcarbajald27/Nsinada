package pe.gob.oefa.apps.sinada.persistencia.inf.proceso;

import java.util.List;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.persistencia.inf.BaseDAO;
import pe.gob.oefa.apps.sinada.bean.proceso.Bandeja;
import pe.gob.oefa.apps.sinada.bean.proceso.CasoOefa;

public interface BandejaDAO extends BaseDAO<Bandeja, Long>
{
	public Bandeja validarBandeja(Bandeja prmBandeja) throws PersistenciaException;
	
	public Bandeja validarBandejaEfa(Bandeja prmBandeja) throws PersistenciaException;
	
	public Bandeja validarBandejaOefa(Bandeja prmBandeja) throws PersistenciaException;

	Long validarBandejaNoTransaccional(Bandeja prmBean)
			throws PersistenciaException;



	
}
