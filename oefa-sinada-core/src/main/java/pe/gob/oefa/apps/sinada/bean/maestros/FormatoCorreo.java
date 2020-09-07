package pe.gob.oefa.apps.sinada.bean.maestros;

import pe.gob.oefa.apps.base.bean.BaseBean;

public class FormatoCorreo extends BaseBean{

	private long idFormatoCorreo;
	private String mensaje;
	public long getIdFormatoCorreo() {
		return idFormatoCorreo;
	}
	public void setIdFormatoCorreo(long idFormatoCorreo) {
		this.idFormatoCorreo = idFormatoCorreo;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	
}
