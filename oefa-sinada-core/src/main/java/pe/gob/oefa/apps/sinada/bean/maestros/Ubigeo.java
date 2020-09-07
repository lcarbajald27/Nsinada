package pe.gob.oefa.apps.sinada.bean.maestros;

import java.io.Serializable;

import pe.gob.oefa.apps.base.bean.BaseBean;

public class Ubigeo extends BaseBean{

	private long 	idUbigeo;
	private String 	codigoDepartamento;
	private String 	descripcionDepartamento;
	
	private String 	codigoProvincia;
	private String 	descripcion;
	
	private String 	codigoDistrito;
	private String  descripcionDistrito;
	
	private int     estadoDep;
	private int 	estadoProv;
	private int 	estadoDist;

	
	public Ubigeo() {
		super();
	}

	public long getIdUbigeo() {
		return idUbigeo;
	}

	public void setIdUbigeo(long idUbigeo) {
		this.idUbigeo = idUbigeo;
	}

	public String getCodigoDepartamento() {
		return codigoDepartamento;
	}

	public void setCodigoDepartamento(String codigoDepartamento) {
		this.codigoDepartamento = codigoDepartamento;
	}

	public String getDescripcionDepartamento() {
		return descripcionDepartamento;
	}

	public void setDescripcionDepartamento(String descripcionDepartamento) {
		this.descripcionDepartamento = descripcionDepartamento;
	}

	public String getCodigoProvincia() {
		return codigoProvincia;
	}

	public void setCodigoProvincia(String codigoProvincia) {
		this.codigoProvincia = codigoProvincia;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCodigoDistrito() {
		return codigoDistrito;
	}

	public void setCodigoDistrito(String codigoDistrito) {
		this.codigoDistrito = codigoDistrito;
	}

	public String getDescripcionDistrito() {
		return descripcionDistrito;
	}

	public void setDescripcionDistrito(String descripcionDistrito) {
		this.descripcionDistrito = descripcionDistrito;
	}

	public int getEstadoDep() {
		return estadoDep;
	}

	public void setEstadoDep(int estadoDep) {
		this.estadoDep = estadoDep;
	}

	public int getEstadoProv() {
		return estadoProv;
	}

	public void setEstadoProv(int estadoProv) {
		this.estadoProv = estadoProv;
	}

	public int getEstadoDist() {
		return estadoDist;
	}

	public void setEstadoDist(int estadoDist) {
		this.estadoDist = estadoDist;
	}

	@Override
	public String toString() {
		return "Ubigeo [idUbigeo=" + idUbigeo + ", codigoDepartamento="
				+ codigoDepartamento + ", descripcionDepartamento="
				+ descripcionDepartamento + ", codigoProvincia="
				+ codigoProvincia + ", descripcion=" + descripcion
				+ ", codigoDistrito=" + codigoDistrito
				+ ", descripcionDistrito=" + descripcionDistrito
				+ ", estadoDep=" + estadoDep + ", estadoProv=" + estadoProv
				+ ", estadoDist=" + estadoDist + "]";
	}

	
	
}
