package pe.gob.oefa.apps.sinada.servicio.impl.seguridad;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.sinada.bean.proceso.PersonaOefa;
import pe.gob.oefa.apps.sinada.bean.seguridad.Usuario;
import pe.gob.oefa.apps.sinada.persistencia.inf.seguridad.UsuarioDAO;
import pe.gob.oefa.apps.sinada.servicio.inf.seguridad.UsuarioService;

@Service("usuarioService")
@Transactional(readOnly = true)
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioDAO usuarioDAO;
	
	@Override
	public Long insertar(Usuario prmBean) throws ServicioException {
		try{
			return usuarioDAO.insertar(prmBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public int actualizar(Usuario prmBean) throws ServicioException {
		try{
			return usuarioDAO.actualizar(prmBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public int eliminar(Usuario prmBean) throws ServicioException {
		try{
			return usuarioDAO.eliminar(prmBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public Usuario buscarPorId(Long prmIdBean) throws ServicioException {
		List<Usuario> lst=null;
		Usuario oUsuario= new Usuario();
		try 
		{ 
			oUsuario = usuarioDAO.buscarPorId(prmIdBean);
//			if(lst!=null && ){
//				oUsuario=lst.get(0);
//			}
		
		} 
		catch (PersistenciaException e) 
		{
			 
		}  
		return oUsuario;
	}

	@Override
	public List<?> listar(Usuario prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return usuarioDAO.listar(prmBean);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

	@Override
	public Usuario loginUsuario(Usuario prmUsuario) throws ServicioException {
		List<Usuario> lst=null;
		Usuario oUsuario= new Usuario();
		try 
		{ 
			oUsuario =  usuarioDAO.loginUsuario(prmUsuario);
		
		
		} 
		catch (PersistenciaException e) 
		{
			 
		}  
		return oUsuario;
	}

	@Override
	public boolean cambiarClave(Usuario prmUsuario) throws ServicioException {
		try{
			return usuarioDAO.cambiarClave(prmUsuario);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public List<Usuario> listarUsuariosSinada(Usuario prmBean)
			throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return usuarioDAO.listarUsuariosSinada(prmBean);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

}
