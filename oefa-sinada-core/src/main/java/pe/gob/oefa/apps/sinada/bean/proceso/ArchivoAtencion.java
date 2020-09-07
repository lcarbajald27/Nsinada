package pe.gob.oefa.apps.sinada.bean.proceso;

import pe.gob.oefa.apps.base.bean.BaseBean;
import pe.gob.oefa.apps.sinada.bean.maestros.Maestro;

public class ArchivoAtencion extends BaseBean{
	
	private long idArchivoAtencion;
	private long idAtencionDenuncia;
	private String  nombreArchivo;
	private String rutaArchivo;
	private String mimeTypeArchivo;
	private Maestro estado;
	private String flagActivo;
	private Maestro tipoArchivo;
	private int tipoTabla; // TipoTablaArchivoAtencion
	private String descripcionArchivo;
	
	private String uiid;
	
	/******* Posicion de la lista en la que se encuentra el archivo ****/
	private int posicionArchivo;

	public ArchivoAtencion() {
		super();
	}


	public String getFlagActivo() {
		return flagActivo;
	}


	public void setFlagActivo(String flagActivo) {
		this.flagActivo = flagActivo;
	}


	public long getIdArchivoAtencion() {
		return idArchivoAtencion;
	}


	public void setIdArchivoAtencion(long idArchivoAtencion) {
		this.idArchivoAtencion = idArchivoAtencion;
	}


	public long getIdAtencionDenuncia() {
		return idAtencionDenuncia;
	}


	public void setIdAtencionDenuncia(long idAtencionDenuncia) {
		this.idAtencionDenuncia = idAtencionDenuncia;
	}


	public String getNombreArchivo() {
		return nombreArchivo;
	}


	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}


	public String getRutaArchivo() {
		return rutaArchivo;
	}


	public void setRutaArchivo(String rutaArchivo) {
		this.rutaArchivo = rutaArchivo;
	}


	public String getMimeTypeArchivo() {
		return mimeTypeArchivo;
	}


	public void setMimeTypeArchivo(String mimeTypeArchivo) {
		this.mimeTypeArchivo = mimeTypeArchivo;
	}


	public Maestro getEstado() {
		if(estado==null){
			estado = new Maestro();
		}
		return estado;
	}


	public void setEstado(Maestro estado) {
		this.estado = estado;
	}


	public Maestro getTipoArchivo() {
		if(tipoArchivo==null){
			tipoArchivo = new Maestro();
		}
		return tipoArchivo;
	}


	public void setTipoArchivo(Maestro tipoArchivo) {
		this.tipoArchivo = tipoArchivo;
	}


	public String getDescripcionArchivo() {
		return descripcionArchivo;
	}


	public void setDescripcionArchivo(String descripcionArchivo) {
		this.descripcionArchivo = descripcionArchivo;
	}


	public int getTipoTabla() {
		return tipoTabla;
	}


	public void setTipoTabla(int tipoTabla) {
		this.tipoTabla = tipoTabla;
	}


	public int getPosicionArchivo() {
		return posicionArchivo;
	}


	public void setPosicionArchivo(int posicionArchivo) {
		this.posicionArchivo = posicionArchivo;
	}


	public String getUiid() {
		return uiid;
	}


	public void setUiid(String uiid) {
		this.uiid = uiid;
	}




	
}
