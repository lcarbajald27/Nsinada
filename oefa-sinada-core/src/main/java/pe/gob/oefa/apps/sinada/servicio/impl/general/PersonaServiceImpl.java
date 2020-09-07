package pe.gob.oefa.apps.sinada.servicio.impl.general;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.sinada.bean.general.Persona;
import pe.gob.oefa.apps.sinada.bean.sirin.ContenidoNorma;
import pe.gob.oefa.apps.sinada.persistencia.inf.general.PersonaDAO;
import pe.gob.oefa.apps.sinada.servicio.inf.general.PersonaService;

@Service("personaService")
@Transactional(readOnly = true)
public class PersonaServiceImpl implements PersonaService {

	
	@Autowired
	private PersonaDAO personaDAO;
	
	@Override
	public Long insertar(Persona prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		try{
			return personaDAO.insertar(prmBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public int actualizar(Persona prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		try{
			return personaDAO.actualizar(prmBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public int eliminar(Persona prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Persona buscarPorId(Long prmIdBean) throws ServicioException {
		List<Persona> lst = null;
		Persona oPersona = new Persona();
		try {
			oPersona = personaDAO.buscarPorId(prmIdBean);

		} catch (PersistenciaException e) {

		}
		return oPersona;
	}

	@Override
	public List<?> listar(Persona prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Persona buscarPorNumeroDocumento(String prmIdBean)
			throws ServicioException {
		List<Persona> lst=null;
		Persona oPersona= new Persona();
		try 
		{ 
			oPersona =  personaDAO.buscarPorNumeroDocumento(prmIdBean);
		System.out.println("oPersona:::"+oPersona);
		
		} 
		catch (PersistenciaException e) 
		{
			 
		}  
		return oPersona;
	}

	@Override
	public List<Persona> buscarPorNombreCompletoSSO(String nombreCompleto)
			throws ServicioException {
		try {
			return personaDAO.buscarPorNombreCompletoSSO(nombreCompleto);
		} catch (PersistenciaException e) {
			throw new ServicioException(e);
		}
	}

}
