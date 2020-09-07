package pe.gob.oefa.apps.sinada.persistencia.impl.proceso.view;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.sinada.bean.proceso.view.BandejaDenuncia;
import pe.gob.oefa.apps.sinada.persistencia.inf.proceso.view.BandejaDenunciaDAO;

@Transactional
@Repository(value="bandejaDenunciaDAO")
public class BandejaDenunciaDAOImpl implements BandejaDenunciaDAO {

	@Autowired
	@Qualifier("sinadaSqlSessionFactory")
	SqlSessionFactory sqlSessionFactory;
	
	private String logBase = "oefa-sinada-core:BandejaDenunciaDAOImpl";
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public Long insertar(BandejaDenuncia prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int actualizar(BandejaDenuncia prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int eliminar(BandejaDenuncia prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BandejaDenuncia buscarPorId(Long prmIdBean)
			throws PersistenciaException {
		SqlSession sqlSession = null;	
		List<BandejaDenuncia> lst = null;
		BandejaDenuncia oBandejaDenuncia = new BandejaDenuncia();
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("bandejaEntidad.buscarPorId", prmIdBean);
			oBandejaDenuncia=lst.get(0);
		}
		catch (Exception e) {
			logger.error(this.logBase+ " : buscarPorId" + e.getMessage());
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return oBandejaDenuncia;
	}

	@Override
	public List<?> listar(BandejaDenuncia prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;	
		List<BandejaDenuncia> lst = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("bandejaDenuncia.listar",prmBean);

		}
		catch (Exception e) {
			logger.error(this.logBase+ " : listar" + e.getMessage());
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return lst;
	}

	@Override
	public List<BandejaDenuncia> listarBandejaOrganoCompetente(
			BandejaDenuncia prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;	
		List<BandejaDenuncia> lst = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("bandejaDenuncia.listarBandejaOrganoCompentente",prmBean);

		}
		catch (Exception e) {
			logger.error(this.logBase+ " : listarBandejaOrganoCompetente" + e.getMessage());
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return lst;
	}

	@Override
	public List<BandejaDenuncia> listarBandejaAsignacionDeDenuncias(
			BandejaDenuncia prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;	
		List<BandejaDenuncia> lst = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("bandejaDenuncia.listarBandejaAsignacionDeDenuncias",prmBean);

		}
		catch (Exception e) {
			logger.error(this.logBase+ " : listarBandejaAsignacionDeDenuncias" + e.getMessage());
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return lst;
	}

	@Override
	public List<BandejaDenuncia> listarBandejaEspecialistaSinada(
			BandejaDenuncia prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;	
		List<BandejaDenuncia> lst = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("bandejaDenuncia.listarBandejaEspecialistaSinada",prmBean);

		}
		catch (Exception e) {
			logger.error(this.logBase+ " : listarBandejaEspecialistaSinada" + e.getMessage());
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return lst;
	}

	@Override
	public BandejaDenuncia buscarPorIdBandejaDetalle(long id)
			throws PersistenciaException {
		SqlSession sqlSession = null;	
		List<BandejaDenuncia> lst = null;
		BandejaDenuncia oBandejaDenuncia = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("bandejaDenuncia.buscarPorIdBandejaDetalle", id);
			if(lst!=null && lst.size()>0){
				oBandejaDenuncia=lst.get(0);
			}
			
		}
		catch (Exception e) {
			logger.error(this.logBase+ " : buscarPorIdBandejaDetalle" + e.getMessage());
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return oBandejaDenuncia;
	}

	@Override
	public List<BandejaDenuncia> listarBandejaOrganoCompententeCompleto(
			BandejaDenuncia prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;	
		List<BandejaDenuncia> lst = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("bandejaDenuncia.listarBandejaOrganoCompententeCompleto",prmBean);

		}
		catch (Exception e) {
			logger.error(this.logBase+ " : listarBandejaOrganoCompententeCompleto" + e.getMessage());
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return lst;
	}

	@Override
	public List<BandejaDenuncia> buscarBandejaDeDenunciasEntidadPorEstado(
			BandejaDenuncia prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;	
		List<BandejaDenuncia> lst = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("bandejaDenuncia.buscarBandejaDeDenunciasEntidadPorEstado",prmBean);

		}
		catch (Exception e) {
			logger.error(this.logBase+ " : buscarBandejaDeDenunciasEntidadPorEstado" + e.getMessage());
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return lst;
	}

	@Override
	public List<BandejaDenuncia> buscarDenunciaOefaPorIdDenunciaDireccionSupSubDireccion(
			BandejaDenuncia prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;	
		List<BandejaDenuncia> lst = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("bandejaDenuncia.buscarDenunciaOefaPorIdDenunciaDireccionSupSubDireccion",prmBean);

		}
		catch (Exception e) {
			logger.error(this.logBase+ " : buscarDenunciaOefaPorIdDenunciaDireccionSupSubDireccion" + e.getMessage());
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return lst;
	}

	@Override
	public List<BandejaDenuncia> listarBandejaEntidadBasicoCompleto(
			BandejaDenuncia prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;	
		List<BandejaDenuncia> lst = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("bandejaDenuncia.listarBandejaEntidadBasicoCompleto",prmBean);

		}
		catch (Exception e) {
			logger.error(this.logBase+ " : listarBandejaEntidadBasicoCompleto" + e.getMessage());
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return lst;
	}

}
