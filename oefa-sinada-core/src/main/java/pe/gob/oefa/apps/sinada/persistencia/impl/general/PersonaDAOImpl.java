package pe.gob.oefa.apps.sinada.persistencia.impl.general;

import java.util.ArrayList;
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
import pe.gob.oefa.apps.sinada.bean.general.Persona;
import pe.gob.oefa.apps.sinada.persistencia.inf.general.PersonaDAO;

@Transactional
@Repository(value="personaDAO")
public class PersonaDAOImpl implements PersonaDAO {

	@Autowired
	@Qualifier("sinadaSqlSessionFactory")
	SqlSessionFactory sqlSessionFactory;
	
	private String logBase = "oefa-sinada-core:personaDAOImpl";
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public Long insertar(Persona prmBean) throws PersistenciaException {
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession(false);
			int filasAfectadas = sqlSession.insert("persona.agregar", prmBean);
			if (filasAfectadas>0 && prmBean.getIdPersona()>0)
			{
				return prmBean.getIdPersona();
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
	public int actualizar(Persona prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			int filasAfectadas = sqlSession.update("persona.modificar", prmBean);
		
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
	public int eliminar(Persona prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Persona buscarPorId(Long prmIdBean) throws PersistenciaException {
		SqlSession sqlSession = null;	
		List<Persona> lst = null;
		Persona oPersona = new Persona();
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("persona.buscarPorId", prmIdBean);
			if(lst!=null && lst.size()>0){
				oPersona=lst.get(0);
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
		return oPersona;
	}

	@Override
	public List<?> listar(Persona prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Persona buscarPorNumeroDocumento(String prmIdBean)
			throws PersistenciaException {
		SqlSession sqlSession = null;	
		List<Persona> lst = null;
		Persona oPersona = new Persona();
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("persona.buscarPorDocumento", prmIdBean);
			if(lst!=null && lst.size()>0 ){
				oPersona=lst.get(0);
			}
			
		}
		catch (Exception e) {
			logger.error(this.logBase+ " : buscarPorNumeroDocumento" + e.getMessage());
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return oPersona;
	}

	@Override
	public List<Persona> buscarPorNombreCompletoSSO(String nombreCompleto)
			throws PersistenciaException {
		SqlSession sqlSession = null;	
		List<Persona> lst = new ArrayList<Persona>();
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("persona.buscarPorNombreCompletoSSO", nombreCompleto);
			if (lst==null) {
				lst=new ArrayList<Persona>();
			}
		}
		catch (Exception e) {
			logger.error(this.logBase+ " : buscarPorNombreCompletoSSO" + e.getMessage());
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return lst;
	}

}
