package pe.gob.oefa.apps.sinada.bean.proceso;

import java.io.Serializable;
import java.util.Date;

import pe.gob.oefa.apps.base.bean.BaseBean;

public class DescripcionCaso extends BaseBean{
	
	private long idDescripcionCaso;
	private String descripcionCaso;
	private String documento;
	private String direccion;
	private String nombreCompleto;
	private String telefono;
	private String correo;
	private int estado;
	private String flagActivo;
	
	private String mensajeHtml;
	

	public DescripcionCaso() {
		super();
	}


	public long getIdDescripcionCaso() {
		return idDescripcionCaso;
	}


	public void setIdDescripcionCaso(long idDescripcionCaso) {
		this.idDescripcionCaso = idDescripcionCaso;
	}


	public String getDescripcionCaso() {
		return descripcionCaso;
	}


	public void setDescripcionCaso(String descripcionCaso) {
		this.descripcionCaso = descripcionCaso;
	}


	public String getDocumento() {
		return documento;
	}


	public void setDocumento(String documento) {
		this.documento = documento;
	}


	public String getDireccion() {
		return direccion;
	}


	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}


	public String getNombreCompleto() {
		return nombreCompleto;
	}


	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}


	public String getTelefono() {
		return telefono;
	}


	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	public String getCorreo() {
		return correo;
	}


	public void setCorreo(String correo) {
		this.correo = correo;
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


	public String getMensajeHtml() {
		return mensajeHtml;
	}


	public void setMensajeHtml(String mensajeHtml) {
		this.mensajeHtml = mensajeHtml;
	}

	

	
}
