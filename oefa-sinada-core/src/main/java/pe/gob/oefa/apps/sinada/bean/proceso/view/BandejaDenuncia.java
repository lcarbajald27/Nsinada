package pe.gob.oefa.apps.sinada.bean.proceso.view;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import pe.gob.oefa.apps.base.bean.BaseBean;
import pe.gob.oefa.apps.sinada.bean.proceso.Denuncia;
import pe.gob.oefa.apps.sinada.bean.proceso.DetalleCaso;

public class BandejaDenuncia extends BaseBean{
	
	
	private int tipoEntidadAtencion;
	private String nombreTipoEntidadAtencion;
	private long idBandejaDetalle;
	private long idBandeja;
	private int  tipoResponsableBandeja;
	private int idResponsableBandeja;
	private long idEfa;
	private int  direccionSupervicion;
	private int subDireccion;
	private int coordinacion;
	private int estadoBandeja;
	
	private int tipoBandeja;
	private long idDenuncia;
	private int estadoBandejaDetalle;
	private String nombreEstadoBandejaDetalle;
	private int tipoAsignacion;
	private String nombreTipoAsignacion;
	
	
	private String codigoDenuncia;
	private int tipoDenuncia;
	private String nombreTipoDenuncia;
	private int medioRecepcion;
	private String nombreMedioRecepcion;
	private long idCaso;
	
	private String 	numeroCaso;
	private long 	idProblematica;
	private String 	nombreProblematica;
	private String 	idDebidoA1;
	private String 	nombreDebidoA1;
	private String 	idDebidoA2;
	private String 	nombreDebidoA2;
	private String 	idDebidoA3;
	private String 	nombreDebidoA3;
	private String 	idZonaSuceso1;
	private String 	nombreZonaSuceso1;
	private String 	idZonaSuceso2;
	private String 	nombreZonaSuceso2;
	private String 	idZonaSuceso3;
	private String 	nombreZonaSuceso3;
	
	private int numeroDenunciantes;
	
	
	
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
	private String nombreDepartamentoDenunciado;
	private String nombreProvinciaDenunciado;
	private String nombreDistritoDenunciado;
	
	private String nombreDenunciadoDenuncia;
	private String direccionDenunciadoDenuncia;
	
	
	
	private String codigoAcceso;
	private String fechaRegistroDenuncia;
	private int estadoDenuncia;
	private String nombreEstadoDenuncia;
	private String tiempoTranscurrido;
	private String fechaUltimaAccion;
	private String tiempoUltimaAccion;
	
	/********************************/
	
	private int totalAcciones;
	private int denunciasAtendidas;
	private int denunciasArchivadas;
	private int denunciasNoAtendida;
	private int denunciasPendientes;
	
	private String fechaInicioAtencion;
	
	private int diasHabilesPlazo;
	
	private Date fechaRegistroDenunciaAsignadaOrgano;
	private double diasHabilesTranscurridosDenunciaAsignada;
	
	/*********************************/
	
	private String fechaPlazoAtencion;
	
	
	private List<EntidadCaso> lstEntidadCaso;
	
	private List<EntidadDenuncia> lstEntidadDenuncia;
	
	//CAMPOS PARA FILTROS
	
	private String nombreDenunciante;
	
	private String fechaRegistroDenunciaInicio;
	private String fechaRegistroDenunciaFin;
	private String fechaTiempoTranscurrido;
	private String fechaTiempoTranscurridoUltimaAccion;
	private String year;
	private String month;
	private String nombreEntidad;
	
	private DetalleCaso 	problematica = new DetalleCaso();
    
    private DetalleCaso 	debidoA1;
    private DetalleCaso 	debidoA2;
    private DetalleCaso 	debidoA3;
    
    private DetalleCaso 	zonasuceso1;
    private DetalleCaso 	zonasuceso2;
    private DetalleCaso 	zonasuceso3;
	
    private Denuncia denuncia;
    
    
    private String validaEncuestaDenunciante;
    private String nombreEspecialistaSinada;
    private int validaEspecialista;
    
    private int tipoValidacionEspecialista;
	
    
    List<AccionesRealizadas> lstAccionesRealizadas;
    
