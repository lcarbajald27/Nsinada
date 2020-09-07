package pe.gob.oefa.apps.sinada.servicio.impl.proceso.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.sinada.bean.proceso.view.BandejaDenuncia;
import pe.gob.oefa.apps.sinada.persistencia.inf.proceso.view.BandejaDenunciaDAO;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.view.BandejaDenunciaService;

@Service("bandejaDenunciaService")
@Transactional(readOnly = true)
public class BandejaDenunciaServiceImpl implements BandejaDenunciaService {

	@Autowired
	private BandejaDenunciaDAO bandejaDenunciaDAO;
	
	@Override
	public Long insertar(BandejaDenuncia prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int actualizar(BandejaDenuncia prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int eliminar(BandejaDenuncia prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BandejaDenuncia buscarPorId(Long prmIdBean)
			throws ServicioException {
		List<BandejaDenuncia> lst=null;
		BandejaDenuncia oBandejaDenuncia= new BandejaDenuncia();
		try 
		{ 
			oBandejaDenuncia =  bandejaDenunciaDAO.buscarPorId(prmIdBean);
		
		
		} 
		catch (PersistenciaException e) 
		{
			 
		}  
		return oBandejaDenuncia;
	}

	@Override
	public List<?> listar(BandejaDenuncia prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return bandejaDenunciaDAO.listar(prmBean);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

	@Override
	public List<BandejaDenuncia> listarBandejaOrganoCompetente(
			BandejaDenuncia prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return bandejaDenunciaDAO.listarBandejaOrganoCompetente(prmBean);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

	@Override
	public List<BandejaDenuncia> listarBandejaAsignacionDeDenuncias(
			BandejaDenuncia prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return bandejaDenunciaDAO.listarBandejaAsignacionDeDenuncias(prmBean);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

	@Override
	public List<BandejaDenuncia> listarBandejaEspecialistaSinada(
			BandejaDenuncia prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return bandejaDenunciaDAO.listarBandejaEspecialistaSinada(prmBean);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

	@Override
	public BandejaDenuncia buscarPorIdBandejaDetalle(long id)
			throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return bandejaDenunciaDAO.buscarPorIdBandejaDetalle(id);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

	@Override
	public List<BandejaDenuncia> listarBandejaOrganoCompententeCompleto(
			BandejaDenuncia prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return bandejaDenunciaDAO.listarBandejaOrganoCompententeCompleto(prmBean);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

	@Override
	public List<BandejaDenuncia> buscarDenunciaOefaPorIdDenunciaDireccionSupSubDireccion(
			BandejaDenuncia prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return bandejaDenunciaDAO.buscarDenunciaOefaPorIdDenunciaDireccionSupSubDireccion(prmBean);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

	@Override
	public List<BandejaDenuncia> listarBandejaEntidadBasicoCompleto(
			BandejaDenuncia prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return bandejaDenunciaDAO.listarBandejaEntidadBasicoCompleto(prmBean);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

}
