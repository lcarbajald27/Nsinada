package pe.gob.oefa.apps.sinada.bean.sirin;

import java.io.Serializable;
import java.util.Date;

import pe.gob.oefa.apps.base.bean.BaseBean;

public class Normas extends BaseBean{
	
    private long 	idNorma;
    private String 	numeroNorma;
    private int     ambitoDispositivo;
    private String 	descripcion;
    private int 	tipoDispositivo;
    private String 	nombreTipoDispositivo;
    private String 	fechaVigencia;
    private String 	fechaModificacion;
    private String 	fechaDerogacion;
    private int 	sector;
    private int 	subSector;
    private int 	actividad;
    private long 	entidadEmisora;
    private int 	estado;
    private String 	nombreEstado;
    private String 	flagActivo;
    private String numeroNorma2;
    private String descripcionGeneral;
    
    /****************************/
    private long 	idCasoEfa;
    
    /***************************/
	public Normas() {
		super();
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getTipoDispositivo() {
		return tipoDispositivo;
	}

	public void setTipoDispositivo(int tipoDispositivo) {
		this.tipoDispositivo = tipoDispositivo;
	}


	public String getFechaVigencia() {
		return fechaVigencia;
	}

	public void setFechaVigencia(String fechaVigencia) {
		this.fechaVigencia = fechaVigencia;
	}

	public String getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(String fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public String getFechaDerogacion() {
		return fechaDerogacion;
	}

	public void setFechaDerogacion(String fechaDerogacion) {
		this.fechaDerogacion = fechaDerogacion;
	}

	public int getSector() {
		return sector;
	}

	public void setSector(int sector) {
		this.sector = sector;
	}

	public int getSubSector() {
		return subSector;
	}

	public void setSubSector(int subSector) {
		this.subSector = subSector;
	}

	public int getActividad() {
		return actividad;
	}

	public void setActividad(int actividad) {
		this.actividad = actividad;
	}

	public long getEntidadEmisora() {
		return entidadEmisora;
	}

	public void setEntidadEmisora(long entidadEmisora) {
		this.entidadEmisora = entidadEmisora;
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

	public int getAmbitoDispositivo() {
		return ambitoDispositivo;
	}

	public void setAmbitoDispositivo(int ambitoDispositivo) {
		this.ambitoDispositivo = ambitoDispositivo;
	}

	public String getNombreTipoDispositivo() {
		return nombreTipoDispositivo;
	}

	public void setNombreTipoDispositivo(String nombreTipoDispositivo) {
		this.nombreTipoDispositivo = nombreTipoDispositivo;
	}

	public String getNombreEstado() {
		return nombreEstado;
	}

	public void setNombreEstado(String nombreEstado) {
		this.nombreEstado = nombreEstado;
	}

	public String getNumeroNorma2() {
		return numeroNorma2;
	}

	public void setNumeroNorma2(String numeroNorma2) {
		this.numeroNorma2 = numeroNorma2;
	}

	public long getIdCasoEfa() {
		return idCasoEfa;
	}

	public void setIdCasoEfa(long idCasoEfa) {
		this.idCasoEfa = idCasoEfa;
	}

	public String getDescripcionGeneral() {
		return descripcionGeneral;
	}

	public void setDescripcionGeneral(String descripcionGeneral) {
		this.descripcionGeneral = descripcionGeneral;
	}



	
}
