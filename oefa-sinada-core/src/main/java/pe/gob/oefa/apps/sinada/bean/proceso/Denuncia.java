package pe.gob.oefa.apps.sinada.bean.proceso;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import java.util.Date;

import pe.gob.oefa.apps.base.bean.BaseBean;
import pe.gob.oefa.apps.sinada.bean.maestros.Maestro;
import pe.gob.oefa.apps.sinada.bean.maestros.Ubigeo;
import pe.gob.oefa.apps.sinada.bean.proceso.view.EntidadCaso;

public class Denuncia extends BaseBean{
	private long 	idDenuncia;
	private String 	codigoDenuncia;
	
    private int 	tipoDenuncia;
    
    private int 	medioRecepcion;
    private String nombreMedioRecepcion;
    private String 	hojaTramite;
    private String 	rutaHojaTramite;
    
    
    private Caso    caso;
    
    private String 	departamento;
    private String 	provincia;
    private String 	distrito;
    private String  codigoCentroPoblado;
    private String 	direccion;
    private String 	referencia;

	// Info GIS
	private String oficinasDesconcentradas;
	private String oficinasDesconcentradasNombre;
	private String areaConservacion;
	private String areaConservacionNombre;
	private String adminLocales;
	private String adminLocalesNombre;
	private String zonaAmortiguamiento;
	private String zonaAmortiguamientoNombre;
	private String cuerposAgua;
	private double latitude;
	private double longitude;
	private double coord_x_utm;
	private double coord_y_utm;
	private int zona;

    private int  	tipo_responsable;
    private long 	responsableProblema;
    
    private int 	tipoMedio;
    private String 	codigoAcceso;
    private String 	fechaRegistro;
    private String 	tiempoTranscurrido;
    private int 	estado;
    private String 	nombreEstado;
    
    private String 	flagActivo;
    private Maestro centroPoblado;
    
    private List<Denunciante> lstDenunciante;
   
    private List<ArchivoDenuncia> lstArchivoMedio;
    
    private String descripcionArchivo;
    
    private Maestro maestroTipoDenuncia;
    
    private Ubigeo ubDepartamento;
    private Ubigeo ubDistrito;
    private Ubigeo ubProvincia;
    
    private String rutaArchivoFicha;
    private String validaEncuesta;
    List<EntidadCaso> lstEntidadesCompetentes;
    
    
    private String nombreDenunciado;
    private String direccionDenunciado;
    
    private String uiid;
    
    private String rutaFicha;

	public Denuncia() {
		super();
	}


	public long getIdDenuncia() {
		return idDenuncia;
	}


	public void setIdDenuncia(long idDenuncia) {
		this.idDenuncia = idDenuncia;
	}


	public String getCodigoDenuncia() {
		return codigoDenuncia;
	}


	public void setCodigoDenuncia(String codigoDenuncia) {
		this.codigoDenuncia = codigoDenuncia;
	}


	public int getTipoDenuncia() {
		return tipoDenuncia;
	}


	public void setTipoDenuncia(int tipoDenuncia) {
		this.tipoDenuncia = tipoDenuncia;
	}


	public int getMedioRecepcion() {
		return medioRecepcion;
	}


	public void setMedioRecepcion(int medioRecepcion) {
		this.medioRecepcion = medioRecepcion;
	}


	public String getHojaTramite() {
		return hojaTramite;
	}


	public void setHojaTramite(String hojaTramite) {
		this.hojaTramite = hojaTramite;
	}


	public String getRutaHojaTramite() {
		return rutaHojaTramite;
	}


	public void setRutaHojaTramite(String rutaHojaTramite) {
		this.rutaHojaTramite = rutaHojaTramite;
	}


	public Caso getCaso() {
		return caso;
	}


	public void setCaso(Caso caso) {
		this.caso = caso;
	}


