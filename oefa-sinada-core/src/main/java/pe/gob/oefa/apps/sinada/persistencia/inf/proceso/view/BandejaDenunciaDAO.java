package pe.gob.oefa.apps.sinada.persistencia.inf.proceso.view;

import java.util.List;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.persistencia.inf.BaseDAO;
import pe.gob.oefa.apps.sinada.bean.proceso.Denuncia;
import pe.gob.oefa.apps.sinada.bean.proceso.view.BandejaDenuncia;

public interface BandejaDenunciaDAO extends BaseDAO<BandejaDenuncia, Long>{

	public List<BandejaDenuncia> listarBandejaOrganoCompetente(BandejaDenuncia prmBean) throws PersistenciaException;
	
	public List<BandejaDenuncia> listarBandejaAsignacionDeDenuncias(BandejaDenuncia prmBean) throws PersistenciaException;
	
	public List<BandejaDenuncia> listarBandejaEspecialistaSinada(BandejaDenuncia prmBean) throws PersistenciaException;
	
	public BandejaDenuncia buscarPorIdBandejaDetalle(long id) throws PersistenciaException;
	
	public List<BandejaDenuncia> listarBandejaOrganoCompententeCompleto(BandejaDenuncia prmBean) throws PersistenciaException;
	
	public List<BandejaDenuncia> buscarBandejaDeDenunciasEntidadPorEstado(BandejaDenuncia prmBean) throws PersistenciaException;
	
	public List<BandejaDenuncia> buscarDenunciaOefaPorIdDenunciaDireccionSupSubDireccion(BandejaDenuncia prmBean) throws PersistenciaException;
	
	
	public List<BandejaDenuncia> listarBandejaEntidadBasicoCompleto(BandejaDenuncia prmBean) throws PersistenciaException;
	
	
	
	
	
}
