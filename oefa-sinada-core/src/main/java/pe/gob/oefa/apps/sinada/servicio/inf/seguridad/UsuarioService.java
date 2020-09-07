package pe.gob.oefa.apps.sinada.servicio.inf.seguridad;

import java.util.List;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.persistencia.inf.BaseDAO;
import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.base.servicio.inf.BaseService;
import pe.gob.oefa.apps.sinada.bean.proceso.PersonaOefa;
import pe.gob.oefa.apps.sinada.bean.seguridad.Perfil;
import pe.gob.oefa.apps.sinada.bean.seguridad.Usuario;
import pe.gob.oefa.apps.sinada.bean.sirefa.Efa;
import pe.gob.oefa.apps.sinada.bean.sirin.Normas;

public interface UsuarioService extends BaseService<Usuario, Long>{

	public Usuario loginUsuario (Usuario prmUsuario) throws ServicioException;
	
	public boolean cambiarClave (Usuario prmUsuario)throws ServicioException;
	
	public List<Usuario> listarUsuariosSinada(Usuario prmBean)throws ServicioException;
	
}
