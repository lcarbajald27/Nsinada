package pe.gob.oefa.apps.sinada.servicio.impl.sirefa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.sinada.bean.sirefa.DatosContacto;
import pe.gob.oefa.apps.sinada.persistencia.inf.sirefa.ContactoDAO;
import pe.gob.oefa.apps.sinada.persistencia.inf.sirefa.DatosContactoDAO;
import pe.gob.oefa.apps.sinada.servicio.inf.sirefa.DatosContactoService;

@Service("datosContactoService")
@Transactional(readOnly = true)
public class DatosContactoServiceImpl implements DatosContactoService{

	@Autowired
	private DatosContactoDAO datosContactoDAO;
	
	@Override
	public int actualizar(DatosContacto prmDatosContacto) throws ServicioException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public DatosContacto buscarPorId(Long prmDatosContacto) throws ServicioException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int eliminar(DatosContacto prmDatosContacto) throws ServicioException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Long insertar(DatosContacto prmDatosContacto) throws ServicioException {
		try{
			return datosContactoDAO.insertar(prmDatosContacto);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public List<?> listar(DatosContacto prmDatosContacto) throws ServicioException {
		List<DatosContacto> lstDatosContacto = null;
		
			try {
				lstDatosContacto= (List<DatosContacto>) datosContactoDAO.listar(prmDatosContacto);
			} catch (PersistenciaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	
		return lstDatosContacto;
	}

}
