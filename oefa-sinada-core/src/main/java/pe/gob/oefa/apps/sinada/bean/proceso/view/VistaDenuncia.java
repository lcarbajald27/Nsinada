package pe.gob.oefa.apps.sinada.bean.proceso.view;

import java.util.List;

import pe.gob.oefa.apps.base.bean.BaseBean;
import pe.gob.oefa.apps.sinada.bean.proceso.ArchivoDenuncia;
import pe.gob.oefa.apps.sinada.bean.proceso.Denunciante;

public class VistaDenuncia extends BaseBean{
	
	
	private long idDenuncia;
	private String codigoDenuncia;
	private int tipoDenuncia;
	private String nombreTipoDenuncia;
	private int medioRecepcion;
	private String nombreMedioRecepcion;
	private String hojaTramite;
	private String departamento;
	private String nombreDepartamento;
	private String provincia;
	private String nombreProvincia;
	private String distrito;
	private String nombreDistrito;
	private String centroPoblado;
	private String nombreCentroPoblado;
	private String direccion;
	private String referencia;
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

	private int tipoResponsable;
	private String nombreTipoResponsable;
	private long responsableProblema;
	private String nombreDenunciado;
	private String documentoDenunciado;
	private String departamentoDenunciado;
	private String provinciaDenunciado;
	private String distritoDenunciado;
	private String direccionDenunciado;
	private String referenciaDenunciado;
	private String nombreCargoDenunciado;
	private String nombreRepreDenunciado;
	private String nombreDepartamentoDenunciado;
	private String nombreProvinciaDenunciado;
	private String nombreDistritoDenunciado;
	
	private String nombreDenunciadoDenuncia;
	private String direccionDenunciadoDenuncia;
	
	private int  tipoMedio;
	private String nombreTipoMedioArchivo;
	private String codigoacceso;
	private String fechaRegistro;
	private int estado;
	
	private String rutaHojaTramite;
	private long idCaso;
	private String numeroCaso;
	private long idProblematica;
	private String nombreProblematica;
	private long idDebidoa1;
	private String nombreDebidoaNivel1;
	private long idDebidoa2;
	private String nombreDebidoaNivel2;
	private long idDebidoa3;
	private String nombreDebidoaNivel3;
	private long idZonaSuceso1;
	private String nombreZonaSuceso1;
	private long idZonaSuceso2;
	private String nombreZonaSuceso2;
	private long idZonaSuceso3;
	private String nombreZonaSuceso3;
	
	private String uiid;
	
	
	private List<Denunciante> lstDenunciante = null;
	
	private List<EntidadCaso> lstEntidadCaso=null;
	
	private List<ArchivoDenuncia> lstArchivoDenuncia;
	
	private String dataHTML;
	
