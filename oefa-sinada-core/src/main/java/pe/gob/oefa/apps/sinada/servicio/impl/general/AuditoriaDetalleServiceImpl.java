package pe.gob.oefa.apps.sinada.servicio.impl.general;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.sinada.bean.general.AuditoriaDetalle;
import pe.gob.oefa.apps.sinada.persistencia.inf.general.AuditoriaDetalleDAO;
import pe.gob.oefa.apps.sinada.servicio.inf.general.AuditoriaDetalleService;


@Service("auditoriaDetalleService")
@Transactional(readOnly = true)
public class AuditoriaDetalleServiceImpl implements AuditoriaDetalleService {

	@Autowired
	private AuditoriaDetalleDAO auditoriaDetalleDAO;
	
	@Override
	public Long insertar(AuditoriaDetalle prmBean) throws ServicioException {
		try{
			return auditoriaDetalleDAO.insertar(prmBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public int actualizar(AuditoriaDetalle prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int eliminar(AuditoriaDetalle prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public AuditoriaDetalle buscarPorId(Long prmIdBean)
			throws ServicioException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> listar(AuditoriaDetalle prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return null;
	}

}
