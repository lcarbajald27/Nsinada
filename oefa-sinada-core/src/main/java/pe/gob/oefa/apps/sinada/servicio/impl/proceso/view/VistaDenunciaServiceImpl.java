package pe.gob.oefa.apps.sinada.servicio.impl.proceso.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.sinada.bean.proceso.view.VistaDenuncia;
import pe.gob.oefa.apps.sinada.persistencia.inf.proceso.view.VistaDenunciaDAO;
import pe.gob.oefa.apps.sinada.servicio.inf.proceso.view.VistaDenunciaService;

@Service("vistaDenunciaService")
@Transactional(readOnly = true)
public class VistaDenunciaServiceImpl implements VistaDenunciaService {

	@Autowired
	private VistaDenunciaDAO vistaDenunciaDAO;
	
	@Override
	public Long insertar(VistaDenuncia prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int actualizar(VistaDenuncia prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int eliminar(VistaDenuncia prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public VistaDenuncia buscarPorId(Long prmIdBean) throws ServicioException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> listar(VistaDenuncia prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VistaDenuncia buscarFichaDenunciaPorId(VistaDenuncia prmData)
			throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return vistaDenunciaDAO.buscarFichaDenunciaPorId(prmData);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

	@Override
	public VistaDenuncia generarVisualizacionPreliminarFichaDenuncia(
			VistaDenuncia prmData) throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return vistaDenunciaDAO.generarVisualizacionPreliminarFichaDenuncia(prmData);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

}
