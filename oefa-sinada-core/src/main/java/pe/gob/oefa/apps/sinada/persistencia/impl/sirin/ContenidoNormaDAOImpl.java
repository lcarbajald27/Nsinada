package pe.gob.oefa.apps.sinada.persistencia.impl.sirin;

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
import pe.gob.oefa.apps.sinada.bean.sirin.ContenidoNorma;
import pe.gob.oefa.apps.sinada.persistencia.inf.sirin.ContenidoNormaDAO;

@Transactional
@Repository(value="contenidoNormaDAO")
public class ContenidoNormaDAOImpl implements ContenidoNormaDAO {

	
	@Autowired
	@Qualifier("sinadaSqlSessionFactory")
	SqlSessionFactory sqlSessionFactory;
	
	private String logBase = "oefa-sinada-core:ContenidoNormaDAOImpl";
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public Long insertar(ContenidoNorma prmBean) throws PersistenciaException {
		return null;
		// TODO Auto-generated method stub
		
	}

	@Override
	public int actualizar(ContenidoNorma prmBean) throws PersistenciaException {
		return 0;
		// TODO Auto-generated method stub
		
	}

	@Override
	public int eliminar(ContenidoNorma prmBean) throws PersistenciaException {
		return 0;
		// TODO Auto-generated method stub
	}

	@Override
	public ContenidoNorma buscarPorId(Long prmIdBean)
			throws PersistenciaException {
		SqlSession sqlSession = null;	
		List<ContenidoNorma> lst = null;
		ContenidoNorma oContenidoNorma = new ContenidoNorma();
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("contenidoNorma.buscarPorId", prmIdBean);
			if(lst!=null && lst.size()>0){
				oContenidoNorma=lst.get(0);
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
		return oContenidoNorma;
	}

	@Override
	public List<?> listar(ContenidoNorma prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;	
		List<ContenidoNorma> lst = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("contenidoNorma.listar",prmBean);

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
