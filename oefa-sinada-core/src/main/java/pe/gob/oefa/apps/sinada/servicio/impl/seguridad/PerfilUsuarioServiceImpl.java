package pe.gob.oefa.apps.sinada.servicio.impl.seguridad;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.sinada.bean.seguridad.Perfil;
import pe.gob.oefa.apps.sinada.bean.seguridad.PerfilUsuario;
import pe.gob.oefa.apps.sinada.persistencia.inf.seguridad.PerfilUsuarioDAO;
import pe.gob.oefa.apps.sinada.servicio.inf.seguridad.PerfilUsuarioService;

@Service("perfilUsuarioService")
@Transactional(readOnly = true)
public class PerfilUsuarioServiceImpl implements PerfilUsuarioService {

	@Autowired
	private PerfilUsuarioDAO perfilUsuarioDAO;
	
	@Override
	public Long insertar(PerfilUsuario prmBean) throws ServicioException {
		try{
			return perfilUsuarioDAO.insertar(prmBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public int actualizar(PerfilUsuario prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int eliminar(PerfilUsuario prmBean) throws ServicioException {
		try{
			return perfilUsuarioDAO.eliminar(prmBean);
		}catch (Exception e){
			throw new ServicioException(e);
		}
	}

	@Override
	public PerfilUsuario buscarPorId(Long prmIdBean) throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return perfilUsuarioDAO.buscarPorId(prmIdBean);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

	@Override
	public List<?> listar(PerfilUsuario prmBean) throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return perfilUsuarioDAO.listar(prmBean);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

	@Override
	public PerfilUsuario validarUsuarioPorPerfil(PerfilUsuario prmBean)
			throws ServicioException {
		// TODO Auto-generated method stub
		try {
			return perfilUsuarioDAO.validarUsuarioPorPerfil(prmBean);
		} catch (Exception e) {
			throw new ServicioException(e);
		}
	}

}
