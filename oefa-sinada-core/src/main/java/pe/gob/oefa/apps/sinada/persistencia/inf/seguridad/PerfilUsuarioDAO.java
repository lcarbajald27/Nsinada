package pe.gob.oefa.apps.sinada.persistencia.inf.seguridad;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.persistencia.inf.BaseDAO;
import pe.gob.oefa.apps.sinada.bean.seguridad.Perfil;
import pe.gob.oefa.apps.sinada.bean.seguridad.PerfilUsuario;
import pe.gob.oefa.apps.sinada.bean.sirefa.Efa;
import pe.gob.oefa.apps.sinada.bean.sirin.Normas;

public interface PerfilUsuarioDAO extends BaseDAO<PerfilUsuario, Long>{

	public PerfilUsuario validarUsuarioPorPerfil(PerfilUsuario prmBean)throws PersistenciaException;
	
}
