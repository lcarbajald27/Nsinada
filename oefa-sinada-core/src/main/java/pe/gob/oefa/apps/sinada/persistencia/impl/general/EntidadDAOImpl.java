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
import pe.gob.oefa.apps.sinada.bean.general.Entidad;
import pe.gob.oefa.apps.sinada.persistencia.inf.general.EntidadDAO;

@Transactional
@Repository(value="entidadDAO")
public class EntidadDAOImpl implements EntidadDAO {

	@Autowired
	@Qualifier("sinadaSqlSessionFactory")
	SqlSessionFactory sqlSessionFactory;
	
	private String logBase = "oefa-sinada-core:EntidadDAOImpl";
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public Long insertar(Entidad prmBean) throws PersistenciaException {
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession(false);
			int filasAfectadas = sqlSession.insert("entidad.agregar", prmBean);
			if (filasAfectadas>0 && prmBean.getIdEntidad()>0)
			{
				return prmBean.getIdEntidad();
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
	public int actualizar(Entidad prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			int filasAfectadas = sqlSession.update("entidad.modificar", prmBean);
		
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
	public int eliminar(Entidad prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Entidad buscarPorId(Long prmIdBean) throws PersistenciaException {
		SqlSession sqlSession = null;	
		List<Entidad> lst = null;
		Entidad oEntidad = new Entidad();
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("entidad.buscarPorId", prmIdBean);
			if(lst!=null && lst.size()>0){
				oEntidad=lst.get(0);
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
		return oEntidad;
	}

	@Override
	public List<?> listar(Entidad prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Entidad buscarPorNumeroDocumento(String prmIdBean)
			throws PersistenciaException {
		SqlSession sqlSession = null;	
		List<Entidad> lst = null;
		Entidad oEntidad = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("entidad.buscarPorDocumento", prmIdBean);
			if(lst!=null && lst.size()>0 ){
				oEntidad=lst.get(0);
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
		return oEntidad;
	}

	@Override
	public List<Entidad> buscarPorRazonSocialSSO(String razonSocial)
			throws PersistenciaException {
		SqlSession sqlSession = null;	
		List<Entidad> lst = new ArrayList<Entidad>();

		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("entidad.buscarPorRazonSocialSSO", razonSocial);
			if(lst==null ){
				lst= new ArrayList<Entidad>();
			}
			
		}
		catch (Exception e) {
			logger.error(this.logBase+ " : buscarPorRazonSocialSSO" + e.getMessage());
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return lst;
	}

}
