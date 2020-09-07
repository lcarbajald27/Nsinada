package pe.gob.oefa.apps.sinada.servicio.impl.proceso.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.sinada.bean.proceso.view.AccionesRealizadas;
import pe.gob.oefa.apps.sinada.persistencia.inf.proceso.view.AccionesRealizadasDAO;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.view.AccionesRealizadasService;

@Service("accionesRealizadasService")
@Transactional(readOnly = true)
public class AccionesRealizadasServiceImpl implements AccionesRealizadasService {

	@Autowired
	private AccionesRealizadasDAO accionesRealizadasDAO;
	
	@Override
	public Long insertar(AccionesRealizadas prmBean)
			throws ServicioException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int actualizar(AccionesRealizadas prmBean)
			throws ServicioException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int eliminar(AccionesRealizadas prmBean)
			throws ServicioException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public AccionesRealizadas buscarPorId(Long prmIdBean)
			throws ServicioException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> listar(AccionesRealizadas prmBean)
			throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return accionesRealizadasDAO.listar(prmBean);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

	@Override
	public List<AccionesRealizadas> listarAccionesPorIdBandeja(
			AccionesRealizadas prmData) throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return (List<AccionesRealizadas>) accionesRealizadasDAO.listarAccionesPorIdBandeja(prmData);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

	@Override
	public AccionesRealizadas buscarPorTipoTablaIdAccion(
			AccionesRealizadas prmData) throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return accionesRealizadasDAO.buscarPorTipoTablaIdAccion(prmData);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

	@Override
	public List<AccionesRealizadas> listarAccionesRealizadasPorEstados(
			AccionesRealizadas prmData) throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return accionesRealizadasDAO.listarAccionesRealizadasPorEstados(prmData);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

}
