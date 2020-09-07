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
import pe.gob.oefa.apps.sinada.bean.proceso.PersonaOefa;
import pe.gob.oefa.apps.sinada.bean.seguridad.Perfil;
import pe.gob.oefa.apps.sinada.bean.seguridad.Usuario;
import pe.gob.oefa.apps.sinada.persistencia.inf.seguridad.UsuarioDAO;


@Transactional
@Repository(value="usuarioDAO")
public class UsuarioDAOImpl implements UsuarioDAO {

	@Autowired
	@Qualifier("sinadaSqlSessionFactory")
	SqlSessionFactory sqlSessionFactory;
	
	private String logBase = "oefa-sinada-core:UsuarioDAOImpl";
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public Long insertar(Usuario prmBean) throws PersistenciaException {
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession(false);
			int filasAfectadas = sqlSession.insert("usuario.agregar", prmBean);
			if (filasAfectadas>0 && prmBean.getIdUsuario()>0)
			{
				return prmBean.getIdUsuario();
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
	public int actualizar(Usuario prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			int filasAfectadas = sqlSession.update("usuario.modificar", prmBean);
		
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
	public int eliminar(Usuario prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			int filasAfectadas = sqlSession.update("usuario.eliminar", prmBean.getIdUsuario());
			
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
	public Usuario buscarPorId(Long prmIdBean) throws PersistenciaException {
		SqlSession sqlSession = null;	
		List<Usuario> lst = null;
		Usuario oUsuario =null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("usuario.buscarPorId", prmIdBean);
			if(lst!=null && lst.size()>0){
				oUsuario=lst.get(0);
			}
			
		}
		catch (Exception e) {
			logger.error(this.logBase+ " : eliminar" + e.getMessage());
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return oUsuario;
	}

	@Override
	public List<?> listar(Usuario prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;	
		List<Usuario> lst = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("usuario.listar",prmBean);

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
	public Usuario loginUsuario(Usuario prmUsuario)
			throws PersistenciaException {
		SqlSession sqlSession = null;	
		List<Usuario> lst = null;
		Usuario oUsuario = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("usuario.loginUsuario", prmUsuario);
			if(lst!=null && lst.size()>0){
				oUsuario=lst.get(0);
			}
			
		}
		catch (Exception e) {
			logger.error(this.logBase+ " : loginUsuario" + e.getMessage());
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return oUsuario;
	}

	@Override
	public boolean cambiarClave(Usuario prmUsuario)
			throws PersistenciaException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;
		try
		{
			boolean result = false;
			sqlSession = sqlSessionFactory.openSession();
			int filasAfectadas = sqlSession.update("usuario.cambioClave", prmUsuario);
		
			if(filasAfectadas>0){
				result=true;
			}
			return result;
		}
		catch (Exception e)
		{
			logger.error(this.logBase+ " : cambiarClave" + e.getMessage());
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
	public List<Usuario> listarUsuariosSinada(Usuario prmBean)
			throws PersistenciaException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;	
		List<Usuario> lst = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("usuario.listarUsuariosSinada",prmBean);

		}
		catch (Exception e) {
			logger.error(this.logBase+ " : listarUsuariosSinada" + e.getMessage());
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return lst;
	}

}
