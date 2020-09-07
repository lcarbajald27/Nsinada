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
import pe.gob.oefa.apps.sinada.bean.proceso.Denuncia;
import pe.gob.oefa.apps.sinada.bean.proceso.view.DenunciaEstado;
import pe.gob.oefa.apps.sinada.persistencia.inf.proceso.view.DenunciaEstadoDAO;

@Transactional
@Repository(value="denunciaEstadoDAO")
public class DenunciaEstadoDAOImpl implements DenunciaEstadoDAO {

	@Autowired
	@Qualifier("sinadaSqlSessionFactory")
	SqlSessionFactory sqlSessionFactory;
	
	private String logBase = "oefa-sinada-core:DenunciaEstadoDAOImpl";
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public Long insertar(DenunciaEstado prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int actualizar(DenunciaEstado prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int eliminar(DenunciaEstado prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public DenunciaEstado buscarPorId(Long prmIdBean)
			throws PersistenciaException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;	
		List<DenunciaEstado> lst = null;
		DenunciaEstado obj = new DenunciaEstado();
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("denunciaEstado.buscarPorId",prmIdBean);
			if(lst!=null && lst.size()>0){
				obj = lst.get(0);
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
		return obj;
	}

	@Override
	public List<?> listar(DenunciaEstado prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

}
