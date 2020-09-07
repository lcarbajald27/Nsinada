package pe.gob.oefa.apps.sinada.bean.seguridad;

import pe.gob.oefa.apps.base.bean.BaseBean;

public class SessionSistema extends BaseBean {

	private long idSession;
	private String ref;
	private int estado;
	
	public long getIdSession() {
		return idSession;
	}
	public void setIdSession(long idSession) {
		this.idSession = idSession;
	}
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	
	
	
}
