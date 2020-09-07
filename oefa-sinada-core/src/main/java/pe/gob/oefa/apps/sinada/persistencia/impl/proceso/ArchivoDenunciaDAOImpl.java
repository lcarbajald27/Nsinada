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
import pe.gob.oefa.apps.sinada.bean.proceso.ArchivoDenuncia;
import pe.gob.oefa.apps.sinada.persistencia.inf.proceso.ArchivoDenunciaDAO;

@Transactional
@Repository(value="archivoDenunciaDAO")
public class ArchivoDenunciaDAOImpl implements ArchivoDenunciaDAO {

	@Autowired
	@Qualifier("sinadaSqlSessionFactory")
	SqlSessionFactory sqlSessionFactory;
		
	private String logBase = "oefa-sinada-core:ArchivoDenunciaDAOImpl";
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public Long insertar(ArchivoDenuncia prmBean) throws PersistenciaException {
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession(false);
			int filasAfectadas = sqlSession.insert("archivoDenuncia.agregar", prmBean);
			if (filasAfectadas>0 && prmBean.getIdArchivoDenuncia()>0)
			{
				return prmBean.getIdArchivoDenuncia();
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
	public int actualizar(ArchivoDenuncia prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int eliminar(ArchivoDenuncia prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			int filasAfectadas = sqlSession.update("archivoDenuncia.eliminar", prmBean.getIdArchivoDenuncia());
			
			return filasAfectadas;
		}
		catch (Exception e)
		{
			logger.error(this.logBase+ " : eliminar" + e.getMessage());
			// TODO: handle exception
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw new PersistenciaException(e);
		}
		finally 
		{
			sqlSession.close();
		}
	}

	@Override
	public ArchivoDenuncia buscarPorId(Long prmIdBean)
			throws PersistenciaException {
		SqlSession sqlSession = null;	
		List<ArchivoDenuncia> lst = null;
		ArchivoDenuncia oarchivoDenuncia = new ArchivoDenuncia();
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("archivoDenuncia.buscarPorId", prmIdBean);
			if(lst!=null && lst.size()>0){
				oarchivoDenuncia=lst.get(0);
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
		return oarchivoDenuncia;
	}

	@Override
	public List<?> listar(ArchivoDenuncia prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;	
		List<ArchivoDenuncia> lst = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("archivoDenuncia.listar",prmBean);

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
	
	
	
	/****************************************************************************************/
	
	@Transactional(readOnly=true)
	@Override
	public Long insertarArchivoDenunciaNoTransaccional(ArchivoDenuncia prmBean) throws PersistenciaException {
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession(false);
			int filasAfectadas = sqlSession.insert("archivoDenuncia.agregar", prmBean);
			if (filasAfectadas>0 && prmBean.getIdArchivoDenuncia()>0)
			{
				return prmBean.getIdArchivoDenuncia();
			}
		}
		catch (Exception e)
		{
			logger.error(this.logBase+ " : insertarArchivoDenunciaNoTransaccional" + e.getMessage());
			throw new PersistenciaException(e);
		}
		finally 
		{
			sqlSession.close();
		}
		return null;
	}

}