    private String nombreEntidadCompentente;
	public long getIdBandejaDetalle() {
		return idBandejaDetalle;
	}
	public void setIdBandejaDetalle(long idBandejaDetalle) {
		this.idBandejaDetalle = idBandejaDetalle;
	}
	public long getIdBandeja() {
		return idBandeja;
	}
	public void setIdBandeja(long idBandeja) {
		this.idBandeja = idBandeja;
	}
	public int getTipoResponsableBandeja() {
		return tipoResponsableBandeja;
	}
	public void setTipoResponsableBandeja(int tipoResponsableBandeja) {
		this.tipoResponsableBandeja = tipoResponsableBandeja;
	}
	public int getIdResponsableBandeja() {
		return idResponsableBandeja;
	}
	public void setIdResponsableBandeja(int idResponsableBandeja) {
		this.idResponsableBandeja = idResponsableBandeja;
	}
	public int getEstadoBandeja() {
		return estadoBandeja;
	}
	public void setEstadoBandeja(int estadoBandeja) {
		this.estadoBandeja = estadoBandeja;
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
	public int getEstadoBandejaDetalle() {
		return estadoBandejaDetalle;
	}
	public void setEstadoBandejaDetalle(int estadoBandejaDetalle) {
		this.estadoBandejaDetalle = estadoBandejaDetalle;
	}
	public int getTipoAsignacion() {
		return tipoAsignacion;
	}
	public void setTipoAsignacion(int tipoAsignacion) {
		this.tipoAsignacion = tipoAsignacion;
	}
	public String getNombreTipoAsignacion() {
		return nombreTipoAsignacion;
	}
	public void setNombreTipoAsignacion(String nombreTipoAsignacion) {
		this.nombreTipoAsignacion = nombreTipoAsignacion;
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
	public String getNombreMedioRecepcion() {
		return nombreMedioRecepcion;
	}
	public void setNombreMedioRecepcion(String nombreMedioRecepcion) {
		this.nombreMedioRecepcion = nombreMedioRecepcion;
	}
	public long getIdCaso() {
		return idCaso;
	}
	public void setIdCaso(long idCaso) {
		this.idCaso = idCaso;
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
	public String getCodigoAcceso() {
		return codigoAcceso;
	}
	public void setCodigoAcceso(String codigoAcceso) {
		this.codigoAcceso = codigoAcceso;
	}
	public String getFechaRegistroDenuncia() {
		return fechaRegistroDenuncia;
	}
	public void setFechaRegistroDenuncia(String fechaRegistroDenuncia) {
		this.fechaRegistroDenuncia = fechaRegistroDenuncia;
	}
	public int getEstadoDenuncia() {
		return estadoDenuncia;
	}
	public void setEstadoDenuncia(int estadoDenuncia) {
		this.estadoDenuncia = estadoDenuncia;
	}
	public String getNombreEstadoDenuncia() {
		return nombreEstadoDenuncia;
	}
	public void setNombreEstadoDenuncia(String nombreEstadoDenuncia) {
		this.nombreEstadoDenuncia = nombreEstadoDenuncia;
	}
	
	public String getNombreTipoDenuncia() {
		return nombreTipoDenuncia;
	}
	public void setNombreTipoDenuncia(String nombreTipoDenuncia) {
		this.nombreTipoDenuncia = nombreTipoDenuncia;
	}
	
	public String getTiempoTranscurrido() {
		return tiempoTranscurrido;
	}
	public void setTiempoTranscurrido(String tiempoTranscurrido) {
		this.tiempoTranscurrido = tiempoTranscurrido;
	}
	public String getNombreDepartamento() {
		return nombreDepartamento;
	}
	public void setNombreDepartamento(String nombreDepartamento) {
		this.nombreDepartamento = nombreDepartamento;
	}
	public String getNombreProvincia() {
		return nombreProvincia;
	}
	public void setNombreProvincia(String nombreProvincia) {
		this.nombreProvincia = nombreProvincia;
	}
	public String getNombreDistrito() {
		return nombreDistrito;
	}
	public void setNombreDistrito(String nombreDistrito) {
		this.nombreDistrito = nombreDistrito;
	}
	public List<EntidadCaso> getLstEntidadCaso() {
		if(lstEntidadCaso == null){
			lstEntidadCaso = new ArrayList<EntidadCaso>();
		}
		return lstEntidadCaso;
	}
	public void setLstEntidadCaso(List<EntidadCaso> lstEntidadCaso) {
		this.lstEntidadCaso = lstEntidadCaso;
	}
	public List<EntidadDenuncia> getLstEntidadDenuncia() {
		if(lstEntidadDenuncia == null){
			lstEntidadDenuncia = new ArrayList<EntidadDenuncia>();
		}
		return lstEntidadDenuncia;
	}
	public void setLstEntidadDenuncia(List<EntidadDenuncia> lstEntidadDenuncia) {
		this.lstEntidadDenuncia = lstEntidadDenuncia;
	}
	public String getNombreEstadoBandejaDetalle() {
		return nombreEstadoBandejaDetalle;
	}
	public void setNombreEstadoBandejaDetalle(String nombreEstadoBandejaDetalle) {
		this.nombreEstadoBandejaDetalle = nombreEstadoBandejaDetalle;
	}
	public String getFechaUltimaAccion() {
		return fechaUltimaAccion;
	}
	public void setFechaUltimaAccion(String fechaUltimaAccion) {
		this.fechaUltimaAccion = fechaUltimaAccion;
	}
	public String getTiempoUltimaAccion() {
		return tiempoUltimaAccion;
	}
	public void setTiempoUltimaAccion(String tiempoUltimaAccion) {
		this.tiempoUltimaAccion = tiempoUltimaAccion;
	}
	public long getIdEfa() {
		return idEfa;
	}
	public void setIdEfa(long idEfa) {
		this.idEfa = idEfa;
	}
	public int getDireccionSupervicion() {
		return direccionSupervicion;
	}
	public void setDireccionSupervicion(int direccionSupervicion) {
		this.direccionSupervicion = direccionSupervicion;
	}
	public int getSubDireccion() {
		return subDireccion;
	}
	public void setSubDireccion(int subDireccion) {
		this.subDireccion = subDireccion;
	}
	public int getCoordinacion() {
		return coordinacion;
	}
	public void setCoordinacion(int coordinacion) {
		this.coordinacion = coordinacion;
	}
	
	//CONSTRUCTORES PARA FILTROS
	
	public String getNombreDenunciante() {
		return nombreDenunciante;
	}
	public void setNombreDenunciante(String nombreDenunciante) {
		this.nombreDenunciante = nombreDenunciante;
	}
	public String getNombreDenunciado() {
		return nombreDenunciado;
	}
	public void setNombreDenunciado(String nombreDenunciado) {
		this.nombreDenunciado = nombreDenunciado;
	}
	public String getFechaRegistroDenunciaInicio() {
		return fechaRegistroDenunciaInicio;
	}
	public void setFechaRegistroDenunciaInicio(String fechaRegistroDenunciaInicio) {
		this.fechaRegistroDenunciaInicio = fechaRegistroDenunciaInicio;
	}
	public String getFechaRegistroDenunciaFin() {
		return fechaRegistroDenunciaFin;
	}
	public void setFechaRegistroDenunciaFin(String fechaRegistroDenunciaFin) {
		this.fechaRegistroDenunciaFin = fechaRegistroDenunciaFin;
	}
	public String getFechaTiempoTranscurrido() {
		return fechaTiempoTranscurrido;
	}
	public void setFechaTiempoTranscurrido(String fechaTiempoTranscurrido) {
		this.fechaTiempoTranscurrido = fechaTiempoTranscurrido;
	}
	public String getFechaTiempoTranscurridoUltimaAccion() {
		return fechaTiempoTranscurridoUltimaAccion;
	}
	public void setFechaTiempoTranscurridoUltimaAccion(String fechaTiempoTranscurridoUltimaAccion) {
		this.fechaTiempoTranscurridoUltimaAccion = fechaTiempoTranscurridoUltimaAccion;
	}
	public DetalleCaso getProblematica() {
		return problematica;
	}
	public void setProblematica(DetalleCaso problematica) {
		this.problematica = problematica;
	}
	public DetalleCaso getDebidoA1() {
		return debidoA1;
	}
	public void setDebidoA1(DetalleCaso debidoA1) {
		this.debidoA1 = debidoA1;
	}
	public DetalleCaso getDebidoA2() {
		return debidoA2;
	}
	public void setDebidoA2(DetalleCaso debidoA2) {
		this.debidoA2 = debidoA2;
	}
	public DetalleCaso getDebidoA3() {
		return debidoA3;
	}
	public void setDebidoA3(DetalleCaso debidoA3) {
		this.debidoA3 = debidoA3;
	}
	public DetalleCaso getZonasuceso1() {
		return zonasuceso1;
	}
	public void setZonasuceso1(DetalleCaso zonasuceso1) {
		this.zonasuceso1 = zonasuceso1;
	}
	public DetalleCaso getZonasuceso2() {
		return zonasuceso2;
	}
	public void setZonasuceso2(DetalleCaso zonasuceso2) {
		this.zonasuceso2 = zonasuceso2;
	}
	public DetalleCaso getZonasuceso3() {
		return zonasuceso3;
	}
	public void setZonasuceso3(DetalleCaso zonasuceso3) {
		this.zonasuceso3 = zonasuceso3;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getNombreEntidad() {
		return nombreEntidad;
	}
	public void setNombreEntidad(String nombreEntidad) {
		this.nombreEntidad = nombreEntidad;
	}
	public int getTotalAcciones() {
		return totalAcciones;
	}
	public void setTotalAcciones(int totalAcciones) {
		this.totalAcciones = totalAcciones;
	}
	public int getDenunciasAtendidas() {
		return denunciasAtendidas;
	}
	public void setDenunciasAtendidas(int denunciasAtendidas) {
		this.denunciasAtendidas = denunciasAtendidas;
	}
	public int getDenunciasArchivadas() {
		return denunciasArchivadas;
	}
	public void setDenunciasArchivadas(int denunciasArchivadas) {
		this.denunciasArchivadas = denunciasArchivadas;
	}
	public int getDenunciasNoAtendida() {
		return denunciasNoAtendida;
	}
	public void setDenunciasNoAtendida(int denunciasNoAtendida) {
		this.denunciasNoAtendida = denunciasNoAtendida;
	}
	public int getDenunciasPendientes() {
		return denunciasPendientes;
	}
	public void setDenunciasPendientes(int denunciasPendientes) {
		this.denunciasPendientes = denunciasPendientes;
	}
	public String getFechaPlazoAtencion() {
		return fechaPlazoAtencion;
	}
	public void setFechaPlazoAtencion(String fechaPlazoAtencion) {
		this.fechaPlazoAtencion = fechaPlazoAtencion;
	}
	public String getNombreEspecialistaSinada() {
		return nombreEspecialistaSinada;
	}
	public void setNombreEspecialistaSinada(String nombreEspecialistaSinada) {
		this.nombreEspecialistaSinada = nombreEspecialistaSinada;
	}
	public int getTipoValidacionEspecialista() {
		return tipoValidacionEspecialista;
	}
	public void setTipoValidacionEspecialista(int tipoValidacionEspecialista) {
		this.tipoValidacionEspecialista = tipoValidacionEspecialista;
	}
	public String getValidaEncuestaDenunciante() {
		return validaEncuestaDenunciante;
	}
	public void setValidaEncuestaDenunciante(String validaEncuestaDenunciante) {
		this.validaEncuestaDenunciante = validaEncuestaDenunciante;
	}
	public int getValidaEspecialista() {
		return validaEspecialista;
	}
	public void setValidaEspecialista(int validaEspecialista) {
		this.validaEspecialista = validaEspecialista;
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
	public String getIdDebidoA1() {
		return idDebidoA1;
	}
	public void setIdDebidoA1(String idDebidoA1) {
		this.idDebidoA1 = idDebidoA1;
	}
	public String getNombreDebidoA1() {
		return nombreDebidoA1;
	}
	public void setNombreDebidoA1(String nombreDebidoA1) {
		this.nombreDebidoA1 = nombreDebidoA1;
	}
	public String getIdDebidoA2() {
		return idDebidoA2;
	}
	public void setIdDebidoA2(String idDebidoA2) {
		this.idDebidoA2 = idDebidoA2;
	}
	public String getNombreDebidoA2() {
		return nombreDebidoA2;
	}
	public void setNombreDebidoA2(String nombreDebidoA2) {
		this.nombreDebidoA2 = nombreDebidoA2;
	}
	public String getIdDebidoA3() {
		return idDebidoA3;
	}
	public void setIdDebidoA3(String idDebidoA3) {
		this.idDebidoA3 = idDebidoA3;
	}
	public String getNombreDebidoA3() {
		return nombreDebidoA3;
	}
	public void setNombreDebidoA3(String nombreDebidoA3) {
		this.nombreDebidoA3 = nombreDebidoA3;
	}
	public String getIdZonaSuceso1() {
		return idZonaSuceso1;
	}
	public void setIdZonaSuceso1(String idZonaSuceso1) {
		this.idZonaSuceso1 = idZonaSuceso1;
	}
	public String getNombreZonaSuceso1() {
		return nombreZonaSuceso1;
	}
	public void setNombreZonaSuceso1(String nombreZonaSuceso1) {
		this.nombreZonaSuceso1 = nombreZonaSuceso1;
	}
	public String getIdZonaSuceso2() {
		return idZonaSuceso2;
	}
	public void setIdZonaSuceso2(String idZonaSuceso2) {
		this.idZonaSuceso2 = idZonaSuceso2;
	}
	public String getNombreZonaSuceso2() {
		return nombreZonaSuceso2;
	}
	public void setNombreZonaSuceso2(String nombreZonaSuceso2) {
		this.nombreZonaSuceso2 = nombreZonaSuceso2;
	}
	public String getIdZonaSuceso3() {
		return idZonaSuceso3;
	}
	public void setIdZonaSuceso3(String idZonaSuceso3) {
		this.idZonaSuceso3 = idZonaSuceso3;
	}
	public String getNombreZonaSuceso3() {
		return nombreZonaSuceso3;
	}
	public void setNombreZonaSuceso3(String nombreZonaSuceso3) {
		this.nombreZonaSuceso3 = nombreZonaSuceso3;
	}
	public int getNumeroDenunciantes() {
		return numeroDenunciantes;
	}
	public void setNumeroDenunciantes(int numeroDenunciantes) {
		this.numeroDenunciantes = numeroDenunciantes;
	}
	public Denuncia getDenuncia() {
		return denuncia;
	}
	public void setDenuncia(Denuncia denuncia) {
		this.denuncia = denuncia;
	}
	public String getNombreEntidadCompentente() {
		return nombreEntidadCompentente;
	}
	public void setNombreEntidadCompentente(String nombreEntidadCompentente) {
		this.nombreEntidadCompentente = nombreEntidadCompentente;
	}
	public int getTipoEntidadAtencion() {
		return tipoEntidadAtencion;
	}
	public void setTipoEntidadAtencion(int tipoEntidadAtencion) {
		this.tipoEntidadAtencion = tipoEntidadAtencion;
	}
	public String getNombreTipoEntidadAtencion() {
		return nombreTipoEntidadAtencion;
	}
	public void setNombreTipoEntidadAtencion(String nombreTipoEntidadAtencion) {
		this.nombreTipoEntidadAtencion = nombreTipoEntidadAtencion;
	}
	public String getFechaInicioAtencion() {
		return fechaInicioAtencion;
	}
	public void setFechaInicioAtencion(String fechaInicioAtencion) {
		this.fechaInicioAtencion = fechaInicioAtencion;
	}
	public int getDiasHabilesPlazo() {
		return diasHabilesPlazo;
	}
	public void setDiasHabilesPlazo(int diasHabilesPlazo) {
		this.diasHabilesPlazo = diasHabilesPlazo;
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
	public Date getFechaRegistroDenunciaAsignadaOrgano() {
		return fechaRegistroDenunciaAsignadaOrgano;
	}
	public void setFechaRegistroDenunciaAsignadaOrgano(
			Date fechaRegistroDenunciaAsignadaOrgano) {
		this.fechaRegistroDenunciaAsignadaOrgano = fechaRegistroDenunciaAsignadaOrgano;
	}
	public double getDiasHabilesTranscurridosDenunciaAsignada() {
		return diasHabilesTranscurridosDenunciaAsignada;
	}
	public void setDiasHabilesTranscurridosDenunciaAsignada(
			double diasHabilesTranscurridosDenunciaAsignada) {
		this.diasHabilesTranscurridosDenunciaAsignada = diasHabilesTranscurridosDenunciaAsignada;
	}
	public List<AccionesRealizadas> getLstAccionesRealizadas() {
		return lstAccionesRealizadas;
	}
	public void setLstAccionesRealizadas(
			List<AccionesRealizadas> lstAccionesRealizadas) {
		this.lstAccionesRealizadas = lstAccionesRealizadas;
	}

	
	

}
