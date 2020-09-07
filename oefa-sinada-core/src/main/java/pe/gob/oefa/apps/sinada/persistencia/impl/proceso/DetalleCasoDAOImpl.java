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
import pe.gob.oefa.apps.sinada.bean.proceso.DetalleCaso;
import pe.gob.oefa.apps.sinada.persistencia.inf.proceso.DetalleCasoDAO;

@Transactional
@Repository(value="detalleCasoDAO")
public class DetalleCasoDAOImpl implements DetalleCasoDAO {

	@Autowired
	@Qualifier("sinadaSqlSessionFactory")
	SqlSessionFactory sqlSessionFactory;
	
	private String logBase = "oefa-sinada-core:DetalleCasoDAOImpl";
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public Long insertar(DetalleCaso prmBean) throws PersistenciaException {
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession(false);
			int filasAfectadas = sqlSession.insert("detalleCaso.agregar", prmBean);
			if (filasAfectadas>0 && prmBean.getIdDetalleCaso()>0)
			{
				return prmBean.getIdDetalleCaso();
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
	public int actualizar(DetalleCaso prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			int filasAfectadas = sqlSession.update("detalleCaso.modificar", prmBean);
		
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
	public int eliminar(DetalleCaso prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			int filasAfectadas = sqlSession.update("detalleCaso.eliminar", prmBean.getIdDetalleCaso());
			
			return filasAfectadas;
		}
		catch (Exception e)
		{
			logger.error(this.logBase+ " : eliminar" + e.getMessage());
			// TODO: handle exception
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw new PersistenciaException(e);
		}
		finally 
		{
			sqlSession.close();
		}
	}

	@Override
	public DetalleCaso buscarPorId(Long prmIdBean) throws PersistenciaException {
		SqlSession sqlSession = null;	
		List<DetalleCaso> lst = null;
		DetalleCaso bean = new DetalleCaso();
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("detalleCaso.buscarPorId",prmIdBean);
			bean = lst.get(0);
		}
		catch (Exception e) {
			logger.error(this.logBase+ " : buscarPorId" + e.getMessage());
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return bean;
	}

	@Override
	public List<?> listar(DetalleCaso prmBean) throws PersistenciaException {
		SqlSession sqlSession = null;	
		List<DetalleCaso> lst = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("detalleCaso.listar",prmBean);

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
	public DetalleCaso buscarPorTipoCasoCodigoPadreNivelAndDescripcion(
			DetalleCaso prmData) throws PersistenciaException {
		SqlSession sqlSession = null;	
		List<DetalleCaso> lst = null;
		DetalleCaso bean = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("detalleCaso.buscarPorTipoCasoCodigoPadreNivelAndDescripcion",prmData);
			if(lst!=null && lst.size()>0){
				bean = lst.get(0);
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
		return bean;
	}

	@Override
	public List<DetalleCaso> listarDetalleCasoRegistradosDenuncia(
			DetalleCaso prmData) throws PersistenciaException {
		SqlSession sqlSession = null;	
		List<DetalleCaso> lst = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("detalleCaso.listarDetalleCasoRegistradosDenuncia",prmData);

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

}
