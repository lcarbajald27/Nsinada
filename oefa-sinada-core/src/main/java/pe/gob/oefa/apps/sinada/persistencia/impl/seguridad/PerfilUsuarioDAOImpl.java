package pe.gob.oefa.apps.sinada.persistencia.impl.seguridad;

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
import pe.gob.oefa.apps.sinada.bean.seguridad.Perfil;
import pe.gob.oefa.apps.sinada.bean.seguridad.PerfilUsuario;
import pe.gob.oefa.apps.sinada.persistencia.inf.seguridad.PerfilUsuarioDAO;

@Transactional
@Repository(value="perfilUsuarioDAO")
public class PerfilUsuarioDAOImpl implements PerfilUsuarioDAO {

	@Autowired
	@Qualifier("sinadaSqlSessionFactory")
	SqlSessionFactory sqlSessionFactory;
	
	private String logBase = "oefa-sinada-core:PerfilUsuarioDAOImpl";
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public Long insertar(PerfilUsuario prmBean) throws PersistenciaException {
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession(false);
			int filasAfectadas = sqlSession.insert("perfilUsuario.agregar", prmBean);
			if (filasAfectadas>0 && prmBean.getIdPerfilUsuario()>0)
			{
				return prmBean.getIdPerfilUsuario();
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
	public int actualizar(PerfilUsuario prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int eliminar(PerfilUsuario prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			int filasAfectadas = sqlSession.update("perfilUsuario.eliminar", prmBean.getIdPerfilUsuario());
			
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
	public PerfilUsuario buscarPorId(Long prmIdBean)
			throws PersistenciaException {
		SqlSession sqlSession = null;	
		List<PerfilUsuario> lst = null;
		PerfilUsuario oBean = new PerfilUsuario();
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("perfilUsuario.buscarPorId", prmIdBean);
			if(lst!=null && lst.size()>0){
				oBean=lst.get(0);
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
	public List<?> listar(PerfilUsuario prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;	
		List<PerfilUsuario> lst = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("perfilUsuario.listar",prmBean);

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
	public PerfilUsuario validarUsuarioPorPerfil(PerfilUsuario prmBean)
			throws PersistenciaException {
		SqlSession sqlSession = null;	
		List<PerfilUsuario> lst = null;
		PerfilUsuario oBean = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("perfilUsuario.validarUsuarioPorPerfil", prmBean);
			if(lst!=null && lst.size()>0){
				oBean=lst.get(0);
			}
			
		}
		catch (Exception e) {
			logger.error(this.logBase+ " : validarUsuarioPorPerfil" + e.getMessage());
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return oBean;
	}

}
