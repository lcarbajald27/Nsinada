package pe.gob.oefa.apps.sinada.persistencia.impl.seguridad;

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
import pe.gob.oefa.apps.sinada.bean.seguridad.SessionSistema;
import pe.gob.oefa.apps.sinada.bean.seguridad.Usuario;
import pe.gob.oefa.apps.sinada.persistencia.inf.seguridad.SessionSistemaDAO;

@Transactional
@Repository(value="sessionSistemaDAO")
public class SessionSistemaDAOImpl implements SessionSistemaDAO {

	@Autowired
	@Qualifier("sinadaSqlSessionFactory")
	SqlSessionFactory sqlSessionFactory;
	
	private String logBase = "oefa-sinada-SessionSistemaDAOImpl";
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public Long insertar(SessionSistema prmBean) throws PersistenciaException {
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession(false);
			int filasAfectadas = sqlSession.insert("sessionSistema.agregar", prmBean);
			if (filasAfectadas>0 && prmBean.getIdSession()>0)
			{
				return prmBean.getIdSession();
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
	public int actualizar(SessionSistema prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int eliminar(SessionSistema prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			int filasAfectadas = sqlSession.update("sessionSistema.eliminar", prmBean.getIdSession());
			
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
	public SessionSistema buscarPorId(Long prmIdBean)
			throws PersistenciaException {
		SqlSession sqlSession = null;	
		List<SessionSistema> lst = null;
		SessionSistema oBean =null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("sessionSistema.buscarPorId", prmIdBean);
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
	public List<?> listar(SessionSistema prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

}
