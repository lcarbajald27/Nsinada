package pe.gob.oefa.apps.sinada.persistencia.impl.proceso;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.sinada.bean.proceso.BandejaDetalle;
import pe.gob.oefa.apps.sinada.persistencia.inf.proceso.BandejaDetalleDAO;

@Transactional
@Repository(value="bandejaDetalleDAO")
public class BandejaDetalleDAOImpl implements BandejaDetalleDAO {

	@Autowired
	@Qualifier("sinadaSqlSessionFactory")
	SqlSessionFactory sqlSessionFactory;
	
	private String logBase = "oefa-sinada-core:BandejaDetalleDAOImpl";
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	
	@Override
	public Long insertar(BandejaDetalle prmBean)
			throws PersistenciaException {
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession(false);
			int filasAfectadas = sqlSession.insert("bandejaDetalle.agregar", prmBean);
			if (filasAfectadas>0 && prmBean.getIdBandejaDetalle()>0)
			{
				return prmBean.getIdBandejaDetalle();
			}
		}
		catch (Exception e)
		{
			logger.error(this.logBase+ " : insertar" + e.getMessage());
			throw new PersistenciaException(e);
		}
		finally 
		{
			sqlSession.close();
		}
		return null;
	}

	@Override
	public int actualizar(BandejaDetalle prmBean)
			throws PersistenciaException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			int filasAfectadas = sqlSession.update("bandejaDetalle.modificar", prmBean);
		
			return filasAfectadas;
		}
		catch (Exception e)
		{
			logger.error(this.logBase+ " : actualizar" + e.getMessage());
			// TODO: handle exception
//			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw new PersistenciaException(e);
		}
		finally 
		{
			sqlSession.close();
		}
	}

	@Override
	public int eliminar(BandejaDetalle prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			int filasAfectadas = sqlSession.update("bandejaDetalle.eliminar", prmBean.getIdBandejaDetalle());
			
			return filasAfectadas;
		}
		catch (Exception e)
		{
			logger.error(this.logBase+ " : eliminar" + e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw new PersistenciaException(e);
		}
		finally 
		{
			sqlSession.close();
		}
	}

	@Override
	public BandejaDetalle buscarPorId(Long prmIdBean)
			throws PersistenciaException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;	
		List<BandejaDetalle> lst = null;
		BandejaDetalle bean = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("bandejaDetalle.buscarPorId",prmIdBean);
			if(lst!=null && lst.size()>0){
				bean = lst.get(0);
			}
		}
		catch (Exception e) {
			logger.error(this.logBase+ " : eliminar" + e.getMessage());
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return bean;
	}

	@Override
	public List<?> listar(BandejaDetalle prmBean)
			throws PersistenciaException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;	
		List<BandejaDetalle> lst = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("bandejaDetalle.listar",prmBean);

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
	public List<BandejaDetalle> buscarEspecialistaSinada(BandejaDetalle prmBean)
			throws PersistenciaException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;	
		List<BandejaDetalle> lst = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("bandejaDetalle.buscarEspecialistaSinada",prmBean);

		}
		catch (Exception e) {
			logger.error(this.logBase+ " : buscarEspecialistaSinada" + e.getMessage());
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return lst;
	}

	@Override
	public BandejaDetalle buscarDenunciaPorIdDenunciaTipoBandejaIdBandeja(
			BandejaDetalle prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;	
		List<BandejaDetalle> lst = null;
		BandejaDetalle bean = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("bandejaDetalle.buscarDenunciaPorIdDenunciaTipoBandejaIdBandeja",prmBean);
			if(lst!=null && lst.size()>0){
				bean = lst.get(0);
			}
		}
		catch (Exception e) {
			logger.error(this.logBase+ " : buscarDenunciaPorIdDenunciaTipoBandejaIdBandeja" + e.getMessage());
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return bean;
	}
	
	
	@Transactional(readOnly=true)
	@Override
	public Long insertarBandejaDetalleNoTransaccional(BandejaDetalle prmBean)
			throws PersistenciaException {
		SqlSession sqlSession = null;
		try
		{
			if (prmBean.getIdUnidadOrganica() != null && prmBean.getIdUnidadOrganica() == 0){
				prmBean.setIdUnidadOrganica(null);
			}
			sqlSessionFactory.getConfiguration().setJdbcTypeForNull(JdbcType.NULL);
			sqlSession = sqlSessionFactory.openSession(false);
			int filasAfectadas = sqlSession.insert("bandejaDetalle.agregar", prmBean);
			if (filasAfectadas>0 && prmBean.getIdBandejaDetalle()>0)
			{
				return prmBean.getIdBandejaDetalle();
			}
		}
		catch (Exception e)
		{
			logger.error(this.logBase+ " : insertarBandejaDetalleNoTransaccional" + e.getMessage());
			throw new PersistenciaException(e);
		}
		finally 
		{
			sqlSession.close();
		}
		return null;
	}

}
