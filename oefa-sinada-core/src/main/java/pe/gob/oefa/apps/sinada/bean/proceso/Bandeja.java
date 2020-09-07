package pe.gob.oefa.apps.sinada.bean.proceso;

import pe.gob.oefa.apps.base.bean.BaseBean;

public class Bandeja extends BaseBean {
	
	private long idBandeja;
	private int  tipoResponsable;
	private long idResponsable;
	private int  estado;
	private String flagActivo;
	
	private long idEFa;
	private int direccion;
	private int subDireccion;
	private int coordinacion;
	public long getIdBandeja() {
		return idBandeja;
	}
	public void setIdBandeja(long idBandeja) {
		this.idBandeja = idBandeja;
	}
	public int getTipoResponsable() {
		return tipoResponsable;
	}
	public void setTipoResponsable(int tipoResponsable) {
		this.tipoResponsable = tipoResponsable;
	}
	public long getIdResponsable() {
		return idResponsable;
	}
	public void setIdResponsable(long idResponsable) {
		this.idResponsable = idResponsable;
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
	public long getIdEFa() {
		return idEFa;
	}
	public void setIdEFa(long idEFa) {
		this.idEFa = idEFa;
	}
	public int getDireccion() {
		return direccion;
	}
	public void setDireccion(int direccion) {
		this.direccion = direccion;
	}
	public int getSubDireccion() {
		return subDireccion;
	}
	public void setSubDireccion(int subDireccion) {
		this.subDireccion = subDireccion;
	}
	public int getCoordinacion() {
		return coordinacion;
	}
	public void setCoordinacion(int coordinacion) {
		this.coordinacion = coordinacion;
	}
	


}
