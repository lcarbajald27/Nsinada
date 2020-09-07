package pe.gob.oefa.apps.sinada.persistencia.impl.sirefa;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.sinada.bean.sirefa.DatosContacto;
import pe.gob.oefa.apps.sinada.persistencia.inf.sirefa.DatosContactoDAO;

@Transactional
@Repository(value="datosContactoDAO")
public class DatosContactoDAOImpl implements DatosContactoDAO{

	@Autowired
	@Qualifier("sinadaSqlSessionFactory")
	SqlSessionFactory sqlSessionFactory;
	
	private String logBase = "oefa-sinada-core:DatosContactoDAOImpl";
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public int actualizar(DatosContacto prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public DatosContacto buscarPorId(Long prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int eliminar(DatosContacto prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Long insertar(DatosContacto prmBean) throws PersistenciaException {
		SqlSession sqlSession = null;
		try
		{
//			System.out.println("public Long insertar(DatosContacto prmBean) throws PersistenciaException");
			sqlSession = sqlSessionFactory.openSession(false);
			int filasAfectadas = sqlSession.insert("datosContacto.agregar", prmBean);
			if (filasAfectadas>0 && prmBean.getIdDatosContacto()>0)
			{
				return prmBean.getIdDatosContacto();
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
	public List<?> listar(DatosContacto prmBean) throws PersistenciaException {
		SqlSession sqlSession = null;	
		List<DatosContacto> listaDatosContacto = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			listaDatosContacto = sqlSession.selectList("datosContacto.listar", prmBean);
			
		}
		catch (Exception e) {
			logger.error(this.logBase+ " : listar" + e.getMessage());
			throw new PersistenciaException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return listaDatosContacto;
	}

}
