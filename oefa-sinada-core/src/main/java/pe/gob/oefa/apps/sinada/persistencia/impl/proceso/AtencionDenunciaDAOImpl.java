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
import pe.gob.oefa.apps.sinada.bean.proceso.AtencionDenuncia;
import pe.gob.oefa.apps.sinada.bean.proceso.Bandeja;
import pe.gob.oefa.apps.sinada.persistencia.inf.proceso.AtencionDenunciaDAO;

@Transactional
@Repository(value="atencionDenunciaDAO")
public class AtencionDenunciaDAOImpl implements AtencionDenunciaDAO {

	@Autowired
	@Qualifier("sinadaSqlSessionFactory")
	SqlSessionFactory sqlSessionFactory;
	
	private String logBase = "oefa-sinada-core:ArchivoAtencionDAOImpl";
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	
	@Override
	public Long insertar(AtencionDenuncia prmBean) throws PersistenciaException {
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession(false);
			int filasAfectadas = sqlSession.insert("atencionDenuncia.agregar", prmBean);
			if (filasAfectadas>0 && prmBean.getIdAtencionDenuncia()>0)
			{
				return prmBean.getIdAtencionDenuncia();
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
	public int actualizar(AtencionDenuncia prmBean)
			throws PersistenciaException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			int filasAfectadas = sqlSession.update("atencionDenuncia.modificar", prmBean);
		
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
	public int eliminar(AtencionDenuncia prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			int filasAfectadas = sqlSession.update("atencionDenuncia.eliminar", prmBean.getIdAtencionDenuncia());
			
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
	public AtencionDenuncia buscarPorId(Long prmIdBean)
			throws PersistenciaException {
		SqlSession sqlSession = null;	
		List<AtencionDenuncia> lst = null;
		AtencionDenuncia oAtencionDenuncia = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("atencionDenuncia.buscarPorId", prmIdBean);
			if(lst!=null && lst.size()>0){
				oAtencionDenuncia=lst.get(0);
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
		return oAtencionDenuncia;
	}

	@Override
	public List<?> listar(AtencionDenuncia prmBean)
			throws PersistenciaException {
		SqlSession sqlSession = null;	
		List<AtencionDenuncia> lst = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("atencionDenuncia.listar", prmBean);
		
		}
		catch (Exception e) {
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return lst;
	}

	@Override
	public List<AtencionDenuncia> buscarAtencionDenunciaRechazada(
			AtencionDenuncia prmData) throws PersistenciaException {
		SqlSession sqlSession = null;	
		List<AtencionDenuncia> lst = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("atencionDenuncia.listarAtencionRechazada", prmData);
		
		}
		catch (Exception e) {
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return lst;
	}

}
