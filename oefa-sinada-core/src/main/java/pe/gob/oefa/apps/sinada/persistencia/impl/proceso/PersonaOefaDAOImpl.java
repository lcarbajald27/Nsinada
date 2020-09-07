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
import pe.gob.oefa.apps.sinada.bean.proceso.InformeAccion;
import pe.gob.oefa.apps.sinada.bean.proceso.PersonaOefa;
import pe.gob.oefa.apps.sinada.persistencia.inf.proceso.PersonaOefaDAO;

@Transactional
@Repository(value="personaOefaDAO")
public class PersonaOefaDAOImpl implements PersonaOefaDAO {

	@Autowired
	@Qualifier("sinadaSqlSessionFactory")
	SqlSessionFactory sqlSessionFactory;
	
	private String logBase = "oefa-sinada-core:PersonaOefaDAOImpl";
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public Long insertar(PersonaOefa prmBean) throws PersistenciaException {
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession(false);
			int filasAfectadas = sqlSession.insert("personaOefa.agregar", prmBean);
			if (filasAfectadas>0 && prmBean.getIdPersonaOefa()>0)
			{
				return prmBean.getIdPersonaOefa();
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
	public int actualizar(PersonaOefa prmBean) throws PersistenciaException {
		return 0;
		
		
	}

	@Override
	public int eliminar(PersonaOefa prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			int filasAfectadas = sqlSession.update("personaOefa.eliminar", prmBean.getIdPersonaOefa());
			
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
	public PersonaOefa buscarPorId(Long prmIdBean) throws PersistenciaException {
		SqlSession sqlSession = null;	
		List<PersonaOefa> lst = null;
		PersonaOefa oPersonaOefa= null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("personaOefa.buscarPorId", prmIdBean);
			if(lst!=null && lst.size()>0){
				oPersonaOefa=lst.get(0);
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
		return oPersonaOefa;
	}

	@Override
	public List<?> listar(PersonaOefa prmBean) throws PersistenciaException {
		SqlSession sqlSession = null;	
		List<PersonaOefa> lst = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("personaOefa.listar", prmBean);
		
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
	public PersonaOefa buscarPorIdPersona(PersonaOefa prmBean)
			throws PersistenciaException {
		SqlSession sqlSession = null;	
		List<PersonaOefa> lst = null;
		PersonaOefa oPersonaOefa= null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("personaOefa.buscarPorIdPersona", prmBean);
			if(lst!=null && lst.size()>0){
				oPersonaOefa=lst.get(0);
			}
			
			
		}
		catch (Exception e) {
			logger.error(this.logBase+ " : buscarPorIdPersona" + e.getMessage());
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return oPersonaOefa;
	}

}
