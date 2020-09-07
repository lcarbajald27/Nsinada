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
import pe.gob.oefa.apps.sinada.bean.proceso.EvaluacionInforme;
import pe.gob.oefa.apps.sinada.bean.proceso.EvaluacionRechazo;
import pe.gob.oefa.apps.sinada.persistencia.inf.proceso.EvaluacionInformeDAO;

@Transactional
@Repository(value="evaluacionInformeDAO")
public class EvaluacionInformeDAOImpl implements EvaluacionInformeDAO {

	
	@Autowired
	@Qualifier("sinadaSqlSessionFactory")
	SqlSessionFactory sqlSessionFactory;
	
	private String logBase = "oefa-sinada-core:EvaluacionInformeDAOImpl";
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public Long insertar(EvaluacionInforme prmBean)
			throws PersistenciaException {
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession(false);
			int filasAfectadas = sqlSession.insert("evaluacionInforme.agregar", prmBean);
			if (filasAfectadas>0 && prmBean.getIdEvaluacionInforme()>0)
			{
				return prmBean.getIdEvaluacionInforme();
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
	public int actualizar(EvaluacionInforme prmBean)
			throws PersistenciaException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			int filasAfectadas = sqlSession.update("evaluacionInforme.modificar", prmBean);
		
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
	public int eliminar(EvaluacionInforme prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			int filasAfectadas = sqlSession.update("evaluacionInforme.eliminar", prmBean.getIdEvaluacionInforme());
			
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
	public EvaluacionInforme buscarPorId(Long prmIdBean)
			throws PersistenciaException {
		SqlSession sqlSession = null;	
		List<EvaluacionInforme> lst = null;
		EvaluacionInforme oBean = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("evaluacionInforme.buscarPorId", prmIdBean);
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
	public List<?> listar(EvaluacionInforme prmBean)
			throws PersistenciaException {
		SqlSession sqlSession = null;	
		List<EvaluacionInforme> lst = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("evaluacionInforme.listar", prmBean);
		
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
	public EvaluacionInforme buscarPorIdInformeAccion(EvaluacionInforme prmBean)
			throws PersistenceException {
		SqlSession sqlSession = null;	
		List<EvaluacionInforme> lst = null;
		EvaluacionInforme oBean = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("evaluacionInforme.buscarPorIdInformeAccion", prmBean);
			if(lst!=null && lst.size()>0){
				oBean=lst.get(0);
			}
			
			
		}
		catch (Exception e) {
			logger.error(this.logBase+ " : buscarPorIdInformeAccion" + e.getMessage());
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return oBean;
	}

}
