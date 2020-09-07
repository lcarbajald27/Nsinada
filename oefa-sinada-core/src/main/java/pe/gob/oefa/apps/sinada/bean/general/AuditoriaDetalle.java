package pe.gob.oefa.apps.sinada.bean.general;

import pe.gob.oefa.apps.base.bean.BaseBean;

public class AuditoriaDetalle extends BaseBean{

	private long idAuditoriaDetalle;
	private long idAuditoria;
	private long idCampo;
	private String valor;
	public long getIdAuditoriaDetalle() {
		return idAuditoriaDetalle;
	}
	public void setIdAuditoriaDetalle(long idAuditoriaDetalle) {
		this.idAuditoriaDetalle = idAuditoriaDetalle;
	}
	public long getIdAuditoria() {
		return idAuditoria;
	}
	public void setIdAuditoria(long idAuditoria) {
		this.idAuditoria = idAuditoria;
	}
	public long getIdCampo() {
		return idCampo;
	}
	public void setIdCampo(long idCampo) {
		this.idCampo = idCampo;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	
	
	
}
