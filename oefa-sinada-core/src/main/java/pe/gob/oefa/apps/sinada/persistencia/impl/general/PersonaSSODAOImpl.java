package pe.gob.oefa.apps.sinada.persistencia.impl.general;

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
import pe.gob.oefa.apps.sinada.bean.general.PersonaSSO;
import pe.gob.oefa.apps.sinada.persistencia.inf.general.PersonaSSODAO;

@Transactional
@Repository(value="personaSSODAO")
public class PersonaSSODAOImpl implements PersonaSSODAO {

	
	@Autowired
	@Qualifier("sinadaSqlSessionFactory")
	SqlSessionFactory sqlSessionFactory;
	
	private String logBase = "oefa-sinada-core:PersonaSSODAOImpl";
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	
	@Override
	public Long insertar(PersonaSSO prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int actualizar(PersonaSSO prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int eliminar(PersonaSSO prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PersonaSSO buscarPorId(Long prmIdBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> listar(PersonaSSO prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PersonaSSO buscarPorNumeroDocumento(PersonaSSO prmData)
			throws PersistenciaException {
		SqlSession sqlSession = null;	
		List<PersonaSSO> lst = null;
		PersonaSSO oPersona = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("personaSSO.buscarPorNumeroDocumento", prmData);
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
	public List<PersonaSSO> listaPersonaSSO(PersonaSSO prmData)
			throws PersistenciaException {
		SqlSession sqlSession = null;	
		List<PersonaSSO> lst = null;
	
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("personaSSO.listar", prmData);
		
			
		}
		catch (Exception e) {
			logger.error(this.logBase+ " : buscarPorId" + e.getMessage());
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return lst;
	}

}
