package pe.gob.oefa.apps.sinada.bean.sirefa;

import java.io.Serializable;
import java.sql.Date;

import pe.gob.oefa.apps.base.bean.BaseBean;

public class Contacto extends BaseBean{
	
	private long 	idContacto;
	private long 	idEfa;
	private int 	tipoContacto;
	private String 	documento;
	private String 	titular;
	private int 	tipoCargo;
	private int 	tipoPeriodo;
	private Date 	fechaRegistro;
	private int 	flagActivo;
	
	public Contacto() {
		super();
	}
	
	public long getIdContacto() {
		return idContacto;
	}

	public long getIdEfa() {
		return idEfa;
	}

	public void setIdEfa(long idEfa) {
		this.idEfa = idEfa;
	}

	public int getTipoContacto() {
		return tipoContacto;
	}

	public void setTipoContacto(int tipoContacto) {
		this.tipoContacto = tipoContacto;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	public int getTipoCargo() {
		return tipoCargo;
	}

	public void setTipoCargo(int tipoCargo) {
		this.tipoCargo = tipoCargo;
	}

	public int getTipoPeriodo() {
		return tipoPeriodo;
	}

	public void setTipoPeriodo(int tipoPeriodo) {
		this.tipoPeriodo = tipoPeriodo;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public int getFlagActivo() {
		return flagActivo;
	}

	public void setFlagActivo(int flagActivo) {
		this.flagActivo = flagActivo;
	}

	public void setIdContacto(long idContacto) {
		this.idContacto = idContacto;
	}

	@Override
	public String toString() {
		return "Contacto [idContacto=" + idContacto + ", idEfa=" + idEfa + ", tipoContacto=" + tipoContacto
				+ ", documento=" + documento + ", titular=" + titular + ", tipoCargo=" + tipoCargo + ", tipoPeriodo="
				+ tipoPeriodo + ", fechaRegistro=" + fechaRegistro + ", flagActivo=" + flagActivo + "]";
	}
	
}
