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
import pe.gob.oefa.apps.sinada.bean.general.AuditoriaDetalle;
import pe.gob.oefa.apps.sinada.persistencia.inf.general.AuditoriaDetalleDAO;

@Transactional
@Repository(value="auditoriaDetalleDAO")
public class AuditoriaDetalleDAOImpl implements AuditoriaDetalleDAO {

	
	@Autowired
	@Qualifier("sinadaSqlSessionFactory")
	SqlSessionFactory sqlSessionFactory;
	
	private String logBase = "oefa-sinada-core:AuditoriaDetalleDAOImpl";
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public Long insertar(AuditoriaDetalle prmBean) throws PersistenciaException {
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession(false);
			
			
			int filasAfectadas = sqlSession.insert("auditoriaDetalle.agregar", prmBean);
			if (filasAfectadas>0 && prmBean.getIdAuditoriaDetalle()>0)
			{
				return prmBean.getIdAuditoriaDetalle();
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
	public int actualizar(AuditoriaDetalle prmBean)
			throws PersistenciaException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int eliminar(AuditoriaDetalle prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public AuditoriaDetalle buscarPorId(Long prmIdBean)
			throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> listar(AuditoriaDetalle prmBean)
			throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

}
