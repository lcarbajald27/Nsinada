package pe.gob.oefa.apps.sinada.servicio.impl.seguridad;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.sinada.bean.seguridad.Perfil;
import pe.gob.oefa.apps.sinada.persistencia.inf.seguridad.PerfilDAO;
import pe.gob.oefa.apps.sinada.servicio.inf.seguridad.PerfilService;


@Service("perfilService")
@Transactional(readOnly = true)
public class PerfilServiceImpl implements PerfilService {

	
	@Autowired
	private PerfilDAO perfilDAO;
	
	@Override
	public Long insertar(Perfil prmBean) throws ServicioException {
		try{
			return perfilDAO.insertar(prmBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public int actualizar(Perfil prmBean) throws ServicioException {
		try{
			return perfilDAO.actualizar(prmBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public int eliminar(Perfil prmBean) throws ServicioException {
		try{
			return perfilDAO.eliminar(prmBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public Perfil buscarPorId(Long prmIdBean) throws ServicioException {
		List<Perfil> lst=null;
		Perfil oPerfil= new Perfil();
		try 
		{ 
			lst = (List<Perfil>) perfilDAO.buscarPorId(prmIdBean);
			if(lst!=null){
				oPerfil=lst.get(0);
			}
		
		} 
		catch (PersistenciaException e) 
		{
			 
		}  
		return oPerfil;
	}

	@Override
	public List<?> listar(Perfil prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return perfilDAO.listar(prmBean);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

}
