package pe.gob.oefa.apps.sinada.persistencia.impl.maestros;

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
import pe.gob.oefa.apps.sinada.bean.maestros.FormatoCorreo;
import pe.gob.oefa.apps.sinada.persistencia.inf.maestros.FormatoCorreoDAO;

@Transactional
@Repository(value="formatoCorreoDAO")
public class FormatoCorreoDAOImpl implements FormatoCorreoDAO {
	
	@Autowired
	@Qualifier("sinadaSqlSessionFactory")
	SqlSessionFactory sqlSessionFactory;
	
	private String logBase = "oefa-sinada-core:FormatoCorreoDAOImpl";
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public Long insertar(FormatoCorreo prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int actualizar(FormatoCorreo prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int eliminar(FormatoCorreo prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public FormatoCorreo buscarPorId(Long prmIdBean)
			throws PersistenciaException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;	
		List<FormatoCorreo> lst = null;
		FormatoCorreo oBean = null;
		
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("formatoCorreo.buscarXid", prmIdBean);
			
			if(lst!=null && lst.size()>0){
				
				oBean = lst.get(0);
				
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
	public List<?> listar(FormatoCorreo prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

}
