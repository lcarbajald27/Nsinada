package pe.gob.oefa.apps.sinada.bean.seguridad;

import java.io.Serializable;
import java.util.Date;

import pe.gob.oefa.apps.base.bean.BaseBean;

public class Perfil extends BaseBean{
	
	private long 	idPerfil;
    private String 	nombrePerfil;
    private int 	estado;
    private String 	flagActivo;
    private Usuario 	usuario;

	public Perfil() {
		super();
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public String getFlagActivo() {
		return flagActivo;
	}

	public void setFlagActivo(String flagActivo) {
		this.flagActivo = flagActivo;
	}

	public long getIdPerfil() {
		return idPerfil;
	}

	public void setIdPerfil(long idPerfil) {
		this.idPerfil = idPerfil;
	}

	public String getNombrePerfil() {
		return nombrePerfil;
	}

	public void setNombrePerfil(String nombrePerfil) {
		this.nombrePerfil = nombrePerfil;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	
	
	
}
