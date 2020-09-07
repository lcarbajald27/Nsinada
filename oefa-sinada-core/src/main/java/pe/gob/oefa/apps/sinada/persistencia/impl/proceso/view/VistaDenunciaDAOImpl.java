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
import pe.gob.oefa.apps.sinada.bean.proceso.view.AccionesRealizadas;
import pe.gob.oefa.apps.sinada.bean.proceso.view.VistaDenuncia;
import pe.gob.oefa.apps.sinada.persistencia.inf.proceso.view.VistaDenunciaDAO;

@Transactional
@Repository(value="vistaDenunciaDAO")
public class VistaDenunciaDAOImpl implements VistaDenunciaDAO {

	
	@Autowired
	@Qualifier("sinadaSqlSessionFactory")
	SqlSessionFactory sqlSessionFactory;
	
	
	private String logBase = "oefa-sinada-core:VistaDenunciaDAOImpl";
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	
	@Override
	public Long insertar(VistaDenuncia prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int actualizar(VistaDenuncia prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int eliminar(VistaDenuncia prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public VistaDenuncia buscarPorId(Long prmIdBean)
			throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> listar(VistaDenuncia prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VistaDenuncia buscarFichaDenunciaPorId(VistaDenuncia prmData)
			throws PersistenceException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;	
		List<VistaDenuncia> lst = null;
		VistaDenuncia bean = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("vistaDenuncia.buscarFichaDenunciaPorId",prmData);
			if(lst!=null && lst.size()>0){
				bean = lst.get(0);
			}
		}
		catch (Exception e) {
			logger.error(this.logBase+ " : buscarFichaDenunciaPorId" + e.getMessage());
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return bean;
	}

	@Override
	public VistaDenuncia generarVisualizacionPreliminarFichaDenuncia(
			VistaDenuncia prmData) throws PersistenceException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;	
		List<VistaDenuncia> lst = null;
		VistaDenuncia bean = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("vistaDenuncia.generarVisualizacionPreliminarFichaDenuncia",prmData);
			if(lst!=null && lst.size()>0){
				bean = lst.get(0);
			}
		}
		catch (Exception e) {
			logger.error(this.logBase+ " : generarVisualizacionPreliminarFichaDenuncia" + e.getMessage());
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return bean;
	}

}
