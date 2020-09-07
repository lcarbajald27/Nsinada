package pe.gob.oefa.apps.sinada.servicio.inf.proceso;

import java.util.List;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.persistencia.inf.BaseDAO;
import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.base.servicio.inf.BaseService;
import pe.gob.oefa.apps.sinada.bean.proceso.Bandeja;
import pe.gob.oefa.apps.sinada.bean.proceso.BandejaDetalle;
import pe.gob.oefa.apps.sinada.bean.proceso.CasoOefa;
import pe.gob.oefa.apps.sinada.bean.proceso.view.BandejaDenuncia;

public interface BandejaDetalleService extends BaseService<BandejaDetalle, Long>
{
	public List<BandejaDetalle> buscarEspecialistaSinada(BandejaDetalle prmBean)throws ServicioException;
	
	public BandejaDetalle buscarDenunciaPorIdDenunciaTipoBandejaIdBandeja(BandejaDetalle prmBean)throws ServicioException;



}
