package pe.gob.oefa.apps.sinada.persistencia.impl.sirefa;

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
import pe.gob.oefa.apps.sinada.bean.sirefa.Efa;
import pe.gob.oefa.apps.sinada.persistencia.inf.sirefa.EfaDAO;

@Transactional
@Repository(value="efaDAO")
public class EfaDAOImpl implements EfaDAO {

	@Autowired
	@Qualifier("sinadaSqlSessionFactory")
	SqlSessionFactory sqlSessionFactory;
	
	private String logBase = "oefa-sinada-core:EfaDAOImpl";
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public int actualizar(Efa prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Efa buscarPorId(Long prmBean) throws PersistenciaException {
		SqlSession sqlSession = null;	
		List<Efa> lst = null;
		Efa oEfaBean = new Efa();
		try
		{
	
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("efa.buscarPorId", prmBean);
			if(lst!=null && lst.size()>0){
				
				oEfaBean = lst.get(0);
				
			}
		}
		catch (Exception e) 
		{
			logger.error(this.logBase+ " : buscarPorId" + e.getMessage());
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return oEfaBean;
	}

	@Override
	public int eliminar(Efa prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Long insertar(Efa prmBean) throws PersistenciaException 
	{
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession(false);
			int filasAfectadas = sqlSession.insert("efa.agregar", prmBean);
			if (filasAfectadas>0 && prmBean.getIdEfa()>0)
			{
				return prmBean.getIdEfa();
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
	public List<?> listar(Efa prmBean) throws PersistenciaException 
	{
		SqlSession sqlSession = null;	
		List<Efa> beans = null;
		try
		{
			System.out.println("REST EFA : "+prmBean);
			sqlSession = sqlSessionFactory.openSession();
			beans = sqlSession.selectList("efa.buscarPorFiltros", prmBean);
		}
		catch (Exception e) 
		{
			logger.error(this.logBase+ " : listar" + e.getMessage());
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return beans;
	}

	@Override
	public List<Efa> buscarPorFiltros(Efa efa) throws PersistenciaException
	{
		SqlSession sqlSession = null;	
		List<Efa> beans = null;
		try
		{
			System.out.println("REST EFA : "+efa);
			sqlSession = sqlSessionFactory.openSession();
			beans = sqlSession.selectList("efa.buscarPorFiltros", efa);
		}
		catch (Exception e) 
		{
			logger.error(this.logBase+ " : buscarPorFiltros" + e.getMessage());
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return beans;
	}

	@Override
	public Efa buscarEfaPorIdEntidad(Efa efa) throws PersistenciaException {
		SqlSession sqlSession = null;	
		List<Efa> lst = null;
		Efa oEfaBean = new Efa();
		try
		{
			System.out.println("REST EFA : "+efa);
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("efa.buscarPorIdEntidad", efa);
			if(lst!=null && lst.size()>0){
				
				oEfaBean = lst.get(0);
				
			}
		}
		catch (Exception e) 
		{
			logger.error(this.logBase+ " : buscarEfaPorIdEntidad" + e.getMessage());
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return oEfaBean;
	}

	@Override
	public List<Efa> listarEfaDerivar(Efa efa) throws PersistenciaException {
		SqlSession sqlSession = null;	
		List<Efa> beans = null;
		try
		{
	
			sqlSession = sqlSessionFactory.openSession();
			beans = sqlSession.selectList("efa.listarEfaDerivar", efa);
		}
		catch (Exception e) 
		{
			logger.error(this.logBase+ " : listarEfaDerivar" + e.getMessage());
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return beans;
	}

	@Override
	public List<Efa> buscarPorIdEfaPorUbigeo(Efa efa)
			throws PersistenciaException {
		SqlSession sqlSession = null;	
		List<Efa> beans = null;
		try
		{
	
			sqlSession = sqlSessionFactory.openSession();
			beans = sqlSession.selectList("efa.buscarPorIdEfaPorUbigeo", efa);
		}
		catch (Exception e) 
		{
			logger.error(this.logBase+ " : buscarPorIdEfaPorUbigeo" + e.getMessage());
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return beans;
	}

	@Override
	public boolean existeOD(String codOD) throws PersistenciaException {
		SqlSession sqlSession = null;	
		Integer cantidadOD = 0;
		boolean resultado = false;
		Efa oEfaBean = new Efa();
		try
		{
	
			sqlSession = sqlSessionFactory.openSession();
			cantidadOD = (Integer) sqlSession.selectOne("efa.contarOD", codOD);
			if(cantidadOD!=null && cantidadOD.intValue()>0){
				
				resultado = true;
				
			}
		}
		catch (Exception e) 
		{
			logger.error(this.logBase+ " : contarOD" + e.getMessage());
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return resultado;
	}

	@Override
	public boolean existeALA(String codALA) throws PersistenciaException {
		SqlSession sqlSession = null;	
		Integer cantidadALA = 0;
		boolean resultado = false;
		Efa oEfaBean = new Efa();
		try
		{
	
			sqlSession = sqlSessionFactory.openSession();
			cantidadALA = (Integer) sqlSession.selectOne("efa.contarALA", codALA);
			if(cantidadALA!=null && cantidadALA.intValue()>0){
				
				resultado = true;
				
			}
		}
		catch (Exception e) 
		{
			logger.error(this.logBase+ " : contarALA" + e.getMessage());
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return resultado;
	}

	@Override
	public boolean existeANP(String codANP) throws PersistenciaException {
		SqlSession sqlSession = null;	
		Integer cantidadANP = 0;
		boolean resultado = false;
		Efa oEfaBean = new Efa();
		try
		{
	
			sqlSession = sqlSessionFactory.openSession();
			cantidadANP = (Integer) sqlSession.selectOne("efa.contarANP", codANP);
			if(cantidadANP!=null && cantidadANP.intValue()>0){
				
				resultado = true;
				
			}
		}
		catch (Exception e) 
		{
			logger.error(this.logBase+ " : contarANP" + e.getMessage());
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return resultado;
	}

	@Override
	public boolean existeZA(String codZA) throws PersistenciaException {
		SqlSession sqlSession = null;
		Integer cantidadZA = 0;
		boolean resultado = false;
		Efa oEfaBean = new Efa();
		try
		{

			sqlSession = sqlSessionFactory.openSession();
			cantidadZA = (Integer) sqlSession.selectOne("efa.contarZA", codZA);
			if(cantidadZA!=null && cantidadZA.intValue()>0){

				resultado = true;

			}
		}
		catch (Exception e)
		{
			logger.error(this.logBase+ " : contarZA" + e.getMessage());
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return resultado;
	}
	@Override
	public int buscarCodigoUnidadOrganicaPorOD(String codOD) throws PersistenciaException {
		SqlSession sqlSession = null;
		Integer codUnidad = 0;
		int resultado = 0;
		Efa oEfaBean = new Efa();
		try
		{

			sqlSession = sqlSessionFactory.openSession();
			codUnidad = (Integer) sqlSession.selectOne("efa.codigoUnidadPorOD", codOD);
			if(codUnidad!=null && codUnidad.intValue()>0){

				resultado = codUnidad.intValue();

			}
		}
		catch (Exception e)
		{
			logger.error(this.logBase+ " : buscarCodigoUnidadOrganicaPorOD" + e.getMessage());
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return resultado;
	}
	@Override
	public int buscarCodigoUnidadOrganicaPorALA(String codALA) throws PersistenciaException {
		SqlSession sqlSession = null;
		Integer codUnidad = 0;
		int resultado = 0;
		Efa oEfaBean = new Efa();
		try
		{

			sqlSession = sqlSessionFactory.openSession();
			codUnidad = (Integer) sqlSession.selectOne("efa.codigoUnidadPorALA", codALA);
			if(codUnidad!=null && codUnidad.intValue()>0){

				resultado = codUnidad.intValue();

			}
		}
		catch (Exception e)
		{
			logger.error(this.logBase+ " : buscarCodigoUnidadOrganicaPorALA" + e.getMessage());
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return resultado;
	}
	@Override
	public int buscarCodigoUnidadOrganicaPorANP(String codANP) throws PersistenciaException {
		SqlSession sqlSession = null;
		Integer codUnidad = 0;
		int resultado = 0;
		Efa oEfaBean = new Efa();
		try
		{

			sqlSession = sqlSessionFactory.openSession();
			codUnidad = (Integer) sqlSession.selectOne("efa.codigoUnidadPorANP", codANP);
			if(codUnidad!=null && codUnidad.intValue()>0){

				resultado = codUnidad.intValue();

			}
		}
		catch (Exception e)
		{
			logger.error(this.logBase+ " : buscarCodigoUnidadOrganicaPorANP" + e.getMessage());
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return resultado;
	}
	@Override
	public int buscarCodigoUnidadOrganicaPorZA(String codZA) throws PersistenciaException {
		SqlSession sqlSession = null;
		Integer codUnidad = 0;
		int resultado = 0;
		Efa oEfaBean = new Efa();
		try
		{

			sqlSession = sqlSessionFactory.openSession();
			codUnidad = (Integer) sqlSession.selectOne("efa.codigoUnidadPorZA", codZA);
			if(codUnidad!=null && codUnidad.intValue()>0){

				resultado = codUnidad.intValue();

			}
		}
		catch (Exception e)
		{
			logger.error(this.logBase+ " : buscarCodigoUnidadOrganicaPorZA" + e.getMessage());
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return resultado;
	}

}
