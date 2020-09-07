package pe.gob.oefa.apps.sinada.servicio.inf.proceso.view;

import java.util.List;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.persistencia.inf.BaseDAO;
import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.base.servicio.inf.BaseService;
import pe.gob.oefa.apps.sinada.bean.proceso.view.BandejaDenuncia;

public interface BandejaDenunciaService extends BaseService<BandejaDenuncia, Long>{

	public List<BandejaDenuncia> listarBandejaOrganoCompetente(BandejaDenuncia prmBean) throws ServicioException;
	
	public List<BandejaDenuncia> listarBandejaAsignacionDeDenuncias(BandejaDenuncia prmBean) throws ServicioException;
	
	public List<BandejaDenuncia> listarBandejaEspecialistaSinada(BandejaDenuncia prmBean) throws ServicioException;
	
	public BandejaDenuncia buscarPorIdBandejaDetalle(long id) throws ServicioException;
	
	public List<BandejaDenuncia> listarBandejaOrganoCompententeCompleto(BandejaDenuncia prmBean) throws ServicioException;
	
	public List<BandejaDenuncia> buscarDenunciaOefaPorIdDenunciaDireccionSupSubDireccion(BandejaDenuncia prmBean) throws ServicioException;
	
	public List<BandejaDenuncia> listarBandejaEntidadBasicoCompleto(BandejaDenuncia prmBean) throws ServicioException;
	
	
}
