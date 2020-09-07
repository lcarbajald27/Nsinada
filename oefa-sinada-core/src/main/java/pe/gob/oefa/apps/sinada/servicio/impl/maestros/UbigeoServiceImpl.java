package pe.gob.oefa.apps.sinada.servicio.impl.maestros;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.sinada.bean.maestros.CentroPoblado;
import pe.gob.oefa.apps.sinada.bean.maestros.Departamento;
import pe.gob.oefa.apps.sinada.bean.maestros.Distrito;
import pe.gob.oefa.apps.sinada.bean.maestros.Provincia;
import pe.gob.oefa.apps.sinada.bean.maestros.Ubigeo;
import pe.gob.oefa.apps.sinada.persistencia.inf.maestros.UbigeoDAO;
import pe.gob.oefa.apps.sinada.servicio.inf.maestros.UbigeoService;

@Service("ubigeoService")
@Transactional(readOnly = true)
public class UbigeoServiceImpl implements UbigeoService{

	@Autowired
	private UbigeoDAO ubigeoDAO;

	@Override
	public Long insertar(Ubigeo prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int actualizar(Ubigeo prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int eliminar(Ubigeo prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Ubigeo buscarPorId(Long prmIdBean) throws ServicioException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> listar(Ubigeo prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ubigeo> listarDepartamento() throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return ubigeoDAO.listarDepartamento();
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

	@Override
	public List<Ubigeo> listarProvincia(Ubigeo prmData)
			throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return ubigeoDAO.listarProvincia(prmData);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

	@Override
	public List<Ubigeo> listarDistrito(Ubigeo prmData) throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return ubigeoDAO.listarDistrito(prmData);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

	@Override
	public List<CentroPoblado> listarCentroPoblado(CentroPoblado prmData)
			throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return ubigeoDAO.listarCentroPoblado(prmData);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}
	


}
