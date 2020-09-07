package pe.gob.oefa.apps.sinada.bean.general;

import java.io.Serializable;
import java.sql.Date;

import pe.gob.oefa.apps.base.bean.BaseBean;

public class ContactoPersona extends BaseBean{
	
	private long 	idContactoPersona;
	private int 	tipoContacto;
	private String 	nombreTipoContacto;
	private String 	valor;
	private int 	tipoPersona;
	private long 	idPersona;
	private int 	estado;
	private String 	flagActivo;
	
	public ContactoPersona() {
		super();
	}

	public long getIdContactoPersona() {
		return idContactoPersona;
	}

	public void setIdContactoPersona(long idContactoPersona) {
		this.idContactoPersona = idContactoPersona;
	}

	public int getTipoContacto() {
		return tipoContacto;
	}

	public void setTipoContacto(int tipoContacto) {
		this.tipoContacto = tipoContacto;
	}

	

	public int getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(int tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public long getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(long idPersona) {
		this.idPersona = idPersona;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
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

	public String getNombreTipoContacto() {
		return nombreTipoContacto;
	}

	public void setNombreTipoContacto(String nombreTipoContacto) {
		this.nombreTipoContacto = nombreTipoContacto;
	}



	
}
