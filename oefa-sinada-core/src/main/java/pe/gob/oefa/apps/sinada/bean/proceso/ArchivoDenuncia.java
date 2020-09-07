package pe.gob.oefa.apps.sinada.bean.proceso;

import java.io.Serializable;
import java.util.Date;

import pe.gob.oefa.apps.base.bean.BaseBean;
import pe.gob.oefa.apps.sinada.bean.maestros.Maestro;

public class ArchivoDenuncia extends BaseBean{
	
	private long idArchivoDenuncia;
	private int tipoArchivoDenuncia;
	private String nombreTipoArchivo;
	private String nombreArchivo;
	private String rutaArchivoDenuncia;
	private long idDenuncia;
	private String flagActivo;
	private String mimeTypeArchivo;
	private String descripcionArchivo;
	private String posicionArchivo;
	private Maestro tipoArchivo;
	private String uiid;

	public String getPosicionArchivo() {
		return posicionArchivo;
	}


	public void setPosicionArchivo(String posicionArchivo) {
		this.posicionArchivo = posicionArchivo;
	}


	public ArchivoDenuncia() {
		super();
	}


	public long getIdArchivoDenuncia() {
		return idArchivoDenuncia;
	}


	public void setIdArchivoDenuncia(long idArchivoDenuncia) {
		this.idArchivoDenuncia = idArchivoDenuncia;
	}


	public int getTipoArchivoDenuncia() {
		return tipoArchivoDenuncia;
	}


	public void setTipoArchivoDenuncia(int tipoArchivoDenuncia) {
		this.tipoArchivoDenuncia = tipoArchivoDenuncia;
	}


	public String getNombreArchivo() {
		return nombreArchivo;
	}


	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}


	public String getRutaArchivoDenuncia() {
		return rutaArchivoDenuncia;
	}


	public void setRutaArchivoDenuncia(String rutaArchivoDenuncia) {
		this.rutaArchivoDenuncia = rutaArchivoDenuncia;
	}


	public long getIdDenuncia() {
		return idDenuncia;
	}


	public void setIdDenuncia(long idDenuncia) {
		this.idDenuncia = idDenuncia;
	}


	public String getFlagActivo() {
		return flagActivo;
	}


	public void setFlagActivo(String flagActivo) {
		this.flagActivo = flagActivo;
	}


	public String getMimeTypeArchivo() {
		return mimeTypeArchivo;
	}


	public void setMimeTypeArchivo(String mimeTypeArchivo) {
		this.mimeTypeArchivo = mimeTypeArchivo;
	}


	public String getNombreTipoArchivo() {
		return nombreTipoArchivo;
	}


	public void setNombreTipoArchivo(String nombreTipoArchivo) {
		this.nombreTipoArchivo = nombreTipoArchivo;
	}


	public String getDescripcionArchivo() {
		return descripcionArchivo;
	}


	public void setDescripcionArchivo(String descripcionArchivo) {
		this.descripcionArchivo = descripcionArchivo;
	}


	public Maestro getTipoArchivo() {
		return tipoArchivo;
	}


	public void setTipoArchivo(Maestro tipoArchivo) {
		this.tipoArchivo = tipoArchivo;
	}


	public String getUiid() {
		return uiid;
	}


	public void setUiid(String uiid) {
		this.uiid = uiid;
	}


	
	
	
}
