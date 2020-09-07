package pe.gob.oefa.apps.sinada.bean.proceso;

import pe.gob.oefa.apps.base.bean.BaseBean;
import pe.gob.oefa.apps.sinada.bean.maestros.Maestro;

public class Encuesta extends BaseBean {

	private Long 	idEncuesta;
	private long 	idDenuncia;
	private String codigoDenuncia;
	private Maestro pregunta1;
	private Maestro pregunta2;
	private Maestro pregunta3;
	private int 	estado;
	private Maestro tipoEncuesta;
	private BandejaDetalle bandejaDetalle;
	private String fechaRegistro;
	
	private String fechaInicio;
	private String fechaFin;

	
	
	public Long getIdEncuesta() {
		return idEncuesta;
	}
	public void setIdEncuesta(Long idEncuesta) {
		this.idEncuesta = idEncuesta;
	}
	public long getIdDenuncia() {
		return idDenuncia;
	}
	public void setIdDenuncia(long idDenuncia) {
		this.idDenuncia = idDenuncia;
	}
	public Maestro getPregunta1() {
		return pregunta1;
	}
	public void setPregunta1(Maestro pregunta1) {
		this.pregunta1 = pregunta1;
	}
	public Maestro getPregunta2() {
		return pregunta2;
	}
	public void setPregunta2(Maestro pregunta2) {
		this.pregunta2 = pregunta2;
	}
	public Maestro getPregunta3() {
		return pregunta3;
	}
	public void setPregunta3(Maestro pregunta3) {
		this.pregunta3 = pregunta3;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public Maestro getTipoEncuesta() {
		return tipoEncuesta;
	}
	public void setTipoEncuesta(Maestro tipoEncuesta) {
		this.tipoEncuesta = tipoEncuesta;
	}

	public BandejaDetalle getBandejaDetalle() {
		return bandejaDetalle;
	}
	public void setBandejaDetalle(BandejaDetalle bandejaDetalle) {
		this.bandejaDetalle = bandejaDetalle;
	}
	public String getCodigoDenuncia() {
		return codigoDenuncia;
	}
	public void setCodigoDenuncia(String codigoDenuncia) {
		this.codigoDenuncia = codigoDenuncia;
	}
	public String getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public String getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public String getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	
	
}
