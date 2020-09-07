package pe.gob.oefa.apps.sinada.persistencia.impl.maestros;

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
import pe.gob.oefa.apps.sinada.bean.maestros.CentroPoblado;
import pe.gob.oefa.apps.sinada.bean.maestros.Departamento;
import pe.gob.oefa.apps.sinada.bean.maestros.Distrito;
import pe.gob.oefa.apps.sinada.bean.maestros.Provincia;
import pe.gob.oefa.apps.sinada.bean.maestros.Ubigeo;
import pe.gob.oefa.apps.sinada.persistencia.inf.maestros.UbigeoDAO;

@Transactional
@Repository(value="ubigeoDAO")
public class UbigeoDAOImpl implements UbigeoDAO {

	@Autowired
	@Qualifier("sinadaSqlSessionFactory")
	SqlSessionFactory sqlSessionFactory;

	private String logBase = "oefa-sinada-core:UbigeoDAOImpl";
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public Long insertar(Ubigeo prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int actualizar(Ubigeo prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int eliminar(Ubigeo prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Ubigeo buscarPorId(Long prmIdBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> listar(Ubigeo prmBean) throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ubigeo> listarDepartamento() throws PersistenciaException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;	
//		List<Ubigeo> lst = new ArrayList<Ubigeo>();
		List<Ubigeo> lst = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("ubigeo.listarDepartamento");
			//Valir si exiten registro en el listado de Ubigeo
//			if (lstUbigeo != null && lstUbigeo.size() > 0) {
//				//Castiar datos a lst de Departamento
//				for (Ubigeo ubigeo : lstUbigeo) {
//					Departamento departamento = new Departamento();
//					departamento.setCodDep(ubigeo.getCodigoDepartamento());
//					departamento.setCodigoDepartamento(ubigeo.getCodigoDepartamento());
//					departamento.setDescripcionDepartamento(ubigeo.getNombreUbigeo());
//					lst.add(departamento);
//				}
//			}
		}
		catch (Exception e) {
			logger.error(this.logBase+ " : listarDepartamento" + e.getMessage());
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return lst;
	}

	@Override
	public List<Ubigeo> listarProvincia(Ubigeo prmData)
			throws PersistenciaException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;	
//		List<Ubigeo> lst = new ArrayList<Ubigeo>();
		List<Ubigeo> lst = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("ubigeo.listarProvincia",prmData);
			//Valir si exiten registro en el listado de Ubigeo
//			if (lstUbigeo != null && lstUbigeo.size() > 0) {
//				//Castiar datos a lst de Departamento
//				for (Ubigeo ubigeo : lstUbigeo) {
//					Provincia provincia = new Provincia();
//					provincia.setCodDep(ubigeo.getCodigoDepartamento());
//					provincia.setCodigoDepartamento(ubigeo.getCodigoDepartamento());
//					provincia.setCodProv(ubigeo.getCodigoProvincia());
//					provincia.setCodigoProvincia(ubigeo.getCodigoProvincia());
//					provincia.setDescripcion(ubigeo.getNombreUbigeo());
//					lst.add(provincia);
//				}
//			}
		}
		catch (Exception e) {
			logger.error(this.logBase+ " : listarProvincia" + e.getMessage());
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return lst;
	}

	@Override
	public List<Ubigeo> listarDistrito(Ubigeo prmData)
			throws PersistenciaException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;	
//		List<Ubigeo> lst = new ArrayList<Ubigeo>();
		List<Ubigeo> lst = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("ubigeo.listarDistrito",prmData);
			//Valir si exiten registro en el listado de Ubigeo

		}
		catch (Exception e) {
			logger.error(this.logBase+ " : listarDistrito" + e.getMessage());
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return lst;
	}

	@Override
	public List<CentroPoblado> listarCentroPoblado(CentroPoblado prmData)
			throws PersistenciaException {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;	

		List<CentroPoblado> lst = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			lst = sqlSession.selectList("centroPoblado.listarCentroPoblado",prmData);
			//Valir si exiten registro en el listado de Ubigeo

		}
		catch (Exception e) {
			logger.error(this.logBase+ " : listarCentroPoblado" + e.getMessage());
			throw new PersistenceException(e);
		}
		finally
		{
			sqlSession.close();
		}
		return lst;
	}
	
	

}
