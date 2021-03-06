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
import pe.gob.oefa.apps.sinada.bean.proceso.EvaluacionRechazo;
import pe.gob.oefa.apps.sinada.bean.proceso.InformeAccion;
import pe.gob.oefa.apps.sinada.persistencia.inf.proceso.EvaluacionRechazoDAO;

@Transactional
@Repository(value="evaluacionRechazoDAO")
public class EvaluacionRechazoDAOImpl implements EvaluacionRechazoDAO {
	
	@Autowired
	@Qualifier("sinadaSqlSessionFactory")
	SqlSessionFactory sqlSessionFactory;
	
	private String logBase = "oefa-sinada-core:EvaluacionRechazoDAOImpl";
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public Long insertar(EvaluacionRechazo prmBean)
			throws PersistenciaException {
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession(false);
			int filasAfectadas = sqlSession.insert("evaluacionRechazo.agregar", prmBean);
			if (filasAfectadas>0 && prmBean.getIdEvaluacionRechazo()>0)
			{
				return prmBean.getIdEvaluacionRechazo();
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
	public int actualizar(EvaluacionRechazo prmBean)
			throws PersistenciaException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			int filasAfectadas = sqlSession.update("evaluacionRechazo.modificar", prmBean);
		
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
	public int eliminar(EvaluacionRechazo prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			int filasAfectadas = sqlSession.update("evaluacionRechazo.eliminar", prmBean.getIdEvaluacionRechazo());
			
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
	public EvaluacionRechazo buscarPorId(Long prmIdBean)
			throws PersistenciaException {
		SqlSession sqlSession = null;	
		List<EvaluacionRechazo> lst = null;
		EvaluacionRechazo oBean = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("evaluacionRechazo.buscarPorId", prmIdBean);
			if(lst!=null && lst.size()>0){
				oBean=lst.get(0);
			}
			
			
		}
		catch (Exception e) {
			logger.error(this.logBase+ " : buscarPorId" + e.getMessage());
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return oBean;
	}

	@Override
	public List<?> listar(EvaluacionRechazo prmBean)
			throws PersistenciaException {
		SqlSession sqlSession = null;	
		List<EvaluacionRechazo> lst = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("evaluacionRechazo.listar", prmBean);
		
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
	public EvaluacionRechazo buscarXIdAtencionDenuncia(EvaluacionRechazo prmBean)
			throws PersistenceException {
		SqlSession sqlSession = null;	
		List<EvaluacionRechazo> lst = null;
		EvaluacionRechazo bean = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("evaluacionRechazo.buscarXidAtencion", prmBean);
			if(lst!= null && lst.size()>0){
				bean = lst.get(0);
			}
		}
		catch (Exception e) {
			logger.error(this.logBase+ " : buscarXIdAtencionDenuncia" + e.getMessage());
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return bean;
	}

}
