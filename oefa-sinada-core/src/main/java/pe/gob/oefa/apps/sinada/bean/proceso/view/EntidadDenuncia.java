package pe.gob.oefa.apps.sinada.bean.proceso.view;

import pe.gob.oefa.apps.base.bean.BaseBean;

public class EntidadDenuncia extends BaseBean{

	
	private long 	idBandejaDetalle;			// id Entida Caso
	private int 	tipoEntidadComponente;	 	// Tipo de Entidad EFA - OEFA
	private String 	nombreTipoEntidad;			// Nombre Tipo de Entidad   EFA - OEFA
	private long 	idEfa;						// Id de la EFA
	private int 	direccionSupervicion; 		// Direccion Supervicion -- OEFA
	private int  	subDireccion;				// Sub Direccion  -- OEFA
	private int     coordinacion;
	private String  nombreEntidad;				// Nombre de la Entidad de la EFA o OEFA
	private int 	tipoAsignacion;				// Tipo de Asignacion de la entidad por denuncia
	private String 	nombreTipoAsignacion;		// nombre Tipo Asignacion
	private long 	idDenuncia;					// id de la Denuncia
	private String 	codigoDenuncia;				// Codigo de la Denuncia
	private String  fechaUltimaAccion;			// Fecha de la ultima accion de la entidad por denuncia
	private String 	tiempoTranscurrido;			// fecha del tiempo transcurrido desde la ultima accion de la entidad en la denuncia
	private int estadoEntidadDenuncia;
	private String nombreEstadoEntidadDenuncia;
	private String fechaPlazoAtencion;
	
	
	public long getIdBandejaDetalle() {
		return idBandejaDetalle;
	}
	public void setIdBandejaDetalle(long idBandejaDetalle) {
		this.idBandejaDetalle = idBandejaDetalle;
	}
	public int getTipoEntidadComponente() {
		return tipoEntidadComponente;
	}
	public void setTipoEntidadComponente(int tipoEntidadComponente) {
		this.tipoEntidadComponente = tipoEntidadComponente;
	}
	public String getNombreTipoEntidad() {
		return nombreTipoEntidad;
	}
	public void setNombreTipoEntidad(String nombreTipoEntidad) {
		this.nombreTipoEntidad = nombreTipoEntidad;
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
	public String getNombreEntidad() {
		return nombreEntidad;
	}
	public void setNombreEntidad(String nombreEntidad) {
		this.nombreEntidad = nombreEntidad;
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
	public String getFechaUltimaAccion() {
		return fechaUltimaAccion;
	}
	public void setFechaUltimaAccion(String fechaUltimaAccion) {
		this.fechaUltimaAccion = fechaUltimaAccion;
	}
	public String getTiempoTranscurrido() {
		return tiempoTranscurrido;
	}
	public void setTiempoTranscurrido(String tiempoTranscurrido) {
		this.tiempoTranscurrido = tiempoTranscurrido;
	}
	public int getEstadoEntidadDenuncia() {
		return estadoEntidadDenuncia;
	}
	public void setEstadoEntidadDenuncia(int estadoEntidadDenuncia) {
		this.estadoEntidadDenuncia = estadoEntidadDenuncia;
	}
	public String getNombreEstadoEntidadDenuncia() {
		return nombreEstadoEntidadDenuncia;
	}
	public void setNombreEstadoEntidadDenuncia(String nombreEstadoEntidadDenuncia) {
		this.nombreEstadoEntidadDenuncia = nombreEstadoEntidadDenuncia;
	}
	public String getFechaPlazoAtencion() {
		return fechaPlazoAtencion;
	}
	public void setFechaPlazoAtencion(String fechaPlazoAtencion) {
		this.fechaPlazoAtencion = fechaPlazoAtencion;
	}
	
	
	
	
}
