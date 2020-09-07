package pe.gob.oefa.apps.sinada.bean.sirefa;

import java.io.Serializable;

import pe.gob.oefa.apps.base.bean.BaseBean;

public class DatosContacto extends BaseBean{
	
    private long 	idDatosContacto;
    private long 	idContacto;
    private int 	tipoDatoContacto;
    private String 	valor;
    private int 	flagPrincipal;
    private int 	flagActivo;
    
	public DatosContacto() {
		super();
	}
	public long getIdDatosContacto() {
		return idDatosContacto;
	}
	public void setIdDatosContacto(long idDatosContacto) {
		this.idDatosContacto = idDatosContacto;
	}
	public long getIdContacto() {
		return idContacto;
	}
	public void setIdContacto(long idContacto) {
		this.idContacto = idContacto;
	}
	public int getTipoDatoContacto() {
		return tipoDatoContacto;
	}
	public void setTipoDatoContacto(int tipoDatoContacto) {
		this.tipoDatoContacto = tipoDatoContacto;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public int getFlagPrincipal() {
		return flagPrincipal;
	}
	public void setFlagPrincipal(int flagPrincipal) {
		this.flagPrincipal = flagPrincipal;
	}
	public int getFlagActivo() {
		return flagActivo;
	}
	public void setFlagActivo(int flagActivo) {
		this.flagActivo = flagActivo;
	}
	@Override
	public String toString() {
		return "DatosContacto [idDatosContacto=" + idDatosContacto + ", idContacto=" + idContacto
				+ ", tipoDatoContacto=" + tipoDatoContacto + ", valor=" + valor + ", flagPrincipal=" + flagPrincipal
				+ ", flagActivo=" + flagActivo + "]";
	}
	
}
