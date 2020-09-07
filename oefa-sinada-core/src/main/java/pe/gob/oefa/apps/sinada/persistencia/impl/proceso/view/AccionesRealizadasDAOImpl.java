package pe.gob.oefa.apps.sinada.persistencia.impl.proceso.view;

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
import pe.gob.oefa.apps.sinada.bean.proceso.ArchivoAtencion;
import pe.gob.oefa.apps.sinada.bean.proceso.view.AccionesRealizadas;
import pe.gob.oefa.apps.sinada.persistencia.inf.proceso.view.AccionesRealizadasDAO;

@Transactional
@Repository(value="accionesRealizadasDAO")
public class AccionesRealizadasDAOImpl implements AccionesRealizadasDAO {

	@Autowired
	@Qualifier("sinadaSqlSessionFactory")
	SqlSessionFactory sqlSessionFactory;
	
	private String logBase = "oefa-sinada-core:AccionesRealizadasDAOImpl";
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public Long insertar(AccionesRealizadas prmBean)
			throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int actualizar(AccionesRealizadas prmBean)
			throws PersistenciaException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int eliminar(AccionesRealizadas prmBean)
			throws PersistenciaException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public AccionesRealizadas buscarPorId(Long prmIdBean)
			throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> listar(AccionesRealizadas prmBean)
			throws PersistenciaException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;	
		List<AccionesRealizadas> lst = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("accionesRealizadas.listar",prmBean);

		}
		catch (Exception e) {
			logger.error(this.logBase+ " : AccionesRealizadas" + e.getMessage());
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return lst;
	}

	@Override
	public List<AccionesRealizadas> listarAccionesPorIdBandeja(
			AccionesRealizadas prmData) throws PersistenceException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;	
		List<AccionesRealizadas> lst = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("accionesRealizadas.listarPorIdBandejaDetalle",prmData);

		}
		catch (Exception e) {
			logger.error(this.logBase+ " : listarAccionesPorIdBandeja" + e.getMessage());
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return lst;
	}

	@Override
	public AccionesRealizadas buscarPorTipoTablaIdAccion(
			AccionesRealizadas prmData) throws PersistenceException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;	
		List<AccionesRealizadas> lst = null;
		AccionesRealizadas bean = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("accionesRealizadas.buscarPorTipoTablaIdAccion",prmData);
			if(lst!=null && lst.size()>0){
				bean = lst.get(0);
			}
		}
		catch (Exception e) {
			logger.error(this.logBase+ " : buscarPorTipoTablaIdAccion" + e.getMessage());
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return bean;
	}

	@Override
	public List<AccionesRealizadas> listarAccionesRealizadasPorEstados(
			AccionesRealizadas prmData) throws PersistenceException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;	
		List<AccionesRealizadas> lst = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("accionesRealizadas.listarAccionesRealizadasPorEstados",prmData);

		}
		catch (Exception e) {
			logger.error(this.logBase+ " : listarAccionesRealizadasPorEstados" + e.getMessage());
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return lst;
	}

}
