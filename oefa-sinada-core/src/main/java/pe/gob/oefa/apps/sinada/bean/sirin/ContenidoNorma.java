package pe.gob.oefa.apps.sinada.bean.sirin;

import java.io.Serializable;
import java.util.Date;

import pe.gob.oefa.apps.base.bean.BaseBean;

public class ContenidoNorma extends BaseBean{
	
	private long 		idContenidoNorma;
	private long 		idNorma;
	private int 		tipoDispositivo;
	private String 		nombreTipoDispositivo;
	private String      descripcionNorma;
    private String 		numeroNorma;
    private String 		numeroContenidoNorma;
    private long 		idTitulo;
    private long 		idCapitulo;
    private long		idSubcapitulo;
    private long		idSeccion;
    private long 		idArticulo;
    private	String 		fechaVigencia;
    private int			estado;
    private String      nombreEstado;
    private String		numeroTitulo;
    private String 		descripcionTitulo;
    private String 		numeroCapitulo;
    private String 		descripcionCapitulo;
    private String      numeroSubCapitulo;
    private String      descripcionSubCapitulo;
    private String 		numeroSeccion;
    private String      descripcionSeccion;
    private String      numeroArticulo;
    private String      descripcionArticulo;
    
    
    
    //**************************** //
    private long 		idCasoOefa;
    private long		idCasoEfa;
    private int  		tipoNormaCaso;

	public ContenidoNorma() {
		super();
	}

	public long getIdContenidoNorma() {
		return idContenidoNorma;
	}

	public void setIdContenidoNorma(long idContenidoNorma) {
		this.idContenidoNorma = idContenidoNorma;
	}

	public long getIdNorma() {
		return idNorma;
	}

	public void setIdNorma(long idNorma) {
		this.idNorma = idNorma;
	}

	public String getNumeroNorma() {
		return numeroNorma;
	}

	public void setNumeroNorma(String numeroNorma) {
		this.numeroNorma = numeroNorma;
	}

	public String getNumeroContenidoNorma() {
		return numeroContenidoNorma;
	}

	public void setNumeroContenidoNorma(String numeroContenidoNorma) {
		this.numeroContenidoNorma = numeroContenidoNorma;
	}

	public long getIdTitulo() {
		return idTitulo;
	}

	public void setIdTitulo(long idTitulo) {
		this.idTitulo = idTitulo;
	}

	public long getIdCapitulo() {
		return idCapitulo;
	}

	public void setIdCapitulo(long idCapitulo) {
		this.idCapitulo = idCapitulo;
	}

	public long getIdSubcapitulo() {
		return idSubcapitulo;
	}

	public void setIdSubcapitulo(long idSubcapitulo) {
		this.idSubcapitulo = idSubcapitulo;
	}

	public long getIdSeccion() {
		return idSeccion;
	}

	public void setIdSeccion(long idSeccion) {
		this.idSeccion = idSeccion;
	}

	public long getIdArticulo() {
		return idArticulo;
	}

	public void setIdArticulo(long idArticulo) {
		this.idArticulo = idArticulo;
	}

	public String getFechaVigencia() {
		return fechaVigencia;
	}

	public void setFechaVigencia(String fechaVigencia) {
		this.fechaVigencia = fechaVigencia;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public String getNumeroTitulo() {
		return numeroTitulo;
	}

	public void setNumeroTitulo(String numeroTitulo) {
		this.numeroTitulo = numeroTitulo;
	}

	public String getDescripcionTitulo() {
		return descripcionTitulo;
	}

	public void setDescripcionTitulo(String descripcionTitulo) {
		this.descripcionTitulo = descripcionTitulo;
	}

	public String getNumeroCapitulo() {
		return numeroCapitulo;
	}

	public void setNumeroCapitulo(String numeroCapitulo) {
		this.numeroCapitulo = numeroCapitulo;
	}

	public String getDescripcionCapitulo() {
		return descripcionCapitulo;
	}

	public void setDescripcionCapitulo(String descripcionCapitulo) {
		this.descripcionCapitulo = descripcionCapitulo;
	}

	public String getNumeroSubCapitulo() {
		return numeroSubCapitulo;
	}

	public void setNumeroSubCapitulo(String numeroSubCapitulo) {
		this.numeroSubCapitulo = numeroSubCapitulo;
	}

	public String getDescripcionSubCapitulo() {
		return descripcionSubCapitulo;
	}

	public void setDescripcionSubCapitulo(String descripcionSubCapitulo) {
		this.descripcionSubCapitulo = descripcionSubCapitulo;
	}

	public String getNumeroSeccion() {
		return numeroSeccion;
	}

	public void setNumeroSeccion(String numeroSeccion) {
		this.numeroSeccion = numeroSeccion;
	}

	public String getDescripcionSeccion() {
		return descripcionSeccion;
	}

	public void setDescripcionSeccion(String descripcionSeccion) {
		this.descripcionSeccion = descripcionSeccion;
	}

	public String getNumeroArticulo() {
		return numeroArticulo;
	}

	public void setNumeroArticulo(String numeroArticulo) {
		this.numeroArticulo = numeroArticulo;
	}

	public String getDescripcionArticulo() {
		return descripcionArticulo;
	}

	public void setDescripcionArticulo(String descripcionArticulo) {
		this.descripcionArticulo = descripcionArticulo;
	}

	public long getIdCasoEfa() {
		return idCasoEfa;
	}

	public void setIdCasoEfa(long idCasoEfa) {
		this.idCasoEfa = idCasoEfa;
	}

	public String getNombreEstado() {
		return nombreEstado;
	}

	public void setNombreEstado(String nombreEstado) {
		this.nombreEstado = nombreEstado;
	}

	public String getNombreTipoDispositivo() {
		return nombreTipoDispositivo;
	}

	public void setNombreTipoDispositivo(String nombreTipoDispositivo) {
		this.nombreTipoDispositivo = nombreTipoDispositivo;
	}

	public String getDescripcionNorma() {
		return descripcionNorma;
	}

	public void setDescripcionNorma(String descripcionNorma) {
		this.descripcionNorma = descripcionNorma;
	}

	public int getTipoDispositivo() {
		return tipoDispositivo;
	}

	public void setTipoDispositivo(int tipoDispositivo) {
		this.tipoDispositivo = tipoDispositivo;
	}

	public long getIdCasoOefa() {
		return idCasoOefa;
	}

	public void setIdCasoOefa(long idCasoOefa) {
		this.idCasoOefa = idCasoOefa;
	}

	public int getTipoNormaCaso() {
		return tipoNormaCaso;
	}

	public void setTipoNormaCaso(int tipoNormaCaso) {
		this.tipoNormaCaso = tipoNormaCaso;
	}



	
	
}
