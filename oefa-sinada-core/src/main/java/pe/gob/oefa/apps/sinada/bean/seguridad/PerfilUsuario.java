package pe.gob.oefa.apps.sinada.bean.seguridad;

import pe.gob.oefa.apps.base.bean.BaseBean;
import pe.gob.oefa.apps.sinada.bean.maestros.Maestro;

public class PerfilUsuario extends BaseBean{
	
	private long 	idPerfilUsuario;
	private Perfil 	perfil;
	private Usuario	usuario;
	private Maestro estado;
	
	
	public long getIdPerfilUsuario() {
		return idPerfilUsuario;
	}
	public void setIdPerfilUsuario(long idPerfilUsuario) {
		this.idPerfilUsuario = idPerfilUsuario;
	}
	public Perfil getPerfil() {
		return perfil;
	}
	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Maestro getEstado() {
		return estado;
	}
	public void setEstado(Maestro estado) {
		this.estado = estado;
	}
	
	

}
