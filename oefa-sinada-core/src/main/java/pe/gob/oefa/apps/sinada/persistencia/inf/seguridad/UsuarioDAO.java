package pe.gob.oefa.apps.sinada.persistencia.inf.seguridad;

import java.util.List;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.persistencia.inf.BaseDAO;
import pe.gob.oefa.apps.sinada.bean.proceso.PersonaOefa;
import pe.gob.oefa.apps.sinada.bean.seguridad.Usuario;


public interface UsuarioDAO extends BaseDAO<Usuario, Long>{

	public Usuario loginUsuario (Usuario prmUsuario)throws PersistenciaException;
	
	public boolean cambiarClave (Usuario prmUsuario)throws PersistenciaException;
	
	public List<Usuario> listarUsuariosSinada(Usuario prmBean)throws PersistenciaException;
	
}
