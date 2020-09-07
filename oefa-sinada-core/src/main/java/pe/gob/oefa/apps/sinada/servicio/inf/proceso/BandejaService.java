package pe.gob.oefa.apps.sinada.servicio.inf.proceso;

import java.util.List;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.persistencia.inf.BaseDAO;
import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.base.servicio.inf.BaseService;
import pe.gob.oefa.apps.sinada.bean.proceso.Bandeja;
import pe.gob.oefa.apps.sinada.bean.proceso.CasoOefa;

public interface BandejaService extends BaseService<Bandeja, Long>
{
	public Bandeja validarBandeja(Bandeja prmBandeja) throws ServicioException;
	
	public Bandeja validarBandejaEfa(Bandeja prmBandeja) throws ServicioException;
	
	public Bandeja validarBandejaOefa(Bandeja prmBandeja) throws ServicioException;
}
