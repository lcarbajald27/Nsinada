package pe.gob.oefa.apps.sinada.servicio.impl.general;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.sinada.bean.general.Auditoria;
import pe.gob.oefa.apps.sinada.persistencia.inf.general.AuditoriaDAO;
import pe.gob.oefa.apps.sinada.servicio.inf.general.AuditoriaService;


@Service("auditoriaService")
@Transactional(readOnly = true)
public class AuditoriaServiceImpl implements AuditoriaService {

	@Autowired
	private AuditoriaDAO auditoriaDAO;
	
	@Override
	public Long insertar(Auditoria prmBean) throws ServicioException {
		try{
			return auditoriaDAO.insertar(prmBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public int actualizar(Auditoria prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int eliminar(Auditoria prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Auditoria buscarPorId(Long prmIdBean) throws ServicioException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> listar(Auditoria prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return null;
	}

}
