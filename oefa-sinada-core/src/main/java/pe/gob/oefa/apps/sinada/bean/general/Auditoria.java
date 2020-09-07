package pe.gob.oefa.apps.sinada.bean.general;

import pe.gob.oefa.apps.base.bean.BaseBean;

public class Auditoria extends BaseBean{

	private long idAuditoria;
	private long idTabla;
	private String tipoAuditoria;
	private long idUsuario;
	private String fechaHora;
	private String hostIp;
	private long idSession;
	private long idRegistro;
	
	public long getIdAuditoria() {
		return idAuditoria;
	}
	public void setIdAuditoria(long idAuditoria) {
		this.idAuditoria = idAuditoria;
	}
	public long getIdTabla() {
		return idTabla;
	}
	public void setIdTabla(long idTabla) {
		this.idTabla = idTabla;
	}

	public String getTipoAuditoria() {
		return tipoAuditoria;
	}
	public void setTipoAuditoria(String tipoAuditoria) {
		this.tipoAuditoria = tipoAuditoria;
	}
	public long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getFechaHora() {
		return fechaHora;
	}
	public void setFechaHora(String fechaHora) {
		this.fechaHora = fechaHora;
	}
	public String getHostIp() {
		return hostIp;
	}
	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}
	public long getIdSession() {
		return idSession;
	}
	public void setIdSession(long idSession) {
		this.idSession = idSession;
	}
	public long getIdRegistro() {
		return idRegistro;
	}
	public void setIdRegistro(long idRegistro) {
		this.idRegistro = idRegistro;
	}
	
	
	
}
