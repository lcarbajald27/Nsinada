package pe.gob.oefa.apps.base.webservice.rest.sso.bean;

import java.util.Date;

public class SSOObjectREST {
	
	private String codigo;
	private String descripcion;
	private Date fechaHora;
	private UsuarioSSO data;
	
	public SSOObjectREST() {
		this.setData(new UsuarioSSO());
		this.setFechaHora(new Date());
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public UsuarioSSO getData() {
		return data;
	}

	public void setData(UsuarioSSO data) {
		this.data = data;
	}

	public Date getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}

	@Override
	public String toString() {
		return "SSOObjectREST [codigo=" + codigo + ", descripcion="
				+ descripcion + ", fechaHora=" + fechaHora + ", data=" + data.toString()
				+ "]";
	}
	
	

}
