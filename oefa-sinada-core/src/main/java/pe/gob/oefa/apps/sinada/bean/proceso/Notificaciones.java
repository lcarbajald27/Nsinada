package pe.gob.oefa.apps.sinada.bean.proceso;

import pe.gob.oefa.apps.base.bean.BaseBean;

public class Notificaciones extends BaseBean{
	
	private Long idNotificacion;
	private long idBandeja;
	private long idBandejaEntidad;
	private int tipoNotificacion;
	private String descripcionNotificacion;
	private String fechaRegistro;
	private int estado;
	private String flagActivo;


	public long getIdBandeja() {
		return idBandeja;
	}
	public void setIdBandeja(long idBandeja) {
		this.idBandeja = idBandeja;
	}
	public int getTipoNotificacion() {
		return tipoNotificacion;
	}
	public void setTipoNotificacion(int tipoNotificacion) {
		this.tipoNotificacion = tipoNotificacion;
	}
	public String getDescripcionNotificacion() {
		return descripcionNotificacion;
	}
	public void setDescripcionNotificacion(String descripcionNotificacion) {
		this.descripcionNotificacion = descripcionNotificacion;
	}
	public String getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
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
	public Long getIdNotificacion() {
		return idNotificacion;
	}
	public void setIdNotificacion(Long idNotificacion) {
		this.idNotificacion = idNotificacion;
	}
	public long getIdBandejaEntidad() {
		return idBandejaEntidad;
	}
	public void setIdBandejaEntidad(long idBandejaEntidad) {
		this.idBandejaEntidad = idBandejaEntidad;
	}

	
}
