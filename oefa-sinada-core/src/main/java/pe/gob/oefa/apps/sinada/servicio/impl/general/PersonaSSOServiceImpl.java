package pe.gob.oefa.apps.sinada.servicio.impl.general;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.sinada.bean.general.PersonaSSO;
import pe.gob.oefa.apps.sinada.persistencia.inf.general.PersonaDAO;
import pe.gob.oefa.apps.sinada.persistencia.inf.general.PersonaSSODAO;
import pe.gob.oefa.apps.sinada.servicio.inf.general.PersonaSSOService;

@Service("personaSSOService")
@Transactional(readOnly = true)
public class PersonaSSOServiceImpl implements PersonaSSOService {

	@Autowired
	private PersonaSSODAO personaSSODAO;
	
	@Override
	public Long insertar(PersonaSSO prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int actualizar(PersonaSSO prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int eliminar(PersonaSSO prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PersonaSSO buscarPorId(Long prmIdBean) throws ServicioException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> listar(PersonaSSO prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PersonaSSO buscarPorNumeroDocumento(PersonaSSO prmData)
			throws ServicioException {
		// TODO Auto-generated method stub
		try{
			return personaSSODAO.buscarPorNumeroDocumento(prmData);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public List<PersonaSSO> listaPersonaSSO(PersonaSSO prmData)
			throws ServicioException {
		// TODO Auto-generated method stub
		try{
			return personaSSODAO.listaPersonaSSO(prmData);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

}
