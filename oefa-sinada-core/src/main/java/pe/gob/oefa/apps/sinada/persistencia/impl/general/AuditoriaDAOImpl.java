package pe.gob.oefa.apps.sinada.persistencia.impl.general;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.sinada.bean.general.Auditoria;
import pe.gob.oefa.apps.sinada.persistencia.inf.general.AuditoriaDAO;


@Transactional
@Repository(value="auditoriaDAO")
public class AuditoriaDAOImpl implements AuditoriaDAO {

	
	
	@Autowired
	@Qualifier("sinadaSqlSessionFactory")
	SqlSessionFactory sqlSessionFactory;
	
	private String logBase = "oefa-sinada-core:AuditoriaDAOImpl";
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public Long insertar(Auditoria prmBean) throws PersistenciaException {
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession(false);
			
			
			int filasAfectadas = sqlSession.insert("auditoria.agregar", prmBean);
			if (filasAfectadas>0 && prmBean.getIdAuditoria()>0)
			{
				return prmBean.getIdAuditoria();
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
	public int actualizar(Auditoria prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int eliminar(Auditoria prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Auditoria buscarPorId(Long prmIdBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> listar(Auditoria prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

}
