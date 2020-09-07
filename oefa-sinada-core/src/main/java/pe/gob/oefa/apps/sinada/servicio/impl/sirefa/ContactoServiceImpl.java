package pe.gob.oefa.apps.sinada.servicio.impl.sirefa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.sinada.bean.sirefa.Contacto;
import pe.gob.oefa.apps.sinada.persistencia.inf.sirefa.ContactoDAO;
import pe.gob.oefa.apps.sinada.servicio.inf.sirefa.ContactoService;

@Service("contactoService")
@Transactional(readOnly = true)
public class ContactoServiceImpl implements ContactoService{

	@Autowired
	private ContactoDAO contactoDAO;
	
	@Override
	public int actualizar(Contacto prmContacto) throws ServicioException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Contacto buscarPorId(Long prmContacto) throws ServicioException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int eliminar(Contacto prmContacto) throws ServicioException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Long insertar(Contacto prmContacto) throws ServicioException {
		try{
			return contactoDAO.insertar(prmContacto);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public List<?> listar(Contacto prmContacto) throws ServicioException {
		// TODO Auto-generated method stub
		return null;
	}

}