	public String getDepartamento() {
		return departamento;
	}


	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}


	public String getProvincia() {
		return provincia;
	}


	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}


	public String getDistrito() {
		return distrito;
	}


	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}


	public String getDireccion() {
		return direccion;
	}


	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}


	public String getReferencia() {
		return referencia;
	}


	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}


	public int getTipo_responsable() {
		return tipo_responsable;
	}


	public void setTipo_responsable(int tipo_responsable) {
		this.tipo_responsable = tipo_responsable;
	}





	public long getResponsableProblema() {
		return responsableProblema;
	}


	public void setResponsableProblema(long responsableProblema) {
		this.responsableProblema = responsableProblema;
	}


	public int getTipoMedio() {
		return tipoMedio;
	}


	public void setTipoMedio(int tipoMedio) {
		this.tipoMedio = tipoMedio;
	}


	public String getCodigoAcceso() {
		return codigoAcceso;
	}


	public void setCodigoAcceso(String codigoAcceso) {
		this.codigoAcceso = codigoAcceso;
	}


	public String getFechaRegistro() {
		return fechaRegistro;
	}


	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}


	public String getTiempoTranscurrido() {
		return tiempoTranscurrido;
	}


	public void setTiempoTranscurrido(String tiempoTranscurrido) {
		this.tiempoTranscurrido = tiempoTranscurrido;
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


	public List<Denunciante> getLstDenunciante() {
		return lstDenunciante;
	}


	public void setLstDenunciante(List<Denunciante> lstDenunciante) {
		this.lstDenunciante = lstDenunciante;
	}


	public List<ArchivoDenuncia> getLstArchivoMedio() {
		return lstArchivoMedio;
	}


	public void setLstArchivoMedio(List<ArchivoDenuncia> lstArchivoMedio) {
		this.lstArchivoMedio = lstArchivoMedio;
	}


	public String getDescripcionArchivo() {
		return descripcionArchivo;
	}


	public void setDescripcionArchivo(String descripcionArchivo) {
		this.descripcionArchivo = descripcionArchivo;
	}


	public Maestro getMaestroTipoDenuncia() {
		return maestroTipoDenuncia;
	}


	public void setMaestroTipoDenuncia(Maestro maestroTipoDenuncia) {
		this.maestroTipoDenuncia = maestroTipoDenuncia;
	}


	public Maestro getCentroPoblado() {
		return centroPoblado;
	}


	public void setCentroPoblado(Maestro centroPoblado) {
		this.centroPoblado = centroPoblado;
	}


	public Ubigeo getUbDepartamento() {
		return ubDepartamento;
	}


	public void setUbDepartamento(Ubigeo ubDepartamento) {
		this.ubDepartamento = ubDepartamento;
	}


	public Ubigeo getUbDistrito() {
		return ubDistrito;
	}


	public void setUbDistrito(Ubigeo ubDistrito) {
		this.ubDistrito = ubDistrito;
	}


	public Ubigeo getUbProvincia() {
		return ubProvincia;
	}


	public void setUbProvincia(Ubigeo ubProvincia) {
		this.ubProvincia = ubProvincia;
	}


	public String getRutaArchivoFicha() {
		return rutaArchivoFicha;
	}


	public void setRutaArchivoFicha(String rutaArchivoFicha) {
		this.rutaArchivoFicha = rutaArchivoFicha;
	}


	public List<EntidadCaso> getLstEntidadesCompetentes() {
		return lstEntidadesCompetentes;
	}


	public void setLstEntidadesCompetentes(List<EntidadCaso> lstEntidadesCompetentes) {
		this.lstEntidadesCompetentes = lstEntidadesCompetentes;
	}


	public String getNombreEstado() {
		return nombreEstado;
	}


	public void setNombreEstado(String nombreEstado) {
		this.nombreEstado = nombreEstado;
	}


	public String getValidaEncuesta() {
		return validaEncuesta;
	}


	public void setValidaEncuesta(String validaEncuesta) {
		this.validaEncuesta = validaEncuesta;
	}


	public String getCodigoCentroPoblado() {
		return codigoCentroPoblado;
	}


	public void setCodigoCentroPoblado(String codigoCentroPoblado) {
		this.codigoCentroPoblado = codigoCentroPoblado;
	}


	public String getUiid() {
		return uiid;
	}


	public void setUiid(String uiid) {
		this.uiid = uiid;
	}


	public String getRutaFicha() {
		return rutaFicha;
	}


	public void setRutaFicha(String rutaFicha) {
		this.rutaFicha = rutaFicha;
	}


	public String getNombreDenunciado() {
		return nombreDenunciado;
	}


	public void setNombreDenunciado(String nombreDenunciado) {
		this.nombreDenunciado = nombreDenunciado;
	}


	public String getDireccionDenunciado() {
		return direccionDenunciado;
	}


	public void setDireccionDenunciado(String direccionDenunciado) {
		this.direccionDenunciado = direccionDenunciado;
	}


	public String getNombreMedioRecepcion() {
		return nombreMedioRecepcion;
	}


	public void setNombreMedioRecepcion(String nombreMedioRecepcion) {
		this.nombreMedioRecepcion = nombreMedioRecepcion;
	}

	// Info GIS

	public String getOficinasDesconcentradas() {
		return oficinasDesconcentradas;
	}

	public void setOficinasDesconcentradas(String oficinasDesconcentradas) {
		this.oficinasDesconcentradas = oficinasDesconcentradas;
	}

	public String getAreaConservacion() {
		return areaConservacion;
	}

	public void setAreaConservacion(String areaConservacion) {
		this.areaConservacion = areaConservacion;
	}

	public String getAdminLocales() {
		return adminLocales;
	}

	public void setAdminLocales(String adminLocales) {
		this.adminLocales = adminLocales;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getCoord_x_utm() {
		return coord_x_utm;
	}

	public void setCoord_x_utm(double coord_x_utm) {
		this.coord_x_utm = coord_x_utm;
	}

	public double getCoord_y_utm() {
		return coord_y_utm;
	}

	public void setCoord_y_utm(double coord_y_utm) {
		this.coord_y_utm = coord_y_utm;
	}

	public int getZona() {
		return zona;
	}

	public void setZona(int zona) {
		this.zona = zona;
	}

	public String getZonaAmortiguamiento() {
		return zonaAmortiguamiento;
	}

	public void setZonaAmortiguamiento(String zonaAmortiguamiento) {
		this.zonaAmortiguamiento = zonaAmortiguamiento;
	}

	public String getOficinasDesconcentradasNombre() {
		return oficinasDesconcentradasNombre;
	}

	public void setOficinasDesconcentradasNombre(String oficinasDesconcentradasNombre) {
		this.oficinasDesconcentradasNombre = oficinasDesconcentradasNombre;
	}

	public String getAreaConservacionNombre() {
		return areaConservacionNombre;
	}

	public void setAreaConservacionNombre(String areaConservacionNombre) {
		this.areaConservacionNombre = areaConservacionNombre;
	}

	public String getAdminLocalesNombre() {
		return adminLocalesNombre;
	}

	public void setAdminLocalesNombre(String adminLocalesNombre) {
		this.adminLocalesNombre = adminLocalesNombre;
	}

	public String getZonaAmortiguamientoNombre() {
		return zonaAmortiguamientoNombre;
	}

	public void setZonaAmortiguamientoNombre(String zonaAmortiguamientoNombre) {
		this.zonaAmortiguamientoNombre = zonaAmortiguamientoNombre;
	}


	public String getCuerposAgua() {
		return cuerposAgua;
	}


	public void setCuerposAgua(String cuerposAgua) {
		this.cuerposAgua = cuerposAgua;
	}
}
