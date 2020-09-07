package pe.gob.oefa.apps.sinada.bean.proceso;

import pe.gob.oefa.apps.base.bean.BaseBean;

public class BandejaDetalle extends BaseBean{

	private long idBandejaDetalle;
	private long idBandeja;
	private Bandeja bandeja;
	private int tipoBandeja;
	private long idDenuncia;
	private int tipoAsignacion;
	private int estado;
	private String flagActivo;
	private String fechaPlazoAtencion;
	private Integer idUnidadOrganica;

	
	private int dias;

	public long getIdBandeja() {
		return idBandeja;
	}

	public void setIdBandeja(long idBandeja) {
		this.idBandeja = idBandeja;
	}

	public long getIdBandejaDetalle() {
		return idBandejaDetalle;
	}

	public void setIdBandejaDetalle(long idBandejaDetalle) {
		this.idBandejaDetalle = idBandejaDetalle;
	}

	public int getTipoBandeja() {
		return tipoBandeja;
	}

	public void setTipoBandeja(int tipoBandeja) {
		this.tipoBandeja = tipoBandeja;
	}

	public long getIdDenuncia() {
		return idDenuncia;
	}

	public void setIdDenuncia(long idDenuncia) {
		this.idDenuncia = idDenuncia;
	}

	public int getTipoAsignacion() {
		return tipoAsignacion;
	}

	public void setTipoAsignacion(int tipoAsignacion) {
		this.tipoAsignacion = tipoAsignacion;
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

	public Bandeja getBandeja() {
		return bandeja;
	}

	public void setBandeja(Bandeja bandeja) {
		this.bandeja = bandeja;
	}

	public String getFechaPlazoAtencion() {
		return fechaPlazoAtencion;
	}

	public void setFechaPlazoAtencion(String fechaPlazoAtencion) {
		this.fechaPlazoAtencion = fechaPlazoAtencion;
	}

	public int getDias() {
		return dias;
	}

	public void setDias(int dias) {
		this.dias = dias;
	}


	public Integer getIdUnidadOrganica() {
		return idUnidadOrganica;
	}

	public void setIdUnidadOrganica(Integer idUnidadOrganica) {
		this.idUnidadOrganica = idUnidadOrganica;
	}
}
