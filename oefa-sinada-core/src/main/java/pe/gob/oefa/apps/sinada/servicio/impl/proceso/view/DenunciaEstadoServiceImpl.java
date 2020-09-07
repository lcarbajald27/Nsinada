package pe.gob.oefa.apps.sinada.servicio.impl.proceso.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.sinada.bean.proceso.view.DenunciaEstado;
import pe.gob.oefa.apps.sinada.persistencia.inf.proceso.view.DenunciaEstadoDAO;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.view.DenunciaEstadoService;

@Service("denunciaEstadoService")
@Transactional(readOnly = true)
public class DenunciaEstadoServiceImpl implements DenunciaEstadoService {

	@Autowired
	private DenunciaEstadoDAO denunciaEstadoDAO;
	
	@Override
	public Long insertar(DenunciaEstado prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int actualizar(DenunciaEstado prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int eliminar(DenunciaEstado prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public DenunciaEstado buscarPorId(Long prmIdBean) throws ServicioException {
		// TODO Auto-generated method stub
		try{
			return denunciaEstadoDAO.buscarPorId(prmIdBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public List<?> listar(DenunciaEstado prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return null;
	}

}
