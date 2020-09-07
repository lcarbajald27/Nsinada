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
import pe.gob.oefa.apps.sinada.bean.proceso.Caso;
import pe.gob.oefa.apps.sinada.bean.proceso.CasoEfa;
import pe.gob.oefa.apps.sinada.persistencia.inf.proceso.CasoDAO;

@Transactional
@Repository(value="casoDAO")
public class CasoDAOImpl implements CasoDAO {

	@Autowired
	@Qualifier("sinadaSqlSessionFactory")
	SqlSessionFactory sqlSessionFactory;
	
	private String logBase = "oefa-sinada-core:CasoDAOImpl";
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public Long insertar(Caso prmBean) throws PersistenciaException {
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession(false);
			int filasAfectadas = sqlSession.insert("caso.agregar", prmBean);
			if (filasAfectadas>0 && prmBean.getIdCaso()>0)
			{
				return prmBean.getIdCaso();
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
	public int actualizar(Caso prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int eliminar(Caso prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();

			int filasAfectadas = sqlSession.update("caso.eliminar", prmBean.getIdCaso());
			
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
	public Caso buscarPorId(Long prmIdBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;	
		Caso oCaso = null;
		List<Caso> lst = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("caso.buscarPorId",prmIdBean);
			if(lst!=null && lst.size()>0){
				oCaso = lst.get(0);
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
		return oCaso;
	}

	@Override
	public List<?> listar(Caso prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;	
		List<Caso> lst = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("caso.listar",prmBean);

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

	@Override
	public long generarCodigoCaso() throws PersistenciaException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;	
		List<Caso> lst = null;
		long codigoCaso =0;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("caso.generarCodigoCaso");
			if(lst!=null && lst.size()>0){
				
				codigoCaso = lst.get(0).getIdCaso();
			}
			

		}
		catch (Exception e) {
			logger.error(this.logBase+ " : generarCodigoCaso" + e.getMessage());
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return codigoCaso;
	}

	@Override
	public Caso validaCasoRegistrado(Caso prmCaso) throws PersistenciaException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;	
		Caso oCaso = null;
		List<Caso> lst = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("caso.validaCasoRegistrado",prmCaso);
			if(lst!=null && lst.size()>0){
				oCaso = lst.get(0);
			}

		}
		catch (Exception e) {
			logger.error(this.logBase+ " : validaCasoRegistrado" + e.getMessage());
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return oCaso;
	}

}
