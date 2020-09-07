package pe.gob.oefa.apps.sinada.persistencia.impl.proceso;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.sinada.bean.proceso.Bandeja;
import pe.gob.oefa.apps.sinada.persistencia.inf.proceso.BandejaDAO;

@Transactional
@Repository(value="bandejaDAO")
public class BandejaDAOImpl implements BandejaDAO {

	@Autowired
	@Qualifier("sinadaSqlSessionFactory")
	SqlSessionFactory sqlSessionFactory;
	
	private String logBase = "oefa-sinada-core:BandejaDAOImpl";
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public Long insertar(Bandeja prmBean) throws PersistenciaException {
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession(false);
			int filasAfectadas = sqlSession.insert("bandeja.agregar", prmBean);
			if (filasAfectadas>0 && prmBean.getIdBandeja()>0)
			{
				return prmBean.getIdBandeja();
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
	public int actualizar(Bandeja prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			int filasAfectadas = sqlSession.update("bandeja.modificar", prmBean);
		
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
	public int eliminar(Bandeja prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			int filasAfectadas = sqlSession.update("bandeja.eliminar", prmBean.getIdBandeja());
			
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
	public Bandeja buscarPorId(Long prmIdBean) throws PersistenciaException {
		SqlSession sqlSession = null;	
		List<Bandeja> lst = null;
		Bandeja oBandeja = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("bandeja.buscarPorId", prmIdBean);
			if(lst!=null && lst.size()>0){
				oBandeja=lst.get(0);
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
		return oBandeja;
	}

	@Override
	public List<?> listar(Bandeja prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bandeja validarBandeja(Bandeja prmBandeja)
			throws PersistenciaException {
		SqlSession sqlSession = null;	
		List<Bandeja> lst = null;
		Bandeja oBandeja = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("bandeja.validarBandeja", prmBandeja);
			if(lst!=null && lst.size()>0){
				oBandeja=lst.get(0);
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
		return oBandeja;
	}

	@Override
	public Bandeja validarBandejaEfa(Bandeja prmBandeja)
			throws PersistenciaException {
		SqlSession sqlSession = null;	
		List<Bandeja> lst = null;
		Bandeja oBandeja = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("bandeja.validarBandejaEfa", prmBandeja);
			if(lst!=null && lst.size()>0){
				oBandeja=lst.get(0);
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
		return oBandeja;
	}

	@Override
	public Bandeja validarBandejaOefa(Bandeja prmBandeja)
			throws PersistenciaException {
		SqlSession sqlSession = null;	
		List<Bandeja> lst = null;
		Bandeja oBandeja = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("bandeja.validarBandejaOefa", prmBandeja);
			if(lst!=null && lst.size()>0){
				oBandeja=lst.get(0);
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
		return oBandeja;
	}
	
	

	
	@Transactional(readOnly=true)
	@Override
	public Long validarBandejaNoTransaccional(Bandeja prmBean) throws PersistenciaException {
		SqlSession sqlSession = null;
		List<Bandeja> lst = null;
		Bandeja oBandeja = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession(false);
			lst = sqlSession.selectList("bandeja.validarBandeja", prmBean);
			if(lst!=null && lst.size()>0){
				oBandeja=lst.get(0);
			}
			
			
			if(oBandeja==null){
				int filasAfectadas = sqlSession.insert("bandeja.agregar", prmBean);
				if (filasAfectadas>0 && prmBean.getIdBandeja()>0)
				{
					return prmBean.getIdBandeja();
				}
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

}
