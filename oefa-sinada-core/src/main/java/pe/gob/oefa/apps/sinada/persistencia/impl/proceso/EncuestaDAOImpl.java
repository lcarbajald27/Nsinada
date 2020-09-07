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

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.sinada.bean.proceso.Encuesta;
import pe.gob.oefa.apps.sinada.bean.proceso.Notificaciones;
import pe.gob.oefa.apps.sinada.persistencia.inf.proceso.EncuestaDAO;

@Transactional
@Repository(value="encuestaDAO")
public class EncuestaDAOImpl implements EncuestaDAO {

	@Autowired
	@Qualifier("sinadaSqlSessionFactory")
	SqlSessionFactory sqlSessionFactory;
		
	private String logBase = "oefa-sinada-core:EncuestaDAOImpl";
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public Long insertar(Encuesta prmBean) throws PersistenciaException {
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession(false);
			int filasAfectadas = sqlSession.insert("encuesta.agregar", prmBean);
			if (filasAfectadas>0 && prmBean.getIdEncuesta()>0)
			{
				return prmBean.getIdEncuesta();
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
	public int actualizar(Encuesta prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int eliminar(Encuesta prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Encuesta buscarPorId(Long prmIdBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> listar(Encuesta prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;	
		List<Encuesta> lst = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("encuesta.listar",prmBean);

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