	private String rutaFichaDenuncia;
	
	
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
	public String getNombreTipoDenuncia() {
		return nombreTipoDenuncia;
	}
	public void setNombreTipoDenuncia(String nombreTipoDenuncia) {
		this.nombreTipoDenuncia = nombreTipoDenuncia;
	}
	public int getMedioRecepcion() {
		return medioRecepcion;
	}
	public void setMedioRecepcion(int medioRecepcion) {
		this.medioRecepcion = medioRecepcion;
	}
	public String getNombreMedioRecepcion() {
		return nombreMedioRecepcion;
	}
	public void setNombreMedioRecepcion(String nombreMedioRecepcion) {
		this.nombreMedioRecepcion = nombreMedioRecepcion;
	}
	public String getHojaTramite() {
		return hojaTramite;
	}
	public void setHojaTramite(String hojaTramite) {
		this.hojaTramite = hojaTramite;
	}
	public String getDepartamento() {
		return departamento;
	}
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
	public String getNombreDepartamento() {
		return nombreDepartamento;
	}
	public void setNombreDepartamento(String nombreDepartamento) {
		this.nombreDepartamento = nombreDepartamento;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getNombreProvincia() {
		return nombreProvincia;
	}
	public void setNombreProvincia(String nombreProvincia) {
		this.nombreProvincia = nombreProvincia;
	}
	public String getDistrito() {
		return distrito;
	}
	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}
	public String getNombreDistrito() {
		return nombreDistrito;
	}
	public void setNombreDistrito(String nombreDistrito) {
		this.nombreDistrito = nombreDistrito;
	}
	public String getCentroPoblado() {
		return centroPoblado;
	}
	public void setCentroPoblado(String centroPoblado) {
		this.centroPoblado = centroPoblado;
	}
	public String getNombreCentroPoblado() {
		return nombreCentroPoblado;
	}
	public void setNombreCentroPoblado(String nombreCentroPoblado) {
		this.nombreCentroPoblado = nombreCentroPoblado;
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
	public int getTipoResponsable() {
		return tipoResponsable;
	}
	public void setTipoResponsable(int tipoResponsable) {
		this.tipoResponsable = tipoResponsable;
	}
	public String getNombreTipoResponsable() {
		return nombreTipoResponsable;
	}
	public void setNombreTipoResponsable(String nombreTipoResponsable) {
		this.nombreTipoResponsable = nombreTipoResponsable;
	}
	public long getResponsableProblema() {
		return responsableProblema;
	}
	public void setResponsableProblema(long responsableProblema) {
		this.responsableProblema = responsableProblema;
	}
	public String getNombreDenunciado() {
		return nombreDenunciado;
	}
	public void setNombreDenunciado(String nombreDenunciado) {
		this.nombreDenunciado = nombreDenunciado;
	}
	public String getDocumentoDenunciado() {
		return documentoDenunciado;
	}
	public void setDocumentoDenunciado(String documentoDenunciado) {
		this.documentoDenunciado = documentoDenunciado;
	}
	public String getDepartamentoDenunciado() {
		return departamentoDenunciado;
	}
	public void setDepartamentoDenunciado(String departamentoDenunciado) {
		this.departamentoDenunciado = departamentoDenunciado;
	}
	public String getProvinciaDenunciado() {
		return provinciaDenunciado;
	}
	public void setProvinciaDenunciado(String provinciaDenunciado) {
		this.provinciaDenunciado = provinciaDenunciado;
	}
	public String getDistritoDenunciado() {
		return distritoDenunciado;
	}
	public void setDistritoDenunciado(String distritoDenunciado) {
		this.distritoDenunciado = distritoDenunciado;
	}
	public String getDireccionDenunciado() {
		return direccionDenunciado;
	}
	public void setDireccionDenunciado(String direccionDenunciado) {
		this.direccionDenunciado = direccionDenunciado;
	}
	public String getReferenciaDenunciado() {
		return referenciaDenunciado;
	}
	public void setReferenciaDenunciado(String referenciaDenunciado) {
		this.referenciaDenunciado = referenciaDenunciado;
	}
	public String getNombreCargoDenunciado() {
		return nombreCargoDenunciado;
	}
	public void setNombreCargoDenunciado(String nombreCargoDenunciado) {
		this.nombreCargoDenunciado = nombreCargoDenunciado;
	}
	public String getNombreRepreDenunciado() {
		return nombreRepreDenunciado;
	}
	public void setNombreRepreDenunciado(String nombreRepreDenunciado) {
		this.nombreRepreDenunciado = nombreRepreDenunciado;
	}
	public int getTipoMedio() {
		return tipoMedio;
	}
	public void setTipoMedio(int tipoMedio) {
		this.tipoMedio = tipoMedio;
	}
	public String getNombreTipoMedioArchivo() {
		return nombreTipoMedioArchivo;
	}
	public void setNombreTipoMedioArchivo(String nombreTipoMedioArchivo) {
		this.nombreTipoMedioArchivo = nombreTipoMedioArchivo;
	}
	public String getCodigoacceso() {
		return codigoacceso;
	}
	public void setCodigoacceso(String codigoacceso) {
		this.codigoacceso = codigoacceso;
	}
	public String getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public String getRutaHojaTramite() {
		return rutaHojaTramite;
	}
	public void setRutaHojaTramite(String rutaHojaTramite) {
		this.rutaHojaTramite = rutaHojaTramite;
	}
	public long getIdCaso() {
		return idCaso;
	}
	public void setIdCaso(long idCaso) {
		this.idCaso = idCaso;
	}
	public String getNumeroCaso() {
		return numeroCaso;
	}
	public void setNumeroCaso(String numeroCaso) {
		this.numeroCaso = numeroCaso;
	}
	public long getIdProblematica() {
		return idProblematica;
	}
	public void setIdProblematica(long idProblematica) {
		this.idProblematica = idProblematica;
	}
	public String getNombreProblematica() {
		return nombreProblematica;
	}
	public void setNombreProblematica(String nombreProblematica) {
		this.nombreProblematica = nombreProblematica;
	}
	public long getIdDebidoa1() {
		return idDebidoa1;
	}
	public void setIdDebidoa1(long idDebidoa1) {
		this.idDebidoa1 = idDebidoa1;
	}
	public String getNombreDebidoaNivel1() {
		return nombreDebidoaNivel1;
	}
	public void setNombreDebidoaNivel1(String nombreDebidoaNivel1) {
		this.nombreDebidoaNivel1 = nombreDebidoaNivel1;
	}
	public long getIdDebidoa2() {
		return idDebidoa2;
	}
	public void setIdDebidoa2(long idDebidoa2) {
		this.idDebidoa2 = idDebidoa2;
	}
	public String getNombreDebidoaNivel2() {
		return nombreDebidoaNivel2;
	}
	public void setNombreDebidoaNivel2(String nombreDebidoaNivel2) {
		this.nombreDebidoaNivel2 = nombreDebidoaNivel2;
	}
	public long getIdDebidoa3() {
		return idDebidoa3;
	}
	public void setIdDebidoa3(long idDebidoa3) {
		this.idDebidoa3 = idDebidoa3;
	}
	public String getNombreDebidoaNivel3() {
		return nombreDebidoaNivel3;
	}
	public void setNombreDebidoaNivel3(String nombreDebidoaNivel3) {
		this.nombreDebidoaNivel3 = nombreDebidoaNivel3;
	}
	public long getIdZonaSuceso1() {
		return idZonaSuceso1;
	}
	public void setIdZonaSuceso1(long idZonaSuceso1) {
		this.idZonaSuceso1 = idZonaSuceso1;
	}
	public String getNombreZonaSuceso1() {
		return nombreZonaSuceso1;
	}
	public void setNombreZonaSuceso1(String nombreZonaSuceso1) {
		this.nombreZonaSuceso1 = nombreZonaSuceso1;
	}
	public long getIdZonaSuceso2() {
		return idZonaSuceso2;
	}
	public void setIdZonaSuceso2(long idZonaSuceso2) {
		this.idZonaSuceso2 = idZonaSuceso2;
	}
	public String getNombreZonaSuceso2() {
		return nombreZonaSuceso2;
	}
	public void setNombreZonaSuceso2(String nombreZonaSuceso2) {
		this.nombreZonaSuceso2 = nombreZonaSuceso2;
	}
	public long getIdZonaSuceso3() {
		return idZonaSuceso3;
	}
	public void setIdZonaSuceso3(long idZonaSuceso3) {
		this.idZonaSuceso3 = idZonaSuceso3;
	}
	public String getNombreZonaSuceso3() {
		return nombreZonaSuceso3;
	}
	public void setNombreZonaSuceso3(String nombreZonaSuceso3) {
		this.nombreZonaSuceso3 = nombreZonaSuceso3;
	}
	public String getNombreDepartamentoDenunciado() {
		return nombreDepartamentoDenunciado;
	}
	public void setNombreDepartamentoDenunciado(String nombreDepartamentoDenunciado) {
		this.nombreDepartamentoDenunciado = nombreDepartamentoDenunciado;
	}
	public String getNombreProvinciaDenunciado() {
		return nombreProvinciaDenunciado;
	}
	public void setNombreProvinciaDenunciado(String nombreProvinciaDenunciado) {
		this.nombreProvinciaDenunciado = nombreProvinciaDenunciado;
	}
	public String getNombreDistritoDenunciado() {
		return nombreDistritoDenunciado;
	}
	public void setNombreDistritoDenunciado(String nombreDistritoDenunciado) {
		this.nombreDistritoDenunciado = nombreDistritoDenunciado;
	}
	public List<Denunciante> getLstDenunciante() {
		return lstDenunciante;
	}
	public void setLstDenunciante(List<Denunciante> lstDenunciante) {
		this.lstDenunciante = lstDenunciante;
	}
	public List<EntidadCaso> getLstEntidadCaso() {
		return lstEntidadCaso;
	}
	public void setLstEntidadCaso(List<EntidadCaso> lstEntidadCaso) {
		this.lstEntidadCaso = lstEntidadCaso;
	}
	public List<ArchivoDenuncia> getLstArchivoDenuncia() {
		return lstArchivoDenuncia;
	}
	public void setLstArchivoDenuncia(List<ArchivoDenuncia> lstArchivoDenuncia) {
		this.lstArchivoDenuncia = lstArchivoDenuncia;
	}
	public String getDataHTML() {
		return dataHTML;
	}
	public void setDataHTML(String dataHTML) {
		this.dataHTML = dataHTML;
	}
	public String getUiid() {
		return uiid;
	}
	public void setUiid(String uiid) {
		this.uiid = uiid;
	}
	public String getRutaFichaDenuncia() {
		return rutaFichaDenuncia;
	}
	public void setRutaFichaDenuncia(String rutaFichaDenuncia) {
		this.rutaFichaDenuncia = rutaFichaDenuncia;
	}
	public String getNombreDenunciadoDenuncia() {
		return nombreDenunciadoDenuncia;
	}
	public void setNombreDenunciadoDenuncia(String nombreDenunciadoDenuncia) {
		this.nombreDenunciadoDenuncia = nombreDenunciadoDenuncia;
	}
	public String getDireccionDenunciadoDenuncia() {
		return direccionDenunciadoDenuncia;
	}
	public void setDireccionDenunciadoDenuncia(String direccionDenunciadoDenuncia) {
		this.direccionDenunciadoDenuncia = direccionDenunciadoDenuncia;
	}


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
