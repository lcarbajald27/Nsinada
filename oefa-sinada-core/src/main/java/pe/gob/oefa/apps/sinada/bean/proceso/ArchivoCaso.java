package pe.gob.oefa.apps.sinada.bean.proceso;

import java.io.Serializable;
import java.util.Date;

import pe.gob.oefa.apps.base.bean.BaseBean;

public class ArchivoCaso extends BaseBean{
	
	private long idArchivoCaso;
	private long idDescripcionCaso;
	private int  tipoArchivoCaso;
	private String nombreArchivoCaso;
	private String rutaArchivoCaso;
	private String flagActivo;
	private String mimeTypeArchivo;
	private String uiid="0";
	

	public ArchivoCaso() {
		super();
	}


	public long getIdArchivoCaso() {
		return idArchivoCaso;
	}


	public void setIdArchivoCaso(long idArchivoCaso) {
		this.idArchivoCaso = idArchivoCaso;
	}


	public long getIdDescripcionCaso() {
		return idDescripcionCaso;
	}


	public void setIdDescripcionCaso(long idDescripcionCaso) {
		this.idDescripcionCaso = idDescripcionCaso;
	}


	public int getTipoArchivoCaso() {
		return tipoArchivoCaso;
	}


	public void setTipoArchivoCaso(int tipoArchivoCaso) {
		this.tipoArchivoCaso = tipoArchivoCaso;
	}


	public String getNombreArchivoCaso() {
		return nombreArchivoCaso;
	}


	public void setNombreArchivoCaso(String nombreArchivoCaso) {
		this.nombreArchivoCaso = nombreArchivoCaso;
	}


	public String getRutaArchivoCaso() {
		return rutaArchivoCaso;
	}


	public void setRutaArchivoCaso(String rutaArchivoCaso) {
		this.rutaArchivoCaso = rutaArchivoCaso;
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


	public String getUiid() {
		return uiid;
	}


	public void setUiid(String uiid) {
		this.uiid = uiid;
	}




	
}
