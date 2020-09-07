package pe.gob.oefa.apps.sinada.persistencia.inf.proceso;

import java.util.List;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.persistencia.inf.BaseDAO;
import pe.gob.oefa.apps.sinada.bean.proceso.Bandeja;
import pe.gob.oefa.apps.sinada.bean.proceso.BandejaDetalle;
import pe.gob.oefa.apps.sinada.bean.proceso.CasoOefa;
import pe.gob.oefa.apps.sinada.bean.proceso.view.BandejaDenuncia;

public interface BandejaDetalleDAO extends BaseDAO<BandejaDetalle, Long>
{
		public List<BandejaDetalle> buscarEspecialistaSinada(BandejaDetalle prmBean)throws PersistenciaException;
		
		public BandejaDetalle buscarDenunciaPorIdDenunciaTipoBandejaIdBandeja(BandejaDetalle prmBean)throws PersistenciaException;

		Long insertarBandejaDetalleNoTransaccional(BandejaDetalle prmBean)
				throws PersistenciaException;


}
