package pe.gob.oefa.apps.sinada.persistencia.impl.sirefa;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.sinada.bean.sirefa.Contacto;
import pe.gob.oefa.apps.sinada.persistencia.inf.sirefa.ContactoDAO;

@Transactional
@Repository(value="contactoDAO")
public class ContactoDAOImpl implements ContactoDAO{

	@Autowired
	@Qualifier("sinadaSqlSessionFactory")
	SqlSessionFactory sqlSessionFactory;
	
	private String logBase = "oefa-sinada-core:ContactoDAOImpl";
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public int actualizar(Contacto prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Contacto buscarPorId(Long prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int eliminar(Contacto prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Long insertar(Contacto prmBean) throws PersistenciaException {
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession(false);
			int filasAfectadas = sqlSession.insert("contacto.agregar", prmBean);
			if (filasAfectadas>0 && prmBean.getIdContacto()>0)
			{
				return prmBean.getIdContacto();
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
	public List<?> listar(Contacto prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

}
